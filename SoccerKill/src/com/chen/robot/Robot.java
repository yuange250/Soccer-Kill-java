package com.chen.robot;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.roles.Role;
import com.chen.tools.Mapping;

public class Robot extends Role{
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
		Role role_temp;
		for(int i=0;i<this.cards.size();i++)
		{
			if(cards.get(i).gettype().equals("shoot")&&this.getshoot_time()!=0)
			{
				temp=cards.get(i);
				cards.remove(i);
				this.shoot_time--;
				break;
			}
		}
		if(temp!=null)
		{
			for(int i=0;i<map_role.size();i++)
			{
				role_temp=(Role)map_role.get(i).getobj();
				if(!role_temp.getname().equals(this.name))
				{
					temp.setaim(map_role.get(i));
				}
			}
		}
		else
		{
			this.setshoot_time(1);
		}
		return temp;
	}
	public Vector<Mapping> giveUP(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num=this.getCards().size()-this.getlife();
		cards_giveup=new Vector<Mapping>();
		for(int i=0,length=200;i<num;i++,length+=100)
		{
			Mapping m=new Mapping(length,200,150,100);
			m.setObj(this.getCards().get(0));
			cards_giveup.add(m);
			this.getCards().remove(0);
		}
		return cards_giveup;
	}
}
