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
		
		System.out.println(pixelParMetres);

		if(premiereFois) {
			pixelParMetres = getWidth()/150;
			int espace=0;
			monstre = new Monstres(1000, 20, "images.jpg", pixelParMetres);
			
				for(int i = 0 ; i < tableauRec.length ; i++) {
					tableauRec[i] = new Rectangle(50 + espace, 50 + espace, pixelParMetres);
					espace = espace + 80;
				}
				espace = 0;
				for(int i = 0 ; i < tableauRec.length ; i++) {
					tableauTri[i] = new Triangle(50, 50, 10, 15, pixelParMetres);
					espace = espace + 80;
				}
			premiereFois = false;
		}

		tableauRec[0].dessiner(g2d);
		tableauRec[1].dessiner(g2d);
		tableauRec[2].dessiner(g2d);

		tableauTri[0].dessiner(g2d);
		tableauTri[1].dessiner(g2d);

	//	planCartesion.setBalle(canon.getBalle());

		
		
		if(monstreMort==false) {
			monstre.dessiner(g2d);
		}


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


		//System.out.println(canon.getBalle().getPosition().toString()+" POSTIONS DANS LE RUN");

			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
			

			for(int i =0 ; i < tableauRec.length ; i++) {
				CollisionRectangle.detectionCollisionRectangle(canon.getBalle(),tableauRec[i]);
			}
			

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
	//WALID
	  public void prochaineImage() {
		  if(canon.getBalle().getVitesse()!=null) {
		  System.out.println("Prochaine image...on avance de " + deltaT + " secondes");
			calculerUneIterationPhysique(deltaT);
			repaint();
		  }
	  }
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	//Benakmoum Walid
	public void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		calculerLesForces();
		canon.avancerUnPas(deltaT);
	}
	

	/**
     * Teste les collisions entre les éléments graphiques et ajuste leurs vitesses en conséquence.
     */
	//ZAKARIA SOUDAKI
	public void testerCollisionsEtAjusterVitesses() {	
		 
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
	    monstre = new Monstres(1000, 20, "images.jpg", pixelParMetres);
	    canon = new Canon(0, 10,pixelParMetres);
	    
	   monstreMort=false;


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
			
				gestionSourisRecClick(e);
				gestionSourisTriClick(e);
				repaint();
			}
			 
		});
	
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
				
			public void mouseDragged(MouseEvent e) {
				gestionSourisRecDragged(e);
				gestionSourisTriDragged(e);
				gestionSourisCanon(e);
				repaint();
			}
			
		});
	}
	//Méthode qui gère les click de la souris pour le rectangle
		//Ahmad Khanafer
		private void gestionSourisRecDragged(MouseEvent e) {
			for(int i =0 ; i < tableauRec.length; i++) {
				int index = tableauRec[i].getClickedResizeHandleIndex(e.getX(), e.getY());
					if (tableauRec[i].isClickedOnIt() == true && index != -1) {
						tableauRec[i].redimension(index, e.getX(), e.getY());
						repaint();
					}else if(tableauRec[i].contient(e.getX(), e.getY()) && tableauRec[i].isClickedOnIt() == true && index == -1 ) {
						tableauRec[i].rotate( e.getX(), e.getY());
						repaint();
					}
					if(tableauRec[i].contient(e.getX(), e.getY()) && tableauRec[i].isClickedOnIt() == false) {
						tableauRec[i].move( e.getX(), e.getY());
						repaint();
					}
			}	
		}
		
		private void gestionSourisTriDragged(MouseEvent e) {
			for(int i =0 ; i < tableauTri.length; i++) {
				int index = tableauTri[i].getClickedResizeHandleIndex(e.getX(), e.getY());
					if (tableauTri[i].isClickedOnIt() == true && index != -1) {
						tableauTri[i].redimension(index, e.getX(), e.getY());
						repaint();
					}else if(tableauTri[i].contient(e.getX(), e.getY()) && tableauTri[i].isClickedOnIt() == true && index == -1 ) {
						tableauTri[i].rotate( e.getX(), e.getY());
						repaint();
					}
					if(tableauTri[i].contient(e.getX(), e.getY()) && tableauTri[i].isClickedOnIt() == false) {
						tableauTri[i].move( e.getX(), e.getY());
						repaint();
					}
			}
		}
		
		private void gestionSourisRecClick(MouseEvent e) {
			for(int i =0 ; i < tableauRec.length; i++) {
				if(tableauRec[i].contient(e.getX(), e.getY())) {
					System.out.println("CLICKEEEZZZZZZZZZ on");
					tableauRec[i].setClickedOnIt(true);
					repaint();
				}else {
					System.out.println("CLICKEEEZZZZZZZZZ off");
					tableauRec[i].setClickedOnIt(false);
					repaint();
				}
			}
			
		}
		
		private void gestionSourisTriClick(MouseEvent e) {
			for(int i =0 ; i < tableauTri.length; i++) {
				if(tableauTri[i].contient(e.getX(), e.getY())) {
					tableauTri[i].setClickedOnIt(true);
					repaint();
				}else {
					tableauTri[i].setClickedOnIt(false);
					repaint();
				}
			}
		}
	/**
	 * Méthode qui permet de gérer le canon selon les mouvements de la souris
	 * @param e Événement de la souris
	 */
	  //Benakmoum Walid
		private void gestionSourisCanon(MouseEvent e) {
		    boolean toucheObjet = false;

		    for (Rectangle rec : tableauRec) {
		        if (rec.contient(e.getX(), e.getY())) {
		            toucheObjet = true;
		            break; 
		        }
		    }

		    
		    if (!toucheObjet) {
		        for (Triangle tri : tableauTri) {
		            if (tri.contient(e.getX(), e.getY())) {
		                toucheObjet = true;
		                break;
		            }
		        }
		    }

		    if (!balleTiree && !toucheObjet) {
		        canon.rotate(e.getX(), e.getY());
		        canon.changerTaille(e.getX(), e.getY());
		    }

		   
		    if (canon.contient(e.getX(), e.getY())) {
		        canon.move(e.getY());
		    }
		    repaint();
		}
	
}
		
			

		