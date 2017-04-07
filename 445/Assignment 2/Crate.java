
public class Crate{
	private int experation;
	private int initial;
	private int current;
	private double cost;
	
	//Default constructor, sets values to 0
	public Crate(){
		experation = 0;
		initial = 0;
		current = 0;
		cost = 0;
	}
	/**
	* Constructor to assign values to each field
	* @param d experation date of Crate
	* @param i intitial amount of things in crate
	* @param c cost of crate
	*/
	public Crate(int d, int i, double c){
		setExperation(d);
		setInitial(i);
		setCost(c);
		current = initial;
	}
	
    /**
    * Sets the cost of the crate
    * @param c the cost of crate
    * @throws IllegalArgumentException Will be thrown when a negative value is passed in.
    */
	public void setCost(double c){
		if (c < 0){
			throw new IllegalArgumentException("The cost must be positive.");
		}
		cost = c;
	}
	/**
	*Gets the cost of crate
	* @return Cost the cost of crate
	*/
	public double getCost(){
		return cost;
	}
    /**
    * Sets the experation date of crate
    * @param d Experation date
    * @throws IllegalArgumentException Will be thrown when a negative value is passed in.
    */
	public void setExperation(int d){
		if (d < 0){
			throw new IllegalArgumentException("The experiation date must be positive.");
		}
		experation = d;
	}
	
	public int getExperation(){
		return experation;
	}
	
    /**
    * Sets the initial amount in the crate
    * @param u The initial amount
    * @throws IllegalArgumentException Will be thrown when a negative value is passed in.
    */
	public void setInitial(int i){
		if (i < 0){
			throw new IllegalArgumentException("The initial amount of produce must be positive.");
		}
		initial = i;
	}
	public int getInitial(){
		return initial;
	}
	
    /**
    * Sets the current amount in the crate
    * @param cc the current amount in crate
    * @throws IllegalArgumentException Will be thrown when a negative value is passed in.
    */
	public void setCurrent(int cc){
		if (cc < 0){
			throw new IllegalArgumentException("The current amount must be positive.");
		}
		current = cc;
	}
	/**
    * Gets the current amount in crate
    * @return Current of the crate
    */
	public int getCurrent(){
		return current;
	}
	
	public int compare(Crate x){
		if(experation == x.getExperation()){
			return 0;
		}
		else if(experation < x.getExperation()){
			return -1;
		}
		else if(experation > x.getExperation()){
			return 1;
		}
		return 0;
	}
	//Converts the crate and it's parameters to a string
	public String display(){
		StringBuilder s = new StringBuilder();
		s.append("Expires: "+experation+" Start Count: "+initial+" Remain: "+current+" Cost: "+cost);
		return s.toString();
	}
	/*Checks experation
	* @param dayy The day, global clock
	* @return true if crate is expired
	*/
	public boolean checkExperation(int dayy){
		if (experation == dayy){
			return true;
		}
		else{
			return false;
		}
	}
	
}