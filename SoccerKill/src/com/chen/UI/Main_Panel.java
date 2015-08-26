package com.chen.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Role;
import com.chen.tools.Mapping;
import com.chen.tools.TotalMapping;

public class Main_Panel extends JPanel implements MouseListener,ActionListener,Runnable{
	  JButton b_sure=new JButton("sure");
	  JButton b_cancel=new JButton("cancel");
	  JButton b_giveup=new JButton("end");
	  int mod=0;//0 for init stage 1 for panding stage 2 for getcard stage 3 for usecard stage 4 for losecard stage 5 for others stage 6 for being attacked stage
	  //7 for gameover
	  int action_mod=0;//player use shoot,1 for choose a shoot card 2 for shoot a role 3 for use a medi 4 for nearly kill a man. 
	  Role ShootAim;//the aim player shoot
	  TotalMapping tm=new TotalMapping(); //all the items in the panel mapping the items in the program was managed in this class
	  Robot current_robot;
	  int drive_mod=0;
	  int diemod_out=0;
	  boolean if_win=true;
	  
     public Main_Panel(){//init the panel
   	  this.setSize(600,600);
   	  this.setBackground(Color.orange);
   	  this.setLayout(null); 
   	  this.repaint();
   	  Graphics g = null;
   	  this.addMouseListener(this);
   	  tm.InitSpace();
   	  b_sure.setLayout(null);
   	  b_cancel.setLayout(null);
   	  b_giveup.setLayout(null);
   	  b_sure.setSize(30,20);
   	  b_cancel.setSize(30, 20);
   	  this.add(b_sure);
   	  this.add(b_cancel);
   	  this.add(b_giveup);
   	  b_sure.setBounds(390, 420, 60, 30);
   	  b_cancel.setBounds(390,450,60, 30);
   	  b_giveup.setBounds(390, 510, 60, 30);
   	  Font f=new Font("宋体",Font.BOLD,12);
   	  b_sure.setFont(f);
   	  b_cancel.setFont(f);
   	  b_giveup.setFont(f);
   	  b_sure.setEnabled(false);
   	  b_cancel.setEnabled(false);
   	  b_giveup.setEnabled(false);
   	  b_sure.addActionListener(this);
   	  b_cancel.addActionListener(this);
   	  b_giveup.addActionListener(this);
   	  Thread t=new Thread(tm);
   	  t.start();
     }
     public void paint(Graphics g)
     {//paint function
   	  super.paint(g);
   	  String filename = null;
   	  Vector<Mapping> map_middle=tm.getMap_middle();
   	  Vector<Mapping> map_role=tm.getMap_role();
   	  Vector<Mapping> map_down=tm.getMap_down();
   	  for(int i=0;i<map_role.size();i++)
   	  {
   			  Role role_temp=(Role)map_role.get(i).getobj();
   			  filename="role_pic/"+role_temp.getname()+".jpg";
   			  Image image=getToolkit().getImage(filename);
   	    	  g.drawImage(image, map_role.get(i).getX(),map_role.get(i).getY(),map_role.get(i).getWidth(),map_role.get(i).getHeight(),this);
   			  for(int j=0,length=map_role.get(i).getY();j<role_temp.getmaxlife();j++,length+=20)
   			  {
   				  Image image_temp;
   				  if(j<role_temp.getlife())
   				  {
   					  image_temp = getToolkit().getImage("image/soccer_alive.png");
   				  }
   				  else
   				  {
   					  image_temp = getToolkit().getImage("image/soccer.png");
   				  }
   				  g.drawImage(image_temp, map_role.get(i).getX(), length, 20,20,this);
   			  }
   			  Font font=new Font("KF-GB Gothic",Font.PLAIN,20);
   			  g.setFont(font);
   			  g.setColor(Color.RED);
   			  g.drawString(String.valueOf(role_temp.getCards().size()),map_role.get(i).getX()+130,map_role.get(i).getY()+180);
   	  }
   	  for(int i=0;i<map_down.size();i++)
   	  {
   			  Card card_temp=(Card)map_down.get(i).getobj();
   			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
   			  Image image=getToolkit().getImage(filename);
   	    	  g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
   	  }
   	  for(int i=0;i<map_middle.size();i++)
   	  {
   		  Card card_temp=(Card)map_middle.get(i).getobj();
			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
			  Image image=getToolkit().getImage(filename);
	    	  g.drawImage(image, map_middle.get(i).getX(),map_middle.get(i).getY(),map_middle.get(i).getWidth(),map_middle.get(i).getHeight(),this);
   	  }
   	  switch(mod)
   	  {
   	  case 1:g.drawString("judge stage", 0, 30);
   	         break;
   	  case 2:g.drawString("getcard stage", 0, 30);
   	         break;
   	  case 3:if(action_mod==0)
   		     g.drawString("action stage", 0, 30);
   	         else if(action_mod==1)
   	         g.drawString("please select an aim", 0, 30);
   	         else if(action_mod==4)
   	        g.drawString("if use a medi to save him", 0, 30);
   	         break;
   	  case 4:g.drawString("please choose"+(tm.getCurrentUser().getCards().size()-tm.getCurrentUser().getlife())+"to drop", 0, 30);
   	         break;
   	  case 5:g.drawString("out of your action", 0, 30);
   	         break;
   	  case 6:if(diemod_out==0)
   		     g.drawString("put drive or not?", 0, 30);
   	         else if(diemod_out==1)
   	         g.drawString("use a medi to save yourself?", 0, 30);
   	         break;
   	  case 7:if(if_win)
   		     g.drawString("game over you win!", 0, 30);
   	         else
   	         g.drawString("game over you lose!", 0, 30);
   	         break;
   	  }
	  }
	public void run()
	{//thread function
		
	    while(true)
	    {
	    	
	    	if(mod==0)
			{
				mod=1;
			}
	    	else if(mod==1)
			{
				mod=2;
			}
			else if(mod==2)
			{
				tm.addCardstouser(tm.getCurrentUser());
				mod=3;
			}	
			else if(mod==3)
			{
				
				if(action_mod==4)
				{
					b_cancel.setEnabled(true);
					b_giveup.setEnabled(false);
				}
				else
				{
					b_giveup.setEnabled(true);
				}
			}
			else if(mod==4)
			{
				if(tm.getCurrentUser().getCards().size()<=tm.getCurrentUser().getlife())
				{
					b_sure.setEnabled(false);
					b_giveup.setEnabled(false);
					b_cancel.setEnabled(false);
					tm.getCurrentUser().setshoot_time(1);
					tm.Card_num_clear();
					mod=5;
					current_robot=tm.getRobot();
					tm.addCardstouser(current_robot);
				}
				else
				{
				if(tm.getCard_giveupnum()==(tm.getCurrentUser().getCards().size()-tm.getCurrentUser().getlife()))
				{
					b_sure.setEnabled(true);
				}
				else
				{
					b_sure.setEnabled(false);
				}
				}
				
			}
			else if(mod==5)
			{
				Card temp;
				if((temp=current_robot.excute(tm.getMap_role()))!=null)
				{
					if(temp.gettype()=="shoot"&&temp.getaim().getobj()==tm.getCurrentUser())
					{
						Mapping m=new Mapping(100+tm.getMap_middle().size()*100,200,150,100);
						m.setObj(temp);
						tm.getMap_middle().add(m);
						mod=6;
						b_cancel.setEnabled(true);
					}
					else
					{
						Mapping m=new Mapping(100+tm.getMap_middle().size()*100,200,150,100);
						m.setObj(temp);
						tm.getMap_middle().add(m);
					}
				}
				else
				{
					Vector<Mapping> m=current_robot.giveUP();
				    for(int i=0;i<m.size();i++)
				    {
				    	tm.getMap_middle().add(m.get(i));
				    }
					mod=0;
				}
			}
			else if(mod==7)
			{
				repaint();
				break;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.repaint();
	    }
	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		Mapping temp=tm.findTheObject(x,y);
	  if(temp!=null)
	  {
		  System.out.println(mod+" "+action_mod);
		if(mod==3)
		{
	          if(temp.getobj() instanceof Card&&action_mod==0)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype()+" "+(card_temp.gettype().equals("shoot")&&tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==450));
	          		if(card_temp.gettype().equals("shoot")&&tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 b_cancel.setEnabled(true);
	          		    	 action_mod=1;
	          		}
	          		else if(card_temp.gettype().equals("medi")&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 if(tm.getCurrentUser().getmaxlife()>tm.getCurrentUser().getlife())
	          		    	 {
	          		    	    b_sure.setEnabled(true);
	          		    	 }
	          		    	 action_mod=3;
	          		    	 b_cancel.setEnabled(true);
	          		    	
	          		}
	          	}
	          else if(action_mod==1&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot")&&tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		    	 b_cancel.setEnabled(false);
	          		         action_mod=0;
	          		}
	          }
	          else if(action_mod==3&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		    	 b_cancel.setEnabled(false);
	          		         action_mod=0;
	          		}
	          }
	          else if(action_mod==4&&temp.getobj() instanceof Card)
	          {//if save someone
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		}
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 b_sure.setEnabled(true);
	          		}
	          }
	          else if(action_mod==1&&temp.getobj() instanceof Role)
	          {
	        	  ShootAim=(Role)temp.getobj();
	        	  b_sure.setEnabled(true);
	          }
        }
		if(mod==4)
		{
			if(temp.getobj() instanceof Card)
	        {
	          	 Card card_temp=(Card)temp.getobj();
	             System.out.println(card_temp.gettype());
	          	 if(temp.getY()==450&&tm.getCard_giveupnum()!=(tm.getCurrentUser().getCards().size()-tm.getCurrentUser().getlife()))
	          	 {
	          		  temp.setY(400);
	          		  b_cancel.setEnabled(true);
	          		  tm.setCard_giveupnum(tm.getCard_giveupnum()+1);
	          	 }
	          	 else if(temp.getY()==400)
          		 {
          		      temp.setY(450);
          		      b_cancel.setEnabled(false);
          		      tm.setCard_giveupnum(tm.getCard_giveupnum()-1);
          		 }
	        }
		}
		if(mod==6&&diemod_out!=1)
		{
			 if(drive_mod==0&&temp.getobj() instanceof Card)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("drive")&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 b_sure.setEnabled(true);
	          		    	 b_cancel.setEnabled(true);
	          		    	 drive_mod=1;
	          		}
	          		
	          	}
	          else if(temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          	    System.out.println(card_temp.gettype().equals("drive")&&temp.getY()==400);
	          		if(card_temp.gettype().equals("drive")&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		    	 b_sure.setEnabled(false);
	          		    	 drive_mod=0;
	          		}
	          }
		}
		else if(mod==6&&diemod_out!=0)
		{
			 if(diemod_out==1&&temp.getobj() instanceof Card)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 b_sure.setEnabled(true);
	          		    	 b_cancel.setEnabled(true);
	          		    	 diemod_out=2; 
	          		}
	          		
	          	}
	          else if(drive_mod==2&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          	    System.out.println(card_temp.gettype().equals("medi")&&temp.getY()==400);
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		    	 b_sure.setEnabled(false);
	          		    	 diemod_out=1; 
	          		}
	          }
		}
		repaint();
	 }
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b_sure)
		{
			if(mod==3&&action_mod==1)
			{
				action_mod=0;
				tm.CardGiveUP();
				Robot rb_temp=(Robot)ShootAim;
				Card temp;
				if((temp=rb_temp.beattack())!=null)
				{
				       Mapping m=new Mapping(100+tm.getMap_middle().size()*100,200,150,100);
				       m.setObj(temp);
				       tm.getMap_middle().add(m);
				       System.out.println(tm.getMap_middle().size());
				}
				if(rb_temp.getlife()==0)
				{
					action_mod=4;
				}
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				tm.getCurrentUser().setshoot_time(0);
			}
			else if(mod==3&&action_mod==3)
			{
				action_mod=0;
				tm.CardGiveUP();
				tm.getCurrentUser().liferise();
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
			}
			else if(mod==3&&action_mod==4)
			{
				action_mod=0;
				tm.CardGiveUP();
				ShootAim.liferise();
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
			}
			if(mod==4)
			{
				tm.CardGiveUP();
				b_sure.setEnabled(false);
				b_giveup.setEnabled(false);
				b_cancel.setEnabled(false);
				tm.getCurrentUser().setshoot_time(1);
				tm.Card_num_clear();
				mod=5;
				current_robot=tm.getRobot();
				tm.addCardstouser(current_robot);
			}
			else if(mod==6&&diemod_out==2)
			{
				tm.CardGiveUP();
				diemod_out=0;
				tm.getCurrentUser().liferise();
				b_sure.setEnabled(false);
				b_giveup.setEnabled(false);
				b_cancel.setEnabled(false);
				mod=5;
			}
			else if(mod==6&&diemod_out==0)
			{
				tm.CardGiveUP();
				drive_mod=0;
				b_sure.setEnabled(false);
				b_giveup.setEnabled(false);
				b_cancel.setEnabled(false);
				mod=5;
			}
		}
		else if(e.getSource()==b_giveup)
		{
			 for(int i=0;i<tm.getMap_down().size();i++)
             {
            	 if(tm.getMap_down().get(i).getY()==400)
            		 tm.setCard_giveupnum(tm.getCard_giveupnum()+1);
             }
			mod=4;
		}
		else if(e.getSource()==b_cancel)
		{
			if(mod==3||mod==4)
			{
				if(action_mod==4)
				{
					Robot temp=(Robot)ShootAim;
					Card card_temp=(Card)temp.saveyourself();
					if(card_temp!=null)
					{
						   Mapping m=new Mapping(100+tm.getMap_middle().size()*100,200,150,100);
					       m.setObj(card_temp);
					       tm.getMap_middle().add(m);
					       System.out.println(tm.getMap_middle().size());
					       b_sure.setEnabled(false);
							b_cancel.setEnabled(false);
							tm.redistributeSpace();
							action_mod=0;
					}
					else
					{
						 mod=7;
						 if_win=true;
					}
				}
				else 
				{
					 System.out.println(tm.getMap_middle().size());
				     b_sure.setEnabled(false);
				     b_cancel.setEnabled(false);
				     tm.redistributeSpace();
					 action_mod=0;
				}
				action_mod=0;
			}
			else if(mod==6&&diemod_out==0)
			{
				tm.redistributeSpace();
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				tm.getCurrentUser().beattack();
				if(tm.getCurrentUser().getlife()<=0)
				{
					if(current_robot.ifSave()!=null)
					{
						
					}
					else
					{
						diemod_out=1;
					}
					b_cancel.setEnabled(true);
				}
				else
				mod=5;
			}
			else if(mod==6&&diemod_out!=0)
			{
				tm.redistributeSpace();
				mod=7;
				if_win=false;
			}
		}
		repaint();
	}
}
