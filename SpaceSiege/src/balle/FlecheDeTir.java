package balle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import interfaces.Dessinable;

public class FlecheDeTir implements Dessinable  {
	private double x1, y1;
	
	private double x2, y2;
	
	private Line2D.Double corps, traitDeTete,deuxiemeTraitDeTete;   
	
	private double angleTete = 45;    
	
	private double longueurTraitDeTete = 8; 
	
	private double pixelsParMetre = 1;
	
	private double angle;
	
	public FlecheDeTir(double x1, double y1, double dx,double dy) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + dx;
		this.y2 = y1 ;
		creerLaGeometrie();
	}
private void creerLaGeometrie() {//debut
		
		
		corps = new Line2D.Double(x1, y1, x2, y2);
		double longueurFleche = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) );
		double dxPetitTrait = longueurTraitDeTete*(x2-x1)/longueurFleche;
		double dyPetitTrait = longueurTraitDeTete*(y2-y1)/longueurFleche;
		double x3 = x2-dxPetitTrait;
		double y3 = y2-dyPetitTrait;
		traitDeTete = new Line2D.Double( x2, y2, x3, y3);
		deuxiemeTraitDeTete = new Line2D.Double( x2, y2, x2 + dyPetitTrait, y2 + dxPetitTrait);
	}//fin

	@Override
public void dessiner(Graphics2D g2d) {	//debut
			
		AffineTransform mat = new AffineTransform();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.red);
		
		mat.scale(pixelsParMetre, pixelsParMetre);
		
		g2d.draw( mat.createTransformedShape(corps) );  		//corps de la fleche
		
		mat.rotate(Math.toRadians(angleTete), x2,  y2);
		g2d.draw( mat.createTransformedShape(traitDeTete) );
		g2d.draw( mat.createTransformedShape(deuxiemeTraitDeTete) );//un des deux traits qui formeront la tete de la fleche
		
		g2d.setColor(Color.gray);
		
		
	}// fin
	public void setPointInitial(int x1,int y1) {
		this.x1=x1;
		this.y1=y1;
		creerLaGeometrie();
	}
	public void setPointFinal(int x2,int y2) {
		this.x2=x2;
		this.y2=y2;
		creerLaGeometrie();
	}
/*	public double getAngle() {
		angle=Math.atan((y2-y1)/(x2-x1));
		return angle;
		
	}
	*/
}
