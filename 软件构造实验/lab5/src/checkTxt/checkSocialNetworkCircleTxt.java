package checkTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logs.Mylog;
import myExceptions.IntimacyOutOfRangeException;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.sameTagException;
import otherDirectory.label;
import physicalObject.Friend;

public class checkSocialNetworkCircleTxt {

  /**
   * 检查社交轨道的构造文件.
   * @param filePath 外部文件的路径
   * @return true如果文件合法
   * @throws IOException 读取文件出现的IO异常
   * @throws SyntaxException 语法异常
   * @throws ParameterNumberException 参数数目不正确异常
   * @throws sameTagException 标签相同异常
   * @throws IntimacyOutOfRangeException 亲密度不正确异常
   */
  public static boolean check(String filePath) throws IOException, SyntaxException, 
      ParameterNumberException, sameTagException, IntimacyOutOfRangeException {
    File file = new File(filePath);
    int tag = 0;/*
    tag=0:没有异常
    tag=1:ParameterNumberExcepton
    tag=2:sameTagException
    tag=3:SyntaxException
    tag=4:IntimacyOutOfRangeException
    */
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = "";
    
    //centralUser
    while (temp  == null || temp.equals("")) {
      temp = br.readLine();
    }
      
    int num = 0;
    char[] user = temp.toCharArray();
    for (char ch:user) {
      if (ch  == ',') {
        num++;
      }
    }
     
    if (num != 2) {
      Mylog.logger.error("[SocialNetwork] " + "wrong parameter number at "
          + temp + " ,exception 3 parameters,but is " + (num + 1) + " prameters");
      System.out.println("wrong parameter number at " + temp 
          + " ,exception 3 parameters,but is " + (num + 1) + " prameters");
      tag = 1;
    } else {
      String regex = "CentralUser ::= <([A-Za-z ]+),([0-9 ]+),([MF ])>";
      Pattern pattern = Pattern.compile(regex); 
      Matcher matcher = pattern.matcher(temp);
      if (!matcher.matches()) {
        Mylog.logger.error("[SocialNetwork] " + "syntax exception at " + temp);
        System.out.println("syntax exception at " + temp);
        tag = 3;
      }
        
      
      //Friend
      regex = "Friend ::= <(.*?),(.*?),(.*?)>";
      pattern = Pattern.compile(regex);
      String relation = "SocialTie ::= <([A-Za-z0-9 ]+),([A-Za-z0-9 ]+),([0-9. ]+)>";
      Pattern pattern1 = Pattern.compile(relation);
      Matcher matcher1 = pattern1.matcher(temp);
      while (temp != null) {
        temp = br.readLine();
        if (temp == null) {
          break;
        }
        if (temp.equals("")) {
          continue;
        }
        matcher = pattern.matcher(temp);
        matcher1 = pattern1.matcher(temp);
        if (matcher.matches()) {
          label name = new label(matcher.group(1).trim());
          int age = Integer.parseInt(matcher.group(2).trim());
          char sex = matcher.group(3).trim().charAt(0);
        } else if (matcher1.matches()) {
          String name1 = matcher1.group(1).trim();
          String name2 = matcher1.group(2).trim();
          
          double intimacy = Double.parseDouble(matcher1.group(3));
          if (name1.equals(name2)) {
            Mylog.logger.error("[SocialNetwork] " + temp + " this relation haves two same people");
            System.out.println(temp + " this relation haves two same people");
            tag = 2;
          }
            
          if (intimacy < 0 || intimacy > 1) {
            Mylog.logger.error("[SocialNetwork] " 
                + temp + " this relation's intimacy is out of range");
            System.out.println(temp + " this relation's intimacy is out of range");
            tag = 4;
          }
        } else {
          Mylog.logger.error("[SocialNetwork] " + "syntax exception at " + temp);
          System.out.println("syntax exception at " + temp);
          tag = 3;
        }
      }
    }
    
    br.close();
    if (tag == 1) {
      throw new ParameterNumberException("Parameter Number error");
    } else if (tag == 2) {
      throw new sameTagException("same people occurs twice");
    } else if (tag == 3) {
      throw new SyntaxException("syntax exception");
    } else if (tag == 4) {
      throw new IntimacyOutOfRangeException("relation's intimacy is out of range");
    }
    return true;
  }
}
