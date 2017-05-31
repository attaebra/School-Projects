import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;
import java.util.*;

public class CityTest{
	//Tests getting the area name.
	@Test
	public void testGetName(){
		City city = new City("Bikini Bottom");
		assertEquals(city.getName(), "Bikini Bottom");
	}
	
	//Test getting the alias for outside cities
	@Test
	public void testAlias(){
		City city = new City("Outside City", "Rock Bottom", true);
		assertEquals(city.getAlias(), "Rock Bottom");
	}
	
	//Test if a locations that are outside are outside by asserting true on the boolean.
	@Test
	public void testOutsideCity(){
		City city = new City("Outskirts", "Rock Bottom", true);
		assertEquals(city.outsideCity(), true);
	}
	
	//Test adding new routes to cities
	@Test
	public void testAddRoute(){
		City city = new City("Rock Bottom");
		Road road = Mockito.mock(Road.class);
		assertEquals(city.addRoute(road), 1);
	}
	
	//Tests if generating a new direction works
	@Test
	public void testNewDirection(){
		City city = new City("Bikini Bottom");
		Road road = Mockito.mock(Road.class);
		city.addRoute(road);
		Random num = Mockito.mock(Random.class);
		Mockito.when(num.nextInt()).thenReturn(0);
		
		assertEquals(city.findDirection(num), road);
		
	}
	
}