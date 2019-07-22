package relations;

import java.util.ArrayList;
import java.util.List;

import physicalObject.Electron;



public class ElectronRealtion extends Relation<Electron,Electron> {
  
  /*
   * AF：描述电子之间的关系
   * 
   * safety from rep exposure:
   * All fields are private;
   * object1和object2是mutable的，所以getObject1()和getObject1()进行了防御式拷贝
   */
  public ElectronRealtion(Electron e1,Electron e2) {
    object1 = e1;
    object2 = e2;
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
      
    if (o.getClass() == this.getClass()) {
      ElectronRealtion r = (ElectronRealtion)o;
      return this.object1.equals(r.object1);
    }
    return false;
  }
  
  /*
   * @param：e：一个电子
   * @return：返回true如果当前关系中的两个电子包含e
   */
  public boolean inRelation(Electron e) {
    return e.equals(object1) || e.equals(object2);
  }
  
  /**
   * 返回与e有关系的那个电子.
   * @param e 一个电子
   * @return 返回与e有关系的那个电子
   */
  public Electron relationedElectron(Electron e) {
    if (e.equals(object1)) {
      return object2;
    } else if (e.equals(object2)) {
      return object1;
    } else {
      return null;
    } 
  }
  
  /*
   * @return:返回object1的拷贝
   */
  public Electron getObject1() {
    Electron e = new Electron(object1.getTrackNum());
    return e;
  }
  
  /*
   * @return:返回object2的拷贝
   */
  public Electron getObject2() {
    Electron e = new Electron(object2.getTrackNum());
    return e;
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return object1.hashCode() + object2.hashCode();
  }
}
