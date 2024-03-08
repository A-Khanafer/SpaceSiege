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
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreDeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panelTable;
	private JButton btnBacAsable;
	private JButton btnNiveauPrecedent;
	private JButton btnNiveauSuivant;
	private JButton btnPause;
	private JButton btnReinitialiser;
	private JButton btnDemarrer;
	private JButton btn1Image;

	public static void afficherFenetre() {
       
		FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
    }
	
	public FenetreDeJeu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
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
		
		btnBacAsable = new JButton("RETOUR");
		btnBacAsable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FenetreModeDeJeu.afficherFenetre();
				dispose();
			}
		});
		btnBacAsable.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnBacAsable.setBounds(870, 204, 209, 68);
		panelFonctionnalites.add(btnBacAsable);
		
		btnNiveauPrecedent = new JButton("NIVEAU PRECEDENT");
		btnNiveauPrecedent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauPrecedent.setBounds(870, 131, 209, 62);
		panelFonctionnalites.add(btnNiveauPrecedent);
		
		btnNiveauSuivant = new JButton("NIVEAU SUIVANT");
		btnNiveauSuivant.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauSuivant.setBounds(870, 52, 209, 68);
		panelFonctionnalites.add(btnNiveauSuivant);
		
		btnPause = new JButton("PAUSE");
		btnPause.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnPause.setBounds(623, 52, 209, 68);
		panelFonctionnalites.add(btnPause);
		
		btnReinitialiser = new JButton("REINITIALISER");
		btnReinitialiser.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnReinitialiser.setBounds(623, 131, 209, 62);
		panelFonctionnalites.add(btnReinitialiser);
		
		btnDemarrer = new JButton("DEMARRER");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneAnimationPhysique.demarrer();
			}
		});
		btnDemarrer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnDemarrer.setBounds(377, 52, 209, 68);
		panelFonctionnalites.add(btnDemarrer);
		
		btn1Image = new JButton("+1 IMAGE");
		btn1Image.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btn1Image.setBounds(377, 131, 209, 62);
		panelFonctionnalites.add(btn1Image);
		
		JSlider slider = new JSlider();
		slider.setBounds(377, 242, 440, 26);
		panelFonctionnalites.add(slider);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(167, 52, 109, 46);
		panelFonctionnalites.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(167, 131, 109, 44);
		panelFonctionnalites.add(spinner_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(990, 0, 97, 23);
		panelFonctionnalites.add(chckbxNewCheckBox);
		
		JRadioButton rdbBalleNormal = new JRadioButton("Balles Normales");
		rdbBalleNormal.setBounds(16, 53, 109, 23);
		panelFonctionnalites.add(rdbBalleNormal);
		
		JRadioButton rdbBalleElastique = new JRadioButton("Balles Elastiques");
		rdbBalleElastique.setBounds(16, 97, 109, 23);
		panelFonctionnalites.add(rdbBalleElastique);
		
		JRadioButton rdbBalleNova = new JRadioButton("Balles Novas");
		rdbBalleNova.setBounds(16, 139, 109, 23);
		panelFonctionnalites.add(rdbBalleNova);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(167, 235, 109, 37);
		panelFonctionnalites.add(comboBox);
		
		JButton btnNewButton = new JButton("MODES EXTRA");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(16, 189, 109, 37);
		panelFonctionnalites.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("QUITTER");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1.setBounds(16, 235, 109, 37);
		panelFonctionnalites.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("CHOIX DE BALLES :");
		lblNewLabel.setBounds(16, 11, 109, 26);
		panelFonctionnalites.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("MASSE DES BALLES :");
		lblNewLabel_1.setBounds(167, 14, 137, 20);
		panelFonctionnalites.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("VIES DES MONSTRES :");
		lblNewLabel_2.setBounds(167, 106, 148, 26);
		panelFonctionnalites.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("TYPE DE GRAVITÉ :");
		lblNewLabel_3.setBounds(167, 214, 109, 23);
		panelFonctionnalites.add(lblNewLabel_3);
		
		panelTable = new JPanel();
		panelTable.setBackground(new Color(255, 255, 255));
		panelTable.setBounds(1097, 674, 387, 287);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		JPanel panelGraphique = new JPanel();
		panelGraphique.setBounds(1097, 0, 387, 663);
		contentPane.add(panelGraphique);
		
	}
}
