import java.lang.Iterable;
import java.util.Iterator;
import java.lang.Comparable;
import java.lang.IllegalArgumentException;

public class PHPArray<V> implements Iterable<V> {
	
	private int N;	//number of key-value pairs in the symbol table
	private int M;	// size of linear probing table
	
	//Represents the hash table that contains nodes, and the node pointers
	private Node<V>[] nodeTable;
	private Node<V> initial;
	private Node<V> last;
	private Node<V> current;

	/**
	*Initializes the PHPArray
	*@param initialCapacity, intended start size
	*/
	public PHPArray(int initialCapacity) {
		N = 0;
		M = initialCapacity;
    	Node<V>[] hashTable = (Node<V>[]) new Node<?>[M];
    	nodeTable = hashTable;
		initial = null;
		last = null;
		current = null;
	}
	
	/**
	*Returns the size of the array (key-value pairs)
	*@return N, the number of key-value pairs inside the table
	*/
	public int length() {
		return N;
	}
	
	/**
	*Returns true or false based on whether empty or not
	*@return whether size is 0 or not.
	*/
	public boolean isEmpty() {
		return length() == 0;
	}
	
	/**
	*Places a new key, value pair into the array.
	*@param k, this is the key
	*@param v, this is the value
	*/
	public void put(String k, V v) {
		if (k == null) return;
		if (v == null) unset(k);
		if (N >= M/2) resize(2*M);
		N++;
		int i;
		for (i = hash(k, M); nodeTable[i] != null; i++) {
			if (nodeTable[i].data.key.equals(k)){
				nodeTable[i].data.value = v;
				return;
			}
		}
		nodeTable[i] = new Node<V>(new Pair<V>(k,v));
		if (initial == null) {
			initial = nodeTable[i];
			current = initial;
		}
		if (last == null) {
			last = nodeTable[i];
		}
		nodeTable[i].previous = last;
		last.next = nodeTable[i];
		last = nodeTable[i];
	}
	
	/**
	*If the user decides to use int value for the string convieniently converts that into a string
	*then calls the original put()
	*@param i, this is the key to be converted to String
	*@param v, this is the value 
	*/
	public void put(Integer i, V v) {
		String k = i.toString();
		if (i == null) return;
		if (v == null) unset(k);
		put(k,v);
	}
	
	/**
	*Resizes the hash table if necessary
	*@param the new intended capacity
	*/
	private void resize(int newCap) {
		System.out.print("\t\tSize: " + N);
		System.out.println(" -- resizing array from " + M + " to " + newCap);
		@SuppressWarnings("unchecked")
		Node<V>[] temp = (Node<V>[]) new Node<?>[newCap];
		for (int i = 0; i < M; i++)
		{
			if (nodeTable[i] != null)
			{
				temp[hash(nodeTable[i].data.key, newCap)] = nodeTable[i];
			}
		}
		nodeTable = temp;
		M = newCap;
	}
	/**
	*Get the value for any given key
	*@param k, the key to be searched for
	*@return the value at that key k, or null if empty
	*/
	public V get(String k) {
		for (int i = hash(k, M); nodeTable[i] != null; i = (i + 1) % M) {
			if (nodeTable[i].data.key.equals(k)) {
				return nodeTable[i].data.value;
			}
		}
		return null;
	}
	
	/**
	*If the value you'd like to get is type integer, convert to String
	*and call regular get
	*@param i, the key you'd like to search for.
	*@return value associated from original get() method
	*/
	public V get(Integer i) {
		String k = i.toString();
		return get(k);
	}
	
	/**
	*Determines whether or not the table contains a (key, value) pair
	*with the associated key
	*@param k, the key
	*@return true or false whether key is contained or not.
	*/
	public boolean contains(String k) {
		return get(k) != null;
	}
	
	/**
	*Deletes the key and associated value from the table and rehashes if necessary
	*@param k, the key
	*/
	public void unset(String k) {
		if (!contains(k)) return;

		//Finds the position of the key i
		int i = hash(k, M);
		while (!k.equals(nodeTable[i].data.key)) i = (i + 1) % M;

		//Deletes the key and it's associated value
		if (last == nodeTable[i]) last = nodeTable[i].previous;
		if (initial == nodeTable[i]) initial = nodeTable[i].next;
		
		nodeTable[i].previous.next = nodeTable[i].next;
		nodeTable[i].next.previous = nodeTable[i].previous;
		nodeTable[i] = null;

		//Rehashes all the keys in the same cluster
		i = (i + 1) % M;
		while (nodeTable[i] != null) {
			String keyToRehash = nodeTable[i].data.key;
			System.out.println("\t\tKey " + keyToRehash + " rehashed...\n");
            V valToRehash = nodeTable[i].data.value;
			nodeTable[i] = null;
			int newHash = hash(keyToRehash, M);
			
			while (nodeTable[newHash] != null) newHash++;
			
			nodeTable[newHash] = new Node<V>(new Pair<V>(keyToRehash, valToRehash));
			i = (i + 1) % M;
		}
		N--;
	}
	
	/**
	*Converts integer key values to String and then calls original unset() method
	*to delete
	*@param i, the key
	*/
	public void unset(Integer i) {
		String k = i.toString();
		unset(k);
	}
	
	/**
	*
	*
	*/
	public Pair<V> each() {
		if (current == null) return null;

		Pair<V> currentPair = current.data;
		current = current.next;
		return currentPair;
	}
	
