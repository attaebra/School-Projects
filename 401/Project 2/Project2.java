/**
This program performs basic matrix matrix operations
such as addition, subtraction, multiplication.
*/

import java.util.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.*;

public class Project2{
        
	public static void main(String[] args) throws FileNotFoundException, IOException {
				Scanner keyboard = new Scanner(System.in);
                int N = 100, choice2 = 0;
                double[][] matrix1 = new double[N][N];
                double[][] matrix2 = new double[N][N];
                double[][] matrix3 = new double[N][N];
				int[] dims1 = new  int[2];
				int[] dims2 = new int[2];
				String filename1, filename2, choice, pick2, again, again2;
				
				System.out.println("This program performs basic matrix operations, please view the\nexample to determine matrix file format.\n\n");
				do{
						//Import First Matrix
						filename1 = getFilename();
						dims1 = getDimensions(filename1);
						matrix1 = readIn(filename1, dims1[0], dims1[1]);
						
						//Import Second Matrix
						filename2 = getFilename();
						dims2 = getDimensions(filename2);
						matrix2 = readIn(filename2, dims2[0], dims2[1]);
					do{	
						//Choice of arithmetic
						choice = getChoice();
						
						//Using via Switch
						switch(choice.toUpperCase()){
							case "A":
								matrix3 = add(dims1, dims2, matrix1, matrix2, matrix3);
								Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								break;
							case "S":
								matrix3 = subtract(dims1, dims2, matrix1, matrix2, matrix3);
								Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								break;
							case "M":
								matrix3 = multiply(dims1, dims2, matrix1, matrix2, matrix3);
								Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								break;
							case "E":
								matrix3 = element(dims1, dims2, matrix1, matrix2, matrix3);
								Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
								break;
							case "T":
								choice2 = getAnotherChoice();
								switch(choice2){
									case 1:
										matrix3 = transpose(dims1, matrix1);
										Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
										printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
										break;
									case 2:
										matrix3 = transpose(dims2, matrix2);
										Display(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
										printresults(dims1, dims2, choice, choice2, matrix1, matrix2, matrix3);
										break;
								}
								break;
						}
					System.out.print("Would you like to perform a different operation? \"Y\" or \"N\": ");
					again = keyboard.next();
					while(!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")){
						System.out.print("Please enter \"Y\" or \"N\": ");
						again = keyboard.next();
					}
				}while(again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n"));
						System.out.print("Would you like to run with different matrices? \"Y\" or \"N\": ");
						again2 = keyboard.next();
						while(!again2.equalsIgnoreCase("Y") && !again2.equalsIgnoreCase("N")){
							System.out.print("Please enter \"Y\" or \"N\": ");
							again2 = keyboard.next();
						}
				}while(again2.equalsIgnoreCase("y") && !again2.equalsIgnoreCase("n"));
				
	}
	/**
	*Prompts the user to enter the name of the file containing matrix data
	*@return the validated name of the file
	*@throws FileNotFoundException thrown due to the fact that if file doesn't exist it keeps asking.
	*/
	public static String getFilename() throws FileNotFoundException {
		String filename = "";
		File matrixData = new File(filename);
		boolean invalid = true;
             
		Scanner keyboard = new Scanner(System.in);
		do{
			System.out.print("Please enter the name of the file for the matrix: ");
			filename = keyboard.nextLine();
				matrixData = new File(filename);
					if(!matrixData.exists()){
						System.out.println("\nThe file does not exist or is invalid.");
					}
					else{
						invalid = false;
					}
		}while(invalid == true);
			
		return filename;
	}
	/**
	*Uses file and determines the dimensions for the matrix
	*@param filename the file name that the user gave.
	*@return the dimensions of the file in an array
	*@throws FileNotFoundException because the file is already validated
	*/
	public static int[] getDimensions(String filename) throws FileNotFoundException {
				File matrixData = new File(filename);
				Scanner input = new Scanner(matrixData);
				// pre-read in the number of rows/columns
				int [] dimensions = new int[2];
				dimensions[0] = input.nextInt();
				dimensions[1] = input.nextInt();
				input.close();
				return dimensions;
	}
	/**
	*Reads in the file information from the given file
	*@param filename the file name that the user gave
	*@param rows the amount of rows in the matrix
	*@Param columns the amount of columns in the matrix
	*@return The filled 2D array
	*@throws FileNotFoundException because the file name is already deemed valid.
	*/
	public static double[][] readIn(String filename, int rows, int columns) throws FileNotFoundException {
		int N = 100;
		File matrixData = new File(filename);
		double[][] matrixinfo = new double[N][N];
		boolean invalid = true;
                        
				//read in the data
				Scanner input = new Scanner(matrixData);
				input.nextLine();
				try{
					for(int i = 0; i < rows; i++) {
						for (int j = 0; j < columns; j++){
								double value;
								value = input.nextDouble();
								matrixinfo[i][j] = value;
							}
						}
				}
				catch(NoSuchElementException nse){
					System.out.println("There is an issue loading the file, please check your matrix file for errors in dimensions.");
					System.exit(1);
				}
				
				return matrixinfo;
	}
	/**
	*Asks the user what matrix operation they would like to perform
	*@return The choice of operation
	*/
	public static String getChoice() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("\n\tEnter A to add two matrices\n\tEnter S to subtract two matrices\n\tEnter M to multiply two matrices\n\tEnter E to multiply the element by elements of two matrices\n\tEnter T to transpose one of the two files\n\t\nPlease enter your choice: ");
		String pick = keyboard.nextLine();
		
		while(!pick.equalsIgnoreCase("A") && !pick.equalsIgnoreCase("S") && !pick.equalsIgnoreCase("M") && !pick.equalsIgnoreCase("E") && !pick.equalsIgnoreCase("N") &&!pick.equalsIgnoreCase("T")){
		System.out.print("\nPlease pick a valid choice from below: ");
		System.out.print("\n\tEnter A to add two matrices\n\tEnter S to subtract two matrices\n\tEnter M to multiply two matrices\n\tEnter E to multiply the element by elements of two matrices\n\tEnter T to transpose one of the two files\n\t\nPlease enter your choice: ");
		pick = keyboard.nextLine();
		}
	return pick;
	}
	/**
	*Asks the user if they would like to transpose the first matrix or the second
	*@return The choice of matrix
	*/
	public static int getAnotherChoice() {
		int pick = 0;
		Scanner keyboard = new Scanner(System.in);
		boolean usercantreadboolean = true;
		do{
			try{
			System.out.print("\nWould you like to transpose the first file or the second?\n1. First file\n2. Second file\nEnter your choice: ");
			pick = keyboard.nextInt();
				if(pick != 1 && pick != 2){
					System.out.println("\nYou can only choose 1 or 2!");
				}
				else{
					usercantreadboolean = false;
				}
			}
			catch(InputMismatchException ime){
				System.out.println("\nYou can only choose 1 or 2!");
				keyboard.nextLine();
				continue;
			}
		}while(usercantreadboolean == true);
	return pick;
	}
	/**
	*Asks the user if they would like to save the file or not
	*@return The choice of yes or no for saving the file
	*/
	public static int getYetAnotherChoice() {
		int pick2 = 0;
		Scanner keyboard = new Scanner(System.in);
		boolean usercantreadboolean2 = true;
		do{
			try{
			System.out.print("\nWould you like to save the file?\n1. Yes\n2. No\nEnter your choice: ");
			pick2 = keyboard.nextInt();
				if(pick2 != 1 && pick2 != 2){
					System.out.println("\nYou can only choose 1 or 2!");
				}
				else{
					usercantreadboolean2 = false;
				}
			}
			catch(InputMismatchException ime){
				System.out.println("\nYou can only choose 1 or 2!");
				keyboard.nextLine();
				continue;
			}
		}while(usercantreadboolean2 == true);
	return pick2;
	}
	/**
	*Performs the math for the addition of two matrices
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param A The first matrix
	*@param B The second matrix
	*@param C an empty array that I would like to be filled with the answer
	*@return The answer to the operation
	*/
	public static double[][] add(int dims1[], int dims2[], double A[][], double B[][], double C[][]){
		if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
			for (int i = 0; i < dims1[0]; i++){
				for (int j = 0; j < dims1[1]; j++){
					C[i][j] = A[i][j] + B[i][j];
				}
			}
		}
		else{
			System.out.println("\nCAN'T ADD: THE TWO MATRICES HAVE DIFFERENT DIMENSIONS");
		}
	return C;
	}
	/**
	*Performs the math for the subtraction of two matrices
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param A The first matrix
	*@param B The second matrix
	*@param C an empty array that I would like to be filled with the answer
	*@return The answer to the operation
	*/
	public static double[][] subtract(int dims1[], int dims2[], double A[][], double B[][], double C[][]){
		if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
			for (int i = 0; i < dims1[0]; i++){
				for (int j = 0; j < dims1[1]; j++){
					C[i][j] = A[i][j] - B[i][j];
				}
			}
		}
		else{
			System.out.println("\nCAN'T SUBTRACT: THE TWO MATRICES HAVE DIFFERENT DIMENSIONS");
		}
	return C;
	}
	/**
	*Performs the math for the multiplication of two matrices
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param A The first matrix
	*@param B The second matrix
	*@param C an empty array that I would like to be filled with the answer
	*@return The answer to the operation
	*/
	public static double[][] multiply(int dims1[], int dims2[], double A[][], double B[][], double C[][]){
		double sum = 0;
		if (dims1[1] == dims2[0]){
			for (int i = 0; i < dims1[0]; i++){
				for (int j = 0; j < dims2[1]; j++){
					for (int k = 0; k < dims1[1]; k++){
						sum = sum + A[i][k] * B[k][j]; 
					}
					C[i][j] = sum;
					sum = 0;
				}
			}
		}
		else{
			System.out.println("\nCAN'T MULTIPLY: THE TWO MATRICES HAVE INCOMPATIBLE DIMENSIONS");
		}
		return C;
	}
		/**
	*Performs the math for the element by element multiplication of two matrices
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param A The first matrix
	*@param B The second matrix
	*@param C an empty array that I would like to be filled with the answer
	*@return The answer to the operation
	*/
	public static double[][] element(int dims1[], int dims2[], double A[][], double B[][], double C[][]){
	if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
		for (int i = 0; i < dims1[0]; i++){
			for (int j = 0; j < dims1[1]; j++){
				C[i][j] = A[i][j] * B[i][j];
			}
		}
	}
	else{
		System.out.println("\nCAN'T PERFORM ELEMENT BY ELEMENT MULTIPLICATION: THE TWO MATRICES HAVE DIFFERENT DIMENSIONS");
	}
	return C;
}
	/**
	*Performs the math for the transpose of the chosen matrix
	*@param dims1 The dimensions of the matrix chosen for the operation
	*@param A The matrix that the operation is being performed on
	*@return The answer to the operation
	*/
	public static double[][] transpose(int dims1[], double A[][]) {
		int N = 100;
        double[][] temp = new double[N][N];
        for (int i = 0; i < dims1[0]; i++)
            for (int j = 0; j < dims1[1]; j++)
                temp[j][i] = A[i][j];
        return temp;
	}
	/**
	*Displays the result of the chosen operation
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param choice The operation that the user chose
	*@param choice2 The matrix that user chose to transpose
	*@param A The first matrix
	*@param B The second matrix
	*@param C The result of said operation
	*/
	public static void Display(int dims1[], int dims2[], String choice, int choice2, double A[][], double B[][], double C[][]){
		switch(choice.toUpperCase()){
			case "A":
				if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
				//Prints out the first matrix
				System.out.println("\tA = \n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", A[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints second matrix
				System.out.printf("\tB = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", B[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints result matrix
				System.out.printf("\tA + B = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", C[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");;
							}
						}
					}
				}
				break;
			case "S":
				if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
				//Prints out the first matrix
				System.out.println("\tA = \n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", A[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints  out the second matrix
				System.out.printf("\tB = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", B[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints result matrix
				System.out.printf("\tA - B = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", C[i][j]);
							if (j == dims1[1] - 1){
								System.out.printf("\n");;
							}
						}
					}
				}
				break;
			case "M":
				if (dims1[1] == dims2[0]){
					//Prints out the first matrix
					System.out.println("\tA = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							System.out.printf("\t%.2f\t", A[i][j]);
								if (j == dims1[1] - 1){
									System.out.println("\n");
								}
						}
					}
					//Prints out the second matrix
					System.out.printf("\tB = \n");
					for (int i = 0; i < dims2[0]; i++){
						for (int j = 0; j < dims2[1]; j++){
							System.out.printf("\t%.2f\t", B[i][j]);
							if (j == dims2[1] - 1){
								System.out.println("\n");
							}
						}
					}
					//Prints result matrix
					System.out.printf("\tA * B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims2[1]; j++){
							System.out.printf("\t%.2f\t", C[i][j]);
							if (j == dims2[1] - 1){
								System.out.println("\n");
							}
						}
					}
				}
				break;
			case "E":
				if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
				//Prints out the first matrix
				System.out.println("\tA = \n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", A[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints second matrix
				System.out.printf("\tB = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", B[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");
							}
					}
				}
				System.out.print("\n");
				//Prints result matrix
				System.out.printf("\tA * B = \n\n");
				for (int i = 0; i < dims1[0]; i++){
					for (int j = 0; j < dims1[1]; j++){
						System.out.printf("\t%.2f\t", C[i][j]);
							if (j == dims1[1] - 1){
								System.out.println("\n");;
							}
						}
					}
				}
				break;
				case "T":
						switch(choice2){
							case 1:
								//Prints out original matrix
								System.out.println("\tA = \n");
								for (int i = 0; i < dims1[0]; i++){
									for (int j = 0; j < dims1[1]; j++){
										System.out.printf("\t%.2f\t", A[i][j]);
											if (j == dims1[1] - 1){
												System.out.println("\n");
											}
									}
								}
								//Prints transpose
								System.out.printf("\tTranspose A = \n\n");
								for (int i = 0; i < dims1[1]; i++){
									for (int j = 0; j < dims1[0]; j++){
										System.out.printf("\t%.2f\t", C[i][j]);
											if (j == dims1[0] - 1){
												System.out.println("\n");;
											}
									}
								}
								break;
							case 2:
								//Prints out original matrix
								System.out.println("\tB = \n");
								for (int i = 0; i < dims1[0]; i++){
									for (int j = 0; j < dims1[1]; j++){
										System.out.printf("\t%.2f\t", A[i][j]);
											if (j == dims1[1] - 1){
												System.out.println("\n");
											}
									}
								}
								//Prints transpose
								System.out.printf("\tTranspose B = \n\n");
								for (int i = 0; i < dims1[1]; i++){
									for (int j = 0; j < dims1[0]; j++){
										System.out.printf("\t%.2f\t", C[i][j]);
											if (j == dims1[0] - 1){
												System.out.println("\n");;
											}
									}
								}
								break;
						}
		}
	}
	/**
	*Displays the result of the chosen operation
	*@param dims1 The dimensions of the first matrix
	*@param dims2 The dimensions of the second matrix
	*@param choice The operation that the user chose
	*@param choice2 The matrix that user chose to transpose
	*@param A The first matrix
	*@param B The second matrix
	*@param C The result of said operation
	@throws IOException thrown when the file name input is invalid and cannot be created
	*/
	public static void printresults(int dims1[], int dims2[], String choice, int choice2, double A[][], double B[][], double C[][]) throws IOException {
		DecimalFormat df = new DecimalFormat("##.##");
		String record = "";
		File printResults = new File(record);
		boolean invalid = true;
        Scanner keyboard = new Scanner(System.in);
		int savechoice = getYetAnotherChoice();
		
		switch(savechoice){
			case 1:
			do{
				try{
					System.out.print("Please enter the name of the file to print to: ");
					record = keyboard.nextLine();
					printResults = new File(record);
						if(!printResults.exists()){
							System.out.print("\nThe file does not exist, creating the file...");
							printResults.createNewFile();
							invalid = false;
						}
						else{
							invalid = false;
						}
				}
				catch(IOException e){
						System.out.println("\nThe name you entered is invalid, try again.");
						continue;
				}
			}while(invalid == true);
				
				FileWriter fw = new FileWriter(printResults, true);
				PrintWriter pw = new PrintWriter(fw);
				
				switch(choice.toUpperCase()){
				case "A":
					if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
					//Prints out the first matrix
					pw.println("__________________________________________");
					pw.println("A = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(A[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					System.out.print("\n");
					//Prints second matrix
					pw.println("B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+B[i][j]+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					pw.print("\n");
					//Prints result matrix
					pw.println("A + B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+C[i][j]+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
							}
						}
					}
					pw.close();
					break;
				case "S":
					pw.println("__________________________________________");
					if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
					//Prints out the first matrix
					pw.println("A = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(A[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					System.out.print("\n");
					//Prints second matrix
					pw.println("B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(B[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					pw.print("\n");
					//Prints result matrix
					pw.println("A - B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(C[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
							}
						}
					}
					pw.close();
					break;
				case "M":
					if (dims1[1] == dims2[0]){
						//Prints out the first matrix
						pw.println("__________________________________________");
						pw.println("A = \n");
						for (int i = 0; i < dims1[0]; i++){
							for (int j = 0; j < dims1[1]; j++){
								pw.print("\t"+df.format(A[i][j])+"\t");
									if (j == dims1[1] - 1){
										pw.println("\n");
									}
							}
						}
						//Prints out the second matrix
						pw.println("B = \n");
						for (int i = 0; i < dims2[0]; i++){
							for (int j = 0; j < dims2[1]; j++){
								pw.print("\t"+df.format(B[i][j])+"\t");
								if (j == dims2[1] - 1){
									pw.println("\n");
								}
							}
						}
						//Prints result matrix
						pw.println("A * B = \n");
						for (int i = 0; i < dims1[0]; i++){
							for (int j = 0; j < dims2[1]; j++){
								pw.print("\t"+df.format(C[i][j])+"\t");
								if (j == dims2[1] - 1){
									pw.println("\n");
								}
							}
						}
					}
					pw.close();
					break;
				case "E":
					if (dims1[0] == dims2[0] && dims1[1] == dims2[1]){
					//Prints out the first matrix
					pw.println("__________________________________________");
					pw.println("A = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(A[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					pw.print("\n");
					//Prints second matrix
					pw.println("B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(B[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
						}
					}
					pw.print("\n");
					//Prints result matrix
					pw.println("A * B = \n");
					for (int i = 0; i < dims1[0]; i++){
						for (int j = 0; j < dims1[1]; j++){
							pw.print("\t"+df.format(C[i][j])+"\t");
								if (j == dims1[1] - 1){
									pw.println("\n");
								}
							}
						}
					}
					pw.close();
					break;
				case "T":
							switch(choice2){
								case 1:
									pw.println("__________________________________________");
									pw.print("\n");
									//Prints out original matrix
									pw.println("A = \n");
									for (int i = 0; i < dims1[0]; i++){
										for (int j = 0; j < dims1[1]; j++){
											pw.print("\t"+df.format(A[i][j])+"\t");
												if (j == dims1[1] - 1){
													pw.println("\n");
												}
										}
									}
									pw.print("\n");
									//Prints transpose
									pw.println("Transpose A = \n\n");
									for (int i = 0; i < dims1[1]; i++){
										for (int j = 0; j < dims1[0]; j++){
											pw.print("\t"+C[i][j]+"\t");
												if (j == dims1[0] - 1){
													pw.println("\n");;
												}
										}
									}
									break;
								case 2:
									pw.println("__________________________________________");
									pw.print("\n");
									//Prints out original matrix
									pw.println("B = \n");
									for (int i = 0; i < dims1[0]; i++){
										for (int j = 0; j < dims1[1]; j++){
											pw.print("\t"+A[i][j]+"\t");
												if (j == dims1[1] - 1){
													pw.println("\n");
												}
										}
									}
									pw.print("\n");								
									//Prints transpose
									pw.println("Transpose B = \n\n");
									for (int i = 0; i < dims1[1]; i++){
										for (int j = 0; j < dims1[0]; j++){
											pw.print("\t"+C[i][j]+"\t");
												if (j == dims1[0] - 1){
													pw.println("\n");;
												}
										}
									}
									break;
							}
							pw.close();
							break;
				}
				break;
			case 2:
				break;
		}
	}
}