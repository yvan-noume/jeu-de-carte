package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import fr.jest.controller.GraphicControler;
import fr.jest.model.JestTimer;
import fr.jest.model.Party;
import fr.jest.model.RegularCard;
/**
 * Emplacement d'une {@link RegularCard}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see PlayerSpot
 * @see GraphicControler
 * @see GameView
 * @see BufferedImage
 * @see JButton
 */
public class CardSpot extends JButton {

	private static final long serialVersionUID = 4868678776742272478L;
	/**
	 * La Largeur d'un {@link CardSpot}
	 */
	public static final int CARD_WIDTH = 87;
	/**
	 * La hauteur d'un {@link CardSpot}
	 */
	public static final int CARD_HEIGHT = 130 ;
	
	private String ROOT = "Files/images/deckstyle/"+Party.getGameDeckStyle().toString()+"/" ;
	/**
	 * L'extension de toutes les images doit etre .PNG
	 */
	private final String EXTENSION = ".png" ;
	/**
	 * Image correspondant à un spot vide
	 */
	private final String EMPY_SPOT = ROOT+"spot"+EXTENSION ;
	/**
	 * LA {@link Party}
	 */
	private  Party cardSportPartyView ;
	/**
	 * Le controleur {@link GraphicControler} de la {@link Party}
	 */
	private  GraphicControler cardSpotGraphicControllerView;
	/**
	 * La position du {@link PlayerSpot} qui contient ce {@link CardSpot}
	 */
	private int myPlayerSpotPosition ;
	/**
	 * la position du {@link CardSpot} dans le {@link PlayerSpot}
	 */
	private int myPosition ;
	private boolean hasInternListener = false;
	/**
	 * Listener qui émule la réalisation d'une offre de l'utilisateur auprès du controleur {@link GraphicControler}
	 * 	
	 *  */
	private GraphicControler.MakeOfferExternListener myMakeOfferExternListener ;
	/**
	 * Listener qui émule l'action de prise de l'utilisateur auprès du controleur {@link GraphicControler}
	 * 	
	 *  */
	private GraphicControler.TakeCardExternListener myTakeCardExternListener ;
	
	

	/**
	 * le nom de l'image à afficher
	 */
	private String spotImage = EMPY_SPOT ;
	/**
	 * Constructeur par défaut
	 */
	public CardSpot() {
		this.setMinimumSize(getPreferredSize());

	}
	/**
	 * constructeur avec Image par défaut
	 * @param defaultCard image par défaut
	 */
	public CardSpot(String defaultCard) {
		this.setMinimumSize(getPreferredSize());
		this.setSpotImage(defaultCard);

	}
	
	protected void paintComponent(Graphics g) {
			BufferedImage buffIm = correspond(spotImage);
			g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
			g.drawImage(buffIm,0,0,this.getWidth(),this.getHeight(),null);
	}
	
	//@Override
	public  void addMakeOfferListener(GraphicControler.MakeOfferExternListener l) {
		if(this.hasInternListener==false) {
			this.hasInternListener=true;
		super.addMouseListener(new CardSpotMouseInternListener());
		}
		this.myMakeOfferExternListener=l;
	}
	
	public  void addTakeCardListener(GraphicControler.TakeCardExternListener l) {
		if(this.hasInternListener==false) {
			this.hasInternListener=true;
		super.addMouseListener(new CardSpotMouseInternListener());
		}
		this.myTakeCardExternListener= l;

	}
	
	public  void removeMouseListener() {
		// TODO Auto-generated method stub
		while(this.getMouseListeners().length!=0) {
		super.removeMouseListener(this.getMouseListeners()[0]);
		}
		this.hasInternListener=false;

	}
	
