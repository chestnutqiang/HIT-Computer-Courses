package Iterator;

import java.util.Iterator;

import applications.ConcreteCircularOrbit;
import track.PlanetTrack;

public class TrackIterator implements Iterator{

	private ConcreteCircularOrbit system;
	private int index=0;
	private int n;
	/*
	 * RI：
	 * system：一个行星轨道系统
	 * index：迭代器当前遍历到的下标
	 * n：轨道数量
	 * 
	 * AF:
	 * 遍历行星轨道的迭代器
	 * 
	 *  safety from rep exposure:
	 * All fields are private;
	 * index and n 是int类型的，所以是immutable
	 */
	
    public TrackIterator(ConcreteCircularOrbit o) {
		system=o;
		n=system.getTrack().size();
	}
    
    /*
	 * @return：true如果有下一个元素，否则为false
	 */
	@Override
	public boolean hasNext() {
		if(index<n)
			return true;
		return false;
	}

	/*
	 * @return：下一个元素
	 */
	@Override
	public PlanetTrack next() {
		if(index<n) {
			PlanetTrack t=system.getTrack().get(index);
			index++;
			return t;
		}
		return null;
	}

}
