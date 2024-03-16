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
import physique.Vecteur2D;

public class Canon extends JPanel implements Selectionnable, Dessinable {

	private int x,y;
	private Rectangle2D.Double rectangleCanon ;
	private Ellipse2D.Double cercle ;
	private Ellipse2D.Double base ;
	private double pixelsParMetre = 10;
	private boolean balleTiree = false;
	private boolean premiereFois = true;
	private int largeur = 100; //30
	private int hauteur = 50; //15
	private Area aireCercle;
	private Area aireRect;
	private Area aireBase;
	private BalleBasique balle;
	private double rotation= 0 ;
	private boolean curseurActiver=false;
	private Vecteur2D vitesse = new Vecteur2D(20,20);
	private FlecheDeTir positionDeTir;
	private double dx=0;
	private double dy=0;
	
	
	

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
		positionDeTir = new FlecheDeTir(cercle.getCenterX(), cercle.getCenterY(), dx,dy);
		if(!balleTiree && premiereFois ) {
			balle= new BalleBasique(50, 2, hauteur,new Vecteur2D(3,y), new Vecteur2D(0,0));
			premiereFois = false;
			
		}

		if(!balleTiree) {
			balle.setPosition(new Vecteur2D(3, y));
		}

		


	}
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setColor(Color.BLUE);
		
		positionDeTir.dessiner(g2dPrive);
		
		g2dPrive.fill(aireBase);
		if(balleTiree) {
			balle.dessiner(g2dPrive);
		}
		
		//ROTATED
		g2dPrive.rotate(rotation, cercle.getCenterX(), cercle.getCenterY());
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
	
	public void rotate(int ex, int ey) {
	   
		
	    rotation = positionDeTir.getAngle();
	 //   AffineTransform transform = AffineTransform.getRotateInstance(rotation, x + hauteur / 2, y + hauteur / 2);
	  
	  // = new Area(transform.createTransformedShape(new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur)));
	//    aireCercle = new Area(transform.createTransformedShape(new Ellipse2D.Double(3,y,hauteur,hauteur)));	 

	    
	    if(ey < cercle.getCenterY()) {
	    	vitesse.setX(Math.cos(rotation)*positionDeTir.calculerModulus()/4);
	    	vitesse.setY(Math.sin(rotation)*positionDeTir.calculerModulus()/4);
	    	balle.setVitesse(vitesse);
	    }else if(ey > cercle.getCenterY()) {
	    	double newRotCos = 2*Math.PI - rotation;
	    	double newRotSin = Math.PI - rotation;
	    	vitesse.setX(Math.cos(newRotCos)*positionDeTir.calculerModulus()/4);
	 	    vitesse.setY(Math.sin(newRotSin)*positionDeTir.calculerModulus()/4);
	 	    balle.setVitesse(vitesse);
	    }else {
	    	vitesse.setX(positionDeTir.calculerModulus()/8);
	    }
	    
	}

	public void move( int eY) {
		this.y = eY - hauteur/2;
		creerLaGeometrie();
	;
	}
	public int getPointeX() {
	return   (int) rectangleCanon.getMaxX();
	   
	}

	public int getPointeY() {
	   
    return (int)  rectangleCanon.getCenterY();
	 
	}

	public BalleBasique getBalle() {
		return this.balle;
	}
	public int getMasseBalleBasique() {
		return 0;
	}
	public void avancerUnPas(double deltaT) {
		this.balle.avancerUnPas(deltaT);
		creerLaGeometrie();

	}
	public FlecheDeTir getFleche() {
		return this.positionDeTir;
	} 
	public void changerTaille(double x2,double y2) {
		dx=x2-positionDeTir.getX1();
		dy=y2-positionDeTir.getY1();
		creerLaGeometrie();
	}
	public void setBalleTiree() {
		balleTiree=true;
		creerLaGeometrie();
	}
	public boolean isPremiereFois() {
		return premiereFois;
	}
	public void setPremiereFois(boolean premiereFois) {
		this.premiereFois = premiereFois;
	}
	public boolean isCurseurActiver() {
		return curseurActiver;
	}
	public void setCurseurActiver(boolean curseurActiver) {
		this.curseurActiver = curseurActiver;
	}
	
	
}


