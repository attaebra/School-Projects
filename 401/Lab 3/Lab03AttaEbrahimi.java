/**
The purpose of this program is to calculate a customer's monthly bill.
It asks the user to enter the letter of the package they purchased,
and the number of hours they used. It then displays the total charges
in a currency format.
 */
package lab03attaebrahimi;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Lab03AttaEbrahimi {

    public static void main(String[] args) {
        //This is the creation of my keyboard scanner for the program
          Scanner keyboard = new Scanner(System.in);
          System.out.println("This program calculates a customer's monthly bill based on the\n" +
          "subscription that they chose.\n\n");
          System.out.print ("Which package are you subscribed to? ");
          String subscribedto = keyboard.next();
          
          while (!subscribedto.equalsIgnoreCase("A") && !subscribedto.equalsIgnoreCase("B") && !subscribedto.equalsIgnoreCase("C")){
                System.out.println("Sorry, we only offer packages A, B, and C. Try again.");
                System.out.print ("Which package are you subscribed to? ");
                subscribedto = keyboard.next();   
          }
                switch (subscribedto.toUpperCase()){
                        case "A":
                            //This is the variable that will hold the input for the amount of hours.
                            double hoursused=0;
                            //This try and catch statement will prevent the user from entering anything
                            try{
                                //Found this exception on the internet
                                //nextDouble will throw InputMismatchException
                                //If the next token does not match the Double expression
                                System.out.print("How many hours of service did you use this month? ");
                                hoursused = keyboard.nextDouble();
                                //This while loop prevents the user from entering a number less than zero
                                while (hoursused < 0){
                                    System.out.print("Please enter a number greater than zero! ");
                                    hoursused = keyboard.nextDouble();
                                }
                            }
                            catch (InputMismatchException exception){
                                //Prints "This is not a number" when the user
                                //Inputs something other than a double
                                System.out.println("This is not a number value, the program will end. Try again.");
                                break;
                            }
                            //If the user used less than ten hours, then the cost is $9.95
                            //as stated in the program description for package A
                            if (hoursused <= 10){
                                System.out.println("The cost is $9.95!");
                            }
                            else{
                               double cost = 0;
                               //This calculates the cost by subtracting the amount of hours they used by ten
                               //Since the first 10 hours are free, then multiplying it by two
                               //Because it is two dollars per extra hour, and then adding it to the
                               //Base price
                                  cost = cost + 9.95 + (hoursused - 10)*2.00;
                                  //This is the creation of a currency formatter
                                  NumberFormat formatter = NumberFormat.getCurrencyInstance();
                                  String coststring = formatter.format(cost);
                                  System.out.println("The cost this month is " + coststring + ".");
                            }
                                break;
                        case "B":
                            //Everything for case B is exactly the same as case A
                            //Except I changed the name of the hours variable
                            //As well as the cost per hour and base price
                            double hoursused2=0;
                            try{
                                System.out.print("How many hours of service did you use this month? ");
                                hoursused2 = keyboard.nextDouble();
                                
                                while (hoursused2 < 0){
                                    System.out.print("Please enter a number greater than zero! ");
                                    hoursused = keyboard.nextDouble();
                                }
                            }
                            catch (InputMismatchException exception){
                                System.out.println("This is not a number value, the program will end. Try again.");
                                break;
                            }
                            if (hoursused2 <= 20){
                                System.out.println("The cost is $13.95!");
                            }
                            else{
                               double cost = 0;
                                  cost = cost + 13.95 + (hoursused2 - 20)*1.00;
                                  NumberFormat formatter = NumberFormat.getCurrencyInstance();
                                  String coststring = formatter.format(cost);
                                  System.out.println("The cost this month is " + coststring + ".");
                            }
                                break;
                        case "C":
                            //Case C is the unlimited plan so it is always $19.95, I like case C the best
                            //Because it was easiest to write.
                            System.out.println("The cost this month is $19.95");
                    }      
    }
    
}
