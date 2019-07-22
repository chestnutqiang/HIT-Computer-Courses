package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import applications.ConcreteCircularOrbitE;
import centralObject.Nucleus;
import centralObject.Stellar;
import constractFromTxt.ConstractASystem;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.ElectronTrack;
import track.PlanetTrack;

public class OutputAtom {

	public static void main(String[] args) throws IOException {
		ConcreteCircularOrbitE system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
		output(system);

	}
	
	public static void output(ConcreteCircularOrbitE system) {
		try {
		   	 Nucleus nucleus=system.getNucleus();
			 File file = new File("src/TXT/AtomOutput.txt");

			 if (!file.exists()) {
			   file.createNewFile();
			 }
			 
			 

			 FileWriter fw = new FileWriter(file.getAbsoluteFile());
			 BufferedWriter bw = new BufferedWriter(fw);
			 String central = "ElementName ::= " + nucleus.getName() + "\n";
			 bw.write(central);
			 
			 int num = system.getTracks().size();
			 String trackNum = "NumberOfTracks ::= " + num + "\n";
			 bw.write(trackNum);
			   
			 
			String content = "NumberOfElectron ::= ";
			 for (ElectronTrack t:system.getTracks()) {
				int radius = t.r;
				content += radius + "/" +t.ObjectOnTrack.size() + ";";
			 }
			 
			 content = content.substring(0, content.length() - 1);
			 
			 bw.write(content);
			   
			 bw.close();

			 System.out.println("Done");

			 } catch (IOException e) {
			   e.printStackTrace();
			 }
	}

}
