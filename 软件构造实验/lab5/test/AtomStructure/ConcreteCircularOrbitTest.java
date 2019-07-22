package AtomStructure;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import applications.ConcreteCircularOrbitE;
import centralObject.Nucleus;
import constractFromTxt.ConstractASystem;
import physicalObject.Electron;
import relations.ElectronRealtion;
import track.ElectronTrack;


public class ConcreteCircularOrbitTest {
  ConcreteCircularOrbitE system;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testAddNewTrack() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    assertEquals(5,system.getTrackNum());
    ElectronTrack t = new ElectronTrack(2);
    t.ObjectOnTrack.add(new Electron(2));
    system.addNewTrack(t);
    assertEquals(1,system.getTracks().indexOf(t));
  }

  @Test
  public void testRemoveTrack() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    ElectronTrack t = system.getTracks().get(2);
    assertEquals(true,system.getTracks().contains(t));
    system.removeTrack(3);
    assertEquals(false,system.getTracks().contains(t));
  }

  @Test
  public void testAddCentralPoint() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    Nucleus nucleus  = new Nucleus("Mg");
    assertEquals(true,system.addCentralPoint(nucleus));
  }

  @Test
  public void testAddObjectOnTrack() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    //System.out.println(system.NumberOfgetTracks());
    ElectronTrack t = new ElectronTrack(3);
    Electron e = new Electron(3);
    system.addObjectOnTrack(t, e);
    assertEquals(true,system.getTracks().get(2).ObjectOnTrack.contains(e));
  }

  @Test
  public void testAddRelationship() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    Electron e = new Electron(3);
    assertEquals(false,system.getRelations1().contains(e));
    system.addRelationship(e);
    assertEquals(true,system.getRelations1().contains(e));
  }

  @Test
  public void testAddRelatioOfPhyO() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    Electron e1 = new Electron(2),e2 = new Electron(3);
    ElectronRealtion r = new ElectronRealtion(e1, e2);
    assertEquals(false,system.getRelations2().contains(r));
    system.addRelatioOfPhyO(e1, e2);
    assertEquals(true,system.getRelations2().contains(r));
  }

  @Test
  public void testElectronTransmit() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    ElectronTrack t1 = new ElectronTrack(3),t2 = new ElectronTrack(2);
    Electron e = new Electron(3);
    assertEquals(false,system.getTracks().get(1).ObjectOnTrack.contains(e));
    system.electronTransmit(e, t1, t2);
    assertEquals(true,system.getTracks().get(1).ObjectOnTrack.contains(e));
  }

  @Test
  public void testGetback() throws IOException {
    system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
    ElectronTrack t1 = new ElectronTrack(3),t2 = new ElectronTrack(2);
    Electron e = new Electron(3);
    assertEquals(false,system.getTracks().get(1).ObjectOnTrack.contains(e));
    assertEquals(8,system.getTracks().get(1).ObjectOnTrack.size());
    system.electronTransmit(e, t1, t2);
    assertEquals(true,system.getTracks().get(1).ObjectOnTrack.contains(e));
    assertEquals(9,system.getTracks().get(1).ObjectOnTrack.size());
    assertEquals(true,system.getback(1));
    assertEquals(8,system.getTracks().get(1).ObjectOnTrack.size());
  }
}
