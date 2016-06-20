package main;

import org.lwjgl.opengl.GL11;

import states.GameState;
import states.Play;
import states.Title;
import states.Pause;

public class GameStateManager {
	
	public static GameState currentState;
	public static GameState lastState;
	public static long window;
	
	public static GameState Play = new Play();
	public static GameState Title = new Title();
	public static GameState Pause = new Pause();
	
	
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
		
		currentState.selfUpdate();
		
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
		
		if (currentState.isPausedState()){
			currentState.onRestore();
		}
		else{
			currentState.onStart();
		}
		
		
	}
	
	

}
