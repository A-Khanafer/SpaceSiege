package interfaces;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import physique.Vecteur2D;

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
    
    /**
	 * Méthode permet de dessiner un objet
	 * @param g2d Contexte Graphique
	 */
    //Ahmad Khanafer
	public void dessiner(Graphics2D g2d);
	
	/**
	 * Retourne vrai si le point passé en paramètre fait partie de l'objet dessinable
	 * sur lequel cette methode sera appelée
	 * 
	 * 
	 * @param xPix Coordonnée en x du point (exprimé en pixels) 
	 * @param yPix Coordonnée en y du point (exprimé en pixels)
	 */
	//Ahmad Khanafer
	public boolean contient(double xPix, double yPix);
	
    /**
     * Méthode pour obtenir l'index de la poignée de redimensionnement située à une position donnée.
     *
     * @param x La coordonnée en X de la position.
     * @param y La coordonnée en Y de la position.
     * @return L'index de la poignée de redimensionnement, ou -1 s'il n'y a aucune poignée à cette position.
     */
    //Ahmad Khanafer
    public int getClickedResizeHandleIndex(double x, double y);
    
    /**
     * Méthode pour déterminer si le rectangle est sélectionné.
     *
     * @return true si le rectangle est sélectionné, sinon false.
     */
    //Ahmad Khanafer
    public boolean isClickedOnIt();
    
    /**
     * Méthode pour définir l'état de sélection du rectangle.
     *
     * @param clickedOnIt true pour sélectionner le rectangle, sinon false.
     */
    //Ahmad Khanafer
    public void setClickedOnIt(boolean clickedOnIt);
    
    public Area toAire();
    
    public Vecteur2D getPosition();
}

   

