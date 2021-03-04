package fr.jest.model;
/**
 * cette interface impl�ment�e par la {@link Party} fournie une methode qui permet de visiter tous les {@link Player} et de Calculer la valeur de leur {@link Jest} du {@link Player} visit�
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 * @see Player
 * @see Party
 */
public interface Visitor {
	/**
	 * Cette methode renvoie la valeur du {@link Jest} du {@link Player} calcul�e en fonction les r�gles de la {@link Party} d�finie dans le {@link JestEvalMethod} pass� en Parametre
	 *<br> 
	 * @param evalMethod Les regles de la {@link Party}
	 * @param targetPlayer le {@link Player} � visiter
	 * @return la valeur du {@link Jest} du {@link Player} visit�
	 */
	public int visit(JestEvalMethod evalMethod, Player targetPlayer); // USED THE EVAL THE JEST VALUE OF A PLAYER
}
