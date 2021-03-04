package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.jest.model.Player;
/**
 * Une Boite de Dialogue contenat les cartes du Jest du {@link Player}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see JDialog
 */
public class JestVisualizer extends JDialog {
	private static final long serialVersionUID = -8070480920844910026L;
	private int VISUALIZE_WIDTH = CardSpot.CARD_WIDTH*5 ;
	private int VISUALIZE_HEIGHT = CardSpot.CARD_HEIGHT*2 + 50 ;
	
	

	
	public JestVisualizer(JFrame parent , String playerName) {
		
		super(parent, playerName+"'S JEST", true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(VISUALIZE_WIDTH,VISUALIZE_HEIGHT);
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setContentPane(new JPanel() {
			private static final long serialVersionUID = -2583175473117994576L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(VISUALIZE_WIDTH,VISUALIZE_HEIGHT);
			}	
		});
		
		this.setVisible(false);
	}
	

	
	public void addJestCard (CardSpot cp) {
		this.getContentPane().add(cp);
	}
	
	public void showDialog() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}

}


