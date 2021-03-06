package com.chen.roles;
import java.util.Vector;

import com.chen.cards.*;
import com.chen.tools.Mapping;

public class Role {
	protected int maxLife;
	protected boolean alive=true;
	protected int life=0;
	protected Equipment[] equip;
	protected Vector<Card> cards;
	protected Vector<Mapping> cards_giveup;
	private boolean judge_over=false;
	protected String name;
	protected int shoot_time;
	private int Id;
	protected int position;
	protected int attackdistance;
	protected int identity;//1 for zhugong 2 for zhongchen 3 for fanzie 4 for neijian
	public Role(int Id,int identity)
	{
		this.Id=Id;
		this.position=Id;
		this.identity=identity;
	}
	public int getPosition()
	{
		return this.position;
	}
	public boolean ifAlive()
	{
		return alive;
	}
	public Vector<Mapping> die()
	{
		cards_giveup=new Vector<Mapping>();
		for(int i=0,length=150;i<cards.size();i++,length+=100)
		{
			Mapping m=new Mapping(length,250,150,100);
			m.setObj(this.getCards().get(0));
			cards_giveup.add(m);
			this.getCards().remove(0);
		}
		this.Id=-1;
		alive=false;
		return cards_giveup;
	}
	public int getIdentity()
	{
		return identity;
	}
	public int getattackdistance()
	{
		return attackdistance;
	}
	public void SetId(int id)
	{
		Id=id;
	}
	public int getId()
	{
		return Id;
	}
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
		life+=1;
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

