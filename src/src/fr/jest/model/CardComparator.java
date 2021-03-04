package fr.jest.model;

import java.util.Comparator;
/** Comparateur de Cartes Jouables <br>
 *  cette Classe fournie une methode qui permet de comparer deux cartes  en fonction des critères définies dans les règles du jeu
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see RegularCard
 * @see CardColour
 * @see CardComparator#compare(RegularCard, RegularCard)
 * 
 *
 */
public class CardComparator implements Comparator<RegularCard> {
	/**
	 * 
	 * cette methode permet de Comparer deux Cartes en fonction de leur Famille d'abord puis en fonction de leur valeur si les deux cartes sont de la même famille
	 * 	
	 * @return un entier positif si la premier est plus forte que la seconde <BR> un entier negatif si la seconde est la plus forte <br> zéro si les deux cartes sont identiques (en termes d'attributs , pas de référence)
	 * 			
	 */
	@Override
	public int compare(RegularCard arg0, RegularCard arg1) {
		// TODO Auto-generated method stub
		
		if(arg0.getColour().equals(arg1.getColour()) && arg0.getValue() == arg1.getValue()) {
		return 0;
		}else if ( arg0.getValue() > arg1.getValue()) {
		return 1 ;
		}else if( arg0.getValue() < arg1.getValue()) {
		return -1;
		}else if (arg0.getValue()==arg1.getValue() && !arg0.getColour().equals(arg1.getColour())) {
			return arg0.getColour().compareTo(arg1.getColour());
		}else {
			System.out.println("[Error !!!] Unable to Compare Those CARDS");
			arg0.printCard();
			arg1.printCard();
			System.exit(-1);
			return 2;
		}
		
	}

}
