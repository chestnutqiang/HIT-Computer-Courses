package relations;

import physicalObject.Friend;

public class SocialTie extends Relation<Friend,Friend> {
  private double intimacy;
  /*
   * RI：
   * Intimacy：亲密度
   * o1:关系中的第一个人
   * o2:关系中的第二个人
   * 
   * AF:
   * 描述朋友关系
   * 
   * safety from rep exposure:
   * All fields are private;
   * Inimacy是double型，所以 是immutable类型的
   */
  
  public SocialTie(Friend o1,Friend o2) {
    this.object1 = o1;
    this.object2 = o2;
  }
  
  /*
   * @return 返回当前社交关系的亲密度
   */
  public double getIntimacy() {
    return this.intimacy;
  }
  
  /*
   * @param x：要设置的当前社交关系的亲密度
   */
  public void setIntimacy(Double x) {
    intimacy = x;
  }
  
  /*
   * @param：f：一Friend类型的变量
   * @return：true如果当前关系包含f
   */
  boolean containFriend(Friend f) {
    boolean a  =  false;
    boolean b  =  false;
    if (object1.getClass() == f.getClass()) {
      a = object1.equals(f);
    }
      
    if (object2.getClass() == f.getClass()) {
      b = object2.equals(f);
    }
      
    return a || b;
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
    if (o.getClass() != this.getClass()) {
      return false;
    } else {
      SocialTie f = (SocialTie)o;
      boolean a = this.containFriend(f.object1);
      boolean b = this.containFriend(f.object2);
      return a && b;
    }
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return object1.hashCode() + object2.hashCode();
  }
}
