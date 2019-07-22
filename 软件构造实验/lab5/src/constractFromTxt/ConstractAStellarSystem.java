package constractFromTxt;

import applications.ConcreteCircularOrbit;
import centralObject.Stellar;
import checkTxt.checkStellarSystemTxt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logs.Mylog;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.WrongAngleException;
import myExceptions.sameTagException;
import otherDirectory.label;
import otherDirectory.number;
import output.OutputStellar;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;

public class ConstractAStellarSystem {
  
  
  public static void main(String arg0s[]) throws Exception {
	  ConcreteCircularOrbit system = constract("src/TXT/BigStellarSystem.txt");
	  System.out.println(system.getStellar().getName().toString());
	  System.out.println(system.getTrack().size());
	  OutputStellar.WriterOutput(system);
  }

  static /*
   * AF
   * 根据读入的文件来建立行星系统
   */
      void checkRep(Stellar c) {
    assert c != null : "central stellar is null";
  }

  /**
   * 根据外部文件构造系统.
   * @param filePath 文件路径
   * @return 构造的系统
   * @throws IOException 抛出IO异常
   */
  public static ConcreteCircularOrbit constract(String filePath) throws IOException {
		Scanner in  = new Scanner(System.in);
	    Map<number,PlanetTrack> tracks = new HashMap<number,PlanetTrack>();
	    try {
	      checkStellarSystemTxt.check(filePath);
	    } catch (SyntaxException e) {
	      System.out.println("please choose another file path");
	      String file = in.nextLine();
	      return constract(file);
	    } catch (ParameterNumberException e) {
	      System.out.println("please choose another file path");
	      String file = in.nextLine();
	      return constract(file);
	    } catch (sameTagException e) {
	      System.out.println("please choose another file path");
	      String file = in.nextLine();
	      return constract(file);
	    } catch (WrongAngleException e) {
	      System.out.println("please choose another file path");
	      String file = in.nextLine();
	      return constract(file);
	    }
	    catch (IOException e) {
	      System.out.println("please choose another file path");
	      String file = in.nextLine();
	      return constract(file);
	    }
	    Mylog.logger.info("[Stellar] constract a StellarSystem from "+filePath);
	    List<PlanetTrack> list = new ArrayList<PlanetTrack>();
	    Stellar newSte = null;
	    File file = new File(filePath);
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String temp = null;
	    do {
	      temp = br.readLine();
	    } while (temp   ==  null ||  temp.equals(""));
	    String regex = "Stellar ::= <([A-Za-z ]+),([A-Za-z0-9. ]+),([A-Za-z0-9. ]+)>";
	    Pattern pattern = Pattern.compile(regex); 
	    Matcher matcher = pattern.matcher(temp);
	    if (matcher.find()) {
	      newSte = new Stellar(new label(matcher.group(1).trim()),
	    new number(matcher.group(2).trim()),new number(matcher.group(3).trim()));
	    } else {
	      System.out.println("not match");
	    }


	    while (temp != null) {
	      temp = br.readLine();
	      if (temp == null) {
	        break;
	      }
	      String planetregex = "Planet ::= <([A-Za-z0-9 ]+),([A-Za-z ]+),([A-Za-z ]+),"
	          + "([e0-9. ]+),([e0-9. ]+),([e0-9. ]+),([CW ]+),([0-9. ]+)>";
	      pattern = Pattern.compile(planetregex);
	      matcher = pattern.matcher(temp);
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
	      }
	    }
	    br.close();
	    
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
  
  
  
  
  public static ConcreteCircularOrbit NioConstract(String filePath) throws Exception {
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
              
              System.out.println(planet);
              
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
}