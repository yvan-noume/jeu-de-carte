package fr.jest.model;

import java.util.Observable;

import javax.swing.JOptionPane;

import fr.jest.graphic.JestGraphicTimer;
/**
 * Il s'agit du compteur de la Partie<br>
 * Le Compte � rebours est fix� �  10 minutes d'apr�es les r�gles du jeu<br>
 * 
 * Il'agit du modele du compteur , il est observ� par un compteur graphique {@link JestGraphicTimer} qui affiche sur la fenetre de jeu le resultat du compte � rebours<br>
 * 
 *Cette Classe implemente le patron Singleton : on ne peut pas instancier plusieurs timers dans une m�me partie 
 * 
 * @see JestGraphicTimer
 * @see Observable
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
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
	 * La methode Get instance permet de r�cuperer une r�ference sur un JestTimer � la fois
	 * @param pParty la Partie qui est rattach�e au Timer
	 * @return la reference sur le timer si elle existe ou bien une nouvelle r�f�rence sur un Timer si il n y en a pas d�j�
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
	 * cette Classe anonyme contient  la methode run qui est ex�cut�e dans le Thread de d�comptage
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
	 * @param period permet de fixer la dur�e de d�comptage
	 */
	public void  setTimer(int period) {
		
	}
	/**
	 * permet de d�marrer le d�compte<br>
	 * Attention cette methode doit etre appel� une seule fois sur un compteur<br>
	 * pour reprendre un decompte qui a d�j� commenc�, il faut utiliser la m�thode {@link JestTimer#resume()}
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
	 * permet d'arreter le d�compte
	 */
	public void pause() {
	
		savedCurrentTimePast=currentTimePast;
		currentTimePast=endTime+1000;
		
		timerOnPause=true;
	}
	/**
	 *  permet de reprendre le de�compte lorsqu'il a �t� mis en pause<br>
	 *  
	 */
	public void resume() {

		timerOnPause=false;
		Thread timerThread= new Thread(timerRun, "RESUME_TIMER");
		currentTimePast=savedCurrentTimePast;
		timerThread.start();
	}
	/**
	 * @deprecated  cette methode n'est pas impl�ment�e
	 */
	public void resetTimer() {
		
	}
	
	/**
	 * permet de deref�rencer le Timer (detruire) le Timer de la Partie afin de pouvoir en cr�er un nouveau
	 */
	public void reset() {
		myInstance=null;
	}

}
