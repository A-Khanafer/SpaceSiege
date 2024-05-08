package bacasable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	 * Le nombre actuel de plaque rebondissante dans le panneau.
	 */
	private int nbrPlaqueRebondissante = 0;
/**
 * porteur d'obstacles
 */
	private ObstacleHolder obHolder = new ObstacleHolder();
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
		
	private Stack<Obstacles> ctrlZ = new Stack<Obstacles>();
   
	
	private Rectangle2D.Double limiteCanon, limiteMonstre;

    /**
     * Constructeur par défaut de PanelBacASable.
     * Initialise les paramètres par défaut et ajoute des écouteurs de souris.
     */
    public PanelBacASable() {
    	setFocusable(true);
        setBackground(new Color(255, 255, 255));
        	
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
        canon = new Canon(0, 10, pixelParMetres);
        monstre = new Monstres(getWidth()- ((8*pixelParMetres)/2) - 100, getHeight()/2 - ((8*pixelParMetres)/2), pixelParMetres);
        if(obHolder != null) {
            obHolder.drawContient(g2d);
        }
        canon.dessiner(g2d);
        g2d.setColor( new Color(255, 0, 0, 100) );
        limiteCanon = new Rectangle2D.Double(0, 0, canon.getPointeX()+50, getHeight());
        limiteMonstre = new Rectangle2D.Double(getWidth()- ((20*pixelParMetres)/2) - 100,getHeight()/2 - ((20*pixelParMetres)/2) , 20*pixelParMetres, 20*pixelParMetres);
        g2d.fill(limiteCanon);
        g2d.fill(limiteMonstre);
        monstre.dessiner(g2d);

    }

    /**
     * Ajoute un rectangle au panneau.
     */
  //Ahmad Khanafer
    public void ajouterRectangle() {
        if(nbrRec < 100) {
            double espace = getWidth()/2;
            Rectangle rec = new Rectangle(100 + espace, getHeight()/2, pixelParMetres);
            obHolder.addObstacle(rec);
            espace = espace + 20;
            nbrRec++;
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
        if(nbrTri < 100) {
        	double espace = getWidth()/2;
            Triangle tri = new Triangle(50 + espace, getHeight()/2, pixelParMetres);
            obHolder.addObstacle(tri);
            espace = espace + 20;
            nbrTri++;
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
        if(nbrCercle < 100) {
        	double espace = getWidth()/2;
            Cercle cer = new Cercle(50 + espace, getHeight()/2, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercle++;
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
        if(nbrCercleElectrique < 100) {
        	double espace = getWidth()/2;
            CercleElectrique cer = new CercleElectrique(50 + espace, getHeight()/2, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercleElectrique++;  
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
        if(nbrEpines < 100) {
        	double espace = getWidth()/2;
            Epines epi = new Epines(50 + espace,getHeight()/2, pixelParMetres);
            obHolder.addObstacle(epi);
            nbrEpines++;
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
        if(nbrPlaqueRebondissante < 100) {
        	double espace = getWidth()/2;
            PlaqueRebondissante plaque = new PlaqueRebondissante(50 + espace, getHeight()/2, pixelParMetres);
            obHolder.addObstacle(plaque);
            nbrPlaqueRebondissante++;
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
            }
        });
    
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gestionSourisFormeDragged(e);
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
                
                if (ob.getPositionCentre().getX() <= canon.getPointeX() + 50) {
                    ob.move(getWidth()/2, e.getY());
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
	        	ArrayList<Obstacles> obTemp =  obHolder.getObstacleHolder();
	        	 for(Obstacles ob : obTemp) { 
	        		if(ob.isClickedOnIt() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE ) {
	        			obHolder.getObstacleHolder().remove(ob);
	        			ctrlZ.push(ob);
	        			repaint();
	        			break;
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
    //Permet la sauvegarde de niveau en creant un fichier texte et le met sur le bureau et verifie si une des formes est dans une zone interdite
    //Ahmad Khanafer
	public void sauvegardeNiveau() {
		boolean niveauPasAccepte = false;
		ArrayList<Obstacles> obTemp =  obHolder.getObstacleHolder();
		
	   	for(Obstacles ob : obTemp) {
	   		Area limiteMonstreAire = new Area(limiteMonstre);
	   		limiteMonstreAire.intersect(ob.toAire());
	   		Double[] coins = ob.getCoins();
	   		if(limiteCanon.contains(coins[0])){
				niveauPasAccepte = true;
	   			break;
	   		}
	   		if(limiteCanon.contains(coins[1])){
				niveauPasAccepte = true;
	   			break;
	   		}
	   		if(limiteCanon.contains(coins[2])){
				niveauPasAccepte = true;
	   			break;
	   		}
	  		if(limiteCanon.contains(coins[3])){
	   			niveauPasAccepte = true;
	   			break;
	   		}
	  		if(!limiteMonstreAire.isEmpty()) {
	  			niveauPasAccepte = true;
	   			break;
	  		}
	   	}
	   	
	   	if(niveauPasAccepte) {
	   		JOptionPane.showInternalMessageDialog(null, "Une ou plusieurs formes est dans une zone interdite");
	   	}else {
	   	
		   	System.out.println("gangn");
				String filePath = System.getProperty("user.home") + "/Documents/obstacles.txt";
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
					for (Obstacles obstacle : obHolder.getObstacleHolder()) {
		                String[] lines = obstacle.toString().split("\\n");
		                writer.write("*" + lines[0]);
		                for (int j = 1; j < lines.length; j++) {
		                    writer.newLine();
		                    writer.write(lines[j]);
		                }
		                writer.newLine();
		            }
		            writer.write("//");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
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

 
	
    
	