package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;

public class FenetreBacASable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static void afficherFenetre() {
      
		FenetreBacASable fenetre = new FenetreBacASable();
        fenetre.setVisible(true);
    }

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public FenetreBacASable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 192));
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(1080, 11, 394, 771);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(186, 793, 173, 157);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(368, 793, 166, 157);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(544, 793, 174, 157);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(728, 793, 166, 157);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(904, 793, 166, 157);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(1080, 793, 196, 157);
		contentPane.add(panel_7);
		panel_7.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(1287, 793, 187, 157);
		contentPane.add(panel_8);
		panel_8.setLayout(null);
	}
}
