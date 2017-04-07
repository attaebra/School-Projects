/*Atta Ebrahimi
CS 0445
Assignment Four*/
import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.Scanner;

public class Assign4
{
	private Integer [] myArray;
	private Integer [] copyArray;
	private Random Generator;
	private int trialNumber = 0;
	private double medianAnswer5 = 0, medianAnswer10 = 0, medianAnswer20 = 0, randomPivot = 0;
	private int arrayFill = 0; //0 for Random, 1 for Sorted, 1 for Reverse Parameters
	private String simpleQ = "Simple QuickSort";
	private String randPivot = "Random Pivot";
	private String reversed = "reverse";
	private String rand = "random";
	private String sorted = "sorted";
	private String Med = "Median of Three";
	private FileWriter fwriter;
	private PrintWriter outputFile;
	
	
	public Assign4() throws IOException
	{	
	System.out.print("Enter array size: ");
	Scanner keyboard = new Scanner(System.in);
	
	int arraySize = keyboard.nextInt();
	
	myArray = new Integer [arraySize];
	Generator = new Random();
	
	System.out.print("Enter number of trials: ");
	trialNumber = keyboard.nextInt();
	
	System.out.print("Enter file name: ");
	String fileName = keyboard.next();
	
	fwriter = new FileWriter(fileName, false);
			
	outputFile = new PrintWriter(fwriter);
	
	//start program
	fillArrayRandom();
	
	double answer1 = simpleQuick(1);
	
	toStringFile(simpleQ,rand,answer1);
	
	 //for median 5
	toStringFile(Med + " (5)",rand,medianAnswer5);
	
	 //for median 10
	toStringFile(Med + " (10)",rand,medianAnswer10);
	
	 //for median 20
	toStringFile(Med + " (20)",rand,medianAnswer20);
	
	 //for random pivot
	toStringFile(randPivot + " (5)",rand,randomPivot);
		
	Reset();
	fillArraySorted();
	double answer2 = simpleQuick(2);
	
	//for simple quicksort
	toStringFile(simpleQ,sorted,answer2);
	
	 //for median 5
	toStringFile(Med + " (5)",sorted,medianAnswer5);
	
	 //for median 10
	toStringFile(Med + " (10)",sorted,medianAnswer10);
	
	 //for median 20
	toStringFile(Med + " (20)",sorted,medianAnswer20);
	
	 //for random pivot
	toStringFile(randPivot + " (5)",sorted,randomPivot);
	
	Reset();
	fillArrayReverse();
	double answer3 = simpleQuick(3);
	
	//for simple quicksort
	toStringFile(simpleQ,reversed,answer3);
	
	 //for median 5
	toStringFile(Med + " (5)",reversed,medianAnswer5);
	
	 //for median 10
	toStringFile(Med + " (10)",reversed,medianAnswer10);
	
	//for median 20
	toStringFile(Med + " (20)",reversed,medianAnswer20);
	
	 //for random pivot
	toStringFile(randPivot + " (5)",reversed,randomPivot);
	
	outputFile.close(); //Has to be after all output files

			
	}
	
	
	
	///////Algorithm Runs
	public double simpleQuick(int run)
	{
	
	double simpAnswer = 0;
	double median5 = 0, median10 = 0, median20 = 0, pivot = 0;
	String sortType;
	
	if(run == 1)
	{
	sortType = rand;
	}
	else if(run == 2)
	{
	sortType = sorted;
	}
	else
	{
	sortType = reversed;
	}
	
	for(int i = 0; i < trialNumber; i++)
	{
	
	System.out.println("\n" + toString(simpleQ,sortType));
	
	System.out.print("Before sorting: ");

	printArray();
	long start = System.nanoTime(); 
	
	Quick.quickSort(myArray,myArray.length);  //Simple quick Sort 
	
	long finish = System.nanoTime(); 
	
    double delta = finish - start; 
	
	printEnd(delta);
	//for simple quicksort

	simpAnswer += delta;			//add the delta to the answer 
	myArray = copyArray.clone(); 
	
	System.out.println("\n" + toString(Med + " (5)",sortType));
	System.out.print("Before sorting: ");
	printArray();
	
	median5 += Median(5);			//base case 5
	printEnd(median5);
	
	myArray = copyArray.clone();
	
	System.out.println("\n" + toString(Med + " (10)",sortType));
	System.out.print("Before sorting: ");
	printArray();
	median10 += Median(10);			//base case 10
	printEnd(median10);

	myArray = copyArray.clone();
	System.out.println("\n" + toString(Med + " (20)",sortType));
	System.out.print("Before sorting: ");
	printArray();
	median20 += Median(20);			//base case 20
	printEnd(median20);
	
	myArray = copyArray.clone(); 
	System.out.println("\n" + toString(randPivot + " (5)",sortType));
	System.out.print("Before sorting: ");
	printArray();
	pivot += pivotFunction(); 		//pivot
	printEnd(pivot);
	
	if(run == 1)
	{
	fillArrayRandom();			//fill array randomly again
	}
	else if(run == 2)
	{
	fillArraySorted();			//fill array sorted
	}
	else
	{
	fillArrayReverse();			//fill array reversed
	}
	}
	
	medianAnswer5 = (((double)median5/1000000000) / trialNumber);	//MedianAnswer5
	
	medianAnswer10 = (((double)median10/1000000000) / trialNumber); //MedianAnswer10
	
	medianAnswer20 = (((double)median20/1000000000) / trialNumber); //MedianAnswer10

	randomPivot = (((double)pivot/1000000000) / trialNumber); //random Pivot 
	
	return (((double)simpAnswer/1000000000) / trialNumber);
	}
	
