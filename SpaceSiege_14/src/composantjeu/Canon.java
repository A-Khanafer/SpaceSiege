package composantjeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;

import interfaces.Dessinable;
import interfaces.Selectionnable;
import outils.OutilsImage;
import physique.Vecteur2D;
/**
 * Classe représentant un canon dans une application d'animation physique. Le canon est capable de tirer des balles et de pivoter en fonction
 * de l'interaction de l'utilisateur. Cette classe gère la géométrie du canon, y compris son corps, son avant arrondi et sa base. Elle permet également
 * de contrôler l'angle et la force de tir en ajustant la position de la souris par rapport au canon.
 * @author Benakmoum Walid
 * */
public class Canon extends JPanel implements Selectionnable, Dessinable {

    private static final long serialVersionUID = 1L;

	/**
     * Position horizontale du canon sur le panneau de dessin.
     */
    private double x;

    
    private double pixelsParMetre;
    /**
     * Position verticale du canon sur le panneau de dessin.
     */
    private double y;

    /**
     * Représente le corps du canon sous forme de rectangle. Initialement positionné avec une hauteur de 50 pixels et une largeur de 100 pixels.
     */
    private Rectangle2D.Double rectangleCanon;

    /**
     * Représente l'avant arrondi du canon. Utilisé pour le dessin du canon et possiblement pour des calculs de collision ou d'interaction.
     */
    private Ellipse2D.Double cercle;

    /**
     * Base du canon représentée par un ellipse, utilisée pour stabiliser visuellement le canon sur le panneau.
     */
    private Ellipse2D.Double base;

    /**
     * Indique si une balle a été tirée par le canon. Ce champ est utilisé pour contrôler l'affichage de la balle et son comportement.
     */
    private  boolean balleTiree = false;

    /**
     * Marqueur pour initialiser la balle uniquement lors du premier tir. Cela permet de réinitialiser ou de configurer la balle avant son premier usage.
     */
    private  boolean premiereFois = true;

    /**
     * Largeur du canon, définie initialement à 100 pixels. Influence la taille du rectangle représentant le corps du canon.
     */
    private double largeur = 100;

    /**
     * Hauteur du canon, définie initialement à 50 pixels. Affecte la taille du rectangle du corps du canon ainsi que du cercle avant.
     */
    private double hauteur = 50;

    /**
     * Aire couvrant la forme du cercle à l'avant du canon. Utilisée pour le dessin et peut-être pour les interactions ou les collisions.
     */
    private Area aireCercle;

    /**
     * Aire couvrant la forme rectangulaire du corps du canon. Utilisée pour le dessin et potentiellement pour des interactions.
     */
    private Area aireRect;

    /**
     * Aire couvrant la base du canon. Sert à stabiliser visuellement le canon et peut être utilisée pour les interactions.
     */
    private Area aireBase;

    /**
     * L'objet Balleque le canon utilise pour tirer. Ce champ est crucial pour le contrôle du tir et le comportement de la balle.
     */
 
    
    private Balle balleActuelle;

    /**
     * Angle actuel de rotation du canon, en degrés. Ce champ détermine la direction dans laquelle la balle sera tirée.
     */
    private double rotation = 0;

    /**
     * Vecteur représentant la vitesse de la balle au moment du tir. Utilisé pour déterminer la trajectoire initiale de la balle.
     */
    private Vecteur2D vitesse = new Vecteur2D(20, 20);

    /**
     * Représente la direction et la force de tir sous forme de flèche. Utilisée pour indiquer visuellement ces paramètres à l'utilisateur.
     */
    private FlecheDeTir positionDeTir;

    /**
     * Distance horizontale depuis l'origine de la flèche de tir jusqu'à sa position actuelle, affectant la force du tir.
     */
    private double dx = 0;

    /**
     * Distance verticale depuis l'origine de la flèche de tir jusqu'à sa position actuelle, influençant l'angle de tir.
     */
    private double dy = 0;
    
    /**
     * Indique si la prochaine image doit être affichée. Cela peut être utilisé pour gérer les animations ou les états de jeu.
     */
    private boolean prochaineImage = false;

    /**
     * Indique la balle choisie par le joueur. Utile pour sélectionner le type de balle à tirer.
     */
    private static int balleChoisie = 1;
    
    private String urlImage;
    
