import java.util.Random;
/*
 *MultiDS implements Primitive Q and Reorder to make a data structure to contain
 *Card objects for the war game.
*/
public class MultiDS<T> implements PrimitiveQ<T>, Reorder{
	private final T[] Q;
	private int marker;
	private String builtstring;
	
	
	//Primitive Methods
	
	//Constructor for MultiDS
	public MultiDS(int totalLength){
		if (totalLength < 1){
			throw new IllegalArgumentException("Total length must be greater than 1");
		}
		Q = (T[]) new Object[totalLength];
		marker = 0;
	}
	//toString Method
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < marker; i++){
			s.append(Q[i] + " ");
		}
		return s.toString();
	}
	/** Add a new item to the PrimitiveQ in the next available location.  If
     *  all goes well, return true.  If there is no room in the PrimitiveQ for
	 *  the new item, return false (you do NOT have to resize it)
     * 
     * @param item The item to add to PrimitiveQ
     * @return true if successfully added, false if there is not enough room
     */	
	public boolean addItem(T item){
	if (Q == null){
		return false;
	}
	if (full() == true){
		return false;
    }
	else{
		Q[marker] = item;
		marker++;
	}
	return true;
	}
	
	/** Remove and return the "oldest" item in the PrimitiveQ.  If the PrimitiveQ
	 *  is empty, return null.
     */	
	public T removeItem(){
		T temp0;
		if (empty() == true){
			return null;
		}
		else{
			temp0 = Q[0];
			Q[0] = null;
			marker--;
			for(int i = 0; i < marker; i++){
				Q[i]=Q[i+1];
			}
		}
		return temp0;
	}
	/** Return true if the PrimitiveQ is full, and false otherwise
     */	
	public boolean full(){
		return marker == Q.length;
	}
	
	/** Return true if the PrimitiveQ is empty, and false otherwise
     */
	public boolean empty(){
		return marker == 0;
	}
	

	/** Return the number of items currently in the PrimitiveQ
     */	
	public int size(){
		return marker;
	}
	
	/** Reset the PrimitiveQ to empty status by reinitializing the variables
	 *  appropriately
     */	
	public void clear(){
		marker = 0;
	}
	//Reorder
	/** Logically reverse the data in the Reorder object so that the item
	 *  that was logically first will now be logically last and vice
	 *  versa.  The physical implementation of this can be done in 
	 *  many different ways, depending upon how you actually implemented
	 *  your physical MultiDS class
     */
	public void reverse(){
		for (int i = 0; i < marker/2; i++){
			T temp = null;
			temp = Q[i];
			Q[i] = Q[marker - i - 1];
			Q[marker - i - 1] = temp;
		}
	}

	/** Remove the logical last item of the DS and put it at the 
	 *  front.  As with reverse(), this can be done physically in 
	 *  different ways depending on the underlying implementation.
     */
	public void shiftRight(){
		T last;
		last = Q[marker-1];
		for(int i = marker-1; i > 0; i--){
			Q[i] = Q[i-1];
		}
		Q[0] = last;
	}

	/** Remove the logical first item of the DS and put it at the
	 *  end.  As above, this can be done in different ways.
     */
	public void shiftLeft(){
		T first;
		first = Q[0];
		for(int i = 0; i <= marker-1; i++){
			Q[i] = Q[i+1];
		}
		Q[marker-1] = first;
	}
	
	/** Reorganize the items in the object in a pseudo-random way.  The exact
	 *  way is up to you but it should utilize a Random object (see Random in 
	 *  the Java API)
     */
	public void shuffle(){
		Random rnd = new Random();
		for (int i = marker -1; i > 0; i--){
			int random = rnd.nextInt(i+1);
			T temp3 = Q[random];
			Q[random] = Q[i];
			Q[i] = temp3;
		}
	}
}