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

import java.awt.Color;

import obstacles.Rectangle;

public class ZoneAnimationPhysique extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean enCoursDAnimation;
	private Rectangle rec = new Rectangle(50,50);
	private boolean onOff = false;
	private Canon AllahUAkbar= new Canon (0,80);
	private FlecheDeTir fleche=new FlecheDeTir(AllahUAkbar.getPointX(),AllahUAkbar.getPointY(), 0, 0);
	
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		

		rec.dessiner(g2d);

		
		
		fleche.dessiner(g2d);
		
		
		 
		
		AllahUAkbar.dessiner(g2d);
	//AllahUAkbar.rotate(40);

	}

	public void run() {
		
	}
	public void demarrer() {
		if (!enCoursDAnimation) { 
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode
	
	
	
	
	
	 private void ecouteurSouris() {
		  addMouseListener((MouseListener) new MouseAdapter() {
				@Override
				
				public void mouseClicked(MouseEvent e) {
					
					fleche.setPointInitial(AllahUAkbar.getPointX(),AllahUAkbar.getPointY());
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
					fleche.setPointFinal(e.getX(), e.getY());
					repaint();
					
					if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == true) {
						System.out.println(rec.isClickedOnIt());
						rec.rotate( e.getX(), e.getY());
						repaint();
					}
					
					
					if(rec.contient(e.getX(), e.getY()) && rec.isClickedOnIt() == false) {
						System.out.println(rec.isClickedOnIt());
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
