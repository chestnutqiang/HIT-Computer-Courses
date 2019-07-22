package APIs;

import applications.SocialNetworkCircle;
import circularOrbit.CircularOrbit;
import java.util.ArrayList;
import java.util.List;

import physicalObject.Friend;
import track.SocialTrack;

public class SocialNetworkCircleAPI implements CircularOrbitAPIs<Friend> {

  /*
   * AF:
   * 
   *safe from rep exposure:
   *没有rep
   */
  
  /*
   * @param：c：一个社交网络系统
   * @return：多轨道系统中各轨道上物体分布的熵值
   */
  @Override
  public double getObjectDistributionEntropy(CircularOrbit c) {
    SocialNetworkCircle system = (SocialNetworkCircle)c;
    int sumNum = 0;
    for (SocialTrack t:system.getTracks()) {
      sumNum = sumNum + t.ObjectOnTrack.size();
    }
    double ans = 0;
    for (SocialTrack t:system.getTracks()) {
      int size = t.ObjectOnTrack.size();
      double p = (double)size / sumNum;
      ans += -p * Math.log(p);
    }
    return ans;
  }

  /*
   * @param：c：一个社交网络系统，e1，e2：两个社交网络系统里面的人
   * @return：计算任意两个物体之间的最短逻辑距离
   */
  @Override
  public int getLogicalDistance(CircularOrbit c, Friend e1, Friend e2) {
    SocialNetworkCircle system = (SocialNetworkCircle)c;
    return system.distance(e1, e2);
  }

  @Override
  public double getPhysicalDistance(CircularOrbit c, Friend e1, Friend e2) {
    try {
      throw new Exception("not implement");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
  }

  /*
   * @param：c1，c2：两个社交网络系统
   * @return：计算两个多轨道系统之间的差异
   */
  @Override
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
    if (c1.getClass() != c2.getClass()) {
      return null;
    }
    SocialNetworkCircle system1 = (SocialNetworkCircle)c1;
    SocialNetworkCircle system2 = (SocialNetworkCircle)c2;
    List<SocialTrack> track1 = system1.getTracks();
    List<SocialTrack> track2 = system2.getTracks();
    final int trackNum = Math.abs(track1.size() - track2.size());
    List<Integer> objectNum = new ArrayList<Integer>();
    int i;
    int len = Math.max(track1.size(), track2.size());
    for (i = 0;i < len;i++) {
      if (i < track1.size()) {
        if (i < track2.size()) {
          objectNum.add(track1.get(i).ObjectOnTrack.size() - track2.get(i).ObjectOnTrack.size());
        } else {
          objectNum.add(track1.get(i).ObjectOnTrack.size());
        }
      } else {
        objectNum.add(-track2.get(i).ObjectOnTrack.size());
      }
    }
    List<Friend> planets1 = new ArrayList<Friend>();
    List<Friend> planets2 = new ArrayList<Friend>();
    for (SocialTrack t:track1) {
      for (Friend p:t.ObjectOnTrack) {
        planets1.add(p);
      }
    }
    for (SocialTrack t:track2) {
      for (Friend p:t.ObjectOnTrack) {
        planets2.add(p);
      }
    }
    Difference<Friend> difference = new Difference<Friend>(trackNum, objectNum, planets1, planets2);
    return difference;
  }

}
