package myUtil;

public class SimpleListLk<T extends Comparable<T>> implements SimpleList{
	
	private class Node<E>{
		private Node<E> next;
		public Comparable<E> data;
		
		private Node(Comparable<E> data, Node<E> next){
			this.data = data;
			this.next = next;
		}
	}
	
	private Node<T> head;
	private int size;

	public SimpleListLk() {
		this.head = null;
		this.size = 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public Comparable<T> get(int i) {
		if(i >= size || i < 0){
			throw new IndexOutOfBoundsException();
		}
		Node<T> pointer = head;
		int j = 0;
		while(j<i){
			pointer = pointer.next;
			j++;
		}
		return pointer.data;
	}

	@Override
	public Comparable<T> set(int i, Comparable item) {
		Node<T> prev = null;
		Node<T> pointer = head;
		Node<T> set = null;
		
		if(i >= size || i < 0){
			throw new IndexOutOfBoundsException();
		}
		if(i == 0){
			head = new Node<T>(item, head.next);
			set = head;
		}
		else{
			for(int j=0;j<i;j++){
				prev = pointer;
				pointer = pointer.next;
			}
			prev.next = new Node<T>(item, pointer.next);
			set = prev.next;
		}
		return set.data;
	}

	@Override
	public int indexOf(Object item) {
		int index = -1;
		Node<T> prev = null;
		Node<T> pointer = head;
		for(int j=0;j<size;j++){
			if(pointer.data==item){
				index = j;
			}
			prev = pointer;
			pointer = pointer.next;
		}
		return index;
	}

	@Override
	public void add(int i, Comparable item) {
		if(i > size || i < 0){
			throw new IndexOutOfBoundsException();
		}
		if(i == 0){
			head = new Node<T>(item, head);
			size++;
		}
		else{
			Node<T> prev = null;
			Node<T> pointer = head;
			for(int j=0;j<i;j++){
				prev = pointer;
				pointer = pointer.next;
			}
			prev.next = new Node<T>(item, pointer);
			size++;
		}
	}

	@Override
	public Comparable<T> remove(int i) {
		Node<T> pointer = null;
		if(i > size-1 || i < 0){
			throw new IndexOutOfBoundsException();
		}
		if(i==0){
			pointer = head;
			head = head.next;
			size--;
		}
		else{
			Node<T> prev = null;
			pointer = head;
			for(int j=0;j<i;j++){
				prev = pointer;
				pointer = pointer.next;
			}
			prev.next = pointer.next;
			size--;
		}
		return pointer.data;
	}

	@Override
	public Comparable<T> max() {
		if(size==0){
			throw new IllegalArgumentException();	//not sure what the appropriate exception type is
		}
		if(size==1)	return head.data;
		Comparable<T> max = head.data;
		//Node<T> prev = head;
		Node<T> pointer = head.next;
		for(int j=1;j<size;j++){
			if(pointer.data.compareTo((T) max)==1)	max = pointer.data;
			//prev = pointer;
			pointer = pointer.next;
		}
		return max;
	}

	@Override
	public Comparable min() {
		if(size==0){
			throw new IllegalArgumentException();	//not sure what the appropriate exception type is
		}
		if(size==1)	return head.data;
		Comparable<T> min = head.data;
		//Node<T> prev = head;
		Node<T> pointer = head.next;
		for(int j=1;j<size;j++){
			if(pointer.data.compareTo((T) min)==-1)	min = pointer.data;
			//prev = pointer;
			pointer = pointer.next;
		}
		return min;
	}

}
