package checkTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logs.Mylog;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.WrongAngleException;
import myExceptions.sameTagException;
import otherDirectory.label;
import otherDirectory.number;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;

public class checkStellarSystemTxt {
  public static void main(String arg0s[]) throws SyntaxException, ParameterNumberException, sameTagException, WrongAngleException, IOException {
    check("src/TXT/BigStellarSystem.txt");
    System.out.println("done");
  }
  
  /**
   * 检查文件是否含有错误，如果文件含有错误将错误全部打印出来并抛出异常.
   * 
   * @param filePath 文件路径
   * 
   */
  public static 
      boolean check(String filePath) throws SyntaxException, ParameterNumberException, 
          sameTagException, WrongAngleException, IOException {
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = "";
    int tag = 0;/*
    tag=0:没有异常
    tag=1:ParameterNumberExcepton
    tag=2:sameTagException
    tag=3:SyntaxException
    tag=4:WrongAngleException
    
    */
    while (temp == null || temp.equals("")) {
      temp = br.readLine();
    }
    char[] stellar = temp.toCharArray();
    int num = 0;
    int len = temp.length();
    int i;
    for (i = 0;i < len;i++) {
      if (stellar[i] == ',') {
        num++;
      }
        
    }
    //恒星的参数数目不正确
    if (num != 2) {
      Mylog.logger.error("[Stellar] parameter exception at steller,excepted 3 parameter3,"
          + "but is " + (num + 1) + " parameters");
      System.out.println("parameter exception at steller,"
          + "excepted 3 parameter3,but is " + (num + 1) + " parameters");
      tag = 1;
    }
    String regex = "Stellar ::= <([A-Za-z]+),([A-Za-z0-9.]+),([A-Za-z0-9.]+)>";
    Pattern pattern = Pattern.compile(regex); 
    Matcher matcher = pattern.matcher(temp);
    //恒星的输入不符合语法规范
    if (!matcher.matches()) {
      tag = 3;
      Mylog.logger.error("[Stellar] Syntax Ecception at Stellar");
      System.out.println("Syntax Ecception at Stellar");
    }
    //行星
    Set<String> planetName = new HashSet<String>();
    while (temp != null) {
      temp = br.readLine();
      if (temp == null) {
        break;
      }
      if (temp.equals("")) {
        continue;
      }
      char[] planet = temp.toCharArray();
      num = 0;
      len = temp.length();
      for (i = 0;i < len;i++) {
        if (planet[i] == ',') {
          num++;
        }
          
      }
      //行星的参数数量不正确
      if (num != 7) {
        Mylog.logger.error("[Stellar] parameter exception at " + temp + ", except 8 parameters,"
            + "but is " + (num + 1) + " parameters");
        System.out.println("parameter exception at " + temp + ", "
            + "except 8 parameters,but is " + (num + 1) + " parameters");
        tag = 1;
      } else {
        String planetregex = "Planet ::= <([A-Za-z0-9 ]+),([A-Za-z ]+),([A-Za-z ]+),"
            + "([e0-9. ]+),([e0-9. ]+),([e0-9. ]+),([CW ]+),([0-9. ]+)>";
        pattern = Pattern.compile(planetregex);
        matcher = pattern.matcher(temp);
        if (matcher.find()) {
          if (!planetName.add(matcher.group(1))) {
            Mylog.logger.error("[Stellar] " + matcher.group(1) + "  this planet occurs twice!!!");
            System.out.println(matcher.group(1) + "  this planet occurs twice!!!");
            tag = 2;
          }
              
          if (!(matcher.group(2).equals("Solid") || matcher.group(2).equals("Liquid") 
              || matcher.group(2).equals("Gas"))) {
            Mylog.logger.error("[Stellar] syntaxException at " + temp 
                + " ,this planet is not Solid or Liquid or Gas");
            System.out.println("syntaxException at " + temp + " ,"
                + "this planet is not Solid or Liquid or Gas");
            tag = 3;
          }
              
          //超过10000的数字未使用科学计数法
          BigDecimal b1 = new BigDecimal(matcher.group(4).trim());
          BigDecimal b2 = new BigDecimal(matcher.group(5).trim());
          BigDecimal b3 = new BigDecimal(matcher.group(6).trim());
          BigDecimal tenThousand = new BigDecimal("10000");
          if (b1.compareTo(tenThousand) > 0 && matcher.group(4).indexOf('e') == -1) {
            Mylog.logger.error("[Stellar] " + temp + " :" + matcher.group(4) 
                + " is bigger than 10000 but don't use Scientific counting");
            System.out.println(temp + " :" + matcher.group(4) 
                + " is bigger than 10000 but don't use Scientific counting");
            tag = 3;
          }
          if (b2.compareTo(tenThousand) > 0 && matcher.group(5).indexOf('e') == -1) {
            Mylog.logger.error("[Stellar] " + temp + " :" + matcher.group(5) 
                + " is bigger than 10000 but don't use Scientific counting");
            System.out.println(temp + " :" + matcher.group(5) 
                + " is bigger than 10000 but don't use Scientific counting");
            tag = 3;
          }
            
          if (b3.compareTo(tenThousand) > 0 && matcher.group(6).indexOf('e') == -1) {
            Mylog.logger.error("[Stellar] " + temp + " :" 
                + matcher.group(6) + " is bigger than 10000 but don't use Scientific counting");
            System.out.println(temp + " :" + matcher.group(6) 
                + " is bigger than 10000 but don't use Scientific counting");
            tag = 3;
          }
          
          //检查角度是否在0-360度这个范围
          double angle = Double.parseDouble(matcher.group(8));
          if (angle < 0 || angle > 360) {
            Mylog.logger.error("[Stellar] wrong angle at " + temp);
            System.out.println("wrong angle at " + temp);
            tag = 4;
          }
            
        } else {
          Mylog.logger.error("[Stellar] Syntax Exception at " + temp);
          System.out.println("Syntax Exception at " + temp);
          tag = 3;
        }
      }
          
            
    }
    br.close();
        
    if (tag == 1) {
      throw new ParameterNumberException("build fail");
    } else if (tag == 2) {
      throw new sameTagException("build fail");
    } else if (tag == 3) {
      throw new SyntaxException("build fail");
    } else if (tag == 4) {
      throw new WrongAngleException("build fail");
    }
    return true;
  }
}