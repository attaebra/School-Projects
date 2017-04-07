/*
 * set and get hypotenuse
 * get base and height
 */
public class RightTriangle
{
    private double base = 0;
    private double height = 0;
	
	/**
	* Constructor to assign values to each field (LegA and LegB -base and height-)
	* @param b Base of triangle LegA
	* @param h Height of triangle LegB
	*/
	public RightTriangle(double b, double h) {
		setLegA(b);
		setLegB(h);
	}
	
	/**
    * No-args constructor, which just initializes the base and height
    * to default values (both 1).
    */
    public RightTriangle() {
        base = 1;
        height = 1;
    }
	
    /**
    * Sets the base of the triangle
    * @param b The new width
    * @throws IllegalArgumentException Will be thrown when a negative (or zero) value is passed in.
    */
    public void setLegA(double b) {
        if (b <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Base must be positive.");
            throw iae;
        }
        base = b;
    }
    
	/**
    * Gets the height of the triangle
    * @return Height of the triangle
    */
    public double getLegA() {
        return base;
    }
    
	/**
	* Sets the height of the triangle
	* @param h The new height
	* @throws IllegalArgumentException Will be thrown when a negative (or zero) value is passed in.
	*/
    public void setLegB(double h) {
        if (h <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("Height must be positive.");
            throw iae;
        }
        height = h;
    }
	
    /**
    * Gets the height of the triangle
    * @return Height of the triangle
    */
    public double getLegB() {
        return height;
    }
	
	/**
    * Returns the hypotenuse of the triangle
    * @return The hypotenuse of the triangle
    */
    public double getHypotenuse() {
		double hypotenuse = Math.sqrt(base*base+height*height);
        return hypotenuse;
    }
	
	/**
    * Returns the perimeter of the triangle
    * @return The perimeter of the triangle
    */
	public double getPerimeter() {
		double perimeter = base+height+getHypotenuse();
		return perimeter;
	}
}