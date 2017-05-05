package myUtil;

/**
 * SimpleList Interface for IT179, ISU.
 * This is for student to implement (without iterator)
 * 			
 * 2/20/2017
 *  
 * @author Chung-Chih Li
 *
 * @param <T> Generic type parameter.
 */
public interface SimpleList<T extends Comparable<T>>{
	
	/**
	 * @return the number of elements stored in this MyList.
	 */
	public int size();
	
	/**
	 * Get the element at index i
	 * @param i is an int.
	 * @return the ith elements in this MyList, where i starts from 0.
	 * @exception IndexOutOfBoundsException will be thrown if parameter i is not a legitimate index.  
	 */
	public T get(int i); 
	
	/**
	 * Replace the element at i with new one provided by the parameter item. 
	 * @param i is an int.
	 * @param item is a T.
	 * @return the old item at i.
	 * @exception IndexOutOfBoundsException will be thrown if parameter i is not a legitimate index. 
	 */
	public T set(int i, T item);
	
	/**
	 * Find the index of the input item in this MyList. 
	 * @param item is an Object.
	 * @return the index of the first element that equals to item, -1 if not found.
	 */
	public int indexOf(Object item);
	
		
	/**
	 * Add (insert) item to the ith position of this MyList.
	 * @param i is an int.
	 * @param item is a T.
	 * @exception IndexOutOfBoundsException will be thrown if parameter i is not an legitimate index. 
	 */
	public void add(int i, T item);
	
	
	/**
	 * Remove the item at the ith position from this MyArrayList.
	 * @param i is an int.
	 * @exception IndexOutOfBoundsException will be thrown if parameter i is not a legitimate index. 
	 * @return the item that is removed.
	 */
	public T remove(int i);
	
	/**
	 * @return the maximum member in the list according to compareTo method  
	 */
	public T max();
	
	/**
	 * @return the minimum member in the list according to compareTo method 
	 */
	public T min();
	
	
}
