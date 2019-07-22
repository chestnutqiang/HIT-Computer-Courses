package track;

import java.util.ArrayList;

import otherDirectory.number;
import physicalObject.PlanetWithSatellite;

public class PlanetTrack extends Track<number,PlanetWithSatellite> {
  /*
   * RI：
   * r:轨道半径
   * 
   * AF：
   * 表示行星轨道
   */
  public PlanetTrack(number r1) {
    r = r1;
    ObjectOnTrack = new ArrayList<PlanetWithSatellite>();
  }
  
}
