package util;

import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SeekClosestNumberFromArray<T extends Number> {

	public static final Logger seekClosestNumLogger = Logger.getLogger(SeekClosestNumberFromArray.class);

	/**
	 * Returns an average value of all integers passed as input
	 * 
	 * @param  arrItems  ? extends Number Array
	 * @return double    Average value
	 * @see    Number
	 */

	@SuppressWarnings("unchecked")
	public  double getAverageValue( T... arrItems){
		double avg = 0;
		for(T eachItem : arrItems){
			avg+=eachItem.doubleValue();
		}
		return avg/arrItems.length;
	}

	/**
	 * Finds the array content that is closest to the average value 
	 * of all integers present in the content
	 * 
	 * @param  arrItems  T<? extends Number>  Array
	 * @return T         Closest Item in the Array of T
	 */

	@SuppressWarnings("unchecked")
	public T findClosestValueToAvgArray( T... arrItems){
		double average = getAverageValue(arrItems);
		double difference = Double.MAX_VALUE;
		T closestNumber = null ;
		for(T eachItem : arrItems){
			if(Math.abs(eachItem.doubleValue() - average) <= difference){
				difference = (double) Math.abs(eachItem.doubleValue() - average);
				closestNumber = eachItem;
			}
		}

		return closestNumber;
	}

	public static void main(String[] args) {

		Scanner inputPrompt = null;
		try{
			inputPrompt = new Scanner(System.in);
			seekClosestNumLogger.setLevel(Level.toLevel(System.getProperty("DEBUG_LEVEL","DEBUG")));
			seekClosestNumLogger.info("1) enter value(s) separated by comma");
			seekClosestNumLogger.info("2) Press enter ");
			String inputValue = inputPrompt.nextLine();
			//using lambda expression to convert a String stream into Integer Array
			Integer[] _intArr = Stream.of(inputValue.split(",\\s*")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new );
			SeekClosestNumberFromArray<Integer> seekObj = new SeekClosestNumberFromArray<>();
			seekClosestNumLogger.info("The Array Element closest to the average value of contents is: " + seekObj.findClosestValueToAvgArray(_intArr));
		}catch(NumberFormatException ne){
			seekClosestNumLogger.error(ne);
			System.exit(-1);
		}
		finally{
			if(inputPrompt != null)
				inputPrompt.close();
		}

		System.exit(0);
	}



}
