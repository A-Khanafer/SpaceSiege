package application;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import composantdessin.Titre;
import outils.OutilsImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import composantdessin.BoutonsIntro;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Classe principale de l'application qui crée et affiche la fenêtre principale.
 * Cette classe étend {@code JFrame} pour créer une interface utilisateur graphique, comprenant un menu, des boutons et des labels.
 * Elle initialise l'interface utilisateur et définit les actions pour les boutons et les éléments de menu.
 * @author ZAKARIA SOUDAKI
 */

public class AppPrincipal14 extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private boolean enCoursDAnimation = false;
	private ImageIcon ImgFond = new ImageIcon("fondjeu.jpg"); 
	private int longueur = 1280;
	private int hauteur = 720;
	private BoutonsIntro boutonsIntro;
	
	
	 /**
     * Lance l'interface utilisateur principale de l'application.
     * Cette méthode statique crée une instance de {@code AppPrincipal14} et rend la fenêtre visible.
     */
	public static void afficherFenetre() {
		
		AppPrincipal14 fenetre = new AppPrincipal14();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
        fenetre.setVisible(true);
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
		
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, longueur, hauteur);
		
		
		
		
		
		
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		Titre titre_1 = new Titre("etoiles2.png", "titre2.png");
		titre_1.setBackground(new Color(0, 0, 0,0));
		titre_1.setBounds(465, 30, 342, 269);
		contentPane.add(titre_1);
		
		boutonsIntro = new BoutonsIntro(longueur, hauteur);
		boutonsIntro.setLocation(540, 355);
		contentPane.add(boutonsIntro);
		
		JButton btnSansSon = new JButton("New button");
		btnSansSon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("sansson2.png", btnSansSon);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("sansson1.png", btnSansSon);

			}
		});
		btnSansSon.setBounds(10, 626, 55, 54);
		contentPane.add(btnSansSon);
		OutilsImage.lireImageEtPlacerSurBouton("sansson1.png", btnSansSon);

		
		JButton btnApropos = new JButton("New button");
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
					
						+ "");
				
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
		btnApropos.setBounds(10, 11, 55, 54);
		contentPane.add(btnApropos);
		OutilsImage.lireImageEtPlacerSurBouton("apropo1.png", btnApropos);
		
		JButton btnInfo = new JButton("New button");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "PROJET SCIENCE INFORMATIQUE ET MATHÉMATIQUE :"+"\n"+
						"MEMBRES DE L'ÉQUIPE DE DÉVELOPPEMENT : AHMAD KHANAFER , WALID BENAKMOUM , ZAKARIA SOUDAKI"
									);
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
		btnInfo.setBounds(75, 11, 62, 54);
		contentPane.add(btnInfo);
		OutilsImage.lireImageEtPlacerSurBouton("auteurs1.png", btnInfo);

		
		JButton btnX = new JButton("New button");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("xRouge.png", btnX);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("xBlanc.png", btnX);

			}
		});
		btnX.setBounds(1209, 11, 55, 54);
		contentPane.add(btnX);
		OutilsImage.lireImageEtPlacerSurBouton("xBlanc.png", btnX);
		
		JLabel lbl = new JLabel("");
		Image img  = new ImageIcon(this.getClass().getResource("/fondanimer.gif")).getImage();
		lbl.setIcon(new ImageIcon(img));
		lbl.setBounds(0, -25, 1280, 770);
		contentPane.add(lbl);
		
		
		
		
		
		
		
//		JButton btnJouer = new JButton("JOUER");
//		btnJouer.setBounds(550, 448, 216, 69);
//		contentPane.add(btnJouer);
//		btnJouer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				FenetreModeDeJeu.afficherFenetre();
//				setVisible(false);
//			}
//		});
//		btnJouer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		
//				JButton btnQuitter = new JButton("QUITTER");
//				btnQuitter.setBounds(550, 545, 216, 69);
//				contentPane.add(btnQuitter);
//				btnQuitter.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						setVisible(false);
//					}
//				});
//				btnQuitter.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		
		 
		

	}
}
