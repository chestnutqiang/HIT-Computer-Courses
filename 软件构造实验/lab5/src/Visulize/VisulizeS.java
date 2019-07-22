package Visulize;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import applications.SocialNetworkCircle;
import constractFromTxt.ConstractSocialNetwork;
import physicalObject.Friend;


public class VisulizeS {
  private  static SocialNetworkCircle system;
  /*
   * RI：
   * system：一个社交网络，要求非null
   * 
   * AF：
   * 根据system来生成图像可视化社交网络
   * 
   * safety from rep exposure:
   * All fields are private;
   */
  public static void main(String[] args) throws IOException {
    system=new ConstractSocialNetwork("src/SocialNetworkCircle/SocialNetworkCircle.txt").Constract();
    visualize(system);
  }
  
  public static void visualize(SocialNetworkCircle system) {
    JFrame newFrame=new JFrame("AtomStructrue");       
    CircularOrbitHelper.setWindows(newFrame);
    newFrame.add(new guiDao());
  }
  
  static class guiDao  extends JPanel {
      
    public void paint(Graphics g) {
      int i,j;
      g.setColor(Color.BLACK);  
      g.fillRect(0, 0, 20, 20);
      g.fillOval(390, 390, 20, 20);
      for(i=1;i<=system.getTracks().size();i++) {
        g.drawOval(400-40*i, 400-40*i, 80*i, 80*i);
        List object=system.getTracks().get(i-1).ObjectOnTrack;
        int nums=object.size();
        double angle=360/nums;
        for(j=0;j<nums;j++) {
          double angle1=j*angle;
          double x=400+40*i*Math.cos(angle1/180*Math.PI),y=400+40*i*Math.sin(angle1/180*Math.PI);
          g.setColor(Color.blue);
          g.fillOval((int)(x-5), (int)(y-5), 10, 10);
          g.drawLine((int)x, (int)y, 400, 400);
          Friend f1=(Friend) object.get(j);
          g.setColor(Color.RED);
          for(Friend f2:f1.MyFriend()) {
            int tracknum=f2.getTrackNum(),number=system.getTracks().get(tracknum).ObjectOnTrack.indexOf(f2);
            double x1=400+40*(tracknum+1)*Math.cos(number/system.getTracks().get(tracknum).ObjectOnTrack.size()/180*Math.PI);
            double y1=400+40*(tracknum+1)*Math.sin(number/system.getTracks().get(tracknum).ObjectOnTrack.size()/180*Math.PI);
            g.drawLine((int)x1, (int)y1, (int)x, (int)y);
          }
          g.setColor(Color.black);
        }
      }
    }

  }

}
