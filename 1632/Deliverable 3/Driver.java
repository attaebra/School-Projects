import java.util.*;

public class Driver {
	
	private int driverNumber;
	private int csHelp;
	
	public Driver(int number){
		this.driverNumber = number;
	}
	
	public int getDriverNumber(){
		return driverNumber;
	}
	
	public int getCSHelpCount(){
		return csHelp;
	}
	
	public void increaseHelp(){
		csHelp++;
	}
	
	public String toString(){
		return "Driver " + driverNumber + " met with Prof. Laboon " + csHelp + " time(s).";
	}
}