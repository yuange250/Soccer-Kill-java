package com.chen.tools;

public class Mapping {
	int locX;
	int locY;
	int height;
	int width;
	Object object;
	public Mapping(int locX,int locY,int height,int width){
		this.locX=locX;
		this.locY=locY;
		this.height=height;
		this.width=width;
	}
	public void setObj(Object object){
		this.object=object;
	}
	public Object getobj(){
		return object;
	}
	public int getX(){
		return locX;
	}
	public int getY(){
		return locY;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public void setY(int y)
	{
		this.locY=y;
	}
	public void setX(int x)
	{
		this.locX=x;
	}
}