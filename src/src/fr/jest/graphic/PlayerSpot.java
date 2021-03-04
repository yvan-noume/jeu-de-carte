package fr.jest.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.jest.controller.GraphicControler;
import fr.jest.model.Offer;
import fr.jest.model.Player;

//Observing an Offer/**
/**
 * l'emplacement du {@link Offer} des {@link Player}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see CardSpot
 * @see JestVisualizer
 */
public class PlayerSpot extends JPanel implements Observer{
	private static final long serialVersionUID = 8079262673804960258L;
	private static final int TEXT_WIDTH =100;
	private static final int TEXT_HEIGHT=25;
	
	//private final int BTN_WIDTH  = new JButton().getPreferredSize().width;
	private static final int BTN_HEIGHT = new JButton().getPreferredSize().height ;
	
	public static final int PLAYER_SPOT_WIDTH = CardSpot.CARD_WIDTH*2 + 10;
	public static final int PLAYER_SPOT_HEIGHT = CardSpot.CARD_HEIGHT + TEXT_HEIGHT + BTN_HEIGHT + 30 ;
	
	
	private CardSpot[] cardSpot = {new CardSpot(),new CardSpot()};
	private JButton showJestBtn = new JButton("Show Jest") ;
	private JTextField playerNameField= new JTextField();
	
	private JPanel playerSpotContainer = new JPanel(){
		@Override
		protected void paintComponent(Graphics g) {
				g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
		}
		private static final long serialVersionUID = 1L;
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PLAYER_SPOT_WIDTH,PlayerSpot.PLAYER_SPOT_HEIGHT-5);
		}
	};
	
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public PlayerSpot(String pPlayerName , boolean isRealPlayer) {
		this.add(playerSpotContainer);
		this.playerSpotContainer.setLayout(new GridBagLayout());
		
		playerNameField.setText(pPlayerName);
		playerNameField.setPreferredSize(new Dimension(TEXT_WIDTH,TEXT_HEIGHT));
		playerNameField.setMinimumSize(playerNameField.getPreferredSize());
		playerNameField.setHorizontalAlignment(JTextField.CENTER);
		playerNameField.setFont(new Font("arial", Font.BOLD|Font.ITALIC, 14));
		playerNameField.setEnabled(false);
		playerNameField.setDisabledTextColor(Color.black);
		
		
		this.gbc.insets= new Insets(0, 2, 2, 2);
		this.gbc.anchor=GridBagConstraints.PAGE_START;
		this.gbc.gridx=0;
		this.gbc.gridy=0;
		this.gbc.gridwidth=2;
		this.gbc.gridheight=1;
		this.gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.playerSpotContainer.add(playerNameField,this.gbc);
		this.gbc.gridwidth=1;
		this.gbc.gridx=0;
		this.gbc.gridy=1;
		this.gbc.insets= new Insets(2, 2, 2, 2);
		this.playerSpotContainer.add(cardSpot[0],this.gbc);
		this.gbc.gridx=1;
		this.gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.playerSpotContainer.add(cardSpot[1],this.gbc);
		if(isRealPlayer == true) {
			this.gbc.gridwidth=1;
			this.gbc.gridx=0;
			this.gbc.gridy=2;
			this.gbc.gridwidth=GridBagConstraints.REMAINDER;
			this.playerSpotContainer.add(showJestBtn,this.gbc);
		}else {
			JButton inutile = new JButton("A") {
				private static final long serialVersionUID = -4333188453860708428L;
				@Override
				protected void paintComponent(Graphics g) {
						g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
				}
				@Override
				public Dimension getPreferredSize() {
					return showJestBtn.getPreferredSize();
				}
			};
			inutile.setEnabled(false);
			inutile.setBorderPainted(false);
			this.gbc.gridwidth=1;
			this.gbc.gridx=0;
			this.gbc.gridy=2;
			this.gbc.gridwidth=GridBagConstraints.REMAINDER;
			this.playerSpotContainer.add(inutile,this.gbc);
			
		
		}
		
	}
	
	//@Override
	protected void paintComponent(Graphics g) {
			g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);
		
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(PLAYER_SPOT_WIDTH,PLAYER_SPOT_HEIGHT);
	}
	
	public void setCardImage(int position , String cardName) {
		this.cardSpot[position].setSpotImage(cardName);
	}
	
	public CardSpot getCardSpot(int position) {
		return cardSpot[position];
	}

	@Override
	public void update(Observable o, Object arg) {
		//arg est l'offre (collection de deux cartes) qui est observée
		if(arg instanceof Offer) {
			for(int k = 0 ; k <((Offer)arg).size();k++) {
				setCardImage(k, ((Offer)arg).getCard(k).getCardName());
			}
		}

	}
	


	public int getSpotSize() {
		return cardSpot.length;
	}
	public JButton getShowJestBtn() {
		return this.showJestBtn;
	}
	
	public void setEnableEveryComponent(boolean lockStatus) {
		cardSpot[0].setEnabled(lockStatus);
		cardSpot[1].setEnabled(lockStatus);
		showJestBtn.setEnabled(lockStatus);
		
	}
	
	

}
