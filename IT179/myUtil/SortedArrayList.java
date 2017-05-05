package myUtil;

import java.util.ArrayList;

/**
 * An ArrayList that keeps the elements sorted in ascending order as defined by the elements' class
 * 
 * @author jclomel
 *
 * @param <T> the type of the elements in the SortedArrayList.  Must implement the Comparable<T> interface.
 */
public class SortedArrayList<T extends Comparable<T>> extends ArrayList<T> {

	public SortedArrayList() {
		super();
	}
	
	public SortedArrayList(int capacity){
		super(capacity);
	}

	/**
	 * add an item to the underlying array in the correct position to keep the array sorted in ascending order
	 * 
	 * @param item the item to add to the underlying array
	 * @return a boolean, always true, represents a successful addition of the item to the underlying array
	 */
	public boolean add(T item){
		/*
		 * Compare the item to each element in the array, starting at the beginning element.
		 * This comparison will always result in 1, meaning the item is larger than the element in the array UNTIL
		 * the correct position to insert is discovered once the item is smaller than the element it is compared to,
		 * or the end of the array is reached. At this point, insert the item into the array.
		 * 
		 * Unfortunately the method is specified to return a boolean value, but it is essentially meaningless since an add
		 * should never fail.
		 */
		for(int i=0; i < super.size(); i++){				
			if(super.get(i).compareTo(item) == 1){			
				super.add(i, item);	
				//System.out.println(super.toString());
				return true;
			}
		}
		super.add(item);
		return true;
	}
	
	/**
	 * Insert an item to the underlying array at the specified index.
	 * 
	 * @param i the index to add the item to the underlying array
	 * @param item the item to add to the underlying array
	 * 
	 * @throws IllegalArgumentException if adding the item at the specified index would break ascending order
	 * @throws ArrayIndexOutOfBoundsException if the index is invalid (either negative or greater than the size of the underlying array)
	 */
	public void add(int i, T item){
		//below is print debugging to console
		//if(i<40){ if(i==super.size()){System.out.print("\n!");} System.out.print("\n" + i + " " + item); System.out.print(super.toString()); }
		
		if(super.size() == 0) {
			super.add(0, item);
		}
		else if(i < 0 || i > super.size()){
			//more print debugging to console
			//System.out.println(i + " " + super.size());
			throw new ArrayIndexOutOfBoundsException();		//index to add can't be less than zero or greater than the size of the array, those values make no sense in the context of the array
		}
		else if(i == 0 && super.get(i).compareTo(item) == 1){ 		//if at the start, only check the item after
			super.add(0, item); 
		}
		else if(i == super.size() && super.get(i-1).compareTo(item) == -1){		//if at the end, only check the item before
			super.add(i, item);
		}
		else if(super.get(i-1).compareTo(item) == -1){			//item in the array just before the index to add the new item is smaller than the new item
			if(super.get(i).compareTo(item) == 1)				//item in the array at the index to add the new item is larger than the new item
			{
				super.add(i, item);	
				//even more print debugging.  
				//These debug comments, when active, really help to visualize what is going on by printing array contents progressively to the console
				//if(i<40){ System.out.println("\n" + super.toString()); }
			}	
			else throw new IllegalArgumentException();
		}
		else throw new IllegalArgumentException();		//adding the item at the specified index would break ascending order, throw an error
	}
	
	/**
	 * returns the index in the underlying array of the Object.
	 * 
	 * @param object the Object to find in the underlying array
	 * @return an int, -1 if the Object could not be found in the array, otherwise the index of the Object in the array.
	 */
	public int indexOf(Object object){
		
		if(super.size() <= 0) return -1;		//if the size of the array is 0 (is empty), the index cannot be found
		if(!object.getClass().equals(super.get(0).getClass())) return -1;	//if the object is not of the same type as the array, the index cannot be found
		
		//binary sort
		int start = 0;
		int end = super.size() - 1;
		while(start <= end){
			int marker = (start + end) / 2;
			if(object.equals(super.get(marker))) return marker;		//found the object.  Return the marker index.
			if(super.get(marker).compareTo((T) object) > 0){		//the object is less than the marker, remove the top half
				end = marker - 1;
			}
			else{													//the object is greater than the marker, remove the bottom half
				start = marker + 1;
			}
		}
		
		return -1;		//the object was not found in the array	
	}
}
