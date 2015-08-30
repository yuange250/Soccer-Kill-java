package com.chen.tools;

import java.util.Vector;

import com.chen.cards.Card;
import com.chen.robot.Robot;
import com.chen.roles.Messi;
import com.chen.roles.Role;

public class TotalMapping implements Runnable{
      //all the pictures mapping is managed in this class
	  private Vector<Mapping> map_role=new Vector<Mapping>();//roles
	  private Vector<Mapping> map_down=new Vector<Mapping>();//the player's cards
	  private Vector<Mapping> map_middle=new Vector<Mapping>();//the cards which have being used
	 
	  private GameManager gm=new GameManager();//distribute cards
	  private Role current_role=gm.nextRole();
	  private int cards_drop_num=0;
	

	  //this part is going to concentrate on mod 4 drop cards
	  public void cards_drop_num_clear()
	  {
		  cards_drop_num=0;
	  }
	  public int getcards_drop_num()
	  {
		  return cards_drop_num;
	  }
	  public void setcards_drop_num(int num)
	  {
		  cards_drop_num=num;
	  }
	  
	  
	  public Role getCurrentUser(){
		  return current_role;
	  }
	  public Robot getRobot()
	  {
		  return gm.nextRobot();
	  }
	  
	  public void run()
	  {//middle auto clean
		 
		  while(true)
		  {
		     for(int i=0;i<map_middle.size();)
		     {
		    	 map_middle.get(i).lifeAdd();;
		    	 if(map_middle.get(i).getLife()==40)
		    	 {
		    		 gm.collectoldcards((Card)map_middle.get(i).getobj());
		    		 map_middle.remove(i);
		    	 }
		    	 else
		    	 {
		    		 i++;
		    	 }
		     }
		     try {
			    Thread.sleep(50);
		    } catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			    e.printStackTrace();
		     }
		  }
		  
	  }
	 public void addCardstouser(Role role)
	 {
		  gm.distributeCards(role);
		  this.redistributeSpace();
		  System.out.println(current_role.getCards().size());
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
	 
	 //give me a piont return the object which this piont points at
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
	  //init the UI at first
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
		//redistribute the space of map_down
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
		
		//dropCard
		//drop the cards which have been selected out
		public void cardDrop()
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
