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

import obstacles.CercleElectrique;
import obstacles.Rectangle;

import obstacles.Triangle;
import physique.Collisions;
import physique.MoteurPhysique;

public class Niveau1 extends Niveaux {

	/**
	 * La classe Niveau1 étend JPanel et implémente Runnable pour fournir une zone d'animation interactive. Cette zone permet de simuler des animations basées sur la physique, telles que le mouvement d'un canon tirant des balles, et de gérer des interactions avec des obstacles.
	 * @author Benakmoum Walid
	 * @author Khanafer Ahmad
	 * @author Soudaki Zakaria
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Indique si une animation est actuellement en cours.
     */
	private boolean enCoursDAnimation=false;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	  private static final double K_CONST = 9.0e4;
	/**
     * L'intervalle de temps (en secondes) utilisé pour chaque itération du calcul physique.
     */
	private final double deltaT=0.05;
	/**
     * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
     */
	private int tempsDuSleep = 5;
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
    int hauteurComposant;
    /**
     * Largeur du composant d'animation, influençant le placement des éléments et la détection des collisions.
     */
    int longueurComposant;
    
    /**
     * Index utilisé pour identifier les manipulations spécifiques des éléments de l'interface, telles que le redimensionnement ou la rotation d'obstacles.
     */
    private int index = -1;
    /**
     * Le nombre de vie du joueur.
     */
    private int nombreDeVie=2;
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
    private Triangle[] tableauTri;
    
    private int keyCode = 0;
    
    
  
    private CercleElectrique boule;
    /**
     * Le canon utilisé pour tirer des balles.
     */
	
	/**
    * Indique si le monstre est mort.
    */
    private boolean monstreMort=false;
    
    private boolean first =true;
    
    private Vecteur2D forceHautBas = new Vecteur2D(0,0);
    
