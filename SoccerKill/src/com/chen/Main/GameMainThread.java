package com.chen.Main;

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

import com.chen.UI.Main_Panel;
import com.chen.UI.Main_frame;
import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Role;
import com.chen.tools.Mapping;
import com.chen.tools.TotalMapping;

public class GameMainThread  implements Runnable{
	  //Jbutton part 
	  private Main_frame m=new Main_frame();
      private MouseListener_panel mlp;
      private ActionListener_panel alp;
      Main_Panel mp=m.getMainPanel();
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
	  
	  Role save_aim;
	  Role role_last;
	  int mod_last;
	  
      public GameMainThread()
      {
          this.tm.InitSpace();
          mlp=new MouseListener_panel(this);
          alp=new ActionListener_panel(this);
    	  mp.addMouseListener(mlp);
    	  mp.setActionListenerforButton(alp);
    	  mp.setGameMainThread(this);
       	  Thread t=new Thread(tm);//the auto clean is start,map_middle is autoclean
      	  t.start();
      }
	public void run()
	{//thread functio
	    while(true)
	    {
	    	if(mod==0)
			{
				mod=1;

			}
	    	else if(mod==1)
			{
				mod=2;
				tm.findTheAbleCard(mod, 0);
			}
			else if(mod==2)
			{
				tm.addCardstouser(tm.getCurrentUser());
				tm.findTheAbleCard(mod, 0);
				mod=3;
			}	
			else if(mod==3)
			{
				tm.findTheAbleCard(mod, 0);
				if(action_mod==4)
				{
					mp.b_cancel.setEnabled(true);
					mp.b_end.setEnabled(false);
				}
				else
				{
					mp.b_end.setEnabled(true);
				}
				if(action_mod==1)
				{
					tm.findTheEableRole();
				}
			}
			else if(mod==4)
			{
				tm.findTheAbleCard(mod, 0);
				if(tm.getCurrentUser().getCards().size()<=tm.getCurrentUser().getlife())
				{//when drop cards,if the role needn't drop any card,just skip this mod
					mp.b_sure.setEnabled(false);
					mp.b_end.setEnabled(false);
					mp.b_cancel.setEnabled(false);
					tm.getCurrentUser().setshoot_time(1);
					mod=5;
					current_robot=(Robot)tm.getRobot(tm.getCurrentUser());
					tm.addCardstouser(current_robot);
				}
				else
				{
				if(tm.getcards_drop_num()==(tm.getCurrentUser().getCards().size()-tm.getCurrentUser().getlife()))
				{//when the num of the selected cards is equal to the num the role should drop, b_sure is on
					mp.b_sure.setEnabled(true);
				}
				else
				{
					mp.b_sure.setEnabled(false);
				}
				}
				
			}
			else if(mod==5)
			{
				tm.findTheAbleCard(mod, 0);
				Card temp;
				if((temp=current_robot.excute(tm.getMap_role(),tm.getTotalRolenum()))!=null)
				{
					if(temp.gettype()=="shoot")
					{//the robot shoot someone
						shoot_aim=(Role)temp.getaim().getobj();
						point_line=2;
						mp.repaint();
						mp.jt.append(current_robot.getname()+"向"+shoot_aim.getname()+"出了一张射\n");
						mp.jt.setCaretPosition(mp.jt.getText().length());
						if(shoot_aim==tm.getCurrentUser())
						{
						    Mapping m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
						    m.setObj(temp);
						    tm.getMap_middle().add(m);
						    mod=6;
						    mp.b_cancel.setEnabled(true);
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
								// TODO �Զ����ɵ� catch ��
								e.printStackTrace();
							}
							if((card_temp=rb_temp.beattack())!=null)
							{
								   mp.jt.append(shoot_aim.getname()+"出了一张挡\n");
								   mp.jt.setCaretPosition(mp.jt.getText().length());
							       m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
							       m.setObj(card_temp);
							       tm.getMap_middle().add(m);
							}
							point_line=0;
						}
					    //machine learning
						for(int j=1;j<tm.getMap_role().size();j++)
						{
							Robot r=(Robot)tm.getMap_role().get(j).getobj();
							r.judgeEnemies(current_robot, shoot_aim);
						}
						if(shoot_aim.getlife()<=0)
						{
							save_aim=shoot_aim;
							role_last=current_robot;
							mod_last=mod;
							mod=8;
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
					mp.jt.append(current_robot.getname()+"弃牌\n");
					mp.jt.setCaretPosition(mp.jt.getText().length());
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
			else if(mod==6)
			{
				tm.findTheAbleCard(mod, 0);
			}
			else if(mod==7)
			{
				mp.repaint();
				break;
			}
			else if(mod==8)
			{
				tm.findTheAbleCard(mod, ifsave_mod);
				if(ifsave_mod==0)
				{
			 	  Card temp;
				  if(this.current_robot!=null)
				 {		 
					if((temp=this.current_robot.ifSave(save_aim))!=null)
					{
						mp.jt.append(current_robot.getname()+"给"+save_aim.getname()+"用了云南白药\n");
						mp.jt.setCaretPosition(mp.jt.getText().length());
						  Mapping m=new Mapping(150+tm.getMap_middle().size()*100,250,150,100);
					      m.setObj(temp);
					      tm.getMap_middle().add(m);
					      save_aim.liferise();
					      mod=mod_last;
					      if(role_last!=tm.getCurrentUser())
					      current_robot=(Robot)role_last;
					      else 
					      current_robot=null;
					}
					else
					{
						Role role_temp=tm.getRobot(current_robot);
						if(role_temp==role_last)
						{//no one save the needed-saving one
							mod=mod_last;
							if(role_last!=tm.getCurrentUser())
							current_robot=(Robot)role_last;
							else
							current_robot=null;
							someOneDie();
						}
						if(role_temp==tm.getCurrentUser())
						{
							current_robot=null;
						} 
						else
						{
							current_robot=(Robot)role_temp;
						}
					  }
				   }
				  else
				  {
						ifsave_mod=1;
						mp.b_cancel.setEnabled(true);
				   }
				  }
				}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mp.repaint();
	    }
	}
	public TotalMapping getTotalMapping()
	{
		return tm;
	}
	public Robot getCurrentRobot()
	{
		return current_robot;
	}
	public int getMod()
	{
		return mod;
	}
	public int getActionMod()
	{
		return action_mod;
	}
	public int getIfSaveMod()
	{
		return ifsave_mod;
	}
	public boolean IfWin()
	{
		return if_win;
	}
	public int getPointLine()
	{
		return point_line;
	}
	public Role getShootAim()
	{
		return shoot_aim;
	}
	public void someOneDie()
	{
		Vector<Mapping> m=save_aim.die();
		tm.redistributeId();
		 mp.jt.append(save_aim.getname()+"挂了\n");
		 mp.jt.setCaretPosition(mp.jt.getText().length());
		 boolean if_over=true;
	    for(int i=0;i<m.size();i++)
	    {
	    	tm.getMap_middle().add(m.get(i));
	    }
	    if(save_aim==tm.getCurrentUser())
	    {
	    	if_win=false;
	    	mod=7;
	    	return;
	    }
	    for(int i=0;i<tm.getMap_role().size();i++)
	    {
	    	Role role_temp=(Role)tm.getMap_role().get(i).getobj();
	    	if(role_temp.getIdentity()==3||role_temp.getIdentity()==4)
	    	{
	    		if_over=if_over&&(!role_temp.ifAlive());
	    	}
	    }
	    if(if_over)
	    {
	    	mod=7;
	    	if_win=true;
	    	return;
	    }
	}
}
