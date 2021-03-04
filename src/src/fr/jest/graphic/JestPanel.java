package fr.jest.graphic;
/**
 * Cette interface d�crit toutes les m�thodes communes � tous les �crans du jeu
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 * @see HomePan
 * @see ConfigPan
 * @see GamePanel
 * @see GameView
 */
public interface JestPanel {
	/**
	 * D�finit la mani�re dint les composant de l'�cran doivent �tre assembl�s
	 */
	public void assemble();
	/**
	 * renvoie true si l'�cran est d�j� assembl�
	 * @return renvoie true si l'�cran est d�j� assembl� ,renvoie false dans le cas contraire
	 */
	public boolean isAssembled();
	/**
	 * definie la fen�tre � laquelle l'�cran est rattach�
	 * @param pGameView  la fen�tre � laquelle l'�cran est rattach�
	 */
	public void setMyGameView(GameView pGameView);
}
