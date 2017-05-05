package myUtil;


/**
 * Creates a Huffman Tree for encoding and compressing a message.  Uses an internal Node class to build a binary tree to represent
 * the Huffman Tree.  As described in the assignment, this tree is right-heavy following the logic laid out in the assignment description.
 * 1. The node with the smallest frequency occurrence is selected first.
 * 2. When two nodes are tied in frequency during selecting the smallest nodes to build the tree or when merging trees to build the tree,
 *    select the node according to the following criteria (in order, so if the first test is a tie, do the second, and so on until a winner
 *    is selected):
 *    - height of tree
 *    - number of nodes in tree
 *    - the sum of the ascii values of letter contained in the tree, which when using the standard english alphabet ascii values, will always
 *      result in different sums, and therefore will always select a winner.
 *      
 * This class also contains methods to print the tree, and per the assignment, methods to print all the code words to make a code book,
 * encode an arbitrary string consisting of letters and a space (" ") character, or decode a binary string that has been encoded with this
 * same Huffman Tree.
 * 
 * @author Justin Lomelino
 *
 */
public class HuffmanTree {
	
	HNode root;			//the root, or top-level node of the tree
	HNode[] nodes;		//an unlinked collection of all the nodes of the tree
	HNode[] tree;		//just contains the root node, but is a linked structure so all nodes are available to access
	
	/**
	 * Represents a node in the Huffman Tree.  Can contain:
	 *  
	 * - a letter Character
	 * - Integer value representing the frequency of occurrence in some
	 *   model of the frequency of occurrence of a letter in the english language
	 * - Node 'links' to the left and right child nodes, or null if not present
	 * 
	 * @author Justin Lomelino
	 *
	 */
	private class HNode{
		private Character letter;
		private Integer value;
		private HNode left, right;
		private HNode(Character letter, Integer value, HNode left, HNode right){
			this.letter = letter;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		
		public Integer getValue(){ return value; }
	}

	/**
	 * Constructs a Huffman Tree for encoding and decoding a message consisting of Characters and a space (" ") character from
	 * an array of characters representing the letters to use and an array of integers corresponding to the array of characters by index
	 * and representing the frequency of occurrence of the corresponding character in some model of the english language.
	 * 
	 * @param a a char[] representing the letters to use in the tree
	 * @param f an int[] with the indexes corresponding to the characters in the a char[] representing the frequency of occurrence in an'average' message
	 */
	public HuffmanTree(char[] a, int[] f) {
		HNode[] nodes = createNodes(a, f);
		tree = buildTree(nodes);
	}
	
	public HNode getRoot(){
		return this.root;
	}
	
	/**
	 * creates a HNode[] of nodes representing each character supplied at the frequencies specified by array index per character
	 * 
	 * @param a char[] representing the characters.
	 * @param f int[] representing the frequencies of characters.  The index in this array corresponds to the index in the 'a' char[]
	 * @return a HNode[] of all the nodes representing the characters and frequencies in the arrays initially supplied
	 */
	public HNode[] createNodes(char[] a, int[] f){
		HNode[] nodes = new HNode[a.length];
		for(int i=0;i<a.length;i++){
			HNode newNode = new HNode(a[i], f[i], null, null);
			nodes[i] = newNode;
		}
		this.nodes = nodes;
		return nodes;
	}
	
	/**
	 * recursive function to build the Huffman Tree.  Will run until nodes.length==1, and then sets the one remaining node in nodes to the root
	 * 
	 * I make an assumption here that an empty Huffman Tree is useless and therefore I don't have to worry about a tree with a single null node as the root
	 * 
	 * @param nodes a HNode[] containing the nodes left to be assigned to the tree.  Once this is length==1, the remaining node is set as the root and the method stops
	 * @return a HNode[] containing the nodes left to be assigned to the tree, including the newly created parent node
	 */
	public HNode[] buildTree(HNode[] nodes){

		if(nodes[0]!=null && nodes[1]==null){		//base recursion case, only one HNode in the tree, so that node is the root node
			root = nodes[0];
			return nodes;
		}
		
		
		
		//setting up variables to contain the smallest frequency node and the next smallest frequency node
		Integer smallestFreq = 10000;
		Integer nextSmallestFreq = 10000;
		HNode smallest = null;
		HNode nextSmallest = null;
		
		HNode[] newNodes = new HNode[nodes.length];		//will hold the array that will be returned that the array returned can be constructed per some rules
		
		
		
		//find the smallest and nextSmallest nodes per frequency and the rules in the assignment.  Uses the getNodePriority method
		//to help determine node sizes when the frequencies are the same.
		for(HNode node:nodes){
			if(node == null) continue;
			
			if (node.getValue() < smallestFreq){
				nextSmallestFreq = smallestFreq;
				nextSmallest = smallest;
				smallestFreq = node.getValue();
				smallest = node;
			}
			else if (node.getValue() == smallestFreq){
				if(node == smallest) continue;
				HNode priorityNode = getNodePriority(smallest, node);
				if(priorityNode == smallest){
					nextSmallestFreq = node.getValue();
					nextSmallest = node;
				}
				else{
					nextSmallestFreq = smallestFreq;
					nextSmallest = smallest;
					smallestFreq = node.getValue();
					smallest = node;
				}
			}
			else if(node.getValue() < nextSmallestFreq){
				nextSmallestFreq = node.getValue();
				nextSmallest = node;
			}
			else if(node.getValue() == nextSmallestFreq){
				if(node == nextSmallest) continue;
				HNode priorityNode = getNodePriority(nextSmallest, node);
				if(priorityNode == node){
					nextSmallestFreq = node.getValue();
					nextSmallest = node;
				}
			}
		}
		
		
		
		//generate the new parent node, by first assigning the left and right child nodes and then making a new HNode containing the
		//(up to) two child nodes as well as their summed frequency values.
		HNode priorityNode = getNodePriority(smallest, nextSmallest);
		HNode left = null;
		HNode right = null;
		if(priorityNode == smallest){
			left = nextSmallest;
			right = smallest;
		}
		else{
			left = smallest;
			right = nextSmallest;
		}

		HNode newParent = new HNode(null, (smallest.value + nextSmallest.value), left, right);
		
		
		
		//remove the smallest and next smallest node, and add the new parent node
		int counter = 0;
		
		for(HNode node:nodes){
			if(node!=null){
				if((node == smallest)||(node == nextSmallest)) continue;
				else{ 
					newNodes[counter] = node;  
					}
				counter++;
			}
		}
		newNodes[counter] = newParent;
		

		
		//recursively build the tree using the new tree root node and the remaining nodes in the array
		return buildTree(newNodes);
		
	}
	
