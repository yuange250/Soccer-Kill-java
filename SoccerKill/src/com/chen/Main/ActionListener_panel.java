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
				mp.mp.jt.append(mp.tm.getCurrentUser().getname()+"向"+mp.shoot_aim.getname()+"出了一张射\n");
				mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
				if((temp=rb_temp.beattack())!=null)
				{
				       Mapping m=new Mapping(150+mp.tm.getMap_middle().size()*100,250,150,100);
				       m.setObj(temp);
				       mp.tm.getMap_middle().add(m);
				       mp.mp.jt.append(mp.shoot_aim.getname()+"出了一张挡\n");
				       mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
				}
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.tm.getCurrentUser().setshoot_time(0);
		
				if(mp.shoot_aim.getlife()<=0)
				{
					mp.save_aim=mp.shoot_aim;
					mp.mod_last=mp.mod;
					mp.role_last=mp.tm.getCurrentUser();
					mp.mod=8;
				}
			}
			else if(mp.mod==3&&mp.action_mod==3)
			{//use a medi
				mp.mp.jt.append(mp.tm.getCurrentUser().getname()+"用了一张云南白药\n");
				mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
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
				mp.mod=5;
				mp.current_robot=(Robot)mp.tm.getRobot(mp.tm.getCurrentUser());
				mp.tm.addCardstouser(mp.current_robot);
			    mp.action_mod=0;
			}
			else if(mp.mod==8&&mp.ifsave_mod==2)
			{//being nearly killed and use a medi
				mp.mp.jt.append(mp.tm.getCurrentUser().getname()+"给"+mp.save_aim.getname()+"用了一张云南白药\n");
				mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
				mp.tm.cardDrop();
				mp.ifsave_mod=0;
				mp.save_aim.liferise();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_end.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.current_robot=(Robot)mp.role_last;
				mp.mod=mp.mod_last;
			}
			else if(mp.mod==6)
			{//use a drive
				mp.mp.jt.append(mp.tm.getCurrentUser().getname()+"出了一张挡\n");
				mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
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
			 mp.mp.jt.append(mp.tm.getCurrentUser().getname()+"弃牌\n");
			 mp.mp.jt.setCaretPosition(mp.mp.jt.getText().length());
			 mp.mod=4;
		}
		else if(e.getSource()==mp.mp.b_cancel)
		{
			if(mp.mod==3)
			{
//				if(mp.action_mod==4)
//				{
//					Robot temp=(Robot)mp.shoot_aim;
//					Card card_temp=(Card)temp.saveyourself();
//					if(card_temp!=null)
//					{
//						   Mapping m=new Mapping(150+mp.tm.getMap_middle().size()*100,250,150,100);
//					       m.setObj(card_temp);
//					       mp.tm.getMap_middle().add(m);
//					       mp.mp.b_sure.setEnabled(false);
//							mp.mp.b_cancel.setEnabled(false);
//							mp.tm.redistributeSpace();
//							mp.action_mod=0;
//					}
//					else
//					{
//						 mp.mod=7;
//						 mp.if_win=true;
//					}
//				}
//				else 
//				{
				     mp.mp.b_sure.setEnabled(false);
				     mp.mp.b_cancel.setEnabled(false);
				     mp.tm.redistributeSpace();
					 mp.action_mod=0;
//				}
				mp.action_mod=0;
			}
			else if(mp.mod==4)
			{
				mp.mp.b_sure.setEnabled(false);
			     mp.mp.b_cancel.setEnabled(false);
			     mp.tm.redistributeSpace();
			}
			else if(mp.mod==6&&mp.ifsave_mod==0)
			{
				mp.tm.redistributeSpace();
				mp.mp.b_sure.setEnabled(false);
				mp.mp.b_cancel.setEnabled(false);
				mp.tm.getCurrentUser().beattack();
				if(mp.tm.getCurrentUser().getlife()<=0)
				{
//				      Robot temp=mp.current_robot;
//				      Card card_temp;
//				      while(mp.current_robot!=mp.tm.getCurrentUser())
//				      {
//					      if((card_temp=mp.current_robot.ifSave(mp.shoot_aim))!=null)
//					     {
//					    	  Mapping m=new Mapping(150+mp.tm.getMap_middle().size()*100,250,150,100);
//						       m.setObj(card_temp);
//						       mp.tm.getMap_middle().add(m);
//						       mp.shoot_aim=temp;
//						       mod=5;
//						       
//					      }
//				      }
//				    mp.ifsave_mod=1;
//					mp.mp.b_cancel.setEnabled(true);
					mp.save_aim=mp.shoot_aim;
					mp.mod_last=5;
					mp.role_last=mp.current_robot;
					mp.mod=8;
				}
				else
				mp.mod=5;
			}
			else if(mp.mod==8)
			{
				mp.tm.redistributeSpace();
				mp.current_robot=(Robot)mp.tm.getRobot(mp.tm.getCurrentUser());
			    mp.ifsave_mod=0;
			    System.out.println(mp.current_robot.getname()+" "+mp.role_last.getname());
			    if(mp.current_robot==mp.role_last)
			    {
			      mp.someOneDie();
			    }
			}
			mp.point_line=0;
		}
		mp.mp.repaint();
	}
}
