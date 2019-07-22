package SocialNetworkCircle;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import APIs.Difference;
import APIs.SocialNetworkCircleAPI;
import applications.SocialNetworkCircle;
import constractFromTxt.ConstractSocialNetwork;
import physicalObject.Friend;



public class SocialNetworkCircleAPITest {

	SocialNetworkCircle network,network1;
	SocialNetworkCircleAPI caculate=new SocialNetworkCircleAPI();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetObjectDistributionEntropy() throws IOException {
		network=new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
		assertEquals(true,caculate.getObjectDistributionEntropy(network)==1.2148896539491205);
		SocialNetworkCircle network1=new ConstractSocialNetwork("src/TXT/SocialNetworkCircle_Medium.txt").Constract();
		assertEquals(true,caculate.getObjectDistributionEntropy(network1)==0.3982203402500038);
	}

	@Test
	public void testGetLogicalDistance() throws IOException {
		network=new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
		List<Friend> list=network.getFriends();
		Friend f1=list.get(4),f2=list.get(1),f3=list.get(8);
		assertEquals(2,caculate.getLogicalDistance(network, f2, f1));
		assertEquals(3,caculate.getLogicalDistance(network, f1, f3));
		assertEquals(3,caculate.getLogicalDistance(network, f2, f3));
	}

	@Test
	public void testGetDifference() throws IOException {
		network=new ConstractSocialNetwork("src/TXT/SocialNetworkCircle.txt").Constract();
		network1=new ConstractSocialNetwork("src/TXT/SocialNetworkCircle_Medium.txt").Constract();
		Difference D=caculate.getDifference(network, network1);
		assertEquals(1,D.getTrackNum());
		List<Friend> f1=D.getObject1();
		assertEquals(9,f1.size());
		List<Integer> diff=D.getObjectNum();
		assertEquals(-75,(int)diff.get(0));
	}

}
