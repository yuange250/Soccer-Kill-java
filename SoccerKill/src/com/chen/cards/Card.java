package com.chen.cards;

import com.chen.tools.Mapping;

public class Card {
	protected int point;
	protected String color;//1 for hearts,2 for diamond,3 for spade,4 for club
	protected String type;
	protected Mapping aim;
	protected boolean enable=true;
	public Card(int point,String color,String type)
	{
		this.point=point;
		this.color=color;
		this.type=type;
	}
	public void setEnable(boolean enable)
	{
	    this.enable=enable;
	}
	public boolean getEnable()
	{
		return enable;
	}
	public Mapping getaim()
	{
		return aim;
	}
	public void setaim(Mapping aim)
	{
		this.aim=aim;
	}
	public int getpoint()
	{
		return point;
	}
	public String getcolor(){
		return color;
	}
	public String gettype(){
		return type;
	}
}
