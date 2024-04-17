package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import niveaux.Niveau1;
import niveaux.Niveau2;
import niveaux.Niveau3;
import niveaux.Niveaux;
import composantdessin.PlanCartesien;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import physique.Vecteur2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 * Classe principale de l'interface de jeu, gérant la disposition des éléments de jeu et les interactions utilisateur.
 * Cette classe crée une fenêtre contenant une zone d'animation pour visualiser le jeu, un ensemble de contrôles pour interagir avec le jeu,
 * et un panel de paramétrage pour ajuster les configurations du jeu telles que les types de balles, la gravité, etc.
 * @author ZAKARIA SOUDAKI
 */
public class FenetreDeJeu extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panelTable;
	private JButton btnRetour;
	private JButton btnNiveauPrecedent;
	private JButton btnNiveauSuivant;
	private JButton btnPause;
	private JButton btnReinitialiser;
	private JButton btnDemarrer;
	private JButton btn1Image;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Niveaux niveaux [] = new Niveaux[10]; 
	private int index = 0;
	private boolean enCoursDAnimation=false;

	private JComboBox comboBoxTypeGrav;

	private Niveau1 niv1;
	private Niveau2 niv2;
	private Niveau3 niv3;
	private Niveaux nivActuel;
	private Niveaux nivSuivant ;
	private JRadioButton rdbBalleNormal;
	private JRadioButton rdbBalleElastique;
	private JRadioButton rdbBalleNova;
	private JPanel panelGraphique;
	private PlanCartesien planCartesien;
	/**
     * Méthode statique pour afficher la fenêtre de jeu. Crée une instance de {@code FenetreDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre() {
       
		FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
        
    }
	public void changerNiveau() {
	   	 index++;
		 niveaux[0] = niv1;
		 niveaux[1] = niv2;
		 niveaux[2] = niv3;
		 nivActuel = niveaux[index];
		 nivSuivant = niveaux[index +1];
	}
	/**
     * Constructeur qui initialise la fenêtre de jeu, y compris la zone d'animation et les panneaux de contrôle.
     *
     */
	//ZAKARIA SOUDAKI
	public FenetreDeJeu() {
		 niv1 = new Niveau1();
	   	 niv2 = new Niveau2();
		 niv3 = new Niveau3();
		System.out.println("salut___________________________________________________________________________");
		 niveaux[0] = niv1;
		 niveaux[1] = niv2;
		 niveaux[2] = niv3;
		 nivActuel = niveaux[index];
		 nivActuel.addPropertyChangeListener(new PropertyChangeListener() {
		 	public void propertyChange(PropertyChangeEvent evt) {
		 		if (evt.getPropertyName().equals("position") ) {
					planCartesien.setPosition((Vecteur2D) evt.getNewValue());
			 }
		 	}
		 });
		 nivSuivant = niveaux[index +1];
		 
		 
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 1010);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		//
		nivActuel.setBounds(0, 0, 1296, 672);
		contentPane.add(nivActuel);
		//
		JPanel panelFonctionnalites = new JPanel();
		panelFonctionnalites.setBackground(new Color(255, 255, 255));
		panelFonctionnalites.setBounds(0, 683, 1187, 286);
		contentPane.add(panelFonctionnalites);
		panelFonctionnalites.setLayout(null);
		
		btnRetour = new JButton("RETOUR");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FenetreModeDeJeu.afficherFenetre();
				setVisible(false);
				nivActuel.stopperAnim();
			}
		});
		btnRetour.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnRetour.setBounds(870, 204, 209, 68);
		panelFonctionnalites.add(btnRetour);
		
		btnNiveauPrecedent = new JButton("NIVEAU PRECEDENT");
		btnNiveauPrecedent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauPrecedent.setBounds(870, 131, 209, 62);
		panelFonctionnalites.add(btnNiveauPrecedent);
		
		btnNiveauSuivant = new JButton("NIVEAU SUIVANT");
		btnNiveauSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				
if (index >1 ) {
	JOptionPane.showMessageDialog(null, "FIN DES NIVEAUX __________");
}
else {
	

				nivActuel.arreter();
				getContentPane().remove(nivActuel);
				
				nivSuivant.setBounds(0, 0, 1296, 672);
				
				contentPane.add(nivSuivant);

			
				changerNiveau();
				repaint();
}
			}
		});
		btnNiveauSuivant.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauSuivant.setBounds(870, 52, 209, 68);
		panelFonctionnalites.add(btnNiveauSuivant);
		
		btnPause = new JButton("PAUSE");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.stopperAnim();
			}
		});
		btnPause.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnPause.setBounds(623, 52, 209, 68);
		panelFonctionnalites.add(btnPause);
		
		btnReinitialiser = new JButton("REINITIALISER");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
              nivActuel.reinitialiserApplication();
              desactiverLesRadios();
              
              
			}
		});
		btnReinitialiser.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnReinitialiser.setBounds(623, 131, 209, 62);
		panelFonctionnalites.add(btnReinitialiser);
		
		btnDemarrer = new JButton("DEMARRER");
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			

				nivActuel.demarrer();
				desactiverLesRadios();
				
