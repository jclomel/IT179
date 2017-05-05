import java.util.ArrayList;
import myUtil.Recursion;
import java.util.Random;

/**
 * Students should not modify this program  
 *
 * @author Chung-Chih Li,  
 * 
 *  3/30/2017
 */
public class Asg6 {
	
	static Random randGenerator = new Random(2281947);
	
	static void testFabonacciPower() {
		System.out.printf("FabonacciPower : ");	
		for (int i=3;i<9;i++)  System.out.print(Recursion.fibonacciPower(i)+" ");
	}

	static void testReverse() {
		String  s="String reversal test.";
		System.out.printf("%n%n%s --> %s%n", s, Recursion.reverse(s));
	}
	
	static void testGCD() {
		int a,b;
		for (int i=0;i<10;i++) {
			a=randGenerator.nextInt(1000000000);
			b=randGenerator.nextInt(1000000000);
			System.out.printf("%ngcd(%d,%d)=%d",a,b,Recursion.gcd(a,b));
		}
		System.out.println("\n");
	}

	
	public static void testPascalTriangle() {
		for (int n=0; n<=30; n += 7) {
			String str="(1+x)^"+n+":";
			int[] c = Recursion.pascalTriangle(n);
			for (int i =0;i<c.length;i++) str += " "+c[i]; 
			System.out.println(str);
		}
	}
	
	static void testHanoi() {
		System.out.println("\nhanoi(3,'A','B','C'):");
		Recursion.hanoi(3,'A','B','C');
		System.out.println("\nhanoi(3,'B','A','C'):");
		Recursion.hanoi(3,'B','A','C');
		System.out.println("\nhanoi(4,'B','A','C'):");
		Recursion.hanoi(4,'B','A','C');
	}
	
	static void testBinaryToDecimal() {
 		String str = "101101";
 		System.out.println("\n\n"+str+" --> "+Recursion.binaryToDecimal(str));
 		str = "001101111";
  		System.out.println(str+" --> "+Recursion.binaryToDecimal(str));
  		str = "10001111110";
  		System.out.println(str+str+" --> "+Recursion.binaryToDecimal(str+str));
  		System.out.println(str+str+str+" --> "+Recursion.binaryToDecimal(str+str+str));
 	}
	
	
	static void testBSearcg() {
		int[] a = new int[3000];
		a[0] = randGenerator.nextInt(100);
		for (int i=1;i<a.length;i++) a[i] = a[i-1]+randGenerator.nextInt(100);; 
		for (int i=1;i<10;i++) {
			int key = a[randGenerator.nextInt(3000)] + randGenerator.nextInt(2); 
			int at = Recursion.bSearch(a, 0, a.length-1, key);
			System.out.printf("%n%8d --> [%d]=%s",key, at, (at != -1 ? a[at] : "x"));			
		}
	}
	
	static void testPowerSet() {	
		System.out.println("\nPowersets");
		String s ="";
		ArrayList<String> ps;
		ps = Recursion.powerSet(s);
		System.out.println(s+" "+ps.size()+":"+ps.toString());
		s ="abc";
		ps = Recursion.powerSet(s);
		System.out.println(s+" "+ps.size()+":"+ps.toString());
		s ="abcd";
		ps = Recursion.powerSet(s);
		System.out.println(s+" "+ps.size()+":"+ps.toString());
		s ="abcdef";
		ps = Recursion.powerSet(s);
		System.out.println(s+" "+ps.size()+":"+ps.toString());
	}
	
	static void testChoose() {
		String s = "abcdef";
		ArrayList<String> C;
		System.out.println("\nChoose from "+s); 
		for (int i=0;i<10;i++){
			C = Recursion.choose(i, s);
			System.out.print("("+s.length()+","+i+")"+C.size());
			if (C.size() == 0) 
				System.out.println();
			else
				System.out.println(":"+C.toString());
		}
	}
	
	static void testPermute() {	
		String s = "abc";
		System.out.print("\n\n"
				+ "Permutation\n"); 
		ArrayList<String> permS = Recursion.permute(s);
		System.out.println(s.toString()+" "+permS.size()+":"+permS.toString());
		s = "abcd";
		permS = Recursion.permute(s);
		System.out.println(s.toString()+" "+permS.size()+":"+permS.toString());
		s = "12345";
		permS = Recursion.permute(s);
		System.out.println(s.toString()+" "+permS.size()+":"+permS.toString());
	}
	
	public static void main(String[] args) {
		// 1
		testFabonacciPower(); 
		// 2
		testReverse();
		// 3
		testGCD();
		// 4
		testPascalTriangle();
		// 5
		testBSearcg();
		// 6
		testBinaryToDecimal();
		// 7
		testHanoi();
		// 8
		testPermute();
		// 9
		testPowerSet();
		// 10
		testChoose();
	}
	
}


