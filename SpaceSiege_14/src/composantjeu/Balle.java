package composantjeu;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

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
    protected Vecteur2D accel;

    /**
     * Représentation géométrique de la balle utilisée pour le dessin à l'écran.
     */
    /**
     * Représente un cercle qui définit la forme géométrique de la balle.
     */
    protected Ellipse2D.Double cercle;

    /**
     * Liste des traînées laissées par la balle lors de son déplacement.
     */
    protected LinkedList<Ellipse2D.Double> trainees = new LinkedList<>();

    /**
     * Nombre maximal de traînées autorisées.
     */
    protected final int MAX_TRAÎNÉES = 3;

    /**
     * Indique si la balle est en cours d'exécution.
     */
    protected volatile boolean running = true;

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
	public Balle(int masseDonne, int chargeDonne, double diametreDonne, Vecteur2D position, Vecteur2D vitesse,double pixelParMetre) {
		masse = masseDonne;
		this.pixelsParMetre=pixelParMetre;
		charge = chargeDonne;
		diametre = diametreDonne;
		this.vitesse = vitesse;
		this.position = position;
		this.position = this.position.multiplie(1/pixelsParMetre);
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
	public void setSommeDesForces(Vecteur2D sommeForcesSurMonstre) {

		try {


			 accel = MoteurPhysique.calculAcceleration(sommeForcesSurMonstre, masse);

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
//	    System.out.println(vitesse.getX()+" VITESSE EN X");
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
	/**
	 * Retourne la position centrale de la balle.
	 * 
	 * @return La position centrale de la balle sous forme de vecteur.
	 */
	
	public Vecteur2D getPosCentral() {
		return new Vecteur2D(position.getX() + diametre / 2,position.getY() + diametre / 2);
	}

	/**
	 * Définit la position de la balle.
	 * 
	 * @param position La nouvelle position de la balle sous forme de vecteur.
	 */
	  //Benakmoum Walid
	public void setPosition(Vecteur2D position) {
	    this.position = position.multiplie(1/pixelsParMetre);
	}
	/**
	 * Permet d'avoir le cercle de la balle
	 * @return le cercle
	 */
	//ZAKARIA SOUDAKI
	public Ellipse2D.Double getCercle() {
		return cercle;
	}
	
	 /**
	  * Méthode pour dessiner
	  * @param g2dPrive Contexte graphique
	  */
	 //Benakmoum Walid
	public void dessiner(Graphics2D g2dPrive) {
		
	}


	//ZAKARIA SOUDAKI
	public double getRayon() {
		return diametre/2;
	}
	/**
	 * Retourne la position actuelle de la balle en mètres.
	 * 
	 * @return La position de la balle sous forme de vecteur en mètres.
	 */
	//Benakmoum Walid
    public Vecteur2D getPositionEnMetre() {
	  return(position.multiplie(1/pixelsParMetre));
    }
    /**
     * Méthode pour déclencher l'explosion de la balle.
     */
	//Benakmoum Walid
    public void exploser() {

}
    /**
     * Définit la masse de la balle.
     * 
     * @param mas La nouvelle masse de la balle.
     */
	//Benakmoum Walid
    public void setMasse(int mas) {
        this.masse = mas;
    }

    /**
     * Méthode permettant de déterminer le type de la balle actuelle.
     * 
     * @return Le type de balle actuelle.
     */
	//Benakmoum Walid
    public int quelleTypeBalle() {
        return 0; // À remplir avec la logique appropriée
    }

    /**
     * Méthode pour augmenter la vitesse de la balle.
     */
	//Benakmoum Walid
    public void boostVitesse() {
        this.vitesse.additionne(new Vecteur2D(1000, 1000)); // Valeurs arbitraires pour l'exemple
    }

    /**
     * Retourne l'accélération actuelle de la balle.
     * 
     * @return L'accélération de la balle.
     */
	//Benakmoum Walid
    public Vecteur2D getAcceleration() {
        return accel;
    }
	
	
	
}

