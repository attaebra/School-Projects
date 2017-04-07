/**
This program allows a user to track inventory for their company. The program asks a user for a file with
inventory information, and allows the user to buy, sell, and change prices of the things that they use at their
company.
*/

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.io.*;


public class Project1{
	
	public static void main(String[] args) throws FileNotFoundException{
	boolean invalid = true, programboolean = true, lampsboolean = true, chairsboolean = true, desksboolean = true;
				Scanner keyboard = new Scanner(System.in);
				NumberFormat currency = NumberFormat.getCurrencyInstance();
			
					String filename = "empty";
					File inventoryData = new File(filename);
					//Inputs file/ensures file exists
					System.out.print("Please enter the inventory file name: ");
					filename = keyboard.nextLine();
					do{
					inventoryData = new File(filename);
							if(!inventoryData.exists()){
								System.out.print("The file does not exist or is invalid, try inputting the correct name: ");
								filename = keyboard.nextLine();
								inventoryData = new File(filename);
							}
							else{
								break;
							}
					}while(invalid);
				//defines most variables (used chair, desk, and lamp)
				Scanner inFile = new Scanner(inventoryData);
				double lampprice = 0, chairsprice = 0, desksprice = 0;
				int lampquantity = 0, chairsquantity = 0, desksquantity = 0, lampsell = 0, lampbuy = 0, chairsell = 0, chairbuy = 0, desksell = 0, deskbuy = 0;
				int lampsellprice = 0, chairsellprice = 0, desksellprice = 0;
				String lamps = " ", chairs = " ", desks = " ", again;
				
				//reads in balance and variables to display
				double balance = inFile.nextDouble();
					lamps = inFile.next();
					lampprice = inFile.nextDouble();
					lampquantity = inFile.nextInt();
					chairs = inFile.next();
					chairsprice = inFile.nextDouble();
					chairsquantity = inFile.nextInt();
					desks = inFile.next();
					desksprice = inFile.nextDouble();
					desksquantity = inFile.nextInt();
		do{
				System.out.printf("\nCurrent balance: $%.2f", balance);
				System.out.printf("\n\t1. %s\t\t (%d at $%.2f)", lamps, lampquantity, lampprice);
				System.out.printf("\n\t2. %s\t\t (%d at $%.2f)", chairs, chairsquantity, chairsprice);
				System.out.printf("\n\t3. %s\t\t (%d at $%.2f)", desks, desksquantity, desksprice);
				System.out.println("\n\t0.Exit");
				
				PrintWriter pw = new PrintWriter(filename);
				int choice = 1;
		do{//prevents program from ending and allows user to go up and down in cases
					//choice for switch case
					try{
						System.out.print("\nPlease enter choice: ");
						choice = keyboard.nextInt();
						if(choice != 1 && choice != 2 && choice != 3 && choice != 0){
							System.out.print("Your choice must be 1, 2, 3, or 0, choose again!");
						}
						else{
							break;
						}
					}
					catch(InputMismatchException ime){
						System.out.println("Your choice must be 1, 2, 3, or 0, choose again!");
						keyboard.nextLine();
						continue;
					}
				}while(invalid);
			//switch into lamps, chairs, desk menu
			switch(choice){

					case 1:
						do{
								//lamps menu
								System.out.printf("\nCurrent balance: $%.2f", balance);
								System.out.printf("\nCurrent quantity: %d", lampquantity);
								System.out.printf("\nCurrent price: $%.2f", lampprice);
								System.out.printf("\n1. Sell %s", lamps);
								System.out.printf("\n2. Buy %s", lamps);
								System.out.println("\n3. Change price");
								System.out.println("0. Return to main menu");
								int lampschoice = -1;
								//choice to buy, sell, or change price
								do{
									try{
										System.out.print("\nPlease enter choice: ");
										lampschoice = keyboard.nextInt();
										if(lampschoice != 1 && lampschoice != 2 && lampschoice != 3 && lampschoice != 0){
											System.out.print("Your choice must be 1, 2, 3, or 0, choose again!");
										}
										else{
											break;
										}
									}
									catch(InputMismatchException ime){
										System.out.println("Your choice must be 1, 2, 3, or 0, choose again!");
										keyboard.nextLine();
										continue;
									}
								}while(invalid);
											switch(lampschoice){
													case 1:
														do{
															try{
																System.out.printf("\n\nAmount to sell (current quantity %d): ", lampquantity);
																lampsell = keyboard.nextInt();
																if(lampsell <= 0){
																	System.out.printf("\nYou must sell more than zero");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.printf("\nYou must sell more than zero");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
																if(lampsell > lampquantity){
																	System.out.print("\nYou are selling more product than you have!\nDo you wish to proceed? (Y or N?) ");
																	again = keyboard.next();
																	while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																		System.out.print("Please enter \"Y\" or \"N\": ");
																		again = keyboard.next();
																	}
																			if(again.equalsIgnoreCase("Y")){
																			lampquantity = lampquantity-lampsell;
																			balance = balance + (lampprice*lampsell);
																			}
																}
																else{
																	//math for selling
																	lampquantity = lampquantity - lampsell;
																	balance = balance + (lampprice*lampsell);
																}
																		//updates file
																		pw.println(balance);
																		pw.println(lamps + " " + lampprice + " " + lampquantity);
																		pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																		pw.println(desks + " " + desksprice + " " + desksquantity);
																		pw.close();
														break;
														case 2:
															do{
																try{
																	System.out.printf("\n\nAmount to buy (current quantity %d): ", lampquantity);
																	lampbuy = keyboard.nextInt();
																	if(lampbuy <= 0){
																		System.out.printf("\nYou must buy more than zero");
																		continue;
																	}
																	else{
																		break;
																	}
																}
																catch(InputMismatchException e){
																	System.out.printf("\nYou must buy more than zero");
																	keyboard.nextLine();
																	continue;
																}
															}while(invalid);
															do{
																try{
																	System.out.print("\nPrice per item: ");
																	lampsellprice = keyboard.nextInt();
																	if(lampsellprice <= 0){
																		System.out.print("\nThe price must be a positive number!");
																		continue;
																	}
																	else{
																		break;
																	}
																}
																catch(InputMismatchException e){
																	System.out.print("\nThe price must be a positive number!");
																	keyboard.nextLine();
																	continue;
																}
															}while(invalid);
																	if((lampbuy*lampsellprice) > balance){
																		System.out.print("\nYou are buying more product than you can afford!\nDo you wish to proceed? (Y or N?) ");
																		again = keyboard.next();
																		while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																			System.out.print("Please enter \"Y\" or \"N\": ");
																			again = keyboard.next();
																		}
																				if(again.equalsIgnoreCase("Y")){
																				//buys if user insists (asks yes or no)
																				lampquantity = lampquantity-lampsell;
																				balance = balance - (lampsellprice*lampbuy);
																				}
																	}
																	else{
																		//buy math
																		lampquantity = lampquantity + lampbuy;
																		balance = balance - (lampsellprice*lampbuy);
																	}
																			//prints to file
																			pw.println(balance);
																			pw.println(lamps + " " + lampprice + " " + lampquantity);
																			pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																			pw.println(desks + " " + desksprice + " " + desksquantity);
																			pw.close();
															break;
													case 3:
														do{
															try{
																//updates price
																System.out.printf("\n\nNew price: ");
																lampprice = keyboard.nextInt();
																if(lampprice <= 0){
																	System.out.print("The price must be greater than zero!");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.print("The price must be greater than zero!");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
															//prints to file
															pw.println(balance);
															pw.println(lamps + " " + lampprice + " " + lampquantity);
															pw.println(chairs + " " + chairsprice + " " + chairsquantity);
															pw.println(desks + " " + desksprice + " " + desksquantity);
															pw.close();
														break;
													case 0:
														//prints to file
														pw.println(balance);
														pw.println(lamps + " " + lampprice + " " + lampquantity);
														pw.println(chairs + " " + chairsprice + " " + chairsquantity);
														pw.println(desks + " " + desksprice + " " + desksquantity);
														pw.close();
														lampsboolean = false;
														break;
											}
								}while(lampsboolean == true);
								break;
					case 2:
						do{
							//each case mimics first one exactly, so it's self explanatory
							System.out.printf("\nCurrent balance: $%.2f", balance);
							System.out.printf("\nCurrent quantity: %d", chairsquantity);
							System.out.printf("\nCurrent price: $%.2f", chairsprice);
							System.out.printf("\n1. Sell %s", chairs);
							System.out.printf("\n2. Buy %s", chairs);
							System.out.println("\n3. Change price");
							System.out.println("0. Return to main menu");
							int chairschoice = -1;
							do{
								try{
									System.out.print("\nPlease enter choice: ");
									chairschoice = keyboard.nextInt();
									if(chairschoice != 1 && chairschoice != 2 && chairschoice != 3 && chairschoice != 0){
										System.out.print("Your choice must be 1, 2, 3, or 0, choose again!");
									}
									else{
										break;
									}
								}
								catch(InputMismatchException ime){
									System.out.println("Your choice must be 1, 2, 3, or 0, choose again!");
									keyboard.nextLine();
									continue;
								}
							}while(invalid);
										switch(chairschoice){
												case 1:
													do{
														try{
															System.out.printf("\n\nAmount to sell (current quantity %d): ", chairsquantity);
															chairsell = keyboard.nextInt();
															if(chairsell <= 0){
																System.out.printf("\nYou must sell more than zero");
																continue;
															}
															else{
																break;
															}
														}
														catch(InputMismatchException e){
															System.out.printf("\nYou must sell more than zero");
															keyboard.nextLine();
															continue;
														}
													}while(invalid);
															if(chairsell > chairsquantity){
																System.out.print("\nYou are selling more product than you have!\nDo you wish to proceed? (Y or N?) ");
																again = keyboard.next();
																while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																	System.out.print("Please enter \"Y\" or \"N\": ");
																	again = keyboard.next();
																}
																		if(again.equalsIgnoreCase("Y")){
																		chairsquantity = chairsquantity-chairsell;
																		balance = balance + (chairsprice*chairsell);
																		}
															}
															else{
																chairsquantity = chairsquantity- chairsell;
																balance = balance + (chairsprice*chairsell);
															}
																	pw.println(balance);
																	pw.println(lamps + " " + lampprice + " " + lampquantity);
																	pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																	pw.println(desks + " " + desksprice + " " + desksquantity);
																	pw.close();
													break;
													case 2:
														do{
															try{
																System.out.printf("\n\nAmount to buy (current quantity %d): ", chairsquantity);
																chairbuy = keyboard.nextInt();
																if(chairbuy <= 0){
																	System.out.printf("\nYou must buy more than zero");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.printf("\nYou must buy more than zero");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
														do{
															try{
																System.out.print("\nPrice per item: ");
																chairsellprice = keyboard.nextInt();
																if(chairsellprice <= 0){
																	System.out.print("\nThe price must be a positive number!");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.print("\nThe price must be a positive number!");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
																if((chairbuy*chairsellprice) > balance){
																	System.out.print("\nYou are buying more product than you can afford!\nDo you wish to proceed? (Y or N?) ");
																	again = keyboard.next();
																	while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																		System.out.print("Please enter \"Y\" or \"N\": ");
																		again = keyboard.next();
																	}
																			if(again.equalsIgnoreCase("Y")){
																			chairsquantity = chairsquantity-chairsell;
																			balance = balance - (chairsellprice*chairbuy);
																			}
																}
																else{
																	chairsquantity = chairsquantity + chairbuy;
																	balance = balance - (chairsellprice*chairbuy);
																}
																		pw.println(balance);
																		pw.println(lamps + " " + lampprice + " " + lampquantity);
																		pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																		pw.println(desks + " " + desksprice + " " + desksquantity);
																		pw.close();
														break;
												case 3:
													do{
														try{
															System.out.printf("\n\nNew price: ");
															chairsprice = keyboard.nextInt();
															if(chairsprice <= 0){
																System.out.print("The price must be greater than zero!");
																continue;
															}
															else{
																break;
															}
														}
														catch(InputMismatchException e){
															System.out.print("The price must be greater than zero!");
															keyboard.nextLine();
															continue;
														}
													}while(invalid);
													
														pw.println(balance);
														pw.println(lamps + " " + lampprice + " " + lampquantity);
														pw.println(chairs + " " + chairsprice + " " + chairsquantity);
														pw.println(desks + " " + desksprice + " " + desksquantity);
														pw.close();
													break;
												case 0:
													pw.println(balance);
													pw.println(lamps + " " + lampprice + " " + lampquantity);
													pw.println(chairs + " " + chairsprice + " " + chairsquantity);
													pw.println(desks + " " + desksprice + " " + desksquantity);
													pw.close();
													chairsboolean = false;
													break;
										}
							}while(chairsboolean == true);
							break;
					case 3:
							do{
								System.out.printf("\nCurrent balance: $%.2f", balance);
								System.out.printf("\nCurrent quantity: %d", desksquantity);
								System.out.printf("\nCurrent price: $%.2f", desksprice);
								System.out.printf("\n1. Sell %s", desks);
								System.out.printf("\n2. Buy %s", desks);
								System.out.println("\n3. Change price");
								System.out.println("0. Return to main menu");
								int deskschoice = -1;
								do{
									try{
										System.out.print("\nPlease enter choice: ");
										deskschoice = keyboard.nextInt();
										if(deskschoice != 1 && deskschoice != 2 && deskschoice != 3 && deskschoice != 0){
											System.out.print("Your choice must be 1, 2, 3, or 0, choose again!");
										}
										else{
											break;
										}
									}
									catch(InputMismatchException ime){
										System.out.println("Your choice must be 1, 2, 3, or 0, choose again!");
										keyboard.nextLine();
										continue;
									}
								}while(invalid);
											switch(deskschoice){
													case 1:
														do{
															try{
																System.out.printf("\n\nAmount to sell (current quantity %d): ", desksquantity);
																desksell = keyboard.nextInt();
																if(desksell <= 0){
																	System.out.printf("\nYou must sell more than zero");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.printf("\nYou must sell more than zero");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
																if(desksell > desksquantity){
																	System.out.print("\nYou are selling more product than you have!\nDo you wish to proceed? (Y or N?) ");
																	again = keyboard.next();
																	while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																		System.out.print("Please enter \"Y\" or \"N\": ");
																		again = keyboard.next();
																	}
																			if(again.equalsIgnoreCase("Y")){
																			desksquantity = desksquantity-desksell;
																			balance = balance + (desksprice*desksell);
																			}
																}
																else{
																	desksquantity = desksquantity - desksell;
																	balance = balance + (desksprice*desksell);
																}
																		pw.println(balance);
																		pw.println(lamps + " " + lampprice + " " + lampquantity);
																		pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																		pw.println(desks + " " + desksprice + " " + desksquantity);
																		pw.close();
														break;
														case 2:
															do{
																try{
																	System.out.printf("\n\nAmount to buy (current quantity %d): ", desksquantity);
																	deskbuy = keyboard.nextInt();
																	if(deskbuy <= 0){
																		System.out.printf("\nYou must buy more than zero");
																		continue;
																	}
																	else{
																		break;
																	}
																}
																catch(InputMismatchException e){
																	System.out.printf("\nYou must buy more than zero");
																	keyboard.nextLine();
																	continue;
																}
															}while(invalid);
															do{
																try{
																	System.out.print("\nPrice per item: ");
																	desksellprice = keyboard.nextInt();
																	if(desksellprice <= 0){
																		System.out.print("\nThe price must be a positive number!");
																		continue;
																	}
																	else{
																		break;
																	}
																}
																catch(InputMismatchException e){
																	System.out.print("\nThe price must be a positive number!");
																	keyboard.nextLine();
																	continue;
																}
															}while(invalid);
																	if((deskbuy*desksellprice) > balance){
																		System.out.print("\nYou are buying more product than you can afford!\nDo you wish to proceed? (Y or N?) ");
																		again = keyboard.next();
																		while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
																			System.out.print("Please enter \"Y\" or \"N\": ");
																			again = keyboard.next();
																		}
																				if(again.equalsIgnoreCase("Y")){
																				desksquantity = desksquantity-desksell;
																				balance = balance - (desksellprice*deskbuy);
																				}
																	}
																	else{
																		desksquantity = desksquantity + deskbuy;
																		balance = balance - (desksellprice*deskbuy);
																	}
																			pw.println(balance);
																			pw.println(lamps + " " + lampprice + " " + lampquantity);
																			pw.println(chairs + " " + chairsprice + " " + chairsquantity);
																			pw.println(desks + " " + desksprice + " " + desksquantity);
																			pw.close();
															break;
													case 3:
														do{
															try{
																System.out.printf("\n\nNew price: ");
																desksprice = keyboard.nextInt();
																if(desksprice <= 0){
																	System.out.print("The price must be greater than zero!");
																	continue;
																}
																else{
																	break;
																}
															}
															catch(InputMismatchException e){
																System.out.print("The price must be greater than zero!");
																keyboard.nextLine();
																continue;
															}
														}while(invalid);
															pw.println(balance);
															pw.println(lamps + " " + lampprice + " " + lampquantity);
															pw.println(chairs + " " + chairsprice + " " + chairsquantity);
															pw.println(desks + " " + desksprice + " " + desksquantity);
															pw.close();
														break;
													case 0:
														pw.println(balance);
														pw.println(lamps + " " + lampprice + " " + lampquantity);
														pw.println(chairs + " " + chairsprice + " " + chairsquantity);
														pw.println(desks + " " + desksprice + " " + desksquantity);
														pw.close();
														desksboolean = false;
														break;
											}
								}while(desksboolean == true);

							break;
					case 0:
							pw.println(balance);
							pw.println(lamps + " " + lampprice + " " + lampquantity);
							pw.println(chairs + " " + chairsprice + " " + chairsquantity);
							pw.println(desks + " " + desksprice + " " + desksquantity);
							pw.close();
							programboolean = false;
							break;
			}													
		}while(programboolean == true);
	}
}