package composantdessin;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import composantjeu.Balle;
import composantjeu.BalleBasique;
import physique.Vecteur2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Composant permettant de tracer une fonction en segments avec une forme Path2D
 * Il est possible de recadrer la portion visible de la fonction.
 * 
 * @author Benakmoum Walid
 */

public class PlanCartesien extends JPanel {

	//

	// n'oubliez pas de compléter la javadoc des propriétés!!!

	//
	/**
     * Numéro de série de la classe pour la sérialisation.
     */
	private static final long serialVersionUID = 1L;

	/** Valeur par défaut de l'abscisse minimale. */
	private final double DEFAUT_X_MIN = 0;
	/** Valeur par défaut de l'abscisse maximale. */
	private final double DEFAUT_X_MAX = 100;
	/** Valeur par défaut de l'ordonnée minimale. */
	private final double DEFAUT_Y_MIN = -0.5;
	/** Valeur par défaut de l'ordonnée maximale. */
	private final double DEFAUT_Y_MAX = 3;
	/** Valeur par défaut du diamètre du cercle. */
	private final double DEFAUT_CERCLE_DIA = 2;
	/** Valeur actuelle de l'abscisse minimale. */
	private double xMin = DEFAUT_X_MIN;
	/** Valeur actuelle de l'abscisse maximale. */
	private double xMax = DEFAUT_X_MAX;
	/** Valeur actuelle de l'ordonnée minimale. */
	private double yMin = DEFAUT_Y_MIN;
	/** Valeur actuelle de l'ordonnée maximale. */
	private double yMax = DEFAUT_Y_MAX;
	/**
     * Constante représentant la valeur maximale du volume du contenant.
     */
	private final int VOLUME_MAX=300;
	/** Quantité de sel sélectionnée. */
	private int quantSel = 40;
	/** Volume initial. */
	private int volumeIni = 15;
	/** Débit. */
	private double debit = 1.5;
	/** Temps. */
	private int temps = 0;
	/** Nombre de lignes en abscisse pour la grille en X. */
	private int nmbreLigX = 12;
	/** Nombre de lignes en ordonnée pour la grille en Y. */
	private int nmbreLigY = 12;
	/** Position initiale de la souris lors d'un glissement. */
	private int initialMouseX;
	/** Diamètre du cercle à dessiner. */
	private double cercleDia = DEFAUT_CERCLE_DIA;
	/** Trajectoire des axes du graphique. */
	private Path2D.Double axes;
	/** Trajectoire de la courbe à tracer. */
	private Path2D.Double ligneBrisee;
	/** Trajectoire de la grille du graphique. */
	private Path2D.Double grille;
	/** Trajectoire des taquets de la grille. */
	private Path2D.Double taquets;
	/** Ellipse représentant le cercle. */
	private Ellipse2D.Double cercle;
	/** Nombre de segments utilisés pour approximer la courbe. */
	private int nbSegmentsPourApproximer = 80;
	/** Nombre de pixels par unité en abscisse. */
	private double pixelsParUniteX;
	/** Nombre de pixels par unité en ordonnée. */
	private double pixelsParUniteY;
	
	private Balle balle;

	/**
	 * Constructeur: cree le composant et fixe la couleur de fond
	 */
	public PlanCartesien() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int dx = e.getX() - initialMouseX;
				cercleDia += dx * 0.01;
				if (cercleDia > DEFAUT_CERCLE_DIA * 3) {
					cercleDia = DEFAUT_CERCLE_DIA * 3;
				}

