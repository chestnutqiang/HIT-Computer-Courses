package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import applications.SocialNetworkCircle;
import centralObject.CentralUser;
import constractFromTxt.ConstractSocialNetwork;
import physicalObject.Friend;
import relations.SocialTie;

public class OutputNetwork {

	public static void main(String[] args) throws IOException {
		SocialNetworkCircle system =new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
		output(system);
	}
	
	public static void output(SocialNetworkCircle system) throws IOException {
		
		File file = new File("src/TXT/SocialNetworkOutput.txt");
		
		CentralUser user = system.getUser();

		 if (!file.exists()) {
		   file.createNewFile();
		 }
		 
		 FileWriter fw = new FileWriter("src/TXT/SocialNetworkOutput.txt");
		 BufferedWriter bw = new BufferedWriter(fw);
		 
		 
		 String central = "CentralUser ::= <" + user.getName().toString() + ","
		     + user.getAge() + "," +user.getSex()+">\n";
		 bw.write(central);
		 
		 for (Friend f:system.getFriends()) {
			 String friend = "Friend ::= <" + f.getname().toString()
			     + "," + f.getAge() + "," + f.getSex() + ">\n";
			 if (!f.getname().equals(user.getName())) {
			   bw.write(friend);
			 }
			 
		 }
		 
		 for (SocialTie t:system.getRelations()) {
			 String relation = "SocialTie ::= <" + t.object1.getname().toString()
			     + "," + t.object2.getname().toString() + "," + t.getIntimacy()
			     + ">\n";
			 bw.write(relation);
		 }
		 
		 bw.close();
		 
		 System.out.println("Done");
	}

}
