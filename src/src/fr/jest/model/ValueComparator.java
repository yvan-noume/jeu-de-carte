package fr.jest.model;

import java.util.Comparator;
import java.util.Map;
/**
 * Cette classe fourni un autre comparateur de {@link Player} . On compare les {@link Player} en fonction de leur Score (La valeur de Leur {@link Jest}) en fin de Partie afin de Determiner le vainqueur de la {@link Party}
 * les joueurs et leur score respectifs sont fournis dans un Map, et c'est cette Map qui fait l'objet d'un Trie.
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see Party
 * @see Jest 
 * @see Player
 * @see Comparator
 */
public class ValueComparator implements Comparator<Player> {
	/**
	 * 
	 *  pBase La map doit contenir les {@link Player } et leur score respectif
	 */
	private Map<Player, Integer> base ;
	/**
	 * 
	 * @param pBase La map contenant les {@link Player } et leur score respectif
	 */
	public ValueComparator(Map<Player, Integer> pBase) {
		this.base=pBase;
	}
	/**
	 * constructeur par defaut
	 */
	public ValueComparator() {
		
	}
	/**
	 * Voir les spécification de la methode dans la documentation de {@link Comparator#compare(Object, Object)}
	 */
	@Override
	public int compare(Player arg0, Player arg1) {
			if (base.get(arg0) > base.get(arg1)) {
	            return -1;
	        } else if (base.get(arg0) < base.get(arg1)) {
	            return 1;
	        }else {
	        	return arg0.getName().compareToIgnoreCase(arg1.getName());
	        }
		}

	public void setBase(Map<Player, Integer> base) {
		this.base = base;
	}
	
	

}
