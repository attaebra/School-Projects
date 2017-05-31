import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;

public class RoadTest{
	//Test getting name method.
	@Test
	public void testGetName(){
		City first = Mockito.mock(City.class);
		City second = Mockito.mock(City.class);
			Road road = new Road("Unit Testing Blvd.", first, second);
			assertEquals(road.getName(), "Unit Testing Blvd.");
	}
	
	//Test getting the start location
	@Test
	public void testGetStart(){
		City start = Mockito.mock(City.class);
		City end = Mockito.mock(City.class);
		Road road = new Road("Unit Testing Blvd.", start, end);
			assertEquals(road.getStart(), start);
	}
	
	//Test getting the end location
	@Test
	public void testGetEnd(){
		City start = Mockito.mock(City.class);
		City end = Mockito.mock(City.class);
		Road road = new Road("Unit Testing Blvd.", start, end);
			assertEquals(road.getEnd(), end);
	}
	
}