package screen;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import algorithm.BoxCollision;
import graphics.Shader;
import main.GameStateManager;
import main.GameThread;
import main.Input;
import main.Renderable;
import main.Updateable;
import math.Matrix4f;
import math.Vector3f;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Player implements Updateable{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
	
	public boolean paused = false;
	
	public Vector3f location;
	public Vector3f rot = new Vector3f(0.0f,180.0f,0.0f);
	
	public Vector3f viewRay;
	
//	public Matrix4f pr_matrix = Matrix4f.orthographic(50.0f, -50.0f, 50.0f*SCREEN_RATIO, -50.0f*SCREEN_RATIO, 50.0f, -50.0f);
	
	public Matrix4f pr_matrix = Matrix4f.perspective(1.0f, -1.0f, 1.0f*SCREEN_RATIO, -1.0f*SCREEN_RATIO, -10.0f, -5.0f);
	
	public Matrix4f vw_matrix = Matrix4f.identity();
	
	public float [] boundBox;
	public Vector3f playerDimensions = new Vector3f(1.0f,3.0f,1.0f); //width, height, length
	
	public Shader shader;
	
	public Player(Vector3f loc, Shader shader){
		
		this.location = loc;
		this.shader = shader;
		
		shader.enable();
		shader.setUniform4f("pr_matrix", pr_matrix);
		shader.setUniform4f("vw_matrix", vw_matrix);
		shader.setUniform1i("tex", 1);
		shader.disable();
		
		boundBox = createBoundBox(playerDimensions);
		
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
			
			move = Vector3f.multiply(move.normalized(), 0.15f);
			
			location = Vector3f.add(location, move);

			float [] futureBox = createBoundBox(playerDimensions);
			
//			System.out.println(" xmax:"+futureBox[0]+" xmin:"+futureBox[1]+" ymax:"+futureBox[2]+" ymin:"+futureBox[3]+" zmax:"+futureBox[4]+" zmin:"+futureBox[5]);
			
			boolean collide = false;
			

			

			for (Renderable r: getNearbyWalls(4.0f)){
				

				
				if (BoxCollision.checkCollision(r, futureBox)){
					collide = true;
//					float [] renderBox = BoxCollision.createCornerArray(r);
//					System.out.println(" xmax:"+renderBox[0]+" xmin:"+renderBox[1]+" ymax:"+renderBox[2]+" ymin:"+renderBox[3]+" zmax:"+renderBox[4]+" zmin:"+renderBox[5]);

					
					break;
				}
			}

			
//			System.out.println();
			
			if (collide == true){
				location = Vector3f.subtract(move, location);
			}
			
			
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
	
	public float [] createBoundBox(Vector3f dimensions){
		
		float [] boundBox = new float [6];
		
		float width = dimensions.x;
		float height = dimensions.y;
		float length = dimensions.z;
		
		boundBox[0] = location.x + width/2.0f;
		boundBox[1] = location.x - width/2.0f;
		
		boundBox[2] = location.y + height/2.0f;
		boundBox[3] = location.y - height/2.0f;

		boundBox[4] = location.z + length/2.0f;
		boundBox[5] = location.z - length/2.0f;
		
		return boundBox;

				
		
		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}
	
	public ArrayList<Wall> getNearbyWalls(float distance){
		
		ArrayList<Wall> nearbyWalls = new ArrayList<Wall>();
		
		ArrayList<Renderable> renderables = GameStateManager.currentState.getRenderables();
		
		for (Renderable r: renderables){
			if (r instanceof Wall && Vector3f.subtract(location,r.location).getMagnitude() < distance){
				nearbyWalls.add((Wall) r);
			}
		}
		
		return nearbyWalls;
		
	}
	
	





}
