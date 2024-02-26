package balle;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.Vecteur2D;

public class BalleBasique extends Balle {
	private Ellipse2D.Double cercle;


public BalleBasique(int masseDonne,int chargeDonne,int diametreDonne,Vecteur2D position) {
	super(masseDonne, chargeDonne, diametreDonne,position);
	
}
private void creerLaGeometrie() {
	cercle = new Ellipse2D.Double(this.position.getX(),this.position.getY(), diametre, diametre);

}
public void dessiner(Graphics2D g2d) {
	Graphics2D g2dPrive = (Graphics2D) g2d.create();
	g2dPrive.scale(pixelsParMetre, pixelsParMetre);
	g2dPrive.fill(cercle);
}
public void setPixelsParMetre(double pixelParMetre) {
	this.pixelsParMetre=pixelParMetre;
}
}
