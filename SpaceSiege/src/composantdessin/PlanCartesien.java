package composantdessin;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import outils.OutilsMath;
/*
 * Cette classe crée un composant graphique pour afficher un plan cartésien
 * avec une courbe qui décrit une fonction contenu dans la classe OutilsMath. Le plan Cartésien
 * possède constamment 12 valeurs apparentes en x et en y , celle-ci changent selon les données
 * que l'utilisateur entre. Ce fonctionnement est optimal puisqu'il permet l'affichage clair des données.
 */
/**
 * @author Zakaria Soudaki
 */

public class PlanCartesien extends JPanel {
	
	private static final long serialVersionUID = 1L;
	/**
	 * la coordonnée minimum en x par défaut (réel)
	 */
	private final double DEFAUT_X_MIN = -4;
	
	/**
	 * la coordonnée maximale en x par défaut (réel)
	 */
	private final double DEFAUT_X_MAX = 4;  
	
	/**
	 * la coordonnée minimum en y par défaut (réel)
	 */
	private final double DEFAUT_Y_MIN = -4;
	
	/**
	 * la coordonnée maximale en y par défaut (réel)
	 */
	private final double DEFAUT_Y_MAX = 4; 
	
	/**
	 * la coordonnée minimal en x actuelle (réel)
	 */
	private double xMin = DEFAUT_X_MIN;
	
	/**
	 * la coordonnée maximale en x actuelle (réel)
	 */
	private double xMax = DEFAUT_X_MAX;
	
	/**
	 * la coordonnée minimal en y actuelle (réel) 
	 */
	private double yMin = DEFAUT_Y_MIN;
	
	/**
	 * la coordonnée maximale en y actuelle (réel)
	 */
	private double yMax = DEFAUT_Y_MAX;
	
	/**
	 * Diametre du point sur la fonction (entier)
	 */
	private double diametreCercle = 15;
	
	/**
	 * Quantité de sel par défaut (entier)
	 */
	private final int QUANTITE_SEL = 40;
	
	/**
	 * Quantité d'eau par défaut (entier)
	 */
	private final int QUANTITE_EAU = 15;
	
	/**
	 * Débit par défaut (réel)
	 */
	private final double DEBIT = 1.5;
	
	/**
	 * Temps initial ( initialement 0 )
	 */
	private final int TEMPS = 0;
	
	/**
	 * Quantité de sel actuelle (entier)
	 */
	private int quantiteSel = QUANTITE_SEL;
	
	/**
	 * Quantité d'eau actuelle (entier)
	 */
	private int quantiteEau = QUANTITE_EAU;
	
	/**
	 * Débit actuel (réel)
	 */
	private double debit = DEBIT;
	
	/**
	 * Temps actuel / courant (entier)
	 */
	private int temps = TEMPS;

	/**
	 * Lignes des axes , de la courbe, du grillage et des taquets. De type  Path2D.Double
	 */
	private Path2D.Double axes, ligneBrisee, lignesGrillage, lignesTaquet ;
	/**
	 * Objet de type Ellipse2D.Double utilisé pour créer le Point sur la fonction
	 */
	private Ellipse2D.Double cerclePoint;
	
	/**
	 * Nombre de segment sur le tracer de la fonction ( de type entier)
	 */
	private int nbSegmentsPourApproximer = 80;
	
	/**
	 * Nombre de pixels entre chaque case du graphique en x et en y ( de type réel)
	 */
	private double pixelsParUniteX, pixelsParUniteY;
	
	/**
     * Constructeur de la classe PlanCartesien.
     * Crée le composant et fixe la couleur de fond par défaut.
     */
	public PlanCartesien() {	
		setBackground(Color.white);
		setPreferredSize(new Dimension(425,400));
	}//fin du constructeur
	
	/**
	 * Dessiner la fonction sur le composant
	 * @param g Le contexte graphique
	 */
	// Auteur : Zakaria Soudaki
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;	
		
		recadrer( -5,61, -ecartEntreTaquets() ,(concentrationInitiale())+ecartEntreTaquets()  );		
		
		//anticrenage 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		pixelsParUniteX = getWidth()/(xMax-xMin);
		pixelsParUniteY = getHeight()/(yMax-yMin);
		
		AffineTransform matTransfo = new AffineTransform();
		
		matTransfo.scale(1, -1);
	    matTransfo.scale(pixelsParUniteX, pixelsParUniteY);
	    matTransfo.translate(-xMin, -yMax);
	    
		//on dessine les axes, la courbe, le quadrillage les taquets et les valeurs
		g2d.setColor(Color.gray);
		
		pixelsParUniteX = getWidth()/(xMax-xMin);
		pixelsParUniteY = getHeight()/(yMax-yMin);

