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

public class Titre extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image imgTitre = null;
    private Image imgEtoile= null;
    private  int MOUVEMENT = 5;
    private boolean start = false;
    private int longueurEtoiles ;
    private int hauteurEtoile ;
    private int longueurTitre ;
    private int hauteurTitre ;
    
	/**
	 * Create the panel.
	 */
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
	private void dessiner(Graphics2D g2d, int mouvement) {
		
        g2d.drawImage( imgEtoile,  getWidth()/2- longueurEtoiles/2, getHeight()/2 -hauteurTitre+35  - MOUVEMENT, longueurEtoiles,  hauteurEtoile,  null);
        g2d.drawImage( imgTitre,  getWidth()/2-longueurTitre/2, getHeight()/2-hauteurTitre/2 + MOUVEMENT,  longueurTitre,  hauteurTitre,  null);

	}
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		dessiner(g2d, MOUVEMENT);
	
		
	}
	@Override
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
	public void demarrer() {
		if (!start) {
			Thread proc = new Thread(this);
			proc.start();
			start = true;
		}
	}//fin
	

}

