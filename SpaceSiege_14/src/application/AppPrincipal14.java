package application;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.TableUI;

import composantdessin.Titre;
import outils.OutilsImage;
import systeme.Son;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import composantdessin.BoutonsIntro;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
/**
 * Classe principale de l'application qui crée et affiche la fenêtre principale.
 * Cette classe étend {@code JFrame} pour créer une interface utilisateur graphique, comprenant un menu, des boutons et des labels.
 * Elle initialise l'interface utilisateur et définit les actions pour les boutons et les éléments de menu.
 * @author ZAKARIA SOUDAKI
 */


public class AppPrincipal14 extends JFrame{
	
	
	
	Son musique = new Son();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int longueur = 1920;
	private int hauteur = 1200;
	private BoutonsIntro boutonsIntro;
	private boolean son = true;
	private AppPrincipal14 actuel = this;
	
	 /**
     * Lance l'interface utilisateur principale de l'application.
     * Cette méthode statique crée une instance de {@code AppPrincipal14} et rend la fenêtre visible.
     */
	public static void retour(AppPrincipal14 app) {
		
		app.setVisible(true);
		
    }
	public void fermer() {
		setVisible(false);
		dispose();
	}

	/**
     * Point d'entrée principal de l'application.
     * Cette méthode est appelée au démarrage de l'application et initialise l'interface utilisateur en invoquant {@code afficherFenetre()}.
     *
     * @param args Arguments de ligne de commande (non utilisés).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipal14 frame = new AppPrincipal14();
					frame.setLocationRelativeTo(null);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					
					frame.setUndecorated(true); 
					
 					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Construit l'interface utilisateur principale de l'application.
     * Ce constructeur initialise la fenêtre principale, configure la barre de menu, les éléments de menu, 
     * et prépare le contenu principal de l'interface, y compris le panneau avec les boutons et le label de titre.
     */
	//ZAKARIA SOUDAKI
	public AppPrincipal14() {
		
		musique.setFile(0);
		musique.play();
		musique.loop();
		
		Border emptyBorder = BorderFactory.createEmptyBorder();		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, longueur, hauteur);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		
		Titre titre_1 = new Titre("etoiles2.png", "titre2.png", 330,220, 450,360);
		titre_1.setBounds(1920/2-512/2, 66, 512, 500);
		contentPane.add(titre_1);
	
		boutonsIntro = new BoutonsIntro(longueur, hauteur, actuel);
		contentPane.add(boutonsIntro);
		
		JButton btnSon = new JButton("New button");
		btnSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (son == true) {
					musique.stop();
					son = false;
				}else {
					musique.play();
					musique.loop();
					son=true;
				}
				
			}
		});
		btnSon.setBorder(emptyBorder);
		btnSon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(son == true) {
					OutilsImage.lireImageEtPlacerSurBouton("sansson2.png", btnSon);
				}
				if(son == false) {
					OutilsImage.lireImageEtPlacerSurBouton("son2.png", btnSon);
				}

			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(son == false) {
					OutilsImage.lireImageEtPlacerSurBouton("sansson1.png", btnSon);
				}
                if(son == true) {
					OutilsImage.lireImageEtPlacerSurBouton("son1.png", btnSon);
				}
			}
		});
		btnSon.setBounds(10, 1005, 55, 55);
		contentPane.add(btnSon);
		OutilsImage.lireImageEtPlacerSurBouton("son1.png", btnSon);

		
		JButton btnApropos = new JButton("New button");
		btnApropos.setBorder(emptyBorder);
		btnApropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, ""
						+ "Plongez dans une aventure palpitante avec notre application de jeu interactif où "
						+ "\n"+ "vous incarnez un tireur d'élite. Utilisez votre canon pour lancer des balles à travers"
						+ "\n"+ " une série d'obstacles intelligemment disposés. Grâce à des collisions physiques réalistes,"
						+ "\n"+ " les balles rebondissent sur les surfaces, défiant la gravité dans un ballet de mouvements imprévisibles."
						+ "\n"+ " Votre mission ? Éliminer les redoutables monstres qui se cachent derrière les obstacles. Mais ce n'est pas tout !"
						+ "\n"+ " Notre mode bac à sable vous permet de libérer votre créativité en construisant vos propres niveaux, défiant ainsi"
						+ "\n"+ " vos amis à les conquérir. Êtes-vous prêt à relever le défi et à devenir le maître de la physique et de la stratégie"
						+ "\n"+ " dans ce monde de tir et d'aventure ?\n"
					
						+ "\"PROJET SCIENCE INFORMATIQUE ET MATHÉMATIQUE :\"+\"\\n\"+\n"
						+ "						\"MEMBRES DE L'ÉQUIPE DE DÉVELOPPEMENT : AHMAD KHANAFER , WALID BENAKMOUM , ZAKARIA SOUDAKI\"");
				
			}
		});
		btnApropos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("apropo2.png", btnApropos);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("apropo1.png", btnApropos);
			}
		});
		btnApropos.setBounds(10, 11, 55, 55);
		contentPane.add(btnApropos);
		OutilsImage.lireImageEtPlacerSurBouton("apropo1.png", btnApropos);
		
		JButton btnInfo = new JButton();
		btnInfo.setBorder(emptyBorder);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tutoriel.afficherFenetre(actuel);
			}
		});
		btnInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("auteurs2.png", btnInfo);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("auteurs1.png", btnInfo);

			}
		});
		btnInfo.setBounds(75, 11, 55, 55);
		contentPane.add(btnInfo);
		OutilsImage.lireImageEtPlacerSurBouton("auteurs1.png", btnInfo);

		
		
		
		JLabel lbl  = new JLabel("");
		ImageIcon gifIcon = new ImageIcon(this.getClass().getResource("/fondanime4.gif"));
		Image img = gifIcon.getImage();
		Image resizedImg = img.getScaledInstance(1920,1080, Image.SCALE_DEFAULT);
		lbl.setIcon(new ImageIcon(resizedImg));
		lbl.setBounds(0, 0, 1920, 1080);
		contentPane.add(lbl);
		
		
		
		
		

	}
}

