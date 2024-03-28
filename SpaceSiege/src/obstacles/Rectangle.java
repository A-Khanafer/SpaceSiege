package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
public class Rectangle implements Obstacles, Dessinable, Selectionnable {


    /**
     * Le nombre de pixels par mètre.
     */
    private double pixelsParMetre = 10;

    /**
     * La largeur du rectangle en pixels.
     */
    private double largeurRec = 10 * pixelsParMetre;

    /**
     * La hauteur du rectangle en pixels.
     */
    private double longueurRec = 10 * pixelsParMetre;

    /**
     * La forme du rectangle.
     */
    private Rectangle2D.Double rectangle, rectanglePointille;

    /**
     * La zone délimitée par le rectangle.
     */
    private Area aireRec;

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
     * Constructeur de la classe Rectangle.
     *
     * @param posX La position en X du coin supérieur gauche du rectangle.
     * @param posY La position en Y du coin supérieur gauche du rectangle.
     */
    //Ahmad Khanafer
    public Rectangle(double posX, double posY) {
        this.coinXGauche = posX;
        this.coinYGauche = posY;
        poigneRedimensionnement = new Ellipse2D.Double[8];
        
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
        poigneRedimensionnement[1] = new Ellipse2D.Double(coinXGauche + largeurRec / 2 - tailleHandle / 2,
                coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle); // En haut au milieu
        poigneRedimensionnement[2] = new Ellipse2D.Double(coinXGauche + largeurRec - tailleHandle / 2,
                coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle); // En haut à droite
        poigneRedimensionnement[3] = new Ellipse2D.Double(coinXGauche + largeurRec - tailleHandle / 2,
                coinYGauche + longueurRec / 2 - tailleHandle / 2, tailleHandle, tailleHandle); // Millieu droit
        poigneRedimensionnement[4] = new Ellipse2D.Double(coinXGauche + largeurRec - tailleHandle / 2,
                coinYGauche + longueurRec - tailleHandle / 2, tailleHandle, tailleHandle); // En bas à droite
        poigneRedimensionnement[5] = new Ellipse2D.Double(coinXGauche + largeurRec / 2 - tailleHandle / 2,
                coinYGauche + longueurRec - tailleHandle / 2, tailleHandle, tailleHandle); // En bas au milieu
        poigneRedimensionnement[6] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2,
                coinYGauche + longueurRec - tailleHandle / 2, tailleHandle, tailleHandle); // En bas à gauche
        poigneRedimensionnement[7] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2,
                coinYGauche + longueurRec / 2 - tailleHandle / 2, tailleHandle, tailleHandle); // Millieu gauche
    }
    //Méthode privée qui calcule les 4 coins en rotation et dessine les segments pour permettre la collisions.
    //Ahmad Khanafer
    private void calculerCoins() {
 
        Point2D.Double coinSupGauche = new Point2D.Double(coinXGauche, coinYGauche);
        Point2D.Double coinSupDroit = new Point2D.Double(coinXDroite, coinYDroite);
        Point2D.Double coinInfDroit = new Point2D.Double(coinXBasDroit, coinYBasDroit);
        Point2D.Double coinInfGauche = new Point2D.Double(coinXBasGauche, coinYBasGauche);

        AffineTransform rotation = AffineTransform.getRotateInstance(angleRotation, centreX, centreY);

        Point2D.Double[] coins = new Point2D.Double[4];
        rotation.transform(coinSupGauche, coins[0] = new Point2D.Double());
        rotation.transform(coinSupDroit, coins[1] = new Point2D.Double());
        rotation.transform(coinInfDroit, coins[2] = new Point2D.Double());
        rotation.transform(coinInfGauche, coins[3] = new Point2D.Double());

        segmentHaut = new Line2D.Double(coins[0].getX(), coins[0].getY(), coins[1].getX(), coins[1].getY());
        segmentBas = new Line2D.Double(coins[3].getX(), coins[3].getY(), coins[2].getX(), coins[2].getY());
        segmentGauche = new Line2D.Double(coins[0].getX(), coins[0].getY(), coins[3].getX(), coins[3].getY());
        segmentDroite = new Line2D.Double(coins[1].getX(), coins[1].getY(), coins[2].getX(), coins[2].getY());
        
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
        g2dCopy.setColor(Color.red);
        g2dCopy.draw(segmentBas);
        g2dCopy.draw(segmentGauche);
        g2dCopy.draw(segmentDroite);
        g2dCopy.draw(segmentHaut);
        g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dCopy.rotate(angleRotation, centreX, centreY);
        g2dCopy.setColor(Color.blue);
        g2dCopy.fill(rectangle);

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
    /**
     * Cette méthode retourne les coordonnées des deux points définissant un segment de ligne.
     *
     * @param segment Le segment de ligne dont vous souhaitez obtenir les coordonnées des points.
     * @return Un tableau de double de longueur 4 contenant les coordonnées des deux points du segment.
     *         Les deux premiers éléments du tableau représentent les coordonnées (x, y) du premier point
     *         et les deux derniers éléments du tableau représentent les coordonnées (x, y) du deuxième point.
     */
    //Zakaria Soudaki
    public double[] getPointsSegment(Line2D.Double segment) {
        double[] points = new double[4];
        points[0] = segment.getX1();
        points[1] = segment.getY1();
        points[2] = segment.getX2();
        points[3] = segment.getY2();
        return points;
    }

   


 
}
