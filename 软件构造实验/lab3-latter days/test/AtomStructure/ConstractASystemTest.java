package AtomStructure;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import applications.ConcreteCircularOrbitE;
import constractFromTxt.ConstractASystem;
import physicalObject.Electron;

public class ConstractASystemTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConstract() throws IOException {
		ConcreteCircularOrbitE system=ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
		assertEquals("Rb", system.getNucleus().getName());
		assertEquals(5,system.getTrackNum());
		assertEquals(8,system.getTracks().get(1).ObjectOnTrack.size());
		Electron e=new Electron(1, 2);
		assertEquals(true,e.equals(system.getTracks().get(0).ObjectOnTrack.get(1)));
	}

}
