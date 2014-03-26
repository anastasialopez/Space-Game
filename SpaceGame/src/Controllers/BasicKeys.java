package Controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import basicgame.Game;


public class BasicKeys extends KeyAdapter implements Controller{
	Action action;
	
	public BasicKeys(){
		action = new Action();
	}
	
	public Action action(Game game){
		return action;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	    switch (key) {
	    	case KeyEvent.VK_UP:
	    		action.thrust = 1;
	    		break;
	    	case KeyEvent.VK_DOWN:
	    		action.thrust = -1;
	    		break;
		    case KeyEvent.VK_LEFT:
		    	action.turn = -1;
		    	break;
		    case KeyEvent.VK_RIGHT:
		    	action.turn = +1;
		    	break;
		    case KeyEvent.VK_SPACE:
		    	action.shoot = true;
		      	break;
		    case KeyEvent.VK_ESCAPE:
		    	action.esc = true;
		    	break;
	    }
	}
	
	public void keyReleased(KeyEvent e) {
	    int key = e.getKeyCode();
	    switch (key) {
	    case KeyEvent.VK_UP:
	      action.thrust = 0;
	      break;
	    case KeyEvent.VK_DOWN:
		  action.thrust = 0;
		  break;
	    case KeyEvent.VK_LEFT:
	      action.turn = 0;
	      break;
	    case KeyEvent.VK_RIGHT:
	      action.turn = 0;
	      break;
	    case KeyEvent.VK_SPACE:
	      action.shoot = false;
	      break;
    }
	}
}
