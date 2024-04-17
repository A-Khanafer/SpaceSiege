package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;

public class Cercle implements Obstacles, Dessinable, Selectionnable {
	
	 /**
     * Le nombre de pixels par mètre.
     */
    private double pixelsParMetre;

    /**
     * La largeur du rectangle en mètre.
     */
    private double largeur;

    /**
     * La hauteur du rectangle en pixels.
     */
    private double longueur;

    /**
     * La forme du rectangle.
     */
    private Rectangle2D.Double  rectanglePointille;

    /**
     * La zone délimitée par le rectangle.
     */
    private Area aireRec;

    /**
     * Les coordonnées du centre du cercle.
     */
    private double centreX, centreY;

    /**
     * L'angle de rotation du rectangle.
     */
    private double angleRotation;

    /**
     * Les poignées de redimensionnement du rectangle.
     */
    private Ellipse2D.Double[] poigneRedimensionnement;

    /**
     * Indique si le rectangle est sélectionné.
     */
    private boolean estClique = false;
    /**
     * Les coordonnées des coins du rectangle autour du cercle.
     */
    private double coinXGauche, coinYGauche, coinXDroite, coinYDroite, coinXBasGauche, coinYBasGauche, coinXBasDroit, coinYBasDroit;
    /**
     * La poignée de rotation du cercle.
     */
    private Ellipse2D.Double cercle;
    /**
     * Aire de la poignée de rotation du cercle.
     */
	private Area  aireCercle;
	/**
     * Constructeur de la classe Rectangle.
     *
     * @param posX La position en X du coin supérieur gauche du rectangle.
     * @param posY La position en Y du coin supérieur gauche du rectangle.
     */
    //Ahmad Khanafer
	public Cercle(double posX, double posY, double pixelsParMetre) {
    	this.pixelsParMetre = pixelsParMetre;
        this.coinXGauche = posX;
        this.coinYGauche = posY;
        largeur = 10 * this.pixelsParMetre;
        longueur = 10 * this.pixelsParMetre;
        poigneRedimensionnement = new Ellipse2D.Double[8];
        creerLaGeometrie();
    }
	// Méthode privée pour initialiser la géométrie du rectangle
    //Ahmad Khanafer
    private void creerLaGeometrie() {
    	coinXDroite = coinXGauche + largeur;
        coinYDroite = coinYGauche;

        coinXBasGauche = coinXGauche;
        coinYBasGauche = coinYGauche + longueur;

        coinXBasDroit = coinXDroite;
        coinYBasDroit = coinYDroite + longueur;
    	// Création du rectangle
        rectanglePointille = new Rectangle2D.Double(coinXGauche, coinYGauche, largeur, longueur);
        cercle= new Ellipse2D.Double(coinXGauche , coinYGauche , largeur, longueur);
        centreX = cercle.getCenterX();
        centreY = cercle.getCenterY();
        aireCercle = new Area(cercle);
        
        creationResizeHandles();
        calculerCoins();
        
    }
	
	private void calculerCoins() {
		// TODO Auto-generated method stub
		
	}
	private void creationResizeHandles() {
		double tailleHandle = 15; 

        double[][] handlesCoordinates = {
            {-largeur / 2, -longueur / 2},    // En haut à gauche
                  // En haut au milieu
            {largeur / 2, -longueur / 2},     // En haut à droite

            {largeur / 2, longueur / 2},      // En bas à droite

            {-largeur / 2, longueur / 2},     // En bas à gauche

        };

        poigneRedimensionnement = new Ellipse2D.Double[4];
        for (int i = 0; i < 4; i++) {

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

	@Override
	public void dessiner(Graphics2D g2d) {
	   	
        Graphics2D g2dCopy = (Graphics2D) g2d.create();
        Graphics2D g2dCopy2 = (Graphics2D) g2d.create();
        g2dCopy.setColor(Color.red);
        g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dCopy.rotate(angleRotation, centreX, centreY);
        g2dCopy.fill(cercle);
        
        if (estClique) {
            BasicStroke pointille = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[]{4}, 0); // Création de la ligne pointillée
            g2dCopy.setStroke(pointille);
            g2dCopy.setColor(Color.black);
            g2dCopy.draw(rectanglePointille);
            g2dCopy.setColor(Color.green);
            g2dCopy2.setColor(Color.red);
            for (Ellipse2D.Double handle : poigneRedimensionnement) {
            	g2dCopy2.fill(handle);
            }
        }
		
	}

	@Override
	public void redimension(int index, int eX, int eY) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void rotate(int eX, int eY) {
		// TODO Auto-generated method stub
	}

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
		return aireCercle.contains(xPix, yPix);
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
