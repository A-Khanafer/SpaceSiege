package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import composantdessin.Titre;
import outils.OutilsImage;

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
	private JPanel contentPane;
	 /**
     * Méthode statique pour afficher la fenêtre de sélection de mode de jeu.
     * Cette méthode crée une instance de {@code FenetreModeDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre() {
	       
		FenetreModeDeJeu fenetre = new FenetreModeDeJeu();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
        fenetre.setVisible(true);
        
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
	public FenetreModeDeJeu() {
		
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Titre titre_1 = new Titre("etoiles1.png", "modedejeu.png");
		titre_1.setBackground(new Color(0, 0, 0));
		titre_1.setBounds(428, 66, 362, 309);
		contentPane.add(titre_1);
						
						JButton btnClassic = new JButton("CLASSIQUE");
						btnClassic.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseEntered(MouseEvent e) {
								OutilsImage.lireImageEtPlacerSurBouton("classique2.png", btnClassic);
								btnClassic.setBounds(237, 416, 302, 69);

								
							}
							@Override
							public void mouseExited(MouseEvent e) {
								OutilsImage.lireImageEtPlacerSurBouton("classique.png", btnClassic);
								btnClassic.setBounds(237, 426, 302, 69);

								
							}
						});
						btnClassic.setBounds(237, 426, 302, 69);
						contentPane.add(btnClassic);
						btnClassic.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								
								FenetreDeJeu.afficherFenetre();
								setVisible(false);

							}
						});
						OutilsImage.lireImageEtPlacerSurBouton("classique.png", btnClassic);
						
								JButton btnSandBox = new JButton("BAC A SABLE");
								btnSandBox.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseEntered(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("bacasable2.png", btnSandBox);
										btnSandBox.setBounds(663, 416, 325, 69);

									}
									@Override
									public void mouseExited(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("bacasable.png", btnSandBox);
										btnSandBox.setBounds(663, 426, 325, 69);

									}
								});
								btnSandBox.setBounds(663, 426, 325, 69);
								contentPane.add(btnSandBox);
								btnSandBox.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										
										
										FenetreBacASable.afficherFenetre();
										setVisible(false);

									
									}
								});
								OutilsImage.lireImageEtPlacerSurBouton("bacasable.png", btnSandBox);
								
								
								JButton btnMenu = new JButton("MENU");
								btnMenu.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseEntered(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("menu2.png", btnMenu);
										btnMenu.setBounds(505, 564, 190, 65);
										
									}
									@Override
									public void mouseExited(MouseEvent e) {
										OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnMenu);
										btnMenu.setBounds(505, 574, 190, 65);
									}
								});
								btnMenu.setBounds(505, 574, 190, 69);
								contentPane.add(btnMenu);
								btnMenu.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										
										AppPrincipal14.afficherFenetre();
										setVisible(false);

										
										
									}
								});
								OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnMenu);
								
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
								
								JLabel lbl = new JLabel("New label");
								Image img  = new ImageIcon(this.getClass().getResource("/fondjeu4.png")).getImage();
								lbl.setIcon(new ImageIcon(img));
								lbl.setBounds(-12, -34, 1300, 790);
								contentPane.add(lbl);
	}
}