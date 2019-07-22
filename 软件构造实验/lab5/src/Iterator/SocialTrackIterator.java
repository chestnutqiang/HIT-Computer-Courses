package Iterator;

import java.util.Iterator;

import applications.SocialNetworkCircle;
import track.PlanetTrack;
import track.SocialTrack;

public class SocialTrackIterator implements Iterator<SocialTrack>{
  
  private SocialNetworkCircle system;
  private int index;
  private int size;
  
  /*
   * RI：
   * system：一个社交轨道系统
   * index：迭代器当前遍历到的下标
   * n：轨道数量
   * 
   * AF:
   * 遍历社交轨道的迭代器
   * 
   * safety from rep exposure:
   * All fields are private;
   * index，size是int型，所以 是immutable类型的
   */
  
  /**
   * 构造方法.
   * @param system 要遍历的对象
   */
  public SocialTrackIterator(SocialNetworkCircle system) {
    this.system = system;
    index = 0;
    size = system.getTracks().size();
  }

  /*
   * @return 返回true如果有下一个元素
   */
  @Override
  public boolean hasNext() {
    if (index < size) {
      return true;
    }
    return false;
  }

  /*
   * @return：返回下一个元素
   */
  @Override
  public SocialTrack next() {
    if (index < size) {
      SocialTrack t = system.getTracks().get(index);
      index++;
      return t;
    }
    return null;
  }

}
