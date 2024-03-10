package balle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import interfaces.Dessinable;

public class FlecheDeTir implements Dessinable {
	private double x1, y1;
	private double x2, y2;
	private Line2D.Double corps, traitDeTete, deuxiemeTraitDeTete;
	private double angleTete = 45;
	private double longueurTraitDeTete = 8;
	private double pixelsParMetre = 1;
	private double angleRadians;
	private double rotation;

	public FlecheDeTir(double x1, double y1, double dx, double dy, double rotation) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + dx;
		this.y2 = y1;
		this.rotation = rotation;
		creerLaGeometrie();
	}

	private void creerLaGeometrie() {
		corps = new Line2D.Double(x1, y1, x2, y2);
		double longueurFleche = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double dxPetitTrait = longueurTraitDeTete * (x2 - x1) / longueurFleche;
		double dyPetitTrait = longueurTraitDeTete * (y2 - y1) / longueurFleche;
		double x3 = x2 - dxPetitTrait;
		double y3 = y2 - dyPetitTrait;
		traitDeTete = new Line2D.Double(x2, y2, x3, y3);
		deuxiemeTraitDeTete = new Line2D.Double(x2, y2, x2 + dyPetitTrait, y2 + dxPetitTrait);
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.red);

		mat.scale(pixelsParMetre, pixelsParMetre);

		g2d.draw(mat.createTransformedShape(corps)); // corps de la fleche

		mat.rotate(Math.toRadians(angleTete), x2, y2);
		g2d.draw(mat.createTransformedShape(traitDeTete));
		g2d.draw(mat.createTransformedShape(deuxiemeTraitDeTete));
	}

	public void setPointInitial(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
		creerLaGeometrie();
	}

	public void setPointFinal(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
		creerLaGeometrie();
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;

	}
	public double getAngle() {

		double dx = x2 - x1;
		double dy = y2 - y1;

	 angleRadians = Math.atan2(dy, dx);

		return angleRadians;
	}
	public double calculerModulus() {
	    double dx = x2 - x1;
	    double dy = y2 - y1;
	    return Math.sqrt(dx * dx + dy * dy);
	}
	public double calculerComposantX() {
	    
	    double modulus = calculerModulus();
	    
	    
	    return modulus * Math.cos(angleRadians);
	}

	public double calculerComposantY() {
	    
	    double modulus = calculerModulus();
	    
	    return modulus * Math.sin(angleRadians);
	}


}