package physique;

import physique.Vecteur2D;

/**
 * Fournit des méthodes statiques pour calculer l'accélération, la vitesse, la position,
 * et les forces en se basant sur les principes physiques élémentaires.
 * Cette classe permet de simuler des interactions physiques simples dans un environnement virtuel,
 * telles que le mouvement des objets sous l'effet de la gravité ou suite à des collisions.
 * @author walid Benakmoum Walid
 */

public class MoteurPhysique {
	/**
	 * Constante de l'accélération gravitationnelle
	 */
	private static double accelG = 9.80665;
	/**
	 * Constante de tolerance utilisee dans les comparaisons reelles avec zero
	 */
	private static final double EPSILON = 1e-10; 
	
	/**
	 * Constante de restitution pour le calcul des collisions
	 */
	private static final double COEFFICIENT_DE_RESTITUTION = 0.8;
	
	
	/**
     * Calcule l'accélération d'un objet à partir de la somme des forces appliquées et de sa masse.
     * @param sommeDesForces Vecteur représentant la somme des forces appliquées à l'objet.
     * @param masse Masse de l'objet concerné.
     * @return L'accélération de l'objet sous forme de vecteur.
     * @throws Exception Si la masse est nulle ou presque, une exception est lancée.
     */
	//Benakmoum Walid

	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception { 
		if(masse < EPSILON) 
			throw new Exception("Erreur MoteurPhysique: La masse étant nulle ou presque l'accéleration ne peut pas etre calculée.");
		else
			return new Vecteur2D( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse );	
	}

	/**
     * Calcule la nouvelle vitesse d'un objet en fonction du temps, de sa vitesse initiale et de son accélération.
     * @param deltaT Temps écoulé.
     * @param vitesse Vitesse initiale de l'objet.
     * @param accel Accélération de l'objet.
     * @return La nouvelle vitesse de l'objet.
     */
	//Benakmoum Walid

	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D resultVit = vitesse.additionne(Vecteur2D.multiplie(accel, deltaT) );
		return new Vecteur2D(resultVit.getX(), resultVit.getY());
		
	}
	/**
     * Calcule la nouvelle position d'un objet en fonction du temps, de sa position initiale et de sa vitesse.
     * @param deltaT Temps écoulé.
     * @param position Position initiale de l'objet.
     * @param vitesse Vitesse de l'objet.
     * @return La nouvelle position de l'objet.
     */
	//Benakmoum Walid

	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition);
		return new Vecteur2D(resultPos.getX(), resultPos.getY());
		
	}
	
	/**
     * Calcule la force de gravité agissant sur un objet en fonction de sa masse et de l'angle d'application de la force.
     * @param masse Masse de l'objet.
     * @param angle Angle d'application de la force de gravité.
     * @return La force de gravité sous forme de vecteur.
     */
	//Benakmoum Walid

	public static Vecteur2D calculForceGrav(double masse, double angle) {
		return new Vecteur2D(Math.cos(angle)*accelG*masse, Math.sin(angle)*accelG*masse);
	}

	
	/**
     * Calcule la somme des forces agissant sur un objet.
     * @param Fa Force a.
     * @param Fb Force b.
     * @param Fc Force c.
     * @return La somme totale des forces.
     */
	//Benakmoum Walid
	public static double sommeDesForces (double Fa, double Fb, double Fc) {
		double Ft = Fa + Fb + Fc;
		return Ft;
	}
	/**
	 * Methode pour changer la gravité
	 * @param nouvelleGravite la nouvelle gravité
	 */
	public static void changerGravite(double nouvelleGravite) {
	    accelG = nouvelleGravite;
	}


}
