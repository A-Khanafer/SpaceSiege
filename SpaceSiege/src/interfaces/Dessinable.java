package interfaces;
import java.awt.Graphics2D;
/**
 * Interface qui définit la méthode qu'un objet doit implémenter pour pouvoir
 * etre dessiner
 *@author Benakmoum Walid
 */
public interface Dessinable {
	/**
	 * Méthode permet de dessiner un objet
	 * @param g2d Contexte Graphique
	 */
	public void dessiner(Graphics2D g2d);
}
