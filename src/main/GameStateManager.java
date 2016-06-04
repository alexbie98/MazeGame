package main;

import states.GameState;
import states.Test;

public class GameStateManager {
	
	public static GameState currentState;
	public static GameState lastState;
	public static long window;
	
	public static GameState Test = new Test();
	
	
	private GameStateManager(){}
	
	public static void setWindow(long window){
		GameStateManager.window = window;
	}
	
	
	public static void render(){
		Renderer.render(currentState);
	}
	
	public static void update(){
		
		if (currentState.isPausedState()){
			return;
		}
		
		for (Updateable u: currentState.getUpdateables()){
			
			if(!u.isUpdatePaused()){
				u.update();
			}
			
		}
	}
	
	public static void goTo(GameState nextState){
		
		if (currentState!= null){
			currentState.onExit();
			
			lastState = currentState;
		}
		
		currentState = nextState;
		currentState.onStart();
		
		
	}
	
	

}
