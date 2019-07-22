package IO;

import java.io.IOException;

import applications.ConcreteCircularOrbit;

public class StellarIOcontest {
	static StellarClient test1;
	static StellarClient test2;
	static StellarClient test3;
	
	
	public StellarIOcontest() {
		BufferStellarIO temp1 = new BufferStellarIO();
		NioStellarIO temp2 = new NioStellarIO();
		ScannerStellarIO temp3 = new ScannerStellarIO();
		test1 = new StellarClient(temp1);
		test2 = new StellarClient(temp2);
		test3 = new StellarClient(temp3);
	}

	public static void main(String[] args) throws IOException {
		StellarIOcontest contest = new StellarIOcontest();
		contest.inputContest();
		contest.outputContest();
	}
		
	
	static void inputContest() throws IOException {
		long start = System.currentTimeMillis();
		test1.constract("src/TXT/BigStellarSystem.txt");
		long time1 = System.currentTimeMillis();
		System.out.println("buffer的输入时间是  "+(time1-start));
		test2.constract("src/TXT/BigStellarSystem.txt");
		long time2 = System.currentTimeMillis();
		System.out.println("nio的输入时间是  "+(time2-time1));
		test3.constract("src/TXT/BigStellarSystem.txt");
		long time3 = System.currentTimeMillis();
		System.out.println("scanner的输入时间是  "+(time3-time2));
	}
	
	static void outputContest() throws IOException {
		ConcreteCircularOrbit system = test1.constract("src/TXT/BigStellarSystem.txt");
		long start = System.currentTimeMillis();
		test1.output(system);
		long time1 = System.currentTimeMillis();
		System.out.println("channel的输出时间是  "+(time1-start));
		test2.output(system);
		long time2 = System.currentTimeMillis();
		System.out.println("nio的输出时间是  "+(time2-time1));
		test3.output(system);
		long time3 = System.currentTimeMillis();
		System.out.println("buffer的输出时间是  "+(time3-time2));
	}

}
