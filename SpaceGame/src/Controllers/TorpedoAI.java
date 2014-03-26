package Controllers;

import basicgame.Game;
import utilities.Constants;
import utilities.Vector2D;
import Objects.Ship;
import Objects.GameObject;

public class TorpedoAI implements Controller{
	public static final double SHOOTING_DISTANCE = 250;
	public static final double SHOOTING_ANGLE = Math.PI / 12;
	GameObject target;
	public GameObject enemy;
	Action action = new Action();
	//Ship ship;
	
	@Override
	public Action action(Game game) {
		GameObject nextTarget = findTarget(game.getGameObjects());
		if (nextTarget == null)
			return action;
		switchTarget(nextTarget);
		//if (enemy.distance(nextTarget) > Constants.FLEE_DISTANCE)
			seek();
		//action.shoot = ((Math.abs(angleToTarget()) < SHOOTING_ANGLE) && inShootingDistance());
		return action;
	}
	
	public GameObject findTarget(Iterable<GameObject> gameObjects){
		double minDistance = 2 * 600;
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
	
	public void seek() {
		//ship.fleeing = false;
		double angle = angleToTarget();
		action.turn = (int) Math.signum(Math.sin(angle));
		if ((Math.abs(angle) < Constants.PURSUIT_ANGLE))
			action.thrust = Constants.PURSUIT_THRUST;
	}
	
	public double angleToTarget() {
		Vector2D v = enemy.position.to(target.position);
		v.rotate(-enemy.direction.theta());
		return v.theta();
	}
	
	public boolean inShootingDistance() {
		return  enemy.distance(target) < SHOOTING_DISTANCE + target.radius();
	}
		   
	public void switchTarget(GameObject newTarget) {
		target = newTarget;
	}
}
