package applications;

import java.util.ArrayList;
import java.util.List;

import centralObject.Nucleus;
import circularOrbit.CircularOrbit;
import track.ElectronTrack;
import track.Track;
import otherDirectory.number;
import physicalObject.Electron;
import relations.ElectronRealtion;


public class ConcreteCircularOrbitE implements CircularOrbit<Nucleus, Electron>{
	
	private Nucleus nucleus;
	private int NumberOfTracks;
	private List<ElectronTrack> tracks=new ArrayList<ElectronTrack>();//轨道
	private List<Electron> relation1;//存储与原子核有关系的那些电子
	private List<ElectronRealtion> relation2;//存储原子之间的关系
	private List<ConcreteCircularOrbitE> memento;
	
	/*
	 * RI：
	 * nucleus：原子核，要求非null
	 * NumberOfTracks：原子的轨道数
	 * tracks：表示原子的轨道的集合
	 * relation1：表示原子系统电子与原子核关系的集合
	 * relation2：表示电子之间关系的集合
	 * memento:存储原子轨道状态的集合，用于进行状态的恢复
	 * 
	 * 
	 * AF：
	 * 一个原子轨道结构
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * nucleus,tracks,relation1,relation2,memento是mutable的，所以getNucleus(),getTracks(),getRelations1(),getRelations2()和getTrackNum()进行了防御式拷贝
	 */
	
	/*
	 * @return:返回原子核的拷贝
	 */
	public Nucleus getNucleus() {
		return new Nucleus(nucleus.getName());
	}
	
	/*
	 * @return:返回轨道数量的拷贝
	 */
	public int getTrackNum() {
		return NumberOfTracks;
	}
	
	/*
	 * @return:返回轨道的拷贝
	 */
	public List<ElectronTrack> getTracks() {
		List<ElectronTrack> temp=new ArrayList<ElectronTrack>();
		temp.addAll(tracks);
		return temp;
	}
	
	/*
	 * @return:返回原子与电子关系的拷贝
	 */
	public List<Electron> getRelations1() {
		List<Electron> temp=new ArrayList<Electron>();
		temp.addAll(relation1);
		return temp;
	}
	
	/*
	 * @return:返回电子关系的拷贝
	 */
	public List<ElectronRealtion> getRelations2(){
		List<ElectronRealtion> temp=new ArrayList<ElectronRealtion>();
		temp.addAll(relation2);
		return temp;
	}
	
	/*
	 * @return:返回之前状态的拷贝
	 */
	public List<ConcreteCircularOrbitE> getMemento(){
		List<ConcreteCircularOrbitE> temp=new ArrayList<ConcreteCircularOrbitE>();
		temp.addAll(memento);
		return temp;
	}
	
	/*
	 * @param：n:原子核
	 * @param：tracks：轨道列表
	 * @return：根据n和tracks构建系统
	 */
	public ConcreteCircularOrbitE(Nucleus n,List<ElectronTrack> tracks) {
		nucleus=n;
		for(ElectronTrack t:tracks) {
			ElectronTrack t1=new ElectronTrack(t.r);
			t1.ObjectOnTrack.addAll(t.ObjectOnTrack);
			this.tracks.add(t1);
		}
		NumberOfTracks=tracks.size();
		memento=new ArrayList<ConcreteCircularOrbitE>();
		relation1=new ArrayList<Electron>();
		relation2=new ArrayList<ElectronRealtion>();
	}
	
	/*
	 * @param：t 一条轨道，加入这个新轨道
	 */
	@Override
	public void addNewTrack(Track t) {//轨道编号，r>=1而且是整数
		ElectronTrack t1=(ElectronTrack)(t);
		tracks.add(t1.r-1, t1);
		NumberOfTracks++;
		
	}
	
	
	/*
	 * @param：x 轨道的序号，移除轨道号为x的轨道
	 * @return：true如果移除成功，否则false
	 */
	@Override
	public boolean removeTrack(int x) {
		for(ElectronTrack t:tracks) {
			if(t.r.equals(x)) {
				tracks.remove(t);
				return true;
			}
		}
		return false;
	}

	/*
	 * @param：c：一个原子核，加入新的原子核，替换掉老的原子核
	 * @return：true如果加入成功，否则false
	 */
	@Override
	public boolean addCentralPoint(Nucleus c) {
		if(nucleus==null)
			{nucleus=new Nucleus(c.getName());return true;}//原子核不能有两个
		return false;
	}

	/*
	 * @param：t 一条轨道，o 要加入的电子，在特定轨道上加入一个电子
	 * @return：true如果加入成功，否则false
	 */
	@Override
	public boolean addObjectOnTrack(Track t, Electron o) {
		ConcreteCircularOrbitE old=new ConcreteCircularOrbitE(nucleus,tracks);
		memento.add(old);
		for(ElectronTrack t1:tracks) {
			if(t1.equals(t)) {
				t1.ObjectOnTrack.add(o);
				return true;
			}
		}
		return false;
	}

	/*
	 * @param：o 一个电子，为o这个电子与原子核添加关系
	 */
	@Override
	public void addRelationship(Electron o) {
		if(!relation1.contains(o))
			relation1.add(o);
	}
	
	/*
	 * @param：o1 ，o2：两个电子，为这两个电子添加关系
	 */
	@Override
	public void addRelatioOfPhyO(Electron o1, Electron o2) {
		ElectronRealtion R=new ElectronRealtion(o1, o2);
		if(!relation2.contains(R)) {
			relation2.add(R);
		}
	}
	
	/*
	 * @param：e1：一个电子，t1 这个电子原来的轨道，t2：要跃迁的轨道，电子轨道跃迁，从t1轨道跃迁到t2轨道上
	 */
	public void ElectronTransmit(Electron e1,ElectronTrack t1,ElectronTrack t2) {
		ConcreteCircularOrbitE old=new ConcreteCircularOrbitE(nucleus,tracks);
		memento.add(old);
		for(ElectronTrack t:tracks) {
			if(t.equals(t1)) {
				t.ObjectOnTrack.remove(e1);
			}
			if(t.equals(t2)) {
				e1.setTrackNum(tracks.indexOf(t2)+1);
				e1.setElectronNum(t2.ObjectOnTrack.size());
				t.ObjectOnTrack.add(e1);
			}
		}
	}
	
	/*
	 * @param：step要恢复的步数，管理电子跃迁 的状态进行状态的恢复
	 * @return：true如果恢复成功，否则false。
	 */
	public boolean getback(int step) {
		int len=memento.size();
		if(step>0&&step<=len) {
			tracks=memento.get(len-step).tracks;
			return true;
		}
		else
			return false;
	}

}
