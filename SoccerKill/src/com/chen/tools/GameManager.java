package com.chen.tools;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Messi;
import com.chen.roles.Role;
import com.chen.roles.Ronaldo;

public class GameManager {
	//Overview: response for the schedule of the game and distributing cards to roles
	Role []roles=new Role[5];
	Card []allcards=new Card[100];
	Card []dropcards=new Card[100];
	private int dropcardsnum=1;
	private int cardsnum=1;
	private int pointer=0;
	private int cardpointer=1;
	private int totalrolenum=5;
	public GameManager(){
		Ronaldo ronaldo1=new Ronaldo(1,3);
		Ronaldo ronaldo2=new Ronaldo(2,2);
		Ronaldo ronaldo3=new Ronaldo(3,3);
		Ronaldo ronaldo4=new Ronaldo(4,4);
		Messi messi=new Messi(0,1);
		roles[0]=messi;
		roles[1]=ronaldo1;
		roles[2]=ronaldo2;
		roles[3]=ronaldo3;
		roles[4]=ronaldo4;
		for(int i=2;i<=9;i++)
		{
	    Card card=new Card(i,"diamond","drive");
		allcards[cardsnum++]=card;
		}
		Card card=new Card(2,"hearts","drive");
		allcards[cardsnum++]=card;
		for(int i=2;i<=3;i++)
		{
	    card=new Card(i,"diamond","medi");
		allcards[cardsnum++]=card;
		}
		for(int i=3;i<=9;i++)
		{
	    card=new Card(i,"hearts","medi");
		allcards[cardsnum++]=card;
		}
		for(int i=2;i<=9;i++)
		{
	    card=new Card(i,"club","shoot");
		allcards[cardsnum++]=card;
		}
		for(int i=6;i<=9;i++)
		{
	    card=new Card(i,"diamond","shoot");
		allcards[cardsnum++]=card;
		}
		for(int i=7;i<=9;i++)
		{
	    card=new Card(i,"spade","shoot");
		allcards[cardsnum++]=card;
		}
		washCards();
	}
	public Role getCurrentRole()
	{
		return roles[0];
	}
	public Role nextRole(Role role){
		if((role.getId()+1)!=totalrolenum)
		{
		    for(int i=0;i<5;i++)
		    {
		    	if(roles[i].getId()==role.getId()+1)
		    	{
		    		return roles[i];
		    	}
		    }
		}
		return roles[0];
	}
	public void washCards()
	{
		for(int i=0;i<30;i++)
		{
			int key=(int)(Math.random()*cardsnum);
			System.out.println(key);
			if(key<=cardsnum/2)
			{
				Card  temp;
				for(int j=1;j<key;j++)
				{
				   temp=allcards[j];
				   allcards[j]=allcards[key*2-j];
				   allcards[key*2-j]=temp;
				}
			}
			else
			{
				Card  temp;
				for(int j=cardsnum-1;j>key;j--)
				{
				   temp=allcards[j];
				   allcards[j]=allcards[key*2-j];
				   allcards[key*2-j]=temp;
				}
			}
		}
	}
	public synchronized void distributeCards(Role temp){
		//这个发完牌之后要记得还要洗牌.
		temp.addCard(allcards[cardpointer++]);
		if(cardpointer==cardsnum)
		{
			allcards=dropcards;
			cardsnum=dropcardsnum;
			washCards();
			cardpointer=1;
			dropcardsnum=0;
		}
		temp.addCard(allcards[cardpointer++]);
		if(cardpointer==cardsnum)
		{
			allcards=dropcards;
			cardsnum=dropcardsnum;
			washCards();
			cardpointer=1;
			dropcardsnum=0;
		}
	}
	public  synchronized void collectoldcards(Card card)
	{
		dropcards[dropcardsnum++]=card;
	}
	protected void redistributeId()
	{
	     totalrolenum--;
	     for(int i=0,j=0;i<5;i++)
	     {
	    	 if(roles[i].ifAlive())
	    	 {
	    		 roles[i].SetId(j);
	    		 j++;
	    	 }
	     }
	}
	protected int getTotalrolenum()
	{
		return this.totalrolenum;
	}
}
