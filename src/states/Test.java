package states;


import graphics.Shader;
import math.Vector3f;
import screen.Maze;
import screen.Player;
import screen.Tile;
import screen.Wall;

public class Test extends GameState{
	
	

	
	@Override
	public void onStart() {
		
		
		
		addUpdateable(new Player(new Vector3f(0.0f,20.0f,0.0f), Shader.WORLD));
		
		
		
		Maze m = new Maze(4.0f, 15, 15, new Vector3f(0.0f,0.0f,0.0f));
		
		for (Wall w: m.getWalls()){
			addRenderable(w);
		}
		
		for (Tile t: m.getSolutionTiles()){
			addRenderable(t);
		}
		
		
		
		
		
	}

	@Override
	public void onExit() {
		
		
	}

}
