package balle;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

public class Balle {
	protected int masse;

	protected double diametre;
	
	protected double pixelsParMetre;
	
	protected Area aire;
	
	protected int charge;
	
	 Ellipse2D.Double cercle;
	 
	 protected Vecteur2D position; 
	 
	 protected Vecteur2D vitesse = new Vecteur2D(0,0);
	 
	 protected Vecteur2D accel = new Vecteur2D(0,0); 
  
	
	public Balle (int masseDonne,int chargeDonne,int diametreDonne,Vecteur2D position) {
		masse=masseDonne;
		charge=chargeDonne;
		diametre=diametreDonne;
		aire= new Area(cercle);
		this.position = new Vecteur2D(position); 
		
	}
	public void setSommeDesForces(Vecteur2D sommeForcesSurLaBalle) {
		 
		try {
			accel = MoteurPhysique.calculAcceleration(sommeForcesSurLaBalle, masse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void avancerUnPas(double deltaT) {
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
		
	}
}