    private Image image = null;
    /**
     * Constructeur de la classe Canon.
     * 
     * @param x             Position horizontale initiale du canon.
     * @param y             Position verticale initiale du canon.
     * @param pixelsParMetre Facteur de conversion de pixels en mètres.
     */
    //Benakmoum Walid
	public Canon(double x,double y,double pixelsParMetre,String urlImage) {
		this.pixelsParMetre=pixelsParMetre;
		this.x=x*this.pixelsParMetre;
		this.y=y*this.pixelsParMetre;
		this.urlImage=urlImage;
		hauteur = 6*this.pixelsParMetre;
		largeur = 12*this.pixelsParMetre;

		creerLaGeometrie();
	}
	  /**
     * Crée la géométrie du canon, incluant le corps principal, la base et le cercle à l'avant.
     */
	//Benakmoum Walid
	private void creerLaGeometrie() {
		
		rectangleCanon=new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur);
		base=new Ellipse2D.Double(0,y-10,3,hauteur+20);
		cercle=new Ellipse2D.Double(3,y,hauteur,hauteur);
		aireRect=new Area(rectangleCanon);
		aireCercle=new Area(cercle);
		aireBase= new Area(base);
		aireRect.add(aireCercle);
		positionDeTir = new FlecheDeTir(cercle.getCenterX(), cercle.getCenterY(), dx,dy);
		//image =OutilsImage.lireImageEtRedimensionner(urlImage,(int)103,(int) 51);


