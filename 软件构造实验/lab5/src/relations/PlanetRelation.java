package relations;

import physicalObject.PlanetWithSatellite;

public class PlanetRelation <Stellar,PlanetWithStatellite> {
  private Stellar o1;
  private PlanetWithSatellite o2;
   
  /*
    * RI
    * o1：恒星，要求不为null
    * o2：行星，要求不为null
    * 
    * AF：
    * 表示o1和o2之间的关系
    * 
    * safe from rep exposure
    * All fields are private;
    */
   
  public PlanetRelation(Stellar a,PlanetWithSatellite b) {
    o1 = a;
    o2 = b;
  }
}