    private Vecteur2D forceDroiteGauche = new Vecteur2D(0,0);
   

   
   

    
    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
	public Niveau1() {
		
		
		 setFocusable(true);

        
		
		if(first) {
			
		
	    setLayout(null);
	    first =false;
		}
		
		
		
		tableauRec = new Rectangle[11];
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
		

		   if(premiereFois) {
			
			
			pixelParMetres = (double) getWidth()/150;
		
			
			
		   	monstre = new Monstres(1200, 40, pixelParMetres);
			canon = new Canon(0, 10,pixelParMetres);
			
			System.out.println(monstre.getNombreDeVie()+"___");
		
			 
			 tableauRec[0] = new Rectangle(  143,  354, 330, 53,0);
			 
			 tableauRec[1] = new Rectangle(  1200,  120, 186, 48,0);
			 
			 tableauRec[2] = new Rectangle(  1000,  500, 229, 57,0);
			 
			 tableauRec[3] = new Rectangle(  1100,  493, 280, 56,0);
			 
			 tableauRec[4] = new Rectangle( 1300, 100, 258, 57,0);
			 
			 tableauRec[5] = new Rectangle(  398, 377, 290, 52,0);
			 
			 tableauRec[6] = new Rectangle( 1050, 200, 138, 47,0);
			 
			 tableauRec[7] = new Rectangle(  1100,458, 210, 60,0);
			 
			 tableauRec[8] = new Rectangle( 1000, 800, 162, 36,45);
			 
			 tableauRec[9] = new Rectangle(  1080, 464, 140, 124,45);
			 
			 tableauRec[10] = new Rectangle(  1172, 538, 113, 92,0);

			
			 premiereFois = false;
			
		}

		

		for (int i = 0; i < tableauRec.length; i++) {
			tableauRec[i].dessiner(g2d);
			
		}

		


		boule= new CercleElectrique(398 ,200,pixelParMetres);
		boule.dessiner(g2d);
          

		

		
		 
		if(monstreMort==false) {
			monstre.dessiner(g2d);
		}

        
		canon.dessiner(g2d);

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
	public void run() {
		
		requestFocusInWindow(); 
		
		while (enCoursDAnimation) {			

			Vecteur2D anciennePosition = canon.getBalle().getPositionEnMetre();
			
			calculerUneIterationPhysique(deltaT);
			
			testerCollisionsEtAjusterVitesses();
			
			this.pcs.firePropertyChange("position", anciennePosition, canon.getBalle().getPositionEnMetre());	

			
			for(int i =0 ; i < tableauRec.length ; i++) {
				Collisions.collisionRectangle(canon.getBalle(),tableauRec[i]);
			}
			

            if(Collisions.getNbRebond()>=153) {
				enCoursDAnimation=false;
		
				reinitialiserPosition();
				
			}
            
            Area areaBalle = new Area(canon.getBalle().getCercle()); 
	        Area areaMonstre = monstre.toAire();
	        areaBalle.intersect(areaMonstre);

	        if (!areaBalle.isEmpty()) {
	        	monstre.perdUneVie();
	        	reinitialiserPosition();
	        }

	        
	        
			if(monstre.getNombreDeVie()==0) {
	    	    monstreMort=true;
	            enCoursDAnimation = false; 
	            
	            JOptionPane.showMessageDialog(null,"VOUS AVEZ GAGNE");
	            
	            reinitialiserApplication();
	    	}
			
			System.out.println("________________________________"+Collisions.getNbRebond());
			
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
			balleTiree=true;
			canon.setBalleTiree();
			requestFocus();
		}
	}//fin methode
	/**
	 * Méthode qui arrête l'animation en cours.
	 */
	public void arreter() {
		enCoursDAnimation=false;
	}
	/**
	 * Méthode qui change le type de gravité utilisé dans la simulation.
	 * @param typeGravite Le type de gravité à utiliser : "TERRE", "MARS" ou "ESPACE".
	 */

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
	        	gravite = 0;
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

	  public void prochaineImage() {
		  
		  if(canon.getBalle().getVitesse()!=new Vecteur2D(0,0)) {
			  canon.setProchaineImage(true);
			  calculerUneIterationPhysique(deltaT);
				repaint();
		  }
		  
	  }
	  public boolean getEnCoursAnimation() {
			return enCoursDAnimation;
			
		}

	  
	/**
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
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
		
	
		
		for(int i =0 ; i < tableauRec.length ; i++) {
		    Collisions.collisionMonstreRec(monstre, tableauRec[i]);	
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
	 Vecteur2D force=new Vecteur2D(0,0);
	    double rayonElectrique = boule.getRayonElectrique();
		Vecteur2D forceMonstre= forceHautBas.additionne(forceDroiteGauche);
	    if (distance < rayonElectrique) {
	        try {
	        	boule.setAnimation(true);
	           
	            Vecteur2D vecteurUnitaire = boule.getPositionCentre().soustrait(canon.getBalle().getPosCentral()).normalise();


	    // Pour le bien de l'appli K est reduit
	            double forceElectrique = K_CONST * (1 / distance);
                  System.out.println(forceElectrique+"____________________________---");
	         
	           force = vecteurUnitaire.multiplie(forceElectrique);

	          
	           
	        } catch (Exception e) {
	            
	            System.err.println("Une erreur est survenue lors de la normalisation du vecteur unitaire : " + e.getMessage());
	        }
	    }
	 
	    canon.getBalle().setSommeDesForces(forceDeGravite.additionne(force));
	    monstre.setSommeDesForces(forceMonstre);
	}
	/**
	 * Réinitialise l'application à son état initial, incluant la remise à zéro de tous les composants d'animation et des variables d'état.
	 * Cette méthode stoppe l'animation en cours si elle est active, réinitialise la rotation, le temps total écoulé, l'état de tir de la balle,
	 */
	public void reinitialiserApplication() {
	
		    enCoursDAnimation = false;

		    tempsTotalEcoule = 0;


		    balleTiree = false;
		    canon.setPremiereFois(true);
		    monstre = new Monstres(1000, 20, pixelParMetres);
		    if(monstre.getNombreDeVie()==0) {
		    monstre.setNombreDeVie(1);
		    }
		    canon = new Canon(0, 10,pixelParMetres);
		    Collisions.setNbrebond(0);
		   monstreMort=false;


		   repaint();
	}
	public void reinitialiserPosition() {
		enCoursDAnimation=false;
		balleTiree = false;
	    canon.setPremiereFois(true);
	    canon = new Canon(0, 10,pixelParMetres);
	    Collisions.setNbrebond(0);
	    repaint();
	}
	/**
     * Méthode qui permet de tirer la balle.
     */
	public  void TirerBalle() {
		balleTiree=true;
		canon.setBalleTiree();
		repaint();
		
	}
	/**
	 * Méthode qui permet de choisir le type de balle à tirer.
	 * @param nb Le numéro de la balle à choisir.
	 */

	public void choisirBalle(int nb) {

		canon.setBalleActuelle(nb);

		repaint();
	}
	/**
	 * Méthode qui définit le nombre de vie du joueur.
	 * @param nb Le nombre de vie à définir.
	 */
	public void setNombreDeVie(int nb) {
	    this.nombreDeVie = nb;
	    if (this.monstre != null) {
	        this.monstre.setNombreDeVie(nombreDeVie);
	    }
	    repaint();
	}
	public void stopperAnim() {
		if(enCoursDAnimation==true) {
	enCoursDAnimation=false;
		}else {
		demarrer();
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
		private void gestionSourisCanon(MouseEvent e) {
		boolean toucheObjet = false;


		    if (!balleTiree && !toucheObjet) {
		 
		    	if(e.getX()>30) {
		        canon.rotate(e.getX(), e.getY());
		    
		    	
		        canon.changerTaille(e.getX(), e.getY());
		    	}
		    }

		   
		    if (canon.contient(e.getX(), e.getY())) {
		        canon.moveY(e.getY());
		    }
		    repaint();
		}

	public int getVie() {
		return this.nombreDeVie;
		/*
	      public void keyPressed(KeyEvent e) {
	        	
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_SPACE:
	                	  if(canon.getBalle().quelleTypeBalle()==3) {
	               for (int i = 0; i < 10; i++) {
				
	                       canon.getBalle().exploser();
	               }
	                     
	                     repaint();
	                	  }
	                	
	            }

	         
	        }
	        */
	}
	public void ecouteurClavier() {

	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	
	        	switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                	  if(canon.getBalle().quelleTypeBalle()==3) {
               for (int i = 0; i < 10; i++) {
			
                       canon.getBalle().exploser();
               }
                     
                     repaint();
                	  }
                	
            }

	            if(enCoursDAnimation) {
	            keyCode = e.getKeyCode();
	            

	            switch (keyCode) {
	                case KeyEvent.VK_UP:
	                    // Action à effectuer lors de l'appui sur la flèche vers le haut
//	                		monstre.setPosY(-2);
	                	forceHautBas.setY(-50);
	                    break;
	                case KeyEvent.VK_DOWN:
	                    // Action à effectuer lors de l'appui sur la flèche vers le bas
//	                		monstre.setPosY(2);
	                	forceHautBas.setY(50);
	                	
	                	
	                    break;
	                case KeyEvent.VK_LEFT:
	                    // Action à effectuer lors de l'appui sur la flèche vers la gauche
//	                		monstre.setPosX(-2);
	                	forceDroiteGauche.setX(-50);
	                	
	                	
	                    break;
	                case KeyEvent.VK_RIGHT:
	                    // Action à effectuer lors de l'appui sur la flèche vers la droite
//	                		monstre.setPosX(2);
	                	forceDroiteGauche.setX(50);
	                		
	                	
	                    break;
	                default:
	                	keyCode = 0;
	                    // Action à effectuer pour d'autres touches, si nécessaire
	                    break;
	            }
	          
	        }
	        }
	    	
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		  keyCode = e.getKeyCode();
		            switch (keyCode) {
		                case KeyEvent.VK_UP:
		                    // Action à effectuer lors de l'appui sur la flèche vers le haut
//		                		monstre.setPosY(-2);
		                	forceHautBas.setY(0);
		                    break;
		                case KeyEvent.VK_DOWN:
		                    // Action à effectuer lors de l'appui sur la flèche vers le bas
//		                		monstre.setPosY(2);
		                	forceHautBas.setY(0);
		                	
		                	
		                    break;
		                case KeyEvent.VK_LEFT:
		                    // Action à effectuer lors de l'appui sur la flèche vers la gauche
//		                		monstre.setPosX(-2);
		                	forceDroiteGauche.setX(0);
		                	
		                	
		                    break;
		                case KeyEvent.VK_RIGHT:
		                    // Action à effectuer lors de l'appui sur la flèche vers la droite
//		                		monstre.setPosX(2);
		                	forceDroiteGauche.setX(0);
		                		
		                	
		                    break;
		                default:
		                	keyCode = 0;
		                    // Action à effectuer pour d'autres touches, si nécessaire
		                    break;
		            }
	    	}
	    });
	}
	public void setMasseBalle(int mas) {
		canon.getBalle().setMasse(mas);
	}
	
	public Monstres getMonstre() {
		return this.monstre;
	}
		
}
		

		
			

		
