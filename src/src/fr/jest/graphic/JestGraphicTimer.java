package fr.jest.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.jest.model.JestTimer;
import fr.jest.model.Party;
/**
 * Ce Panel s'occupe de l'affichage du Chronomètre de la {@link Party}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see JestTimer
 */
public class JestGraphicTimer extends JPanel implements Observer{
	private static final int TEXT_WIDTH =100;
	private static final int TEXT_HEIGHT=25;
	private static final long serialVersionUID = 4539703073196485360L;
	private String minuts ;
	private String seconds;
	
	private JTextField  timerField = new JTextField("0 : 00"){
		private static final long serialVersionUID = -74993667883L;
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GameView.CONTENT_WIDTH/10,GameView.CONTENT_HEIGHT/30);
		}
	};
	/**
	 * constructeur par défaut
	 */
	public JestGraphicTimer() {
		
		timerField.setEditable(false);
		timerField.setPreferredSize(new Dimension(TEXT_WIDTH,TEXT_HEIGHT));
		timerField.setMinimumSize(timerField.getPreferredSize());
		timerField.setHorizontalAlignment(JTextField.CENTER);
		timerField.setFont(new Font("arial", Font.BOLD|Font.ITALIC, 14));
		timerField.setEnabled(false);
		timerField.setDisabledTextColor(Color.black);
		this.add(timerField);
	}
	/**
	 * mise à jour de l'affichage
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	
		minuts=((String)arg).split(":")[0];
		seconds=((String)arg).split(":")[1];

		repaint();

	}
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		timerField.setText(minuts+" : "+seconds);
		
	}

}
