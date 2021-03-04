package fr.jest.model;

import java.util.ArrayList;
/**
 * Cette Classe d�finie une strat�gie "Moyen" pour les Joueurs Virtuels
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 * @see Strategy
 * @see VirtualPlayer
 */
public class NiveauMoyen implements Strategy {
	/**
	 * constructeur par d�faut
	 */	
	public NiveauMoyen() {
		// TODO Auto-generated constructor stub

	}
	/**
	 * Afficher le deux cartes avant de cacher celle qu'on a choisi de cacher !!
	 *AU NIVEAU FACILE , LE JOUEUR VIRTUEL A 50% DE CHANCE DE MONTRER SA MEILLEURE , CACHANT AINSI 
	 *SA CARTE LA PLUS DEFAVORABLE 
	 */
	@Override
	public void makeOffer(Offer myOffer) {
		//Afficher le deux cartes avant de cacher celle qu'on a choisi de cacher !!
		//AU NIVEAU FACILE , LE JOUEUR VIRTUEL A 50% DE CHANCE DE MONTRER SA MEILLEURE , CACHANT AINSI 
		//SA CARTE LA PLUS DEFAVORABLE 
		double coupDeChance = Math.random();
		myOffer.showAllCards();
		if(coupDeChance <= 0.5) {
			RegularCard worstCard = myOffer.getWorstCard();
			worstCard.setChown(false); // LAISSER VISIBLE LA MEILLEURE CARTE
			worstCard.setCardName();
		}else {
			RegularCard bestCard= myOffer.getBestCard();
			bestCard.setChown(false); // LAISSER VISIBLE LA PIRE CARTE
			bestCard.setCardName();
		}
	}
	/**
	 * prend une carte al�atoire chez un joueur al�atoire <br>
	 */
	@Override
	public int takeCard(ArrayList<Player> playerList , Player taker) { //RETURNS THE INDEX OF THE PLAYER WHO GOT HIS CARD TAKEN
		int targetIndex=0;
		double probability = Math.random();
		int randomIndex = (int)((probability*playerList.size())%playerList.size());
		int limit = 0 ;
		
		do {
			limit++;
			probability = Math.random();
			randomIndex = (int)((probability*playerList.size())%playerList.size());
		}while( (playerList.get(randomIndex).getOffer().size()<=1 || playerList.get(randomIndex).equals(taker)) && limit < 1000 );
			
			if(limit<1000 && taker.alreadyTookACard()==false && playerList.get(randomIndex).getOffer().size()>1 && !playerList.get(randomIndex).equals(taker) ) {
				taker.setAlreadyTookACard(true);
				if(probability>0.5) {
					Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+playerList.get(randomIndex).getName()+" ||==== {{ "+playerList.get(randomIndex).getOffer().getHiddenCard().toString()+"  }} ===>>> "+taker.getName());
					taker.getJest().addCard(playerList.get(randomIndex).getOffer().giveHiddenCard()); 
					targetIndex=randomIndex;
				}else {
					Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+playerList.get(randomIndex).getName()+" ||==== {{ "+playerList.get(randomIndex).getOffer().getVisibleCard().toString()+"  }} ===>>> "+taker.getName());
					taker.getJest().addCard(playerList.get(randomIndex).getOffer().giveVisibleCard()); 
					targetIndex=randomIndex;
				}
			}else if(taker.alreadyTookACard()==false && taker.getOffer().size()>1){
				taker.setAlreadyTookACard(true);
				Party.setBroadCast(Party.getBroadCast()+"\nTransaction :: "+playerList.get(randomIndex).getName()+" ||==== {{ "+playerList.get(randomIndex).getOffer().getCard().toString()+"  }} ===>>> "+taker.getName());
				taker.getJest().addCard(taker.getOffer().giveCard());
				for(int k=0; k<playerList.size();k++) {if(playerList.get(k)!=null) {
					if(playerList.get(k)==taker) {
						targetIndex=k;
					}
				}}
			}else {
				targetIndex=(int)((probability*playerList.size())%playerList.size());
			}
		
		
		

		return targetIndex ;
	}
	
	
	public void takeBestCard(RegularCard[] possibilities) {
		
	}
	
	


}
