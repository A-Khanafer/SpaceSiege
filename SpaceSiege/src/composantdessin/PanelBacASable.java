package composantdessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import obstacles.Rectangle;
import obstacles.Triangle;

public class PanelBacASable extends JPanel {

	
	private double pixelParMetres;
	private Rectangle[] tableauRec;
	private Triangle[] tableauTri;
	private boolean premiereFois = true;
	private int nbrRec = 0;
	private int nbrTri = 0;
	private boolean editeurModeOn = true;
	
	
	public PanelBacASable() {
		setBackground(new Color(255, 255, 255));
		tableauRec = new Rectangle[3];
		tableauTri = new Triangle[3];
		ecouteurSouris();
	}
	
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		pixelParMetres = getWidth()/150;
		
		if(premiereFois) {
			int espace=0;
				for(int i = 0 ; i < tableauRec.length ; i++) {
					tableauRec[i] = new Rectangle(100 + espace, 100 + espace, pixelParMetres);
					espace = espace + 20;
				}
				espace = 0;
				for(int i = 0 ; i < tableauRec.length ; i++) {
					tableauTri[i] = new Triangle(50 + espace, 50 + espace, 10, 15, pixelParMetres);
					espace = espace + 20;
				}
			premiereFois = false;
		}
		
		dessinerRec(g2d);
		dessinerTri(g2d);
		
		
	}
	
	public void ajouterRectangle() {
		if(nbrRec < 3) {
			nbrRec += 1;
			repaint();
		}else {
			JOptionPane.showMessageDialog(null,"Nombre Maximale de Rectangle Atteint");
		}
	}
	
	public void ajouterTriangle() {
		if(nbrTri < 2) {
			nbrTri += 1;
			repaint();
		}else {
			JOptionPane.showMessageDialog(null,"Nombre Maximale de Triangle Atteint");
		}
	}
	
	private void dessinerRec(Graphics2D g2d) {
		switch(nbrRec) {
		case 0 :
			break;
		case 1 :
			tableauRec[0].dessiner(g2d);
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
	
	
	
	private void ecouteurSouris() {
		addMouseListener((MouseListener) new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {
				
				gestionSourisRecClick(e);
				gestionSourisTriClick(e);
				repaint();

			}
		});
	
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
				
			public void mouseDragged(MouseEvent e) {
				gestionSourisRecDragged(e);
				gestionSourisTriDragged(e);
				repaint();
			}
		});
	}
	
	//Méthode qui gère les click de la souris pour le rectangle
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
				}
		}	
	}
	
	private void gestionSourisTriDragged(MouseEvent e) {
		for(int i =0 ; i < tableauTri.length; i++) {
			int index = tableauTri[i].getClickedResizeHandleIndex(e.getX(), e.getY());
				if (tableauTri[i].isClickedOnIt() == true && index != -1) {
					tableauTri[i].redimension(index, e.getX(), e.getY());
					repaint();
				}else if(tableauTri[i].contient(e.getX(), e.getY()) && tableauTri[i].isClickedOnIt() == true && index == -1 ) {
					tableauTri[i].rotate( e.getX(), e.getY());
					repaint();
				}
				if(tableauTri[i].contient(e.getX(), e.getY()) && tableauTri[i].isClickedOnIt() == false) {
					tableauTri[i].move( e.getX(), e.getY());
					repaint();
				}
		}
	}
	
	private void gestionSourisRecClick(MouseEvent e) {
		for(int i =0 ; i < tableauRec.length; i++) {
			if(tableauRec[i].contient(e.getX(), e.getY())) {
				System.out.println("CLICKEEEZZZZZZZZZ on");
				tableauRec[i].setClickedOnIt(true);
				repaint();
			}else {
				System.out.println("CLICKEEEZZZZZZZZZ off");
				tableauRec[i].setClickedOnIt(false);
				repaint();
			}
		}
	}
	
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

	
	}