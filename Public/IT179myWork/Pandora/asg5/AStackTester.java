import myUtil.AStack;
public class AStackTester {


	public static void main(String[] args) {
		AStack<String> stringStack = new AStack<String>();
		System.out.println(stringStack.empty());
		
		System.out.println(stringStack.push("One"));
		System.out.println(stringStack.toString());
		System.out.println(stringStack.empty());
		
		stringStack.push("Two");
		System.out.println(stringStack.toString());
		
		System.out.println(stringStack.peek());
		System.out.println(stringStack.toString());
		
		AStack<Integer> intStack = new AStack<Integer>(2);
		intStack.push(new Integer(1));
		intStack.push(new Integer(2));
		System.out.println(intStack.toString());
		System.out.print(intStack.getSize());
		System.out.println(" " + intStack.getLength());
		
		intStack.push(new Integer(3));
		System.out.println(intStack.toString());
		System.out.print(intStack.getSize());
		System.out.println(" " + intStack.getLength());
		
		intStack.push(new Integer(4));
		intStack.push(new Integer(5));
		System.out.println(intStack.toString());
		System.out.print(intStack.getSize());
		System.out.println(" " + intStack.getLength());
		
		System.out.println(intStack.pop());
		System.out.println(intStack.toString());
		System.out.print(intStack.getSize());
		System.out.println(" " + intStack.getLength());
		
		System.out.println(intStack.pop());
		System.out.println(intStack.toString());
		System.out.print(intStack.getSize());
		System.out.println(" " + intStack.getLength());
		
		System.out.println(stringStack.toString());
		System.out.println(stringStack.pop());
		System.out.println(stringStack.toString());
		System.out.print(stringStack.getSize());
		System.out.println(stringStack.getLength());
		
		System.out.println(stringStack.pop());
		System.out.println(stringStack.toString());
		System.out.print(stringStack.getSize());
		System.out.println(" " + stringStack.getLength());
		
		/*System.out.println(stringStack.pop());
		System.out.println(stringStack.toString());
		System.out.print(stringStack.getSize());
		System.out.println(stringStack.getLength());*/
		
	}

}
