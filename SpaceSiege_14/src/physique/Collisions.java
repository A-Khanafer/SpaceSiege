package physique;

import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.nio.Buffer;

import composantjeu.Balle;
import composantjeu.BalleBasique;
import composantjeu.Monstres;
import interfaces.Obstacles;
import obstacles.Cercle;
import obstacles.PlaqueRebondissante;
import obstacles.Rectangle;
import obstacles.Triangle;

/**
 * Classe qui calcule toutes les collisions de l'application ainsi que le rebond de façon physique ( sauf pour
 * la collision monstre contre les objet sinon le mode deux joueur serai tro facile)
 * @author Soudaki Zakaria
 *@author Benakmoum Walid
 */
public class Collisions {
	
	
	/**
	 * segments de tout rectangles
	 */
	private static  Line2D.Double [] segmentRec = new Line2D.Double[4];
	/**
	 * segments de tout triangle
	 */
	private static Line2D.Double [] segmentTri = new Line2D.Double[3];
	/**
	 * correcteur de rebond
	 */
	private static int buffer = 2;
	/**
	 * compteur de rebond de la balle
	 */
	private static int compteurRebonds =0;
	/**
	 * Méthode qui calcul la distance entre deux point
	 * @param x1 position x premier point
	 * @param x2 position x deuxieme point
	 * @param y1 position y premier point
	 * @param y2 posiiton y deuxieme point
	 * @return distance entre les deux point
	 * 
	 */
	//ZAKARIA SOUDAKI
	public static double distanceEntreDeuxPoints( double x1,double x2,double y1,double y2 ) {
		double distX = x2 - x1;
		double distY = y2 - y1;
		double longueur = Math.sqrt( (distX*distX) + (distY*distY) );
		
		return longueur;
		
	}
	// intersection point/cercle
	/**
	 * Méthode pour detecter la collision entre un point et un cercle (coins du rectangle et la balle)
	 * @param cx position x du centre du cercle
	 * @param x1 position x du point
	 * @param cy position y du centre du cercle
	 * @param y1 position y du point
	 * @param rayon rayon du cercle
	 * @return true ou false (true = il y a collision) (false = il n'y a pas de collision)
	 */
	//ZAKARIA SOUDAKI
	public static boolean detectionPointCercle (double cx, double x1 ,double cy ,double y1, double rayon) {
		
		double distance = distanceEntreDeuxPoints(cx,x1, cy,y1);
		
		if (distance <= rayon ) {			
			return true;
		}else {
			return false;
		}
	}
	//pour savoir si les point les plus proches sont sur la ligne
	/**
	 * Méthode qui permet de savoir si le point le plus proche de la balle sur l'axe d'un segment du carré fait 
	 * parti du segment ou non
	 * @param procheX position x du point le plus proche de la balle
	 * @param procheY position y du point le plus proche de la balle
	 * @param segX1 position x du premier point du segement
	 * @param segY1 position y du premier point du segement
	 * @param segX2 position x du deuxième point du segement
	 * @param segY2 position y du deuxième point du segement
	 * @param longueur longueur du segment 
	 * @return true le point est sur le segment , false il ne l'est pas
	 */
	//ZAKARIA SOUDAKI
	public static boolean detectionLigne( double procheX, double procheY, double segX1, double segY1, double segX2, double segY2,double longueur) {
		
		double d1 = distanceEntreDeuxPoints(segX1, procheX,segY1, procheY );
		double d2 = distanceEntreDeuxPoints(segX2, procheX,segY2, procheY );

		if( d1 + d2 >= longueur-0.01 && d1 + d2 <= longueur +0.01) {
			return true;
		}
		return false;	
	}
	//point/ligne au debut sur tout les coins
	/**
	 * Méthode qui detecte la collision entre la balle et les quatre coins du rectangle
	 * @param rec la géometrie (le rectangle)
	 * @param balle la deuxième géometrie ( la balle)
	 * @return true si la balle touche un des coins, false si elle n'en touche aucun
	 */
	//ZAKARIA SOUDAKI
	public static boolean detectionToucherCoinLigne(Line2D.Double [] segments , Balle balle) {
		
		boolean dansSeg1p1 = detectionPointCercle( balle.getPosXCentre() , segments[0].getX1() ,balle.getPosYCentre() ,segments[0].getY1() , balle.getDiametre()/2 +0.5);
		boolean dansSeg1p2 = detectionPointCercle( balle.getPosXCentre() , segments[0].getX2() ,balle.getPosYCentre() ,segments[0].getY2() , balle.getDiametre()/2 +0.5);
		
		boolean dansSeg3p1 = detectionPointCercle( balle.getPosXCentre() , segments[2].getX1() ,balle.getPosYCentre() ,segments[2].getY1() , balle.getDiametre()/2 +0.5);
		boolean dansSeg3p2 = detectionPointCercle( balle.getPosXCentre() , segments[2].getX2() ,balle.getPosYCentre() ,segments[2].getY2() , balle.getDiametre()/2 +0.5);

		if (dansSeg1p1 || dansSeg1p2 || dansSeg3p1 || dansSeg3p2) {
		 return true;
		}
	   	return false;
	}
	
	
	//calculer la longueur des segments
	/**
	 * Méthode qui calcul la longueur de tout les segments du rectangle
	 * @param rec la géometrie ( le rectangle )
	 * @return tableau contenant les longueurs [0]= segment1  [1]= segment2  [2]= segment3  [3]= segment4
	 */
	//ZAKARIA SOUDAKI
    public static double [] longueurDesSegments(Line2D.Double [] segments ){
    
    	double[] longueursSegments = new double[4];
    	for (int i = 0; i < segments.length; i++) {
    	    Line2D.Double segment = segments[i]; // Supposant que l'index commence à 1
    	    longueursSegments[i] = distanceEntreDeuxPoints(segment.x2, segment.x1, segment.y2, segment.y1);
    	}
		return longueursSegments;
    }
    //calcul du Dot
    /**
     * Méthode qui calcul le facteur dot pour le calcul du point le plus proche de la balle
     * @param rec rec la géometrie ( le rectangle )
     * @param balle la deuxième géometrie ( la balle)
     * @param longueurs Segments tableau des longueurs des segments du rectangle
     * @return tableau contenant le facteur dot de chaque segment   [0]= dotsegment1  [1]= dotsegment2  [2]= dotsegment3  [3]= dotsegment4
     */
  //ZAKARIA SOUDAKI
    public static double [] calculDot( Line2D.Double [] segments, Balle balle, double [] longueursSegments) {
		
    	double[] dot = new double[segments.length];
    	
    	for (int i = 0; i < segments.length; i++) {
    		
    		Line2D.Double segment = segments[i];
    		
    	     dot[i] = (
    	        (((balle.getPosXCentre() - segment.getX1()) * (segment.getX2() - segment.getX1())) +
    	        ((balle.getPosYCentre() - segment.getY1()) * (segment.getY2() - segment.getY1()))
    	    )) / Math.pow(longueursSegments[i], 2);
    	}
    	return dot;
    }
    /**
     * Méthode pour calculer la vitesse de la balle après la collision contre un des segements
     * @param segment le segement contre lequel elle fait la collison
     * @param balle la géometrie ( la balle)
     * @param etat tableau de boolean pour connaitre l'état de tout les segments
**/
  //ZAKARIA SOUDAKI
    private static void calculRebondPhysique(Line2D.Double segment, Balle balle) {
   
    	double dx = segment.getX2() - segment.getX1();
 	    double dy = segment.getY2() - segment.getY1();

 	    double nx = -dy; 
 	    double ny = dx;
 	    
 	    Vecteur2D normale = new Vecteur2D(nx, ny);
 	    try {
			normale = normale.normalise();
		} catch (Exception e) {
			e.printStackTrace();
		} 	    
 	    double produitScalaire = balle.getVitesse().prodScalaire(normale);
 	    Vecteur2D vitesseApresCollision = balle.getVitesse().soustrait((normale.multiplie(produitScalaire)).multiplie(2));

 	    if(balle.quelleTypeBalle()==2) {
 	    	  balle.setVitesse(vitesseApresCollision.additionne(new Vecteur2D(10,10)));	
 	    }else {

 	    balle.setVitesse(vitesseApresCollision);

 	    
 	    }
 	   compteurRebonds++;
    }
    /**
     * Méthode pour calculer la vitesse de la balle après la collision contre un des segments
     * @param segment les segments de l'objets
     * @param balle  La balle
     */
    //Benakmoum Walid
    private static void calculRebondPhysiquePlaque(Line2D.Double segment, Balle balle) {
    	   
    	double dx = segment.getX2() - segment.getX1();
 	    double dy = segment.getY2() - segment.getY1();

 	    double nx = -dy; 
 	    double ny = dx;
 	    
 	    Vecteur2D normale = new Vecteur2D(nx, ny);
 	    try {
			normale = normale.normalise();
		} catch (Exception e) {
			e.printStackTrace();
		} 	    
 	    double produitScalaire = balle.getVitesse().prodScalaire(normale);
 	    Vecteur2D vitesseApresCollision = balle.getVitesse().soustrait((normale.multiplie(produitScalaire)).multiplie(2));

 	  

 	    balle.setVitesse(vitesseApresCollision.additionne(new Vecteur2D(10,10)));
 	   
        compteurRebonds++;
 	    
 	    
    }

