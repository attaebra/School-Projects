//Atta Ebrahimi, ate9@pitt.edu
/**
This program asks the user for the base of the log and what they would like to take
the logarithm of. It then uses the method of repeated integer division to find the floor
of the log.
*/

import java.util.Scanner;
import java.util.InputMismatchException;

public class LogFloor {
	public static void main (String[] args){
		Scanner keyboard = new Scanner(System.in);
		//base is a variable that holds the input for the base of the log
		//that the user will input.
		int base = 0;
		//log is a variable that holds the value they would like to find
		//the floor of (i.e log10("123456"))
		int log = 0;
		//result is the variable that store the result of the division
		//every time there is a division.
		int result = 0;
		//i is a dummy variable that will count the amount of times the
		//equation to find the floor was done. Effectively giving us
		//the floor of the logarithm
		int i = 0;
		//Invalid boolean created to prevent letter input
		//from being a problem for both the base and log variable
		//that the user is asked to input. (while loop will not repeat without it)
		boolean invalid = true;
		System.out.println("This program will calculate the floor of a logarithm");
		//This do loop repeats the process of asking for input when the user enters
		//something they are not suppose to.
		do{
			//We try to ask the user for input that is greater than 0
			//for the base of the log
			try{
				System.out.print("Please enter the base for your logarithm: ");
				base = keyboard.nextInt();
					//"If" the user enters a number that is less than zero
					//this statement will prevent that from occurring.
					if(base < 1){
						System.out.println("You must enter a number greater than 1, try again!");
						continue;
					}
					//If the user entered acceptable input they can proceed with the rest of the program.
					//This break effectively "breaks" out of the loop that prevents bad input.
					else{
						break;
					}
			}
			//If we try, and fail the catch exception will throw us back to the top
			//in the case that the user enters a sentence or non-numerical input.
			catch(InputMismatchException ime){
				System.out.println("You must enter a number greater than 1!");
				keyboard.nextLine();
				continue;
			}
		}while(invalid);
		//This loop does the exact same thing as above for the variable "log"
		do{
			try{
				System.out.print("Please the value you'd like to find the base of: ");
				log = keyboard.nextInt();
					if(log < 1){
						System.out.println("You must enter a number greater than 1, try again!");
						continue;
					}
					else{
						break;
					}
			}
			catch(InputMismatchException ime){
				System.out.println("You must enter a number greater than 1!");
				keyboard.nextLine();
				continue;
			}
		}while(invalid);
		//This does the first division in finding the log floor
		result = log/base;
		//This loop will do the rest of the divisions
		//as long as the result of the division
		//is greater than result. The loop also adds one
		//To "i" every time there is a division.
		//after the loop finishes i + 1 is the floor of the log.
		for (i = 0; result > base; i++)
		{
			result = result/base;
		}
		//adds 1 to i, to account for the first division we did outside the loop.
		i = i + 1;
		//Displays the floor of the log.
		System.out.println("\nThe floor of the log is " + i);
	}
}