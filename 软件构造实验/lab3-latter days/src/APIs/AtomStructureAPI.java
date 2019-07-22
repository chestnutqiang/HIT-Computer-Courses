package APIs;

import java.util.ArrayList;
import java.util.List;

import applications.ConcreteCircularOrbitE;
import circularOrbit.CircularOrbit;
import physicalObject.Electron;
import relations.ElectronRealtion;
import track.ElectronTrack;


public class AtomStructureAPI implements CircularOrbitAPIs<Electron>{
	private final int Infinity=10000;
	
	/*
	 * AF:
	 *用来计算多轨道系统要求的功能
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 */
	
	/*
	 * @param：c：轨道系统，要求为原子轨道系统类型
	 * @return：多轨道系统中各轨道上物体分布的熵值
	 */
	@Override
	public double getObjectDistributionEntropy(CircularOrbit c) {
		ConcreteCircularOrbitE system=(ConcreteCircularOrbitE)c;
		int sum=0;
		double ans=0;
		for(ElectronTrack t:system.getTracks()) {
			sum+=t.ObjectOnTrack.size();
		}
		for(ElectronTrack t:system.getTracks()) {
			double p=(double)t.ObjectOnTrack.size()/sum;
			ans+=-p*Math.log(p);
		}
		return ans;
	}

	/*
	 * @param：c：轨道系统，要求为原子轨道系统类型，e1，e2：两个电子
	 * @return：得到e1，e2的逻辑距离
	 */
	@Override
	public int getLogicalDistance(CircularOrbit c, Electron e1, Electron e2) {
		ConcreteCircularOrbitE system=(ConcreteCircularOrbitE)c;
		int count=1;
		List<Electron> visited=new ArrayList<Electron>(),relationed=new ArrayList<Electron>(),find=new ArrayList<Electron>();
		relationed.add(e1);
		while(!relationed.isEmpty()) {
			find.clear();
			for(ElectronRealtion r:system.getRelations2()) {
				Electron E1=r.object1,E2=r.object2;
				if(relationed.contains(E1)&&!visited.contains(E2)) {
					find.add(E2);
					if(E2.equals(e2))
						return count;
				}
				else if(relationed.contains(E2)&&!visited.contains(E1)) {
					find.add(E1);
					if(E1.equals(e2))
						return count;
				}
			}
			visited.addAll(relationed);
			relationed.clear();
			relationed.addAll(find);
			count++;
		}
		return Infinity;
	}

	/*
	 * @param：c：轨道系统，要求为原子轨道系统类型，e1，e2：两个电子
	 * @return：多轨道系统任意两个物体之间的物理距离
	 */
	@Override
	public double getPhysicalDistance(CircularOrbit c, Electron e1, Electron e2) {
		try {
			throw new Exception("not implement");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * @param：c1，c2：两个轨道系统，要求为原子轨道系统类型
	 * @return：两个多轨道系统的差异
	 */
	@Override
	public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
		if(c1.getClass()!=c2.getClass())
			return null;
		ConcreteCircularOrbitE system1=(ConcreteCircularOrbitE)c1,system2=(ConcreteCircularOrbitE)c2;
		List<ElectronTrack> track1=system1.getTracks(),track2=system2.getTracks();
		int trackNum=Math.abs(track1.size()-track2.size());
		List<Integer> objectNum=new ArrayList<Integer>();
		int i,len=Math.max(track1.size(), track2.size());
		for(i=0;i<len;i++) {
			if(i<track1.size()) {
				if(i<track2.size()) {
					objectNum.add(track1.get(i).ObjectOnTrack.size()-track2.get(i).ObjectOnTrack.size());
				}
				else
					objectNum.add(track1.get(i).ObjectOnTrack.size());
			}
			else
				objectNum.add(-track2.get(i).ObjectOnTrack.size());
		}
		List<Electron> planets1=new ArrayList<Electron>(),planets2=new ArrayList<Electron>();
		for(ElectronTrack t:track1) {
			for(Electron p:t.ObjectOnTrack)
				planets1.add(p);
		}
		for(ElectronTrack t:track2) {
			for(Electron p:t.ObjectOnTrack)
				planets2.add(p);
		}
		Difference<Electron> D=new Difference<Electron>(trackNum, objectNum, planets1, planets2);
		return D;
	}

}
