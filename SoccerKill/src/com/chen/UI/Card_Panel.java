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
import com.chen.robot.Robot;
import com.chen.roles.Messi;
import com.chen.roles.Role;
import com.chen.roles.Ronaldo;
import com.chen.tools.CardGiveUPtool;
import com.chen.tools.GameManager;
import com.chen.tools.Mapping;
import com.chen.tools.TotalMapping;

public class Card_Panel extends JPanel implements MouseListener,ActionListener{
	  JButton b_sure=new JButton("确定");
	  JButton b_cancel=new JButton("取消");
	  JButton b_giveup=new JButton("弃牌");
	  Robot robot_temp;
	  int mod=0;//0 for going to pick a card,1 for going to choose the role to kill, 2 for going to giveup
	  //3 for other player is going on,4 for current_role being shoot;
	  Role current_role;
	  Role active_role;
	  Role aim_role;
	  Mapping aim_map;
	  Vector<Card> card_to_giveup=new Vector<Card>();
	  TotalMapping tm=new TotalMapping();
	  int num_to_giveup=0;
	  
      public Card_Panel(){
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
    	  b_sure.addActionListener(this);
    	  b_cancel.addActionListener(this);
    	  b_giveup.addActionListener(this);
    	  
 //   	  b_sure.setBorderPainted(false);
//    	  BufferedImage tag = new BufferedImage(150, 200,
//                  BufferedImage.TYPE_INT_RGB);
//    	  
//          tag.getGraphics().drawImage(image, 0, 0,150, 200, null); 
//          Icon icon = new ImageIcon(tag);
//    	  jb1.setIcon(icon);
//    	  jb1.setBounds(0, 0, 200, 200);
      }
     /* public boolean robot_excute()
      {
    	    tm.map_middle_clear();
		    Card temp;
			if((temp=robot_temp.excute(map_role))!=null)
			{
				Mapping m_temp=new Mapping(200,200,150,100);
				m_temp.setObj(temp);
			    map_middle.add(m_temp);
				this.paint(this.getGraphics());
				aim_role=(Role)temp.getaim().getobj();
				if(aim_role==current_role)
				{
					mod=4;
					Font f=new Font("宋体",Font.BOLD,50);
					Graphics g=this.getGraphics();
					g.setFont(f);
					g.drawString("是否出闪", 180, 300);
					b_cancel.setEnabled(true);
				}
				return true;
			}
			else
			{
				map_middle=robot_temp.giveUP();
				this.redistributeSpace();
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				this.paint(this.getGraphics());
			    return false;
			}
      }*/
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
  
  	/**/

	/**
	 * Listener section 
	 * listen the mouse
	 * and the button
      **/
   public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		Mapping temp=tm.findTheObject(x,y);
	    if(temp!=null)
	    {
	    	if(mod==0)
	    	{
	    	    tm.map_middle_clear();
	          	if(temp.getobj() instanceof Card)
	          	{
	          		Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot")&&current_role.getshoot_time()!=0)
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
	    		tm.map_middle_clear();
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
	    	else if(mod==2)
	    	{
	    	    tm.map_middle_clear();
	    		if(temp.getobj() instanceof Card)
	          	{
	          		Card card_temp=(Card)temp.getobj();
	          		if(temp.getY()==450&&CardGiveUPtool.countnum(tm.getMap_down())!=num_to_giveup)
	          			temp.setY(400);
	          		else if(temp.getY()==400)
	          			temp.setY(450);
	          	}
	    		if(CardGiveUPtool.countnum(tm.getMap_down())==num_to_giveup)
	    		{
	    			 b_sure.setEnabled(true);
	    		}
	    		else
	    		{
	    			 b_sure.setEnabled(false);
	    		}
	    		
	    	}
	    	else if(mod==4)
	    	{
	    		if(temp.getobj() instanceof Card)
	          	{
	          		Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("drive"))
	          		{
	          			       aim_map=temp;
	          		    	   temp.setY(400);
	          		    	   b_sure.setEnabled(true);
	          		}
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b_sure)
		{
			if(mod==1)
			{
				Card temp=(Card)aim_map.getobj();
				tm.getMap_middle().add(aim_map);
	    		aim_map.setY(200);
	    		aim_map.setX(200);
				for(int i=0;i<current_role.getCards().size();i++)
				{
					if(current_role.getCards().get(i)==temp)
					{
					     current_role.getCards().remove(i);
					}
				}
			    tm.redistributeSpace();
			    this.paint(this.getGraphics());
			    
			    current_role.setshoot_time(0);
			    Card temp2;
				if((temp2=aim_role.beattack())!=null)
				{
				       Mapping map_temp=new Mapping(300,200,150,100);
				       map_temp.setObj(temp2);
				       tm.getMap_middle().add(map_temp);
				}
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				mod=0;
				tm.redistributeSpace();
			    this.paint(this.getGraphics());
			   
			}
			else if(mod==2)
			{
				map_middle=CardGiveUPtool.pickout(map_down);
				for(int i=0;i<map_middle.size();i++)
				{
					Card temp=(Card)map_middle.get(i).getobj();
					CardGiveUPtool.deleteFromCardArray(current_role.getCards(), temp);
				}
				this.redistributeSpace();
				b_sure.setEnabled(false);
				b_cancel.setEnabled(false);
				this.paint(this.getGraphics());
			    mod=4;
			    gm.nextRole();
				robot_temp=(Robot)gm.nextRole();
				gm.distributeCards(robot_temp);
			    if(!this.robot_excute())
			    {
			    	mod=0;
			    	gm.distributeCards(current_role);
			    	this.redistributeSpace();
			    	repaint();
			    }
			}
			else if(mod==4)
			{
				Card temp=(Card)aim_map.getobj();
				map_middle.add(aim_map);
	    		aim_map.setY(200);
	    		aim_map.setX(300);
				for(int i=0;i<current_role.getCards().size();i++)
				{
					if(current_role.getCards().get(i)==temp)
					{
					     current_role.getCards().remove(i);
					}
				}
			    redistributeSpace();
			    this.paint(this.getGraphics());
				 if(!this.robot_excute())
				 {
				     mod=0;
				     gm.distributeCards(current_role);
				     this.redistributeSpace();
				     repaint();
				  }
			}
		}
		else if(e.getSource()==b_cancel)
		{
			map_middle=new Vector<Mapping>();
			repaint();
			if(mod==4)
			{
				aim_role.beattack();
		    	if(!this.robot_excute())
			   {
			       mod=0;
			       gm.distributeCards(current_role);
			       this.redistributeSpace();
			       repaint();
			    }
			}
		}
		else if(e.getSource()==b_giveup)
		{
			if((num_to_giveup=current_role.getCards().size()-current_role.getlife())>0)
			{
				Graphics g=this.getGraphics();
				Font f=new Font("宋体",Font.BOLD,50);
				g.setFont(f);
				g.drawString("请弃掉"+num_to_giveup+"张牌", 180, 300);
				mod=2;
			}
			mod=2;
			current_role.setshoot_time(1);
		}
	}
}
