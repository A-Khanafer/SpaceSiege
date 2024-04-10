package obstacles;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
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
	private double pixelsParMetres;
	private double coinXGauche, coinYGauche;
	private double largeur, hauteur;
	private Path2D.Double triangle;
	private double angleRotation= 0 ;
	private double centreY;
	private double centreX;
	private Rectangle2D.Double rectanglePointille;
	private Area aireTri;
	private Ellipse2D.Double[] poigneRedimensionnement;
    private Ellipse2D.Double poigneRotation;
	private Area airePoigne;
	
	
	private Line2D.Double segmentHaut, segmentBas, segmentGauche, segmentDroite;
	private double coinXDroite, coinYDroite, coinXBasGauche, coinYBasGauche, coinXBasDroit, coinYBasDroit, sommetX, sommetY;
	private boolean estClique = false;
	/**
	 * Contruire un sapin en spécifiant les dimensions de son rectangle englobant imaginaire.
	 * On décide que le feuillage occupera les 5/6 de la hauteur du sapin, le reste pour le tronc
	 *  
	 * @param x Le x du coin supérieur-gauche du rectangle qui englobe le sapin
	 * @param y Le y du coin supérieur-gauche du rectangle qui englobe le sapin
	 * @param largeur La largeur du sapin
	 * @param hauteur La hauteur totale du sapin
	 */
	public Triangle(double x, double y, double largeur, double hauteur, double pixelsParMetres) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.pixelsParMetres = pixelsParMetres;
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= largeur * this.pixelsParMetres;
		this.hauteur = hauteur* this.pixelsParMetres; 
		poigneRedimensionnement = new Ellipse2D.Double[8];
		creerLaGeometrie();
	} //fin constructeur

	
	/**
	 * Méthode privée pour créer les formes qui composent le sapin
	 * Cette méthode doit être appelée de nouveau chaque fois que sa position ou dimension est modifiée
	 */
	private void creerLaGeometrie() {
		
		//calculer les dimensions qui dépendent des caractéristiques données au sapin dans son ensemble
		//on choisit des proportions plausibles pour un sapin!
		rectanglePointille = new Rectangle2D.Double(coinXGauche, coinYGauche, largeur, hauteur);
		centreX = rectanglePointille.getCenterX();
		centreY = rectanglePointille.getCenterY();
		triangle = new Path2D.Double();
		triangle.moveTo(coinXGauche, coinYGauche + hauteur);
		triangle.lineTo(coinXGauche+largeur, coinYGauche + hauteur);
		triangle.lineTo(coinXGauche+largeur/2, coinYGauche );
		triangle.closePath();
		aireTri = new Area(triangle);
		poigneRotation = new Ellipse2D.Double((coinXGauche+largeur/2) - 15, coinYGauche - 50, 30, 30);
        airePoigne = new Area(poigneRotation);
        AffineTransform transformation = new AffineTransform();
        transformation.rotate(angleRotation, centreX, centreY);
        airePoigne.transform(transformation);
		creationResizeHandles();
		calculerCoins();
		
	}
	
    private void creationResizeHandles() {
        double tailleHandle = 15; // Taille des poignées de redimensionnement

        // Création des poignées de redimensionnement
        poigneRedimensionnement[0] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2, coinYGauche - tailleHandle / 2,
                tailleHandle, tailleHandle); // En haut à gauche
        poigneRedimensionnement[1] = new Ellipse2D.Double(coinXGauche + largeur / 2 - tailleHandle / 2,
                coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle); // En haut au milieu
        poigneRedimensionnement[2] = new Ellipse2D.Double(coinXGauche + largeur - tailleHandle / 2,
                coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle); // En haut à droite
        poigneRedimensionnement[3] = new Ellipse2D.Double(coinXGauche + largeur - tailleHandle / 2,
                coinYGauche + hauteur / 2 - tailleHandle / 2, tailleHandle, tailleHandle); // Millieu droit
        poigneRedimensionnement[4] = new Ellipse2D.Double(coinXGauche + largeur - tailleHandle / 2,
                coinYGauche + hauteur - tailleHandle / 2, tailleHandle, tailleHandle); // En bas à droite
        poigneRedimensionnement[5] = new Ellipse2D.Double(coinXGauche + largeur / 2 - tailleHandle / 2,
                coinYGauche + hauteur - tailleHandle / 2, tailleHandle, tailleHandle); // En bas au milieu
        poigneRedimensionnement[6] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2,
                coinYGauche + hauteur - tailleHandle / 2, tailleHandle, tailleHandle); // En bas à gauche
        poigneRedimensionnement[7] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2,
                coinYGauche + hauteur / 2 - tailleHandle / 2, tailleHandle, tailleHandle); // Millieu gauche
    }

    private void calculerCoins() {

        Point2D.Double coinSupGauche = new Point2D.Double(coinXGauche, coinYGauche);
        Point2D.Double coinSupDroit = new Point2D.Double(coinXDroite, coinYDroite);
        Point2D.Double coinInfDroit = new Point2D.Double(coinXBasDroit, coinYBasDroit);
        Point2D.Double coinInfGauche = new Point2D.Double(coinXBasGauche, coinYBasGauche);
        Point2D.Double sommet = new Point2D.Double(sommetX, sommetY);
        AffineTransform rotation = AffineTransform.getRotateInstance(angleRotation, centreX, centreY);

        Point2D.Double[] coins = new Point2D.Double[5];
        rotation.transform(coinSupGauche, coins[0] = new Point2D.Double());
        rotation.transform(coinSupDroit, coins[1] = new Point2D.Double());
        rotation.transform(coinInfDroit, coins[2] = new Point2D.Double());
        rotation.transform(coinInfGauche, coins[3] = new Point2D.Double());
        rotation.transform(sommet, coins[4] = new Point2D.Double());

        
        segmentBas = new Line2D.Double(coins[3].getX(), coins[3].getY(), coins[2].getX(), coins[2].getY());
        segmentGauche = new Line2D.Double(coins[0].getX(), coins[0].getY(), coins[3].getX(), coins[3].getY());
        segmentDroite = new Line2D.Double(coins[1].getX(), coins[1].getY(), coins[2].getX(), coins[2].getY());
    }
    
	/**
	 * Dessiner le sapin.
	 * Cette méthode doit garder le contexte graphique g2d intacte, car possiblement d'autres 
	 * objets l'utiliseront par la suite.
	 * 
	 * @param g2d Le contexte graphique du composant sur lequel on dessine
	 */
	public void dessiner(Graphics2D g2d) {
		
		Graphics2D g2dCopy = (Graphics2D) g2d.create();
		g2dCopy.setColor(Color.yellow);
		g2dCopy.rotate(angleRotation, centreX, centreY);
		g2dCopy.fill(triangle);

        if (estClique) {
            BasicStroke pointille = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[]{4}, 0); // Création de la ligne pointillée
            g2dCopy.setStroke(pointille);
            g2dCopy.setColor(Color.black);
            g2dCopy.draw(rectanglePointille);
            g2dCopy.setColor(Color.red);
            
            for (Ellipse2D.Double handle : poigneRedimensionnement) {
                g2dCopy.fill(handle);
            }
        } 
	}
	
	


	@Override
	public void redimension(int index, int eX, int eY) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void rotate(int eX, int eY) {
		if (eX <= centreX) {
            double longueurCoteOppose = eX - centreX;
            double longueurCoteAdjacent = centreY - eY;
            angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
        } else {
            double longueurCoteOppose = eX - centreX;
            double longueurCoteAdjacent = centreY - eY;
            angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
        }
		System.out.println(angleRotation);
        creerLaGeometrie();
		
	}


	@Override
	public void move(int eX, int eY) {
		this.coinXGauche = eX - largeur / 2;
        this.coinYGauche = eY - hauteur / 2;
        creerLaGeometrie();
	}


	/**
     * Méthode pour déterminer si le rectangle est sélectionné.
     *
     * @return true si le rectangle est sélectionné, sinon false.
     */
    //Ahmad Khanafer
    public boolean isClickedOnIt() {
        return estClique;
    }

    /**
     * Méthode pour définir l'état de sélection du rectangle.
     *
     * @param clickedOnIt true pour sélectionner le rectangle, sinon false.
     */
    //Ahmad Khanafer
    public void setClickedOnIt(boolean clickedOnIt) {
        this.estClique = clickedOnIt;
    }
    
	@Override
	public boolean contient(double xPix, double yPix) {
		return aireTri.contains(xPix, yPix);
	}
	
    /**
     * Méthode pour obtenir l'index de la poignée de redimensionnement située à une position donnée.
     *
     * @param x La coordonnée en X de la position.
     * @param y La coordonnée en Y de la position.
     * @return L'index de la poignée de redimensionnement, ou -1 s'il n'y a aucune poignée à cette position.
     */
    //Ahmad Khanafer
    public int getClickedResizeHandleIndex(double x, double y) {
        for (int i = 0; i < poigneRedimensionnement.length; i++) {
            if (poigneRedimensionnement[i].contains(x, y)) {
                return i;
            }
        }
        return -1; // Aucune poignée de redimensionnement trouvée à cette position
    }
	
}
