package com.chen.roles;
import java.util.Vector;

import com.chen.cards.*;

public class Messi extends Role{
  public Messi(){
	  cards=new Vector<Card>();
	  Card card=new Card(2,"club","shoot");
	  cards.add(card);
	  card=new Card(3,"club","shoot");
	  cards.add(card);
	  card=new Card(2,"diamond","drive");
	  cards.add(card);
	  name="messi";
	  life=2;
	  maxLife=3;
  }
}
