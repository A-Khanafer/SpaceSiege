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

public class FenetreBacASable extends JFrame {

	/** Cette fenêtre est conçue pour tester différentes fonctionnalités graphiques et interactions dans un environnement contrôlé.
	 * Elle peut être utilisée pour démontrer les capacités de manipulation d'objets graphiques ou pour développer de nouvelles
	 * fonctionnalités avant leur intégration dans l'application principale.
	 * @author Soudaki Zakaria
	 */
	 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
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
		
		JPanel panelBacASable = new JPanel();
		panelBacASable.setBackground(new Color(255, 255, 255));
		panelBacASable.setBounds(10, 11, 1060, 771);
		contentPane.add(panelBacASable);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 793, 166, 157);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnCarre = new JButton("Carre");
		btnCarre.setBounds(10, 11, 146, 135);
		panel.add(btnCarre);
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
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(23, 579, 159, 72);
		panel_1.add(btnNewButton_2);
		
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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(186, 793, 173, 157);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnCercle = new JButton("Cercle");
		btnCercle.setBounds(10, 11, 153, 135);
		panel_2.add(btnCercle);
		OutilsImage.lireImageEtPlacerSurBouton("cercle.png", btnCercle);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(368, 793, 166, 157);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnTriangle = new JButton("Triangle");
		btnTriangle.setBounds(10, 11, 146, 135);
		panel_3.add(btnTriangle);
		OutilsImage.lireImageEtPlacerSurBouton("imageTriangle.jpg", btnTriangle);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(544, 793, 174, 157);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JButton btnSpikes = new JButton("Spikes");
		btnSpikes.setBounds(10, 11, 154, 135);
		panel_4.add(btnSpikes);
		OutilsImage.lireImageEtPlacerSurBouton("spikes.png", btnSpikes);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(728, 793, 166, 157);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JButton btnBounce = new JButton("Plaque Rebondissante");
		btnBounce.setBounds(10, 11, 146, 135);
		panel_5.add(btnBounce);
		OutilsImage.lireImageEtPlacerSurBouton("bouncePad.png", btnBounce);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(904, 793, 166, 157);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
		
		JButton btnAiment = new JButton("BouleAiment");
		btnAiment.setBounds(10, 11, 146, 135);
		panel_6.add(btnAiment);
		OutilsImage.lireImageEtPlacerSurBouton("balle.png", btnAiment);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(1080, 793, 197, 157);
		contentPane.add(panel_7);
		panel_7.setLayout(null);
		
		JButton btnCanon = new JButton("Position Canon");
		btnCanon.setBounds(10, 11, 177, 135);
		panel_7.add(btnCanon);
		OutilsImage.lireImageEtPlacerSurBouton("canon.png", btnCanon);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(1287, 793, 187, 157);
		contentPane.add(panel_8);
		panel_8.setLayout(null);
		
		JButton btnMonstre = new JButton("Monstres");
		btnMonstre.setBounds(10, 11, 167, 135);
		panel_8.add(btnMonstre);
		OutilsImage.lireImageEtPlacerSurBouton("images.jpg", btnMonstre);

	}
}
