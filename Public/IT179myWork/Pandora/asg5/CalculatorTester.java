
import myUtil.Calculator;
public class CalculatorTester {

	public static void main(String[] args){
		Calculator calc = new Calculator();
		System.out.println("1 + 2 = " + calc.calculate("1", "+", "2"));
		System.out.println("2 - 1 = " + calc.calculate("2", "-", "1"));
		System.out.println("1 - 2 = " + calc.calculate("1", "-", "2"));
		System.out.println("2 * 2 = " + calc.calculate("2", "*", "2"));
		System.out.println("2 * 0 = " + calc.calculate("2", "*", "0"));
		System.out.println("4 / 2 = " + calc.calculate("4", "/", "2"));
		System.out.println("2 / 4 = " + calc.calculate("2", "/", "4"));
		try{
			System.out.println("2 / 0 = " + calc.calculate("2", "/", "0"));
		}
		catch(ArithmeticException e){
			System.out.println("2 / 0 = " + e.getMessage());
		}
		System.out.println("0 / 2 = " + calc.calculate("0", "/", "2"));
		System.out.println("2 ^ 3 = " + calc.calculate("2", "^", "3"));
		System.out.println("0 ^ 3 = " + calc.calculate("0", "^", "3"));
		System.out.println("3 ^ 0 = " + calc.calculate("3", "^", "0"));
		
		System.out.println(calc.evaluate("(7)"));
		System.out.println(calc.evaluate("2+2"));
		System.out.println(calc.evaluate("2+2-1"));
		System.out.println(calc.evaluate("2+2-1+3-1"));
		System.out.println(calc.evaluate("(2+2)"));
		System.out.println(calc.evaluate("(2+2)+2"));
		System.out.println(calc.evaluate("2+(2+2)"));
		System.out.println(calc.evaluate("2-(2+2)"));
		System.out.println(calc.evaluate("(2+2)-2"));
		
		System.out.println(calc.evaluate("2*2"));
		System.out.println(calc.evaluate("2/2"));
		System.out.println(calc.evaluate("2*2/3"));
		System.out.println(calc.evaluate("2*2-1"));
		System.out.println(calc.evaluate("1-2*2"));
		System.out.println(calc.evaluate("(1-2)*2"));
		System.out.println(calc.evaluate("2*(2-1)"));
		System.out.println(calc.evaluate("2*(2+1)"));
		System.out.println(calc.evaluate("2/(2+1)"));
		
		System.out.println(calc.evaluate("2^2^2"));
		System.out.println(calc.evaluate("2^3^2"));
		System.out.println(calc.evaluate("2^3+5^3"));
		System.out.println(calc.evaluate("9*(8+4+1)+2*1^(3+2)^(9*(3+2)+9)*6"));
		
		System.out.println(calc.evaluatePostfix("2 2 +"));
		System.out.println(calc.evaluatePostfix("2 2 + 3 *"));
		System.out.println(calc.evaluatePostfix("2 2 3 ^ ^"));
		System.out.println(calc.evaluatePostfix("2 2 + 3 ^ 2 +"));
		
		System.out.println(calc.infixToPostfix("2+2"));
		System.out.println(calc.infixToPostfix("2/(2+1)"));
		
		String exp = calc.infixToPostfix("9*(8+4+1)+2*1^(3+2)^(9*(3+2)+9)*6");
		System.out.println(calc.evaluatePostfix(exp));
		
		try{
			System.out.println(calc.evaluate("(2+2"));
		}
		catch(ArithmeticException e){
			System.out.println("ArithmeticException:" +e.getMessage());
		}
		
		try{
			System.out.println(calc.infixToPostfix("(2+2"));
		}
		catch(ArithmeticException e){
			System.out.println("ArithmeticException:" +e.getMessage());
		}
		
		try{
			System.out.println(calc.evaluate("10(-2+2)"));
		}
		catch(ArithmeticException e){
			System.out.println("ArithmeticException:" +e.getMessage());
		}
		
		try{
			System.out.println(calc.evaluate("1+(2*3+)4"));
		}
		catch(ArithmeticException e){
			System.out.println("ArithmeticException:" +e.getMessage());
		}
		
	}

}
