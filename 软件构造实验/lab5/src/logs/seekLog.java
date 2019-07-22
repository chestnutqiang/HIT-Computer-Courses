package logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class seekLog {

  private final static File file = new File("src/logs/message.log");
  
  public static void main(String[] args) throws IOException {
    seekByApplacation("Electron");
  }
  
  public static void seekByType(String type) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = null;
    switch (type) {
    case "INFO":{
      String regex = ".*INFO.*";
        Pattern pattern  =  Pattern.compile(regex); 
        while((line = br.readLine()) != null) {
          Matcher matcher  =  pattern.matcher(line);
          if(matcher.matches())
            System.out.println(line);
        }
      break;
    }
      
    case "Error":{
      String regex = ".*Error.*";
        Pattern pattern  =  Pattern.compile(regex); 
        while((line = br.readLine()) != null) {
          Matcher matcher  =  pattern.matcher(line);
          if(matcher.matches())
            System.out.println(line);
        }
      break;
    }
    default:
      break;
    }
  }
  
  public static void seekByTime(String time) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = null;
    while ((line = br.readLine()) != null) {
       if(line.startsWith(time)) {
         System.out.println(line);
       }
     }
  }
  
  
  public static void seekByApplacation(String application) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = null;
    switch (application) {
    case "Stellar":{
      String regex = ".*Stellar.*";
        Pattern pattern  =  Pattern.compile(regex); 
        while((line = br.readLine()) != null) {
          Matcher matcher  =  pattern.matcher(line);
          if(matcher.matches())
            System.out.println(line);
        }
      break;
    }
      
    case "Electron":{
      String regex = ".*Electron.*";
        Pattern pattern  =  Pattern.compile(regex); 
        while((line = br.readLine()) != null) {
          Matcher matcher  =  pattern.matcher(line);
          if(matcher.matches())
            System.out.println(line);
        }
      break;
    }
    
    case "SocialNetwork":{
      String regex = ".*SocialNetwork.*";
        Pattern pattern  =  Pattern.compile(regex); 
        while((line = br.readLine()) != null) {
          Matcher matcher  =  pattern.matcher(line);
          if(matcher.matches())
            System.out.println(line);
        }
      break;
    }
    default:
      break;
    }
  }

}
