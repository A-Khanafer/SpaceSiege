package composantdessin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import outils.OutilsImage;
import java.awt.Color;

/**
 * Classe qui permet d'afficher un titre animer qui bouge de haut vers le bas selon la hauteur de la fonction sinus(x)
 * @author ZAKARIA SOUDAKI
 *
 */
public class Titre extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * image du titre
	 */
	private Image imgTitre = null;
	/**
	 * image des étoile autour du titre
	 */
    private Image imgEtoile= null;
    /**
     * coefficient de mouvement du titre
     */
    private  int MOUVEMENT = 5;
    /**
     * boolean début animation
     */
    private boolean start = false;
    /**
     * longueur image étoile
     */
    private int longueurEtoiles ;
    /**
     * hauteur image étoile
     */
    private int hauteurEtoile ;
    /**
     * longueur image titre
     */
    private int longueurTitre ;
    /**
     * hauteur image titre
     */
    private int hauteurTitre ;
    
	/**
	 * Create the panel.
	 */
    //ZAKARIA SOUDAKI
	public Titre(String etoiles, String titre, int longueurImg, int hauteurImg , int longueurEtoiles, int hauteurEtoile) {
		 
		 imgTitre = OutilsImage.lireImage(titre); 
	     imgEtoile= OutilsImage.lireImage(etoiles);
	     longueurTitre = longueurImg;
	     hauteurTitre = hauteurImg;
	     this.longueurEtoiles = longueurEtoiles;
	     this.hauteurEtoile = hauteurEtoile;
	     setOpaque(false);
	     demarrer();
	}
	/**
	 * méthode pour déssiner le titre
	 * @param g2d objet dessin
	 * @param mouvement coefficient mouvement
	 */
	//ZAKARIA SOUDAKI
	private void dessiner(Graphics2D g2d, int mouvement) {
		
        g2d.drawImage( imgEtoile,  getWidth()/2- longueurEtoiles/2, getHeight()/2 -hauteurTitre+35  - MOUVEMENT, longueurEtoiles,  hauteurEtoile,  null);
        g2d.drawImage( imgTitre,  getWidth()/2-longueurTitre/2, getHeight()/2-hauteurTitre/2 + MOUVEMENT,  longueurTitre,  hauteurTitre,  null);

	}
	/**¸
	 * méthode pour executer le dessin lui-même
	 */
	//ZAKARIA SOUDAKI
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		dessiner(g2d, MOUVEMENT);
	
		
	}
	@Override
	/**
	 * méthode d'animation du titre
	 */
	//ZAKARIA SOUDAKI
	public void run() {
		
		double y = 0;
		double x = 0;
		
		while(start) {
			
			  y =  Math.sin(x); 
			  
			  y=  y*10;
			  
			  MOUVEMENT = (int) y;
			  
	            x += 0.1; 
	           
			repaint();
			
			try {
				
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
		}
		
	}
	/**
	 * méthode démarrer l'animation
	 */
	//ZAKARIA SOUDAKI
	public void demarrer() {
		if (!start) {
			Thread proc = new Thread(this);
			proc.start();
			start = true;
		}
	}//fin
	

}