	/**
	*Sets each iteration value back to the beginning
	*/
	public void reset() {
		current = initial;
	}
	
	/**
	*Assigns new keys to the values starting at 0 and ending a length()-1
	*/
	public void sort() {
		if (!(initial.data.value instanceof Comparable)) throw new IllegalArgumentException();
		if (initial == null || initial.next == null) return;
		
		//Creates array without null nodes in table
		Node<V>[] a = listToArray();
		Node<V>[] temp = (Node<V>[]) new Node<?>[N];
		
		mergeSort(a, temp, 0, N-1);
		
		//Creates new linked list and generates the keys
		initial = a[0];
		nodeTable[0] = a[0];
		nodeTable[0].data.key = "0";
		
		int i;
		for (i = 1; i < a.length; i++) {
			String key = Integer.toString(i);
			nodeTable[hash(key, M)] = a[i];
			nodeTable[hash(key, M)].data.key = key;
			a[i-1].next = a[i];
			a[i].previous = a[i-1];
		}
		a[i-1].next = null;
	}
	
	/**
	*Sorts the values just like sort, but instead of reassigning the keys to ints starting at 0,
	*we keep the keys as they were initially.
	*/
	public void asort() {
		if (!(initial.data.value instanceof Comparable)) throw new IllegalArgumentException();
		if (initial == null || initial.next == null) return;

		// need array without the null elements of nodeTable
		Node<V>[] a = listToArray();
		@SuppressWarnings("unchecked")
		Node<V>[] temp = (Node<V>[]) new Node<?>[N];
		mergeSort(a, temp, 0, N-1);
		// create new linked list
		initial = a[0];
		int i;
		for (i = 1; i < N; i++) {
			a[i-1].next = a[i];
			a[i].previous = a[i-1];
		}
		a[i-1].next = null;
	}
	
	/**
	*Simple mergeSort algorithm
	*/
	private void mergeSort(Node<V>[] a, Node<V>[] temp, int left, int right) {
		if( left < right ) {
			int center = (left + right) / 2;
			mergeSort(a, temp, left, center);
			mergeSort(a, temp, center + 1, right);
			merge(a, temp, left, center + 1, right);
		}
	}
	
	/**
	*Part of mergeSort algorithm
	*/
	private void merge(Node<V>[] a, Node<V>[] temp, int left, int middle, int right ) {
		int leftEnd = middle - 1;
		int k = left;
		int num = right - left + 1;

		while(left <= leftEnd && middle <= right) {
			if(a[left].compareTo(a[middle]) <= 0) temp[k++] = a[left++];
			else temp[k++] = a[middle++];
		}
		// Copy rest of initial half
		while(left <= leftEnd) {
			temp[k] = a[left];
			k++;
			left++;
		}
		// Copy rest of second half
		while(middle <= right) {
			temp[k] = a[middle];
			k++;
			middle++;
		}
		// Copy temp back
		for(int i = 0; i < num; i++, right--) a[right] = temp[right];
	}
	
	/**
	*Converts node linked list into an array ignoring null elements
	*/
	public Node<V>[] listToArray(){
		
    	Node<V>[] temp = (Node<V>[]) new Node<?>[N];
    	Node<V> current = initial;
    	int index = 0;
		while(index < N) {
			temp[index] = current;
			current = current.next;
			index++;
		}
		return temp;
	}
	
	/**
	*Hash function for keys - returns value between 0 and M-1
	*@param key, the key
	*@param M, the capacity
	*
	*/
	private int hash(String key, int M) {
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	/**
	*Allows for easy iteration through the values within the PHPArray
	*/
	public Iterator<V> iterator() {	
		return new ArrayIterator();
	}
	
	/**
	*For grading purposes, determines if the table is set up correctly
	*hashing values properly by printing table
	*/
	public void showTable() {
		System.out.println("\tRaw Hash Table Contents:");
		for (int i = 0; i < M; i++) {
			System.out.print(i + ": ");
			if (nodeTable[i] == null) {
				System.out.println("null");
			}
			else
			{
				nodeTable[i].printData();
			}
		}
	}
	
	/**
	*Inner class that allows user to extract (key, value) pairs whilst still
	*maintaining a good level of data encapsulation
	*/
	public static class Pair<V> {
		public String key;
		public V value;
		
		private Pair(String k, V v)
		{
			key = k;
			value = v;
		}
	}

	/**
	*Node that holds the key, value pair information and the linked list information.
	*/
	private static class Node<V> implements Comparable<Node<V>> {
		private Pair<V> data;
		private Node<V> next;
		private Node<V> previous;

		private Node(Pair<V> d) {
			data = d;
		}

		// used for next() return
		public String toString() {
			return data.value.toString();
		}

		// used for showTable()
		private void printData() {
			System.out.println("Key: " + data.key + " Value: " + data.value);
		}

		public int compareTo(Node<V> n) {
			V thisData = this.data.value;
			V nData = n.data.value;
			@SuppressWarnings("unchecked")
			int compare = ((Comparable<V>) thisData).compareTo(nData);
			return compare;
		}
	}

	/**
	*Iterator for the linked list
	*/
	private class ArrayIterator implements Iterator<V> {
		private Node<V> current;

		public ArrayIterator() {
			current = initial;
		}

		public boolean hasNext() {
			if (current != null) return true;
			return false;
		}

		public V next() {
			V val = current.data.value;
			current = current.next;
			return val;
		}
	}
}