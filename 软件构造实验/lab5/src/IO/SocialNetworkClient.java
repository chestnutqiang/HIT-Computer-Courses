package IO;

import java.io.IOException;

import applications.SocialNetworkCircle;

public class SocialNetworkClient {
	private SocialNetworkIO strategy;
	
	public SocialNetworkClient(SocialNetworkIO strategy) {
		this.strategy = strategy;
	}
	
	public SocialNetworkCircle Constract(String filePath) {
		return strategy.Constract(filePath);
	}
	
	public void output(SocialNetworkCircle system) throws IOException {
		strategy.output(system);
	}
	
	public static void main(String[] args) throws IOException {
		BufferSocialNetwork bs = new BufferSocialNetwork();
		SocialNetworkClient test = new SocialNetworkClient(bs);
		SocialNetworkCircle system = test.Constract("src/TXT/BigSocialNetworkCircle.txt");
		test.output(system);
	}

}
