package gui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import graphics.Texture;
import main.GameStateManager;
import main.GameThread;
import main.Input;
import main.Updateable;
import math.Vector3f;

public class Cursor extends Label implements Updateable{
	
	public boolean paused = false;
	public int choices;
	public float gap;
	public Vector3f start;
	
	private int selected = 0;
	public boolean changed = false;
	public boolean spaceKeyPressed = false;
	
	public boolean upKeyDown = false;
	public boolean downKeyDown = false;
	public boolean spaceKeyDown = true;
	
	public Cursor(Vector3f location, float width, float height, Texture texture, int choices, float gap) {
		super(location, width, height, texture);
		this.choices = choices;
		this.gap = gap;
		
		start = new Vector3f(location.x,location.y, location.z);
		
	}

	@Override
	public void update() {
		
		if (paused){
			return;
		}
		
		if (Input.keys[GLFW.GLFW_KEY_UP]){
			
			if (!upKeyDown){
				
				upKeyDown = true;
				selected--;
				selected = (selected%choices + choices)%choices;
				changed = true;

			}
		}
		else{
			upKeyDown = false;
		}
		
		if (Input.keys[GLFW.GLFW_KEY_DOWN]){
			
			if (!downKeyDown){
				
				downKeyDown = true;
				selected++;
				selected = (selected%choices + choices)%choices;
				changed = true;
			}
		}
		else{
			downKeyDown = false;
		}
		
		
		
		
		if (changed){
			this.location.y = start.y - gap*selected;
			
			build();
			generate();
			
			changed = false;
	
		}
		
		if (Input.keys[GLFW.GLFW_KEY_SPACE]){
			
			if (!spaceKeyDown){
				
				spaceKeyPressed = true;
			}
		}
		else{
			spaceKeyDown = false;
		}
		
		
		
		
		

		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}
	
	public int getChoice(){
		return selected;
		
	}
	
	
	
	

}
