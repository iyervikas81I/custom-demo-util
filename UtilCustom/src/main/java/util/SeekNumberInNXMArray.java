package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SeekNumberInNXMArray {

	public static final Logger seekNumberInNXMArrayLogger = Logger.getLogger(SeekNumberInNXMArray.class);

	public static void main(String[] args) {

		if(System.getProperty("INPUT_VALUE") == null || System.getProperty("INPUT_VALUE").equals("")){
			seekNumberInNXMArrayLogger.error("INPUT_VALUE needs to be provided as a VM Argument");
			System.exit(-1);
		}
		Scanner inputPrompt = null;
		ArrayList<ArrayList<Integer>> _2dArrayList = new ArrayList<>();
		try{
			inputPrompt = new Scanner(System.in);
			seekNumberInNXMArrayLogger.setLevel(Level.toLevel(System.getProperty("DEBUG_LEVEL","DEBUG")));
			seekNumberInNXMArrayLogger.info("1) enter value(s) separated by comma for each row in array");
			seekNumberInNXMArrayLogger.info("2) Press enter, repeat Step 1 with different values. ");
			seekNumberInNXMArrayLogger.info("3) Press enter,Press ctrl+z when done.");
			String inputValue = "";
			while(inputPrompt.hasNext()){
				inputValue = inputPrompt.nextLine();
				Integer[] eachRowInt = Stream.of(inputValue.split(",\\s*")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new ); //using regex pattern \\s* to ignore whitespace
				ArrayList<Integer> eachRow = new ArrayList<Integer>(Arrays.asList(eachRowInt));
				_2dArrayList.add(eachRow);
			}

			//using lambda expression to convert arraylist<arraylist<Integer>> stream into Integer Array
			Integer[][] _2dArray =  _2dArrayList.stream().map(arrListInt -> arrListInt.toArray(new Integer[0])).toArray(Integer[][]::new);

			SeekNumberInNXMArray numberSeeker2DArrAlgorithm = new SeekNumberInNXMArray();
			seekNumberInNXMArrayLogger.info("Match Found for Input Number " + System.getProperty("INPUT_VALUE") + ":" + numberSeeker2DArrAlgorithm.doesGivenNumberExist(_2dArray,Integer.parseInt(System.getProperty("INPUT_VALUE"))));

		}finally{
			if(inputPrompt != null)
				inputPrompt.close();
		}
		System.exit(0);
	}


   boolean doesGivenNumberExist(Integer[][] _2dArray, int inputValue) {

		/* Divide and Conquer logic as follows:
		 * Since array already sorted, We can perform a row search for the number by eliminating a couple quadrants
		 * Pick up a row in between to begin with and search for the number.
		 * At this point, we can either have all values in the row < input value ( in which case we can ignore top rows)
		 * or we could have found a value > input value ( in which case, we have to search in all rows below current row till previous column or 
		 *	we have to search on all subsequent columns and previous rows
		 *	we can recursively call the same method since the iteration is again over a 2d array, albeit with changed coordinates 
		 */
		return doesGivenNumberExist(_2dArray,0, _2dArray.length-1, 0,  _2dArray[0].length-1, inputValue);
	}

	boolean doesGivenNumberExist(Integer[][] _2dArray,int rowUpper, int rowLower, int colLeft, int colRight, int inputValue){
		boolean doesNbrExist = false;
		//boundary condition: While bumping up the co-ordinates for sub-matrices when no match found, we should not exceed original array length
		if(rowUpper > rowLower || colLeft > colRight){
			return false;
		}
		int centerRow = rowUpper + (rowLower-rowUpper)/2;
		//boundary condition: input number should be in between the top left number and bottom right most number for logic
		if(inputValue >= _2dArray[rowUpper][colLeft] || inputValue <= _2dArray[rowLower][colRight]){
			//fix the row, move along the column
			int _curCol = colLeft;
			//match found
			if(Arrays.asList(_2dArray[centerRow]).contains(inputValue)){
				seekNumberInNXMArrayLogger.info("Match Found for input value:" + inputValue + " at row:" + (centerRow + 1) );
				return true;
			}
			while(_2dArray[centerRow][_curCol] < inputValue && _curCol < colRight){
				_curCol++;
			}
			//At this point, we can either have all values in the row < input value ( in which case we can ignore top rows)
			if(_curCol == colRight) doesNbrExist= doesGivenNumberExist(_2dArray, ++centerRow,rowLower,colLeft,colRight,inputValue);
			//or we could have found a value > input value ( in which case, we have to search in all rows below current row till previous column or 
			//we have to search on all subsequent columns and previous rows
			//we can recursively call the same method since the iteration is again over a 2d array, albeit with changed coordinates
			else doesNbrExist =  doesGivenNumberExist(_2dArray,centerRow + 1,rowLower,colLeft,_curCol -1,inputValue) || 
					doesGivenNumberExist(_2dArray,rowUpper,centerRow - 1,_curCol + 1,colRight,inputValue);
		}
		return doesNbrExist;
	}
}
