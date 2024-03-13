package outils;

import java.awt.geom.Line2D;

import balle.BalleBasique;
import obstacles.Rectangle;
import balle.Balle;


public class OutilsCollisions {
	
	
	
	public static double distanceEntreDeuxPoints( double x1,double x2,double y1,double y2 ) {
		double distX = x1 - x2;
		double distY = y1 - y2;
		double longueur = Math.sqrt( (distX*distX) + (distY*distY) );
		
		return longueur;
		
	}
	public static boolean detectionPointLigne (double x1, double x2 ,double c1 ,double c2, double rayon) {
		
		double distance = distanceEntreDeuxPoints(x1,x2,c1,c2);
		if (distance <= rayon) {
			return true;
		}else {
			return false;
		}
	}
	public static boolean detectionCollisionBalleLigne(Balle balle, Rectangle rec) {
		
		boolean dans1 = detectionPointLigne(rec.getSegment1().getX1(),rec.getSegment1().getY1(), balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		boolean dans2 = detectionPointLigne(rec.getSegment1().getX2(),rec.getSegment2().getY2(), balle.getPosXCentre(),balle.getPosYCentre(),balle.getDiametre()/2);
		if (dans1 || dans2) {
			return true;
		}else {
			return false;
		}
		
	}

}
//double distance1Seg1 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment1().x1,rec.getSegment1().y1	);
//double distance2Seg1 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment1().x2,rec.getSegment1().y2	);
//double longueurSeg1 = distanceEntreDeuxPoints(rec.getSegment1().x1,rec.getSegment1().x2, rec.getSegment1().y1,rec.getSegment1().y2);
//
//double distance1Seg2 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment2().x1,rec.getSegment2().y1	);
//double distance2Seg2 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment2().x2,rec.getSegment2().y2	);
//double longueurSeg2 = distanceEntreDeuxPoints(rec.getSegment2().x1,rec.getSegment2().x2, rec.getSegment2().y1,rec.getSegment2().y2);
//
//double distance1Seg3 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment3().x1,rec.getSegment3().y1	);
//double distance2Seg3 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment3().x2,rec.getSegment3().y2	);
//double longueurSeg3 = distanceEntreDeuxPoints(rec.getSegment3().x1,rec.getSegment3().x2, rec.getSegment3().y1,rec.getSegment3().y2);
//
//double distance1Seg4 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment4().x1,rec.getSegment4().y1	);
//double distance2Seg4 = distanceEntreDeuxPoints(balle.getPosition().getX(),balle.getPosition().getY(), rec.getSegment4().x2,rec.getSegment4().y2	);
//double longueurSeg4 = distanceEntreDeuxPoints(rec.getSegment4().x1,rec.getSegment4().x2, rec.getSegment4().y1,rec.getSegment4().y2);
//
//if (distance1Seg1 + distance2Seg1 >= longueurSeg1 || distance1Seg2 + distance2Seg2 >= longueurSeg2 ||
//		distance1Seg3 + distance2Seg3 >= longueurSeg3 || longueurSeg4 + distance2Seg4 >= longueurSeg4) {
//	return true;
//}






