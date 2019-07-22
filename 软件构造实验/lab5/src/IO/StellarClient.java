package IO;

import java.io.IOException;

import applications.ConcreteCircularOrbit;

public class StellarClient {
	private StellarIO strategy;

	public StellarClient(StellarIO strategy) {
		this.strategy = strategy;
	}
	
	public ConcreteCircularOrbit constract(String filePath) throws IOException {
		return strategy.constract(filePath);
	}
	
	public void output(ConcreteCircularOrbit system) throws IOException {
		strategy.output(system);
	}
	
	public static void main(String[] args) throws IOException {
		BufferStellarIO bio = new BufferStellarIO();
		StellarClient test = new StellarClient(bio);
		ConcreteCircularOrbit system = test.constract("src/TXT/StellarSystem.txt");
		test.output(system);
	}

}
