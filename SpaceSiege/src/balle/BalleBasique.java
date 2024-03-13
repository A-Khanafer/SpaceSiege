package balle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

public class BalleBasique extends Balle {
	private Ellipse2D.Double cercle;


	



	public BalleBasique(int masseDonne,int chargeDonne,int diametreDonne,Vecteur2D position) {
		super(masseDonne, chargeDonne, diametreDonne,position);
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
		
		super.avancerUnPas(deltaT);

		System.out.println("JE RENTRE DANS LA METHODE AVAnCEPAS DANS BALLE BASIQUE");
		System.out.println(position.toString());
		creerLaGeometrie();
		
	}



	public void gererCollisions(double posSol, double posMur) {
		
		if ( (position.getY() + diametre) >= ( posSol ) ) {
			
			vitesse.setY(-vitesse.getY());
			position.setY(posSol-diametre);
		}
        if ( (position.getX() + diametre) >= ( posMur ) ) {
			
			vitesse.setX(-vitesse.getX());
			position.setX(posMur-diametre);
		}
	
}
}

