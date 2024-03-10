package physique;

import physique.Vecteur2D;



public class MoteurPhysique {
	/**
	 * Constante de l'accélération gravitationnelle
	 */
	private static final double ACCEL_G = 9.80665;
	/**
	 * Constante de tolerance utilisee dans les comparaisons reelles avec zero
	 */
	private static final double EPSILON = 1e-10; 
	
	/**
	 * Constante de restitution pour le calcul des collisions
	 */
	private static final double COEFFICIENT_DE_RESTITUTION = 0.8;
	
	
	
	public static Vecteur2D calculAcceleration(Vecteur2D sommeDesForces, double masse) throws Exception { 
		if(masse < EPSILON) 
			throw new Exception("Erreur MoteurPhysique: La masse étant nulle ou presque l'accéleration ne peut pas etre calculée.");
		else
			return new Vecteur2D( sommeDesForces.getX()/masse , sommeDesForces.getY()/masse );	
	}

	
	
	public static Vecteur2D calculVitesse(double deltaT, Vecteur2D vitesse, Vecteur2D accel) {
		Vecteur2D resultVit = vitesse.additionne(Vecteur2D.multiplie(accel, deltaT) );
		return new Vecteur2D(resultVit.getX(), resultVit.getY());
		
	}

	public static Vecteur2D calculPosition(double deltaT, Vecteur2D position, Vecteur2D vitesse) {
		Vecteur2D deltaPosition = Vecteur2D.multiplie(vitesse, deltaT);
		Vecteur2D resultPos = position.additionne(deltaPosition);
		return new Vecteur2D(resultPos.getX(), resultPos.getY());
		
	}
	

	public static Vecteur2D calculForceGrav(double masse, double angle) {
		return new Vecteur2D(Math.cos(angle)*ACCEL_G*masse, Math.sin(angle)*ACCEL_G*masse);
	}

	
	
	public static double sommeDesForces (double Fa, double Fb, double Fc) {
		double Ft = Fa + Fb + Fc;
		return Ft;
	}

	
	
	public static double calculVitesseApresCollision (double vitesseFinalVoitureA ,double masseA, double masseB) {
		double J = ((1+COEFFICIENT_DE_RESTITUTION)*vitesseFinalVoitureA)/((1/masseA)+(1/masseB));
		return J/masseB;
	}
	
	


}
