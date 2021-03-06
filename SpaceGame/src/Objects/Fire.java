package Objects;

import java.awt.Rectangle;

import utilities.Constants;
import utilities.Vector2D;

public class Fire extends Sprite {
	
	public Fire(Vector2D position, Vector2D velocity, double rotate){
		
		image = Constants.FIRE;
		height = 250;
    	width = 100;
		this.position = position;
		this.velocity = velocity;
		this.rotate = rotate;
		this.dead = false;
	}

	@Override
	public double radius() {
		return radius;
	}

	@Override
	public void update() {
		this.dead = true;
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
