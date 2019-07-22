package centralObject;

import centralObject.CentralPoint;

public class Nucleus extends CentralPoint{
	private String ElementName;
	/*
	 * RI：
	 * ElementName：元素的名字，要求非空
	 * 
	 * AF：
	 * 表示原子核
	 * 
	 * safety from rep exposure:
	 * All fields are private;
	 * ElementName是String，所以是immutable的。
	 */

	/*
	 * @param：s：String类型的值，非空
	 */
	public Nucleus(String s) {
		ElementName=new String(s);
	}
	
	/*
	 * @return：返回当前变量的名字
	 */
	public String getName() {
		return new String(ElementName);
	}
}
