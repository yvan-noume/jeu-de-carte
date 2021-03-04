package fr.jest.model;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;




/**
 * Cette classe decrit La Pioche 
 * Le Jest est un jeu de 17 Cartes Jouables 
 * 
 * cette classe implemente le Pattern Singleton , un processus ne peut donc instancier qu'un seul objet de ce type à la fois 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see RegularCard
 * @see LinkedList
 * @see DeckStyle
 *
 */
public class Deck {
	private static Deck myDeck = null ;
	private static final int DECK_MAX = 17 ;
	private int numberofRemainingCards = Deck.DECK_MAX ;
	private DeckStyle myDeckStyle = DeckStyle.DEFAULT ;
	//private List<RegularCard> deckCards = new ArrayList<RegularCard>(Deck.DECK_MAX);
	private List<RegularCard> deckCards = new LinkedList<RegularCard>();

	private Iterator<RegularCard> cardIterator = null;
	/**
	 * Constructeur de la Pioche de la partie
	 * @param configDeckStyle le type de carte avec lequel on veut jouer
	 */
	private Deck(DeckStyle configDeckStyle) {

		this.numberofRemainingCards=17;
		if(configDeckStyle==null) {
			this.myDeckStyle=DeckStyle.DEFAULT;
		}
		
		//CREATION DES 16 CARTES NORMALES
		for(CardColour itColour : CardColour.values()) {
			if(!itColour.equals(CardColour.JOKER)) {
				for(int valueCount=1;valueCount<=4;valueCount++) {
					 deckCards.add(new RegularCard(valueCount, itColour, this.myDeckStyle));
				}
			}
		}
		//CREATION DU JOKER
		RegularCard joker =new RegularCard(0, CardColour.JOKER, this.myDeckStyle);
		joker.giveTrophyCond();
		deckCards.add(joker);
				
	}
	/**

	 * @param configDeckStyle le type de carte avec lequel on veut jouer
	 * @return 	 renvoie le reference sur un object de type deck si il y a un<br>
	 * Sinon cree une reference sur un objet de type Deck avant de la renvoyer <br>
	 */
	public static Deck getInstanceOfDeck(DeckStyle configDeckStyle) {
		if (myDeck==null) {
			myDeck = new Deck(configDeckStyle);
			myDeck.cardIterator = myDeck.deckCards.listIterator();
			return myDeck;
		}else {
			return myDeck;
		}
	}
	public RegularCard removeCard() {
		if(myDeck!=null && myDeck.numberofRemainingCards > 0) {
			myDeck.numberofRemainingCards--;
			return myDeck.deckCards.remove(0);
		}else if(myDeck.numberofRemainingCards==0) {
			System.out.println("[ERROR !!!] No More Card in the Deck");
			return null ;
		}else {
			System.out.println("[ERROR !!!] You must create a Deck first!!!");
			return null ;
		}
	}
	public void shuffleDeck() {
		
		Collections.shuffle(deckCards);
		
	}
	
	public void printDeck() { //DISPLAY THE CONTENT OF THE DECK
		System.out.println("[[ DeckStyle : "+this.myDeckStyle+"  ]]");
		ListIterator<RegularCard> li = deckCards.listIterator();
		while(li.hasNext()) {
			li.next().printCard();
		}
	}
	

	
	public int size() {
		if(myDeck!=null) {
			return myDeck.deckCards.size();
		}else {
			System.out.println("[ERROR !!!] You must create a Deck first!!!");
			return -1 ;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer result =  new StringBuffer(500);
		for(RegularCard card : deckCards) {
			result.append("\n"+card.toString());
		}
		return result.toString();
	}
	
	public boolean hasNext() {
		return myDeck.cardIterator.hasNext();
	}
	
	public RegularCard next() {
		return myDeck.cardIterator.next();
	}
	public List<RegularCard> getDeckCards() {
		return deckCards;
	}

	public static int getDeckMax() {
		return DECK_MAX;
	}
	
	public void reset() {
		myDeck=null;
	}
}
