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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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

import obstacles.Cercle;
import obstacles.CercleElectrique;
import obstacles.Epines;
import obstacles.PlaqueRebondissante;
import obstacles.Rectangle;

import obstacles.Triangle;
import physique.Collisions;
import physique.MoteurPhysique;

public class Niveau2 extends Niveaux {

	/**
	 * La classe Niveau1 étend JPanel et implémente Runnable pour fournir une zone d'animation interactive. Cette zone permet de simuler des animations basées sur la physique, telles que le mouvement d'un canon tirant des balles, et de gérer des interactions avec des obstacles.
	 * @author Benakmoum Walid
	 * @author Khanafer Ahmad
	 * @author Soudaki Zakaria
	 */
	
	/**
	 * serialVersionUID pour la sérialisation.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Indique si une animation est actuellement en cours.
	 */
	private boolean enCoursDAnimation = false;
	
	/**
	 * Support pour les changements de propriété.
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 * Constante pour la constante électrique K.
	 */
	private static final double K_CONST = 9.0e4;
	
	/**
	 * L'intervalle de temps (en secondes) utilisé pour chaque itération du calcul physique.
	 */
	private final double deltaT = 0.05;
	
	/**
	 * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
	 */
	private int tempsDuSleep = 10;
	
	
	
	/**
	 * Indique si une balle a été tirée par le canon.
	 */
	private boolean balleTiree = false;
	
	/**
	 * Utilisé pour effectuer des opérations lors du premier appel de certaines méthodes ou conditions.
	 */
	private double tempsTotalEcoule = 0;
	
	/**
	 * Position du mur du sol, utilisée pour détecter les collisions et gérer les rebonds ou arrêts des objets animés.
	 */
	private double posMurSol;
	
	/**
	 * Position du mur droit, utilisée pour détecter les collisions latérales.
	 */
	private double posMurDroit;
	
	/**
	 * Position du mur haut, utilisée pour détecter les collisions et gérer les interactions en haut de la zone d'animation.
	 */
	private double posMurHaut;
	
	/**
	 * Position du mur gauche, permettant de gérer les collisions sur le côté gauche de la zone d'animation.
	 */
	private double posMurGauche;
	
	/**
	 * Hauteur du composant d'animation, utilisée pour ajuster les interactions et le rendu en fonction de la taille de la zone d'animation.
	 */
	private int hauteurComposant;
	
	/**
	 * Largeur du composant d'animation, influençant le placement des éléments et la détection des collisions.
	 */
	private int longueurComposant;
	
	/**
	 * Index utilisé pour identifier les manipulations spécifiques des éléments de l'interface, telles que le redimensionnement ou la rotation d'obstacles.
	 */
	private int index = -1;
	
	/**
	 * Le nombre de vie du joueur.
	 */
	private int nombreDeVie = 2;
	
	/**
	 * L'instance du monstre présent dans le niveau.
	 */
	private Monstres monstre;
	
	/**
	 * Indique si c'est la première fois que le niveau est affiché, utilisé pour initialiser les objets une seule fois.
	 */
	private boolean premiereFois = true;
	
	/**
     * L'index de la balle choisie par le joueur.
     */
    private  int balleChoisie;
    /**
     * Un tableau de triangles servant d'obstacles dans la zone d'animation (commenté pour le moment).
     */
   
  
   
    
	
	
	
	/**
	 * Code de la touche enfoncée.
	 */
	private int keyCode = 0;
	
	/**
	 * Objet représentant la boule électrique.
	 */
	private CercleElectrique boule;
	
	/**
	 * Le canon utilisé pour tirer des balles.
	 */
	private Canon canon;
	
     /**
	 * Indique si le monstre est mort.
	 */
	private boolean monstreMort = false;
	
	/**
	 * Utilisé pour détecter le premier appel lors du lancement de l'animation.
	 */
	private boolean first = true;
	
	/**
	 * Vecteur représentant la force haut/bas appliquée sur les objets.
	 */
	private Vecteur2D forceHautBas = new Vecteur2D(0, 0);
	
	/**
	 * Vecteur représentant la force droite/gauche appliquée sur les objets.
	 */
	private Vecteur2D forceDroiteGauche = new Vecteur2D(0, 0);
	
