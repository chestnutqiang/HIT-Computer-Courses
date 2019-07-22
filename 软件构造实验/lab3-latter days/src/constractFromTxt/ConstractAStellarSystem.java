package constractFromTxt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.ConcreteCircularOrbit;
import centralObject.Stellar;
import otherDirectory.label;
import otherDirectory.number;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;

public class ConstractAStellarSystem {

	/*
	 * AF
	 * 根据读入的文件来建立行星系统
	 */

	public static ConcreteCircularOrbit Constract(String filePath) throws IOException{ 
		List<PlanetTrack> list=new ArrayList<PlanetTrack>();
		Stellar newSte=null;
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        String regex="Stellar ::= <([A-Za-z]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+)>";
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(temp);
        if(matcher.find()) {
        	newSte=new Stellar(new label(matcher.group(1)),new number(matcher.group(2)),new number(matcher.group(3)));
        }
        else
        	System.out.println("not match");
        while(temp!=null) {
        	temp=br.readLine();
        	if(temp==null)
        		break;
        	String planetregex="Planet ::= <([A-Za-z0-9.]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+),([CW]+),([0-9.]+)>";
        	pattern=Pattern.compile(planetregex);
        	matcher=pattern.matcher(temp);
        	if(matcher.find()) {
        		Planet p1=new Planet(new label(matcher.group(1)),new label(matcher.group(2)),new label(matcher.group(3)),new number(matcher.group(4)),new number(matcher.group(5)),new number(matcher.group(6)),matcher.group(7),Double.parseDouble(matcher.group(8)));
            	PlanetTrack t1=new PlanetTrack(new number(matcher.group(5)));
            	if(!list.contains(t1)) {
            		t1.ObjectOnTrack.add(new PlanetWithSatellite(p1));
                	list.add(t1);
            	}
            	else {
            		for(PlanetTrack t:list) {
            			if(t.equals(t1)) {
            				t.ObjectOnTrack.add(new PlanetWithSatellite(p1));
            				break;
            			}
            		}
            	}
        	}
        }
        br.close();
        Collections.sort(list, new Comparator<PlanetTrack>() {//对轨道的大小进行排序

			@Override
			public int compare(PlanetTrack o1, PlanetTrack o2) {
				BigDecimal r1=o1.r.value,r2=o2.r.value;
				if(r1.compareTo(r2)==1)
					return 1;
				if(r1.compareTo(r2)==-1)
					return -1;
				return 0;
			}
        	
        	
        });
        
		ConcreteCircularOrbit target=new ConcreteCircularOrbit(list,newSte);
        return target;
	}

}
