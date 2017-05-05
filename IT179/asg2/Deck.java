
import java.util.ArrayList;
import java.util.Iterator;

import poker.*;

/**
 * represents a standard deck of 52 playing cards using an ArrayList of Card objects
 * Extends the Pack abstract class to allow for data structures for playing cards.
 * Implements the Gameable interface, which extends the Deckable interface, to allow for basic playing
 * card operations and to play the 'find the aces' game as detailed in assignment 2.
 * 
 * @author jclomel
 * @param deck the ArrayList representation of a deck of cards
 * @param currentSuit an arrayList of all cards of the currently processing suit in relative order
 * @param suits a constant char[] with 4 chars, each one representing one of the suits of the deck
 */
public class Deck extends Pack<Card> implements Gameable<Card>, Iterable<Card>{
	
	private ArrayList<Card> currentSuit;
	private static final char[] suits = {'C', 'D', 'H', 'S'};

	/**
	 * Initialize a blank deck. Using this constructor forces a deck to generate and shuffle itself.
	 */
	public Deck() {
		super(suits, null);		//the ArrayList<Card> that the Pack parent object requires is generated below.  For now, it gets a placeholder.
		this.generate();
	}
	
	/**
	 * Initialize a deck, assigning an ArrayList of Cards to represent a deck of playing cards or some subset of it
	 * @param deck an ArrayList, represents a a deck of playing cards or some subset of it
	 */
	public Deck(ArrayList<Card> deck){
		super(suits, deck);
		//this.setDeck(deck);
	}

	/**
	 * play the 'find the aces' game as detailed in assignment 2, displaying the output to the console and labeling suits and number of reverses
	 * @param deck an ArrayList of Cards, representing a standard deck of playing cards
	 */
	@Override
	public void findAces(ArrayList<Card> deck) {
		//display a welcome message and create a counter for overall reversals
		System.out.println("Playing: \"Find the Aces\"\n");
		int overallCounter = 0;

		//run for ever char in suits, so in a standard deck of cards, 4 times
		for(char c:super.getSuits()){
			//determine the label and display it
			String label = "";
			switch(c){
			case 'C':
				label = "Clubs";
				break;
			case 'D':
				label = "Diamonds";
				break;
			case 'H':
				label = "Hearts";
				break;
			case 'S':
				label = "Spades";
				break;
			}
			System.out.println("Suit: " + label);
			
			//separate out the suit, set a counter for number of flips in the suit, and check if the first card is an ace.
			//then display the current suit, creating the first line of the run (the 'starting condition')
			getSuitRelativeOrder(c, deck);
			int counter = 0;
			boolean isAce = checkFirst(currentSuit, (c+"1"));
			displayDeck(currentSuit);
			
			//while an Ace is not in the first position
			while(!isAce){
				//get the kind of card in the first position in the deck as an int
				int kind = currentSuit.get(0).getKind();
				//flip the sequence of cards as defined by the first position of the deck and the card at the index indicated by the int above, inclusive of all cards in-between.
				//the first card will be at the index specified by the int above, and the last card in the first position, with all other cards swapping places with their mirror.
				flipSequence(currentSuit, kind-1);
				//increment the reversal counter
				counter++;
				//check if the first card is now an Ace
				isAce = checkFirst(currentSuit, (c+"1"));
				//if not, display the deck as it is now, post-flip
				if(!isAce){
					displayDeck(currentSuit);
				}
				
			}
			//once the Ace is in first position, display the last sequence, print a label for the reversal counter and display it
			if(counter!=0){		//don't display if the Ace generated at the front(counter==0), to avoid double-printing that sequence.
				displayDeck(currentSuit);
			}
			System.out.println(label + " reversed " + counter + " times.\n");
			//increment the overall counter by the amount in the suit counter
			overallCounter += counter;
		}
		//after all suits have run, display the number of reversals for the whole deck
		System.out.println("Total reversals for entire deck: " + overallCounter + "\n");
	}
	
	
	/**
	 * Flip a sequence of cards, as described in the Deckable Interface comments
	 * @param deck an ArrayList of Cards, representing a standard deck of playing cards
	 * @param lastIndex an int representing the index of the final card in the sequence to be flipped.  The first card will end up at this index, and the last card at the first index.
	 */
	@Override
	public void flipSequence(ArrayList<Card> deck, int lastIndex) {
		//create an iterator for the deck or portion of the deck supplied, and create a new blank ArrayList<Card> to hold the results of the flip
		Iterator<Card> sIter = suitIterator(deck);
		ArrayList<Card> newAl = new ArrayList<Card>();
		
		//fill up the new ArrayList<Card> with the contents of the supplied deck or portion of a deck
		for(int i = 0; i<deck.size(); i++){
			newAl.add(deck.get(i));
		}
		
		int counter = 0;				//begin at the head
		int seqPointer = lastIndex;		//begin at the tail
		//iterate through the ArrayList<Card>, assigning the current card starting at the head to the index specified by the seqPointer,
		//then moving the seqPointer backwards before moving on to the next element of the supplied ArrayList<Card>.
		while(sIter.hasNext()){
			if(counter <= lastIndex){
				newAl.set(seqPointer, (Card)sIter.next());
				seqPointer--;
			}
			else{
				newAl.set(counter, (Card)sIter.next());
			}
			counter++;
		}
		//once the whole supplied deck has been iterated through, set the Deck object's currentSuit to the new ArrayList<Card>
		//populated with the flipped sequence of cards from the supplied deck.
		currentSuit = newAl;
	}
	

