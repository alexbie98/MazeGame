package states;


import graphics.Shader;
import main.Renderable;
import math.Vector3f;
import screen.Maze;
import screen.Player;
import screen.Tile;
import screen.Wall;

public class Test extends GameState{
	
	public float mazeSize = 4.0f;
	
	

	
	@Override
	public void onStart() {
		
		
		
		addUpdateable(new Player(new Vector3f(mazeSize/2.0f,0.0f,mazeSize/2.0f), Shader.WORLD));
		
		
		
		Maze m = new Maze(mazeSize, 5, 5, new Vector3f(0.0f,0.0f,0.0f));
		
		for (Renderable r: m.getRenderables()){
			addRenderable(r);
		}
		
		
		
		
		
		
	}

	@Override
	public void onExit() {
		
		
	}

}
