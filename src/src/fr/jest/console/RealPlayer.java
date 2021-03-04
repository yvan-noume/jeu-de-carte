package fr.jest.console;

import java.io.IOException;
import java.util.ArrayList;

import fr.jest.controller.PartyConsoleEngine;
import fr.jest.model.Jest;
import fr.jest.model.Offer;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.RegularCard;
/**
 * cette classe implémente des méthodes qui s'occupe des intéraction entre le jeu et l'utilisateur en mode Console
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *
 */
public class RealPlayer extends Player {
	/**
	 * Champs non-impléménté
	 */
	private StringBuffer canalDeCommunication ;
	/**
	 * Constructeur du {@link RealPlayer}
	 * @param name Le nom du {@link Player}
	 * @param pCanalDeCommunication - non impléménté
	 */
	public RealPlayer(String name , StringBuffer pCanalDeCommunication) {
		super(name);
		canalDeCommunication=pCanalDeCommunication;
		
	}
	/**
	 * permet au Joueur de selectionner la {@link RegularCard } qu'il souhaite ajouter à son {@link Jest}<br>
	 * cette méthode contraint le {@link Player} qui joue à respecter les règles du Jeu :
	 * <ul>
	 * <li>interdiction de prendre une carte chez un {@link Player} qui a une {@link Offer} incomplete</li>
	 * <li>interdiction de prendre une carte chez soi même sauf si notre {@link Offer} est la seul {@link Offer} complet qui reste</li>
	 * <li></li>
	 * </ul>
	 * @param playerList la liste de {@link Player} de la {@link Party}
	 * @return l'indice du Joueur chez qui la Carte a été prise
	 */
	public int takeCard(ArrayList<Player> playerList) {
		this.setAlreadyTookACard(true);
		int choice =-1 ;
		Player target = null ;
		int targetIndex = 0;
		boolean selftarget = true ;
		//this.canalDeCommunication.append("Mr./Mss"+this.getName()+" , Here are the Offers\n");
		System.out.println("Mr./Mss"+this.getName()+" , Here are the Offers") ;
		for(int k= 0 ; k < playerList.size();k++) {if(playerList.get(k) != null) {
			playerList.get(k).getOffer().printOffer();
			//this.canalDeCommunication.append(playerList.get(k).getOffer().toString()+"\n");
			if( playerList.get(k).getOffer().size() > 1 && playerList.get(k)!=this) {
				selftarget=false;
			}
		}}
		if(selftarget) {
			int cardChoice = -1 ;
			System.out.println("[!!!] You must take a card in your Own Offer !!! ") ;
			System.out.println("Which card would you like to Take ? ") ;
			System.out.println("\tPress 0 to take Card#00 \n") ;
			System.out.print("\tPress 1 to take Card#01   :: Your choice :: ");
			cardChoice=readCard();
			
			Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+this.getName()+" is Takin his Own Card :: "+this.getOffer().getCard(cardChoice).toString());
			System.out.println(" ");
			this.getJest().addCard(this.getOffer().giveCard(cardChoice));
			for(int k=0; k<playerList.size();k++) {if(playerList.get(k)!=null) {
				if(playerList.get(k)==this) {
					targetIndex=k;
				}
			}}
			Party.clrscr();
			setChanged();
			notifyObservers();
			return targetIndex;
		}
		System.out.println("[!!!]NOTE -- Press '"+(Party.getGameNumberOfPlayer()+3)+"' To check Previous Transaction") ;
		System.out.println("[!!!]NOTE -- Press '"+(Party.getGameNumberOfPlayer()+4)+"' To check Your Own Jest") ;
		System.out.println("[!!!]NOTE -- Press '"+(Party.getGameNumberOfPlayer()+5)+"' To check The Trophies") ;
		System.out.println("Which Player is your target ?") ;
		for(int k= 0 ; k < playerList.size();k++) {if(playerList.get(k) != null) {
			System.out.println("\tPress '"+k+"' to select Mr."+playerList.get(k).getName()) ;
		}}
		System.out.print("\t\t\t Your Choice :: ") ;
		
		choice=readPlayer();
		while (playerList.get(choice).getOffer().size()<=1 || playerList.get(choice)==this) {
			if(playerList.get(choice).getOffer().size()<=1) {
				System.out.print("\t "+playerList.get(choice).getName()+"' offer is Incomplet !! \n\t\t\t Your Choice :: ") ;
			}else if (playerList.get(choice)==this) {
				System.out.print("\t "+playerList.get(choice).getName()+"' You Should not take a card in your Own Offer !! \n\t\t\t Your Choice :: ") ;
			}
			choice=readPlayer();
		}
			target=playerList.get(choice);
			target.getOffer().printOffer();

			System.out.println("Which card would you like to Take ? ") ;
			System.out.println("\tPress 0 to take Card#00 ") ;

