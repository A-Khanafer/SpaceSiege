package obstacles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import balle.BalleBasique;
import interfaces.Dessinable;
import interfaces.Obstacles;
import interfaces.Selectionnable;
import physique.Vecteur2D;
 

public class Rectangle implements Obstacles, Dessinable, Selectionnable {

	private double pixelsParMetre= 10;
	private double width = 10*pixelsParMetre;
	private double height = 10*pixelsParMetre;
	private Rectangle2D.Double rectangle, rectanglePointille;
	private Area aireRec, aireRotate;
	
	private double centreX, centreY;
	private double angleRotation;
	private Double[] resizeHandles;
	private double diametre = 2*pixelsParMetre;
	private boolean clickedOnIt = false;
	private boolean first = true;
	
	//collisons
	private Line2D.Double segmentHaut,segmentBas,segmentGauche,segmentDroite;
	
	private double coinXGauche,coinYGauche;
	private double coinXDroite,coinYDroite;
	private double coinXBasGauche,coinYBasGauche;
	private double coinXBasDroit,coinYBasDroit;

	
	
	
	public Rectangle(double posX, double posY) {
		this.coinXGauche = posX;
		this.coinYGauche = posY;
		resizeHandles = new Ellipse2D.Double[8];
		creerLaGeometrie();
		
	}
	
	private void creerLaGeometrie() {
			coinXDroite = coinXGauche + width;
			coinYDroite = coinYGauche;
			
			coinXBasGauche = coinXGauche;
			coinYBasGauche = coinYGauche + height;
			
			coinXBasDroit = coinXDroite;
			coinYBasDroit = coinYDroite + height;
			
			
			rectangle = new Rectangle2D.Double(coinXGauche, coinYGauche, width, height);
			rectanglePointille = rectangle;
			centreX  = rectangle.getCenterX();
			centreY = rectangle.getCenterY();
			aireRec = new Area(rectangle);
			creationResizeHandles();
			
			
			segmentHaut = new Line2D.Double(coinXGauche, coinYGauche, coinXDroite, coinYDroite);
			segmentBas = new Line2D.Double(coinXBasGauche, coinYBasGauche,coinXBasDroit ,coinYBasDroit );
			segmentGauche = new Line2D.Double(coinXGauche, coinYGauche, coinXBasGauche, coinYBasGauche);
			segmentDroite = new Line2D.Double(coinXDroite, coinYDroite, coinXBasDroit, coinYBasDroit);
			
	}
	
