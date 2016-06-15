package screen;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import graphics.Shader;
import main.GameStateManager;
import main.GameThread;
import main.Updateable;
import math.Matrix4f;
import math.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Player implements Updateable{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
	
	public boolean paused = false;
	
	public Vector3f location;
	public Vector3f rot = new Vector3f(0.0f,0.0f,0.0f);
	
//	public Matrix4f pr_matrix = Matrix4f.orthographic(50.0f, -50.0f, 50.0f*SCREEN_RATIO, -50.0f*SCREEN_RATIO, 50.0f, -50.0f);
	
	public Matrix4f pr_matrix = Matrix4f.perspective(1.0f, -1.0f, 1.0f*SCREEN_RATIO, -1.0f*SCREEN_RATIO, -10.0f, -5.0f);
	
	public Matrix4f vw_matrix = Matrix4f.identity();
	
	
	
	public GLFWKeyCallback camControls;
	
	public Shader shader;
	
	public Player(Vector3f location, Shader shader){
		
		this.location = location;
		this.shader = shader;
		
		shader.enable();
		shader.setUniform4f("pr_matrix", pr_matrix);
		shader.setUniform4f("vw_matrix", vw_matrix);
		shader.setUniform1i("tex", 1);
		shader.disable();
	
		camControls = new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				
				//movement
				if(key == GLFW.GLFW_KEY_W && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					location.z--;
				}
				
				if(key == GLFW.GLFW_KEY_A && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					location.x--;
				}
				
				if(key == GLFW.GLFW_KEY_S && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					location.z++;
				}

				if(key == GLFW.GLFW_KEY_D && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					location.x++;
				}
				
				
				//camera movement
				
				if(key == GLFW.GLFW_KEY_LEFT && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					rot.y-=3;
				}
				
				if(key == GLFW.GLFW_KEY_RIGHT && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					rot.y+=3;
				}
				
				if(key == GLFW.GLFW_KEY_UP && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					rot.x-=3;
				}

				if(key == GLFW.GLFW_KEY_DOWN && (action == GLFW.GLFW_REPEAT || action ==GLFW.GLFW_PRESS)){
					rot.x+=3;
				}
				

			}
		};
		
		GLFW.glfwSetKeyCallback(GameStateManager.window, camControls);
		
	}
	


	@Override
	public void update() {
		
		
		if (paused){
			return;
		}
		
	
		
		updateCamera();
		
	}
	

	
	public void updateCamera(){
		
		Matrix4f rot = Matrix4f.rotate(Matrix4f.ROTATION_AXIS_X, this.rot.x).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, this.rot.y)).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Z, this.rot.z));
		vw_matrix = rot.multiply(Matrix4f.translate(new Vector3f(-location.x,-location.y,-location.z)));
		System.out.println(location.toString());
		
		shader.enable();
		shader.setUniform4f("vw_matrix", vw_matrix);
		shader.disable();
		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}


}
