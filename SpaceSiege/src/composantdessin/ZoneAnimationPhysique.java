package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JPanel;

import balle.Balle;
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
	private double deltaT=0.05;
	private double rotation=20;
	private int tempsDuSleep = 50;
	private Rectangle rec = new Rectangle(50,50);
	private boolean onOff = false;
	private Canon canon= new Canon (0,80);
	private FlecheDeTir fleche = new FlecheDeTir(canon.getPointeX(), canon.getPointeY(), 0, 0, rotation);

	private boolean premierFois=false;
	private double tempsTotalEcoule =0;
	
	double hauteurComposant,largeurComposant ;
	
	
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
		
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		

//		rec.dessiner(g2d);
		canon.dessiner(g2d);
		if (!premierFois) {
			premierFois=true;
	//AllahUAkbar.rotate(rotation);
		}
		
		fleche.setPointInitial(canon.getPointeX(), canon.getPointeY());
	    fleche.setRotation(rotation); 
	    fleche.dessiner(g2d);
		
	
	    hauteurComposant = getHeight();
	    largeurComposant = getWidth();
	}

	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			System.out.println("Temps ecoule "+tempsTotalEcoule);
			
			calculerUneIterationPhysique(deltaT);
			testerCollisionsEtAjusterVitesses();
				
				
			
			
			
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
	
	
	private void testerCollisionsEtAjusterVitesses() {	
		 
		canon.getBalle().gererCollisions(hauteurComposant, largeurComposant);
		
	}
	
	private void calculerLesForces() {
	Vecteur2D forceDeGravité=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
	Vecteur2D ForcesX = new Vecteur2D(1000,0);
	Vecteur2D somme = new Vecteur2D();
	somme = forceDeGravité.additionne(ForcesX);
		canon.getBalle().setSommeDesForces(somme);
		
	  //   Vecteur2D forceFrottemenrRouge=new Vecteur2D(forceFrottementRougeX,0);
	  //   Vecteur2D forceGRouge=new Vecteur2D(0,0);    
	  //   Vecteur2D sommeForceRouge=forceGRouge.additionne(forceFrottemenrRouge);
	     
	
	  //   route.getAutoRouge().setSommeDesForces(sommeForceRouge);
	     
	}
	 private void ecouteurSouris() {
		  addMouseListener((MouseListener) new MouseAdapter() {
				@Override
				
				public void mouseClicked(MouseEvent e) {
					


					fleche.setPointInitial(canon.getPointeX(),canon.getPointeY());
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
//					fleche.setPointFinal(e.getX(), e.getY());
//					repaint();
					
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
