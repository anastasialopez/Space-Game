package Objects;

import java.awt.Image;
import java.awt.Rectangle;

import Controllers.Action;
import Controllers.Controller;
import basicgame.SpaceGame;
import utilities.Constants;
import utilities.Vector2D;

public class Torpedo extends Sprite{
	SpaceGame game;
	Controller controller;
	
	boolean thrusting = false;
	int timeShot = Constants.ENEMY_BULLET_DELAY;
	int timeShotLeft = 0;
	
	Controller control;
	public Action action;
	 
	public Torpedo(SpaceGame spaceGame, Controller controller, Image image, Vector2D v, Vector2D p, double w, double h) {
		this.image = image;
		width = w;
		height = h;
		this.game = spaceGame;
		this.controller = controller;
		
		direction = new Vector2D(0, -1);
		velocity = new Vector2D(v);
		position = new Vector2D(p);
		radius = 30;

		//this.position = new Vector2D(positionX, positionY);
		//this.velocity = new Vector2D(velocityX, velocityY);
		this.newBorn = true;

	}
	
	@Override
	public void update(){
		Action action = controller.action(game);
		direction.rotate((Constants.ENEMY_STEER_RATE * action.turn * Constants.DT) % Constants.ENEMY_MAX_STEER_RATE);
		rotate = direction.theta() + Math.PI / 2;

		velocity.add(direction, Constants.TORPEDO_MAG_ACC * action.thrust * Constants.DT);
		velocity.mult(Constants.SHIP_LOSS);
			
		position.add(velocity, Constants.DT);
		position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
	}

	@Override
	public double radius() {
		return (width + height)/ 4;
	}

	@Override
	public void hit() {
		this.dead = true;
	}

	public boolean overlap(GameObject object){
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle.intersects(object.getBounds2D());
	}
		
	@Override
	public void bounce(GameObject object) {
		Vector2D coll = new Vector2D(object.position);
		coll.substract(this.position);
		coll.normalise();
		
		Vector2D tang = new Vector2D(-coll.y, coll.x);
		Vector2D vc = this.velocity.proj(coll);
		Vector2D vt = this.velocity.proj(tang);
		Vector2D oc = object.velocity.proj(coll);
		Vector2D ot = object.velocity.proj(tang);
		this.velocity.set(vt);
		this.velocity.add(oc);
		object.velocity.set(ot);
		object.velocity.add(vc);
		
	}
		
	@Override
	public double getWidth(){
		return width;
	}
		
	@Override
	public double getHeight(){
		return height;
	}

	@Override
	public Rectangle getBounds2D() {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle;
	}

}
