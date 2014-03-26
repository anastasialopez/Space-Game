package Objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;


public abstract class Sprite extends GameObject {
	Image image;
	public double width, height, radius, rotate;
	
	public void draw(Graphics2D g) {
		double imW = image.getWidth(null);
		double imH = image.getHeight(null);

		AffineTransform t = new AffineTransform();
		//t.rotate(direction, 0, 0);
		if(rotate != 0.0){
			t.rotate(rotate);
		}
		
		t.scale(width/imW, height/imH);
		t.translate(-imW/2.0, -imH/2.0);
		AffineTransform t0 = g.getTransform();   
		g.translate(position.x, position.y);
		g.drawImage(image, t, null);
		g.setTransform(t0);
		
		
	}
}
