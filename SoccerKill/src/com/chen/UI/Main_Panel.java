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
import javax.swing.border.EmptyBorder;

import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Role;
import com.chen.tools.Mapping;
import com.chen.tools.TotalMapping;

public class Main_Panel extends JPanel implements Runnable{
	  //Jbutton part 
	  JButton b_sure=new JButton("sure");
	  JButton b_cancel=new JButton("cancel");
	  JButton b_end=new JButton("end");
	  
	  //mod_part
	  int mod=0;//0 for init stage 1 for panding stage 2 for getcard stage 3 for usecard stage 4 for losecard stage 5 for others stage 6 for being attacked stage
	  //7 for gameover
	  int action_mod=0;//player use shoot,1 for choose a shoot card 2 for shoot a role 3 for use a medi 4 for nearly kill a man. 
	  int drive_mod=0;//out of the player's action,someone attack the player if he would use a drive to avoid 
	  int ifsave_mod=0;//out of the player's action,the player is gong to die,if he will save himself
	  boolean if_win=true;
	  int point_line=0;//one use a card and one is the victim of the card,0 for no line 1 for player to robots,2 for robot to robot  
	  
	  //others
	  Role shoot_aim;//the aim player to shoot
	  Robot current_robot;//mod 5 robot's action
	  TotalMapping tm=new TotalMapping(); //all the items in the panel mapping the items in the program was managed in this class
   
	  MouseListener_panel mp=new MouseListener_panel(this);
	  ActionListener_panel ap=new ActionListener_panel(this);
	public Main_Panel(){//init the panel
   	  this.setSize(800,700);
   	  this.setBackground(Color.orange);
   	  this.setLayout(null); 
   	  this.repaint();
   	  Graphics g = null;
   	  this.addMouseListener(mp);
   	  tm.InitSpace();
   	  b_sure.setLayout(null);
   	  b_cancel.setLayout(null);
   	  b_end.setLayout(null);
   	  b_sure.setSize(60,30);
   	  b_cancel.setSize(60, 30);
   	  this.add(b_sure);
   	  this.add(b_cancel);
   	  this.add(b_end);
   	  b_sure.setBounds(590, 520, 60, 30);
   	  b_sure.setBorder(new EmptyBorder(0, 0, 0, 0));
   	  b_cancel.setBounds(590,550,60, 30);
   	  b_cancel.setBorder(new EmptyBorder(0, 0, 0, 0));
   	  b_end.setBounds(590, 610, 60, 30);
   	  b_end.setBorder(new EmptyBorder(0, 0, 0, 0));
   	  Font f=new Font("Arial",Font.BOLD,12);
   	  b_sure.setFont(f);
   	  b_cancel.setFont(f);
   	  b_end.setFont(f);
   	  b_sure.setEnabled(false);
   	  b_cancel.setEnabled(false);
   	  b_end.setEnabled(false);
   	  b_sure.addActionListener(ap);
   	  b_cancel.addActionListener(ap);
   	  b_end.addActionListener(ap);
   	  Thread t=new Thread(tm);//the auto clean is start,map_middle is autoclean
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
   	  {//paint role
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
   	  {//paint map_middle
   			  Card card_temp=(Card)map_down.get(i).getobj();
   			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
   			  Image image=getToolkit().getImage(filename);
   	    	  g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
   	  }
   	  for(int i=0;i<map_middle.size();i++)
   	  {//paint map_down
   		  Card card_temp=(Card)map_middle.get(i).getobj();
			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
			  Image image=getToolkit().getImage(filename);
	    	  g.drawImage(image, map_middle.get(i).getX(),map_middle.get(i).getY(),map_middle.get(i).getWidth(),map_middle.get(i).getHeight(),this);
   	  }
   	  if(current_robot!=null)
   	  {
   	     Mapping m_temp=tm.getRoleMap(current_robot);
   	     g.drawString("current role", m_temp.getX(), m_temp.getY()+200);
   	  }
   	  switch(mod)
   	  {//paint instructions
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
   	  case 6:if(ifsave_mod==0)
   		     g.drawString("put drive or not?", 0, 30);
   	         else if(ifsave_mod==1)
   	         g.drawString("use a medi to save yourself?", 0, 30);
   	         break;
   	  case 7:if(if_win)
   		     g.drawString("game over you win!", 0, 30);
   	         else
   	         g.drawString("game over you lose!", 0, 30);
   	         break;
   	  }
   	  switch(point_line)
   	  {
   	  case 0:break;
   	  case 1:g.drawLine(tm.getRoleMap(tm.getCurrentUser()).getX()+75, tm.getRoleMap(tm.getCurrentUser()).getY()+100, tm.getRoleMap(shoot_aim).getX()+75, tm.getRoleMap(shoot_aim).getY()+100);
   	         break;
   	  case 2:g.drawLine(tm.getRoleMap(current_robot).getX()+75, tm.getRoleMap(current_robot).getY()+100, tm.getRoleMap(shoot_aim).getX()+75, tm.getRoleMap(shoot_aim).getY()+100);
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
					b_end.setEnabled(false);
				}
				else
				{
					b_end.setEnabled(true);
				}
			}
			else if(mod==4)
			{
				if(tm.getCurrentUser().getCards().size()<=tm.getCurrentUser().getlife())
				{//when drop cards,if the role needn't drop any card,just skip this mod
					b_sure.setEnabled(false);
					b_end.setEnabled(false);
					b_cancel.setEnabled(false);
					tm.getCurrentUser().setshoot_time(1);
					tm.cards_drop_num_clear();
					mod=5;
					current_robot=(Robot)tm.getRobot(tm.getCurrentUser());
					tm.addCardstouser(current_robot);
				}
				else
				{
				if(tm.getcards_drop_num()==(tm.getCurrentUser().getCards().size()-tm.getCurrentUser().getlife()))
				{//when the num of the selected cards is equal to the num the role should drop, b_sure is on
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
					if(temp.gettype()=="shoot")
					{//the robot shoot someone
						shoot_aim=(Role)temp.getaim().getobj();
						point_line=2;
						repaint();
						if(shoot_aim==tm.getCurrentUser())
						{
						Mapping m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
						m.setObj(temp);
						tm.getMap_middle().add(m);
						mod=6;
						b_cancel.setEnabled(true);
						}
						else
						{
							Mapping m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
							m.setObj(temp);
							tm.getMap_middle().add(m);
							Card card_temp;
							Robot rb_temp=(Robot)shoot_aim;
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
							if((card_temp=rb_temp.beattack())!=null)
							{
							       m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
							       m.setObj(card_temp);
							       tm.getMap_middle().add(m);
							}
							
							point_line=0;
						}
					}
					else
					{//the role use a card that others can not disturb
						Mapping m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
						m.setObj(temp);
						tm.getMap_middle().add(m);
					}
				}
				else
				{//the robot has nothing to do
					Vector<Mapping> m=current_robot.end();
				    for(int i=0;i<m.size();i++)
				    {
				    	tm.getMap_middle().add(m.get(i));
				    }
				    Role nextrole ;
					if((nextrole=tm.getRobot(current_robot))!=tm.getCurrentUser())
					{
						current_robot=(Robot)nextrole;
						tm.addCardstouser(current_robot);
					}
					else
					{
						current_robot=null;
						mod=0;
					}
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
}
