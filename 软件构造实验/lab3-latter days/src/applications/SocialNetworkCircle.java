package applications;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import centralObject.CentralUser;
import circularOrbit.CircularOrbit;
import physicalObject.Friend;
import relations.SocialTie;
import track.SocialTrack;
import track.Track;

public class SocialNetworkCircle implements CircularOrbit<CentralUser, Friend>{
	
	
	//central,user is both the centraluser;
	private CentralUser user;
	private Friend central;
	private List<Friend> friends = new ArrayList<Friend>();//为了方便计算轨道，friends加入了user本身
	private List<SocialTie> relations = new ArrayList<SocialTie>();
	private List<SocialTrack> tracks = new ArrayList<SocialTrack>();
	final int Infinity=100000;
	
	/*
	 * RI：
	 * uesr：中心使用者，非空
	 * friends：使用者所有的朋友
	 * relations：这个社交网络所有的关系
	 * tracks：这个社交网站所有的轨道
	 * infinity：100000，作为无穷大使用
	 * 
	 * AF
	 *  表示一个社交网络
	 * 
	 * safe from rep exposure：
	 *  All fields are private;
	 * Infinity是int型，所以 是immutable类型的
	 * friends,tracks是mutable的，所以getFriends(),getTracks()进行了防御式拷贝
	 */
	
	public SocialNetworkCircle(List<Friend> friends,List<SocialTie> relations,CentralUser user,Friend central) {
		this.friends=friends;
		this.relations=relations;
		this.user=user;
		this.central=central;
	}
	
	/*
	 * @return 这个人朋友列表的拷贝
	 */
	public List<Friend> getFriends(){
		List<Friend> ans=new ArrayList<Friend>();
		ans.addAll(friends);
		return ans;
	}
	
	/*
	 * @return 这个人轨道列表的拷贝
	 */
	public List<SocialTrack> getTracks(){
		List<SocialTrack> ans=new ArrayList<SocialTrack>();
		ans.addAll(tracks);
		return ans;
	}
	