	/**
	 * returns the HNode with the highest priority according to the three tests described in the assignment, in order
	 * - tree height
	 * - number of nodes in the tree
	 * - sum of ascii values of letters in the tree, which are always going to be different values
	 * 
	 * @param node1	the first HNode to test
	 * @param node2 the second HNode to test
	 * @return the HNode out of node1 and node2 that has priority according to the tests described in the assignment
	 */
	public HNode getNodePriority(HNode node1, HNode node2){
		//test tree height
		HNode treeHeightWinner = compareTreeHeight(node1, node2);
		if(treeHeightWinner != null) return treeHeightWinner;
		
		//if tree heights are equal, test number of nodes in the tree
		HNode numNodesWinner = compareNumNodes(node1, node2);
		if(numNodesWinner != null) return numNodesWinner;
		
		//if number of nodes in the tree are equal, test the sum of the ascii values of letters in the tree
		HNode asciiSumWinner = compareAsciiValues(node1, node2);
		return asciiSumWinner;
	}
	
	/**
	 * Helper method for comparing frequency values of two nodes.  Returns the node with the higher frequency value.
	 * 
	 * @param node1 the first HNode to check
	 * @param node2 the second HNode to check
	 * @return the HNode out of node1 and node2 with the greatest frequency value
	 */
	public HNode compareValues(HNode node1, HNode node2){
		if(node1.value > node2.value)return node1;
		if(node2.value > node1.value)return node2;
		return null;
	}
	
	/**
	 * Helper method for comparing the height of two trees represented by the root nodes.
	 * 
	 * @param node1 the first tree to check tree height
	 * @param node2 the second tree to check tree height
	 * @return the node that has the greater tree height
	 */
	public HNode compareTreeHeight(HNode node1, HNode node2){
		int n1 = heightOfTree(node1);
		int n2 = heightOfTree(node2);
		if(n1>n2) return node1;
		if(n2>n1) return node2;
		return null;
	}
	
	/**
	 * Helper method to get the height of a tree represented by it's root node
	 * @param node the root node of the tree to get tree height
	 * @return an int representing the height of the tree that the node supplied represents
	 */
	public int heightOfTree(HNode node){
		if(node == null) return 0;
		else return 1 + Math.max(heightOfTree(node.left), heightOfTree(node.right));
	}
	
	/**
	 * Helper method to compare the number of nodes in the trees represented by the nodes supplied
	 * 
	 * @param node1 the first node to count the number of nodes in the tree it represents
	 * @param node2 the second node to count the number of nodes in the tree it represents
	 * @return the HNode that has more nodes in the tree it represents
	 */
	public HNode compareNumNodes(HNode node1, HNode node2){
		int n1 = numberOfNodes(node1);
		int n2 = numberOfNodes(node2);
		if(n1>n2) return node1;
		if(n2>n1) return node2;
		return null;
	}
	
	/**
	 * Helper method, returns the number of nodes in the tree represented by the node supplied
	 * @param node the node to count the number of nodes in the tree it represents
	 * @return an integer representing the number of nodes in the tree that the supplied node represents
	 */
	public int numberOfNodes(HNode node){
		if(node==null) return 0;
		else return 1+(numberOfNodes(node.left) + numberOfNodes(node.right));
	}
	
