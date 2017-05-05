
import java.util.ArrayList;

/**
 * An abstract class representing a 'pack' or loose collection of cards
 * (unopened deck of playing cards, for example, since it is not yet 'generated')
 * Contains data structures and accessors/mutators for making an ArrayList representation of a 'pack' of cards.
 * 
 * @author jclomel
 * @param <T> the type of the pack, with T being a class that acts as a representation for a playing card.  Typically Card from the poker package
 * @param suits a char[] representing the suits of the pack of cards.
 * @param deck the deck of cards or some subset of it that the pack contains.
 */
public abstract class Pack<T> {
	
	private ArrayList<T> deck;
	private char[] suits;

	public Pack(char[] suits, ArrayList<T> deck) {
		this.suits = suits;
		this.deck = deck;
	}

	public ArrayList<T> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<T> deck) {
		this.deck = deck;
	}

	public char[] getSuits() {
		return suits;
	}

	public void setSuits(char[] suits) {
		this.suits = suits;
	}

}
