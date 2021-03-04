package fr.jest.model;

import java.util.Comparator;
/**
 * Cette Classe fournie une methode qui permet de comparer les offres de deux joueurs  en comparant les cartes visibles � l'aide d'un {@link CardComparator}<br>
 * 
 * Cette Methode est utile pour d�terminer l'ordre de passage des joueurs.
 * 
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 * @see CardComparator
 * @see RegularCard
 * @see Player
 * @see Offer
 */
public class OfferComparator implements Comparator<Offer> {
	private CardComparator cardComp= new CardComparator();
	/**
	 * cette methode permet de comparer deux Offres
	 * @return Cette Methode renvoie : <br><ul><li>Un entier n�gatif si la seconde offre vaut plus que la premi�re</li><li>Un entier positif si la Seconde Offre vaut moins que la premi�re</li><li>z�ro si les deux offres ont la m�me  valeur (ce qui en principe n'arrive jamais dan sune partie)</li></ul>
	 */
	@Override
	public int compare(Offer o1, Offer o2) {
		RegularCard o1VisCard= o1.getVisibleCard();
		RegularCard o2VisCard= o2.getVisibleCard();
		if(o1VisCard==null) {
			o1VisCard=new RegularCard(0, CardColour.JOKER, DeckStyle.DEFAULT);
		}
		if(o2VisCard==null) {
			o2VisCard=new RegularCard(0, CardColour.JOKER, DeckStyle.DEFAULT);

		}
		
		return cardComp.compare(o1VisCard, o2VisCard);
	}

}
