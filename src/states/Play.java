package states;


import org.lwjgl.glfw.GLFW;

import graphics.Shader;
import main.GameStateManager;
import main.GameThread;
import main.Input;
import main.Renderable;
import math.Vector3f;
import screen.Cube;
import screen.Maze;
import screen.Player;
import screen.Tile;
import screen.Wall;

public class Play extends GameState{
	
	public float mazeSize = 4.0f;
	public int mazeWidth = 20;
	public int mazeLength = 20;
	

	
	private int tick = 0;
	private int rate;
	
	private boolean genComplete = false;
	
	public Maze maze;
	public Player player;
	
	public boolean pKeyDown = false;
	
	public boolean pathRevealed = false;
	
	@Override
	public void onStart() {
		
		
		
		
		player = new Player(new Vector3f(mazeSize/2.0f,100.0f,mazeSize/2.0f), Shader.WORLD);
		addUpdateable(player);
		
		
		
		maze = new Maze(mazeSize, mazeWidth, mazeLength, new Vector3f(0.0f,0.0f,0.0f));
		
		Cube cube = new Cube(2.0f, new Vector3f(mazeSize * mazeWidth - mazeSize/2.0f, 1.0f, mazeSize * mazeLength - mazeSize/2.0f));
		
		
		addRenderable(cube);
		
		rate = (int)Math.ceil((double)maze.getRenderables().size() / (double)450);
		
		
		
		
		
	
	}
	
	@Override
	public void selfUpdate() {
		
		if (Input.keys[GLFW.GLFW_KEY_P]){
			
			if (!pKeyDown){
				pKeyDown = true;
				GameStateManager.goTo(GameStateManager.Pause);
			}
			
		}
		else{
			pKeyDown = false;
		}
		
		if (Input.keys[GLFW.GLFW_KEY_LEFT_SHIFT] && !pathRevealed){
			
			pathRevealed = true;
			
			int x = Math.round(((player.location.x - mazeSize/2.0f)/mazeSize));
			int z = Math.round(((player.location.z - mazeSize/2.0f)/mazeSize));
			
			int n = 20 * z + x;
			
			maze.generateSolution(n);
			
			for (Tile t: maze.getSolutionTiles()){
				addRenderable(t);
			}
			
			
		}
		
		if (player.location.x > (mazeWidth-1)*mazeSize && player.location.z > (mazeWidth-1)*mazeSize){
			
			GameStateManager.Play = new Play();
			GameStateManager.goTo(GameStateManager.Title);
		}
		
		if (!genComplete){
		
			for (int i = 0; i< rate; i++){
				
				if (maze.getRenderables().size() > tick * rate+i){
					addRenderable(maze.getRenderables().get(maze.getRenderables().size()-1-(tick * rate+i)));
				}
				else{
					genComplete = true;
					break;
				}
				
			}
			
		}
		
		
		tick++;
		
		


		
		
		
	}
	


	@Override
	public void onExit() {
		
		setPausedState(true);
		
	}

	@Override
	public void onRestore() {
		
		player.setShaderUniforms();
		setPausedState(false);
		
		
	}



	

}
