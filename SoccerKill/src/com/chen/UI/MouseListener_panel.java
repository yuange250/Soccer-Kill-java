package com.chen.UI;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.chen.cards.Card;
import com.chen.roles.Role;
import com.chen.tools.Mapping;

public class MouseListener_panel implements MouseListener{
    private Main_Panel mp;
    public MouseListener_panel(Main_Panel mp)
    {
    	this.mp=mp;
    }
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		Mapping temp=mp.tm.findTheObject(x,y);
	    if(temp!=null)
	    {
		  if(mp.mod==3)
		  {
	          if(temp.getobj() instanceof Card&&mp.action_mod==0)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype()+" "+(card_temp.gettype().equals("shoot")&&mp.tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==550));
	          		if(card_temp.gettype().equals("shoot")&&mp.tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==550)
	          		{//selected a shoot
	          		    	 temp.setY(500);
	          		    	 mp.b_cancel.setEnabled(true);
	          		    	 mp.action_mod=1;
	          		}
	          		else if(card_temp.gettype().equals("medi")&&temp.getY()==550)
	          		{//select a medi
	          		    	 temp.setY(500);
	          		    	 if(mp.tm.getCurrentUser().getmaxlife()>mp.tm.getCurrentUser().getlife())
	          		    	 {
	          		    	    mp.b_sure.setEnabled(true);
	          		    	 }
	          		    	 mp.action_mod=3;
	          		    	 mp.b_cancel.setEnabled(true);
	          		    	
	          		}
	          	}
	          else if(mp.action_mod==1&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("shoot")&&mp.tm.getCurrentUser().getshoot_time()!=0&&temp.getY()==500)
	          		{//unselect a shoot
	          		    	 temp.setY(550);
	          		    	 mp.b_cancel.setEnabled(false);
	          		         mp.action_mod=0;
	          		}
	          }
	          else if(mp.action_mod==3&&temp.getobj() instanceof Card)
	          {//unslelect a medi
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==500)
	          		{
	          		    	 temp.setY(550);
	          		    	 mp.b_cancel.setEnabled(false);
	          		         mp.action_mod=0;
	          		}
	          }
	          else if(mp.action_mod==4&&temp.getobj() instanceof Card)
	          {//if save someone
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==500)
	          		{
	          		    	 temp.setY(550);
	          		}
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==550)
	          		{
	          		    	 temp.setY(500);
	          		    	 mp.b_sure.setEnabled(true);
	          		}
	          }
	          else if(mp.action_mod==1&&temp.getobj() instanceof Role)
	          {//select a shoot and select a role
	        	  mp.shoot_aim=(Role)temp.getobj();
	        	  int distance=mp.shoot_aim.getId()-mp.tm.getCurrentUser().getId();
	        	  if(distance>=3)
	        		  distance=5-distance;
	        	  if(distance<=mp.tm.getCurrentUser().getattackdistance()&&mp.shoot_aim!=mp.tm.getCurrentUser())
	        	  {
	        		  mp.b_sure.setEnabled(true);
	        		  mp.point_line=1;
	        	  }
	        	  else
	        		mp.shoot_aim=null;
	          }
        }
		if(mp.mod==4)
		{
			if(temp.getobj() instanceof Card)
	        {
	          	 Card card_temp=(Card)temp.getobj();
	             System.out.println(card_temp.gettype());
	          	 if(temp.getY()==550&&mp.tm.getcards_drop_num()!=(mp.tm.getCurrentUser().getCards().size()-mp.tm.getCurrentUser().getlife()))
	          	 {
	          		  temp.setY(500);
	          		  mp.b_cancel.setEnabled(true);
	          		  mp.tm.setcards_drop_num(mp.tm.getcards_drop_num()+1);
	          	 }
	          	 else if(temp.getY()==500)
          		 {
          		      temp.setY(550);
          		      mp.b_cancel.setEnabled(false);
          		      mp.tm.setcards_drop_num(mp.tm.getcards_drop_num()-1);
          		 }
	        }
		}
		if(mp.mod==6&&mp.ifsave_mod!=1)
		{
			 if(mp.drive_mod==0&&temp.getobj() instanceof Card)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("drive")&&temp.getY()==550)
	          		{
	          		    	 temp.setY(500);
	          		    	 mp.b_sure.setEnabled(true);
	          		    	 mp.b_cancel.setEnabled(true);
	          		    	 mp.drive_mod=1;
	          		}
	          		
	          	}
	          else if(temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          	    System.out.println(card_temp.gettype().equals("drive")&&temp.getY()==500);
	          		if(card_temp.gettype().equals("drive")&&temp.getY()==500)
	          		{
	          		    	 temp.setY(550);
	          		    	 mp.b_sure.setEnabled(false);
	          		    	 mp.drive_mod=0;
	          		}
	          }
		}
		else if(mp.mod==6&&mp.ifsave_mod!=0)
		{
			 if(mp.ifsave_mod==1&&temp.getobj() instanceof Card)
	          {//current role use a card
	          	    Card card_temp=(Card)temp.getobj();
	          		System.out.println(card_temp.gettype());
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==550)
	          		{
	          		    	 temp.setY(500);
	          		    	 mp.b_sure.setEnabled(true);
	          		    	 mp.b_cancel.setEnabled(true);
	          		    	 mp.ifsave_mod=2; 
	          		}
	          		
	          	}
	          else if(mp.drive_mod==2&&temp.getobj() instanceof Card)
	          {
	        	   Card card_temp=(Card)temp.getobj();
	          	    System.out.println(card_temp.gettype());
	          	    System.out.println(card_temp.gettype().equals("medi")&&temp.getY()==500);
	          		if(card_temp.gettype().equals("medi")&&temp.getY()==500)
	          		{
	          		    	 temp.setY(550);
	          		    	 mp.b_sure.setEnabled(false);
	          		    	 mp.ifsave_mod=1; 
	          		}
	          }
		}
		mp.repaint();
	 }
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

}
