package fr.jest.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.swing.text.AbstractDocument.LeafElement;

import fr.jest.console.RealPlayer;

//Oberved by the views and observing its components/**
/**
 * Cette classe est le Coeur du jeu .<br>
 * elle decrit la partie dans san globalité<br>
 * Une partie est caractérisée par : <br>
 * <ul>
 * <li>Une liste de {@link Player} participant à la {@link Party}<br></li>
 * <li>Une pioche ( {@link Deck} )<br></li>
 * <li>La {@link Strategy}  des {@link VirtualPlayer}<br></li>
 * <li>Les regles de la {@link Party} ( {@link JestEvalMethod} ) <br></li>
 * <li>le minuteur de la {@link Party} ( {@link JestTimer} )<br></li>
 * </ul>
 * 
 *@author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see Player
 *@see Deck
 *@see Strategy
 *@see JestEvalMethod
 *@see JestTimer
 */
public class   Party extends Observable implements Visitor,Observer {
	/**
	 * 
	 * Le Nombre Maximum de joueur pour Party : d'après les règles du jeu
	 */
	private  final int MAXPLAYER = 4 ;
	/**
	 * le nombre de Jeu pour cette {@link Party} : 4 par défaut
	 */
	private static  int gameNumberOfPlayer = 4 ;
	/**
	 * Le nombre de {@link RealPlayer} :  1 par Defaut
	 */
	private  int gameNumberOfRealPlayer = 1;
	/**
	 * Le nombre de Carte dans les {@link Trophies} : <br> 2 {@link RegularCard} pour une {@link Party} de 3 {@link Player} et 1 {@link RegularCard} pour une {@link Party} de 4 {@link Player}
	 */
	private  static  int numberOfCardsInTrophies = 1;
	/**
	 * Non implémente , il est sensé etre utilisé pour informer les joueurs de toutes les transaction de cartes qui se produisent .
	 */
	private static String broadCast ="";
	/**
	 * Le style de {@link RegularCard} utilisé pour la {@link Party}
	 */
	private   static DeckStyle gameDeckStyle = DeckStyle.DEFAULT;
	/**
	 * La pioche de la {@link Party}
	 */
	private   Deck partyDeck ;
	/**
	 * les {@link Trophies} de la {@link Party}
	 */
	private   static Trophies partyTrophies ;
	/**
	 * La liste des {@link Player} de la {@link Party}
	 */
	private   ArrayList<Player> players = new ArrayList<Player>();
	/**
	 * Un  iterateur permettant de parcourir la collection des {@link Player}
	 */
	private   Iterator<Player> playersIterator ;
	/**
	 * Le comparateur de {@link Player} permetant de désigner l'ordre de passage des {@link Player} à chaque séquence de la {@link Party}
	 */
	private   PlayerOrderComparator playOrdComp ;
	/**
	 * Le tableau de score de la {@link Party}
	 */
	private   HashMap<Player, Integer> scoreBoard = new HashMap<Player, Integer>(4);
	/**
	 * Le vainqueur de la {@link Party}
	 */
	private   Player partyWinner ;
	

