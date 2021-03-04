package fr.jest.model;

import java.util.ArrayList;
/**
 * Cette interface definie les m�thodes � impl�menter dans la Stategie des {@link VirtualPlayer}
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 * @see VirtualPlayer
 * @see Player
 */
public interface Strategy {
	/**
	 * cette methode doit d�finir la mani�re dont le joueur virtuel va faire sont offre
	 * @param myOffer L'offre du Joueur Virtuel
	 */
	public void makeOffer(Offer myOffer);
	/**
	 * Cette methode d�finie la mani�re avec laquelle le joueur virtuel prend une carte chez ses adversaires
	 * @param playerList la liste de tous les joueurs de la partie
	 * @param taker le joueur qui doit prendre une carte 
	 * @return l'indice du joueur chez qui la carte  a �t� prise dans la Liste des Joueurs
	 */
	public int takeCard (ArrayList<Player> playerList,Player taker); //RETURNS THE INDEX OF THE PLAYER WHO GOT HIS CARD TAKEN

}
