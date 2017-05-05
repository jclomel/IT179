
import java.util.ArrayList;

/**
 * allows for standard card deck operations(checking, generation, moving), 
 * given an ArrayList representation of a standard deck of cards
 * 
 * This interface allows:
 * - generate and shuffle a new deck
 * - split out a suit of cards, keeping relative order
 * - get the first card in the deck
 * - get the card located at an index
 * - get the card that matches the string
 * - check the first card to see if it is the same as the string
 * - check the card located at index to see if it is the same as the string
 * - flip the order of cards, from the first card to the supplied index,
 *   so that the first card ends up at the index, the second card at index-1, the third at index-2, and so on
 *   until the last card is in the first position
 * - print the contents of the deck to the console
 * 
 * @author jclomel
 * @param <T> the type of the objects contained in the ArrayList deck representation, typically Card from the poker package
 *
 */
public interface Deckable<T>{
	
	/**
	 * generate and shuffle a new deck of 52 playing cards with 4 suits using the pokerDeck class in the poker package
	 * @return an ArrayList<T> with T being some sort of representation of a playing card
	 */
	public ArrayList<T> generate();
	
	/**
	 * return an ArrayList<T> representing one suit of cards in relative order out of a deck of cards
	 * @param suit a char, represents the suit of the cards to select
	 * @param deck an ArrayList<T> with T representing some sort of playing card
	 * @return an ArrayList<T> representing all the cards of the given suit in relative order from a deck of cards
	 */
	public ArrayList<T> getSuitRelativeOrder(char suit, ArrayList<T> deck);
	
	/**
	 * return an object representing the card at the top of the deck or subset of a deck supplied
	 * @param deck an ArrayList<T> with T representing some kind of playing card
	 * @return an object, representing the top card of the deck or subset of a deck supplied
	 */
	public Object getFirst(ArrayList<T> deck);
	
	/**
	 * return an object representing the card located at index in a deck or subset of a deck
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 * @param index an int, the index of the Card object to return
	 * @return an Object, represents the standard playing card located at index
	 */
	public Object getCard(ArrayList<T> deck, int index);
	
	/**
	 * returns an object representing the card that matches the target string
	 * @param deck an ArrayList<Card> representing a deck of cards or some subset of it
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  The card matching the target string will be returned.
	 * @return an object representing a standard playing card that matches the suit and type of the target String
	 */
	public Object getCard(ArrayList<T> deck, String target);
	
	/**
	 * return a boolean, true if the first card in the deck matches the target String, otherwise false
	 * @param deck an ArrayList<Card> representing a standard deck of playing cards or some subset of it
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  It is used to check if the card at the given index is the same.
	 * @return a boolean, true if the first card in the deck matches the target String, otherwise false
	 */
	public boolean checkFirst(ArrayList<T> deck, String target);
	
	/**
	 * return a boolean, true if the card located at index in the deck matches the target String, otherwise false
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 * @param index an int representing the index of the card to check
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  It is used to check if the card at the given index is the same.
	 * @return a boolean, true if the card located at index in the deck matches the target String, otherwise false
	 */
	public boolean checkCard(ArrayList<T> deck, int index, String target);
	
	/**
	 * flips the order of a sequence of cards, starting at the front of the deck until the lastIndex, such that the first card ends up in position lastIndex and the last card ends up in the first position
	 * @param deck an ArrayList<Card> representing a deck of cards or some subset of it
	 * @param lastIndex an int representing the index of the last card to be included in the sequence
	 */
	public void flipSequence(ArrayList<T> deck, int lastIndex);
	
	/**
	 * prints the contents of a deck of playing cards or some subset of it to the console
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 */
	public void displayDeck(ArrayList<T> deck);

}
