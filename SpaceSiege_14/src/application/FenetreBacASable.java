package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import bacasable.PanelBacASable;
import bacasable.SauvegardeNiveau;
import outils.OutilsImage;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class FenetreBacASable extends JFrame {

	/** Cette fenêtre est conçue pour tester différentes fonctionnalités graphiques et interactions dans un environnement contrôlé.
	 * Elle peut être utilisée pour démontrer les capacités de manipulation d'objets graphiques ou pour développer de nouvelles
	 * fonctionnalités avant leur intégration dans l'application principale.
	 * @author Soudaki Zakaria
	 */
	 
	private static final long serialVersionUID = 1L;
	/**
	 * panel fond
	 */
	private JPanel contentPane;
	/**
	 * bouton du carre
	 */
	private JButton btnCarre;
	/**
	 * bouton du cercle
	 */
	private JButton btnCercle;
	/**
	 * bouton du triangle
	 */
	private JButton btnTriangle;
	/**
	 *  bouton des épines
	 */
	private JButton btnEpines;
	/**
	 *  bouton de la paque rebondissante
	 */
	private JButton btnPlaqueRebondissante;
	/**
	 *  bouton du cercle élèctrique
	 */
	private JButton btnCercleElectrique;
	/**
	 *  bouton du canon
	 */
	private JButton btnCanon;

	/**
	 * bouton retour
	 */
	private JButton btnRetour;
	/**
	 * panel de construction de niveau
	 */
	private PanelBacASable panelBacASable;
	/**
	 * fenetre précédente fermée
	 */
	private static FenetreModeDeJeu appli;
	/**
	 * selectionneur de fichier
	 */
	private JFileChooser fileChooser;
	/**
	 * bouton charger fichier
	 */
	private JButton btnLoad;
	/**
	 * chemin de données
	 */
	private String selectedFilePath;
	/**
	 * fenêtre actuelle
	 */
	private static FenetreBacASable fenetre;
	/**
	 * bouton sauvegarder
	 */
	private JButton btnSauvegarder;
	
	
	/**
	 * méthode pour l'ouverture de la fenetre actuelle et la fermeture de la précédente
	 * @param app fenêtre précédente
	 */
	//ZAKARIA SOUDAKI
	public static void afficherFenetre(FenetreModeDeJeu app) {
      
		appli= app;
		
		fenetre = new FenetreBacASable();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
        fenetre.setVisible(true);
        app.setVisible(false);
    }

	/**
	 * Permet de lancer l'application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreBacASable frame = new FenetreBacASable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crée le panneau
	 */
	//ZAKARIA SOUDAKI
	public FenetreBacASable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1080);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelBacASable = new PanelBacASable();
		panelBacASable.setBounds(0, 0, 1920, 864);
		contentPane.add(panelBacASable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(1052, 901, 842, 223);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 155, 261, 31);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(197, 155, 74, 31);
		panel_1.add(btnNewButton_1);
		
		btnRetour = new JButton("RETOUR");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FenetreModeDeJeu.retour(appli);
				setVisible(false);
			}
		});
		btnRetour.setBounds(10, 92, 73, 52);
		panel_1.add(btnRetour);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(93, 92, 96, 52);
		panel_1.add(btnNewButton_3);
		
		JLabel lblNbDeBalle = new JLabel("Nombre de balles total  :");
		lblNbDeBalle.setBounds(477, 11, 135, 28);
		panel_1.add(lblNbDeBalle);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(549, 50, 63, 34);
		panel_1.add(spinner);
		
		JLabel lblNewLabel = new JLabel("Nombre de balles elastiques :");
		lblNewLabel.setBounds(676, 11, 156, 28);
		panel_1.add(lblNewLabel);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(676, 50, 75, 34);
		panel_1.add(spinner_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(93, 17, 63, 22);
		panel_1.add(lblNewLabel_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(93, 50, 63, 34);
		panel_1.add(spinner_2);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(10, 17, 71, 22);
		panel_1.add(lblNewLabel_2);
		
		JSlider slider = new JSlider();
		slider.setBounds(11, 55, 70, 26);
		panel_1.add(slider);
		
		btnLoad = new JButton("Load Niveau");
		btnLoad.addActionListener(new ActionListener() {
			//Ahmad Khanafer
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.setSize(400, 300);
			    frame.setLocationRelativeTo(null);
				
				fileChooser = new JFileChooser();
				fileChooser.setBounds(1076, 11, 576, 453);
				contentPane.add(fileChooser);
				fileChooser.setAcceptAllFileFilterUsed(false); // Désactive le filtre "Tous les fichiers"
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		        fileChooser.setFileFilter(filter);
		        
		        // Afficher le dialogue et obtenir la réponse de l'utilisateur
		        int result = fileChooser.showOpenDialog(frame);
		
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            
		            if (selectedFile != null && selectedFile.getName().endsWith(".txt")) {
		            	  
		            	selectedFilePath = selectedFile.getAbsolutePath();
		                JOptionPane.showMessageDialog(frame, "Fichier sélectionné: " + selectedFile.getAbsolutePath());
		                SauvegardeNiveau.lireFichierTexte(selectedFilePath);
		                SauvegardeNiveau.creationFichierBinaire(selectedFilePath);
				        
				        
				        panelBacASable.setObHolder(SauvegardeNiveau.lectureFichierBinaireObjet());
				        
				        
				        } else {
		            	JOptionPane.showMessageDialog(frame, "Erreur: Veuillez sélectionner un fichier .txt!", "Erreur de fichier", JOptionPane.ERROR_MESSAGE);
		                
		            }
		        }  else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
	                JOptionPane.showMessageDialog(frame, "Sélection annulée.");
	            }
				
		        System.out.println(selectedFilePath);
		        
			}
		});
		btnLoad.setBounds(736, 155, 96, 57);
		panel_1.add(btnLoad);
		

		

		btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.sauvegardeNiveau();
			}
		});
		btnSauvegarder.setBounds(635, 155, 96, 57);
		panel_1.add(btnSauvegarder);
		
		btnCarre = new JButton("Carre");
		btnCarre.setBounds(10, 1019, 115, 105);
		contentPane.add(btnCarre);
		btnCarre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterRectangle();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("carre.png", btnCarre);
		
		btnCercle = new JButton("Cercle");
		btnCercle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterCercle();
			}
		});
		btnCercle.setBounds(141, 1019, 115, 105);
		contentPane.add(btnCercle);
		OutilsImage.lireImageEtPlacerSurBouton("cercle.png", btnCercle);
		
		btnTriangle = new JButton("Triangle");
		btnTriangle.setBounds(273, 1019, 115, 105);
		contentPane.add(btnTriangle);
		btnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterTriangle();
			}
		});
		OutilsImage.lireImageEtPlacerSurBouton("imageTriangle.jpg", btnTriangle);
		
		btnEpines = new JButton("Spikes");
		btnEpines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterEpines();
			}
		});
		btnEpines.setBounds(402, 1019, 115, 105);
		contentPane.add(btnEpines);
		OutilsImage.lireImageEtPlacerSurBouton("spikes.png", btnEpines);
		
		btnPlaqueRebondissante = new JButton("Plaque Rebondissante");
		btnPlaqueRebondissante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterPlaqueRebondissante();
			}
		});
		btnPlaqueRebondissante.setBounds(527, 1019, 115, 105);
		contentPane.add(btnPlaqueRebondissante);
		OutilsImage.lireImageEtPlacerSurBouton("bouncePad.png", btnPlaqueRebondissante);
		
		btnCercleElectrique = new JButton("BouleAiment");
		btnCercleElectrique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterCercleElectrique();
			}
		});
		btnCercleElectrique.setBounds(662, 1019, 115, 105);
		contentPane.add(btnCercleElectrique);
		OutilsImage.lireImageEtPlacerSurBouton("balle.png", btnCercleElectrique);
		
	
	}
	/**
	 * obetnir le chemin de données
	 * @return chemin de données
	 */
	//ahmad khanafer
	public String getSelectedFilePath() {
        return selectedFilePath;
    }
}
