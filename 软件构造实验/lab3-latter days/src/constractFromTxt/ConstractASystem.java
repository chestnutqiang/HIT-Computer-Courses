package constractFromTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.ConcreteCircularOrbitE;
import centralObject.Nucleus;
import physicalObject.Electron;
import track.ElectronTrack;
/*
 * RI：filepath是读入文件的路径
 * 
 * AF：
 * 根据读入文件来构建原子系统
 */
public class ConstractASystem {

	
	public static ConcreteCircularOrbitE Constract(String filePath) throws IOException{
		Nucleus nucleus=null;
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new FileReader(file));
		String temp=null;
		temp=br.readLine();
		String regex="ElementName ::= ([A-Za-z]+)";
	    Pattern pattern = Pattern.compile(regex); 
	    Matcher matcher = pattern.matcher(temp);
	    if(matcher.find()) {
	    	nucleus=new Nucleus(matcher.group(1));
	    }
		temp=br.readLine();
		regex="NumberOfTracks ::= ([0-9]+)";
		pattern=Pattern.compile(regex);
		matcher=pattern.matcher(temp);
		int n=0,i,j;
		if(matcher.matches())
			n=Integer.parseInt(matcher.group(1));
		//System.out.println("读入的n是"+n);
		temp=br.readLine();
		temp=temp.substring(temp.indexOf("=")+1);
		String temp1[]=temp.split(";");
		List<ElectronTrack> tracks=new ArrayList<ElectronTrack>();
		for(i=1;i<=n;i++) {
			int index=temp1[i-1].indexOf(i+"/");
			ElectronTrack t=new ElectronTrack(i);
			int electronnum=(Integer.parseInt(temp1[i-1].substring(index+2)));
			for(j=1;j<=electronnum;j++) {
				Electron e=new Electron(i, j);
				t.ObjectOnTrack.add(e);
			}
			tracks.add(t);
			//System.out.println(tracks.size());
		}
		br.close();
		ConcreteCircularOrbitE system=new ConcreteCircularOrbitE(nucleus,tracks);
		return system;
	}

}
