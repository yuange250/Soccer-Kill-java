package com.chen.UI;

import javax.swing.JFrame;

public class Main_frame extends JFrame{
	Main_Panel cp=new Main_Panel();
  public Main_frame(){
	  this.setLocation(150,150);
	  this.setSize(600,600);
	  this.setVisible(true);
	  this.add(cp);
	  this.setLayout(null);
	  cp.setLocation(0, 0);
	  Thread t=new Thread(cp);
	  t.start();
  }
}
