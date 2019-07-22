package lab6;

import java.util.ArrayList;
import java.util.List;

public class River {
	int n;
	int h;
	static List<Ladder> ladders;
	/*
	 * RI:
	 * n:梯子数目
	 * h:梯子长度
	 * ladders:这条河上面的所有的梯子。
	 * 
	 * AF：
	 * 表示河流上所有梯子的集合
	 */
	
	public River(int x,int y) {
		ladders = new ArrayList<Ladder>();
		n = x;
		h = y;
		for (int i = 0;i < n;i++) {
			Ladder ladder = new Ladder(h);
			ladders.add(ladder);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
