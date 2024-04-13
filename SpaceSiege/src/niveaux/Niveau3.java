package niveaux;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import composantdessin.PlanCartesien;
import composantjeu.Balle;
import composantjeu.BalleBasique;
import composantjeu.Canon;
import composantjeu.FlecheDeTir;
import composantjeu.Monstres;
import physique.Vecteur2D;

import java.awt.Color;

import obstacles.Rectangle;

import obstacles.Triangle;

import outils.CollisionRectangle;
import physique.MoteurPhysique;

public class Niveau3 extends Niveaux {

	/**
	 * La classe Niveaux étend JPanel et implémente Runnable pour fournir une zone d'animation interactive. Cette zone permet de simuler des animations basées sur la physique, telles que le mouvement d'un canon tirant des balles, et de gérer des interactions avec des obstacles.
	 * @author Benakmoum Walid
	 * @author Khanafer Ahmad
	 * @author Soudaki Zakaria
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Indique si une animation est actuellement en cours.
     */
	private boolean enCoursDAnimation=false;

	private double rotation=20;
	/**
     * L'intervalle de temps (en secondes) utilisé pour chaque itération du calcul physique.
     */
	private double deltaT=0.10;
	/**
     * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
     */
	private int tempsDuSleep = 10;
	/**
     * Un rectangle servant d'obstacle dans la zone d'animation.
     */

	private Rectangle[] tableauRec;
	
	/**
     * Indique si une balle a été tirée par le canon.
     */
	private boolean balleTiree = false;
	
	/**
     * Utilisé pour effectuer des opérations lors du premier appel de certaines méthodes ou conditions.
     */
	private double tempsTotalEcoule =0;
	
	/**
     * Position du mur du sol, utilisée pour détecter les collisions et gérer les rebonds ou arrêts des objets animés.
     */
    double posMurSol;
    /**
     * Position du mur droit, utilisée pour détecter les collisions latérales.
     */
    double posMurDroit;
    /**
     * Position du mur haut, utilisée pour détecter les collisions et gérer les interactions en haut de la zone d'animation.
     */
    double posMurHaut;
    /**
     * Position du mur gauche, permettant de gérer les collisions sur le côté gauche de la zone d'animation.
     */
    double posMurGauche;
    
    /**
     * Hauteur du composant d'animation, utilisée pour ajuster les interactions et le rendu en fonction de la taille de la zone d'animation.
     */
    double hauteurComposant;
    /**
     * Largeur du composant d'animation, influençant le placement des éléments et la détection des collisions.
     */
    double largeurComposant;
    
    /**
     * Index utilisé pour identifier les manipulations spécifiques des éléments de l'interface, telles que le redimensionnement ou la rotation d'obstacles.
     */
    private int index = -1;
    private int nombreDeVie=1;
    private Monstres monstre;

    
    private double pixelParMetres;
	private boolean premiereFois = true;


    private  int balleChoisie;

    private Triangle[] tableauTri;
    /**
     * Le canon utilisé pour tirer des balles.
     */
	private Canon canon;
    private boolean monstreMort=false;
    private PlanCartesien planCartesion= new PlanCartesien();

    
    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
    //Benakmoum Walid
	public Niveau3() {
		setBackground(new Color(192, 192, 192));
		setLayout(null);
		tableauRec = new Rectangle[3];
		tableauTri = new Triangle[3];
		ecouteurSouris();
		ecouteurClavier();
		
		
	}
	/**
     * Dessine les composants graphiques de la zone d'animation, y compris le canon et les obstacles.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
	// Benakmoum Walid 
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		
	    posMurSol = getHeight();
	    posMurDroit = getWidth();
	    posMurGauche = 0;
	    posMurHaut = 0;

	    hauteurComposant = getHeight();
	    largeurComposant = getWidth();
	   
	   

	}
	/**
     * Exécute l'animation en boucle tant que enCoursDAnimation est vrai. Gère le calcul physique et les collisions.
     */
	//Benakmoum Walid
	public void run() {
		
}

	/**
     * Démarre le thread d'animation si ce n'est pas déjà fait.
     */
	// Benakmoum Walid
	public void demarrer() {
		
		
			
		}
	//fin methode
	//WALID
	  public void prochaineImage() {
		  
	  }
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	//Benakmoum Walid
	public void calculerUneIterationPhysique(double deltaT) {
		
	}
	

	/**
     * Teste les collisions entre les éléments graphiques et ajuste leurs vitesses en conséquence.
     */
	//ZAKARIA SOUDAKI
	public void testerCollisionsEtAjusterVitesses() {	
		 
	}

	 /**
     * Calcule les forces agissant sur les objets de la zone d'animation, telles que la gravité.
     */
// Benakmoum Walid
	private void calculerLesForces() {

//		Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
//       
//        
//		canon.getBalle().setSommeDesForces(forceDeGravite);


	}
	/**
	 * Réinitialise l'application à son état initial, incluant la remise à zéro de tous les composants d'animation et des variables d'état.
	 * Cette méthode stoppe l'animation en cours si elle est active, réinitialise la rotation, le temps total écoulé, l'état de tir de la balle,
	 */
	  //Benakmoum Walid
	public void reinitialiserApplication() {
	
	  

	}
	/**
     * Méthode qui permet de tirer la balle.
     */
	  //Benakmoum Wid
	public  void TirerBalle() {
		
	}
	public void arreter() {
		
	}
	public void choisirBalle(int nb) {

	}
	public void setNombreDeVie(int nb) {
	    this.nombreDeVie = nb;
	    if (this.monstre != null) {
	        this.monstre.setNombreDeVie(nb);
	    }
	    repaint();
	}
	public void changerTypeGravite(String typeGravite) {
	
	}
	
	/**
     * Initialise l'écouteur de clavier pour interagir avec l'animation via le clavier.
     */
	  //Benakmoum Walid
	private void ecouteurClavier() {
		
	}
	private void ecouteurSouris() {
		
	}
	private void gestionSourisRecDragged(MouseEvent e) {
		
	}
	private void gestionSourisTriDragged(MouseEvent e) {
		
	}
	private void gestionSourisRecClick(MouseEvent e) {
		
	}
	private void gestionSourisTriClick(MouseEvent e) {
		
	}
	private void gestionSourisCanon(MouseEvent e) {
		
	}
}
				

		