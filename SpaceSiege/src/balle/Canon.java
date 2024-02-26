package balle;

import java.awt.Color;
import java.awt.Graphics2D;
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
	 private double pixelsParMetre;
	 private double diametreCercle=75;
	 private double largeur = 200;
	 private double hauteur = 75;
	 private Area aireRectCanon;
	public Canon(int x,int y) {
		this.x=x;
		this.y=y;
		creerLaGeometrie();
	}
	private void creerLaGeometrie() {
		rectangleCanon=new Rectangle2D.Double(x, y, largeur, hauteur);
		cercle=new Ellipse2D.Double(x-diametreCercle,y,diametreCercle,diametreCercle);
	    aireRectCanon=new Area(rectangleCanon);
		Area airecercle=new Area(cercle);
		aireRectCanon.subtract(airecercle);
	}
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
	
		g2dPrive.setColor(Color.BLUE);
		g2dPrive.fill(aireRectCanon);
		
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		return false;
	}

}
