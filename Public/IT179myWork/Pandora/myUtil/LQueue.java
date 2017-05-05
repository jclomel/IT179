package myUtil;

import java.util.NoSuchElementException;

import myUtil.SimpleListLk;

/**
 * A queue implementation using a single-linked list as the data structure
 * @author Justin Lomelino
 *
 * @param <T> the data type for the objects contained in the queue
 */
public class LQueue<T> {
	
	private int capacity;
	private SimpleListLk data;

	/**
	 * public Constructor.  Creates a new LQueue with unlimited capacity, using a single-linked list as the underlying data structure
	 */
	public LQueue() {
		data = new SimpleListLk();
		this.capacity = -1;
	}
	
	/**
	 * public Constructor.  Creates a new LQueue with a capacity that is specified by the provided int, using a single-linked list as the
	 * underlying data structure
	 * @param capacity and integer representing the capacity of the queue
	 */
	public LQueue(int capacity){
		data = new SimpleListLk();
		this.capacity = capacity;
	}
	
	/**
	 * adds the object provided to the end of the queue
	 * @param offer the object to be added to the end of the queue
	 * @return a boolean that is true if the object was added to the end of the queue
	 */
	public boolean offer(T offer){
		if(data.size()+1 > capacity && capacity != -1) return false;
		data.add(data.size(), (Comparable) offer);
		return true;
	}
	
	/**
	 * removes the first object from the queue.  This object should be the first object added to the queue that has not already been removed.
	 * @return the object removed from the front of the queue
	 */
	public T remove(){
		if(data.size() > 0) return (T) data.remove(0);
		else throw new NoSuchElementException();
	}
	
	/**
	 * removes the first object from the queue.  This method differs from the 'remove' method in that it does not throw an error if the queue is
	 * empty, but instead returns a null value
	 * @return the object removed from the front of the queue, or null if the queue is empty
	 */
	public T poll(){
		if(data.size() > 0) return (T) data.remove(0);
		else return null;
	}
	
	/**
	 * returns the object at the front of the queue without removing it from the queue.
	 * @return the object at the front of the queue, or null if the queue is empty
	 */
	public T peek(){
		if(data.size() > 0){
			return (T) data.get(0);
		}
		else return null;
	}
	
	/**
	 * returns the object in the last position in the queue without removing it from the queue
	 * @return the object in the last position in the queue
	 */
	public T peekRear(){
		return (T) data.get(data.size()-1);
	}
	
	/**
	 * returns the object at the front of the queue without removing it from the queue.  Throws a NoSuchElementException if the queue is empty
	 * @return the object at the front of the queue
	 */
	public T element(){
		if(data.size() > 0) return (T) data.get(0);
		else throw new NoSuchElementException();
	}
	
	/**
	 * returns the number of objects in the queue
	 * @return an integer representing the number of objects in the queue
	 */
	public int size(){
		return data.size();
	}
	
	/**
	 * returns the capacity of the queue.  This value will be -1 if the capacity is unlimited
	 * @return an integer representing the capacity of the queue.  -1 if capacity is unlimited
	 */
	public int capacity(){
		return capacity;
	}
	
	/**
	 * returns true if the queue contains no objects, otherwise returns false
	 * @return a boolean representing whether the queue is empty
	 */
	public boolean empty(){
		if(data.size()<=0) return true;
		return false;
	}

}
