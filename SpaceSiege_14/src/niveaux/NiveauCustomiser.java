package niveaux;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import composantjeu.Balle;
import composantjeu.Canon;
import composantjeu.Monstres;
import interfaces.Obstacles;
import obstacles.Cercle;
import obstacles.CercleElectrique;
import obstacles.Epines;
import obstacles.ObstacleHolder;
import obstacles.PlaqueRebondissante;
import obstacles.Rectangle;
import obstacles.Triangle;
import physique.Collisions;
import physique.MoteurPhysique;
import physique.Vecteur2D;

public class NiveauCustomiser extends Niveaux {

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
	private final double deltaT=0.005;
	/**
     * Le temps de pause (en millisecondes) entre chaque itération de l'animation.
     */
	private int tempsDuSleep = 1;

	
	/**
     * Indique si une balle a été tirée par le canon.
     */
	private boolean balleTiree = false;

	
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
     * Ecouteur de clavier
     */
    private int keyCode = 0;
    
    /**
     * Le canon utilisé pour tirer des balles.
     */
	
	/**
    * Indique si le monstre est mort.
    */
    private boolean monstreMort=false;
    /**
     * Indique si c'est la première fois que le contexte est utilisé ou non.
     */
    private boolean first =true;
    /**
     * Vecteur représentant la force exercée dans le sens haut-bas.
     */
    private Vecteur2D forceHautBas = new Vecteur2D(0,0);
    /**
     * Vecteur représentant la force exercée dans le sens droite-gauche.
     */
    private Vecteur2D forceDroiteGauche = new Vecteur2D(0,0);
    /**
     * Ancienne valeur associée à un certain état ou paramètre.
     */
    private boolean ancienneValeur;
    /**
     * Objet responsable de la gestion des obstacles dans le contexte.
     */
    private ObstacleHolder obHolder;

    /**
     * Vecteur représentant la somme totale des forces exercées dans le contexte.
     */
   private Vecteur2D forceTotal= new Vecteur2D(0,0);
   
   /**
	 * force de déplacement du monstre
	 */
	private int forceMonstre = 50;
   
   

    
    
	/**
	 * Constructeur de la classe. Permet de crée l'interface
	 */
	//Ahmad Khanafer
	public NiveauCustomiser() {
		
		
		 setFocusable(true);

        
		
		if(first) {
			
		
	    setLayout(null);
	    first =false;
		}
		
		ecouteurSouris();
		ecouteurClavier();
		
		
		
			
		
	}
	/**
     * Dessine les composants graphiques de la zone d'animation, y compris le canon et les obstacles.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
	//Ahmad Khanafer
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		

		   if(premiereFois) {
			
			
			pixelParMetres = (double) getWidth()/150;

			monstre = new Monstres(getWidth()- ((8*pixelParMetres)/2) -50, getHeight()/2 - ((8*pixelParMetres)/2), pixelParMetres);
			canon = new Canon(0, 10,pixelParMetres);
			
			System.out.println(monstre.getNombreDeVie()+"___");
			 premiereFois = false;
		   }
		   
		   if(obHolder!=null) {
	        	obHolder.drawContient(g2d);
	        }
		   
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
	//Ahmad Khanafer
	public void run() {
		requestFocusInWindow(); 
		while (enCoursDAnimation) {

			

			Vecteur2D anciennePosition = canon.getBalle().getPositionEnMetre();
			
			calculerUneIterationPhysique(deltaT);
			
			testerCollisionsEtAjusterVitesses();
			
			this.pcs.firePropertyChange("position", anciennePosition, canon.getBalle().getPositionEnMetre());	
			
			this.pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);



			Area areaBalle = new Area(canon.getBalle().getCercle());
	        boolean enCollisionAvecEpines = false;

	       
	        for(Obstacles ob : obHolder.getObstacleHolder()) {
	            if (ob instanceof Rectangle) {
	                Collisions.collisionRectangle(canon.getBalle(), (Rectangle) ob);
	                Collisions.collisionMonstreRec(monstre, (Rectangle) ob);
	            } else if (ob instanceof Cercle) {
	                Collisions.collisionCercle(canon.getBalle(), (Cercle) ob);
	            } else if (ob instanceof Triangle) {
	                Collisions.collisionTriangle(canon.getBalle(), (Triangle) ob);
	            } else if (ob instanceof Epines) {
	                Area areaEpines = ((Epines) ob).toAire();
	                areaEpines.intersect(areaBalle);
	                if (!areaEpines.isEmpty()) {
	                    enCollisionAvecEpines = true;
	                }
	            } else if (ob instanceof PlaqueRebondissante) {
	                Collisions.collisionPlaqueRebondissante(canon.getBalle(), (PlaqueRebondissante) ob);
	            }
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
     * Calcule une itération physique en fonction du deltaT.
     * @param deltaT Le temps écoulé depuis la dernière itération.
     */
	  //Benakmoum Walid
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
	//Ahmad Khanafer
	public void testerCollisionsEtAjusterVitesses() {	
		
        boolean state = Collisions.collisionMonstreBalle(monstre, canon.getBalle());
		
		if (state) {
			reinitialiserPosition();
		}
		
		Area areaBalle = new Area(canon.getBalle().getCercle());
		boolean enCollisionAvecEpines = false; 

		for(Obstacles ob : obHolder.getObstacleHolder()) {
		    if (ob instanceof Rectangle) {
		        Collisions.collisionRectangle(canon.getBalle(), (Rectangle) ob);
		        Collisions.collisionMonstreRec(monstre, (Rectangle) ob);
		    } else if (ob instanceof Cercle) {
		        Collisions.collisionCercle(canon.getBalle(), (Cercle) ob);
		    } else if (ob instanceof Triangle) {
		        Collisions.collisionTriangle(canon.getBalle(), (Triangle) ob);
		    } else if (ob instanceof Epines) {
		        Area areaEpines = ((Epines) ob).toAire();
		        areaEpines.intersect(areaBalle); 
		        if (!areaEpines.isEmpty()) {
		            enCollisionAvecEpines = true;
		        }
		    } else if (ob instanceof PlaqueRebondissante) {
		        Collisions.collisionPlaqueRebondissante(canon.getBalle(), (PlaqueRebondissante) ob);
		    }
		}

	
		if (enCollisionAvecEpines) {
		    System.out.println("ENCOURS ANIM");
		    ancienneValeur = enCoursDAnimation;
		    enCoursDAnimation = false;
		    pcs.firePropertyChange("enCoursDAnimation", ancienneValeur, enCoursDAnimation);
		    reinitialiserPosition();
		}
		
		
		Collisions.gererCollisionsBordures(posMurSol, posMurDroit , posMurHaut, posMurGauche, canon.getBalle());
		Collisions.collisionMonstreMur(monstre, longueurComposant, hauteurComposant);
	}

