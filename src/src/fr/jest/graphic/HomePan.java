package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.jest.model.Party;
/**
 * L'ecran Principal du Jeu
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see JestPanel
 *@see GameView
 */
public class HomePan extends JPanel implements JestPanel {
	
	private final String  TRANSLUSCENT = "Files/images/transparent.png";
	private static final long serialVersionUID = -8030618147300982137L;
	private static HomePan myInstance ;
	/**
	 * La fenetre du Jeu
	 */
	private GameView myGameView ;
	private boolean isAssembled = false;
	
	private JPanel containerPanel = new JPanel() {
		private static final long serialVersionUID = 5821391828323765740L;

		protected void paintComponent(Graphics g) {
			try {
				g.drawImage(ImageIO.read(new File(TRANSLUSCENT)),0,0,this.getWidth(),this.getHeight(),null);
				//System.out.println("SPOT UPDATED");

			} catch (IOException e) {
			}

		}
	};
	/**
	 * Le bouton pour lancer la Party
	 */
	private JButton beginBtn = new JButton("Begin Party");
	/**
	 * Le bouton pour afficher l'ecran de configuration de la {@link Party}
	 */
	private JButton configBtn = new JButton("Config Party");
	private JPanel welcomePan = new JPanel() {
		private static final long serialVersionUID = -6499416280790912947L;

		@Override
		protected void paintComponent(Graphics g) {
			try {
				g.drawImage(ImageIO.read(new File("Files/images/welcome.png")),0,0,this.getWidth(), this.getHeight(),null);
			} catch (IOException e) {
				System.out.println("Erreur lors de la lecture du background !!!");
			}
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GameView.FRAME_WIDTH*2/3,GameView.FRAME_HEIGHT*2/3);
		}
	};
		/**
		 * Le constructeur par défaut
		 */
		private  HomePan() {
			// TODO Auto-generated constructor stub
		}
		
		public static HomePan getInstanceOfHomePan() {
			if(HomePan.myInstance==null) {
				HomePan.myInstance=new HomePan();
			}
			return HomePan.myInstance;
		}

	
	@Override
	protected void paintComponent(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Files/images/fond/backgnd.jpg")),0,0,this.getWidth(), this.getHeight(),null);
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du background !!!");
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT);
	}

	@Override
	public void assemble() {
		this.setLayout(new GridBagLayout());
		this.isAssembled=true;
		this.setupHome();
		this.add(containerPanel);
	}
	@Override
	public boolean isAssembled() {
		return this.isAssembled;
	}
	/**
	 * configuration de l'ecran d'accueil
	 */
	private void setupHome() {
		containerPanel.add(welcomePan);
		beginBtn.addActionListener(new BeginListener());
		containerPanel.add(beginBtn);
		configBtn.addActionListener(new configListener());
		containerPanel.add(configBtn);
	}
	
	public void setMyGameView(GameView myGameView) {
		this.myGameView = myGameView;
	}
	/**
	 * cette Classe définie les Actions a executer lorsqu'on clique sur le bouton pour configurer la {@link Party}
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class configListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			myGameView.switchPan((JPanel)ConfigPan.getInstanceOfConfigPan());
			myGameView.switchPanListener.actionPerformed(e);
			myGameView.getMyController().getMyParty().setPartyConfigured(true);

		}
		
	}
	/**
	 * cette Classe définie les Actions a executer lorsqu'on clique sur le bouton pour lancer la {@link Party}
	 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
	 *
	 */
	class BeginListener implements ActionListener {

		@Override
			public   void actionPerformed(ActionEvent e) {
					Runnable  setBeginRunnable =  new Runnable() {
						public  void run() {
								myGameView.getMyController().beginParty();
						}
					};
					
					if(myGameView.getMyController().getMyParty().isSequenceStrated()==true) {
						Thread wait_msg=new Thread("Please Wait_DIALOG") {
							public void run(){
								JOptionPane.showMessageDialog(null, "Please Wait a Sequence\nis running in another View", "Wait Please !!", JOptionPane.INFORMATION_MESSAGE);		
							}
						};
						
						try {
							wait_msg.start();
							wait_msg.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		
							Thread beginMethodThread = new Thread(setBeginRunnable, "CONTROLER_BEGIN_PARTY_THREAD");
								beginMethodThread.start();
								try {
									beginMethodThread.join();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							
							myGameView.switchPan((JPanel)GamePanel.getInstanceOfGamePan());
							myGameView.switchPanListener.actionPerformed(e);
							
							//myGameView.getMyController().getMyParty().setPartyStarted(true);
							//myGameView.getMyController().getMyParty().setPartyConfigured(true);	
				
				
			}
	}
	
	public void reset() {
		myInstance=null;
	}
	

}
