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
 * @author Ahmad Khanafer
 */
public class Triangle implements Dessinable, Obstacles, Selectionnable {
	/**
     * Le nombre de pixels par mètre.
     */
	private double pixelsParMetres;
	private double coinXGauche, coinYGauche;
	/**
     * La largeur et hauteur du triangle en mètre.
     */
	private double largeur, hauteur;
	/**
     * La forme du triangle.
     */
	private Path2D.Double triangle;
	/**
     * La zone délimitée par le rectangle.
     */
	private Area aireTri;
	 /**
     * L'angle de rotation du rectangle.
     */
	private double angleRotation= 0 ;
	/**
     * Approximation des coordonnées du centre du triangle.
     */
	private double centreY, centreX;
	/**
     * La zone délimitée par le triangle pour les transformations.
     */
	private Rectangle2D.Double rectanglePointille;
	/**
     * Les poignées de redimensionnement du rectangle.
     */
	private Ellipse2D.Double[] poigneRedimensionnement;
	/**
     * La poignée de rotation du triangle.
     */
    private Ellipse2D.Double poigneRotation;
    /**
     * Aire de la poignée de rotation du triangle.
     */
	private Area airePoigne;
	/**
     * Les segments du triangles.
     */
	private Line2D.Double segmentBas, segmentGauche, segmentDroite;
	/**
     * Les coordonnées des coins du rectangle pointillé.
     */
	private double coinXDroite, coinYDroite, coinXBasGauche, coinYBasGauche, coinXBasDroit, coinYBasDroit, sommetX, sommetY;
	/**
     * Indique si le rectangle est sélectionné.
     */
	private boolean estClique = false;
	/**
	 * Contruire un triangle en spécifiant les dimensions de son rectangle englobant imaginaire.
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

	
	// Méthode privée pour initialiser la géométrie du triangle
    //Ahmad Khanafer
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
	// Méthode privée pour créer les poignées de redimensionnement du rectangle
    //Ahmad Khanafer
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
    //Méthode privée qui calcule les 4 coins en rotation et dessine les segments pour permettre la collisions.
    //Ahmad Khanafer
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
  //Ahmad Khanafer
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
	/**
     * Méthode pour redimensionner le rectangle en fonction de la poignée de redimensionnement sélectionnée.
     *
     * @param index L'index de la poignée de redimensionnement sélectionnée.
     * @param eX    La position en X de la souris.
     * @param eY    La position en Y de la souris.
     */
	//Ahmad Khanafer
	@Override
	public void redimension(int index, int eX, int eY) {
		// TODO Auto-generated method stub
		
	}
	/**
     * Méthode pour effectuer la rotation du rectangle en fonction de la position de la souris.
     *
     * @param eX La position en X de la souris.
     * @param eY La position en Y de la souris.
     */
    //Ahmad Khanafer
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

	/**
     * Méthode pour déplacer le rectangle en fonction de la position de la souris.
     *
     * @param eX La position en X de la souris.
     * @param eY La position en Y de la souris.
     */
    //Ahmad Khanafer
	@Override
	public void move(int eX, int eY) {
		this.coinXGauche = eX - largeur / 2;
        this.coinYGauche = eY - hauteur / 2;
        creerLaGeometrie();
	}


	/**
     * Méthode pour déterminer si le triangle est sélectionné.
     *
     * @return true si le triangle est sélectionné, sinon false.
     */
    //Ahmad Khanafer
    public boolean isClickedOnIt() {
        return estClique;
    }

    /**
     * Méthode pour définir l'état de sélection du triangle.
     *
     * @param clickedOnIt true pour sélectionner le rectangle, sinon false.
     */
    //Ahmad Khanafer
    public void setClickedOnIt(boolean clickedOnIt) {
        this.estClique = clickedOnIt;
    }
    /**
     * Méthode pour déterminer si le triangle contient un point donné.
     *
     * @param xPix La coordonnée en X du point.
     * @param yPix La coordonnée en Y du point.
     * @return true si le triangle contient le point, sinon false.
     */
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
    
    /**
     * Méthode pour obtenir un segment spécifique du rectangle.
     *
     * @param num Le numéro du segment à récupérer (1 pour le gauche, 2 pour la droite, 3 pour le haut, ).
     * @return Le segment spécifié.
     */
  //ZAKARIA SOUDAKI
    public Line2D.Double getSegment(int num) {

        Line2D.Double seg = new Line2D.Double();
        if (num == 1) {
            seg = segmentGauche;
        }
        if (num == 2) {
            seg = segmentDroite;
        }
        if (num == 3) {
            seg = segmentBas;
        }
 
        return seg;
    }
	
}
