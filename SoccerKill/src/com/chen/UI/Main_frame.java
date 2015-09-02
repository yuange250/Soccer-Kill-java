package com.chen.UI;

import javax.swing.JFrame;

import com.chen.Main.GameMainThread;

public class Main_frame extends JFrame{
  private Main_Panel 	 cp=new Main_Panel();
  public Main_frame(){
	  this.setLocation(150,0);
	  this.setSize(800,700);
	  this.setVisible(true);
	  this.setLayout(null);
	  cp.setLocation(0, 0);
	  this.add(cp);
  }
  public Main_Panel getMainPanel()
  {
	  return cp;
  }
}
