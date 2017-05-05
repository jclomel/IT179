/**
 * IT 179, Spring 2017
 * @author Chung-Chih Li   3/3/2017
 * 
 *  Students should not modify this file.
 *  
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import myUtil.Calculator;

/**
 *  This program will read infix arithmetic expressions from a file and use Calculator class's static 
 *  method to do the following:
 *  
 *  1. Evaluate the expression directly from its infix notation;
 *  2. Convert to postfix notation;
 *  3. Evaluate the expression from its postfix notation.
 * 
 *  @author Chung-Chih Li 
 */

public class Asg5 {

	/**
	 * @param args[0], if provided, should be the name of file that contains arithmetic expressions in infix notation.
	 * Each line contains one expression.   
	 */
	public static void main(String[] args) {
			
		String  fname;
		Scanner inS = null;
		fname = (args.length != 0 ? args[0] : "/home/ADILSTU/ccli/Public/IT179/asg5/exps.txt");		
		try {
			File file = new File(fname);
			inS = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Can't find file: "+fname);
			System.exit(-1);
		}
		
		String postf, exp="";
		double value=0;
		int i=0;
		
		while (inS.hasNextLine()) {
			postf="";
			try {
				exp = inS.nextLine();
				postf = Calculator.infixToPostfix(exp);
				value = Calculator.evaluate(exp);
				System.out.printf("\n%2d:  Infix %7.2f = %s",++i,value,exp);
				value = Calculator.evaluatePostfix(postf);
				System.out.printf("\n   Postfix %7.2f = %s%n",value,postf);
				
			} catch (ArithmeticException e) {
				System.out.printf("\n%2d  *******  Error = %s : %s%n",++i,e.getMessage(),exp);	
				if (postf.length()!=0) 
					System.out.printf("    *******  Error = %s : %s%n",e.getMessage(),postf);
			} 
		}
		inS.close();
	}
}

