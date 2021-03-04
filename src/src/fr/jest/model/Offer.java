package fr.jest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fr.jest.graphic.PlayerSpot;

/**
 * Cette classe d�crit la main d'un {@link Player}
 * <br>
 * Elle �tend la classe {@link Observable} : en effet un {@link Offer} est observ� par un {@link PlayerSpot} ( impl�mentation du patron MVC)
 * <br>
 * un Offer est Caract�ris� par :
 * <br>
 * <ul>
 * <li>Une paire de {@link RegularCard}</li>
 * <li>Un {@link Player} qui est son propr�taire.</li>
 * </ul>
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 *@see RegularCard
 *@see Player
 */
public class Offer extends Observable {
	/**
	 * Le Nombre de Carte max dans la main d'un {@link Player} tel que d�finie dans les r�gles du Jest
	 */
	private static final int OFFER_MAX_SIZE=2;
	/**
	 * Le propri�taire du {@link Offer}
	 */
	private final Player owner ;
	/**
	 * La liste des {@link RegularCard} du {@link Offer}
	 */
	private List<RegularCard> offerCards ;
	/**
	 * Un {@link Iterator} permettant de parcourir la liste de {@link RegularCard} du {@link Offer}
	 */
	private Iterator<RegularCard> offerIterator ;
	/**
	 * champs Non utile
	 */
	private int offerCardIndex = 0 ;
	/**
	 * Un comparateur pour les cartes du {@link Offer}
	 */
	private CardComparator offeComparator = new CardComparator();
	/**
	 * Constructeur 
	 * @param pOwner le propi�taire du {@link Offer}
	 */
	public Offer( Player pOwner) {
		this.offerCards =  new ArrayList<RegularCard>() ;
		this.offerIterator = this.offerCards.listIterator();
		this.offerCardIndex=0;
		this.owner=pOwner;
	}
	/**
	 * Ajoute une {@link RegularCard} � la main du {@link Player}
	 * @param newCard la R�f�rence sur la {@link RegularCard} � ajouter
	 */
	public  void  addCard(RegularCard newCard) {
		if(this.offerCardIndex<Offer.OFFER_MAX_SIZE) {
			this.offerCards.add(newCard);
			this.offerCardIndex++;
			setChanged();
			notifyObservers(this);
		}else {
			System.out.println("[!!!]Error , "+this.owner.getName()+"'S OFFER CANNOT TAKE MORE CARDS !");
		}
	}
	/**
	 * retirer une {@link RegularCard} du {@link Deck} et l'ajouter dans l' {@link Offer} du {@link Player}
	 * @param deck le pioche de la {@link Party}
	 */
	public void  addCard(Deck deck) {
		if(this.offerCardIndex<Offer.OFFER_MAX_SIZE) {
			this.offerCards.add(deck.removeCard());
			this.offerCardIndex++;

			setChanged();
			notifyObservers(this);
		}else {
			System.out.println("[!!!]Error , "+this.owner.getName()+"'S OFFER CANNOT TAKE MORE CARDS !");
		}
	}
	/**
	 * retire la carte � la position 0 du {@link Offer} du {@link Player} 
	 * @return une R�f�rence sur la {@link RegularCard} qui a �t� retir�e
	 */
	public  RegularCard giveCard() {
		//REMOVE THE  CARD OF THE LOWEST INDEX IN THE TABLE and decrease all indexes by one
		
		if( this.size() <=0 ) {
			System.out.println("[!!!]Error , "+this.owner.getName()+"'S OFFER IS EMPTY !");
			return null;
		}else {
			this.offerCardIndex--;
			RegularCard poppedCard = this.offerCards.get(0);
			this.offerCards.remove(0);
			setChanged();
			notifyObservers(this);
			return poppedCard ;
		}
	}
	/**
	 * retire la carte � la position indiqu�e du {@link Offer} du {@link Player} 
	 * 
	 * @param offerPosition la position de la {@link RegularCard}  � retirer
	 * @return une R�f�rence sur la {@link RegularCard} qui a �t� retir�e
	 */
	public  RegularCard giveCard(int offerPosition){ // SWITCH THE WANTED CARD TO INDEX 0 SO THAT IT WILL BE REMOVED BY giveCard()
		if( this.size() <=0 ) {
			System.out.println("[!!!]Error , "+this.owner.getName()+"'S OFFER IS EMPTY !");
			return null;
		}else {
			this.offerCardIndex--;
			RegularCard poppedCard = this.offerCards.get(offerPosition);
			this.offerCards.remove(offerPosition);
			setChanged();
			notifyObservers(this);
			return poppedCard ;
		}
	}
	/**
	 * retire une carte du {@link Offer} du {@link Player}
	 * @param card la {@link RegularCard} � retirer du {@link Offer}
	 * @return une r�f�rence sur la {@link RegularCard} qui a �t� retir�e
	 */
	public  RegularCard giveCard(RegularCard card){ // SWITCH THE WANTED CARD TO INDEX 0 SO THAT IT WILL BE REMOVED BY giveCard()
		int offerPosition = 0 ;
		if( this.size() <=0 ) {
			System.out.println("[!!!]Error , "+this.owner.getName()+"'S OFFER IS EMPTY !");
			return null;
		}else {
			this.offerCardIndex--;
			for(int k = 0 ; k < this.offerCards.size();k++) {
				if(this.offerCards.get(k).equals(card)) {
					offerPosition=k;
					break;
				}
			}
			
			RegularCard poppedCard = this.offerCards.get(offerPosition);
			this.offerCards.remove(offerPosition);
			setChanged();
			notifyObservers(this);
			return poppedCard ;
		}
	}
	/**
	 * retire la carte visible du {@link Offer} du {@link Player}
	 * @return une r�f�rence sur la {@link RegularCard} visible du {@link Player}
	 */
	public  RegularCard giveVisibleCard(){
		int visibleCardIndex =0 ;
		boolean found = false ;
			for (int k =0 ; k<this.offerCards.size();k++) {
					if(this.offerCards.get(k).isChown()) {
						visibleCardIndex=k;
						found=true;
						break;
					}
			}
		
			if(found) {
				return this.giveCard(visibleCardIndex) ;
			}else {
				return null;
			}
	}
	/**
	 * retire la carte face cach�e du {@link Offer} du {@link Player}
	 * @return une r�f�rence sur la {@link RegularCard} face cach�e du {@link Player}
	 * 
	 */
	public  RegularCard giveHiddenCard(){
		int HiddenCardIndex =0 ;
		boolean found = false ;
			for (int k =0 ; k<this.offerCards.size();k++) {
					if(!this.offerCards.get(k).isChown()) {
						HiddenCardIndex=k;
						found=true;
						break;
					}
			}
		
			if(found) {
				return this.giveCard(HiddenCardIndex) ;
			}else {
				return null;
			}
	}
	/**
	 * renvoie une r�f�rence sue la carte � la position indiqu�e du {@link Offer} du {@link Player}  sans la retirer du {@link Offer}
	 * @param offerPosition l'indice de la {@link RegularCard} � renvoyer
	 * @return une R�f�rence sur la {@link RegularCard}
	 */
	public RegularCard getCard(int offerPosition) {
		return this.offerCards.get(offerPosition);
	}
	/**
	 * renvoie une r�f�rence sue la carte � la position 0 du {@link Offer} du {@link Player}  sans la retirer du {@link Offer}
	 * @return une R�f�rence sur la {@link RegularCard}
	 */
	public RegularCard getCard() {
		return this.offerCards.get(0);
	}
	/**
	 * renvoie une r�f�rence sur la carte face cach�e du {@link Offer} du {@link Player} sans la retirer du {@link Offer}
	 * @return une r�f�rence sur la {@link RegularCard} face cach�e du {@link Player}
	 * 
	 */
	public RegularCard getHiddenCard() {
		int hiddenCardIndex =0 ;
		boolean found = false ;
		for (int k =0 ; k<this.offerCards.size();k++) {
			if(!this.offerCards.get(k).isChown()) {
				hiddenCardIndex=k;
				found=true;
				break;
			}
	}
		if(found) {
			return this.getCard(hiddenCardIndex) ;
		}else {
			return null;
		}
	}
	/**
	 * renvoie une r�f�rence sur la carte visible du {@link Offer} du {@link Player} sans la retirer du {@link Offer}
	 * @return une r�f�rence sur la {@link RegularCard} visible du {@link Player}
	 * 
	 */
	public RegularCard getVisibleCard() {
		int visibleCardIndex =0 ;
		boolean found = false ;
		for (int k =0 ; k<this.offerCards.size();k++) {
			if(this.offerCards.get(k).isChown()) {
				visibleCardIndex=k;
				found=true;
				break;
			}
	}
		if(found) {
			return this.getCard(visibleCardIndex) ;
		}else {
			return null;
		}
	}
	/**
	 * renvoie l'indice de  la carte visible  du {@link Offer} du {@link Player} sans la retirer du {@link Offer}
	 * @return un entier qui repr�sente l'indice de la {@link RegularCard} visible du {@link Player}
	 * 
	 */
	public int getVisibleCardIndex() {
		int visibleCardIndex =0 ;
		boolean found = false ;
		for (int k =0 ; k<this.offerCards.size();k++) {
			if(this.offerCards.get(k).isChown()) {
				visibleCardIndex=k;
				found=true;
				break;
			}
	}
		if(found) {
			return visibleCardIndex ;
		}else {
			return -1;
		}
	}
	/**
	 * renvoie l'indice de  la carte face cach�e  du {@link Offer} du {@link Player} sans la retirer du {@link Offer}
	 * @return un entier qui repr�sente l'indice de la {@link RegularCard} face cach�e du {@link Player}
	 * 
	 */
	public int getHiddenCardIndex() {
		int hiddenCardIndex =0 ;
		boolean found = false ;
		for (int k =0 ; k<this.offerCards.size();k++) {
			if(!this.offerCards.get(k).isChown()) {
				hiddenCardIndex=k;
				found=true;
				break;
			}
	}
		if(found) {
			return hiddenCardIndex ;
		}else {
			return -1;
		}
	}
	/**
	 * renvoie une r�f�rence  sur la meilleure {@link RegularCard} du {@link Offer} 
	 * @return  r�f�rence  sur la meilleure {@link RegularCard} du {@link Offer}
	 */
	public RegularCard getBestCard() {
		if(this.size()>1) {
		if(this.offeComparator.compare(this.offerCards.get(0), this.offerCards.get(1))>0) {
			return this.getCard(0) ;
		}else {
			return this.getCard(1);
		}}else {
			return this.getCard(0) ;
		}
			
	}
	/**
	 * renvoie une r�f�rence  sur la pire {@link RegularCard} du {@link Offer} 
	 * @return  r�f�rence  sur la pire {@link RegularCard} du {@link Offer}
	 */
	public RegularCard getWorstCard() {
		if(this.size()>1) {
		if(this.offeComparator.compare(this.offerCards.get(0), this.offerCards.get(1))<0) {
			return this.getCard(0) ;
		}else {
			return this.getCard(1);
		}
		}else {
			return this.getCard(0) ;
		}
	}
	/**
	 * permet de cacher la {@link RegularCard} � l'indice offerPosition dans le {@link Offer} du {@link Player}
	 * @param offerPosition l'indice de la carte � cacher
	 */
	public void hideCard(int offerPosition) {
		this.offerCards.get(offerPosition).setChown(false);
		this.offerCards.get(offerPosition).setCardName();
		setChanged();
		notifyObservers(this);

	}
	/**
	 * permet de d�voiler la {@link RegularCard} � l'indice offerPosition dans le {@link Offer} du {@link Player}
	 * @param offerPosition l'indice de la carte � d�voiler
	 */
	public void showCard(int offerPosition) {
		this.offerCards.get(offerPosition).setChown(true);
		this.offerCards.get(offerPosition).setCardName();
		setChanged();
		notifyObservers(this);
	}
	/**
	 * Afficher sur la sortie standard les cartes de l'offre 
	 */
	public void printOffer() { //DISPLAY THE CONTENT OF THE OFFER
		String playerType="";
		int cardPos = 0 ;
		if(this.owner instanceof VirtualPlayer) {
			playerType="--VIRTUAL-- ";
		}
		System.out.println("[[ "+playerType+this.owner.getName()+"'S OFFER   ]]");
		for(RegularCard card : this.offerCards) {
			if(card!=null) {
			System.out.print("\t\t:: Card #0"+cardPos+"-->");
			card.printCard();
			cardPos++;
			}
		}
	}
	/**
	 * renvoie les infos sur les {@link RegularCard} du {@link Offer} sous forme de {@link String}
	 * @return une {@link String} contenant les infos sur les {@link RegularCard} de l' offer
	 */
	public String toString() {
		StringBuffer result =  new StringBuffer(100);
		
		String playerType="";
		if(this.owner instanceof VirtualPlayer) {
			playerType="--VIRTUAL-- ";
		}
		result.append("[[ "+playerType+this.owner.getName()+"'S OFFER   ]]\n");
		if( this.offerCards.isEmpty()==false) {
			for(RegularCard card : this.offerCards) {
				result.append(card.toString()+"\n");
			}
		}else {
			result.append(this.owner.getName()+"'S OFFER IS EMPTY !!! ");
		}
		
		return result.toString();
	}
	/**
	 * renvoie le nombre de {@link RegularCard} dans ce {@link Offer}
	 * @return le nombre de {@link RegularCard} dans ce {@link Offer}
	 */
	public int size() {
		return offerCards.size();
	}
	/**
	 * permet de d�voiler toutes les cartes de cette {@link Offer}
	 */
	public void showAllCards() {
		for(RegularCard card : this.offerCards) {if(card!=null) {
			card.setChown(true);
			card.setCardName();

		}}
		setChanged();
		notifyObservers(this);
	}
	/**
	 * permet de cacher toutes les cartes de cette {@link Offer}
	 */
	public void hideAllCards() {
		for(RegularCard card : this.offerCards) {if(card!=null) {
			card.setChown(false);
			card.setCardName();

		}}
		setChanged();
		notifyObservers(this);
	}
	public void setChanged() {
		super.setChanged();
	}
	
	public ArrayList<RegularCard> getOfferCards() {
		return (ArrayList<RegularCard>)offerCards;
	}

	public Iterator<RegularCard> getOfferIterator() {
		return offerIterator;
	}
	

	
	
	
}
