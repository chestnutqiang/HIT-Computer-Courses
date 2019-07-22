package IO;

import java.io.IOException;

import applications.SocialNetworkCircle;

public class SocialNetworkIOcontest {
	static SocialNetworkClient test1;
	static SocialNetworkClient test2;
	static SocialNetworkClient test3;
	static SocialNetworkCircle system;
	
	public SocialNetworkIOcontest() {
		test1 = new SocialNetworkClient(new BufferSocialNetwork());
		test2 = new SocialNetworkClient(new NioSocialNetwork());
		test3 = new SocialNetworkClient(new ScannerSocialNetwork());
	}
	
	public static void main(String[] args) throws IOException {
		SocialNetworkIOcontest test = new SocialNetworkIOcontest();
		test.inputContest();
		test.outputContest();
	}
	
	static void inputContest() throws IOException {
		long start = System.currentTimeMillis();
		system = test1.Constract("src/TXT/BigSocialNetworkCircle.txt");
		long time1 = System.currentTimeMillis();
		System.out.println("buffer的输入时间是  "+(time1-start));
		test2.Constract("src/TXT/BigSocialNetworkCircle.txt");
		long time2 = System.currentTimeMillis();
		System.out.println("nio的输入时间是  "+(time2-time1));
		test3.Constract("src/TXT/BigSocialNetworkCircle.txt");
		long time3 = System.currentTimeMillis();
		System.out.println("scanner的输入时间是  "+(time3-time2));
	}
	
	static void outputContest() throws IOException {
		long start = System.currentTimeMillis();
		test1.output(system);
		long time1 = System.currentTimeMillis();
		System.out.println("channel的输出时间是  "+(time1-start));
		test2.output(system);
		long time2 = System.currentTimeMillis();
		System.out.println("buffer的输出时间是  "+(time2-time1));
		test3.output(system);
		long time3 = System.currentTimeMillis();
		System.out.println("writer的输出时间是  "+(time3-time2));
	}

}
