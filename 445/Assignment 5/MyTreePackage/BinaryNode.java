/*Comparable binary tree is subclass of binary tree
and super class of binary search tree*/



// CS 0445 Spring 2015
// BinaryNode class for Assignment 5.  Add the methods specified in the
// assignment sheet so that this class works correctly.
package MyTreePackage;
/**
   A class that represents nodes in a binary tree.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class BinaryNode<T>
{
   private T             data;
   private BinaryNode<T> leftChild;  // Reference to left child
   private BinaryNode<T> rightChild; // Reference to right child

   public BinaryNode()
   {
      this(null); // Call next constructor
   } // end default constructor

   public BinaryNode(T dataPortion)
   {
      this(dataPortion, null, null); // Call next constructor
   } // end constructor

   public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild,
                                    BinaryNode<T> newRightChild)
   {
      data = dataPortion;
      leftChild = newLeftChild;
      rightChild = newRightChild;
   } // end constructor

   /** Retrieves the data portion of this node.
       @return  The object in the data portion of the node. */
   public T getData()
   {
      return data;
   } // end getData

   /** Sets the data portion of this node.
       @param newData  The data object. */
   public void setData(T newData)
   {
      data = newData;
   } // end setData

   /** Retrieves the left child of this node.
       @return  The node’s left child. */
   public BinaryNode<T> getLeftChild()
   {
      return leftChild;
   } // end getLeftChild

   /** Sets this node’s left child to a given node.
       @param newLeftChild  A node that will be the left child. */
   public void setLeftChild(BinaryNode<T> newLeftChild)
   {
      leftChild = newLeftChild;
   } // end setLeftChild

   /** Detects whether this node has a left child.
       @return  True if the node has a left child. */
   public boolean hasLeftChild()
   {
      return leftChild != null;
   } // end hasLeftChild

   /** Retrieves the right child of this node.
       @return  The node’s right child. */
   public BinaryNode<T> getRightChild()
   {
      return rightChild;
   } // end getRightChild
   
   /** Sets this node’s right child to a given node.
       @param newRightChild  A node that will be the right child. */
   public void setRightChild(BinaryNode<T> newRightChild)
   {
      rightChild = newRightChild;
   } // end setRightChild
   
   /** Detects whether this node has a right child.
       @return  True if the node has a right child. */
   public boolean hasRightChild()
   {
      return rightChild != null;
   } // end hasRightChild
   
   /** Detects whether this node is a leaf.
       @return  True if the node is a leaf. */
   public boolean isLeaf()
   {
      return (leftChild == null) && (rightChild == null);
   } // end isLeaf

   /** Counts the nodes in the subtree rooted at this node.
       @return  The number of nodes in the subtree rooted at this node. */
   public int getNumberOfNodes()
   {
      int leftNumber = 0;
      int rightNumber = 0;
      
      if (leftChild != null)
         leftNumber = leftChild.getNumberOfNodes();
      
      if (rightChild != null)
         rightNumber = rightChild.getNumberOfNodes();
      
      return 1 + leftNumber + rightNumber;
   } // end getNumberOfNodes
   
   /** Computes the height of the subtree rooted at this node.
       @return  The height of the subtree rooted at this node. */
   public int getHeight()
   {
      return getHeight(this); // Call private getHeight
   } // end getHeight

   private int getHeight(BinaryNode<T> node)
   {
      int height = 0;

      if (node != null)
         height = 1 + Math.max(getHeight(node.getLeftChild()),
                               getHeight(node.getRightChild()));
                             
      return height;
   } // end getHeight

   /** Copies the subtree rooted at this node.
       @return  The root of a copy of the subtree rooted at this node. */
   public BinaryNode<T> copy()
   {
      BinaryNode<T> newRoot = new BinaryNode<>(data);
      
      if (leftChild != null)
         newRoot.setLeftChild(leftChild.copy());
      
      if (rightChild != null)
         newRoot.setRightChild(rightChild.copy());
      
      return newRoot;
   } // end copy
   
   	// **********************************
	// Complete the additional methods below
	// **********************************
	
	public boolean isFull()	// If the tree is a full tree, return true
	{						// Otherwise, return false.  See notes for
							// definition of full.
							//If all nodes have either 2 children or no children it is full
							//else it is not
							
	boolean isFull;
	if((hasRightChild() && !hasLeftChild()) || (!hasRightChild() && hasLeftChild()))	//can't be full
	{
	isFull = false;
	}
	else if(isLeaf())
	{
	isFull = true;
	}
	else
	{
	boolean isFull1;
	isFull = leftChild.isFull();	//is left child full
	isFull1 = rightChild.isFull();	//is right child full
	if((isFull && isFull1) && (leftChild.getNumberOfNodes() == rightChild.getNumberOfNodes())) //requirements to be true
	{
	isFull = true;
	}
	else
	{
	isFull = false;
	}
	}
	
	return isFull;
	
	}
	
  	public boolean isBalanced(int k)	// Return true if 1) the difference
	{	// in height between the left and right subtrees is at most k,
		// and 2) the left and right subtrees are both recursively
		// k-balanced; return false otherwise
		return balancer(this,k,0,0);
	}
	
	private boolean balancer(BinaryNode<T> node, int k, int lHeight, int rHeight) //determine k balance
	{
	if((Math.abs(lHeight - rHeight)) > k)	//heights aren't balanced
	{
	return false;
	}
	else if(node == null || node.isLeaf())
	{
	return true;
	}
	else if(node.hasRightChild() && !node.hasLeftChild())	//balance checker
	{
	return balancer(node.getRightChild(),k,lHeight,rHeight + 1);
	}
	else if(!node.hasRightChild() && node.hasLeftChild()) //balance checker
	{
	return balancer(node.getLeftChild(),k,lHeight + 1,rHeight);
	}
	else
	{
	return balancer(node.getLeftChild(),k,lHeight,rHeight) && balancer(node.getRightChild(),k,lHeight,rHeight); //true if makes it here
	}
	}
	
	public void drawGraphically() {
        BinaryNode<T>[] root = new BinaryNode[1];
        root[0] = this;
        drawGraphicallyRecursive(getHeight(), getMaxLength() + 1, root);
    }
    private int getMaxLength() {
        return getMaxLengthRecursive(getData().toString().length(), this);
    }
    private int getMaxLengthRecursive(int maxLength, BinaryNode node) {
        if (!node.hasLeftChild() && !node.hasRightChild()) { //base case
            return maxLength;
        }
        if (node.hasRightChild() && node.getRightChild().getData().toString().length() > maxLength) {
            maxLength = node.getRightChild().getData().toString().length();
        }
        if (node.hasLeftChild() && node.getLeftChild().getData().toString().length() > maxLength) {
            maxLength = node.getLeftChild().getData().toString().length();
        }
        if (node.hasRightChild()) {
            maxLength = getMaxLengthRecursive(maxLength, node.getRightChild());
        }
        if (node.hasLeftChild()) {
            maxLength = getMaxLengthRecursive(maxLength, node.getLeftChild());
        }
        return maxLength;
    }
    private double spacingSummation(int maxWidth, int height) {
        if (height <= 1) {
            return 0;
        } else {
            return maxWidth * Math.pow(2, height - 3) + spacingSummation(maxWidth, height - 1);
        }
    }
    private void drawGraphicallyRecursive(int height, int maxWidth, BinaryNode<T>[] roots) {
        // test to see if too big to display
        if (height >= 10) {
            System.out.println("Tree height too large to draw graphically");
            return;
        }
        // first, print horizontal row of nodes
        // prints 1st filler, node data + filler, and 2nd filler for each root
        if (roots.length == 1) {
            System.out.println();
        }
        for (BinaryNode<T> node : roots) {
            for (int filler = 0; filler < spacingSummation(maxWidth, height); filler++) { //first filler
                System.out.print(" ");
            }
            if (node != null) { //print node if present
                System.out.print(node.getData().toString()); //print data
                for (int filler = 0; filler <= maxWidth - node.getData().toString().length(); filler++) { //fill remaining data space
                    System.out.print(".");
                }
            } else {
                for (int filler = 0; filler <= maxWidth; filler++) { //else fill empty data space
                    System.out.print(" ");
                }
            }
            for (int filler = 0; filler < spacingSummation(maxWidth, height); filler++) { // second filler
                System.out.print(" ");
            }
        }
        System.out.println();
        // second, print n rows of diagonal lines
        // prints 1st space, / + filler, 2nd space, \ + filler, and 3rd space for each node, for each row
        for (int row = 0; row <= spacingSummation(maxWidth, height)/2; row++) {
            for (BinaryNode<T> node : roots) {
                for (int filler = 0; filler < spacingSummation(maxWidth, height)/2; filler++) {
                    System.out.print(" ");
                }
                String firstDiagonal = "", secondDiagonal = "";
                for (int filler = 0; filler < spacingSummation(maxWidth, height)/2 - row; filler++) {
                    firstDiagonal += " ";
                }
                for (int filler = 0; filler < row; filler++) {
                    secondDiagonal += " ";
                }
                if (node != null) {
                    if (node.hasLeftChild()) {
                        firstDiagonal += "/";
                    } else {
                        firstDiagonal += " ";
                    }
                    if (node.hasRightChild()) {
                        secondDiagonal += "\\";
                    } else {
                        secondDiagonal += " ";
                    }
                } else {
                    firstDiagonal += " ";
                    secondDiagonal += " ";
                }
                for (int filler = 0; filler < row + maxWidth; filler++) {
                    firstDiagonal += " ";
                }
                for (int filler = 0; filler <= spacingSummation(maxWidth, height)/2 - row + spacingSummation(maxWidth, height - 1); filler++) {
                    secondDiagonal += " ";
                }
                System.out.print(firstDiagonal + secondDiagonal);
            }
            System.out.println();
        }
        // third, recursively call next generation of tree
        if (height
                > 1) {
            BinaryNode[] children = new BinaryNode[roots.length * 2];
            for (int node = 0; node < roots.length; node++) {
                if (roots[node] != null) {
                    children[node * 2] = roots[node].getLeftChild();
                    children[node * 2 + 1] = roots[node].getRightChild();
                } else {
                    children[node * 2] = null;
                    children[node * 2 + 1] = null;
                }
            }
            drawGraphicallyRecursive(height - 1, maxWidth, children);
        } else {
            System.out.println();
        }		// end BinaryNode
	}
}
