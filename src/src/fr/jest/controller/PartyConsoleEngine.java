package fr.jest.controller;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.util.Observable;
import java.util.Observer;

import fr.jest.Launcher;
import fr.jest.console.RealPlayer;
import fr.jest.model.CustomRules;
import fr.jest.model.Deck;
import fr.jest.model.DefaultRules;
import fr.jest.model.Jest;
import fr.jest.model.JestBoolean;
import fr.jest.model.LigthRules;
import fr.jest.model.NiveauFacile;
import fr.jest.model.NiveauMoyen;
import fr.jest.model.Offer;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.PlayerOrderComparator;
import fr.jest.model.Trophies;
import fr.jest.model.VirtualPlayer;
/**
 * Cette classe est le {@link Controler} pour le mode Console du Jeu.<br>
 * @author Jeff Jordan
 * @see Runnable
 * @see Observer
 * @see Controler
 *
 */
public class PartyConsoleEngine implements Runnable,Observer,Controler{
	/**
	 * La {@link Party} gérée sur par le {@link Controler}
	 */
	private Party myParty = null ;
	/**
	 * Drapeau signalant que  la methode {@link Controler#sequenceLoop()} est arrivée à son terme normalement 
	 */
	private boolean endSequenceLoop = false ;
	
	/**
	 * Le flux d'entrée des donnée depuis l'entrée standard
	 */
	public static BufferedReader input= new BufferedReader(
            new InputStreamReader(
                    Channels.newInputStream(
                    (new FileInputStream(FileDescriptor.in)).getChannel())));
	/**
	 * l'instance du Controleur Console :  une partie  ne peut instanciée qu'un seul Controleur à la fois
	 */
	private static PartyConsoleEngine myInstance = null ;
	public static int counter = 0 ;
	
	/**
	 * Verrou permettant de gerer la lecture des entrées de l'utilisateur de manière asynchrone
	 * <br> le Thread qui attend les entrées de l'utilisateur  set ce flag ce qui permet de faire de sortir d'une boucle d'attente
	 */
	private static boolean  IOLockFlag = false ;
	/**
	 * Verrou permettant de gerer la lecture des entrées de l'utilisateur de manière asynchrone
	 * <br> le Thread qui attend les entrées de l'utilisateur  set ce flag ce qui permet de faire de sortir d'une boucle d'attente
	 */
	private JestBoolean LockObject = new JestBoolean(false);
	/**
	 * Non implémenté
	 */
	private StringBuffer consoleView = new StringBuffer(500);
	/**
	 * Ce Runnable s'occupe d'attendre les entrées de l'utilisateur
	 */
	private Runnable AsyncIO ;
	/**
	 * Le controleur graphique de la {@link Party} : en effet les deux vues doivent communiquer entre elles
	 */
	private GraphicControler myGraphicContoller;
	
	/**
	 * constructeur du {@link Controler}
	 * @param pMyParty la partie qui est gérée par le {@link Controler}
	 */
	private  PartyConsoleEngine(Party pMyParty) {
		
		AsyncIO = new Runnable() {	
			@Override
			public void run() {
				Party.clrscr();
					System.out.println("PRESS 'ENTER' TO CONTINUE !!!");
					try {
						input.readLine();
					} catch (IOException e) {e.printStackTrace();}
					IOLockFlag =true;
					LockObject.setBooleanValue(true);
				
				
			}
		};
		this.myParty=pMyParty;
	}
	/**
	 * renvoie une référence sur un {@link PartyConsoleEngine}
	 * @param pMyParty  la partie qui est gérée par ce {@link Controler}
	 * @return renvoie une Instance sur un {@link PartyConsoleEngine}
	 */
	public static PartyConsoleEngine getInstanceOfEngine(Party pMyParty) {
		if(myInstance==null) {
			counter++;
			myInstance= new PartyConsoleEngine(pMyParty);
		}
		return PartyConsoleEngine.myInstance;
	}
	
	
	
