package fr.jest.model;

import java.util.Observable;

/**
 * Cette classe d�crit un joueur de mani�re g�n�rale.<br>

 * Un Joueur peu importe sa  nature profonde (Reel ou Virtuel) 
 * possede :<br> <ul>
 * <li>Une Identit�  :  au moins un nom,</li>
 * <li>Un Jest : dans lequel il conserve les cartes qu'il r�cup�re chez les autres joueurs,</li>
 * <li>Une Offer :  deux cartes dont il dispose dans la main au d�but de chaque s�quence,</li>
 * 
 * </ul>
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
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
	 * L'identit� du Joueur  :  il s'agit � minima d'un nom unique
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
	 * Ce membre permet de savoir si le joueur a d�ja jou� pendant une s�quence car , un joueur  est autoris�  � faire une offre e � prendre une carte chez une cible une seule fois pendant une m�me s�quence
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
	 * C'est grace � cette methode que la Partie peut determiner La valeur du Jest du Joueur en appliquant les r�gles d�finies par {@link Party#setRules(JestEvalMethod)}
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
	 * Cette m�thode renvoie juste le nom du joueur sous forme de String
	 * @return le nom du joueur 
	 */
	public String toString() {
		return this.getName();
	}

}
