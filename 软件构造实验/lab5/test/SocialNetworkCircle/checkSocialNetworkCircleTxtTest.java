package SocialNetworkCircle;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import checkTxt.checkSocialNetworkCircleTxt;
import myExceptions.IntimacyOutOfRangeException;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.sameTagException;

public class checkSocialNetworkCircleTxtTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testCheck() {
    try {
      checkSocialNetworkCircleTxt.check("src/TXT/wrongSocialnetworkSyntax.txt");
    } catch (Exception e) {
      assertTrue(e instanceof SyntaxException);
    }
    try {
      checkSocialNetworkCircleTxt.check("src/TXT/wrongSocialnetworkParameter.txt");
    } catch (Exception e) {
      assertTrue(e instanceof ParameterNumberException);
    }
    try {
      checkSocialNetworkCircleTxt.check("src/TXT/wrongSocialnetworkIntimacy.txt");
    } catch (Exception e) {
      assertTrue(e instanceof IntimacyOutOfRangeException);
    }
    try {
      checkSocialNetworkCircleTxt.check("src/TXT/wrongSocialnetworkSameTag.txt");
    } catch (Exception e) {
      System.out.println(e.getClass());
      assertTrue(e instanceof sameTagException);
    }
  }

}