	/**
	 * cette methode permet de démarrer une {@link Party} dans un {@link Thread} en appellant la methode {@link Controler#beginParty()}
	 */
	@Override
	public void run(){		
		try {
			this.beginParty();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Exiting Thread " +Thread.currentThread().getName());
			return;
			}
	}
	
	
	/**
	 * cette methode constitue le reel moteur du Jeu : elle 
	 * s'occupe de :
	 * <ul>
	 * <li>Configuer la {@link Party} si elle n'a pas encore été configurée</li>
	 * <li>Instancier les {@link Player} et les attributs de la {@link Party} </li>
	 * <li>Lancer les séquences</li>
	 * <li>publier les resultats</li>
	 * <li>Recommencer la Party eventuellement à la fin</li>
	 * </ul>
	 */
	public  void beginParty() {
		System.out.print("\tHELLO  !!!  WELCOME TO JESTSET GAME \n\t");
		Thread startConsole = new Thread(AsyncIO,"Console_Begin_Party_AsyncIO");
		startConsole.start();
		
			//On continu seulement si Thread AsynIO a eu le verrou en premier
			//Sinon on attend 
			//on sait si le thread AsyncIO a eu le verrou en premier en fonction de la valeur du boolean 
			//LockObject
			while(LockObject.booleanValue()==false && myParty.isPartyEnded()==false) {
			//	System.out.print("*");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			LockObject.setBooleanValue(false);
			while(IOLockFlag==false && myParty.isPartyEnded()==false) {
				System.out.print("#");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
			IOLockFlag=false;
			if(myParty.isPartyEnded()) {
				System.out.println("PARTY ENDED IN ANOTHER VIEW");
				//this.publishResults();
				return;
			}
		
		
		/*try {
			this.input.readLine();

		} catch (IOException e) {
			throw new ClosedByInterruptException();
		}*/
		
		synchronized (myParty) {
				if(myParty.isPartyConfigured()==false) {
					System.out.print("\tDo you Want to Configure the Party ('0'-->'No' -- '1'-->'Yes')  :: ");
					if(readChoice(0,1)==1) {
						myParty.setPartyConfigured(true);
						Party.setViewWherePartyConfigured(false);
						this.configParty();
		
					}
				}
				
				if(  Party.getViewWherePartyConfigured()==false && ( myParty.isPartyConfigured()==false || myParty.isPartyStarted()==false) ){
					
					this.setParty();
					this.createPlayers();					
				}
				if(myParty.isPartyStarted()==false) {
					myParty.setPartyStarted(true);
					Party.setViewWherePartyStarted(false);
				}
				//POUR GERER LES BAILLES DE LA PARTIE EST CONFIGURE DANS UNE VUE ET EST DEMARRE DANS UNE AUTRE VUE
				if(Party.getPartyTrophies()==null) {
					Party.setPartyTrophies(Trophies.getInstanceofTrophies(myParty.getPartyDeck()));
				}
				if(Party.getViewWherePartyStarted()==false) {
				myParty.distributeCards();
				myParty.notifyAll();
				}
		}
		
			this.sequenceLoop();
		
		if(Party.getViewWherePartyEnded()==false) {
			this.publishResults();
			System.out.println("Do You Want to Restart ?\t '0' --> No  ; '1' --> Yes");
			int choice = readChoice(0, 1);
			if(choice == 1 ) {
				Launcher.restart();
				System.out.println("Exiting Previous Console Thread");
			}else {
				System.exit(0);
			}
		}
		
	}
	
	public  void configParty() {
		System.out.print("PARTY CONFIGURATION\n\tPlease enter the number Of   Players ( '3' OR '"+myParty.getMAXPLAYER()+"' ) :: ");
		Party.setGameNumberOfPlayer(readChoice(3,myParty.getMAXPLAYER()));
		System.out.print("\tplease enter the number Of Human  Players ( FROM '0' TO '2' ) \n\t\t [!!!]Other players will be Virtual players !!! \n\t\t Number of Human Player :: ");
		myParty.setGameNumberOfRealPlayer(readChoice(0,2));
		
		if(Party.getGameNumberOfPlayer()==4) {
			Party.setNumberOfCardsInTrophies(1);
		}else {
			Party.setNumberOfCardsInTrophies(2);
		}
		
		System.out.print("\tplease select the rules you want to play with ::\n\t\t --> '1' == 'DEFAULT RULES' --STANDARD RULES--\n\t\t --> '2' == 'LIGTH RULES'   --NO NEGATIVE POINTS & NO JOKER RULE--\n\t\t --> '3' == 'CUSTOM RULES'\n\t\t Your Choice ::  ");
		int choice = readChoice(1,3) ;
		if(choice ==1) {
			Party.setRules(new DefaultRules());
		}else if (choice == 2) {
			Party.setRules(new LigthRules());

		}else {
			Party.setRules(new CustomRules());

		}
		
		System.out.print("\tplease enter the difficulty of Virtual players ::\n\t\t --> '1' == 'EASY'\n\t\t --> '2' == 'MEDIUM'\n\t\t Your Choice ::  ");
		if(readChoice(1,2)==1) {
			Party.setVirtualPlayerStrategy(new NiveauFacile());
		}else {
			Party.setVirtualPlayerStrategy(new NiveauMoyen());
		}


	}
	
	public   void sequenceLoop()  {
		myParty.setPlayOrdComp( new PlayerOrderComparator(myParty.getPlayers()));
		Thread sequenceStart ;
				while((Party.getGameNumberOfPlayer()==4 && myParty.sequenceNumber()<=4)||(Party.getGameNumberOfPlayer()==3 && myParty.sequenceNumber()<=5)) {
					//HANDLING WHEN PARTY ENDED IN ANOTHER VIEW
					sequenceStart=new Thread(AsyncIO,"Sequence_Begin_AsyncIO");
					sequenceStart.start();
						//On continu seulement si Thread AsynIO a eu le verrou en premier
						//Sinon on attend 
						//on sait si le threas AsyncIO a eu le verrou en premier en fonction de la valeur du boolean 
						//LockObject
					while(LockObject.booleanValue()==false && myParty.isPartyEnded()==false) {
						//System.out.print("*");
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					LockObject.setBooleanValue(false);
						
						while(IOLockFlag==false && myParty.isPartyEnded()==false) {
							System.out.print("#");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}						}
						IOLockFlag=false;
					
					
					if(myParty.isPartyEnded()) break ;

		
						
			synchronized (myParty) {
								if (myParty.isSequenceStrated()==false) {
									myParty.setSequenceStrated(true);
									myParty.partyNotOver(myParty.getPartyDeck());

									System.out.println("*****SEQUENCE#0"+myParty.sequenceNumber()+"***********");
									System.out.println("Cards in the deck "+myParty.getPartyDeck().size()+" cards");

			

								Party.setBroadCast("");
								//System.out.println("[!!!]************ "+myParty.getPartyDeck().size()+" CARDS REMAINING IN THE DECK!!!****************");
								//this.consoleView.append("[!!!]************ "+myParty.getPartyDeck().size()+" CARDS REMAINING IN THE DECK!!!****************\n");
								if((Party.getGameNumberOfPlayer()==4 && myParty.sequenceNumber()<4)||(Party.getGameNumberOfPlayer()==3 && myParty.sequenceNumber()<5)) {
									/*EVERYBOBY MAKES AN OFFER*/
									for(Player cur : myParty.getPlayers()) {
										if(cur instanceof RealPlayer) {
											((RealPlayer)cur).makeOffer();
										}else if(cur instanceof VirtualPlayer) {
											((VirtualPlayer)cur).makeOffer();
										}
										//myParty.update(null, null);
									}
									myParty.getPlayOrdComp().setOrder();
									//myParty.update(null, null);
									//playOrdComp.printOrder();
									/*END OF OFFERS*/
									
									/*EVERYBOBY TAKES ONE CARD*/
									while(myParty.theyAllTookOneCard()==false) {
										myGraphicContoller.hideAllCards();

										int targetIndex=0;
										if(myParty.getPlayOrdComp().nextPlayer() instanceof RealPlayer) {
											targetIndex=((RealPlayer)myParty.getPlayOrdComp().nextPlayer()).takeCard(myParty.getPlayers());
					
										}else if(myParty.getPlayOrdComp().nextPlayer() instanceof VirtualPlayer) {
											targetIndex=((VirtualPlayer)myParty.getPlayOrdComp().nextPlayer()).takeCard(myParty.getPlayers());
										}
										
										//myParty.update(null, null);
									
									if(!myParty.getPlayers().get(targetIndex).alreadyTookACard()) {
										myParty.getPlayOrdComp().updateOrder(targetIndex);
									}else {
										targetIndex=myParty.nextPlayerNeedingCardIndex();
										if(targetIndex>=0) {
											myParty.getPlayOrdComp().updateOrder(targetIndex);
										}
									}
									}
									/*END OF THE DEAL*/
									if(myParty.getPartyDeck().size()>0) {
										//pour des raison de coherence avec GUI
										myParty.buildStack();
										myParty.fillOffers();
										for(Player cur : myParty.getPlayers()) {	
											cur.getOffer().hideAllCards();
										}
										myParty.getPlayers().get(0).getOffer().showAllCards();
									}else {
										endSequenceLoop=true;
									}
									//NOTIFICATION DE L'INTERFACE GRAPHIQUE QUE LA SEQUENCE EST TERMINE EN INTERFACE GRAPHIQUE
									myParty.setSequenceStrated(false);
									myParty.notifyAll();
										try {
											//System.out.println("Console waiting BY HIMSELF!!!");
											myParty.wait();
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
								}
								
								if(endSequenceLoop==true && ((Party.getGameNumberOfPlayer()==4 && myParty.sequenceNumber()==4)||(Party.getGameNumberOfPlayer()==3 && myParty.sequenceNumber()==5))  ){
									myParty.setSequenceStrated(false);
									Party.setViewWherePartyEnded(false);
									myParty.setPartyEnded(true);
									System.out.println("CArds in the deck "+myParty.getPartyDeck().size()+" cards");

									for(Player cur : myParty.getPlayers()) {
										Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+cur.getName()+" ||==== {{ "+cur.getOffer().getCard().toString()+"  }} ===>>> "+cur.getName());
										cur.getJest().addCard(cur.getOffer().giveCard());
									}
									myParty.setChanged();
									myParty.notifyObservers();
									break;
								}
								
								
						}else {
							try {
								System.out.println("PLEASE WAIT A SEQUENCE IS RUNNING IN ANOTHER VIEW!!!");
								myParty.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
				}
		}
	}
	
	public void createPlayers() {
		int k = 0;
		
		
		for( k = 0 ; k < myParty.getGameNumberOfRealPlayer() ; k++) { //CREATION DES JOUEURS REELS
			System.out.print("Player#"+k+" , Please enter your name ::");
			myParty.getPlayers().add(k,new RealPlayer(readName(k),this.consoleView));
			myParty.getPlayers().get(k).addObserver(myParty);
		}
		for( ; k < Party.getGameNumberOfPlayer() ; k++) {  //CREATION DES JOUEURS VIRTUELS
			myParty.getPlayers().add(k,new VirtualPlayer(Party.getVirtualPlayerStrategy()));
			myParty.getPlayers().get(k).addObserver(myParty);
		}
		myParty.setPlayersIterator(myParty.getPlayers().iterator());
		while(myParty.getPlayersIterator().hasNext()) {
			Player currentPlayer = myParty.getPlayersIterator().next() ;
			currentPlayer.setJest(new Jest(currentPlayer));
			currentPlayer.setOffer(new Offer(currentPlayer));
		}
	}
	
	public  void setParty() {
		myParty.setPartyDeck(Deck.getInstanceOfDeck(Party.getGameDeckStyle()));
		myParty.getPartyDeck().shuffleDeck();
		Party.setPartyTrophies(Trophies.getInstanceofTrophies(myParty.getPartyDeck()));
		
	}
	
	private  int readChoice(int includedMinValue , int includedMaxValue)  {
		String asciiChoice = "" ;
		int    intChoice = -1;
		while (intChoice<includedMinValue || intChoice>includedMaxValue) {
			try { 	asciiChoice=PartyConsoleEngine.input.readLine();
					intChoice = Integer.parseInt(asciiChoice);
					if(intChoice<includedMinValue || intChoice>includedMaxValue ) {
						System.out.println("\tplease enter an Integer in [ "+includedMinValue+" ; "+includedMaxValue+" ]");
						intChoice=-1;
					}

				 }catch(NumberFormatException   e) {
						System.out.println("\tplease enter an Integer in [ "+includedMinValue+" ; "+includedMaxValue+" ]");
				  intChoice=-1;
				 }catch (IOException e) {
					// TODO: handle exception
					 e.printStackTrace();
				}
		}
		return intChoice;
	}
	
	private  String readName(int playerRank) {
		String name =  "Player#";
		try {
			name=PartyConsoleEngine.input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(name.trim().isEmpty()) {
			name="Player#"+playerRank;
		}
		return name.toUpperCase() ;
	}

	public Party getMyParty() {
		// TODO Auto-generated method stub
		return this.myParty;
	}
	/**
	 * Cette Methode est Non-implémenté pour ce {@link Controler}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		/**
		 * l'objet recu est un String qui est contient des informations sur ce qui doit etre afficher en console
		 */
		
		/*for(int k=0;k<myParty.getGameNumberOfPlayer();k++) {
			myParty.getPlayers().get(k).getOffer().printOffer();
			myParty.getPlayers().get(k).getJest().printJest();			
		}*/
	}
	/**
	 * Methode non-implémentée
	 */
	public void  updateConsoleView() {
		/*boolean offerMadeFlag = true ; //permet de savoir a quelle etape de la sequence on se situe
		consoleView.delete(0, consoleView.capacity());
		consoleView.append("*************SEQUENCE#0"+myParty.sequenceNumber()+"***********\n");
		this.consoleView.append(Party.getBroadCast()+"\n\n");
		this.consoleView.append("[!!!]************ "+myParty.getPartyDeck().size()+" CARDS REMAINING IN THE DECK!!!****************\n");


		for(int k=0;k<Party.getGameNumberOfPlayer();k++) {
			if(myParty.getPlayers().get(k).getOffer().size()<2) {
				offerMadeFlag=false;
			}
		}
		*/
		//si le flag est a false les offre n'ont pas toutes ete faites
		//if(offerMadeFlag==false){
			
		//}else{
			
		//}
		
		
	}

	public static BufferedReader getInput() {
		if(myInstance==null) {
			return null;
		}else {
			return PartyConsoleEngine.input;
		}
	}	
	
	public void setMyParty() {
		this.myParty = Party.getInstanceOfParty();
	}
	
	public void reset() {
		myInstance=null;
		myParty=null;
	}

	/**
	 * determine le vainqueur de la {@link Party} et affiche le tableau de score 
	 */
	public void publishResults() {
		if(/*myParty.isPartyEnded() && */( (Party.getGameNumberOfPlayer()==4 && myParty.sequenceNumber()==4)||(Party.getGameNumberOfPlayer()==3 && myParty.sequenceNumber()==5) ) ) {
			Party.getPartyTrophies().printTropies();
			Party.getPartyTrophies().attributeTrophies(myParty.getPlayers());
			myParty.setPartyWinner(myParty.setupResults(myParty.getPlayers()));
			System.out.println("---------- WE HAVE A WINNER ----------");
			System.out.println("---------- THE BOSS OF THE JESTSET IS "+myParty.getPartyWinner().getName()+" WITH "+myParty.getScoreBoard().get(myParty.getPartyWinner())+" POINTS ----------");
			System.out.println("---------- THE SCORE BOARD  ----------");
			System.out.println(myParty.getScoreBoard().toString());
			System.out.println("---------- PLAYERS' JEST  ----------");
			for(Player man : myParty.getPlayers()) {
				man.getJest().printJest();
			}
			myParty.setPartyEnded(true);


	}
	}

	public GraphicControler getMyGraphicContoller() {
		return myGraphicContoller;
	}

	public void setMyGraphicContoller(GraphicControler myGraphicContoller) {
		this.myGraphicContoller = myGraphicContoller;
	}
	
	
	
	
	

}
