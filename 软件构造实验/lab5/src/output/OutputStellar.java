package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.experimental.theories.Theories;

import applications.ConcreteCircularOrbit;
import centralObject.Stellar;
import constractFromTxt.ConstractAStellarSystem;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;

public class OutputStellar {

	public static void main(String[] args) throws IOException {
		ConcreteCircularOrbit system=ConstractAStellarSystem.constract("src/TXT/StellarSystem.txt");
		Bufferoutput(system);
	}
	
	public static void Bufferoutput(ConcreteCircularOrbit system) {
		try {
		   	 Stellar stellar=system.getStellar();
			 File file = new File("src/TXT/StellarOutput.txt");

			 if (!file.exists()) {
			   file.createNewFile();
			 }

			 FileWriter fw = new FileWriter(file.getAbsoluteFile());
			 BufferedWriter bw = new BufferedWriter(fw);
			 String central = "Stellar ::= <" + stellar.getName().toString() + ","+
			 stellar.getRadius().toString() + ","+stellar.getWeight().toString() + ">\n";
			 bw.write(central);
			   
			 for (PlanetTrack t:system.getTrack()) {
				 for (PlanetWithSatellite p:t.ObjectOnTrack) {
					 Planet planet=p.planet;
					 String name = planet.getName().toString();
					 String state = planet.getState().toString();
					 String color = planet.getColor().toString();
					 String planetradius=planet.getPlanetRadius().toString();
					 String orbitalRadius = planet.getOrbitalRadius().toString();
					 String direction = planet.getDirection();
					 String speed = planet.getSpeed().toString();
					 String content="Planet ::= <" + name + "," + state + 
					 "," + color + "," + planetradius + "," + orbitalRadius + "," + speed + "," + 
					 direction + "," + planet.getAngle() + ">\n";
					 bw.write(content);
				 }
			 }
			   
			bw.close();

			System.out.println("Done");

			} catch (IOException e) {
			  e.printStackTrace();
			}
	}
	
	public static void WriterOutput(ConcreteCircularOrbit system) {
		try {
		   	 Stellar stellar=system.getStellar();
			 File file = new File("src/TXT/StellarOutput.txt");

			 if (!file.exists()) {
			   file.createNewFile();
			 }

			 FileWriter fw = new FileWriter(file.getAbsoluteFile());
			 String central = "Stellar ::= <" + stellar.getName().toString() + ","+
			 stellar.getRadius().toString() + ","+stellar.getWeight().toString() + ">\n";
			 fw.write(central);
			   
			 for (PlanetTrack t:system.getTrack()) {
				 for (PlanetWithSatellite p:t.ObjectOnTrack) {
					 Planet planet=p.planet;
					 String name = planet.getName().toString();
					 String state = planet.getState().toString();
					 String color = planet.getColor().toString();
					 String planetradius=planet.getPlanetRadius().toString();
					 String orbitalRadius = planet.getOrbitalRadius().toString();
					 String direction = planet.getDirection();
					 String speed = planet.getSpeed().toString();
					 String content="Planet ::= <" + name + "," + state + 
					 "," + color + "," + planetradius + "," + orbitalRadius + "," + speed + "," + 
					 direction + "," + planet.getAngle() + ">\n";
					 fw.write(content);
				 }
			 }
			   
			fw.close();

			System.out.println("Done");

			} catch (IOException e) {
			  e.printStackTrace();
			}
	}

}
