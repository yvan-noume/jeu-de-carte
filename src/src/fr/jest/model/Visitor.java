package fr.jest.model;
/**
 * cette interface implémentée par la {@link Party} fournie une methode qui permet de visiter tous les {@link Player} et de Calculer la valeur de leur {@link Jest} du {@link Player} visité
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see Player
 * @see Party
 */
public interface Visitor {
	/**
	 * Cette methode renvoie la valeur du {@link Jest} du {@link Player} calculée en fonction les règles de la {@link Party} définie dans le {@link JestEvalMethod} passé en Parametre
	 *<br> 
	 * @param evalMethod Les regles de la {@link Party}
	 * @param targetPlayer le {@link Player} à visiter
	 * @return la valeur du {@link Jest} du {@link Player} visité
	 */
	public int visit(JestEvalMethod evalMethod, Player targetPlayer); // USED THE EVAL THE JEST VALUE OF A PLAYER
}
