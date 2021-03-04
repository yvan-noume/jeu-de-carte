package fr.jest.model;
/**
 * <p>
 * Cette Classe Abstraite permet de definir les règles de la partie c'est à dire comment le comptage des points s'opère en find de Partie 
 *</p>
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * 
 * @see DefaultRules
 * @see LigthRules
 * @see CustomRules
 * @see Party
 *
 */
public abstract class JestEvalMethod {
	/**
	 * ce membre assimilable a un boolean indique si oui ou nom on doit tenir compte de la regle faisant des carreaux des cartes a valeurs negatives.<br>
	 * 1 - Oui , appliquer la règle <br>
	 * 2 - Non ,  ne pas appliquer la règle<br>
	 */
	protected int negativeDiamonds = 1 ;
	
	/**
	 * ce membre assimilable a un boolean indique si oui ou nom on doit tenir compte de la regle suivante : "<ul> <li>Si le Joueur possede Le {@link CardColour#JOKER} et 1 , 2 ou 3 {@link CardColour#HEARTS} Alors la valeur du {@link CardColour#JOKER}  est de zero et tous les {@link CardColour#HEARTS} sont à points négatifs </li> <li>Si le Joueur possede Le {@link CardColour#JOKER} et aucun HEARTS , Le Joker vaut 4 points (valeur modifiable par l'utilisateur lors de la configuration de la Partie)</li>  <li>Si le Joueur possede Le {@link CardColour#JOKER} et tous les HEARTS alors , le Joker vaut 0 et les HEARTS sont a points positifs</li>  </ul>".<br>
	 * 1 - Oui , appliquer la règle <br>
	 * 2 - Non ,  ne pas appliquer la règle<br>
	 */
	protected int heartsAndJokerRule = 1 ;
	/**
	 * ce membre assimilable a un boolean indique si oui ou nom on doit tenir compte de la regle suivante : "Si le Joueur Possede un SPADES et un HEARTS de même valeur alors la valeur de sont se boit augmentée de deux unités".<br>
	 * 1 - Oui , appliquer la règle <br>
	 * 2 - Non ,  ne pas appliquer la règle<br>
	 */
	protected int blackPairRule = 1 ;
	/**
	 * ce membre permet de configurer la valeur du Joker dans le cas ou la règle entre le Joker et les HEARTS est appliquée<br>
	 * valeur min - 0 <br>
	 * valeur max - 5<br>
	 */
	protected int jokerValue = 4 ;
	/**
	 * ce membre assimilable a un boolean indique si oui ou nom on doit tenir compte de la regle suivante : "Si le Joueur possed un As et c'est la seule carte de sa famille  dans le Jest du Joueur , alors la valeur du l'As passe de 1 à 5".<br>  
	 * 1 - Oui , appliquer la règle <br>
	 * 2 - Non ,  ne pas appliquer la règle<br>
	 */
	protected int aceRule = 1 ;
	
	
	
	public int getNegativeDiamonds() {
		return negativeDiamonds;
	}
	public int getHeartsAndJokerRule() {
		return heartsAndJokerRule;
	}
	public int getBlackPairRule() {
		return blackPairRule;
	}
	public int getJokerValue() {
		return jokerValue;
	}
	public void setNegativeDiamonds(int negativeDiamonds) {
		this.negativeDiamonds = negativeDiamonds;
	}
	public void setHeartsAndJokerRule(int heartsAndJokerRule) {
		this.heartsAndJokerRule = heartsAndJokerRule;
	}
	public void setBlackPairRule(int blackPairRule) {
		this.blackPairRule = blackPairRule;
	}
	public void setJokerValue(int jokerValue) {
		this.jokerValue = jokerValue;
	}
	
	public int getAceRule() {
		return aceRule;
	}
	public void setAceRule(int aceRule) {
		this.aceRule = aceRule;
	}
	 public String toString() {
		 return "Diam_neg :: "+negativeDiamonds+"\nH&Joker :: "+heartsAndJokerRule+"\nblack_pair :: "+blackPairRule+"\nAce :: "+aceRule+"\nJ_Value :: "+jokerValue ;
	 }
	/**
	 * Cette methode permet d'evaluer la avaleur du Jest d'un joueur cible
	 * @param targetPlayer le joueur dont on souhaite évaluer la valeur du Jest
	 * @return la valeur du Jest du joueur cible
	 */
	public abstract int evalJest(Player targetPlayer) ;
	/**
	 * cette methodes permets de vérifier si le Jest du joueur contient des As éligibles pour la règle sur les As
	 * @param targetJest le joueur propietaire du Jest
	 */
	public abstract void checkAces(Jest targetJest);
	/**
	 * cette methode permet de vérifier si le Jest du joueur cible possede des paires de cartes noires
	 * @param targetJest le Joueur cible
	 * @return le nombre de paire de carte de couleur noires trouvé dans le Jest du Joueur cible
	 */
	public abstract int checkBlackPairs(Jest targetJest);
	/**
	 * Cette Methode permet de vérifier si le Jest du Joueur cible contient le Joker
	 * @param targetJest le joueur cible
	 * @return un boolean indiquant si oui ou non le Jest du Joueur possede le Joker
	 */
	public abstract boolean hasJoker(Jest targetJest);
	/**
	 * cette methode permet d'appliquer la regle entre le Joker et les HEARTS
	 * @param targetJest le joueur cible
	 * @param pHasJoker  un flag qui informe si le joueur possede le Joker ou Pas
	 */
	public abstract void checkJokerAndHearts(Jest targetJest,boolean pHasJoker);
	/**
	 * Cette methode permet de compter le nombre de Hearts presents dans le Jest du Joueur cible
	 * @param targetJest le joueur cile
	 * @return le nombre de HEARTS contenu dans le Jest du Joueur cible
	 */
	public abstract int countHearts(Jest targetJest);
	/**
	 * Cette methode permet de rendre la valeur de carreux négative chez le Joueur cible si la regle est appliquée
	 * @param targetJest le Joueur cible
	 */
	public abstract void setDiamondsValues(Jest targetJest);
}
