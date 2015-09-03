package com.chen.robot;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.roles.Role;
import com.chen.tools.Mapping;

public class Robot extends Role{
	private int[] enemyIdentities=new int[5];
	public Robot(int Id,int identity)
	{
		    super(Id,identity);
		    this.identity=identity;
	    	enemyIdentities[0]=1;
	}
	public Card beattack()
	{
		for(int i=0;i<cards.size();i++)
		{
			if(cards.get(i).gettype().equals("drive"))
			{
				  Card temp=cards.get(i);
				  cards.remove(i);
			      return temp;
			}
		}
		lifedrop(1);
		return null;
	}
	public Card excute(Vector<Mapping> map_role)
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Card temp=null;
		Mapping temp_map=null;
		for(int i=0;i<this.cards.size();i++)
		{
			if(cards.get(i).gettype().equals("medi")&&this.getlife()<this.getmaxlife())
			{
				temp=cards.get(i);
				cards.remove(i);
				this.liferise();
				break;
			}
			else if(cards.get(i).gettype().equals("shoot")&&this.getshoot_time()!=0)
			{
				temp_map=this.getAim(map_role);
				if(temp_map!=null)
				{
				   temp=cards.get(i);
				   cards.remove(i);
				   this.shoot_time--;
				   temp.setaim(temp_map);
				   break;
				}
			}
		}
		if(temp!=null)
		{
			
		}
		else
		{
			this.setshoot_time(1);
		}
		return temp;
	}
	public Card saveyourself()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Card temp=null;
		Role role_temp;
		for(int i=0;i<this.cards.size();i++)
		{
			if(cards.get(i).gettype().equals("medi")&&this.getlife()<this.getmaxlife())
			{
				temp=cards.get(i);
				cards.remove(i);
				this.liferise();
				break;
			}
		}
		return temp;
	}
	public Vector<Mapping> end(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num=this.getCards().size()-this.getlife();
		cards_giveup=new Vector<Mapping>();
		for(int i=0,length=150;i<num;i++,length+=100)
		{
			Mapping m=new Mapping(length,250,150,100);
			m.setObj(this.getCards().get(0));
			cards_giveup.add(m);
			this.getCards().remove(0);
		}
		return cards_giveup;
	}
	public Card ifSave()
	{
		return null;
	}
	private Mapping getAim(Vector<Mapping> map_role)
	{
		Card temp=null;
		Role role_temp = null;
		for(int j=0;j<map_role.size();j++)
		{
			role_temp=(Role)map_role.get(j).getobj();
			if(role_temp.getId()!=this.getId())
			{
				 int distance=this.getId()-role_temp.getId();
				 if(distance<0)
					 distance=-distance;
	        	  if(distance>=3)
	        		  distance=5-distance;
	        	  
	        	  if(distance<=this.getattackdistance())
	        	  {
	        		   if(this.getIdentity()==3)
	        		   {
	        			 if(FanzieGetAim(j))
	        			 {
	        				 return map_role.get(j);
	        			 }
	        		   }
	        		   else if(this.getIdentity()==2)
	        		   {
	        			   if(ZhongchenGetAim(j))
	        			   {
	        				   return map_role.get(j);
	        			   }
	        		   }
	        		   else if(this.getIdentity()==4)
	        		   {
	        			   if(NeijianGetAim(j))
	        			   {
	        				   return map_role.get(j);
	        			   }
	        		   }
	        	  }
		   }
		}
		return null;
	}
	private boolean FanzieGetAim(int j)
	{
		  if(enemyIdentities[j]==1||enemyIdentities[j]==2||enemyIdentities[3]==4)
    		  return true;
		  else
			  return false;
	}
	private boolean ZhongchenGetAim(int j)
	{
		  if(enemyIdentities[j]==3)
    		  return true;
		  else
			  return false;
	}
	private boolean NeijianGetAim(int j)
	{
		 if(enemyIdentities[j]==3||enemyIdentities[j]==2)
   		      return true;
		  else
			  return false;
	}
	public void judgeEnemies(Role current_role,Role aim)
	{
		if(aim.getIdentity()==1&&current_role.getId()!=this.getId())
		{
			enemyIdentities[current_role.getId()]=3;
		}
		else if(aim.getIdentity()==3&&current_role.getId()!=this.getId())
		{
			enemyIdentities[current_role.getId()]=2;
		}
	}
}
