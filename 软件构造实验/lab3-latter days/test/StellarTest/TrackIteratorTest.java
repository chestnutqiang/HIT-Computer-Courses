package StellarTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import Iterator.TrackIterator;
import applications.ConcreteCircularOrbit;
import constractFromTxt.ConstractAStellarSystem;
import otherDirectory.label;
import otherDirectory.number;
import track.PlanetTrack;

public class TrackIteratorTest {

	ConcreteCircularOrbit system;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTrackIterator() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		
	}

	@Test
	public void testHasNext() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		TrackIterator it=new TrackIterator(system);
		assertEquals(true, it.hasNext());
		it.next();
		assertEquals(true, it.hasNext());
		for(int i=0;i<7;i++)
			it.next();
		assertEquals(false,it.hasNext());
	}

	@Test
	public void testNext() throws IOException {
		system=ConstractAStellarSystem.Constract("src/TXT/StellarSystem.txt");
		TrackIterator it=new TrackIterator(system);
		it.next();
		PlanetTrack t=it.next(),t1=it.next();
		assertEquals(new number("1.49e6"),t.r);
		assertEquals(new label("Saturn"),t.ObjectOnTrack.get(0).getPlanet().getName());
		assertEquals(new label("Mercury"),t1.ObjectOnTrack.get(0).getPlanet().getName());
	}

}
