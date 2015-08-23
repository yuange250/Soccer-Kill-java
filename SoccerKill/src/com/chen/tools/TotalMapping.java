package com.chen.tools;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.roles.Messi;
import com.chen.roles.Role;

public class TotalMapping {
//all the pictures mapping is managed in this class
	  private Vector<Mapping> map_role=new Vector<Mapping>();
	  private Vector<Mapping> map_down=new Vector<Mapping>();
	  private Vector<Mapping> map_middle=new Vector<Mapping>();
	  private GameManager gm=new GameManager();
	  private Role current_role=gm.nextRole();
	  public Role getCurrentUser(){
		  return current_role;
	  }
	  public void addCardstoCurrentuser()
	  {
		  gm.distributeCards(current_role);
		  this.redistributeSpace();
		  System.out.println(current_role.getCards().size());
	  }
	  
	  public void Map_middleassignment(Vector<Mapping> m)
	  {
		  map_middle=m;
	  }
	 public Vector<Mapping> getMap_role()
	 {
		 return map_role;
	 }
	 public Vector<Mapping> getMap_down()
	 {
		 return map_down;
	 }
	 public Vector<Mapping> getMap_middle()
	 {
		 return map_middle;
	 }
	  public void map_middle_clear()
	  {
		  map_middle=new  Vector<Mapping>();
	  }
	  public Mapping findTheObject(int x,int y){

	  		for(int i=0;i<map_role.size();i++)
	  		{
	  			if(x>map_role.get(i).getX()&&x<(map_role.get(i).getX()+map_role.get(i).getWidth())&&y>map_role.get(i).getY()&&y<(map_role.get(i).getY()+map_role.get(i).getHeight()))
	  			{
	  			       return map_role.get(i);	
	  			}
	  		}
	  		for(int i=0;i<map_down.size();i++)
	  		{
	  			if(x>map_down.get(i).getX()&&x<(map_down.get(i).getX()+map_down.get(i).getWidth())&&y>map_down.get(i).getY()&&y<(map_down.get(i).getY()+map_down.get(i).getHeight()))
	  			{
	  			       return map_down.get(i);	
	  			}
	  		}
	  		return null;
	  	}
		public void InitSpace()
	  	{
	  		int i=0;
	  		int locX=0;
	  		int locY=0;
	  		int height=0;
	  		int width=0;
	  		map_down=new Vector<Mapping>();
	  		Mapping m=new Mapping(450,400,200,150);
	  		Role role_temp=gm.nextRole();
	  		m.setObj(gm.nextRole());
	  		map_role.add(m);
	  	    m=new Mapping(225,0,200,150);
	  		m.setObj(gm.nextRole());
	  		map_role.add(m);
	  		locY=450;
	  		height=150;
	  		width=100;
	          for(;i<role_temp.getCards().size();i++,locX+=100)
	          {
	          	m=new Mapping(locX,locY,height,width);
	          	m.setObj(role_temp.getCards().get(i));
	          	map_down.add(m);
	          }
	  	}
		public void redistributeSpace()
	  	{
	  		int i=0;
	  		int locX=0;
	  		int locY=450;
	  		int height=0;
	  		int width=0;
	  		height=150;
	  		width=100;
	  		map_down=new Vector<Mapping>();
	  		Mapping m;
	  	    for(;i<current_role.getCards().size();i++,locX+=100)
	  	    {
	  	        m=new Mapping(locX,locY,height,width);
	  	        m.setObj(current_role.getCards().get(i));
	  	        map_down.add(m);
	  	     }
	  	}
		public void CardGiveUP()
		{
			for(int i=0;i<map_down.size();i++)
			{
				if(map_down.get(i).getY()==400)
				{
					Mapping m_temp=map_down.get(i);
					Card temp=(Card)map_down.get(i).getobj();
					current_role.removeCard(temp);
					m_temp.setX(100+map_middle.size()*100);
					m_temp.setY(200);
					map_middle.add(m_temp);
				}
			}
			this.redistributeSpace();
		}
}
