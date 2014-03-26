package basicgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import utilities.Constants;
import Objects.GameObject;

public class View extends JComponent{

	private static final long serialVersionUID = 1L;
	SpaceGame game;
	Image im = Constants.MILKYWAY1; 
	AffineTransform bgTransf; 
	
	public View(SpaceGame game){
	    double imWidth = im.getWidth(null); 
	    double imHeight = im.getHeight(null); 
	    double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : 
	                                Constants.FRAME_WIDTH/imWidth); 
	    double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : 
	                                Constants.FRAME_HEIGHT/imHeight);
	    bgTransf = new AffineTransform(); 
	    bgTransf.scale(stretchx, stretchy);
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics g0) {
		Graphics2D g = (Graphics2D) g0;
		g.drawImage(im, bgTransf,null);
	    // paint the background
	    //g.setColor(Constants.BG_COLOR);
	    //g.fillRect(0, 0, getWidth(), getHeight());
	    
	    synchronized(game){
	    	if(game.getLevel() == 0){
	    		String level = "GAME OVER";
		    	g.setColor(Color.white);
		    	//g.drawString(lives, 10, 20);
		    	g.setFont(new Font("Courier", Font.BOLD, 40));
		    	g.drawString(level, Constants.FRAME_WIDTH/2 - 100, Constants.FRAME_HEIGHT/2);
	    	}
	    	else if(game.getLevel() == 5){
	    		String level = "YOU HAVE WON, CONGRATULATIONS!";
		    	g.setColor(Color.white);
		    	//g.drawString(lives, 10, 20);
		    	g.setFont(new Font("Courier", Font.BOLD, 40));
		    	g.drawString(level, Constants.FRAME_WIDTH/2 - 300, Constants.FRAME_HEIGHT/2);
	    	}
	    	
	    	else{
		    	String level = "LEVEL: " + game.getLevel();
		    	g.setColor(Color.white);
		    	//g.drawString(lives, 10, 20);
		    	g.setFont(new Font("Courier", Font.BOLD, 20));
		    	g.drawString(level, 10, 60);
			    for(GameObject a : SpaceGame.objects){
			    	a.draw(g);	    	
			    }
			    drawLives(g);
	    	}
	    }
	    //SpaceGame.ship.draw(g);
	}
	
	public void drawLives(Graphics2D g){
		int x = 10, y = 10;
		g.scale(2, 2);
		
	    for(int i = 0; i < SpaceGame.ship.getLives(); ++i, x+=15){
	    	int[] startingXPts = { 0 + x, 2 + x, 6 + x, 6 + x, 0 + x,-6 + x,-6 + x,-2 + x};
		    int[] startingYPts = {-3 + y,-6 + y,-6 + y,0 + y, 6 + y,0 + y,-6 + y,-6 + y };
		    g.setColor(Color.GREEN);
		    g.fillPolygon(startingXPts, startingYPts, startingXPts.length);
	    }
	}
	
	@Override
	public Dimension getPreferredSize() {
		return Constants.FRAME_SIZE;
	}

}
