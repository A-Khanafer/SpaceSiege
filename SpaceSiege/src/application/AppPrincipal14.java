package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppPrincipal14 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPrincipal14 frame = new AppPrincipal14();
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
	public AppPrincipal14() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuAuteur = new JMenu("Auteurs");
		menuBar.add(menuAuteur);
		
		JMenu menuApropos = new JMenu("À propos");
		menuBar.add(menuApropos);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		menuApropos.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(408, 236, 313, 317);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitre = new JLabel("SPACE SIEGE");
		lblTitre.setBounds(10, 21, 293, 67);
		panel.add(lblTitre);
		lblTitre.setBackground(new Color(0, 0, 0));
		lblTitre.setForeground(new Color(255, 255, 255));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 37));
		
		JButton btnJouer = new JButton("JOUER");
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FenetreDeJeu.afficherFenetre();
				dispose();
			}
		});
		btnJouer.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		btnJouer.setBounds(46, 105, 216, 69);
		panel.add(btnJouer);

		JButton btnNewButton = new JButton("QUITTER");
		btnNewButton.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		btnNewButton.setBounds(46, 196, 216, 69);
		panel.add(btnNewButton);
		
		

		JButton btnQuitter = new JButton("QUITTER");
		btnQuitter.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 23));
		btnQuitter.setBounds(46, 196, 216, 69);
		panel.add(btnQuitter);

	}
}