	/**
	 * Ancienne valeur de l'état d'animation.
	 */
	private boolean ancienneValeur;

/**
 * Boolean pour voir si la balle explose ou pas
 */
	   private boolean enExplosion=false;;
	   /**
	     * Tableau des rectangles présents dans la configuration.
	     */
	   private Rectangle[] tableauRectangles = {
			   new Rectangle(449, 674, 183, 90, 0.0),
			    new Rectangle(634, 678, 104, 84, 0.0),
			    new Rectangle(739, 673, 232, 90, 0.0),
			    new Rectangle(516, 584, 168, 90, 0.0),
			    new Rectangle(689, 585, 208, 90, 0.0),
			    new Rectangle(707, 257, 93, 324, 0.0053202619460245675),
			    new Rectangle(706, 3, 90, 105, 0.0),
		    };
	   /**
	     * Tableau des épingles présentes dans la configuration.
	     */
		    private Epines[] tableauEpines = {
		    	
		    	    new Epines(542, 375, 284, 45, -1.5707963267948966),
		    	 
		    	    };
		    /**
		     * Tableau des cercles électriques présents dans la configuration.
		     */
		    private CercleElectrique[] tableauCerclesElectriques = {
		    		 new CercleElectrique(500, 250, 90, 90, 0),
		    		
		    };
		    /**
		     * Tableau des triangles présents dans la configuration.
		     */
		    private Triangle[] tableauTriangles = {
		    		 new Triangle(857, 413, 90, 176, 1.0058022145284227),
		    		    new Triangle(943, 486, 90, 180, 0.651374961817385),
		    };
		    /**
		     * Tableau des cercles présents dans la configuration.
		     */
		    private Cercle[] tableauCercles = {
		    		new Cercle(300, 335, 90, 90, 0),
		    };
		    /**
		     * Tableau des plaques rebondissantes présentes dans la configuration.
		     */
		    private PlaqueRebondissante[] tableauPlaquesRebondissantes = {
		    		 new PlaqueRebondissante(834, 48, 180, 27, -0.5016040541891205),
		    		 new PlaqueRebondissante(1027, 677, 180, 27, 0.0),
		    };
		    /**
		     * Force du monstre dans cette configuration.
		     */
	private int forceMonstre = 50;

			private boolean enCollisionAvecEpines=false;
   


    
    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
	//ZAKARIA SOUDAKI
	public Niveau2() {
		
		setLayout(null);
		
		 setFocusable(true);

        
		
		if(first) {
			
		
	    setLayout(null);
	    first =false;
		}
		
		
		
;
		ecouteurSouris();
		ecouteurClavier();
		
		
		
			
		
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
		

		   if(premiereFois) {
			
			
			pixelParMetres = (double) getWidth()/150;
		
			
			
			 monstre = new Monstres(getWidth()- ((8*pixelParMetres)/2) - 100, getHeight()/2 - ((8*pixelParMetres)/2), pixelParMetres);
			canon = new Canon(0, 10,pixelParMetres);
			
		
		
			 premiereFois = false;
			
		}

	
		for (int i = 0; i < tableauRectangles.length; i++) {
	tableauRectangles[i].dessiner(g2d);
			
		}

	    for (int i = 0; i < tableauEpines.length; i++) {
	        tableauEpines[i].dessiner(g2d);
	    }

	    for (int i = 0; i < tableauCerclesElectriques.length; i++) {
	        tableauCerclesElectriques[i].dessiner(g2d);
	    }

	    for (int i = 0; i < tableauTriangles.length; i++) {
	        tableauTriangles[i].dessiner(g2d);
	    }

	    for (int i = 0; i < tableauCercles.length; i++) {
	        tableauCercles[i].dessiner(g2d);
	    }

	    for (int i = 0; i < tableauPlaquesRebondissantes.length; i++) {
	        tableauPlaquesRebondissantes[i].dessiner(g2d);
	    }
		


		boule=  new CercleElectrique(500, 250, 90, 90, 0);
		boule.dessiner(g2d);
          

		

		
		 
		if(monstreMort==false) {
			monstre.dessiner(g2d);
		}

        
		canon.dessiner(g2d);
		if(modeScience) {
		afficherDonneesBalleActuelle(g2d);
		}
	    posMurSol = getHeight();
	    posMurDroit = getWidth();
	    posMurGauche = 0;
	    posMurHaut = 0;

	    hauteurComposant = getHeight();
	    longueurComposant = getWidth();
	     
	    
	   
	    
	   
	}
	
