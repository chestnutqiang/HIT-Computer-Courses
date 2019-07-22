package physicalObject;

import otherDirectory.label;
import otherDirectory.number;

public class Planet extends PhysicalObject implements Cloneable {
  private label name;
  private label shape;
  private label color;
  private String direction;
  private double  angle;
  private number planetRadius;
  private number orbitalRadius;
  private number rotationSpeed;
  
  /*
   * RI：
   * name：行星的名字，label类型
   * shape：行星形状，label类型
   * color：行星颜色，label类型
   * direction：公转方向，String类型
   * angle：行星与恒星所成的角度，double类型
   * PlanetRadius,orbitalRadius, rotationSpeed：行星半径，公转半径，公转速度，number类型
   * 
   * AF：
   * 表示行星
   * 
   *  safety from rep exposure:
   * All fields are private;
   * direction and angle 是immutable的
   * name,rotationSpeed,orbitalRadius,color是mutable的，所以getName(),getSpeed(),getorbitalRadius()
   * ,getColor()进行了防御式拷贝
   */
  
  /*
   * @return：返回行星名字的拷贝
   */
  public label getName() {
    label temp = new label(name.toString());
    return temp;
  }
  
  public label getState() {
    label temp = new label(shape.toString());
    return temp;
  }
  
  /*
   * @return：返回行星角度的拷贝
   */
  public double getAngle() {
    return angle;
  }
  
  /*
   * @return：返回行星速度的拷贝
   */
  public number getSpeed() {
    return new number(rotationSpeed.toString());
  }
  
  /*
   * @return：返回公转半径的拷贝
   */
  public number getOrbitalRadius() {
    return new number(orbitalRadius.toString());
  }
  
  public number getPlanetRadius() {
    return new number(planetRadius.toString());
  }
  
  /*
   * @return：返回行星公转方向
   */
  public String getDirection() {
    return direction;
  }
  
  /*
   * @return：返回行星颜色的拷贝
   */
  public label getColor() {
    return new label(color.toString());
  }
 
  
  /**
   * 构造方法.
   * @param n 行星的名字
   * @param s 行星形状
   * @param c 行星颜色
   * @param r1 行星半径
   * @param r2 公转半径
   * @param speed 公转速度
   * @param d 公转方向
   * @param an 行星与恒星所成的角度
   */
  public Planet(label n,label s,label c,number r1,number r2,number speed,String d,double an) {
    name = n;
    shape = s;
    color = c;
    planetRadius = r1;
    orbitalRadius = r2;
    rotationSpeed = speed;
    direction = d;
    angle = an;
  }
  
  /*
   * @return当前对象的一个复制的对象
   */
  public Planet clone() {
    return new Planet(name,shape,color,planetRadius,orbitalRadius,rotationSpeed,direction,angle);
  }
  
  
  
  /*
   * @param：o：任意一种类型的变量
   * @return：返回true如果o与当前变量相等
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
      
    if (this.getClass() != o.getClass()) {
      return false;
    } else {
      Planet p1 = (Planet)o;
      return name.equals(p1.getName());
    }
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
