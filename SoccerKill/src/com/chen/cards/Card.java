package com.chen.cards;

public class Card {
	protected int point;
	protected String color;//1 for hearts,2 for diamond,3 for spade,4 for club
	protected String type;
	public Card(int point,String color,String type)
	{
		this.point=point;
		this.color=color;
		this.type=type;
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
