package bacasable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

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
 * PanelBacASable est un JPanel personnalisé qui représente un bac à sable pour dessiner des rectangles et des triangles.
 * Il permet d'ajouter, de déplacer, de redimensionner et de faire pivoter des formes géométriques.
 * @author Ahmad Khanafer
 */
public class PanelBacASable extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Le ratio utilisé pour convertir les mètres en pixels.
	 */
	private double pixelParMetres = 10;
	

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
	
	private int nbrPlaqueRebondissante = 0;
	
	private boolean editeurModeOn = true;

	private ObstacleHolder obHolder = new ObstacleHolder();
	
	private boolean canonClick = false;

	private Canon canon;
	
	private Monstres monstre;
	
	private boolean monstredessin = false;
	
	boolean monstreCreer = false;
	
	private Point2D.Double[] coinsCercle;
	
	private Point2D.Double[] coinsCercleE;
	
	private Point2D.Double[] coinsEpines;
	
	private Point2D.Double[] coinsRec;
	
	private Point2D.Double[] coinsTri;
	
	private Point2D.Double valPlusHaute = new Point2D.Double(0,0);
	
	private double val = 0;

	

    /**
     * Constructeur par défaut de PanelBacASable.
     * Initialise les paramètres par défaut et ajoute des écouteurs de souris.
     */
	//Ahmnad Khanafer
    public PanelBacASable() {
        setBackground(new Color(255, 255, 255));
        canon = new Canon(0, 10,pixelParMetres);
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
        canon.dessiner(g2d);
        
        if (monstredessin) {
        	monstre.dessiner(g2d);
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
            
            coinsRec = rec.getCoins();
            
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
            
            coinsTri = tri.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Triangle Atteint");
        }
    }
    /**
     * Ajoute un Cercle au panel.
     */
  //Ahmnad Khanafer
    public void ajouterCercle() {
        if(nbrCercle < 3) {
        	double espace=0;
        	Cercle cer = new Cercle(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(cer);
            nbrCercle += 1;
            
            coinsCercle = cer.getCoins();
            
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
            
            coinsCercleE = cer.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Electrique Atteint");
        }
    }
    
    public void ajouterEpines() {
        if(nbrEpines < 3) {
        	double espace=0;
        	Epines epi = new Epines(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(epi);
            nbrEpines += 1;
            
            coinsEpines = epi.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale d'Epines Atteint");
        }
    }
    
    public void ajouterPlaqueRebondissante() {
        if(nbrPlaqueRebondissante < 3) {
        	double espace=0;
        	PlaqueRebondissante epi = new PlaqueRebondissante(50 + espace, 50 + espace, pixelParMetres);
            obHolder.addObstacle(epi);
            nbrPlaqueRebondissante += 1;
            
            coinsEpines = epi.getCoins();
            
            repaint();
        } else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Plaque Rebondissante Atteint");
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
				if (ob.isClickedOnIt() == true && index != -1) {
					ob.redimension(index, e.getX(), e.getY());
					repaint();
					
				}else if(ob.isClickedOnIt() == true && index == -1 ) {
					ob.rotate( e.getX(), e.getY());
					repaint();	
				}
				if(ob.contient(e.getX(), e.getY()) && ob.isClickedOnIt() == false) {

					if(ob.getPosition().getY() < getHeight()-20) {
						ob.move( e.getX(), e.getY());
						 
					}else if (ob.getPosition().getY()+ ob.getLongueur() >= getHeight()-20 ) {
							ob.move( e.getX() , (int) (getHeight() - ob.getLargeur()/2));
							
							
						} 
//						else if (ob.getPosition().getY() + ob.getLargeur() >= getHeight()-20){
//						    ob.move(e.getX(), (int)( getHeight()- ob.getLargeur()/2 ));
//					}  
//					
				
					
//						
//						if (ob.getPosition().getY() + ob.getLongueur() >= getHeight()-20 ) {
//						ob.move(e.getX(), (int)( getHeight() ));
//						
//					}else if (ob.getPosition().getY() + ob.getLargeur() >= getHeight()-20){
//						ob.move(e.getX(), (int)( getHeight() ));
//					}
						
					
					
					
					
					
					
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

	 //ZAKARIA
    public void ajouterMonstre() {
    	
		if (nbrMonstre < 1) {
    		monstre = new Monstres(200,200, pixelParMetres);
    		nbrMonstre++;
    		monstredessin = true;
    		monstreCreer = true;
    		repaint();
    	}else {
            JOptionPane.showMessageDialog(null,"Nombre Maximale de Cercle Atteint");
    	}
    }
    
    
    public void calculLimitesFormes(Point2D.Double[] tab) {
    	
    	for (int i = 0; i < tab.length; i++) {
    		
			if (tab[i].getX() >= getHeight()) {
				
			}
		}
    
    }
    
    
    public void posPlusHaute(Point2D.Double[] tab) {
    	
    	
    	for (int i = 0; i < tab.length; i++) {
    		
    		if(tab[i].getY() > val) {
    			val = tab[i].getY(); 
    			}
		}
    }
    public Point2D.Double calculHauteurEq ( Vecteur2D centre ,Point2D.Double point, double hauteur) {
    	
    	double dist1 = Collisions.distanceEntreDeuxPoints( point.getX(), point.getX() , point.getY() , hauteur);
    	double dist2 = Collisions.distanceEntreDeuxPoints( centre.getX() , centre.getX() , centre.getY(), hauteur);
    	
    	double posY = dist2 - dist1;
    	
    	return new Point2D.Double(centre.getX(), posY);
    	
    }
    
   }

 
	
    
	