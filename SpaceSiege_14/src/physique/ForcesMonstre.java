package physique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ForcesMonstre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	    private int centerX;
	    private int centerY;
	    private Vecteur2D vitesse;
	    private Vecteur2D acceleration;
	    private Vecteur2D sommeDesForces;

	    public ForcesMonstre(Vecteur2D vitesse, Vecteur2D acceleration, Vecteur2D sommeDesForces) {
	        this.vitesse = vitesse;
	        this.acceleration = acceleration;
	        this.sommeDesForces = sommeDesForces;
	        
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        
	        // Calcul du centre du JPanel
	        centerX = getWidth() / 2;
	        centerY = getHeight() / 2;

	        // Dessiner un point central noir
	        g2d.setColor(Color.BLACK);
	        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);

	        // Dessiner les vecteurs
	        drawVector(g2d, vitesse, Color.RED, "Vitesse");
	        drawVector(g2d, acceleration, Color.BLUE, "Accélération");
	        drawVector(g2d, sommeDesForces, Color.GREEN, "Somme des Forces");
	    }

	    private void drawVector(Graphics2D g2d, Vecteur2D vector, Color color, String label) {
	        g2d.setColor(color);
	        int endX = centerX + (int) (vector.getX() * 100); // Scale by 100 for visibility
	        int endY = centerY - (int) (vector.getY() * 100); // Negative because y-axis is inverted in GUIs
	        g2d.drawLine(centerX, centerY, endX, endY);
	        g2d.drawString(label + " (" + vector.getX() + ", " + vector.getY() + ")", endX, endY);
	    }

	    public void setVitesse(Vecteur2D vitesse) {
	        this.vitesse = vitesse;
	        repaint();
	    }

	    public void setAcceleration(Vecteur2D acceleration) {
	        this.acceleration = acceleration;
	        repaint();
	    }

	    public void setSommeDesForces(Vecteur2D sommeDesForces) {
	        this.sommeDesForces = sommeDesForces;
	        repaint();
	    }
	
}
