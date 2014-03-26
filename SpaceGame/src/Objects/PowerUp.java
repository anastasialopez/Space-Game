package Objects;

import java.awt.Rectangle;

import utilities.Constants;
import utilities.Vector2D;
import basicgame.SpaceGame;

public class PowerUp extends Sprite{
	SpaceGame game;
	int time = Constants.POWERUP_TIME;
	
	public PowerUp(SpaceGame game){
		this.game = game;
		image = Constants.HEART;
		width = 40;
		height = 40;
		this.radius = Constants.SECOND_ASTEROIDS_RADIUS;
		double positionX = Math.random() * (Constants.FRAME_WIDTH - 2 * radius);
		double positionY = Math.random() * (Constants.FRAME_HEIGHT  - 2 * radius);
		
		this.position = new Vector2D(positionX, positionY);
		this.velocity = new Vector2D(0, 0);
	}

	@Override
	public void hit() {
		dead = true;	
	}

	@Override
	public void bounce(GameObject object) {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean overlap(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double radius() {
		return 30;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void update() {
		--time;
		if(time == 0){
			this.dead = true;
		}
		
	}
	
	@Override
	public Rectangle getBounds2D() {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle;
	}

}
