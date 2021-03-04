package fr.jest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.TreeMap;
/**
 * Cette Classe définie les methodes propres aux cartes qui sont désignées comme trophé en début de partie
 * <br>
 * Cette classe implémente le patron singleton ; on ne peut pas instancier plusieurs {@link Trophies} differnets dans une même partie
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see Observable
 *@see RegularCard
 */
public class Trophies extends Observable {
	/**
	 * Le nombre de carte dans les trophées : ce nombre dépend du nombre de joueur de la partie
	 * <ul>
	 * <li>1 carte   : pour une partie de 4 joueurs</li>
	 * <li>2 cartes  : pour une partie de 3 joueurs</li>
	 * </ul>
	 */
	public static int NumberOfCardsInTrophies ;
	private static Trophies myTrophies;
	private List<RegularCard> trophyCard = new ArrayList<RegularCard>();
	/**
	 * Les règles de la partie
	 */
	private JestEvalMethod rules ;
	// PREVIOUSLY 	private JestEvalMethod rules = Party.getRules();

	/**
	 * Constructeur
	 * @param deck le {@link Trophies} est construit à partir d'une pioche
	 */
	private Trophies(Deck deck) {
		for(int i =0 ; i<Trophies.NumberOfCardsInTrophies; i++) {
			RegularCard tmpVar = deck.removeCard();
			tmpVar.setChown(true);
			tmpVar.setCardName();
			this.trophyCard.add(i,tmpVar);
			rules = Party.getRules();
		}

		}
	
	public static Trophies getInstanceofTrophies(Deck deck) {
		NumberOfCardsInTrophies = Party.getNumberOfCardsInTrophies() ;
		
		if(myTrophies==null) {
			myTrophies= new Trophies(deck);
			return myTrophies;
		}else {
			return myTrophies;
		}
	}
	/**
	 * DISPLAY THE CONTENT OF THE TROPHIES on STDOUT
	 */
	public void printTropies() { //DISPLAY THE CONTENT OF THE TROPHIES
		System.out.println("[[ TROPHY CARDS  ]]\n");
		for(RegularCard card : this.trophyCard) {
			card.printTrophyCard();
		}
	}
	/**
	 * @return renvoies les information sur les cartes de {@link Trophies} sous forme de {@link String}
	 */
	@Override
	public String toString() {
		StringBuffer result =  new StringBuffer(500);
		for(RegularCard card : trophyCard) {
			result.append("\n"+card.toString());
		}
		return result.toString();
	}
	/**
	 * Ajoutes les cartes de {@link Trophies} aux {@link Jest}  des {@link Player} qui remplissent les conditions d'attribution
	 * @param playerList la liste des {@link Player} de la {@link Party}
	 */
	public void attributeTrophies(ArrayList<Player>playerList) {
		//IF FAUT ATTRIBUER LE JOKER EN PRIORITE POUR NE PAS LEVER UNE EXCEPTION
		if(trophyCard.size()>1) {
			if(trophyCard.get(1).getColour().equals(CardColour.JOKER)) {
				RegularCard tmpVarCard0 =  trophyCard.get(0) ;
				RegularCard tmpVarCard1 =  trophyCard.get(1) ;

				trophyCard.remove(0);
				trophyCard.remove(0);
				
				trophyCard.add(0,tmpVarCard1);
				trophyCard.add(1, tmpVarCard0);
			}
		}
		
		for(RegularCard card : this.trophyCard) {
			String[] tropCondParsing = card.getTrophyCond().toString().split("_");
			try {
				if(card.getTrophyCond().equals(TrophyCondition.HASJOKER)) {
					this.hasJoker(playerList).getJest().addCard(card);
				}else if(tropCondParsing[0].equals("MAJORITY")) {
					int referenceValue =  Integer.parseInt(tropCondParsing[1].trim());
					this.majorityOf(referenceValue, playerList).getJest().addCard(card);
				}else if(tropCondParsing[0].equals("HIGHEST")) {
					CardColour ReferenceColour  = CardColour.valueOf(tropCondParsing[1]);
					this.highestOrLowest(tropCondParsing[0], ReferenceColour, playerList).getJest().addCard(card);
				}else if(tropCondParsing[0].equals("LOWEST")) {
					CardColour ReferenceColour  = CardColour.valueOf(tropCondParsing[1]);
					this.highestOrLowest(tropCondParsing[0], ReferenceColour, playerList).getJest().addCard(card);
				}else if(card.getTrophyCond().equals(TrophyCondition.BESTJEST)) {
					this.bestJest(playerList).getJest().addCard(card);
				}else if(card.getTrophyCond().equals(TrophyCondition.BESTJESTNOJOKER)) {
					this.bestJestWOJoker(playerList).getJest().addCard(card);
				}else {
					System.out.print("[ERROR!!!] CANNOT ATTRIBUTE THE TROPHY -->");
					card.printfCard();
					System.exit(1);
				}
			}catch (NullPointerException e) {
				// TODO: handle exception
			}
			
		}
	}
	
	
	/**
	 * 
	 * @param faceValue la valeur de Carte dont on cherche le Joueur qui en a le plus
	 * @param playerList la liste des {@link Player} de la {@link Party}
	 * @return le {@link Player} qui la majorite de carte avec la valeur faceValue
	 */
	public Player majorityOf(int faceValue, ArrayList<Player>playerList) {
		HashMap<Player, Integer> baseMap = new HashMap<Player, Integer>();
		ValueComparator vlc  = new ValueComparator(baseMap);
		TreeMap<Player,Integer> occurenceBoard = new TreeMap<Player, Integer>(vlc);
		TreeMap<Player,Integer> tieMap = new TreeMap<Player, Integer>(vlc);
		
		for(Player man : playerList) {
			baseMap.put(man, 0);
		}
			for(int k=0; k<playerList.size();k++) {
				for(RegularCard card : playerList.get(k).getJest().getMyJestCards()) {
					if(card.getValue()==faceValue) {
						baseMap.put(playerList.get(k), baseMap.get(playerList.get(k))+1);
					}
				}
			}	
		occurenceBoard.putAll(baseMap);
		tieMap.put(occurenceBoard.firstKey(), occurenceBoard.get(occurenceBoard.firstKey()));
		occurenceBoard.remove(occurenceBoard.firstKey());
		tieMap.put(occurenceBoard.firstKey(), occurenceBoard.get(occurenceBoard.firstKey()));

		
		if(tieMap.get(tieMap.firstKey())==tieMap.get(tieMap.lastKey())) {
			RegularCard firstKeyCard = new RegularCard(faceValue, CardColour.JOKER, null);
			RegularCard secondKeyCard = new RegularCard(faceValue, CardColour.JOKER, null) ;
				for(RegularCard card : tieMap.firstKey().getJest().getMyJestCards()) {
					if(card.getValue()==faceValue && card.getColour().compareTo(firstKeyCard.getColour())>0) {
						firstKeyCard=card;
					}
				}
				for(RegularCard card : tieMap.lastKey().getJest().getMyJestCards()) {
					if(card.getValue()==faceValue && card.getColour().compareTo(secondKeyCard.getColour())>0) {
						secondKeyCard=card;
					}
				}
			
			if(firstKeyCard.getColour().compareTo(secondKeyCard.getColour())>0) {
				//System.out.println("Trophy Winner :: "+tieMap.firstKey());
				return tieMap.firstKey();
			}else {
				//System.out.println("Trophy Winner :: "+tieMap.lastKey());
				return tieMap.lastKey();
			}
		}else {
			//System.out.println("Trophy Winner :: "+tieMap.firstKey());
			return tieMap.firstKey();
		}
	}
	
