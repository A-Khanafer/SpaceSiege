package composantjeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import application.FenetreDeJeu;
import interfaces.Obstacles;
import outils.OutilsImage;
import physique.MoteurPhysique;
import physique.Vecteur2D;
/**
 * La classe Monstres représente un élément de jeu interactif qui peut être dessiné sur un JPanel.
 * Elle est utilisée pour créer des monstres qui peuvent être éliminés dans le jeu.
 * @author ZAKARIA SOUDAKI, ahamd khanafer
 */
public class Monstres extends JPanel implements  Serializable{
	
	private static final long serialVersionUID = -4198412908689199658L;
	
	
	 	/**
	 	 * pixels par mètre
	 	 */
	 	private double pixelsParMetres;
	    /** 
	     * Le rectangle représentant le monstre
	     *  **/

	    private Rectangle2D rec ;
	    /**
	     * air du monstre
	     */
	    private Path2D.Double aireMonstre;

	    
	    /** L'image du monstre 
	     * **/
	    private Image imgDecor = null;
	    
	
	    
	    /** La largeur du rectangle représentant le monstre 
	     * **/
	    private double longueurRectangle; // Exemple : largeur du rectangle

	    /** La hauteur du rectangle représentant le monstre 
	     * **/
	    private double hauteurRectangle; // Exemple : hauteur du rectangle
	    
	  
	    /**
	     * en cour d'animation boolean
	     */
	    private boolean enCourDAnimation = false;


	    /**
	     * nombre de vie du monstre
	     */
	    private static int nombreDeVie=1;
	    
	    /**
	     * nom image du monstre
	     */
	    private String nomImg1 = "monstre1.png";
	    
	    /**
	     * nom image du monstre
	     */
	    private String nomImg2 = "monstre2.png";
	    
	    private String nomImg3 = "monstre3.png";
	    
	    /**
	     * index pour parcourir tableau d'objet
	     */
	    private int index = 1;
	    
	    /**
	     * image du monstre actuelle
	     */
	    private String imgActuel;
	    
	    
	    /**
	     * Masse de la balle, affectant son comportement dans les calculs physiques.
	     */
	    private int masse = 5;


	    /**
	     * Ratio de conversion des unités physiques en pixels pour l'affichage à l'écran. Non initialisé ici.
	     */
	    private double pixelsParMetre;

	    /**
	     * Zone graphique représentant la balle, utile pour le dessin et les détections de collision.
	     */
	    private Area air;


	    /**
	     * Position actuelle de la balle dans l'espace de simulation, exprimée par un vecteur.
	     */
	    private Vecteur2D position = new Vecteur2D();

	    /**
	     * Vitesse actuelle de la balle, représentée par un vecteur.
	     */
	    private Vecteur2D vitesse= new Vecteur2D(0,0);

	    /**
	     * Accélération de la balle, déterminée par les forces appliquées sur elle. Initialement définie à zéro.
	     */
	    private Vecteur2D accel = new Vecteur2D(0, 0);
	    
	    private Vecteur2D sommeForcesSurLaBalle = new Vecteur2D(0,0);

        /**
         * boolean effectuer une tache une seule fois
         */
		private boolean premiereFois = true;
		
		private Vecteur2D posCentre ;
		
		private boolean premierFois = true;

	    /**
		 * Constructeur de la classe Monstres.
		 * @param posX La position en X du monstre.
		 * @param posY La position en Y du monstre.
		 * @param nomImage Le nom de l'image à utiliser pour représenter le monstre.
		 **/
	    //ZAKARIA SOUDAKI
	    public Monstres(double posX, double posY, double pixelsParMetres) {
	    	setOpaque(false);
	    	this.pixelsParMetres = pixelsParMetres;
	        this.position = new Vecteur2D(posX,posY);
	        
	        longueurRectangle = 8*this.pixelsParMetres;
	        hauteurRectangle = 8*this.pixelsParMetres;
	        
	        this.posCentre = new Vecteur2D(posX +longueurRectangle, posY+hauteurRectangle);
	        Random rand = new Random();
	        
	        int indexImg = 1;
	        
			if(premierFois) {
	        	indexImg = rand.nextInt(3) + 1;
	        	
	        	 if (indexImg == 1) {
	 		        imgDecor = OutilsImage.lireImage(nomImg1); 
	 	        }
	 	        if (indexImg == 2){
	 		        imgDecor = OutilsImage.lireImage(nomImg2); 
	 	        }
	 	        if (indexImg == 3){
	 	        	imgDecor = OutilsImage.lireImage(nomImg3); 
	 	        }
	        	
	        	premierFois = false;
	        }
	        
	        
	       
	
	       creerLaGeometrie();

	    }
	   
	    /**
		 * Initialise la géométrie du monstre.
		 **/
	    //ZAKARIA SOUDAKI
	    private void creerLaGeometrie() {
	    	
	    	
	        rec = new Rectangle2D.Double(position.getX(),position.getY(),longueurRectangle,hauteurRectangle);
	        aireMonstre = new Path2D.Double(rec);
	        
	    }

	    /**
		 * Dessine le monstre sur le composant graphique spécifié.
		 * @param g2d Le contexte graphique dans lequel dessiner le monstre.
		 **/
	    //ZAKARIA SOUDAKI
	    public void dessiner(Graphics2D g2d) {
	    	
	        g2d.drawImage( imgDecor, (int) position.getX(), (int) position.getY(),  (int)longueurRectangle, (int) hauteurRectangle,  null);


	    }
	    
	    /**
		 * Récupère la zone d'air du monstre, utilisée pour les collisions.
		 * @return La zone d'air du monstre.
		 **/
	    //ZAKARIA SOUDAKI
	    public Area toAire() {
	    	Area aire = new Area(aireMonstre);
			return aire;
	    }
	    
