package sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import composantjeu.Canon;
import composantjeu.Monstres;
import interfaces.Obstacles;
import obstacles.Cercle;
import obstacles.CercleElectrique;
import obstacles.Epines;
import obstacles.ObstacleHolder;
import obstacles.Rectangle;
import obstacles.Triangle;

/**
 * PanelBacASable est un JPanel personnalisé qui représente un bac à sable pour dessiner des rectangles et des triangles.
 * Il permet d'ajouter, de déplacer, de redimensionner et de faire pivoter des formes géométriques.
 * @author Ahmad Khanafer
 */
public class PanelBacASable extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Le ratio utilisé pour convertir les mètres en pixels.
	 */
	private double pixelParMetres;

	/**
	 * Le tableau contenant les objets Rectangle.
	 */
	private Rectangle[] tableauRec;

	/**
	 * Le tableau contenant les objets Triangle.
	 */
	private Triangle[] tableauTri;
	/**
	 * Le tableau contenant les objets Cercle.
	 */
	private Cercle[] tableauCercle;
	
	private Canon canon;

	/**
	 * Indique si c'est la première fois que le panneau est peint.
	 */
	private boolean premiereFois = true;

	/**
	 * Le nombre actuel de rectangles dans le panneau.
	 */
	private int nbrRec = 0;

	/**
	 * Le nombre actuel de triangles dans le panneau.
	 */
	private int nbrTri = 0;

	private int nbrCercle = 0;
	
	private int nbrCercleElectrique = 0;
	
	private int nbrEpines = 0;
	
	private int nbrMonstre = 0;
	
	private int nbrCanon = 0;
	/**
	 * Indique si le mode éditeur est activé ou désactivé.
	 */
	private boolean editeurModeOn = true;

	private ObstacleHolder obHolder = new ObstacleHolder();


    /**
     * Constructeur par défaut de PanelBacASable.
     * Initialise les paramètres par défaut et ajoute des écouteurs de souris.
     */
	//Ahmnad Khanafer
    public PanelBacASable() {
        setBackground(new Color(255, 255, 255));
        tableauRec = new Rectangle[3];
        tableauTri = new Triangle[3];
        tableauCercle = new Cercle[3];
//        canon = new Canon()

        ecouteurSouris();
    }

    /**
     * Redéfinition de la méthode paintComponent pour dessiner les formes géométriques.
     * @param g Objet Graphics pour dessiner sur le JPanel.
     */
  //Ahmnad Khanafer
    public void paintComponent(Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        pixelParMetres = getWidth()/150;
        
        if(obHolder!=null) {
        	obHolder.drawContient(g2d);
        }
    }

    /**
     * Ajoute un rectangle au panel.
     */
  //Ahmnad Khanafer
    public void ajouterRectangle() {
        if(nbrRec < 3) {
        	double espace=0;
        	Rectangle rec = new Rectangle(100 + espace, 100 + espace, pixelParMetres);
            obHolder.addObstacle(rec);
            espace = espace + 20;
            nbrRec += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Rectangle Atteint");
        }
    }

    /**
     * Ajoute un triangle au panel.
     */
  //Ahmnad Khanafer
    public void ajouterTriangle() {
        if(nbrTri < 2) {
        	double espace=0;
        	Triangle tri = new Triangle(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(tri);
            espace = espace + 20;
            nbrTri += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Triangle Atteint");
        }
    }
    /**
     * Ajoute un triangle au panel.
     */
  //Ahmnad Khanafer
    public void ajouterCercle() {
        if(nbrCercle < 3) {
        	double espace=0;
        	Cercle cer = new Cercle(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercle += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
        }
    }
    
    public void ajouterCercleElectrique() {
        if(nbrCercleElectrique < 3) {
        	double espace=0;
        	CercleElectrique cer = new CercleElectrique(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercleElectrique += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
        }
    }
    
    public void ajouterEpines() {
        if(nbrEpines < 3) {
        	double espace=0;
        	Epines epi = new Epines(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(epi);
            nbrEpines += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
        }
    }
    //ZAKARIA
    public void ajouterMonstre() {
    	
		if (nbrMonstre < 1) {
    		Monstres monstre = new Monstres(200,200, pixelParMetres);
    		obHolder.addObstacle(monstre);
    		nbrMonstre++;
    		repaint();
    	}else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
    	}
    }
    
    private void ajouterCanon() {
    	
		if( nbrCanon < 1) {
    		
    	}
    	
    }

    
    //Ajoute des écouteurs de souris pour la gestion des actions de l'utilisateur.
    //Ahmnad Khanafer
    private void ecouteurSouris() {
        addMouseListener((MouseListener) new MouseAdapter() {
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

	
	private void gestionSourisFormeDragged(MouseEvent e) {
			
		for(Obstacles ob : obHolder.getObstacleHolder()) {
			int index = ob.getClickedResizeHandleIndex(e.getX(), e.getY());
				if (ob.isClickedOnIt() == true && index != -1) {
					ob.redimension(index, e.getX(), e.getY());
					repaint();
				}else if(ob.isClickedOnIt() == true && index == -1 ) {
					ob.rotate( e.getX(), e.getY());
					repaint();	
				}
				if(ob.contient(e.getX(), e.getY()) && ob.isClickedOnIt() == false) {
					ob.move( e.getX(), e.getY());
					
					
					repaint();
					break;
				}		
		}
	}
	
	private void gestionSourisFormeClick(MouseEvent e) {
		for(Obstacles ob : obHolder.getObstacleHolder()) {
			if(ob.contient(e.getX(), e.getY())) {
				ob.setClickedOnIt(true);
				repaint();
			}else {
				ob.setClickedOnIt(false);
				repaint();
			}
		}
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

	public ObstacleHolder getObHolder() {
		return obHolder;
	}

	public void setObHolder(ObstacleHolder obHolder) {
		this.obHolder = obHolder;
		repaint();
	}

	
	}