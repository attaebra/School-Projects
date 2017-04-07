import java.io.*;
import java.util.*;

public class Anagram{
	
	static String inputString;
	static DictInterface D;
	static int wordCount;
	static TreeSet<String> perWord;
	protected final static ArrayList<SortedSet<String>> anagramList = new ArrayList<SortedSet<String>>(0);

	public static void main(String[] args) throws IOException, InterruptedException{
		//Initialize scanners and output writer, and dictionary type
		String st;
		Scanner fileScan = null;
		Scanner inputFile = null;
		PrintWriter outputFile = null;
		int dictType = 0;
		String dictionaryType = null;
		
		//This will search for the arguments
		try{
			fileScan = new Scanner(new File("dictionary.txt"));
			inputFile = new Scanner(new File(args[0]));
			outputFile = new PrintWriter(new File(args[1]));
			dictionaryType = args[2];
		}
		catch (IOException io){
			System.out.println(io);
			System.exit(0);
		}
		//If the arguments are blank, I allow the user to enter the information so the program can still run
		//This is how I preferred to run the program so I implemented it so I can test faster
		catch (Exception e){
			dictType = getChoice();
			if (dictType == 1) D = new MyDictionary();
			else if (dictType == 2) D = new DLBDict();
			String filename = getFilename("Please enter the name of the input file: ");
			outputFile = new PrintWriter(getFilenameCreate("Please enter the name of the output file: "));
			inputFile = new Scanner(new File(filename));
		}
		//If the arguments are in fact there, this initializes them and continues the program
		if (dictType == 0){
			if (dictionaryType.equals("orig")) D = new MyDictionary();
			else if (dictionaryType.equals("dlb")) D = new DLBDict();
			else{
				System.out.println("The dicionary arguments are incorrect.");
				System.exit(0);
			}
		}
		
		long begin = System.currentTimeMillis();
		//Fills the dictionary into the chosen implementation of the data structure.
		while (fileScan.hasNext()){
			st = fileScan.nextLine();
			D.add(st);
		}
		
		//Adds the strings from the input file into an ArrayList, also removes the spaces.
		ArrayList<String> words = new ArrayList<String>();
		String word = "";
		while(inputFile.hasNext()){
			word = inputFile.nextLine();
			word = word.replaceAll("\\s","");
			words.add(word);
		}
		
		//This finds the anagrams for each individual word and holds the TreeSet of them into the master global ArrayList
		//Implementing it this way made it hard to print the solutions by amount of words in the anagram, so I didn't.
		for (int i = 0; i <= words.size()-1; i++){
			perWord = new TreeSet<String>();
			findAnagrams(new StringBuilder(), words.get(i).toCharArray(), 0, words.get(i).length()-1, perWord);
			anagramList.add(perWord);
		}
		
		//Prints the solutions to the output file in alphabetical order.
		for (int i = 0; i <= anagramList.size()-1; i++){
			outputFile.printf("Here are the solutions for %s:", words.get(i));
			outputFile.println("");
			SortedSet<String> set = anagramList.get(i);
			for(String anAnagram : set){
				outputFile.println(anAnagram);
			}
		}
		
		//Close input and output files.
		inputFile.close();
		outputFile.close();
		
		long end = System.currentTimeMillis();
		System.out.println("The program took: "+(end-begin)+" ms");

	}

	/**
	*Finds valid anagrams of an input and adds them to a TreeSet for that specific word
	*@param s, this is the string that is being built and backtracked throughout the method
	*@param letters this is an array of characters in the string, it holds unused letters
	*@param start, start of the StringBuilder
	*@param end, length of the initial word
	*/
	static void findAnagrams(StringBuilder s, char[] letters, int start, int l, TreeSet<String> eachWord){
		//For each letter in the unused
		for(int i = 0; i <= letters.length-1; i++){
			//Char "*" signifies the character has been removed from my character list, do not use it.
			if (letters[i] != '*'){
				char c = letters[i]; 
				s.append(c);
				letters[i] = '*';
				//Check if the appending letter to the string's current value creates a word or a prefix
				int ret = D.searchPrefix(s, start, s.length()-1);
				//If it is a word or a prefix, recurse on the current value of the word
				if (ret == 1 || ret == 3){
					findAnagrams(s, letters, start, l, eachWord);
				}
				//If the current value of the word is a word or is a word or a prefix
				if ((ret == 2) || (ret == 3)){	
					//If the matched word is less than the length of the source word's length
					if (s.length()-1 < l){
						//Recursively call with new lower bounds.
						findAnagrams(new StringBuilder(s).insert(s.length(), " "), letters, s.length()+1, l+1, eachWord);
					}
					//If the word has not already been added and is the appropriate length.
					if (s.length()-1 == l){
							eachWord.add(new String(s));
					}
					if (letters.length-1 == 0){
						break;
					}
				}
				//Reinsert letters back into it's original position in letters
				letters[i] = c;
				//Remove C from the end of the word.
				s.deleteCharAt(s.length()-1); //remove c from END of word
			}
		}
	}
		
	/**
	*Validates filename for the input file
	*@return the validated name of the file
	*@throws FileNotFoundException thrown due to the fact that if file doesn't exist it keeps asking.
	*/
	public static String getFilename(String message) throws FileNotFoundException {
	String filename = "";
	File confirm = new File(filename);
	boolean invalid = true;
		 
	Scanner keyboard = new Scanner(System.in);
	do{
		System.out.print(message);
		filename = keyboard.nextLine();
			confirm = new File(filename);
				if(!confirm.exists()){
					System.out.print("\nThe file does not exist or is invalid.");
				}
				else{
					invalid = false;
				}
	}while(invalid == true);
		
	return filename;
	}
	
	/**
	*Validates filename for the output file
	*@return the validated name of the file
	*@throws FileNotFoundException thrown due to the fact that if file doesn't exist it keeps asking.
	*@throws IOException, there's no IO issue, if it doesn't exist I create it.
	*/
	public static File getFilenameCreate(String message) throws FileNotFoundException, IOException {
	String filename = "";
	File confirm = new File(filename);
	boolean invalid = true;
		 
	Scanner keyboard = new Scanner(System.in);

		System.out.print(message);
		filename = keyboard.nextLine();
			confirm = new File(filename);
				if(!confirm.exists()){
					confirm.createNewFile();
				}

		
	return confirm;
	}
	/**
	*Allows the user to choose between original and DLB
	*@return The choice of either 1 or 2 indicating original or DLB respectively
	*/
	public static int getChoice(){
		int choice = 0;
		Scanner keyboard = new Scanner(System.in);
		boolean loopboolean = true;
		do{
			try{
			System.out.print("Would you like to use MyDictionary or DLB? Enter: <1 or 2>: ");
			choice = keyboard.nextInt();
				if(choice != 1 && choice != 2){
					System.out.println("\nYou can only choose 0-4!");
				}
				else{
					loopboolean = false;
				}
			}
			catch(InputMismatchException ime){
				System.out.println("\nYou can only choose 1 or 2!");
				keyboard.nextLine();
				continue;
			}
		}while(loopboolean == true);
	return (choice);
	}
}