	 /**
     * Calcule les forces agissant sur les objets de la zone d'animation, telles que la gravité.

     */

// Benakmoum Walid
	private void calculerLesForces() throws Exception {
		
		//WALID BENAKMOUM TU DOIS FAIRE CETTE METHODE POUR QU ELLE PRENNE EN COMPTE TOUT LES CERCLE ELECTRIQUE
		Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
		Vecteur2D forceMonstre= forceHautBas.additionne(forceDroiteGauche);
		 forceElec=new Vecteur2D(0,0);
		
		 for (Obstacles ob : obHolder.getObstacleHolder()) {

             if (ob instanceof CercleElectrique) {
                 try {
                     Vecteur2D positionBalle = canon.getBalle().getPosCentral();
                     Vecteur2D positionCentre = ((CercleElectrique) ob).getPositionCentre();
                     double distance = positionBalle.distance(positionCentre);
                     double rayonElectrique = ((CercleElectrique) ob).getRayonElectrique();

                     if (distance < rayonElectrique) {
                         
                         Vecteur2D vecteurUnitaire = positionCentre.soustrait(positionBalle).normalise();

                        
                         double forceElectrique = K_CONST / distance; 
                         System.out.println(forceElectrique + "__---");

                      
                         Vecteur2D forceElecToAdd = vecteurUnitaire.multiplie(forceElectrique);
                         forceElec = forceElec.additionne(forceElecToAdd);
                     }
                 } catch (Exception e) {
                     System.err.println("Une erreur est survenue lors du calcul de la force électrique : " + e.getMessage());
                 }
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
	
		    enCoursDAnimation = false;

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


		   repaint();
	}
	/**
	 * Méthode qui arrête ou démarre l'animation en fonction de son état actuel.
	 */
	//Benakmoum Walid
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
	//Benakmoum Walid
	public  void TirerBalle() {
		balleTiree=true;
		canon.setBalleTiree();
		repaint();
		
	}
	
	/**
	 * Méthode pour changer la force de déplacement du monstre
	 */
	public void setForceMonstre(int force) {
		this.forceMonstre = force;
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
	enCoursDAnimation=false;
		}else {
		demarrer();
		}
	}

	public void setObHolder(ObstacleHolder obHolder) {
		this.obHolder = obHolder;
		repaint();
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
	            
	                if (canon.getBalle().getDiametre() < 200) {
	                    canon.getBalle().exploser();
	                    System.out.println(canon.getBalle().getDiametre()+"JE SUIS LE DIAMETRE");
	                    repaint();
	                  
	                } else {
	                    timer.cancel();
	                }
	            }
	        }, 0, delai);
	    }
	}

}
		
			
			

		
