package poker;
import java.util.ArrayList;

public class PokerDeck {

	private char[] suits = {'C','D','H','S'};
	private Card[] deck;
	
	/**
	 * Construct a standard deck of poker cards.
	 */
	public PokerDeck() {
		deck = new Card[52];
		for (int i=0;i<4;i++) 
			for (int j=0;j<13;j++) 
				deck[i*13+j] = new Card(suits[i],j+1);				
	}
	
	/**
	 * Randomly shuffle the cards
	 */
	public void shuffle() {
		Card temp;
		int k;
		for (int i=0;i<52;i++) {
			k = (int) (Math.random()*52.0);
			temp = deck[k];
			deck[k] =deck[i];
			deck[i] = temp;
		}
	}
	
	/**
	 * @return An ArrayList of Cards
	 */
	public ArrayList<Card> toArrayList() {
		ArrayList<Card>  adeck = new ArrayList<Card>();
		for (int i=0;i<52;i++) {
			adeck.add(adeck.size(),deck[i]);
		}
		return adeck;
	}


}
