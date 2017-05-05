
import java.util.ArrayList;

/**
 * represents a set of possible 'card tricks' and 'games' to be done with a set of cards
 * 
 * This interface allows:
 * - play 'Find Aces' attempting to sort according to the rules given in the assignment and find the ace in each suit of the deck.
 *   Should display each 'move' in the search to the console, allowing you to follow along
 *   
 * @author jclomel
 * @param <T> the type of the objects contained in the ArrayList deck representation, typically Card from the poker package
 *
 */
public interface Gameable<T> extends Deckable<T>{
	
	/**
	 * play 'Find Aces' attempting to sort according to the given rules and find the ace in each suit of the deck.
	 * Should display each 'move' in the search to the console, allowing you to follow along
	 * @param deck an ArrayList<T> with T being a representation of some sort of playing card
	 */
	public void findAces(ArrayList<T> deck);

}
