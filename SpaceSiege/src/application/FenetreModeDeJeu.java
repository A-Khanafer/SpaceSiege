package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
/**
 * La classe  est conçue pour permettre aux utilisateurs de choisir entre différents modes de jeu.
 * Elle étend  pour afficher une interface graphique comprenant plusieurs boutons, chacun correspondant à un mode de jeu différent.
 * Les utilisateurs peuvent choisir de jouer en mode classique, en mode bac à sable, ou de retourner au menu principal.
 */
public class FenetreModeDeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	 /**
     * Méthode statique pour afficher la fenêtre de sélection de mode de jeu.
     * Cette méthode crée une instance de {@code FenetreModeDeJeu} et la rend visible.
     */
	public static void afficherFenetre() {
	       
		FenetreModeDeJeu fenetre = new FenetreModeDeJeu();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panelClassic = new JPanel();
		panelClassic.setBackground(new Color(0, 0, 0));
		panelClassic.setBounds(73, 306, 293, 116);
		contentPane.add(panelClassic);
		panelClassic.setLayout(null);
		
		JButton btnClassic = new JButton("CLASSIQUE");
		btnClassic.setBounds(33, 23, 216, 69);
		panelClassic.add(btnClassic);
		btnClassic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				FenetreDeJeu.afficherFenetre();
				dispose();
			}
		});
		btnClassic.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
				JPanel panelSandBox = new JPanel();
				panelSandBox.setBackground(new Color(0, 0, 0));
				panelSandBox.setBounds(432, 306, 293, 116);
				contentPane.add(panelSandBox);
				panelSandBox.setLayout(null);
				
						JButton btnSandBox = new JButton("BAC A SABLE");
						btnSandBox.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								
								FenetreBacASable.afficherFenetre();
								dispose();
							
							}
						});
						btnSandBox.setBounds(41, 23, 216, 69);
						panelSandBox.add(btnSandBox);
						btnSandBox.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
						
						JPanel panel = new JPanel();
						panel.setBackground(new Color(0, 0, 0));
						panel.setBounds(796, 306, 293, 116);
						contentPane.add(panel);
						panel.setLayout(null);
						
						
						JButton btnQuitter = new JButton("MENU");
						btnQuitter.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								AppPrincipal14.afficherFenetre();
								dispose();
								
								
							}
						});
						btnQuitter.setBounds(44, 21, 216, 69);
						panel.add(btnQuitter);
						btnQuitter.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
						
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(new Color(0, 0, 0));
						panel_1.setBounds(403, 184, 345, 79);
						contentPane.add(panel_1);
						panel_1.setLayout(null);
						
						JLabel lblTitre = new JLabel("MODE DE JEUX");
						lblTitre.setBounds(10, 11, 325, 67);
						panel_1.add(lblTitre);
						lblTitre.setBackground(new Color(0, 0, 0));
						lblTitre.setForeground(new Color(255, 255, 255));
						lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
						lblTitre.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 37));
	}

}
