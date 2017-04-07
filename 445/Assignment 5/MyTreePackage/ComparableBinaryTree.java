package MyTreePackage;
import StackAndQueuePackage.*; // Needed by tree iterators
import java.util.*;
import java.io.*;	// Needed for Assignment 5 methods


/*Comparable Binary Tree
Get min and get max gets the data of the trees and when the tree is traversed
determine which value is smallest and largest

Is Binary search Tree,,,,, If node isn't going in descending order then it isn't a binary
search tree......Left value must be smaller than the right value
Return true for binarysearchtree() because it is always a binary search tree*/

public class ComparableBinaryTree<T extends Comparable<? super T>> extends BinaryTree<T> implements ComparableTreeInterface<T>
{
	public ComparableBinaryTree()
	{}
	
	public T getMax()	// If the tree is not empty, return the maximum
						// value in the tree; otherwise return null
	{
	BinaryNode<T> rootNode = getRootNode();
	if(rootNode == null)
	{
	return null;
	}
	else{
	rootNode = getMaxRecurse(rootNode, rootNode, rootNode);//call recursive function
	return (T)rootNode.getData();
	}
	}
	
	private BinaryNode<T> getMaxRecurse(BinaryNode<T> searcher, BinaryNode<T> max, BinaryNode<T> max1) //Gets max 
	{
	if(searcher.isLeaf())	//if the node is a leaf node then return
	{
	if(max1.getData().compareTo(max.getData()) > 0)
	{
	max = max1;
	}
	return max;
	}
	
	if(searcher.getLeftChild() != null)	//if tree has a left child get max 
	{
	if((searcher.getData().compareTo(searcher.getLeftChild().getData())) < 0)
	{
	max = searcher.getLeftChild();
	}

	max = getMaxRecurse(searcher.getLeftChild(), max, max1);
	}
	
	
	if(searcher.getRightChild() != null)//if tree has a right child get max
	{

	if((searcher.getData().compareTo(searcher.getRightChild().getData())) < 0)
	{
	max1 = searcher.getRightChild();
	}

	max1 = getMaxRecurse(searcher.getRightChild(), max, max1);
	}

	if(max1.getData().compareTo(max.getData()) > 0)//if max1 is > max then translate max1 into max
	{
	max = max1;
	}
	return max;
	
	}
	
	public T getMin()	// If the tree is not empty, return the minimum
						// value in the tree; otherwise return null
	{
	BinaryNode<T> rootNode = getRootNode();
	if(rootNode == null)
	{
	return null;
	}
	else{
	rootNode = getMinRecurse(rootNode, rootNode, rootNode);//Call Recursive method
	return (T)rootNode.getData();
	}
	}
	
	private BinaryNode<T> getMinRecurse(BinaryNode<T> searcher, BinaryNode<T> min, BinaryNode<T> min1) //Get Minimum
	{
	if(searcher.isLeaf())//If node is a Leaf
	{
	if(min1.getData().compareTo(min.getData()) < 0)
	{
	min = min1;
	}
	return min;
	}
	
	if(searcher.getLeftChild() != null)//Search left subtree
	{
	if((searcher.getData().compareTo(searcher.getLeftChild().getData())) > 0)
	{
	min = searcher.getLeftChild();
	}

	min = getMinRecurse(searcher.getLeftChild(), min, min1);
	}
	
	
	if(searcher.getRightChild() != null)//Search right subtree
	{

	if((searcher.getData().compareTo(searcher.getRightChild().getData())) > 0)
	{
	min1 = searcher.getRightChild();
	}

	min1 = getMinRecurse(searcher.getRightChild(), min, min1);
	}

	if(min1.getData().compareTo(min.getData()) < 0)//if min1 is less than min then translate min1 into min
	{
	min = min1;
	}
	return min;
	
	}

	public boolean isBST()	// Return true if the the tree meets the
					// recursive definition of a BST; else
							// return false 
	{
	BinaryNode<T> searcher = getRootNode();
	ArrayList<BinaryNode<T>> myList = new ArrayList<BinaryNode<T>>();		//arraylist to hold inorder values

	boolean bool = isBST(searcher, searcher, myList);
	for(int i = 0; i < myList.size() - 1; i++)
	{
	if((myList.get(i).getData().compareTo(myList.get(i + 1).getData())) > 0)	//see if values are increasing
	{
	return false;
	}
	}
	return true;
	}
	
	private boolean isBST(BinaryNode<T> searcher, BinaryNode<T> previous, ArrayList<BinaryNode<T>> myList) //traverse in order
	{
	boolean answer = true;
	if(searcher.isLeaf())	
	{
	myList.add(searcher);	//add to arraylist
	return true;
	}
	else
	{
	if(searcher.hasLeftChild())
	{
	answer = isBST(searcher.getLeftChild(), previous, myList);
	}
	myList.add(searcher);	//add to arraylist

	if(searcher.hasRightChild())
	{
	answer = isBST(searcher.getRightChild(), previous, myList);
	}
	}
	return answer;
	
	
	
	}
	
	
}
	
	

