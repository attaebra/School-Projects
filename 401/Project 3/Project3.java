/**
This program allows a user to track inventory for their company. The program asks a user for a file with
inventory information, and allows the user to buy, sell, and change prices of the things that they use at their
company.
*/

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.*;
import java.io.*;


public class Project3{
	
	public static void main(String[] args) throws FileNotFoundException{
				boolean invalid = true, programboolean = true;
				Scanner keyboard = new Scanner(System.in);
				NumberFormat currency = NumberFormat.getCurrencyInstance();
				
				String filename = getFilename();
				File inventoryData = new File(filename);
			
				//defines most variables (used chair, desk, and lamp)
				int lampsell = 0, lampbuy = 0, chairsell = 0, chairbuy = 0, desksell = 0, deskbuy = 0, lampsellprice = 0, chairsellprice = 0, desksellprice = 0;
				String again;
			do{
				Scanner inFile = new Scanner(inventoryData);
					//Creates ArrayList to hold Products
					ArrayList<Product> ProductHolder = new ArrayList<Product>();
					//reads in balance and variables to display
					double balance = inFile.nextDouble();
					while(inFile.hasNext()){
						Product maker = new Product(inFile.next(), inFile.nextDouble(), inFile.nextInt());
						ProductHolder.add(maker);
					}
					inFile.close();
					
				
				System.out.println("\nCurrent balance: "+balance);
				for(int i = 0; i < ProductHolder.size(); i++){
				System.out.print("\t"+(i+1)+". "+ProductHolder.get(i).getName()+"\t\t ("+ProductHolder.get(i).getQuantity()+" at "+currency.format(ProductHolder.get(i).getPrice())+")\n");
				}
				System.out.println("\t"+(ProductHolder.size()+1)+". Add an option");
				System.out.println("\t0. Exit");	
				
				int choice = getChoice(ProductHolder);
				
				//determines whether user wants to add Product, change existing Products, or exit
				if (choice == (ProductHolder.size()+1)){
					ProductHolder = addItem(ProductHolder, filename, balance);
				}
				else if(choice != 0){
					doStuff(choice, ProductHolder, filename, balance);
				}
				else{
					programboolean = false;
				}
			}while(programboolean == true);
	}
	/**
	*Validates filename for the inventory
	*@return the validated name of the file
	*@throws FileNotFoundException thrown due to the fact that if file doesn't exist it keeps asking.
	*/
	public static String getFilename() throws FileNotFoundException {
		String filename = "";
		File confirm = new File(filename);
		boolean invalid = true;
             
		Scanner keyboard = new Scanner(System.in);
		do{
			System.out.print("Please enter the name of the inventory file: ");
			filename = keyboard.nextLine();
				confirm = new File(filename);
					if(!confirm.exists()){
						System.out.println("\nThe file does not exist or is invalid.");
					}
					else{
						invalid = false;
					}
		}while(invalid == true);
			
		return filename;
	}
	/**
	*Allows the user to choose which Product they would like to edit.
	*@param ProductHolder the list of Product objects
	*@return The choice of which product to edit
	*/
	public static int getChoice(ArrayList<Product> ProductHolder){
		int choice = 0;
		boolean invalid = true;
		Scanner keyboard = new Scanner(System.in);
		do{
			//choice for switch case
			try{
				System.out.print("\nPlease enter choice: ");
				choice = keyboard.nextInt();
				if(choice < 0 || choice > (ProductHolder.size()+1)){
					System.out.print("Your choice must be one of the choices above!");
				}
				else{
					break;
				}
			}
			catch(InputMismatchException ime){
				System.out.print("Your choice must be one of the choices above!");
				keyboard.nextLine();
				continue;
			}
		}while(invalid);
		return choice;
	}
	/**
	*Asks the user if they would like to buy sell or change price
	*@return The choice
	*/
	public static int getAnotherChoice() {
		int pick = 0;
		Scanner keyboard = new Scanner(System.in);
		boolean usercantreadboolean = true;
		do{
			try{
			System.out.print("\nWhich of the above options would you like to choose (0-4): ");
			pick = keyboard.nextInt();
				if(pick != 1 && pick != 2 && pick != 2 && pick != 3 && pick !=4 && pick !=0){
					System.out.println("\nYou can only choose 1 or 2!");
				}
				else{
					usercantreadboolean = false;
				}
			}
			catch(InputMismatchException ime){
				System.out.println("\nYou can only choose 0-4!");
				keyboard.nextLine();
				continue;
			}
		}while(usercantreadboolean == true);
	return (pick);
	}
	/**
	*Does all the actual math in changing things
	*@param choice This choice is used to display the objects, choice - 1 is equal to the object that the user chose
	*@param ProductHolder This is the ArrayList of Products that was filled in the main that dictates the 
	*@param filename The filename that was confirmed in an above method
	*@param balance This is the balance that was read from the file in the main
	*@throws FileNotFoundException file is already validated
	*/
	public static void doStuff(int choice, ArrayList<Product> ProductHolder, String filename, double balance) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(filename);
		NumberFormat curr = NumberFormat.getCurrencyInstance();
		
