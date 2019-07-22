package physicalObject;


public class Electron extends PhysicalObject implements ElectronInterface{
  private int trackNum;//轨道编号
  /*
   * RI：
   * trackNum：电子所在的轨道的编号
   * electronNum：电子在它所在轨道上所有电子的编号
   * 
   * AF：
   * 根据trackNum和electronNum唯一地确定一个电子
   * 
   * safety from rep exposure:
   * All fields are private;
   * trackNum and electronNum 是int类型的，所以是immutable
   */
  public Electron(int x) {
    trackNum = x;
  }
  
  /*
   * @return：返回电子所在的轨道数
   */
  public int getTrackNum() {
    return trackNum;
  }
  
  /*
   * @param：x:要设置的轨道数，要求为正整数
   */
  public void setTrackNum(int x) {
    trackNum = x;
  }
 
  
  /*
   * @param：o：任意一种类型的变量
   * @return：返回true如果o与当前变量相等
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
      
    if (this.getClass() == o.getClass()) {
      Electron e = (Electron)o;
      return trackNum == e.getTrackNum() ;
    }
    return false;
  }
  
  /*
   * @return：返回当前变量的hash值
   */
  @Override
  public int hashCode() {
    return trackNum ;
  }
}
