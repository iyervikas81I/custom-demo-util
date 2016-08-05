package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSeekClosestNumberFromArray {
	
	Integer[] intArr;
	public SeekClosestNumberFromArray<Integer> testClass;

	@Before
	public void setUp() throws Exception {
		intArr = new Integer[]{1, 2, 3, 5, -1, 7, 145, -33, 22, 14};
	    testClass = new SeekClosestNumberFromArray<Integer>();
	}

	@Test
	public void findClosestValueToAvgArray() {
		assertEquals(testClass.findClosestValueToAvgArray(intArr), new Integer(14));
		assertNotEquals(testClass.findClosestValueToAvgArray(intArr), new Integer(-14));
	}

}
