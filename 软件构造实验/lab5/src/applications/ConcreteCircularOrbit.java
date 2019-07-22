package applications;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import APIs.StellarAPI;
import centralObject.CentralPoint;
import physicalObject.PhysicalObject;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.Track;
import otherDirectory.label;
import otherDirectory.number;
import centralObject.Stellar;
import circularOrbit.CircularOrbit;
import constractFromTxt.ConstractAStellarSystem;
import logs.Mylog;
import relations.PlanetRelation;
import track.PlanetTrack;

public class ConcreteCircularOrbit implements CircularOrbit<Stellar,PlanetWithSatellite>,Cloneable {
  
  private List<PlanetTrack> tracks = new ArrayList<PlanetTrack>();
  private List<PlanetRelation> relation = new ArrayList<PlanetRelation>();
  private Stellar central;
  
  /*
   * RI:
   * tracks:行星轨道
   * relation:行星之间的关系
   * central：中央的恒星
   * 
   * AF
   * 表示一个行星系统
   * 
   * safety from rep exposure:
   * All fields are private;
   * tracks,relation，central是mutable的，所以getStellar()，getTrack()，getRelation()进行了防御式拷贝
   */
  
  public static void main(String arg0s[]) throws IOException {
		ConcreteCircularOrbit system = ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
		StellarAPI test = new StellarAPI();
		test.getPhysicalDistance(system, system.tracks.get(0).ObjectOnTrack.get(0), system.tracks.get(1).ObjectOnTrack.get(0));
  }
  
  public ConcreteCircularOrbit clone() {
	  ConcreteCircularOrbit system = new ConcreteCircularOrbit(getTrack(),getStellar());
	  return system;
  }
  
  
  void checkRep() {
    assert central != null : "central stellar is null";
    for (PlanetTrack t:tracks) {
      assert t.ObjectOnTrack.size() > 0 : "this track has no object";
    }
  }
  
  /*
   * @param：t1：一系列轨道，c：一个恒星
   */
  public ConcreteCircularOrbit(List<PlanetTrack> t1,Stellar c) {
    tracks = t1;
    central = c;
  }
  
  //创造一个空的对象
  public ConcreteCircularOrbit() {
    
  }
  
  /**
   * 返回一个新的恒星，防御式拷贝.
   * @return 返回一个新的恒星，防御式拷贝
   */
  public Stellar getStellar() {
    if (central == null) {
      return null;
    }
    Stellar temp = new Stellar(central.getName(),central.getRadius(),central.getWeight());
    assert temp.equals(central);
    return temp;
  }
  
  /**
   * 返回轨道，防御式拷贝.
   * @return 返回轨道，防御式拷贝
   */
  public List<PlanetTrack> getTrack() {
    List<PlanetTrack> t = new ArrayList<PlanetTrack>();
    t.addAll(tracks);
    assert t.equals(tracks);
    return t;
  }
  
  /**
   * 返回恒星与行星的关系，防御式拷贝.
   * @return 返回恒星与行星的关系，防御式拷贝
   */
  public List<PlanetRelation> getRelation() {
    List<PlanetRelation> r = new ArrayList<PlanetRelation>();
    r.addAll(relation);
    assert r.equals(relation);
    return r;
  }

  /*
   * @param:t:一条行星轨道
   */
  @Override
  public void addNewTrack(Track t) {
    PlanetTrack t1 = (PlanetTrack)t;
    assert t1.getClass() == t.getClass();
    if (!tracks.contains(t1)) {
      tracks.add(t1);
    }
      
    checkRep();
  }

  /*
   * @param:x:轨道数，要求>0
   * @return :true如果成功移除一条轨道
   */
  @Override
  public boolean removeTrack(int x) {
    assert x >= 0;
    if (tracks.size() > x) {
      tracks.remove(x);
      Mylog.logger.info("[Stellar] " + "delete a planet track");
      checkRep();
      return true;
    }
    return false;
  }

  /*
   * @param:c：一个恒星
   * @return :true如果成功加入恒星
   */
  @Override
  public boolean addCentralPoint(Stellar c) {
    if (central != null) {
      return false;
    } else {
      Mylog.logger.info("[Stellar] " + "add a new Steller");
      central = c;
      checkRep();
      return true;
    }
  }

