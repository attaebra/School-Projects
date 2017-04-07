/*Atta Ebrahimi
CS 0445
Assignment Three*/
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Assign3{
	private boolean found;
	private int x1 = 0, y1 =0;
	private Integer ans1, ans2;
	private ArrayList<Integer> locations;//For coordinates
	
	public Assign3()
	{
	ans1 = new Integer(0);
	ans2 = new Integer(0);
	Scanner inScan = new Scanner(System.in);
	File fName;
	String fString;
	Scanner fReader;
	locations = new ArrayList<Integer>();
	//insert grid
	while (true)
        {
           try
           {
               System.out.print("Please enter grid filename: ");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);
			   System.out.println("");
              
               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }
		
		//Split the string
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);
		
		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}

		// Show user the grid
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.print("Please enter phrase (sep. by single spaces): ");
		
		String word = inScan.nextLine().toLowerCase();

		

		while (!(word.equals("")))
		{
		String [] words;
		//search for word
		words = word.split(" ");
		System.out.print("Looking for: ");
		for(int g = 0; g < words. length; g++)
		{
		System.out.print(words[g] + " ");
		}
		int wordLength = words.length;
		System.out.println("\ncontaining " + wordLength + " words");
			int x = 0, y = 0, x1 = 0, y1 = 0;
			boolean firstWord = true;
			
			found = false;
			for (int r = 0; (r < rows && !found); r++)
			{
				for (int c = 0; (c < cols && !found); c++)
				{
				// Start search for each position at index 0 of the word
					found = findWord(r, c, words, 0, theBoard, 0, wordLength, 0);
					if (found)
					{
						x = r;  // store starting indices of solution
						y = c;
					}
				}

			}
			if (found)//word found
			{
				int k = locations.size();
				int sizer = words.length - 1;
				System.out.println("The phrase: " + word + "\nwas found");
				for(int m = 0; m < words.length; m++)
				{					
					System.out.print(words[sizer] + " (" + locations.get(k - 2) + "," + locations.get(k - 1) + ")" );
					System.out.println(" to " + "(" + locations.get(k - 4) + "," + locations.get(k - 3) + ")" );
					k-=4;
					sizer--;
				}
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
			else
			{
				System.out.println("The word: " + word);
				System.out.println("was not found\n");
			}
			
			
			System.out.println("\nPlease enter the word to search for:");
        	word = (inScan.nextLine()).toLowerCase();
			locations.clear();
		}
		}
		
		public boolean findWord(int r, int c, String [] word, int loc, char [][] bo, int wordCount, int wordLength, int direction)
	{
		//System.out.println("findWord: " + r + ":" + c + " " + word[wordCount] + ": " + loc); // trace code
		if(loc == 0)
		{
		}
		// Check boundary conditions
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0)
		{
			return false;
			}
		else if (bo[r][c] != word[wordCount].charAt(loc))  // char does not match
		{
			//no match
			direction = 0;
			if(wordCount == 0)
			{
			return false;
			}
			else		
			{
			wordCount--;
			return false;
			}
			}
		else  	// current character matches
		{
			bo[r][c] = Character.toUpperCase(bo[r][c]); 
				
			boolean answer = false;
			if (loc == word[wordCount].length()-1)		
			{
				if((wordLength - 1) == wordCount)
				{
				ans1 = r;
				ans2 = c;
				locations.add(ans1);
				locations.add(ans2);
				if(direction == 1)
				{
				locations.add(ans1);
				locations.add(ans2 - (word[wordCount].length() - 1));
				}
				else if(direction == 2)
				{
				locations.add(ans1 - (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				else if(direction == 3)
				{
				locations.add(ans1 + (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				else if(direction == 4)
				{
				locations.add(ans1 + (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				answer = true;	
				
				}
				else
				{
				
				ans1 = r;
				ans2 = c;
				locations.add(ans1);
				locations.add(ans2);
				if(direction == 1)
				{
				locations.add(ans1);
				locations.add(ans2 - (word[wordCount].length() - 1));
				}
				if(direction == 2)
				{
				locations.add(ans1 - (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				if(direction == 3)
				{
				locations.add(ans1 + (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				if(direction == 4)
				{
				locations.add(ans1 + (word[wordCount].length() - 1));
				locations.add(ans2);
				}
				answer = false;
				wordCount++;
				loc = 0;
				answer = findWord(r, c+1, word, loc, bo, wordCount, wordLength, 1);
				if(!answer)
				{
				answer = findWord(r+1, c, word, loc, bo, wordCount, wordLength, 2);
				}
				if(!answer)
				{
				answer = findWord(r, c-1, word, loc, bo, wordCount, wordLength, 3);
				}
				if(!answer)
				{
				answer = findWord(r-1, c, word, loc, bo, wordCount, wordLength, 4);
				}
				if (!answer)
					bo[r][c] = Character.toLowerCase(bo[r][c]);
				}
			}
				
			else	// Still have more letters to match, so recurse.
			{		// Try all four directions if necessary.
				if((direction == 0) || (direction == 1))
				{
				answer = findWord(r, c+1, word, loc+1, bo, wordCount, wordLength, 1);  
					}// Right
					
				if ((!answer) && ((direction == 0) || (direction == 2)))
				{
					answer = findWord(r+1, c, word, loc+1, bo, wordCount, wordLength, 2);  // Down
					}
				if ((!answer) && ((direction == 0) || (direction == 3)))
				{	
					answer = findWord(r, c-1, word, loc+1, bo, wordCount, wordLength, 3);  // Left
					}
				if ((!answer) && ((direction == 0) || (direction == 4)))
				{
					answer = findWord(r-1, c, word, loc+1, bo, wordCount, wordLength, 4);  // Up	
					}
					
				if (!answer)
					bo[r][c] = Character.toLowerCase(bo[r][c]);
			}
			return answer;
		}
	}			
	
	public static void main(String[] args)
	{
	new Assign3();
	}
}
