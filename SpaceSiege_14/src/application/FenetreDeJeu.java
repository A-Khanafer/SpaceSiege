package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import niveaux.Niveau1;
import niveaux.Niveau2;
import niveaux.Niveau3;
import niveaux.Niveaux;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
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
	private PlanCartesien planCartesien;
    private String fondActuel = "/fondanimer.gif";
    private JLabel lbl;
    private Image  img;
    private ImageIcon gifIcon;
    
    private static FenetreModeDeJeu appli;
    private static FenetreDeJeu fenetre;
	/**
     * Méthode statique pour afficher la fenêtre de jeu. Crée une instance de {@code FenetreDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre(FenetreModeDeJeu app) {
       
		appli = app;
		
		fenetre = new FenetreDeJeu();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
        fenetre.setVisible(true);
        app.setVisible(false);
        
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
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		 niv1 = new Niveau1();
	   	 niv2 = new Niveau2();
		 niv3 = new Niveau3();
		 niveaux[0] = niv1;
		 niveaux[1] = niv2;
		 niveaux[2] = niv3;
		 nivActuel = niveaux[index];
		 nivActuel.setFocusable(true);

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
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//
	    nivActuel.setBounds(0, 0, 1296, 672);
		contentPane.add(nivActuel);
		//
		JPanel panelFonctionnalites = new JPanel();
		panelFonctionnalites.setBackground(new Color(255, 255, 255));
		panelFonctionnalites.setBounds(0, 683, 795, 286);
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
				
				FenetreModeDeJeu.retour(appli);
				setVisible(false);
				nivActuel.arreter();

			}
		});
		btnRetour.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnRetour.setBounds(521, 128, 68, 68);
		panelFonctionnalites.add(btnRetour);
		OutilsImage.lireImageEtPlacerSurBouton("retour1.png", btnRetour);

		
		btnNiveauPrecedent = new JButton("NIVEAU PRECEDENT");
		btnNiveauPrecedent.setBorder(emptyBorder);
		btnNiveauPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("nivPrecedent2.png", btnNiveauPrecedent);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("nivPrecedent1.png", btnNiveauPrecedent);

			}
		});
		btnNiveauPrecedent.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauPrecedent.setBounds(640, 52, 68, 68);
		panelFonctionnalites.add(btnNiveauPrecedent);
		OutilsImage.lireImageEtPlacerSurBouton("nivPrecedent1.png", btnNiveauPrecedent);

		
		btnNiveauSuivant = new JButton("NIVEAU SUIVANT");
		btnNiveauSuivant.setBorder(emptyBorder);
		btnNiveauSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("nivsuivant2.png", btnNiveauSuivant);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("nivsuivant1.png", btnNiveauSuivant);

			}
		});
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
				refreshLabel();
			
				changerNiveau();
				repaint();
}
			}
		});
		btnNiveauSuivant.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnNiveauSuivant.setBounds(718, 52, 68, 68);
		panelFonctionnalites.add(btnNiveauSuivant);
		OutilsImage.lireImageEtPlacerSurBouton("nivsuivant1.png", btnNiveauSuivant);

		
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
		btnPause.setBounds(403, 52, 68, 68);
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
              planCartesien.reset();
              desactiverLesRadios();
              
              
			}
		});
		btnReinitialiser.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		btnReinitialiser.setBounds(481, 52, 68, 68);
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
		btnDemarrer.setBounds(325, 52, 68, 68);
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
		btn1Image.setBounds(562, 52, 68, 68);
		panelFonctionnalites.add(btn1Image);
		OutilsImage.lireImageEtPlacerSurBouton("plusimg1.png",btn1Image);

		
		JSlider slider = new JSlider();
		slider.setBounds(325, 246, 440, 26);
		panelFonctionnalites.add(slider);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(50, 10, 80, 1));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
            int masse= (int)spinner.getValue();
            nivActuel.setMasseBalle(masse);
            						
			}
		});
		spinner.setBounds(167, 52, 109, 46);
		panelFonctionnalites.add(spinner);
		
		JSpinner spinnerVieMonstre = new JSpinner();
		spinnerVieMonstre.setModel(new SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
		spinnerVieMonstre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nivActuel.setNombreDeVie((int)spinnerVieMonstre.getValue());
				System.out.println("ALHAMDOULLIHA");
			}
		});
		spinnerVieMonstre.setBounds(167, 131, 109, 44);
		panelFonctionnalites.add(spinnerVieMonstre);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(689, 0, 97, 23);
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
			
				changerFondEtGrav(typeGravite);
		     
		     
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
		panelTable.setBounds(805, 683, 377, 286);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		
		
		comboBoxTypeGrav.setSelectedItem("ESPACE");
	    niv1.changerTypeGravite("ESPACE"); 
	    
		planCartesien = new PlanCartesien(nivActuel.getBalle().getPositionEnMetre());
		planCartesien.setBounds(1192, 683, 382, 286);
		contentPane.add(planCartesien);
		
		
	  
		
		
//		    lbl = new JLabel();
////			Image img  = new ImageIcon(this.getClass().getResource(fondActuel)).getImage();
//			img =OutilsImage.lireImageEtRedimensionner(fondActuel, nivActuel.getWidth(), nivActuel.getHeight());
//
//			lbl.setIcon(new ImageIcon(img));
//			lbl.setBounds(nivActuel.getX(), nivActuel.getY(), nivActuel.getWidth(), nivActuel.getHeight());
//			contentPane.add(lbl);
			
			lbl  = new JLabel();
			
			ImageIcon gifIcon = new ImageIcon(this.getClass().getResource("/espacelvl.gif"));
			img = gifIcon.getImage();
			Image resizedImg = img.getScaledInstance(nivActuel.getWidth(),nivActuel.getHeight(), Image.SCALE_DEFAULT);
			lbl.setIcon(new ImageIcon(resizedImg));
			lbl.setBounds(nivActuel.getX(),nivActuel.getY(), nivActuel.getWidth(), nivActuel.getHeight());
			contentPane.add(lbl);
		
		
	
	    
	}
	private void refreshLabel() {
		getContentPane().remove(lbl);

		comboBoxTypeGrav.setSelectedItem("ESPACE"); 
	    niv1.changerTypeGravite("ESPACE"); 

		contentPane.add(lbl);

		
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
	public void changerFondEtGrav(String fond) {
		
		 switch (fond) {
	        case "TERRE":
	        	
	            break;
	        case "MARS":
	        	fondActuel = "mars2.jpg";
	            break;
	        case "ESPACE":
	        	fondActuel = "spacegif.gif";
	            break;
	        case "LUNE":
	        	
	        default:
	        	fondActuel = "terre4.png";
	            break;
	    }
//			img =OutilsImage.lireImageEtRedimensionner(fondActuel, nivActuel.getWidth(), nivActuel.getHeight());
//			lbl.setIcon(new ImageIcon(img));
//	        nivActuel.changerTypeGravite(fond);

		repaint();
	}
}

