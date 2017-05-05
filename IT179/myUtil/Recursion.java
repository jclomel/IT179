package myUtil;

import java.util.ArrayList;


public class Recursion {

	
	public static int fibonacciPower(int n){
		//step 1 base case
		if(n<=2) return n;
		//steps 2 and 3, reduce towards solution and use result
		return fibonacciPower(n-2) * fibonacciPower(n-1);
	}
	
	public static String reverse(String str){
		//step 1
		if(str.length() == 0) return str;
		//step 2 and 3
		return reverse(str.substring(1)) + str.charAt(0);
	}
	
	public static int gcd(int a, int b){
		//step 2
		int r = a%b;
		//step 1
		if(r==0) return b;
		//step 3
		return gcd(b, r);
	}
	
	public static int[] pascalTriangle(int n){
		int[] pt = new int[n+1];
		pt[0]=1;
		
		//step 1
		if(n==0) return pt;
		
		//step 2
		int[] temp = pascalTriangle(n-1);
		for(int i=1; i<n; i++){
			pt[i] = temp[i-1] + temp[i];
		}
		pt[n] = 1;
		
		//debug print, draws the triangle
		/*for(int i=0; i<pt.length; i++){
			System.out.print(" " +pt[i]+ " ");
		}
		System.out.print("\n");*/
		
		//step 3
		return pt;
	}
	
	public static int bSearch(int[] a, int s, int e, int key){
		//step 1
		if(s>e) return -1;
		int m = (s + e)/2;
		//step 1 as well
		if(a[m]==key) return m;
		//step 2 and 3
		if(a[m] > key){
			return bSearch(a, s, m-1, key);
		}
		else return bSearch(a, m+1, e, key);
	}
	
	public static int binaryToDecimal(String str){
		//step 1
		if(str.equals("0")) return 0;
		if(str.equals("1")) return 1;
		
		//step 2
		int a = Character.getNumericValue(str.charAt(0));
		a = (int) (a * Math.pow(2, str.length()-1));
		String substring = str.substring(1);
		
		//step 3
		return a + binaryToDecimal(substring);
	}
	
	public static void hanoi(int n, char s, char t, char x){
		//step 1
		if(n == 1){
			System.out.println(s + " -> " + t);
			return;
		}
		//step 2 and 3
		else{
			hanoi(n-1, s, x, t);
			System.out.println(s + " -> " + t);
			hanoi(n-1, x, t, s);
		}
	}
	
	public static ArrayList<String> permute(String s){
		ArrayList<String> per = new ArrayList<String>();
		
		//step 1
		if(s.length() == 0){
			per.add(s); return per;
		}
		char a = s.charAt(0);
		
		//step 2
		ArrayList<String> temp = permute(s.substring(1));
		
		//step 3
		for(int i=0; i<temp.size();i++){
			String x = temp.get(i);
			for(int j=0; j<x.length();j++){
				per.add(x.substring(0, j) + a + x.substring(j));
			}
			per.add(x+a);
		}
		return per;
	}
	
	public static ArrayList<String> powerSet(String s){
		ArrayList<String> ps;
		
		//step 1
		if(s.length() == 0){
			ps = new ArrayList<String>();
			ps.add(s); return ps;
		}
		char a = s.charAt(0);
		
		//step 2
		ps = powerSet(s.substring(1));
		
		//step 3
		int n = ps.size();
		for(int i=0; i<n; i++){
			ps.add(a + ps.get(i));
		}
		return ps;
	}
	
	public static ArrayList<String> choose(int n, String s){
		ArrayList<String> temp = new ArrayList<String>();
		
		//step 1
		if(n==s.length()){
			temp.add(s);
			return temp;
		}
		else if(n>s.length()){
			return temp;
		}
		else if(n==1){
			for(int i=0;i<s.length();i++){
				temp.add(Character.toString(s.charAt(i)));
			}
			return temp;
		}
		else if(n<=0){
			return temp;
		}

		//step 2
		else{
			String tempStr = s.substring(1);
			String removed = s.substring(0,1);
			
			ArrayList<String> withoutRemoved = choose(n, tempStr);
			
			ArrayList<String> withRemoved = choose((n-1), tempStr);
			
			for(int i=0; i<withRemoved.size(); i++){
				withRemoved.set(i, removed + withRemoved.get(i));
			}
			
			//step 3
			for(int i=0; i<withRemoved.size(); i++){
				temp.add(withRemoved.get(i));
			}
			for(int i=0;i<withoutRemoved.size();i++){
				temp.add(withoutRemoved.get(i));
			}
			return temp;
			
		}
		
	}

}
