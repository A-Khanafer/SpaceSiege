package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import niveaux.Niveau1;
import niveaux.Niveau2;
import niveaux.Niveau3;
import niveaux.NiveauCustomiser;
import niveaux.Niveaux;
import obstacles.ObstacleHolder;
import outils.OutilsImage;
import composantdessin.PlanCartesien;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import physique.Vecteur2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Classe principale de l'interface de jeu, gérant la disposition des éléments de jeu et les interactions utilisateur.
 * Cette classe crée une fenêtre contenant une zone d'animation pour visualiser le jeu, un ensemble de contrôles pour interagir avec le jeu,
 * et un panel de paramétrage pour ajuster les configurations du jeu telles que les types de balles, la gravité, etc.
 * @author ZAKARIA SOUDAKI
 */
public class FenetreDeJeu extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * panel de fond
	 */
	
	private JPanel contentPane;
	/**
	 * bouton de fond
	 */
	private JButton btnRetour;
	

	/**
	 * bouton de pause
	 */
	private JButton btnPause;
	/**
	 * bouton réinitialiser
	 */
	private JButton btnReinitialiser;
	/**
	 * bouton demarrer
	 */
	private JButton btnDemarrer;
	/**
	 * bouton 1 image
	 */
	private JButton btn1Image;
	
	/**
	 * boutons radios
	 */
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * tableau contenant tout les niveaux pré-construits
	 */
	private static Niveaux niveaux [] = new Niveaux[10]; 
	/**
	 * index pour choisi le niveau
	 */
	private static int index = 0;
	/**
	 * boolean demarrer arreter l'animation
	 */
	private boolean enCoursDAnimation=false;

	/**
	 * JComboBox choix de gravité
	 */
	private JComboBox comboBoxTypeGrav;

	/**
	 * niveau 1
	 */
	private Niveau1 niv1;
	/**
	 * niveau 2
	 */
	private Niveau2 niv2;
	/**
	 * niveau 3
	 */
	private Niveau3 niv3;
	/**
	 * niveau custom
	 */
	private NiveauCustomiser nivCustom;
	/**
	 * niveau actuel
	 */
	private static Niveaux nivActuel;
	/**
	 * bouton balle normale
	 */
	private JRadioButton rdbBalleNormal;
	/**
	 * bouton balle élastique
	 */
	private JRadioButton rdbBalleElastique;
	/**
	 * bouton Balle nova
	 */
	private JRadioButton rdbBalleNova;
	/**
	 *le plan cartésien
	 */
	private PlanCartesien planCartesien;
   
	/**
	 * label pour le fond
	 */
    private JLabel lbl = new JLabel();
    /**
     * image de fond
     */
    private Image  img;
    /**
     * image redimentionnée
     */
    private ImageIcon gifIcon;
    
    /**
     * fenetre choix de niveaux
     */

    private static FenetreNiveaux appli;
    /**
     * fenetre actuelle
     */
    private static FenetreDeJeu fenetre;
    /**
     * spinner changer masse pour la balle 
     */
    private JSpinner spinnerMasse;
    /**
     * spinner choix nombre vie du monstre
     */
    private JSpinner spinnerVieMonstre;
   
    
    private Object[][] data = {
            {"Niveau 1", "Donnée 1 Niveau 1", "Donnée 2 Niveau 1", "Donnée 3 Niveau 1"},
            {"Niveau 2", "Donnée 1 Niveau 2", "Donnée 2 Niveau 2", "Donnée 3 Niveau 2"},
            {"Niveau 3", "Donnée 1 Niveau 3", "Donnée 2 Niveau 3", "Donnée 3 Niveau 3"}
    };

	
	/**
     * Méthode statique pour afficher la fenêtre de jeu. Crée une instance de {@code FenetreDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre(FenetreNiveaux app, int indexx) {
		
        index = indexx;
		appli = app;
		fenetre = new FenetreDeJeu();
		
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true);
//		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setVisible(true);
        app.setVisible(false);
        
    }

	
	/**
     * Constructeur qui initialise la fenêtre de jeu, y compris la zone d'animation et les panneaux de contrôle.
     *
     */
	//ZAKARIA SOUDAKI
	public FenetreDeJeu() {
		

		 Border emptyBorder = BorderFactory.createEmptyBorder();
		
		 niv1 = new Niveau1();
	   	 niv2 = new Niveau2();
		 niv3 = new Niveau3();
		 nivCustom = new NiveauCustomiser();
		 niveaux[0] = niv1;
		 niveaux[1] = niv2;
		 niveaux[2] = niv3;
		 niveaux[3] = nivCustom;
		 nivActuel = niveaux[index];
		 
		 nivActuel.addPropertyChangeListener(new PropertyChangeListener() {
		 	public void propertyChange(PropertyChangeEvent evt) {
		 		if (evt.getPropertyName().equals("position") ) {
					planCartesien.setPosition((Vecteur2D) evt.getNewValue());
			 }
		 		 if (evt.getPropertyName().equals("enCoursDAnimation")) {	                   
	                   desactiverLesRadios();
	                }
		 	}
		 });

		 
		 
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//
	    nivActuel.setBounds(0, 0, 1920, 864);
		contentPane.add(nivActuel);
		//
		JPanel panelFonctionnalites = new JPanel();
		panelFonctionnalites.setBackground(new Color(255, 255, 255));
		panelFonctionnalites.setBounds(0, 866, 795, 224);
		contentPane.add(panelFonctionnalites);
		panelFonctionnalites.setLayout(null);
		
		btnRetour = new JButton("RETOUR");
		btnRetour.setBorder(emptyBorder);
		btnRetour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("retour2.png", btnRetour);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("retour1.png", btnRetour);

			}
		});
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FenetreNiveaux.retour(appli);
				
				setVisible(false);
				
				nivActuel.arreter();
                contentPane.remove(nivActuel);
			}
		});
		btnRetour.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnRetour.setBounds(617, 14, 68, 68);
		panelFonctionnalites.add(btnRetour);
		OutilsImage.lireImageEtPlacerSurBouton("retour1.png", btnRetour);

		
		
		btnPause = new JButton("PAUSE");
		btnPause.setBorder(emptyBorder);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("pause2.png", btnPause);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("pause1.png", btnPause);

			}
		});
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.stopperAnim();
			}
		});
		btnPause.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnPause.setBounds(380, 14, 68, 68);
		panelFonctionnalites.add(btnPause);
		OutilsImage.lireImageEtPlacerSurBouton("pause1.png", btnPause);

		
		btnReinitialiser = new JButton("REINITIALISER");
		btnReinitialiser.setBorder(emptyBorder);
		btnReinitialiser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("recommencer2.png",btnReinitialiser);

			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("recommencer1.png",btnReinitialiser);

			}
		});
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
              nivActuel.reinitialiserApplication();
              rdbBalleNormal.setSelected(true);
            
              rdbBalleElastique.setSelected(false);
              rdbBalleNova.setSelected(false);
              planCartesien.reset();
              desactiverLesRadios();
              
              
			}
		});
		btnReinitialiser.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnReinitialiser.setBounds(458, 14, 68, 68);
		panelFonctionnalites.add(btnReinitialiser);
		OutilsImage.lireImageEtPlacerSurBouton("recommencer1.png",btnReinitialiser);

		
		btnDemarrer = new JButton("DEMARRER");
		btnDemarrer.setBorder(emptyBorder);
		btnDemarrer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("demarrer2.png",btnDemarrer);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("demarrer1.png",btnDemarrer);

			}
		});
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			

			
				nivActuel.demarrer();
				desactiverLesRadios();
				
			}
		});
		btnDemarrer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnDemarrer.setBounds(302, 14, 68, 68);
		panelFonctionnalites.add(btnDemarrer);
		OutilsImage.lireImageEtPlacerSurBouton("demarrer1.png",btnDemarrer);

		
		btn1Image = new JButton("+1 IMAGE");
		btn1Image.setBorder(emptyBorder);
		btn1Image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("plusimg2.png",btn1Image);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("plusimg1.png",btn1Image);

			}
		});
		btn1Image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.prochaineImage();
			}
		});
		btn1Image.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btn1Image.setBounds(539, 14, 68, 68);
		panelFonctionnalites.add(btn1Image);
		OutilsImage.lireImageEtPlacerSurBouton("plusimg1.png",btn1Image);

		
		JSlider slider = new JSlider();
		slider.setBounds(312, 94, 440, 26);
		panelFonctionnalites.add(slider);
		
		spinnerMasse = new JSpinner();
		spinnerMasse.setModel(new SpinnerNumberModel(50, 10, 80, 1));
		spinnerMasse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
            int masse= (int)spinnerMasse.getValue();
            nivActuel.setMasseBalle(masse);
            						
			}
		});
		spinnerMasse.setBounds(167, 52, 109, 46);
		panelFonctionnalites.add(spinnerMasse);
		
		spinnerVieMonstre = new JSpinner();
		spinnerVieMonstre.setModel(new SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
		spinnerVieMonstre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nivActuel.setNombreDeVie((int)spinnerVieMonstre.getValue());
				System.out.println("ALHAMDOULLIHA");
			}
		});
		spinnerVieMonstre.setBounds(167, 131, 109, 44);
		panelFonctionnalites.add(spinnerVieMonstre);
		
		JCheckBox chckbxModeScienti = new JCheckBox("Mode scientifique");
		chckbxModeScienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nivActuel.setModeScience(chckbxModeScienti.isSelected());
			}
		});
		chckbxModeScienti.setBounds(649, 0, 137, 23);
		panelFonctionnalites.add(chckbxModeScienti);
		
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
				changerFondEtGrav(typeGravite);
		     
		     
			}
		});
		comboBoxTypeGrav.setModel(new DefaultComboBoxModel(new String[] {"ESPACE", "TERRE", "MARS"}));
		comboBoxTypeGrav.setBounds(643, 135, 109, 37);
