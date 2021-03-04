package fr.jest.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.jest.Launcher;
import fr.jest.console.RealPlayer;
import fr.jest.graphic.CardSpot;
import fr.jest.graphic.ConfigPan;
import fr.jest.graphic.GamePanel;
import fr.jest.graphic.GameView;
import fr.jest.graphic.JestVisualizer;
import fr.jest.graphic.PlayerSpot;
import fr.jest.graphic.TrophySpot;
import fr.jest.model.Deck;
import fr.jest.model.DeckStyle;
import fr.jest.model.DefaultRules;
import fr.jest.model.Jest;
import fr.jest.model.JestEvalMethod;
import fr.jest.model.JestTimer;
import fr.jest.model.NiveauMoyen;
import fr.jest.model.Offer;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.PlayerOrderComparator;
import fr.jest.model.RegularCard;
import fr.jest.model.Strategy;
import fr.jest.model.Trophies;
import fr.jest.model.VirtualPlayer;
/**
 * Cette classe est le {@link Controler} pour le mode Graphique du Jeu.<br>
 * @author Jeff Jordan
 * 
 * @see Controler
 *
 */
public class GraphicControler implements Controler {
	
	public static BufferedImage clubs_as_Im ; 
	public static BufferedImage diamonds_as_Im ;
	public static BufferedImage spades_as_Im ;
	public static BufferedImage hearts_as_Im ;
	
	public static BufferedImage clubs_2_Im ; 
	public static BufferedImage diamonds_2_Im ;
	public static BufferedImage spades_2_Im ;
	public static BufferedImage hearts_2_Im ;
	
	public static BufferedImage clubs_3_Im ; 
	public static BufferedImage diamonds_3_Im ;
	public static BufferedImage spades_3_Im ;
	public static BufferedImage hearts_3_Im ;
	
	public static BufferedImage clubs_4_Im ; 
	public static BufferedImage diamonds_4_Im ;
	public static BufferedImage spades_4_Im ;
	public static BufferedImage hearts_4_Im ;
	
	public static BufferedImage hidden_Im ; 
	public static BufferedImage ref_Im ;
	public static BufferedImage joker_Im ;
	public static BufferedImage spot_Im ;
	
	public static BufferedImage transluscent_Im ;
	public static BufferedImage background_Im ;
	
	
	/**
	 * une référence sur le {@link Controler} en mode Console de la {@link Party} 
	 */
	private PartyConsoleEngine myConsoleController ;
	/**
	 * une référence sur la {@link Party} qui est gérée par ce {@link Controler}
	 */
	private Party myParty  = null;
	/**
	 * la fenêtre du Jeu : elle Correspond à la Vue Graphique du Jeu
	 */
	private GameView myView = null ;
	/**
	 * le nombre de Joueur Reel
	 */
	private int numberOfRealPlayer;
	/**
	 * Le nombre de Joueur de la {@link Party}
	 */
	private int numberOfPlayer ;
	/**
	 * Les règles de la {@link Party}
	 */
	private JestEvalMethod rules ;
	/**
	 * La Liste de noms utilisés par les Joueurs Réels
	 */
	private ArrayList<String> NameList = new ArrayList<String>(4);
	/**
	 * La stategie des Joueurs virtuels
	 */
	private Strategy botStrategy ;
	/**
	 * Le style de Cartes avec lequel la {@link Party} sera Joué
	 */
	private DeckStyle deckStyle ;
	/**
	 * Les choix de L'utilisateur pour les règles
	 */
	private int[] customRules = {1,1,1,1,4};
	private int offerAlreadyMadeFlag = 0 ; //pour que le thread des bots se lance une seule fois
											// et pour arreter de prendre des offres à la fin d'une sequence
									//doit etre strictement inferieur au nombre de joueur reel
											//pour pouvoir continuer a prendre des offres dans une mm sequence
	
	private ArrayList<HashSet<CardSpot>> realPlayersJest = new ArrayList<HashSet<CardSpot>>();
	/**
	 * Le numéro de la dernière séquence de jeu : 4 par défaut car par défaut car une partie par est configurée pour 4 joueurs par défaut
	 */
	private int LAST_SEQUENCE=4;
	/**
	 * le numéro de séquence actuel
	 */
	private int sequenceNumber = 1 ;
	
	/**
	 * ce Thread va s'occuper de la réalisation des Offer par les Joueurs Virtuels
	 */
	private Runnable v_makeOffer ;
	/**
	 * ce Thread va s'occuper de la prise de Carte par les Joueur Virtuels
	 */
	private Runnable v_playerTakeCardHandler ;
	/**
	 * ce Thread s'occupe de preparer la {@link Party} pour la Prochaine Séquence ou bien il permet de lancer l'affichage des résultats
	 */
	private Runnable setUpNextSequence ; // CE THREAD VA METTRE EN PLACE LA PROCHAINE SEQUENCE ;
										//SI TOUS LES JOUEURS ONT PRIS UNE CARTE
	private Runnable publishResults ;
	/**
	 * ce booleen permet de savoir si la séquence s'est terminée pendant le tour d'un joueur virtuel
	 */
	private boolean endOfSequenceInV_PlayerThread = false ;
	
