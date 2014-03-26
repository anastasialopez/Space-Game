package utilities;

import java.util.Random;

public class Vector2D {
	
	public double x, y;
	
	public Vector2D(){
		
	}
	
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D vector){
		this.x = vector.x;
		this.y = vector.y;
	}
	
	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2D vector){
		this.x = vector.x;
		this.y = vector.y;
	}
	
	public boolean equals(Object o){
		Vector2D vector = (Vector2D) o;
		if (vector.x == this.x && vector.y == this.y) return true;
		return false;
	}
	
	public double magnitude(){
		double magnitud = Math.sqrt((x * x) + (y * y));
		return magnitud;
	}
	
	public double theta(){
		double theta = Math.atan2(y,x);
		return theta;
	}
	
	public double theta(Vector2D vector){
		double theta1 = (this.x * vector.x) + (this.y + vector.y);
		double theta2 = this.magnitude() * vector.magnitude();
		return Math.acos(theta1/theta2);
	}
	
	public String toString(){
		String out = "X coordinate is " + String.valueOf(x) + ", Y coordinate is " + String.valueOf(y);
		return out;
	}
	
	public void add(Vector2D vector){
		x += vector.x;
		y += vector.y;
	}
	
	public void add(double x, double y){
		this.x += x;
		this.y += y;
	}
	
	public void add(Vector2D vector, double fac){
		this.x += vector.x * fac;
		this.y += vector.y * fac;
	}
	
	public void mult(double factor){
		this.x *= factor;
		this.y *= factor;
	}
	
	public void wrap(double w, double h){
		this.x = (this.x + w) % w;
		this.y = (this.y + h) % h;
	}
	
	public void rotate(double theta){
		double auxX = this.x * Math.cos(theta) - this.y * Math.sin(theta);
		double auxY = this.x * Math.sin(theta) + this.y * Math.cos(theta);
		this.x = auxX;
		this.y = auxY;
	}
	
	public double scalarProduct(Vector2D vector){
		return this.x * vector.x + this.y * vector.y;
	}
	
	public double vectorialProduct(Vector2D vector){
		double scalar = this.magnitude() * vector.magnitude() * Math.cos(theta(vector));
		return scalar;
	}
	
	public double dist(Vector2D vector){
		Vector2D aux = new Vector2D(this.x - vector.x, this.y - vector.y);
		return aux.magnitude();
	}
	
	public void normalise(){
		double magnitude = this.magnitude();
		this.x = this.x/magnitude;
		this.y = this.y/magnitude;
	}
	
	public void substract(Vector2D vector){
		this.x -= vector.x;
		this.y -= vector.y;
	}
	
	public Vector2D proj(Vector2D direction){
		Vector2D result = new Vector2D(direction);
		result.mult(this.scalarProduct(direction));
		return result;
	}
	
	public Vector2D to(Vector2D v) {
		return new Vector2D(v.x - x, v.y - y);
	}
	
	public static Vector2D randomVelocity(){
		Random random = new Random();
		Vector2D v = new Vector2D(random.nextInt(300) - 150, random.nextInt(300) - 150);
		return v;
	}
	
	
	
	
	
}