	/**
	 * Helper method to compare the sum of the ascii values of any letters in the trees represented by the supplied nodes
	 * 
	 * @param node1 the first node to get the sum of the ascii values of any letters in the tree it represents
	 * @param node2 the second node to get the sum of the ascii values of any letters in the tree it represents
	 * @return the HNode with the greater sum of the ascii values of any letters in the tree it represents
	 */
	public HNode compareAsciiValues(HNode node1, HNode node2){
		int n1 = sumOfAscii(node1);
		int n2 = sumOfAscii(node2);
		if(n1>n2) return node1;
		if(n2>n1) return node2;
		return null;
	}
	
	/**
	 * Helper method to get the sum of the ascii values of any letters in the tree represented by the supplied node
	 * @param node the node to get the sum of ascii values of any letters in the tree it represents
	 * @return an int representing the sum of the ascii values of any letters in the tree represented by the supplied node
	 */
	public int sumOfAscii(HNode node){
		if(node==null) return 0;
		if(node.letter==null) return 0 + (sumOfAscii(node.left) + sumOfAscii(node.right));
		char c = node.letter;
		return (int)c + (sumOfAscii(node.left) + sumOfAscii(node.right));
	}
	
	/**
	 * Displays a binary tree structure, with the root node at the left and children organized into visually progressing layers moving
	 * right across the screen.  Unfortunately, connectors extend past the point they should vertically, but this method still supplies
	 * a nice visual representation of a binary tree represented by the HNode supplied.
	 * 
	 * @param root the root node of the tree to display
	 * @param level an int that should be 0 in most cases, helps determine how the tree is drawn
	 */
	public static void printBinaryTree(HNode root, int level){
	    if(root==null)
	         return;
	    printBinaryTree(root.right, level+1);
	    if(level!=0){
	        for(int i=0;i<level-1;i++)
	            System.out.print("|\t");
	        	if(root.letter!=null){
	        		System.out.println("|-------"+root.value+":"+root.letter);
	        	}
	        	else System.out.println("|-------"+root.value);
	    }
	    else
	        System.out.println(root.value);
	    printBinaryTree(root.left, level+1);
	} 
	
	/**
	 * Displays a list of the letters in the Huffman Tree, their ascii values, the binary 'code word' for the letter, and the amount of 
	 * average occurrence in an average message in some model of the english language, in this format:
	 * letter [ascii] : codeWord (frequency)
	 * 
	 * starts with the root node of the Huffman Tree.
	 */
	public void printCodeWords(){
		printCodeWords(root, "");
	}
	
	/**
	 * Displays a list of the letters in the Huffman Tree, their ascii values, the binary 'code word' for the letter, and the amount of 
	 * average occurrence in an average message in some model of the english language, in this format:
	 * letter [ascii] : codeWord (frequency)
	 * 
	 * @param node the node representing the root of the tree that should have it's code structure displayed
	 * @param path the prefix for the binary code word for the letters in the tree
	 */
	public void printCodeWords(HNode node, String path){
		if(node.left==null){
			System.out.printf("%s [%d] : %s (%d)%n", node.letter, (int)node.letter, path, node.value);
			return;
		}
		printCodeWords(node.left, (path+"0"));
		printCodeWords(node.right, (path+"1"));
	}
	
	/**
	 * Encodes an arbitrary String consisting of english characters and a space (" ") character into a binary compressed code representing
	 * the letters in the String and encoded according to the Huffman Tree
	 * 
	 * @param text the String to encode
	 * @return a String of 0 and 1 characters representing the binary encoded version of the String supplied
	 */
	public String encode(String text){
		String coded = "";
		for(int i=0;i<text.length();i++){
			String code = find(text.charAt(i));
			coded += code; //+ " | ";
		}
		return coded;
	}
	
	/**
	 * Finds the encoding for a character according to the Huffman Tree.  Starts at the root node.
	 * @param a the character to encode
	 * @return a String of 0 and 1 characters representing the binary encoded version of the character supplied
	 */
	public String find(char a){
		return find(a, root, "");
	}
	
	/**
	 * Finds the encoding for a character according to the Huffman Tree.
	 * 
	 * @param a the character to encode
	 * @param node the root node of the tree of characters to use when encoding
	 * @param path the prefix applied to the binary encoded String for the character
	 * @return a String representing the binary encoded character according to the Huffman Tree
	 */
	public String find(char a, HNode node, String path){
		if(node.left==null){
			if(node.letter==a) return path;
			else return null;
		}
		String code = find(a, node.left, (path+"0"));
		if(code!=null) return code;
		return find(a, node.right, (path+"1"));
	}
	
	/**
	 * Decodes back to english characters and spaces a binary encoded String that was encoded with this Huffman Tree
	 * 
	 * @param codeString a String of 0 and 1 representing the binary encoded message
	 * @return a String of the characters and spaces that were encoded with this Huffman Tree
	 */
	public String decode(String codeString){
		HNode node = root;
		String text = "";

		for(int i=0;i<codeString.length();i++){
			if(codeString.charAt(i)=='0'){
				node = node.left;
			}
			else{
				node = node.right;
			}

			if(node.left==null){		//node is a leaf, as this is a full binary tree
				text = text + node.letter;
				node = root;	
			}
			
		}
		return text;
	}

}
