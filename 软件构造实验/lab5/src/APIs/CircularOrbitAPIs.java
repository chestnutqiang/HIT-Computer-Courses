package APIs;

import circularOrbit.CircularOrbit;

/*
 * AF:
 * 
 * getObjectDistributionEntropy(CircularOrbit c)
 * 计算多轨道系统中各轨道上物体分布的熵值
 * 
 * getLogicalDistance(CircularOrbit c, PlanetWithSatellite e1, PlanetWithSatellite e2)
 * 计算任意两个物体之间的最短逻辑距离
 * 
 * getPhysicalDistance(CircularOrbit c, PlanetWithSatellite e1, PlanetWithSatellite e2)
 * 计算任意两个物体之间的物理距离
 * 
 * getDifference(CircularOrbit c1, CircularOrbit c2)
 * 计算两个多轨道系统之间的差异
 */

public interface CircularOrbitAPIs<E> {

  abstract double getObjectDistributionEntropy(CircularOrbit c);
  
  abstract int getLogicalDistance(CircularOrbit c, E e1, E e2);
  
  abstract double getPhysicalDistance(CircularOrbit c, E e1, E e2);
  
  public abstract Difference getDifference(CircularOrbit c1, CircularOrbit c2);
  

}