	/**
     * Exécute l'animation en boucle tant que enCoursDAnimation est vrai. Gère le calcul physique et les collisions.
     */
	//Benakmoum Walid
	public void run() {
		requestFocusInWindow(); 
		while (enCoursDAnimation) {

			
			

			Vecteur2D anciennePosition = canon.getBalle().getPositionEnMetre();
			
			calculerUneIterationPhysique(deltaT);
			
			testerCollisionsEtAjusterVitesses();
			
			this.pcs.firePropertyChange("position", anciennePosition, canon.getBalle().getPositionEnMetre());	
			
			this.pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);

			Area areaBalle = new Area(canon.getBalle().getCercle()); 
			  enCollisionAvecEpines = false;
			for(int i =0 ; i < tableauRectangles.length ; i++) {
				Collisions.collisionRectangle(canon.getBalle(),tableauRectangles[i]);
			}
			for(int i =0 ; i < tableauCercles.length ; i++) {
				Collisions.collisionCercle(canon.getBalle(),tableauCercles[i]);
			}
			for(int i =0 ; i < tableauTriangles.length ; i++) {
				Collisions.collisionTriangle(canon.getBalle(),tableauTriangles[i]);
			}
			for(int i =0 ; i < tableauPlaquesRebondissantes.length ; i++) {
				Collisions.collisionPlaqueRebondissante(canon.getBalle(),tableauPlaquesRebondissantes[i]);
			}
			for(int i =0 ; i < tableauEpines.length ; i++) {
				enCollisionAvecEpines = false; 
				 Area areaEpines = tableauEpines[i].toAire();
	                areaEpines.intersect(areaBalle);
	                if (!areaEpines.isEmpty()) {
	                    enCollisionAvecEpines = true;
			}
	                if (enCollisionAvecEpines) {
	    	         
	    	            ancienneValeur = enCoursDAnimation;
	    	            enCoursDAnimation = false;
	    	            pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
	    	            reinitialiserPosition();
	    	        }

		
	        Area areaMonstre = monstre.toAire();
	        areaBalle.intersect(areaMonstre);

	        if (!areaBalle.isEmpty()) {
	        	monstre.perdUneVie();
	        	reinitialiserPosition();
	        }




			if(monstre.getNombreDeVie()==0) {
	    	    monstreMort=true;
	    	    ancienneValeur = enCoursDAnimation;
	    	    enCoursDAnimation = false;
	    	    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
	            
	            JOptionPane.showMessageDialog(null,"VOUS AVEZ GAGNE");
	            
	            reinitialiserApplication();
	    	}



			
		
			


			if(Collisions.getNbRebond()>=3) {
				ancienneValeur = enCoursDAnimation;
			    enCoursDAnimation = false;
			    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
		
				reinitialiserPosition();
				
			}

			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...!");	
		}
     }

