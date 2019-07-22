package lab6;

public interface Strategy {
	/*
	 * @param：
	 * monkey：一个要过河的猴子对象
	 * 
	 * @return：
	 * 猴子要选择的梯子
	 */
	public Ladder strategy(Monkey monkey);
}
