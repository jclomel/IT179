import java.util.ArrayList;

import myUtil.Recursion;


public class RecursionTester {

	public static void main(String[] args){
		System.out.println(Recursion.fibonacciPower(6));
		System.out.println(Recursion.fibonacciPower(7));
		System.out.println(Recursion.fibonacciPower(8));
		System.out.println(Recursion.reverse("Hello"));
		System.out.println("a:12 b:18  " + Recursion.gcd(12, 18));
		Recursion.pascalTriangle(5);
		Recursion.pascalTriangle(7);
		int[] a = {1, 3, 4, 5, 7, 8, 9, 10, 12};
		System.out.println(Recursion.bSearch(a, 0, a.length-1, 8));
		System.out.println(Recursion.bSearch(a, 0, a.length-1, 10));
		System.out.println(Recursion.bSearch(a, 0, a.length-1, 14));
		ArrayList<String> permutation = Recursion.permute("abcd");
		for(int i=0;i<permutation.size(); i++){
			System.out.print(permutation.get(i) + " ");
		}
		System.out.print("\n" + permutation.size() + "\n");
		ArrayList<String> permutation2 = Recursion.permute("abc");
		for(int i=0;i<permutation2.size(); i++){
			System.out.print(permutation2.get(i) + " ");
		}
		System.out.print("\n" + permutation2.size() + "\n");
		
		ArrayList<String> permutation3 = Recursion.powerSet("abc");
		for(int i=0;i<permutation3.size(); i++){
			System.out.print(permutation3.get(i) + " ");
		}
		System.out.print("\n" + permutation3.size() + "\n");
		System.out.println("\n1001 " + Recursion.binaryToDecimal("1001"));
		System.out.println("\n1001 " + Recursion.binaryToDecimal("1001001"));
		Recursion.hanoi(3, 'A', 'C', 'B');
		System.out.println("");
		Recursion.hanoi(5, 'A', 'C', 'B');
		System.out.println("");
		
		ArrayList<String> choose = Recursion.choose(2, "abcd");
		System.out.print("Choose 2 from abcd -- ");
		for(int i=0;i<choose.size();i++){
			System.out.print(choose.get(i) + " ");
		}
		System.out.println("");
		
		ArrayList<String> choose2 = Recursion.choose(2, "bcd");
		System.out.print("Choose 2 from bcd -- ");
		for(int i=0;i<choose2.size();i++){
			System.out.print(choose2.get(i) + " ");
		}
		System.out.println("");
		
		ArrayList<String> choose3 = Recursion.choose(3, "abcde");
		System.out.print("Choose 3 from abcde -- ");
		for(int i=0;i<choose3.size();i++){
			System.out.print(choose3.get(i) + " ");
		}
		System.out.println("");
	}

}
