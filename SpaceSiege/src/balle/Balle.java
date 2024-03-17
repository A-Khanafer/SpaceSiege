package balle;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

/**
 * Classe de base pour représenter une balle dans une simulation physique. Cette classe gère les propriétés physiques de base comme la masse, la position, la vitesse, et l'accélération, ainsi que la représentation graphique de la balle.
 * @author Benakmoum Walid
 */
public class Balle {
    /**
     * Masse de la balle, affectant son comportement dans les calculs physiques.
     */
    protected int masse;

    /**
     * Diamètre de la balle, utilisé dans la représentation graphique et les calculs de collision.
     */
    protected double diametre;

    /**
     * Ratio de conversion des unités physiques en pixels pour l'affichage à l'écran. Non initialisé ici.
     */
    protected double pixelsParMetre;

    /**
     * Zone graphique représentant la balle, utile pour le dessin et les détections de collision.
     */
    protected Area aire;

    /**
     * Charge électrique de la balle, envisagée pour des extensions futures impliquant des interactions électromagnétiques.
     */
    protected int charge;

    /**
     * Indique si la balle a été tirée, permettant de contrôler son état dans la simulation.
     */
    protected boolean balleTiree = false;

    /**
     * Position actuelle de la balle dans l'espace de simulation, exprimée par un vecteur.
     */
    protected Vecteur2D position;

    /**
     * Vitesse actuelle de la balle, représentée par un vecteur.
     */
    protected Vecteur2D vitesse;

    /**
     * Accélération de la balle, déterminée par les forces appliquées sur elle. Initialement définie à zéro.
     */
    protected Vecteur2D accel = new Vecteur2D(0, 0);

    /**
     * Représentation géométrique de la balle utilisée pour le dessin à l'écran.
     */
    protected Ellipse2D.Double cercle;
    /**
     * Constructeur qui initialise une nouvelle instance de balle avec des valeurs spécifiques.
     * 
     * @param masseDonne La masse de la balle.
     * @param chargeDonne La charge électrique de la balle.
     * @param diametreDonne Le diamètre de la balle.
     * @param position La position initiale de la balle.
     * @param vitesse La vitesse initiale de la balle.
     */
    //Benakmoum Walid
	public Balle(int masseDonne, int chargeDonne, int diametreDonne, Vecteur2D position, Vecteur2D vitesse) {
		masse = masseDonne;
		charge = chargeDonne;
		diametre = diametreDonne;
		this.vitesse = vitesse;
		this.position = position;
		initialiserCercle();
	}
	/**
     * Initialise la représentation géométrique de la balle pour le dessin.
     */
	  //Benakmoum Walid
	private void initialiserCercle() {
		cercle = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);
		aire = new Area(cercle);
	}
	/**
     * Met à jour l'accélération de la balle en fonction de la somme des forces appliquées sur elle.
     * 
     * @param sommeForcesSurLaBalle La somme des forces appliquées sur la balle.
     */
	  //Benakmoum Walid
	public void setSommeDesForces(Vecteur2D sommeForcesSurLaBalle) {

		try {


//			 accel = MoteurPhysique.calculAcceleration(sommeForcesSurLaBalle, masse);
//			System.out.println(sommeForcesSurLaBalle.getY()+"N");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * Fait avancer la balle d'un pas, en mettant à jour sa position et sa vitesse en fonction de son accélération actuelle.
     * 
     * @param deltaT Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
	  //Benakmoum Walid
	public void avancerUnPas(double deltaT) {
	
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);

		initialiserCercle();
		
	}
	/**
	 * Retourne la masse de la balle.
	 * 
	 * @return La masse de la balle.
	 */
	  //Benakmoum Walid
	public int getMasse() {
	    return this.masse;
	}

	/**
	 * Génère une représentation textuelle de la balle, affichant sa position avec un nombre spécifié de décimales.
	 * 
	 * @param nbDecimales Le nombre de décimales à afficher pour les coordonnées de la position.
	 * @return Une chaîne de caractères décrivant la position de la balle.
	 */
	  //Benakmoum Walid
	public String toString(int nbDecimales) {
	    String s = "Balle : position=[ " + String.format("%."+nbDecimales+"f", position.getX()) + ", " + String.format("%."+nbDecimales+"f", position.getY()) + "]" ;
	    return s;
	}

	/**
	 * Retourne la position actuelle de la balle.
	 * 
	 * @return La position de la balle sous forme de vecteur.
	 */
	  //Benakmoum Walid
	public Vecteur2D getPosition() {
	    return this.position;
	}

	/**
	 * Retourne le diamètre de la balle.
	 * 
	 * @return Le diamètre de la balle.
	 */
	  //Benakmoum Walid
	public double getDiametre() {
	    return this.diametre;
	}

	/**
	 * Définit la vitesse de la balle.
	 * 
	 * @param vit La nouvelle vitesse de la balle sous forme de vecteur.
	 */
	  //Benakmoum Walid
	public void setVitesse(Vecteur2D vit) {
	    this.vitesse = vit;
	}

	/**
	 * Retourne la vitesse actuelle de la balle.
	 * 
	 * @return La vitesse de la balle.
	 */
	  //Benakmoum Walid
	public Vecteur2D getVitesse() {
	    return this.vitesse;
	}

	/**
	 * Retourne la coordonnée X du centre de la balle.
	 * 
	 * @return La coordonnée X du centre de la balle.
	 */
	  //Benakmoum Walid
	public double getPosXCentre() {
	    return this.position.getX() + diametre / 2;
	}

	/**
	 * Retourne la coordonnée Y du centre de la balle.
	 * 
	 * @return La coordonnée Y du centre de la balle.
	 */
	  //Benakmoum Walid
	public double getPosYCentre() {
	    return this.position.getY() + diametre / 2;
	}

	/**
	 * Définit la position de la balle.
	 * 
	 * @param position La nouvelle position de la balle sous forme de vecteur.
	 */
	  //Benakmoum Walid
	public void setPosition(Vecteur2D position) {
	    this.position = position;
	}
	
	
	
	
	
	
}
