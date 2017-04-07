
/*
 * Describes a crop, its yield, and its price through toString method and 
 * figures out if the crops in question are the same crops through use of 
 * equals methods.
 * @author mariakalymon
 */

public class Crop {

    private String Name;
    private int Yield;
    private double Price;
    
    /**
     * Initializer constructor, used to directly set the fields
     * @param name The name of the crop
     * @param yield The amount of the crop
     * @param price The cost of the crop
     */
    public Crop(String name, int yield, double price) {
        setName(name);
        setYield(yield);
        setPrice(price);
    }
    
    /**
     * Copy constructor, used to make a duplicate of the crop object
     * @param c The copy of the crop object
     */
    public Crop(Crop c) {
        Name = c.getName();
        Yield = c.getYield();
        Price = c.getPrice();
    }
    
    /**
     * No-args constructor, used to initialize the values
     */
    public Crop() {
        Name = " ";
        Yield = 0;
        Price = 0.00;
    }
    
    /**
     * Sets the name of the crop 
     * @param name New value for the name
     */
    public void setName(String name) {
        Name = name;
    }
    
    /**
     * Sets the yield/amount of the crop
     * @param yield New value for the yield
     * @throws IllegalArgumentException Thrown when the yield is negative
     */
    public void setYield(int yield) throws IllegalArgumentException  {
        if (yield < 0) {
            IllegalArgumentException iae = new IllegalArgumentException("yield must be positive");
            throw iae;
        }
        Yield = yield;
    }
    
    /**
     * Sets the price of the crop
     * @param price New value for the price
     * @throws IllegalArgumentException Thrown when the price is negative
     */
    public void setPrice(double price) throws IllegalArgumentException  {
        if (price < 0) {
            IllegalArgumentException iae = new IllegalArgumentException("price must be positive");
            throw iae;
        }
        Price = price;
    }
    
    /** 
     * Gets the name of the crop
     * @return Name of the crop
     */
    public String getName() {
        return Name;
    }
    
    /**
     * Gets the yield of the crop
     * @return Yield of the crop
     */
    public int getYield() {
        return Yield;
    }
    
    /**
     * Gets the price of the crop
     * @return Price of the crop
     */
    public double getPrice() {
        return Price;
    }
    
    /**
     * Equals method comparing two Crop objects to each other
     * @param p Copy of the Crop object
     * @return True if Crop name is the same.
     */
    public boolean equals(Crop p) {
        if (p.Name.equalsIgnoreCase(Name)) {
            return true;
        }
        else return false;
    }
    
    public boolean equals(String crop) {
        if (crop.equalsIgnoreCase(Name)) {
            return true;
        }
        else return false;
    }
    
    public String toString() {
        return "In summary, you have " + Name + " at a yield of " + Yield + " priced at $" + Price+" per kilogram.";  
    } 
}
