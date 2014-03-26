package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

//SoundManager for Asteroids

public class SoundManager {

	static int nBullet = 0;
	static boolean thrusting = false;

	// this may need modifying
	final static String path = "sounds/";

	// note: having too many clips open may cause
	// "LineUnavailableException: No Free Voices"
	public final static Clip[] bullets = new Clip[5];
	public final static Clip[] enemyBullets = new Clip[5];
	public final static Clip[] torpedos = new Clip[5];

	public final static Clip bangMedium = getClip("bangMedium");
	public final static Clip bangSmall = getClip("bangSmall");
	public final static Clip fire = getClip("Fire");
	public final static Clip thrust = getClip("thrust");
	public final static Clip bullet = getClip("EnemyBullet");
	public final static Clip torpedo = getClip("torpedo");

	public final static Clip[] clips = {bangMedium, bangSmall, fire, thrust, bullet, torpedo};
	
static {
		for (int i = 0; i < bullets.length; i++){
			torpedos[i] = getClip("torpedo");
			bullets[i] = getClip("Fire");
			enemyBullets[i] = getClip("EnemyBullet");
		}
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 20; i++) {
			fire();
			Thread.sleep(100);
		}
		for (Clip clip : clips) {
			play(clip);
			Thread.sleep(1000);
		}
	}

	// methods which do not modify any fields

	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	// methods which modify (static) fields

	public static void fire() {
		// Fire the n-th bullet and increment the index
		Clip clip = bullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;
	}
	
	public static void enemyFire() {
		// Fire the n-th bullet and increment the index
		Clip clip = enemyBullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;
	}
	
	public static void torpedo() {
		// Fire the n-th bullet and increment the index
		Clip clip = torpedos[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % torpedos.length;
	}

	public static void startThrust() {
		if (!thrusting) {
			thrust.loop(-1);
			thrusting = true;
		}
	}

	public static void stopThrust() {
		thrust.loop(0);
		thrusting = false;
	}

	// Custom methods playing a particular sound
	// Please add your own methods below

	public static void asteroids() {
		play(bangSmall);
	}
	public static void extraShip() {
		play(bangMedium);
	}

}