	/**
     * Démarre le thread d'animation si ce n'est pas déjà fait.
     */
	//Benakmoum Walid
	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
		 ancienneValeur = enCoursDAnimation;
		    enCoursDAnimation = true;
		    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
			balleTiree=true;
			canon.setBalleTiree();
			requestFocus();
		}
	}//fin methode
	/**
	 * Méthode qui arrête l'animation en cours.
	 */
	//Benakmoum Walid
	public void arreter() {
		ancienneValeur = enCoursDAnimation;
	    enCoursDAnimation = false;
	    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
	}
	/**
	 * Méthode qui change le type de gravité utilisé dans la simulation.
	 * @param typeGravite Le type de gravité à utiliser : "TERRE", "MARS" ou "ESPACE".
	 */
	//Benakmoum Walid
	public void changerTypeGravite(String typeGravite) {
	    double gravite;
	    switch (typeGravite) {
	        case "TERRE":
	            gravite = 9.81/2;
	            break;
	        case "MARS":
	            gravite = 3.711/2;
	            break;
	        case "ESPACE":
	            gravite = 0;
	            break;
	        case "LUNE":
	        	gravite = 2.837;
	        	break;
	        default:
	            gravite = 0; 
	            break;
	    }
	   
	    MoteurPhysique.changerGravite(gravite);
	}
	/**
	 * Méthode qui avance l'animation d'une itération.
	 */
	//Benakmoum Walid
	  public void prochaineImage() {
		  
		  if(canon.getBalle().getVitesse()!=new Vecteur2D(0,0) && balleTiree) {
			  canon.setProchaineImage(true);
			  calculerUneIterationPhysique(deltaT);
				repaint();
		  }
		  
	  }
	  /**
	   * Méthode qui retourne l'état de l'animation en cours.
	   *
	   * @return true si une animation est en cours, sinon false.
	   */
	//Benakmoum Walid
	  public boolean getEnCoursAnimation() {
			return enCoursDAnimation;
			
		}

	  
	  /**
		 * Méthode pour changer la force de déplacement du monstre
		 */
		public void setForceMonstre(int force) {
			this.forceMonstre = force;
		}
	  
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	//Benakmoum Walid & ZAKARIA SOUDAKI
	public void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
	try {
		calculerLesForces();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		canon.avancerUnPas(deltaT);
		monstre.avancerUnPas(deltaT);
	}
	

	/**
     * Teste les collisions entre les éléments graphiques et ajuste leurs vitesses en conséquence.
     */
	//ZAKARIA SOUDAKI
	public void testerCollisionsEtAjusterVitesses() {	
		
        boolean state = Collisions.collisionMonstreBalle(monstre, canon.getBalle());
		
		if (state) {
			reinitialiserPosition();
		}
		
	
		
		for(int i =0 ; i < tableauRectangles.length ; i++) {
		    Collisions.collisionMonstreRec(monstre, tableauRectangles[1]);	
		    Collisions.collisionMonstreRec(monstre, tableauRectangles[2]);	
        
		}
		
		
		Collisions.gererCollisionsBordures(posMurSol, posMurDroit , posMurHaut, posMurGauche, canon.getBalle());
		Collisions.collisionMonstreMur(monstre, longueurComposant, hauteurComposant);
	}

	 /**
     * Calcule les forces agissant sur les objets de la zone d'animation, telles que la gravité.

     */

