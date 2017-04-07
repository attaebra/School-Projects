import java.util.Scanner;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.*;
/**
*This program returns an ArrayList from a method, passes it into a method,
*and searches through it for a string and deletes the last
*instance of it if it appears more than once.
*/
public class Lab9{
	public static void main(String[] args){
	boolean invalid = true;
	int amount = 0;
	String print = "Please enter string ";
	
	Scanner keyboard = new Scanner(System.in);
	ArrayList<String> StringHolder = new ArrayList<String>();
		//prevents bad input for number of strings
		do{
			try{
			System.out.print("\nHow many strings would you like to enter? ");
			amount = keyboard.nextInt();
				if(amount < 0){
					System.out.print("You must enter a number greater than zero, try again.");
				}
				else{
					break;
				}
			}
			catch(InputMismatchException ime){
			System.out.print("You must enter a number.");
			keyboard.nextLine();
			continue;
			}
		}while(invalid);
		
		//calls method to fill string holder with strings
		StringHolder = getStrings(amount, print);
		System.out.println(StringHolder);		
		
		//cleans System.in string
		keyboard.nextLine();
		System.out.print("Enter the string you would like to search for: ");
		String search = keyboard.nextLine();
		
		//calls method to count amount of times string is in ArrayList
		int times = countValues(StringHolder, search);
		System.out.println("The string appears "+times+" times");
		
		//Deletes last instance of the string
		if (times > 1){
			int delete = StringHolder.lastIndexOf(search);
			StringHolder.remove(delete);
		}
		System.out.println(StringHolder);
	}
	
	/**
	*Prompts the user for the strings to fill array
	*@param Integer the amount of strings the user would like to enter into ArrayList
	*@param String the string to prompt user
	*@return The filled ArrayList
	*/
	public static ArrayList<String> getStrings(int amount, String print){
		Scanner kb = new Scanner(System.in);
		ArrayList<String> StringGuy = new ArrayList<String>();
			for (int i=0; i < amount; i++) {
				System.out.print(print+i+": ");
				String holder = kb.nextLine();
				StringGuy.add(holder);
			}
		return(StringGuy);
	}
	/**
	*Counts the amount of times the chosen string is in the ArrayList
	*@param ArrayList the array of strings built beforehand
	*@param String the string that the user would like to search for in the ArrayList 
	*@return The number of times the string appears in the list
	*/
	public static int countValues(ArrayList<String> StringHolder, String search){
			int count = 0;
			for (int i = 0; i < StringHolder.size(); i++) {
				if (StringHolder.get(i).equals(search)) {
					count++;
				}
			}
			return count;
		}
}