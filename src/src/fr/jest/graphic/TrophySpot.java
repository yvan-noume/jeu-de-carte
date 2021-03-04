package fr.jest.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.jest.controller.GraphicControler;
import fr.jest.model.Trophies;
/**
 * l'emplacement des {@link Trophies}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see CardSpot
 */
public class TrophySpot extends JPanel {

	private static final long serialVersionUID = -2457418295247918132L;
	private final int TROPHY_SPOT_WIDTH = GameView.FRAME_WIDTH;
	private final int TROPHY_SPOT_HEIGHT = CardSpot.CARD_HEIGHT +60 ;
	
	private final int TEXT_WIDTH =100;
	private final int TEXT_HEIGHT=25;
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	
	private CardSpot[] trophyCardSpot = {new CardSpot(),new CardSpot()} ;
	private JTextField trophyTextField = new JTextField("Game Trophy");
	
	public TrophySpot(int numberOfTrophies) {
		
		trophyTextField.setPreferredSize(new Dimension(TEXT_WIDTH,TEXT_HEIGHT));
		trophyTextField.setHorizontalAlignment(JTextField.CENTER);
		trophyTextField.setFont(new Font("arial", Font.BOLD|Font.ITALIC, 14));
		trophyTextField.setEnabled(false);
		trophyTextField.setDisabledTextColor(Color.black);
		
		this.setLayout(new GridBagLayout());
		
		this.gbc.insets= new Insets(2, 2, 2, 2);
		this.gbc.gridx=0;
		this.gbc.gridy=0;
		this.gbc.gridwidth=2;
		this.gbc.gridheight=1;
		this.gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(trophyTextField,this.gbc);
		
		this.gbc.gridy=1;
		for(int k=0; k< numberOfTrophies;k++) {
			if(k==numberOfTrophies-1) {
				this.gbc.gridwidth=GridBagConstraints.REMAINDER;
			}else {
				this.gbc.gridwidth=1;
			}
			this.gbc.gridx=k;
			this.add(this.trophyCardSpot[k],this.gbc);
		}
	}
	
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
			g.drawImage(GraphicControler.transluscent_Im,0,0,this.getWidth(),this.getHeight(),null);

		for(CardSpot spot : this.trophyCardSpot) {
			spot.repaint();
		}
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(TROPHY_SPOT_WIDTH,TROPHY_SPOT_HEIGHT);
	}
	
	public void  setTropySpotImage(String trophyImage,int position) {
trophyCardSpot[position].setSpotImage(trophyImage);	
}
	
	
}
