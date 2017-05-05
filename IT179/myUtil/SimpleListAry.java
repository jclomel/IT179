package myUtil;

public class SimpleListAry<T extends Comparable<T>> implements SimpleList{

	private Object[] data;
	private int size;
	
	public SimpleListAry() {
		this.data = new Object[10];
		this.size = 0;
	}
	
	public SimpleListAry(int capacity){
		this.data = new Object[capacity];
		this.size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Comparable get(int i) {
		return (Comparable) data[i];
	}

	@Override
	public Comparable set(int i, Comparable item) {
		this.data[i] = item;
		return (Comparable) this.data[i];
	}

	@Override
	public int indexOf(Object item) {
		for(int i=0;i<size;i++){
			if(data[i]==item){
				return i;
			}
		}
		return -1;
		
	}

	@Override
	public void add(int i, Comparable item) {
		if(i<0 || i>size){
			throw new IndexOutOfBoundsException();
		}
		if(size+2>data.length){
			Object[] temp = new Object[data.length*2];
			for(int j=0;j<=size;j++){
				temp[j] = data[j];
			}
			data = temp;
		}
		
		for(int k=size+1;k>i;k--){
			data[k] = data[k-1];
		}
		data[i] = item;
		size++;
	}

	@Override
	public Comparable remove(int i) {
		if(i<0 || i>size-1){
			throw new IndexOutOfBoundsException();
		}
		Object ret = data[i];
		for(int j=i;j<size;j++){
			data[j] = data[j+1];
		}
		size--;
		return (Comparable) ret;
	}

	@Override
	public Comparable max() {
		if(size==0){
			throw new IllegalArgumentException();	//not sure what the appropriate exception type is
		}
		if(size==1){
			return (Comparable) data[0];
		}
		Comparable max = (Comparable) data[0];
		for(int i=1;i<size;i++){
			if(((Comparable<T>) data[i]).compareTo((T) max)==1){
				max = (Comparable) data[i];
			}
		}
		return max;
	}

	@Override
	public Comparable min() {
		if(size==0){
			throw new IllegalArgumentException();	//not sure what the appropriate exception type is
		}
		if(size==1){
			return (Comparable) data[0];
		}
		Comparable min = (Comparable) data[0];
		for(int i=1;i<size;i++){
			if(((Comparable<T>) data[i]).compareTo((T) min)==-1){
				min = (Comparable) data[i];
			}
		}
		return min;
	}

}
