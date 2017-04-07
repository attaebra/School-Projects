/**
 * Program that uses the Crop class
 * @author mariakalymon
 */

public class CropDriver {

    public static void main(String[] args) {
        
        //first Crop object from the initializer constructor
        Crop c = new Crop("Corn", 5000, 0.25);
        
        System.out.println("The information is below: ");
        System.out.println("\tThe crop is "+ c.getName());
        System.out.println("\tThe crop's yield is "+ c.getYield());
        System.out.println("\tThe crop is priced at "+ c.getPrice());
        
        System.out.println(c.toString());
        
        System.out.println(" ");
        
        //second Crop object from no-args constructor
        Crop c2 = new Crop();
        
        //set using mutator methods
        c2.setName("Wheat");
        c2.setYield(8000);
        c2.setPrice(0.003);
        
        System.out.println("The information is below: ");
        System.out.println("\tThe crop is "+ c2.getName());
        System.out.println("\tThe crop's yield is "+ c2.getYield());
        System.out.println("\tThe crop is priced at "+ c2.getPrice());
        
        System.out.println(c2.toString());
        
        System.out.println(" ");
        
        //third Crop object from copy constructor
        Crop c3 = new Crop(c);
        
        //using mutator method to change needed value
        c3.setYield(5500);
        
        System.out.println("The information is below: ");
        System.out.println("\tThe crop is "+ c3.getName());
        System.out.println("\tThe crop's yield is "+ c3.getYield());
        System.out.println("\tThe crop is priced at "+ c3.getPrice());
        
        System.out.println(c3.toString());
    
        System.out.println(" ");
        
        System.out.println("Lets look at the crops and their equivalencies!");

        //equals method comparing object c and object c2
        if (c.equals(c2)) {
            System.out.println("\tThe first and second crops are equal.");
        }
        else {
            System.out.println("\tThe first and second crops are not equal.");
        }
    
        //equals method comparing object c and object c3
        if (c.equals(c3)) {
            System.out.println("\tThe first and third crops are equal.");
        }
        else {
            System.out.println("\tThe first and third crops are not equal.");
        }
    
        //**not required** 
        //equals method comparing a String crop to the first Crop object 
        String crop = "Carrot";
        if (c.equals(crop)) {
            System.out.println("\tThe first crop is equivalent to the crop in question.");   
        }
        else {
            System.out.println("\tThe first crop is NOT equivalent to the crop in question, this case the crop in question a " + crop + ".");
        }
    }
    
}
