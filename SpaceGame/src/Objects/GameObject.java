package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import basicgame.SpaceGame;
import utilities.Vector2D;

public abstract class GameObject {
	
	public SpaceGame game;
	public Vector2D position;
	public Vector2D velocity;
	public Boolean dead = false;
	public Boolean newBorn = false;
	public double radius;
	public Vector2D direction;
	public boolean fleeing;
	public int lives;

	public abstract void hit();
	public abstract void bounce(GameObject object);
	public abstract boolean overlap(GameObject object);
	public abstract double radius();
	public abstract double getWidth();
	public abstract double getHeight();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	//abstract void update(Action action);

	public abstract Rectangle getBounds2D();
	public double distance(GameObject obj) {
		double distance = this.position.dist(obj.position);
		return distance;
	}
	public Vector2D to(GameObject target) {
		return new Vector2D(target.position.x - this.position.x, target.position.y - this.position.y);
	}
	
	public int getLives() {
		return lives;
	}
	
	public void increaseLives() {
		lives++;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
