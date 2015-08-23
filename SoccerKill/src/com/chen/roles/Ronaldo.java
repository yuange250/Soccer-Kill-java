package com.chen.roles;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.robot.Robot;

public class Ronaldo extends Robot{
	public Ronaldo(){
		cards=new Vector<Card>();
		  Card card=new Card(2,"club","shoot");
		  cards.add(card);
		  card=new Card(2,"diamond","drive");
		  cards.add(card);
		  card=new Card(2,"diamond","drive");
		  cards.add(card);
		  card=new Card(2,"diamond","drive");
		  cards.add(card);
		  name="ronaldo";
		  life=2;
		  maxLife=3;
		  shoot_time=1;
	  }
}
