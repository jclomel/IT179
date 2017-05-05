/**
 * Don't modify any of the following codes. 
 * 
 * 2/7/2017
 * 
 * @author Chung-Chih Li
 */
import myUtil.SortedArrayList;
import java.util.ArrayList;
import java.util.Random;
import poker.Card;
import poker.XCard;
import poker.PokerDeck;

public class Asg3 {
	
	static Random randGenerator;
	
	public static void testInteger() {
		SortedArrayList<Integer> sorted= new SortedArrayList<Integer>();
		
		for (int i=0;i<20;i++) {  // randomly add 0 numbers from {0,1,...999}. 
			sorted.add(randGenerator.nextInt(1000));
		}
	
		int indexOutOfBoundsCount=0;
		int outOfOrderCount=0;
		for (int i=0;i<100;i++) {
			try {
				int n = randGenerator.nextInt(1000);
				int at = randGenerator.nextInt(sorted.size()*3/2);
				sorted.add(at,n);
			} catch (IndexOutOfBoundsException e) {
				System.out.print("+");
				indexOutOfBoundsCount++;
			} catch (IllegalArgumentException e) {
				System.out.print(".");
				outOfOrderCount++;
			} 
		}
		System.out.println("\nsize:"+sorted.size()+" Index out bounds:"+indexOutOfBoundsCount+
				"  Out of order:"+outOfOrderCount);
		System.out.println(sorted.toString());
	}
	
	
	public static void testXCard() {
		PokerDeck adeck = new PokerDeck();
		SortedArrayList<XCard> sorted = new SortedArrayList<XCard>();
		adeck.shuffle();
		ArrayList<Card> shuffled = adeck.toArrayList();
		for (int i=0;i<shuffled.size();i++) {
			Card a = shuffled.get(i);
			sorted.add(new XCard(a.getSuit(), a.getKind()));
		}
		System.out.println(shuffled.toString());
		System.out.println(sorted.toString());
	}
	
	public static void testIndexOf() {
		SortedArrayList<Integer> sorted= new SortedArrayList<Integer>();
		
		int size = 1000000;
		for (int i=0;i<size;i++) sorted.add(i,i);
		
		long ms = System.currentTimeMillis();
		for (int i=0;i<100;i++) {
			sorted.indexOf(new Integer(randGenerator.nextInt(size)));
		}
		ms = System.currentTimeMillis()-ms;
		System.out.print(ms+" ms for 100 seraches.  ");
		int n = randGenerator.nextInt(size);
		System.out.println("Index of "+n+" is "+ sorted.indexOf(new Integer(n)));
	}
	
	public static void main(String[] args) {
		if (args.length == 0) 
			randGenerator = new Random(123456789);
		else
			randGenerator = new Random(Integer.parseInt(args[0]));
		
		System.out.print("\n\n** Test on Integer:\n");
		testInteger();
		testInteger();
		System.out.print("\n\n** Test on XCard:\n");
		testXCard(); 
		System.out.print("\n\n** Test indexOf:\n");
		testIndexOf();		
		testIndexOf();
	}

}
