import myUtil.SimpleList;
import myUtil.SimpleListAry;
import myUtil.SimpleListLk;

import java.util.Random;

/**
 * Students should not modify this program  
 *
 * @author Chung-Chih Li,  
 * 
 *  2/20/2017
 */
public class Asg4 {
	
	
	static Random randGenerator;
	
	/**
	 * Print the entire list
	 * @param list
	 */
	static <T extends Comparable<T>> void print(SimpleList<T> list) {
		int size = list.size();
		for (int i=0;i<size;i++) {
			System.out.print(list.get(i)+" ");
		}
		System.out.println();
	}

	/**
	 * Add n many integers into list (add to the head)
	 * @param list
	 * @param n
	 * @return millisecond spent to finish the task
	 */
	static void addToHead(SimpleList<Integer> list, int n) {
		long ms = System.currentTimeMillis();
		for (int i=0; i<n; i++) 
			list.add(0, randGenerator.nextInt(n*10));
		ms = System.currentTimeMillis()-ms;
		System.out.printf("%6d ms to add %6d numbers to the head. ", ms, n);
	}
	
	/**
	 * Add n many integers into list (append to the tail)
	 * @param list
	 * @param n
	 * @return millisecond spent to finish the task
	 */
	static long addToTail(SimpleList<Integer> list, int n) {
		long ms = System.currentTimeMillis();
		for (int i=0; i<n; i++) 
			list.add(list.size(), randGenerator.nextInt(n*10));
		return System.currentTimeMillis()-ms;
	}
		
	/**
	 * Remove 1/3 of the items at 0, 3, 6, 9, ...
	 * @param list
	 * @return millisecond spent to finish the task
	 */
	static <T extends Comparable<T>> void removeAThird(SimpleList<T> list) { 
		long ms = System.currentTimeMillis();
		int n=list.size();
		for (int i=0; i<list.size() ; i+=2)
			list.remove(i);
		ms = System.currentTimeMillis()-ms;
		System.out.printf("%6d ms to remove 1/3 of %6d numbers.%n", ms, n);
	}
	
	
	/**
	 * Basic Test, add, remove, indexOf, get, size, max, min 
	 * @param ln
	 */
	static void initialTest(SimpleList<Integer> ln) {
		addToHead(ln,10);
		addToTail(ln,10);
		System.out.println();
		print(ln);
		removeAThird(ln);
		print(ln);
		int max = ln.indexOf(ln.max());
		int min = ln.indexOf(ln.min());
		System.out.println("Max: "+ln.get(max)+" at "+max);
		System.out.println("Min: "+ln.get(min)+" at "+min);
		Integer x= ln.get(min);
		ln.remove(min);
		System.out.println("After "+ x +" removed, " + x + "'s index becomes "+ln.indexOf(x)+"\n");		
	}
	
	
	/**
	 * Count and print the even number in ln
	 * @param ln
	 */
	static void countEven(SimpleList<Integer> ln) {
		long ms = System.currentTimeMillis();
		int count=0;
		for (int i=0; i < ln.size(); i++) {
			int n = ln.get(i);
			if (n%2 == 0) 
				count++;
		}
		ms = System.currentTimeMillis()- ms;
		System.out.printf("%6d ms to count %6d even numbers.%n",ms, count);
	}
	
	static void testAddHead(SimpleList<Integer> ln) {
		System.out.println("\nTest add to head and count even numbers on "+ln);
		int max=100000;
		for (int n=100; n<=max; n*=10) {
			addToHead(ln,n);
			countEven(ln);				
		}
	}
	
	static void testAddTail(SimpleList<Integer> ln) {
		System.out.println("\nTest add to Tail on "+ln);
		int max=100000;
		for (int n=100; n<=max; n*=10) {
			addToTail(ln,n);
			countEven(ln);			
		}
	}
	
	static void testRemove(SimpleList<Integer> ln) {
		System.out.println("\nRemove a third of "+ln);
		int max=100000;
		for (int n=100; n<=max; n*=10) {
			if (ln instanceof SimpleListAry) 
				addToTail(ln,n);
			else
				addToHead(ln,n);
			removeAThird(ln);	
		}
	}
	
	static void testEfficiency() {
		SimpleList<Integer> ln;
		ln = new SimpleListAry<Integer>();
		testAddHead(ln);
		ln = new SimpleListLk<Integer>();
		testAddHead(ln);
		ln = new SimpleListAry<Integer>();
		testAddTail(ln);
		ln = new SimpleListLk<Integer>();
		testAddTail(ln);
		ln = new SimpleListAry<Integer>();
		testRemove(ln);
		ln = new SimpleListLk<Integer>();
		testRemove(ln);
	}
	
	public static void main(String[] args) {
		if (args.length == 0) 
			randGenerator = new Random(123456789);
		else
			randGenerator = new Random(Integer.parseInt(args[0]));
		
		System.out.println("**");
		
		SimpleList<Integer> ln;
		ln = new SimpleListLk<Integer>();
		initialTest(ln);
		
		ln = new SimpleListAry<Integer>();
		initialTest(ln);
		testEfficiency();
		
		
	}
}
