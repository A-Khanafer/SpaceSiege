package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import obstacles.Rectangle;

public class ZoneAnimationPhysique extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean enCoursDAnimation;
	
	public ZoneAnimationPhysique() {
		
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Rectangle rec = new Rectangle(100,100);
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
