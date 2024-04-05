package outils;

import java.awt.geom.Line2D;

import composantjeu.Balle;
import composantjeu.BalleBasique;
import obstacles.Rectangle;
import physique.Vecteur2D;

/**
 * 
 * @author Soudaki Zakaria
 *
 */
public class CollisionRectangle {
	
	/**
	 * Méthode qui calcul la distance entre deux point
	 * @param x1 position x premier point
	 * @param x2 position x deuxieme point
	 * @param y1 position y premier point
	 * @param y2 posiiton y deuxieme point
	 * @return distance entre les deux point
	 * 
	 */
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
	public static boolean detectionToucherCoinLigne(Rectangle rec, Balle balle) {
		
		boolean dansSeg1p1 = detectionPointCercle( balle.getPosXCentre() , rec.getSegment(1).getX1() ,balle.getPosYCentre() ,rec.getSegment(1).getY1() , balle.getDiametre()/2 );
		boolean dansSeg1p2 = detectionPointCercle( balle.getPosXCentre() , rec.getSegment(1).getX2() ,balle.getPosYCentre() ,rec.getSegment(1).getY2() , balle.getDiametre()/2 );
		
		boolean dansSeg3p1 = detectionPointCercle( balle.getPosXCentre() , rec.getSegment(3).getX1() ,balle.getPosYCentre() ,rec.getSegment(3).getY1() , balle.getDiametre()/2 );
		boolean dansSeg3p2 = detectionPointCercle( balle.getPosXCentre() , rec.getSegment(3).getX2() ,balle.getPosYCentre() ,rec.getSegment(3).getY2() , balle.getDiametre()/2 );

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
    public static double [] longueurDesSegments(Rectangle rec ){
    
    	double[] longueursSegments = new double[4];
    	for (int i = 0; i < 4; i++) {
    	    Line2D.Double segment = rec.getSegment(i+1); // Supposant que l'index commence à 1
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
    public static double [] calculDot( Rectangle rec, Balle balle, double [] longueursSegments) {
		
    	double[] dot = new double[4];
    	
    	for (int i = 0; i < 4; i++) {
    		
    		Line2D.Double segment = rec.getSegment(i+1);
    		
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
     */

    private static void calculRebondPhysique(Line2D.Double segment, Balle balle, boolean [] etat) {
   
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
 	    balle.setVitesse(vitesseApresCollision);
    }

     //calcul rebond sur un coin rectangle
    /**
     * Méthode qui calcul la vitesse de la balle après la collision contre un des coins
     * @param balle la géometrie ( la balle)
     */
     public static void calculRebondCoin (Balle balle) {

	    balle.setVitesse( new Vecteur2D ( -balle.getVitesse().getX(), -balle.getVitesse().getY() ));
}
    /**
     * Méthode principale qui detecte la collision entre la balle et le rectangle et ajuste la vitesse de la balle après la collision
     * @param balle la géometrie ( la balle)
     * @param rec rec la géometrie ( le rectangle )
     */

	 public static void detectionCollisionRectangle(Balle balle, Rectangle rec) {

	        
		    boolean toucherCoinsLigne = detectionToucherCoinLigne(rec,balle);
		   
		    if (toucherCoinsLigne) {
		    	calculRebondCoin(balle);
		     }else if (!toucherCoinsLigne) {

       		double [] longueur = longueurDesSegments(rec );
			double [] dot = calculDot(rec , balle , longueur);
		    double [] xProcheSegments = new double[4];
		    double [] yProcheSegments = new double[4];

		    for (int i = 0; i < 4; i++) {
		    	
		        Line2D.Double segment = rec.getSegment(i + 1);
		  
		        double dotCopy = dot[i]; 
		        double xProche = segment.getX1() + (dotCopy * (segment.getX2() - segment.getX1()));
		        double yProche = segment.getY1() + (dotCopy * (segment.getY2() - segment.getY1()));
		        
		        xProcheSegments[i]= xProche;
		        yProcheSegments[i]= yProche;
		        

		    }
		    
		    
		    boolean[] seg = new boolean[4]; 

		    for (int i = 0; i < 4; i++) {
		        Line2D.Double segment = rec.getSegment(i + 1);
		        double distanceSegBalle = distanceEntreDeuxPoints(balle.getPosXCentre(), xProcheSegments[i], balle.getPosYCentre(), yProcheSegments[i]) - balle.getDiametre()/2;
		        
		        if (distanceSegBalle <= balle.getDiametre()/8) {
		            seg[i] = true; 
		            
		            
		            boolean surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i], segment.getX1(), segment.getY1(), segment.getX2(), segment.getY2(), longueur[i]);
		            
		            if (!surSegment) {
		                seg[i] = false; 
		            }
		        }
		    }
		    
		    if (seg[0] == true) {
		        System.out.println("Le segment 1 est vrai");
		        calculRebondPhysique(rec.getSegment(1), balle, seg);
		        seg[0] = false;
		    } 
		    if (seg[1] == true) {
		        System.out.println("Le segment 2 est vrai");
		        calculRebondPhysique(rec.getSegment(2), balle, seg);
		        seg[1] = false;
		    } 
		    if (seg[2] == true) {
		        System.out.println("Le segment 3 est vrai");
		        calculRebondPhysique(rec.getSegment(3), balle, seg);
		        seg[2] = false;
		    } 
		    if (seg[3] == true) {
		        System.out.println("Le segment 4 est vrai");
		        calculRebondPhysique(rec.getSegment(4), balle, seg);
		        seg[3] = false;
		        
		    }
		    
		 }
	}
	 
	 
}

