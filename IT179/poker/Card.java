package poker;

public class Card {
	private char suit;
	private int kind;
	
	/**
	 * Set suit and kind of the card
	 * @param suit 
	 * @param kind
	 */
	Card(char suit, int kind) {
		this.suit = suit;
		this.kind = kind;
	}
	
	/**
	 * @return a char indicating the suit of the card.
	 */
	public char getSuit() {
		return suit;
	}
	
	/**
	 * @return an int indicating the kind of the card.
	 */
	public int getKind() {
		return kind;
	}
	
	/**
	 *  @return a String, suit(char) followed by its kind(int).
	 */
	public String toString() {
		return suit+""+kind+" ";
	}

}
