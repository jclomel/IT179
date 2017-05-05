package myUtil;

import myUtil.AStack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Calculator {
	private static String operatorsString = "(+-*/^)";
	
	static AStack operators;
	static AStack operands;
	static AStack postFix;
	
	public static double evaluate(String infix){
		//TODO still need to check for misformed expressions (paren balance already checked)
		boolean isBalanced = checkBalance(infix);
		if(!isBalanced) throw new ArithmeticException("Poorly formed expression.  Each '(' must have a ')'!");
		checkMisformed(infix);
		
		operators = new AStack();
		operands = new AStack();
		Object result;
		
		StringTokenizer tokens = new StringTokenizer(infix, operatorsString, true);
		
		while(tokens.hasMoreTokens()){
			String token = tokens.nextToken();
			String prevOp = "";
			try{
				prevOp = (String)operators.peek();
			}
			catch(ArrayIndexOutOfBoundsException e){
				prevOp = "";
			}
			
			switch(token){
			case "+":
			case "-":
				stackCheck(token, prevOp, new String[]{"(", ""});
				break;
			case "*":
			case "/":
				stackCheck(token, prevOp, new String[]{"+", "-", "(", ""});
				break;
			case "^":
				stackCheck(token, prevOp, new String[]{"^", "/", "*", "+", "-", "(", ""});
				break;
			case ")":
				boolean notFlushed = true;
				while(notFlushed){
					String op = (String)operators.peek();
					if(!op.equals("(")){
						//System.out.println(display());
						result = process(operators, operands);
						operands.push(String.valueOf(result));
					}
					else{
						operators.pop();
						notFlushed = false;
					}
				}
				
				break;
			case "(":
				operators.push(token);
				break;
			default:
				operands.push(token);
			}
			//System.out.println(display());
		}

		System.out.print("infix: " + infix + "\n");
		//System.out.println(display());
		
		boolean notFlushed = true;
		while(notFlushed){
			if(operators.getSize()==0&&operands.getSize()==1){
				notFlushed = false;
			}
			else{
				operands.push(String.valueOf(process(operators, operands)));
				//System.out.println(display());
			}
		}
		result = operands.pop();
		if(result instanceof String){
			return Double.parseDouble((String)result);
		}
		return (double)result;
		
	}
	
	public static double evaluatePostfix(String postfix){
		//TODO evaluate an expression using postfix notation
		postFix = new AStack();
		Object result = null;
		
		StringTokenizer tokens = new StringTokenizer(postfix, " ");
		
		while(tokens.hasMoreTokens()){
			String token = tokens.nextToken();
			switch(token){
			case "+":
			case "-":
			case "*":
			case "/":
			case "^":
				String op2 = (String) postFix.pop();
				String op1 = (String) postFix.pop();
				result = calculate(op1, token, op2);
				postFix.push(Double.toString((double)result));
				break;
			default:
				postFix.push(token);
			}
		}
		System.out.println("postfix: " +postfix);
		return (double) result;
		
	}
	
	public static String infixToPostfix(String infix){
		boolean isBalanced = checkBalance(infix);
		if(!isBalanced) throw new ArithmeticException("Poorly formed expression.  Each '(' must have a ')'!");
		checkMisformed(infix);
		
		operators = new AStack();
		operands = new AStack();
		String result = "";
		
		StringTokenizer tokens = new StringTokenizer(infix, operatorsString, true);
		
		while(tokens.hasMoreTokens()){
			String token = tokens.nextToken();
			String prevOp = "";
			try{
				prevOp = (String)operators.peek();
			}
			catch(ArrayIndexOutOfBoundsException e){
				prevOp = "";
			}
			
			switch(token){
			case "+":
			case "-":
				stackCheckPF(token, prevOp, new String[]{"(", ""});
				break;
			case "*":
			case "/":
				stackCheckPF(token, prevOp, new String[]{"+", "-", "(", ""});
				break;
			case "^":
				stackCheckPF(token, prevOp, new String[]{"^", "/", "*", "+", "-", "(", ""});
				break;
			case ")":
				boolean notFlushed = true;
				while(notFlushed){
					String op = (String)operators.peek();
					if(!op.equals("(")){
						//System.out.println(display());
						String res = (String) operators.pop();
						operands.push(res);
					}
					else{
						operators.pop();
						notFlushed = false;
					}
				}
				
				break;
			case "(":
				operators.push(token);
				break;
			default:
				operands.push(token);
			}
			//System.out.println(display());
		}


		
		boolean notFlushed = true;
		while(notFlushed){
			if(operators.getSize()==0){
				notFlushed = false;
			}
			else{
				operands.push(operators.pop());
				//System.out.println(display());
			}
		}
		
		System.out.print("infix: " + infix + "\n");
		//System.out.println(display());
		int size = operands.getSize();
		//System.out.println("size:"+size);
		for(int i=0;i<size;i++){
			result = operands.pop() + " " + result;
			//System.out.println(i + ": " + result);
			//System.out.println(display());
		}
		return result;
	}
	
	public static void stackCheck(String token, String prevOp, String[] ops){
		//if(prevOp.equals("(")||prevOp.equals("")){
		if(Arrays.asList(ops).contains(prevOp)){
			operators.push(token);
		}
		else{
			double result = process(operators, operands);
			operands.push(String.valueOf(result));
			try{
				prevOp = (String)operators.peek();
			}
			catch(ArrayIndexOutOfBoundsException e){
				prevOp = "";
			}
			stackCheck(token, prevOp, ops);
		}
	}
	
	public static void stackCheckPF(String token, String prevOp, String[] ops){
		//if(prevOp.equals("(")||prevOp.equals("")){
		if(Arrays.asList(ops).contains(prevOp)){
			operators.push(token);
		}
		else{
			String result = (String) operators.pop();
			operands.push(result);
			try{
				prevOp = (String)operators.peek();
			}
			catch(ArrayIndexOutOfBoundsException e){
				prevOp = "";
			}
			stackCheckPF(token, prevOp, ops);
		}
	}
	
	public static double calculate(String operand1, String operator, String operand2){
		double op1 = Double.parseDouble(operand1);
		double op2 = Double.parseDouble(operand2);
		double result = 0;
		switch(operator){
		case "+":
			result = op1 + op2;
			break;
		case "-":
			result = op1 - op2;
			break;
		case "*":
			result = op1 * op2;
			break;
		case "/":
			if(op2 != 0){
				result = op1/op2;
			}
			else throw new ArithmeticException("You can't divide by zero!");
			break;
		case "^":
			result = Math.pow(op1, op2);
		}
		return result;
	}
	
	public static double process(AStack operators, AStack operands){
		if(operands.getSize()==1&&operators.getSize()==0)return Double.parseDouble((String) operands.pop());
		//System.out.println(display());
		String op2 = (String) operands.pop();
		String op1 = (String) operands.pop();
		String oper = (String) operators.pop();
		return calculate(op1, oper, op2);
	}
	
	public static String display(){
		return(operators.toString() + "\n" + operands.toString());
	}
	
	public static boolean checkBalance(String s) {

	   AStack<Character> pStack = new AStack<Character>();
	   try {
	 	for (int i=0;i<s.length();i++) {
	 	    if (s.charAt(i)=='(') { 
	  		pStack.push(s.charAt(i));
	  		continue;
	 	     }
	     	     if (s.charAt(i)!=')') continue; // ignore other character
	     	     pStack.pop();
	      	} 
	    } catch (ArrayIndexOutOfBoundsException e) {
	 	return false;
	    }
	    return pStack.empty();
	}
	
	public static void checkMisformed(String s){
		//TODO check for " (+ " and " +) " and " 1( " and " )1 " scenarios by scanning the string
		StringTokenizer scan = new StringTokenizer(s, operatorsString, true);
		String c = scan.nextToken();
		String cn = "";
		while(scan.hasMoreTokens()){
			cn  = scan.nextToken();
			if((c.equals("+")||c.equals("-")||c.equals("*")||c.equals("/")||c.equals("^"))&&cn.equals(")")){
				throw new ArithmeticException("Operators must be followed by a numeric value or '('.");
			}
			else if(c.equals("(")&&(cn.equals("+")||cn.equals("-")||cn.equals("*")||cn.equals("/")||cn.equals("^"))){
				throw new ArithmeticException("Operators should not follow opening parenthesis.");
			}
			else if(Character.isDigit(c.charAt(0))&&cn.equals("(")){
				throw new ArithmeticException("Missing operator before '('.");
			}
			else if((c.equals(")")&&Character.isDigit(c.charAt(0)))){
				throw new ArithmeticException("Missing operator after ')'.");
			}
			c = cn;
		}
	}

}
