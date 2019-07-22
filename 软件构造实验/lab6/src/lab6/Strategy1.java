package lab6;

import java.util.List;

public class Strategy1 implements Strategy{

	/*
	 * @param:
	 * monkey:要选择梯子的猴子对象
	 * 
	 * @return 猴子要选择哪一个梯子
	 * 这里采用的策略是实验手册里面使用的策略3
	 * 优先选择没有猴子的梯子，若所有梯子上都有猴子，则在岸 边等待，直到某个梯子空闲出来
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
			return null;
		}
	}

}
