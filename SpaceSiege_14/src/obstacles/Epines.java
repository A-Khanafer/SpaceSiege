package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import interfaces.Obstacles;
import physique.Vecteur2D;

public class Epines implements Obstacles, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 433299140604212960L;
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
	private Path2D.Double epines;
	/**
     * La zone délimitée par le rectangle.
     */
	private Path2D.Double aireEpines;
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
	
	private final int NB_EPINE = 10;
	
	
	
	public Epines(double x, double y, double pixelsParMetres) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.pixelsParMetres = pixelsParMetres;
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= 30 * this.pixelsParMetres;
		this.longueur = 5* this.pixelsParMetres; 
		poigneRedimensionnement = new Ellipse2D.Double[8];
		creerLaGeometrie();
	}
	
	public Epines(double x, double y, double largeur, double hauteur, double angleRotation) {
		//on mémorise les caractéristique du sapin dans son ensemble
		this.coinXGauche = x;
		this.coinYGauche = y;
		this.largeur= largeur;
		this.longueur = hauteur; 
		this.angleRotation = angleRotation;
		poigneRedimensionnement = new Ellipse2D.Double[8];
		creerLaGeometrie();
	}
	
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
		
		epines = new Path2D.Double();
		epines.moveTo(coinXGauche, coinYGauche + longueur);
		for(int i  = 1; i <= NB_EPINE ; i++) {
			epines.lineTo(coinXGauche + (i*largeur/NB_EPINE) - (largeur/NB_EPINE)/2 , coinYGauche);
			epines.lineTo(coinXGauche + (i*largeur/NB_EPINE), coinYGauche + longueur);
			
		}
		epines.closePath();
		
		aireEpines = new Path2D.Double(rectanglePointille);
		
		poigneRotation = new Ellipse2D.Double((coinXGauche+largeur/2) - 15, coinYGauche - 50, 30, 30);
        airePoigne = new Area(poigneRotation);
        AffineTransform transformation = new AffineTransform();
        transformation.rotate(angleRotation, centreX, centreY);
        airePoigne.transform(transformation);
        aireEpines.transform(transformation);
        
		creationResizeHandles();
		calculerCoins();
		
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
		
	}

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

	private double[] rotatePoint(double d, double e, double angleRotation) {
		 double cosAngle = Math.cos(angleRotation);
	        double sinAngle = Math.sin(angleRotation);
	        double rotatedX = d * cosAngle - e * sinAngle;
	        double rotatedY = d * sinAngle + e * cosAngle;
	        return new double[]{rotatedX, rotatedY};
	}

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
	
	@Override
	public void move(int eX, int eY) {
		this.coinXGauche = eX - largeur / 2;
        this.coinYGauche = eY - longueur / 2;
        creerLaGeometrie();	
	}
	
	@Override
	public void dessiner(Graphics2D g2d) {
	   	
        Graphics2D g2dCopy = (Graphics2D) g2d.create();
        Graphics2D g2dCopy2 = (Graphics2D) g2d.create();
        g2dCopy.setColor(Color.red);
//        g2dCopy.draw(segmentBas);
//        g2dCopy.draw(segmentGauche);
//        g2dCopy.draw(segmentDroite);
        g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dCopy.rotate(angleRotation, centreX, centreY);
        g2dCopy.setColor(Color.gray);
        g2dCopy.fill(epines);
        
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
	
	@Override
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		return aireEpines.contains(xPix, yPix);
	}
	
	@Override
	public int getClickedResizeHandleIndex(double x, double y) {
		for (int i = 0; i < poigneRedimensionnement.length; i++) {
            if (poigneRedimensionnement[i].contains(x, y)) {
                return i;
            }
        }
        return -1; // Aucune poignée de redimensionnement trouvée à cette position
	}
	
	@Override
	public boolean isClickedOnIt() {
		// TODO Auto-generated method stub
		return estClique;
	}
	
	@Override
	public void setClickedOnIt(boolean clickedOnIt) {
		this.estClique = clickedOnIt;
	}
	
	public String toString() {
		    
	  String epi;
		    	
	  epi= "epi\n" + 
		   		Integer.toString((int) coinXGauche) + "\n" +
		    	Integer.toString((int) coinYGauche) + "\n" +
		    	Integer.toString((int) largeur) + "\n" +
		    	Integer.toString((int) longueur) + "\n" +
		    	Double.toString(angleRotation) + "\n";
		return epi;
		    	
		}


	@Override
	public Vecteur2D getPosition() {
		return new Vecteur2D(coinXGauche, coinYGauche);
	}

	@Override
	public Point2D.Double[] getCoins() {
		Point2D.Double[] tab = new Point2D.Double[4];
		tab[0] = new Point2D.Double(coinXGauche,coinYGauche); 
		tab[1] = new Point2D.Double(coinXDroite,coinYDroite); 
		tab[2] = new Point2D.Double(coinXBasDroit,coinYBasDroit); 
		tab[3] = new Point2D.Double(coinXBasGauche,coinYBasGauche); 

		return tab;
	}

	public double getLongueur() {
		return this.longueur;
	}

	public double getLargeur() {
		return this.largeur;
	}
	
	
	public Area toAire() {
		return null;
	}
	
}
