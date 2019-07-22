package lab6;

import java.util.List;

import logs.Mylog;

public class Caculate {
	int N;
	
	
	public Caculate(int N) {
		this.N = N;
	}
	/*
	 * @param：
	 * monkeys：一组已经过完河的猴子的集合
	 * 
	 * spec：
	 * 计算吞吐率
	 */
	public void caculateTh(List<Monkey> monkeys) {
		int endtime = 0;
		for (Monkey m:monkeys) {
			System.out.println("id "+m.id+" 出生时刻 "+m.bornTime+" 过河时间 "+m.time);
			int time1 = m.bornTime + m.time;
			endtime = Math.max(endtime, time1);
		}
		double th = (double)N / endtime;
		Mylog.logger.info("吞吐率是 "+th);
	}
	
	/*
	 * @param：
	 * monkeys：一组已经过完河的猴子的集合
	 * 
	 * spec：
	 * 计算公平性
	 */
	public void caculateFair(List<Monkey> monkeys) {
		int size = monkeys.size();
		int sum = size*(size - 1);
		int count = 0;
		for (Monkey m1:monkeys) {
			for(Monkey m2:monkeys) {
				if(m1 == m2) {
					continue;
				} else {
					//计算两者的出生时间差
					int temp1 = m1.bornTime - m2.bornTime;
					//计算两者的到达时间差
					int temp2 = (m1.bornTime + m1.time) - (m2.bornTime + m2.time);
					
					//认为是公平的情况有以下几种
					//1.两者产生时间相同，无论谁先到达均认为公平
					//2.m1比m2产生的早，m1到达的时间不比m2晚
					//3.m1比m2产生的晚，m1到达的时间不比m2早
					if(temp1 == 0 || (temp1 > 0 && temp2 >= 0)||(temp1 < 0 && temp2 <= 0)) {
						count++;
					} else {
						count--;
					}
				}
			}
		}
		double fair = (double)count / sum;
		Mylog.logger.info("这一次过河的公平性是 "+fair);
	}
	
}
