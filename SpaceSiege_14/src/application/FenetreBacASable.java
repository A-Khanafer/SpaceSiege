package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import outils.OutilsImage;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import composantdessin.PanelBacASable;

public class FenetreBacASable extends JFrame {

	/** Cette fenêtre est conçue pour tester différentes fonctionnalités graphiques et interactions dans un environnement contrôlé.
	 * Elle peut être utilisée pour démontrer les capacités de manipulation d'objets graphiques ou pour développer de nouvelles
	 * fonctionnalités avant leur intégration dans l'application principale.
	 * @author Soudaki Zakaria
	 */
	 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCarre;
	private JButton btnCercle;
	private JButton btnTriangle;
	private JButton btnSpikes;
	private JButton btnPlaqueRebondissante;
	private JButton btnAimant;
	private JButton btnCanon;
	private JButton btnMonstre;
	private JButton btnRetour;
	private JPanel panelCercle;
	private JPanel panelCarre;
	private JPanel panelTriangle;
	private JPanel panelSpike;
	private JPanel panelPlaqueRebondissante;
	private JPanel panelAimant;
	private JPanel panelMonstre;
	private PanelBacASable panelBacASable;
	
	
	public static void afficherFenetre() {
      
		FenetreBacASable fenetre = new FenetreBacASable();
        fenetre.setVisible(true);
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
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelCarre = new JPanel();
		panelCarre.setBackground(new Color(255, 255, 255));
		panelCarre.setBounds(10, 793, 166, 157);
		contentPane.add(panelCarre);
		panelCarre.setLayout(null);
		
		panelBacASable = new PanelBacASable();
		panelBacASable.setBounds(10, 11, 1060, 771);
		contentPane.add(panelBacASable);
		
		btnCarre = new JButton("Carre");
		btnCarre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterRectangle();
			}
		});
		btnCarre.setBounds(10, 11, 146, 135);
		panelCarre.add(btnCarre);
		OutilsImage.lireImageEtPlacerSurBouton("carre.png", btnCarre);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(1080, 11, 394, 771);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(23, 675, 159, 72);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(210, 675, 159, 72);
		panel_1.add(btnNewButton_1);
		
		btnRetour = new JButton("RETOUR");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FenetreModeDeJeu.afficherFenetre();
				setVisible(false);
			}
		});
		btnRetour.setBounds(23, 579, 159, 72);
		panel_1.add(btnRetour);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(210, 579, 158, 72);
		panel_1.add(btnNewButton_3);
		
		JLabel lblNbDeBalle = new JLabel("Nombre de balles total  :");
		lblNbDeBalle.setBounds(23, 21, 201, 28);
		panel_1.add(lblNbDeBalle);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(23, 60, 133, 34);
		panel_1.add(spinner);
		
		JLabel lblNewLabel = new JLabel("Nombre de balles elastiques :");
		lblNewLabel.setBounds(23, 123, 201, 28);
		panel_1.add(lblNewLabel);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(23, 172, 133, 34);
		panel_1.add(spinner_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(23, 233, 201, 22);
		panel_1.add(lblNewLabel_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(23, 266, 143, 34);
		panel_1.add(spinner_2);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(23, 329, 159, 22);
		panel_1.add(lblNewLabel_2);
		
		JSlider slider = new JSlider();
		slider.setBounds(24, 367, 200, 26);
		panel_1.add(slider);
		
		panelCercle = new JPanel();
		panelCercle.setBounds(186, 793, 173, 157);
		contentPane.add(panelCercle);
		panelCercle.setLayout(null);
		
		btnCercle = new JButton("Cercle");
		btnCercle.setBounds(10, 11, 153, 135);
		panelCercle.add(btnCercle);
		OutilsImage.lireImageEtPlacerSurBouton("cercle.png", btnCercle);
		
		panelTriangle = new JPanel();
		panelTriangle.setBounds(368, 793, 166, 157);
		contentPane.add(panelTriangle);
		panelTriangle.setLayout(null);
		
		btnTriangle = new JButton("Triangle");
		btnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBacASable.ajouterTriangle();
			}
		});
		btnTriangle.setBounds(10, 11, 146, 135);
		panelTriangle.add(btnTriangle);
		OutilsImage.lireImageEtPlacerSurBouton("imageTriangle.jpg", btnTriangle);
		
		panelSpike = new JPanel();
		panelSpike.setBounds(544, 793, 174, 157);
		contentPane.add(panelSpike);
		panelSpike.setLayout(null);
		
		btnSpikes = new JButton("Spikes");
		btnSpikes.setBounds(10, 11, 154, 135);
		panelSpike.add(btnSpikes);
		OutilsImage.lireImageEtPlacerSurBouton("spikes.png", btnSpikes);
		
		panelPlaqueRebondissante = new JPanel();
		panelPlaqueRebondissante.setBounds(728, 793, 166, 157);
		contentPane.add(panelPlaqueRebondissante);
		panelPlaqueRebondissante.setLayout(null);
		
		btnPlaqueRebondissante = new JButton("Plaque Rebondissante");
		btnPlaqueRebondissante.setBounds(10, 11, 146, 135);
		panelPlaqueRebondissante.add(btnPlaqueRebondissante);
		OutilsImage.lireImageEtPlacerSurBouton("bouncePad.png", btnPlaqueRebondissante);
		
		panelAimant = new JPanel();
		panelAimant.setBounds(904, 793, 166, 157);
		contentPane.add(panelAimant);
		panelAimant.setLayout(null);
		
		btnAimant = new JButton("BouleAiment");
		btnAimant.setBounds(10, 11, 146, 135);
		panelAimant.add(btnAimant);
		OutilsImage.lireImageEtPlacerSurBouton("balle.png", btnAimant);
		
		JPanel panelCanon = new JPanel();
		panelCanon.setBounds(1080, 793, 197, 157);
		contentPane.add(panelCanon);
		panelCanon.setLayout(null);
		
		btnCanon = new JButton("Position Canon");
		btnCanon.setBounds(10, 11, 177, 135);
		panelCanon.add(btnCanon);
		OutilsImage.lireImageEtPlacerSurBouton("canon.png", btnCanon);
		
		panelMonstre = new JPanel();
		panelMonstre.setBounds(1287, 793, 187, 157);
		contentPane.add(panelMonstre);
		panelMonstre.setLayout(null);
		
		btnMonstre = new JButton("Monstres");
		btnMonstre.setBounds(10, 11, 167, 135);
		panelMonstre.add(btnMonstre);
		OutilsImage.lireImageEtPlacerSurBouton("images.jpg", btnMonstre);
		
		

	}
}