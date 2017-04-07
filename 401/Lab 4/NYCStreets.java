//Atta Ebrahimi, ate9@pitt.edu
/**
This program determines whether a the street is eastbound or westbound (one-way street in NYC).  The program takes an integer for the street number and it must be between 1 and 155 (inclusive).  The program will then display whether the street is eastbound or westbound.
 */

import java.util.Scanner;
import java.util.InputMismatchException;
public class NYCStreets {
    public static void main(String[] args) {
		//Invalid boolean created to prevent letter input
		//from being a problem for both the base and log variable
		//that the user is asked to input. (while loop will not repeat without it)
		boolean invalid = true;
		int street = 0;
        System.out.println("This program will tell you the direction of a one-way street in New York City.");
        Scanner keyboard = new Scanner(System.in);
		String runagain = "y";
//do loop repeats whole program
do{
	//This do loop repeats the process of asking for input when the user enters
	//something they are not suppose to.
	do{
		try{
			//get street number
			System.out.print("Please enter the street number: ");
			street = keyboard.nextInt();
				if (street < 1 || street > 155) {
				//Invalid, street must be between 1 and 155
					System.out.println("The street number must be between 1 and 155, try again!");
					continue;
				}
				//If input is okay, breaks out of loop
				else{
					break;
				}
			}
			//If we try, and fail the catch exception will throw us back to the top
			//in the case that the user enters a sentence or non-numerical input.
			catch(InputMismatchException ime){
				System.out.println("You must enter a number between 1 and 155, try again!");
				keyboard.nextLine();
				continue;
			}
	}while(invalid);
        
        //Determine whether street is east or west.
        if (street % 2 == 0) {
        //even, so eastbound
            System.out.println("The street is eastbound.");
        }
		//odd, so westbound
        else {
            System.out.println("The street is westbound.");
        }
			System.out.print("Would you like to flip again (Y or N?) ");
	//Asks the user if they want to run again, if they do it does.
	runagain = keyboard.next();
	while (!runagain.equalsIgnoreCase("y") && !runagain.equalsIgnoreCase("n")){
		System.out.print("You must enter Y or N, try again!: ");
		runagain = keyboard.next();
		}
}while(runagain.equalsIgnoreCase("y"));	
}
}