	public  void removeMakeOfferListener() {
		this.myMakeOfferExternListener=null;
	}
	public void removeTakeCardListener() {
		this.myTakeCardExternListener=null;
		}
	
	
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(CARD_WIDTH,CARD_HEIGHT);
	}
	
	public void setSpotImage(String cardName) {
		this.spotImage=ROOT+cardName+EXTENSION;
	}
	
	
	public String getSpotImage() {
		return this.spotImage;
	}
	
	public int getMyPlayerSpotPosition() {
		return myPlayerSpotPosition;
	}
	public int getMyPosition() {
		return myPosition;
	}
	public void setMyPlayerSpotPosition(int myPlayerSpotPosition) {
		this.myPlayerSpotPosition = myPlayerSpotPosition;
	}
	public void setMyPosition(int myPosition) {
		this.myPosition = myPosition;
	}

	

	public GraphicControler getCardSpotGraphicControllerView() {
		return cardSpotGraphicControllerView;
	}
	public void setCardSpotGraphicControllerView(GraphicControler cardSpotGraphicControllerView) {
		this.cardSpotGraphicControllerView = cardSpotGraphicControllerView;
	}
	public Party getCardSportPartyView() {
		return cardSportPartyView;
	}
	public void setCardSportPartyView(Party cardSportPartyView) {
		this.cardSportPartyView = cardSportPartyView;
	}
	public GraphicControler.MakeOfferExternListener getMyMakeOfferExternListener() {
		return myMakeOfferExternListener;
	}
	public void setMyExternListener(GraphicControler.MakeOfferExternListener myExternListener) {
		this.myMakeOfferExternListener = myExternListener;
	}



	class CardSpotMouseInternListener implements MouseListener{
		@Override
		public  void mouseClicked(MouseEvent e) {
			
			//HANDLING WHEN PARTY ENDED IN ANOTHER VIEW
			if(cardSportPartyView.isPartyEnded()) {
				JestTimer.getInstanceOfTimer(cardSportPartyView).pause();	
				cardSpotGraphicControllerView.cleanPlayerSpots();
				Thread scoreThread =new Thread(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "-----THE SCORE BOARD------\n"+cardSportPartyView.getScoreBoard().toString(), "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				},"SCORE_BOARD" );

				scoreThread.start();
				return;
			}
				
			if(cardSportPartyView.isSequenceStrated()==true && cardSportPartyView.getNumberOfUserActionToEndASequence()==2*cardSportPartyView.getGameNumberOfRealPlayer()) {
				new Thread("SEQUENCE_RUNNING_MSG") {
					public void run() {
						JOptionPane.showMessageDialog(null, "Please Wait a Sequence\nis running in another View", "Wait Please !!", JOptionPane.INFORMATION_MESSAGE);	
					};
				}.start();
				return;		
			}
			
				if(cardSportPartyView.isSequenceStrated()==false || cardSportPartyView.getNumberOfUserActionToEndASequence()>0 ) {
					cardSportPartyView.setSequenceStrated(true);
		
					if(myMakeOfferExternListener!=null) {
						myMakeOfferExternListener.setCardPosition(myPosition);
						myMakeOfferExternListener.setPlayerPosition(myPlayerSpotPosition);

						myMakeOfferExternListener.mouseClicked(e);
						
		
					}else if(myTakeCardExternListener!=null) {
						//cardSportPartyView.setSequenceStrated is set to false in every
						// block of the graphic controler who ends a sequence
						cardSportPartyView.setNumberOfUserActionToEndASequence(cardSportPartyView.getNumberOfUserActionToEndASequence()-1);

						//System.out.println("take card executed");

						myTakeCardExternListener.setTargetPosition(myPlayerSpotPosition);
						myTakeCardExternListener.setTargetCardPosition(myPosition);
						myTakeCardExternListener.mouseClicked(e);		
						
					}
					//ON REINITIALISE LE COMPTEUR POUR LA PROCHAINE SEQUENCE
					if(cardSportPartyView.getNumberOfUserActionToEndASequence()<=0) {
						cardSportPartyView.setNumberOfUserActionToEndASequence(cardSportPartyView.getGameNumberOfRealPlayer()*2);
					}
					
				}
			//}
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
		
	}
	/**
	 * permet de faire une correspondance entre un nom d'image et une image chargée en mémoire
	 * @param spotImage le nom de l'image à afficher
	 * @return le BufferedImage correspondant à cette image 
	 */
	public BufferedImage correspond(String spotImage) {
		String filename = (spotImage.split("/")[4]).split("\\.")[0] ;
		if(filename.equals("hidden")) {
			return GraphicControler.hidden_Im ;
		}else if(filename.equals("spot")) {
			return GraphicControler.spot_Im ;
		}else if(filename.equals("ref")) {
			return GraphicControler.ref_Im ;

		}else if(filename.equals("joker")) {
			return GraphicControler.joker_Im ;

		}else if(filename.equals("as_clubs")) {
			return GraphicControler.clubs_as_Im ;

		}else if(filename.equals("2_clubs")) {
			return GraphicControler.clubs_2_Im ;

		}else if(filename.equals("3_clubs")) {
			return GraphicControler.clubs_3_Im ;

		}else if(filename.equals("4_clubs")) {
			return GraphicControler.clubs_4_Im ;

		}else if(filename.equals("as_spades")) {
			return GraphicControler.spades_as_Im ;

		}else if(filename.equals("2_spades")) {
			return GraphicControler.spades_2_Im ;

		}else if(filename.equals("3_spades")) {
			return GraphicControler.spades_3_Im ;

		}else if(filename.equals("4_spades")) {
			return GraphicControler.spades_4_Im ;

		}else if(filename.equals("as_diamonds")) {
			return GraphicControler.diamonds_as_Im ;

		}else if(filename.equals("2_diamonds")) {
			return GraphicControler.diamonds_2_Im ;

		}else if(filename.equals("3_diamonds")) {
			return GraphicControler.diamonds_3_Im ;

		}else if(filename.equals("4_diamonds")) {
			return GraphicControler.diamonds_4_Im ;

		}else if(filename.equals("as_hearts")) {
			return GraphicControler.hearts_as_Im ;

		}else if(filename.equals("2_hearts")) {
			return GraphicControler.hearts_2_Im ;

		}else if(filename.equals("3_hearts")) {
			return GraphicControler.hearts_3_Im ;

		}else if(filename.equals("4_hearts")) {
			return GraphicControler.hearts_4_Im ;

		}
		
		
		return null ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( (obj instanceof CardSpot) == false ) {
			return false ;
		}else {
			if( ((CardSpot)obj).getSpotImage().equals(this.getSpotImage()) ) {
				return true ;
			}else {
				return false ;
			}
		}
	}
	
}
