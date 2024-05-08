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
 * Composant permettant de tracer une fonction en segments avec une forme Path2D.
 * Il est possible de recadrer la portion visible de la fonction.
 * Ce composant représente un plan cartésien avec des axes X et Y ainsi qu'une grille.
 * Il permet également de dessiner des lignes et des repères en fonction de coordonnées spécifiées.
 * 
 * @author Benakmoum Walid
 */


public class PlanCartesien extends JPanel {
	/** Valeur minimale de l'abscisse. */
    private double xMin = 0;
    /** Valeur maximale de l'abscisse. */
    private double xMax;
    /** Valeur minimale de l'ordonnée. */
    private double yMin = 0;
    /** Valeur maximale de l'ordonnée. */
    private double yMax;
    /** Nombre de lignes de la grille. */
    private int nmbreLig = 12;
    /** Nombre de pixels par mètre sur l'axe des X. */
    private double pixelParMetreX;
    /** Nombre de pixels par mètre sur l'axe des Y. */
    private double pixelParMetreY;
    /** Position du composant sur le plan cartésien. */
    private Vecteur2D position;
    /** Forme représentant un cercle. */
    private Ellipse2D.Double cercle;
    /** Ligne représentant l'axe des X. */
    private Line2D.Double axeX;
    /** Ligne représentant l'axe des Y. */
    private Line2D.Double axeY;
    /** Indique si c'est la première fois que le composant est dessiné. */
    private boolean premiereFois = true;
    /** Forme représentant la grille du plan cartésien. */
    private Path2D.Double grille;
    /** Pas entre deux lignes de la grille sur l'axe des X. */
    private double deltaX;
    /** Pas entre deux lignes de la grille sur l'axe des Y. */
    private double deltaY;
    /** Forme représentant les lignes dessinées sur le plan cartésien. */
    private Path2D.Double ligne=new Path2D.Double();;
    /** Forme représentant les repères dessinés sur le plan cartésien. */
    private Path2D.Double taquets;
    /**
     * Constructeur de la classe PlanCartesien.
     * 
     * @param posInitial Position initiale du composant sur le plan cartésien.
     */
  //Benakmoum Walid
	public PlanCartesien(Vecteur2D posInitial) {
		setBackground(Color.white);
		this.position = posInitial;
	
		cercle = new Ellipse2D.Double(position.getX(), position.getY(), 5, 5);
	}
	 /**
     * Méthode appelée pour dessiner les composants graphiques du plan cartésien.
     * 
     * @param g Objet Graphics utilisé pour dessiner.
     */
	//Benakmoum Walid
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
		
		ligne.moveTo((position.getX()*pixelParMetreX)+deltaX, ( position.getY()*pixelParMetreY));
		ligne.lineTo((position.getX()*pixelParMetreX)+deltaX, ( position.getY()*pixelParMetreY));
		
		g2d.setColor(Color.red);
		g2d.draw(ligne);
		
		g2d.setColor(Color.black);
		creerTaquets(g2d);
		
	    g2d.setColor(Color.black);
	    Font font = new Font("SansSerif", Font.BOLD, 14);
	    g2d.setFont(font);
	    String texte = "Position de la balle en mètre";
	    int textLarg = g2d.getFontMetrics().stringWidth(texte);
	    int xTexte = (getWidth() - textLarg) / 2; 
	    int yTexte = 20; 
	    g2d.drawString(texte, xTexte, yTexte);
		
	}
	/**
     * Méthode permettant de définir la position du composant sur le plan cartésien.
     * 
     * @param pos Nouvelle position du composant.
     */
	 //Benakmoum Walid
	public void setPosition(Vecteur2D pos) {
		this.position = pos;
		repaint();
	}
	/**
     * Méthode privée appelée pour créer la grille du plan cartésien.
     */
	//Benakmoum Walid
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
	/**
     * Méthode permettant de réinitialiser les lignes dessinées sur le plan cartésien.
     */
	//Benakmoum Walid
	public void reset() {
		ligne.reset();
		repaint();
	}
	/**
     * Méthode privée appelée pour créer les repères (taquets) sur le plan cartésien.
     * 
     * @param g2d Objet Graphics2D utilisé pour dessiner.
     */
	//Benakmoum Walid
	private void creerTaquets(Graphics2D g2d) {
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
