import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;
import java.util.*;

public class DriverTest{
	//Tests getting driver number
	@Test
	public void testNumber(){
		Driver driver = new Driver(1337);
		assertEquals(driver.getDriverNumber(), 1337);
	}
	
	//Test getting the number of laboon visits
	@Test
	public void testCSHelpCount(){
		Driver driver = new Driver(1337);
		assertEquals(driver.getCSHelpCount(), 0);
	}
	
	//Test incrementing the help count
	@Test
	public void testIncreaseHelp(){
		Driver driver = new Driver(1337);
		driver.increaseHelp();
		driver.increaseHelp();
		driver.increaseHelp();
		assertEquals(driver.getCSHelpCount(), 3);
	}
	
	//Test if the toString method is producing the correct output string
	@Test
	public void testString(){
		Driver driver = new Driver(1337);
		driver.increaseHelp();
		driver.increaseHelp();
		assertEquals(driver.toString(), "Driver 1337 met with Prof. Laboon 2 time(s).");
	}
	
}