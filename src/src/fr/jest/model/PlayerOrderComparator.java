package fr.jest.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Cette classe permet de determiner l'ordre de passage des joueurs <br>
 * 
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *
 */
public class PlayerOrderComparator implements Comparator<Player> {
	/**
	 * Les Joueurs à classer
	 */
	private  List<Player> playerList ;//= new ArrayList<Player>();
	/**
	 * Un {@link OfferComparator qui va permettre de comparer les offres des Joueurs}
	 */
	private OfferComparator offerComp = new OfferComparator();
	/**
	 * La liste  des index des joueurs de l'arrayList de depart dans l'ordre du plus prioritaire au moins prioritaire
	 */
	private int[] playerOrder ;
	/**
	 * Constructeur
	 * @param playerList les joueurs à ordonner
	 */
	public PlayerOrderComparator(ArrayList<Player> playerList) {
		this.playerList=playerList;
		this.playerOrder =  new int[playerList.size()];
	}
	/**
	 * Determine l'ordre de passage des joueurs et le met dans le tableau {@link PlayerOrderComparator#playerOrder}
	 */
	public void setOrder() {
		int[] localOrder = new int[this.playerOrder.length];
		int etape = 0 , bestPlayerIndex = 0;
		
		for(int k = 0 ; k < this.playerList.size();k++) {
			localOrder[k]=k;
		}
		//1er passage
			for(etape=0;etape<this.playerList.size();etape++) {
				bestPlayerIndex=etape;
				for(int i = etape ; i<this.playerList.size();i++) {
					if(offerComp.compare(this.playerList.get(localOrder[i]).getOffer(),this.playerList.get(localOrder[bestPlayerIndex]).getOffer())>0) {
						if(this.playerList.get(i).alreadyTookACard()==false && this.playerList.get(i).getOffer().size()>1 ) {
							bestPlayerIndex=i;
						}
					}
				}
				int tmp = localOrder[bestPlayerIndex];
				localOrder[bestPlayerIndex]=localOrder[etape];
				localOrder[etape]=tmp;		
			}
			
		this.playerOrder= localOrder ;
	}
	
	/**
	 * permet de mettre à jour l'ordre de passage des joueurs à chaque fois qu'un joueur a pris une Carte
	 * @param nextPlayerIndex l'index du prochain joueur , à savoir le joueur chez qui on a pris une carte si ce dernier n'a pas encore jouer
	 */
	public void updateOrder(int nextPlayerIndex) { 
		int[] localOrder = new int[this.playerOrder.length];
		
		if(!this.playerList.get(nextPlayerIndex).alreadyTookACard()) {
			localOrder[0]=nextPlayerIndex;
		}
		
		this.playerOrder= localOrder ;
	}
	
	/**
	 * 
	 * @return  le prochain joueur 
	 */
	public Player nextPlayer() {
		return this.playerList.get(this.playerOrder[0]) ;
	}
	/**
	 * compare deux	 joueurs en fonctions de leur offre
	 */
	@Override
	public int compare(Player o1, Player o2) {
		// TODO Auto-generated method stub
		if(o1.alreadyTookACard() && !o2.alreadyTookACard()) {
			return -1;
		}else if(!o1.alreadyTookACard() && o2.alreadyTookACard()) {
			return 1;
		}else {
			return offerComp.compare(o1.getOffer(), o2.getOffer());
		}
	}
	/**
	 * Affiche les joueurs dans l'ordre de passage
	 */
	public void printOrder() {
		for(int i =0 ; i<this.playerOrder.length;i++) {
			System.out.println(this.playerList.get(this.playerOrder[i]).getName());
		}
	}
	/**
	 * 
	 * @return le tableau contenant l'ordre de passage des joueurs
	 */
	public int[] getPlayerOrder() {
		return playerOrder;
	}
	

	
	
	

}
