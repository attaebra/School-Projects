import java.util.*;
import java.io.*;

public class WordSearch{
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner keyboard = new Scanner(System.in);

		String filename;
		int[] dimensions = new int[2];

		System.out.println("This program will solve word searches.\n");

		filename = getFilename();
		dimensions = getDimensions(filename);

		char[][] wordGrid = new char[dimensions[0]][dimensions[1]];
		wordGrid = readIn(filename, dimensions[0], dimensions[1]);

		display(dimensions, wordGrid);
		int[] words = new int[]

		while(!(word.equals(""))){

			int x = 0, y = 0;

			// Search for the word.  Note the nested for loops here.  This allows us to
			// start the search at any of the locations in the board.  The search itself
			// is recursive (see findWord method for details).  Note also the boolean
			// which allows us to exit the loop before all of the positions have been
			// tried -- as soon as one solution has been found we can stop looking.

			boolean found = false;
			for (int r = 0; (r < dimensions[0] && !found); r++){
				for (int c = 0; (c < dimensions[1] && !found); c++){
					// Start search for each position at index 0 of the word
					found = findWord(dimensions[0], dimensions[1], words, 0, theBoard);
					if (found)
					{
						x = r;  // store starting indices of solution
						y = c;
					}
				}
			}

			if (found){
				System.out.println("The word: " + word);
				System.out.println("was found starting at location (" + x + "," + y + ")");
				for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < cols; j++)
					{
						System.out.print(theBoard[i][j] + " ");
						theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
					}
					System.out.println();
				}
			}
			else{
				System.out.println("The word: " + word);
				System.out.println("was not found");
			}
			
			System.out.println("Please enter the word to search for:");
			word = (inScan.nextLine()).toLowerCase();
		}
	}

	/**
	*Prompts the user to enter the name of the file containing matrix data
	*@return the validated name of the file
	*@throws FileNotFoundException thrown due to the fact that if file doesn't exist it keeps asking.
	*/
	public static String getFilename() throws FileNotFoundException {
		String filename = "";
		File wordData = new File(filename);
		boolean invalid = true;

		Scanner keyboard = new Scanner(System.in);
		do{
			System.out.print("Please enter the grid filename: ");
			filename = keyboard.nextLine();
			wordData = new File(filename);
			if(!wordData.exists()){
				System.out.println("\nThe file does not exist or is invalid.");
			}
			else{
				invalid = false;
			}
		}while(invalid == true);

		return filename;
	}
	/**
	*Uses file and determines the dimensions for the word search matrix
	*@param filename the file name that the user gave
	*@return the dimensions of the file in an array
	*@throws FileNotFoundException because the file is already validated
	*/
	public static int[] getDimensions(String filename) throws FileNotFoundException {
		File wordData = new File(filename);
		Scanner input = new Scanner(wordData);
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
	*@param rows the amount of rows in the character grid
	*@Param columns the amount of columns in the character grid
	*@return The filled 2D array
	*@throws FileNotFoundException because the file name is already deemed valid.
	*/
	public static char[][] readIn(String filename, int rows, int columns) throws FileNotFoundException {
		File wordData = new File(filename);
		char[][] wordInfo = new char[rows][columns];
		boolean invalid = true;
		char valueHolder[] = new char[columns];

		//read in the data
		Scanner input = new Scanner(wordData);
		input.nextLine();
		//Shit works yo
		try{
			for(int i = 0; i < rows; i++) {
				String value = input.nextLine();
				valueHolder = value.toCharArray();	
				for (int j = 0; j < columns; j++) {		
					wordInfo[i][j] = valueHolder[j];
				}
			}
		}
		catch(NoSuchElementException nse){
			//System.out.println("There is an issue loading the file, please check your data file for errors in dimensions.");
			//System.exit(1);
		}

		return wordInfo;
	}

	/**
	*
	*
	*
	*
	*/
	public static void display(int dims1[], char A[][]){
		//Prints out the first matrix
		System.out.println("");
		for (int i = 0; i < dims1[0]; i++){
			for (int j = 0; j < dims1[1]; j++){
				System.out.print(" "+A[i][j]+" ");
				if (j == dims1[1] - 1){
					System.out.println("\n");
				}
			}
		}
	}

	public static String findWord(char[][] puzzle, String word){
		Scanner keyboard = new Scanner(System.in);
		int amountOfWords = 0;

		System.out.println("Please enter phrase (separated by single spaces):");
		String stringOfWords = keyboard.nextLine;
		String[] arrayOfWords = stringOfWords.toLower().split("");

		amountOfWords = arrayOfWords.length();
		return arrayOfWords;
	}

	// Recursive method to search for the word.  Return true if found and false
	// otherwise.
	public boolean findWord(int r, int c, String word, int loc, char [][] bo) {
		//System.out.println("findWord: " + r + ":" + c + " " + word + ": " + loc); // trace code
		
		// Check boundary conditions
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0)
			return false;
		else if (bo[r][c] != word.charAt(loc))  // char does not match
		return false;
		else  	// current character matches
		{
			bo[r][c] = Character.toUpperCase(bo[r][c]);  // Change it to
				// upper case.  This serves two purposes:
				// 1) It will no longer match a lower case char, so it will
				//    prevent the same letter from being used twice
				// 2) It will show the word on the board when displayed

			boolean answer;
			if (loc == word.length()-1)		// base case - word found and we
				answer = true;				// are done!
				
			else	// Still have more letters to match, so recurse.
			{		// Try all four directions if necessary.
				answer = findWord(r, c+1, word, loc+1, bo);  // Right
				if (!answer)
					answer = findWord(r+1, c, word, loc+1, bo);  // Down
				if (!answer)
					answer = findWord(r, c-1, word, loc+1, bo);  // Left
				if (!answer)
					answer = findWord(r-1, c, word, loc+1, bo);  // Up
				
				// If answer was not found, backtrack.  Note that in order to
				// backtrack for this algorithm, we need to move back in the
				// board (r and c) and in the word index (loc) -- these are both 
				// handled via the activation records, since after the current AR 
				// is popped, we revert to the previous values of these variables.
				// However, we also need to explicitly change the character back
				// to lower case before backtracking.
				if (!answer)
					bo[r][c] = Character.toLowerCase(bo[r][c]);
			}
			return answer;
		}
	}		
}