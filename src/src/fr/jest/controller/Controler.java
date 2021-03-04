package fr.jest.controller;

import fr.jest.model.Deck;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.Trophies;
/**
 * Cette interface regroupe toutes les m�thodes qu'un Controleur doit obligatoirement impl�menter
 * @author Jeff Jordan
 *
 */
public interface Controler {
/**
 * Cette Methode permet de Lancer une {@link Party}<br>
 * si La {@link Party} n'est pas encore configur�e, elle etablie la configuration par d�faut avant  d�marrer la {@link Party}
 */
public void beginParty();
/**
 * Cette Methode Permet de Configurer une {@link Party}
 */
public void configParty();
/**
 * Cette est reelement impl�ment�e dane Controleur Console<br>
 * elle permet de recommencer les s�quence jusqu'� ce que la {@link Party} se termine
 */
public void sequenceLoop();
/**
 * Cette methode permtet de cr�er les {@link Player} de la {@link Party} , elle est toujour appel�e apr�s la configuration de la {@link Party}
 */
public void createPlayers();
/**
 * Cette methode permet d'instancier certains membre de la {@link Party} apr�s que cette derni�re ait �t� configur�e <br>
 * par exemple : le {@link Deck} et les {@link Trophies}
 */
public void setParty();
/**
 * renvoie une ref�rence sur la {@link Party} que le {@link Controler} g�re actuellement
 * @return une ref�rence sur la {@link Party} que le {@link Controler} g�re actuellement
 */
public Party getMyParty();
}
