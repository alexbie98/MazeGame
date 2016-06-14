package states;


import graphics.Shader;
import main.GameThread;
import math.Matrix4f;
import math.Vector3f;
import screen.Maze;
import screen.Player;
import screen.Tile;
import screen.Wall;

public class Test extends GameState{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
//	private Matrix4f pr_matrix = Matrix4f.perspective(60.0f, 1.0f/SCREEN_RATIO, 1.0f, 100.0f);
	private Matrix4f pr_matrix = Matrix4f.orthographic(50.0f, -50.0f, 50.0f*SCREEN_RATIO, -50.0f*SCREEN_RATIO, 50.0f, -50.0f);

	private Matrix4f vw_matrix = Matrix4f.rotate(Matrix4f.ROTATION_AXIS_X, 65).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, 30));

	@Override
	public void onStart() {
		
		Shader.WORLD.enable();
		Shader.WORLD.setUniform4f("pr_matrix", pr_matrix);
		Shader.WORLD.setUniform4f("vw_matrix", vw_matrix.multiply(Matrix4f.translate(new Vector3f(0.0f,0.0f,-25.0f))));
		Shader.WORLD.setUniform1i("tex", 1);
		Shader.WORLD.disable();
		
		Maze m = new Maze(4.0f, 8, 15, new Vector3f(0.0f,0.0f,0.0f));
		
		for (Wall w: m.getWalls()){
			addRenderable(w);
		}
		
		for (Tile t: m.getSolutionTiles()){
			addRenderable(t);
		}
		
		
		addUpdateable(new Player(new Vector3f(0.0f,0.0f,0.0f)));
		
		
	}

	@Override
	public void onExit() {
		
		
	}

}
