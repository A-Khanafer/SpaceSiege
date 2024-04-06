package obstacles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;



/**
 * La classe Triangle est un exemple d'objet dessinable simple et rempli d'une couleur uniforme.
 * 
 * 
 * @author Caroline Houle
 *
 */
public class Triangle implements Dessinable, Obstacles, Selectionnable {
	private double coinXGauche, coinYGauche;
	private double largeur, hauteur;
	private Path2D.Double triangle;
	private double angleRotation;
	private int centreY;
	private int centreX;
	private Rectangle2D.Double recExterieur;
	private Area aireTri;
	
	
	
	private Line2D.Double segmentHaut, segmentBas, segmentGauche, segmentDroite;
	private double coinXDroite, coinYDroite, coinXBasGauche, coinYBasGauche, coinXBasDroit, coinYBasDroit;
	/**
	 * Contruire un sapin en spécifiant les dimensions de son rectangle englobant imaginaire.
	 * On décide que le feuillage occupera les 5/6 de la hauteur du sapin, le reste pour le tronc
	 *  
	 * @param x Le x du coin supérieur-gauche du rectangle qui englobe le sapin
	 * @param y Le y du coin supérieur-gauche du rectangle qui englobe le sapin
	 * @param largeur La largeur du sapin
	 * @param hauteur La hauteur totale du sapin
	 */
	public Triangle(double x, double y, double largeur, double hauteur) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= largeur;
		this.hauteur = hauteur; 
		
		creerLaGeometrie();
	} //fin constructeur

	
	/**
	 * Méthode privée pour créer les formes qui composent le sapin
	 * Cette méthode doit être appelée de nouveau chaque fois que sa position ou dimension est modifiée
	 */
	private void creerLaGeometrie() {
		
		//calculer les dimensions qui dépendent des caractéristiques données au sapin dans son ensemble
		//on choisit des proportions plausibles pour un sapin!
		recExterieur = new Rectangle2D.Double(coinXGauche, coinYGauche, largeur, hauteur);
		triangle = new Path2D.Double();
		triangle.moveTo(coinXGauche, coinYGauche + hauteur);
		triangle.lineTo(coinXGauche+largeur, coinYGauche + hauteur);
		triangle.lineTo(coinXGauche+largeur/2, coinYGauche );
		triangle.closePath();
		aireTri = new Area(triangle);
			
	}

	/**
	 * Dessiner le sapin.
	 * Cette méthode doit garder le contexte graphique g2d intacte, car possiblement d'autres 
	 * objets l'utiliseront par la suite.
	 * 
	 * @param g2d Le contexte graphique du composant sur lequel on dessine
	 */
	public void dessiner(Graphics2D g2d) {

		g2d.fill(triangle);
		g2d.draw(recExterieur);

	}
	
	/**
	 * Retourner une chaine de caractères avec les caractéristiques du sapin.
	 * Méthode utile pour débugger.
	 * 
	 * @return Une chaine contenant la position et les dimensions du sapin
	 */
	@Override
	public String toString() {
		return ("Triangle : Le coin est à (" + coinXGauche + ", " + coinYGauche + "). Sa largeur est de " + largeur + " et sa hauteur est de " + hauteur);
	}


	/**
	 * Retourner la coordonnée en X du coin supérieur-gauche du rectangle qui entoure le sapin
	 * @return La corodonnée en X du coin supérieur-gauche du rectangle qui entoure le sapin
	 */
	public double getX() {
		return coinXGauche;
	}

	/**
	 * Modifier la coordonnée en X du coin supérieur-gauche du rectangle qui englobe le sapin
	 *  La géométrie sera recréée suite à ce changement.
	 * @param x La nouvelle coordonnée en X du coin supérieur-gauche du rectangle qui englobe le sapin
	 */
	public void setX(double x) {
		this.coinXGauche = x;
		creerLaGeometrie();
	}

	/**
	 * Retourne la coordonnée en Y du coin supérieur-gauche du rectangle qui englobe le sapin
	 * @return La coordonnée en Y du coin supérieur-gauche du rectangle qui englobe le sapin
	 */
	public double getY() {
		return coinYGauche;
	}

	/**
	 * Modifier la coordonnée en Y du coin supérieur-gauche du rectangle qui englobe le sapin
	 * La géométrie sera recréée suite à ce changement.
	 * @param y La nouvelle coordonnée en Y du coin supérieur-gauche du rectangle qui englobe le sapin
	 */
	public void setY(double y) {
		this.coinYGauche = y;
		creerLaGeometrie();
	}

	/**
	 * Retourne la largeur du sapin
	 * @return La largeur du sapin
	 */
	public double getLargeur() {
		return largeur;
	}

	/**
	 * Modifier la largeur du sapin
	 * La géométrie sera recréée suite à ce changement.
	 * @param largeur La nouvelle largeur du sapin
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	/**
	 * Retourner la hauteur du sapin
	 * @return La hauteur du sapin
	 */
	public double getHauteur() {
		return hauteur;
	}

	/**
	 * Modifier la hauteur du sapin
	 * La géométrie sera recréée suite à ce changement.
	 * @param hauteur La nouvelle hauteur du sapin
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}


	@Override
	public void redimension(int index, int eX, int eY) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void rotate(int eX, int eY) {
		if (eX <= centreX) {
            double longueurCoteOppose = eX - centreX;
            if (eY < centreY) {
                double longueurCoteAdjacent = centreY - eY;
                angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
            } else {
                double longueurCoteAdjacent = centreY - eY;
                angleRotation = -Math.PI + Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
            }
        } else {
            double longueurCoteOppose = eX - centreX;
            if (eY < centreY) {
                double longueurCoteAdjacent = centreY - eY;
                angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
            } else {
                double longueurCoteAdjacent = eY - centreY;
                angleRotation = Math.PI - Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
            }
        }
        creerLaGeometrie();
		
	}


	@Override
	public void move(int eX, int eY) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean contient(double xPix, double yPix) {
		return aireTri.contains(xPix, yPix);
	}

	
}
