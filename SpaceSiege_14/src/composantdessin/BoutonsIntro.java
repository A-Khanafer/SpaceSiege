package composantdessin;

import javax.swing.JPanel;

import application.AppPrincipal14;
import application.FenetreModeDeJeu;
import outils.OutilsImage;
import systeme.Son;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BoutonsIntro extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Son bouttonClicker = new Son();
    private Image imgFond = null;
    private int longueurPanel  = 170;
    private int hauteurPanel  = 125 ;
    private int longueurBtn = 110;
    private int hauteurBtn = 36;
    
	/**
	 * Create the panel.
	 */
	public BoutonsIntro( int longueur,  int hauteur , AppPrincipal14 app) {
		bouttonClicker.setFile(2);
		this.setBounds(longueur/2 - longueurPanel/2 , hauteur/2 +60 ,longueurPanel, hauteurPanel);
		setBackground(new Color(0, 0, 0,0));
		setLayout(null);
		
		imgFond = OutilsImage.lireImage("fond.png");
		
		JButton btnQuitter = new JButton("QUITTER");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnQuitter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("q2.png", btnQuitter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("q1.png", btnQuitter);

			}
		});
		btnQuitter.setBounds(longueurPanel/2-longueurBtn/2, 67 , longueurBtn, hauteurBtn);
		add(btnQuitter);
		OutilsImage.lireImageEtPlacerSurBouton("q1.png", btnQuitter);
		
		
		JButton btnJouer = new JButton("JOUER");
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bouttonClicker.play();
				OutilsImage.lireImageEtPlacerSurBouton("q2.png", btnJouer);
			
				FenetreModeDeJeu.afficherFenetre(app);
			
				
			}
		});
		btnJouer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("p2.png", btnJouer);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				OutilsImage.lireImageEtPlacerSurBouton("p1.png", btnJouer);

			}
		});
		btnJouer.setBounds(longueurPanel/2-longueurBtn/2, 17 , longueurBtn, hauteurBtn);
		add(btnJouer);
		OutilsImage.lireImageEtPlacerSurBouton("p1.png", btnJouer);

		
		

		
		
		
	}
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		imgFond = OutilsImage.lireImage("fond.png");
		dessiner(g2d);
		
	}
	
	
	public void dessiner(Graphics2D g2d) {
		
        g2d.drawImage( imgFond,  0,  0,  getWidth(),  getHeight(),  null);

	}
}
