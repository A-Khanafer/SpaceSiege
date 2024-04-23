package obstacles;

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
	
	
	public ArrayList<Obstacles> getObstacleHolder() {
		return obstacleHolder;
	}

	public void setObstacleHolder(ArrayList<Obstacles> obstacleHolder) {
		this.obstacleHolder = obstacleHolder;
	}
	
	public String toString() {
		return obstacleHolder.toString();
	}

}
