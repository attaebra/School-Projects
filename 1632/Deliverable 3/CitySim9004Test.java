import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;
import java.util.*;

public class CitySim9004Test{
	//Test if the creation of the map is completed
	@Test
	public void testCreate(){
		assertEquals(CitySim9004.create(), true);
	}
	
	//Test if argument length is always 1
	@Test
	public void testLength(){
		String [] arguments = {"It is one"};
		assertEquals(CitySim9004.checkLengthOfArgs(arguments), true);
	}
	
	//Test if a place is outside of the city, this is guaranteed to work
	//because it just calls the city method.
	@Test
	public void testInOrOut(){
		City city = new City("Outside City", "Rock Bottom", true);
		assertEquals(CitySim9004.isOutside(city), true);
	}
	
	//Ensures that the break points print
	@Test
	public void testBreaks(){
		assertEquals(CitySim9004.printBreak(), "-----");
	}
	
	//Ensures that the output of where the driver is going and coming from
	//through the proper road is correct.
	@Test
	public void testDriverOutput(){
		City start = new City("Bikini Bottom");
		City end = new City("Rock Bottom");
		Road road = new Road("Underwater Tunnel", start, end);
		Driver driver = new Driver(100);
		assertEquals(CitySim9004.driverOutput(driver, road), "Driver 100 coming from Bikini Bottom going to Rock Bottom through Underwater Tunnel.");
	}
	
	//Ensures that the argument can be casted as type integer.
	@Test
	public void testCheckInt(){
		String [] arguments = {"1"};
		assertEquals(CitySim9004.checkInt(arguments), true);
	}
	
	//Checks if our method for determining whether we are in Sennott
	//is proper or not.
	@Test
	public void testAtSennott(){
		City city = new City("Sennott");
		assertEquals(CitySim9004.isAtSennott(city), true);
	}
	
	//Checks if the driver has left the city and prints the 
	//corresponding message
	@Test
	public void testStop(){
		Driver driver = new Driver(1337);
		City city = new City("Outside City", "Rock Bottom", true);
		assertEquals(CitySim9004.driverStop(driver, city), true);
	}
	
	//Checks if increasing help visits goes through properly
	@Test
	public void testIncreaseHelp(){
		Driver driver = new Driver(26);
		assertEquals(CitySim9004.increaseHelpTotal(driver), true);
	}
	
	//Checks if output visits are returned properly
	@Test
	public void testOutputVisits(){
		Driver driver = new Driver(1337);
		assertEquals(CitySim9004.outputVisits(driver), "Driver 1337 met with Prof. Laboon 0 time(s).");
	}
	
	//Checks if output string for CS help works for 0 visits
	@Test
	public void testZeroVisits(){
		Driver driver = new Driver(1337);
		assertEquals(CitySim9004.checkVisits(driver), true);
	}
	
	//Checks if output string for CS help works for 3+ visits
	@Test
	public void testThreeOrMoreVisits(){
		Driver driver = new Driver(1337);
		driver.increaseHelp();
		driver.increaseHelp();
		driver.increaseHelp();
		driver.increaseHelp();
		assertEquals(CitySim9004.checkVisits(driver), false);
	}
}