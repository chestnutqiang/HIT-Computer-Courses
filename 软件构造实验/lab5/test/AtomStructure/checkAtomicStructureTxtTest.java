package AtomStructure;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import checkTxt.checkAtomicStructureTxt;
import myExceptions.IncorrectDependencyException;
import myExceptions.SyntaxException;

public class checkAtomicStructureTxtTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testCheck() {
    try {
      checkAtomicStructureTxt.check("src/TXT/wrongAtomicSyntax.txt");
    } catch (Exception e) {
      assertTrue(e instanceof SyntaxException);
    }
    try {
      checkAtomicStructureTxt.check("src/TXT/wrongAtomicSyntax2.txt");
    } catch (Exception e) {
      assertTrue(e instanceof SyntaxException);
    }
    try {
      checkAtomicStructureTxt.check("src/TXT/wrongAtomicSyntax3.txt");
    } catch (Exception e) {
      assertTrue(e instanceof SyntaxException);
    }
    try {
      checkAtomicStructureTxt.check("src/TXT/wrongAtomicDependency.txt");
    } catch (Exception e) {
      assertTrue(e instanceof IncorrectDependencyException);
    }
    
  }

}
