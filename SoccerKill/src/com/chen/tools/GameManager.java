package com.chen.tools;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.roles.Messi;
import com.chen.roles.Role;
import com.chen.roles.Ronaldo;

public class GameManager {
	//Overview: response for the schedule of the game and distributing cards to roles
	Role []roles=new Role[2];
	Card []allcards=new Card[100];
	private int pointer=0;
	private int cardpointer=0;
	public GameManager(){
		Ronaldo ronaldo=new Ronaldo();
		Messi messi=new Messi();
		roles[0]=ronaldo;
		roles[1]=messi;
	    Card card=new Card(2,"club","shoot");
		allcards[0]=card;
		allcards[4]=card;
	    card=new Card(3,"club","shoot");
	    allcards[1]=card;
		allcards[5]=card;
		card=new Card(2,"diamond","drive");
		allcards[2]=card;
		allcards[6]=card;
		card=new Card(3,"diamond","drive");
		allcards[3]=card;
		allcards[7]=card;
	}
	public Role nextRole(){
		if(pointer<roles.length-1)
		{
			pointer++;
			return roles[pointer];
		}
		else
		{
			pointer=0;
			return roles[pointer];
		}
	}
	public void distributeCards(Role temp){
		//这个发完牌之后要记得还要洗牌.
		temp.addCard(allcards[cardpointer++]);
		temp.addCard(allcards[cardpointer++]);
		
	}
}
