package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.jest.controller.GraphicControler;
import fr.jest.model.Offer;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.Trophies;
/**
 * Ecran de Jeu de la {@link Party}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see JestPanel
 */
public class GamePanel extends JPanel implements JestPanel{

	private static final long serialVersionUID = 3346275168355323083L;
	/**
	 * La fenetre du Jeu
	 */
	private GameView myGameView ;
	private static GamePanel myInstance = null ;
	/**
	 * une Liste Contenant tous les emplacements des {@link Offer} ( {@link PlayerSpot} )  des {@link Player} 
	 */
	private List<PlayerSpot> playerSpots = new ArrayList<PlayerSpot>();
	private JPanel playerSpotContainer ;
	private boolean isAssembled = false;
	/**
	 * l'emplacement des {@link Trophies} (  {@link TrophySpot} )
	 */
	private TrophySpot trophySpot ;
	/**
	 * L'emplacement du Chrono de la {@link Party} 
	 */
	private JestGraphicTimer myGraphicTimer ;

	/**
	 * constructeur 
	 */
	private GamePanel() {
		createPlayerSpotContainer();
		this.setLayout(new GridLayout(0,1));

	}
	
	private GamePanel(int pWidth, int pHeight) {
		createPlayerSpotContainer();
		this.setLayout(new GridBagLayout());
	}
	
	
	
	public static GamePanel getInstanceOfGamePan() {
		if(GamePanel.getMyInstance()==null) {
			GamePanel.myInstance=new GamePanel();
		}
		return GamePanel.getMyInstance();
	}
	
	public static GamePanel getInstanceOfGamePan(int pWidth, int pHeight) {
		if(GamePanel.getMyInstance()==null) {
			GamePanel.myInstance=new GamePanel(pWidth,pHeight);
		}
		return GamePanel.getMyInstance();
	}

	@Override
	protected void paintComponent(Graphics g) {
			g.drawImage(GraphicControler.background_Im,0,0,this.getWidth(), this.getHeight(),null);

	
	}
	
	private void createPlayerSpotContainer() {
		playerSpotContainer = new JPanel() {
			private static final long serialVersionUID = 1611122987349237146L;
			@Override
			protected void paintComponent(Graphics g) {
					g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH,PlayerSpot.PLAYER_SPOT_HEIGHT+55);
			}
			
		};
	}

	public void  newPlayer(String pPlayerName ,boolean isReal,int position) {
		PlayerSpot tmpVar = new PlayerSpot(pPlayerName , isReal);
		playerSpots.add(position,tmpVar);
		playerSpotContainer.add(tmpVar, position);
	}


	public void assemble() {
		GridBagConstraints gbc = new GridBagConstraints();
		this.isAssembled=true;
		myGraphicTimer=new JestGraphicTimer() ;
		Party.getMyTimer().addObserver(myGraphicTimer);
		int k =0;
				for( k=0;k<myGameView.getMyController().getMyParty().getGameNumberOfRealPlayer();k++) {
					newPlayer(myGameView.getMyController().getMyParty().getPlayers().get(k).getName(), true,k);
					myGameView.getMyController().getMyParty().getPlayers().get(k).getOffer().addObserver(playerSpots.get(k));
					for(int i=0 ; i<playerSpots.get(k).getSpotSize();i++) {
						playerSpots.get(k).getCardSpot(i).setMyPlayerSpotPosition(k);
						playerSpots.get(k).getCardSpot(i).setMyPosition(i);
						
					}
					myGameView.getMyController().setEnabledPlayer(k, false);
				}
				for( ;k<Party.getGameNumberOfPlayer();k++) {
					newPlayer(myGameView.getMyController().getMyParty().getPlayers().get(k).getName(), false,k);
					myGameView.getMyController().getMyParty().getPlayers().get(k).getOffer().addObserver(playerSpots.get(k));
					for(int i=0 ; i<playerSpots.get(k).getSpotSize();i++) {
						playerSpots.get(k).getCardSpot(i).setMyPlayerSpotPosition(k);
						playerSpots.get(k).getCardSpot(i).setMyPosition(i);
					}
					myGameView.getMyController().setEnabledPlayer(k, false);
				}
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridheight=1;		
		this.add(new RefCardAndDeckSpot(myGraphicTimer),gbc);
		gbc.gridy=1;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(trophySpot,gbc);
		gbc.gridy=2;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(playerSpotContainer);
	}

	@Override
	public boolean isAssembled() {
		// TODO Auto-generated method stub
		return this.isAssembled;
	}

	public void setMyGameView(GameView myGameView) {
		this.myGameView = myGameView;
	}

	public static GamePanel getMyInstance() {
		return myInstance;
	}

	public List<PlayerSpot> getPlayerSpots() {
		return playerSpots;
	}

	public TrophySpot getTrophySpot() {
		return trophySpot;
	}

	public void setTrophySpot(TrophySpot trophySpot) {
		this.trophySpot = trophySpot;
	}
	
	public void setEnableEveryComponent(boolean lockStatus) {
		for(PlayerSpot ps : playerSpots) {
			ps.setEnableEveryComponent(lockStatus);
		}
		trophySpot.setEnabled(lockStatus);
		
	}
	
	public void reset() {
		myInstance=null;
	}
	
	
	
	

	
	

	

}
