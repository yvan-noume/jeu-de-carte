package fr.jest.model;

import java.util.Observable;

import javax.swing.JOptionPane;

import fr.jest.graphic.JestGraphicTimer;
/**
 * Il s'agit du compteur de la Partie<br>
 * Le Compte à rebours est fixé à  10 minutes d'aprèes les règles du jeu<br>
 * 
 * Il'agit du modele du compteur , il est observé par un compteur graphique {@link JestGraphicTimer} qui affiche sur la fenetre de jeu le resultat du compte à rebours<br>
 * 
 *Cette Classe implemente le patron Singleton : on ne peut pas instancier plusieurs timers dans une même partie 
 * 
 * @see JestGraphicTimer
 * @see Observable
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *
 */
public class JestTimer  extends Observable{
	
	private Party myParty ;
	private long currentTimeOnStart ;
	private  long endTime ;//temps de debut du thread + 10 min
	private long currentTimePast = 0 ;
	private long savedCurrentTimePast = 0 ;
	private long minutsRemaining = 0 ;
	private long secondsRemaining = 0 ;
	private boolean timerOnPause = false ;
	
	private static JestTimer myInstance = null;
	
	/**
	 * La methode Get instance permet de récuperer une réference sur un JestTimer à la fois
	 * @param pParty la Partie qui est rattachée au Timer
	 * @return la reference sur le timer si elle existe ou bien une nouvelle référence sur un Timer si il n y en a pas déjà
	 */
	public static JestTimer getInstanceOfTimer(Party pParty) {
		if(JestTimer.myInstance==null) {
			JestTimer.myInstance= new JestTimer(pParty);
			return JestTimer.myInstance;
		}else {
			return JestTimer.myInstance;
		}
	}
	/**
	 * cette Classe anonyme contient  la methode run qui est exécutée dans le Thread de décomptage
	 */
	private Runnable timerRun = new Runnable() {
		@Override
		public void run() {
			while(currentTimePast<endTime) {
				currentTimePast+=1000;
				secondsRemaining=endTime-currentTimePast;
				minutsRemaining=secondsRemaining/60000;
				secondsRemaining=secondsRemaining-minutsRemaining*60000;
				myInstance.setChanged();
				myInstance.notifyObservers(String.format("%02d", minutsRemaining)+":"+String.format("%02d", (secondsRemaining/1000)));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//END THE PARTY
			if(timerOnPause==false) {
				synchronized(myParty) {
					myParty.setBeforelastSequence(true);
					myParty.setLastSequence(true);
					myParty.notifyAll();
					}
			}
		}
	};
	
	//private Thread timerThread ;
	
	private JestTimer(Party pParty) {
		this.myParty=pParty;
	}
	/**
	 * @deprecated
	 * @param period permet de fixer la durée de décomptage
	 */
	public void  setTimer(int period) {
		
	}
	/**
	 * permet de démarrer le décompte<br>
	 * Attention cette methode doit etre appelé une seule fois sur un compteur<br>
	 * pour reprendre un decompte qui a déjà commencé, il faut utiliser la méthode {@link JestTimer#resume()}
	 */
	public void start() {
		timerOnPause=false;
		currentTimeOnStart=System.currentTimeMillis();
		endTime=currentTimeOnStart+600_000;
		//endTime=currentTimeOnStart+300_00;
		currentTimePast=currentTimeOnStart;
		Thread timerThread=new Thread(timerRun, "TIMER_THREAD");
		timerThread.start();
		if(Party.getViewWherePartyStarted()==false) {
			System.out.println("Party Timer has Started!!!");
		}else {
			Thread startMsg =new Thread(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null,"Party Timer started !!!", "Timer Started", JOptionPane.INFORMATION_MESSAGE);
				}
			},"START_MSG") ;
			
			startMsg.start();
		}
		
	}
	/**
	 * permet d'arreter le décompte
	 */
	public void pause() {
	
		savedCurrentTimePast=currentTimePast;
		currentTimePast=endTime+1000;
		
		timerOnPause=true;
	}
	/**
	 *  permet de reprendre le deécompte lorsqu'il a été mis en pause<br>
	 *  
	 */
	public void resume() {

		timerOnPause=false;
		Thread timerThread= new Thread(timerRun, "RESUME_TIMER");
		currentTimePast=savedCurrentTimePast;
		timerThread.start();
	}
	/**
	 * @deprecated  cette methode n'est pas implémentée
	 */
	public void resetTimer() {
		
	}
	
	/**
	 * permet de dereférencer le Timer (detruire) le Timer de la Partie afin de pouvoir en créer un nouveau
	 */
	public void reset() {
		myInstance=null;
	}

}
