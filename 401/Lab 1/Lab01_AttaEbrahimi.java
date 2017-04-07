/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_attaebrahimi;

/**
 *
 * @author Atta
 */
public class Lab01_AttaEbrahimi {

    /* This is the main method, every program must have one.  It is
     * (almost) always the first method called when a program executes.
     * The arguments (String[] args) come from the command line...more on
     * this later.
     */
    public static void main(String[] args) {
        String name = "Atta Ebrahimi";
        double sideA = 9;
        double sideB = 4;
        double sideC = 10;
        
        double semiperimeter1 = 0.5 * (sideA + sideB + sideC);
        System.out.println("The semiperimeter of the triangle is "+semiperimeter1 +".");
        
        double semiperimeter2 = (sideA + sideB + sideC) / 2;
        System.out.println("The semiperimeter of the triangle is "+semiperimeter2 +".");
        
        System.out.println(name+", the program is now ending.");
    }
    
}