	/**
	 * 
	 * @param handler cette chaine determiner si la methode chercher le HiGHEST ou Le LOWEST Card 
	 * @param colour  la {@link CardColour} qui fait l'objet du {@link Trophies} card
	 * @param playerList la liste de joueur de la Partie
	 * @return le Joueur qui verifie la condition d'attribution du {@link Trophies}
	 */
	public Player highestOrLowest(String handler ,CardColour colour , ArrayList<Player> playerList) {
		HashMap<Player, Integer> baseMap = new HashMap<Player, Integer>();
		ValueComparator vlc  = new ValueComparator(baseMap);
		TreeMap<Player,Integer> faceValueBoard = new TreeMap<Player, Integer>(vlc);
		if(handler.equals("HIGHEST")){
			for(Player man : playerList) {
				baseMap.put(man, 0);
			}
		}else {
			for(Player man : playerList) {
				baseMap.put(man, 5);
			}
		}
		
		for(int k=0; k<playerList.size();k++) {
			for(RegularCard card : playerList.get(k).getJest().getMyJestCards()) {
				if(handler.equals("HIGHEST") && card.getColour().equals(colour) && baseMap.get(playerList.get(k))<card.getValue() ) {
					baseMap.put(playerList.get(k), card.getValue());
				}else if (handler.equals("LOWEST") && card.getColour().equals(colour) && baseMap.get(playerList.get(k))>card.getValue()) {
					baseMap.put(playerList.get(k), card.getValue());
				}
			}
		}
		
		for(int k = 0 ; k < playerList.size() ; k++ ) {
			if(baseMap.get(playerList.get(k))==0) {
				baseMap.remove(playerList.get(k));
			}
		}
		
		faceValueBoard.putAll(baseMap);
		if(handler.equals("HIGHEST")) {
			//System.out.println("H trophy winner :: "+faceValueBoard.firstKey());
			return faceValueBoard.firstKey();
		}else {
			//System.out.println("L trophy winner :: "+faceValueBoard.lastKey());
			return faceValueBoard.lastKey();
		}
	}
	/**
	 * 
	 * @param playerList la Liste des Joueurs de la Partie
	 * @return le {@link Player} qui possede le {@link CardColour#JOKER} dans son Jest
	 */
	public Player hasJoker(ArrayList<Player> playerList) {
		for(Player man : playerList) {
			for(RegularCard card : man.getJest().getMyJestCards()) {
				if(card.getColour().equals(CardColour.JOKER)) {
					//System.out.println("Has Joker trophy winner :: "+man);
					return man;
				}
			}
		}
		return null ;
	}
	/**
	 * 
	 * @param playerList la liste de sk=joueurs de la partie
	 * @return le Joueur qui le jest de plus grande valeur
	 */
	public Player bestJest(ArrayList<Player> playerList) {
		HashMap<Player, Integer> baseMap = new HashMap<Player, Integer>();
		ValueComparator vlc  = new ValueComparator(baseMap);
		TreeMap<Player,Integer> scoreBoard = new TreeMap<Player, Integer>(vlc);
		TreeMap<Player,Integer> tieMap = new TreeMap<Player, Integer>(vlc);
		
		for(Player man : playerList) {
			baseMap.put(man, this.rules.evalJest(man));
		}

		
		
		
		scoreBoard.putAll(baseMap);
		tieMap.put(scoreBoard.firstKey(), scoreBoard.get(scoreBoard.firstKey()));
		scoreBoard.remove(scoreBoard.firstKey());
		tieMap.put(scoreBoard.firstKey(), scoreBoard.get(scoreBoard.firstKey()));

		
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
				//System.out.println("Best Jest Trophy Winner :: "+tieMap.firstKey());
				return tieMap.firstKey();
			}else {
				//System.out.println("Best Jest Trophy Winner :: "+tieMap.lastKey());
				return tieMap.lastKey();
			}
		}else {
			//System.out.println("Best Jest Trophy Winner :: "+tieMap.firstKey());
			return tieMap.firstKey();
		}
	}
	/**
	 * 
	 * @param playerList la liste des joueurs de la {@link Party}
	 * @return  le Joueur qui le jest de plus grande valeur sans tenir compte du {@link CardColour#JOKER}
	 */
	public Player bestJestWOJoker(ArrayList<Player> playerList) {
		HashMap<Player, Integer> baseMap = new HashMap<Player, Integer>();
		ValueComparator vlc  = new ValueComparator(baseMap);
		TreeMap<Player,Integer> occurenceBoard = new TreeMap<Player, Integer>(vlc);
		TreeMap<Player,Integer> tieMap = new TreeMap<Player, Integer>(vlc);
		int savedHeartJokerRuleParam = this.rules.getHeartsAndJokerRule();
		int savedJokerValue = this.rules.jokerValue;
		this.rules.setHeartsAndJokerRule(0);
		this.rules.setJokerValue(0);
		for(Player man : playerList) {
			baseMap.put(man, this.rules.evalJest(man));
		}
		this.rules.setHeartsAndJokerRule(savedHeartJokerRuleParam);
		this.rules.setJokerValue(savedJokerValue);
	
		occurenceBoard.putAll(baseMap);
		tieMap.put(occurenceBoard.firstKey(), occurenceBoard.get(occurenceBoard.firstKey()));
		occurenceBoard.remove(occurenceBoard.firstKey());
		tieMap.put(occurenceBoard.firstKey(), occurenceBoard.get(occurenceBoard.firstKey()));

		
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
				//System.out.println("Best Jest W/O JokerTrophy Winner :: "+tieMap.firstKey());
				return tieMap.firstKey();
			}else {
				//System.out.println("Best Jest W/O Joker Trophy Winner :: "+tieMap.lastKey());
				return tieMap.lastKey();
			}
		}else {
			//System.out.println("Best Jest W/O Joker Trophy Winner :: "+tieMap.firstKey());
			return tieMap.firstKey();
		}
	}

	public List<RegularCard> getTrophyCard() {
		return trophyCard;
	}
	/**
	 * 
	 * @return le nombre de carte dans les trophés
	 */
	public int size() {
		return trophyCard.size();
	}
	/**
	 * permet de deréfencer le {@link Trophies} de la partie en fin de {@link Party} , afin de pouvoir en creer un nouveau pour une nouvelle Partie 
	 */
	public void reset() {
		myTrophies=null;
	}
	
}
