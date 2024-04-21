package composantjeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physique.MoteurPhysique;
import physique.Vecteur2D;

/**
 * La classe BalleBasique représente une balle simple dans une simulation physique.
 * Elle hérite de la classe Balle et permet de dessiner la balle, de la faire avancer et de gérer ses collisions avec les murs.
 * @author Benakmoum Walid
 * @author ZAKARIA SOUDAKI
 */
public class BalleBasique extends Balle {

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
        g2dPrive.setColor(Color.MAGENTA);
        g2dPrive.fill(cercle);
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

        vitesse = MoteurPhysique.calculVitesse(deltaT, vitesse, accel);
        position = MoteurPhysique.calculPosition(deltaT, position, vitesse);

        creerLaGeometrie();
    }

   
}

