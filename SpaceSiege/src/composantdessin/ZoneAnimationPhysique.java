package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import balle.BalleBasique;
import balle.Canon;

import java.awt.Color;

public class ZoneAnimationPhysique extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean enCoursDAnimation;
	
	public ZoneAnimationPhysique() {
		setBackground(new Color(255, 255, 255));
		
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		
		
		
		
		Canon AllahUAkbar=new Canon(0,80);
		AllahUAkbar.rotate(10);
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
}
