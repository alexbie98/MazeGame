package screen;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import main.GameStateManager;
import main.GameThread;
import main.Updateable;
import math.Matrix4f;
import math.Vector3f;

public class Player implements Updateable{
	
	
	public boolean paused = false;
	
	public Vector3f location;
	
	public Matrix4f cam_matrix = Matrix4f.identity();
	
	public Player(Vector3f location){
		
		this.location = location;
	}

	@Override
	public void update() {
		
		GLFW.glfwSetKeyCallback(GameStateManager.window, new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				
				if(key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_REPEAT){
					location.z++;
				}
				
				if(key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_REPEAT){
					location.x--;
				}
				
				if(key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_REPEAT){
					location.z--;
				}

				if(key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_REPEAT){
					location.x++;
				}
				
				

			}
		});
		
		
//		System.out.println(location.toString());
		

		
	}
	
	public void updateCamera(){
		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}

}
