package Objects;

import java.awt.Rectangle;
import java.util.Random;

import basicgame.SpaceGame;
import utilities.Constants;
import utilities.SoundManager;
import utilities.Vector2D;

public class Asteroid extends Sprite{
	
	public double radius = Constants.INITIAL_ASTEROIDS_RADIUS;
	public static final double MAX_SPEED = 100;
	public int counter = Constants.INVINSIBLE;
	int here = 0;
	
	public Asteroid(double positionX, double positionY, double velocityX, double velocityY){
		image = Constants.ASTEROID;
		width = radius;
		height = radius;
		rotate = 0.0;
		this.position = new Vector2D(positionX, positionY);
		this.velocity = new Vector2D(velocityX, velocityY);
		newBorn = true;
	}
	
	public Asteroid(Vector2D position, Vector2D velocity, SpaceGame game){
		image = Constants.ASTEROID1;
		width = 100;
		height = 100;
		rotate = 0.0;
		this.position = new Vector2D(position);
		this.velocity = new Vector2D(velocity);
		this.game = game;
		newBorn = true;
	}
	
	public Asteroid(SpaceGame game){
		image = Constants.ASTEROID1;
		width = 100;
		height = 100;
		rotate = 0.0;
		Random random = new Random();
		
		double positionX = Math.random() * (Constants.FRAME_WIDTH - 2 * radius);
		double positionY = Math.random() * (Constants.FRAME_HEIGHT  - 2 * radius);
		
		Rectangle thisAsteroid = new Rectangle((int)(positionX), (int)(positionY), (int) width, (int) height);
		Rectangle shipRectangle = new Rectangle((int)(Constants.FRAME_WIDTH/2), (int)(Constants.FRAME_WIDTH/2), 300, 300);
		
		if(thisAsteroid.intersects(shipRectangle)){
			System.out.println("Aseroid Here");
			positionX += random.nextInt(150 - 50) + 50;
			positionY += random.nextInt(150 - 50) + 50;
		}
		
		double velocityX = random.nextInt(200) - 50;
		double velocityY = random.nextInt(200) - 50;
		
		
		this.position = new Vector2D(positionX, positionY);
		this.velocity = new Vector2D(velocityX, velocityY);
		this.newBorn = true;
		
		this.game = game;
		
	}
	
	@Override
	public void update(){
		--counter;
		position.add(velocity, Constants.DT);
		position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		if(counter == 0){
			newBorn = false;
			counter = Constants.INVINSIBLE;
		}
		/*positionX += velocityX * Constants.DT;
		positionY += velocityY * Constants.DT;
		positionX = (positionX + Constants.FRAME_WIDTH) % Constants.FRAME_WIDTH;
		positionY = (positionY + Constants.FRAME_HEIGHT) % Constants.FRAME_HEIGHT;*/
	}
	
	/*@Override
	public void draw(Graphics2D g) {
		int x = (int) position.x;
	    int y = (int) position.y;
	    g.setColor(Color.green);
	    g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}*/

	@Override
	public void hit() {
		newBorn = false;
		if(radius == Constants.INITIAL_ASTEROIDS_RADIUS){
			splitAsteroid(this, 3, Constants.SECOND_ASTEROIDS_RADIUS);
		}
		if(radius == Constants.SECOND_ASTEROIDS_RADIUS){
			splitAsteroid(this, 2, Constants.FINAL_ASTEROIDS_RADIUS);
		}
		dead = true;
		SoundManager.asteroids();
		
	}
	
	public double radius() {
		return (width + height)/ 4.5;
	}
	
	@Override
	public String toString(){
		String asteroid = "";
		asteroid += "Position: " + this.position.toString() + "\n";
		asteroid += "Velocity: " + this.velocity.toString() + "\n";
		asteroid += "Radius: " + this.radius + "\n";
		asteroid += "Game: " + this.game.toString() + "\n";
		return asteroid;
	}
	
	void splitAsteroid(Asteroid asteroid, int split, int radius){
		for(int i = 0; i < split; ++i){
			Random random = new Random();
			double velocityX = random.nextInt(300) - 150;
			double velocityY = random.nextInt(300) - 150;
			//double newX = random.nextInt(75) + asteroid.position.x;
			//double newY = random.nextInt(75) + asteroid.position.y;
			
			Vector2D newVelocity = new Vector2D(velocityX, velocityY);
			//Vector2D newPosition = new Vector2D(newX, newY);
			
			Asteroid division = new Asteroid(asteroid.position, newVelocity, asteroid.game);
			division.image = Constants.images.get(random.nextInt(3));
			division.width = radius;
			division.height = radius;
			division.radius = radius;
			game.add(division);
		}
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
	public boolean overlap(GameObject object) {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle.intersects(object.getBounds2D());
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
