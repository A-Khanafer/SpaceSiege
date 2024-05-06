package physique;

import javax.swing.JPanel;

import composantjeu.Monstres;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;

public class ForcesMonstre extends JPanel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Monstres monstre;
	
	private Vecteur2D vitesse;
	
	private Vecteur2D accel;
	
	private Vecteur2D sommeForces;
	
	private int diametre = 20;

    public ForcesMonstre() {
       
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        // Calculer le point central du panel
        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;
        
        // Dessiner un point noir au centre
        g2d.setColor(Color.RED);
        g2d.fillOval(centreX , centreY , diametre, diametre);

       
        if (vitesse != null) {
            dessinerVecteur(g2d, new Vecteur2D(centreX, centreY), vitesse, Color.BLUE);
        }

      
//        if (accel != null) {
//            dessinerVecteur(g2d, new Vecteur2D(centreX, centreY), accel, Color.GREEN);
//        }
        
        if (sommeForces != null) {
        	dessinerVecteur(g2d, new Vecteur2D(centreX, centreY), sommeForces, Color.GREEN);
        }
    }

    private void dessinerVecteur(Graphics2D g2d, Vecteur2D centre, Vecteur2D vecteur, Color couleur) {
        double scale = 2; // Ajuster pour contrôler la longueur des flèches
        
        double finX = centre.getX() + vecteur.getX() * scale;
        double finY = centre.getY() + vecteur.getY() * scale;
        g2d.setColor(couleur);
        Line2D.Double ligne = new Line2D.Double(centre.getX()+diametre/2, centre.getY()+diametre/2, finX, finY);
        g2d.draw(ligne);
        dessinerFleche(g2d, ligne);
    }

    private void dessinerFleche(Graphics2D g2d, Line2D.Double ligne) {
    	
        double angle = Math.atan2(ligne.y2 - ligne.y1, ligne.x2 - ligne.x1);
        int longueur = 10; // Longueur des bras de la flèche
        
        Line2D.Double bras1 = new Line2D.Double(ligne.x2, ligne.y2,
        		
                ligne.x2 - longueur * Math.cos(angle - Math.PI / 6),
                ligne.y2 - longueur * Math.sin(angle - Math.PI / 6));
        
        Line2D.Double bras2 = new Line2D.Double(ligne.x2, ligne.y2,
        		
                ligne.x2 - longueur * Math.cos(angle + Math.PI / 6),
                ligne.y2 - longueur * Math.sin(angle + Math.PI / 6));
        g2d.draw(bras1);
        g2d.draw(bras2);
    }

	public void setVal(Vecteur2D vitesse, Vecteur2D accel, Vecteur2D sommeForces) {
		    this.vitesse = vitesse;
	        this.accel = accel;
	        this.sommeForces = sommeForces;
	        repaint();
		
	}
}