	/*
	 * @param 这个人的轨道列表
	 */
	public void setTracks(List<SocialTrack> tracks) {
		List<SocialTrack> tracks1 = new ArrayList<SocialTrack>();
		tracks1.addAll(tracks);
		this.tracks = tracks1;
	}

	
	@Override
	public void addNewTrack(Track t) {//在这个应用中这个spec没有意义
		try {
			throw new Exception("no implement");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//没有继承
	@Override
	public boolean removeTrack(int x) {
		try {
			throw new Exception("not implement");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * @param：c：中心使用者
	 * @return：true如果加入成功
	 */
	@Override
	public boolean addCentralPoint(CentralUser c) {
		if(user==null) {
			user=c;
			return true;
		}
		return false;
	}

	//感觉在轨道上加入物体也没有意义，因为并没有与其他人产生社交联系，没有写这个功能的测试
	@Override
	public boolean addObjectOnTrack(Track t, Friend o) {
		SocialTrack t1=tracks.get((int) t.r);
		if(!t1.ObjectOnTrack.contains(o)) {
			t1.ObjectOnTrack.add(o);
			o.setTrackNum((int) t.r);
			return true;
		}
		return false;
	}

	/*
	 * @param：o：一个朋友，要求出现在这个社交网络里面
	 */
	@Override
	public void addRelationship(Friend o) {
		if(!friends.contains(o)) {
			friends.add(o);
			o.setTrackNum(0);
			SocialTie t=new SocialTie(central,o);
			relations.add(t);
			tracks.get(0).ObjectOnTrack.add(o);
		}
	}
	
	/*
	 * @param：f 社交网络里面的一个人，t：要跳转到的轨道
	 */
	public void transit (Friend f, SocialTrack t) {
		int trackNum=f.getTrackNum();
		tracks.get(trackNum).ObjectOnTrack.remove(f);
		t.ObjectOnTrack.add(f);
	}

	/*
	 * @param：f1，f2：社交网络里面的两个人
	 * 为他们添加社交关系
	 */
	@Override
	public void addRelatioOfPhyO(Friend f1, Friend f2) {
		SocialTie st1=new SocialTie(f1, f2);
		relations.add(st1);
		f1.addFriend(f2);;
		f2.addFriend(f1);;
		int count=0;
		tracks=new ArrayList<SocialTrack>();
		List<Friend> list1=new ArrayList<Friend>(),added=new ArrayList<Friend>();
		list1.add(central);
		boolean flag=true;
		while(flag) {
			flag=false;
			List<Friend> list2=new ArrayList<Friend>();
			SocialTrack track=new SocialTrack(count);
			for(SocialTie st:relations) {
				if(!added.contains(st.object2)&&list1.contains(st.object1)) {
					st.object2.setTrackNum(count);
					track.ObjectOnTrack.add(st.object2);
					list2.add(st.object2);
					added.add(st.object2);
					flag=true;
				}
				else if(!added.contains(st.object1)&&list1.contains(st.object2)) {
					st.object1.setTrackNum(count);
					track.ObjectOnTrack.add(st.object1);
					list2.add(st.object1);
					added.add(st.object1);
					flag=true;
				}
			}
			tracks.add(track);
			list1.clear();
			list1.addAll(list2);
			count++;
		}
	}
	
	/*
	 * @param：f1，f2：社交网络里面的两个人
	 * 删除f1,f2社交关系
	 */
	public void DeleteRelationOfFriend(Friend f1,Friend f2) {
		SocialTie st1=new SocialTie(f1, f2);
		relations.remove(st1);
		f1.removeFriend(f2);;
		f2.removeFriend(f1);;
		int count=0;
		tracks=new ArrayList<SocialTrack>();
		List<Friend> list1=new ArrayList<Friend>(),added=new ArrayList<Friend>();
		list1.add(central);
		boolean flag=true;
		while(flag) {
			flag=false;
			List<Friend> list2=new ArrayList<Friend>();
			SocialTrack track=new SocialTrack(count);
			for(SocialTie st:relations) {
				if(!added.contains(st.object2)&&list1.contains(st.object1)) {
					st.object2.setTrackNum(count);
					track.ObjectOnTrack.add(st.object2);
					list2.add(st.object2);
					added.add(st.object2);
					flag=true;
				}
				else if(!added.contains(st.object1)&&list1.contains(st.object2)) {
					st.object1.setTrackNum(count);
					track.ObjectOnTrack.add(st.object1);
					list2.add(st.object1);
					added.add(st.object1);
					flag=true;
				}
			}
			tracks.add(track);
			list1.clear();
			list1.addAll(list2);
			count++;
		}
	}
	/*
	 * @param：f：第一轨道上的某个朋友
	 */
	//计算信息扩散度，当两个人之间的朋友度大于0.8认为两个人可以通过社交关系认识
	//两个人之间的朋友度定义为两个人之间通过朋友关系联系起来的路径的亲密度之积的最大值
	//通过第一轨道上的某个朋友能够认识的人不包括中心使用者自己
	public int SpreadFriend(Friend f) {
		List<Friend> list1 = new ArrayList<Friend>();
		int count=0,i,len=f.MyFriend().size();
		for(i=0;i<len;i++) {
			Friend friend=f.MyFriend().get(i);
			double Intimacy=f.getIntimacy().get(i);
			count+=Spread(friend, list1, Intimacy);
		}
		return count;
	}
	
	public int Spread(Friend f,List<Friend> list1,double Intimacy) {
		if(f.getname().equals(user.getName()))
			return 0;
		int ans=0,i,len=f.MyFriend().size();
		for(i=0;i<len;i++) {
			Friend friend=f.MyFriend().get(i);
			if(!list1.contains(friend)&&Intimacy*f.getIntimacy().get(i)>0.8) {
				list1.add(friend);
				ans++;
				ans+=Spread(friend, list1, Intimacy*f.getIntimacy().get(i));
			}
		}
		return ans;
	}
	
	/*
	 * @param：f1，f2：社交网络里面的两个人
	 * @return：计算两个朋友之间的逻辑距离
	 */
	public int Distance(Friend f1,Friend f2) {
		int ans=100000;
		Set<Friend> set=new HashSet<Friend>();
		ans=distance(f1, f2, set);
		return ans;
	}
	
	
	public int distance(Friend f1,Friend f2,Set<Friend> set) {
		int ans=100000;
		if(f1.MyFriend().contains(f2))
			return 1;
		else {
			set.add(f1);
			for(Friend f3:f1.MyFriend()) {
				Set<Friend> s1=new HashSet<Friend>();
				s1.addAll(set);
				if(!set.contains(f3))
					ans=Math.min(ans, distance(f3, f2, s1)+1);
			}
		}
		return ans;
	}
}
