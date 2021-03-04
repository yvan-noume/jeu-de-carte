package fr.jest.model;
/**<p>
 * Cartes Jouable caracterisee par : <br>
 *  </p>
 * <ul>
 * <li>Une Famille : {@link CardColour}<br></li>
 * <li>Une valeur : un Entier entre 0 et 4 <br></li>
 * <li>Une Condition d'attibution de trophée {@link TrophyCondition}<br></li>
 * <li>Un etat :  cache ou decouvert<br> </li>
 *
 *</ul>
 * 
 * <p>
 * Les attributs cardName et deckStyle permettent de charger les bonnes images depuis le bon repertoire en mode de jeu Interface Graphique
 * 
 * </p>
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see CardColour
 * @see TrophyCondition
 *
 */
public class RegularCard {
	/**
	 * La Famille de la carte
	 */
	private CardColour colour ;
	/**
	 * la value de la carte dans sa famille
	 */
	private int value ;
	/**
	 * La condion d'attribution a un joueur si jamais cette carte est un trophé
	 */
	private TrophyCondition trophyCond;
	/**
	 * La visibilite de la carte :: true==visible ;  false==cachee
	 */
	private boolean isChown = false ;
	/**
	 * L'image caracterisant chaque carte est specifiée par son nom
	 */
	private String cardName ; //L'image caracterisant chaque carte est specifiée par son nom
	/**
	 *  Le nom du repertoire contenant les cartes d'un certain Style de Deck
	 */
	private DeckStyle deckStyleDirectory; // Le nom du repertoire contenant les cartes d'un certain Style de Deck
	/**
	 * Constructeur d'une carte , 
	 * il permet de definir les attributs de la nouvelle carte ainsi que son nom et sa condition d'attribution de trophee
	 * @param value  la valeur de la carte a creer
	 * @param colour la famille de la carte a creer
	 * @param deckStyle l'apparence de la carte en mode Graphique
	 */
	public RegularCard(int value ,CardColour colour , DeckStyle deckStyle) {
		this.isChown=false;
		this.value=value;
		this.colour=colour;
		this.deckStyleDirectory=deckStyle;
		//GIVING TROPHY CONDITION FOR EACH CARD
		this.giveTrophyCond();
		this.setCardName();
	}
	
	
	public CardColour getColour() {
		return colour;
	}
	public void setColour(CardColour colour) {
		this.colour = colour;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public TrophyCondition getTrophyCond() {
		return trophyCond;
	}
	public void setTrophyCond(TrophyCondition trophyCond) {
		this.trophyCond = trophyCond;
	}
	public boolean isChown() {
		return isChown;
	}
	public void setChown(boolean isChown) {
		this.isChown = isChown;
	}

	public String getCardName() {
		return cardName;
	}

	/**
	 * donne a la carte un nom qui correspond à  la nature de la carte si la carte est devoilée <br>
	 * attribut le nom "hidden" a cette carte si elle est cachée
	 */
	public void setCardName() {
		if(isChown==true) {
			if(this.colour==CardColour.JOKER) {
				this.cardName="joker";
			}
			else if(this.value==1) {
				this.cardName = "as_"+this.colour.toString().toLowerCase();
			}else {
				this.cardName = this.value+"_"+this.colour.toString().toLowerCase();
			}
		}else {
			this.cardName="hidden";
		}
	}


	public DeckStyle getDeckStyleDirectory() {
		return deckStyleDirectory;
	}


	public void setDeckStyleDirectory(DeckStyle deckStyleDirectory) {
		this.deckStyleDirectory = deckStyleDirectory;
	}
	/**
	 * Si la carte est devoilée , elle renvoie les infos sur la carte sous de String
	 * Sinon elle renvoie des infos cachée
	 * 
	 * @return Les infos sur la carte si elle est visible
	 * 
	 */
	public String toString() {
		if(this.isChown()) {
		return "[{colour:"+this.colour+"}{value:"+this.value+"}]{name:"+this.cardName+"}" ;
			}else {
		return "[{colour:HIDDEN}{value:HIDDEN}]";
			}
	}
	/**
	 * Display infos about the card on StdOut with tab an CR
	 */
	public void printCard() { //DISPLAY THE CONTENT OF THE CARD
		try {
			if(this.isChown()) {
			System.out.print("\t[{colour:"+this.colour+"}{value:"+this.value+"}]\n");
			}else {
			System.out.print("\t[{colour:HIDDEN}{value:HIDDEN}]\n");

			}
		}catch(NullPointerException e) {
			System.out.println("[Error !!!] Cannot Print a Null Card!!!");
		}
	}
	/**
	 * DISPLAY ON STDOUT THE CONTENT OF THE CARD WITH IT TROPHY CONDITION  WITH A CARRIAGE RETURN AND TAB
	 */
	public void printTrophyCard() { //DISPLAY THE CONTENT OF THE CARD
		try {
			if(this.isChown()) {
			System.out.print("\t[{colour:"+this.colour+"}{value:"+this.value+"}{TrophyCond:"+this.trophyCond+"}]\n");
			}else {
			System.out.print("\t[{colour:HIDDEN}{value:HIDDEN}]\n");

			}
		}catch(NullPointerException e) {
			System.out.println("[Error !!!] Cannot Print a Null Card!!!");
		}
	}
	/**
	 * DISPLAY ON STDOUT THE CONTENT OF THE CARD WITHOUT CARRIAGE RETURN AND TABULATION
	 */
	public void printfCard() { //DISPLAY THE CONTENT OF THE CARD WITHOUT CARRIAGE RETURN AND TABULATION
		try {
			if(this.isChown()) {
			System.out.print("[{colour:"+this.colour+"}{value:"+this.value+"}]");
			}else {
			System.out.print("[{colour:HIDDEN}{value:HIDDEN}]");

			}
		}catch(NullPointerException e) {
			System.out.println("[Error !!!] Cannot Print a Null Card!!!");
		}
	}
	/**
	 * attibut la condition de trophe a la carte en fonction de sa valeur et de sa famille , telle que definie dans les regles du Jeu
	 */
	public void giveTrophyCond() {
		if(this.colour.equals(CardColour.CLUBS) && this.getValue()==1) {
			this.trophyCond=TrophyCondition.HIGHEST_SPADES;
		}else if(this.colour.equals(CardColour.CLUBS) && this.getValue()==2) {
			this.trophyCond=TrophyCondition.LOWEST_HEARTS;
		}else if(this.colour.equals(CardColour.CLUBS) && this.getValue()==3) {
			this.trophyCond=TrophyCondition.HIGHEST_HEARTS;
		}else if(this.colour.equals(CardColour.CLUBS) && this.getValue()==4) {
			this.trophyCond=TrophyCondition.LOWEST_SPADES;
		}else if(this.colour.equals(CardColour.SPADES) && this.getValue()==1) {
			this.trophyCond=TrophyCondition.HIGHEST_CLUBS;
		}else if(this.colour.equals(CardColour.SPADES) && this.getValue()==2) {
			this.trophyCond=TrophyCondition.MAJORITY_3;
		}else if(this.colour.equals(CardColour.SPADES) && this.getValue()==3) {
			this.trophyCond=TrophyCondition.MAJORITY_2;
		}else if(this.colour.equals(CardColour.SPADES) && this.getValue()==4) {
			this.trophyCond=TrophyCondition.LOWEST_CLUBS;
		}else if(this.colour.equals(CardColour.HEARTS) && this.getValue()==1) {
			this.trophyCond=TrophyCondition.HASJOKER;
		}else if(this.colour.equals(CardColour.HEARTS) && this.getValue()==2) {
			this.trophyCond=TrophyCondition.HASJOKER;
		}else if(this.colour.equals(CardColour.HEARTS) && this.getValue()==3) {
			this.trophyCond=TrophyCondition.HASJOKER;
		}else if(this.colour.equals(CardColour.HEARTS) && this.getValue()==4) {
			this.trophyCond=TrophyCondition.HASJOKER;
		}else if(this.colour.equals(CardColour.DIAMONDS) && this.getValue()==1) {
			this.trophyCond=TrophyCondition.MAJORITY_4;
		}else if(this.colour.equals(CardColour.DIAMONDS) && this.getValue()==2) {
			this.trophyCond=TrophyCondition.HIGHEST_DIAMONDS;
		}else if(this.colour.equals(CardColour.DIAMONDS) && this.getValue()==3) {
			this.trophyCond=TrophyCondition.LOWEST_DIAMONDS;
		}else if(this.colour.equals(CardColour.DIAMONDS) && this.getValue()==4) {
			this.trophyCond=TrophyCondition.BESTJESTNOJOKER;
		}else if(this.colour.equals(CardColour.JOKER)) {
			this.trophyCond=TrophyCondition.BESTJEST;
		}
		
	}
	/**
	 * 
	 * @return Une copie profonde de la carte à cet instant dans un objet different
	 */
	public RegularCard cloneCard() {
		RegularCard clone = new RegularCard(this.value, this.colour, this.deckStyleDirectory) ;
		clone.giveTrophyCond();
		return clone ;
	}
}
