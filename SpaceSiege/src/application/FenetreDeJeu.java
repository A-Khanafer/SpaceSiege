package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import composantdessin.ZoneAnimationPhysique;
import composantdessin.PlanCartesien;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;

public class FenetreDeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panelTable;
	private JButton btnBacAsable;
	private JButton btnNiveauPrecedent;
	private JButton btnNiveauSuivant;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;

	public static void afficherFenetre() {
        // Création d'une instance de MaFenetre
		FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }
	
	public FenetreDeJeu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ZoneAnimationPhysique zoneAnimationPhysique = new ZoneAnimationPhysique();
		zoneAnimationPhysique.setBounds(0, 0, 1087, 663);
		contentPane.add(zoneAnimationPhysique);
		
		JPanel panelFonctionnalites = new JPanel();
		panelFonctionnalites.setBackground(new Color(255, 255, 255));
		panelFonctionnalites.setBounds(0, 674, 1087, 287);
		contentPane.add(panelFonctionnalites);
		panelFonctionnalites.setLayout(null);
		
		btnBacAsable = new JButton("BAC A SABLE");
		btnBacAsable.setBounds(918, 208, 159, 68);
		panelFonctionnalites.add(btnBacAsable);
		
		btnNiveauPrecedent = new JButton("NIVEAU PRECEDENT");
		btnNiveauPrecedent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 11));
		btnNiveauPrecedent.setBounds(918, 115, 159, 62);
		panelFonctionnalites.add(btnNiveauPrecedent);
		
		btnNiveauSuivant = new JButton("NIVEAU SUIVANT");
		btnNiveauSuivant.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 11));
		btnNiveauSuivant.setBounds(918, 11, 159, 68);
		panelFonctionnalites.add(btnNiveauSuivant);
		
		btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(749, 11, 159, 68);
		panelFonctionnalites.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(749, 115, 159, 62);
		panelFonctionnalites.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("New button");
		btnNewButton_5.setBounds(580, 11, 159, 68);
		panelFonctionnalites.add(btnNewButton_5);
		
		btnNewButton_6 = new JButton("New button");
		btnNewButton_6.setBounds(580, 115, 159, 62);
		panelFonctionnalites.add(btnNewButton_6);
		
		JSlider slider = new JSlider();
		slider.setBounds(449, 232, 440, 26);
		panelFonctionnalites.add(slider);
		
		panelTable = new JPanel();
		panelTable.setBackground(new Color(255, 255, 255));
		panelTable.setBounds(1097, 674, 387, 287);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
	}
}
