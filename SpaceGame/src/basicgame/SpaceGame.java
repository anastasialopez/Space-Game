package basicgame;

import java.util.ArrayList;

import Controllers.BasicKeys;
import Controllers.Controller;
import Controllers.ShooterAI;
import Controllers.StalkerAI;
import Controllers.StupidAI;
import Objects.Asteroid;
import Objects.Ship;
import Objects.Enemy;
import Objects.GameObject;
import Objects.PowerUp;
import utilities.Constants;
import utilities.JEasyFrameFull;
import utilities.Vector2D;

public class SpaceGame implements Game{
	
	static ArrayList<GameObject> objects;
	static ArrayList<GameObject> pending;
	static ArrayList<GameObject> active;
	BasicKeys control;
	static Ship ship;
	private int level = 0;
	int score = 0;
	Controller controller;
	int powerUp = Constants.POWER_UP_LIVE;
	Boolean pUp = true;
	
	public void loadLevel(){
		switch (level){
		case 1:
			addAsteroids(Constants.INITIAL_ASTEROIDS);
			break;

		case 2:
			addAsteroids(Constants.INITIAL_ASTEROIDS);
			addStupidController(2);
			break;
		
		case 3:			
			addAsteroids(Constants.INITIAL_ASTEROIDS - 1);
			addStupidController(1);
			addShooterController(2);
			break;
		
		case 4:
			addAsteroids(1);
			addStalkerController();
		}
	}
	
	public void addStupidController(int number){
		for(int i = 0; i < number; ++i ){
			controller = new StupidAI();
			Enemy enemy = new Enemy(this, controller, Constants.ENEMY_SHIP1, Vector2D.randomVelocity(), 70, 70, 5);
			objects.add(enemy);
		}
	}
	
	public void addShooterController(int number){
		for(int i = 0; i < number; ++i ){
			controller = new ShooterAI();
			Enemy enemy2 = new Enemy(this, controller, Constants.ENEMY_SHIP2, Vector2D.randomVelocity(), 70, 70, 5);
			((ShooterAI) controller).enemy = enemy2;
			objects.add(enemy2);
		}
	}
	
	public void addStalkerController(){
		controller = new StalkerAI();
		Enemy enemy3 = new Enemy(this, controller, Constants.ENEMY_SHIP3, new Vector2D(0,0), 120, 120, 40);
		((StalkerAI) controller).enemy = enemy3;
		objects.add(enemy3);
	}
	
	public void addPowerUp(int number){
		PowerUp heart = new PowerUp(this);
		objects.add(heart);
	}
	
	public void addAsteroids(int number){
		for(int i = 0; i < number; i++){
			Asteroid asteroid = new Asteroid(this);
			objects.add(asteroid);
		}
	}
	
	public int countEnemies(){
		int number = 0;
		int power = 0;
		for(GameObject object : objects){
			String classObject = object.getClass().toString();
			if(classObject.contains("Objects.Asteroid") || classObject.contains("Objects.Enemy")) ++number;
			if(classObject.contains("Objects.PowerUp")) ++power;
		}
		if(power <= 0) pUp = true;
		else pUp = false;
		return number;
	}
	
	public SpaceGame(){
		objects = new ArrayList<GameObject>();
		pending = new ArrayList<GameObject>();
		active = new ArrayList<GameObject>();
		
		loadLevel();
		
		control = new BasicKeys();
		ship = new Ship(this, control);
		objects.add(ship);
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpaceGame game = new SpaceGame();
	    View view = new View(game);
	    new JEasyFrameFull(view).addKeyListener(game.control);
	    
	    while(true){
	    	game.update();
	    	view.repaint();
	    	Thread.sleep(Constants.DELAY);
	    }
	}
	
	public void update(){
		if(powerUp <= 0 && pUp){
			addPowerUp(1);
			powerUp = Constants.POWER_UP_LIVE;
		}
		
		else if(powerUp <= 0) powerUp = Constants.POWER_UP_LIVE;
		
		Controllers.Action action = control.action(this);
		if(action.esc){
			System.exit(1);
		}
		ship.action = control.action(this);
		for(GameObject o : objects){
			o.update();
			checkCollision(o);
			if(!o.dead)
				active.add(o);
		}
		
		synchronized (this) {
			objects.clear();
		    objects.addAll(pending);
		    objects.addAll(active);
		    
		    if(countEnemies() == 0 && this.level == 4){
		    	this.level = 5;
		    }
		    else if(countEnemies() == 0 && this.level < 4){
				++level;
				loadLevel();
			}
		}
		if(ship.dead){
			this.level = 0;
			for(GameObject object : objects){
				object.dead = true;
			}
		}
		pending.clear();
		active.clear();
		//ship.update();
		--powerUp;
	}

