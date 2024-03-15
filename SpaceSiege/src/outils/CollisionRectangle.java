package outils;

import java.awt.geom.Line2D;

import balle.BalleBasique;
import obstacles.Rectangle;
import physique.Vecteur2D;
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
		
		    double minX = Math.min(segX1, segX2);
		    double maxX = Math.max(segX1, segX2);
		    double minY = Math.min(segY1, segY2);
		    double maxY = Math.max(segY1, segY2);
		    return  procheX >= minX && procheX <= maxX && procheY >= minY && procheY <= maxY;
	
//		double d1 = distanceEntreDeuxPoints(procheX, segX1, procheY, segY1);
//		double d2 = distanceEntreDeuxPoints(procheX, segX2, procheY, segY2);
//		if(d1+d2 >=  longueur -0.1 && d1+d2 <= longueur + 0.1 ) {
//			return true;
//		}
//            return false;
		    
		
		
		
	}
	
	
	//point/ligne au debut les coins
	public static boolean detectionPointLigne1(Rectangle rec, Balle balle) {
		
		boolean dansSeg1p1 = detectionPointCercle(rec.getSegment(1).x1,rec.getSegment(1).y1, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		boolean dansSeg1p2 = detectionPointCercle(rec.getSegment(1).x2,rec.getSegment(1).y2, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		
		boolean dansSeg2p1 = detectionPointCercle(rec.getSegment(3).x1,rec.getSegment(3).y1, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		boolean dansSeg2p2 = detectionPointCercle(rec.getSegment(3).x2,rec.getSegment(3).y2,balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);


	

		if (dansSeg1p1 || dansSeg1p2 || dansSeg2p1 || dansSeg2p2) {
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
    
	public static boolean detectionCollisionBalleLigne(BalleBasique balle, Rectangle rec) {
	        
		
		
		    boolean extrimiteLigne = detectionPointLigne1(rec,balle);
		   
		    if (extrimiteLigne) {
		    	 return true;
		     }

       		double [] longueur = longueurDesSegments(rec );
			double [] dot = calculDot(rec , balle , longueur);
		    double [] xProcheSegments = new double[4];
		    double [] yProcheSegments = new double[4];

		    for (int i = 0; i < 4; i++) {
		    	
		        Line2D.Double segment = rec.getSegment(i + 1);
		  
		        double dotCopy = dot[i]; 
		        double xProche = segment.x1 + (dotCopy * (segment.x2 - segment.x1));
		        double yProche = segment.y1 + (dotCopy * (segment.y2 - segment.y1));
		        
		        xProcheSegments[i]= xProche;
		        yProcheSegments[i]= yProche;
		        
//		        boolean surSegment;
//		        surSegment = detectionLigne(xProche, yProche,segment.x1,segment.y1,segment.x2,segment.y2, longueur[i]);
//		        
//		        if (!surSegment) {
//		        	return false;
//		        }
		    }
		    
		    double distanceSeg1Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[0],balle.getPosYCentre(),yProcheSegments[0]);
		    double distanceSeg2Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[1],balle.getPosYCentre(),yProcheSegments[1]);
		    double distanceSeg3Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[2],balle.getPosYCentre(),yProcheSegments[2]);
		    double distanceSeg4Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[3],balle.getPosYCentre(),yProcheSegments[3]);
		    
		    System.out.println("OOOOOOOOOOOOOOOOO"+ distanceSeg1Balle+"OOOOOOOOOOO"+distanceSeg2Balle+"OOOOOOOOOOOOOOO"+distanceSeg3Balle+"OOOOOOOOOOOOOO"+distanceSeg4Balle);
		    
		  {
		    	
		    	
		    }
		    
		    if(distanceSeg1Balle <= balle.getDiametre()/2 || distanceSeg2Balle <= balle.getDiametre()/2 || distanceSeg3Balle <= balle.getDiametre()/2 
		    		||distanceSeg4Balle <= balle.getDiametre()/2) {

		    	
//		    	  for (int i = 0; i < 4; i++) {
//				    	
//				        Line2D.Double segment = rec.getSegment(i + 1);
//				        boolean surSegment= false;
//				        surSegment = detectionLigne(xProcheSegments[i], yProcheSegments[i],segment.x1,segment.y1,segment.x2,segment.y2, longueur[i]);
//				        
//				        if (surSegment) {
//				        	return true;
//				        }
//		    }
		    	return true;
		    }
		    return false;
	}
}

