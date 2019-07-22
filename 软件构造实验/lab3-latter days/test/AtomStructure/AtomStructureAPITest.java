package AtomStructure;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import APIs.AtomStructureAPI;
import APIs.Difference;
import applications.ConcreteCircularOrbitE;
import constractFromTxt.ConstractASystem;
import physicalObject.Electron;
import track.ElectronTrack;


public class AtomStructureAPITest {

	ConcreteCircularOrbitE system;
	AtomStructureAPI caculate=new AtomStructureAPI();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetObjectDistributionEntropy() throws IOException {
		system=ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
		assertEquals(true,caculate.getObjectDistributionEntropy(system)==1.2681057323704885);
		system=ConstractASystem.Constract("src/TXT/AtomicStructure_Medium.txt");
		assertEquals(true,caculate.getObjectDistributionEntropy(system)==1.4238273157479775);
	}

	@Test
	public void testGetLogicalDistance() throws IOException {
		system=ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
		List<ElectronTrack> tracks=system.getTracks();
		Electron e1=tracks.get(0).ObjectOnTrack.get(1),e2=tracks.get(2).ObjectOnTrack.get(13);
		assertEquals(10000,caculate.getLogicalDistance(system, e1, e2));
		system.addRelatioOfPhyO(e1, e2);
		assertEquals(1,caculate.getLogicalDistance(system, e1, e2));
		Electron e3=tracks.get(3).ObjectOnTrack.get(1);
		system.addRelatioOfPhyO(e2, e3);
		assertEquals(2,caculate.getLogicalDistance(system, e1, e3));
	}

	@Test
	public void testGetDifference() throws IOException {
		system=ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
		ConcreteCircularOrbitE system1=ConstractASystem.Constract("src/TXT/AtomicStructure_Medium.txt");
		Difference D=caculate.getDifference(system, system1);
		assertEquals(1,D.getTrackNum());
		List<Integer> num=D.getObjectNum();
		assertEquals(-22,(int)num.get(3));
		List<Electron> object=D.getObject1();
		assertEquals(true,object.contains(new Electron(3, 5)));
	}

}