				// Limite le rétrécissement au diamètre de base
				if (cercleDia < DEFAUT_CERCLE_DIA) {
					cercleDia = DEFAUT_CERCLE_DIA;
				}
				initialMouseX = e.getX();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				initialMouseX = e.getX();
			}
		});
		setBackground(Color.white);
		cercleDia = DEFAUT_CERCLE_DIA;
	}// fin du constructeur

	/**
	 * Dessiner la fonction sur le composant
	 * 
	 * @param g Le contexte graphique
	 */
	//Auteur Benakmoum Walid
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		balle= new BalleBasique(0, 0, 0, new Vecteur2D(20,25), new Vecteur2D(0,0));
		double y = (double) quantSel /1;
		double deltaY = (yMax - yMin) / (nmbreLigY+1);
		double deltaX = (xMax - xMin) / (nmbreLigX+1);
		yMax = y + deltaY;
		yMin=-deltaY;
		xMin=-deltaX;
		recadrer(xMin, xMax, yMin, yMax);
		pixelsParUniteX = getWidth() / (xMax - xMin);
		pixelsParUniteY = getHeight() / (yMax - yMin);

		creerGrille();

		creerAxes(g2d);

		creerApproxCourbe();

		creerCercle();

		creerTaquets(g2d);

		AffineTransform matTransfo = new AffineTransform();

		matTransfo.scale(1, -1);

		matTransfo.scale(pixelsParUniteX, pixelsParUniteY);

		matTransfo.translate(-xMin, -yMax);

		// on dessine les axes

		g2d.setColor(Color.blue);

		BasicStroke stroke = new BasicStroke(3.0f);

		g2d.setStroke(stroke);

		g2d.draw(matTransfo.createTransformedShape(axes));

		// on dessine la courbe

		g2d.setColor(Color.red);

		g2d.draw(matTransfo.createTransformedShape(ligneBrisee));

		// On dessine les grilles

		g2d.setColor(Color.gray);

		BasicStroke stroke1 = new BasicStroke(1.0f);

		g2d.setStroke(stroke1);

		g2d.draw(matTransfo.createTransformedShape(grille));

		g2d.setStroke(stroke);

		g2d.draw(matTransfo.createTransformedShape(taquets));

		// On dessine le cercle
		g2d.setColor(Color.green);
		g2d.fill(matTransfo.createTransformedShape(cercle));

		
		g2d.setColor(Color.black);
		
	}// fin paintComponent

	/**
	 * Creation du Path2D formant les axes
	 * 
	 * @param g2d Le contexte graphique 2D.
	 */
	//Auteur Benakmoum Walid
	private void creerAxes(Graphics2D g2d) {
		axes = new Path2D.Double();
		BasicStroke stroke = new BasicStroke(2.0f);
		g2d.setStroke(stroke);
		axes.moveTo(xMin, 0);
		axes.lineTo(xMax, 0);
		axes.moveTo(0, yMin);
		axes.lineTo(0, yMax);
	}

	/**
	 * Creation de l'approximation de la courbe sous la forme d'un Path2D
	 */
	//Auteur Benakmoum Walid
	private void creerApproxCourbe() {
		double x, y;
		ligneBrisee = new Path2D.Double();
		x = balle.getPosition().getX(); // on commence à 0
		y = evalFonction(x);
		ligneBrisee.moveTo(x, y);
		
		
				y = evalFonction(x);
				ligneBrisee.lineTo(x, y);
			}
	
	

	/**
	 * 
	 * 
	 * Evaluation de la valeur de la fonction en vigueur pour un x donné
	 * 
	 * @param x Valeur de x
	 * @return la valeur de la fonction pour ce x, en fonction de la fonction
	 *         envigueur
	 */
	//Auteur Benakmoum Walid
	private double evalFonction(double x) {
		return (balle.getPosition().getY());
	}

	/**
	 * Modifie le nombre de petits segments de droite qui formeront la courbe (nb
	 * d'echantillonages)
	 * 
	 * @param nbSegmentsPourApproximer Le nombdrfe de segments voulus
	 */
	public void setNbSegmentsPourApproximer(int nbSegmentsPourApproximer) {
		this.nbSegmentsPourApproximer = nbSegmentsPourApproximer;
		repaint();
	}

	/**
	 * 
	 * 
	 * Permet de modifier les limites entre lesquelles la fonction sera tracee
	 *
	 * 
	 * @param xMin Abcisse minimale visible
	 * @param xMax Abcisse maximale visible
	 * @param yMin Ordonnee minimale visible
	 * @param yMax Ordonnee maximale visible
	 */
	public void recadrer(double xMin, double xMax, double yMin, double yMax) {

		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		repaint();
	}

	/**
	 * Cette méthode génère les lignes de la grille sur le graphique, tant en abscisse (X) qu'en ordonnée (Y),
	 * en utilisant les paramètres de la grille définis par `nmbreLigX` et `nmbreLigY`. La grille est centrée autour de l'origine.
	 * Chaque ligne de la grille est créée avec des segments de droite.
	 */
	//Auteur Benakmoum Walid
	private void creerGrille() {
		grille = new Path2D.Double();

		double deltaX = (xMax - xMin) / (nmbreLigX + 1);
		double deltaY = (yMax - yMin) / (nmbreLigY + 1);

		// Centre la grille autour de l'origine
		double xCentrage = -xMin % deltaX;
		double yCentrage = -yMin % deltaY;

		// Lignes de grille en x
		for (int i = 0; i < nmbreLigX + 1; i++) {
			double grilleX = xMin + i * deltaX + xCentrage;
			grille.moveTo(grilleX, yMin);
			grille.lineTo(grilleX, yMax);
		}

		// Lignes de grille en y
		for (int i = 0; i < nmbreLigY + 1; i++) {
			double grilleY = yMin + i * deltaY + yCentrage;
			grille.moveTo(xMin, grilleY);
			grille.lineTo(xMax, grilleY);
		}

		repaint();
	}

	/**
	 * Cette méthode génère les taquets de la grille sur le graphique, tant en abscisse qu'en ordonnée,
	 * et ajoute des chiffres à côté des taquets correspondants. Les taquets marquent les divisions de la grille du graphique.
	 * Les chiffres sont affichés uniquement pour les valeurs positives.
	 *
	 * @param g2d Le contexte graphique 2D dans lequel dessiner les taquets et les chiffres.
	 */
	//Auteur Benakmoum Walid
	private void creerTaquets(Graphics2D g2d) {
		taquets = new Path2D.Double();

		double deltaX = (xMax - xMin) / (nmbreLigX + 1);
		double deltaY = (yMax - yMin) / (nmbreLigY + 1);

		// Centre les taquets autour de l'origine
		double xCentrage = -xMin % deltaX;
		double yCentrage = -yMin % deltaY;

		DecimalFormat dfX = new DecimalFormat("#");
		DecimalFormat dfY = new DecimalFormat("#.###");

		// Taquets en X
		for (int i = 0; i < nmbreLigX + 1; i++) {
			double xTaquet = xMin + i * deltaX + xCentrage;
			if (xTaquet != 0.0) {
				taquets.moveTo(xTaquet, -0.02);
				taquets.lineTo(xTaquet, 0.02);
				// Affiche les chiffres seulement si xTaquet est positif
				if (xTaquet > 0) {
					int posX = (int) ((xTaquet - xMin) * pixelsParUniteX) - 15;
					int posY = (int) (getHeight() + (yMin * pixelsParUniteY) + 15);
					String formattedXChiffre = dfX.format(xTaquet);
					g2d.setColor(Color.black);
					g2d.drawString(formattedXChiffre, posX, posY + 10);
				}
			}
		}

		int taquetHauteur = 10;

		// Taquets en Y avec le pas calculé
		for (int i = 0; i < nmbreLigY + 1; i++) {
			double yTaquet = yMin + i * deltaY + yCentrage;
			if (yTaquet != Math.abs(0)) {
				taquets.moveTo(-0.2, yTaquet);
				taquets.lineTo(0.2, yTaquet);
				// Affiche les chiffres seulement si yTaquet est positif
				if (yTaquet > 0) {
					double posY =  (getHeight() - (yTaquet - yMin) * pixelsParUniteY) - taquetHauteur / 2;
					double posX =  (-xMin * pixelsParUniteX) - 35;
					String formattedYChiffre = dfY.format(yTaquet);
					g2d.setColor(Color.black);
					g2d.drawString(formattedYChiffre, (int) posX, (int) posY);
				}
			}
		}
	}

	/**
	 * Cette méthode génère une ellipse (cercle) qui représente visuellement une certaine quantité ou valeur associée au temps.
	 */
	//Auteur Benakmoum Walid
	private void creerCercle() {
		double xCercle = balle.getPosition().getX();
		double yCercle = evalFonction(xCercle);

		double rayonCercleX = cercleDia / 2;
		double rayonCercleY = (cercleDia / 2) * (pixelsParUniteX / pixelsParUniteY);

		double xCoinSuperieurGauche = xCercle - rayonCercleX;
		double yCoinSuperieurGauche = yCercle - rayonCercleY;

		cercle = new Ellipse2D.Double(xCoinSuperieurGauche, yCoinSuperieurGauche, 2 * rayonCercleX, 2 * rayonCercleY);

	}

	/**
	 * Définit la quantité de sel sélectionnée par l'utilisateur grâce au spinner.
	 *
	 * @param qSelSpinner La quantité sélectionnée.
	 */
	//Auteur Benakmoum Walid
	public void setQuantSel(int qSelSpinner) {
		quantSel = qSelSpinner;
		repaint();
	}

	/**
	 * Définit le volume initial sélectionnée par l'utilisateur grâce au spinner.
	 *
	 * @param vIniSpinner Le volume initial.
	 */
	//Auteur Benakmoum Walid
	public void setVolumeIni(int vIniSpinner) {
		volumeIni = vIniSpinner;
		repaint();
	}

	/**
	 * Définit le débit sélectionnée par l'utilisateur grâce au spinner.
	 *
	 * @param debitSpinner Le débit.
	 */
	//Auteur Benakmoum Walid
	public void setDebit(double debitSpinner) {
		debit = debitSpinner;
		repaint();
	}

	/**
	 * Définit le temps sélectionnée par l'utilisateur grâce au curseurs.
	 *
	 * @param tempsSpinner Le temps.
	 */
	//Auteur Benakmoum Walid
	public void setTemps(int tempsSpinner) {
		temps = tempsSpinner;
		creerCercle();
		repaint();
	}

    public void setBalle(Balle b) {
    	this.balle=b;
    	repaint();
    }
}

