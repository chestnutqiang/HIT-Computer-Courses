package centralObject;

import centralObject.CentralPoint;
import otherDirectory.label;

public class CentralUser extends CentralPoint{
	private label name;
	private int age;
	private char sex;
	/*
	 * RI:
	 * name:中心的名字，不为null
	 * age：中心使用者的年龄，要求为正整数
	 * sex：中心使用者的性别，要求为'M'或者'F'
	 * 
	 * AF：
	 * 描述中心使用者
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * age是int型，sex是char型，所以 是immutable类型的
	 * name是mutable的，所以getName()进行了防御式拷贝
	 */
	public CentralUser(label name,int age,char sex) {
		this.name=name;
		this.age=age;
		this.sex=sex;
	}
	
	public label getName() {
		return new label(name.toString());
	}
}
