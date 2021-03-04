package fr.jest.model;

import java.util.ArrayList;
/**
 * Cette interface definie les méthodes à implémenter dans la Stategie des {@link VirtualPlayer}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see VirtualPlayer
 * @see Player
 */
public interface Strategy {
	/**
	 * cette methode doit définir la manière dont le joueur virtuel va faire sont offre
	 * @param myOffer L'offre du Joueur Virtuel
	 */
	public void makeOffer(Offer myOffer);
	/**
	 * Cette methode définie la manière avec laquelle le joueur virtuel prend une carte chez ses adversaires
	 * @param playerList la liste de tous les joueurs de la partie
	 * @param taker le joueur qui doit prendre une carte 
	 * @return l'indice du joueur chez qui la carte  a été prise dans la Liste des Joueurs
	 */
	public int takeCard (ArrayList<Player> playerList,Player taker); //RETURNS THE INDEX OF THE PLAYER WHO GOT HIS CARD TAKEN

}
