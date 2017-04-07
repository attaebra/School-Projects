/**
The purpose of this program is to calculate a customer's monthly bill.
It asks the user to enter the letter of the package they purchased,
and the number of hours they used. It then displays the total charges
in a currency format.
 */
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;




public class ISPProgramFileCapable{

    public static void main(String[] args) throws IOException {
		boolean invalid = true, fileboolean = true;
		String again = " ";
		//Repeats program
		do{
			Scanner keyboard = new Scanner(System.in);
			NumberFormat formatter = NumberFormat.getCurrencyInstance();

			String filename = "empty";
			File ispData = new File(filename);
		//Prevents bad input for filename
		do{
			System.out.print("Data file of prior internet usage: ");
			filename = keyboard.nextLine();
			ispData = new File(filename);
			try{
				if (!ispData.exists()){
					System.out.println("\nThe file does not exist, creating file...");
					ispData.createNewFile();
					System.out.println("The file was created successfully!\n");
					fileboolean = false;
				}
				else{
					break;
				}
			}
			catch(IOException e){
				System.out.println("\nYou must enter a valid file name!\n");
				continue;
			}
		}while(fileboolean == true);
			

			//Counting variables for length of file to set array.
			int count = 0;
			int count1 = 0;
			Scanner inFile = new Scanner(ispData);
			Scanner countFile = new Scanner(ispData);
			do{
				if(countFile.hasNext()){
					count1++;
					countFile.next();
					countFile.nextDouble();
					countFile.nextDouble();
				}
				else{
					break;
				}
			}while(invalid);
			countFile.close();
			
			count1 = count1*3;
			
			//Array that gets populated from file
			String[] subscription1 = new String[count1];
			double[] time = new double[count1];
			double[] price = new double[count1];
			int a = 0;
			while(inFile.hasNext()){
				subscription1[a] = inFile.next();
				time[a] = inFile.nextDouble();
				price[a] = inFile.nextDouble();
				a++;
				count++;
			}
			double totalprice = 0;
			double totaltime = 0;
			//Calculates total price and time from array
			for(int b = 0; b < count; b++){
				totaltime = totaltime + time[b];
				totalprice = totalprice + price[b];
			}
			//Displays the statistics of file if there is more than one run in file
			if (count > 1){
			double averagetime = totaltime/count;
			double averagepaid = totalprice/count;
			String averagepaidstring = formatter.format(averagepaid);
			String totalpaidstring = formatter.format(totalprice);
			
			System.out.println("Usage history:");
			System.out.printf("\n\tAverage hours used: %.1f", averagetime);
			System.out.println("\n\tAverage paid: " + averagepaidstring);
			System.out.printf("\n\tTotal hours used: %.1f", totaltime);
			System.out.println("\n\tTotal paid: " + totalpaidstring);
			}
			//Appends to file
			FileWriter fw = new FileWriter("ISP.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			
			  //Beginning of actual program
			  System.out.println("\n\nThis program calculates a customer's monthly bill based on the\n" +
			  "subscription that they chose.\n");
			  System.out.print ("Which package are you subscribed to? ");
			  String subscribedto = keyboard.next();
			//Prevents user from entering invalid choice
			 while(!subscribedto.equalsIgnoreCase("A") && !subscribedto.equalsIgnoreCase("B") && !subscribedto.equalsIgnoreCase("C")){
					System.out.println("Sorry, we only offer packages A, B, and C. Try again.");
					System.out.print ("Which package are you subscribed to? ");
					subscribedto = keyboard.next();   
			 }
					switch (subscribedto.toUpperCase()){
							case "A":
								double hoursused=0;
								//Do and try prevent bad input in hours
								do{
									try{

										System.out.print("How many hours of service did you use this month? ");
										hoursused = keyboard.nextDouble();
										if(hoursused < 0){
											System.out.println("Please enter a number greater than zero!");
											continue;
										}
										else{
											break;
										}
									}
									catch (InputMismatchException exception){
										System.out.println("This is not a number value, try again.");
										keyboard.nextLine();
										continue;
									}
								}while(invalid);
									//Calculates cost if less than or equal to ten hrs.
									if (hoursused <= 10){
										System.out.println("The cost is $9.95!");
										pw.println(subscribedto + " " + hoursused + " " + 9.95);
										pw.close();
									}
									//Calculates if greater than ten
									else{
									   double cost = 0;
										  cost = cost + 9.95 + (hoursused - 10)*2.00;
										  String coststring = formatter.format(cost);
										  System.out.println("The cost this month is " + coststring + ".");
										  pw.println(subscribedto + " " + hoursused + " " + cost);
										  pw.close();
								}
									break;
							case "B":
								//Everything the same as A
								double hoursused2=0;
							do{
								try{
									System.out.print("\nHow many hours of service did you use this month? ");
									hoursused2 = keyboard.nextDouble();
									
									if(hoursused2 < 0){
										System.out.print("Please enter a number greater than zero!");
										continue;
									}
									else{
										break;
									}
								}
								catch (InputMismatchException exception){
									System.out.println("This is not a number value, the program will end. Try again.");
									break;
								}
							}while(invalid);
								if(hoursused2 <= 20){
									System.out.println("The cost is $13.95!");
									pw.println(subscribedto + " " + hoursused2 + " " + 13.95);
									pw.close();
								}
								else{
								   double cost = 0;
									  cost = cost + 13.95 + (hoursused2 - 20)*1.00;
									  String coststring = formatter.format(cost);
									  System.out.println("The cost this month is " + coststring + ".");
									  pw.println(subscribedto + " " + hoursused2 + " " + cost);
									  pw.close();
								}
								break;
							case "C":
								//Mostly the same as A, stores variables for file purposes only.
								double hoursused3=0;
								try{
									System.out.print("How many hours of service did you use this month? ");
									hoursused3 = keyboard.nextDouble();
									
									while (hoursused3 < 0){
										System.out.print("Please enter a number greater than zero! ");
										hoursused = keyboard.nextDouble();
									}
								}
								catch (InputMismatchException exception){
									System.out.println("This is not a number value, the program will end. Try again.");
									break;
								}
								//Case C is the unlimited plan so it is always $19.95, I like case C the best
								//Because it was easiest to write.
								System.out.println("The cost this month is $19.95");
								pw.println(subscribedto + " " + hoursused3 + " " + 19.95);
								pw.close();
						}
						inFile.close();
						System.out.print("Run again? \"Y\" or \"N\": ");
						again = keyboard.next();
						while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
							System.out.print("Please enter \"Y\" or \"N\": ");
							again = keyboard.next();
						}
    }while(again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n"));
    }
}
