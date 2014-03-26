package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final Color BG_COLOR = Color.black;
	public static final int FRAME_WIDTH = JEasyFrameFull.WIDTH;
	public static final int FRAME_HEIGHT = JEasyFrameFull.HEIGHT;
	public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
	public static final int DELAY = 20;
	public static final int INVINSIBLE = 45;
	public static final double DT = Constants.DELAY / 1000.0;
	public static final int INITIAL_ASTEROIDS = 3;
	
	public static final int SHIP_RADIUS = 1;
	public static final int INITIAL_ASTEROIDS_RADIUS = 150;
	public static final int SECOND_ASTEROIDS_RADIUS = 80;
	public static final int FINAL_ASTEROIDS_RADIUS = 40;
	public static final int INITIAL_SHIP_LIVES = 5;
	
	public static final double SHIP_STEER_RATE = 0.1;
	public static final double SHIP_MAX_STEER_RATE = 2* Math.PI;
	public static final double SHIP_MAG_ACC = 250;
	public static final double SHIP_LOSS = 0.99;
	
	public static final int BULLET_DELAY = 12;
	public static final int BULLET_SPEED = 180;
	public static final int BULLET_TIME = 120;
	
	public static final double ENEMY_STEER_RATE = 1;
	public static final double ENEMY_MAX_STEER_RATE = 2* Math.PI;
	public static final double ENEMY_MAG_ACC = 80;
	
	public static final int ENEMY_BULLET_DELAY = 60;
	public static final int ENEMY_BULLET_SPEED = 200;
	public static final int ENEMY_BULLET_TIME = 70;
	
	public static final double TORPEDO_STEER_RATE = 0.1;
	public static final double TORPEDO_MAX_STEER_RATE = 2* Math.PI;
	public static final double TORPEDO_MAG_ACC = 300;
	public static final double TORPEDO_LOSS = 0.991;
	
	public static final int TORPEDO_DELAY = 50;
	public static final int TORPEDO_SPEED = 300;
	public static final int TORPEDO_TIME = 100;
	
	public static final int POWERUP_TIME = 700;
	
	//public static final int WORLD_WIDTH = JEasyFrameFull.WIDTH;
	//public static final int WORLD_HEIGHT = JEasyFrameFull.HEIGHT;
	
	public static final double PURSUIT_ANGLE = Math.PI/12;
	public static final int PURSUIT_THRUST = 1;
	public static final double FLEE_DISTANCE = 90;
	public static final double FLEE_ANGLE = 25;
	public static final int FLEE_THRUST = 1;
	
	public static final int POWER_UP_LIVE = 1000;
	
	public static Image ASTEROID, MILKYWAY1, SHIP, SHIP_FIRE, BULLET, ASTEROID1, ASTEROID2, ASTEROID3, MEAN_BULLET,  
						ENEMY_SHIP1, ENEMY_SHIP2, ENEMY_SHIP3, TORPEDO, HEART, FIRE;
	static {
	  try {
	    ASTEROID = ImageManager.loadImage("asteroid");
	    ASTEROID1 = ImageManager.loadImage("asteroid1");
	    ASTEROID2 = ImageManager.loadImage("asteroid2");
	    ASTEROID3 = ImageManager.loadImage("asteroid3");
	    MILKYWAY1 = ImageManager.loadImage("galaxy");
	    SHIP = ImageManager.loadImage("ship");
	    SHIP_FIRE = ImageManager.loadImage("ship_fire");
	    BULLET = ImageManager.loadImage("laser_2");
	    MEAN_BULLET = ImageManager.loadImage("mean_bullet");
	    TORPEDO = ImageManager.loadImage("torpedo");
	    ENEMY_SHIP1 = ImageManager.loadImage("enemy_ship1");
	    ENEMY_SHIP2 = ImageManager.loadImage("enemy_ship2");
	    ENEMY_SHIP3 = ImageManager.loadImage("enemy_ship3");
	    HEART = ImageManager.loadImage("heart");
	    FIRE = ImageManager.loadImage("fire_2");
	  } catch (IOException e) { System.exit(1); }
	}
	
	public static List<Image> images = Arrays.asList(ASTEROID1, ASTEROID2, ASTEROID3);
	
}