	    /**
		 * Récupère le rectangle représentant le monstre.
		 * @return Le rectangle représentant le monstre.
		 **/
	    //ZAKARIA SOUDAKI
	    public Rectangle2D getRec() {
	    	return this.rec;
	    }
	    
	    //ZAKARIA SOUDAKI

	    public void perdUneVie() {
	    	nombreDeVie--;
	    }
	    
	    //ZAKARIA SOUDAKI

	    public int getNombreDeVie(){
	    	return nombreDeVie;
	    }
	    
	    //ZAKARIA SOUDAKI
	    public void setNombreDeVie(int nb) {
	    	nombreDeVie=nb;
	    	
	    }
	   
		
	
	  
	/**
     * Met à jour l'accélération de la balle en fonction de la somme des forces appliquées sur elle.
     * 
     * @param sommeForcesSurLaBalle La somme des forces appliquées sur la balle.
     */
    //ZAKARIA SOUDAKI
	public void setSommeDesForces(Vecteur2D sommeForcesSurLaBalle) {

		
		this.sommeForcesSurLaBalle = sommeForcesSurLaBalle;
		
		try {

 
		
			 accel = MoteurPhysique.calculAcceleration(sommeForcesSurLaBalle, masse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * Fait avancer la balle d'un pas, en mettant à jour sa position et sa vitesse en fonction de son accélération actuelle.
     * 
     * @param deltaT Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    //ZAKARIA SOUDAKI

	public void avancerUnPas(double deltaT) {
	
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		FenetreDeJeu.setDonnees(vitesse,accel, sommeForcesSurLaBalle);
	    creerLaGeometrie();
		repaint();
	}
	
	/**
	 * Retourne la masse du monstre.
	 * 
	 * @return La masse du monstre.
	 */
    //ZAKARIA SOUDAKI

	public int getMasse() {
	    return this.masse;
	}

	/**
	 * Génère une représentation textuelle de la balle, affichant sa position avec un nombre spécifié de décimales.
	 * 
	 * @param nbDecimales Le nombre de décimales à afficher pour les coordonnées de la position.
	 * @return Une chaîne de caractères décrivant la position du monstre.
	 */
    //ZAKARIA SOUDAKI

	public String toString(int nbDecimales) {
	    String s = "Balle : position=[ " + String.format("%."+nbDecimales+"f", position.getX()) + ", " + String.format("%."+nbDecimales+"f", position.getY()) + "]" ;
	    return s;
	}

	/**
	 * Retourne la position actuelle du monstre.
	 * 
	 * @return La position du monstre sous forme de vecteur.
	 */
    //ZAKARIA SOUDAKI

	public Vecteur2D getPosition() {
	    return this.position;
	}
    //ZAKARIA SOUDAKI

	public void setPosition(Vecteur2D vec) {
		this.position = vec;
	}
	
	/**
	 * Définit la vitesse du monstre.
	 * 
	 * @param vit La nouvelle vitesse du monstre sous forme de vecteur.
	 */
    //ZAKARIA SOUDAKI

	public void setVitesse(Vecteur2D vit) {
	    this.vitesse = vit;
	}

	/**
	 * Retourne la vitesse actuelle du monstre.
	 * 
	 * @return La vitesse du monstre.
	 */
    //ZAKARIA SOUDAKI
	public Vecteur2D getVitesse() {
	    return this.vitesse;
	}
	
	/**
	 * Retourne l'acceleration actuelle du monstre.
	 * 
	 * @return L'acceleration du monstre.
	 */
    //ZAKARIA SOUDAKI
	public Vecteur2D getAcceleration() {
		return this.accel;
	}
	/**
	 * obtenir la longueur du rectangle
	 * @return la longueur
	 */
    //ZAKARIA SOUDAKI
	public double getLongueurRectangle() {
		return longueurRectangle;
	}
	/**
	 * définir longueur rectangle
	 * @param longueurRectangle la longueur
	 */
    //ZAKARIA SOUDAKI
	public void setLongueurRectangle(double longueurRectangle) {
		this.longueurRectangle = longueurRectangle;
	}
	/**
	 * obtenir la hauteur du rectangle
	 * @return la hauteur
	 */
    //ZAKARIA SOUDAKI
	public double getHauteurRectangle() {
		return hauteurRectangle;
	}
	/**
	 * définir hauteur du rectangle
	 * @param hauteurRectangle la hauteur
	 */
    //ZAKARIA SOUDAKI
	public void setHauteurRectangle(double hauteurRectangle) {
		this.hauteurRectangle = hauteurRectangle;
	}

    //ahmad khanafer
	public void move(int eX, int eY) {
        this.position.setX(eX - longueurRectangle / 2); 
        this.position.setY(eY - hauteurRectangle / 2); 
        creerLaGeometrie();
	}
	/**
	 * verifier si un point est contenu dans le monstre
	 * @param xPix donnée en x
	 * @param yPix donnée en y
	 * @return
	 */
    //ZAKARIA SOUDAKI
	public boolean contient(double xPix, double yPix) {
		if(toAire().contains(xPix, yPix)) {

			return true;
		}
		return false;
	}
	
    //Ahmad Khanafer
    public String toString() {
    	
    	String mons;
    	
    	mons= "mons\n" + 
    			Integer.toString((int) position.getX()) + "\n" +
    			Integer.toString((int) position.getY()) + "\n" +
    			Integer.toString((int) longueurRectangle) + "\n" +
    			Integer.toString((int) hauteurRectangle) + "\n" +
    			Double.toString((int) 0.0) + "\n";
		return mons;
    	
    }
}
