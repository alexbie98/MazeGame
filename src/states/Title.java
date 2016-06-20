package states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import graphics.Shader;
import graphics.Texture;
import gui.TitleLabel;
import gui.Cursor;
import gui.Label;
import main.GameStateManager;
import main.GameThread;
import math.Matrix4f;
import math.Vector3f;

public class Title extends GameState{
	
	private Cursor cursor;
	

	@Override
	public void onStart() {
		
		
		
		Shader.WORLD.enable();
		Shader.WORLD.setUniform4f("pr_matrix", Matrix4f.orthographic(10.0f, -10.0f, 10.0f * GameThread.SCREEN_RATIO, -10.0f * GameThread.SCREEN_RATIO, -10.0f, 10.0f));
		Shader.WORLD.setUniform4f("vw_matrix", Matrix4f.identity());
		Shader.WORLD.setUniform1i("tex", 1);
		Shader.WORLD.disable();
		
		Texture titleTex = new Texture("res/title.png");
		Texture playTex = new Texture("res/play.png");
		Texture exitTex = new Texture("res/exit.png");
		Texture settingsTex = new Texture("res/settings.png");
		Texture cursorTex = new Texture("res/cursor.png");
		
		TitleLabel titleLabel = new TitleLabel(new Vector3f(-6.64f,5.0f,0.0f), 6.64f *2, 1.49f *2, titleTex);
		
		Label play = new Label(new Vector3f(-3.0f, 1.5f, 0.0f), 4.5f, 1.5f, playTex);
		Label settings = new Label(new Vector3f(-3.5f, 0.0f, 0.0f), 9.0f, 1.5f, settingsTex);
		Label exit = new Label(new Vector3f(-3.1f, -1.5f, 0.0f), 4.5f, 1.5f, exitTex);
		
		cursor = new Cursor(new Vector3f(-5.0f,1.5f, 0.0f),  1.5f * 1.5f/0.9f,1.5f, cursorTex, 3, 1.5f);
		
		
		
		addRenderable(titleLabel);
		
		addRenderable(play);
		addRenderable(settings);
		addRenderable(exit);
		
		addRenderable(cursor);
		
		
		
		
		
		
	}
	
	@Override
	public void selfUpdate() {
		
		
		if (cursor.spaceKeyPressed){
			
			switch(cursor.getChoice()){
			
			case 0: cursor.spaceKeyPressed = false; GameStateManager.goTo(GameStateManager.Play); ; break;
			case 1: cursor.spaceKeyPressed = false; System.out.println("This doesn't do anything yet!");  break;
			case 2: cursor.spaceKeyPressed = false; GLFW.glfwDestroyWindow(GameStateManager.window); break;
			}
		}
		

		
		
	}

	@Override
	public void onExit() {
		
		empty();
		
	}

	@Override
	public void onRestore() {

		
	}

	

}
