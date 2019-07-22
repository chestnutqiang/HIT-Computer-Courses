package Visulize;

import java.io.IOException;

/*
 * RI：
 * 在visualize(CircularOrbit c)中，输入的c是一个多轨道模型的具体实现类
 * 
 * AF:
 * 可视化多轨道模型
 * setwindows是定义了窗口的大小和一些属性
 * visualize(CircularOrbit c)是可视化c这个多轨道系统，需要在以后的子类中override
 */

import javax.swing.JFrame;

import circularOrbit.CircularOrbit;

public  class CircularOrbitHelper {   
  
  
  public static void setWindows(JFrame windows) {
    windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //定义JFrame关闭时的操作（必需），有效避免不能关闭后台当前框体进程的问题    
    windows.setSize(900, 900);      //定义JFrame的相关属性
    windows.setLocation(200, 200);
    windows.setVisible(true); 
  }
  
  public static void visualize(CircularOrbit c) {//override

  };
  
}