		boolean lampsboolean = true, invalid = true;
		int lampsell = 0, lampbuy = 0, chairsell = 0, chairbuy = 0, desksell = 0, deskbuy = 0, lampsellprice = 0, chairsellprice = 0, desksellprice = 0;
		String again;
		int choice1 = choice-1;
		do{
			//lamps menu
			System.out.print("\nCurrent balance: "+curr.format(balance));
			System.out.print("\nCurrent quantity: "+ProductHolder.get(choice1).getQuantity());
			System.out.print("\nCurrent price: "+curr.format(ProductHolder.get(choice1).getPrice()));
			System.out.print("\n1. Sell "+ProductHolder.get(choice1).getName());
			System.out.print("\n2. Buy "+ProductHolder.get(choice1).getName());
			System.out.println("\n3. Change price.");
			System.out.println("0. Return to main menu.");
			int lampschoice = getAnotherChoice();
			//choice to buy, sell, or change price
			switch(lampschoice){
					case 1:
						do{
							try{
								System.out.print("\n\nAmount to sell (current quantity "+ProductHolder.get(choice1).getQuantity()+"): ");
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
								if(ProductHolder.get(choice1).getQuantity() < 0){
									System.out.print("\nYou cannot sell items that you do not have.");
								}
								else if(lampsell > ProductHolder.get(choice1).getQuantity()){
									System.out.print("\nYou are selling more product than you have!\nDo you wish to proceed? (Y or N?) ");
									again = keyboard.next();
									while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
										System.out.print("Please enter \"Y\" or \"N\": ");
										again = keyboard.next();
									}
											if(again.equalsIgnoreCase("Y")){
											ProductHolder.get(choice1).setQuantity(ProductHolder.get(choice1).getQuantity()-lampsell);
											balance = balance + (ProductHolder.get(choice1).getPrice()*lampsell);
											}
								}
								else{
									//Math for selling
									ProductHolder.get(choice1).setQuantity(ProductHolder.get(choice1).getQuantity()-lampsell);
									balance = balance + (ProductHolder.get(choice1).getPrice()*lampsell);
								}
										//updates file
										pw.println(balance);
										for(int i = 0; i < ProductHolder.size(); i++){
											pw.println(ProductHolder.get(i).getName()+" "+ProductHolder.get(i).getPrice()+" "+ProductHolder.get(i).getQuantity());
										}
										pw.close();
						break;
						case 2:
							do{
								try{
									System.out.print("\n\nAmount to buy (current quantity "+ProductHolder.get(choice1).getQuantity()+"): ");
									lampbuy = keyboard.nextInt();
									if(lampbuy <= 0){
										System.out.print("\nYou must buy more than zero");
										continue;
									}
									else{
										break;
									}
								}
								catch(InputMismatchException e){
									System.out.print("\nYou must buy more than zero");
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
												ProductHolder.get(choice1).setQuantity(ProductHolder.get(choice1).getQuantity()+lampbuy);
												balance = balance - (lampsellprice*lampbuy);
												}
									}
									else{
										//buy math
										ProductHolder.get(choice1).setQuantity(ProductHolder.get(choice1).getQuantity()+lampbuy);
										balance = balance - (lampsellprice*lampbuy);
									}
											//prints to file
											pw.println(balance);
											for(int i = 0; i < ProductHolder.size(); i++){
												pw.println(ProductHolder.get(i).getName()+" "+ProductHolder.get(i).getPrice()+" "+ProductHolder.get(i).getQuantity());
											}
											pw.close();
							break;
					case 3:
						do{
							try{
								//updates price
								System.out.printf("\n\nNew price: ");
								ProductHolder.get(choice1).setPrice(keyboard.nextDouble());
								if(ProductHolder.get(choice1).getPrice() <= 0){
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
							for(int i = 0; i < ProductHolder.size(); i++){
								pw.println(ProductHolder.get(i).getName()+" "+ProductHolder.get(i).getPrice()+" "+ProductHolder.get(i).getQuantity());
							}
							pw.close();
						break;
					case 0:
						//prints to file
						pw.println(balance);
						for(int i = 0; i < ProductHolder.size(); i++){
							pw.println(ProductHolder.get(i).getName()+" "+ProductHolder.get(i).getPrice()+" "+ProductHolder.get(i).getQuantity());
						}
						pw.close();
						lampsboolean = false;
						break;
			}
			}while(lampsboolean == true);
			pw.close();
	}
	/**
	*Adds an item if the user chooses to do so
	*@param ProductHolder the ArrayList that contains the Products
	*@param filename the validated file name
	*@param balance the balance taken from the top of the file in main
	*@return The ArrayList with the added Product
	*@throws FileNotFoundException file is already validated
	*/
	public static ArrayList<Product> addItem(ArrayList<Product> ProductHolder, String filename, double balance)throws FileNotFoundException{
		Scanner keyboard = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(filename);
		boolean sameboolean = false, invalid = true, invalid1 = true, invalid2 = true;
		String itemname = "";
		int itemquantity = 0;
		double itemprice = 0;
			//determines if the Product exists
			do{
				sameboolean = false;
				System.out.print("\nEnter the name of the new item: ");
				itemname = keyboard.nextLine();
				for(int i = 0; i < ProductHolder.size(); i++){
					if (itemname.equalsIgnoreCase(ProductHolder.get(i).getName())){
						sameboolean = true;
					}
				}
				if(sameboolean == true){
					System.out.print("Error: The item already exists.");
				}
				else{
					invalid = false;
				}
			}while(invalid == true);
		//gets quantity
		do{
			try{
			System.out.print("Enter the quantity of the new item: ");
			itemquantity = keyboard.nextInt();
				if(itemquantity < 0){
					System.out.print("You must enter a number greater than zero, try again.");
				}
				else{
					invalid1 = false;
				}
			}
			catch(InputMismatchException ime){
			System.out.print("You must enter an integer value.");
			keyboard.nextLine();
			continue;
			}
		}while(invalid1 == true);
		//gets price
		do{
			try{
			System.out.print("Enter the price of the new item: ");
			itemprice = keyboard.nextDouble();
				if(itemprice < 0){
					System.out.print("You must enter a number greater than zero, try again.");
				}
				else{
					invalid2 = false;
				}
			}
			catch(InputMismatchException ime){
			System.out.print("You must enter a number.");
			keyboard.nextLine();
			continue;
			}
		}while(invalid2 == true);
		//creates product to add
		Product temp = new Product(itemname, itemprice, itemquantity);
		ProductHolder.add(temp);
		//Prints to file
		pw.println(balance);
		for(int i = 0; i < ProductHolder.size(); i++){
			pw.println(ProductHolder.get(i).getName()+" "+ProductHolder.get(i).getPrice()+" "+ProductHolder.get(i).getQuantity());
		}
		pw.close();
		return ProductHolder;
	}
	}
