package IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.SocialNetworkCircle;
import centralObject.CentralUser;
import checkTxt.checkSocialNetworkCircleTxt;
import logs.Mylog;
import myExceptions.IntimacyOutOfRangeException;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.sameTagException;
import otherDirectory.label;
import output.OutputNetwork;
import physicalObject.Friend;
import relations.SocialTie;
import track.SocialTrack;

public class NioSocialNetwork implements SocialNetworkIO {
	  private List<SocialTie> relations = new ArrayList<SocialTie>();
	  private List<Friend> friends = new ArrayList<Friend>();
	  private CentralUser user;
	  private Friend f1;
	  /*
	   * RI：
	   * relations：朋友关系
	   * friendnum：朋友数量
	   * friends：存储所有朋友
	   * user：centralUser类型，中心使用者
	   * tracks：朋友关系
	   * filepath:文件路径，要求为String类型
	   * 
	   * AF：
	   *  根据读入文件来构建社交网络
	   *  
	   * safety from rep exposure:
	   * All fields are private;
	   * friendNum是int型，filePath是String型，所以 是immutable类型的
	   */
	  
	  //I/O使用方法
	  public static void main(String[] args) {
		  NioSocialNetwork test = new NioSocialNetwork();
		  long start = System.currentTimeMillis();
		  SocialNetworkCircle system = test.Constract("src/TXT/BigSocialNetworkCircle.txt");
		  long end = System.currentTimeMillis();
		  System.out.println("Nio建立社交轨道系统花费的时间是" + (end - start));
		  try {
			test.output(system);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
		
		public void constract(String filePath) throws IOException {
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
		              
		              String social=new String(toTemp);
		              
		              String regex = "Friend ::= <(.*?),(.*?),(.*?)>";
		              Pattern pattern = Pattern.compile(regex);
		              Matcher matcher = pattern.matcher(social);
		              if (matcher.find()) {
		            	label name = new label(matcher.group(1).trim());
		      	        int age = Integer.parseInt(matcher.group(2).trim());
		      	        char sex = matcher.group(3).trim().charAt(0);
		      	        Friend f = new Friend(name, age, 1000, sex);
		      	        friends.add(f);
		              } else {
		                	regex = "SocialTie ::= <(.*?),(.*?),(.*?)>";
		                    pattern = Pattern.compile(regex); 
		                    matcher = pattern.matcher(social);
		                    if (matcher.find()) {
		                    	String name1 = matcher.group(1).trim();
		            	        String name2 = matcher.group(2).trim();
		            	        double intimacy = Double.parseDouble(matcher.group(3));
		            	        Friend f1 = new Friend(new label(name1), 0, 0, 'M');
		            	        Friend f2 = new Friend(new label(name2), 0, 0, 'M');
		            	        f1.addFriend(f2);;
		            	        f1.addIntimacy(intimacy);
		            	        f2.addFriend(f1);;
		            	        f2.addIntimacy(intimacy);
		            	        SocialTie st = new SocialTie(f1, f2);
		            	        st.setIntimacy(intimacy);
		            	        relations.add(st);
		                    } else {
		                    	regex = "CentralUser ::= <([A-Za-z]+),([0-9]+),([MF])>";
		                    	pattern = Pattern.compile(regex); 
		                	    matcher = pattern.matcher(social);
		                	    if (matcher.matches()) {
		                	      label name = new label(matcher.group(1));
		                	      int age = Integer.parseInt(matcher.group(2));
		                	      char sex = matcher.group(3).charAt(0);
		                	      user = new CentralUser(name,age,sex);
		                	      f1 = new Friend(name, age, 0, sex);
		                	      if (!friends.contains(f1)) {
		                	        friends.add(f1);
		                	      }
		                	    }
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
		}
	  

	  /**
	   * 根据外部文件构造社交轨道系统.
	   * @return 根据外部文件构造的社交轨道系统
	   */
	  @Override
	  public SocialNetworkCircle Constract(String filePath) {
	    try {
	      if (checkSocialNetworkCircleTxt.check(filePath)) {
	        Mylog.logger.info("[SocialNetwork]" + "constract network from " + filePath);
	        constract(filePath);
	        SocialNetworkCircle s = new SocialNetworkCircle(friends,relations,user,f1);
	        return s;
	      }
	    } catch (IOException | SyntaxException | ParameterNumberException | sameTagException
	        | IntimacyOutOfRangeException e) {
	      Scanner in = new Scanner(System.in);
	      System.out.println("please input another file path ");
	      String file = in.nextLine();
	      return this.Constract(file);
	    }
	    return null;
	  }
	  
	  
	  
	  public void output(SocialNetworkCircle system) throws IOException {
			
			File file = new File("src/TXT/SocialNetworkOutput.txt");
			
			CentralUser user = system.getUser();

			 if (!file.exists()) {
			   file.createNewFile();
			 }
			 
			 FileWriter fw = new FileWriter("src/TXT/SocialNetworkOutput.txt");
			 BufferedWriter bw = new BufferedWriter(fw);
			 
			 String central = "CentralUser ::= <" + user.getName().toString() + ","
			     + user.getAge() + "," +user.getSex()+">\n";
			 bw.write(central);
			 
			 for (Friend f:system.getFriends()) {
				 String friend = "Friend ::= <" + f.getname().toString()
				     + "," + f.getAge() + "," + f.getSex() + ">\n";
				 if (!f.getname().equals(user.getName())) {
				   bw.write(friend);
				 }
				 
			 }
			 
			 for (SocialTie t:system.getRelations()) {
				 String relation = "SocialTie ::= <" + t.object1.getname().toString()
				     + "," + t.object2.getname().toString() + "," + t.getIntimacy()
				     + ">\n";
				 bw.write(relation);
			 }
			 
			 bw.close();
		}
	}
