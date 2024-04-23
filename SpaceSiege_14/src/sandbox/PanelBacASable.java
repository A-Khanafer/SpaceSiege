package sandbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfaces.Obstacles;
import obstacles.Cercle;
import obstacles.Rectangle;
import obstacles.Triangle;

/**
 * PanelBacASable est un JPanel personnalisé qui représente un bac à sable pour dessiner des rectangles et des triangles.
 * Il permet d'ajouter, de déplacer, de redimensionner et de faire pivoter des formes géométriques.
 * @author Ahmad Khanafer
 */
public class PanelBacASable extends JPanel {

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
	/**
	 * Indique si le mode éditeur est activé ou désactivé.
	 */
	private boolean editeurModeOn = true;

	private ArrayList<Obstacles> obstacles;

	

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
        
        if(premiereFois) {
            double espace=0;
            for(int i = 0 ; i < tableauRec.length ; i++) {
                tableauRec[i] = new Rectangle(100 + espace, 100 + espace, pixelParMetres);
                espace = espace + 20;
            }
            espace = 0;
            for(int i = 0 ; i < tableauTri.length ; i++) {
                tableauTri[i] = new Triangle(50 + espace, 50 + espace, 10, 15, pixelParMetres);
                espace = espace + 20;
            }
            espace = 0;
            for(int i = 0 ; i < tableauCercle.length ; i++) {
                tableauCercle[i] = new Cercle(  100 + espace,  100 + espace, pixelParMetres);
                espace = espace + 20;
            }
            premiereFois = false;
        }
        
