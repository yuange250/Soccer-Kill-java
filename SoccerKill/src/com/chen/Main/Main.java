package com.chen.Main;
import com.chen.UI.*;

public class Main {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
         GameMainThread gmt=new GameMainThread();
         Thread t=new Thread(gmt);
         t.start();
	}

}
