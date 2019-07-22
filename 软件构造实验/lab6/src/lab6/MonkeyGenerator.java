package lab6;

import java.util.ArrayList;
import java.util.List;

public class MonkeyGenerator {
	static int id = 0;
	int t;
	int k;
	int MV;
	int N;
	List<Thread> threads;
	List<Monkey> monkeys;
	/*
	 * RI:
	 * id:猴子的ID
	 * t:每次生产猴子的时间周期
	 * k:每次生产猴子的数量
	 * MV:猴子的最大速度
	 * N:生产猴子的总量
	 * 
	 * AF：
	 * 猴子产生器
	 */
	
	public MonkeyGenerator(int t,int k,int MV,int N) {
		this.t = t;
		this.k = k;
		this.MV = MV;
		this.N = N;
		threads = new ArrayList<Thread>();
		monkeys = new ArrayList<Monkey>();
	}
	
	/*
	 * @param:
	 * time:要产生猴子的时刻
	 * 
	 * @return：
	 * 在这个时刻随机产生的一只猴子
	 */
	public Monkey generator(int time) {
		id++;
		String direction = Math.random()>0.5?"L->R":"R->L";
		
		//猴子的速度差异非常大的代码：
		//int speed = Math.random()>0.5?1:10;
		
		int speed = (int) (Math.ceil(Math.random()*MV));
		Monkey m = new Monkey(time,id,direction,speed);
		return m;
	}
	
	/*
	 * @param:
	 * river:猴子要过的河
	 * 
	 * spec:
	 * 利用猴子产生器产生猴子并启动猴子过河的线程
	 */
	public void born(River river) throws InterruptedException {
		int time = 0;
		int count = N;
		//生成N个猴子线程并启动
		while (count >= 0) {
			for(int i =0;i < Math.min(k,count);i++) {
				Monkey monkey = generator(time);
				monkey.setRiver(river);
				monkeys.add(monkey);
				Thread t = new Thread(monkey);
				threads.add(t);
				t.start();
			}
			time += t;
			count -= k;
			try {
				Thread.sleep(1000*t);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		River river = new River(3,20);//n,h
		MonkeyGenerator generator = new MonkeyGenerator(2,4,10,20);//t,k,mv,N
		generator.born(river);
		
		//使所有的猴子线程都结束之后再启动主线程
		//为了计算吞吐率和公平性
		boolean live = true;
		while (live) {
			live = false;
			for (Thread t:generator.threads) {
				if (t.isAlive()) {
					live = true;
					break;
				}
			}
			if (live) {
				Thread.sleep(1000);
			}
		}
		
		Caculate caculate = new Caculate(generator.N);
		
		//计算吞吐率
		caculate.caculateTh(generator.monkeys);
		
		//计算公平性
		caculate.caculateFair(generator.monkeys);
		
	}
}
