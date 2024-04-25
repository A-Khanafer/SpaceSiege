package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;
import outils.OutilsImage;

/**
 * La classe Rectangle représente un obstacle rectangulaire dans un environnement graphique.
 * Cette classe implémente les interfaces Obstacles, Dessinable et Selectionnable pour fournir des fonctionnalités
 * de gestion, de dessin et de sélection pour le rectangle.
 * 
 * @author Ahmad Khanafer
 * @author zakaria soudaki
 */
public class Rectangle implements Obstacles, Dessinable, Selectionnable, Serializable {


	private static final long serialVersionUID = -6979285541279947116L;

	/**
     * Le nombre de pixels par mètre.
     */
    private double pixelsParMetre;

    /**
     * La largeur du rectangle en mètre.
     */
    private double largeurRec;

    /**
     * La hauteur du rectangle en pixels.
     */
    private double longueurRec;

    /**
     * La forme du rectangle.
     */
    private Rectangle2D.Double rectangle, rectanglePointille;

    /**
     * La zone délimitée par le rectangle.
     */
    private transient Area aireRec;

    /**
     * Les coordonnées du centre du rectangle.
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
     * Les segments du rectangle.
     */
    private Line2D.Double segmentHaut, segmentBas, segmentGauche, segmentDroite;

    /**
     * Les coordonnées des coins du rectangle.
     */
    private double coinXGauche, coinYGauche, coinXDroite, coinYDroite, coinXBasGauche, coinYBasGauche, coinXBasDroit, coinYBasDroit;
    /**
     * La poignée de rotation du rectangle.
     */
    private Ellipse2D.Double poigneRotation;
    /**
     * Aire de la poignée de rotation du rectangle.
     */
	private transient Area airePoigne;
	
	private Point2D.Double[] coins;
	
	private transient BufferedImage texture;
    /**
     * Constructeur de la classe Rectangle.
     *
     * @param posX La position en X du coin supérieur gauche du rectangle.
     * @param posY La position en Y du coin supérieur gauche du rectangle.
     */
    //Ahmad Khanafer
    public Rectangle(double posX, double posY, double pixelsParMetre) {
    	this.pixelsParMetre = pixelsParMetre;
        this.coinXGauche = posX;
        this.coinYGauche = posY;
        largeurRec = 10 * this.pixelsParMetre;
        longueurRec = 10 * this.pixelsParMetre;
        poigneRedimensionnement = new Ellipse2D.Double[8];
        textureRandom();
        creerLaGeometrie();
    }

