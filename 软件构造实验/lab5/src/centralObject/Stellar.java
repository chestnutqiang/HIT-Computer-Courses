package centralObject;

import otherDirectory.label;
import otherDirectory.number;

public class Stellar extends CentralPoint {
  private label name;
  private number radius;
  private number weight;
  
  /*
   * RI：
   * name：恒星的名字，label类型
   * radius：恒星的半径，number类型
   * weight：恒星的质量，number类型
   * 
   * AF：
   * 表示恒星
   * 
   * safe from rep exposure:
   * 所有的rep都是private的
   * name，radius,weight都是mutable的，所以getName(),getRadius(),getWeight()进行防御式拷贝
   */
  
  /*
   * @return：对name进行防御式拷贝
   */
  public label getName() {
    return new label(name.toString());
  }
  
  /*
   * return:对radius进行防御式拷贝
   */
  public number getRadius() {
    return new number(radius.toString());
  }
  
  /*
   * return:对恒星质量进行防御式拷贝
   */
  public number getWeight() {
    return new number(weight.toString());
  }

  /**
   * 构造方法.
   * name 恒星名字
   * b 恒星半径
   * c 恒星质量
   */
  public Stellar(label name,number b,number c) {
    this.name = name;
    radius = b;
    weight = c;
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
    if (this.getClass() == o.getClass()) {
      Stellar temp = (Stellar)o;
      return name.equals(temp.name) && radius.equals(temp.radius) && weight.equals(temp.weight);
    }
    return false;
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    int ans = name.hashCode() + radius.hashCode() + weight.hashCode();
    return ans;
  }
}