  /*
   * @param：o：行星
   *  为恒星与行星添加关系
   */
  @Override
  public void addRelationship(PlanetWithSatellite o) {
    assert o != null;
    Mylog.logger.info("[Stellar] " + "add a relation between " + central.getName().toString()
        + " to" + o.planet.getName().toString());
    PlanetRelation r = new PlanetRelation(central,o);
    relation.add(r);
  }

  /*
   * 没有继承这个方法，明确要求行星之间没有关系
   */
  @Override
  public void addRelatioOfPhyO(PlanetWithSatellite o1, PlanetWithSatellite o2) {
    try {
      throw new Exception("not implement");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 计算时间t的时候行星的角度.
   * @param planet 行星，要求在这个系统中，t：时间，要求正数
   * @return :t时间的时候P行星的角位置,要求 >= 0,<360
   */
  public BigDecimal currentAngle(PlanetWithSatellite planet,BigDecimal t) {
    Mylog.logger.info("[Stellar] " + "caculate angle of "
         + planet.getPlanet().getName().toString() + " at time " + t.toString());
    assert t.compareTo(new BigDecimal(0)) > 0;
    boolean flag = false;
    for (PlanetTrack t1:tracks) {
      if (t1.ObjectOnTrack.contains(planet)) {
        flag = true;
      }
        
    }
      
    assert flag;
    
    Planet p = planet.getPlanet();
    BigDecimal omiga = p.getSpeed().value.divide(p.getOrbitalRadius().value,
        10,BigDecimal.ROUND_HALF_UP);
    BigDecimal theta = omiga.multiply(t);
    BigDecimal angle1 = new BigDecimal(p.getAngle());
    if (p.getDirection().equals("CW")) {
      theta = theta.add(angle1);
      theta = theta.divideAndRemainder(BigDecimal.valueOf(360))[1].setScale(0,
          BigDecimal.ROUND_DOWN);
      assert theta.compareTo(new BigDecimal("0")) >= 0 
          && theta.compareTo(new BigDecimal("360")) < 0;
      return theta;
    } else {
      theta = angle1.subtract(theta);
      theta = theta.divideAndRemainder(BigDecimal.valueOf(360))[1].setScale(0,
          BigDecimal.ROUND_DOWN);
      if (theta.compareTo(new BigDecimal("0")) == -1) {
        theta = theta.add(new BigDecimal("360"));
      }
      assert theta.compareTo(new BigDecimal("0")) >= 0 
          && theta.compareTo(new BigDecimal("360")) < 0;
      return theta;
    }
  }
  
  /**
   * 计算行星p1和行星p2的距离.
   * @param planet1 planet2两个行星
   * @return 行星p1和行星p2的距离
   */
  public BigDecimal distanceOfTwoPlanet(PlanetWithSatellite planet1,PlanetWithSatellite planet2) {
    Mylog.logger.info("[Stellar] " + "caculate the distance of " + planet1.planet.getName() 
        + " and " + planet2.planet.getName());
    Planet p1 = planet1.getPlanet();
    Planet p2 = planet2.getPlanet();
    BigDecimal r1 = p1.getOrbitalRadius().value;
    BigDecimal r2 = p2.getOrbitalRadius().value;
    double theta = p1.getAngle() - p2.getAngle();
    theta = (theta / 180) * Math.PI;
    double costheta = Math.cos(theta);
    BigDecimal costheta1 = new BigDecimal(costheta);
    BigDecimal two = new BigDecimal(2);
    BigDecimal r1pingfang = r1.multiply(r1);
    BigDecimal r2pingfang = r2.multiply(r2);
    BigDecimal r1r2cos = r1.multiply((r2.multiply(costheta1)));
    BigDecimal distancepingfang = r1pingfang.add(r2pingfang.subtract(r1r2cos.multiply(two)));
    BigDecimal distance = number.sqrt(distancepingfang, 0);
    assert distance.compareTo(new BigDecimal("0")) > 0;
    return distance;
  }

  /*
   * t:一条行星轨道，o：一个行星
   * @return :true如果成功在轨道t上添加行星o
   */
  @Override
  public boolean addObjectOnTrack(Track t, PlanetWithSatellite o) {
    assert o != null;
    assert t != null;
    Mylog.logger.info("[Stellar] " + "add a planet at a track");
    for (PlanetTrack t1:tracks) {
      if (t1.r.equals(t.r)) {
        t1.ObjectOnTrack.add(o);
        checkRep();
        return true;
      }
    }
    return false;
  }
}
