package Visulize;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import applications.ConcreteCircularOrbitE;
import constractFromTxt.ConstractASystem;



public class VisulizeE extends CircularOrbitHelper{
  
  private static ConcreteCircularOrbitE system;
  /*
   * RI：
   * system：一个电子轨道，要求非null
   * 
   * AF：
   * 根据system来生成图像可视化电子轨道
   * 
   * safety from rep exposure:
   * All fields are private;
   */
  public static void main(String[] args) throws IOException {
    system = ConstractASystem.Constract("src/AtomStructure/AtomicStructure.txt");
    visualize(system);
  }
  
  /*
   * @param：system：一个原子轨道系统
   */
  public static void visualize(ConcreteCircularOrbitE system) {
    JFrame newFrame = new JFrame("AtomStructrue");       
    CircularOrbitHelper.setWindows(newFrame);
    newFrame.add(new guiDao());     //将需要呈现的图像添加进JFrame中
  }
  
  static class guiDao  extends JPanel {
    
    /*
     * @param：g：Graphics型变量
     */
    public void paint(Graphics g) {
      int i,j;
      g.setColor(Color.BLACK);  
      g.fillRect(0, 0, 20, 20);
      g.fillOval(390, 390, 20, 20);
      for(i = 1;i <= system.getTrackNum();i++) {
        g.drawOval(400-40*i, 400-40*i, 80*i, 80*i);
        int nums = system.getTracks().get(i-1).ObjectOnTrack.size();
        double angle = 360/nums;
        for(j = 0;j<nums;j++) {
          double angle1 = j*angle;
          double x = 400+40*i*Math.cos(angle1/180*Math.PI),y = 400+40*i*Math.sin(angle1/180*Math.PI);
          g.fillOval((int)(x-5), (int)(y-5), 10, 10);
        }
      }
    }

  }

  
}
