import java.util.Scanner;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class Assign2{
	public static void main(String args[]) throws FileNotFoundException{
		boolean invalid = true;
		Scanner keyboard = new Scanner(System.in);
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		
		//Array Stacks
		//ArrayStack<Crate> truckStack = new ArrayStack<Crate>(150);
		ArrayStack<Crate> placeholderStack = new ArrayStack<Crate>(150);
		ArrayStack<Crate> storageStack = new ArrayStack<Crate>(150);
		Crate counterCrate = new Crate();
		int amountOfCrates = 0, totalAmountOfCrates = 0, moves = 0, totalMoves = 0, day = 0;
		double produceCost = 0, runCost = 0, totalLast = 0, totalOverall = 0, totalProduceCost = 0, totalRunCost = 0;
		//Opens and verifies filename
		String filename = "";
		File inventoryStuff = new File(filename);
		System.out.print("Please enter the inventory file name: ");
		filename = keyboard.nextLine();
		do{
			inventoryStuff = new File(filename);
				if(!inventoryStuff.exists()){
					System.out.println("The file does not exist or is invalid, try inputting the correct name: ");
					filename = keyboard.nextLine();
					inventoryStuff = new File(filename);
				}
				else{
					break;
				}
		}while(invalid);
		
		//Creates File Scanner Object
		Scanner inFile = new Scanner(inventoryStuff);
		while(inFile.hasNext()){
			String command = inFile.nextLine();
			//Switch-Case to initiate commands.
			switch (command){
				case "receive":
					amountOfCrates = inFile.nextInt();
					totalAmountOfCrates = totalAmountOfCrates + amountOfCrates;
					System.out.println("Receiving "+amountOfCrates+" crates of produce.");
					for(int i = 0; i < amountOfCrates; i++){
						Crate tempCrate = new Crate(inFile.nextInt(), inFile.nextInt(), inFile.nextDouble());
						produceCost = produceCost+tempCrate.getCost();
						totalProduceCost = totalProduceCost+produceCost;
						placeholderStack.push(tempCrate);
					}
					//Creates a temp crate to compare
					Crate temp = new Crate(2,2,2);
					while(!placeholderStack.isEmpty()){
						temp = placeholderStack.pop();
						while(!storageStack.isEmpty() && temp.compare(storageStack.peek()) == 1){
							placeholderStack.push(storageStack.pop());
							moves++;
							totalRunCost = totalRunCost+runCost;
						}
						storageStack.push(temp);
						moves++;
					}
					//Variable math for the report
					runCost = moves;
					totalRunCost = totalRunCost+runCost;
					totalMoves = totalMoves+moves;
					totalLast = produceCost+runCost;
					totalOverall = totalProduceCost+totalRunCost;
					break;
				//Could not get this quite working
				case "sell":
					if (counterCrate.getCurrent() == 0){
							counterCrate = storageStack.pop();
							System.out.println("Getting crate: " + counterCrate.display() + " from the stack.");
					}
					int amount = inFile.nextInt();
					sell(amount, counterCrate, storageStack, placeholderStack);
					break;
				case "display":
					System.out.println("");
					display(counterCrate, placeholderStack, storageStack);
					break;
				case "skip":
					day++;
					System.out.println("The current day is now "+day);
						while(!storageStack.isEmpty()){
							Crate placeholderCrate = storageStack.pop();
							if(placeholderCrate.checkExperation(day) == true){
								placeholderCrate = null;
							}
							else{
								placeholderStack.push(placeholderCrate);
							}
						}
						while (!placeholderStack.isEmpty()){
							storageStack.push(placeholderStack.pop());
						}
						System.out.println();

					break;
				case "report":
					System.out.println("Country Produce Store Financial Statement:");
					System.out.println("\tMost Recent Shipment:");
					System.out.println("\t\tCrates: "+amountOfCrates);
					System.out.println("\t\tProduce cost: "+currency.format(produceCost));
					System.out.println("\t\tLabor (moves): "+moves);
					System.out.println("\t\tLabor cost: "+currency.format(runCost));
					System.out.println("\t\t------------------------");
					System.out.println("\t\tTotal: "+currency.format(totalLast));
					System.out.println("");
					System.out.println("\tOverall Expenses:");
					System.out.println("\t\tCrates: "+totalAmountOfCrates);
					System.out.println("\t\tProduce cost: "+currency.format(totalProduceCost));
					System.out.println("\t\tLabor (moves): "+totalMoves);
					System.out.println("\t\tLabor cost: "+currency.format(totalRunCost));
					System.out.println("\t\t------------------------");
					System.out.println("\t\tTotal: "+currency.format(totalOverall));
					moves = 0;
					break;
			}
		}
	}
	//Method for sell that does not quite work
	public static void sell(int amount, Crate counterCrate, ArrayStack<Crate> storageStack, ArrayStack<Crate> placeholderStack){
			System.out.println(counterCrate.getCurrent());
			if (counterCrate.getCurrent() == 0){
					counterCrate = storageStack.pop();
					System.out.println("Getting crate: " + counterCrate.display() + " from the stack.");
			}
			if (counterCrate.getCurrent() > amount){
				System.out.println(amount+" produce needed for order.");
				counterCrate.setCurrent(counterCrate.getCurrent() - amount);
				System.out.println(amount +" produce used from current crate.");
				} 		
			else if (counterCrate.getCurrent() == amount){
				System.out.println(amount+" produce needed for order.");
				System.out.println(amount + " produce used from current crate.");
				counterCrate = storageStack.pop();
				}
			else if (counterCrate.getCurrent() < amount){
				int newamount = (amount - counterCrate.getCurrent());
				System.out.println(amount+" produce needed for order.");
				System.out.println(counterCrate.getCurrent()+" produce used from current crate.");
				counterCrate = storageStack.pop();
				System.out.println("Getting crate: " + counterCrate.display() + " from the stack.");
				sell(newamount, counterCrate, storageStack, placeholderStack);
			}
			System.out.println("");
	}
	//Will display the items by moving them to a temp, displaying, and then moving them back to storage
	public static void display(Crate counterCrate, ArrayStack<Crate> placeholderStack, ArrayStack<Crate> storageStack){
			System.out.println("Stack crates (top to bottom):");
			while(!storageStack.isEmpty()){
				Crate placeholderCrate = storageStack.pop();
				System.out.println(placeholderCrate.display());
				placeholderStack.push(placeholderCrate);
				}
			while (!placeholderStack.isEmpty()){
				storageStack.push(placeholderStack.pop());
			}
			System.out.println();
	}
}