		creerAxes();
		creerApproxCourbe();
	    quadrillage();
	    taquets();
	    
	    BasicStroke stroke = new BasicStroke(2);
		g2d.setStroke(stroke);
		
		g2d.setColor(Color.LIGHT_GRAY);
		
		g2d.draw(matTransfo.createTransformedShape(lignesGrillage));
		
		g2d.setColor(Color.black);
		
		g2d.draw(matTransfo.createTransformedShape(lignesTaquet));
		
		g2d.setFont(new Font( "Roboto", Font.BOLD, 9));
		
		chiffresAxe(g2d);
		
	    g2d.setColor(Color.black);
	    
	    g2d.draw(matTransfo.createTransformedShape(axes));
	    
	    //on dessine la courbe 
	    g2d.setColor(Color.red);
	    g2d.draw(matTransfo.createTransformedShape(ligneBrisee)); 
	    
	    g2d.setColor(Color.blue);
	    cerclePoint();
		g2d.fill(cerclePoint);
	    
	}//fin paintComponent

	/**
	 * Creation du Path2D formant les axes
	 */
	private void creerAxes() {
	    axes = new Path2D.Double ();
	    axes.moveTo( xMin, 0 );
	    axes.lineTo( xMax,  0 );
	    axes.moveTo( 0,  yMin );
	    axes.lineTo( 0,  yMax );
	}
	
	/**
	 * Creation de l'approximation de la courbe sous la forme d'un Path2D
	 */
	private void creerApproxCourbe() {
	    double x, y;

	    ligneBrisee = new Path2D.Double();
	    x = 0;  //on commence a l'extreme gauche
	  	y = evalFonction(x);
	  	ligneBrisee.moveTo( x, y );
	
	    for (int k=1; k<nbSegmentsPourApproximer+1; k++) {
	   		x = 0+ k*(xMax-xMin)/nbSegmentsPourApproximer;   //on ajoute un intervalle fixe en x
	   		
	   		
	    	y = evalFonction(x); //modifier cette ligne!!!
	    	ligneBrisee.lineTo( x, y); //modifier cette ligne!!!
	    			
	    }//fin for
	   
	}
	
	/**
	 * Evaluation de la valeur de la fonction en vigueur pour un x donné
	 * @param x Valeur de x
	 * @return la valeur de la fonction pour ce x, en fonction de la fonction envigueur
	 */
	// Auteur : Zakaria Soudaki
	public double evalFonction(double x) {
		if (OutilsMath.volumeEau(quantiteEau, debit, x)>= 300) {
			return OutilsMath.concentrationSel(quantiteSel, 300);
		}else {
			return OutilsMath.concentrationSel(quantiteSel, OutilsMath.volumeEau(quantiteEau, debit, x));
		}
		
	}
	
	/**
	 * Modifie le nombre de petits segments de droite qui formeront la courbe (nb d'echantillonages)
	 * @param nbSegmentsPourApproximer Le nombre de segments voulus
	 */
	public void setNbSegmentsPourApproximer(int nbSegmentsPourApproximer) {
		this.nbSegmentsPourApproximer = nbSegmentsPourApproximer;
		repaint();
	}

	/**
	 * Permet de modifier les limites entre lesquelles la fonction sera tracee
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
	 * méthode pour tracer le quadrillage du graphique
	 */
	// Auteur : Zakaria Soudaki
	public void quadrillage() { //DEBUT
		lignesGrillage = new Path2D.Double();
		
		for (double i = xMin*5; i < xMax*5; i+=5) {
			lignesGrillage.moveTo(i, yMin);
			lignesGrillage.lineTo(i, yMax);
		}
	    for (double j = yMin; j < yMax;j+=ecartEntreTaquets()) {
	    	lignesGrillage.moveTo(xMin, j );
	    	lignesGrillage.lineTo(xMax, j);
	    }
	}//FIN
	/**
	 * méthode qui dessine les taquets en x et en y avec le bon écart de valeurs entre chacun des taquets
	 */
	// Auteur : Zakaria Soudaki
	public void taquets() {//DEBUT
	    lignesTaquet = new Path2D.Double();
	    
	    for (double k = 0; k < xMax; k+=5) {
	    	 lignesTaquet.moveTo(k, -concentrationInitiale()/90);
	    	 lignesTaquet.lineTo(k, concentrationInitiale()/90);
		}
	    for (double a = 0; a < yMax;a+=ecartEntreTaquets()) {
	    	lignesTaquet.moveTo(-0.55, a);
	    	lignesTaquet.lineTo(0.55, a);
	    }
	}//FIN
	/**
	 * méthode qui permet de dessiner les valeurs vis à vis de chacun des taquets
	 * et de les centrer
	 * @param g2d Le contexte graphique sur lequel ajouter les chiffres
	 */
	// Auteur : Zakaria Soudaki
	public void chiffresAxe(Graphics2D g2d) {//DEBUT
		int valeurX = (int) (xMin);
		double valeurY = (int) (yMin);
		
		DecimalFormat decimalFormat = new DecimalFormat("#0.000"); 
		
		for (double i= 0 ; i< (xMax-xMin)/5 ; i++) {//DEBUT FOR 
			
			int positionX =  (int) (i*pixelsParUniteX*5+xMin);
		    int positionY = (int) (getHeight()+((yMin*pixelsParUniteY*0.1 -10)));
		    
			String texte = "" + valeurX;
			
			if(valeurX > 0) {
				
		    	g2d.drawString(texte, positionX, positionY);
		    }
			
			valeurX += 5;
		}//FIN FOR
		for (int i = 0; i < (yMax-yMin)/ecartEntreTaquets(); i++) {//DEBUT FOR
			
			int positionX =  (int) (-xMin*pixelsParUniteX-28);
		   int positionY = (int) (getHeight()- i*pixelsParUniteY*ecartEntreTaquets() -pixelsParUniteY*ecartEntreTaquets());
			//int positionY = (int) (getHeight() - (i *0.25 *pixelsParUniteY));
			
		    String texte = decimalFormat.format(valeurY);
		    
		    if(valeurY > 0) {
		    	
		    	g2d.drawString(texte, positionX, positionY);
		    }
			valeurY +=ecartEntreTaquets();
		}//FIN FOR
	}//FIN
    /**
	 * Calcul la valeur de la concentration à l'instant 0 secondes
	 * @return la concentration à l'instant 0 secondes
	 */
	// Auteur : Zakaria Soudaki
	public double concentrationInitiale () {//DEBUT
	    double c = evalFonction(0);
	    return c;
	    
	}//FIN
	/**
	 * Méthode qui calcule la valeur de l'écart entre les taquets dans le graphique
	 * @return l'écart entre chaque taquet dans le graphique
	 */
	// AUTEUR : Zakaria Soudaki
	public double ecartEntreTaquets() {//DEBUT
		return concentrationInitiale()/12;
		
	}//FIN

	
	/**
     * Crée un cercle pour représenter un point à un instant donné.
     */
	// Auteur : Zakaria Soudaki
	public void cerclePoint() {//DEBUT
		double positionCoinGaucheX = (temps*pixelsParUniteX) -(xMin*pixelsParUniteX+(diametreCercle/2));
		double positionCoinGaucheY = getHeight()+ ((yMin*pixelsParUniteY))-(evalFonction(temps)*pixelsParUniteY)-(diametreCercle/2);
		
		cerclePoint = new Ellipse2D.Double (positionCoinGaucheX,positionCoinGaucheY, diametreCercle, diametreCercle );
	}//FIN
	/**
     * Modifie le temps.
     * @param temps Le nouveau temps.
     */
	// Auteur : Zakaria Soudaki
	public void setTemps (int temps) {//DEBUT
		this.temps = temps;
		 cerclePoint(); 
		repaint();
	}//FIN
	/**
     * Modifie la quantité de sel.
     * @param sel La nouvelle quantité de sel.
     */
	// Auteur : Zakaria Soudaki
	public void setSel (int sel) {//DEBUT
		this.quantiteSel = sel;
		 cerclePoint(); 
		repaint();
	
	}//FIN
	/**
     * Modifie la quantité d'eau.
     * @param eau La nouvelle quantité d'eau.
     */
	// Auteur : Zakaria Soudaki
	public void setEau (int eau) {//DEBUT
		this.quantiteEau = eau ;
		 cerclePoint(); 
		repaint();
	}//FIN
	 /**
     * Modifie le débit.
     * @param debit Le nouveau débit.
     */
	// Auteur : Zakaria Soudaki
	public void setDebit (double debit) {//DEBUT
		this.debit = debit;
		 cerclePoint(); 
		repaint();
	}//FIN
	
	/**
	 * Méthode qui permet d'obtenir la valeur du diametre courant du cercle qui représente
	 * le point sur le graphique.
	 * @return valeur courante du diametre du cercle qui sert Point.
	 */
	//AUTEUR : Zakaria Soudaki
	public double getDiametreCercle() {//DEBUT
		return diametreCercle;
	}//FIN
	/**
	 * Méthode qui permet de définir un nouveau diametre pour le cercle qui sert de point
	 * sur le graphique.
	 * @param nouveauDiametre nouvelle valeur du diametre du cercle qui sert de point .
	 */
	//AUTEUR : Zakaria Soudaki
	public void setDiametreCercle(double nouveauDiametre) {//DEBUT
		this.diametreCercle = nouveauDiametre;
		repaint();
	}//FIN
}//fin classe
