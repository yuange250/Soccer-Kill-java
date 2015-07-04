package com.chen.roles;

import java.util.Vector;

import com.chen.cards.Card;

public class Ronaldo extends Role{
	public Ronaldo(){
		cards=new Vector<Card>();
		  Card card=new Card(2,"club","shoot");
		  cards.add(card);
		  card=new Card(3,"club","shoot");
		  cards.add(card);
		  card=new Card(2,"diamond","drive");
		  name="ronaldo";
		  life=2;
		  maxLife=3;
	  }
}
