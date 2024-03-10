package balle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

public class BalleBasique extends Balle {
	private Ellipse2D.Double cercle;

	protected Vecteur2D position= new Vecteur2D(50,50);


	public BalleBasique(int masseDonne,int chargeDonne,int diametreDonne,Vecteur2D position) {
		super(masseDonne, chargeDonne, diametreDonne,position);
		creerLaGeometrie();
	}
	public void creerLaGeometrie() {
		cercle = new Ellipse2D.Double(this.position.getX(),this.position.getY(), diametre, diametre);

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

		System.out.println("JE RENTRE DANS LA METHODE AVAnCEPAS DANS BALLE BASIQUE");
		System.out.println(position.toString());
		creerLaGeometrie();
	}
}