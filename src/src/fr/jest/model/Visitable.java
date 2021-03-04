package fr.jest.model;
/**
 * Cette interface est impl�mnt�e par tous les {@link Player} de la {@link Party}<br>
 * Elle declare une methode {@link Visitable#accept(Visitor)} qui permet � un {@link Visitor} (la {@link Party}) de calculer la Valeur de {@link Jest}  du {@link Player}
 * @author Jeff Jordan Tchelong Nwabo - Universit� de Technologie de Troyes - R�seau et T�l�communication Semestre 01- Automne 2019 - LO02 
 *@see Party
 *@see Player
 */
public interface Visitable {
	/**
	 * 
	 * @param myVisitor Le Visiteur qui dont la methode {@link Visitor#visit(JestEvalMethod, Player)}  va permettre de determiner la valeur du {@link Jest} du {@link Player} qui est visit�
	 * @return la Valeur du {@link Jest} du {@link Player}
	 */
	public int accept(Visitor myVisitor);
}
