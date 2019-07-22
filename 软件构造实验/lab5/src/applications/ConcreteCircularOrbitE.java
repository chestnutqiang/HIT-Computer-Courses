package applications;

import java.util.ArrayList;
import java.util.List;

import centralObject.Nucleus;
import circularOrbit.CircularOrbit;
import logs.Mylog;
import track.ElectronTrack;
import track.Track;
import otherDirectory.number;
import physicalObject.Electron;
import relations.ElectronRealtion;


public class ConcreteCircularOrbitE implements CircularOrbit<Nucleus, Electron> ,Cloneable{
  
  private Nucleus nucleus;
  private int numberOfTracks;
  private List<ElectronTrack> tracks = new ArrayList<ElectronTrack>();//轨道
  private List<Electron> relation1;//存储与原子核有关系的那些电子
  private List<ElectronRealtion> relation2;//存储原子之间的关系
  private List<ConcreteCircularOrbitE> memento;
  
  /*
   * RI：
   * nucleus：原子核，要求非null
   * numberOfTracks：原子的轨道数
   * tracks：表示原子的轨道的集合
   * relation1：表示原子系统电子与原子核关系的集合
   * relation2：表示电子之间关系的集合
   * memento:存储原子轨道状态的集合，用于进行状态的恢复
   * 
   * 
   * AF：
   * 一个原子轨道结构
   * 
   * safety from rep exposure:
   * All fields are private;
   * nucleus,tracks,relation1,relation2,memento是mutable的，
   * 所以getNucleus(),getTracks(),getRelations1(),getRelations2()和getTrackNum()进行了防御式拷贝
   */
  
  
  void checkRep() {
    assert nucleus != null : "nucleus is null!";
    assert numberOfTracks >= 0 : "NUmber of tracks is nagetive";
    for (ElectronTrack t:tracks) {
      assert t.ObjectOnTrack.size() > 0 : "this track has no object";
    }
  }
  
  /**
   * 返回原子核的拷贝.
   * @return 返回原子核的拷贝
   */
  public Nucleus getNucleus() {
    Nucleus ans = new Nucleus(nucleus.getName());
    assert ans != null;
    return ans;
  }
  
  /*
   * @return:返回轨道数量的拷贝
   */
  public int getTrackNum() {
    return numberOfTracks;
  }
  
  /**
   * 返回轨道的拷贝.
   * @return 返回轨道的拷贝
   */
  public List<ElectronTrack> getTracks() {
    List<ElectronTrack> temp = new ArrayList<ElectronTrack>();
    temp.addAll(tracks);
    assert temp.equals(tracks);
    return temp;
  }
  
  /**
   * 返回原子与电子关系的拷贝.
   * @return 返回原子与电子关系的拷贝
   */
  public List<Electron> getRelations1() {
    List<Electron> temp = new ArrayList<Electron>();
    temp.addAll(relation1);
    assert temp.equals(relation1);
    return temp;
  }
  
  /**
   * 返回电子关系的拷贝.
   * @return 返回电子关系的拷贝
   */
  public List<ElectronRealtion> getRelations2() {
    List<ElectronRealtion> temp = new ArrayList<ElectronRealtion>();
    temp.addAll(relation2);
    assert temp.equals(relation2);
    return temp;
  }
  
  /**
   * 返回之前状态的拷贝.
   * @return 返回之前状态的拷贝
   */
  public List<ConcreteCircularOrbitE> getMemento() {
    List<ConcreteCircularOrbitE> temp = new ArrayList<ConcreteCircularOrbitE>();
    temp.addAll(memento);
    assert temp.equals(memento);
    return temp;
  }
  
  public ConcreteCircularOrbitE clone() {
	  ConcreteCircularOrbitE system = new ConcreteCircularOrbitE(getNucleus(),getTracks());
	  return system;
  }
  
  /**
   * 根据n和tracks构建系统.
   * @param n 原子核
   * @param tracks 轨道列表
   */
  public ConcreteCircularOrbitE(Nucleus n,List<ElectronTrack> tracks) {
    nucleus = n;
    for (ElectronTrack t:tracks) {
      ElectronTrack t1 = new ElectronTrack(t.r);
      t1.ObjectOnTrack.addAll(t.ObjectOnTrack);
      this.tracks.add(t1);
    }
    numberOfTracks = tracks.size();
    memento = new ArrayList<ConcreteCircularOrbitE>();
    relation1 = new ArrayList<Electron>();
    relation2 = new ArrayList<ElectronRealtion>();
    checkRep();
  }
  
  /*
   * @param：t 一条轨道，加入这个新轨道
   */
  @Override
  public void addNewTrack(Track t) {
    ElectronTrack t1 = (ElectronTrack)(t);
    assert t1.getClass() == t.getClass();
    Mylog.logger.info("[Electron] " + "add a new track in nuclues system");
    tracks.add(t1.r - 1, t1);
    numberOfTracks++;
    checkRep();
  }
  
  
  /*
   * @param：x 轨道的序号，移除轨道号为x的轨道
   * @return：true如果移除成功，否则false
   */
  @Override
  public boolean removeTrack(int x) {
    assert x >= 0;
    Mylog.logger.info("[Electron] " + "remove a track in nuclues system");
    for (ElectronTrack t:tracks) {
      if (t.r.equals(x)) {
        tracks.remove(t);
        checkRep();
        return true;
      }
    }
    return false;
  }

