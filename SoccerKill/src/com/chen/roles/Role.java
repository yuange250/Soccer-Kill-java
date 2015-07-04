package com.chen.roles;
import java.util.Vector;

import com.chen.cards.*;

public class Role {
	protected int maxLife;
	protected int life=0;
	protected Equipment[] equip;
	protected Vector<Card> cards;
	protected String name;
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
	public void addCard()
	{
		//add cards 
	}
	public void removeCard(){
		
	}
	public void usecard()
	{
		
	}
	public void beattack()
	{
		lifedrop(1);
	}
}

