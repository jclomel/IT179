import myUtil.HuffmanTree;

public class HuffmanTreeTester {

	public static void main(String[] args){
		char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		int[] ff = {10, 22, 40, 54, 20, 8, 77, 2};
		int[] f = {82,15,29,43,127,22,20,61,70,5,8,40,24,67,75,19,4,60,63,91,28,10,23,2,21,1,123};
		char[] a = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '};
		
		HuffmanTree ht = new HuffmanTree(a, f);
		
		HuffmanTree.printBinaryTree(ht.getRoot(), 0);
		ht.printCodeWords();
		//System.out.printf("Test: %s%n", ht.encode("HUFFMAN CODE IS VERY VERY USEFUL"));
		System.out.printf("Test: %s%n", ht.decode(ht.encode("HUFFMAN CODE IS VERY VERY USEFUL")));
		//System.out.printf("Test: %s%n", ht.decode(ht.encode("H")));
		//System.out.printf("Test: %s%n", ht.decode(ht.encode("HU")));
		//System.out.printf("Test: %s%n", ht.decode(ht.encode("HUF")));
	}

}