			System.out.print("\tPress 1 to take Card#01   :: Your choice :: \n") ;
			
			int Cardchoice=readCard();
			this.canalDeCommunication.append("\n");
			System.out.print("\n") ;
			Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+target.getName()+" ||==== {{ "+target.getOffer().getCard(Cardchoice).toString()+"  }} ===>>> "+this.getName());
			this.getJest().addCard(target.getOffer().giveCard(Cardchoice)); 
			targetIndex=choice;
		
		Party.clrscr();
		return targetIndex;
	}
	/**
	 * cette methode Permet au {@link RealPlayer} de choisir la {@link RegularCard } qu'il souhaite cacher
	 */
	public void makeOffer() {

			this.setAlreadyTookACard(false);
			int    intChoice = -1;
			System.out.println("[!!!]NOTE -- Press '"+(Party.getGameNumberOfPlayer()+4)+"' To check Your Own Jest") ;
			System.out.println("[!!!]NOTE -- Press '"+(Party.getGameNumberOfPlayer()+5)+"' To check The Trophies") ;
			System.out.println("Mr./Mss. "+this.getName()+" ,Here is your Offer ") ;
			this.getOffer().showAllCards();
			this.getOffer().printOffer();
			System.out.println("Mr./Mss. "+this.getName()+" ,Here is your Offer \n") ;
			System.out.println("Which card would you like to Take ? ") ;
			System.out.println("\tPress 0 to take Card#00 ") ;
			System.out.println("\tPress 1 to take Card#01   :: Your choice :: \n") ;
				intChoice=readCard();
			if(intChoice == 0 || intChoice == 1) {
				this.offer.hideCard(intChoice);
			}else {
				 System.out.println("[!!!]Error ! FATAL ERROR");
				 System.exit(-1);
			}
		
		
		setChanged();
		notifyObservers();
		Party.clrscr();
	}
	
	/**
	 * permet de choisir un {@link Player} cible
	 * @return un entier qui correspond à la position du {@link Player} cible
	 */
	private int readPlayer() {
		String asciiChoice = "" ;
		int    intChoice = -1;
		while (intChoice<0 || intChoice>(Party.getGameNumberOfPlayer()-1)  ) {
			try { 	asciiChoice=PartyConsoleEngine.getInput().readLine();
					intChoice = Integer.parseInt(asciiChoice);
					if(intChoice==Party.getGameNumberOfPlayer()+4 ) {
						this.jest.printJest();
					}else if(intChoice==Party.getGameNumberOfPlayer()+5) {
						Party.getPartyTrophies().printTropies();
					}else if(intChoice==Party.getGameNumberOfPlayer()+3) {
						System.out.println(Party.getBroadCast());
					}
					if((intChoice<0 || intChoice>(Party.getGameNumberOfPlayer()-1)) && ( intChoice!=Party.getGameNumberOfPlayer()+5 || intChoice!=Party.getGameNumberOfPlayer()+4 || intChoice!=Party.getGameNumberOfPlayer()+3 ) ) {
						System.out.print("\tplease enter '0' OR '1' OR '2' OR '3' eventually : ");
						intChoice=-1;
					}
				 }catch(NumberFormatException  e) {
						System.out.print("\tplease enter '0' OR '1' OR '2' OR '3' eventually : ");
				  intChoice=-1;

				 }catch (IOException e) {
					// TODO: handle exception
					 e.printStackTrace();
				}
		}
		return intChoice;
	}
	/**
	 * permet de choisir une {@link RegularCard}
	 * @return un entier qui correspond à la position de la {@link RegularCard} qu'on souhaite ajouter à notre {@link Jest}
	 */
	private int readCard() {
		String asciiChoice = "" ;
		int    intChoice = -1;
		while ((intChoice != 0 )&&(intChoice !=1)) {
			try { 	asciiChoice=PartyConsoleEngine.getInput().readLine();
					intChoice = Integer.parseInt(asciiChoice);
					if(intChoice==Party.getGameNumberOfPlayer()+4 ) {
						this.jest.printJest();
					}else if(intChoice==Party.getGameNumberOfPlayer()+5) {
						Party.getPartyTrophies().printTropies();
					}else if(intChoice==Party.getGameNumberOfPlayer()+3) {
						System.out.println(Party.getBroadCast());
					}
					if((intChoice != 0) && (intChoice != 1) && (intChoice!=Party.getGameNumberOfPlayer()+5 || intChoice!=Party.getGameNumberOfPlayer()+4|| intChoice!=Party.getGameNumberOfPlayer()+3) ) {
						System.out.print("please enter '0' OR '1' : ");
						intChoice=-1;
					}

				 }catch(NumberFormatException  e) {
				   System.out.print("please enter '0' OR '1' : ");
				  intChoice=-1;

				 }catch (IOException e) {
					// TODO: handle exception
					 e.printStackTrace();
				}
		}
		return intChoice;
	}


	
}
