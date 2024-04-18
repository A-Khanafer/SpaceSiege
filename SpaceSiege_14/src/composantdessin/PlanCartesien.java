package composantdessin;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import composantjeu.Balle;
import composantjeu.BalleBasique;
import physique.Vecteur2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Composant permettant de tracer une fonction en segments avec une forme Path2D
 * Il est possible de recadrer la portion visible de la fonction.
 * 
 * @author Benakmoum Walid
 */


public class PlanCartesien extends JPanel {

	private double pixelParMetre;
	private Vecteur2D position;
	private Ellipse2D.Double cercle;
	private Line2D.Double axeX, axeY;
	private boolean premiereFois = true;
	
	public PlanCartesien(Vecteur2D posInitial) {
		setBackground(Color.white);
		this.position = posInitial;
		cercle = new Ellipse2D.Double(position.getX()-10, position.getY()-10, 10, 10);
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(premiereFois) {
		
			
			int x,y;
			
			x = getWidth(); 
			y = getHeight();
			
			pixelParMetre = x/150;
					
			System.out.println(x + " aohfiUWSHGN");
			System.out.println(y + " aohfiUWSHGN");
			g2d.setColor(Color.blue);
			axeX = new Line2D.Double(0, y - 50, x, y - 50);
			axeY = new Line2D.Double( 50, 0, 50, y);
				
			premiereFois = false;

		}
		
		g2d.setColor(Color.black);

		g2d.setStroke(new BasicStroke(2.0f));
		g2d.draw(axeX);
		g2d.draw(axeY);
		
		cercle = new Ellipse2D.Double(position.getX()/8 + 50, position.getY(), 10, 10);
		
		g2d.fill(cercle);
		
	}
	
	public void setPosition(Vecteur2D pos) {
		this.position = pos;
		repaint();
	}
	
}

