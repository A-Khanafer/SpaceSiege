package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import balle.BalleBasique;
import balle.Canon;
import balle.FlecheDeTir;
import physique.Vecteur2D;

import java.awt.Color;

import obstacles.Rectangle;
import physique.MoteurPhysique;

public class ZoneAnimationPhysique extends JPanel implements Runnable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean enCoursDAnimation=false;
	private double deltaT=0.5;
	private double rotation=20;
	private int tempsDuSleep = 50;
	private Rectangle rec = new Rectangle(50,50);
	private boolean onOff = false;
	private Canon canon= new Canon (0,80);
	private FlecheDeTir fleche = new FlecheDeTir(canon.getPointeX(), canon.getPointeY(), 0, 0, rotation);

	private boolean premierFois=false;
	private double tempsTotalEcoule =0;
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
	}

	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);


		// rec.dessiner(g2d);
		canon.dessiner(g2d);
	

		fleche.setPointInitial(canon.getPointeX(), canon.getPointeY());
		fleche.setRotation(rotation);
		fleche.dessiner(g2d);


	}

	public void run() {
		while (enCoursDAnimation) {
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			System.out.println("Temps ecoule "+tempsTotalEcoule);

			calculerUneIterationPhysique(deltaT);

			repaint();
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...!");
	}


	public void demarrer() {
		if (!enCoursDAnimation) {
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode

	private void calculerUneIterationPhysique(double deltaT) {
		tempsTotalEcoule += deltaT;
		calculerLesForces();
		canon.avancerUnPas(deltaT);


	}
	public void updateFlechePosition() {
	    int tipX = canon.getPointeX();
	    int tipY = canon.getPointeY();

	    fleche.setPointInitial(tipX, tipY);
	}

	private void calculerLesForces() {
		//Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
        Vecteur2D forceUtilisateur= new Vecteur2D(fleche.calculerComposantX(),fleche.calculerComposantY());
        
		canon.getBalle().setSommeDesForces((forceUtilisateur));

		// Vecteur2D forceFrottemenrRouge=new Vecteur2D(forceFrottementRougeX,0);
		// Vecteur2D forceGRouge=new Vecteur2D(0,0);
		// Vecteur2D sommeForceRouge=forceGRouge.additionne(forceFrottemenrRouge);

		// sommeForcesA = sommeForceRouge;

		// route.getAutoRouge().setSommeDesForces(sommeForceRouge);

	}
	private void ecouteurSouris() {
		addMouseListener((MouseListener) new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {
				fleche.setPointInitial(canon.getPointeX(), canon.getPointeY());

double newAngleDegrees = Math.toDegrees(fleche.getAngle());
			    
			  
			    canon.rotate(newAngleDegrees);
			
			
				repaint();



				if(rec.contient(e.getX(), e.getY())) {
					System.out.println(rec.isClickedOnIt());
					rec.setClickedOnIt(true);
					repaint();
				}else {
					System.out.println(rec.isClickedOnIt());
					rec.setClickedOnIt(false);
					repaint();
				}

			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override

			public void mouseDragged(MouseEvent e) {
				fleche.setPointInitial(canon.getPointeX(), canon.getPointeY());
				fleche.setPointFinal(e.getX(), e.getY());
				
				double newAngleDegrees = Math.toDegrees(fleche.getAngle());
			    
			
			    canon.rotate(newAngleDegrees);
			    updateFlechePosition();
				repaint();
				if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == true && rec.getClickAv().contains(e.getX(), e.getY()) == false) {

					rec.rotate( e.getX(), e.getY());
					repaint();
				}else if(rec.getClickAv().contains(e.getX(), e.getY()) && rec.isClickedOnIt() == true) {

					rec.resize(e.getX(), e.getY());
					repaint();
				}


				if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == false) {

					rec.move( e.getX(), e.getY());
					repaint();

					if(rec.contient(e.getX(), e.getY()) == false) {
						rec.setClickedOnIt(false);
						repaint();
					}
				}


				if(canon.contient(e.getX(), e.getY())) {
					System.out.println("JE touche le JONHSON");
					canon.move(e.getY());
					repaint();
				}



			}

		});

	}



}