package fr.jest.model;
/**
 * Cette Classe Permet de definir l'identie du Joueur à travers un Nom
 * qui est soit determiner par un identifiant unique soit fournit par l'utilisateur
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *@see Player
 */
public class Identity {
	private static int id = 0 ;
	private String name ;
	private String surname ;
	private String nickname ;
	
	public Identity() {
		this.name="Player#"+Identity.id;
		this.surname="Player#"+Identity.id;
		this.nickname="Player#"+Identity.id;
		Identity.id++;

		
	}
	public Identity(String pName ,String pSurname ,String pNickname) {
		this.name=pName;
		this.surname=pSurname;
		this.nickname=pNickname;
		Identity.id++;

	}
	
	public Identity(String pName ,String pSurname ) {
		this.name=pName;
		this.surname=pSurname;
		this.nickname="Player#"+Identity.id;
		Identity.id++;

	}
	
	public Identity(String pName ) {
		this.name=pName;
		this.surname=pName;
		this.nickname="Player#"+Identity.id;
		Identity.id++;

	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
