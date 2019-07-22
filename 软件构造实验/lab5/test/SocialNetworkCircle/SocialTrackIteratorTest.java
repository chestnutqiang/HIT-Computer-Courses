package SocialNetworkCircle;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import Iterator.SocialTrackIterator;
import applications.SocialNetworkCircle;
import constractFromTxt.ConstractSocialNetwork;
import track.SocialTrack;

public class SocialTrackIteratorTest {

  SocialNetworkCircle system;
  
  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testHasNext() throws IOException {
    system = new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    SocialTrackIterator it = new SocialTrackIterator(system);
    assertEquals(true,it.hasNext());
    while (it.hasNext()) {
      it.next();
    }
      
    assertEquals(false,it.hasNext());
  }

  @Test
  public void testNext() throws IOException {
    system = new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
    SocialTrackIterator it = new SocialTrackIterator(system);
    SocialTrack t = it.next();
    assertEquals(4,t.ObjectOnTrack.size());
    t = it.next();
    assertEquals(3,t.ObjectOnTrack.size());
  }

}
