package Visulize;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import applications.ConcreteCircularOrbit;
import constractFromTxt.ConstractAStellarSystem;

public class Visulize extends CircularOrbitHelper{
	
	private static ConcreteCircularOrbit system;
	/*
	 * RI：
	 * system：一个行星轨道，要求非null
	 * 
	 * AF：
	 * 根据system来生成图像可视化行星轨道
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 */
	
	static void Constract() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt"); 
	}

	public static void main(String args[]) throws IOException {
		Constract();
		visualize(system);
	}
	
	/*
	 * @param：c：一个行星轨道系统
	 */
	public static void visualize(ConcreteCircularOrbit c) {
		CircularOrbitHelper system=new CircularOrbitHelper(); 
		JFrame look =new JFrame("StellarSystem");;
		system.setWindows(look);
		look.add(new guiDao()); 
	}
	

	static class guiDao  extends JPanel {
	    
		/*
		 * @param：g：Graphics型变量
		 */
		public void paint(Graphics g) {
			int i;
			g.setColor(Color.RED);
			g.fillOval(390, 390, 20, 20);
			for(i=0;i<system.getTrack().size();i++) {
				g.setColor(Color.BLACK);
				g.drawOval(400-30*(i+1), 400-30*(i+1), 60*(i+1), 60*(i+1));
				double angle=system.getTrack().get(i).ObjectOnTrack.get(0).planet.getAngle();
				double x=400+30*(i+1)*Math.cos(angle),y=400+30*(i+1)*Math.sin(angle);
				int x1=(int)(x-5),y1=(int)(y-5);
				String color=system.getTrack().get(i).ObjectOnTrack.get(0).planet.getColor().toString();
				switch(color) {
				case "Blue":{
					g.setColor(Color.blue);
					break;
				}
				case "Dark":{
					g.setColor(Color.BLACK);
					break;
				}
				case "Red":{
					g.setColor(Color.RED);
					break;
				}
				case "Yellow":{
					g.setColor(Color.YELLOW);
					break;
				}
				}
				g.fillOval(x1,y1, 10, 10);
			}
		}

	}
	
}
		 

