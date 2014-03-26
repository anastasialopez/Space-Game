package Controllers;

import basicgame.Game;


public class StupidAI implements Controller{
	Action action = new Action();
	
	@Override
	public Action action(Game game) {
		action.shoot = true;
		action.turn = 1;
		action.thrust = 1;
		return action;
	}
	
	
}
