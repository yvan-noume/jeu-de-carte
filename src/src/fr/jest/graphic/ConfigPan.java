package fr.jest.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import fr.jest.model.DeckStyle;
import fr.jest.model.DefaultRules;
import fr.jest.model.LigthRules;
import fr.jest.model.NiveauFacile;
import fr.jest.model.NiveauMoyen;
import fr.jest.model.Party;
/**
 * Ecran de configuration de la {@link Party}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see JestPanel
 */
public class ConfigPan extends JPanel implements JestPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * La fenetre du Jeu
	 */
	private GameView myGameView ;
	
	private static ConfigPan myInstance = null;
	
	private boolean isAssembled = false;
	private boolean enabled = true ;
	
	private PlayerNumberListener playerNumListener = new PlayerNumberListener();
	private RealPlayerListener realPlayerListener = new RealPlayerListener();
	private RuleListener ruleListener =  new RuleListener();
	private CustomRuleListener customRuleListener = new CustomRuleListener();
	private LevelListener levelListener = new LevelListener();
	private DeckListener deckListener = new DeckListener();
	private ClearListener clearListener = new ClearListener() ;
	private SaveListener saveListener = new SaveListener();
	
	private JPanel playerConfContainer = new JPanel() {
		private static final long serialVersionUID = 2609724341656593509L;

		public Dimension getPreferredSize() {
			return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT/6);
		}
	};
		private JLabel numberOfPlayerLabel = new JLabel("Select the number of player : ");
			private ButtonGroup  playerBtnGp = new ButtonGroup();
			private JRadioButton PlayerBtn3 = new JRadioButton("3");
			private JRadioButton PlayerBtn4 = new JRadioButton("4");

		private JLabel numberOfRealPlayerLabel = new JLabel("Select the number of real player : ");
		private ButtonGroup  realPlayerBtnGp = new ButtonGroup();
		private JRadioButton realBtn0 = new JRadioButton("0");
		private JRadioButton realBtn1 = new JRadioButton("1");
		private JRadioButton realBtn2 = new JRadioButton("2");
		private JRadioButton realBtn3 = new JRadioButton("3");
		private JRadioButton realBtn4 = new JRadioButton("4");
		
		private JPanel nameLabelAndFieldsPan =new JPanel();
		private JPanel namesPan = new JPanel();
		private JLabel enterNameLabel = new JLabel("Enter Real Players' names :");
		private JPanel namePan1 = new JPanel();
		private JPanel namePan2 = new JPanel();
		private JPanel namePan3 = new JPanel();
		private JPanel namePan4 = new JPanel();
		private JLabel player1Name = new JLabel("PLAYER#0 :");
		private JTextField realPlayer1Name = new JTextField("PLAYER#0") {
			private static final long serialVersionUID = -7711206459163667083L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH/10,GameView.CONTENT_HEIGHT/30);
			}
		};
		private JLabel player2Name = new JLabel("PLAYER#1 :");
		private JTextField realPlayer2Name = new JTextField("PLAYER#1") {
			private static final long serialVersionUID = -771064591636685083L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH/10,GameView.CONTENT_HEIGHT/30);
			}
		};
		private JLabel player3Name = new JLabel("PLAYER#2 :");
		private JTextField realPlayer3Name = new JTextField("PLAYER#2") {
			private static final long serialVersionUID = -7706459163667083L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH/10,GameView.CONTENT_HEIGHT/30);
			}
		};
		private JLabel player4Name = new JLabel("PLAYER#3 :");
		private JTextField realPlayer4Name = new JTextField("PLAYER#3") {
			private static final long serialVersionUID = -7459163667083L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH/10,GameView.CONTENT_HEIGHT/30);
			}
		};
		
	private JPanel ruleConfContainer = new JPanel() {
		private static final long serialVersionUID = 6808178221014804905L;
		public Dimension getPreferredSize() {
			return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT/5);
		}
	};
	private JLabel selectRulesLabel = new JLabel("Select the type of Rules  : ");
		private ButtonGroup  ruleBtnGp = new ButtonGroup();
		private JRadioButton defaultBtn = new JRadioButton("Default rules");
		private JRadioButton ligthBtn = new JRadioButton("Ligth Rules");
		private JRadioButton customBtn = new JRadioButton("Custom Rules");

		private JPanel CustomRuleConfContainer = new JPanel() {
			private static final long serialVersionUID = 2609724341656593509L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT/5);
			}
		};
			private ButtonGroup  diamNegBtnGp = new ButtonGroup();
			private JLabel diamNegLabel = new JLabel("Diamonds Count Negative : ");	
			private JRadioButton yesDiamNegBtn = new JRadioButton("Yes");
			private JRadioButton noDiamNegBtn = new JRadioButton("No");
			
			private ButtonGroup  heartJokerBtnGp = new ButtonGroup();
			private JLabel heartJokerLabel = new JLabel("Apply the \"heart and joker\" rule: ");	
			private JRadioButton yesHJBtn = new JRadioButton("Yes");
			private JRadioButton noHJBtn = new JRadioButton("No");
			
			private ButtonGroup  blackPairBtnGp = new ButtonGroup();
			private JLabel blackPairLabel = new JLabel("Apply the \"black pair\" rule: ");	
			private JRadioButton yesBPBtn = new JRadioButton("Yes");
			private JRadioButton noPBBtn = new JRadioButton("No");
			
			private ButtonGroup  AceBtnGp = new ButtonGroup();
			private JLabel aceLabel = new JLabel("Apply the \"Ace\" rule: ");	
			private JRadioButton yesAceBtn = new JRadioButton("Yes");
			private JRadioButton noAceBtn = new JRadioButton("No");
			
			private ButtonGroup  jokerValueBtnGp = new ButtonGroup();
			private JLabel jokerValueLabel = new JLabel("Select the value for the Joker : ");
			private JRadioButton jValueBtn1 = new JRadioButton("1");
			private JRadioButton jValueBtn2 = new JRadioButton("2");
			private JRadioButton jValueBtn3 = new JRadioButton("3");
			private JRadioButton jValueBtn4 = new JRadioButton("4");
			private JRadioButton jValueBtn5 = new JRadioButton("5");

			
			
	private JPanel levelConfContainer = new JPanel() {
		private static final long serialVersionUID = 5471474981465574827L;

		public Dimension getPreferredSize() {
			return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT/20);
		}
	};
	
		private ButtonGroup levelBtnGp = new ButtonGroup();
		private JLabel selectLevelLabel = new JLabel("Select the Bots' Level  : ");
		private JRadioButton easyBtn = new JRadioButton("Easy");
		private JRadioButton mediumBtn = new JRadioButton("Medium");
		
	private JPanel deckConfContainer = new JPanel() {
		private static final long serialVersionUID = -5987476833287530140L;

		public Dimension getPreferredSize() {
			return new Dimension(GameView.CONTENT_WIDTH,GameView.CONTENT_HEIGHT/20);
		}
	};
		private ButtonGroup deckBtnGp = new ButtonGroup();
		private JLabel selectDeckLabel = new JLabel("Select the deck style  : ");
		private JRadioButton defaultDeckBtn = new JRadioButton("Default");
		private JRadioButton italianDeckBtn = new JRadioButton("Italian Style");
		private JRadioButton cameroonianDeckBtn = new JRadioButton("Cameroonian Style");
		
		
	private JPanel validationPanel = new JPanel();
	private JButton saveQuitBtn = new JButton("Save And Quit");
	//private JButton quitBtn = new JButton("Quit");
	private JButton clearBtn = new JButton("Clear");
	

	
	private  ConfigPan() {
		this.setLayout(new GridBagLayout());
	}
	
	public static ConfigPan getInstanceOfConfigPan() {
		if(ConfigPan.getMyInstance()==null) {
			ConfigPan.myInstance=new ConfigPan();
		}
		return ConfigPan.getMyInstance();
	}
	
	protected void paintComponent(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Files/images/fond/backgnd.jpg")),0,0,this.getWidth(), this.getHeight(),null);
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du background !!!");
		}
	}


	@Override
	public void assemble() {
		// TODO Auto-generated method stub
		GridBagConstraints gbc = new GridBagConstraints();
		this.isAssembled=true;
		setupPlayerConf();
		setupRulesConf();
		setupCustomRuleConf();
		setupLevelConf();
		setupDeckConf();
		saveQuitBtn.addActionListener(saveListener);
		//quitBtn.addActionListener(quitListener);
		clearBtn.addActionListener(clearListener);
		validationPanel.add(saveQuitBtn);
		//validationPanel.add(quitBtn);
		validationPanel.add(clearBtn);
		
		this.initConfig();
		
		gbc.insets=new Insets(5, 5, 5, 5);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridheight=1;
		this.add(playerConfContainer,gbc);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(ruleConfContainer,gbc);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(CustomRuleConfContainer,gbc);
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(levelConfContainer,gbc);
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(deckConfContainer,gbc);
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		this.add(validationPanel,gbc);
		
		
	}

	@Override
	public boolean isAssembled() {
		return this.isAssembled;
	}
	
	private void setupPlayerConf() {
		//JPanel titlePanel = new JPanel();
		
		namePan1.add(player1Name);
		namePan1.add(realPlayer1Name);
		namePan2.add(player2Name);
		namePan2.add(realPlayer2Name);
		namePan3.add(player3Name);
		namePan3.add(realPlayer3Name);
		namePan4.add(player4Name);
		namePan4.add(realPlayer4Name);
		namesPan.add(namePan1);
		namesPan.add(namePan2);
		namesPan.add(namePan3);
		namesPan.add(namePan4);
		nameLabelAndFieldsPan.add(enterNameLabel);
		nameLabelAndFieldsPan.add(namesPan);
		
		
		JPanel firstQuestionPanel = new JPanel();
		JPanel secondQuestionPanel = new JPanel();
		
		PlayerBtn3.addActionListener(playerNumListener);
		PlayerBtn4.addActionListener(playerNumListener);
		
		playerBtnGp.add(PlayerBtn3);
		playerBtnGp.add(PlayerBtn4);
		
		realPlayerBtnGp.add(realBtn0);
		realPlayerBtnGp.add(realBtn1);
		realPlayerBtnGp.add(realBtn2);
		realPlayerBtnGp.add(realBtn3);
		realPlayerBtnGp.add(realBtn4);
		
		realBtn0.addActionListener(realPlayerListener);
		realBtn1.addActionListener(realPlayerListener);
		realBtn2.addActionListener(realPlayerListener);
		realBtn3.addActionListener(realPlayerListener);
		realBtn4.addActionListener(realPlayerListener);

		playerConfContainer.setLayout(new GridLayout(3,1));
		//playerConfContainer.setPreferredSize(new Dimension(GameView.FRAME_WIDTH,GameView.FRAME_HEIGHT/9));
		
		firstQuestionPanel.add(numberOfPlayerLabel);
		firstQuestionPanel.add(PlayerBtn3);
		firstQuestionPanel.add(PlayerBtn4);
		secondQuestionPanel.add(numberOfRealPlayerLabel);
		//secondQuestionPanel.add(realBtn0);
		secondQuestionPanel.add(realBtn1);
		secondQuestionPanel.add(realBtn2);
		secondQuestionPanel.add(realBtn3);
		secondQuestionPanel.add(realBtn4);
		
		playerConfContainer.add(firstQuestionPanel);
		playerConfContainer.add(secondQuestionPanel);
		playerConfContainer.add(nameLabelAndFieldsPan);
		
	}
	
	private void setupRulesConf() {
		JPanel title = new JPanel();
		JPanel defaultRulePanel = new JPanel();
		JPanel LigthRulePanel = new JPanel();
		JPanel CustomRulePanel = new JPanel();
		
		ruleBtnGp.add(defaultBtn);
		ruleBtnGp.add(ligthBtn);
		ruleBtnGp.add(customBtn);
		
		defaultBtn.addActionListener(ruleListener);
		ligthBtn.addActionListener(ruleListener);
		customBtn.addActionListener(ruleListener);
		
		ruleConfContainer.setLayout(new GridLayout(0,1));
		title.add(selectRulesLabel);
		defaultRulePanel.add(defaultBtn);
		LigthRulePanel.add(ligthBtn);
		CustomRulePanel.add(customBtn);
		
		ruleConfContainer.add(title);
		ruleConfContainer.add(defaultRulePanel);
		ruleConfContainer.add(LigthRulePanel);
		ruleConfContainer.add(CustomRulePanel);
		
	}
	
	private void setupLevelConf() {
		JPanel title = new JPanel();
		JPanel easyLevelPanel = new JPanel();
		JPanel mediumLevelPanel = new JPanel();
		
		levelBtnGp.add(easyBtn);
		levelBtnGp.add(mediumBtn);
		
		easyBtn.addActionListener(levelListener);
		mediumBtn.addActionListener(levelListener);
		
		
		levelConfContainer.setLayout(new GridLayout());
		title.add(selectLevelLabel);
		easyLevelPanel.add(easyBtn);
		mediumLevelPanel.add(mediumBtn);
		levelConfContainer.add(title);
		levelConfContainer.add(easyLevelPanel);
		levelConfContainer.add(mediumLevelPanel);
	}
	
	private void setupDeckConf() {
		JPanel title = new JPanel();
		JPanel defaultDeckPanel = new JPanel();
		JPanel italianDeckPanel = new JPanel();
		JPanel cameroonianPanel = new JPanel();
		
		deckConfContainer.setLayout(new GridLayout());
		deckBtnGp.add(defaultDeckBtn);
		deckBtnGp.add(cameroonianDeckBtn);
		deckBtnGp.add(italianDeckBtn);
		
		defaultDeckBtn.addActionListener(deckListener);
		cameroonianDeckBtn.addActionListener(deckListener);
		italianDeckBtn.addActionListener(deckListener);
		
		title.add(selectDeckLabel);
		defaultDeckPanel.add(defaultDeckBtn);
		italianDeckPanel.add(italianDeckBtn);
		cameroonianPanel.add(cameroonianDeckBtn);
		
		deckConfContainer.add(title);
		deckConfContainer.add(defaultDeckPanel);
		deckConfContainer.add(italianDeckPanel);
		deckConfContainer.add(cameroonianPanel);
	
	}
	
	private void setupCustomRuleConf() {
		JPanel firstQuestionPanel = new JPanel();
		JPanel secondQuestionPanel = new JPanel();
		JPanel thirdQuestionPanel = new JPanel();
		JPanel fourthQuestionPanel = new JPanel();	
		JPanel fifthQuestionPanel = new JPanel();

		yesAceBtn.addActionListener(customRuleListener );
		yesBPBtn.addActionListener(customRuleListener);
		yesDiamNegBtn.addActionListener(customRuleListener);
		yesHJBtn.addActionListener(customRuleListener);
		
		noAceBtn.addActionListener(customRuleListener );
		noPBBtn.addActionListener(customRuleListener);
		noDiamNegBtn.addActionListener(customRuleListener);
		noHJBtn.addActionListener(customRuleListener);
		
		jValueBtn1.addActionListener(customRuleListener);
		jValueBtn2.addActionListener(customRuleListener);
		jValueBtn3.addActionListener(customRuleListener);
		jValueBtn4.addActionListener(customRuleListener);
		jValueBtn5.addActionListener(customRuleListener);
		
		
		
		CustomRuleConfContainer.setLayout(new GridLayout(0,1));
		diamNegBtnGp.add(noDiamNegBtn);
		diamNegBtnGp.add(yesDiamNegBtn);
		firstQuestionPanel.add(diamNegLabel);
		firstQuestionPanel.add(yesDiamNegBtn);
		firstQuestionPanel.add(noDiamNegBtn);
		
		heartJokerBtnGp.add(noHJBtn);
		heartJokerBtnGp.add(yesHJBtn);
		secondQuestionPanel.add(heartJokerLabel);
		secondQuestionPanel.add(yesHJBtn);
		secondQuestionPanel.add(noHJBtn);
		
		blackPairBtnGp.add(noPBBtn);
		blackPairBtnGp.add(yesBPBtn);
		thirdQuestionPanel.add(blackPairLabel);
		thirdQuestionPanel.add(yesBPBtn);
		thirdQuestionPanel.add(noPBBtn);
		
		AceBtnGp.add(noAceBtn);
		AceBtnGp.add(yesAceBtn);
		fourthQuestionPanel.add(aceLabel);
		fourthQuestionPanel.add(yesAceBtn);
		fourthQuestionPanel.add(noAceBtn);
		
		jokerValueBtnGp.add(jValueBtn1);
		jokerValueBtnGp.add(jValueBtn2);
		jokerValueBtnGp.add(jValueBtn3);
		jokerValueBtnGp.add(jValueBtn4);
		jokerValueBtnGp.add(jValueBtn5);
		fifthQuestionPanel.add(jokerValueLabel);
		fifthQuestionPanel.add(jValueBtn1);
		fifthQuestionPanel.add(jValueBtn2);
		fifthQuestionPanel.add(jValueBtn3);
		fifthQuestionPanel.add(jValueBtn4);
		fifthQuestionPanel.add(jValueBtn5);

		
		CustomRuleConfContainer.add(firstQuestionPanel);
		CustomRuleConfContainer.add(secondQuestionPanel);
		CustomRuleConfContainer.add(thirdQuestionPanel);
		CustomRuleConfContainer.add(fourthQuestionPanel);
		CustomRuleConfContainer.add(fifthQuestionPanel);
	}
	
	public void setMyGameView(GameView myGameView) {	
		this.myGameView = myGameView;
	}
	
	public void initConfig() {
		CustomRuleConfContainer.setVisible(false);
		
		realPlayer1Name.setText("PLAYER#0");
		realPlayer2Name.setText("PLAYER#1");
		realPlayer3Name.setText("PLAYER#2");
		realPlayer4Name.setText("PLAYER#3");

		
		namePan1.setVisible(true);
		namePan2.setVisible(false);
		namePan3.setVisible(false);
		namePan4.setVisible(false);
		
		PlayerBtn4.setSelected(true);
		realBtn1.setSelected(true);
		defaultBtn.setSelected(true);
		defaultDeckBtn.setSelected(true);
		mediumBtn.setSelected(true);
		
		yesDiamNegBtn.setSelected(true);
		yesHJBtn.setSelected(true);
		yesBPBtn.setSelected(true);
		yesAceBtn.setSelected(true);
		jValueBtn4.setSelected(true);

		
	}
	
	public void setEnableEveryComponent(boolean lockStatus) {
		setEnabled(lockStatus);
		PlayerBtn3.setEnabled(lockStatus);
		PlayerBtn4.setEnabled(lockStatus);
		
		realBtn1.setEnabled(lockStatus);
		realBtn2.setEnabled(lockStatus);
		realBtn3.setEnabled(lockStatus);
		realBtn4.setEnabled(lockStatus);
		
		realPlayer1Name.setEnabled(lockStatus);
		realPlayer2Name.setEnabled(lockStatus);
		realPlayer3Name.setEnabled(lockStatus);
		realPlayer4Name.setEnabled(lockStatus);
		
		defaultBtn.setEnabled(lockStatus);
		ligthBtn.setEnabled(lockStatus);
		customBtn.setEnabled(lockStatus);
		
		yesDiamNegBtn.setEnabled(lockStatus);
		yesHJBtn.setEnabled(lockStatus);
		yesAceBtn.setEnabled(lockStatus);
		yesBPBtn.setEnabled(lockStatus);
		
		noDiamNegBtn.setEnabled(lockStatus);
		noHJBtn.setEnabled(lockStatus);
		noAceBtn.setEnabled(lockStatus);
		noPBBtn.setEnabled(lockStatus);
		
		jValueBtn1.setEnabled(lockStatus);
		jValueBtn2.setEnabled(lockStatus);
		jValueBtn3.setEnabled(lockStatus);
		jValueBtn4.setEnabled(lockStatus);
		jValueBtn5.setEnabled(lockStatus);
		
		easyBtn.setEnabled(lockStatus);
		mediumBtn.setEnabled(lockStatus);
		
		defaultDeckBtn.setEnabled(lockStatus);
		cameroonianDeckBtn.setEnabled(lockStatus);
		italianDeckBtn.setEnabled(lockStatus);
		
		saveQuitBtn.setEnabled(lockStatus);
		clearBtn.setEnabled(lockStatus);
		//quitBtn.setEnabled(false);
	}
	
	
	
	
	public static ConfigPan getMyInstance() {
		return myInstance;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}





	class PlayerNumberListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(PlayerBtn4.isSelected()) {
				realBtn4.setEnabled(true);
			}else {
				realBtn4.setEnabled(false);
				namePan4.setVisible(false);
				if(realBtn4.isSelected()) {
					realBtn1.setSelected(true);
					namePan2.setVisible(false);
					namePan3.setVisible(false);
				}
			}
			if(PlayerBtn3.isSelected()) {
				myGameView.getMyController().setNumberOfPlayer(3);
			}else if(PlayerBtn4.isSelected()) {
				myGameView.getMyController().setNumberOfPlayer(4);
			}
			
		}
		
	}
	
	class RealPlayerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(realBtn4.isSelected()) {
				myGameView.getMyController().setNumberOfRealPlayer(4);
				namePan1.setVisible(true);
				namePan2.setVisible(true);
				namePan3.setVisible(true);
				namePan4.setVisible(true);
			}else if(realBtn3.isSelected()) {
				myGameView.getMyController().setNumberOfRealPlayer(3);
				namePan1.setVisible(true);
				namePan2.setVisible(true);
				namePan3.setVisible(true);
				namePan4.setVisible(false);
			}else if(realBtn2.isSelected()) {
				myGameView.getMyController().setNumberOfRealPlayer(2);
				namePan1.setVisible(true);
				namePan2.setVisible(true);
				namePan3.setVisible(false);
				namePan4.setVisible(false);
			}else if(realBtn1.isSelected()) {
				myGameView.getMyController().setNumberOfRealPlayer(1);
				namePan1.setVisible(true);
				namePan2.setVisible(false);
				namePan3.setVisible(false);
				namePan4.setVisible(false);
			}else {
				myGameView.getMyController().setNumberOfRealPlayer(0);
				namePan1.setVisible(false);
				namePan2.setVisible(false);
				namePan3.setVisible(false);
				namePan4.setVisible(false);
			}
			
		}
		
	}
	
	class RuleListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(customBtn.isSelected()) {
				CustomRuleConfContainer.setVisible(true);
			}else {
				yesDiamNegBtn.setSelected(true);
				yesHJBtn.setSelected(true);
				yesBPBtn.setSelected(true);
				yesAceBtn.setSelected(true);
				jValueBtn4.setSelected(true);
				myGameView.getMyController().initRules();
				CustomRuleConfContainer.setVisible(false);
			}
			
		}
		
	}
	
	class CustomRuleListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			
		}

	}
	
	class LevelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
	
	class DeckListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(isEnabled()==true) {
					//SAVING NAMES
					this.saveNames();
					//SAVING RULES
					if(defaultBtn.isSelected()) {
						myGameView.getMyController().SetRules(new DefaultRules());
					}else if(ligthBtn.isSelected()) {
						myGameView.getMyController().SetRules(new LigthRules());
					}else if(customBtn.isSelected()) {
						myGameView.getMyController().SetRules(new DefaultRules());
					}
					//SAVING BOT LEVEL
					if(easyBtn.isSelected()) {
						myGameView.getMyController().setStrategy(new NiveauFacile());
					}else {
						myGameView.getMyController().setStrategy(new NiveauMoyen());
					}
					//SAVING DECKSTYLE
					if(defaultDeckBtn.isSelected()) {
						myGameView.getMyController().setDeckStyle(DeckStyle.DEFAULT);
					}else if(cameroonianDeckBtn.isSelected()) {
						myGameView.getMyController().setDeckStyle(DeckStyle.CAMEROONIAN);
					}else if(italianDeckBtn.isSelected()) {
						myGameView.getMyController().setDeckStyle(DeckStyle.ITALIAN);
					}
					
					if(customBtn.isSelected()) {
						saveCustomRules();
						myGameView.getMyController().applyCustomRules();
					}
					Runnable configRun = new Runnable() {				
						public void run() {
							myGameView.getMyController().configParty();
						}
					};
						Thread configThread = new Thread(configRun, "CONFIG_PARTY_THREAD");
						configThread.start();

						try {
							configThread.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					
			}
			myGameView.switchPan((JPanel)HomePan.getInstanceOfHomePan());
			myGameView.switchPanListener.actionPerformed(e);
		}
		
		private void saveCustomRules() {
			if(yesAceBtn.isSelected()) {
				myGameView.getMyController().setCustomRules(3,1);
			}else {
				myGameView.getMyController().setCustomRules(3,0);
			}
			
			if(yesHJBtn.isSelected()) {
				myGameView.getMyController().setCustomRules(1,1);
			}else {
				myGameView.getMyController().setCustomRules(1,0);
			}
			
			if(yesBPBtn.isSelected()) {
				myGameView.getMyController().setCustomRules(2,1);
			}else {
				myGameView.getMyController().setCustomRules(2,0);
			}
			
			if(yesDiamNegBtn.isSelected()) {
				myGameView.getMyController().setCustomRules(0,1);
			}else {
				myGameView.getMyController().setCustomRules(0,0);
			}
			
			if(jValueBtn1.isSelected()) {
				myGameView.getMyController().setCustomRules(4,1);
			}else if(jValueBtn2.isSelected()){
				myGameView.getMyController().setCustomRules(4,2);
			}else if(jValueBtn3.isSelected()){
				myGameView.getMyController().setCustomRules(4,3);
			}else if(jValueBtn4.isSelected()){
				myGameView.getMyController().setCustomRules(4,4);
			}else if(jValueBtn5.isSelected()){
				myGameView.getMyController().setCustomRules(4,5);
			}
			
		}
		
		private void saveNames() {
			if(realBtn4.isSelected()) {
				myGameView.getMyController().addName(realPlayer1Name.getText().toUpperCase(), 0);
				myGameView.getMyController().addName(realPlayer2Name.getText().toUpperCase(), 1);
				myGameView.getMyController().addName(realPlayer3Name.getText().toUpperCase(), 2);
				myGameView.getMyController().addName(realPlayer4Name.getText().toUpperCase(), 3);
			}else if(realBtn3.isSelected()) {
				myGameView.getMyController().addName(realPlayer1Name.getText().toUpperCase(), 0);
				myGameView.getMyController().addName(realPlayer2Name.getText().toUpperCase(), 1);
				myGameView.getMyController().addName(realPlayer3Name.getText().toUpperCase(), 2);
			}else if(realBtn2.isSelected()) {
				myGameView.getMyController().addName(realPlayer1Name.getText().toUpperCase(), 0);
				myGameView.getMyController().addName(realPlayer2Name.getText().toUpperCase(), 1);
			}else if(realBtn1.isSelected()) {	
				myGameView.getMyController().addName(realPlayer1Name.getText().toUpperCase(), 0);
			}
		}
		
	}
	
	class QuitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			myGameView.switchPan((JPanel)HomePan.getInstanceOfHomePan());
			myGameView.switchPanListener.actionPerformed(e);
		}
		
	}
	
	class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			initConfig();
		}
		
	}

	
	
	public void reset() {
		myInstance=null;
	}

}
