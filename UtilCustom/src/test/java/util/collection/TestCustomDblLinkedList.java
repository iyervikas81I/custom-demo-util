package util.collection;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCustomDblLinkedList {
	
	CustomDblLinkedList<String> _customCollection;
	String _concatAllItem = "";

	@Before
	public void setUp() throws Exception {
		
		_customCollection = new CustomDblLinkedList<>();
		
		_customCollection.addFirst("O");
		_customCollection.addFirst("M");
		_customCollection.addFirst("G");
		//FOR-EACH capability
		for(String item : _customCollection){
			_concatAllItem+=item;
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testForEachIteration() {
		assertEquals(_concatAllItem,"GMO");
	}

	@Test
	public void testAddFirst() {
		assertEquals(_customCollection.getFirstElement(),"G");
	}
	
	@Test
	public void testAddLast() {
		assertEquals(_customCollection.getLastElement(),"O");
	}
	
	@Test
	public void testRemoveFirst() {
		assertEquals(_customCollection.removeFirst(),"G");
	}

	
	@Test
	public void testRemoveLast() {
		assertEquals(_customCollection.removeLast(),"O");
	}
	
	@Test
	public void testRemoveElement() {
		_customCollection.remove("O");
		assertEquals(_customCollection.size,2);
	}
	@Test
	public void testClear() {
		_customCollection.clear();
		assertEquals(_customCollection.size,0);
	}
}