		 if (!balleTiree && premiereFois) {

			 balleActuelle = new BalleBasique(50, 2, hauteur-20, new Vecteur2D(3, y), new Vecteur2D(0, 0),pixelsParMetre);

			 premiereFois = false;
		    }
if(!balleTiree) {
	balleActuelle.setPosition(new Vecteur2D(3*pixelsParMetre,y*pixelsParMetre));
}

	}

	/**
     * Dessine le canon sur un composant graphique.
     * 
     * @param g2d L'objet Graphics2D utilisé pour le dessin.
     */
    @Override
  //Benakmoum Walid
	public void dessiner(Graphics2D g2d) {
    	Graphics2D g2dPrive = (Graphics2D) g2d.create();
		g2dPrive.setColor(Color.BLUE);
		
		positionDeTir.dessiner(g2dPrive);
		
		g2dPrive.fill(aireBase);
	if(balleTiree || prochaineImage) {
		balleActuelle.dessiner(g2dPrive);
	}

	
		//ROTATED
		g2dPrive.rotate(rotation, cercle.getCenterX(), cercle.getCenterY());
		g2dPrive.setColor(Color.BLACK);
		g2dPrive.fill(aireRect);
		
	
	}
	

    /**
     * Vérifie si un point donné est contenu dans la zone du canon ou de sa base.
     * 
     * @param xPix Coordonnée X du point à vérifier.
     * @param yPix Coordonnée Y du point à vérifier.
     * @return true si le point est contenu dans le canon ou sa base, false sinon.
     */
    @Override
  //Benakmoum Walid
	public boolean contient(double xPix, double yPix) {
		// TODO Auto-generated method stub
		if(aireRect.contains(xPix, yPix)|| aireBase.contains(xPix, yPix)) {
			return true;
		}
		return false;
	}
    /**
     * Oriente le canon en fonction de la position de la souris.
     * 
     * @param ex Coordonnée X de la souris.
     * @param ey Coordonnée Y de la souris.
     */
  //Benakmoum Walid
	public void rotate(int ex, int ey) {
	   
		
	    rotation = positionDeTir.getAngle();
	 //   AffineTransform transform = AffineTransform.getRotateInstance(rotation, x + hauteur / 2, y + hauteur / 2);
	  
	  // = new Area(transform.createTransformedShape(new Rectangle2D.Double(3+hauteur/2, y, largeur, hauteur)));
	//    aireCercle = new Area(transform.createTransformedShape(new Ellipse2D.Double(3,y,hauteur,hauteur)));	 

	    
	    if(ey < cercle.getCenterY()) {
	    	vitesse.setX(Math.cos(rotation)*positionDeTir.calculerModulus()/4);
	    	vitesse.setY(Math.sin(rotation)*positionDeTir.calculerModulus()/4);
	    	balleActuelle.setVitesse(vitesse);
	    }else if(ey > cercle.getCenterY()) {
	    	double newRotCos = 2*Math.PI - rotation;
	    	double newRotSin = Math.PI - rotation;
	    	vitesse.setX(Math.cos(newRotCos)*positionDeTir.calculerModulus()/4);
	 	    vitesse.setY(Math.sin(newRotSin)*positionDeTir.calculerModulus()/4);
	 	   balleActuelle.setVitesse(vitesse);
	    }else {
	    	vitesse.setX(positionDeTir.calculerModulus()/8);
	    }
	    
	}
	 /**
     * Déplace verticalement le canon.
     * 
     * @param eY Nouvelle position verticale du canon.
     */
	//Benakmoum Walid
	public void move( int eY) {
		this.y = eY - hauteur/2;
		creerLaGeometrie();
	;
	}
	/**
     * Retourne la coordonnée x de la pointe du canon. Utile pour positionner la balle lors du tir.
     * 
     * @return La coordonnée x de la pointe du canon.
     */
	//Benakmoum Walid
	public int getPointeX() {
	return   (int) rectangleCanon.getMaxX();
	   
	}
	/**
     * Retourne la coordonnée y du centre du canon. Utilisé pour aligner la balle avec le canon lors du tir.
     * 
     * @return La coordonnée y du centre du canon.
     */
	//Benakmoum Walid
	public int getPointeY() {
	   
    return (int)  rectangleCanon.getCenterY();
	 
	}
	/**
     * Retourne la balle associée au canon. Permet d'accéder à la balle pour la manipuler, par exemple, lors du tir.
     * 
     * @return L'objet BalleBasique actuellement associé au canon.
     */
	//Benakmoum Walid
	public Balle getBalle() {
		return this.balleActuelle;
	}
	
	/**
     * Fait avancer la balle d'un pas, en fonction du temps deltaT spécifié. Utilisé pour animer le mouvement de la balle après le tir.
     * 
     * @param deltaT Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
	//Benakmoum Walid
	public void avancerUnPas(double deltaT) {
		this.balleActuelle.avancerUnPas(deltaT);

	}
	 /**
     * Retourne la flèche indiquant la direction et la force du tir. Utile pour afficher visuellement ces paramètres dans l'interface utilisateur.
     * 
     * @return L'objet FlecheDeTir représentant la direction et la force du tir actuel.
     */
	//Benakmoum Walid
	public FlecheDeTir getFleche() {
		return this.positionDeTir;
	} 
	/**
     * Ajuste la taille et l'orientation du canon en fonction de la position de la souris, modifiant ainsi la direction et la force du tir.
     * 
     * @param x2 La coordonnée x de la souris.
     * @param y2 La coordonnée y de la souris.
     */
	//Benakmoum Walid
	public void changerTaille(double x2, double y2) {
	    positionDeTir.ajusterTaille(x2, y2);
	    rotation = positionDeTir.getAngle();
	    vitesse.setX(Math.cos(rotation) * positionDeTir.calculerModulus() / 4);
	    vitesse.setY(Math.sin(rotation) * positionDeTir.calculerModulus() / 4);
	    balleActuelle.setVitesse(vitesse);

	}
	/**
     * Marque la balle comme ayant été tirée et réinitialise la géométrie du canon pour refléter tout changement nécessaire suite à cet événement.
     * Cela peut inclure le repositionnement de la balle ou d'autres ajustements visuels du canon.
     */
	//Benakmoum Walid
	public void setBalleTiree() {
		balleTiree=true;
		System.out.println("EST CE QUE JE TIREEEE"+ balleTiree);
		
		creerLaGeometrie();
	}
	/**
     * Indique si le canon est dans son état initial, avant que toute balle ait été tirée. 
     * Cela peut être utilisé pour contrôler certains comportements ou configurations initiales de la balle ou du canon lui-même.
     * 
     * @return Vrai si aucune balle n'a encore été tirée, faux autrement.
     */
	//Benakmoum Walid
	public boolean isPremiereFois() {
		return premiereFois;
	}
	/**
     * Définit si le canon est considéré dans son état initial de "première fois". 
     * Cela peut affecter la manière dont les balles sont initialisées ou d'autres logiques spécifiques avant le premier tir.
     * 
     * @param premiereFois Vrai pour indiquer que le canon est dans son état initial, faux autrement.
     */
	//Benakmoum Walid
	public void setPremiereFois(boolean premiereFois) {
		this.premiereFois = premiereFois;
	}
	/**
     * Choisi la balle associée au canon.
     * 
     * @param nb Le numéro de la balle choisie.
     */
    //Benakmoum Walid

    public void choisirBalleCanon(int nb) {
        balleChoisie = nb;
        creerLaGeometrie();
    }

    /**
     * Retourne la balle actuellement associée au canon.
     * 
     * @return L'objet Balle actuellement associé au canon.
     */
    //Benakmoum Walid

    public Balle getBalleActuelle() {
        return balleActuelle;
    }

    /**
     * Définit si la prochaine image doit être affichée.
     * 
     * @param bol true pour afficher la prochaine image, false sinon.
     */
    public void setProchaineImage(boolean bol) {
        prochaineImage = bol;
    }

    /**
     * Définit la balle actuelle associée au canon.
     * 
     * @param i Le numéro de la balle à associer.
     */
    //Benakmoum Walid

    public void setBalleActuelle(int i) {
        switch (i) {
            case 1:
                balleActuelle = new BalleBasique(50, 2, hauteur, new Vecteur2D(3*pixelsParMetre, y*pixelsParMetre), vitesse, pixelsParMetre);
                break;
            case 2:
                balleActuelle = new BalleElastique(50, 2, hauteur, new Vecteur2D(3*pixelsParMetre, y*pixelsParMetre), vitesse, pixelsParMetre);
                break;
            case 3:
                balleActuelle = new BalleNova(50, 2, hauteur, new Vecteur2D(3*pixelsParMetre, y*pixelsParMetre), vitesse, pixelsParMetre);
                break;
        }
    }

    /**
     * Définit le facteur de conversion de pixels en mètres.
     * 
     * @param pixel Le facteur de conversion à définir.
     */
    //Benakmoum Walid
    public void setPixelsParMetre(int pixel) {
        this.pixelsParMetre = pixel;
        creerLaGeometrie();
    }

}

