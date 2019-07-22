package physicalObject;

import java.util.ArrayList;
import java.util.List;
import otherDirectory.label;

public class Friend extends PhysicalObject{
	private label name;
	private int age;
	private int trackNum;
	private char sex;
	private List<Friend> myFriend=new ArrayList<Friend>();
	private List<Double> Intimacy=new ArrayList<Double>();
	
	/*
	 * RI：
	 * name：朋友的年龄，label类型
	 * age：朋友的年龄，正整数
	 * trackNum：朋友所在的轨道编号
	 *sex：朋友的性别
	 *myFriend：这个人所有的朋友 
	 * Intimacy：这个人和他的朋友的亲密度
	 * 
	 * 
	 * AF：
	 * 表示中心使用者的一个朋友
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * age,trackNum是int型，sex是char型，所以 是immutable类型的
	 * name,myFriend,Intimacy是mutable的，所以getName(),MyFriend(),getIntimacy()进行了防御式拷贝
	 */
	public  Friend(label name,int age,int trackNum,char sex) {
		this.name=new label(name.toString());
		this.age=age;
		this.trackNum=trackNum;//0,1,2,3,4....
		this.sex=sex;
	}
	
	/*
	 * @return 这个人的性别
	 */
	public char getSex() {
		return sex;
	}
	
	/*
	 * @return 这个人的年龄
	 */
	public int getAge() {
		return age;
	}
	
	/*
	 * @return 这个人名字的拷贝
	 */
	public label getname() {
		return new label(name.toString());
	}
	
	/*
	 * @return 这个人所在的轨道数
	 */
	public int getTrackNum() {
		return trackNum;
	}
	
	/*
	 * @param：trackNum要调整到的轨道数，要求为非负整数
	 */
    public void setTrackNum(int trackNum) {
		this.trackNum = trackNum;
	}
    
    /*
	 * @return 这个人的朋友的拷贝
	 */
    public List<Friend> MyFriend(){
    	List<Friend> ans=new ArrayList<Friend>();
    	ans.addAll(myFriend);
    	return ans;
    }
    
    /*
	 * @param：f 要添加的朋友，要求非空
	 */
    public void addFriend(Friend f) {
    	myFriend.add(f);
    }
    
    /*
	 * @param：f 要移除的朋友，要求非空
	 */
    public void removeFriend(Friend f) {
    	myFriend.remove(f);
    }
    
    /*
	 * @return 这个人的朋友亲密度的拷贝
	 */
    public List<Double> getIntimacy(){
    	List<Double> ans=new ArrayList<Double>();
    	ans.addAll(Intimacy);
    	return ans;
    }
    
    /*
	 * @param：x 要添加的朋友亲密度，要求范围为[0,1]
	 */
    public void addIntimacy(double x) {
    	Intimacy.add(x);
    }
	
    /*
	 * @param：o：任意一种类型的变量
	 * @return：返回true如果o与当前变量相等
	 */
	@Override
	public boolean equals(Object o) {
		if(this.getClass()!=o.getClass())
			return false;
		else {
			Friend f=(Friend)o;
			boolean ans=name.equals(f.getname());
			return ans;
		}
	}
	
	/*
	 * @return：返回当前变量的hash值
	 */
	@Override
	public int hashCode() {
		return name.hashCode()+age+trackNum+(sex-'F');
	}
}
