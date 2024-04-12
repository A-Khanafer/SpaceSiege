package interfaces;

import java.awt.Point;

/**
 * L'interface Obstacles définit les méthodes permettant de manipuler un obstacle dans un système.
 * @author Ahmad Khanafer
 */
public interface Obstacles {
    
    /**
     * Redimensionne l'obstacle en fonction de l'index du point de redimensionnement sélectionné et des nouvelles coordonnées de la souris.
     *
     * @param index L'index du point de redimensionnement sélectionné.
     * @param eX    La nouvelle coordonnée X de la souris.
     * @param eY    La nouvelle coordonnée Y de la souris.
     */
	//Ahmad Khanafer
    public void redimension(int index, int eX, int eY);
    
    /**
     * Effectue une rotation de l'obstacle en fonction des coordonnées de la souris.
     *
     * @param eX La coordonnée X de la souris.
     * @param eY La coordonnée Y de la souris.
     */
    //Ahmad Khanafer
    public void rotate(int eX, int eY);
    
    /**
     * Déplace l'obstacle vers de nouvelles coordonnées en fonction des coordonnées de la souris.
     *
     * @param eX La nouvelle coordonnée X de l'obstacle.
     * @param eY La nouvelle coordonnée Y de l'obstacle.
     */
    //Ahmad Khanafer
    public void move(int eX, int eY);
}

