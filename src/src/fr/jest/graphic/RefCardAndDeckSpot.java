package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.jest.controller.GraphicControler;
import fr.jest.model.Deck;
/**
 * emplacement de la carte de référence , {@link JestGraphicTimer} et du {@link Deck}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see CardSpot 
 */
public class RefCardAndDeckSpot extends JPanel {

	private static final long serialVersionUID = -1164700671862986608L;
	private final int RULE_CARD_WIDTH = 107 ;
	private final int RULE_CARD_HEIGHT = 160 ;
	
	private final int RULE_SPOT_WIDTH = GameView.FRAME_WIDTH;
	private final int RULE_SPOT_HEIGHT = RULE_CARD_HEIGHT + 5 ;
	
	
	private CardSpot RefCard = new CardSpot("ref") {
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize() {
		 return new Dimension(RULE_CARD_WIDTH,RULE_CARD_HEIGHT);
		}
	};
	private CardSpot DeckSpot = new CardSpot("hidden"){
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize() {
		 return new Dimension(RULE_CARD_WIDTH,RULE_CARD_HEIGHT);
		}
	};
	
	public RefCardAndDeckSpot(JestGraphicTimer theTimer) {
		// TODO Auto-generated constructor stub
		this.add(RefCard);
		this.add(theTimer);
		this.add(DeckSpot);
	}
	
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
			g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
		
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(RULE_SPOT_WIDTH,RULE_SPOT_HEIGHT);
	}
	
	public void  setDeckSpotImage(String deckImage) {
		this.DeckSpot.setSpotImage(deckImage);
	}

}
