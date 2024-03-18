package composantjeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import outils.OutilsImage;

public class Monstres extends JPanel{
	
	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	    private Rectangle2D rec;
	    private Image imgDecor = null;
	    private int posX;
	    private int posY;
	    private int largeurRectangle = 125; // Exemple : largeur du rectangle
	    private int hauteurRectangle = 125; // Exemple : hauteur du rectangle
	    private Area air;

	    public Monstres(int posX, int posY, String nomImage) {
	        this.posX = posX;
	        this.posY = posY;
	       imgDecor = OutilsImage.lireImage(nomImage); 
	       
	       creerLaGeometrie();
	    }

	    
	    private void creerLaGeometrie() {
	        rec = new Rectangle(posX,posY,largeurRectangle,hauteurRectangle);
	        air = new Area(rec);
	       
	    }

	    public void dessiner(Graphics2D g2d) {
	        g2d.setColor(Color.GREEN);
	        g2d.fill(rec); // Dessiner le rectangle avec l'image
	        g2d.drawImage( imgDecor,  posX,  posY,  largeurRectangle,  hauteurRectangle,  null);


	    }
	    public Area getArea() {
	    	return this.air;
	    }
	    public Rectangle2D getRec() {
	    	return this.rec;
	    }
	   
}
