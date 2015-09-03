package com.chen.roles;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.robot.Robot;

public class Ronaldo extends Robot{
	public Ronaldo(int Id,int identity){
		super(Id,identity);
		cards=new Vector<Card>();
		  name="ronaldo";
		  life=2;
		  maxLife=3;
		  shoot_time=1;
		  attackdistance=1;
	  }
}