//		comboBoxTypeGrav.setBounds(296, 128, 109, 37);
		panelFonctionnalites.add(comboBoxTypeGrav);
		
		JButton btnNewButton = new JButton("MODES EXTRA");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(302, 136, 109, 37);
		panelFonctionnalites.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("QUITTER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton_1.setBounds(421, 136, 109, 37);
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
		lblNewLabel_3.setBounds(539, 142, 109, 23);
		panelFonctionnalites.add(lblNewLabel_3);
		
		
		
		comboBoxTypeGrav.setSelectedItem("ESPACE");
	    niv1.changerTypeGravite("ESPACE"); 
	    
		planCartesien = new PlanCartesien(nivActuel.getBalle().getPositionEnMetre());
		planCartesien.setBounds(1075, 866, 819, 218);
		contentPane.add(planCartesien);
		
		
	  
		
			
			
			
			gifIcon = new ImageIcon(this.getClass().getResource("/espacelvl.gif"));
			img = gifIcon.getImage();
			Image resizedImg = img.getScaledInstance(nivActuel.getWidth(),nivActuel.getHeight(), Image.SCALE_DEFAULT);
			lbl.setIcon(new ImageIcon(resizedImg));
			lbl.setBounds(nivActuel.getX(),nivActuel.getY(), nivActuel.getWidth(), nivActuel.getHeight());
			contentPane.add(lbl);
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 1920, 666);
			contentPane.add(panel);
			

	}
