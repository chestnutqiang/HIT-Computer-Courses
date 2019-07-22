package physicalObject;


import otherDirectory.number;


public class Electron extends PhysicalObject{
	private int TrackNum;//轨道编号
	private int ElectronNum;//这个电子在轨道上的编号
	
	/*
	 * RI：
	 * TrackNum：电子所在的轨道的编号
	 * ElectronNum：电子在它所在轨道上所有电子的编号
	 * 
	 * AF：
	 * 根据TrackNum和ElectronNum唯一地确定一个电子
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * TrackNum and ElectronNum 是int类型的，所以是immutable
	 */
	public Electron(int x,int y) {
		TrackNum=x;
		ElectronNum=y;
	}
	
	/*
	 * @return：返回电子所在的轨道数
	 */
	public int getTrackNum() {
		return TrackNum;
	}
	
	/*
	 * @param：x:要设置的轨道数，要求为正整数
	 */
	public void setTrackNum(int x) {
		TrackNum=x;
	}
	
	/*
	 * @return：返回电子在轨道上的序号
	 */
	public int getEletronNum() {
		return ElectronNum;
	}
	
	/*
	 * @param：electronNum:要设置的序号，要求为正整数
	 */
	public void setElectronNum(int electronNum) {
		ElectronNum = electronNum;
	}
	
	/*
	 * @param：o：任意一种类型的变量
	 * @return：返回true如果o与当前变量相等
	 */
	@Override
	public boolean equals(Object o) {
		if(this.getClass()==o.getClass()) {
			Electron e=(Electron)o;
			return TrackNum==e.getTrackNum()&&ElectronNum==e.getEletronNum();
		}
		return false;
	}
	
	/*
	 * @return：返回当前变量的hash值
	 */
	@Override
	public int hashCode() {
		return TrackNum*ElectronNum;
	}
}
