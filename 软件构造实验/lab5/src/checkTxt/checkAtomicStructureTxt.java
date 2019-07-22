package checkTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.Nucleus;
import logs.Mylog;
import myExceptions.IncorrectDependencyException;
import myExceptions.SyntaxException;
import physicalObject.Electron;
import track.ElectronTrack;

public class checkAtomicStructureTxt {

  /*public static void main(String[] args) {
    try {
      check("src/TXT/wrongAtomicSynax.txt");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IncorrectDependencyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }*/
  
  /** 
   * 检查文件是否含有错误，如果文件含有错误将错误全部打印出来并抛出异常.
   * 
   * @param filePath 文件路径
   */
  public static void check(String filePath) throws IOException, SyntaxException,
      IncorrectDependencyException {
    File file = new File(filePath);
    int tag = 0;/*
    tag=0:没有异常
    tag=1:ParameterNumberExcepton
    tag=3:SyntaxException
    tag=4:IncorrectDependencyException
    */
    
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    do {
      temp = br.readLine();
    } while (temp == null || temp.equals(""));
    String regex = "ElementName ::=([A-Za-z ]+)";
    Pattern pattern = Pattern.compile(regex); 
    Matcher matcher = pattern.matcher(temp);
    if (!matcher.find()) {
      Mylog.logger.error("[Electron] " + "syntax exception at " + temp);
      System.out.println("syntax exception at " + temp);
      tag = 3;
    } else {
      do {
        temp = br.readLine();
      } while (temp == null || temp.equals(""));
      regex = "NumberOfTracks ::=([0-9 ]+)";
      pattern = Pattern.compile(regex);
      matcher = pattern.matcher(temp);
      int n = 0;
      if (!matcher.matches()) {
        Mylog.logger.error("[Electron] " + "syntax exception at " + temp); 
        System.out.println("syntax exception at " + temp);
        tag = 3;
      } else {
        n = Integer.parseInt(matcher.group(1).trim());
        do {
          temp = br.readLine();
        } while (temp == null || temp.equals(""));
        int num = 1;
        char[] ch = temp.toCharArray();
        for (char ch1:ch) {
          if (ch1 == ';') {
            num++;
          }
        }
          
        if (num != n) {
          System.out.println("incorrect dependency exception at " + temp + " ,expected " + n
              + " parameters,but is " + num + " parameters");
          Mylog.logger.error("[Electron] " + "incorrect dependency exception at " + temp 
              + " ,expected " + n + " parameters,but is " + num + " parameters");
          tag = 4;
        }
        regex = "NumberOfElectron ::= (([0-9 ]+)/([0-9 ]+);)+([0-9 ]+)/([0-9 ]+)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(temp);
        
        if (!matcher.matches()) {
          Mylog.logger.error("[Electron] " + "syntax exception at " + temp);
          System.out.println("syntax exception at " + temp);
          tag = 3;
        }
      }
    }
      
    
    if (tag == 3) {
      throw new SyntaxException();
    } else if (tag == 4) {
      throw new IncorrectDependencyException();
    }
    br.close();
  }

}