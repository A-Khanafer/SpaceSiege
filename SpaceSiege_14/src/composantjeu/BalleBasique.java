package composantjeu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;

import physique.MoteurPhysique;
import physique.Vecteur2D;

/**
 * La classe BalleBasique représente une balle simple dans une simulation physique.
 * Elle hérite de la classe Balle et permet de dessiner la balle, de la faire avancer et de gérer ses collisions avec les murs.
 * @author Benakmoum Walid
 * 
 */
public class BalleBasique extends Balle{
	
    /**
     * Constructeur de BalleBasique initialisant la balle avec des propriétés spécifiques.
     *
     * @param masseDonne La masse de la balle.
     * @param chargeDonne La charge de la balle (non utilisée dans cette implémentation).
     * @param diametreDonne Le diamètre de la balle.
     * @param position La position initiale de la balle.
     * @param vitesse La vitesse initiale de la balle.
     */
	//Benakmoum Walid
    public BalleBasique(int masseDonne, int chargeDonne, double diametreDonne, Vecteur2D position, Vecteur2D vitesse,double pixelsParMetre) {
        super(masseDonne, chargeDonne, diametreDonne, position, vitesse,pixelsParMetre);
        creerLaGeometrie();
    }

    /**
     * Crée la géométrie de la balle pour le dessin, en se basant sur sa position et son diamètre.
     */
  //Benakmoum Walid
    public void creerLaGeometrie() {
        cercle = new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre);
    }

	
	
    /**
     * Dessine la balle sur le composant graphique fourni.
     *
     * @param g2d L'objet Graphics2D utilisé pour dessiner la balle.
     */
  //Benakmoum Walid
    public void dessiner(Graphics2D g2d) {
        Graphics2D g2dPrive = (Graphics2D) g2d.create();
        Random random = new Random();
        creerEffetVent(g2dPrive);

        synchronized (trainees) {
            float alpha = 1.0f, facteurDecroissance = 0.1f;
            for (Ellipse2D.Double trace : trainees) {
            	Color couleurDebut = Color.MAGENTA;
                Color couleurFin = Color.BLUE; 
                GradientPaint gradient = new GradientPaint(
                        (float) trace.x, (float) trace.y, couleurDebut,
                        (float) (trace.x + trace.width), (float) (trace.y + trace.height), couleurFin);

              
                g2dPrive.setPaint(gradient);
                g2dPrive.fill(trace);
                alpha -= facteurDecroissance;
                if (alpha < 0) break;
            }
        }
        Color couleurDebut = Color.MAGENTA; 
        Color couleurFin = Color.BLUE; 
        GradientPaint gradient = new GradientPaint(
                (float) position.getX(), (float) position.getY(), couleurDebut,
                (float) (position.getX() + diametre), (float) (position.getY() + diametre), couleurFin);

        
        g2dPrive.setPaint(gradient);
        g2dPrive.fill(cercle);

        g2dPrive.dispose();
    }
    private void creerEffetVent(Graphics2D g2d) {
        double angle = Math.atan2(vitesse.getY(), vitesse.getX());
        double magnitudeVitesse = vitesse.module() / 3;

        double xCentre = position.getX() + diametre / 2;
        double yCentre = position.getY() + diametre / 2;
        double yCentreHaut = position.getY();
        double yCentreBas = position.getY() + diametre;

        double longueurBase = 20 + magnitudeVitesse * 2;

        int nombreLignes = 3; 

        for (int i = 0; i < nombreLignes; i++) {
            double longueur = longueurBase + 5 * i;
            double xFin = xCentre - longueur * Math.cos(angle);
            double yFinHaut = yCentreHaut - longueur * Math.sin(angle);
            double yFinCentre = yCentre - longueur * Math.sin(angle);
            double yFinBas = yCentreBas - longueur * Math.sin(angle);

            g2d.setStroke(new BasicStroke(1.0f + 0.1f * i, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(255, 255, 255, 100 - i * 10));

            g2d.draw(new Line2D.Double(xCentre, yCentreHaut, xFin, yFinHaut));

            g2d.draw(new Line2D.Double(xCentre, yCentre, xFin, yFinCentre));

            g2d.draw(new Line2D.Double(xCentre, yCentreBas, xFin, yFinBas));
        }
    }
    /**
     * Définit le ratio de pixels par mètre pour la balle. Cette méthode peut être utilisée pour ajuster la taille de la balle à l'échelle du dessin.
     *
     * @param pixelsParMetre Le nouveau ratio de pixels par mètre.
     */
  //Benakmoum Walid
    public void setPixelsParMetre(double pixelsParMetre) {
        this.pixelsParMetre = pixelsParMetre;
    }

    /**
     * Fait avancer la balle d'un pas dans le temps, en utilisant la physique pour mettre à jour sa position et sa vitesse.
     *
     * @param deltaT Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
  //Benakmoum Walid
    public void avancerUnPas(double deltaT) {
        synchronized (trainees) {
            if (trainees.size() >= MAX_TRAÎNÉES) {
            	trainees.removeFirst();
            }
            trainees.add(new Ellipse2D.Double(position.getX(), position.getY(), diametre, diametre));
        }
        vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
        position = MoteurPhysique.calculPosition(deltaT, position, vitesse);
        creerLaGeometrie();
    }


	 public void stop() {
	        running = false;
	    }
	 public int quelleTypeBalle() {
	    	return 1;
	    }
}


