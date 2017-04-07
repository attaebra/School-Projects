/**
*Represents the Product for the Project 3 store
*@author Atta Ebrahimi
*/

public class Product{
	private String name;
	private double price;
	private int quantity;
	
	 /**
     * Constructor to assign values to each field.
     * @param n Name of Product.
     * @param p Price of Product.
	 * @param q Quantity of Product
     */
	public Product(String n, double p, int q){
		setName(n);
		setPrice(p);
		setQuantity(q);
	}
	
	/**
     * Copy constructor to make a duplicate of a Product object.
     * @param Product The Product to duplicate.
     */
    public Product(Product i) {
        //option 1:
		name = i.name;
        price = i.price;
        quantity = i.quantity;
    }
	
	 /**
     * No-args constructor, which just initializes the name, price, and quantity
     * to default values.
     */
    public Product() {
		name = "nothing";
        price = 1;
        quantity = 1;
    }
	
	  
    /**
     * Set name of Product and update area.
     * @param n New value for name.
     */
    public void setName(String n) {
        name = n;
    }
	
	 /**
     * Gets the name of the Product.
     * @return Name of the Product.
     */
    public String getName() {
        return name;
    }
	
	/**
     * Set price of Product.
     * @param p New value for price.
     * @throws IllegalArgumentException Thrown when price is 0 or negative.
     */
    public void setPrice(double p) throws IllegalArgumentException {
        if (p <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Price must be positive");
            throw iae;
        }
        price = p;
    }
	
	 /**
     * Gets the price of the Product.
     * @return price of the Product.
     */
    public double getPrice() {
        return price;
    }
	
		/**
     * Set quantity of Product.
     * @param q New value for quantity.
     * @throws IllegalArgumentException Thrown when quantity is 0 or negative.
     */
    public void setQuantity(int q) throws IllegalArgumentException {
        if (q <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Quantity must be positive");
            throw iae;
        }
        quantity = q;
    }
	
	 /**
     * Gets the quantity of the Product.
     * @return quantity of the Product.
     */
    public int getQuantity() {
        return quantity;
    }
	
	 public String toString() {
        String returnval = "Name = "+name+", Price = "+price+", Quantity = "+quantity;
        return returnval;
    }

	 /**
     * Compares to rectangles for equality by comparing widths together
     * and lengths together.
     * @param r Second rectangle to compare.
     * @return true if the rectangles are equal.
     */
    public boolean equals(Product i) {
        //option 1: same dimensions in same order
        if (i.name == name && i.price == price && i.quantity == quantity) return true;
        return false;
    }
	
	public boolean equals(double name, double price, double quantity) {
        if (this.name.equals(name) && this.price == price && this.quantity == quantity) return true;
        else return false;
    }
}