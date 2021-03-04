package fr.jest.model;
/**
 * <p>Enumeration des differents familles de carte
 * Le jest utilise un Jeu de 16 Cartes issues des familles HEARTS,CLUBS,DIAMONDS et SPADES
 * et une carte supplementaire qui sert de JOKER.
 * Ce qui fait un total de 17 Cartes Jouables
 * </p>
 * 
 *<p>
 *Les constantes de l'enumeration sont declarees par ordre de force.<br>
 *de la Famille la plus faible à la famille la plus forte on a :<BR>
 *JOKER<BR>
 *HEARTS<BR>
 *DIAMONDS<BR>
 *CLUBS<BR>
 *SPADES<BR>
 *</p>
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see RegularCard
 * @version 1.0
 * 
 * 
 *
 */
public enum CardColour {
	JOKER, //RESERVER POUR LE JOKER
	HEARTS,
	DIAMONDS,
	CLUBS,
	SPADES;
}
