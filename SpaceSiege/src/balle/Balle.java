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

protected boolean balleTiree=false;

	protected Vecteur2D position= new Vecteur2D(0,0);


	protected Vecteur2D vitesse ;

	protected Vecteur2D accel = new Vecteur2D(0,0);

	Ellipse2D.Double cercle=new Ellipse2D.Double(position.getX(),position.getY(), diametre, diametre);

	public Balle(int masseDonne, int chargeDonne, int diametreDonne, Vecteur2D position, Vecteur2D vitesse) {
		masse = masseDonne;
		charge = chargeDonne;
		diametre = diametreDonne;
		this.vitesse = vitesse;
		this.position = position;
		initialiserCercle();
	}

	private void initialiserCercle() {
		cercle = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);
		aire = new Area(cercle);
	}

	public void setSommeDesForces(Vecteur2D sommeForcesSurLaBalle) {

		try {
//			 accel = MoteurPhysique.calculAcceleration(sommeForcesSurLaBalle, masse);
			System.out.println(sommeForcesSurLaBalle.getY()+"N");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void avancerUnPas(double deltaT) {
	
		vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
		position = MoteurPhysique.calculPosition(deltaT, position, vitesse);

		initialiserCercle();
		
	}
	public int getMasse() {
		return this.masse;
	}
	public String toString(int nbDecimales){
		String s = "Balle : position=[ " + String.format("%."+nbDecimales+"f", position.getX()) + ", " + String.format("%."+nbDecimales+"f", position.getY()) + "]" ;
		return s;
	}
	public Vecteur2D getPosition() {
		return this.position;
	}
	public double getDiametre() {
		return this.diametre;
	}
	public void setVitesse(Vecteur2D vit) {
		this.vitesse=vit;
	}
	public  Vecteur2D getVitesse() {
		return this.vitesse;
	}
	
	//zk
	public double getPosXCentre() {
		return this.position.getX()+diametre/2;
	}
	//zk
	public double getPosYCentre() {
		return this.position.getY()+diametre/2;
	}

	public void setPosition(Vecteur2D position) {
		this.position = position;
	}
	
	
	
	
	
	
}