	/**
	 * La {@link Strategy} des {@link VirtualPlayer}
	 */
	private  static Strategy virtualPlayerStrategy ;  //Niveau Facile Par Defaut
	/**
	 * Les règles de la {@link Party} : par defaut c'est {@link DefaultRules}
	 */
	private  static JestEvalMethod rules ;

	
	//public static  BufferedReader input = new BufferedReader(new InputStreamReader(Channels.newInputStream((new FileInputStream(FileDescriptor.in)).getChannel())));
	/**
	 * La petite pille de {@link RegularCard} permettant de redistribuer les cartes à la fin de chaque {@link Party}
	 */
	private   List<RegularCard> smallStack = new ArrayList<RegularCard>();
	/**
	 * drapeau permettant de savoir si la party a deja été configurée
	 */
	private   boolean partyConfigured = false;
	/**
	 * drapeau permettant de savoir si la party a deja été commencé 
	 */
	private   boolean partyStarted = false ;
	/**
	 * drapeau permettant de savoir si la derniere sequence de la {@link Party} est entrain de se jouer
	 */
	private   boolean lastSequence = false ;
	/**
	 * drapeau permettant de savoir si l'avant derniere sequence de la {@link Party} est entrain de se jouer
	 */
	private   boolean BeforelastSequence = false ; //CRUCIAL POUR DETERMINER LA FIN DE LA PARTIE
	/**
	 * drapeau permettant de savoir si une sequence de la {@link Party} est entrain de se jouer
	 */
	private  volatile Boolean sequenceStrated = new Boolean(false) ;
	/**
	 * drapeau permettant de savoir si la {@link Party} est terminée
	 */
	private  boolean partyEnded = false ;
	/**
	 * indicateur permettant de savoir à quelle du déroulement de la séquence nous sommes : cet indiacteur est utile pour la GamePlay en interface Graphique
	 * car il permet de determiner si on doit encore attentre des actions de l'utilsateur avant de terminer la sequence
	 */
	private int numberOfUserActionToEndASequence ;
	/**
	 * 
	 * drapeau permettant de savoir depuis quelle vue la Party a Commencée
	 * false ==Console :: true ==GUI
	 * 
	 */
	private static boolean viewWherePartyStarted = false ; //false -->Console :: true -->GUI
	/**
	 * 
	 * drapeau permettant de savoir depuis quelle vue la Party s'est terminée
	 * false ==Console :: true ==GUI
	 * 
	 */
	private static boolean viewWherePartyEnded = false ; //false -->Console :: true -->GUI
	/**
	 * 
	 * drapeau permettant de savoir depuis quelle vue la Party a été configurée
	 * false ==Console :: true ==GUI
	 * 
	 */
	private static boolean viewWherePartyConfigured = false ; //false -->Console :: true -->GUI

	
	/**
	 * le Chronometre de la {@link Party}
	 */
	private static JestTimer myTimer ;
	/**
	 * L'instance de la {@link Party} qui est entre de se dérouler
	 * <br> Ce programme n'admet qu'une seule partie puisse etre jouée à la fois.
	 * <br> la classe {@link Party} implémente le pattern singleton
	 */
	private  static Party partyInstance ; 
	
	
	/**
	 * renvoi une instance de {@link Party} si il y en a une ou crée et renvoie une nouvelle instance de {@link Party} si aucune party n'est référencée
	 * @return une référence sur un Objet de type {@link Party}
	 */
	public static Party getInstanceOfParty() {
		if(Party.partyInstance==null) {
			Party.partyInstance= new Party();
			Party.myTimer= JestTimer.getInstanceOfTimer(partyInstance);
			return Party.partyInstance;
		}else {
			return Party.partyInstance;
		}
	}
	/**
	 * Constructeur : il initialise la {@link Party} avec les paramètres par défaut
	 */
	 private Party() {
		 virtualPlayerStrategy= new NiveauFacile();
		 rules=new DefaultRules();
		 partyDeck = Deck.getInstanceOfDeck(DeckStyle.DEFAULT);
		 
	 }
	 /**
	  * cette methode permet de distribuer deux {@link RegularCard} aux {@link Player} en début de partie
	  */
	public  void distributeCards() {
		//On lance le Chrono a la 1ere distribution de carte
		myTimer.start();
		for(Player cur : players) {
			cur.getOffer().addCard(partyDeck);
			cur.getOffer().addCard(partyDeck);
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * cette methode permet de constructeur une pile de {@link RegularCard} à la fin de chaque séquence <br>
	 * les {@link RegularCard} de cette pile seont attriubuer aux {@link Player} de la {@link Party} par  la methode {@link Party#fillOffers()} afin  pouvoir lancer la séquence suivante
	 */
	public  void buildStack() {
		//Actually building the stack
				int stackIndex = 0 ;
						for( Player target : this.getPlayers()) {
							if( target != null) {
								this.getSmallStack().add(stackIndex, target.getOffer().giveCard());
								stackIndex++;
							}
						}
						// THE REFEREE TOOK ONE CARD IN ALL OFFERS AND THE SMALL STACK SHOULD BE HALF-FULL NOW 
						for( ; stackIndex<2*Party.getGameNumberOfPlayer() ; stackIndex++) {
							this.getSmallStack().add(stackIndex,partyDeck.removeCard());
						}
		//SHUFFLING THE STACK
						RegularCard tmpObj ;
						int numberofSteps = 300 + (int)Math.floor(Math.random()*50) ; //SHUFFLE AT LEAST 100 TIMES AND AT MOST 150 TIMES
						int tmpIndex , randomCardIndex ;
							for (tmpIndex=50 ; tmpIndex <= numberofSteps; tmpIndex++) {
								randomCardIndex=(int)Math.floor(Math.random()*((double)this.getSmallStack().size() - 0.5));
								tmpObj=smallStack.get(randomCardIndex);
								this.getSmallStack().remove(randomCardIndex);
								this.getSmallStack().add(randomCardIndex, this.getSmallStack().get(0));
								this.getSmallStack().remove(0);
								this.getSmallStack().add(0, tmpObj);
							}
							
	}
	/**
	 * vide le {@link Party#smallStack } et remple les {@link Offer} de tous les {@link Player} de la {@link Party}
	 */
	public  void fillOffers() {
		for(Player cur : players) {
			cur.getOffer().addCard(this.getSmallStack().remove(0));
			cur.getOffer().addCard(this.getSmallStack().remove(0));
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * Determine qui est le vaiqueur de la {@link Party} ainsi que {@link LeafElement} {@link Party#scoreBoard}
	 * @param playerList la liste des participant de la {@link Party}
	 * @return le {@link Player} qui a remporté la {@link Party}
	 */
	public Player setupResults(ArrayList<Player> playerList) {
		HashMap<Player, Integer> baseMap = new HashMap<Player, Integer>();
		ValueComparator vlc  = new ValueComparator(baseMap);
		TreeMap<Player,Integer> LocalScoreBoard = new TreeMap<Player, Integer>(vlc);
		TreeMap<Player,Integer> tieMap = new TreeMap<Player, Integer>(vlc);
		
		for(Player man : playerList) {
			baseMap.put(man, Party.getRules().evalJest(man));
		}

		
		
		
		LocalScoreBoard.putAll(baseMap);
		this.getScoreBoard().putAll(LocalScoreBoard);
		tieMap.put(LocalScoreBoard.firstKey(), LocalScoreBoard.get(LocalScoreBoard.firstKey()));
		LocalScoreBoard.remove(LocalScoreBoard.firstKey());
		tieMap.put(LocalScoreBoard.firstKey(), LocalScoreBoard.get(LocalScoreBoard.firstKey()));

		
		if(tieMap.get(tieMap.firstKey())==tieMap.get(tieMap.lastKey())) {
			RegularCard firstKeyCard = new RegularCard(1, CardColour.JOKER, null);
			RegularCard secondKeyCard = new RegularCard(1, CardColour.JOKER, null) ;
				for(RegularCard card : tieMap.firstKey().getJest().getMyJestCards()) {
					if( card.getColour().compareTo(firstKeyCard.getColour())>0) {
						firstKeyCard=card;
					}
				}
				for(RegularCard card : tieMap.lastKey().getJest().getMyJestCards()) {
					if( card.getColour().compareTo(secondKeyCard.getColour())>0) {
						secondKeyCard=card;
					}
				}
							
			if(firstKeyCard.getColour().compareTo(secondKeyCard.getColour())>0) {
				setChanged();
				notifyObservers();
				return tieMap.firstKey();
			}else {
				setChanged();
				notifyObservers();
				return tieMap.lastKey();
			}
		}else {
			setChanged();
			notifyObservers();
			return tieMap.firstKey();
		}
		
	}
	/**
	 * permet de determiner si la Party est terminée en fonction du nombre de {@link RegularCard} restant dans la pioche ( {@link Deck} )
	 * @param pDeck la pioche  de la {@link Party}
	 * @return true si la {@link Party} n'est pas encore terminé et false dans le cas contraire
	 */
	public  boolean partyNotOver(Deck pDeck) {
		boolean response = true ;
		
		
		if(this.isBeforelastSequence()==false) {
			if(pDeck.size()>0) {
				response=true;
			}else {
				this.setBeforelastSequence(true);
				response=true;
			}
		}else {
			if(this.isLastSequence()==false) {
				response=true;
				setLastSequence(true);
			}else {
				response=false;
			}

		}
		setChanged();
		notifyObservers();
		return response ;
	}
	/**
	 * permet de savoir si tous les joueurs de la party ont joué une fois pendant la séquence
	 * @return true si tous les {@link Player} ont déja joueé , ou renvoie false si au moins un {@link Player}  n'a pas encore joué
	 */
	public  boolean theyAllTookOneCard() {
		boolean flag = true ;
		for(Player cur : this.getPlayers()) {
			if(!cur.alreadyTookACard()) {
				flag=false;
			}
		}
		return flag;
	}
	/**
	 * renvoie la position du prochain {@link Player} qui n'a pas encore joué
	 * @return renvoie la position du prochain {@link Player} qui n'a pas encore joué
	 */
	public  int nextPlayerNeedingCardIndex() {
		int result = -1 ;
		this.getPlayOrdComp().setOrder();
		int[] order = this.getPlayOrdComp().getPlayerOrder();
		for (int k=0 ; k < this.getPlayers().size(); k++ ) {
			if(this.getPlayers().get(order[k]).alreadyTookACard()==false) {
				result=order[k];
				return result ;
			}
		}
		return result ;		
	}
	
/**
 * permet d'avalur la valeur des  {@link Jest} des {@link Player} de la {@link Party}
 */
	@Override
	public int visit(JestEvalMethod evalMethod, Player targetPlayer) {
		return evalMethod.evalJest(targetPlayer);	
	}
	
	/**
	 * permet d'effacer l'ecran de l'invate de commande ou du terminal
	 */
	public static void clrscr(){
	    //Clears Screen in java
	    try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException ex) {}
	}
	public int getMAXPLAYER() {
		return MAXPLAYER;
	}
	public static int getGameNumberOfPlayer() {
		return gameNumberOfPlayer;
	}
	public int getGameNumberOfRealPlayer() {
		return gameNumberOfRealPlayer;
	}
	public static int getNumberOfCardsInTrophies() {
		return numberOfCardsInTrophies;
	}
	public static String getBroadCast() {
		return broadCast;
	}
	public static DeckStyle getGameDeckStyle() {
		return gameDeckStyle;
	}
	public Deck getPartyDeck() {
		return partyDeck;
	}
	public static Trophies getPartyTrophies() {
		return partyTrophies;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Iterator<Player> getPlayersIterator() {
		return playersIterator;
	}
	public PlayerOrderComparator getPlayOrdComp() {
		return playOrdComp;
	}
	public HashMap<Player, Integer> getScoreBoard() {
		return scoreBoard;
	}
	public Player getPartyWinner() {
		return partyWinner;
	}
	public static Strategy getVirtualPlayerStrategy() {
		return virtualPlayerStrategy;
	}
	public static JestEvalMethod getRules() {
		return rules;
	}
	
	public List<RegularCard> getSmallStack() {
		return smallStack;
	}
	public boolean isPartyStarted() {
		return partyStarted;
	}
	public boolean isLastSequence() {
		return lastSequence;
	}
	public boolean isBeforelastSequence() {
		return BeforelastSequence;
	}
	public static Party getPartyInstance() {
		return partyInstance;
	}
	public static void setGameNumberOfPlayer(int gameNumberOfPlayer) {
		Party.gameNumberOfPlayer = gameNumberOfPlayer;
	}
	public void setGameNumberOfRealPlayer(int gameNumberOfRealPlayer) {
		this.gameNumberOfRealPlayer = gameNumberOfRealPlayer;
	}
	public static void setNumberOfCardsInTrophies(int numberOfCardsInTrophies) {
		Party.numberOfCardsInTrophies = numberOfCardsInTrophies;
	}
	public static void setBroadCast(String broadCast) {
		Party.broadCast = broadCast;
	}
	public static void setGameDeckStyle(DeckStyle gameDeckStyle) {
		Party.gameDeckStyle = gameDeckStyle;
	}
	public void setPartyDeck(Deck partyDeck) {
		this.partyDeck = partyDeck;
	}
	public static void setPartyTrophies(Trophies partyTrophies) {
		Party.partyTrophies = partyTrophies;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public void setPlayersIterator(Iterator<Player> playersIterator) {
		this.playersIterator = playersIterator;
	}
	public void setPlayOrdComp(PlayerOrderComparator playOrdComp) {
		this.playOrdComp = playOrdComp;
	}
	public void setScoreBoard(HashMap<Player, Integer> scoreBoard) {
		this.scoreBoard = scoreBoard;
	}
	public void setPartyWinner(Player partyWinner) {
		this.partyWinner = partyWinner;
	}
	public static void setVirtualPlayerStrategy(Strategy virtualPlayerStrategy) {
		Party.virtualPlayerStrategy = virtualPlayerStrategy;
	}
	public static void setRules(JestEvalMethod rules) {
		Party.rules = rules;
	}
	
	public void setSmallStack(List<RegularCard> smallStack) {
		this.smallStack = smallStack;
	}
	public void setPartyStarted(boolean partyStarted) {
		this.partyStarted = partyStarted;
	}
	public void setLastSequence(boolean lastSequence) {
		this.lastSequence = lastSequence;
	}
	public void setBeforelastSequence(boolean beforelastSequence) {
		BeforelastSequence = beforelastSequence;
	}
	public static void setPartyInstance(Party partyInstance) {
		Party.partyInstance = partyInstance;
	}
	public boolean isPartyConfigured() {
		return partyConfigured;
	}
	public void setPartyConfigured(boolean partyConfigured) {
		this.partyConfigured = partyConfigured;
	}
	
	public int getNumberOfUserActionToEndASequence() {
		return numberOfUserActionToEndASequence;
	}

	public void setNumberOfUserActionToEndASequence(int numberOfUserActionToEndASequence) {
		this.numberOfUserActionToEndASequence = numberOfUserActionToEndASequence;
	}
	
	public  Boolean isSequenceStrated() {
		return sequenceStrated ;
	}
	public void setSequenceStrated(boolean sequenceStrated) {
		this.sequenceStrated=new Boolean(sequenceStrated);
	}
	public void setChanged() {
		super.setChanged();
	}
	/**
	 * notifie les obsrveurs (Les Vues) de la {@link Party} que la {@link Party} a changée
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	
	
	
	public static boolean getViewWherePartyConfigured() {
		return viewWherePartyConfigured;
	}
	public static void setViewWherePartyConfigured(boolean viewWherePartyConfigured) {
		Party.viewWherePartyConfigured = viewWherePartyConfigured;
	}
	public static boolean getViewWherePartyStarted() {
		return viewWherePartyStarted;
	}
	public static boolean getViewWherePartyEnded() {
		return viewWherePartyEnded;
	}
	public static void setViewWherePartyStarted(boolean viewWherePartyStarted) {
		Party.viewWherePartyStarted = viewWherePartyStarted;
	}
	public static void setViewWherePartyEnded(boolean viewWherePartyEnded) {
		Party.viewWherePartyEnded = viewWherePartyEnded;
	}
	
	/**
	 * determine le numéro de la sequence qui est entrain d'être jouée
	 * @return le numéro de la sequence qui est entrain d'être jouée
	 */
	public int sequenceNumber() {
		if(BeforelastSequence==true && lastSequence==true){
			if(gameNumberOfPlayer==4) {
				return 4;
			}else{
				return 5 ;
			}
			
		}else if(this.getPartyDeck().size()>0 || (BeforelastSequence==true && lastSequence==false)) {
			return 1+(((Deck.getDeckMax()-Party.getNumberOfCardsInTrophies())-(2*Party.getGameNumberOfPlayer())-this.getPartyDeck().size())/Party.gameNumberOfPlayer) ;
		}else {
			return 2+(((Deck.getDeckMax()-Party.getNumberOfCardsInTrophies())-(2*Party.getGameNumberOfPlayer())-this.getPartyDeck().size())/Party.gameNumberOfPlayer) ;
		}
	}
	public static JestTimer getMyTimer() {
		return myTimer;
	}
	public static void setMyTimer(JestTimer myTimer) {
		Party.myTimer = myTimer;
	}
	
	public boolean isPartyEnded() {
		return partyEnded;
	}
	public void setPartyEnded(boolean partyEnded) {
		this.partyEnded = partyEnded;
	}
	/**
	 * reinitialise la {@link Party} et remet les Parametres par défaut
	 */
	public void resetParty() {
		gameDeckStyle=DeckStyle.DEFAULT;
		players=null;
		partyDeck.reset();
		partyDeck=null;
		partyTrophies.reset();
		partyTrophies=null;
		gameNumberOfPlayer=4;
		gameNumberOfRealPlayer=1;
		numberOfCardsInTrophies=1;
		partyEnded=false;
		partyStarted=false;
		BeforelastSequence=false;
		lastSequence=false;
		sequenceStrated=false;
		partyConfigured=false;
		scoreBoard=null;
		playersIterator=null;
		partyWinner=null;
		rules=null;
		smallStack=null;
		myTimer.pause();
		myTimer.reset();
		myTimer=null;
		partyInstance=null;		
	}
	
	
	
}
