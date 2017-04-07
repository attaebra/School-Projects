/**
This program tests methods, one gets a number from the user,
the second gets a file name from the user, and the third opens
the file and counts the line.
*/

import java.text.NumberFormat;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Methods{
	public static void main(String[] args) throws FileNotFoundException {
		
		final int LOWER_BOUND = 0;
		final int UPPER_BOUND = 100;
		//Call the getValidNumber method, passing it the values LOWER_BOUND and UPPER_BOUND.
		//Take the returned value and store it in a variable called num.
		//Print out the value of num (here in the main method)
		double num = getValidNumber(LOWER_BOUND, UPPER_BOUND);
		String filename = getFilename();
		int numLines = countFileLines(filename);
		System.out.println("The file "+filename+" has "+numLines+" lines.");
	}
    /**
     * Method validates a number between a constant upper and lower bound
     * @param upper bound and lower bound of the number that shall be validated
     * @return the validated number
     */
	public static double getValidNumber(double lowerBound, double upperBound){
		boolean invalid = true;
		Scanner numberScan = new Scanner(System.in);
		do{			
			try{
				System.out.print("Please enter a number between " + lowerBound + " and " + upperBound + ": ");
				double num = numberScan.nextDouble();
				if (num > lowerBound && num < upperBound){
					invalid = false;
					return num;
				}
				else{
					System.out.println("Your number is invalid.");
				}
			}
			catch(InputMismatchException ime){
				System.out.println("Your number is invalid.");
				numberScan.next();
				invalid = true;
			}
		}while(invalid);
		//Does nothing and is not reached, only for compiling purposes
		return 0;
	}
	

	/**
	 *Asks and validates a file name for the user
	 * @return String object for the file name the user enters
	 */
	public static String getFilename() {
		Scanner keyboard = new Scanner(System.in);
		Scanner infile = null;
		String filename = null;
		boolean invalid;
		do {
			System.out.print("Please enter a file name: ");
			filename = keyboard.nextLine();
			try {
				File file = new File(filename);
				infile = new Scanner(file);
				invalid = false;
			}
			catch (FileNotFoundException fnfe) {
				System.out.println("File does not exist.");
				invalid = true;
			}
		} while (invalid);
		return filename;
	}
	/**
     * Method evaluates the length of the file
     * @param String for file name
     * @return the length of file (amount of lines)
	 * @throws IllegalArgumentException If file contains less or more than 30 numbers.
     */
	public static int countFileLines(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner inFile = new Scanner(file);
		int i = 0;
		while (inFile.hasNextLine()){
			inFile.nextLine();
			i++;
		}
		return i;
	}
}