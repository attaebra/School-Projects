/** Assignment 1 PrimitiveQ<T> interface
 *  Carefully read the specifications for each of the operations and
 *  implement them correctly in your MultiDS class.
 * 
 *  The overall logic of the PrimitiveQ<T> is the following:
 * 		Data is logically "added" in the same order that it is "removed".
 *  However, there is no requirement for the physical storage of the actual
 *  data.  Your only requirement for the MultiDS class is that all of the
 *  methods work as specified and that your MultiDS class have an array as its
 *  its primary data.  You MAY NOT use ArrayList, Vector or any predefined
 *  collection class for your MultiDS data.  However, you may want to use some
 *  some additional instance variables to keep track of the data effectively.
 * 
 *  Note: Later in the term we will discuss how to implement a queue in an
 *  efficient way.  For now, this is a "primitive queue".
 */
public interface PrimitiveQ<T>
{
	/** Add a new item to the PrimitiveQ in the next available location.  If
     *  all goes well, return true.  If there is no room in the PrimitiveQ for
	 *  the new item, return false (you do NOT have to resize it)
     * 
     * @param item The item to add to PrimitiveQ
     * @return true if successfully added, false if there is not enough room
     */
	public boolean addItem(T item);
	
	/** Remove and return the "oldest" item in the PrimitiveQ.  If the PrimitiveQ
	 *  is empty, return null.
     */
	public T removeItem();
	
	/** Return true if the PrimitiveQ is full, and false otherwise
     */
	public boolean full();
	
	/** Return true if the PrimitiveQ is empty, and false otherwise
     */
	public boolean empty();
	
	/** Return the number of items currently in the PrimitiveQ
     */
	public int size();

	/** Reset the PrimitiveQ to empty status by reinitializing the variables
	 *  appropriately
     */
	public void clear();
}