package track;

import java.util.ArrayList;

import otherDirectory.number;
import physicalObject.Electron;

public class ElectronTrack extends Track<Integer,Electron> {
  
  /*
   * AF：
   * 表示电子的轨道
   * 
   * safety from rep exposure:
   * All fields are private;
   */
  public  ElectronTrack(int r) {
    this.r = r;
    ObjectOnTrack = new ArrayList<Electron>();
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
      ElectronTrack t = (ElectronTrack)o;
      return this.r.equals(t.r);
    }
    return false;
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return r.hashCode();
  }
}
