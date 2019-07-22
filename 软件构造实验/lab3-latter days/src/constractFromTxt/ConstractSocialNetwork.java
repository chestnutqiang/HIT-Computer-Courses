package constractFromTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.CentralUser;
import applications.SocialNetworkCircle;
import otherDirectory.label;
import physicalObject.Friend;
import relations.SocialTie;
import track.SocialTrack;

public class ConstractSocialNetwork {
	private List<SocialTie> relations=new ArrayList<SocialTie>();
	private int friendNum;
	private List<Friend> friends=new ArrayList<Friend>();
	private CentralUser user;
	private Friend f1;
	private List<SocialTrack> tracks;
	private String filePath;
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
	public ConstractSocialNetwork(String filePath) {
		this.filePath=filePath;
	}
	
	
	void ConstractUser() throws IOException {
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        temp=br.readLine();
        String regex="CentralUser ::= <([A-Za-z]+),([0-9]+),([MF])>";
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(temp);
        if(matcher.matches()) {
        	label name=new label(matcher.group(1));
            int age=Integer.parseInt(matcher.group(2));
            char sex=matcher.group(3).charAt(0);
            user=new CentralUser(name,age,sex);
            f1=new Friend(name, age, 0, sex);
            if(!friends.contains(f1))
            	friends.add(f1);
        }
        br.close();
        
	}
	
	void ConstractFriend() throws IOException {
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        temp=br.readLine();
        String regex="Friend ::= <(.*?),(.*?),(.*?)>";
        Pattern pattern = Pattern.compile(regex);
        while(temp!=null) {
        	Matcher matcher = pattern.matcher(temp);
        	if(matcher.matches()) {
        		label name=new label(matcher.group(1).trim());
                int age=Integer.parseInt(matcher.group(2).trim());
                char sex=matcher.group(3).trim().charAt(0);
                Friend f=new Friend(name, age, 1000, sex);
                if(!friends.contains(f))
                	friends.add(f);
        	}
        	temp=br.readLine();
        }
        br.close();
	}
	
	void ConstractRelations() throws IOException {
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        temp=br.readLine();
        String regex="SocialTie ::= <(.*?),(.*?),(.*?)>";
        Pattern pattern = Pattern.compile(regex);
        while(temp!=null) {
        	Matcher matcher = pattern.matcher(temp);
        	if(matcher.matches()) {
        		String name1=matcher.group(1).trim();
        		String name2=matcher.group(2).trim();
        		double Intimacy=Double.parseDouble(matcher.group(3));
        		Friend f1=new Friend(new label(name1), 0, 0, 'M'),f2=new Friend(new label(name2), 0, 0, 'M');
        		Friend f3=friends.get(friends.indexOf(f1)),f4=friends.get(friends.indexOf(f2));
                if(!f3.MyFriend().contains(f4)) {
                	f3.addFriend(f4);;
                	f3.addIntimacy(Intimacy);
                	f4.addFriend(f3);;
                	f4.addIntimacy(Intimacy);
                	SocialTie st=new SocialTie(f3, f4);
                	st.setIntimacy(Intimacy);
                	relations.add(st);
                }	
        	}
        	temp=br.readLine();
        }
        br.close();
	}
	
	void CaculateTrack() throws IOException {
		ConstractUser();
		ConstractFriend();
		ConstractRelations();
		tracks=new ArrayList<SocialTrack>();
		int i,j,count=0;
		List<Friend> list1=new ArrayList<Friend>(),added=new ArrayList<Friend>();
		list1.add(f1);
		added.add(f1);
		boolean flag=true;
		while(flag) {
			flag=false;
			List<Friend> list2=new ArrayList<Friend>();
			SocialTrack track=new SocialTrack(count);
			for(SocialTie st:relations) {
				if(!added.contains(st.object2)&&list1.contains(st.object1)) {
					((Friend)(st.object2)).setTrackNum(count);
					track.ObjectOnTrack.add((Friend) st.object2);
					list2.add((Friend) st.object2);
					added.add((Friend) st.object2);
					flag=true;
				}
				else if(!added.contains(st.object1)&&list1.contains(st.object2)) {
					((Friend)(st.object1)).setTrackNum(count);
					track.ObjectOnTrack.add((Friend) st.object1);
					list2.add((Friend) st.object1);
					added.add((Friend) st.object1);
					flag=true;
				}
			}
			if(!track.ObjectOnTrack.isEmpty())
				tracks.add(track);
			list1.clear();
			list1.addAll(list2);
			count++;
		}
	}
	
	
	public SocialNetworkCircle Constract() throws IOException {
		ConstractUser();
		ConstractFriend();
		ConstractRelations();
		SocialNetworkCircle S=new SocialNetworkCircle(friends,relations,user,f1);
		CaculateTrack();
		S.setTracks(tracks);
		return S;
	}
}
