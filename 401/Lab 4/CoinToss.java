//Atta Ebrahimi, ate9@pitt.edu
/**
This program offers the user to play a coin flip game.
It asks the user how many times they want to flip an
unbiased coin and flips the coin that many times.
At the end it tells them how many times heads and tails showed
up.
*/

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CoinToss {
    public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		//Invalid boolean created to prevent letter input when asking how many times to flip.
		boolean invalid = true;
		//j is a dummy variable that represents the amount of times heads appears.
		int j = 0;
		//k is a dummy variable that represents the amount of times heads appears.
		int k = 0;
		//times is the amount of times the user flips a coin.
		int times = 0;
		//This do loop repeats the process of asking for input when the user enters
		//something they are not suppose to.
		do{
			//We try to ask the user for a integer input greater than 0
			//for the amount of times they want to flip
			try{
				System.out.print("How many times should I flip a coin? ");
				times = keyboard.nextInt();
					//"If" the user enters a number less than or equal to zero, they must try again
					// because that is bad input.
					if(times <= 0){
						System.out.println("You must enter a number greater than 0, try again!");
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
				System.out.println("You must enter a number greater than 0, try again!");
				keyboard.nextLine();
				continue;
			}
		}while(invalid);
		
		//i is a dummy variable that causes the coin to flip as long as it is less than the amount of times
		// the user chose to flip (including 0, the amount the user chose is met)
		for (int i = 0; i < times; i++){
			//This creates the pseudo-random generator
			Random randomNum = new Random();
					//The result of the coin flip uses the random generator created above
					//and comes up with a number that is either 0 or 1.
					int result = randomNum.nextInt(2);
						//If the result is 0, j, or the number of heads, is increased by one
						if (result == 0){
							j++;
						}
						//If the result is not 0, it is 1. When the result is 1 k, or the number of tails, is increased by one.
						//This whole process repeats until the number of times is met.
						else{
							k++;
						}
		}
		//This will display the amount of heads and tails that were flipped in the program.
		System.out.println("You flipped heads " + j + " times and tails " + k + " times!");
	}
}