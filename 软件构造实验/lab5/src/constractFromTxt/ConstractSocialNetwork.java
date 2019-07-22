package constractFromTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.CentralUser;
import checkTxt.checkSocialNetworkCircleTxt;
import logs.Mylog;
import myExceptions.IntimacyOutOfRangeException;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.sameTagException;
import applications.SocialNetworkCircle;
import otherDirectory.label;
import physicalObject.Friend;
import relations.SocialTie;
import track.SocialTrack;

//****大文件IO的部分写在IO包里面！！！！！！
public class ConstractSocialNetwork {
  private List<SocialTie> relations = new ArrayList<SocialTie>();
  private int friendNum;
  private List<Friend> friends = new ArrayList<Friend>();
  private CentralUser user;
  private Friend f1;
  private List<SocialTrack> tracks;
  private String filePath;
  /*
   * RI：
   * relations：朋友关系
   * friendnum：朋友数量
   * friends：存储所有朋友
   * user：centralUser类型，中心使用者
   * tracks：朋友关系
   * filepath:文件路径，要求为String类型
   * 
   * AF：
   *  根据读入文件来构建社交网络
   *  
   * safety from rep exposure:
   * All fields are private;
   * friendNum是int型，filePath是String型，所以 是immutable类型的
   */
  
  public ConstractSocialNetwork(String filePath) {
    this.filePath = filePath;
  }
  

  /*public static void main(String[] args) throws IOException {
    SocialNetworkCircle s = new 
        ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    for(Friend f:s.getFriends())
      System.out.println(f.getname()+","+f.getTrackNum());
  }*/
  
  private void constractUser() throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    temp = br.readLine();
    String regex = "CentralUser ::= <([A-Za-z]+),([0-9]+),([MF])>";
    Pattern pattern = Pattern.compile(regex); 
    Matcher matcher = pattern.matcher(temp);
    if (matcher.matches()) {
      label name = new label(matcher.group(1));
      int age = Integer.parseInt(matcher.group(2));
      char sex = matcher.group(3).charAt(0);
      user = new CentralUser(name,age,sex);
      f1 = new Friend(name, age, 0, sex);
      if (!friends.contains(f1)) {
        friends.add(f1);
      }
          
    }
    br.close();
        
  }
  
  private void constractFriend() throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    temp = br.readLine();
    String regex = "Friend ::= <(.*?),(.*?),(.*?)>";
    Pattern pattern = Pattern.compile(regex);
    while (temp != null) {
      Matcher matcher = pattern.matcher(temp);
      if (matcher.matches()) {
        label name = new label(matcher.group(1).trim());
        int age = Integer.parseInt(matcher.group(2).trim());
        char sex = matcher.group(3).trim().charAt(0);
        Friend f = new Friend(name, age, 1000, sex);
        if (!friends.contains(f)) {
          friends.add(f);
        } 
      }
      temp = br.readLine();
    }
    br.close();
  }
  
  private void constractRelations() throws IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    temp = br.readLine();
    String regex = "SocialTie ::= <(.*?),(.*?),(.*?)>";
    Pattern pattern = Pattern.compile(regex);
    while (temp != null) {
      Matcher matcher = pattern.matcher(temp);
      if (matcher.matches()) {
        String name1 = matcher.group(1).trim();
        String name2 = matcher.group(2).trim();
        double intimacy = Double.parseDouble(matcher.group(3));
        Friend f1 = new Friend(new label(name1), 0, 0, 'M');
        Friend f2 = new Friend(new label(name2), 0, 0, 'M');
        Friend f3 = friends.get(friends.indexOf(f1));
        Friend f4 = friends.get(friends.indexOf(f2));
        if (!f3.MyFriend().contains(f4)) {
          f3.addFriend(f4);;
          f3.addIntimacy(intimacy);
          f4.addFriend(f3);;
          f4.addIntimacy(intimacy);
          SocialTie st = new SocialTie(f3, f4);
          st.setIntimacy(intimacy);
          relations.add(st);
        }  
      }
      temp = br.readLine();
    }
    br.close();
  }
  
  private void caculateTrack() throws IOException {
    constractUser();
    constractFriend();
    constractRelations();
    tracks = new ArrayList<SocialTrack>();
    int count = 0;
    int j;
    List<Friend> list1 = new ArrayList<Friend>();
    List<Friend> added = new ArrayList<Friend>();
    list1.add(f1);
    added.add(f1);
    boolean flag = true;
    while (flag) {
      flag = false;
      List<Friend> list2 = new ArrayList<Friend>();
      SocialTrack track = new SocialTrack(count);
      for (SocialTie st:relations) {
        if (!added.contains(st.object2) && list1.contains(st.object1)) {
          ((Friend)(st.object2)).setTrackNum(count);
          track.ObjectOnTrack.add((Friend) st.object2);
          list2.add((Friend) st.object2);
          added.add((Friend) st.object2);
          flag = true;
        } else if (!added.contains(st.object1) && list1.contains(st.object2)) {
          ((Friend)(st.object1)).setTrackNum(count);
          track.ObjectOnTrack.add((Friend) st.object1);
          list2.add((Friend) st.object1);
          added.add((Friend) st.object1);
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
  }
  
  /**
   * 根据外部文件构造社交轨道系统.
   * @return 根据外部文件构造的社交轨道系统
   */
  public SocialNetworkCircle Constract() {
    try {
      if (checkSocialNetworkCircleTxt.check(filePath)) {
        Mylog.logger.info("[SocialNetwork]" + "constract network from " + filePath);
        constractUser();
        constractFriend();
        constractRelations();
        SocialNetworkCircle s = new SocialNetworkCircle(friends,relations,user,f1);
        caculateTrack();
        s.setTracks(tracks);
        return s;
      }
    } catch (IOException | SyntaxException | ParameterNumberException | sameTagException
        | IntimacyOutOfRangeException e) {
      Scanner in = new Scanner(System.in);
      System.out.println("please input another file path ");
      String file = in.nextLine();
      this.filePath = file;
      return this.Constract();
    }
    return null;
    
  }
}
