package basicgame;

import Objects.GameObject;

public interface Game {
	public Iterable<GameObject> getGameObjects();
	public void add(GameObject obj);
	public void checkCollision(GameObject o);
	public boolean overlap(GameObject object, GameObject otherObject);

}
