
public class Asg2{

	/**
	 * main method of the program.
	 * Tests the Deck class for it's ability to play 'Finding Aces'
	 * 
	 * - create an instance of the Deck class
	 * - use Deck to:
	 * 		- generate a standard deck of 52 playing cards and shuffle it
	 * 		- play the 'find the aces' game with the generated deck, following the rules outlined in the Gameable interface
	 * 
	 * @param args N/A
	 * @author jclomel
	 * 
	 */
	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.findAces(deck.getDeck());

	}

}
