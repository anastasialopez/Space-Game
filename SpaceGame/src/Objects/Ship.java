package Objects;

import java.awt.Color;
import java.awt.Rectangle;

import Controllers.Action;
import Controllers.Controller;
import basicgame.SpaceGame;
import utilities.Constants;
import utilities.SoundManager;
import utilities.Vector2D;

public class Ship extends Sprite {
    static final Color COLOR = Color.RED;
    
    final int[] startingXPts = {-17,-10,-17,17};
    final int[] startingYPts = {12,0,-12,0};
    final int[] startingMainThrustXPts = {-10,-25,-20,-25,-10};
    final int[] startingMainThrustYPts = {5,5,0,-5,-5};
    
    boolean thrusting = false;
    int timeShot = Constants.BULLET_DELAY;
    int timeShotLeft = 0;
    public int counter = Constants.INVINSIBLE;
    
    Controller control;
    
    public Action action;

	public boolean fleeing;
	
	Fire fire;
    
    public Ship(SpaceGame game, Controller control) {
    	image = Constants.SHIP;
    	height = 130;
    	width = 117;
    	this.position = new Vector2D();
    	this.velocity = new Vector2D();
    	direction = new Vector2D();
    	this.game = game;
    	this.control = control;
    	this.lives = Constants.INITIAL_SHIP_LIVES;
    	Vector2D fireV = new Vector2D(velocity);
		fireV.add(direction, Constants.BULLET_SPEED);
    	this.fire = new Fire(new Vector2D(position.x, position.y), fireV, rotate);
    	reset();
    }

	public void reset() {
		position.set(Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/2);
		velocity.set(0,  0);
		direction.set(0, -1);
		newBorn = true;
	}
	
	@Override
	public void update(){
		
		if(getLives() <= 0){
			dead = true;
			this.fire.dead = true;
		}
		
		if(newBorn){
			--counter;
		}
		if(counter == 0){
			newBorn = false;
			counter = Constants.INVINSIBLE;
		}
		action = control.action(game);
		
		if(action.thrust == 1){
			thrusting = true;
			showFire();
		}
		else{
			image = Constants.SHIP;
			thrusting = false;
			removeFire();
		}
		
		if(action.shoot && timeShotLeft <= 0){
			shootBullet();
			timeShotLeft = timeShot;
		}
		
		--timeShotLeft;
		
		direction.rotate((Constants.SHIP_STEER_RATE * action.turn) % Constants.SHIP_MAX_STEER_RATE);
		rotate = direction.theta() + Math.PI / 2;
		//double acceleration = action.thrust;
		//if(acceleration > Constants.SHIP_MAG_ACC) acceleration = Constants.SHIP_MAG_ACC;
		
		velocity.add(direction, Constants.SHIP_MAG_ACC * action.thrust * Constants.DT);
		velocity.mult(Constants.SHIP_LOSS);
		
		position.add(velocity, Constants.DT);
		position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
	}

	private void shootBullet() {
		Vector2D bV = new Vector2D(velocity);
	    bV.add(direction, Constants.BULLET_SPEED);
	    Bullet b = new Bullet(new Vector2D(position.x, position.y), bV, rotate);
	    
	    game.add(b);
	    SoundManager.fire();
		
	}
	
	private void showFire(){
		this.fire.position.x = this.position.x;
		this.fire.position.y = this.position.y;
		this.fire.velocity = this.velocity;
		this.fire.rotate = this.rotate;
		this.fire.dead = false;
	    game.add(fire);
	    SoundManager.startThrust();
	}
	
	private void removeFire() {
		this.fire.dead = true;
		
	}
	/*@Override
	public void draw(Graphics2D g){
		AffineTransform at = g.getTransform();
	    g.translate(position.x, position.y);
	    double rot = direction.theta();
	    g.rotate(rot);
	    g.setColor(COLOR);
		//Ellipse2D.Double thisEllipse = new Ellipse2D.Double((int)(position.x - 70), (int)(position.y - rad),(int) (this.radius() + 80),(int) (this.radius() + 200));
		//Rectangle2D.Double rectangle = new Rectangle2D.Double((int)(position.x), (int)(position.y), width, height);
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
	    g.draw(rectangle);
	    g.fill(rectangle);
	    g.setTransform(at);
	    
	    //g.setColor(Color.yellow);
	    //g.fillOval((int)(position.x - radius()), (int)(position.y - radius()),(int) (2 * radius()),(int) (2 * radius()));
	}*/

	@Override
	public double radius() {
		return (width + height)/ 4;
	}

	@Override
	public void hit() {
		if(!newBorn){
			reset();
		}
	}

	public boolean overlap(GameObject object){
		System.out.println("Ship coordenates: " + this.position);
		System.out.println("Objects coordinates: " + object.position);
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle.intersects(object.getBounds2D());
	}
	
	@Override
	public void bounce(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds2D() {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public double distance(GameObject obj) {
		double distance = this.position.dist(obj.position);
		return distance;
	}
}
