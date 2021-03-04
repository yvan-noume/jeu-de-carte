package fr.jest.model;

import java.util.Observable;

/**
 * Cette classe décrit un joueur de manière générale.<br>

 * Un Joueur peu importe sa  nature profonde (Reel ou Virtuel) 
 * possede :<br> <ul>
 * <li>Une Identité  :  au moins un nom,</li>
 * <li>Un Jest : dans lequel il conserve les cartes qu'il récupère chez les autres joueurs,</li>
 * <li>Une Offer :  deux cartes dont il dispose dans la main au début de chaque séquence,</li>
 * 
 * </ul>
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see VirtualPlayer
 * @see fr.jest.console.RealPlayer
 * @see Jest
 * @see Offer
 * @see Identity
 * @see Visitable
 *
 */
public class Player extends Observable implements Visitable {
	/**
	 * L'identité du Joueur  :  il s'agit à minima d'un nom unique
	 */
	protected Identity playerId ;
	/**
	 * Le Jest du Joueur
	 */
	protected Jest jest ;
	/**
	 * L'offre du Joueur
	 */
	protected Offer offer;
	/**
	 * Ce membre permet de savoir si le joueur a déja joué pendant une séquence car , un joueur  est autorisé  à faire une offre e à prendre une carte chez une cible une seule fois pendant une même séquence
	 */
	protected boolean alreadyTookACard = false ;
	
	public Player() {
	this.playerId=new Identity() ;
	
	}
	public Player(String name) {
		this.playerId=new Identity(name) ;
		
	}
	public Player(String name,String Surname) {
		this.playerId=new Identity(name,Surname) ;
		
	}
	public Player(String name,String Surname,String Nickname) {
		this.playerId=new Identity(name,Surname,Nickname) ;
		
	}
	
	
	public String getName() {
		return this.playerId.getName();
	}
	public String getSurname() {
		return this.playerId.getSurname();
	}
	public String getNickname() {
		return this.playerId.getNickname();
	}
	public void setName(String name) {
		this.playerId.setName(name);
	}
	public void setSurname(String surname) {
		this.playerId.setSurname(surname);
	}
	public void setNickname(String nickname) {
		this.playerId.setNickname(nickname);
	}


	public  Jest getJest() {
		return jest;
	}
	
	public void setJest(Jest jest) {
		this.jest = jest;
	}


	public  Offer getOffer() {
		return offer;
	}


	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	

	public void setAlreadyTookACard(boolean alreadyTookACard) {
		this.alreadyTookACard = alreadyTookACard;
	}


	public boolean alreadyTookACard() {
		return alreadyTookACard;
	}
	/**
	 * C'est grace à cette methode que la Partie peut determiner La valeur du Jest du Joueur en appliquant les règles définies par {@link Party#setRules(JestEvalMethod)}
	 * @return la valeur du Jest du Joueur
	 */
	@Override
	public int accept(Visitor myVisitor) {
		if(myVisitor instanceof Party) {
			Party partyCast = (Party) myVisitor;
			return partyCast.visit(Party.getRules(), this);
		}else {
			   System.out.println("[!!!] Fatal Error , The Visitor is Not the Party !!!");
			   System.exit(5);
			   return 5;
		}

	}
	/**
	 * Cette méthode renvoie juste le nom du joueur sous forme de String
	 * @return le nom du joueur 
	 */
	public String toString() {
		return this.getName();
	}

}
