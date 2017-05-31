import java.util.*;

public class CitySim9004{
	static Random rand = null;
	static ArrayList<City> possibleLocations = new ArrayList<City>();
	
	public static void main(String args[]){
		int seed;
		
		if (checkLengthOfArgs(args)){
			if (checkInt(args)){
				final int DRIVER_COUNT = 5;
				City currentLocation = null;
				seed = Integer.parseInt(args[0]);
				rand = new Random(seed);
				String s = "";
				
				create();
				
				for (int i = 1; i <= DRIVER_COUNT; i++){
					Driver d = new Driver(i);
					currentLocation = determineStart(rand);
					
					if (isAtSennott(currentLocation)){
						increaseHelpTotal(d);
					}
					
					while (!currentLocation.outsideCity()){
						Road road = currentLocation.findDirection(rand);
						currentLocation = road.getEnd();
						
						if (isAtSennott(currentLocation)){
							increaseHelpTotal(d);
						}
						driverOutput(d, road);
					}
					
					driverStop(d, currentLocation);
					outputVisits(d);
					checkVisits(d);
					printBreak();
				}
				
			}
			else{
				System.out.println("The arguments are invalid, please enter a single integer argument for the random seed.");
			}
		}
		else{
			System.out.println("The arguments are invalid, please enter a single integer argument for the random seed.");
			System.exit(0);
		}	
	}
	
	public static boolean create(){
		try{
			//Create Cities
			City union = new City("Union");
			City hillman = new City("Hillman");
			City sennott = new City("Sennott");
			City presby = new City("Presby");
			City outsidePhila = new City("Outside City", "Philadelphia", true);
			City outsideClev = new City("Outside City", "Cleveland", true);
			
			//Add inner cities to possible locations
			possibleLocations.add(union);
			possibleLocations.add(hillman);
			possibleLocations.add(sennott);
			possibleLocations.add(presby);
			
			//Fourth Avenue
			Road A = new Road("Fourth Avenue", outsideClev, presby);
			Road B = new Road("Fourth Avenue", presby, union);
			Road C = new Road("Fourth Avenue", union, outsidePhila);
			
			//Fifth Avenue
			Road D = new Road("Fifth Avenue", outsidePhila, hillman);
			Road E = new Road("Fifth Avenue", hillman, sennott);
			Road F = new Road("Fifth Avenue", sennott, outsideClev);
			
			//Bill Street
			Road G = new Road("Bill Street", presby, sennott);
			Road H = new Road("Bill Street", sennott, presby);
			
			//Phil Street
			Road I = new Road("Phil Street", union, hillman);
			Road J = new Road("Phil Street", hillman, union);
			
			//Union Routes
			union.addRoute(C);
			union.addRoute(I);
						
			//Hillman Routes
			hillman.addRoute(E);
			hillman.addRoute(J);	
				
			//Sennott Routes
			sennott.addRoute(F);
			sennott.addRoute(H);
	
			//Presby Routes
			presby.addRoute(G);
			presby.addRoute(B);
			
			//Outside Routes
			outsideClev.addRoute(A);
			outsidePhila.addRoute(D);
			
			return true;
		}catch (Exception e){
			return false;
		}
	}
	//Ensures that there is only one argument
	//Returns true if there is only one argument
	public static boolean checkLengthOfArgs(String[] args){
		return args.length == 1;
	}
	
	//Checks if the argument can be casted as type integer
	//Returns true if it can be an int
	public static boolean checkInt(String[] args){
		try{
			int checkInt = Integer.parseInt(args[0]);

		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
	//Generates the random start of a city.
	//Returns the location the driver starts at start at.
	public static City determineStart(Random randumb){
		int selection = randumb.nextInt(possibleLocations.size());
		return possibleLocations.get(selection);
	}
	
	//Tells you whether you are in or out of city.
	//Returns true if outside.
	public static boolean isOutside(City place){
		return place.outsideCity();
	}
	
	//Prints the break.
	//Returns the dashes string.
	public static String printBreak(){
		String dashes = new String("-----");
		System.out.println(dashes);
		return dashes;
	}
	
	//Displays the printed output of where the driver starts and ends and what road he took.
	//Returns that string.
	public static String driverOutput(Driver driver, Road road){
		String output = "Driver " + driver.getDriverNumber() + " coming from " + road.getStart().getName() + " going to " + road.getEnd().getName() + " through " + road.getName()+".";
		System.out.println(output);
		return output;
	}
	
	//Returns the string that displays when the driver leaves the city altogether.
	//Returns which road he leaves and where he leaves to (Clev or Phila).
	public static boolean driverStop(Driver driver, City city){
			if(!city.getAlias().equals("")){
				String output = "Driver " + driver.getDriverNumber() + " has gone to " + city.getAlias();
				System.out.println(output);
				return true;
			}
			else return false;
	}
	
	//Tells you whether the driver is at Sennott or not.
	//Returns true if at Sennott
	public static boolean isAtSennott(City city){
		if (city.getName().equals("Sennott")) return true;
		else return false;
	}
	
	//Increases the drivers total visits to Laboon.
	public static boolean increaseHelpTotal(Driver driver){
		try{
			driver.increaseHelp();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//Prints the amount of visits a driver has taken in sentence form.
	//Returns the sentence string.
	public static String outputVisits(Driver driver){
		String output = driver.toString();
		System.out.println(output);
		return output;
	}
	
	//Checks if visits are 0, or greater than three and prints the corresponding statement.
	//Returns true if 0, and false if >=3
	public static boolean checkVisits(Driver driver){
		String output = "";
		boolean help = true;
		
		if (driver.getCSHelpCount() == 0){
			output = "That student missed out!";
			help = true;
		}
		else if (driver.getCSHelpCount() >= 3){
			output = "Wow, that driver needed lots of CS help!";
			help = false;
		}
		System.out.println(output);
		return help;
	} 
	
}