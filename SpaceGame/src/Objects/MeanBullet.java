package Objects;

import java.awt.Rectangle;

import utilities.Constants;
import utilities.Vector2D;

public class MeanBullet extends Sprite {
	public int timeLeft = Constants.ENEMY_BULLET_TIME;
	public double radius = 5;
	
	public MeanBullet(Vector2D position, Vector2D velocity, double rotate){
		image = Constants.MEAN_BULLET;
		width = 5;
		height = 15;
		this.position = position;
		this.velocity = velocity;
		this.rotate = rotate;
	}

	@Override
	public double radius() {
		return radius;
	}

	@Override
	public void update() {
		--timeLeft;
		if (timeLeft <= 0) dead = true;
		
		position.add(velocity, Constants.DT);
		position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		
	}

	/*@Override
	void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
	    g.translate(position.x, position.y);
	    double rot = position.theta();
	    g.rotate(rot);
		g.setColor(Color.magenta);
		g.fillOval(1,1, (int)radius, (int)radius);
		g.setTransform(at);

		
	}*/

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
	public double getWidth(){
		return 0;
	}
	
	@Override
	public double getHeight(){
		return 0;
	}
	
	@Override
	public Rectangle getBounds2D() {
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds((int)(position.x), (int)(position.y), (int) this.width, (int) this.height);
		return rectangle;
	}

}