// Benakmoum Walid
	private void calculerLesForces() throws Exception {
	
		 double distance = canon.getBalle().getPosCentral().distance(boule.getPositionCentre());
		 Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
	 forceElec=new Vecteur2D(0,0);
	    double rayonElectrique = boule.getRayonElectrique();
		Vecteur2D forceMonstre= forceHautBas.additionne(forceDroiteGauche);
	    if (distance < rayonElectrique) {
	        try {
	        	boule.setAnimation(true);
	           
	            Vecteur2D vecteurUnitaire = boule.getPositionCentre().soustrait(canon.getBalle().getPosCentral()).normalise();


	    // Pour le bien de l'appli K est reduit
	            double forceElectrique = K_CONST * (1 / distance);
                  System.out.println(forceElectrique+"____________________________---");
	         
	           forceElec = vecteurUnitaire.multiplie(forceElectrique);

	          
	           
	        } catch (Exception e) {
	            
	            System.err.println("Une erreur est survenue lors de la normalisation du vecteur unitaire : " + e.getMessage());
	        }
	    }
	 forceTotal=forceDeGravite.additionne(forceElec);
	    canon.getBalle().setSommeDesForces(forceTotal);
	    monstre.setSommeDesForces(forceMonstre);
	}
	/**
	 * Réinitialise l'application à son état initial, incluant la remise à zéro de tous les composants d'animation et des variables d'état.
	 * Cette méthode stoppe l'animation en cours si elle est active, réinitialise la rotation, le temps total écoulé, l'état de tir de la balle,
	 */
	//Benakmoum Walid
	public void reinitialiserApplication() {
	
		ancienneValeur = enCoursDAnimation;
	    enCoursDAnimation = false;
	    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);

		    tempsTotalEcoule = 0;


		    balleTiree = false;
		    canon.setPremiereFois(true);
		    monstre = new Monstres(getWidth()- ((8*pixelParMetres)/2) - 100, getHeight()/2 - ((8*pixelParMetres)/2), pixelParMetres);
		    if(monstre.getNombreDeVie()==0) {
		    monstre.setNombreDeVie(1);
		    }
		    canon = new Canon(0, 10,pixelParMetres);
		    Collisions.setNbrebond(0);
		   monstreMort=false;
          forceMonstre=50;

		   repaint();
	}
	/**
	 * Méthode qui arrête ou démarre l'animation en fonction de son état actuel.
	 */
	//Benakmoum Walid
	public void reinitialiserPosition() {
		ancienneValeur = enCoursDAnimation;
	    enCoursDAnimation = false;
	    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
		balleTiree = false;
	    canon.setPremiereFois(true);
	    canon = new Canon(0, 10,pixelParMetres);
	    Collisions.setNbrebond(0);
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
	/**
	 * Méthode qui permet de choisir le type de balle à tirer.
	 * @param nb Le numéro de la balle à choisir.
	 */
	//Benakmoum Walid
	public void choisirBalle(int nb) {

		canon.setBalleActuelle(nb);

		repaint();
	}
	/**
	 * Méthode qui définit le nombre de vie du joueur.
	 * @param nb Le nombre de vie à définir.
	 */
	//Benakmoum Walid
	public void setNombreDeVie(int nb) {
	    this.nombreDeVie = nb;
	    if (this.monstre != null) {
	        this.monstre.setNombreDeVie(nombreDeVie);
	    }
	    repaint();
	}
	/**
	 * Méthode qui arrête ou démarre l'animation en fonction de son état actuel.
	 */
	//Benakmoum Walid
	public void stopperAnim() {
		if(enCoursDAnimation==true) {
			 ancienneValeur = enCoursDAnimation;
			    enCoursDAnimation = false;
			    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
			    enExplosion=false;
		}else {
		demarrer();
		enExplosion=true;
		}
	}

	/**
     * Initialise l'écouteur de clavier pour interagir avec l'animation via le clavier.
     */
	  //Benakmoum Walid

	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}


	/**
     * Initialise les écouteurs de souris pour permettre l'interaction avec l'animation via la souris.
     */
	//Ahmad Khanafer
	private void ecouteurSouris() {
	
		addMouseMotionListener(new MouseMotionAdapter() {
			
				
			public void mouseDragged(MouseEvent e) {
				gestionSourisCanon(e);
				repaint();
			}
			
		});
	}
	
	/**
	 * Méthode qui permet de gérer le canon selon les mouvements de la souris
	 * @param e Événement de la souris
	 */
	//Benakmoum Walid
		private void gestionSourisCanon(MouseEvent e) {
		boolean toucheObjet = false;


		    if (!balleTiree && !toucheObjet) {
		 
		    	if(e.getX()>30) {
		        canon.rotate(e.getX(), e.getY());
		    
		    	
		        canon.changerTaille(e.getX(), e.getY());
		    	}
		    }

		   
		    if (canon.contient(e.getX(), e.getY()) && e.getY()-canon.getBalle().getDiametre()/2>0 &&  e.getY()+canon.getBalle().getDiametre()/2<getHeight()) {		   
             canon.moveY(e.getY());
		    }
		    repaint();
		}
		/**
		 * Méthode qui retourne le nombre de vies restantes du joueur.
		 *
		 * @return Le nombre de vies restantes.
		 */
		//Benakmoum Walid
	public int getVie() {
		return this.nombreDeVie;
		
	}
	/**
	 * Méthode qui gère les événements du clavier.
	 */
	//ZAKARIA SOUDAKI
	public void ecouteurClavier() {

	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	
	        	switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
               exploserBalle();
               enExplosion=true;
                	
            }

	            if(enCoursDAnimation) {
	            keyCode = e.getKeyCode();
	            

	            switch (keyCode) {
	                case KeyEvent.VK_UP:
	                   
	                	forceHautBas.setY(-forceMonstre);
	                   
	                    break;
	                case KeyEvent.VK_DOWN:
	                    
	                	forceHautBas.setY(forceMonstre);
	                   
	                	
	                	
	                    break;
	                case KeyEvent.VK_LEFT:
	                  
	                	forceDroiteGauche.setX(-forceMonstre);
	                   
	                	
	                	
	                    break;
	                case KeyEvent.VK_RIGHT:
	                   
	                	forceDroiteGauche.setX(forceMonstre);
	                    
	                	
	                		
	                	
	                    break;
	                default:
	                	keyCode = 0;
	                    break;
	            }
	          
	        }
	        }
	    	
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		  keyCode = e.getKeyCode();
		            switch (keyCode) {
		                case KeyEvent.VK_UP:
		                 
		                	forceHautBas.setY(0);
		                    break;
		                case KeyEvent.VK_DOWN:
		               
		                	forceHautBas.setY(0);
		                	
		                	
		                    break;
		                case KeyEvent.VK_LEFT:
		              
		                	forceDroiteGauche.setX(0);
		                	
		                	
		                    break;
		                case KeyEvent.VK_RIGHT:
		                	forceDroiteGauche.setX(0);
		                		
		                	
		                    break;
		                default:
		                	keyCode = 0;
		                    break;
		            }
	    	}
	    });
	}

