package niveaux;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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

import obstacles.ObstacleHolder;
import obstacles.Rectangle;

import obstacles.Triangle;

import physique.MoteurPhysique;

/**
 * La classe Niveaux étend JPanel et implémente Runnable pour fournir une zone d'animation interactive. Cette zone permet de simuler des animations basées sur la physique, telles que le mouvement d'un canon tirant des balles, et de gérer des interactions avec des obstacles.
 * @author Benakmoum Walid
 * @author Khanafer Ahmad
 * @author Soudaki Zakaria
 */
public class Niveaux extends JPanel implements Runnable {

	
	private static final long serialVersionUID = 1L;
	/**
     * Indique si une animation est actuellement en cours.
     */
	protected boolean enCoursDAnimation=false;

	

	
	
	/**
     * Indique si une balle a été tirée par le canon.
     */
	protected boolean balleTiree = false;
	
	/**
     * Utilisé pour effectuer des opérations lors du premier appel de certaines méthodes ou conditions.
     */
	protected double tempsTotalEcoule =0;
	
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


    
    protected double pixelParMetres;

    /**
     * Le canon utilisé pour tirer des balles.
     */
    protected Canon canon ;
    /**
     * le monstre 
     */
    private Monstres monstre;
    
    /**
     * boolean savoir si le monstre est toucher ou non
     */
    protected boolean monstreMort=false;
    /**
     * compossantes force éléctrique
     */
    protected  Vecteur2D forceElec;
    
    /**
     * composantes somme des forces
     */
	protected  Vecteur2D forceTotal;
	/**
	 * mode scientifique activé ou non
	 */
	protected boolean modeScience=false;
	
	protected int forceMonstre;
	


    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
    //ZAKARIA SOUDAKI
	public Niveaux() {
		
		setOpaque(false);
		
		setBounds(0, 0, 1296, 672);
		setLayout(null);

		pixelParMetres = 8.64;
		canon = new Canon(0, 10,pixelParMetres);

        monstre = new Monstres(getWidth()- ((8*pixelParMetres)/2) - 100, getHeight()/2 - ((8*pixelParMetres)/2), pixelParMetres);
		
		
	}
	/**
     * Dessine les composants graphiques de la zone d'animation, y compris le canon et les obstacles.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
	//ZAKARIA SOUDAKI 
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
	//ZAKARIA SOUDAKI
	public void run() {
		
	}

	/**
     * Démarre le thread d'animation si ce n'est pas déjà fait.
     */
	//ZAKARIA SOUDAKI
	public void demarrer() {
		
		
			
		}
	//fin methode
	//ZAKARIA SOUDAKI
	  public void prochaineImage() {
		  
	  }
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	//ZAKARIA SOUDAKI
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
	//ZAKARIA SOUDAKI
	private void calculerLesForces() {




	}
	/**
	 * Réinitialise l'application à son état initial, incluant la remise à zéro de tous les composants d'animation et des variables d'état.
	 * Cette méthode stoppe l'animation en cours si elle est active, réinitialise la rotation, le temps total écoulé, l'état de tir de la balle,
	 */
	//ZAKARIA SOUDAKI
	public void reinitialiserApplication() {
	
	  

	}
	/**
     * Méthode qui permet de tirer la balle.
     */
		public  void TirerBalle() {
		
	}
   public void arreter() {
		
	}
	//ZAKARIA SOUDAKI
	public void choisirBalle(int nb) {

	}
		public void setNombreDeVie(int nb) {
	    
	}
	public void changerTypeGravite(String typeGravite) {
	
	}
	public boolean getEnCoursAnimation() {
	return enCoursDAnimation;
		
	}
	 public void stopperAnim(){
		  
	 }
	
	 public double getPixelParMetres() {
		 return pixelParMetres;
	 }
	 
	 public Balle getBalle() {
		 return canon.getBalle();
	 }
	
	 
	 
	
	  
	
	//ZAKARIA SOUDAKI
	public void setMasseBalle(int mas) {
	
	}
	//ZAKARIA SOUDAKI
	public Monstres getMonstre() {
		return this.monstre;
	}
	//WALID BENAKMOUM
	public void setModeScience(boolean sc) {
	
	}
	// AHMAD KHANAFER
	public void setObHolder(ObstacleHolder obHolder) {
		
		
	}
	public void setForceMonstre(int force) {
		
	}
	
	
	
	}