	//LE NOMBRE DE CLIC AU BOUT DUQUEL UNE SEQUENCE SE TERMINE 
	// est egal a 2* le nombre de joueur reel car cahque jouer 
	//fait une offre et prend une carte avant que la sequence se termine
	/**
	 * l'indice du prochain {@link Player} qui devra prendre une carte
	 */
	private int takeCardTargetIndexVar ;
	/**
	 * Le constructeur du controleur graphique
	 * @param partyModel la {@link Party} qui sera gérée par ce {@link Controler}
	 */
	public GraphicControler(Party partyModel) {
		this.myParty=partyModel;
		this.numberOfPlayer=Party.getGameNumberOfPlayer();
		this.numberOfRealPlayer=partyModel.getGameNumberOfRealPlayer();
		v_playerTakeCardHandler =  new Runnable() { 
			public void run() {
				try {
					//bien penser a dereferencer le playerOrdComp à la fin de chaque party
						if(myParty.getPlayOrdComp()!=null) {
							if(myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]) instanceof VirtualPlayer){
								//REFUSE THE TAKER TO TAKE HIS OWN CARD
									setEnabledPlayer(myParty.getPlayOrdComp().getPlayerOrder()[0], false);
									myView.getPlayerSpot().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getCardSpot(0).removeTakeCardListener();
									myView.getPlayerSpot().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getCardSpot(1).removeTakeCardListener();
								takeCardTargetIndexVar=((VirtualPlayer)myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0])).takeCard(myParty.getPlayers());
								//REPARIN THE TARGET SPOT VIEW
								reparePlayerSpotView(takeCardTargetIndexVar);
								// AFTER THE TAKER TOOK A CARD ALLOW THE TAKER TO HAVE A CARD TAKEN IF HIS OFFER STILL CONTAINS 2 CARDS
									if(myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getOffer().size()>1) {
										setEnabledPlayer(myParty.getPlayOrdComp().getPlayerOrder()[0], true);
										setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 0);
										setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 1);
		

									}
								//REFUSE THE TARGET TO TAKE HIS OWN CARD
								setEnabledPlayer(takeCardTargetIndexVar, false);
								myView.getPlayerSpot().get(takeCardTargetIndexVar).getCardSpot(0).removeTakeCardListener();
								myView.getPlayerSpot().get(takeCardTargetIndexVar).getCardSpot(1).removeTakeCardListener();
								if(!myParty.getPlayers().get(takeCardTargetIndexVar).alreadyTookACard()) {
									myParty.getPlayOrdComp().updateOrder(takeCardTargetIndexVar);
								}else {
										takeCardTargetIndexVar=myParty.nextPlayerNeedingCardIndex();
										if(takeCardTargetIndexVar>=0) {
											myParty.getPlayOrdComp().updateOrder(takeCardTargetIndexVar);
										}else {
											//target VAUT -1
											//SI ON ARRIVE LA  CA SIGNIFIE QUE TOUS LES JOUEURS ON PRIS UNE 
											//CARTE , IL FAUT DONC  LA SEQUENCE SUIVANTE
											//TODO ECRIRE UNE METHODE POUR CA 
											
										}
		
								}
								if((myParty.getPlayers().get(takeCardTargetIndexVar) instanceof VirtualPlayer)   ) {
									this.run();
								}else if(myParty.getPlayers().get(takeCardTargetIndexVar).alreadyTookACard()==false && iAmTheOnlyOption(takeCardTargetIndexVar)==true){
									//ALLOW THE TARGET TO TAKE HIS OWN CARDS IF IT IS THE ONLY POSSIBLILITY
									setEnabledPlayer(takeCardTargetIndexVar, true);
									setTakeCardExternListener(takeCardTargetIndexVar, 0);
									setTakeCardExternListener(takeCardTargetIndexVar, 1);
									//AFFICHAGE DE LA BOITE DE DIALOGUE SUR UN AUTRE THREAD POUR NE PAS BLOQUER 
									//LE PROGRAMME
										final int errorAvoidTargetVar =takeCardTargetIndexVar ;
										new Thread("NEXT_PLAYER_DIALOG") {
											public void run(){
												JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(errorAvoidTargetVar).getName()+" Please Take a Card !", "Take a Card", JOptionPane.INFORMATION_MESSAGE);
											}
										}.start();
								}else if(myParty.getPlayers().get(takeCardTargetIndexVar).alreadyTookACard()==false && iAmTheOnlyOption(takeCardTargetIndexVar)==false){
									//AFFICHAGE DE LA BOITE DE DIALOGUE SUR UN AUTRE THREAD POUR NE PAS BLOQUER 
									//LE PROGRAMME
									setEnabledPlayer(takeCardTargetIndexVar, false);
									myView.getPlayerSpot().get(takeCardTargetIndexVar).getCardSpot(0).removeTakeCardListener();
									myView.getPlayerSpot().get(takeCardTargetIndexVar).getCardSpot(1).removeTakeCardListener();
		
									final int errorAvoidTargetVar =takeCardTargetIndexVar ;
									new Thread("NEXT_PLAYER_DIALOG") {
										public void run(){
											JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(errorAvoidTargetVar).getName()+" Please Take a Card !", "Take a Card", JOptionPane.INFORMATION_MESSAGE);
										}
									}.start();					
								}
							}
						}
						
				}catch (ArrayIndexOutOfBoundsException e3) {
					// TODO: handle exception
					endOfSequenceInV_PlayerThread=true ;
					Thread nextSeqMaker = new Thread(setUpNextSequence,"SET_NEXT_SEQUENCE");
					setEnabledPlayer(0, true);

					nextSeqMaker.start();
						try {
							nextSeqMaker.join();
							myParty.setSequenceStrated(false);
							
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
				}	
			}
		};
		
		setUpNextSequence = new Runnable() {	
			public  void run() {
				if(Party.getGameNumberOfPlayer()==3) {
					LAST_SEQUENCE=5;
				}else {
					LAST_SEQUENCE=4;
				}
				sequenceNumber=myParty.sequenceNumber();
			
				if(myParty.theyAllTookOneCard()==true ) {
					
					if(myParty.getPartyDeck().size()>0) {
						myParty.buildStack();
						myParty.fillOffers();
					}
					for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
						myParty.getPlayers().get(k).getOffer().hideAllCards();
					}
					
					if (sequenceNumber<LAST_SEQUENCE) {
						//ENABLING  SHOW JEST BUTTONS
						myView.getPlayerSpot().get(0).getShowJestBtn().setEnabled(true);
						setEnabledPlayer(0, true);
						myParty.getPlayers().get(0).getOffer().showAllCards();
						setmakeOfferExternListener(0, 0);
						setmakeOfferExternListener(0, 1);
						

						new Thread("MAKE_OFFER_MSG") {
							public void run() {
						JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(0).getName()+" Please Hide a Card !", "Make An Offer", JOptionPane.INFORMATION_MESSAGE);
							}
						}.start();
						offerAlreadyMadeFlag=0;
					}else {
						for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
							if(k<myParty.getGameNumberOfRealPlayer()) {
								//ON PEUT VOIR LE JEST DE TOUT LE MONDE A LA FIN DE LA PARTIE
								myView.getPlayerSpot().get(k).getShowJestBtn().setEnabled(true);

								//CHAQUE JOUEUR LA CARTE QUI RESTE DANS SON OFFRE
								myParty.getPlayers().get(k).getOffer().showCard(0);
								//ON AJOUTE LA CARTE AU JEST GRAPHIQUE DU JOUEUR
								RegularCard  tmpCard = myParty.getPlayers().get(k).getOffer().getCard();
								tmpCard.setChown(true);
								tmpCard.setCardName();
								realPlayersJest.get(k).add(new CardSpot(tmpCard.getCardName()));
							}
							myParty.getPlayers().get(k).getJest().addCard(myParty.getPlayers().get(k).getOffer().giveCard());
							myView.getPlayerSpot().get(k).getCardSpot(0).setSpotImage("spot");
							updatePlayerSpotView(k);
						}

						//PUBLISH RESULTS
						Thread publishResultsThread =  new Thread(publishResults,"PUBLISH_RESULT_THREAD");
						publishResultsThread.start();

						try {
							publishResultsThread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
								
						
					}
					
					
				}
				
			}
		};
	
		v_makeOffer = new Runnable() {
			@Override
			public  void run() {
				for(int k =myParty.getGameNumberOfRealPlayer(); k<Party.getGameNumberOfPlayer();k++) {
					((VirtualPlayer)myParty.getPlayers().get(k)).makeOffer();
					updatePlayerSpotView(k);
				}
			}
		};
		
		publishResults = new Runnable() {	
			@Override
			public void run() {
				synchronized (myParty) {
					// TODO Auto-generated method stub
					Party.getPartyTrophies().attributeTrophies(myParty.getPlayers());
					myParty.setPartyWinner(myParty.setupResults(myParty.getPlayers()));
					
					for(int k =myParty.getGameNumberOfRealPlayer(); k<Party.getGameNumberOfPlayer();k++) {
						myView.getPlayerSpot().get(k).getShowJestBtn().addActionListener(new ShowJestListener(k));
						//DISABLING ALL SHOW JEST BUTTONS
						myView.getPlayerSpot().get(k).getShowJestBtn().setEnabled(true);
					}
					
					new Thread("ANNOUNCE_WINNER_DIALOG") {
						public void run(){
							
							//RESTART PARTY
							
							
							JOptionPane.showMessageDialog(myView,"WE HAVE A NEW BOSS \n"+myParty.getPartyWinner().getName()+" :: "+myParty.getScoreBoard().get(myParty.getPartyWinner())+" POINTS \nCONGRATULATION SIR"
									+ "\n\n-----THE SCORE BOARD------\n"+myParty.getScoreBoard().toString(), "Information", JOptionPane.INFORMATION_MESSAGE);	
							
						    int response = JOptionPane.showConfirmDialog(null, "Do you want to Restart?", "Confirm",
						        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						    if (response == JOptionPane.NO_OPTION) {
						      System.out.println("No button clicked");
						      System.out.println("GraphicThread state "+Launcher.GraphicThread.getState());
								System.out.println("ConsoleThread state "+Launcher.consoleThread.getState());
						    } else if (response == JOptionPane.YES_OPTION) {
						      Launcher.restart();
						    } else if (response == JOptionPane.CLOSED_OPTION) {
						    	System.exit(5);
						    }
							

						}
					}.start();	
					JestTimer.getInstanceOfTimer(myParty).pause();
					Party.setViewWherePartyEnded(true);
					myParty.setPartyEnded(true);

					
					
					
					
				}
			}
		};
		
	}

	@Override
	public void beginParty() {
		
			if (myParty.isPartyStarted()==false && myParty.isPartyConfigured()==false) {
				initGraphicControler();
				configParty();
			}
			if(myParty.isPartyStarted()==false) {
				Party.setViewWherePartyStarted(true);
				myParty.setPartyStarted(true);
				myParty.setPartyDeck(Deck.getInstanceOfDeck(Party.getGameDeckStyle()));
				myParty.getPartyDeck().shuffleDeck();
				Party.setPartyTrophies(Trophies.getInstanceofTrophies(myParty.getPartyDeck()));
				myParty.distributeCards();
			}
			
			//POUR GERER LES BAILLES DE LA PARTIE EST CONFIGURE DANS UNE VUE ET EST DEMARRE DANS UNE AUTRE VUE
			if(Party.getPartyTrophies()==null) {
				Party.setPartyTrophies(Trophies.getInstanceofTrophies(myParty.getPartyDeck()));
			}
			
			loadImages(Party.getGameDeckStyle());

		
	
		myView.setTrophySpot( new TrophySpot(Party.getNumberOfCardsInTrophies()));
		Party.getPartyTrophies().getTrophyCard().get(0).setChown(true);
		Party.getPartyTrophies().getTrophyCard().get(0).setCardName();
		myView.getTrophySpot().setTropySpotImage(Party.getPartyTrophies().getTrophyCard().get(0).getCardName(),0);
		if(Party.getNumberOfCardsInTrophies()==2) {
			Party.getPartyTrophies().getTrophyCard().get(1).setChown(true);
			Party.getPartyTrophies().getTrophyCard().get(1).setCardName();
			myView.getTrophySpot().setTropySpotImage(Party.getPartyTrophies().getTrophyCard().get(1).getCardName(),1);
		}

		for(int k =0; k<myParty.getGameNumberOfRealPlayer();k++) {
			realPlayersJest.add(new HashSet<CardSpot>());
		}
		myView.setupPane((JPanel)GamePanel.getInstanceOfGamePan());
		myView.setupPane((JPanel)ConfigPan.getInstanceOfConfigPan());
		
		for(int k =0; k<myParty.getGameNumberOfRealPlayer();k++) {
			myView.getPlayerSpot().get(k).getShowJestBtn().addActionListener(new ShowJestListener(k));
			//DISABLING ALL SHOW JEST BUTTONS
			myView.getPlayerSpot().get(k).getShowJestBtn().setEnabled(false);
		}
		
		for (int k = 0 ; k<Party.getGameNumberOfPlayer() ; k++) {
			myView.getPlayerSpot().get(k).update(null, myParty.getPlayers().get(k).getOffer());
			myView.getPlayerSpot().get(k).getCardSpot(0).setCardSportPartyView(myParty);
			myView.getPlayerSpot().get(k).getCardSpot(0).setCardSpotGraphicControllerView(this);
			myView.getPlayerSpot().get(k).getCardSpot(1).setCardSportPartyView(myParty);
			myView.getPlayerSpot().get(k).getCardSpot(1).setCardSpotGraphicControllerView(this);

		}
		
		myParty.setNumberOfUserActionToEndASequence(2*myParty.getGameNumberOfRealPlayer());
		
			setEnabledPlayer(0, true);
			
			
			if(myParty.isSequenceStrated()==false) {
				//System.out.println("Message From Thread"+Thread.currentThread().getName());
	
					new Thread("BEGIN_PARTY_PLAYER#0_MESSAGE") {
						public void run() {
							JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(0).getName()+" Will Go First !!!\n\tPlease Hide a Card !!!", "Party Begin", JOptionPane.INFORMATION_MESSAGE);
						};
					}.start();
			}
			
			myParty.getPlayers().get(0).getOffer().showAllCards();
			//ENABLING ALL SHOW JEST BUTTONS
			myView.getPlayerSpot().get(0).getShowJestBtn().setEnabled(true);
			setmakeOfferExternListener(0, 0);
			setmakeOfferExternListener(0, 1);
		
		
		

	}

	@Override
	public void configParty() {
		
		synchronized (myParty) {
			myParty.setPartyConfigured(true);
			myParty.setGameNumberOfRealPlayer(numberOfRealPlayer);
			Party.setGameNumberOfPlayer(numberOfPlayer);
			Party.setRules(this.rules);
			Party.setVirtualPlayerStrategy(botStrategy);
			Party.setGameDeckStyle(deckStyle);
			myParty.getPartyDeck().shuffleDeck();
			if(Party.getGameNumberOfPlayer()==3) {
				Trophies.NumberOfCardsInTrophies=2;
				Party.setNumberOfCardsInTrophies(2);
				LAST_SEQUENCE=5;
			}else {
				LAST_SEQUENCE=4;
				Trophies.NumberOfCardsInTrophies=1;
				Party.setNumberOfCardsInTrophies(1);
			}
			Party.setViewWherePartyConfigured(true);
			createPlayers();	
			myParty.setChanged();
			myParty.notifyObservers();
			myParty.notifyAll();
		}
		
	}

	@Override
	public void sequenceLoop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPlayers() {
		// TODO Auto-generated method stub
		//myParty.getPlayers().
		int k=0;
		
		for( k = 0 ; k < myParty.getGameNumberOfRealPlayer() ; k++) { //CREATION DES JOUEURS REELS
			myParty.getPlayers().add(k,new RealPlayer(NameList.get(k),new StringBuffer())); //LE STRING BUFFER EST JUSTE INUTILE
			myParty.getPlayers().get(k).addObserver(myParty);

		}
		for( ; k < Party.getGameNumberOfPlayer() ; k++) {  //CREATION DES JOUEURS VIRTUELS
			myParty.getPlayers().add(k,new VirtualPlayer(this.botStrategy));
			myParty.getPlayers().get(k).addObserver(myParty);


		}
		myParty.setPlayersIterator(myParty.getPlayers().iterator());
		while(myParty.getPlayersIterator().hasNext()) {
			Player currentPlayer = myParty.getPlayersIterator().next() ;
			currentPlayer.setJest(new Jest(currentPlayer));
			currentPlayer.setOffer(new Offer(currentPlayer));
		}

	}

	@Override
	public void setParty() {
		// TODO Auto-generated method stub
		myParty.setPartyDeck(Deck.getInstanceOfDeck(Party.getGameDeckStyle()));
		myParty.getPartyDeck().shuffleDeck();
		Party.setPartyTrophies(Trophies.getInstanceofTrophies(myParty.getPartyDeck()));
		
		
		myParty.setChanged();
		myParty.notifyObservers();
	}

	@Override
	public Party getMyParty() {
		// TODO Auto-generated method stub
		return myParty;
	}
	
	public void setMyParty() {
		this.myParty=Party.getInstanceOfParty();
	}
	
	public void setNumberOfPlayer(int numberOfplayer) {
		if(numberOfplayer>=3 && numberOfplayer <= 4) {
			this.numberOfPlayer=numberOfplayer;
		}
	}
	
	public void setNumberOfRealPlayer(int numberOfRealplayer) {
		if(numberOfRealplayer>=0 && numberOfRealplayer <= 4) {
			this.numberOfRealPlayer=numberOfRealplayer;
		}
	}
	
	public void SetRules(JestEvalMethod pRules) {
		//faire attention a avoir appellé setCustomRule 5 fois dans le ConfigPan en cas se Custom rule
		this.rules=pRules;
	}
	/**
	 * cette methode permet d'ajouter de Noms à la liste des noms des Joueurs réels
	 * @param playerName le nom du Joueur
	 * @param position la position du Joueur dans la Liste de Joeur
	 */
	public void addName(String playerName,int position) {
		if(playerName.trim().equals("")) {
		this.NameList.add(position, "PLAYER#"+position);
		}else {
		this.NameList.add(position, playerName);
		}
	}
	/**
	 * Cette methode permet d'initialiser les paramètres de la {@link Party} avec la configuration par défaut
	 */
	public void initGraphicControler() {
		numberOfPlayer=4;
		numberOfRealPlayer=1;
		rules=new DefaultRules();
		NameList.add(0,"PLAYER#0");
		botStrategy=new NiveauMoyen();
		deckStyle=DeckStyle.DEFAULT;
		initRules();
	}
	
	public void setStrategy(Strategy strategy) {
		this.botStrategy=strategy;
	}
	
	public void setDeckStyle(DeckStyle pDeckStyle) {
		this.deckStyle=pDeckStyle;
	}
	
	public void setCustomRules(int RuleIndex, int decision) {
		this.customRules[RuleIndex]=decision;
	}
	/**
	 * Cette permet de reinitialiser les règles customisées en les ramenant aux règles par défaut
	 */
	public void initRules() {
		this.customRules[0]=1;
		this.customRules[1]=1;
		this.customRules[2]=1;
		this.customRules[3]=1;
		this.customRules[4]=4;
	}
	/**
	 * cette methode permet d'appliquer les règles customisées à la party
	 */
	public void applyCustomRules() {
		this.rules.setAceRule(this.customRules[3]);
		this.rules.setNegativeDiamonds(this.customRules[0]);
		this.rules.setBlackPairRule(this.customRules[2]);
		this.rules.setHeartsAndJokerRule(this.customRules[1]);
		this.rules.setJokerValue(this.customRules[4]);
	}
	
	public void setMyView(GameView pMyView) {
		this.myView=pMyView;
	}
	
	
	public PartyConsoleEngine getMyConsoleController() {
		return myConsoleController;
	}

	public void setMyConsoleController(PartyConsoleEngine myConsoleController) {
		this.myConsoleController = myConsoleController;
	}

	public GameView getMyView() {
		return myView;
	}

	public void setEnabledPlayer(int playerPosition,boolean status) {
		myView.getPlayerSpot().get(playerPosition).getCardSpot(0).setEnabled(status);
		myView.getPlayerSpot().get(playerPosition).getCardSpot(1).setEnabled(status);

	}
	
	public boolean isEnabledPlayer(int playerPosition) {
		if(myView.getPlayerSpot().get(playerPosition).getCardSpot(0).isEnabled()==true &&
		myView.getPlayerSpot().get(playerPosition).getCardSpot(1).isEnabled()==true) {
			return true;
		}else {return false;}

	}
	
	
	public void setmakeOfferExternListener(int playerPosition, int CardPosition ) {
		//LA METHODE ADDMOUSELISTENER A ETE SURCHARGE DANS CARDSPORT AVEC EN PARAMETE un GraphicControler.CardSpotMouseExternListener
		myView.getPlayerSpot().get(playerPosition).getCardSpot(CardPosition).addMakeOfferListener(new MakeOfferExternListener());
	}
	
	public void setTakeCardExternListener(int playerPosition, int CardPosition ) {
		//LA METHODE ADDMOUSELISTENER A ETE SURCHARGE DANS CARDSPORT AVEC EN PARAMETE un GraphicControler.CardSpotMouseExternListener
		myView.getPlayerSpot().get(playerPosition).getCardSpot(CardPosition).addTakeCardListener(new TakeCardExternListener());
	}
	/**
	 * cette methode permet de rafraichir le contenu de la main d'un {@link Player}
	 * @param playerPosition le {@link Player} dont la main doit être mise à jour
	 */
	public void updatePlayerSpotView(int playerPosition) {
		myParty.getPlayers().get(playerPosition).getOffer().setChanged();
		myParty.getPlayers().get(playerPosition).getOffer().notifyObservers(myParty.getPlayers().get(playerPosition).getOffer());
		myView.getPlayerSpot().get(playerPosition).getCardSpot(0).repaint();
		myView.getPlayerSpot().get(playerPosition).getCardSpot(1).repaint();
	}
	/**
	 * cette methode permet de savoir si le {@link Player} à pour seule possibilté de prendre une carte dans sa propre main
	 * @param playerPosition l'indice du {@link Player} en question
	 * @return true si les propres cartes du {@link Player} sont les seules options qui lui reste , renvoie false dans le cas  contraire
	 */
	public  boolean iAmTheOnlyOption(int playerPosition) {
		boolean result = true ;
			for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
				if(myParty.getPlayers().get(k).getOffer().size()>1 && myParty.getPlayers().get(k)!=myParty.getPlayers().get(playerPosition) ) {
					result=false;
				}
			}
		return result ;
	}
	/**
	 * changer l'image du spot d'un jouer qui a perdu une carte pour la remplacer par l'image d'un spot vide
	 * <b><u><i>ATTENTION L'IMPLEMENTATION AUTOUR DE CETTE METHODE EST SUGGETE A UN BUG</i></u></b>
	 * 
	 * @param playerPosition  le jouer ayant perdu une carte
	 */
	public void reparePlayerSpotView(int playerPosition) {
		//BECAUSE CARDS ARE CHANGING POSITION IN THE OFFER AFTER THE OFFER LOSE ONE CARD
			if(myParty.getPlayers().get(playerPosition).getOffer().size()<2) myView.getPlayerSpot().get(playerPosition).getCardSpot(1).setSpotImage("spot");
			
	}
	
	/**
	 * Cacher toutes les cartes de tous les {@link Player}
	 */
	public void hideAllCards() {
		for(int k = 0 ; k < myView.getPlayerSpot().size();k++) {
			myView.getPlayerSpot().get(k).getCardSpot(0).setSpotImage("hidden");
			myView.getPlayerSpot().get(k).getCardSpot(1).setSpotImage("hidden");
		}
		myView.repaint();
	}
	/**
	 * remplacer toutes les images de tous les {@link PlayerSpot} par des images de spot Vide
	 */
	public void cleanPlayerSpots() {
		for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
			myView.getPlayerSpot().get(k).getCardSpot(0).setSpotImage("spot");
			myView.getPlayerSpot().get(k).getCardSpot(1).setSpotImage("spot");
			updatePlayerSpotView(k);
		}
	}
	/**
	 * charger toues les images de la Partie pour acceler le GamePlay
	 * @param pDeckStyle
	 */
	public void loadImages(DeckStyle pDeckStyle) {
		String ROOT = "Files/images/deckstyle/"+pDeckStyle.toString()+"/" ;
		String EXTENSION = ".png" ;


		try {
			clubs_2_Im=ImageIO.read(new File(ROOT+"2_clubs"+EXTENSION));
			clubs_3_Im=ImageIO.read(new File(ROOT+"3_clubs"+EXTENSION));
			clubs_4_Im=ImageIO.read(new File(ROOT+"4_clubs"+EXTENSION));
			clubs_as_Im=ImageIO.read(new File(ROOT+"as_clubs"+EXTENSION));
			
			spades_2_Im=ImageIO.read(new File(ROOT+"2_spades"+EXTENSION));
			spades_3_Im=ImageIO.read(new File(ROOT+"3_spades"+EXTENSION));
			spades_4_Im=ImageIO.read(new File(ROOT+"4_spades"+EXTENSION));
			spades_as_Im=ImageIO.read(new File(ROOT+"as_spades"+EXTENSION));
			
			diamonds_2_Im=ImageIO.read(new File(ROOT+"2_diamonds"+EXTENSION));
			diamonds_3_Im=ImageIO.read(new File(ROOT+"3_diamonds"+EXTENSION));
			diamonds_4_Im=ImageIO.read(new File(ROOT+"4_diamonds"+EXTENSION));
			diamonds_as_Im=ImageIO.read(new File(ROOT+"as_diamonds"+EXTENSION));
			
			hearts_2_Im=ImageIO.read(new File(ROOT+"2_hearts"+EXTENSION));
			hearts_3_Im=ImageIO.read(new File(ROOT+"3_hearts"+EXTENSION));
			hearts_4_Im=ImageIO.read(new File(ROOT+"4_hearts"+EXTENSION));
			hearts_as_Im=ImageIO.read(new File(ROOT+"as_hearts"+EXTENSION));
			
			ref_Im=ImageIO.read(new File(ROOT+"ref"+EXTENSION));
			spot_Im=ImageIO.read(new File(ROOT+"spot"+EXTENSION));
			hidden_Im=ImageIO.read(new File(ROOT+"hidden"+EXTENSION));
			joker_Im=ImageIO.read(new File(ROOT+"joker"+EXTENSION));
			
			transluscent_Im=ImageIO.read(new File("Files/images/transparent.png"));
			background_Im=ImageIO.read(new File("Files/images/fond/backgnd.jpg"));
			
		}catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	
	


	public class MakeOfferExternListener implements MouseListener {
		
		//IMPORTANT DE SAVOIR QUE L'ACTION PROVIENT DE QUEL SPOT ET DE QUEL CARTE
		private int playerPosition ;
		private int cardPosition ;
		@Override
		public  void mouseClicked(MouseEvent e) {
			
			Thread vPlayerMakeOffer = new Thread(v_makeOffer,"V_player_MakeOffer");
			
			if(myParty.getPlayers().get(playerPosition).getOffer().getCard(cardPosition).getCardName().equals("hidden")==false && isEnabledPlayer(playerPosition)==true && offerAlreadyMadeFlag<myParty.getGameNumberOfRealPlayer()) {
				int nextPosition = playerPosition+1;
				

				//TODO GErer l'outofBound
				if(playerPosition==Party.getGameNumberOfPlayer()-1 ||(playerPosition==myParty.getGameNumberOfRealPlayer()-1 && myParty.getGameNumberOfRealPlayer()!=Party.getGameNumberOfPlayer())) {
					nextPosition=0;
				}
				//System.out.println("Hide Card executed For real");System.out.println("Make offer executed For real");
				myParty.setNumberOfUserActionToEndASequence(myParty.getNumberOfUserActionToEndASequence()-1);
				
				myParty.getPlayers().get(playerPosition).getOffer().hideCard(cardPosition);
				myParty.update(null,null);
				myParty.getPlayers().get(playerPosition).setAlreadyTookACard(false);
				setEnabledPlayer(playerPosition, false);
				
				
				if((myParty.getPlayers().get(nextPosition)instanceof VirtualPlayer)==false) {
					//DISABLING  SHOW JEST BUTTONS
					myView.getPlayerSpot().get(playerPosition).getShowJestBtn().setEnabled(false);
					myView.getPlayerSpot().get(playerPosition).getCardSpot(cardPosition).removeMakeOfferListener();
					myView.getPlayerSpot().get(playerPosition).getCardSpot(Math.abs((int)Math.floor((cardPosition/2.0)-0.5))).removeMakeOfferListener();
					

				
					if(nextPosition!=0) {
						setmakeOfferExternListener(nextPosition, cardPosition);
						setmakeOfferExternListener(nextPosition, Math.abs((int)Math.floor((cardPosition/2.0)-0.5)));
						//ENABLING  SHOW JEST BUTTONS
						myView.getPlayerSpot().get(nextPosition).getShowJestBtn().setEnabled(true);
						setEnabledPlayer(nextPosition, true);
						updatePlayerSpotView(nextPosition);	
					}
					myView.getPlayerSpot().get(playerPosition).getCardSpot(cardPosition).setSpotImage("hidden");
					myView.getPlayerSpot().get(playerPosition).getCardSpot(Math.abs((int)Math.floor((cardPosition/2.0)-0.5))).setSpotImage("hidden");
					
					myView.getPlayerSpot().get(playerPosition).getCardSpot(cardPosition).repaint();
					myView.getPlayerSpot().get(playerPosition).getCardSpot(Math.abs((int)Math.floor((cardPosition/2.0)-0.5))).repaint();

					if(offerAlreadyMadeFlag<myParty.getGameNumberOfRealPlayer()-1) {
						myParty.getPlayers().get(nextPosition).getOffer().showAllCards();
					}
					
					
				}
				
				if(offerAlreadyMadeFlag==myParty.getGameNumberOfRealPlayer()-1) {
					//CE QUI SE PASSE APRES QUE TOUS LES JOUEURS REELS AIENT FAIT LEUR OFFRE
					vPlayerMakeOffer.start();
					try {
						vPlayerMakeOffer.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
							updatePlayerSpotView(k);
						}
							//MISE EN PLACE DE LA PRISE DE CARTE PAR LES JOUEURS
							myParty.setPlayOrdComp(new PlayerOrderComparator(myParty.getPlayers()));
							myParty.getPlayOrdComp().setOrder();
							
						for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
							//PAS NECESSAIRE DE GERER LE CAS OU LE JOUEUR EN TETE DANS L'ORDRE EST LA SEULE POSSIBILITE CAR ON VIE DE COMMENCER LA PRISE DE CARTE
							//IL YA FORCEMENT DES CIBLES , CEST POUR CA QUE JE NE LUI PAS LA POSSIBILTE DE PENDRE UNE CARTE CHEZ LUI MM A TRAVERS CETTE CONDITION
							if(myParty.getPlayers().get(k)!=myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0])) {
								setTakeCardExternListener(k,0);
								setTakeCardExternListener(k,1);
								setEnabledPlayer(k, true);
								//DISABLING  SHOW JEST BUTTONS ALL THE PLAYER NOT TAKING A CARD
								//nextPosition=0 ici normalement
								myView.getPlayerSpot().get(k).getShowJestBtn().setEnabled(false);
							}else {
								//ENABLING  SHOW JEST BUTTONS  THE PLAYER  TAKING A CARD
								//nextPosition=0 ici normalement
								myView.getPlayerSpot().get(k).getShowJestBtn().setEnabled(true);
							}
						}
							
						//BECAUSE THE FIRST TAKER COULD BE A VIRTUAL PLAYER
						Thread fistTakeCard = new Thread(v_playerTakeCardHandler,"V_Player_Taking_A_Card_First");
						try {
							fistTakeCard.start();
							fistTakeCard.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						updatePlayerSpotView(myParty.getPlayOrdComp().getPlayerOrder()[0]);

						if((myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]) instanceof VirtualPlayer )==false) {
							JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getName()+" Please Take a Card !", "Take a Card", JOptionPane.INFORMATION_MESSAGE);
						}


							
						
				}
				
				if((myParty.getPlayers().get(nextPosition)instanceof VirtualPlayer)==false && nextPosition!=0) {

					JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(playerPosition+1).getName()+" Please Hide a Card !", "Hide a Card", JOptionPane.INFORMATION_MESSAGE);
				}
				offerAlreadyMadeFlag++;
			}
		}
		
		

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub	
		}

		public void setPlayerPosition(int playerPosition) {
			this.playerPosition = playerPosition;
		}

		public void setCardPosition(int cardPosition) {
			this.cardPosition = cardPosition;
		}
	}
	
	public class TakeCardExternListener implements MouseListener {
		
		private int targetPosition ;
		private int targetCardPosition ;

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				if(myView.getPlayerSpot().get(targetPosition).getCardSpot(targetCardPosition).isEnabled()) {
				
				//ON REFUSE QUE LE JOUERUR REEL SE SERVE CHEZ LUI MM 
				// ON VEROUILLE SON OFFRE AVANT SON CHOIX
				myParty.getPlayers().get(targetPosition).getOffer().showCard(targetCardPosition);
				//ON AJOUTE LA CARTE AU JEST GRAPHIQUE DU JOUEUR
				realPlayersJest.get(myParty.getPlayOrdComp().getPlayerOrder()[0]).add(new CardSpot(myParty.getPlayers().get(targetPosition).getOffer().getCard(targetCardPosition).getCardName()));
				//ON AJOUTE LA CARTE AU JEST DU JOUEUR
				myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getJest().addCard(myParty.getPlayers().get(targetPosition).getOffer().giveCard(targetCardPosition));
				myParty.update(null,null);
				


				myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).setAlreadyTookACard(true);
				if(myParty.theyAllTookOneCard()==true) {
					//APRES QUE CHAQUE JOUEUR REEL AIT PRIS UNE CARTE ON VERIFIE SI LA SEQUENCE EST TERMINEE
					Thread nextSeqMaker = new Thread(setUpNextSequence,"SET_NEXT_SEQUENCE");
						try {
							setEnabledPlayer(0, true);
							nextSeqMaker.start();
							nextSeqMaker.join();
							//NOTIFICATION DE LA CONSOLE QUE LA SEQUENCE EST TERMINE EN INTERFACE GRAPHIQUE
							myParty.setSequenceStrated(false);
							
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				
				
				//ON DEVEROUILLE L'OFFRE DU JOUER REELE SI IL PEUT ENCORE DONNER UNE CARTE
				//SI SON OFFFRE EST COMPLETLE 
				//POUR PERMETTRE AUX AUTRE JOUEURS REELS DE SE SERVIR
				if(myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getOffer().size()>1) {
					setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 0);
					setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 1);
					setEnabledPlayer(myParty.getPlayOrdComp().getPlayerOrder()[0], true);
				}
				
			reparePlayerSpotView(targetPosition);
			
				
				//REFUSING THE VICTIM TO BE ABUSED ONE MORE TIME IN A ROW
				myView.getPlayerSpot().get(targetPosition).getCardSpot(0).removeTakeCardListener();
				myView.getPlayerSpot().get(targetPosition).getCardSpot(1).removeTakeCardListener();
				setEnabledPlayer(targetPosition, false);
				//UPDATE THE PLAYER ORDER
				if(!myParty.getPlayers().get(targetPosition).alreadyTookACard()) {
					myParty.getPlayOrdComp().updateOrder(targetPosition);
				}else {
					int targetIndex=myParty.nextPlayerNeedingCardIndex();
					if(targetIndex>=0) {
						myParty.getPlayOrdComp().updateOrder(targetIndex);
					}
				}
				
				
				if((myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]) instanceof VirtualPlayer) &&  (myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).alreadyTookACard()==false) ) {
					Thread targetTakeCard =new Thread(v_playerTakeCardHandler,"Target_Take_Card_Thread");
						try {
							//System.out.println("thread targetTakeCard is spwan in thread :: "+Thread.currentThread().getName());
							targetTakeCard.start();
							targetTakeCard.join();

							//permet de bloquer le thread event-queue machin apres que le dernier 
							//joueur virtuel ait pris une carte
							if(endOfSequenceInV_PlayerThread==true) {
								endOfSequenceInV_PlayerThread=false;
								
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				if(  ((myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]) instanceof VirtualPlayer)==false)   &&   (myParty.getPlayers().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).alreadyTookACard()==false) ) {
					//ON EMPECHE LE PROCHAIN JOUEUR REEL DE PRENDE SA PROPRE CARTE 
					//SAUF SI C'EST LA SEULE POSSIBLITE
					myView.getPlayerSpot().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getCardSpot(0).removeTakeCardListener();
					myView.getPlayerSpot().get(myParty.getPlayOrdComp().getPlayerOrder()[0]).getCardSpot(1).removeTakeCardListener();
					setEnabledPlayer(myParty.getPlayOrdComp().getPlayerOrder()[0], false);
					if(iAmTheOnlyOption(myParty.getPlayOrdComp().getPlayerOrder()[0])) {
						setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 0);
						setTakeCardExternListener(myParty.getPlayOrdComp().getPlayerOrder()[0], 1);
						setEnabledPlayer(myParty.getPlayOrdComp().getPlayerOrder()[0], true);
	
					}
					//MESSAGE INFORMANT LE PROCHAIN JOUEUR REEL QUE C'EST A LUI DE PRENDRE UNE CARTE
					final int errorAvoidTargetVar =myParty.getPlayOrdComp().getPlayerOrder()[0] ;
					new Thread("NEXT_PLAYER_DIALOG") {
						public void run(){
							JOptionPane.showMessageDialog(myView, myParty.getPlayers().get(errorAvoidTargetVar).getName()+" Please Take a Card !", "Take a Card", JOptionPane.INFORMATION_MESSAGE);
						}
					}.start();
				}
			}
			}catch (ArrayIndexOutOfBoundsException e3) {
				// TODO: handle exception
				Thread nextSeqMaker = new Thread(setUpNextSequence,"SET_NEXT_SEQUENCE");
				setEnabledPlayer(0, true);
				nextSeqMaker.start();
					try {
						nextSeqMaker.join();
						myParty.setSequenceStrated(false);

						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public int getTargetPosition() {
			return targetPosition;
		}

		public int getTargetCardPosition() {
			return targetCardPosition;
		}

		public void setTargetPosition(int targetPosition) {
			this.targetPosition = targetPosition;
		}

		public void setTargetCardPosition(int targetCardPosition) {
			this.targetCardPosition = targetCardPosition;
		}
	
	}
	
	class ShowJestListener implements ActionListener{
		private int playerIndex ; 
		public ShowJestListener(int pPlayerIndex) {
			super();
			this.playerIndex=pPlayerIndex;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JestVisualizer jestDialog = new JestVisualizer(myView,myParty.getPlayers().get(playerIndex).getName());
			

			java.util.Iterator<RegularCard> cardIt= myParty.getPlayers().get(playerIndex).getJest().getMyJestCards().iterator();
			while (cardIt.hasNext()) {
				RegularCard tmpCard = cardIt.next();
				tmpCard.setChown(true);
				tmpCard.setCardName();
				realPlayersJest.get(playerIndex).add(new CardSpot(tmpCard.getCardName()));
			}
			
			java.util.Iterator<CardSpot> SpotIt= realPlayersJest.get(playerIndex).iterator();
			while(SpotIt.hasNext()) {
				CardSpot tmpSpotVar = SpotIt.next();
				boolean exist = false ;
				Component[] dialogComponents =  jestDialog.getContentPane().getComponents();
				for(int k=0;k<dialogComponents.length;k++) {
					if(  tmpSpotVar.equals(((CardSpot)dialogComponents[k])) ) {
						exist=true;
					}
				}
				
				if (exist==false) jestDialog.addJestCard(tmpSpotVar);
			}
			
			jestDialog.showDialog();
		}
		
	}

	
	

}
