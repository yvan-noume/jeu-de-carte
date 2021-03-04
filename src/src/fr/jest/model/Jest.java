package fr.jest.model;
//TODO WRITE THE METHOD evalJest
// TODO WHAT IF I WANT TO ADD MORE THAN  07 CARDS IN MY JEST //IN addCard();

import java.util.*;
/**
 * Cette Classe permet de décrire une collection de Cartes qui forment le Jest d'un Joueur<br>
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see Player 
 *@see RegularCard
 *@see ArrayList
 */
public class Jest {
	/**
	 * Il s'agit du nombre maximum de cartes que peut contenir un Jest , ce membre permet de controler la cohérence du modèle
	 * 
	 */
	public static   int maxJestCards  ;

	private int nextCardIndex ;
	private int jestValue ; //THE VALUE OF MY JEST
	private List<RegularCard> myJestCards= new ArrayList<RegularCard>(Jest.maxJestCards); 
	private final Player owner ; // THE OWNER OF THE JEST

	
	public Jest(Player pOwner) {
		maxJestCards = ((int)Math.ceil((Deck.getDeckMax()-Party.getNumberOfCardsInTrophies())/Party.getGameNumberOfPlayer()))+Party.getNumberOfCardsInTrophies() ;	
		this.jestValue=0;
		this.nextCardIndex=0;
		this.owner=pOwner;
		
	}
	
	
	
	public  void addCard(RegularCard newCard) {
		// TODO WHAT IF I WANT TO ADD MORE THAN  07 CARDS IN MY JEST
		if(this.nextCardIndex<Jest.maxJestCards) {
		this.myJestCards.add(this.nextCardIndex,newCard);
		//this.myJestCards[this.nextCardIndex]=newCard;
		this.nextCardIndex++;
		}else {
			System.out.println("[ERROR !!!] "+this.owner.getNickname()+"'s Jest Cannot take More Than "+Jest.maxJestCards+" Cards !!!");
		}
	}
	
	public  void addCard(Deck deck) {
		// TODO WHAT IF I WANT TO ADD MORE THAN  07 CARDS IN MY JEST
		if(this.nextCardIndex<Jest.maxJestCards) {
		this.myJestCards.add(this.nextCardIndex, deck.removeCard());
		//this.myJestCards[this.nextCardIndex]=deck.removeCard();
		this.nextCardIndex++;
		}else {
			System.out.println("[ERROR !!!] "+this.owner.getNickname()+"'s Jest Cannot take More Than "+Jest.maxJestCards+" Cards !!!");
		}
	}
	//TODO WRITE THE METHOD evalJest
	
	/**
	 * DISPLAY THE CONTENT OF THE Jest On StdOut
	 */
	public void printJest() { 
		String playerType="";
		if(this.owner instanceof VirtualPlayer) {
			playerType="--VIRTUAL-- ";
		}
		System.out.println("[[ "+playerType+this.owner.getName()+"'S JEST   ]]");
		if( this.myJestCards.isEmpty()==false) {
			for(RegularCard card : this.myJestCards) {
				card.setChown(true);
				card.printCard();
			}
		}else {
			System.out.println(this.owner.getName()+"'S JEST IS EMPTY !!! ");
		}
		
	}
	/**
	 * @return un  String contenant toutes les informations (rendues visibles au prealable) sur toutes les cartes  de ce Jest
	 */
	public String toString() {
		StringBuffer result =  new StringBuffer(500);
		
		String playerType="";
		if(this.owner instanceof VirtualPlayer) {
			playerType="--VIRTUAL-- ";
		}
		result.append("[[ "+playerType+this.owner.getName()+"'S JEST   ]]\n");
		if( this.myJestCards.isEmpty()==false) {
			for(RegularCard card : this.myJestCards) {
				card.setChown(true);
				result.append("\n"+card.toString());
			}
		}else {
			result.append(this.owner.getName()+"'S JEST IS EMPTY !!! ");
		}
		
		return result.toString();
	}
	
	/**
	 * Cette methode realise une copie profonde de ce Jest dans une autre réference d'objet de type Jest
	 * <br>En effet , cette methode copie itérarivement toutes les cartes du Jest dans une nouvelle référence sur un Jest
	 * 
	 * @return une copie profonde du jest appelant cette methode
	 */
	public Jest cloneJest() {
		Jest clone = new Jest(this.owner) ;
		clone.nextCardIndex=this.nextCardIndex;
		clone.jestValue=this.jestValue;
		for(int k = 0 ; k<this.myJestCards.size();k++) {
			clone.myJestCards.add(k,this.myJestCards.get(k).cloneCard());
		}
		
		return clone ;
	}

	public RegularCard getMyJestCards(int index) {
		return myJestCards.get(index);
	}
	
	

	public List<RegularCard> getMyJestCards() {
		return myJestCards;
	}

	public int getJestValue() {
		return jestValue;
	}

	public void setJestValue(int jestValue) {
		this.jestValue = jestValue;
	}


	
}