     //calcul rebond sur un coin rectangle
    /**
     * Méthode qui calcul la vitesse de la balle après la collision contre un des coins
     * @param balle la géometrie ( la balle)
     */
  //ZAKARIA SOUDAKI
     public static void calculRebondCoin (Balle balle) {

    	 if(balle.quelleTypeBalle()==2) {
	    	  balle.setVitesse(balle.getVitesse().additionne(new Vecteur2D(10,10)));	
	    }
    	 
	    balle.setVitesse( new Vecteur2D ( -balle.getVitesse().getX(), -balle.getVitesse().getY() ));
	    compteurRebonds++;
     }
    /**
     * Méthode principale qui detecte la collision entre la balle et le rectangle et ajuste la vitesse de la balle après la collision
     * @param balle la géometrie ( la balle)
     * @param rec rec la géometrie ( le rectangle )
     */
   //ZAKARIA SOUDAKI

	 public static void collisionRectangle(Balle balle, Rectangle rec) {
		 
		
		 for (int i = 0; i < segmentRec.length; i++) {
	    		
	    		segmentRec[i] = rec.getSegment(i+1);
		 }
		 
		    boolean toucherCoinsLigne = detectionToucherCoinLigne(segmentRec,balle);
		   
		    if (toucherCoinsLigne) {
		    	calculRebondCoin(balle);
		     }else if (!toucherCoinsLigne) {

       		double [] longueur = longueurDesSegments(segmentRec );
			double [] dot = calculDot(segmentRec , balle , longueur);
		    double [] xProcheSegments = new double[4];
		    double [] yProcheSegments = new double[4];

		    for (int i = 0; i < segmentRec.length; i++) {
		    	
		        Line2D.Double segment = segmentRec[i];
		  
		        double dotCopy = dot[i]; 
		        double xProche = segment.getX1() + (dotCopy * (segment.getX2() - segment.getX1()));
		        double yProche = segment.getY1() + (dotCopy * (segment.getY2() - segment.getY1()));
		        
		        xProcheSegments[i]= xProche;
		        yProcheSegments[i]= yProche;
		        

		    }
		    
		    boolean[] seg = new boolean[4]; 

		    for (int i = 0; i < segmentRec.length; i++) {
		        Line2D.Double segment = segmentRec[i];
		        boolean distanceSegBalle = detectionPointCercle(balle.getPosXCentre(),xProcheSegments[i] ,balle.getPosYCentre(), yProcheSegments[i], balle.getDiametre()/2+buffer);
		
		        if (distanceSegBalle) {
		            seg[i] = true; 
		            
		            
		            boolean surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i], segment.getX1(), segment.getY1(), segment.getX2(), segment.getY2(), longueur[i]);
		            
		            if (!surSegment) {
		                seg[i] = false; 
		            }
		        }
		    }
		    if (seg[0] == true) {
		    	
		        calculRebondPhysique(segmentRec[0], balle);
		        System.out.println("SEG 0;");
		        seg[0] = false;
		       
		    }  
		    if (seg[1] == true) {

		        calculRebondPhysique(segmentRec[1], balle);
		        System.out.println("SEG 1;");
		        seg[1] = false;
		    } 
		    if (seg[2] == true) {

		        calculRebondPhysique(segmentRec[2], balle);
		        System.out.println("SEG 2;");
		        seg[2] = false;
		    } 
		    if (seg[3] == true) {

		        calculRebondPhysique(segmentRec[3], balle);
		        System.out.println("SEG 3;");
		        seg[3] = false;
		        
		    } 
		 }
	}
	 /**
	     * Méthode principale qui detecte la collision entre la balle et une plaqueRebondissante et ajuste la vitesse de la balle après la collision
	     * @param balle la géometrie ( la balle)
	     * @param rec rec la géometrie ( le rectangle )
	     */
	    //Benakmoum Walid

	 public static void collisionPlaqueRebondissante(Balle balle, PlaqueRebondissante plaque) {
		 
			
		 for (int i = 0; i < segmentRec.length; i++) {
	    		
	    		segmentRec[i] = plaque.getSegment(i+1);
		 }
		 
		    boolean toucherCoinsLigne = detectionToucherCoinLigne(segmentRec,balle);
		   
		    if (toucherCoinsLigne) {
		    	calculRebondCoin(balle);
		     }else if (!toucherCoinsLigne) {

       		double [] longueur = longueurDesSegments(segmentRec );
			double [] dot = calculDot(segmentRec , balle , longueur);
		    double [] xProcheSegments = new double[4];
		    double [] yProcheSegments = new double[4];

		    for (int i = 0; i < segmentRec.length; i++) {
		    	
		        Line2D.Double segment = segmentRec[i];
		  
		        double dotCopy = dot[i]; 
		        double xProche = segment.getX1() + (dotCopy * (segment.getX2() - segment.getX1()));
		        double yProche = segment.getY1() + (dotCopy * (segment.getY2() - segment.getY1()));
		        
		        xProcheSegments[i]= xProche;
		        yProcheSegments[i]= yProche;
		        

		    }
		    
		    boolean[] seg = new boolean[4]; 

		    for (int i = 0; i < segmentRec.length; i++) {
		        Line2D.Double segment = segmentRec[i];
		        boolean distanceSegBalle = detectionPointCercle(balle.getPosXCentre(),xProcheSegments[i] ,balle.getPosYCentre(), yProcheSegments[i], balle.getDiametre()/2+buffer);
		
		        if (distanceSegBalle) {
		            seg[i] = true; 
		            
		            
		            boolean surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i], segment.getX1(), segment.getY1(), segment.getX2(), segment.getY2(), longueur[i]);
		            
		            if (!surSegment) {
		                seg[i] = false; 
		            }
		        }
		    }
		    if (seg[0] == true) {
		    	
		    	calculRebondPhysiquePlaque(segmentRec[0], balle);
		        System.out.println("SEG 0;");
		        seg[0] = false;
		       
		    }  
		    if (seg[1] == true) {

		    	calculRebondPhysiquePlaque(segmentRec[1], balle);

		        System.out.println("SEG 1;");
		        seg[1] = false;
		    } 
		    if (seg[2] == true) {

		    	calculRebondPhysiquePlaque(segmentRec[2], balle);
		     
		        System.out.println("SEG 2;");
		        seg[2] = false;
		    } 
		    if (seg[3] == true) {

		    	calculRebondPhysiquePlaque(segmentRec[3], balle);
		       
		        System.out.println("SEG 3;");
		        seg[3] = false;
		        
		    } 
		 }
	}

	 /**
	  * méthode qui calcul la détection de la collision balle-triangle et calcul le rebond
	  * @param balle la balle
	  * @param tri le triangle
	  */

	 public static void collisionTriangle(Balle balle, Triangle tri) {
		 
		 for (int i = 0; i < segmentTri.length; i++) {
	    		segmentTri[i] = tri.getSegment(i+1);
		 }
		 
		    boolean toucherCoinsLigne = detectionToucherCoinLigne(segmentTri,balle);
		   System.out.println(toucherCoinsLigne);
		    if (toucherCoinsLigne) {
		    	calculRebondCoin(balle);
		     }else if (!toucherCoinsLigne) {

    		double [] longueur = longueurDesSegments(segmentTri );
			double [] dot = calculDot(segmentTri , balle , longueur);
		    double [] xProcheSegments = new double[3];
		    double [] yProcheSegments = new double[3];

		    for (int i = 0; i < segmentTri.length; i++) {
		    	
		        Line2D.Double segment = segmentTri[i];
		  
		        double dotCopy = dot[i]; 
		        double xProche = segment.getX1() + (dotCopy * (segment.getX2() - segment.getX1()));
		        double yProche = segment.getY1() + (dotCopy * (segment.getY2() - segment.getY1()));
		        
		        xProcheSegments[i]= xProche;
		        yProcheSegments[i]= yProche;
		        

		    }
		    
		    boolean[] seg = new boolean[3]; 

		    for (int i = 0; i < segmentTri.length; i++) {
		        Line2D.Double segment = segmentTri[i];
		        boolean distanceSegBalle = detectionPointCercle(balle.getPosXCentre(),xProcheSegments[i] ,balle.getPosYCentre(), yProcheSegments[i], balle.getDiametre()/2+buffer);
				
		        if (distanceSegBalle) {
		            seg[i] = true; 
		            
		            
		            
		            boolean surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i], segment.getX1(), segment.getY1(), segment.getX2(), segment.getY2(), longueur[i]);
		            
		            if (!surSegment) {
		                seg[i] = false; 
		            }
		        }
		    }
		    if (seg[0] == true) {
		        calculRebondPhysique(segmentTri[0], balle);
		        seg[0] = false;
		    } 
		    if (seg[1] == true) {
		        calculRebondPhysique(segmentTri[1], balle);
		        seg[1] = false;
		    } 
		    if (seg[2] == true) {
		        calculRebondPhysique(segmentTri[2], balle);
		        seg[2] = false;
		    } 

		 }
		 
	 }
	 
	 /**
	  * méthode qui détecte et calcul le rebond de la collision balle - cercle et ajuste la vitesse de la balle en conséquence
	  * @param balle la balle
	  * @param cercle le cercle
	  */
	//ZAKARIA SOUDAKI
	 public static void collisionCercle(Balle balle, Cercle cercle) {
		 
		double RayBalle = balle.getRayon();
		double RayCercle = cercle.getRayon();
		
		Vecteur2D pos = cercle.getPositionCentre();
		
		Point centreBalle = new Point ((int) balle.getPosXCentre(),(int) balle.getPosYCentre());
		Point centreCercle = new Point ((int) pos.getX(), (int) pos.getY());
 
		double distance = distanceEntreDeuxPoints(centreBalle.x, centreCercle.x, centreBalle.y, centreCercle.y);
		
		if (distance <= RayBalle+ RayCercle) {
			
			double dx =  centreBalle.x - centreCercle.x;
		    double dy =  centreBalle.y - centreCercle.y;
			Vecteur2D vecNormal = (new Vecteur2D(dx, dy));
			try {
				vecNormal = vecNormal.normalise();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
			double produitScalaire = balle.getVitesse().prodScalaire(vecNormal);
	 	    Vecteur2D vitesseApresCollision = balle.getVitesse().soustrait((vecNormal.multiplie(produitScalaire)).multiplie(2));
	 	    balle.setVitesse(vitesseApresCollision);
	 	   compteurRebonds++;
		}
		
		

	 }
	 /**
	  * méthode qui détecte quand le monstre est toucher par la balle
	  * @param monstre le monstre
	  * @param balle la balle
	  * @return
	  */
	//ZAKARIA SOUDAKI
	 public static boolean collisionMonstreBalle(Monstres monstre , Balle balle) {
		 
		 Area areaBalle = new Area(balle.getCercle()); 
		 Area areaMonstre = monstre.toAire();
	        areaBalle.intersect(areaMonstre);

	        if (!areaBalle.isEmpty()) {
	        	monstre.perdUneVie();
	        	return true;
	        }
	        
	        return false;
		 
	 }
	 /**
	  * méthode qui calcul la collision du monstre contre un obstacle de type rectangle
	  * @param monstre le monstre
	  * @param rec le rectangle
	  */
	 public static void collisionMonstreRec (Monstres monstre,  Rectangle rec ) {
		 Vecteur2D vec = null;
		 Area areaRec = rec.toAire();
		 Area areaMonstre = monstre.toAire();
	        areaMonstre.intersect(areaRec);

	        if (!areaMonstre.isEmpty()) {
	        	try {
					 vec = monstre.getVitesse().normalise();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	

	        	monstre.setVitesse(new Vecteur2D(-monstre.getVitesse().getX(),-monstre.getVitesse().getY()));
	        }
	 }
	 
	 /**
	  * 	  méthode qui calcul la collision du monstre contre un obstacle de type triangle

	  * @param monstre le monstre
	  * @param tri le triangle
	  */
     public static void collisionMonstreTri (Monstres monstre, Triangle tri) {
    	 Area areaRec = tri.toAire();
    	 Area areaMonstre = monstre.toAire();
	        areaMonstre.intersect(areaRec);

	        if (!areaMonstre.isEmpty()) {
	        	monstre.setVitesse(new Vecteur2D(-monstre.getVitesse().getX(),-monstre.getVitesse().getY()));
	        }
		 
	 }
     /**
      * méthode qui calcul la collision du monstre contre un obstacle de type cerlce

      * @param monstre le monstre
      * @param cercle le cercle
      */
     //ZAKARIA SOUDAKI
     
     public static void collisionMonstreCercle (Monstres monstre, Cercle cercle) {
    	 Area areaRec = cercle.toAire();
    	 Area areaMonstre = monstre.toAire();
	        areaMonstre.intersect(areaRec);

	        if (!areaMonstre.isEmpty()) {
	        	monstre.setVitesse(new Vecteur2D(-monstre.getVitesse().getX(),-monstre.getVitesse().getY()));
	        }
	 }
	 
	 /**
	  * méthode pour obtenir le nombre de rebond de la balle
	  * @return nombre de rebonds
	  */
     //ZAKARIA SOUDAKI
	 public static int getNbRebond() {
		 return compteurRebonds;
	 }
	 /**
	  * méthode pour changer le nombre de rebonds
	  * @param nb nombre de rebonds désirer
	  */
// ZAKARIA SOUDAKI
	 public  static void setNbrebond(int nb) {
         compteurRebonds = nb;
	 }
	 
	 /**
	  * Méthode qui calcul la collision de la balle contre une des paroies de son interface et qui ajuste sa vitesse
	  * @param posSol position sol
	  * @param posMurDroit position mur droit
	  * @param posMurHaut position mur haut
	  * @param posMurGauche position mur de gauche
	  * @param balle la balle
	  */
	 //ZAKARIA SOUDAKI 
	  public static void gererCollisionsBordures(double posSol, double posMurDroit, double posMurHaut, double posMurGauche, Balle balle) {
	    	if ( (balle.getPosition().getY() + balle.getDiametre()) >= ( posSol ) ) {
	    		
	    		if(balle.quelleTypeBalle()==2) {
	  	    	  balle.setVitesse(balle.getVitesse().additionne(new Vecteur2D(10,10)));	
	  	    }
	    		
	    		balle.getVitesse().setY(-balle.getVitesse().getY());
	    		balle.getPosition().setY(posSol-balle.getDiametre());
	    		 compteurRebonds++;
	    		 
	    	}
	    	
	    	
	    	if ( (balle.getPosition().getX() + balle.getDiametre()) >= ( posMurDroit ) ) {
	    		
	    		if(balle.quelleTypeBalle()==2) {
	  	    	  balle.setVitesse(balle.getVitesse().additionne(new Vecteur2D(10,10)));	
	  	    }
	    		
	    		balle.getVitesse().setX(-balle.getVitesse().getX());
	    		balle.getPosition().setX(posMurDroit-balle.getDiametre());
	    		 compteurRebonds++;
	    	}
	    	if ( (balle.getPosition().getY()) <= ( posMurHaut ) ) {
	    		
	    		if(balle.quelleTypeBalle()==2) {
	  	    	  balle.setVitesse(balle.getVitesse().additionne(new Vecteur2D(10,10)));	
	  	    }
	    		
	    		balle.getVitesse().setY(-balle.getVitesse().getY());
	    		balle.getPosition().setY(posMurHaut);
	    		 compteurRebonds++;
	    	}
	    	if ( (balle.getPosition().getX()) <= ( posMurGauche ) ) {
	    		
	    		if(balle.quelleTypeBalle()==2) {
	  	    	  balle.setVitesse(balle.getVitesse().additionne(new Vecteur2D(10,10)));	
	  	    }
	    		
	    		balle.getVitesse().setX(-balle.getVitesse().getX());
	    		balle.getPosition().setX(posMurGauche);
	    		 compteurRebonds++;
	    	}
	    	
	    	
	    }
	  
	  /**
	   * méthode pour empêcher le monstre de sortir de son interface et annuler sa vitesse lorsqu'il s'apprète a sortir
	   * @param monstre le monstre
	   * @param longueur la longueur de l'interface de jeu
	   * @param hauteur La hauteur de l'interface de jeu
	   */
	  //ZAKARIA SOUDAKI
	  public static void collisionMonstreMur(Monstres monstre ,int longueur, int hauteur) {
			if (  monstre.getPosition().getX() + monstre.getLongueurRectangle() >= longueur ) {
				monstre.setVitesse(new Vecteur2D(0,0));
				monstre.setPosition(new Vecteur2D(longueur -monstre.getLongueurRectangle() -2,monstre.getPosition().getY()));
				
			}
			if(monstre.getPosition().getY() + monstre.getHauteurRectangle() >= hauteur) {
				 monstre.setVitesse(new Vecteur2D(0,0));
				monstre.setPosition(new Vecteur2D(monstre.getPosition().getX() , hauteur -monstre.getHauteurRectangle() -2));

			}
			if ( monstre.getPosition().getX() <= 0  )	{
				monstre.setVitesse(new Vecteur2D(0,0));
				monstre.setPosition(new Vecteur2D( 2,monstre.getPosition().getY()));

			}
			if(monstre.getPosition().getY() <= 0) {
				monstre.setVitesse(new Vecteur2D(0,0));
				monstre.setPosition(new Vecteur2D( monstre.getPosition().getX(),2 ));

			}
		}
}


