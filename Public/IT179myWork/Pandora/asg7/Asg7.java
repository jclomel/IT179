import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implements a binary search tree from lines of integers in a text file and reports some data about each line
 * 
 * @author Justin Lomelino
 */
public class Asg7 {

	/**
	 * This internal class represents a binary search tree structure using double-linked nodes of data.  This feels like
	 * poor practice, typically I would want to implement this in a separate class file and use that in the program, but
	 * the assignment called for everything to be implemented in the Asg7 class, so here it is.  Since this is meant to be
	 * and internal representation, the class is set to private so it will not be called by external code, and set to static
	 * so that the main method can create instances of it.
	 * @author Justin Lomelino
	 *
	 */
	private static class BinarySearchTree{
		
		TNode<Integer> root;
		private boolean found=false;
		
		public BinarySearchTree(){
			root = null;
		}
	
		/**
		 * represents a node of data in the binary search tree.  Each node is linked to up to 2 other nodes, where the data in the
		 * left node is less than the root, the data in the right node is greater or equal to the root, and each node is itself
		 * a binary search tree
		 * @author Justin Lomelino
		 *
		 * @param <Integer> since we're just dealing with int data, it is incapsulated in an Integer for easier handling.
		 */
		@SuppressWarnings("hiding")
		private class TNode<Integer>{
			private Integer data;
			private TNode<Integer> left, right;
			private TNode(Integer data, TNode<Integer> left, TNode<Integer> right){
				this.data = data;
				this.left = left;
				this.right = right;
			}
		}
	
		/**
		 * adds an integer to the tree
		 * @param data the integer to add to the tree
		 * @return true if the data was successfully added to the tree
		 */
		public boolean add(Integer data){
			if(root!=null) return add(root, data);
			root = new TNode<Integer>(data, null, null);
			return true;
		}
		
		/**
		 * adds an integer to the tree belonging to the specified node
		 * @param node a TNode, the node to add the integer below
		 * @param data the integer to add
		 * @return true if the data was successfully added to the tree
		 */
		public boolean add(TNode<Integer> node, Integer data){
			if(data.compareTo(node.data)==0) return false;
			if(data.compareTo(node.data) < 0) {
				//data is smaller
				if(node.left == null) node.left = new TNode<Integer>(data, null, null);
				else return add(node.left, data);
			}
			else{
				//data is larger
				if(node.right == null) node.right = new TNode<Integer>(data, null, null);
				else return add(node.right, data);
			}
			return true;
		}
		
		/**
		 * searches the tree for the specified integer, and returns true if it is found
		 * @param data the integer to search the tree for
		 * @return true if the integer was found, otherwise false
		 */
		public boolean search(Integer data){
			if(root !=null) return search(root, data);
			return false;
		}
		
		/**
		 * searches the binary tree that has the indicated node as it's root for the specified integer
		 * @param node the root node of the binary search tree to search
		 * @param data the integer to search for
		 * @return true if the integer was found in the tree, otherwise false
		 */
		public boolean search(TNode<Integer> node, Integer data){
			if(data.compareTo(node.data)==0) return true;
			if(data.compareTo(node.data) < 0){
				//data is smaller
				if(node.left==null) return false;
				return search(node.left, data);
			}
			if(node.right==null) return false;
			return search(node.right, data);
		}
		
		/**
		 * calls the overloaded toString using the root node of the binary search tree
		 */
		public String toString(){
			return toString(root);
		}
		
		/**
		 * Returns a String representation of the nodes in the tree recursively
		 * @param node the root node of the tree to return as a String
		 * @return a String, representing the node structure of the tree
		 */
		public String toString(TNode<Integer> node){
			if(node==null) return "";
			return toString(node.left) + " " +
					node.data.toString() + " " +
					toString(node.right);
		}
		
		/**
		 * searches the tree for the maximum Integer value it contains
		 * @return returns the maximum Integer value in the tree
		 */
		public Integer max(){
			if(root==null) throw new NoSuchElementException();
			return max(root);
		}
		
		/**
		 * searches the tree for the maximum Integer value it contains
		 * @param node the root node of the tree to search
		 * @return the maximum Integer value found in the tree
		 */
		public Integer max(TNode<Integer> node){
			if(node.right != null) return max(node.right);
			return node.data;
		}
		
		/**
		 * searches the tree for the minimum Integer value it contains
		 * @return the minimum Integer value found in the tree
		 */
		public Integer min(){
			if(root==null) throw new NoSuchElementException();
			return min(root);
		}
		
		/**
		 * searches the tree for the minimum Integer value it contains
		 * @param node the root node of the tree to search
		 * @return the minimum Integer value found in the tree
		 */
		public Integer min(TNode<Integer> node){
			if(node.left != null) return min(node.left);
			return node.data;
		}
		
