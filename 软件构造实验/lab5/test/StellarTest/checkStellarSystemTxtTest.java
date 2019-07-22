package StellarTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import checkTxt.checkStellarSystemTxt;
import myExceptions.ParameterNumberException;
import myExceptions.SyntaxException;
import myExceptions.WrongAngleException;
import myExceptions.sameTagException;

public class checkStellarSystemTxtTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testCheck() { 
    try {
      checkStellarSystemTxt.check("src/TXT/wrongStellarSyntax.txt");
    } catch (Exception e) {
      assertTrue(e instanceof SyntaxException);
    }
    try {
      checkStellarSystemTxt.check("src/TXT/wrongStellarParameter.txt");
    } catch (Exception e) {
      assertTrue(e instanceof ParameterNumberException);
    }
    try {
      checkStellarSystemTxt.check("src/TXT/wrongStellarSameTag.txt");
    } catch (Exception e) {
      assertTrue(e instanceof sameTagException);
    }
    try {
      checkStellarSystemTxt.check("src/TXT/wrongStellarAngle.txt");
    } catch (Exception e) {
      assertTrue(e instanceof WrongAngleException);
    }
  }

}
