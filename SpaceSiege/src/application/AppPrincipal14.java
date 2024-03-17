package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Classe principale de l'application qui crée et affiche la fenêtre principale.
 * Cette classe étend {@code JFrame} pour créer une interface utilisateur graphique, comprenant un menu, des boutons et des labels.
 * Elle initialise l'interface utilisateur et définit les actions pour les boutons et les éléments de menu.
 * 
 */
public class AppPrincipal14 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	 /**
     * Lance l'interface utilisateur principale de l'application.
     * Cette méthode statique crée une instance de {@code AppPrincipal14} et rend la fenêtre visible.
     */
	public static void afficherFenetre() {
	       
		AppPrincipal14 fenetre = new AppPrincipal14();
        fenetre.setVisible(true);
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
	public AppPrincipal14() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuAuteur = new JMenu("Auteurs");
		menuBar.add(menuAuteur);
		
		JMenu menuApropos = new JMenu("À propos");
		menuBar.add(menuApropos);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		menuApropos.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(408, 236, 313, 317);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitre = new JLabel("SPACE SIEGE");
		lblTitre.setBounds(10, 21, 293, 67);
		panel.add(lblTitre);
		lblTitre.setBackground(new Color(0, 0, 0));
		lblTitre.setForeground(new Color(255, 255, 255));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 37));
		
		JButton btnJouer = new JButton("JOUER");
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FenetreModeDeJeu.afficherFenetre();
				dispose();
			}
		});
		btnJouer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		btnJouer.setBounds(46, 105, 216, 69);
		panel.add(btnJouer);

		JButton btnQuitter = new JButton("QUITTER");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnQuitter.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		btnQuitter.setBounds(46, 196, 216, 69);
		panel.add(btnQuitter);
		
		
		

	}
}
