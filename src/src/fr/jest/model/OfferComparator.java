package fr.jest.model;

import java.util.Comparator;
/**
 * Cette Classe fournie une methode qui permet de comparer les offres de deux joueurs  en comparant les cartes visibles à l'aide d'un {@link CardComparator}<br>
 * 
 * Cette Methode est utile pour déterminer l'ordre de passage des joueurs.
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see CardComparator
 * @see RegularCard
 * @see Player
 * @see Offer
 */
public class OfferComparator implements Comparator<Offer> {
	private CardComparator cardComp= new CardComparator();
	/**
	 * cette methode permet de comparer deux Offres
	 * @return Cette Methode renvoie : <br><ul><li>Un entier négatif si la seconde offre vaut plus que la première</li><li>Un entier positif si la Seconde Offre vaut moins que la première</li><li>zéro si les deux offres ont la même  valeur (ce qui en principe n'arrive jamais dan sune partie)</li></ul>
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
