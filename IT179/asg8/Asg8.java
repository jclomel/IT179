
/**
 * You should not modify this program. You should develop your own 
 * HuffmanTree.java and put it in the package, myUtil. 
 * 
 * @author cli2
 *
 */
import myUtil.HuffmanTree;

public class Asg8 {
	// This frequency is based on the example from Corman's book
	static public void CormanFrequency() {
	    int[] f = {82,15,29,43,127,22,20,61,70,5,8,40,24,67,75,19,4,60,63,91,28,10,23,2,21,1,123};
		char[] a = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '};
		HuffmanTree ht = new HuffmanTree(a, f); // Construct a Huffman Tree based on a and f
		ht.printCodeWords();
		System.out.printf("%nCode: %s%n", ht.encode("HUFFMAN CODE IS VERY VERY USEFUL"));		
		System.out.printf("Text: %s%n", ht.decode("00100111111110011110011111011001001010011101010110101000100111011001001110110010010101111110000010011110011000110100101110101001001110101011111010111001100000011001101011010110011101100010101101000101111000001011111111111000101110"));
		System.out.printf("Text: %s%n", ht.decode("110101100111011000100011111001100111101110111001111011000000110101000001101010001111101011101010011011101100100111100101100100111010010111100011111001"));
		System.out.printf("Text: %s%n", ht.decode("111000101000001101010000011010111000100000101011011000011111001011000011001110001101101001111110000100111100101010111100010111000100000100011000111110000001111100000110"));
		
//		//TODO: remove this!!!
//		HuffmanTree.printBinaryTree(ht.getRoot(), 0);
//		System.out.printf("Test: %s%n", ht.decode(ht.encode("HUFFMAN CODE IS VERY VERY USEFUL")));
	}
	
	public static void main(String[] args) {		
		CormanFrequency();
	}

}