	public void checkCollision(GameObject object) {
		if ( ! object.dead) {
			for (GameObject otherObject : objects) {
				String classObject = object.getClass().toString();
				String classOtherObject = otherObject.getClass().toString();
				if (classObject.contains("Objects.Asteroid") &&  classOtherObject.contains("Objects.Ship") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
					
					if(otherObject.overlap(object)){
						if(!ship.newBorn)
			    			ship.setLives(ship.getLives() - 1);
						object.hit();
			    		otherObject.hit();			    		
			    		increaseScore(0);
			    		return;
					}
		    	}
				
				if (classObject.contains("Objects.Ship") &&  classOtherObject.contains("Objects.Enemy") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
					if(!ship.newBorn)
						ship.setLives(ship.getLives() - 1);
					object.hit();				
		    		increaseScore(0);
		    		return;
		    	}

				if (classObject.contains("Objects.Ship") &&  classOtherObject.contains("Objects.MeanBullet") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
					if(!ship.newBorn)
						ship.setLives(ship.getLives() - 1);
					object.hit();
		    		otherObject.hit();
		    		increaseScore(0);
		    		return;
		    	}
				
				if (classObject.contains("Objects.Ship") &&  classOtherObject.contains("Objects.Torpedo") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
					object.hit();
					ship.setLives(ship.getLives() - 1);
		    		otherObject.hit();
		    		increaseScore(0);
		    		return;
		    	}
				
				if (classObject.contains("Objects.Ship") &&  classOtherObject.contains("Objects.PowerUp") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
		    		otherObject.hit();
		    		ship.increaseLives();
		    		return;
		    	}
				
				if (classObject.contains("Objects.Asteroid") &&  classOtherObject.contains("Objects.Bullet") 
						&& overlap(object, otherObject)) {
					// the object's hit, and the other is also
		    		object.hit();
		    		otherObject.hit();
		    		increaseScore(object.radius());
		    		return; 
		    	}
				
				if(classObject.contains("Objects.Asteroid") &&  classOtherObject.contains("Objects.Asteroid") 
						&& overlap(object, otherObject) && object != otherObject){
					//two asteroids chocan
					if(!object.newBorn){
						/*Vector2D v = object.velocity;
						object.velocity = otherObject.velocity;
						otherObject.velocity = v;*/
						object.bounce(otherObject);
					}
					
				}
				
				if(classObject.contains("Objects.Enemy") &&  classOtherObject.contains("Objects.PowerUp") 
						&& overlap(object, otherObject) && object != otherObject){
					//two asteroids chocan
					object.hit();
					otherObject.hit();
					
				}
				
				if(classObject.contains("Objects.Enemy") &&  classOtherObject.contains("Objects.Bullet") 
						&& overlap(object, otherObject) && object != otherObject){
					//two asteroids chocan
					object.setLives(object.getLives() - 1);
					otherObject.hit();
					
				}
				
			}
		}		
	}

	public boolean overlap(GameObject object, GameObject otherObject) {
		/* distance > sqrt((x1-x2)^2 + (y1-y2)^2) 
		 (r1+r2)2 > ((x1-x2)^2 + (y1-y2)^2)
		double distance = object.position.dist(otherObject.position);
		if((object.radius() + otherObject.radius()) >= distance){
			return true;
		}

		return false;*/
		return object.getBounds2D().intersects(otherObject.getBounds2D());
	}

	public void add(GameObject object) {
		pending.add(object);		
	}

	@Override
	public Iterable<GameObject> getGameObjects() {
		Iterable<GameObject> it = objects;
		return it;
	}
	
	public int getScore(){
		return score;
	}
	
	public void increaseScore(double radius){
		if(radius == Constants.INITIAL_ASTEROIDS_RADIUS) this.score += 10;
		if(radius == Constants.SECOND_ASTEROIDS_RADIUS) this.score += 20;
		if(radius == Constants.FINAL_ASTEROIDS_RADIUS) this.score += 30;
		if(radius == 0) this.score -= 10;
	}

	public Vector2D getShipLocation() {
		return ship.position;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public int getLevel(){
		return this.level;
	}

}