		/**
		 * returns an int representing the number of nodes in the binary search tree
		 * @return an int representing the number of data nodes in the binary search tree
		 */
		public int size(){
			return size(root);
		}
		
		/**
		 * returns an int representing the number of nodes in the binary search tree
		 * @param node the root node of the tree to count nodes in
		 * @return an int representing the number of data nodes in the binary search tree
		 */
		public int size(TNode<Integer> node){
			if(node==null) return 0;
			return size(node.left) + size(node.right) + 1;
		}
		
		/**
		 * returns an int representing the height of the binary search tree
		 * @return an int representing the height of the binary search tree
		 */
		public int height(){
			return height(root);
		}
		
		/**
		 * returns an int representing the height of the binary search tree that has the provided node as it's root
		 * @param node the root node of the tree to search
		 * @return an int representing the height of the tree
		 */
		public int height(TNode<Integer> node){
			if(node==null) return 0;
			int L = height(node.left);
			int R = height(node.right);
			return (L<R ? R : L) + 1;
		}
		
		/**
		 * removes an Integer from the binary search tree
		 * @param data the Integer to remove from the tree
		 * @return true if the Integer was removed, otherwise false
		 */
		public boolean remove(Integer data){
			found = false;
			root = remove(root, data);
			return found;
		}
		
		/**
		 * removes an Integer from the binary search tree
		 * @param node the root node of the tree to search for the Integer
		 * @param data the Integer to remove
		 * @return the node that was removed from the tree
		 */
		public TNode<Integer> remove(TNode<Integer> node, Integer data){
			if(node==null) return null;
			if(data.compareTo(node.data)==0) {
				if(node.left==null && node.right==null) return null;
				Integer x;
				if(node.left != null){
					x = max(node.left);
					node.left = remove(node.left, x);
				}
				else{
					x = min(node.right);
					node.right = remove(node.right, x);
				}
				node.data = x;
				return node;
			}
			if(data.compareTo(node.data) < 0) node.left = remove(node.left, data);
			else node.right = remove(node.right, data);
			return node;
		}
		
		/**
		 * calls the overloaded inorder method using the root of the binary search tree
		 */
		public void inorder(){ inorder(root); }
		
		/**
		 * prints the nodes in the tree in infix order
		 * @param node the root node of the tree to print
		 */
		public void inorder(TNode<Integer> node){
			if(node==null) return;
			inorder(node.left);
			System.out.print(node.data + " ");
			inorder(node.right);
		}
		
		/**
		 * calls the overloaded preorder method using the root of the binary search tree
		 */
		public void preorder(){ preorder(root); }
		
		/**
		 * prints the nodes in the tree in prefix order
		 * @param node the root node of the tree to print
		 */
		public void preorder(TNode<Integer> node){
			if(node==null) return;
			System.out.print(node.data + " ");
			preorder(node.left);
			preorder(node.right);
		}
		
		/**
		 * calls the overloaded postorder method using the root of the binary search tree
		 */
		public void postorder(){ postorder(root); }
		
		/**
		 * prints the nodes in the tree in postfix order
		 * @param node the root node of the tree to print
		 */
		public void postorder(TNode<Integer> node){
			if(node==null) return;
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.data + " ");
		}
	}

	/**
	 * main method of Asg7.  Opens the data file with the name provided as an argument, or if no name is provided, the default "numbers.txt" file.
	 * For each line in the data file, create a binary search tree with the numbers in the line, and display some information about the created
	 * binary search tree.
	 * @param args the data file name to open
	 * @throws FileNotFoundException if the file name provided cannot be located
	 */
	public static void main(String[] args) throws FileNotFoundException{
		Scanner fileReader = null;
		//if a filename was provided, try to open the file 
		if(args.length > 0){
			try {
				fileReader = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		//default to the "numbers.txt" file
		else fileReader = new Scanner(new File("numbers.txt"));
		
		//for each line in the file, create a binary search tree of the numbers in the line
		while(fileReader.hasNextLine()){
			Scanner lineReader = new Scanner(fileReader.nextLine());
			BinarySearchTree bst = new BinarySearchTree();
			//add each number in the line to the binary search tree
			while(lineReader.hasNext()){
				Integer dataPoint = Integer.parseInt(lineReader.next());
				if(bst.search(dataPoint)){
					bst.remove(dataPoint);
				}
				else{
					bst.add(dataPoint);
				}
			}
			
			//display information about the binary search tree
			System.out.print("Prefix: ");
			bst.preorder();
			System.out.print("\n");
			
			System.out.print("Infix: ");
			bst.inorder();
			System.out.print("\n");
			
			System.out.print("Postfix: ");
			bst.postorder();
			System.out.print("\n");
			
			System.out.println("Total: " + bst.size());
			System.out.println("Height: " + bst.height());
			System.out.println("Max: " + bst.max());
			System.out.println("Min: " + bst.min());
			System.out.println("");
			
			lineReader.close();
		}
		
	}
}
