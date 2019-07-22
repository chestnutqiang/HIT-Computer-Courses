package track;

import java.util.List;

/*
 * RI：
 * r:轨道半径
 * objectOnTrack:在这个轨道上的物体的集合
 * 
 * AF：
 * 描述一个轨道
 */

public abstract class Track <L,E>{
	public L r;
	public List<E> ObjectOnTrack;
	
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass()!=this.getClass()) {
			return false;
		}
		Track temp=(Track)o;
		return this.r.equals(temp.r);
	}
	
	@Override
	public int hashCode() {
		return r.hashCode();
	}
}
