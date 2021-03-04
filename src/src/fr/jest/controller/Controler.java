package fr.jest.controller;

import fr.jest.model.Deck;
import fr.jest.model.Party;
import fr.jest.model.Player;
import fr.jest.model.Trophies;
/**
 * Cette interface regroupe toutes les méthodes qu'un Controleur doit obligatoirement implémenter
 * @author Jeff Jordan
 *
 */
public interface Controler {
/**
 * Cette Methode permet de Lancer une {@link Party}<br>
 * si La {@link Party} n'est pas encore configurée, elle etablie la configuration par défaut avant  démarrer la {@link Party}
 */
public void beginParty();
/**
 * Cette Methode Permet de Configurer une {@link Party}
 */
public void configParty();
/**
 * Cette est reelement implémentée dane Controleur Console<br>
 * elle permet de recommencer les séquence jusqu'à ce que la {@link Party} se termine
 */
public void sequenceLoop();
/**
 * Cette methode permtet de créer les {@link Player} de la {@link Party} , elle est toujour appelée après la configuration de la {@link Party}
 */
public void createPlayers();
/**
 * Cette methode permet d'instancier certains membre de la {@link Party} après que cette dernière ait été configurée <br>
 * par exemple : le {@link Deck} et les {@link Trophies}
 */
public void setParty();
/**
 * renvoie une reférence sur la {@link Party} que le {@link Controler} gère actuellement
 * @return une reférence sur la {@link Party} que le {@link Controler} gère actuellement
 */
public Party getMyParty();
}