	////////Median
	public double Median(int size)
	{
		
	long start = System.nanoTime();
	
	TextMergeQuick.quickSort(size, myArray, myArray.length);
	
	long finish = System.nanoTime();

    double delta = finish - start;
	
	return delta;
	}
	
	////////Pivot
	public double pivotFunction()
	{
		
	long start = System.nanoTime();
	
	myQuick.quickSort(myArray, myArray.length);  //Simple quick Sort
	
	long finish = System.nanoTime();

    double delta = finish - start;
	
	return delta;
	}
	
	////Fill array randomly
	public void fillArrayRandom()
	{
	for(int i = 0; i < myArray.length; i++)
	{
	Integer randomNumber = Generator.nextInt(myArray.length);
	myArray[i] = randomNumber;
	}
	copyArray = myArray.clone();
	}
	
	
	//////Fill array Sorted
	public void fillArraySorted()
	{
	for(int i = 0; i < myArray.length; i++)
	{
	Integer myInt = new Integer(i + 1);
	myArray[i] = myInt;
	}
	copyArray = myArray.clone();
	}
	
	//////Fill array Reversed
	public void fillArrayReverse()
	{
	int k = 0; //array index							
	for(int i = myArray.length; i > 0; i--)
	{
	Integer myInt = new Integer(i);
	myArray[k] = myInt;//start at 0
	k++;//increment
	}
	copyArray = myArray.clone();
	}
	
	///////To string
	public String toString(String Algorithm, String Order) //double aveTime)
	{
	StringBuilder b = new StringBuilder();
	b.append("Algorithm: " + Algorithm+"\n");
	b.append("\nArray Size: " + myArray.length+"\n");
	b.append("\nOrder: " + Order+"\n");
	b.append("\n\n");
	//b.append("\nTime: " + aveTime);
	return b.toString();
	}
	
	///////To string file, Possibly just add this to the toString method
	public void toStringFile(String Algorithm, String Order, double aveTime)
	{
	StringBuilder d = new StringBuilder();
	d.append("Algorithm: " + Algorithm+"\n\n");
	d.append("\nArray Size: " + myArray.length+"\n\n");
	d.append("\nOrder: " + Order+"\n");
	d.append("\nNumber of trials: " + trialNumber+"\n\n");
	d.append("\nAverage Time: " + aveTime +"\n\n");
	d.append("\n\n");
	outputFile.println(d.toString());
	//d.setLength(0);
	}
	
	public void Reset()	//change times back to 0
	{
	medianAnswer5 = 0;
	medianAnswer10 = 0;
	medianAnswer20 = 0;
	randomPivot = 0;
	}
	
	public void printArray() //print the array
	{
	System.out.println("\n");
	for(int q = 0; q < 20; q++)
	{
	System.out.print(myArray[q] + " ");
	}
	}
	
	public void printEnd(double delta) //print data for the algorithms
	{
	System.out.print("\n\nAfter sorting: ");
	printArray();
	System.out.println("\nTime (in nanoseconds): " + delta);
	}
	
	public static void main(String[] args) throws IOException
	{
	new Assign4();
	}
	
	
}
