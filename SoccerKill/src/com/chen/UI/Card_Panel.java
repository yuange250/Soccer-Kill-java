package com.chen.UI;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chen.cards.Card;
import com.chen.roles.Messi;
import com.chen.roles.Role;
import com.chen.roles.Ronaldo;
import com.chen.tools.Mapping;

public class Card_Panel extends JPanel implements MouseListener,ActionListener{
	  int locationy=50;
	  Messi messi=new Messi();
	  Ronaldo ronaldo=new Ronaldo();
	  JButton b_sure=new JButton("确定");
	  JButton b_cancel=new JButton("取消");
	  int mod=0;
	  Role aim_role;
	  Mapping aim_map;
	  Vector<Mapping> map_down=new Vector<Mapping>();
	  Vector<Mapping> map_middle=new Vector<Mapping>();
      public Card_Panel(){
    	  this.setSize(600,600);
    	  this.setBackground(Color.orange);
    	  this.setLayout(null); 
    	  this.repaint();
    	  Image image=getToolkit().getImage("cards/shoot_hearts_J.jpg");
    	  Graphics g = null;
    	  this.addMouseListener(this);
    	  this.distributeSpace();
    	  b_sure.setLayout(null);
    	  b_cancel.setLayout(null);
    	  b_sure.setSize(30,20);
    	  b_cancel.setSize(30, 20);
    	  this.add(b_sure);
    	  this.add(b_cancel);
    	  b_sure.setBounds(390, 420, 60, 30);
    	  b_cancel.setBounds(390,450,60, 30);
    	  Font f=new Font("宋体",Font.BOLD,12);
    	  b_sure.setFont(f);
    	  b_cancel.setFont(f);
    	  b_sure.setEnabled(false);
    	  b_cancel.setEnabled(false);
    	  b_sure.addActionListener(this);
    	  b_cancel.addActionListener(this);
 //   	  b_sure.setBorderPainted(false);
//    	  BufferedImage tag = new BufferedImage(150, 200,
//                  BufferedImage.TYPE_INT_RGB);
//    	  
//          tag.getGraphics().drawImage(image, 0, 0,150, 200, null); 
//          Icon icon = new ImageIcon(tag);
//    	  jb1.setIcon(icon);
//    	  jb1.setBounds(0, 0, 200, 200);
      }
      public void paint(Graphics g)
      {
    	  super.paint(g);
    	  String filename = null;
    	  for(int i=0;i<map_down.size();i++)
    	  {
    		  if(map_down.get(i).getobj() instanceof Role)
    		  {
    			  Role role_temp=(Role)map_down.get(i).getobj();
    			  filename="role_pic/"+role_temp.getname()+".jpg";
    			  Image image=getToolkit().getImage(filename);
    	    	  g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
    			  for(int j=0,length=map_down.get(i).getY();j<role_temp.getmaxlife();j++,length+=20)
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
    				  g.drawImage(image_temp, map_down.get(i).getX(), length, 20,20,this);
    			  }
    		  }
    		  else
    		  {
    			  Card card_temp=(Card)map_down.get(i).getobj();
    			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
    			  Image image=getToolkit().getImage(filename);
    	    	  g.drawImage(image, map_down.get(i).getX(),map_down.get(i).getY(),map_down.get(i).getWidth(),map_down.get(i).getHeight(),this);
    		  }
	    	  
    	  }
    	  for(int i=0;i<map_middle.size();i++)
    	  {
    		  Card card_temp=(Card)map_middle.get(i).getobj();
			  filename="cards/"+card_temp.gettype()+"_"+card_temp.getcolor()+"_"+card_temp.getpoint()+".jpg";
			  Image image=getToolkit().getImage(filename);
	    	  g.drawImage(image, map_middle.get(i).getX(),map_middle.get(i).getY(),map_middle.get(i).getWidth(),map_middle.get(i).getHeight(),this);
    	  }
	  }
	/**
	 * Listener section 
	 * listen the mouse
      **/
    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		Mapping temp=findTheObject(x,y);
    	System.out.println(mod);
	    if(temp!=null)
	    {
	    	if(mod==0)
	    	{
	          	if(temp.getobj() instanceof Card)
	          	{
	          		Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot"))
	          		{
	          			       aim_map=temp;
	          		    	   temp.setY(400);
	          		    	   b_cancel.setEnabled(true);
	          		    	   mod=1;
	          		}
	          	}
	    	}
	    	else if(mod==1)
	    	{
	    		if(temp.getobj() instanceof Card)
	          	{
	          		Card card_temp=(Card)temp.getobj();
	          		if(temp.getY()==400)
	          		{
	          			       aim_map=null;
	          		    	   temp.setY(450);
	          		    	   b_sure.setEnabled(false);
	          		    	   b_cancel.setEnabled(false);
	          		    	   mod=0;
	          		}
	          	}
	    		else if(temp.getobj() instanceof Role)
	    		{
	    			Role role_temp=(Role)temp.getobj();
	    			aim_role=role_temp;
	          	    b_sure.setEnabled(true);
	    		}
	    	}
	    }
    	repaint();
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void distributeSpace()
	{
		int i=0;
		int locX=0;
		int locY=0;
		int height=0;
		int width=0;
		map_down=new Vector<Mapping>();
		Mapping m=new Mapping(450,400,200,150);
		m.setObj(messi);
		map_down.add(m);
	    m=new Mapping(225,0,200,150);
		m.setObj(ronaldo);
		map_down.add(m);
		locY=450;
		height=150;
		width=100;
        for(;i<messi.getCards().size();i++,locX+=100)
        {
        	m=new Mapping(locX,locY,height,width);
        	m.setObj(messi.getCards().get(i));
        	map_down.add(m);
        }
	}
	public void redistributeSpace()
	{
		int i=0;
		int locX=0;
		int locY=0;
		int height=0;
		int width=0;
	}
	public Mapping findTheObject(int x,int y){
		for(int i=0;i<map_down.size();i++)
		{
			if(x>map_down.get(i).getX()&&x<(map_down.get(i).getX()+map_down.get(i).getWidth())&&y>map_down.get(i).getY()&&y<(map_down.get(i).getY()+map_down.get(i).getHeight()))
			{
			       return map_down.get(i);	
			}
		}
		return null;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b_sure)
		{
			Card temp=(Card)aim_map.getobj();
			aim_role.beattack();
			System.out.println(messi.getCards().size()+" "+map_down.size());
			for(int i=0;i<messi.getCards().size();i++)
			{
				if(messi.getCards().get(i)==temp)
				{
					messi.getCards().remove(i);
				}
			}
		    for(int i=0;i<map_down.size();i++)
		    {
		    	if(map_down.get(i)==aim_map)
		    	{
		    		map_middle.add(aim_map);
		    		map_down.remove(i);
		    		aim_map.setY(200);
		    		aim_map.setX(200);
		    	}
		    }
		    distributeSpace();
		    System.out.println(messi.getCards().size()+" "+map_down.size());
		    repaint();
		}
		else if(e.getSource()==b_cancel)
		{
			
		}
	}
}
