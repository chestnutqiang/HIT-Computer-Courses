package applications;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import centralObject.CentralUser;
import circularOrbit.CircularOrbit;
import constractFromTxt.ConstractSocialNetwork;
import logs.Mylog;
import physicalObject.Friend;
import relations.SocialTie;
import track.SocialTrack;
import track.Track;

public class SocialNetworkCircle implements CircularOrbit<CentralUser, Friend> ,Cloneable {
  
  
  //central,user is both the centraluser;
  private CentralUser user;
  private Friend central;
  private List<Friend> friends  =  new ArrayList<Friend>();//为了方便计算轨道，friends加入了user本身
  private List<SocialTie> relations  =  new ArrayList<SocialTie>();
  private List<SocialTrack> tracks  =  new ArrayList<SocialTrack>();
  final Integer infinity = 100000;
  

  /*
   * RI：
   * uesr：中心使用者，非空
   * friends：使用者所有的朋友
   * relations：这个社交网络所有的关系
   * tracks：这个社交网站所有的轨道
   * infinity：100000，作为无穷大使用
   * 
   * AF
   *  表示一个社交网络
   * 
   * safe from rep exposure：
   *  All fields are private;
   * infinity是int型，所以 是immutable类型的
   * friends,tracks是mutable的，所以getFriends(),getTracks()进行了防御式拷贝
   */
  
  
  void checkRep() {
    assert user != null : "uesr is null";
    for (SocialTrack t:tracks) {
      assert t.ObjectOnTrack.size() > 0 : "this track has no object on it  " + t.r;
    }
      
    assert tracks.size() <= friends.size();
  }
  
  public SocialNetworkCircle clone() {
	  SocialNetworkCircle system = new SocialNetworkCircle(getFriends(),getRelations(),getUser(),central);
	  return system;
  }
  
  /**
   * 构造方法.
   * @param friends 朋友的列表
   * @param relations 关系的列表
   * @param user 中心使用者
   * @param central 中心使用者
   */
  public SocialNetworkCircle(List<Friend> friends,List<SocialTie> relations,
      CentralUser user,Friend central) {
    this.friends = friends;
    this.relations = relations;
    this.user = user;
    this.central = central;
    checkRep();
  }
  
  public CentralUser getUser() {
    return this.user;
  }
  
  public List<SocialTie> getRelations() {
    List<SocialTie> relation=new ArrayList<SocialTie>();
    relation.addAll(relations);
    return relation;
  }
  
  /**
   * 返回这个人朋友列表的拷贝.
   * @return 这个人朋友列表的拷贝
   */
  public List<Friend> getFriends() {
    List<Friend> ans = new ArrayList<Friend>();
    ans.addAll(friends);
    assert ans.equals(friends);
    return ans;
  }
  
  /**
   * 这个人轨道列表的拷贝.
   * @return 这个人轨道列表的拷贝
   */
  public List<SocialTrack> getTracks() {
    List<SocialTrack> ans = new ArrayList<SocialTrack>();
    ans.addAll(tracks);
    assert ans.equals(tracks);
    return ans;
  }
  
