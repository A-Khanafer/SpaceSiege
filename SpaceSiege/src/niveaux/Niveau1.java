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

public class Niveau1 extends JPanel implements Runnable {

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
	private double deltaT=0.10;
	/**
     * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
     */
	private int tempsDuSleep = 10;
	/**
     * Un rectangle servant d'obstacle dans la zone d'animation.
     */
	private Rectangle rec;
	
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
    
	/**
     * Le canon utilisé pour tirer des balles.
     */
	private Canon canon=new Canon (0,80,(int)pixelParMetres);
    private  int balleChoisie;

    private Triangle tri;

    private boolean monstreMort=false;
    private PlanCartesien planCartesion= new PlanCartesien();

    
    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
    //Benakmoum Walid
	public Niveau1() {
		setBackground(new Color(255, 255, 255));
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
		pixelParMetres = getWidth()/150;
		System.out.println(pixelParMetres);

		if(premiereFois) {
			rec = new Rectangle(50,50, pixelParMetres);
			monstre= new Monstres(950,100,"images.jpg", pixelParMetres);
			tri = new Triangle(150, 150, 10, 10, pixelParMetres);
			premiereFois = false;
		}





		

		
		
		if(monstreMort==false) {
		monstre.dessiner(g2d);
		}


		rec.dessiner(g2d);

		tri.dessiner(g2d);

		canon.dessiner(g2d);


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
	//Benakmoum Walid
	public void run() {
		while (enCoursDAnimation) {

//			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
//			System.out.println("Temps ecoule "+tempsTotalEcoule);


System.out.println(canon.getBalle().getPosition().getX());

			System.out.println(canon.getBalleActuelle().getVitesse());

			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
			


			CollisionRectangle.detectionCollisionRectangle(canon.getBalle(),rec);
				
			

			Area areaBalle = new Area(canon.getBalle().getCercle()); 
	        Area areaMonstre = monstre.getArea();
	        areaBalle.intersect(areaMonstre);

	        if (!areaBalle.isEmpty()) {
	        	monstre.perdUneVie();
	        	reinitialiserApplication();
	        	System.out.println(monstre.getNombreDeVie());
	        }


			repaint();
			if(monstre.getNombreDeVie()==0) {
	    	    monstreMort=true;
	            enCoursDAnimation = false; 
	            JOptionPane.showMessageDialog(null,"VOUS AVEZ GAGNE");
	    	}
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
	// Benakmoum Walid
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
			balleTiree=true;
			canon.setBalleTiree();
			
		}
	}//fin methode
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	//Benakmoum Walid
	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		calculerLesForces();
		canon.avancerUnPas(deltaT);
	}
	

	/**
     * Teste les collisions entre les éléments graphiques et ajuste leurs vitesses en conséquence.
     */
	//ZAKARIA SOUDAKI
	private void testerCollisionsEtAjusterVitesses() {	
		 
		canon.getBalle().gererCollisions(posMurSol, posMurDroit , posMurHaut, posMurGauche);
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
	
	    enCoursDAnimation = false;


	    rotation = 0;
	    tempsTotalEcoule = 0;


	    balleTiree = false;
	    canon.setPremiereFois(true);
	    canon = new Canon(0, 80,(int)pixelParMetres);
	   monstreMort=false;

	    rec = new Rectangle(50, 50, pixelParMetres);

	   repaint();

	}
	/**
     * Méthode qui permet de tirer la balle.
     */
	  //Benakmoum Walid
	public  void TirerBalle() {
		balleTiree=true;
		canon.setBalleTiree();
		repaint();
		
	}
	
	public void choisirBalle(int nb) {

		canon.setBalleActuelle(nb);

		repaint();
	}
	public void setNombreDeVie(int nb) {
	    this.nombreDeVie = nb;
	    if (this.monstre != null) {
	        this.monstre.setNombreDeVie(nb);
	    }
	    repaint();
	}

	
	/**
     * Initialise l'écouteur de clavier pour interagir avec l'animation via le clavier.
     */
	  //Benakmoum Walid
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
	//Ahmad Khanafer
	private void ecouteurSouris() {
		addMouseListener((MouseListener) new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {
			
			
				repaint();



				if(rec.contient(e.getX(), e.getY())) {
					rec.setClickedOnIt(true);
					repaint();
				}else {
					rec.setClickedOnIt(false);
					repaint();
				}
				
				if(tri.contient(e.getX(), e.getY())) {
					tri.setClickedOnIt(true);
					repaint();
				}else {
					tri.setClickedOnIt(false);
					repaint();
				}

			}
		});
	
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
				
			public void mouseDragged(MouseEvent e) {
				
				gestionSourisRec(e);
				gestionSourisTri(e);
					
				gestionSourisCanon(e);
			}
		});
	}
	
	//Méthode qui gère les click de la souris pour le rectangle
	//Ahmad Khanafer
	private void gestionSourisRec(MouseEvent e) {
		index = rec.getClickedResizeHandleIndex(e.getX(), e.getY());
		repaint();
		if (rec.isClickedOnIt() == true && index != -1) {
			rec.redimension(index, e.getX(), e.getY());
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
	
	private void gestionSourisTri(MouseEvent e) {
		index = tri.getClickedResizeHandleIndex(e.getX(), e.getY());
		repaint();
		if (tri.isClickedOnIt() == true && index != -1) {
			tri.redimension(index, e.getX(), e.getY());
			repaint();
		}else if(tri.contient(e.getX(), e.getY()) && tri.isClickedOnIt() == true && index == -1 ) {
			tri.rotate( e.getX(), e.getY());
			repaint();
		}
		if(tri.contient(e.getX(), e.getY()) && tri.isClickedOnIt() == false) {
			tri.move( e.getX(), e.getY());
			repaint();
		}
	}
	/**
	 * Méthode qui permet de gérer le canon selon les mouvements de la souris
	 * @param e Événement de la souris
	 */
	  //Benakmoum Walid
	private void gestionSourisCanon(MouseEvent e) {
		if(!balleTiree && !rec.contient(e.getX(), e.getY())) {
			canon.rotate(e.getX(),e.getY());
        	canon.changerTaille(e.getX(), e.getY());
		}
        if(canon.contient(e.getX(), e.getY())) {
			canon.move(e.getY());
			repaint();
		}
	}
}
		
			

		