package com.chen.roles;
import java.util.Vector;

import com.chen.cards.*;
import com.chen.tools.Mapping;

public class Role {
	protected int maxLife;
	protected int life=0;
	protected Equipment[] equip;
	protected Vector<Card> cards;
	protected Vector<Mapping> cards_giveup;
	private boolean judge_over=false;
	protected String name;
	protected int shoot_time;
	public int getshoot_time()
	{
		return shoot_time;
	}
	public void setshoot_time(int time)
	{
		this.shoot_time=time;
	}
	public int getmaxlife()
	{
		return maxLife;
	}
	public int getlife()
	{//get the life of the role
		return life;
	}
	public String getname()
	{
		return name;
	}
	public void lifedrop(int num){
		//the life was drop,you know,the mount of the dropping life
		//is uncertain
		life-=num;
	}
	public void liferise(){
		//you can only add one life one time
		life+=life;
	}
	public Equipment[] getEquip(){
		return equip;
	}
	public void addEquipment()
	{
		//there are different type of equipments,add it according to its type
	}
	public void removeEquipMent(){
		
	}
	public Vector<Card>  getCards(){
		return cards;
	}
	public void addCard(Card card)
	{
		//add cards 
		cards.add(card);
	}
	public void removeCard(Card card)
	{
	     for(int i=0;i<=cards.size();i++)
		{
			if(cards.get(i)==card)
			{
				cards.remove(i);
				break;
			}
		}
	}
	public void usecard()
	{
		
	}
	public Card beattack()
	{
		lifedrop(1);
		return null;
	}
	public boolean judge_over()
	{
		return judge_over;
	}
	public void judge_over_off()
	{
		judge_over=false;
	}
	public void judge_over_on()
	{
		judge_over=true;
	}
}

