package states;

import org.lwjgl.glfw.GLFW;

import graphics.Shader;
import graphics.Texture;
import gui.Cursor;
import gui.Label;
import main.GameStateManager;
import main.GameThread;
import main.Input;
import math.Matrix4f;
import math.Vector3f;

public class Pause extends GameState{
	
	private Cursor cursor;
	private boolean pKeyDown = true;

	@Override
	public void onStart() {
		
		Shader.WORLD.enable();
		Shader.WORLD.setUniform4f("pr_matrix", Matrix4f.orthographic(10.0f, -10.0f, 10.0f * GameThread.SCREEN_RATIO, -10.0f * GameThread.SCREEN_RATIO, -10.0f, 10.0f));
		Shader.WORLD.setUniform4f("vw_matrix", Matrix4f.identity());
		Shader.WORLD.setUniform1i("tex", 1);
		Shader.WORLD.disable();
		
		
		Texture pausedTex = new Texture("res/paused.png");
		Texture resumeTex = new Texture("res/resume.png");
		Texture mainMenuTex = new Texture("res/main_menu.png");
		Texture cursorTex = new Texture("res/cursor.png");
		
		Label paused = new Label(new Vector3f(-6.0f,4.0f,0.0f), 12.0f, 3.0f, pausedTex);
		Label resume = new Label(new Vector3f(-3.6f,1.0f,0.0f), 6.0f, 1.5f, resumeTex);
		Label mainMenu = new Label(new Vector3f(-4.0f,-0.5f,0.0f), 9.0f, 1.5f, mainMenuTex);
		
		cursor = new Cursor(new Vector3f(-6.0f,1.0f, 0.0f),  1.5f * 1.5f/0.9f,1.5f, cursorTex, 2, 1.5f);
		
		addRenderable(paused);
		addRenderable(resume);
		addRenderable(mainMenu);
		addRenderable(cursor);
		
		
		
		
		
	}

	
	@Override
	public void selfUpdate() {
		
		if (Input.keys[GLFW.GLFW_KEY_P]){
			
			if (!pKeyDown){
				pKeyDown = true;
				GameStateManager.goTo(GameStateManager.Play);
			}
			
		}else{
			pKeyDown = false;
		}
		
		
		if (cursor.spaceKeyPressed){
			
			switch(cursor.getChoice()){
			case 0: cursor.spaceKeyPressed = false; GameStateManager.goTo(GameStateManager.Play);  break;
			case 1: 
				cursor.spaceKeyPressed = false;
				GameStateManager.Play = new Play();
				GameStateManager.goTo(GameStateManager.Title);
				break;
			}
		}

		
		
		
	}
	
	@Override
	public void onExit() {
		
		empty();

	}


	@Override
	public void onRestore() {
		// TODO Auto-generated method stub
		
	}



	

}
