package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;


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
	private double deltaT=0;
	private int tempsDuSleep = 50;
	private Rectangle rec = new Rectangle(50,50);
	private boolean onOff = false;
	private Canon AllahUAkbar= new Canon (0,80);
	private FlecheDeTir fleche=new FlecheDeTir(AllahUAkbar.getPointeX(),AllahUAkbar.getPointeY(), 0, 0);
	private boolean premierFois=false;
	private double tempsTotalEcoule ;
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		

		rec.dessiner(g2d);

		
		fleche.setPointInitial(AllahUAkbar.getPointeX(), AllahUAkbar.getPointeY());
		fleche.dessiner(g2d);
		
		
		 
		
		AllahUAkbar.dessiner(g2d);
		if (!premierFois) {
			premierFois=true;
	AllahUAkbar.rotate(40);
		}
	}

	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
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
		AllahUAkbar.getBalle().avancerUnPas( deltaT );
		
	
	}
	
	//private void calculerLesForces() {
	//	double forceDeGravité=MoteurPhysique.calculForceGrav(AllahUAkbar.getBalle(), deltaT)
		
		
	  //   Vecteur2D forceFrottemenrRouge=new Vecteur2D(forceFrottementRougeX,0);
	  //   Vecteur2D forceGRouge=new Vecteur2D(0,0);    
	  //   Vecteur2D sommeForceRouge=forceGRouge.additionne(forceFrottemenrRouge);
	     
	   //  sommeForcesA = sommeForceRouge;
	     
	  //   route.getAutoRouge().setSommeDesForces(sommeForceRouge);
	     
	//}
	 private void ecouteurSouris() {
		  addMouseListener((MouseListener) new MouseAdapter() {
				@Override
				
				public void mouseClicked(MouseEvent e) {
					


					fleche.setPointInitial(AllahUAkbar.getPointeX(),AllahUAkbar.getPointeY());
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
					
					
                      if(AllahUAkbar.contient(e.getX(), e.getY())) {
                    	  System.out.println("JE touche le JONHSON");
						AllahUAkbar.move(e.getY());
						repaint();
					}
					
					
					
				}

			});
			
	  }

	
	
}
