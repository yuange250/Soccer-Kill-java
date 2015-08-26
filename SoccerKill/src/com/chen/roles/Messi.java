package com.chen.roles;
import java.util.Vector;

import com.chen.cards.*;

public class Messi extends Role{
  public Messi(){
	  cards=new Vector<Card>();
	  name="messi";
	  life=2;
	  maxLife=3;
	  shoot_time=1;
  }
}
