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
	  JButton b_sure=new JButton("确定");
	  JButton b_cancel=new JButton("取消");
	  JButton b_giveup=new JButton("弃牌");
	  int mod=0;
	  int shoot_mod=0;
	  Role ShootAim;
	  Vector<Card> card_to_giveup=new Vector<Card>();
	  TotalMapping tm=new TotalMapping(); 
	  
	  
     public Main_Panel(){
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
     }
     public void paint(Graphics g)
     {
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
	  }
	public void run()
	{
		int middle_clear_flag=0;//the map_middle is auto clean
	    while(true)
	    {
	    	if(middle_clear_flag!=50&&tm.getMap_middle().size()!=0)
	    	{
	    		middle_clear_flag++;
	    	}
	    	else if(middle_clear_flag==50&&tm.getMap_middle().size()!=0)
	    	{
	    		tm.map_middle_clear();
	    		middle_clear_flag=0;
	    	}
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
				tm.addCardstoCurrentuser();
				mod=3;
			}	
			else if(mod==3)
			{
				b_giveup.setEnabled(true);
			}
			else if(mod==4)
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
		if(mod==3)
		{
			  tm.map_middle_clear();
	          if(temp.getobj() instanceof Card&&shoot_mod==0)
	          {
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot")&&tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==450)
	          		{
	          		    	 temp.setY(400);
	          		    	 b_cancel.setEnabled(true);
	          		}
	          		shoot_mod=1;
	          	}
	          else if(shoot_mod==1&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot")&&tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==400)
	          		{
	          		    	 temp.setY(450);
	          		    	 b_cancel.setEnabled(false);
	          		}
	          		shoot_mod=0;
	          }
	          else if(shoot_mod==1&&temp.getobj() instanceof Role)
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
			if(mod==3&&shoot_mod==1)
			{
				tm.CardGiveUP();
				this.repaint();
				Robot rb_temp=(Robot)ShootAim;
				Card temp;
				if((temp=rb_temp.beattack())!=null)
				{
				       Mapping m=new Mapping(100+tm.getMap_middle().size()*100,200,150,100);
				       m.setObj(temp);
				       tm.getMap_middle().add(m);
				       System.out.println(tm.getMap_middle().size());
				}
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				tm.getCurrentUser().setshoot_time(0);
			}
		}
		else if(e.getSource()==b_giveup)
		{
			mod=4;
		}
		repaint();
	}
}