        dessinerRec(g2d);
        dessinerTri(g2d);
        dessinerCercle(g2d);
    }

    /**
     * Ajoute un rectangle au panel.
     */
  //Ahmnad Khanafer
    public void ajouterRectangle() {
        if(nbrRec < 3) {
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
            nbrCercle += 1;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
        }
    }

    /**
     * Dessine les rectangles sur le panel.
     * @param g2d Objet Graphics2D pour dessiner les rectangles.
     */
  //Ahmnad Khanafer
    private void dessinerRec(Graphics2D g2d) {
        switch(nbrRec) {
            case 0 :
                break;
            case 1 :
                tableauRec[0].dessiner(g2d);
                System.out.println(tableauRec[0].toString());
                break;
            case 2 :
                tableauRec[0].dessiner(g2d);
                tableauRec[1].dessiner(g2d);
                break;
            case 3 :
                tableauRec[0].dessiner(g2d);
                tableauRec[1].dessiner(g2d);
                tableauRec[2].dessiner(g2d);
                break;
        }
    }

    /**
     * Dessine les triangles sur le panel.
     * @param g2d Objet Graphics2D pour dessiner les triangles.
     */
  //Ahmnad Khanafer
    private void dessinerTri(Graphics2D g2d) {
        switch(nbrTri) {
            case 0 :
                break;
            case 1 :
                tableauTri[0].dessiner(g2d);
                break;
            case 2 :
                tableauTri[0].dessiner(g2d);
                tableauTri[1].dessiner(g2d);
                break;
        }
    }
    /**
     * Dessine les rectangles sur le panel.
     * @param g2d Objet Graphics2D pour dessiner les rectangles.
     */
  //Ahmnad Khanafer
    private void dessinerCercle(Graphics2D g2d) {
        switch(nbrCercle) {
            case 0 :
                break;
            case 1 :
                tableauCercle[0].dessiner(g2d);
                break;
            case 2 :
                tableauCercle[0].dessiner(g2d);
                tableauCercle[1].dessiner(g2d);
                break;
            case 3 :
            	tableauCercle[0].dessiner(g2d);
            	tableauCercle[1].dessiner(g2d);
            	tableauCercle[2].dessiner(g2d);
                break;
        }
    }

    
    //Ajoute des écouteurs de souris pour la gestion des actions de l'utilisateur.
    //Ahmnad Khanafer
    private void ecouteurSouris() {
        addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gestionSourisRecClick(e);
                gestionSourisTriClick(e);
                gestionSourisCercleClick(e);
                repaint();
            }
        });
    
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gestionSourisRecDragged(e);
                gestionSourisTriDragged(e);
                gestionSourisCercleDragged(e);
                repaint();
            }
        });
    }
	
	//Méthode qui gère les click de la souris pour les rectangles
	//Ahmad Khanafer
	private void gestionSourisRecDragged(MouseEvent e) {
		for(int i =0 ; i < tableauRec.length; i++) {
			int index = tableauRec[i].getClickedResizeHandleIndex(e.getX(), e.getY());
				if (tableauRec[i].isClickedOnIt() == true && index != -1) {
					tableauRec[i].redimension(index, e.getX(), e.getY());
					repaint();
				}else if(tableauRec[i].isClickedOnIt() == true && index == -1 ) {
					tableauRec[i].rotate( e.getX(), e.getY());
					repaint();	
				}
				if(tableauRec[i].contient(e.getX(), e.getY()) && tableauRec[i].isClickedOnIt() == false) {
					tableauRec[i].move( e.getX(), e.getY());
					repaint();
					break;
				}
		}	
	}
	//Méthode qui gère le mouvement de la souris pour les triangles
	//Ahmad Khanafer
	private void gestionSourisTriDragged(MouseEvent e) {
		for(int i =0 ; i < tableauTri.length; i++) {
			int index = tableauTri[i].getClickedResizeHandleIndex(e.getX(), e.getY());
				if (tableauTri[i].isClickedOnIt() == true && index != -1) {
					tableauTri[i].redimension(index, e.getX(), e.getY());
					repaint();
				}else if( tableauTri[i].isClickedOnIt() == true && index == -1 ) {
					tableauTri[i].rotate( e.getX(), e.getY());
					repaint();
				}
				if(tableauTri[i].contient(e.getX(), e.getY()) && tableauTri[i].isClickedOnIt() == false) {
					tableauTri[i].move( e.getX(), e.getY());
					repaint();
					break;
				}
		}
	}
	//Méthode qui gère les click de la souris pour les rectangles
		//Ahmad Khanafer
		private void gestionSourisCercleDragged(MouseEvent e) {
			for(int i =0 ; i < tableauCercle.length; i++) {
				int index = tableauCercle[i].getClickedResizeHandleIndex(e.getX(), e.getY());
					if (tableauCercle[i].isClickedOnIt() == true && index != -1) {
						tableauCercle[i].redimension(index, e.getX(), e.getY());
						repaint();
					}else if(tableauCercle[i].isClickedOnIt() == true && index == -1 ) {
						tableauCercle[i].rotate( e.getX(), e.getY());
						repaint();
					}
					if(tableauCercle[i].contient(e.getX(), e.getY()) && tableauCercle[i].isClickedOnIt() == false) {
						tableauCercle[i].move( e.getX(), e.getY());
						repaint();
						break;
					}
			}	
		}
	//Méthode qui gère le mouvement de la souris pour les rectangles
	//Ahmad Khanafer
	private void gestionSourisRecClick(MouseEvent e) {
		for(int i =0 ; i < tableauRec.length; i++) {
			if(tableauRec[i].contient(e.getX(), e.getY())) {
				tableauRec[i].setClickedOnIt(true);
				repaint();
			}else {
				tableauRec[i].setClickedOnIt(false);
				repaint();
			}
		}
	}
	//Méthode qui gère les click de la souris pour les triangles
	//Ahmad Khanafer
	private void gestionSourisTriClick(MouseEvent e) {
		for(int i =0 ; i < tableauTri.length; i++) {
			if(tableauTri[i].contient(e.getX(), e.getY())) {
				tableauTri[i].setClickedOnIt(true);
				repaint();
			}else {
				tableauTri[i].setClickedOnIt(false);
				repaint();
			}
		}
	}
	//Méthode qui gère le mouvement de la souris pour les rectangles
		//Ahmad Khanafer
		private void gestionSourisCercleClick(MouseEvent e) {
			for(int i =0 ; i < tableauCercle.length; i++) {
				if(tableauCercle[i].contient(e.getX(), e.getY())) {
					tableauCercle[i].setClickedOnIt(true);
					repaint();
				}else {
					tableauCercle[i].setClickedOnIt(false);
					repaint();
				}
			}
		}
		
	public void sauvegardeNiveau() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("mon_fichier.txt"))) {
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	}