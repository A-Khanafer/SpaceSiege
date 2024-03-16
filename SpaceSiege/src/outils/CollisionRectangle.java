package outils;

import java.awt.geom.Line2D;

import balle.BalleBasique;
import obstacles.Rectangle;
import balle.Balle;


public class CollisionRectangle {
	
	
	public static double distanceEntreDeuxPoints( double x1,double x2,double y1,double y2 ) {
		double distX = x2 - x1;
		double distY = y2 - y1;
		double longueur = Math.sqrt( (distX*distX) + (distY*distY) );
		
		return longueur;
		
	}
	// intersection point/cercle
	public static boolean detectionPointCercle (double cx, double x1 ,double cy ,double y1, double rayon) {
		
		double distance = distanceEntreDeuxPoints(cx,x1, cy,y1);
		
		if (distance <= rayon ) {			
			return true;
		}else {
			return false;
		}
	}
	//pour savoir si les point les plus proches sont sur la ligne
	public static boolean detectionLigne( double procheX, double procheY, double segX1, double segY1, double segX2, double segY2,double longueur) {
		
		double d1 = distanceEntreDeuxPoints(segX1, procheX,segY1, procheY );
		double d2 = distanceEntreDeuxPoints(segX2, procheX,segY2, procheY );

		if( d1 + d2 >= longueur-0.01 && d1 + d2 <= longueur +0.01) {
			return true;
		}
		return false;
		
	}
	
	
	//point/ligne au debut les coins
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
    public static double [] longueurDesSegments(Rectangle rec ){
    
    	double[] longueursSegments = new double[4];
    	for (int i = 0; i < 4; i++) {
    	    Line2D.Double segment = rec.getSegment(i+1); // Supposant que l'index commence à 1
    	    longueursSegments[i] = distanceEntreDeuxPoints(segment.x2, segment.x1, segment.y2, segment.y1);
    	}
		return longueursSegments;
    }
    
    //calcul du Dot
    public static double [] calculDot( Rectangle rec, BalleBasique balle, double [] longueursSegments) {
		
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
    
    //calcul rebond Physique
    private static void calculRebondPhysique() {
    	
    }
    
	public static boolean detectionCollisionBalleLigne(BalleBasique balle, Rectangle rec) {
	        
		    boolean toucherCoinsLigne = detectionToucherCoinLigne(rec,balle);
		   
		    if (toucherCoinsLigne) {
		    	 return true;
		     }

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
		    
		    //System.out.println("......."+ distanceSeg1Balle+"......."+distanceSeg2Balle+"......."+distanceSeg3Balle+"......."+distanceSeg4Balle);
		    
		    boolean[] seg = new boolean[4]; 

		    for (int i = 0; i < 4; i++) {
		        Line2D.Double segment = rec.getSegment(i + 1);
		        double distanceSegBalle = distanceEntreDeuxPoints(balle.getPosXCentre(), xProcheSegments[i], balle.getPosYCentre(), yProcheSegments[i]) - balle.getDiametre()/2;
		        
		        if (distanceSegBalle <= 0) {
		            seg[i] = true; 
		            
		            boolean surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i], segment.getX1(), segment.getY1(), segment.getX2(), segment.getY2(), longueur[i]);
		            
		            if (!surSegment) {
		                seg[i] = false; 
		            }
		        }
		    }
		    
		  	
	
		  if( seg[0] || seg[1] || seg[2] || seg[3] == true) {
			  return true;
		  }else {
			  return false;
		  }
	}
}

