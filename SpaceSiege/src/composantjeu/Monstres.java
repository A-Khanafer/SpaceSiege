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
	 * La classe Monstres représente un élément de jeu interactif qui peut être dessiné sur un JPanel.
	 * Elle est utilisée pour créer des monstres qui peuvent être éliminés dans le jeu.
	 * @author zakaria soudaki
	 */
	
	 private static final long serialVersionUID = 1L;
	 
	    /** Le rectangle représentant le monstre **/
	    private Rectangle2D rec;
	    
	    /** L'image du monstre **/
	    private Image imgDecor = null;
	    
	    /** La position en X du monstre **/
	    private int posX;
	    
	    /** La position en Y du monstre **/
	    private int posY;
	    
	    /** La largeur du rectangle représentant le monstre **/
	    private int largeurRectangle = 125; // Exemple : largeur du rectangle
	    
	    /** La hauteur du rectangle représentant le monstre **/
	    private int hauteurRectangle = 125; // Exemple : hauteur du rectangle
	    
	    /** La zone d'air du monstre, utilisée pour les collisions **/
	    private Area air;

	    /**
		 * Constructeur de la classe Monstres.
		 * @param posX La position en X du monstre.
		 * @param posY La position en Y du monstre.
		 * @param nomImage Le nom de l'image à utiliser pour représenter le monstre.
		 **/
	    //Zakaria Soudaki
	    public Monstres(int posX, int posY, String nomImage) {
	        this.posX = posX;
	        this.posY = posY;
	       imgDecor = OutilsImage.lireImage(nomImage); 
	       
	       creerLaGeometrie();
	    }

	    /**
		 * Initialise la géométrie du monstre.
		 **/
	    //Zakaria Soudaki
	    private void creerLaGeometrie() {
	        rec = new Rectangle(posX,posY,largeurRectangle,hauteurRectangle);
	        air = new Area(rec);
	       
	    }

	    /**
		 * Dessine le monstre sur le composant graphique spécifié.
		 * @param g2d Le contexte graphique dans lequel dessiner le monstre.
		 **/
	    //zakaria soudaki
	    public void dessiner(Graphics2D g2d) {
	        g2d.setColor(Color.GREEN);
	        g2d.fill(rec); // Dessiner le rectangle avec l'image
	        g2d.drawImage( imgDecor,  posX,  posY,  largeurRectangle,  hauteurRectangle,  null);


	    }
	    
	    /**
		 * Récupère la zone d'air du monstre, utilisée pour les collisions.
		 * @return La zone d'air du monstre.
		 **/
	    //zakaria soudaki
	    public Area getArea() {
	    	return this.air;
	    }
	    
	    /**
		 * Récupère le rectangle représentant le monstre.
		 * @return Le rectangle représentant le monstre.
		 **/
	    //zakaria soudaki
	    public Rectangle2D getRec() {
	    	return this.rec;
	    }
	   
}
