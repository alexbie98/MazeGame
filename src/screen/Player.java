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
	

	public Vector3f playerDimensions = new Vector3f(1.0f,4.0f,1.0f); //width, height, length
	
	public Shader shader;
	
	public float deltaY = 0.0f;
	
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
			deltaY =0.02f;
			move.y +=0.5f;
			
		}
		
//		if(Input.keys[GLFW.GLFW_KEY_LEFT_SHIFT]){
//			move.y--;
//			
//		}
		
		boolean collided = false;

		if (move.getMagnitude()>0.0f){
			
			move = Vector3f.multiply(move.normalized(),0.15f);
			
		}
		
		location = Vector3f.add(location, move);

		float [] futureBox = createBoundBox(playerDimensions);
		
		ArrayList<Renderable> collidedWalls = new ArrayList<Renderable>();
		
		for (Renderable r: GameStateManager.currentState.getRenderables()){

			
			if (BoxCollision.checkCollision(r, futureBox)){
				
				collided = true;
				
				if (r instanceof Wall){

					collidedWalls.add(r);
				}

			}
		}
		
		location = Vector3f.subtract(move, location);


		
		
		if (collided){
			
			
			
			deltaY = 0.0f;
			
			if (location.y< -0.68f){
				location.y = -0.64f;
			}
			
			if (collidedWalls.size() != 0){
				
				float [] boundBox = BoxCollision.sumBoundBoxes(collidedWalls);
				
				Vector3f openDirection =  BoxCollision.getOpenDirection(boundBox, futureBox);
			
				
				
				if (openDirection.x ==0.0f && openDirection.z == 0.0f){
					move.x = 0.0f;
					move.z = 0.0f;
				}
				
				if (openDirection.x * move.x < 0.0f && openDirection.x!=0.0f){
					move.x = 0.0f;
				}
				
				if (openDirection.z * move.z < 0.0f && openDirection.z!=0.0f){
					move.z = 0.0f;
				}
			}
			
			move.y = 0.0f;
			
			
			

		
			
		}
		
		
		if (!collided){
			
			
			
			deltaY-=0.001f;
			move.y = move.y+deltaY;
			move.x = 0.0f;
			move.z = 0.0f;
		}
		
		location = Vector3f.add(move, location);
			
		System.out.println(location.y);
		
		
		
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
		
		boundBox[2] = location.y + height/6.0f;
		boundBox[3] = location.y - 5.0f/6.0f * height;

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
	
	public ArrayList<Wall> getWalls(){
		
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		ArrayList<Renderable> renderables = GameStateManager.currentState.getRenderables();
		
		for (Renderable r: renderables){
			if (r instanceof Wall){
				walls.add((Wall) r);
			}
		}
		
		return walls;

	}
	
	





}
