package com.chen.tools;

import java.util.Vector;

import com.chen.cards.Card;

public class CardGiveUPtool {
	public static int countnum(Vector<Mapping> temp)
	{
		int num=0;
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getY()==400)
			{
				num++;
			}
		}
		return num;
	}
	public static Vector<Mapping> pickout(Vector<Mapping> temp)
	{
		int num=0;
		int length=200;
		Vector<Mapping> map_giveup=new Vector<Mapping>();
		for(int i=0;i<temp.size();)
		{
			if(temp.get(i).getY()==400)
			{
				temp.get(i).setX(length);
				length+=100;
				temp.get(i).setY(200);
				map_giveup.add(temp.get(i));
			    temp.remove(i);
			}
			else
			{
				i++;
			}
		}
		return map_giveup;
	}
	public static void deleteFromCardArray(Vector<Card> array,Card temp)
	{
		for(int i=0;i<array.size();i++)
		{
			if(array.get(i)==temp)
			{
				array.remove(i);
				break;
			}
		}
	}
}