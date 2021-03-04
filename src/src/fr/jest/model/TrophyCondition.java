package fr.jest.model;
/**
 * Cette Enumeration liste toutes le conditions d'attribution de {@link Trophies} possible
 *  <ul>
 *  
 *  <li>HASJOKER : Le Joueur ayant le Joker dans son {@link Jest} remporte le {@link Trophies}<br></li>
 *<li>MAJORITY_2 : Le Joueur ayant le plus de {@link RegularCard} avec la valeur "2" dans son {@link Jest} remporte le {@link Trophies}<br></li>
	*<li>MAJORITY_3 : Le Joueur ayant le plus de {@link RegularCard} avec la valeur "3" dans son {@link Jest} remporte le {@link Trophies}<br></li>
	*<li>MAJORITY_4 : Le Joueur ayant le plus de {@link RegularCard} avec la valeur "4" dans son {@link Jest} remporte le {@link Trophies}<br></li>
	*<li>BESTJEST : Le Joueur ayant le {@link Jest} de plus grande Valeur remporte le {@link Trophies}<br></li>
	*<li>BESTJESTNOJOKER  : Le Joueur ayant le {@link Jest} de plus grande Valeur remporte sans prende en compte le {@link CardColour#JOKER} le {@link Trophies}<br></li>
	*<li>HIGHEST_CLUBS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#CLUBS} avec la valeur la plus haute remporte le {@link Trophies}<br></li>
	*<li>LOWEST_CLUBS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#CLUBS} avec la valeur la plus basse remporte le {@link Trophies}<br></li>
	*<li>HIGHEST_SPADES : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#SPADES} avec la valeur la plus haute remporte le {@link Trophies}<br></li>
	*<li>LOWEST_SPADES  : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#SPADES} avec la valeur la plus basse remporte le {@link Trophies}<br></li>
	*<li>HIGHEST_HEARTS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#HEARTS} avec la valeur la plus haute remporte le {@link Trophies}<br></li>
	*<li>LOWEST_HEARTS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#HEARTS} avec la valeur la plus basse remporte le {@link Trophies}<br></li>
	*<li>HIGHEST_DIAMONDS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#DIAMONDS} avec la valeur la plus haute remporte le {@link Trophies}<br></li>
	*<li>LOWEST_DIAMONDS : Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#DIAMONDS} avec la valeur la plus basse remporte le {@link Trophies}<br></li>
 * </ul>
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see RegularCard
 * @see Deck
 *
 * 
 *
 */
public enum TrophyCondition {
	/**
	 *  Le Joueur ayant le Joker dans son {@link Jest} remporte le {@link Trophies}
	 */
	HASJOKER,
	/**
	 *  Le Joueur ayant le plus de {@link RegularCard} avec la valeur "2" dans son {@link Jest} remporte le {@link Trophies}
	 */
	MAJORITY_2,
	/**
	 * Le Joueur ayant le plus de {@link RegularCard} avec la valeur "3" dans son {@link Jest} remporte le {@link Trophies}
	 */
	MAJORITY_3,
	/**
	 * Le Joueur ayant le plus de {@link RegularCard} avec la valeur "4" dans son {@link Jest} remporte le {@link Trophies}
	 */
	MAJORITY_4,
	/**
	 * Le Joueur ayant le {@link Jest} de plus grande Valeur remporte le {@link Trophies}
	 */
	BESTJEST,
	/**
	 * Le Joueur ayant le {@link Jest} de plus grande Valeur remporte sans prende en compte le {@link CardColour#JOKER} le {@link Trophies}
	 */
	BESTJESTNOJOKER,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#CLUBS} avec la valeur la plus haute remporte le {@link Trophies}
	 */
	HIGHEST_CLUBS,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#CLUBS} avec la valeur la plus basse remporte le {@link Trophies}
	 */
	LOWEST_CLUBS,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#SPADES} avec la valeur la plus haute remporte le {@link Trophies}
	 */
	HIGHEST_SPADES,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#SPADES} avec la valeur la plus basse remporte le {@link Trophies}
	 */
	LOWEST_SPADES,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#HEARTS} avec la valeur la plus haute remporte le {@link Trophies}
	 */
	HIGHEST_HEARTS,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#HEARTS} avec la valeur la plus basse remporte le {@link Trophies}
	 */
	LOWEST_HEARTS,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#DIAMONDS} avec la valeur la plus haute remporte le {@link Trophies}
	 */
	HIGHEST_DIAMONDS,
	/**
	 * Le Joueur ayant la {@link RegularCard} de la Famille {@link CardColour#HEARTS} avec la valeur la plus basse remporte le {@link Trophies}
	 */
	LOWEST_DIAMONDS;
}
