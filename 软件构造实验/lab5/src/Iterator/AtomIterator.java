package Iterator;

import java.util.Iterator;

import applications.ConcreteCircularOrbitE;
import track.ElectronTrack;

/*
 * RI:
 * system：要遍历的多轨道原子系统
 * index：当前遍历到的下标
 * n：轨道的个数
 * 
 * AF：
 * 遍历原子系统的每一条原子轨道
 */

import track.PlanetTrack;

public class AtomIterator implements Iterator<ElectronTrack> {

  private ConcreteCircularOrbitE system;
  private int index;
  private int size;
  /*
 * RI：
 * system：一个原子轨道系统
 * index：迭代器当前遍历到的下标
 * n：轨道数量
 * 
 * AF:
 * 遍历原子轨道的迭代器
 * 
 * safety from rep exposure:
 * All fields are private;
 * index and n 是int类型的，所以是immutable
 */
  
  /**
   * 初始化iterator.
   * @param system 要遍历的系统
   */
  public AtomIterator(ConcreteCircularOrbitE system) {
    this.system = system;
    index = 0;
    size = system.getTracks().size();
  }

  /*
   * @return：true如果有下一个元素，否则为false
   */
  @Override
  public boolean hasNext() {
    if (index < size) {
      return true;
    }
    return false;
  }

  /*
   * @return：下一个元素
   */
  @Override
  public ElectronTrack next() {
    if (index < size) {
      ElectronTrack t = system.getTracks().get(index);
      index++;
      return t;
    }
    return null;
  }

}
