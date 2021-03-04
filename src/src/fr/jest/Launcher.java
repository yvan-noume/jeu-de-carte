package fr.jest;

import java.io.Console;

import javax.swing.JDialog;

import fr.jest.controller.GraphicControler;
import fr.jest.controller.PartyConsoleEngine;
import fr.jest.graphic.GameView;
import fr.jest.model.Party;

/**
 * C'est la Classe principale : c'est depuis cette classe que toutes les {@link Party} sont lancées
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *
 */
public class Launcher {
	/**
	 * Le modèle du Jeu
	 */
	public static Party gameModel ;
	/**
	 * Le moteur du jeu en Mode console , qui correspond également au Controleur en mode {@link Console}
	 */
	public static PartyConsoleEngine consoleEngine ;
	/**
	 * Le controleur graphique de la {@link Party}
	 */
	public static GraphicControler GuiControler ;
	/**
	 * La fenêtre de lancement de la party en mode Graphique
	 * */
	public static GameView gameView ;
	/**
	 * le {@link Thread} console
	 */
	public static Thread consoleThread ;
	/**
	 * Le {@link Thread} graphique
	 */
	public static Thread GraphicThread  ;
	/**
	 * flag permettant de savoir si c'est la première fois que le programme lance une {@link Party}
	 */
	public static boolean restart = false;
	

	/**
	 * Porte d'entrée du programme
	 * @param args les arguments fournies depuis l'invite de commande : normalement aucun argument n'est attendu de l'invite de commande
	 */
	public static void main(String[] args) {
		JDialog.setDefaultLookAndFeelDecorated(true);
	

		gameModel = Party.getInstanceOfParty();
		consoleEngine = PartyConsoleEngine.getInstanceOfEngine(gameModel);
		GuiControler = new GraphicControler(gameModel);
		gameView = GameView.getInstanceOfGameView(GuiControler);
		
		consoleThread = new Thread(consoleEngine , "Console_Thread#0"+PartyConsoleEngine.counter);
		GraphicThread  = new Thread(gameView,"GUI_Thread") ;
		
		Runnable notif_runnable = new Runnable() {
			@Override
			public  void run() {
				synchronized (gameModel) {
					try {
					gameModel.notifyAll();
					}catch (NullPointerException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
					
		};
		
		
		GuiControler.setMyView(gameView);
		gameModel.addObserver(consoleEngine);
		gameModel.addObserver(gameView);
		
		//gameModel.setPartyDeck(Deck.getInstanceOfDeck(DeckStyle.DEFAULT)); //POUR EVITER UN BUG AU REDEMARRAGE DE LA PARTIE
		consoleEngine.setMyGraphicContoller(GuiControler);
		GuiControler.setMyConsoleController(consoleEngine);
		gameView.linkWithComponents();
		GraphicThread.start();
		consoleThread.start();
		
		try {
			while ( (gameModel.isPartyEnded()==false) && (Party.getGameNumberOfPlayer()==4 && gameModel.sequenceNumber()<=4)||(Party.getGameNumberOfPlayer()==3 && gameModel.sequenceNumber()<=5)) {
				Thread notificator = new Thread(notif_runnable, "NOTIFICATION THREAD");
				notificator.start();

				try {
					notificator.join();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (NullPointerException e) {
			// TODO: handle exception
			//Cette boucle lance une exception quand on lance une nouvelle partie ce qui est tt a fait normal
			//ce bloc catch est juste la pour  eviter un message d'erreur et sortir du thread proprement
			return;
		}
			
		
			

	}
	
	/**
	 * Cette methode permet de relancer une nouvelle Party
	 */
	public static void restart() {
		
	     Thread restartParty = new Thread("Restart Party") {
	    	 public void run() {
	    		 Launcher.gameModel.setPartyEnded(true);
	    			Launcher.gameModel.resetParty();
	    			  Launcher.gameView.reset();
	    			  Launcher.consoleEngine.reset();
	    			  Launcher.consoleEngine=null;
	    		      Launcher.restart=true;
	    		      Launcher.gameModel=null;
	    		      Launcher.gameView.dispose();
	    		      Launcher.gameView=null;
	    		      Party.setViewWherePartyConfigured(false);
	    		      Party.setViewWherePartyEnded(false);
	    		      Party.setViewWherePartyStarted(false);
	    			 System.out.println("party destroyed"); 
	    		     System.out.println("Running after dispose");
	    		 Launcher.main(null);};
	     };
	     restartParty.start();
	}
		

}
