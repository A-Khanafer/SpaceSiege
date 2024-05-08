package bacasable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import composantjeu.Canon;
import composantjeu.Monstres;
import interfaces.Obstacles;
import obstacles.Cercle;
import obstacles.CercleElectrique;
import obstacles.Epines;
import obstacles.ObstacleHolder;
import obstacles.PlaqueRebondissante;
import obstacles.Rectangle;
import obstacles.Triangle;
import physique.Collisions;
import physique.Vecteur2D;

/**
 * PanelBacASable est un JPanel personnalisé qui représente un bac à sable pour dessiner des rectangles, des triangles
 * et d'autres formes géométriques.
 * Il permet d'ajouter, de déplacer, de redimensionner et de faire pivoter les formes géométriques.
 * Il permet également de créer des obstacles, un canon et un monstre.
 * Il permet de sauvegarder les obstacles dessinés dans un fichier texte.
 * Il permet de calculer les limites des formes et de les positionner correctement sur le panneau.
 * @author Ahmad Khanafer
 */
public class PanelBacASable extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Le ratio utilisé pour convertir les mètres en pixels. */
    private double pixelParMetres = 10;

    /** Indique si c'est la première fois que le panneau est peint. */
    private boolean premiereFois = true;

    /** Le nombre actuel de rectangles dans le panneau. */
    private int nbrRec = 0;

	/**
	 * Le nombre actuel de triangles dans le panneau.
	 */
	private int nbrTri = 0;
	/**
	 * Le nombre actuel de cercles dans le panneau.
	 */
	private int nbrCercle = 0;
	/**
	 * Le nombre actuel de cercles electriques dans le panneau.
	 */
	private int nbrCercleElectrique = 0;
	/**
	 * Le nombre actuel d'epines dans le panneau.
	 */
	private int nbrEpines = 0;
	/**
	 * Le nombre actuel de monstres dans le panneau.
	 */
	private int nbrMonstre = 0;
	/**
	 * Le nombre actuel de canon dans le panneau.
	 */
	private int nbrCanon = 0;
	/**
	 * Le nombre actuel de plaque rebondissante dans le panneau.
	 */
	private int nbrPlaqueRebondissante = 0;
	
	/**
	 * boolean activer désactiver mode éditeur
	 */
	private boolean editeurModeOn = true;
	
