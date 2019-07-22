package APIs;

import java.util.ArrayList;
import java.util.List;


//immutable类型表示系统之间的差异
public class Difference<E> {
  private int trackNum;
  private List<Integer> objectNum;
  private List<E> object1;
  private List<E> object2;
  /*
 * RI:
 * trackNum:
 * 两个系统之间轨道数量的差异
 * objectNum:
 * 两个系统的每个轨道上物体数量的差异
 * object1：
 * 第一个系统的轨道上的物体
 * object2
 * 第二个系统轨道上的物体
 * 
 * AF：
 * 描述两个系统之间的差异
 * 
 * safety from rep exposure：
 * all fields are private,and all types in the rep are immutable
 */
  
  /**
   * 这是描述两个系统的差异.
   * @param nums1 物体数量差异
   * @param nums2 每个轨道上的物体数量差异
   * @param list1 第一个系统的物体
   * @param list2 第二个系统的物体
   */
  public Difference(int nums1,List<Integer> nums2,List<E> list1,List<E> list2) {
    trackNum = nums1;
    objectNum = nums2;
    object1 = list1;
    object2 = list2;
  }
  
  public int getTrackNum() {
    return trackNum;
  }
  
  /**
   * 得到物体数量.
   * @return
   */
  public List<Integer> getObjectNum() {
    List<Integer> ans = new ArrayList<Integer>();
    ans.addAll(objectNum);
    return ans;
  }
  
  /**
   * 第一个系统的所有物体.
   * @return
   * 第一个系统的所有物体.
   */
  public List<E> getObject1() {
    List<E> list = new ArrayList<E>();
    list.addAll(object1);
    return list;
  }
  
  /**
   * 第二个系统的所有物体.
   * @return
   * 第二个系统的所有物体.
   */
  public List<E> getObject2() {
    List<E> list = new ArrayList<E>();
    list.addAll(object2);
    return list;
  }
}
