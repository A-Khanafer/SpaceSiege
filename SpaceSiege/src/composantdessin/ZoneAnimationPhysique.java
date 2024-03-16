package composantdessin;

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


import javax.swing.JPanel;

import balle.Balle;
import balle.BalleBasique;
import balle.Canon;
import balle.FlecheDeTir;
import physique.Vecteur2D;

import java.awt.Color;

import obstacles.Rectangle;
import outils.CollisionRectangle;
import physique.MoteurPhysique;

public class ZoneAnimationPhysique extends JPanel implements Runnable {

	/**
	 * La classe ZoneAnimationPhysique étend JPanel et implémente Runnable pour fournir une zone d'animation interactive. Cette zone permet de simuler des animations basées sur la physique, telles que le mouvement d'un canon tirant des balles, et de gérer des interactions avec des obstacles.
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
	private double deltaT=0.05;
	/**
     * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
     */
	private int tempsDuSleep = 5;
	/**
     * Un rectangle servant d'obstacle dans la zone d'animation.
     */
	private Rectangle rec = new Rectangle(50,50);
	/**
     * Indique si une balle a été tirée par le canon.
     */
	private boolean balleTiree = false;
	/**
     * Le canon utilisé pour tirer des balles.
     */
	private Canon canon= new Canon (0,80);
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
	
	//
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
		ecouteurClavier();
		
	}
	/**
     * Dessine les composants graphiques de la zone d'animation, y compris le canon et les obstacles.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		canon.dessiner(g2d);

		rec.dessiner(g2d);

	    posMurSol = getHeight();
	    posMurDroit = getWidth();
	    posMurGauche = 0;
	    posMurHaut = 0;

	    hauteurComposant = getHeight();
	    largeurComposant = getWidth();
	    g2d.setColor(Color.red);
	    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

	}
	/**
     * Exécute l'animation en boucle tant que enCoursDAnimation est vrai. Gère le calcul physique et les collisions.
     */
	
	public void run() {
		while (enCoursDAnimation) {

//			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
//			System.out.println("Temps ecoule "+tempsTotalEcoule);


			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
		/*	
			if( 	CollisionRectangle.detectionCollisionBalleLigne(canon.getBalle(),rec )== true ) {
				enCoursDAnimation=false;
			}
		*/
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...!");
	}

	/**
     * Démarre le thread d'animation si ce n'est pas déjà fait.
     */
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		calculerLesForces();
		canon.avancerUnPas(deltaT);
	}
	

	/**
     * Teste les collisions entre les éléments graphiques et ajuste leurs vitesses en conséquence.
     */
	private void testerCollisionsEtAjusterVitesses() {	
		 
		canon.getBalle().gererCollisions(posMurSol, posMurDroit , posMurHaut, posMurGauche);
	}

	 /**
     * Calcule les forces agissant sur les objets de la zone d'animation, telles que la gravité.
     */

	private void calculerLesForces() {

//		Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
//        Vecteur2D forceUtilisateur= new Vecteur2D(canon.getFleche().calculerComposantX(),canon.getFleche().calculerComposantY());
//        
//		canon.getBalle().setSommeDesForces(forceUtilisateur);


	}
	/**
     * Méthode qui permet de tirer la balle.
     */
	public  void TirerBalle() {
		balleTiree=true;
		canon.setBalleTiree();
	}
	
	/**
     * Initialise l'écouteur de clavier pour interagir avec l'animation via le clavier.
     */
	private void ecouteurClavier() {
	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_W: 
//	                	 System.out.println("SALUT JE ne VAIS pas ICI");
	                    break;
	                case KeyEvent.VK_S: 
//	                   System.out.println("SALUT JE VAIS ICI");
	                    break;
	            }
	            repaint();
	        }
	    });
	}


	/**
     * Initialise les écouteurs de souris pour permettre l'interaction avec l'animation via la souris.
     */
	private void ecouteurSouris() {
		addMouseListener((MouseListener) new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {

				if(rec.contient(e.getX(), e.getY())) {
					rec.setClickedOnIt(true);
					repaint();
				}else {
					rec.setClickedOnIt(false);
					repaint();
				}

			}
		});
	
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
				
			public void mouseDragged(MouseEvent e) {
				
				gestionSourisRec(e);
					
				if(!balleTiree) {
					canon.rotate(e.getX(),e.getY());
		        	canon.changerTaille(e.getX(), e.getY());
				}
                if(canon.contient(e.getX(), e.getY())) {
					canon.move(e.getY());
					repaint();
				}
			}
		});
	}
	
	
	private void gestionSourisRec(MouseEvent e) {
		index = rec.getClickedResizeHandleIndex(e.getX(), e.getY());
		repaint();
		if (rec.isClickedOnIt() == true && index != -1) {
			rec.resize(index, e.getX(), e.getY());
			repaint();
		}else if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == true && index == -1 ) {
			rec.rotate( e.getX(), e.getY());
			repaint();
		}
		if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == false) {
			rec.move( e.getX(), e.getY());
			repaint();
		}
	}
	
}
		
			

		