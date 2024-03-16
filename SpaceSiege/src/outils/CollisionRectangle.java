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
		}
			return false;
		
	}
	//pour savoir si les point les plus proches sont sur la ligne
	public static boolean detectionLigne( double procheX, double procheY, double segX1, double segY1, double segX2, double segY2,double longueur) {
//		boolean boo = false;
//		    double minX = Math.min(segX1, segX2);
//		    double maxX = Math.max(segX1, segX2);
//		    double minY = Math.min(segY1, segY2);
//		    double maxY = Math.max(segY1, segY2);
//		if(procheX >= minX-0.5 && procheX <= maxX+0.5 && procheY >= minY-0.5 && procheY <= maxY+0.5) {
//			boo=true;
//		}
//		    return boo;
		
		double d1 = distanceEntreDeuxPoints(segX1, procheX,segY1, procheY );
		double d2 = distanceEntreDeuxPoints(segX2, procheX,segY2, procheY );

		System.out.println(d1+"  AAAAAAAAAAAAAAAAAAAAAAAAAAAAA  "+d2);
		if( d1 + d2 >= longueur-0.01 && d1 + d2 <= longueur +0.01) {
			return true;
		}
		return false;
		
	}
	
	
	//point/ligne au debut les coins
	public static boolean detectionPointLigne1(Rectangle rec, Balle balle) {
		
		boolean dansSeg1p1 = detectionPointCercle(rec.getSegment(1).x1,rec.getSegment(1).y1, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		boolean dansSeg1p2 = detectionPointCercle(rec.getSegment(1).x2,rec.getSegment(1).y2, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		
		boolean dansSeg3p1 = detectionPointCercle(rec.getSegment(3).x1,rec.getSegment(3).y1, balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		boolean dansSeg3p2 = detectionPointCercle(rec.getSegment(3).x2,rec.getSegment(3).y2,balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);

		
		



		if (dansSeg1p1 || dansSeg1p2 || dansSeg3p1 || dansSeg3p2) {
		 return true;
		}
	   	return false;
	}
	//calculer la longueur des segments
    public static double [] longueurDesSegments(Rectangle rec ){
    
    	double[] longueursSegments = new double[4];
    	for (int i = 0; i < 4; i++) {
    	    Line2D.Double segment = rec.getSegment(i+1); // Supposant que l'index commence Ã  1
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
	        
		
		boolean seg1 = false;
		boolean seg2 = false;
		boolean seg3 = false;
		boolean seg4 = false;

		
		    boolean extrimiteLigne = detectionPointLigne1(rec,balle);
		   
		    if (extrimiteLigne) {
		    	System.out.println("ARRETER BING BANG BONG");
		    	 return true;
		     }

       		double [] longueur = longueurDesSegments(rec);
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
		    
		    double distanceSeg1Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[0],balle.getPosYCentre(),yProcheSegments[0]);
		    double distanceSeg2Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[1],balle.getPosYCentre(),yProcheSegments[1]);
		    double distanceSeg3Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[2],balle.getPosYCentre(),yProcheSegments[2]);
		    double distanceSeg4Balle = distanceEntreDeuxPoints(balle.getPosXCentre(),xProcheSegments[3],balle.getPosYCentre(),yProcheSegments[3]);
		    
		    System.out.println("......."+ distanceSeg1Balle+"......."+distanceSeg2Balle+"......."+distanceSeg3Balle+"......."+distanceSeg4Balle);

//		    for (int i = 0; i<4 ;i++) {
		    	

	    	 Line2D.Double segment1 = rec.getSegment(1);
	    	 Line2D.Double segment2 = rec.getSegment(2);
	    	 Line2D.Double segment3 = rec.getSegment(3);
		     Line2D.Double segment4 = rec.getSegment(4);

		    	
//		    	 boolean surSegment1 = detectionLigne(xProcheSegments[0],yProcheSegments[0],segment1.getX1(),segment1.getY1(), segment1.getX2(),segment1.getY2(),longueur[0] ); 
//				    System.out.println("................"+surSegment1);

//				    if (!surSegment1) {
//				    	return false;
//				    }
//				    boolean surSegment2 = detectionLigne(xProcheSegments[1],yProcheSegments[1],segment2.getX1(),segment2.getY1(), segment2.getX2(),segment2.getY2(),longueur[1] ); 
//				    System.out.println("................"+surSegment2);
//
//				    if (!surSegment2) {
//				    	return false;
//				    }
		     
		     
		     
//				    boolean surSegment3 = detectionLigne(xProcheSegments[2],yProcheSegments[2],segment3.getX1(),segment3.getY1(), segment3.getX2(),segment3.getY2(),longueur[2] ); 
//				    System.out.println("SEG3................"+surSegment3);
//
//				    if (!surSegment3) {
//				    	seg3 = false;
//				    }
//				    boolean surSegment4 = detectionLigne(xProcheSegments[3],yProcheSegments[3],segment4.getX1(),segment4.getY1(), segment4.getX2(),segment4.getY2(),longueur[3] ); 
//				    System.out.println("SEG4................"+surSegment4);
//
//				    if (!surSegment4) {
//
//				    	seg4= false;
//				    }
		    
		    
		    
		 
		    
//		    if(distanceSeg1Balle <= balle.getDiametre()/2) {
//
//		    	return true;
//		    }
//		    if(distanceSeg2Balle <= balle.getDiametre()/2) {
//
//		    	return true;
//		    } 
//		    if(distanceSeg3Balle <= balle.getDiametre()/2) {
//
//		    	seg3= true;
//		    } 
//		     if (distanceSeg4Balle <= balle.getDiametre()/2) {
//
//		    	seg4= true;
//		    }
		   
		    	
	
		  if( seg3 || seg4 == true) {
			  return true;
		  }else {
			  return false;
		  }
	}
}