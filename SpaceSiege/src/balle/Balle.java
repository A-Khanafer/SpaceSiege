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
	
	 
	 protected Vecteur2D position= new Vecteur2D(0,0); 
	 
	 protected Vecteur2D vitesse = new Vecteur2D(0,0);
	 
	 protected Vecteur2D accel = new Vecteur2D(0,0); 
  
	 Ellipse2D.Double cercle=new Ellipse2D.Double(position.getX(),position.getY(), diametre, diametre);
	
	   public Balle(int masseDonne, int chargeDonne, int diametreDonne, Vecteur2D position) {
	        masse = masseDonne;
	        charge = chargeDonne;
	        diametre = diametreDonne;
	        this.position = new Vecteur2D(position);
	        initialiserCercle();
	    }

	    private void initialiserCercle() {
	        cercle = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);
	        aire = new Area(cercle);
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
	public int getMasse() {
		return this.masse;
	}
}
