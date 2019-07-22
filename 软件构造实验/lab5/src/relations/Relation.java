package relations;

/*
 * RI：
 * L：关系中第一个物体的类型
 * E：关系中第二个物体的类型
 * 
 * AF：
 * 用来描述两个物体之间的关系
 */

public abstract class Relation<L,E> {
  public L object1;
  public E object2;
}
