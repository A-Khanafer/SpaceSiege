package obstacles;

import physique.Vecteur2D;

public class Cercle {
	
	private double diametre;
	 /**
     * Position actuelle de la balle dans l'espace de simulation, exprimée par un vecteur.
     */
    protected Vecteur2D position;
	
	
	
	
	public double getRayon() {
		return diametre/2;
	}

	/**
	 * Retourne la coordonnée X du centre de la balle.
	 * 
	 * @return La coordonnée X du centre de la balle.
	 */
	  //Zakaria SOudaki
	public double getPosXCentre() {
	    return this.position.getX() + diametre / 2;
	}

	/**
	 * Retourne la coordonnée Y du centre de la balle.
	 * 
	 * @return La coordonnée Y du centre de la balle.
	 */
	  //Zakaria Soudaki
	public double getPosYCentre() {
	    return this.position.getY() + diametre / 2;
	}
}
