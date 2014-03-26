package Objects;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import Controllers.Action;
import Controllers.Controller;
import Controllers.TorpedoAI;
import basicgame.SpaceGame;
import utilities.Constants;
import utilities.SoundManager;
import utilities.Vector2D;

public class Enemy extends Sprite{
	SpaceGame game;
	Controller controller;
	
	boolean thrusting = false;
	int timeShot = Constants.ENEMY_BULLET_DELAY;
	int timeShotLeft = 0;
	
	Controller control;
	public Action action;
	Boolean torp = true;
	int count = Constants.POWER_UP_LIVE;
	 
	public Enemy(SpaceGame spaceGame, Controller controller, Image image, Vector2D v, double w, double h, int lives) {
		this.image = image;
		width = w;
		height = h;
		this.game = spaceGame;
		this.controller = controller;
		direction = new Vector2D(0, -1);
		velocity = new Vector2D(v);
		position = new Vector2D();
		radius = 30;
		this.lives = lives;
		
		double positionX = Math.random() * (Constants.FRAME_WIDTH - 2 * radius);
		double positionY = Math.random() * (Constants.FRAME_HEIGHT  - 2 * radius);
		
		Rectangle thisAsteroid = new Rectangle((int)(positionX), (int)(positionY), (int) width, (int) height);
		Rectangle shipRectangle = new Rectangle((int)(Constants.FRAME_WIDTH/2), (int)(Constants.FRAME_WIDTH/2), 300, 300);
		
		Random random = new Random();
		if(thisAsteroid.intersects(shipRectangle)){
			System.out.println("Aseroid Here");
			positionX += random.nextInt(300) + 50;
			positionY += random.nextInt(300) + 50;
		}
		
		this.position = new Vector2D(positionX, positionY);
		//this.velocity = new Vector2D(velocityX, velocityY);
		this.newBorn = true;

	}
	
	@Override
	public void update(){
		if(getLives() == 0) dead = true;
		
		Action action = controller.action(game);
		direction.rotate((Constants.ENEMY_STEER_RATE * action.turn * Constants.DT) % Constants.ENEMY_MAX_STEER_RATE);
		rotate = direction.theta() + Math.PI / 2;

		velocity.add(direction, Constants.ENEMY_MAG_ACC * action.thrust * Constants.DT);
		velocity.mult(Constants.SHIP_LOSS);
			
		position.add(velocity, Constants.DT);
		position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		
		if(action.shoot && timeShotLeft <= 0){
			if(action.torpedo && torp){
				shootTorpedo();
				torp = false;
				timeShotLeft = timeShot;
			}
			else{
				shootBullet();
				timeShotLeft = timeShot;	
			}
			
		}
		
		--timeShotLeft;
	}
		
	private void shootTorpedo() {
		Vector2D bV = new Vector2D(velocity);
	    bV.add(direction, Constants.ENEMY_BULLET_SPEED);

		Controller c = new TorpedoAI();
		Torpedo torpedo = new Torpedo(this.game, c, Constants.TORPEDO, this.velocity, this.position, 70, 70);
		((TorpedoAI) c).enemy = torpedo;
		game.add(torpedo);
		SoundManager.torpedo();
		
	}

	private void shootBullet() {
		Vector2D bV = new Vector2D(velocity);
	    bV.add(direction, Constants.ENEMY_BULLET_SPEED);
	    MeanBullet b = new MeanBullet(new Vector2D(position.x, position.y), bV, rotate);
	    game.add(b);
	    SoundManager.enemyFire();
			
	}

	

	@Override
	public double radius() {
		return (width + height)/ 4;
	}

	@Override
	public void hit() {
		torp = true;
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
