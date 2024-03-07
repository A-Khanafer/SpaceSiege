package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;
 

public class Rectangle implements Obstacles, Dessinable, Selectionnable {

	private double pixelsParMetre= 10;
	private double width = 10*pixelsParMetre;
	private double height = 10*pixelsParMetre;
	private Rectangle2D.Double rectangle, rectanglePointille;
	private Area aireRec, aireRotate;
	private double coinXGauche,coinYGauche;
	private double coinXDroite,coinYDroite;
	private double centreX, centreY;
	private double angleRotation;
	private Ellipse2D.Double resizeHandle;
	private double diametre = 2*pixelsParMetre;
	private boolean clickedOnIt = false;

	
	
	
	public Rectangle(double posX, double posY) {
		this.coinXGauche = posX;
		this.coinYGauche = posY;
		creerLaGeometrie();
	}
	
	private void creerLaGeometrie() {
		
		coinXDroite = coinXGauche + width;
		coinYDroite = coinYGauche;
		rectangle = new Rectangle2D.Double(coinXGauche, coinYGauche, width, height);
		rectanglePointille = rectangle;
		centreX  = rectangle.getCenterX();
		centreY = rectangle.getCenterY();

		aireRec = new Area(rectangle);
		resizeHandle = new Ellipse2D.Double(coinXDroite-diametre/2, coinYDroite-diametre/2, diametre, diametre);
	}

	@Override
	public void resize( int eX, int eY) {
		
	
			
			System.out.println("bigbangbussybuss");
			
			  if (clickedOnIt) {
		            double offsetX = eX - resizeHandle.getCenterX();
		            double offsetY = eY - resizeHandle.getCenterY();

		            if (rectangle.width + offsetX >= 0 && rectangle.height - offsetY >= 0) {
		                rectangle.width += offsetX;
		                rectangle.height -= offsetY;
		                rectangle.y += offsetY;
		                resizeHandle.setFrame(rectangle.x + rectangle.width - 10, rectangle.y - 10, 20, 20);

		     
		            }
		        }
			
			
			
			
			
		
		
		
		
	}


	@Override
	public void rotate( int eX, int eY) {
		
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
		
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		
		if (aireRec.contains(xPix, yPix)) {
			return true;
		}else {
		    return false;
		}
		
	}
	

	@Override
	public void move(int eX, int eY) {
			this.coinXGauche = eX - width/2 ;
			this.coinYGauche = eY - height/2 ;
			creerLaGeometrie();	
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopy = (Graphics2D) g2d.create();
		g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2dCopy.rotate(angleRotation, centreX, centreY);
		
		g2dCopy.setColor(Color.blue);
		g2dCopy.fill(rectangle);
		
		if(clickedOnIt) {
			BasicStroke pointille = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0); //creation de la ligne pointille
			g2dCopy.setStroke(pointille);
			g2dCopy.setColor(Color.black);
			g2dCopy.draw(rectanglePointille);
			g2dCopy.setColor(Color.red);
			g2dCopy.fill(resizeHandle);
		}
		
	}
	
	
	
	public boolean isClickedOnIt() {
		return clickedOnIt;
	}

	public void setClickedOnIt(boolean clickedOnIt) {
		this.clickedOnIt = clickedOnIt;
	}
	

	public Ellipse2D.Double getClickAv() {
		return resizeHandle;
	}

	public void setClickAv(Ellipse2D.Double clickAv) {
		this.resizeHandle = clickAv;
	}

	
//	private boolean clickSurZoneRouge(int eX, int eY) {
//		
//		if(clickAv.contains(eX, eY)) {
//			return true;
//		}else {
//			return false;
//		}
//		
//	}
	
	

}
