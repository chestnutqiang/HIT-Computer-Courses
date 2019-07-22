package lab6;

import java.util.List;

public class Strategy2 implements Strategy {

	/*
	 * @return 猴子要选择哪一个梯子
	 * 这里采用的策略是实验手册里面使用的策略3
	 * 优先选择没有猴子的梯子
	 * 若所有梯子上都有猴子，则优先 选择没有与我对向而行的猴子的梯子；
	 * 若满足该条件的梯子有很多， 则选择上面猴子数量最少的那个
	 */
	@Override
	public synchronized Ladder strategy(Monkey monkey) {
		int n = monkey.river.n;
		List<Ladder> ladders = monkey.river.ladders;
		synchronized (ladders) {
			int i;
			for(i = 0;i < n;i++) {
				if(ladders.get(i).monkeyNum == 0) {
					Ladder ladder = ladders.get(i);
					if(monkey.direction.equals("L->R")) {
						ladder.monkeyNum++;//从左向右的猴子数量+1
					} else {
						ladder.monkeyNum--;//从右向左的猴子数量+1
					}
					return ladders.get(i);
				}
			}
			
			//运行到这说明所有的梯子上面都有猴子
			//遇到一个梯子，如果上面没有与自己对向而行的猴子而且可以跳上去（第一个踏板没有被占据）
			//找到上面猴子数量最少的那一个梯子
			if (monkey.direction.equals("L->R")) {
				int min = Integer.MAX_VALUE;
				int k = 0;
				for (i = 0;i < n;i++) {
					Ladder ladder = ladders.get(i);
					if(ladder.monkeyNum > 0 && ladder.state[0] == null) {
						if (ladder.monkeyNum < min) {
							min = ladder.monkeyNum;
							k = i;
						}
					}
				}
				if (min != Integer.MAX_VALUE) {
					ladders.get(k).monkeyNum++;
					return ladders.get(k);
				}
			} else {
				int min = Integer.MIN_VALUE;
				int k = 0;
				for (i = 0;i < n;i++) {
					Ladder ladder = ladders.get(i);
					if(ladder.monkeyNum < 0 && ladder.state[ladder.h - 1] == null) {
						if (ladder.monkeyNum > min) {
							min = ladder.monkeyNum;
							k = i;
						}
					}
				}
				if (min != Integer.MIN_VALUE) {
					ladders.get(k).monkeyNum--;
					return ladders.get(k);
				}
			}
		}
		
		return null;
	}
}