//				nivActuel.TirerBalle();
				System.out.println(""+nivActuel.getClass()+"________________________________________________________________");
				
			}
		});
		btnDemarrer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnDemarrer.setBounds(377, 52, 209, 68);
		panelFonctionnalites.add(btnDemarrer);
		
		btn1Image = new JButton("+1 IMAGE");
		btn1Image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.prochaineImage();
			}
		});
		btn1Image.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btn1Image.setBounds(377, 131, 209, 62);
		panelFonctionnalites.add(btn1Image);
		
		JSlider slider = new JSlider();
		slider.setBounds(377, 242, 440, 26);
		panelFonctionnalites.add(slider);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(167, 52, 109, 46);
		panelFonctionnalites.add(spinner);
		
		JSpinner spinnerVieMonstre = new JSpinner();
		spinnerVieMonstre.setModel(new SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
		spinnerVieMonstre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nivActuel.setNombreDeVie((int)spinnerVieMonstre.getValue());
			}
		});
		spinnerVieMonstre.setBounds(167, 131, 109, 44);
		panelFonctionnalites.add(spinnerVieMonstre);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(990, 0, 97, 23);
		panelFonctionnalites.add(chckbxNewCheckBox);
		
		rdbBalleNormal = new JRadioButton("Balles Normales");
		rdbBalleNormal.setSelected(true);
		rdbBalleNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.choisirBalle(1);
			}
		});
		buttonGroup.add(rdbBalleNormal);
		rdbBalleNormal.setBounds(16, 53, 109, 23);
		panelFonctionnalites.add(rdbBalleNormal);
		
		rdbBalleElastique = new JRadioButton("Balles Elastiques");
		rdbBalleElastique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.choisirBalle(2);
			}
		});
		buttonGroup.add(rdbBalleElastique);
		rdbBalleElastique.setBounds(16, 97, 109, 23);
		panelFonctionnalites.add(rdbBalleElastique);
		
		rdbBalleNova = new JRadioButton("Balles Novas");
		rdbBalleNova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.choisirBalle(3);
			}
		});
		buttonGroup.add(rdbBalleNova);
		rdbBalleNova.setBounds(16, 139, 109, 23);
		panelFonctionnalites.add(rdbBalleNova);
		
		comboBoxTypeGrav = new JComboBox();
		comboBoxTypeGrav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typeGravite = (String) comboBoxTypeGrav.getSelectedItem();
		     nivActuel.changerTypeGravite(typeGravite);
			}
		});
		comboBoxTypeGrav.setModel(new DefaultComboBoxModel(new String[] {"ESPACE", "TERRE", "MARS"}));
		comboBoxTypeGrav.setBounds(167, 235, 109, 37);
		panelFonctionnalites.add(comboBoxTypeGrav);
		
		JButton btnNewButton = new JButton("MODES EXTRA");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(16, 189, 109, 37);
		panelFonctionnalites.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("QUITTER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
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
		panelTable.setBounds(1197, 683, 377, 286);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		panelGraphique = new JPanel();
		panelGraphique.setBounds(1321, 11, 253, 661);
		contentPane.add(panelGraphique);
		panelGraphique.setLayout(null);
		
		planCartesien = new PlanCartesien(nivActuel.getBalle().getPosition());
		planCartesien.setBounds(0, 0, 377, 661);
		panelGraphique.add(planCartesien);
		
		
		
		comboBoxTypeGrav.setSelectedItem("ESPACE"); 
	    niv1.changerTypeGravite("ESPACE"); 
		
	}
	private void desactiverLesRadios() {
		if(nivActuel.getEnCoursAnimation()==true) {
		rdbBalleNormal.setEnabled(false);
		rdbBalleElastique.setEnabled(false);
		rdbBalleNova.setEnabled(false);
		comboBoxTypeGrav.setEnabled(false);
	}
	if(nivActuel.getEnCoursAnimation()==false) {
		rdbBalleNormal.setEnabled(true);
		rdbBalleElastique.setEnabled(true);
		rdbBalleNova.setEnabled(true);
		comboBoxTypeGrav.setEnabled(true);
	}
	}
}
