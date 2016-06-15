package screen;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import graphics.Shader;
import main.GameStateManager;
import main.GameThread;
import main.Input;
import main.Updateable;
import math.Matrix4f;
import math.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Player implements Updateable{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
	
	public boolean paused = false;
	
	public Vector3f location;
	public Vector3f rot = new Vector3f(0.0f,180.0f,0.0f);
	
	public Vector3f viewRay;
	
//	public Matrix4f pr_matrix = Matrix4f.orthographic(50.0f, -50.0f, 50.0f*SCREEN_RATIO, -50.0f*SCREEN_RATIO, 50.0f, -50.0f);
	
	public Matrix4f pr_matrix = Matrix4f.perspective(1.0f, -1.0f, 1.0f*SCREEN_RATIO, -1.0f*SCREEN_RATIO, -10.0f, -5.0f);
	
	public Matrix4f vw_matrix = Matrix4f.identity();
	
	
	
	public Shader shader;
	
	public Player(Vector3f loc, Shader shader){
		
		this.location = loc;
		this.shader = shader;
		
		shader.enable();
		shader.setUniform4f("pr_matrix", pr_matrix);
		shader.setUniform4f("vw_matrix", vw_matrix);
		shader.setUniform1i("tex", 1);
		shader.disable();
		
		
	}
	


	@Override
	public void update() {
		
		
		if (paused){
			return;
		}
		
	
		
		updateCamera();
		
	}
	

	
	public void updateCamera(){
		
		Vector3f move = new Vector3f();
		
		
		
		//movement
		
		if(Input.keys[GLFW.GLFW_KEY_W]){
			move.x -= (float) Math.cos(Math.toRadians(rot.y+90.0f));
			move.z -= (float) Math.sin(Math.toRadians(rot.y+90.0f));
		}
		
		if(Input.keys[GLFW.GLFW_KEY_A]){
			move.x -= (float) Math.cos(Math.toRadians(rot.y));
			move.z -= (float) Math.sin(Math.toRadians(rot.y));
		}
		
		if(Input.keys[GLFW.GLFW_KEY_S]){
			move.x += (float) Math.cos(Math.toRadians(rot.y+90.0f));
			move.z += (float) Math.sin(Math.toRadians(rot.y+90.0f));
		}
		
		if(Input.keys[GLFW.GLFW_KEY_D]){
			move.x += (float) Math.cos(Math.toRadians(rot.y));
			move.z += (float) Math.sin(Math.toRadians(rot.y));
			
		}
		
		if(Input.keys[GLFW.GLFW_KEY_SPACE]){
			move.y ++;
			
		}
		
		if(Input.keys[GLFW.GLFW_KEY_LEFT_SHIFT]){
			move.y--;
			
		}

		if (move.getMagnitude()>0.0f){
			location = Vector3f.add(location,Vector3f.multiply(move.normalized(), 0.3f));
		}
		
		
		
		//camera movement
		
		if(Input.keys[GLFW.GLFW_KEY_LEFT]){
			rot.y-=2.0f;
		}
		
		if(Input.keys[GLFW.GLFW_KEY_RIGHT]){
			rot.y+=2.0f;
		}
		
		if(Input.keys[GLFW.GLFW_KEY_UP]){

			if (rot.x>-80){
				rot.x-=2.0f * SCREEN_RATIO;
			}


		}

		if(Input.keys[GLFW.GLFW_KEY_DOWN]){

			
			if (rot.x<80){
				rot.x+=2.0f * SCREEN_RATIO;
			}
		}
		
		
		
		Matrix4f rot = Matrix4f.rotate(Matrix4f.ROTATION_AXIS_X, this.rot.x).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, this.rot.y)).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Z, this.rot.z));
		
		vw_matrix = rot.multiply(Matrix4f.translate(new Vector3f(-location.x,-location.y,-location.z)));

		
		shader.enable();
		shader.setUniform4f("vw_matrix", vw_matrix);
		shader.disable();
		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}


}
