package obstacles;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;
import physique.Vecteur2D;



/**
 * La classe Triangle est un exemple d'objet dessinable simple et rempli d'une couleur uniforme.
 * 
 * 
 * @author Caroline Houle
 * @author Ahmad Khanafer
 */
public class Triangle implements Obstacles, Serializable {
	
	private static final long serialVersionUID = 8086144604516830730L;
	/**
     * Le nombre de pixels par mètre.
     */
	private double pixelsParMetres;
	private double coinXGauche, coinYGauche;
	/**
     * La largeur et hauteur du triangle en mètre.
     */
	private double largeur, longueur;
	/**
     * La forme du triangle.
     */
	private Path2D.Double triangle;
	/**
     * La zone délimitée par le rectangle.
     */
	private transient Area aireTri;
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
	private transient Area airePoigne;
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
	 */
	public Triangle(double x, double y, double pixelsParMetres) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.pixelsParMetres = pixelsParMetres;
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= 10 * this.pixelsParMetres;
		this.longueur = 20* this.pixelsParMetres; 
		poigneRedimensionnement = new Ellipse2D.Double[8];
		creerLaGeometrie();
	} //fin constructeur
	
	public Triangle(double x, double y, double largeur, double hauteur, double angleRotation) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= largeur;
		this.longueur = hauteur; 
		this.angleRotation = angleRotation;
		poigneRedimensionnement = new Ellipse2D.Double[8];
		creerLaGeometrie();
	}

	
	// Méthode privée pour initialiser la géométrie du triangle
    //Ahmad Khanafer
	private void creerLaGeometrie() {
		coinXDroite = coinXGauche + largeur;
        coinYDroite = coinYGauche;

        coinXBasGauche = coinXGauche;
        coinYBasGauche = coinYGauche + longueur;

        coinXBasDroit = coinXDroite;
        coinYBasDroit = coinYDroite + longueur;
		//calculer les dimensions qui dépendent des caractéristiques données au sapin dans son ensemble
		//on choisit des proportions plausibles pour un sapin!
		rectanglePointille = new Rectangle2D.Double(coinXGauche, coinYGauche, largeur, longueur);
		centreX = rectanglePointille.getCenterX();
		centreY = rectanglePointille.getCenterY();
		sommetX = coinXGauche + largeur/2;
		sommetY = coinYGauche;
		triangle = new Path2D.Double();
		triangle.moveTo(coinXGauche, coinYGauche + longueur);
		triangle.lineTo(coinXGauche+largeur, coinYGauche + longueur);
		triangle.lineTo(sommetX, sommetY );
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
    	double tailleHandle = 15; 

        double[][] handlesCoordinates = {
            {-largeur / 2, -longueur / 2},    // En haut à gauche
            {0, -longueur / 2},                  // En haut au milieu
            {largeur / 2, -longueur / 2},     // En haut à droite
            {largeur / 2, 0},                    // Au milieu à droite
            {largeur / 2, longueur / 2},      // En bas à droite
            {0, longueur / 2},                   // En bas au milieu
            {-largeur / 2, longueur / 2},     // En bas à gauche
            {-largeur / 2, 0}                    // Au milieu à gauche
        };

        poigneRedimensionnement = new Ellipse2D.Double[8];
        for (int i = 0; i < 8; i++) {

            double[] rotatedCoord = rotatePoint(handlesCoordinates[i][0], handlesCoordinates[i][1], angleRotation);
            double handleX = centreX + rotatedCoord[0];
            double handleY = centreY + rotatedCoord[1];
            poigneRedimensionnement[i] = new Ellipse2D.Double(handleX - tailleHandle / 2, handleY - tailleHandle / 2,
                    tailleHandle, tailleHandle);
        }
    }
    /**
     * Applique une rotation aux coordonnées d'un point autour de l'origine (0,0)
     * et retourne les nouvelles coordonnées.
     */
    private double[] rotatePoint(double x, double y, double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        double rotatedX = x * cosAngle - y * sinAngle;
        double rotatedY = x * sinAngle + y * cosAngle;
        return new double[]{rotatedX, rotatedY};
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
        segmentGauche = new Line2D.Double(coins[4].getX(), coins[4].getY(), coins[3].getX(), coins[3].getY());
        segmentDroite = new Line2D.Double(coins[4].getX(), coins[4].getY(), coins[2].getX(), coins[2].getY());
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
        Graphics2D g2dCopy2 = (Graphics2D) g2d.create();
        g2dCopy.setColor(Color.red);
        g2dCopy.draw(segmentBas);
        g2dCopy.draw(segmentGauche);
        g2dCopy.draw(segmentDroite);
        g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dCopy.rotate(angleRotation, centreX, centreY);
        g2dCopy.setColor(Color.yellow);
        g2dCopy.fill(triangle);
        
        if (estClique) {
            BasicStroke pointille = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[]{4}, 0); // Création de la ligne pointillée
            g2dCopy.setStroke(pointille);
            g2dCopy.setColor(Color.black);
            g2dCopy.draw(rectanglePointille);
            g2dCopy.setColor(Color.green);
            g2dCopy.fill(poigneRotation);
            g2dCopy2.setColor(Color.red);
            for (Ellipse2D.Double handle : poigneRedimensionnement) {
            	g2dCopy2.fill(handle);
            }
        } 
	}
	private Point2D.Double transformMousePoint(double mouseX, double mouseY) {
        // Inverser l'angle de rotation pour transformer les coordonnées
        double inverseAngle = -this.angleRotation;

        // Calculer le vecteur de la souris par rapport au centre du rectangle
        double mouseXfromCenter = mouseX - this.centreX;
        double mouseYfromCenter = mouseY - this.centreY;

        // Appliquer la rotation inverse
        double rotatedX = mouseXfromCenter * Math.cos(inverseAngle) - mouseYfromCenter * Math.sin(inverseAngle);
        double rotatedY = mouseXfromCenter * Math.sin(inverseAngle) + mouseYfromCenter * Math.cos(inverseAngle);

        // Re-calculer les coordonnées par rapport à l'origine
        double finalX = rotatedX + this.centreX;
        double finalY = rotatedY + this.centreY;

        return new Point2D.Double(finalX, finalY);
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
		// Vérifier si le redimensionnement est activé
        if (estClique) {
        	
        	Point2D point = transformMousePoint(eX, eY);
        	eX = (int) point.getX();
        	eY = (int) point.getY();
        	
            // Calculer le décalage entre la position actuelle et la position de la souris
            double offsetX = eX - poigneRedimensionnement[index].getCenterX();
            double offsetY = eY - poigneRedimensionnement[index].getCenterY();
            
            // Effectuer le redimensionnement en fonction de l'index du point de redimensionnement sélectionné
            switch (index) {
                case 0: // En haut à gauche
                    if (rectanglePointille.width - offsetX >= 20 && rectanglePointille.height - offsetY >= 20) {
                        // Redimensionner le rectangle
                        largeur -= offsetX;
                        longueur -= offsetY;
                        coinXGauche += offsetX;
                        coinYGauche += offsetY;
                    }
                    break;
                case 1: // En haut au milieu
                    if (rectanglePointille.height - offsetY >= 20) {
                        // Redimensionner le rectangle
                        longueur -= offsetY;
                        coinYGauche += offsetY;
                    }
                    break;
                case 2: // En haut à droite
                    if (rectanglePointille.width + offsetX >= 20 && rectanglePointille.height - offsetY >= 20) {
                        largeur += offsetX;
                        longueur -= offsetY;
                        coinYGauche += offsetY;
                    }
                    break;
                case 3: // Au milieu à droite
                    if (rectanglePointille.width + offsetX >= 20) {
                        // Redimensionner le rectangle
                        largeur += offsetX;
                    }
                    break;
                case 4: // En bas à droite
                    if (rectanglePointille.width + offsetX >= 20 && rectanglePointille.height + offsetY >= 20) {
                        largeur += offsetX;
                        longueur += offsetY;
                    }
                    break;
                case 5: // En bas au milieu
                    if (rectanglePointille.height + offsetY >= 20) {
                        longueur += offsetY;
                    }
                    break;
                case 6: // En bas à gauche
                    if (rectanglePointille.width - offsetX >= 20 && rectanglePointille.height + offsetY >= 20) {
                        largeur -= offsetX;
                        longueur += offsetY;
                        coinXGauche += offsetX;
                    }
                    break;
                case 7: // Au milieu à gauche
                    if (rectanglePointille.width - offsetX >= 20) {
                        largeur -= offsetX;
                        coinXGauche += offsetX;
                    }
                    break;
            }
            creerLaGeometrie();
        }
		
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
		if(airePoigne.contains(eX, eY)) {
			if (eX <= centreX) {
	            double longueurCoteOppose = eX - centreX;
	            double longueurCoteAdjacent = centreY - eY;
	            angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
	        } else {
	            double longueurCoteOppose = eX - centreX;
	            double longueurCoteAdjacent = centreY - eY;
	            angleRotation = Math.atan2(longueurCoteOppose, longueurCoteAdjacent);
	        }
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
        this.coinYGauche = eY - longueur / 2;
        creerLaGeometrie();
	}


	/**
     * Méthode pour déterminer si le triangle est sélectionné.
     *
     * @return true si le triangle est sélectionné, sinon false.
     */
    //Ahmad Khanafer
	@Override
    public boolean isClickedOnIt() {
        return estClique;
    }

    /**
     * Méthode pour définir l'état de sélection du triangle.
     *
     * @param clickedOnIt true pour sélectionner le rectangle, sinon false.
     */
    //Ahmad Khanafer
    @Override
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
	@Override
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
    
	public String toString() {
	    
	    String tri;
	    	
	   	tri= "tri\n" + 
	    		Integer.toString((int) coinXGauche) + "\n" +
	    		Integer.toString((int) coinYGauche) + "\n" +
	    		Integer.toString((int) largeur) + "\n" +
	    		Integer.toString((int) longueur) + "\n" +
	    		Integer.toString((int) angleRotation) + "\n";
		return tri;
	    	
	}

	@Override
	public Area getAir() {
		return this.aireTri;
	}

	@Override
	public Vecteur2D getPosition() {
		return new Vecteur2D(coinXGauche, coinYGauche);
	}
	
}
