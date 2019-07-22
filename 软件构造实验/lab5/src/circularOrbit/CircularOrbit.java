package circularOrbit;

import java.util.ArrayList;
import java.util.List;

import track.Track;

/*
 * RI：
 * L中心点类型
 * E轨道上的类型
 * 
 * AF:
 * 表示轨道的一个抽象类
 */

public interface CircularOrbit<L,E> {
  
  //增加轨道
  void addNewTrack(Track t);
  
  //消去轨道
  boolean removeTrack(int x);
  
  //增加中心点
  boolean addCentralPoint(L c);
  
  //特定轨道加物体
  boolean addObjectOnTrack(Track t,E o);
  
  //增加中心点物体和一个轨道物体之间的关系 
  void addRelationship(E o);
  
  //增加两个轨道物体之间的关系 
  void addRelatioOfPhyO(E o1,E o2);
  
}
