package physicalObject;

import java.util.List;

/*
 * RI:
 * planet:行星，Planet类型，要求非空
 * satellites：这个行星的卫星
 * 
 * AF：
 *  用 decorator模式来实现为行星添加卫星
 */

public class PlanetWithSatellite {
  public Planet planet;
  private List<Satellite> satellites;
  
  /*
   * RI:
   * planet:行星，Planet类型，要求非空
   * satellites：这个行星的卫星
   * 
   * AF：
   *  用 decorator模式来实现为行星添加卫星
   */
  
  /*
   * @param：planet：一个行星，不能为null
   * 构造一个以planet为主要行星，带有一系列卫星的PlanetWithSatellite对象。
   */
  public PlanetWithSatellite(Planet planet) {
    this.planet = planet.clone();
  }
  
  /*
   * @return：当前对象的行星的防御式拷贝
   */
  public Planet getPlanet() {
    return planet.clone();
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
      PlanetWithSatellite p = (PlanetWithSatellite)o;
      return planet.equals(p.getPlanet());
    }
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return planet.hashCode();
  }
}

class Satellite {
  String name;
  //....可以加入其他rep
  
  public Satellite(String name) {
    this.name = name;
  }
  
}
