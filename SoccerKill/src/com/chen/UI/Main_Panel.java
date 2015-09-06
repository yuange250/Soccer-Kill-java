package com.chen.UI;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.chen.Main.ActionListener_panel;
import com.chen.Main.GameMainThread;
import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Role;
import com.chen.tools.Mapping;
import com.chen.tools.TotalMapping;

public class Main_Panel extends JPanel{
	  //Jbutton part 
	 public  JButton b_sure=new JButton("sure");
	 public  JButton b_cancel=new JButton("cancel");
	 public JButton b_end=new JButton("end");
	 public JTextArea jt;
	 private GameMainThread gmt;
public Main_Panel(){//init the panel
   	  this.setSize(1000,700);
   	  this.setBackground(Color.orange);
   	  this.setLayout(null); 
  // 	  this.repaint();
   	  Graphics g = null;
   	//  this.addMouseListener(mp);
   	  b_sure.setLayout(null);
   	  b_cancel.setLayout(null);
   	  b_end.setLayout(null);
   	  b_sure.setSize(60,30);
   	  b_cancel.setSize(60, 30);
   	  jt=new   JTextArea(200,200); 
      jt.setFont(new   Font( "标楷体 ",Font.BOLD,16)); 
      jt.setLineWrap(true);//激活自动换行功能 
      jt.setWrapStyleWord(true);//激活断行不断字功能 
      JScrollPane js=new JScrollPane(jt);
   	  this.add(js);
   	  this.add(b_sure);
   	  this.add(b_cancel);
   	  this.add(b_end);
   	  js.setBounds(800,0,200,200);
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
//   	  b_sure.addActionListener(ap);
//   	  b_cancel.addActionListener(ap);
//   	  b_end.addActionListener(ap);
//   	  Thread t=new Thread(tm);//the auto clean is start,map_middle is autoclean
//  	  t.start();
   
   	 
   }
  public void paint(Graphics g)
     {//paint function
   	  super.paint(g);
   	  String filename = null;
   	  Vector<Mapping> map_middle=gmt.getTotalMapping().getMap_middle();
   	  Vector<Mapping> map_role=gmt.getTotalMapping().getMap_role();
   	  Vector<Mapping> map_down=gmt.getTotalMapping().getMap_down();
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
   	          if(role_temp.getlife()<=0&&role_temp.ifAlive())
   	          {
   	        	 g.drawString("Save me!",map_role.get(i).getX(),map_role.get(i).getY()+100);
   	          }
   			  if(!role_temp.ifAlive())
   	          {
	        	  String s="die";
   	        	  switch(role_temp.getIdentity())
   	        	  {  
   	        	     case 2:s="zhongchen \n"+s;
   	        	     break;
   	        	     case 3:s="fanzie \n"+s;
   	        	     break;
   	        	     case 4:s="neijian \n"+s;
   	        	     break;
   	        	  }
      	    	 g.drawString(s,map_role.get(i).getX(),map_role.get(i).getY()+100);
   	          }
   	          if(gmt.getMod()==3&&gmt.getActionMod()==1&&(!map_role.get(i).getEnable()))
   	          {
   	        	image=getToolkit().getImage("image/gray.png");
   	            g.drawImage(image, map_role.get(i).getX(),map_role.get(i).getY(),map_role.get(i).getWidth(),map_role.get(i).getHeight(),this);
   	          }
   	  }
   	  for(int i=0;i<map_down.size();i++)
   	  {//paint map_middle
   			  Card card_temp=(Card)map_down.get(i).getobj();
   			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
   			  Image image=getToolkit().getImage(filename);
   	    	  g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
   	          if(!card_temp.getEnable())
   	          {
   	               image=getToolkit().getImage("image/gray.png");
   	               g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
   	          }
   	  }
   	  for(int i=0;i<map_middle.size();i++)
   	  {//paint map_down
   		  Card card_temp=(Card)map_middle.get(i).getobj();
			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
			  Image image=getToolkit().getImage(filename);
	    	  g.drawImage(image, map_middle.get(i).getX(),map_middle.get(i).getY(),map_middle.get(i).getWidth(),map_middle.get(i).getHeight(),this);
   	  }
   	  if(gmt.getCurrentRobot()!=null)
   	  {
   	     Mapping m_temp=gmt.getTotalMapping().getRoleMap(gmt.getCurrentRobot());
   	     g.drawString("current role", m_temp.getX(), m_temp.getY()+200);
   	  }
   	  switch(gmt.getMod())
   	  {//paint instructions
   	  case 1:g.drawString("judge stage", 0, 30);
   	         break;
   	  case 2:g.drawString("getcard stage", 0, 30);
   	         break;
   	  case 3:if(gmt.getActionMod()==0)
   		     g.drawString("action stage", 0, 30);
   	         else if(gmt.getActionMod()==1)
   	         g.drawString("please select an aim", 0, 30);
   	         else if(gmt.getActionMod()==4)
   	        g.drawString("if use a medi to save him", 0, 30);
   	         break;
   	  case 4:g.drawString("please choose"+(gmt.getTotalMapping().getCurrentUser().getCards().size()-gmt.getTotalMapping().getCurrentUser().getlife())+"to drop", 0, 30);
   	         break;
   	  case 5:g.drawString("out of your action", 0, 30);
   	         break;
   	  case 6:if(gmt.getIfSaveMod()==0)
   		     g.drawString("put drive or not?", 0, 30);
   	         else if(gmt.getIfSaveMod()==1)
   	         g.drawString("use a medi to save yourself?", 0, 30);
   	         break;
   	  case 7:if(gmt.IfWin())
   		     g.drawString("game over you win!", 0, 30);
   	         else
   	         g.drawString("game over you lose!", 0, 30);
   	  case 8:if(gmt.getIfSaveMod()==1)
   		     g.drawString("if save him?", 0, 30); 
   	         break;
   	  }
   	  switch(gmt.getPointLine())
   	  {
   	  case 0:break;
   	  case 1:g.drawLine(gmt.getTotalMapping().getRoleMap(gmt.getTotalMapping().getCurrentUser()).getX()+75, gmt.getTotalMapping().getRoleMap(gmt.getTotalMapping().getCurrentUser()).getY()+100, gmt.getTotalMapping().getRoleMap(gmt.getShootAim()).getX()+75, gmt.getTotalMapping().getRoleMap(gmt.getShootAim()).getY()+100);
   	         break;
   	  case 2:g.drawLine(gmt.getTotalMapping().getRoleMap(gmt.getCurrentRobot()).getX()+75, gmt.getTotalMapping().getRoleMap(gmt.getCurrentRobot()).getY()+100, gmt.getTotalMapping().getRoleMap(gmt.getShootAim()).getX()+75, gmt.getTotalMapping().getRoleMap(gmt.getShootAim()).getY()+100);
   	         break;
   	  }
	}
	public void setActionListenerforButton(ActionListener_panel ap)
	{
		this.b_cancel.addActionListener(ap);
		this.b_end.addActionListener(ap);
		this.b_sure.addActionListener(ap);
	}
	public void setGameMainThread(GameMainThread gmt)
	{
		this.gmt=gmt;
	}
}
