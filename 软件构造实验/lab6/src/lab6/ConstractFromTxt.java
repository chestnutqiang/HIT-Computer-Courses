package lab6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logs.Mylog;

public class ConstractFromTxt {

	/*
	 * AF：从文件中建立系统
	 */
	public static void main(String[] args) {
		try {
			Constract("src/txt/Competition_2.txt");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void Constract(String filePath) throws IOException, InterruptedException{
	  	List<Monkey> monkeys = new ArrayList<Monkey>();
	  	List<Thread> threads = new ArrayList<Thread>();
	    Mylog.logger.info("启动猴子过河程序 " + filePath);
	    int n = 0;
	    int h = 0;
	    int current = 0;
	    File file = new File(filePath);
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String temp = null;
	    temp = br.readLine();
	    String regex = "n=([0-9]+)";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(temp);
	    if (matcher.find()) {
	      n = Integer.parseInt(matcher.group(1));
	    }
	    temp = br.readLine();
	    regex = "h=([0-9]+)";
	    pattern = Pattern.compile(regex);
	    matcher = pattern.matcher(temp);
	    
	    if (matcher.matches()) {
	      h = Integer.parseInt(matcher.group(1));
	    }
	    River river = new River(n,h);
	    
	    regex = "monkey=<([0-9]+),([0-9]+),([L-R>-]+),([0-9]+)>";
	    pattern = Pattern.compile(regex);
	    int monkeyNum = 0;
	    do {
	      temp = br.readLine();
	      if (temp == null || temp.equals("")) {
	    	  break;
	      }
	      matcher = pattern.matcher(temp);
	      if (matcher.matches()) {
	    	  monkeyNum++;
		      int bornTime = Integer.parseInt(matcher.group(1));
		      Monkey m = new Monkey(bornTime,Integer.parseInt(matcher.group(2)),matcher.group(3),Integer.parseInt(matcher.group(4)));
		      monkeys.add(m);
		      m.setRiver(river);
		      Thread t = new Thread(m);
		      threads.add(t);
		      
		      t.sleep(1000*(bornTime - current));
		      current = bornTime;
		      t.start();
		    }
	    } while (temp != null && !temp.equals(""));
	    
	    Caculate caculate = new Caculate(monkeyNum);
	    boolean live = true;
		while (live) {
			live = false;
			for (Thread t:threads) {
				if (t.isAlive()) {
					live = true;
					break;
				}
			}
			if (live) {
				Thread.sleep(1000);
			}
		}
		
		//计算吞吐率和公平性
		caculate.caculateTh(monkeys);
		caculate.caculateFair(monkeys);
	  }
}
