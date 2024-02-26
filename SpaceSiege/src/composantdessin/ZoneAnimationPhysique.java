package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import balle.BalleBasique;
import balle.Canon;

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
	
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		ecouteurSouris();
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		

		rec.dessiner(g2d);

		
		
		
		
		
		
		
		
		
		Canon AllahUAkbar=new Canon(10,80);
		AllahUAkbar.dessiner(g2d);

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
					if(rec.contient(e.getX(), e.getY())) {
						onOff = true;
						repaint();
					}else {
						onOff = false;
						repaint();
					}
				}
			});
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				
				public void mouseDragged(MouseEvent e) {
					if(rec.contient(e.getX(), e.getY()) && onOff == true) {
						System.out.println();
						rec.rotate( e.getX(), e.getY());
						repaint();
					}
				}

			});
			
	  }

	
	
}
