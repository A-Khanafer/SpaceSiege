package composantjeu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import interfaces.Dessinable;
/**
 * Classe représentant une flèche utilisée pour indiquer la direction et la force de tir.
 * @author Benakmoum Walid
 */
public class FlecheDeTir implements Dessinable {
	/**
     * Coordonnée X du point de départ de la flèche.
     */
    private double x1, y1;

    /**
     * Coordonnées X et Y du point final de la flèche, initialement à 0.
     */
    private double x2 = 0, y2 = 0;

    /**
     * Représente le corps principal de la flèche et les deux traits formant la tête de la flèche.
     */
    private Line2D.Double corps, traitDeTete, deuxiemeTraitDeTete;

    /**
     * Angle formé par les traits de la tête de la flèche par rapport au corps, fixé à 45 degrés.
     */
    private double angleTete = 45;

    /**
     * Longueur des traits formant la tête de la flèche, fixée à 8 unités.
     */
    private double longueurTraitDeTete = 8;

    /**
     * Facteur de mise à l'échelle pour le dessin de la flèche, permettant d'ajuster sa taille à l'affichage, initialement à 1.
     */
    private double pixelsParMetre = 1;

    /**
     * Angle actuel de la flèche en radians, calculé à partir de ses coordonnées.
     */
    private double angleRadians;

    /**
     * Rotation appliquée à la flèche, permettant de modifier son orientation.
     */
    private double rotation;


 /**
  * Constructeur de la classe.Permet de créer la fléche
  * @param x1 position initial en x
  * @param y1 position initial en y
  * @param dx difference entre la position initial x en final
  * @param dy difference entre la position initial y en final
  */
    //Benakmoum Walid
	public FlecheDeTir(double x1, double y1, double dx, double dy) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + dx;
		this.y2 = y1 + dy;

		creerLaGeometrie();
	}
	/**
	 * Crée la géométrie de la flèche, incluant son corps et sa tête, basée sur les coordonnées actuelles.
	 */
	  //Benakmoum Walid
	public void creerLaGeometrie() {
		corps = new Line2D.Double(x1, y1, x2, y2);
		double longueurFleche = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double dxPetitTrait = longueurTraitDeTete * (x2 - x1) / longueurFleche;
		double dyPetitTrait = longueurTraitDeTete * (y2 - y1) / longueurFleche;
		double x3 = x2 - dxPetitTrait;
		double y3 = y2 - dyPetitTrait;
		traitDeTete = new Line2D.Double(x2, y2, x3, y3);
		deuxiemeTraitDeTete = new Line2D.Double(x2, y2, x2 + dyPetitTrait, y2 + dxPetitTrait);
	}

	/**
	 * Dessine la flèche sur un objet Graphics2D fourni, en utilisant une transformation affine pour gérer l'échelle de dessin.
	 *
	 * @param g2d L'objet Graphics2D sur lequel la flèche sera dessinée.
	 */
	@Override
	  //Benakmoum Walid
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.red);

		mat.scale(pixelsParMetre, pixelsParMetre);

		g2d.draw(mat.createTransformedShape(corps)); 

		mat.rotate(Math.toRadians(angleTete), x2, y2);
		g2d.draw(mat.createTransformedShape(traitDeTete));
		g2d.draw(mat.createTransformedShape(deuxiemeTraitDeTete));

	}
	/**
	 * Définit le point initial de la flèche avec les coordonnées spécifiées et recrée sa géométrie.
	 * 
	 * @param x1 La coordonnée X du point initial.
	 * @param y1 La coordonnée Y du point initial.
	 */
	  //Benakmoum Walid
	public void setPointInitial(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
		creerLaGeometrie();
	}
	/**
	 * Définit le point final de la flèche avec les coordonnées spécifiées et recrée sa géométrie.
	 * 
	 * @param x2 La coordonnée X du point final.
	 * @param y2 La coordonnée Y du point final.
	 */
	  //Benakmoum Walid
	public void setPointFinal(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
		creerLaGeometrie();
	}
	/**
	 * Définit la rotation de la flèche. Cette méthode pourrait être utilisée pour ajuster l'orientation de la flèche en fonction de certains calculs ou interactions utilisateur.
	 * 
	 * @param rotation La nouvelle valeur de rotation de la flèche.
	 */
	  //Benakmoum Walid
	public void setRotation(double rotation) {
		this.rotation = rotation;

	}
	// Documentation des autres méthodes publiques...

	/**
	 * Calcule l'angle de la flèche par rapport à l'axe horizontal.
	 *
	 * @return L'angle de la flèche en radians.
	 */
	  //Benakmoum Walid
	public double getAngle() {

		double dx = x2 - x1;
		double dy = y2 - y1;

		angleRadians = Math.atan2(dy, dx);

		return angleRadians;
	}
	/**
	 * Calcule la longueur (ou le module) de la flèche, qui peut être interprétée comme la force du tir.
	 *
	 * @return La longueur de la flèche.
	 */
	  //Benakmoum Walid
	public double calculerModulus() {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}
	/**
	 * Calcule la composante horizontale (X) de la flèche basée sur son angle et sa longueur.
	 *
	 * @return La composante X de la flèche.
	 */
	  //Benakmoum Walid
	public double calculerComposantX() {

		double modulus = calculerModulus();


		return modulus * Math.cos(angleRadians);
	}
	/**
	 * Calcule la composante verticale (Y) de la flèche basée sur son angle et sa longueur.
	 *
	 * @return La composante Y de la flèche.
	 */
	  //Benakmoum Walid
	public double calculerComposantY() {

		double modulus = calculerModulus();

		return modulus * Math.sin(angleRadians);
	}
	/**
	 * Calcule la différence en X entre le point final et le point initial de la flèche.
	 * 
	 * @return La différence en X (dx).
	 */
	  //Benakmoum Walid
	public double getDx() {
		return x2-x1;
	}
	/**
	 * Calcule la différence en Y entre le point final et le point initial de la flèche.
	 * 
	 * @return La différence en Y (dy).
	 */
	  //Benakmoum Walid
	public double getDy() {
		return y2-y1;
	}
	/**
	 * Définit la différence en X pour la flèche. Cette méthode pourrait être implémentée pour ajuster la longueur et la direction de la flèche basée sur un déplacement en X.
	 * 
	 * @param ex La nouvelle différence en X.
	 */
	  //Benakmoum Walid
	public void setDx(double ex) {

	}
	/**
	 * Retourne la coordonnée X du point initial de la flèche.
	 * 
	 * @return La coordonnée X du point initial.
	 */
	  //Benakmoum Walid
	public double getX1() {
		return this.x1;
	}
	/**
	 * Retourne la coordonnée Y du point initial de la flèche.
	 * 
	 * @return La coordonnée Y du point initial.
	 */
	  //Benakmoum Walid
	public double getY1() {
		return this.y1;
	}

}