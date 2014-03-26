package Controllers;

import basicgame.Game;
import Objects.Ship;
import Objects.GameObject;
import utilities.Vector2D;

public class ShooterAI  implements Controller{

	public static final double SHOOTING_DISTANCE = 350;
	public static final double SHOOTING_ANGLE = Math.PI / 12;
	GameObject target;
	public GameObject enemy;
	Action action = new Action();
	public Ship ship;
	
	@Override
	public Action action(Game game) {
		GameObject nextTarget = findTarget(game.getGameObjects());
	    if (nextTarget == null)  
	    	return new Action(); 
	    switchTarget(nextTarget);
	    aim(); 
	    action.shoot = ((Math.abs(angleToTarget()) < SHOOTING_ANGLE) 
	                      && inShootingDistance());
	    return action;
	}
	
	public GameObject findTarget(Iterable<GameObject> gameObjects){
		double minDistance = 2 * SHOOTING_DISTANCE;
		GameObject closestTarget = null;
		for (GameObject obj : gameObjects) {
			if (obj instanceof Ship) {
				double dist = enemy.distance(obj);
				if (dist < minDistance) {
					closestTarget = obj;
					minDistance = dist;
				}
			}
		}
		return closestTarget; 
	}
	
	public double angleToTarget() {
		Vector2D v = enemy.position.to(target.position);
		v.rotate(-enemy.direction.theta());
		return v.theta();
	}
	
	public boolean inShootingDistance() {
		return  enemy.distance(target) < SHOOTING_DISTANCE + target.radius();
	}

	public void aim() {
		double angle = angleToTarget();
		action.turn = (int) Math.signum(Math.sin(angle));
	}
		   
	public void switchTarget(GameObject newTarget) {
		target = newTarget;
	}

}
