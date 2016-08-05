package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestSeekNumberInNXMArray{
	
	Integer[][] intArr;
	public SeekNumberInNXMArray testClass;


	@Before
	public void setUp() throws Exception {
		
		intArr = new Integer[][]{{4,7,12,23,27,34},{6,10,15,24,30,40},{12,15,18,29,32,48},{14,18,21,30,35,54},{20,23,24,35,37,62},{22,27,29,39,40,68},{28,32,33,44,46,76},{30,36,38,48,49,82}};
	    testClass = new SeekNumberInNXMArray();
	}


	@Test
	public void doesGivenNumberExist() {
		assertTrue(testClass.doesGivenNumberExist(intArr,82));
		assertFalse(testClass.doesGivenNumberExist(intArr,41));
	}

}
