package fr.jest.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Cette classe en plus des specifications de la classe {@link Player} qu'elle étend , implemente la strategie des  {@link VirtualPlayer} et leur accorde un Nom propre
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see Player
 * @see Strategy
 * 
 */
public class VirtualPlayer extends Player {
	/**
	 * La {@link Strategy} du {@link VirtualPlayer}
	 */
	private Strategy myStrategy ;
	/**
	 * La Liste de tous les noms utilisés par {@link VirtualPlayer}
	 */
	private static ArrayList<String> virtualNames = new ArrayList<String>();
	

	
	
	/**
	 * Constructeur par défaut  : ce constructeur n'est pas utilisé dans ce projet
	 */
	public VirtualPlayer() {
		if(virtualNames.size()<Party.getGameNumberOfPlayer()) {
			VirtualPlayer.fillVirtualNames();
		}
		this.playerId=new Identity(VirtualPlayer.virtualNames.remove(0)) ;
		this.myStrategy = Party.getVirtualPlayerStrategy();

		}
		
	/**
	 * Ce constructeur fixe la {@link Strategy}  du {@link VirtualPlayer} à l'instanciation
	 * @param Level la Strategie du {@link VirtualPlayer}
	 */
	public VirtualPlayer(Strategy Level) {
		if(virtualNames.size()<Party.getGameNumberOfPlayer()) {
			VirtualPlayer.fillVirtualNames();
		}
		this.playerId=new Identity(VirtualPlayer.virtualNames.remove(0)) ;
		this.myStrategy = Level;
	}
	/**
	 * Cette methode permet aux {@link VirtualPlayer} de prende une Carte Chez un {@link Player }
	 * @param playerList la liste des joueurs de la {@link Party}
	 * @return l'indice du {@link Player}  victime dans la liste {@link Player}
	 */
	public  int takeCard(ArrayList<Player> playerList ) {
		int targetIndex = 0;
		targetIndex=this.myStrategy.takeCard(playerList,this);
		//System.out.println(Party.getBroadCast());
		setChanged();
		notifyObservers();
		return targetIndex;
	}
	/**
	 * permet au {@link VirtualPlayer}  de choisir la {@link RegularCard} carte à cacher pour faire son {@link Offer}
	 */
	public  void makeOffer() {
		this.myStrategy.makeOffer(this.getOffer());
		this.setAlreadyTookACard(false);
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * Determine de maniere Aleatoire tous les noms qui seront utilisés par les {@link VirtualPlayer} durant la {@link Party}
	 */
	private static void fillVirtualNames() {
	
		int randomLine = ((int)((Math.random()*1000)))%10;
		int counter = 1 ;

		try {
			BufferedReader br = new BufferedReader(new FileReader("Files/PlayerName.txt"));
		    String name = br.readLine();
		    while(VirtualPlayer.virtualNames.size()<Party.getGameNumberOfPlayer()) {
		    	randomLine = ((int)((Math.random()*1000)))%10;	
		    	counter = 1 ;
			    while (name != null && counter  < randomLine) {
			    	counter++;
			        name = br.readLine();
			    }
			    if(VirtualPlayer.virtualNames.contains((String)name.toUpperCase().trim().split(" ")[0])==false) {
				    VirtualPlayer.virtualNames.add(name.toUpperCase().trim().split(" ")[0]);
			    }
		    }
		    br.close();
		}catch (Exception e) {
			//System.out.println("[Error!!!] NAME FILE NOT FOUND");
		}
	}
	
	
	
	
}
