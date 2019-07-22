package SocialNetworkCircle;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import applications.SocialNetworkCircle;
import centralObject.CentralUser;
import constractFromTxt.ConstractSocialNetwork;
import otherDirectory.label;
import physicalObject.Friend;


public class SocialNetworkCircleTest {
  

  @Before
  public void setUp() throws Exception {
  }
  
  @Test
  public void testAddCentralPoint() throws IOException {
    SocialNetworkCircle network = 
        new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    CentralUser test = new CentralUser(new label("wxw"),19,'M');
    assertEquals(false,network.addCentralPoint(test));
  }


  @Test
  public void testAddRelationship() throws IOException {
    SocialNetworkCircle network = 
        new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    List<Friend> list = network.getFriends(); 
    Friend f1 = list.get(1);
    Friend f2 = list.get(4);
    Friend f3 = list.get(10);
    //System.out.println(f1.getname().toString());
    assertEquals(0,f1.getTrackNum());
    assertEquals("LisaWong",f1.getname().toString());
    assertEquals(0,f2.getTrackNum());
    assertEquals("DavidChen",f2.getname().toString());
    assertEquals(0,f2.getTrackNum());
  }

  @Test
  public void testAddRelatioOfPhyO() throws IOException {
    SocialNetworkCircle network = 
        new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    List<Friend> list = network.getFriends();
    Friend f1 = list.get(9);
    Friend f2 = list.get(4);
    Friend f3 = list.get(10);
    assertEquals(2,f1.getTrackNum());
    assertEquals(3,f3.getTrackNum());
    assertEquals(false,f1.MyFriend().contains(f2));
    network.addRelatioOfPhyO(f1, f2);
    assertEquals(1,f1.getTrackNum());
    assertEquals(2,f3.getTrackNum());
    assertEquals(true,f1.MyFriend().contains(f2));
    network.deleteRelationOfFriend(f1, f2);
  }

  @Test
  public void testDeleteRelationOfFriend() throws IOException {
    SocialNetworkCircle s = new 
        ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    List<Friend> list = s.getFriends();
    Friend f1 = list.get(4);
    Friend f2 = list.get(11);
    assertEquals(0,f1.getTrackNum());
    assertEquals(1,f2.getTrackNum());
    assertEquals(true,f1.MyFriend().contains(f2));
    s.deleteRelationOfFriend(f1, f2);
    assertEquals(0,f1.getTrackNum());
    assertEquals(3,f2.getTrackNum());
    assertEquals(false,f1.MyFriend().contains(f2));
    s.addRelatioOfPhyO(f1, f2);
  }

  @Test
  public void testSpreadFriend() throws IOException {
    SocialNetworkCircle network = 
        new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    List<Friend> list = network.getFriends();
    Friend f1 = list.get(4);
    Friend f2 = list.get(1);
    int people1 = network.spreadFriend(f1);
    assertEquals(3,people1);
    int people2 = network.spreadFriend(f2);
    assertEquals(0,people2);
  }

  @Test
  public void testdistance() throws IOException {
    SocialNetworkCircle network = 
        new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    List<Friend> list = network.getFriends();
    Friend f1 = list.get(4);
    Friend f2 = list.get(1);
    Friend f3 = list.get(8);
    assertEquals(2,network.distance(f1, f2));
    assertEquals(3,network.distance(f1, f3));
    assertEquals(3,network.distance(f2, f3));
  }

}
