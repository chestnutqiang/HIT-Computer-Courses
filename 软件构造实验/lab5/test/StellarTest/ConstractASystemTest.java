package StellarTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import applications.ConcreteCircularOrbit;
import constractFromTxt.ConstractAStellarSystem;

public class ConstractASystemTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testConstract() throws IOException {
    ConcreteCircularOrbit system = ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    assertEquals("Sun",system.getStellar().getName().toString());
    assertEquals(8,system.getTrack().size());
  }
}
