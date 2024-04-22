package composantdessin;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	private  double xMin =0;
	/** Valeur par dde l'abscisse maximale. */
	private  double xMax ;
	/** Valeur par dde l'ordonnminimale. */
	private  double yMin=0;
	/** Valeur par dde l'ordonnmaximale. */
	private  double yMax;
	private int nmbreLig = 12;
	private double pixelParMetreX;
	private Vecteur2D position;
	private Ellipse2D.Double cercle;
	private Line2D.Double axeX, axeY;
	private boolean premiereFois = true;
	private Path2D.Double grille;
private double deltaX;
private double deltaY;
private double pixelParMetreY;
private Path2D.Double taquets;
private Path2D.Double ligne=new Path2D.Double();
	public PlanCartesien(Vecteur2D posInitial) {
		setBackground(Color.white);
		this.position = posInitial;
	
		cercle = new Ellipse2D.Double(position.getX(), position.getY(), 5, 5);
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if(premiereFois) {
			
			xMax=getWidth();
			yMax=getHeight();
			 deltaX = (xMax - xMin) / (nmbreLig+ 1);
			 deltaY = (yMax - yMin) / (nmbreLig + 1);
			int x,y;
			
			x = getWidth(); 
			y = getHeight();
			
			pixelParMetreX = ((double)getWidth()-deltaX)/150.0;
			pixelParMetreY=((double)getHeight()-deltaY)/77.7;	
			
			g2d.setColor(Color.blue);
			axeX = new Line2D.Double(0, y - deltaY, x, y - deltaY);
			axeY = new Line2D.Double( deltaX, 0, deltaX, y);
				
			premiereFois = false;

		}
		creerGrille();
		 g2d.setColor(Color.gray);
		    g2d.draw(grille);
		g2d.setColor(Color.black);

		g2d.setStroke(new BasicStroke(2.0f));
		g2d.draw(axeX);
		g2d.draw(axeY);
		/*
		System.out.println(getWidth()+"PANELLLLLLLLGRAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(position.getX()+"VOIRRRRRRRRRRRRRRRRRRRRRRRRR");
		System.out.println(position.getY()+"malskdmaskl");
		*/

		//cercle = new Ellipse2D.Double((position.getX()*pixelParMetre)+deltaX,( position.getY()*pixelParMetreY), 5, 5);
		ligne.moveTo((position.getX()*pixelParMetreX)+deltaX, ( position.getY()*pixelParMetreY));
		ligne.lineTo((position.getX()*pixelParMetreX)+deltaX, ( position.getY()*pixelParMetreY));
		
		g2d.setColor(Color.red);
		g2d.draw(ligne);
		
		g2d.setColor(Color.black);
creerTaquetsSui(g2d);
		
		
	}
	
	public void setPosition(Vecteur2D pos) {
		this.position = pos;
		repaint();
	}
	private void creerGrille() {
		grille = new Path2D.Double();


		double xCentrage = -xMin % deltaX;
		double yCentrage = -yMin % deltaY;

		
		for (int i = 0; i < nmbreLig + 1; i++) {
			double grilleX = xMin + i * deltaX + xCentrage;
			grille.moveTo(grilleX, yMin);
			grille.lineTo(grilleX, yMax);
		}

		for (int i = 0; i < nmbreLig + 1; i++) {
			double grilleY = yMin + i * deltaY + yCentrage;
			grille.moveTo(xMin, grilleY);
			grille.lineTo(xMax, grilleY);
		}

		repaint();
	}
	public void reset() {
		ligne.reset();
		repaint();
	}
	private void creerTaquetsSui(Graphics2D g2d) {
	    g2d.setColor(Color.black);
	    int taquetSize = 5; 
	    Font smallFont = new Font("SansSerif", Font.PLAIN, 8); 
	    g2d.setFont(smallFont);

	   
	    for (int i = 0; i <= nmbreLig; i++) {
	        double grilleX = xMin + i * deltaX;
	        int positionEnMetresX = (int) (grilleX / pixelParMetreX); 
	        g2d.drawLine((int) (grilleX), (int) (yMax - deltaY - taquetSize), (int) (grilleX), (int) (yMax - deltaY + taquetSize));
	        g2d.drawString(String.format("%d", positionEnMetresX), (int) (grilleX - 10) + (int)deltaX, (int) (yMax - deltaY+10)); 
	    }

	  
	    for (int i = 0; i <= nmbreLig; i++) {
	        double grilleY = yMax - (yMin + i * deltaY);
	        int positionEnMetresY = (int) ((yMax - grilleY) / pixelParMetreY); 
	        g2d.drawLine((int) (deltaX - taquetSize), (int) (grilleY), (int) (deltaX + taquetSize), (int) (grilleY));
	      
	        g2d.drawString(String.format("%d", positionEnMetresY), (int) (deltaX - 17), (int) (grilleY - deltaY)); 
	    }
	}
	
	

	
}
