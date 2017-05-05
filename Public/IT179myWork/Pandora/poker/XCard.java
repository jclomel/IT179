package poker;

/**
 * a Card object that is Comparable.  XCard implements the Comparable<T> interface and overrides the default equals() method
 * 
 * @author jclomel
 */
public class XCard extends Card implements Comparable<XCard>{

	public XCard(char suit, int kind) {
		super(suit, kind);
	}

	/**
	 * Compares this XCard and another XCard based on suit and value
	 * 
	 * @param x the XCard you will compare this XCard to
	 * @return an int, 1 if the other card is greater, -1 if this card is, and 0 if they are equal
	 */
	@Override
	public int compareTo(XCard x) {
		// TODO test this to make sure the right int is being returned
		if(getSuit() < x.getSuit()) return -1;				//the other card is higher in alphabetical order
		else if(getSuit() > x.getSuit()) return 1;			//this card is higher in alphabetical order
		else{												//this card and the other card have the same suit, check the values
			if(getKind() < x.getKind()) return -1;			//the other card is a larger value
			else if(getKind() > x.getKind()) return 1;		//this card is a larger value
			else return 0;									//this card and the other card are the same suit and value
		}
		
	}
	
	/**
	 * returns true if this card and the other card have the same suit and value
	 * 
	 * @param o the other card.  Set as an object to override the default equals method, and cast to an XCard as necessary
	 * @return a boolean, true if this card and the other card have the same suit and value
	 */
	public boolean equals(Object o){
		boolean value = false;
		if(compareTo((XCard) o) == 0) value=true;
		return value;
		
	}

}