  /*
   * @param c 一个原子核，加入新的原子核，替换掉老的原子核
   * @return true如果加入成功，否则false
   */
  @Override
  public boolean addCentralPoint(Nucleus c) {
    if (nucleus != null) {
      nucleus = new Nucleus(c.getName());
      Mylog.logger.info("[Electron] " + "add a nuclues in nuclues system");
      checkRep();
      return true;
    }
    return false;
  }

  /*
   * @param：t 一条轨道，o 要加入的电子，在特定轨道上加入一个电子
   * @return：true如果加入成功，否则false
   */
  @Override
  public boolean addObjectOnTrack(Track t, Electron o) {
    assert o != null;
    assert t != null;
    Mylog.logger.info("[Electron] " + "add a electron on a track");
    ConcreteCircularOrbitE old = new ConcreteCircularOrbitE(nucleus,tracks);
    memento.add(old);
    for (ElectronTrack t1:tracks) {
      if (t1.equals(t)) {
        t1.ObjectOnTrack.add(o);
        checkRep();
        return true;
      }
    }
    return false;
  }

  /*
   * @param：o 一个电子，为o这个电子与原子核添加关系,要求在这个轨道系统里面
   */
  @Override
  public void addRelationship(Electron o) {
    boolean flag = false;
    for (ElectronTrack t:tracks) {
      if (t.ObjectOnTrack.contains(o)) {
        flag = true;
      }
    }
     
    assert flag : "this electron is out of this system";
    
    Mylog.logger.info("[Electron] " + "add a relation between a electron and a nuclues");
    if (!relation1.contains(o)) {
      relation1.add(o);
    }
      
  }
  
  /*
   * @param：o1 ，o2：两个电子，为这两个电子添加关系,要求这两个电子在这个系统里面
   */
  @Override
  public void addRelatioOfPhyO(Electron o1, Electron o2) {
    boolean flag1  =  false;
    boolean flag2  =  false;
    for (ElectronTrack t:tracks) {
      if (t.ObjectOnTrack.contains(o1)) {
        flag1 = true;
      }
        
      if (t.ObjectOnTrack.contains(o2)) {
        flag2 = true;
      }
    }
    assert flag1 && flag2 : "electron is out of this system";
    
    Mylog.logger.info("[Electron] " + "add a relation between two electrons");
    ElectronRealtion r = new ElectronRealtion(o1, o2);
    if (!relation2.contains(r)) {
      relation2.add(r);
    }
  }
  
  /**
   * 模拟电子跃迁.
   * @param e1 一个电子，t1 这个电子原来的轨道，t2 要跃迁的轨道，电子轨道跃迁，从t1轨道跃迁到t2轨道上,要求电子在系统内，两个轨道均是系统内的轨道
   */
  public void electronTransmit(Electron e1,ElectronTrack t1,ElectronTrack t2) {
    boolean flag = false;
    boolean flag1 = false;
    boolean flag2 = false;
    for (ElectronTrack t:tracks) {
      if (t.ObjectOnTrack.contains(e1)) {
        flag = true;
      }
       
    }
      
    assert flag : "this electron is out of this system";
    
    Mylog.logger.info("[Electron] " + "a electron transmits to another track in nuclues system");
    for (ElectronTrack t:tracks) {
      if (t.equals(t1)) {
        flag1 = true;
      }
       
      if (t.equals(t2)) {
        flag2 = true;
      }
        
    }
    assert flag1 && flag2 : "track is out of this system";
    assert !t1.equals(t2) : "t1,t2 is the same track";
    
    ConcreteCircularOrbitE old = new ConcreteCircularOrbitE(nucleus,tracks);
    memento.add(old);
    for (ElectronTrack t:tracks) {
      if (t.equals(t1)) {
        t.ObjectOnTrack.remove(e1);
        checkRep();
      }
      if (t.equals(t2)) {
        e1.setTrackNum(tracks.indexOf(t2) + 1);
        t.ObjectOnTrack.add(e1);
        checkRep();
      }
    }
  }
  
  /**
   * 把系统恢复到step步之前的状态.
   * @param step 要恢复的步数，管理电子跃迁 的状态进行状态的恢复,要求 >= 1
   * @return true如果恢复成功，否则false。
   */
  public boolean getback(int step) {
    assert step >= 1;
    
    Mylog.logger.info("[Electron] " + "Return to the previous state");
    int len = memento.size();
    if (step > 0 && step <= len) {
      tracks = memento.get(len - step).tracks;
      checkRep();
      return true;
    } else {
      return false;
    }
  }

}
