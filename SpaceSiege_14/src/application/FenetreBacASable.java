package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import bacasable.PanelBacASable;
import bacasable.SauvegardeNiveau;
import outils.OutilsImage;

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
					frame.panelBacASable.requestFocusInWindow();
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
		
		 Border emptyBorder = BorderFactory.createEmptyBorder();

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1505, 950);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelBacASable = new PanelBacASable();
		panelBacASable.setBounds(0, 0, 1486, 765);
		contentPane.add(panelBacASable);
		
		btnCarre = new JButton("Carre");
		btnCarre.setBounds(10, 795, 115, 105);
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
		btnCercle.setBounds(141, 795, 115, 105);
		contentPane.add(btnCercle);
		OutilsImage.lireImageEtPlacerSurBouton("cercle.png", btnCercle);
		
		btnTriangle = new JButton("Triangle");
		btnTriangle.setBounds(273, 795, 115, 105);
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
		btnEpines.setBounds(398, 795, 115, 105);
		contentPane.add(btnEpines);
		OutilsImage.lireImageEtPlacerSurBouton("spikes.png", btnEpines);
		
		btnPlaqueRebondissante = new JButton("Plaque Rebondissante");
		btnPlaqueRebondissante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterPlaqueRebondissante();
			}
		});
		btnPlaqueRebondissante.setBounds(523, 795, 115, 105);
		contentPane.add(btnPlaqueRebondissante);
		OutilsImage.lireImageEtPlacerSurBouton("bouncePad.png", btnPlaqueRebondissante);
		
		btnCercleElectrique = new JButton("BouleAiment");
		btnCercleElectrique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterCercleElectrique();
			}
		});
		btnCercleElectrique.setBounds(648, 795, 115, 105);
		contentPane.add(btnCercleElectrique);
		OutilsImage.lireImageEtPlacerSurBouton("balle.png", btnCercleElectrique);
		

		

		btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.setBorder(emptyBorder);
		btnSauvegarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("telechargement2.png", btnSauvegarder);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("telecharger.png", btnSauvegarder);

			}
		});
		btnSauvegarder.setBounds(1252, 795, 110, 105);
		contentPane.add(btnSauvegarder);
		OutilsImage.lireImageEtPlacerSurBouton("telecharger.png", btnSauvegarder);

		
		btnLoad = new JButton("Load Niveau");
		btnLoad.setBorder(emptyBorder);
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("load1.png", btnLoad);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("load.png", btnLoad);

			}
		});
		btnLoad.setBounds(1118, 795, 124, 105);
		contentPane.add(btnLoad);
		OutilsImage.lireImageEtPlacerSurBouton("load.png", btnLoad);

		
		
		
		
		
		
		JButton btnRetour = new JButton("New button");
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
			}
		});
		btnRetour.setBounds(1372, 795, 114, 105);
		contentPane.add(btnRetour);
		OutilsImage.lireImageEtPlacerSurBouton("retour1.png", btnRetour);

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
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.sauvegardeNiveau();
			}
		});
		
	
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
