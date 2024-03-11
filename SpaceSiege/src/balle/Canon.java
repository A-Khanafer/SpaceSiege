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
	private boolean firt = true;
	private int largeur = 100; //30
	private int hauteur = 50; //15
	private Area aireCercle;
	private Area aireRect;
	private Area aireBase;
	private Area aireJohnson;
	private BalleBasique balle;
	private double rotation= Math.toRadians(30);
	private Vecteur2D positionInitial= new Vecteur2D(x+largeur+hauteur/2,y);
	private Vecteur2D positionNul= new Vecteur2D(0,0);
	private FlecheDeTir positionDeTir;

	public Canon(int x,int y) {
		this.x=x;
		this.y=y;
		creerLaGeometrie();
	}
	private void creerLaGeometrie() {

		
		if(firt == true) {
			balle= new BalleBasique(50, 2, (int) (3*pixelsParMetre),new Vecteur2D(x+largeur+hauteur/2,y));
			firt = false;
		}
		
		rectangleCanon=new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur);
		base=new Ellipse2D.Double(0,y-10,3,hauteur+20);
		cercle=new Ellipse2D.Double(3,y,hauteur,hauteur);
		aireRect=new Area(rectangleCanon);
		aireCercle=new Area(cercle);
		aireBase= new Area(base);
		aireRect.add(aireCercle);
		positionDeTir = new FlecheDeTir(cercle.getCenterX()- hauteur/2, cercle.getCenterY(), 200, 200, rotation);

		System.out.println("JE PASSE ICi");



		//balle.creerLaGeometrie();

	}
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setColor(Color.BLUE);
		g2dPrive.fill(aireBase);
		
		g2dPrive.rotate(rotation, cercle.getCenterX(), cercle.getCenterY());
		positionDeTir.dessiner(g2dPrive);
		
		g2dPrive.setColor(Color.BLACK);
		g2dPrive.fill(aireRect);
		
		balle.dessiner(g2dPrive);

	}

	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		if(aireRect.contains(xPix, yPix)|| aireBase.contains(xPix, yPix)) {
			return true;
		}
		return false;
	}
	public void rotate(double newAngleDegrees) {
	    
	    double newAngleRadians = Math.toRadians(newAngleDegrees);
	  
	    AffineTransform transform = AffineTransform.getRotateInstance(newAngleRadians, x + hauteur / 2, y + hauteur / 2);
	    
	    aireRect = new Area(transform.createTransformedShape(new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur)));
	    aireCercle = new Area(transform.createTransformedShape(new Ellipse2D.Double(3,y,hauteur,hauteur)));
	    // Combine areas as needed
	    aireRect.add(aireCercle);
	    // Now 'aireRect' and 'aireBase' are at the correct angle
	}

	public void move( int eY) {
		this.y = eY - hauteur/2;
		creerLaGeometrie();
	}
	public int getPointeX() {
	return   (int) rectangleCanon.getMaxX();
	   
	}

	public int getPointeY() {
	   
    return (int)  rectangleCanon.getCenterY();
	    // Calculate the new tip position
	   // return y + (int) ((hauteur / 2 + largeur) * Math.sin(rotation));
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
}


