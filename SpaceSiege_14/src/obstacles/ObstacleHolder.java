package obstacles;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import interfaces.Obstacles;
/**
 * Cette classe représente un conteneur d'obstacles.
 * @author Ahmad Khanafer
 */
public class ObstacleHolder implements Serializable {

	/** ID de série pour la sérialisation */
    private static final long serialVersionUID = -151272286484782830L;

    /** Liste des obstacles */
    private ArrayList<Obstacles> obstacleHolder = new ArrayList<Obstacles>();

    /**
     * Constructeur par défaut de la classe ObstacleHolder.
     */
    public ObstacleHolder() {
    }

    /**
     * Dessine tous les obstacles contenus dans le conteneur.
     * 
     * @param g2d le contexte graphique
     */
    public void drawContient(Graphics2D g2d) {
        for (Obstacles obstacle : obstacleHolder) {
            obstacle.dessiner(g2d);
        }
    }

    /**
     * Renvoie la liste des obstacles.
     * 
     * @return la liste des obstacles
     */
    public ArrayList<Obstacles> getObstacleHolder() {
        return obstacleHolder;
    }

    /**
     * Ajoute un obstacle au conteneur.
     * 
     * @param obstacle l'obstacle à ajouter
     */
    public void addObstacle(Obstacles obstacle) {
        obstacleHolder.add(obstacle);
    }

    /**
     * Définit la liste des obstacles.
     * 
     * @param obstacleHolder la liste des obstacles à définir
     */
    public void setObstacleHolder(ArrayList<Obstacles> obstacleHolder) {
        this.obstacleHolder = obstacleHolder;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères du conteneur d'obstacles.
     * 
     * @return une chaîne de caractères représentant le conteneur d'obstacles
     */
    public String toString() {
        return obstacleHolder.toString();
    }

}
