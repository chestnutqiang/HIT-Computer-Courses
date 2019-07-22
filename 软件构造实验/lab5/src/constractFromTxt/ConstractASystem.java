package constractFromTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.ConcreteCircularOrbitE;
import centralObject.Nucleus;
import checkTxt.checkAtomicStructureTxt;
import logs.Mylog;
import myExceptions.IncorrectDependencyException;
import myExceptions.SyntaxException;
import physicalObject.Electron;
import track.ElectronTrack;


/*
 * RI：filepath是读入文件的路径
 * 
 * AF：
 * 根据读入文件来构建原子系统
 */
public class ConstractASystem {

  public static void main(String[] args) throws IOException {
    ConcreteCircularOrbitE system = ConstractASystem.Constract("src/TXT/AtomicStructure.txt");
  }
  
  /**
   * 根据文件构造系统.
   * @param filePath 文件路径
   * @return 构造的系统
   * @throws IOException 可能出现IO异常
   */
  public static ConcreteCircularOrbitE Constract(String filePath) throws IOException{
    try {
      checkAtomicStructureTxt.check(filePath);
    } catch (SyntaxException | IncorrectDependencyException e1) {
      System.out.println("build fail\n please input another file path");
      Scanner in = new Scanner(System.in);
      String file = in.nextLine();
      return Constract(file);
    }
    Mylog.logger.info("[Electron] " + "constract a nuclues system from " + filePath);
    Nucleus nucleus = null;
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    temp = br.readLine();
    String regex = "ElementName ::=([A-Za-z ]+)";
    Pattern pattern = Pattern.compile(regex); 
    Matcher matcher = pattern.matcher(temp);
    if (matcher.find()) {
      nucleus = new Nucleus(matcher.group(1).trim());
    }
    temp = br.readLine();
    regex = "NumberOfTracks ::=([0-9 ]+)";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(temp);
    int n = 0;
    int j;
    if (matcher.matches()) {
      n = Integer.parseInt(matcher.group(1).trim());
    }
     
    do {
      temp = br.readLine();
    } while (temp == null || temp.equals(""));
    
    int i;
    temp = temp.substring(temp.indexOf("=") + 1);
    String[] temp1 = temp.split(";");
    List<ElectronTrack> tracks = new ArrayList<ElectronTrack>();
    for (i = 1;i <= n;i++) {
      int index = temp1[i - 1].indexOf(i + "/");
      ElectronTrack t = new ElectronTrack(i);
      int electronnum = (Integer.parseInt(temp1[i - 1].substring(index + 2).trim()));
      for (j = 1;j <= electronnum;j++) {
        Electron e = new Electron(i);
        t.ObjectOnTrack.add(e);
      }
      tracks.add(t);
      //System.out.println(tracks.size());
    }
    br.close();
    ConcreteCircularOrbitE system = new ConcreteCircularOrbitE(nucleus,tracks);
    return system;
  }
  

}