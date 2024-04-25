package obstacles;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import interfaces.Obstacles;

public class ObstacleHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -151272286484782830L;

	
	private ArrayList<Obstacles> obstacleHolder = new ArrayList<Obstacles>();

	public ObstacleHolder() {
	}
	
	public void drawContient(Graphics2D g2d) {
		
		for (Obstacles obstacle : obstacleHolder) {
			obstacle.dessiner(g2d);
		}
		
	}
	
	public ArrayList<Obstacles> getObstacleHolder() {
		return obstacleHolder;
	}
	
	public void addObstacle(Obstacles obstacle) {
        obstacleHolder.add(obstacle);
    }

	public void setObstacleHolder(ArrayList<Obstacles> obstacleHolder) {
		this.obstacleHolder = obstacleHolder;
	}
	
	public String toString() {
		return obstacleHolder.toString();
	}

}
