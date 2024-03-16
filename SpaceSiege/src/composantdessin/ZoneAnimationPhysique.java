package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import java.awt.geom.AffineTransform;


import javax.swing.JPanel;

import balle.Balle;
import balle.BalleBasique;
import balle.Canon;
import balle.FlecheDeTir;
import physique.Vecteur2D;

import java.awt.Color;

import obstacles.Rectangle;
import outils.CollisionRectangle;
import physique.MoteurPhysique;

public class ZoneAnimationPhysique extends JPanel implements Runnable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean enCoursDAnimation=false;

	private double deltaT=0.05;

	private double rotation=20;
	private int tempsDuSleep = 5;
	private Rectangle rec = new Rectangle(50,50);
	private boolean balleTiree = false;
	private Canon canon= new Canon (0,80);
	private boolean curseurActiver=false;

	private boolean premierFois=false;
	private double tempsTotalEcoule =0;
	
	double posMurSol,posMurDroit, posMurHaut,posMurGauche ;
	double   hauteurComposant, largeurComposant;
	
	private int index = -1;
	
	
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
		ecouteurClavier();
		
	}

	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);




		
		
		canon.dessiner(g2d);
		//rec.dessiner(g2d);

	
	    posMurSol = getHeight();
	    posMurDroit = getWidth();
	    posMurGauche = 0;
	    posMurHaut = 0;
       


	    hauteurComposant = getHeight();
	    largeurComposant = getWidth();
	    g2d.setColor(Color.red);
	    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

	}

	public void run() {
		while (enCoursDAnimation) {
			System.out.println("Un tour de run...on avance de " + deltaT + " secondes");
			System.out.println("Temps ecoule "+tempsTotalEcoule);

			calculerUneIterationPhysique(deltaT);

			testerCollisionsEtAjusterVitesses();
		/*	
			if( 	CollisionRectangle.detectionCollisionBalleLigne(canon.getBalle(),rec )== true ) {
				enCoursDAnimation=false;
			}
		*/
				
			
			
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
		 
		canon.getBalle().gererCollisions(posMurSol, posMurDroit , posMurHaut, posMurGauche);
		
	}
	
	 


	private void calculerLesForces() {
		Vecteur2D forceDeGravite=MoteurPhysique.calculForceGrav(canon.getBalle().getMasse(), Math.toRadians(90));
        Vecteur2D forceUtilisateur= new Vecteur2D(canon.getFleche().calculerComposantX(),canon.getFleche().calculerComposantY());
        
		canon.getBalle().setSommeDesForces(forceUtilisateur);

	}
	public  void TirerBalle() {
		balleTiree=true;
		canon.setBalleTiree();
	}
	
	
	private void ecouteurClavier() {
	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_W: 
	                	 System.out.println("SALUT JE ne VAIS pas ICI");
	                    break;
	                case KeyEvent.VK_S: 
	                   System.out.println("SALUT JE VAIS ICI");
	                    break;
	            }
	            repaint();
	        }
	    });
	}



	private void ecouteurSouris() {
		addMouseListener((MouseListener) new MouseAdapter() {
			@Override

			public void mousePressed(MouseEvent e) {
               
            
			
			    
			  

			
			
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
				if(!balleTiree) {
					canon.rotate(e.getX(),e.getY());
		        	canon.changerTaille(e.getX(), e.getY());
				}
					index = rec.getClickedResizeHandleIndex(e.getX(), e.getY());
					repaint();
					if (rec.isClickedOnIt() == true && index != -1) {
//						System.out.println("RESIZE");
						rec.resize(index, e.getX(), e.getY());
						repaint();
					}else if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == true && index == -1 ) {
						rec.rotate( e.getX(), e.getY());
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
						canon.move(e.getY());
						repaint();
					}

				}

			});


			}
	
	
}
		
			

		