	 private void creationResizeHandles() {
	        double tailleHandle = 15;
	        // En haut a gauche
	        resizeHandles[0] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2, coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle);
	        // en haut millieu
	        resizeHandles[1] = new Ellipse2D.Double(coinXGauche + width / 2 - tailleHandle / 2, coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle);
	        // En haut a droite
	        resizeHandles[2] = new Ellipse2D.Double(coinXGauche + width - tailleHandle / 2, coinYGauche - tailleHandle / 2, tailleHandle, tailleHandle);
	        // Millieu droit
	        resizeHandles[3] = new Ellipse2D.Double(coinXGauche + width - tailleHandle / 2, coinYGauche + height / 2 - tailleHandle / 2, tailleHandle, tailleHandle);
	        // en bas droite
	        resizeHandles[4] = new Ellipse2D.Double(coinXGauche + width - tailleHandle / 2, coinYGauche + height - tailleHandle / 2, tailleHandle, tailleHandle);
	        // en bas millieu
	        resizeHandles[5] = new Ellipse2D.Double(coinXGauche + width / 2 - tailleHandle / 2, coinYGauche + height - tailleHandle / 2, tailleHandle, tailleHandle);
	        // en bas Guache
	        resizeHandles[6] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2, coinYGauche + height - tailleHandle / 2, tailleHandle, tailleHandle);
	        // Millieu gauche
	        resizeHandles[7] = new Ellipse2D.Double(coinXGauche - tailleHandle / 2, coinYGauche + height / 2 - tailleHandle / 2, tailleHandle, tailleHandle);
	    }

	 public void resize(int index, int eX, int eY) {
		    // Vérifier si le redimensionnement est activé
		    if (clickedOnIt) {
		        // Calculer le décalage entre la position actuelle et la position de la souris
		        double offsetX = eX - resizeHandles[index].getCenterX();
		        double offsetY = eY - resizeHandles[index].getCenterY();

		        // Effectuer le redimensionnement en fonction de l'index du point de redimensionnement sélectionné
		        switch (index) {
		            case 0: // En haut à gauche
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width - offsetX >= 20 && rectangle.height - offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    width -= offsetX;
		                    height -= offsetY;
		                    coinXGauche += offsetX;
		                    coinYGauche += offsetY;
		                }
		                break;
		            case 1: // En haut au milieu
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.height - offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    height -= offsetY;
		                    coinYGauche += offsetY;
		                }
		                break;
		            case 2: // En haut à droite
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width + offsetX >= 20 && rectangle.height - offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    width += offsetX;
		                    height -= offsetY;
		                    coinYGauche += offsetY;
		                }
		                break;
		            case 3: // Au milieu à droite
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width + offsetX >= 20) {
		                    // Redimensionner le rectangle
		                    width += offsetX;
		                }
		                break;
		            case 4: // En bas à droite
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width + offsetX >= 20 && rectangle.height + offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    width += offsetX;
		                    height += offsetY;
		                }
		                break;
		            case 5: // En bas au milieu
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.height + offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    height += offsetY;
		                }
		                break;
		            case 6: // En bas à gauche
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width - offsetX >= 20 && rectangle.height + offsetY >= 20) {
		                    // Redimensionner le rectangle
		                    width -= offsetX;
		                    height += offsetY;
		                    coinXGauche += offsetX;
		                }
		                break;
		            case 7: // Au milieu à gauche
		                // Vérifier si le redimensionnement est possible sans rétrécir le rectangle en dessous d'une taille minimale
		                if (rectangle.width - offsetX >= 20) {
		                    // Redimensionner le rectangle
		                    width -= offsetX;
		                    coinXGauche += offsetX;
		                }
		                break;
		        }

		        // Recréer la géométrie avec les nouvelles dimensions
		        creerLaGeometrie();
		    }
		}


	@Override
	public void rotate( int eX, int eY) {
		
		if(eX <= centreX) {
		double longeurCoteOpose = eX - centreX ;
			if(eY < centreY) {
				double longeurCoteAdjacent = centreY - eY;
				angleRotation = Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
			}else {
				double longeurCoteAdjacent = centreY - eY;
				angleRotation = -Math.PI + Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
			}
		}else{
			double longeurCoteOpose = eX - centreX ;
				if(eY < centreY) {
					double longeurCoteAdjacent = centreY - eY;
					angleRotation = Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
				}else {
					double longeurCoteAdjacent = eY - centreY;
					angleRotation = Math.PI - Math.atan2(longeurCoteOpose, longeurCoteAdjacent);
				}
		}
		
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		if (aireRec.contains(xPix, yPix)) {
			return true;
		}else {
		    return false;
		}
		
	}
	
	@Override
	public void move(int eX, int eY) {
			this.coinXGauche = eX - width/2 ;
			this.coinYGauche = eY - height/2 ;
			creerLaGeometrie();	
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dCopy = (Graphics2D) g2d.create();
		g2dCopy.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2dCopy.rotate(angleRotation, centreX, centreY);
		g2dCopy.setColor(Color.blue);
		g2dCopy.fill(rectangle);
		
		if(clickedOnIt) {
			BasicStroke pointille = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0); //creation de la ligne pointille
			g2dCopy.setStroke(pointille);
			g2dCopy.setColor(Color.black);
			g2dCopy.draw(rectanglePointille);
			g2dCopy.setColor(Color.red);
			 for (Ellipse2D.Double handle : resizeHandles) {
				 	g2dCopy.fill(handle);
		        }
		}
		g2dCopy.setColor(Color.red);
		g2dCopy.draw(segmentBas);
		g2dCopy.draw(segmentGauche);
		g2dCopy.draw(segmentDroite);
		g2dCopy.draw(segmentHaut);


	}
	
	public boolean isClickedOnIt() {
		return clickedOnIt;
	}

	public void setClickedOnIt(boolean clickedOnIt) {
		this.clickedOnIt = clickedOnIt;
	}
	
	public int getClickedResizeHandleIndex(double x, double y) {
        for (int i = 0; i < resizeHandles.length; i++) {
            if (resizeHandles[i].contains(x, y)) {
                return i;
            }
        }
        return -1; // Aucun point de contrôle trouvé à cet endroit
    }

	public Line2D.Double getSegment(int num){
		
		Line2D.Double seg = new Line2D.Double();
		if(num==1) {
			seg= segmentBas;
		}
		if(num==2) {
			seg= segmentDroite;
		}
		if(num==3) {
			seg= segmentHaut;
		}
		if(num==4) {
			seg= segmentGauche;
		}
		return seg;
		
	}

    //zk point de chaque coter d'un segment
    public double[] getPointsSegment(Line2D.Double segment) {
        double[] points = new double[4];
        points[0] = segment.getX1();
        points[1] = segment.getY1();
        points[2] = segment.getX2();
        points[3] = segment.getY2();
        return points;
    }
	
    private Point2D.Double calculateRightCorner() {
        // Calculate the coordinates of the right corner before rotation
        double rightCornerX = rectangle.getCenterX() + width / 2;
        double rightCornerY = rectangle.getCenterY() - height / 2;

        // Calculate the distance from the center to the right corner
        double distanceToRightCorner = Math.sqrt((width / 2) * (width / 2) + (height / 2) * (height / 2));

        // Calculate the angle between the center and the right corner before rotation
        double angleToRightCorner = Math.atan2(rightCornerY - rectangle.getCenterY(), rightCornerX - rectangle.getCenterX());

        // Calculate the new angle after rotation
        double newAngle = angleToRightCorner + angleRotation;

        // Calculate the coordinates of the right corner after rotation
        double rotatedRightCornerX = rectangle.getCenterY() + distanceToRightCorner * Math.cos(newAngle);
        double rotatedRightCornerY = rectangle.getCenterY() + distanceToRightCorner * Math.sin(newAngle);

        // Return the coordinates of the rotated right corner
        return new Point2D.Double(rotatedRightCornerX, rotatedRightCornerY);
    }

}
