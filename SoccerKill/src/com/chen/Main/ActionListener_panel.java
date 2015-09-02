package com.chen.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.chen.UI.Main_Panel;
import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.tools.Mapping;

public class ActionListener_panel implements ActionListener{
	private GameMainThread mp;
	public ActionListener_panel(GameMainThread mp)
	{
		this.mp=mp;
	}
	public void actionPerformed(ActionEvent e) {
		// shoot_aim
		if(e.getSource()==mp.mp.b_sure)
		{
			if(mp.mod==3&&mp.action_mod==1)
			{//shoot aim
				mp.action_mod=0;
				mp.point_line=0;
				mp.tm.cardDrop();
				Robot rb_temp=(Robot)mp.shoot_aim;
				Card temp;
				if((temp=rb_temp.beattack())!=null)
				{
				       Mapping m=new Mapping(150+mp.tm.getMap_middle().size()*100,250,150,100);
				       m.setObj(temp);
				       mp.tm.getMap_middle().add(m);
				       System.out.println(mp.tm.getMap_middle().size());
				}
				if(rb_temp.getlife()==0)
				{
					mp.action_mod=4;
				}
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.tm.getCurrentUser().setshoot_time(0);
			}
			else if(mp.mod==3&&mp.action_mod==3)
			{//use a medi
				mp.action_mod=0;
				mp.tm.cardDrop();
				mp.tm.getCurrentUser().liferise();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
			}
			else if(mp.mod==3&&mp.action_mod==4)
			{//save others
				mp.action_mod=0;
				mp.tm.cardDrop();
				mp.shoot_aim.liferise();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
			}
			if(mp.mod==4)
			{//drop card
				mp.tm.cardDrop();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_end.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.tm.getCurrentUser().setshoot_time(1);
				mp.tm.cards_drop_num_clear();
				mp.mod=5;
				mp.current_robot=(Robot)mp.tm.getRobot(mp.tm.getCurrentUser());
				mp.tm.addCardstouser(mp.current_robot);
			    mp.action_mod=0;
			}
			else if(mp.mod==6&&mp.ifsave_mod==2)
			{//being nearly killed and use a medi
				mp.tm.cardDrop();
				mp.ifsave_mod=0;
				mp.tm.getCurrentUser().liferise();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_end.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.mod=5;
			}
			else if(mp.mod==6&&mp.ifsave_mod==0)
			{//use a drive
				mp.tm.cardDrop();
				mp.point_line=0;
				mp.drive_mod=0;
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_end.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.mod=5;
			}
		}
		else if(e.getSource()==mp.mp.b_end)
		{
			 for(int i=0;i<mp.tm.getMap_down().size();i++)
             {
            	 if(mp.tm.getMap_down().get(i).getY()==500)
            		 mp.tm.setcards_drop_num(mp.tm.getcards_drop_num()+1);
             }
			mp.mod=4;
		}
		else if(e.getSource()==mp.mp.b_cancel)
		{
			if(mp.mod==3||mp.mod==4)
			{
				if(mp.action_mod==4)
				{
					Robot temp=(Robot)mp.shoot_aim;
					Card card_temp=(Card)temp.saveyourself();
					if(card_temp!=null)
					{
						   Mapping m=new Mapping(150+mp.tm.getMap_middle().size()*100,250,150,100);
					       m.setObj(card_temp);
					       mp.tm.getMap_middle().add(m);
					       System.out.println(mp.tm.getMap_middle().size());
					       mp.mp.b_sure.setEnabled(false);
							mp.mp.b_cancel.setEnabled(false);
							mp.tm.redistributeSpace();
							mp.action_mod=0;
					}
					else
					{
						 mp.mod=7;
						 mp.if_win=true;
					}
				}
				else 
				{
					 System.out.println(mp.tm.getMap_middle().size());
				     mp.mp.b_sure.setEnabled(false);
				     mp.mp.b_cancel.setEnabled(false);
				     mp.tm.redistributeSpace();
					 mp.action_mod=0;
				}
				mp.action_mod=0;
			}
			else if(mp.mod==6&&mp.ifsave_mod==0)
			{
				mp.tm.redistributeSpace();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.tm.getCurrentUser().beattack();
				if(mp.tm.getCurrentUser().getlife()<=0)
				{
					if(mp.current_robot.ifSave()!=null)
					{
						
					}
					else
					{
						mp.ifsave_mod=1;
					}
					mp.mp.b_cancel.setEnabled(true);
				}
				else
				mp.mod=5;
			}
			else if(mp.mod==6&&mp.ifsave_mod!=0)
			{
				mp.tm.redistributeSpace();
				mp.mod=7;
				mp.if_win=false;
			}
			mp.point_line=0;
		}
		mp.mp.repaint();
	}
}
