package StellarTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import APIs.Difference;
import APIs.StellarAPI;
import applications.ConcreteCircularOrbit;
import constractFromTxt.ConstractAStellarSystem;
import physicalObject.PlanetWithSatellite;


public class StellarAPITest {

	ConcreteCircularOrbit system;
	StellarAPI caculate=new StellarAPI();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetObjectDistributionEntropy() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		assertEquals(true,caculate.getObjectDistributionEntropy(system)==2.0794415416798357);
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem_Medium.txt");
		assertEquals(true,caculate.getObjectDistributionEntropy(system)==7.323327759112637);
	}

	@Test
	public void testGetPhysicalDistance() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		PlanetWithSatellite p1=system.getTrack().get(0).ObjectOnTrack.get(0);
		PlanetWithSatellite p2=system.getTrack().get(1).ObjectOnTrack.get(0);
		PlanetWithSatellite p3=system.getTrack().get(2).ObjectOnTrack.get(0);
		assertEquals(1377773,(int)caculate.getPhysicalDistance(system, p1, p2));
		assertEquals(14760150,(int)caculate.getPhysicalDistance(system, p1, p3));
		assertEquals(13501067,(int)caculate.getPhysicalDistance(system, p3, p2));
	}

	@Test
	public void testGetDifference() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		ConcreteCircularOrbit system1=ConstractAStellarSystem.Constract("src/TXT/StellarSystem_Medium.txt");
		Difference<PlanetWithSatellite> D=caculate.getDifference(system, system1);
		assertEquals(1606,D.getTrackNum());
		List<Integer> objectNum=D.getObjectNum();
		assertEquals(0,(int)(objectNum.get(0)));
		List<PlanetWithSatellite> list=D.getObject1();
		assertEquals("Neptune",list.get(0).getPlanet().getName().toString());
	}

}
