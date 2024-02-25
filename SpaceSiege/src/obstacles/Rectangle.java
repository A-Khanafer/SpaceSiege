package obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;


import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;
 

public class Rectangle implements Obstacles, Dessinable, Selectionnable {

	private double pixelsParMetre;
	private double width = 10*pixelsParMetre;
	private double height = 10*pixelsParMetre;
	private Rectangle2D.Double rectangle;
	private Area aire;
	private double coinXGauche,coinYGauche;
	private double coinXDroite,coinYDroite;
	private double centreX, centreY;
	private AffineTransform matRot = new AffineTransform();

	
	
	
	public Rectangle(double posX, double posY) {
		this.coinXGauche = posX;
		this.coinYGauche = posY;
		creerLaGeometrie();
	}
	
	private void creerLaGeometrie() {
		
		coinXDroite = coinXGauche + width;
		rectangle = new Rectangle2D.Double(coinXGauche, coinYGauche, width, height);
		
		centreX  = rectangle.getCenterX();
		centreY = rectangle.getCenterY();
		if(coinXGauche < centreX ) {
			coinXDroite = rectangle.getMaxX();
			
		}else{
			coinXDroite = rectangle.getMinX();
		}
		aire = new Area(rectangle);
			
	}

	@Override
	public void resize(double topRightCornerX,double topRightCornerY, int eX, int eY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double pixelMillieuForme, int eX, int eY) {
		double angleRotation;
		if(eX <= centreX) {
		double longeurCoteOpose = eX - centreX ;
			if(eY < centreY) {
				double longeurCoteAdjacent = centreY - eY;
				angleRotation = Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
			}else {
				double longeurCoteAdjacent = centreY - eY;
				angleRotation = -Math.PI + Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
			}
		}else{
			double longeurCoteOpose = eX - centreX ;
				if(eY < centreY) {
					double longeurCoteAdjacent = centreY - eY;
					angleRotation = Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
				}else {
					double longeurCoteAdjacent = eY - centreY;
					angleRotation = Math.PI - Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
				}
		}
		matRot.rotate(angleRotation);
//		Coin
		
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		if (aire.contains(xPix, yPix)) {
			return true;
		}else {
		    return false;
		}
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopy = g2d;
		g2dCopy.setColor(Color.black);
		g2dCopy.draw(rectangle);
		
		
	}

}
