package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import composantdessin.Titre;
import outils.OutilsImage;
import systeme.Son;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * La classe  est conçue pour permettre aux utilisateurs de choisir entre différents modes de jeu.
 * Elle étend  pour afficher une interface graphique comprenant plusieurs boutons, chacun correspondant à un mode de jeu différent.
 * Les utilisateurs peuvent choisir de jouer en mode classique, en mode bac à sable, ou de retourner au menu principal.
 * @author ZAKARIA SOUDAKI
 */
public class FenetreModeDeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * panel fond
	 */
	private JPanel contentPane;
	/**
	 * fenetre précédente
	 */
	private static AppPrincipal14 appli;
	/**
	 * fenetre actuelle
	 */
	private static FenetreModeDeJeu fenetre;
	/**
	 * son de l'application
	 */
	Son bouttonClicker = new Son();
	
	/**
	 * longueur fenetre
	 */
	private int longueur = 1800;
	/**
	 * largeur fenetre
	 */
	private int hauteur = 950;
	 /**
     * Méthode statique pour afficher la fenêtre de sélection de mode de jeu.
     * Cette méthode crée une instance de {@code FenetreModeDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre(AppPrincipal14 app) {
		
	    appli =app;
	    fenetre = new FenetreModeDeJeu();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
//		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setVisible(true);
        app.setVisible(false);
        
    }
	/**
	 * méthode pour rentre visible la fenetre précédente
	 * @param app la fenetre précédente
	 */
	//ZAKARIA SOUDAKI
	public static void retour(FenetreModeDeJeu app) {
		
		app.setVisible(true);
		
	}
	/**
     * Méthode principale utilisée pour lancer l'interface de sélection de mode de jeu.
     * @param args Arguments passés au programme (non utilisés).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreModeDeJeu frame = new FenetreModeDeJeu();
					frame.setLocationRelativeTo(null);
					frame.setUndecorated(true); 
//					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Initialise et affiche la fenêtre de sélection de mode de jeu.
     */
	//ZAKARIA SOUDAKI
	public FenetreModeDeJeu() {
		bouttonClicker.setFile(2);
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, longueur, hauteur);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Titre titre_1 = new Titre("etoiles1.png", "modedejeu.png", 270,190, 390,320);
		titre_1.setBackground(new Color(0, 0, 0));
		titre_1.setBounds(longueur/2-412/2, 116, 412, 359);
		contentPane.add(titre_1);
						
						JButton btnClassic = new JButton("CLASSIQUE");
						btnClassic.setBorder(emptyBorder);
						btnClassic.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseEntered(MouseEvent e) {
								OutilsImage.lireImageEtPlacerSurBouton("classique2.png", btnClassic);
								btnClassic.setBounds(458, 565, 302, 69);

								
							}
							@Override
							public void mouseExited(MouseEvent e) {
								OutilsImage.lireImageEtPlacerSurBouton("classique.png", btnClassic);
								btnClassic.setBounds(458, 575, 302, 69);

								
							}
						});
						btnClassic.setBounds(458, 575, 302, 69);
						contentPane.add(btnClassic);
						btnClassic.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								bouttonClicker.play();
								bouttonClicker.reset();
								FenetreNiveaux.afficherFenetre(fenetre);

							}
						});
						OutilsImage.lireImageEtPlacerSurBouton("classique.png", btnClassic);
						
								JButton btnSandBox = new JButton("BAC A SABLE");
								btnSandBox.setBorder(emptyBorder);
								btnSandBox.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseEntered(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("bacasable2.png", btnSandBox);
										btnSandBox.setBounds(1071, 565, 325, 69);

									}
									@Override
									public void mouseExited(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("bacasable.png", btnSandBox);
										btnSandBox.setBounds(1071, 575, 325, 69);

									}
								});
								btnSandBox.setBounds(1071, 575, 325, 69);
								contentPane.add(btnSandBox);
								btnSandBox.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										
										bouttonClicker.play();
										bouttonClicker.reset();
										FenetreBacASable.afficherFenetre(fenetre);
										

									
									}
								});
								OutilsImage.lireImageEtPlacerSurBouton("bacasable.png", btnSandBox);
								
								
								JButton btnMenu = new JButton("MENU");
								btnMenu.setBorder(emptyBorder);
								btnMenu.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseEntered(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("menu2.png", btnMenu);
										btnMenu.setBounds(longueur/2-80, 712, 190, 69);
										
									}
									@Override
									public void mouseExited(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnMenu);
										btnMenu.setBounds(longueur/2-80, 722, 190, 69);
									}
								});
								btnMenu.setBounds(longueur/2-80, 722, 190, 69);
								contentPane.add(btnMenu);
								btnMenu.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										bouttonClicker.play();
										bouttonClicker.reset();
										AppPrincipal14.retour(appli);
										setVisible(false);

										
										
									}
								});
								OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnMenu);
								
								JButton btnX = new JButton("New button");
								btnX.setBorder(emptyBorder);
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
								btnX.setBounds(longueur - 95, 11, 75, 65);
								contentPane.add(btnX);
								OutilsImage.lireImageEtPlacerSurBouton("xBlanc.png", btnX);
								
								JLabel lbl  = new JLabel("");
								ImageIcon gifIcon = new ImageIcon(this.getClass().getResource("/fondmodedejeu3.gif"));
								Image img = gifIcon.getImage();
								Image resizedImg = img.getScaledInstance(longueur,hauteur, Image.SCALE_DEFAULT);
								lbl.setIcon(new ImageIcon(resizedImg));
								lbl.setBounds(0, 0, longueur, hauteur);
								contentPane.add(lbl);
//								
	}
}

