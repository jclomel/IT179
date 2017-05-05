package myUtil;

public class AStack<T> implements Stack{
	
	private Object[] stack;
	private int size;

	public AStack() {
		this.stack = new Object[10];
		this.size = 0;
		
	}
	
	public AStack(int capacity){
		this.stack = new Object[capacity];
		this.size = 0;
	}

	@Override
	public Object push(Object item) {
		if(size+1<=stack.length){
			stack[size] = item;
			size++;
		}
		else{
			Object[] temp = new Object[stack.length*2];
			for(int i=0;i<size;i++){
				temp[i] = stack[i];
			}
			stack = temp;
			this.push(item);
		}
		return item;
	}

	@Override
	public Object peek() {
		if(size!=0){
			return stack[size-1];
		}
		else throw new ArrayIndexOutOfBoundsException("Cannot peek at an empty stack!");
	}

	@Override
	public Object pop() {
		if(size!=0){
			Object ret = stack[size-1];
			size--;
			return ret;
		}
		else throw new ArrayIndexOutOfBoundsException("Cannot pop from an empty stack!");
		
	}

	@Override
	public boolean empty() {
		if(this.size==0){
			return true;
		}
		else return false;
	}
	
	
	/*Not in the API, the methods below are for my own testing purposes*/
	public String toString(){
		String ret = new String("[");
		for(int i=0;i<size;i++){
			ret+=(stack[i].toString()+" ");
		}
		ret+="}";
		return ret;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getLength(){
		return stack.length;
	}

}