    public Rectangle(double posX, double posY, double longueur, double largeur , double rotation) {
    	
    	coinXGauche = posX;
        coinYGauche = posY;
        angleRotation = rotation;
        longueurRec = longueur;
        largeurRec = largeur;
    	poigneRedimensionnement = new Ellipse2D.Double[8];
    	textureRandom();
    	creerLaGeometrie();
    }
    // Méthode privée pour initialiser la géométrie du rectangle
    //Ahmad Khanafer
    private void creerLaGeometrie() {
    	coinXDroite = coinXGauche + largeurRec;
        coinYDroite = coinYGauche;

        coinXBasGauche = coinXGauche;
        coinYBasGauche = coinYGauche + longueurRec;

        coinXBasDroit = coinXDroite;
        coinYBasDroit = coinYDroite + longueurRec;
    	// Création du rectangle
        rectangle = new Rectangle2D.Double(coinXGauche, coinYGauche, largeurRec, longueurRec);
        rectanglePointille = rectangle;
        centreX = rectangle.getCenterX();
        centreY = rectangle.getCenterY();
        aireRec = new Area(rectangle);
        poigneRotation = new Ellipse2D.Double((coinXGauche+largeurRec/2) - 15, coinYGauche - 50, 30, 30);
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
            {-largeurRec / 2, -longueurRec / 2},    // En haut à gauche
            {0, -longueurRec / 2},                  // En haut au milieu
            {largeurRec / 2, -longueurRec / 2},     // En haut à droite
            {largeurRec / 2, 0},                    // Au milieu à droite
            {largeurRec / 2, longueurRec / 2},      // En bas à droite
            {0, longueurRec / 2},                   // En bas au milieu
            {-largeurRec / 2, longueurRec / 2},     // En bas à gauche
            {-largeurRec / 2, 0}                    // Au milieu à gauche
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

        AffineTransform rotation = AffineTransform.getRotateInstance(angleRotation, centreX, centreY);

        coins = new Point2D.Double[4];
        rotation.transform(coinSupGauche, coins[0] = new Point2D.Double());
        rotation.transform(coinSupDroit, coins[1] = new Point2D.Double());
        rotation.transform(coinInfDroit, coins[2] = new Point2D.Double());
        rotation.transform(coinInfGauche, coins[3] = new Point2D.Double());

        calculerSegments();
       
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
                    if (rectangle.width - offsetX >= 20 && rectangle.height - offsetY >= 20) {
                        // Redimensionner le rectangle
                        largeurRec -= offsetX;
                        longueurRec -= offsetY;
                        coinXGauche += offsetX;
                        coinYGauche += offsetY;
                    }
                    break;
                case 1: // En haut au milieu
                    if (rectangle.height - offsetY >= 20) {
                        // Redimensionner le rectangle
                        longueurRec -= offsetY;
                        coinYGauche += offsetY;
                    }
                    break;
                case 2: // En haut à droite
                    if (rectangle.width + offsetX >= 20 && rectangle.height - offsetY >= 20) {
                        largeurRec += offsetX;
                        longueurRec -= offsetY;
                        coinYGauche += offsetY;
                    }
                    break;
                case 3: // Au milieu à droite
                    if (rectangle.width + offsetX >= 20) {
                        // Redimensionner le rectangle
                        largeurRec += offsetX;
                    }
                    break;
                case 4: // En bas à droite
                    if (rectangle.width + offsetX >= 20 && rectangle.height + offsetY >= 20) {
                        largeurRec += offsetX;
                        longueurRec += offsetY;
                    }
                    break;
                case 5: // En bas au milieu
                    if (rectangle.height + offsetY >= 20) {
                        longueurRec += offsetY;
                    }
                    break;
                case 6: // En bas à gauche
                    if (rectangle.width - offsetX >= 20 && rectangle.height + offsetY >= 20) {
                        largeurRec -= offsetX;
                        longueurRec += offsetY;
                        coinXGauche += offsetX;
                    }
                    break;
                case 7: // Au milieu à gauche
                    if (rectangle.width - offsetX >= 20) {
                        largeurRec -= offsetX;
                        coinXGauche += offsetX;
                    }
                    break;
            }
            creerLaGeometrie();
            System.out.println("_______________________longueurRec_________________________"+longueurRec);
            System.out.println("_______________________largeurRec_________________________"+largeurRec);
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
        this.coinXGauche = eX - largeurRec / 2;
        this.coinYGauche = eY - longueurRec / 2;
        creerLaGeometrie();
//        System.out.println("_______________________coinXGauche_________________________"+coinXGauche);
//        System.out.println("_______________________coinYGauche_________________________"+coinYGauche);

    }
    
    /**
     * Méthode pour déterminer si le rectangle contient un point donné.
     *
     * @param xPix La coordonnée en X du point.
     * @param yPix La coordonnée en Y du point.
     * @return true si le rectangle contient le point, sinon false.
     */
    //Ahmad Khanafer
    @Override
    public boolean contient(double xPix, double yPix) {
        return aireRec.contains(xPix, yPix);
    }
    
    /**
     * Méthode pour dessiner le rectangle sur un objet Graphics2D.
     *
     * @param g2d L'objet Graphics2D sur lequel dessiner le rectangle.
     */
    //Ahmad Khanafer
    @Override
    public void dessiner(Graphics2D g2d) { 
    	
        Graphics2D g2dCopy = (Graphics2D) g2d.create();
        Graphics2D g2dCopy2 = (Graphics2D) g2d.create();
        g2dCopy.setColor(Color.red);
        g2dCopy.draw(segmentBas);
        g2dCopy.draw(segmentGauche);
        g2dCopy.draw(segmentDroite);
        g2dCopy.draw(segmentHaut);
        g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dCopy.rotate(angleRotation, centreX, centreY);
        g2dCopy.setColor(Color.blue);
        g2dCopy.fill(rectangle);
        g2dCopy.drawImage(texture, (int) coinXGauche, (int) coinYGauche, (int)largeurRec, (int) longueurRec, null, null);
        
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
     * méthode pour calculer les segments sur chaque coter du rectangle
     */
    //ZAKARIA SOUDAKI
    private void calculerSegments() {
    	 segmentHaut = new Line2D.Double(coins[0].getX(), coins[0].getY(), coins[1].getX(), coins[1].getY());
         segmentBas = new Line2D.Double(coins[3].getX(), coins[3].getY(), coins[2].getX(), coins[2].getY());
         segmentGauche = new Line2D.Double(coins[0].getX(), coins[0].getY(), coins[3].getX(), coins[3].getY());
         segmentDroite = new Line2D.Double(coins[1].getX(), coins[1].getY(), coins[2].getX(), coins[2].getY());		
	}

    
    /**
     * Méthode pour obtenir un segment spécifique du rectangle.
     *
     * @param num Le numéro du segment à récupérer (1 pour le bas, 2 pour la droite, 3 pour le haut, 4 pour la gauche).
     * @return Le segment spécifié.
     */
  //ZAKARIA SOUDAKI
    public Line2D.Double getSegment(int num) {

        Line2D.Double seg = new Line2D.Double();
        if (num == 1) {
            seg = segmentBas;
        }
        if (num == 2) {
            seg = segmentDroite;
        }
        if (num == 3) {
            seg = segmentHaut;
        }
        if (num == 4) {
            seg = segmentGauche;
        }
        return seg;
    }
//    /**
//     * Cette méthode retourne les coordonnées des deux points définissant un segment de ligne.
//     *
//     * @param segment Le segment de ligne dont vous souhaitez obtenir les coordonnées des points.
//     * @return Un tableau de double de longueur 4 contenant les coordonnées des deux points du segment.
//     *         Les deux premiers éléments du tableau représentent les coordonnées (x, y) du premier point
//     *         et les deux derniers éléments du tableau représentent les coordonnées (x, y) du deuxième point.
//     */
//    //Zakaria Soudaki
//    public double[] getPointsSegment(Line2D.Double segment) {
//        double[] points = new double[4];
//        points[0] = segment.getX1();
//        points[1] = segment.getY1();
//        points[2] = segment.getX2();
//        points[3] = segment.getY2();
//        return points;
//    }
    
    private void textureRandom() {
    	 Random rand = new Random();
         int i = rand.nextInt(3) + 1;
         
         switch(i) {
         case 1 : 
        	 texture = (BufferedImage) OutilsImage.lireImage("textureRec1.jpg");
        	 break;
         case 2 : 
        	 texture = (BufferedImage) OutilsImage.lireImage("textureRec2.jpg");
        	 break;
         case 3 : 
        	 texture = (BufferedImage) OutilsImage.lireImage("textureRec3.jpg");
        	 break;
         }
    }
    
    public String toString() {
    	
    	String rec;
    	
    	rec= "rec\n" + 
    			Integer.toString((int) coinXGauche) + "\n" +
    			Integer.toString((int) coinYGauche) + "\n" +
    			Integer.toString((int) largeurRec) + "\n" +
    			Integer.toString((int) longueurRec) + "\n" +
    			Integer.toString((int) angleRotation) + "\n";
		return rec;
    	
    }
    
    public Area getAir() {
    	return this.aireRec;
    }

}