/**
 * porteur d'obstacles
 */
	private ObstacleHolder obHolder = new ObstacleHolder();
	
	/**
	 * boolean canon cliqué
	 */
	private boolean canonClick = false;

	/**
	 * canon lui-même
	 */
	private Canon canon;
	
	/**
	 * monstre lui-même
	 */
	private Monstres monstre;
	
	/**
	 * boolean pour dessiner le monstre ou non
	 */
	private boolean monstredessin = false;
	
	/**
	 * boolean monstre créer ou non
	 */
	boolean monstreCreer = false;
	
	/**
	 * les coins du cercle
	 */
	private Point2D.Double[] coinsCercle;
	
	/**
	 * les coins du cercle éléctrique
	 */
	private Point2D.Double[] coinsCercleE;
	
	/**
	 * les coins des épines
	 */
	private Point2D.Double[] coinsEpines;
	/**
	 * les coins du rectangle
	 */
	private Point2D.Double[] coinsRec;
	/**
	 * les coins du triangle
	 */
	private Point2D.Double[] coinsTri;
	
	
	private Stack<Obstacles> ctrlZ = new Stack<Obstacles>();
   

   
    
   

    /**
     * Constructeur par défaut de PanelBacASable.
     * Initialise les paramètres par défaut et ajoute des écouteurs de souris.
     */
    public PanelBacASable() {
    	setFocusable(true);
        setBackground(new Color(255, 255, 255));
        canon = new Canon(0, 10, pixelParMetres);	
        ecouteurSouris();
        ecouteurFormeEffacage();
        
    }

    /**
     * Redéfinition de la méthode paintComponent pour dessiner les formes géométriques.
     * @param g Objet Graphics pour dessiner sur le JPanel.
     */
    //Ahmad Khanafer
    @Override
    public void paintComponent(Graphics g ) {
    	requestFocusInWindow();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        pixelParMetres = getWidth()/150;
        
        if(obHolder != null) {
            obHolder.drawContient(g2d);
        }
        canon.dessiner(g2d);
        
        if (monstredessin) {
            monstre.dessiner(g2d);
        }
    }

    /**
     * Ajoute un rectangle au panneau.
     */
  //Ahmad Khanafer
    public void ajouterRectangle() {
        if(nbrRec < 3) {
            double espace = 0;
            Rectangle rec = new Rectangle(100 + espace, 100 + espace, pixelParMetres);
            obHolder.addObstacle(rec);
            espace = espace + 20;
            nbrRec++;
            
            coinsRec = rec.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de rectangles atteint");
        }
    }

    /**
     * Ajoute un triangle au panneau.
     */
  //Ahmad Khanafer
    public void ajouterTriangle() {
        if(nbrTri < 2) {
            double espace = 0;
            Triangle tri = new Triangle(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(tri);
            espace = espace + 20;
            nbrTri++;
            
            coinsTri = tri.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de triangles atteint");
        }
    }

    /**
     * Ajoute un cercle au panneau.
     */
  //Ahmad Khanafer
    public void ajouterCercle() {
        if(nbrCercle < 3) {
            double espace = 0;
            Cercle cer = new Cercle(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercle++;
            
            coinsCercle = cer.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de cercles atteint");
        }
    }
    
    /**
     * Ajoute un cercle électrique au panneau.
     */
  //Ahmad Khanafer
    public void ajouterCercleElectrique() {
        if(nbrCercleElectrique < 3) {
            double espace = 0;
            CercleElectrique cer = new CercleElectrique(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercleElectrique++;
            
            coinsCercleE = cer.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de cercles électriques atteint");
        }
    }
    
    /**
     * Ajoute des épines au panneau.
     */
  //Ahmad Khanafer
    public void ajouterEpines() {
        if(nbrEpines < 3) {
            double espace = 0;
            Epines epi = new Epines(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(epi);
            nbrEpines++;
            
            coinsEpines = epi.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal d'épines atteint");
        }
    }
    
    /**
     * Ajoute une plaque rebondissante au panneau.
     */
  //Ahmad Khanafer
    public void ajouterPlaqueRebondissante() {
        if(nbrPlaqueRebondissante < 3) {
            double espace = 0;
            PlaqueRebondissante plaque = new PlaqueRebondissante(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(plaque);
            nbrPlaqueRebondissante++;
            
            coinsEpines = plaque.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de plaques rebondissantes atteint");
        }
    }

    /**
     * Ajoute des écouteurs de souris pour la gestion des actions de l'utilisateur.
     */
  //Ahmad Khanafer
    private void ecouteurSouris() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gestionSourisFormeClick(e);
                repaint();
            }
        });
    
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gestionSourisFormeDragged(e);
                repaint();
            }
        });
    }
    
    
    

    /**
     * Gère les actions de l'utilisateur lorsqu'il déplace la souris.
     * @param e L'événement souris.
     */
  //Ahmad Khanafer
    private void gestionSourisFormeDragged(MouseEvent e) {
        if(canon.contient(e.getX(), e.getY())) {
            canon.moveY(e.getY());
            repaint();
        }
        
        if(monstreCreer) {
            if(monstre.contient(e.getX(), e.getY())) {
                monstre.move(e.getX(), e.getY());
                repaint();
            }
        }
        
        for(Obstacles ob : obHolder.getObstacleHolder()) {
            int index = ob.getClickedResizeHandleIndex(e.getX(), e.getY());
            if (ob.isClickedOnIt() && index != -1) {
                ob.redimension(index, e.getX(), e.getY());
                repaint();
            } else if(ob.isClickedOnIt() && index == -1) {
                ob.rotate(e.getX(), e.getY());
                repaint();    
            } 
           
            if(ob.contient(e.getX(), e.getY()) && !ob.isClickedOnIt()) {
                if(ob.getPositionCentre().getY() < getHeight()-20) {
                    ob.move(e.getX(), e.getY());
                }

                if (ob.getPositionCentre().getY() >= getHeight()-20) {
                    ob.move(e.getX(), (int) (  getHeight()/2));
                } 
                
                repaint();
                break;
            }       
        }
    }


    /**
     * Gère les actions de l'utilisateur lorsqu'il clique sur une forme.
     * @param e L'événement souris.
     */
  //Ahmad Khanafer
    private void gestionSourisFormeClick(MouseEvent e) {
        for(Obstacles ob : obHolder.getObstacleHolder()) {
            if(ob.contient(e.getX(), e.getY())) {
                ob.setClickedOnIt(true);
                repaint();
            } else {
                ob.setClickedOnIt(false);
                repaint();
            }
        }
    }
    
    //Ahmad Khanafer
    private void ecouteurFormeEffacage() {

	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	 for(Obstacles ob : obHolder.getObstacleHolder()) { 
	        		if(ob.isClickedOnIt() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE ) {
	        			obHolder.getObstacleHolder().remove(ob);
	        			ctrlZ.push(ob);
	        			repaint();
	        		}
	        	 }
	        	 
	        	 if(e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown() && !ctrlZ.isEmpty()) {
	        		 	Obstacles ob = ctrlZ.pop();
	        		 	ob.setClickedOnIt(false);
		        		obHolder.getObstacleHolder().add(ob);
		        		repaint();
		        	}
            }
	    });
	}
    

	public void sauvegardeNiveau() {

		String filePath = System.getProperty("user.home") + "/Documents/obstacles.txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (Obstacles obstacle : obHolder.getObstacleHolder()) {

                String[] lines = obstacle.toString().split("\\n");
                writer.write("*" + lines[0]);
                for (int i = 1; i < lines.length; i++) {
                    writer.newLine();
                    writer.write(lines[i]);
                }
                writer.newLine();
            }
            writer.write("//");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoie le conteneur d'obstacles.
     * @return Le conteneur d'obstacles.
     */
  //Ahmad Khanafer
    public ObstacleHolder getObHolder() {
        return obHolder;
    }

    /**
     * Modifie le conteneur d'obstacles.
     * @param obHolder Le nouveau conteneur d'obstacles.
     */
  //Ahmad Khanafer
    public void setObHolder(ObstacleHolder obHolder) {
        this.obHolder = obHolder;
        repaint();
    }

    /**
     * Ajoute un monstre au panneau.
     */
    public void ajouterMonstre() {
        if (nbrMonstre < 1) {
            monstre = new Monstres(200,200, pixelParMetres, 1);
            nbrMonstre++;
            monstredessin = true;
            monstreCreer = true;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nombre maximal de monstres atteint");
        }
    }

    /**
     * Calcule les limites des formes et les positionne correctement sur le panneau.
     * @param tab Le tableau de points représentant les coins de la forme.
     */
    public void calculLimitesFormes(Point2D.Double[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].getX() >= getHeight()) {
                // Traitement à faire
            }
        }
    }

    
    
  
		
/**
 * Calcul la position en hauteur équivalente pour la forme.
 * @param centre Le centre de la forme.
 * @param point Le point à comparer.
 * @param hauteur La hauteur à calculer.
 * @return La position en hauteur équivalente.
 */
    public Point2D.Double calculHauteurEq ( Vecteur2D centre ,Point2D.Double point, double hauteur) {
    	
    	double dist1 = Collisions.distanceEntreDeuxPoints( point.getX(), point.getX() , point.getY() , hauteur);
    	double dist2 = Collisions.distanceEntreDeuxPoints( centre.getX() , centre.getX() , centre.getY(), hauteur);
    	
    	double posY = dist2 - dist1;
    	
    	return new Point2D.Double(centre.getX(), posY);
    	
    }
    
    
    
	

   }

 
	
    
	