/**
 * Méthode qui modifie la masse de la balle actuelle.
 *
 * @param mas La nouvelle masse de la balle.
 */
	//Benakmoum Walid
	public void setMasseBalle(int mas) {
		canon.getBalle().setMasse(mas);
	}
	/**
	 * Méthode qui retourne l'instance du monstre dans le niveau.
	 *
	 * @return L'instance du monstre.
	 */
	public Monstres getMonstre() {
		return this.monstre;
	}
	/**
	 * Méthode qui affiche les données de la balle actuellement tirée.
	 *
	 * @param g L'objet Graphics utilisé pour le dessin.
	 */
	//Benakmoum Walid
	public void afficherDonneesBalleActuelle(Graphics g) {
	    if (balleTiree) {
	        Balle balle = canon.getBalle();
	        if (balle != null) {
	           
	            g.setColor(Color.white);
	            g.drawString("Données de la Balle Actuelle :", 20, 20);
	            g.drawString("Position (x, y)", 20, 40);
	            g.drawString("Vitesse (vx, vy)", 250, 40);
	            g.drawString("Accélération (ax, ay)", 400, 40);
	            g.drawString("Masse", 20, 80);
	            g.drawString("Force électrique (Fex, Fey)", 250, 80);
	            g.drawString("Force appliquée (Fax, Fay)", 400, 80);
	            
	          
	            int positionX = (int) balle.getPosition().getX();
	            int positionY = (int) balle.getPosition().getY();
	            int vitesseX = (int) balle.getVitesse().getX();
	            int vitesseY = -(int) balle.getVitesse().getY();
	            int accelerationX = (int) balle.getAcceleration().getX();
	            int accelerationY = (int) balle.getAcceleration().getY();
	            int masse = balle.getMasse();
	            int forceElectriqueX = (int) forceElec.getX();
	            int forceElectriqueY = (int) forceElec.getY();
	            int forceAppliqueeX = (int) forceTotal.getX();
	            int forceAppliqueeY = (int) forceTotal.getY();
	            
	            g.drawString("(" + positionX + ", " + positionY + ")", 20, 60);
	            g.drawString("(" + vitesseX + ", " + vitesseY + ")", 250, 60);
	            g.drawString("(" + accelerationX + ", " + accelerationY + ")", 400, 60);
	            g.drawString(Integer.toString(masse)+" kg", 20, 100);
	            g.drawString("(" + forceElectriqueX + ", " + forceElectriqueY + ")", 250, 100);
	            g.drawString("(" + forceAppliqueeX + ", " + forceAppliqueeY + ")", 400, 100);
	        }
	    }
	}
	/**
	 * Méthode qui active ou désactive le mode scientifique.
	 *
	 * @param sc true pour activer le mode scientifique, false pour le désactiver.
	 */
	//Benakmoum Walid
	public void setModeScience(boolean sc) {
		this.modeScience=sc;
		repaint();
	}
	/**
	 * Méthode qui permet de exploser la balle
	 */
	//Benakmoum Walid
	public void exploserBalle() {
		  int delai = 100; 
	        

	        
	    if (canon.getBalle().quelleTypeBalle() == 3) {
	        Timer timer = new Timer();
	      

	        timer.scheduleAtFixedRate(new TimerTask() {
	         
				@Override
	            public void run() {
	            if(enExplosion) {
	                if (canon.getBalle().getDiametre() < 200) {
	                    canon.getBalle().exploser();
	                    repaint();
	                  
	                } else {
	                    timer.cancel();
	                }
	            }
	            }
	        }, 0, delai);
	        
	    }
	}

}
		
			

		
			

		