	/**
	 * generate and shuffle a new deck of playing cards
	 */
	@Override
	public ArrayList<Card> generate() {
		PokerDeck pd = new PokerDeck();
		pd.shuffle();
		ArrayList<Card> pdAl = pd.toArrayList();
		super.setDeck(pdAl);
		System.out.println("New deck generated and shuffled.\n" + 
							"Number of Cards in Deck: " + getDeck().size() + "\n" +
							"Number of suits in Deck: " + super.getSuits().length + "\n\n" +
							"Order of shuffled deck:");
		displayDeck(getDeck());
		System.out.println("\n");
		return pdAl;
	}
	
	/**
	 * create an ArrayList of Cards representing all of the cards in the deck of a particular suit in relative order
	 * @return an ArrayList<Card> representing all cards of one suit in relative order to their original positions in the deck
	 * @param suit a char, the letter representing the suit of cards to divide out from the deck
	 * @param deck an ArrayList<Card> representing a standard 52 card deck of playing cards
	 */
	
	@Override
	public ArrayList<Card> getSuitRelativeOrder(char suit, ArrayList<Card> deck) {
		//create a blank ArrayList<Card> to represent the suit to be pulled out
		currentSuit = new ArrayList<Card>();
		//for each card in the supplied deck, if the card has the same char for suit as the char supplied, add it to the new ArrayList<Card>
		for(Card c:deck){
			if(c.getSuit() == suit){
				currentSuit.add(c);
			}
		}
		
		return currentSuit;
	}
	
	/**
	 * get the first card in an ArrayList<Card> representing a deck of playing cards
	 * @return a Card representing the first card on top of the deck
	 * @param deck an ArrayList<Card> representing a deck of cards or some subset of it
	 */
	
	@Override
	public Card getFirst(ArrayList<Card> deck) {
		return deck.get(0);
	}
	
	/**
	 * accessor for the ArrayList<Card> representing the deck
	 */
	
	public ArrayList<Card> getDeck() {
		return super.getDeck();
	}

	/**
	 * mutator for the ArrayList<Card> deck representation
	 */
	public void setDeck(ArrayList<Card> deck) {
		super.setDeck(deck);
	}
	
	/**
	 * prints a deck of cards or some subset of it to the console, with 'pretty' formatting
	 * @param  deck an ArrayList<Card> representing a deck of cards or some subset of it
	 */
	@Override
	public void displayDeck(ArrayList<Card> deck) {
		int counter = 0;

		for(Card c:deck){
			if(counter >= 13){
				counter = 0;
				System.out.print("\n");
			}
			System.out.printf("%-4s", c.toString());
			counter++;
		}
		System.out.print("\n");
	}
	
	/**
	 * returns an Iterator for the ArrayList<Card> representing the whole deck of cards
	 * @return an Iterator for the ArrayList<Card> representing the whole deck of cards
	 */
	
	@Override
	public Iterator<Card> iterator() {
		return getDeck().iterator();
	}
	
	/**
	 * returns an Iterator for the ArrayList<Card> supplied
	 * @param suit the ArrayList<Card> to get an Iterator for
	 * @return an Iterator for the ArrayList<Card> supplied
	 */
	
	public Iterator<Card> suitIterator(ArrayList<Card> suit){
		return suit.iterator();
	}
	
	/**
	 * checks to see if a card in an ArrayList<Card> at an index has the same suit and type as the target String
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 * @param index an integer, the index of the card to check
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  It is used to check if the card at the given index is the same
	 * @return a boolean, true if the card at the supplied index matches the target String, otherwise false
	 */

	@Override
	public boolean checkCard(ArrayList<Card> deck, int index, String target) {
		boolean r;
		char suit = deck.get(index).getSuit(); 
		int kind = deck.get(index).getKind();
		String label = Character.toString(suit) + Integer.toString(kind) ;
		
		if(target.equals(label)){
			r = true;
		}
		else{
			r = false;		
		}
		return r;
	}
	
	/**
	 * checks the first card in an ArrayList<Card> to see if it has the same suit and type as the target String
	 * @param deck an ArrayList<Card> represeting a deck of playing cards or some subset of it
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  It is used to check if the card at the given index is the same
	 * @return a boolean, true if the first card matches the suit and type specified by the target String, otherwise false
	 */

	@Override
	public boolean checkFirst(ArrayList<Card> deck, String target) {
		boolean r;
		char suit = deck.get(0).getSuit(); 
		int kind = deck.get(0).getKind();
		String label = Character.toString(suit) + Integer.toString(kind) ;
		
		if(target.equals(label)){
			r = true;
		}
		else{
			r = false;		
		}
		return r;

	}
	
	/**
	 * returns a Card object located at the index in an ArrayList<Card>
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 * @param index an integer, the index of the card to return
	 * @return a Card object, representing a playing card located at the index in a deck of cards
	 */
	
	@Override
	public Card getCard(ArrayList<Card> deck, int index) {
		return deck.get(index);
	}
	
	/**
	 * returns a Card object located at the index in an ArrayList<Card>
	 * @param deck an ArrayList<Card> representing a deck of playing cards or some subset of it
	 * @param target a String, in the format "ST" where s is a char representing the suit and T is an int representing the type.  The card with the matching suit and type will be returned.
	 * @return a Card object, representing a playing card located at the index in a deck of cards
	 */
	
	@Override
	public Card getCard(ArrayList<Card> deck, String target) {
		int index = deck.indexOf(target);
		return deck.get(index);
	}

	

}
