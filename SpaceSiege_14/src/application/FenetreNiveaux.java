package application;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import bacasable.SauvegardeNiveau;
import outils.OutilsImage;
import systeme.Son;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class FenetreNiveaux extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static FenetreModeDeJeu appli;
	private static FenetreNiveaux fenetre;
	private JFileChooser fileChooser;
	private String selectedFilePath;
	Son bouttonClicker = new Son();
	private JButton btnCustom;
	
	
	public static void afficherFenetre(FenetreModeDeJeu app) {
	       
		appli = app;
		
		fenetre = new FenetreNiveaux();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setVisible(true);
        app.setVisible(false);
        
    }
    public static void retour(FenetreNiveaux app) {
		
		app.setVisible(true);
		
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreNiveaux frame = new FenetreNiveaux();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreNiveaux() {
		bouttonClicker.setFile(2);
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		setLocationRelativeTo(null);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNiv1 = new JButton();
		btnNiv1.setBorder(emptyBorder);
		btnNiv1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl1p.png", btnNiv1);
				btnNiv1.setBounds(1920/2-250, 190+70, 500, 100);


			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl1.png", btnNiv1);
				btnNiv1.setBounds(1920/2-250, 180+70, 500, 100);


			}
		});
		btnNiv1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bouttonClicker.play();
				bouttonClicker.reset();
				FenetreDeJeu.afficherFenetre(fenetre, 0);
				
			}
		});
		btnNiv1.setBounds(1920/2-250, 180+70, 500, 100);
		contentPane.add(btnNiv1);
		OutilsImage.lireImageEtPlacerSurBouton("lvl1.png", btnNiv1);

		
		JButton btnNiv2 = new JButton();
		btnNiv2.setBorder(emptyBorder);
		btnNiv2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl2pt.png", btnNiv2);
				btnNiv2.setBounds(1920/2-250, 1080/2-90, 500, 100);


			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl2.png", btnNiv2);
				btnNiv2.setBounds(1920/2-250, 1080/2-100, 500, 100);


			}
		});
		btnNiv2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bouttonClicker.play();
				bouttonClicker.reset();
				FenetreDeJeu.afficherFenetre(fenetre, 1);
			}
		});
		btnNiv2.setBounds(1920/2-250, 1080/2-100, 500, 100);
		contentPane.add(btnNiv2);
		OutilsImage.lireImageEtPlacerSurBouton("lvl2.png", btnNiv2);

		
		JButton btnNiv3 = new JButton();
		btnNiv3.setBorder(emptyBorder);
		btnNiv3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl3pt.png", btnNiv3);
				btnNiv3.setBounds(1920/2-250, 710-70, 500, 100);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("lvl3.png", btnNiv3);
				btnNiv3.setBounds(1920/2-250, 700-70, 500, 100);

			}
		});
		btnNiv3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bouttonClicker.play();
				bouttonClicker.reset();
				FenetreDeJeu.afficherFenetre(fenetre, 2);
			}
		});
		btnNiv3.setBounds(1920/2-250, 700-70, 500, 100);
		contentPane.add(btnNiv3);
		OutilsImage.lireImageEtPlacerSurBouton("lvl3.png", btnNiv3);

		
		JButton btnRetour = new JButton();
		btnRetour.setBorder(emptyBorder);
		btnRetour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("menu2.png", btnRetour);
				btnRetour.setBounds(1920/2-150, 820, 300, 75);

			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnRetour);
				btnRetour.setBounds(1920/2-150, 810, 300, 75);

			}
		});
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bouttonClicker.play();
				bouttonClicker.reset();
				FenetreModeDeJeu.retour(appli);
				setVisible(false);
			}
		});
		btnRetour.setBounds(1920/2-150, 810, 300, 83);
		contentPane.add(btnRetour);
		OutilsImage.lireImageEtPlacerSurBouton("menu.png", btnRetour);

		
		JButton btnX = new JButton("New button");
		btnX.setBorder(emptyBorder);
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
		btnX.setBounds(1920 - 95, 11, 75, 65);
		contentPane.add(btnX);
		OutilsImage.lireImageEtPlacerSurBouton("xBlanc.png", btnX);
		
		JLabel lbl  = new JLabel("");
		ImageIcon gifIcon = new ImageIcon(this.getClass().getResource("/fondmodedejeu2.gif"));
		Image img = gifIcon.getImage();
		Image resizedImg = img.getScaledInstance(1920,1080, Image.SCALE_DEFAULT);
		lbl.setIcon(new ImageIcon(resizedImg));
		lbl.setBounds(0, 0, 1920, 1080);
		contentPane.add(lbl);
		
		btnCustom = new JButton("Custom");
		btnCustom.addActionListener(new ActionListener() {
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
				        
		                FenetreDeJeu.afficherFenetre(fenetre, 3);
				        FenetreDeJeu.setNiveauCustom(SauvegardeNiveau.lectureFichierBinaireObjet());
				        
				        
				        } else {
		            	JOptionPane.showMessageDialog(frame, "Erreur: Veuillez sélectionner un fichier .txt!", "Erreur de fichier", JOptionPane.ERROR_MESSAGE);
		                
		            }
		        }  else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
	                JOptionPane.showMessageDialog(frame, "Sélection annulée.");
	            }
				
		        System.out.println(selectedFilePath);
			}
		});
		btnCustom.setBounds(916, 961, 89, 23);
		contentPane.add(btnCustom);
	}
}
