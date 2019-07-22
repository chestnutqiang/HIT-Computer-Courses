package applications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import centralObject.CentralPoint;
import physicalObject.PhysicalObject;
import physicalObject.Planet;
import physicalObject.PlanetWithSatellite;
import track.Track;
import otherDirectory.label;
import otherDirectory.number;
import centralObject.Stellar;
import circularOrbit.CircularOrbit;
import relations.PlanetRelation;
import track.PlanetTrack;

public class ConcreteCircularOrbit implements CircularOrbit<Stellar,PlanetWithSatellite>{
	
	private List<PlanetTrack> tracks=new ArrayList<PlanetTrack>();
	private List<PlanetRelation> relation=new ArrayList<PlanetRelation>();
	private Stellar central;
	
	/*
	 * RI:
	 * tracks:行星轨道
	 * relation:行星之间的关系
	 * central：中央的恒星
	 * 
	 * AF
	 * 表示一个行星系统
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * tracks,relation，central是mutable的，所以getStellar()，getTrack()，getRelation()进行了防御式拷贝
	 */
	
	/*
	 * @param：t1：一系列轨道，c：一个恒星
	 */
	public ConcreteCircularOrbit(List<PlanetTrack> t1,Stellar c){//构造方法
		tracks=t1;
		central=c;
	}
	
	//创造一个空的对象
	public ConcreteCircularOrbit () {
		
	}
	
	/*
	 * @return：返回一个新的恒星，防御式拷贝
	 */
	public Stellar getStellar() {
		if(central==null)
			return null;
		Stellar temp=new Stellar(central.getName(),central.getRadius(),central.getWeight());
		return temp;
	}
	
	/*
	 * @return：返回轨道，防御式拷贝
	 */
	public List<PlanetTrack> getTrack(){
		List<PlanetTrack> t=new ArrayList<PlanetTrack>();
		t.addAll(tracks);
		return t;
	}
	
	/*
	 * @return：返回恒星与行星的关系，防御式拷贝
	 */
	public List<PlanetRelation> getRelation(){
		List<PlanetRelation> r=new ArrayList<PlanetRelation>();
		r.addAll(relation);
		return r;
	}

	/*
	 * @return :true如果成功加入一条新的轨道
	 */
	@Override
	public void addNewTrack(Track t) {
		PlanetTrack t1=(PlanetTrack)t;
		if(!tracks.contains(t1))
			tracks.add(t1);
	}

	/*
	 * @return :true如果成功移除一条轨道
	 */
	@Override
	public boolean removeTrack(int x) {
		if(tracks.size()>x) {
			tracks.remove(x);
			return true;
		}
		return false;
	}

	/*
	 * @return :true如果成功加入恒星
	 */
	@Override
	public boolean addCentralPoint(Stellar c) {
		if(central!=null)
			return false;
		else {
			central=c;
			return true;
		}
	}

	/*
	 * @param：o：行星
	 *  为恒星与行星添加关系
	 */
	@Override
	public void addRelationship(PlanetWithSatellite o) {
		PlanetRelation r=new PlanetRelation(central,o);
		relation.add(r);
	}

	/*
	 * 没有继承这个方法，明确要求行星之间没有关系
	 */
	@Override
	public void addRelatioOfPhyO(PlanetWithSatellite o1, PlanetWithSatellite o2) {
		try {
			throw new Exception("not implement");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @param：p：行星，要求在这个系统中，t：时间，要求正数
	 * @return :t时间的时候P行星的角位置
	 */
	public BigDecimal CurrentAngle(PlanetWithSatellite P,BigDecimal t) {
		Planet p=P.getPlanet();
		BigDecimal Omiga=p.getSpeed().value.divide(p.getOrbitalRadius().value,10,BigDecimal.ROUND_HALF_UP);
		BigDecimal theta=Omiga.multiply(t);
		BigDecimal angle1=new BigDecimal(p.getAngle());
		if(p.getDirection().equals("CW")) {
			theta=theta.add(angle1);
			return theta.divideAndRemainder(BigDecimal.valueOf(360))[1].setScale(0,BigDecimal.ROUND_DOWN);
		}
		else {
			theta=angle1.subtract(theta);
			theta=theta.divideAndRemainder(BigDecimal.valueOf(360))[1].setScale(0,BigDecimal.ROUND_DOWN);
			if(theta.compareTo(new BigDecimal("0"))==-1) {
				theta=theta.add(new BigDecimal("360"));
			}
			return theta;
		}
	}
	
	/*
	 * @param：两个行星，要求均在这个系统中
	 * @return :行星p1和行星p2的距离
	 */
	public BigDecimal DistanceOfTwoPlanet(PlanetWithSatellite P1,PlanetWithSatellite P2) {
		Planet p1=P1.getPlanet(),p2=P2.getPlanet();
		BigDecimal r1=p1.getOrbitalRadius().value,r2=p2.getOrbitalRadius().value;
		double theta=p1.getAngle()-p2.getAngle();
		theta=(theta/180)*Math.PI;
		double costheta=Math.cos(theta);
		BigDecimal costheta1=new BigDecimal(costheta);
		BigDecimal two=new BigDecimal(2),r1pingfang=r1.multiply(r1),r2pingfang=r2.multiply(r2),r1r2cos=r1.multiply((r2.multiply(costheta1)));
		BigDecimal distancepingfang=r1pingfang.add(r2pingfang.subtract(r1r2cos.multiply(two)));//r1^2+r2^2-2*r1*r2*cosa
		BigDecimal distance=number.sqrt(distancepingfang, 0);
		return distance;
	}

	/*
	 * t:一条行星轨道，o：一个行星
	 * @return :true如果成功在轨道t上添加行星o
	 */
	@Override
	public boolean addObjectOnTrack(Track t, PlanetWithSatellite o) {
		for(PlanetTrack t1:tracks) {
			if(t1.r.equals(t.r)) {
				t1.ObjectOnTrack.add(o);
				return true;
			}
		}
		return false;
	}



}
