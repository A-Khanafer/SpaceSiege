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
    
	/**
	 * Create the panel.
	 */
	public Titre(String etoiles, String titre) {
		setBackground(new Color(0, 0, 0,0));
		 imgTitre = OutilsImage.lireImage(titre); 
	     imgEtoile= OutilsImage.lireImage(etoiles);
	   
	     demarrer();
	}
	private void dessiner(Graphics2D g2d, int mouvement) {
		
        g2d.drawImage( imgEtoile,  getWidth()/2-160, getHeight()/2-150 - MOUVEMENT, 320,  320,  null);
        g2d.drawImage( imgTitre,  getWidth()/2-160, getHeight()/2-100 + MOUVEMENT,  320,  200,  null);

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
			  
			  y=  y*12;
			  
			  MOUVEMENT = (int) y;
			  
	            x += 0.1; 
	            
			repaint();
			
			try {
				
				Thread.sleep(50);
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
