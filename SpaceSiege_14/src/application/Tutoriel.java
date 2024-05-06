package application;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Fenêtre dont le rôle est d'expliquer comment utiliser l'application, elle explique en détail chaque
 * élément important et guide l'utilisateur dans son aventure dans cette application de jeu à traver des consignes
 * et des images pour tout visualiser.
 * @author ZAKARIA SOUDAKI
 *
 */
public class Tutoriel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel fond
	 */
	private JPanel contentPane;
	/**
	 * fenetre précédente
	 */
	private static AppPrincipal14 appli;
	/**
	 * fenetre actuelle
	 */
	private static Tutoriel fenetre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tutoriel frame = new Tutoriel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 /**
     * Méthode statique pour afficher la fenêtre de sélection de mode de jeu.
     * Cette méthode crée une instance de {@code FenetreModeDeJeu} et la rend visible.
     */
	//ZAKARIA SOUDAKI
    public static void afficherFenetre(AppPrincipal14 app) {
		
	    appli =app;
	    fenetre = new Tutoriel();
		fenetre.setLocationRelativeTo(null);
		fenetre.setUndecorated(true); 
        fenetre.setVisible(true);
        app.setVisible(false);
        
    }
    
    /**
	 * méthode pour rentre visible la fenetre précédente
	 * @param app la fenetre précédente
	 */
	//ZAKARIA SOUDAKI
    public static void retour(FenetreModeDeJeu app) {
		
		app.setVisible(true);
		
	}
    

	/**
     * Initialise et affiche la fenêtre de sélection de mode de jeu.
     */
	//ZAKARIA SOUDAKI
	public Tutoriel() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1400, 1000);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html> BIENVENU DANS SPACE SIEGE <br>Ce tutoriel rapide est conçu pour vous guider à travers votre expérience de jeu <br> et vous fournir \r\ndes explications détaillées sur les fonctionnalités, <br> les modes de jeu, ainsi que d'autres aspects importants de l'application.</html>");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 422, 69);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>La première fenêtre se présente comme ceci :<br> Pour commencer il suffit de cliquer sur jouer, ou quitter pour fermer l'application.</html>");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(10, 67, 229, 75);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("<html>Mais avant tout, si jamais la musique en fond vous dérange,<br>il suffit de cliquer sur le bouton suivant en bas a gauche de <br>l'écrant pour la désactiver ou la réactiver :</html>");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_2.setBounds(10, 139, 206, 95);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("<html>Par la suite, voici la fenêtre des modes jeux:<br><br><br>Les deux mode de jeux disponible dans notre application sont <br>les modes CLASSIQUE  et BAC À SABLE, commençons par décrire<br>le mode CLASSIQUE :</html>");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_3.setBounds(10, 237, 351, 97);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("<html>Pour ce mode de jeu, 3 niveaux préalablement créer par<br>les auteurs sont disponible, il sont en ordre croissant de <br>difficulté, à vous de faire un choix ! </html>");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_4.setBounds(10, 336, 285, 69);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("<html>Voici ce à quoi ressemble l'interface de jeu quelque soit le niveaux choisi:<br><br><br>Au bas de la fenètre plusieurs paramètres de jeu peuvent êtres modifiés <br>par l'utilisateur avant de commencer à jouer :<br><br>1. Le type de balle, chaque balle possède certaines caractéristiques :<br><br>-Balle classique ( un balle normale )<br>-Balle élastique ( augmente de vitesse à chaque rebond )<br>-Balle Nova ( Balle bombe, pour la faire exploser, il faut <br>cliquer sur la touche du clavier espace au<br>momment désirer ) </html>");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_5.setBounds(10, 398, 275, 247);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("<html>2. La masse des Balles ( change la vitesse et l'accélération<br>en fonction de la gravité choisi)<br><br>- automatiquement définit à 50.<br><br>3.Nombre de vies du monstre ( chaque balle enleve une vie )<br><br>-Automatiquement à 1 vie.<br><br>4.Le type de gravité ( change la vitesse et l'accélération de la<br>   balle en fonction de sa masse )<br><br>-Gravité espace séléctionnée par défaut<br><br>5.Force des propulseurs du Monstre ( celui-ci put être déplacé par un deuxième joueur en <br>   utilisant les flèches du clavier losque la balle est tirée )<br><br>-La force est définit en Newton </html>");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_6.setBounds(10, 656, 333, 280);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("<html>D'autre part, diffiérent boutons sont placés au même endroit.<br>De gauche a doite voici à sert chacun d'entre eux :<br><br>1-Tirer<br>2-Pause<br>3-Recommencer<br>4- +Une Image (lorsque le jeu est en pause)<br>5- Menu<br><br>Aussi une case à cocher à droite des bontons permet d'afficher<br>le mode scientifique afin de visiualiser les données scientifique<br>de la balle.</html>");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_7.setBounds(772, 11, 285, 223);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("<html>Maintenant passon au jeu lui-même,<br>Le but est de toucher un monstre ( immobile )dans chaque niveaux<br>en évitant les différents obstacles avec comme outils un canon et les différents types de balles disponible,<br>la difficulter peut être augmenter selon les paramêtres choisi précédemment.<br><br>Pour viser, il suffit de double-cliquer sur l'endroit où l'on souhaite tirer, et un <br>vecteur rouge comme sur l'image apparaitera.<br><br>La force du tir dépend de la longueur du vecteur, et pour les plus malins<br>nous l'avons limiter ;</html>)");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_8.setBounds(772, 237, 269, 223);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("<html>C'est tout pour le mode CLASSIQUE passon au mode<br>BAC À SABLE.<br><br>Voici à quoi ressemble ce mode :<br><br>Le principe est simple,  À VOUS DE CRÉER VOTRE NIVAU !!<br>Au bas de la fenêtres vous avez accès à tout les obstacles <br>disponibles en nombre limités et ayant chacun un principe<br>spécifique.<br><br>-Le rectangle, le triangle et le cercle sont des formes normales<br> qui ne font que collisioner avec la balle, lorsque vous cliquer <br>sur le bouton d'une forme, celle-ci apparait dans l'interface<br>et vous pouvez la déplacer en double-cliquant sur la forme.\r\n\r\n\r\nLorsque vous faite un seul clique, vous avez la possibilité de \r\nchanger la taille de l'obstacle comme vous le souhaitez. En \r\nCliquant sur la poignée verte au dessu vous pouvez faire tourner\r\nn'importe quelle forme aussi,\r\n\r\n2-Les spikes, il ont la faculter de pouvoir faire disparaitre la balle\r\nlorsqu'elle les touche\r\n\r\n3-Le Pad, une plateforme qui augement la vitesse d'une balle de\r\nfaçon significative lorsqu'elle entre en collision avec.\r\n\r\n4- Le cercle élèctrique, toute les balles sont chargées élèctriquement,\r\nelle sont donc attirer ou repoussées par cet élément.</html>");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_9.setBounds(772, 471, 324, 343);
		contentPane.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("<html>Une fois votre niveau complété, il suffit de cliquer\r\nsur le bouton sauvgarder pour l'enregistrer.\r\n\r\nPour y jouer, il suffit de revenir dans le mode classique\r\net  de cliquer sur le bouton au bas de l'écran (Custom)\r\net de séléctionner le fichier du niveau pour le charger dans\r\nl'interface de jeu classique.</html>");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_10.setBounds(772, 811, 198, 125);
		contentPane.add(lblNewLabel_10);
		
		JLabel lblF1 = new JLabel();
		ImageIcon gifIcon = new ImageIcon(this.getClass().getResource("/f1.png"));
		Image img = gifIcon.getImage();
		lblF1.setBounds(432, 11, 260, 159);
		Image resizedImg = img.getScaledInstance(lblF1.getWidth(),lblF1.getHeight(), Image.SCALE_DEFAULT);
		lblF1.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblF1);
		
		
		JLabel lblSon = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/son1.png"));
		img = gifIcon.getImage();
		lblSon.setBounds(509, 169, 93, 75);
		resizedImg = img.getScaledInstance(lblSon.getWidth(),lblSon.getHeight(), Image.SCALE_DEFAULT);
		lblSon.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblSon);
		
		JLabel lblModeDejeu = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/fmdj.png"));
		img = gifIcon.getImage();
		lblModeDejeu.setBounds(429, 245, 263, 149);
		resizedImg = img.getScaledInstance(lblModeDejeu.getWidth(),lblModeDejeu.getHeight(), Image.SCALE_DEFAULT);
		lblModeDejeu.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblModeDejeu);
		
		JLabel lblOption = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/lvl.png"));
		img = gifIcon.getImage();
		lblOption.setBounds(432, 403, 260, 159);
		resizedImg = img.getScaledInstance(lblOption.getWidth(),lblOption.getHeight(), Image.SCALE_DEFAULT);
		lblOption.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblOption);
		
		JLabel lblFJ = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/fdj.png"));
		img = gifIcon.getImage();
		lblFJ.setBounds(432, 573, 260, 180);
		resizedImg = img.getScaledInstance(lblFJ.getWidth(),lblFJ.getHeight(), Image.SCALE_DEFAULT);
		lblFJ.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblFJ);
		
		JLabel lblParam = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/param.png"));
		img = gifIcon.getImage();
		lblParam.setBounds(340, 764, 422, 186);
		resizedImg = img.getScaledInstance(lblParam.getWidth(),lblParam.getHeight(), Image.SCALE_DEFAULT);
		lblParam.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblParam);
		
		JLabel lblbtn = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/btnn.png"));
		img = gifIcon.getImage();
		lblbtn.setBounds(1057, 81, 317, 144);
		resizedImg = img.getScaledInstance(lblbtn.getWidth(),lblbtn.getHeight(), Image.SCALE_DEFAULT);
		lblbtn.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblbtn);
		
		JLabel lblCanon = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/canonnn.png"));
		img = gifIcon.getImage();
		lblCanon.setBounds(1067, 277, 292, 187);
		resizedImg = img.getScaledInstance(lblCanon.getWidth(),lblCanon.getHeight(), Image.SCALE_DEFAULT);
		lblCanon.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblCanon);
		
		JLabel lblBAS = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/BAC.png"));
		img = gifIcon.getImage();
		lblBAS.setBounds(1091, 485, 268, 200);
		resizedImg = img.getScaledInstance(lblBAS.getWidth(),lblBAS.getHeight(), Image.SCALE_DEFAULT);
		lblBAS.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblBAS);
		
		JLabel lblsave = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/saveee.png"));
		img = gifIcon.getImage();
		lblsave.setBounds(1091, 698, 111, 51);
		resizedImg = img.getScaledInstance(lblsave.getWidth(),lblsave.getHeight(), Image.SCALE_DEFAULT);
		lblsave.setIcon(new ImageIcon(resizedImg));
		contentPane.add(lblsave);
		
		JLabel btnsave2 = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/lvlsel.png"));
		img = gifIcon.getImage();
		btnsave2.setBounds(1091, 760, 268, 125);
		resizedImg = img.getScaledInstance(btnsave2.getWidth(),btnsave2.getHeight(), Image.SCALE_DEFAULT);
		btnsave2.setIcon(new ImageIcon(resizedImg));
		contentPane.add(btnsave2);
		
		JLabel btnCustom = new JLabel("New label");
		gifIcon = new ImageIcon(this.getClass().getResource("/cus.png"));
		img = gifIcon.getImage();
		btnCustom.setBounds(1091, 898, 139, 52);
		resizedImg = img.getScaledInstance(btnCustom.getWidth(),btnCustom.getHeight(), Image.SCALE_DEFAULT);
		btnCustom.setIcon(new ImageIcon(resizedImg));
		contentPane.add(btnCustom);
		
		JButton btnNewButton = new JButton("RETOUR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppPrincipal14.retour(appli);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(1259, 913, 89, 23);
		contentPane.add(btnNewButton);
		
		
	}
}
