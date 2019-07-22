package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.ConcreteCircularOrbit;
import centralObject.Stellar;
import logs.Mylog;
import otherDirectory.label;
import otherDirectory.number;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;

public class NioStellarIO implements StellarIO{

	public static void main(String[] args) throws InterruptedException {
		NioStellarIO test = new NioStellarIO();
		try {
			ConcreteCircularOrbit system = test.constract("src/TXT/BigStellarSystem.txt");
			System.out.println("done");
			Thread.sleep(1000000);
			test.output(system);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ConcreteCircularOrbit constract(String filePath) throws IOException {
		Mylog.logger.info("[Stellar] constract a StellarSystem from "+filePath);
		  List<PlanetTrack> list = new ArrayList<PlanetTrack>();
		  Stellar newSte = null;
		  Map<number,PlanetTrack> tracks = new HashMap<number,PlanetTrack>();
		  
	      //指定读取文件所在位置
	      File file = new File(filePath);
	      FileChannel fileChannel = new RandomAccessFile(file,"r").getChannel();
	      ByteBuffer byteBuffer = ByteBuffer.allocate(10);
	      //使用temp字节数组用于存储不完整的行的内容
	      byte[] temp = new byte[0];
	      while(fileChannel.read(byteBuffer) != -1) {
	          byte[] bs = new byte[byteBuffer.position()];
	          byteBuffer.flip();
	          byteBuffer.get(bs);
	          byteBuffer.clear();
	          int startNum=0;
	          //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
	          boolean isNewLine = false;
	          for(int i=0;i < bs.length;i++) {
	              if(bs[i] == 10) {
	                  isNewLine = true;
	                  startNum = i;
	              }
	          }

	          if(isNewLine) {
	              //如果出现了换行符，将temp中的内容与换行符之前的内容拼接
	              byte[] toTemp = new byte[temp.length+startNum];
	              System.arraycopy(temp,0,toTemp,0,temp.length);
	              System.arraycopy(bs,0,toTemp,temp.length,startNum);
	              
	              String planet=new String(toTemp);
	              
	              String planetregex = "Planet ::= <([A-Za-z0-9 ]+),([A-Za-z ]+),([A-Za-z ]+),"
	                      + "([e0-9. ]+),([e0-9. ]+),([e0-9. ]+),([CW ]+),([0-9. ]+)>";
	              Pattern pattern = Pattern.compile(planetregex);
	              Matcher matcher = pattern.matcher(planet);
	              if (matcher.find()) {
	                  Planet p1 = new Planet(new label(matcher.group(1).trim()),
	                      new label(matcher.group(2).trim()),new label(matcher.group(3).trim()),
	                      new number(matcher.group(4).trim()),new number(matcher.group(5).trim()),
	                      new number(matcher.group(6).trim()),
	                      matcher.group(7).trim(),Double.parseDouble(matcher.group(8).trim()));
	                  
	                  number radius = new number(matcher.group(5).trim());
	                  if(!tracks.containsKey(radius)) {
	                	  PlanetTrack t1 = new PlanetTrack(radius);
	                      t1.ObjectOnTrack.add(new PlanetWithSatellite(p1));
	                      tracks.put(radius, t1);
	                  }
	                  else {
	                	  PlanetTrack t1 = tracks.get(radius);
	                	  t1.ObjectOnTrack.add(new PlanetWithSatellite(p1));
	                	  tracks.put(radius, t1);
	                  }
	                  
	                  
	                } else {
	                	String regex = "Stellar ::= <([A-Za-z ]+),([A-Za-z0-9. ]+),([A-Za-z0-9. ]+)>";
	                    pattern = Pattern.compile(regex); 
	                    matcher = pattern.matcher(planet);
	                    if (matcher.find()) {
	                      newSte = new Stellar(new label(matcher.group(1).trim()),
	                    new number(matcher.group(2).trim()),new number(matcher.group(3).trim()));
	                    }
	                }
	              
	              //将换行符之后的内容(去除换行符)存到temp中
	              temp = new byte[bs.length-startNum-1];
	              System.arraycopy(bs,startNum+1,temp,0,bs.length-startNum-1);
	              //使用return即为单行读取，不打开即为全部读取
	              //return;
	          } else {
	              //如果没出现换行符，则将内容保存到temp中
	              byte[] toTemp = new byte[temp.length + bs.length];
	              System.arraycopy(temp, 0, toTemp, 0, temp.length);
	              System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
	              temp = toTemp;
	          }

	      }
	      if(temp.length>0) {
	          System.out.println(new String(temp));
	      }
	      
	      for(Map.Entry<number, PlanetTrack> e:tracks.entrySet()) {
	    	  list.add(e.getValue());
	      }
	      Collections.sort(list, new Comparator<PlanetTrack>() {

	          @Override
	      public int compare(PlanetTrack o1, PlanetTrack o2) {
	            BigDecimal r1 = o1.r.value;
	            BigDecimal r2 = o2.r.value;
	            if (r1.compareTo(r2) == 1) {
	              return 1;
	            }
	            if (r1.compareTo(r2) == -1) {
	              return -1;
	            }
	            return 0;
	          }
	        });
	      
	      ConcreteCircularOrbit target = new ConcreteCircularOrbit(list,newSte);
	      return target;
	}
	

	@Override
	public void output(ConcreteCircularOrbit system) throws IOException {
		File file = new File("src/TXT/StellarOutput.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel channel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Stellar stellar=system.getStellar();
        String central = "Stellar ::= <" + stellar.getName().toString() + ","+
   			 stellar.getRadius().toString() + ","+stellar.getWeight().toString() + ">\n";
        buffer.put(central.getBytes());
        buffer.flip();
        channel.write(buffer);
        
        for (PlanetTrack t:system.getTrack()) {
			 for (PlanetWithSatellite p:t.ObjectOnTrack) {
				 buffer =ByteBuffer.allocate(1024);
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
				 buffer.put(content.getBytes());
				 buffer.flip();
				 channel.write(buffer);
			 }
		 }
        channel.close();
        outputStream.close();
	}

}
