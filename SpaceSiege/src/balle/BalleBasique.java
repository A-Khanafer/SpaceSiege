package balle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

public class BalleBasique extends Balle {

	
	public BalleBasique(int masseDonne,int chargeDonne,int diametreDonne,Vecteur2D position, Vecteur2D vitesse) {
		super(masseDonne, chargeDonne, diametreDonne,position, vitesse);
		creerLaGeometrie();
	}
	public void creerLaGeometrie() {
		cercle = new Ellipse2D.Double(position.getX(),position.getY(), diametre, diametre);

	}
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dPrive = (Graphics2D) g2d.create();
		//g2dPrive.scale(pixelsParMetre, pixelsParMetre);
		g2dPrive.setColor(Color.MAGENTA);
		g2dPrive.fill(cercle);

	}
	public void setPixelsParMetre(double pixelParMetre) {
		this.pixelsParMetre=pixelParMetre;
	}
	public void avancerUnPas(double deltaT) {
		
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		creerLaGeometrie();
		
	}



	public void gererCollisions(double posSol, double posMurDroit, double posMurHaut, double posMurGauche) {
		
		if ( (position.getY() + diametre) >= ( posSol ) ) {
			
			vitesse.setY(-vitesse.getY());
			position.setY(posSol-diametre);
		}
        if ( (position.getX() + diametre) >= ( posMurDroit ) ) {
			vitesse.setX(-vitesse.getX());
			position.setX(posMurDroit-diametre);
		}
        if ( (position.getY()) <= ( posMurHaut ) ) {
			
			vitesse.setY(-vitesse.getY());
			position.setY(posMurHaut);
		}
        if ( (position.getX()) <= ( posMurGauche ) ) {
			
			vitesse.setX(-vitesse.getX());
			position.setX(posMurGauche);
		}
        
	
}
}

