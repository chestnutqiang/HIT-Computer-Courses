package track;

import java.util.ArrayList;

import physicalObject.Friend;


public class SocialTrack extends Track<Integer, Friend> {
  
  /*
   * RI：
   * r:轨道编号0,1,2,3,4....
   * objectOnTrack:在这个轨道上的所有朋友
   * 
   * AF：
   *  描述一条社交轨道
   *  
   *  safety from rep exposure:
   * All fields are private;
   */
  public SocialTrack(int r) {
    this.r = r;
    ObjectOnTrack = new ArrayList<Friend>();
  }
}
