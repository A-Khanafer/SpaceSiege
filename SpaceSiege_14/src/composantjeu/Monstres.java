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

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import interfaces.Obstacles;
import outils.OutilsImage;
import physique.MoteurPhysique;
import physique.Vecteur2D;

public class Monstres extends JPanel implements Runnable, Serializable{
	
	private static final long serialVersionUID = -4198412908689199658L;
	
	
	/**
	 * La classe Monstres représente un élément de jeu interactif qui peut être dessiné sur un JPanel.
	 * Elle est utilisée pour créer des monstres qui peuvent être éliminés dans le jeu.
	 * @author zakaria soudaki
	 */
	
	 
	 
	 	
	 	private double pixelsParMetres;
	    /** Le rectangle représentant le monstre **/

	    private Rectangle2D rec ;
	    
	    private Path2D.Double aireMonstre;

	    
	    /** L'image du monstre **/
	    private Image imgDecor = null;
	    
	    /** La position en X du monstre **/
	
	    
	    /** La largeur du rectangle représentant le monstre **/

	   
	    private double longueurRectangle; // Exemple : largeur du rectangle

	    
	    private double hauteurRectangle; // Exemple : hauteur du rectangle
	    
	  
	    
	    private boolean enCourDAnimation = false;


	    private static int nombreDeVie=1;
	    
	    private String nomImg = "images.jpg";
	    
	    private int index = 1;
	    
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


		private boolean premiereFois = true;

	    /**
		 * Constructeur de la classe Monstres.
		 * @param posX La position en X du monstre.
		 * @param posY La position en Y du monstre.
		 * @param nomImage Le nom de l'image à utiliser pour représenter le monstre.
		 **/
	    //Zakaria Soudaki
	    public Monstres(int posX, int posY, double pixelsParMetres) {
	    	setOpaque(false);
	    	this.pixelsParMetres = pixelsParMetres;
	        this.position = new Vecteur2D(posX,posY);
	        
	        longueurRectangle = 5*this.pixelsParMetres;
	        hauteurRectangle = 5*this.pixelsParMetres;
	        imgDecor = OutilsImage.lireImage(nomImg); 
	       
	       creerLaGeometrie();
//	       demarrer();
	    }
	    public Monstres(double posX, double posY,double largeur, double longueur, double angleRotation) {
	    	setOpaque(false);
	    	
	    	this.position = new Vecteur2D(posX,posY);
	    	longueurRectangle = largeur;
	    	hauteurRectangle = longueur;
	    	
	       
	       creerLaGeometrie();

	    }

	    /**
		 * Initialise la géométrie du monstre.
		 **/
	    //Zakaria Soudaki
	    private void creerLaGeometrie() {
	    	
	    	if(premiereFois) {
	    		imgDecor = OutilsImage.lireImage(nomImg); 
	    		premiereFois = false;
	    	}
	    	
	        rec = new Rectangle2D.Double(position.getX(),position.getY(),longueurRectangle,hauteurRectangle);
	        aireMonstre = new Path2D.Double(rec);
	        
	    }

	    /**
		 * Dessine le monstre sur le composant graphique spécifié.
		 * @param g2d Le contexte graphique dans lequel dessiner le monstre.
		 **/
	    //zakaria soudaki
	    public void dessiner(Graphics2D g2d) {
	    	
	        g2d.drawImage( imgDecor, (int) position.getX(), (int) position.getY(),  (int)longueurRectangle, (int) hauteurRectangle,  null);


	    }
	    
	    /**
		 * Récupère la zone d'air du monstre, utilisée pour les collisions.
		 * @return La zone d'air du monstre.
		 **/
	    //zakaria soudaki
	    public Area toAire() {
	    	Area aire = new Area(aireMonstre);
			return aire;
	    }
	    
	    /**
		 * Récupère le rectangle représentant le monstre.
		 * @return Le rectangle représentant le monstre.
		 **/
	    //zakaria soudaki
	    public Rectangle2D getRec() {
	    	return this.rec;
	    }
	    
	    
	    public void perdUneVie() {
	    	nombreDeVie--;
	    }
	    
	    
	    public int getNombreDeVie(){
	    	return nombreDeVie;
	    }
	    
	    
	    public void setNombreDeVie(int nb) {
	    	nombreDeVie=nb;
	    	
	    }

	    public void demarrer() {
			if (!enCourDAnimation) {
				Thread proc = new Thread(this);
				proc.start();
				enCourDAnimation = true;
			}
		}//fin
	    
		@Override
		public void run() {
		while(enCourDAnimation) {
			
			
			imgActuel =  nomImg + index;
			index++;
			
			
			
			repaint();
		}
				
	}
		
	
	  
	/**
     * Met à jour l'accélération de la balle en fonction de la somme des forces appliquées sur elle.
     * 
     * @param sommeForcesSurLaBalle La somme des forces appliquées sur la balle.
     */
	public void setSommeDesForces(Vecteur2D sommeForcesSurLaBalle) {

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
	public void avancerUnPas(double deltaT) {
	
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		System.out.println("________"+position.getX()+"_________"+position.getY());
	    creerLaGeometrie();
		repaint();
	}
	
	/**
	 * Retourne la masse de la balle.
	 * 
	 * @return La masse de la balle.
	 */
	public int getMasse() {
	    return this.masse;
	}

	/**
	 * Génère une représentation textuelle de la balle, affichant sa position avec un nombre spécifié de décimales.
	 * 
	 * @param nbDecimales Le nombre de décimales à afficher pour les coordonnées de la position.
	 * @return Une chaîne de caractères décrivant la position de la balle.
	 */
	public String toString(int nbDecimales) {
	    String s = "Balle : position=[ " + String.format("%."+nbDecimales+"f", position.getX()) + ", " + String.format("%."+nbDecimales+"f", position.getY()) + "]" ;
	    return s;
	}

	/**
	 * Retourne la position actuelle de la balle.
	 * 
	 * @return La position de la balle sous forme de vecteur.
	 */
	public Vecteur2D getPosition() {
	    return this.position;
	}

	public void setPosition(Vecteur2D vec) {
		this.position = vec;
	}
	
	/**
	 * Définit la vitesse de la balle.
	 * 
	 * @param vit La nouvelle vitesse de la balle sous forme de vecteur.
	 */
	public void setVitesse(Vecteur2D vit) {
	    this.vitesse = vit;
	}

	/**
	 * Retourne la vitesse actuelle de la balle.
	 * 
	 * @return La vitesse de la balle.
	 */
	public Vecteur2D getVitesse() {
	    return this.vitesse;
	}

	public double getLongueurRectangle() {
		return longueurRectangle;
	}

	public void setLongueurRectangle(double longueurRectangle) {
		this.longueurRectangle = longueurRectangle;
	}

	public double getHauteurRectangle() {
		return hauteurRectangle;
	}

	public void setHauteurRectangle(double hauteurRectangle) {
		this.hauteurRectangle = hauteurRectangle;
	}

	
	public void move(int eX, int eY) {
        this.position.setX(eX - longueurRectangle / 2); 
        this.position.setY(eY - hauteurRectangle / 2); 
        creerLaGeometrie();
	}
	
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
