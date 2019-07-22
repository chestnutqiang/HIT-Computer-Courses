package lab6;

import java.util.List;
import logs.Mylog;

public class Monkey implements Runnable{
	int time;
	int bornTime;
	int id;
	String direction;
	int speed;
	River river;
	/*
	 * RI:
	 * time：为了表示猴子距离出生已经过去多少秒.
	 * bornTime:猴子的出生时刻
	 * id:猴子的id
	 * direction:猴子的方向，1表示从左向右，0表示从右向左
	 * speed:猴子的速度
	 * river:要过的河
	 * 
	 * AF:
	 * 表示这个应用中的猴子
	 */
	
	public static void main(String[] args) {
		River river = new River(1,20);
		for(int i=0;i<4;i++) {
			Monkey monkey = new Monkey(0,i,"L->R",4);
			monkey.setRiver(river);
			Thread t = new Thread(monkey);
			t.start();
		}
	}
	
	public Monkey(int bornTime,int id,String direction,int speed) {
		time = 0;
		this.bornTime = bornTime;
		this.id = id;
		this.direction = direction;
		this.speed = speed;
	}
	
	public void setRiver(River river) {
		this.river = river;
	}

	/*
	 *启动猴子的过河线程
	 */
	@Override
	public void run() {
		
		//每只猴子选取特定的过河策略
		//Strategy strategy = new Strategy2();
		Strategy strategy = new Strategy2();
		
		Ladder ladder = strategy.strategy(this);
		while(ladder == null) {
			try {
				Thread.sleep(1000);
				time++;
				if (direction.equals("L->R")) {
					Mylog.logger.info("猴子"+id+"距离出生 "+time+"s:"+" 正在左岸等待");
				} else {
					Mylog.logger.info("猴子"+id+"距离出生 "+time+"s:"+" 正在右岸等待");
				}
				
				ladder = strategy.strategy(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if(direction.equals("L->R")) {
				goLtoR(ladder);
				ladder.monkeyNum--;//从左向右的猴子数量-1
			} else {
				goRtoL(ladder);
				ladder.monkeyNum++;//从右向左的猴子数量-1；
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/*
	 * param:
	 * ladder:这个猴子选择上的梯子
	 * 
	 *这个猴子是从左向右过河的，当这个猴子选择上这个梯子时，描述这个猴子每秒钟过河的状态
	 */
	public int goLtoR(Ladder ladder) throws InterruptedException {
		List<Ladder> ladders = river.ladders;
		int index = 0;
		int i;
		
		//第一秒跳上第一个踏板
		ladder.state[0] = this;
		time++;
		Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder) +" 在踏板"+(index+1));
		Thread.sleep(1000);
		
		while (index < ladder.h) {
			i = index +1;
			while (i<ladder.h && i < index + speed && ladder.state[i] == null) {
				i++;
			}
			if(i >= ladder.h) {
				//Thread.sleep(1000);
				time++;
				ladder.state[index] = null;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+" 过河成功");
				break;
			} else if(i >= index +speed) {
				time++;
				ladder.state[index + speed] = this;
				ladder.state[index] = null;
				index += speed;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder)+" 在踏板"+(index+1));
				Thread.sleep(1000);
			} else if(ladder.state[i] != null) {
				time++;
				i--;
				ladder.state[index] = null;
				ladder.state[i] = this;
				index =i;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder)+" 在踏板"+(index+1));
				Thread.sleep(1000);
			}
		}
		return time + bornTime;
	}
	
	/*
	 *  param:
	 * ladder:这个猴子选择上的梯子
	 * 
	 *  这个猴子是从右向左过河的，当这个猴子选择上这个梯子时，描述这个猴子每秒钟过河的状态
	 */
	public int goRtoL(Ladder ladder) throws InterruptedException {
		List<Ladder> ladders = river.ladders;
		int index = ladder.h - 1;
		int i;
		
		//第一秒跳上第一个踏板
		ladder.state[ladder.h - 1] = this;
		time++;
		Thread.sleep(1000);
		Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder) +" 在踏板"+(index+1));
		
		
		while (index >= 0) {
			i = index - 1;
			while (i >=0 && i > index - speed && ladder.state[i] == null) {
				i--;
			}
			if(i < 0) {
				Thread.sleep(1000);
				time++;
				ladder.state[index] = null;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+" 过河成功");
				break;
			} else if(i <= index - speed) {
				Thread.sleep(1000);
				time++;
				ladder.state[index - speed] = this;
				ladder.state[index] = null;
				index -= speed;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder)+" 在踏板"+(index+1));
			} else if(ladder.state[i] != null) {
				Thread.sleep(1000);
				time++;
				i++;
				ladder.state[index] = null;
				ladder.state[i] = this;
				index =i;
				Mylog.logger.info("猴子"+id+" 距离出生 "+time+"s:"+"在梯子 "+ladders.indexOf(ladder)+" 在踏板"+(index+1));
			}
		}
		return time + bornTime;
	}
}