  /**
   * 设置这个人的轨道列表.
   * @param tracks 这个人的轨道列表
   */
  public void setTracks(List<SocialTrack> tracks) {
    assert !tracks.isEmpty() : "this tracks is empty!";
    List<SocialTrack> tracks1  =  new ArrayList<SocialTrack>();
    tracks1.addAll(tracks);
    this.tracks  =  tracks1;
    assert this.tracks.equals(tracks);
  }

  
  @Override
  public void addNewTrack(Track t) {
    try {
      throw new Exception("no implement");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  //没有继承
  @Override
  public boolean removeTrack(int x) {
    try {
      throw new Exception("not implement");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /*
   * @param：c：中心使用者
   * @return：true如果加入成功
   */
  @Override
  public boolean addCentralPoint(CentralUser c) {
    if (user == null) {
      Mylog.logger.info("[SocialNetwork] " + "add a central user in social system");
      user = c;
      checkRep();
      return true;
    }
    return false;
  }

  //感觉在轨道上加入物体也没有意义，因为并没有与其他人产生社交联系，没有写这个功能的测试
  @Override
  public boolean addObjectOnTrack(Track t, Friend o) {
    SocialTrack t1 = tracks.get((int) t.r);
    if (!t1.ObjectOnTrack.contains(o)) {
      t1.ObjectOnTrack.add(o);
      o.setTrackNum((int) t.r);
      checkRep();
      return true;
    }
    return false;
  }

  /*
   * @param：o：一个朋友，要求出现在这个社交网络里面
   */
  @Override
  public void addRelationship(Friend o) {
    boolean flag = friends.contains(o);
    assert flag : "this friend is out of this system";
    
    Mylog.logger.info("[SocialNetwork] "
        + "add a ralation between central user and a friend in social system");
    if (!friends.contains(o)) {
      friends.add(o);
      o.setTrackNum(0);
      SocialTie t = new SocialTie(central,o);
      relations.add(t);
      tracks.get(0).ObjectOnTrack.add(o);
      checkRep();
    }
  }
  
  /**
   * 跳转轨道.
   * @param f 社交网络里面的一个人，t 要跳转到的轨道，感觉没有任何意义，因为一旦这个人的朋友关系确定，那么这个人的轨道是确定的，所有没有怎么去写这个方法
   */
  public void transit(Friend f, SocialTrack t) {
    int trackNum = f.getTrackNum();
    tracks.get(trackNum).ObjectOnTrack.remove(f);
    t.ObjectOnTrack.add(f);
    checkRep();
  }

  /*
   * @param：f1，f2：社交网络里面的两个人
   * 为他们添加社交关系
   */
  @Override
  public void addRelatioOfPhyO(Friend f1, Friend f2) {
    boolean flag1 = friends.contains(f1);
    boolean flag2 = friends.contains(f2);
    assert flag1 && flag2 : "friend is out of this system";
    
    Mylog.logger.info("[SocialNetwork] " + "add a relation between two friends in social system");
    
    SocialTie st1 = new SocialTie(f1, f2);
    relations.add(st1);
    f1.addFriend(f2);;
    f2.addFriend(f1);;
    int count = 0;
    tracks = new ArrayList<SocialTrack>();
    List<Friend> list1 = new ArrayList<Friend>();
    List<Friend> added = new ArrayList<Friend>();
    list1.add(central);
    boolean flag = true;
    while (flag) {
      flag = false;
      List<Friend> list2 = new ArrayList<Friend>();
      SocialTrack track = new SocialTrack(count);
      for (SocialTie st:relations) {
        if (!added.contains(st.object2) && list1.contains(st.object1)) {
          st.object2.setTrackNum(count);
          track.ObjectOnTrack.add(st.object2);
          list2.add(st.object2);
          added.add(st.object2);
          flag = true;
        } else if (!added.contains(st.object1) && list1.contains(st.object2)) {
          st.object1.setTrackNum(count);
          track.ObjectOnTrack.add(st.object1);
          list2.add(st.object1);
          added.add(st.object1);
          flag = true;
        }
      }
      if (!track.ObjectOnTrack.isEmpty()) {
        tracks.add(track);
      }
        
      list1.clear();
      list1.addAll(list2);
      count++;
    }
    checkRep();
  }
  
  /**
   * 删除f1,f2社交关系.
   * @param f1 f2 社交网络里面的两个人
   * 
   */
  public void deleteRelationOfFriend(Friend f1,Friend f2) {
    boolean flag1 = friends.contains(f1);
    boolean flag2 = friends.contains(f2);
    assert flag1 && flag2 : "friend is out of this system";
    
    Mylog.logger.info("[SocialNetwork] "
        + "delete a relation between two friends in social system");
    
    SocialTie st1 = new SocialTie(f1, f2);
    relations.remove(st1);
    f1.removeFriend(f2);;
    f2.removeFriend(f1);;
    int count = 0;
    tracks = new ArrayList<SocialTrack>();
    List<Friend> list1 = new ArrayList<Friend>();
    List<Friend> added = new ArrayList<Friend>();
    list1.add(central);
    boolean flag = true;
    while (flag) {
      flag = false;
      List<Friend> list2 = new ArrayList<Friend>();
      SocialTrack track = new SocialTrack(count);
      for (SocialTie st:relations) {
        if (!added.contains(st.object2) && list1.contains(st.object1)) {
          st.object2.setTrackNum(count);
          track.ObjectOnTrack.add(st.object2);
          list2.add(st.object2);
          added.add(st.object2);
          flag = true;
        } else if (!added.contains(st.object1) && list1.contains(st.object2)) {
          st.object1.setTrackNum(count);
          track.ObjectOnTrack.add(st.object1);
          list2.add(st.object1);
          added.add(st.object1);
          flag = true;
        }
      }
      if (!track.ObjectOnTrack.isEmpty()) {
        tracks.add(track);
      }
        
      list1.clear();
      list1.addAll(list2);
      count++;
    }
    checkRep();
  }
  /**
   * 通过这个人能达到的信息扩散度.
   * @param f 第一轨道上的某个朋友
   * @return 通过这个人能达到的信息扩散度
   */
  //计算信息扩散度，当两个人之间的朋友度大于0.8认为两个人可以通过社交关系认识
  //两个人之间的朋友度定义为两个人之间通过朋友关系联系起来的路径的亲密度之积的最大值
  //通过第一轨道上的某个朋友能够认识的人不包括中心使用者自己
  
  public int spreadFriend(Friend f) {
    assert tracks.get(0).ObjectOnTrack.contains(f) : "this friend is not at the first track";
    
    Mylog.logger.info("[SocialNetwork] " + "caculate the spread of a friend in social system");
    List<Friend> list1  =  new ArrayList<Friend>();
    int count = 0;
    int i;
    int len = f.MyFriend().size();
    for (i = 0;i < len;i++) {
      Friend friend = f.MyFriend().get(i);
      double infinity = f.getIntimacy().get(i);
      count += spread(friend, list1, infinity);
    }
    assert count >= 0;
    return count;
  }
  
  /**
   * 计算第一轨道上的某个朋友的扩散度.
   * @param f 要计算扩散度的朋友
   * @param list1 朋友列表
   * @param infinity 扩散度
   * @return
   */
  public int spread(Friend f,List<Friend> list1,double infinity) {
    if (f.getname().equals(user.getName())) {
      return 0;
    }
    int ans = 0;
    int i;
    int len = f.MyFriend().size();
    for (i = 0;i < len;i++) {
      Friend friend = f.MyFriend().get(i);
      if (!list1.contains(friend) && infinity * f.getIntimacy().get(i) > 0.8) {
        list1.add(friend);
        ans++;
        ans += spread(friend, list1, infinity * f.getIntimacy().get(i));
      }
    }
    return ans;
  }
  
  /**
   * 计算两个朋友之间的逻辑距离.
   * @param f1 f2 社交网络里面的两个人
   * @return 计算两个朋友之间的逻辑距离
   */
  public int distance(Friend f1,Friend f2) {
    boolean flag1 = friends.contains(f1);
    boolean flag2 = friends.contains(f2);
    assert flag1 && flag2 : "friend is out of this system";
    
    Mylog.logger.info("[SocialNetwork] "
        + "caculate the logical distance between two friends in social system");
    
    int ans = 100000;
    Set<Friend> set = new HashSet<Friend>();
    ans = distance(f1, f2, set);
    assert ans > 0;
    return ans;
  }
  
  /**
   * 上面计算逻辑距离的辅助函数.
   * @param f1 朋友1
   * @param f2 朋友2
   * @param set 已经遍历过的朋友
   * @return
   */
  public int distance(Friend f1,Friend f2,Set<Friend> set) {
    int ans = 100000;
    if (f1.MyFriend().contains(f2)) {
      return 1;
    } else {
      set.add(f1);
      for (Friend f3:f1.MyFriend()) {
        Set<Friend> s1 = new HashSet<Friend>();
        s1.addAll(set);
        if (!set.contains(f3)) {
          ans = Math.min(ans, distance(f3, f2, s1) + 1);
        }
      }
    }
    return ans;
  }
}
