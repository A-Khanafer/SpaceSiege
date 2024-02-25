package obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
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
	private double pixelMillieuX;
	
	
	
	public Rectangle(double posX, double posY) {
		this.coinXGauche = posX;
		this.coinYGauche = posY;
		creerLaGeometrie();
	}
	
	private void creerLaGeometrie() {
		coinXDroite = coinXGauche + width; 
		pixelMillieuX = coinXGauche + width/2;
		rectangle = new Rectangle2D.Double(coinXGauche, coinYGauche, width, height);
		aire = new Area(rectangle);
			
	}

	@Override
	public void resize(double topRightCornerX,double topRightCornerY, int eX, int eY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double pixelMillieuForme, int eX, int eY) {
		double longeurCoteOpose = eX - pixelMillieuForme ;
		double longeurCoteAdjacent = pixelMillieuForme - eY;
		
		
		
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
