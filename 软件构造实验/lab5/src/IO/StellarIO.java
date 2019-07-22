package IO;

import java.io.IOException;

import applications.ConcreteCircularOrbit;

public interface StellarIO {
	public ConcreteCircularOrbit constract(String filePath) throws IOException;
	
	public void output(ConcreteCircularOrbit system) throws IOException;
}
