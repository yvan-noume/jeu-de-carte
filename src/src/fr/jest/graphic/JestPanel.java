package fr.jest.graphic;
/**
 * Cette interface décrit toutes les méthodes communes à tous les écrans du jeu
 * @author Jeff Jordan Tchelong Nwabo - Université de Technologie de Troyes - Réseau et Télécommunication Semestre 01- Automne 2019 - LO02 
 * @see HomePan
 * @see ConfigPan
 * @see GamePanel
 * @see GameView
 */
public interface JestPanel {
	/**
	 * Définit la manière dint les composant de l'écran doivent être assemblés
	 */
	public void assemble();
	/**
	 * renvoie true si l'écran est déjà assemblé
	 * @return renvoie true si l'écran est déjà assemblé ,renvoie false dans le cas contraire
	 */
	public boolean isAssembled();
	/**
	 * definie la fenêtre à laquelle l'écran est rattaché
	 * @param pGameView  la fenêtre à laquelle l'écran est rattaché
	 */
	public void setMyGameView(GameView pGameView);
}
