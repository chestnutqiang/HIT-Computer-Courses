package AtomStructure;

import Iterator.AtomIterator;
import applications.ConcreteCircularOrbitE;
import constractFromTxt.ConstractASystem;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import track.ElectronTrack;


public class AtomIteratorTest {

  ConcreteCircularOrbitE system;
  
  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testHasNext() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    AtomIterator it = new AtomIterator(system);
    assertEquals(true,it.hasNext());
    while (it.hasNext()) {
      it.next();
    }
      
    assertEquals(false,it.hasNext());
  }

  @Test
  public void testNext() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    AtomIterator it = new AtomIterator(system);
    ElectronTrack t = it.next();
    assertEquals(2,t.ObjectOnTrack.size());
    t = it.next();
    assertEquals(8,t.ObjectOnTrack.size());
  }

}
