package IO;

import java.io.IOException;

import applications.SocialNetworkCircle;

public interface SocialNetworkIO {
	public SocialNetworkCircle Constract(String filePath);
	
	public void output(SocialNetworkCircle system) throws IOException;
}
