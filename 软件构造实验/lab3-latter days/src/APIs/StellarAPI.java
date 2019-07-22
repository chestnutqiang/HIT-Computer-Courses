package APIs;

import java.util.ArrayList;
import java.util.List;

import circularOrbit.CircularOrbit;
import applications.ConcreteCircularOrbit;
import physicalObject.PlanetWithSatellite;
import track.PlanetTrack;


public class StellarAPI implements CircularOrbitAPIs<PlanetWithSatellite>{

	/*
	 * AF:
	 *用来计算多轨道系统要求的功能
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 */
	
	/*
	 * @param：c：轨道系统，要求为行星轨道系统类型
	 * @return：多轨道系统中各轨道上物体分布的熵值
	 */
	@Override
	public double getObjectDistributionEntropy(CircularOrbit c) {
		ConcreteCircularOrbit system=(ConcreteCircularOrbit)c;
		int sumNum=0;
		for(PlanetTrack t:system.getTrack())
			sumNum=sumNum+t.ObjectOnTrack.size();
		double ans=0;
		for(PlanetTrack t:system.getTrack()) {
			int size=t.ObjectOnTrack.size();
			double p=(double)size/sumNum;
			ans+=-p*Math.log(p);
		}
		return ans;
	}

	//要求里面说明了行星之间没有逻辑关系
	@Override
	public int getLogicalDistance(CircularOrbit c, PlanetWithSatellite e1, PlanetWithSatellite e2) {
		try {
			throw new Exception("not implement");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * @param：c：轨道系统，要求为行星轨道系统类型，e1，e2：两个行星
	 * @return：多轨道系统任意两个物体之间的物理距离
	 */
	@Override
	public double getPhysicalDistance(CircularOrbit c, PlanetWithSatellite e1, PlanetWithSatellite e2) {
		ConcreteCircularOrbit system=(ConcreteCircularOrbit)c;
		double distance=system.DistanceOfTwoPlanet(e1, e2).doubleValue();
		return distance;
	}

	/*
	 * @param：c1，c2：两个轨道系统，要求为行星轨道系统类型
	 * @return：两个多轨道系统的差异
	 */
	@Override
	public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
		if(c1.getClass()!=c2.getClass())
			return null;
		ConcreteCircularOrbit system1=(ConcreteCircularOrbit)c1,system2=(ConcreteCircularOrbit)c2;
		List<PlanetTrack> track1=system1.getTrack(),track2=system2.getTrack();
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
		List<PlanetWithSatellite> planets1=new ArrayList<PlanetWithSatellite>(),planets2=new ArrayList<PlanetWithSatellite>();
		for(PlanetTrack t:track1) {
			for(PlanetWithSatellite p:t.ObjectOnTrack)
				planets1.add(p);
		}
		for(PlanetTrack t:track2) {
			for(PlanetWithSatellite p:t.ObjectOnTrack)
				planets2.add(p);
		}
		Difference<PlanetWithSatellite> D=new Difference<PlanetWithSatellite>(trackNum, objectNum, planets1, planets2);
		return D;
	}
}
