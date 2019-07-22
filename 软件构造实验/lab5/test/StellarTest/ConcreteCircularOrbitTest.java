package StellarTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import applications.ConcreteCircularOrbit;
import centralObject.Stellar;
import constractFromTxt.ConstractAStellarSystem;
import otherDirectory.label;
import otherDirectory.number;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import constractFromTxt.ConstractAStellarSystem;
import track.PlanetTrack;
import track.Track;

public class ConcreteCircularOrbitTest{
  ConcreteCircularOrbit system;
  
  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testEmpty() {
    system  =  new ConcreteCircularOrbit();
    assertEquals(null,system.getStellar());
    assertEquals(true,system.getTrack().isEmpty());
  }

  @Test
  public void testAddNewTrack() throws IOException {
    system  =  ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    Track temp  =  new PlanetTrack(new number("1333334"));
    Planet p  =  new Planet(new label("w"),new label("x"),new label("w"),new number("5"),
        new number("37"),new number("0"),"CW",0.1314);
    PlanetWithSatellite p1  =  new PlanetWithSatellite(p);
    temp.ObjectOnTrack.add(p1);
    system.addNewTrack(temp);
    assertEquals(9,system.getTrack().size());
    assertEquals(true,temp.equals(system.getTrack().get(8)));
  }

  @Test
  public void testRemoveTrack() throws IOException {
    system  =  ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    assertEquals(false,system.removeTrack(10));
    assertEquals(true,system.removeTrack(2));
  }

  @Test
  public void testAddCentralPoint() {
    system  =  new ConcreteCircularOrbit();
    assertEquals(null,system.getStellar());
    Stellar c  =  new Stellar(new label("three-body"),new number("123"),new number("345"));
    assertEquals(true,system.addCentralPoint(c));
    assertEquals(c,system.getStellar());
  }

  @Test
  public void testAddObjectOnTrack() throws IOException {
    Planet p  =  new Planet(new label("w"),new label("x"),new label("w"),new number("5"),
        new number("2"),new number("0"),"CW",0.1314);
    PlanetWithSatellite P  =  new PlanetWithSatellite(p);
    system  =  ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    assertEquals(1,system.getTrack().get(0).ObjectOnTrack.size());
    PlanetTrack t  =  system.getTrack().get(0);
    assertEquals(true,system.addObjectOnTrack(t, P));
    assertEquals(2,system.getTrack().get(0).ObjectOnTrack.size());
    assertEquals(P,system.getTrack().get(0).ObjectOnTrack.get(1));
  }

  @Test
  public void testAddRelationship() throws IOException {
    system  =  ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    PlanetWithSatellite p  =  system.getTrack().get(0).ObjectOnTrack.get(0);
    system.addRelationship(p);
    assertEquals(1,system.getRelation().size());
  }

  @Test
  public void testAddRelatioOfPhyO() {
    
  }
  
  @Test
  public void testcurrentAngle() throws IOException {
    system  =  ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
    BigDecimal t  =  new BigDecimal("50000000");
    List<PlanetTrack> list  =  system.getTrack();
    assertEquals(0,system.currentAngle(list.get(0).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("207")));
    assertEquals(0,system.currentAngle(list.get(1).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("88")));
    assertEquals(0,system.currentAngle(list.get(2).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("251")));
    assertEquals(0,system.currentAngle(list.get(3).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("9")));
    assertEquals(0,system.currentAngle(list.get(4).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("77")));
    assertEquals(0,system.currentAngle(list.get(5).ObjectOnTrack.get(0),t)
        .compareTo(new BigDecimal("109")));
  }
  
  @Test
  public void testDistanceOfTwoPlanet() {
    system  =  new ConcreteCircularOrbit();
    Planet p  =  new Planet(new label("w"),new label("x"),new label("w"),
        new number("5"),new number("37"),new number("0"),"CW",0.1314);
    Planet p1  =  new Planet(new label("w"),new label("x"),new label("w"),
        new number("7"),new number("46"),new number("9"),"CW",90.1314);
    PlanetWithSatellite P  =  new PlanetWithSatellite(p);
    PlanetWithSatellite P1  =  new PlanetWithSatellite(p1);
    BigDecimal d1  =  system.distanceOfTwoPlanet(P1, P);
    assertEquals(0,d1.compareTo(new BigDecimal(59)));
  }
}
