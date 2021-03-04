package fr.jest.model;

import fr.jest.controller.PartyConsoleEngine;

/**
 * Cette Classe est un Wrapper du type primitif Boolean
 * <br> Cette classe a ete cree pour resoudre certains problèmes de synchronisation({@link PartyConsoleEngine#beginParty()}) par le biais de Verrou ( lock) pour les blocs sunchronized car ces blocs ne prennent pas en parametres des types primitifs
 * <br>Cette classe est inspirée de la Classe {@link Boolean}
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 *	@see Party
 *@see PartyConsoleEngine
 *@see Boolean
 *
 */
public class JestBoolean {
	/**
	 * a {@link JestBoolean} is set to false by default
	 */
	private boolean booleanValue = false ;
	
	public JestBoolean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * construct a new JestBoolean with the specidied value
	 * @param param the value of the boolean wrapped
	 */
	public JestBoolean(boolean param) {
		booleanValue=param;
	}

	/**
	 * 
	 * @return the value of the boolean wrapped
	 */
	public boolean booleanValue() {
		return booleanValue;
	}
	
	/**
	 * Sets the value of the boolean wrapped
	 * @param myValue the value of the boolean wrapped
	 */
	public void setBooleanValue(boolean myValue) {
		this.booleanValue = myValue;
	}
	
	
}