/**
 * méthode pour désactiver les bouton radios
 */
	//walid benakmoum
	private void desactiverLesRadios() {
		if(nivActuel.getEnCoursAnimation()==true) {
		rdbBalleNormal.setEnabled(false);
		rdbBalleElastique.setEnabled(false);
		rdbBalleNova.setEnabled(false);
		comboBoxTypeGrav.setEnabled(false);
		spinnerMasse.setEnabled(false);
		spinnerVieMonstre.setEnabled(false);
		
	}
	if(nivActuel.getEnCoursAnimation()==false) {
		rdbBalleNormal.setEnabled(true);
		rdbBalleElastique.setEnabled(true);
		rdbBalleNova.setEnabled(true);
		comboBoxTypeGrav.setEnabled(true);
		spinnerMasse.setEnabled(true);
		spinnerVieMonstre.setEnabled(true);
	}
	}
	/**
	 * méthode pour changer le fond du niveau selon la graviter choisie
	 * @param fond le nom du fond en question
	 */
	//ZAKARIA SOUDAKI
	public void changerFondEtGrav(String fond) {
		
		
		 switch (fond) {
	        case "TERRE":
	        	gifIcon = new ImageIcon(this.getClass().getResource("/fondAnime2.gif"));
				
	        	//
	            break;
	        case "MARS":
	        	 gifIcon = new ImageIcon(this.getClass().getResource("/mars1.jpg"));
				
	        	//
	            break;
	        case "ESPACE":
	        	 gifIcon = new ImageIcon(this.getClass().getResource("/espacelvl.gif"));
				
	        	//
	            break;
	        case "LUNE":
	        	gifIcon = new ImageIcon(this.getClass().getResource("/imglune2.png"));
	 			img = gifIcon.getImage();
	        	 //
	        	break;
	        default:
	        	 gifIcon = new ImageIcon(this.getClass().getResource("/fondAnime2.gif"));
	 			
	        	 //
	            break;
	    }
			
		    img = gifIcon.getImage();
			Image resizedImg = img.getScaledInstance(nivActuel.getWidth(),nivActuel.getHeight(), Image.SCALE_DEFAULT);
			lbl.setIcon(new ImageIcon(resizedImg));

		repaint();
	}
	
	/**
	 * méthode pour mettre le niveau dans le JFrame
	 * @param obHolder le niveau custom
	 */
	//ahmad khanafer
	public static void  setNiveauCustom(ObstacleHolder obHolder) {
		nivActuel.setObHolder(obHolder);
	}
	
}

