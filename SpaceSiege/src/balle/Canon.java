package balle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import interfaces.Dessinable;
import interfaces.Selectionnable;

public class Canon extends JPanel implements Selectionnable, Dessinable {
	 private double x,y;
	 private Rectangle2D.Double rectangleCanon ;
	 private Ellipse2D.Double cercle ;
	 private Ellipse2D.Double base ;
	 private double pixelsParMetre;
	 private double diametreCercle;
	 private double largeur = 100;  //30
	 private double hauteur = 50;   //15
	 private Area aireCercle;
	 private Area aireRect;
	 private Area aireBase;
	 private Area aireJohnson;
	public Canon(int x,int y) {
		this.x=x;
		this.y=y;
		creerLaGeometrie();
	}
	private void creerLaGeometrie() {
		rectangleCanon=new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur);
		base=new Ellipse2D.Double(0,y-10,3,hauteur+20);
		cercle=new Ellipse2D.Double(3,y,hauteur,hauteur);
	    aireRect=new Area(rectangleCanon);
		aireCercle=new Area(cercle);
		aireBase= new Area(base);
		aireRect.add(aireCercle);
		
	
	
	}
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
	
		g2dPrive.setColor(Color.BLUE);

		
		g2dPrive.fill(aireBase);
		g2dPrive.setColor(Color.BLACK);
		g2dPrive.fill(aireRect);
		
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		if(aireRect.contains(xPix, yPix)|| aireBase.contains(xPix, yPix)) {
			return true;
		}
		return false;
	}
	public void rotate(double tetha) {
		 double angle = Math.toRadians(tetha);
	        AffineTransform transform = AffineTransform.getRotateInstance(angle,10+hauteur/2,y+hauteur/2);
	        aireRect.transform(transform);
	}
	public void move( int eY) {
		this.y = eY - hauteur/2;
		creerLaGeometrie();	
}
}
