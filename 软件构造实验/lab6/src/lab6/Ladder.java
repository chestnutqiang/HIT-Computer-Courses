package lab6;

public class Ladder {
	int h;
	Monkey state[];
	int monkeyNum;
	/*
	 * RI:
	 * h：梯子的长度
	 * state：表示每个踏板的状态
	 * monkeyNum:梯子上面的猴子数量,这是个矢量，表示梯子此时的行进状态
	 * 0:没有猴子
	 * 正数：从左向右的猴子的数量
	 * 负数：从右向左的猴子的数量
	 * 
	 * AF：
	 * 描述一个梯子
	 */
	
	public Ladder(int x) {
		h = x;
		state = new Monkey[x];
		monkeyNum = 0;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
