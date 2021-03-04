package fr.jest.graphic;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import fr.jest.Launcher;
import fr.jest.controller.GraphicControler;
import fr.jest.model.JestTimer;
import fr.jest.model.Party;
/**
 * La fenetre du Jeu
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see GamePanel
 *@see ConfigPan
 *@see HomePan
 *@see GraphicControler
 */
public class GameView extends JFrame implements Runnable,Observer{
	/**
	 * Le controleur Graphique de la {@link Party}
	 */
	private GraphicControler myController = null ;
	
	private static final long serialVersionUID = 1L;
	private static  int FULLSCREEN_WIDTH = 1366;
	private static  int FULLSCREEN_HEIGHT = 768 ;
	
	public static  int FRAME_WIDTH = 900;
	public static  int FRAME_HEIGHT = 650;
	
	public static  int MENU_BAR_WIDTH = FRAME_WIDTH;
	public static  int MENU_BAR_HEIGHT = FRAME_HEIGHT/24;
	
	public static  int CONTENT_WIDTH = FRAME_WIDTH;
	public static  int CONTENT_HEIGHT = FRAME_HEIGHT-MENU_BAR_HEIGHT;
	
	private JPanel globalViewContainer = new JPanel() {
		private static final long serialVersionUID = -2584224739639822626L;

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(CONTENT_WIDTH,CONTENT_HEIGHT);
		}
	};
	private JPanel viewContent = new JPanel();
	private CardLayout contentLayout = new CardLayout();
	private PanelList currentPanel = PanelList.HOMEPAN ;
	public ActionListener switchPanListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			contentLayout.show(viewContent,currentPanel.toString());
			if(( currentPanel.toString().equalsIgnoreCase(GamePanel.class.getName().split("\\.")[3]))==false) {
				quit.setEnabled(false);
				resume.setEnabled(false);
			}else {
				quit.setEnabled(true);
				pause.setEnabled(true);
			}
			if(myInstance.getMyController().getMyParty().isPartyStarted()==false && myInstance.getMyController().getMyParty().isPartyConfigured()==false && currentPanel.toString().equalsIgnoreCase(ConfigPan.class.getName().split("\\.")[3])==false) {
				configPan.setEnableEveryComponent(true);
			}else if(myInstance.getMyController().getMyParty().isPartyStarted()==true && myInstance.getMyController().getMyParty().isPartyConfigured()==false && currentPanel.toString().equalsIgnoreCase(ConfigPan.class.getName().split("\\.")[3])==true){
				configPan.setEnableEveryComponent(false);
			}else if(myInstance.getMyController().getMyParty().isPartyConfigured()==true && currentPanel.toString().equalsIgnoreCase(ConfigPan.class.getName().split("\\.")[3])==true) {
				configPan.setEnableEveryComponent(false);

			}
		}
	};

	private GamePanel gamePan ;
	private HomePan homePan ;
	private ConfigPan configPan;
	
	private static GameView myInstance = null;
	
	private JMenuBar menuBar ;
	private JMenu party ;
		private JMenuItem pause ;
		private JMenuItem resume ;
		private JMenuItem quit ;
		
	private JMenu window ;
		private JMenuItem preference ;
		private JMenuItem reduce ;
		private JMenuItem fullScreen ;
	private JMenu help ;
	private JMenu exit ;
		private JMenuItem exitApp ;
	
	/**
	 * renvoie une réference sur une fenêtre de {@link Party}
	 * @param pMyController le controleur graphique de la {@link Party}
	 * @return une réference sur une fenêtre de {@link Party}
	 */
	public static GameView getInstanceOfGameView(GraphicControler pMyController) {
		if(GameView.myInstance==null) {
			GameView.myInstance=new GameView(pMyController);
		}
		return GameView.myInstance;
	}
	/**
	 * permet d'instancier une fenêtre
	 * @param pMyController le controleur graphique de la {@link Party}
	 */
	private GameView(GraphicControler pMyController) {
		this.myController=pMyController;
		gamePan = GamePanel.getInstanceOfGamePan(CONTENT_WIDTH,CONTENT_HEIGHT) ;
		homePan= HomePan.getInstanceOfHomePan();
		configPan  = ConfigPan.getInstanceOfConfigPan();
		buildUpMenu();
	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		FULLSCREEN_HEIGHT= dim.height;
		FULLSCREEN_WIDTH=dim.width;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JestSet 2.0");
		this.setResizable(false);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		setupPane((JPanel)homePan);
		viewContent.setLayout(contentLayout);
		viewContent.add(homePan, PanelList.HOMEPAN.toString());
		viewContent.add(configPan,PanelList.CONFIGPAN.toString());
		viewContent.add(gamePan, PanelList.GAMEPANEL.toString());

		
		globalViewContainer.add(menuBar);
		globalViewContainer.add(viewContent);
		this.getContentPane().add(globalViewContainer);

	
	}

	@Override
	public void paintComponents(Graphics arg0) {
		this.getContentPane().repaint();
	}
	/**
	 * permet d'assember un écran avant de l'afficher sur la fenêtre
	 * @param pan l'ecran à assembler
	 */
	public void setupPane(JPanel pan) {
		((JestPanel)pan).assemble();
	}
	/**
	 * permet de construire la barre de menu
	 */
	private void buildUpMenu() {
		
		menuBar = new JMenuBar() {
			private static final long serialVersionUID = 1L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(MENU_BAR_WIDTH,MENU_BAR_HEIGHT);
			}
			
		};
		party = new JMenu("Party");
			pause = new JMenuItem("Pause");
			resume = new JMenuItem("Resume");
			quit = new JMenuItem("Quit Party");
		window = new JMenu("Window");
			preference=new JMenuItem("Preference");
			reduce=new JMenuItem("Reduce");
			fullScreen=new JMenuItem("FullScreen");
		help = new JMenu("Help");
		exit = new JMenu("Exit");
			exitApp = new JMenuItem("Close JestSet2.0");
		
		//menuBar.setLocation(0, 0);
		menuBar.add(party);
			party.add(pause);
			party.add(resume);
			party.add(quit);
		menuBar.add(window);
			window.add(preference);
			window.add(reduce);
			window.add(fullScreen);
		menuBar.add(help);
		menuBar.add(exit);
			exit.add(exitApp);
		
		pause.setEnabled(false);
		quit.setEnabled(false);
		resume.setEnabled(false);
		reduce.setEnabled(false);
		
		
		pause.addActionListener(new PauseListener());
		resume.addActionListener(new ResumeListener());
		exitApp.addActionListener(new ExitListener());
		fullScreen.addActionListener(new FullScreenListener());
		reduce.addActionListener(new ReduceListener());
		quit.addActionListener(new QuitListener());
	}
	
	
	/**
	 * permet de lier la fenêtre à tous les écrans qui la constitue
	 */
	public void  linkWithComponents() {
		homePan.setMyGameView(this);
		gamePan.setMyGameView(this);
		configPan.setMyGameView(this);
	}
	/**
	 * permet de changer l'écran de le fenêtre
	 * @param pan l'écran à afficher
	 */
	public void switchPan(JPanel pan) {
		if(((JestPanel)pan).isAssembled()==false) {
			setupPane(pan);
		}
		//System.out.println("THE PROBLEM IS : "+pan.getClass().getName().toUpperCase());
		//System.out.println("THE PROBLEM IS : "+pan.getClass().getName().split("\\.")[3].toUpperCase());
		setCurrentPanel(PanelList.valueOf(pan.getClass().getName().split("\\.")[3].toUpperCase()));
	}

	public void setCurrentPanel(PanelList currentPan) {
		this.currentPanel=currentPan;
	}
	/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#exitApp}
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			//myInstance.dispose();
		}	
	}
	/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#quit} pour quitter la {@link Party}
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class QuitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Launcher.restart();
		}
		
	}/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#resume} pour arreter la pause de la {@link Party}
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class ResumeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JestTimer.getInstanceOfTimer(null).resume();
			resume.setEnabled(false);
			pause.setEnabled(true);
			gamePan.setEnableEveryComponent(true);
			gamePan.repaint();

		}
		
	}
	/**
	 * 
	 * 
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#help} afin d'afficher un écran d'aide
	 * <br> Non-implémenté
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class HelpListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#pause} afin de mettre la {@link Party} en pause
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class PauseListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JestTimer.getInstanceOfTimer(null).pause();
			pause.setEnabled(false);
			resume.setEnabled(true);
			gamePan.setEnableEveryComponent(false);
			gamePan.repaint();
		}
		
	}
	
	/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#fullScreen} afin passer en mode plein écran
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class FullScreenListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			myInstance.setLocation(0, 0);
			myInstance.setSize(FULLSCREEN_WIDTH, FULLSCREEN_HEIGHT);
			MENU_BAR_WIDTH = FULLSCREEN_WIDTH;
			MENU_BAR_HEIGHT = FRAME_HEIGHT/15;
			myInstance.getContentPane().setBackground(Color.black);
			CONTENT_WIDTH = FRAME_WIDTH;
			CONTENT_HEIGHT = FRAME_HEIGHT-MENU_BAR_HEIGHT;
			reduce.setEnabled(true);
			fullScreen.setEnabled(false);
		}
		
	}
	/**
	 * cette classe implémente l'action  à réaliser lorsque l'utilisateur clique sur le bouton {@link GameView#reduce} afin de réduire la taille de l'écran
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class ReduceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			myInstance.setSize(900, 650);
			FRAME_WIDTH=900;
			FRAME_HEIGHT=650;
			MENU_BAR_WIDTH = FRAME_WIDTH;
			MENU_BAR_HEIGHT = FRAME_HEIGHT/24;
			
			CONTENT_WIDTH = FRAME_WIDTH;
			CONTENT_HEIGHT = FRAME_HEIGHT-MENU_BAR_HEIGHT;
			myInstance.setLocation(FULLSCREEN_WIDTH/2-myInstance.getSize().width/2, FULLSCREEN_HEIGHT/2-myInstance.getSize().height/2);
			fullScreen.setEnabled(true);
			reduce.setEnabled(false);
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		GameView.myInstance.setVisible(true);
	}

	public GraphicControler getMyController() {
		return myController;
	}
	/**
	 * mettre à jour les informations affichées
	 */
	@Override
	public void update(Observable o, Object arg) {
		gamePan.repaint();
	
	}
	
	public List<PlayerSpot>getPlayerSpot(){
		return gamePan.getPlayerSpots();
	}

	public TrophySpot getTrophySpot() {
		return gamePan.getTrophySpot();
	}
	
	public void setTrophySpot(TrophySpot ts) {
		gamePan.setTrophySpot( ts);
	}
	
	/**
	 * reinitialiser la fenetre
	 */
	public void reset() {			
			//switchPan((JPanel)HomePan.getInstanceOfHomePan());
			//ConfigPan.getInstanceOfConfigPan().initConfig();
			configPan.reset();
			configPan=null;
			Launcher.gameModel.setPartyStarted(false);
			Launcher.gameModel.setPartyConfigured(false);
			Launcher.gameModel=null;
			//myInstance.getMyController().setMyParty();
			Launcher.gameModel=myController.getMyParty();
			Launcher.consoleEngine.setMyParty();
			Launcher.gameModel.setChanged();
			Launcher.gameModel.notifyObservers();
			homePan.reset();
			homePan=null;
			gamePan.reset();
			gamePan=null;
			myInstance=null;
			//switchPanListener.actionPerformed(e);
		
	}

	public GamePanel getGamePan() {
		return gamePan;
	}

	public HomePan getHomePan() {
		return homePan;
	}

	public ConfigPan getConfigPan() {
		return configPan;
	}
	
	
	
	
	
	


	
}
