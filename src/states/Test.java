package states;

import java.util.ArrayList;

import algorithm.Edge;
import algorithm.Graph;
import algorithm.GraphGenerator;
import algorithm.MinSpan;
import graphics.Shader;
import main.GameThread;
import main.Renderer;
import math.Matrix4f;
import math.Vector3f;
import screen.Box;
import screen.Player;
import screen.Wall;

public class Test extends GameState{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
	private int mazeLength = 20, mazeWidth = 20;
	private float wallSize = 2.0f;
	
//	private Matrix4f pr_matrix = Matrix4f.perspective(60.0f, 1.0f/SCREEN_RATIO, 1.0f, 100.0f);
	private Matrix4f pr_matrix = Matrix4f.orthographic(50.0f, -50.0f, 50.0f*SCREEN_RATIO, -50.0f*SCREEN_RATIO, 50.0f, -50.0f);
//	private Matrix4f vw_matrix = Matrix4f.identity();
	private Matrix4f vw_matrix = Matrix4f.rotate(Matrix4f.ROTATION_AXIS_X, 50).multiply(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, 35));

	@Override
	public void onStart() {
		
		Shader.WORLD.enable();
		Shader.WORLD.setUniform4f("pr_matrix", pr_matrix);
		Shader.WORLD.setUniform4f("vw_matrix", vw_matrix.multiply(Matrix4f.translate(new Vector3f(-20.0f,0.0f,-10.0f))));
		Shader.WORLD.setUniform1i("tex", 1);
		Shader.WORLD.disable();
		
		Graph grid = GraphGenerator.generateMazeGraph(mazeLength, mazeWidth);
		Graph minSpan = MinSpan.kruskal(grid);
		
		Graph maze = GraphGenerator.subtract(grid, minSpan);
		
		
		
		for (Edge e: minSpan.edges){
			System.out.println(e.toString());
		}
		System.out.println();
//		for (Edge e: maze.edges){
//			System.out.println(e.toString());
//		}
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		
		
		for (Edge e: maze.edges){
			
			Vector3f nodeACoords = new Vector3f(e.nodeA%mazeWidth, 0.0f, e.nodeA/mazeWidth);
			Vector3f nodeBCoords = new Vector3f(e.nodeB%mazeWidth, 0.0f, e.nodeB/mazeWidth);
			
			float largerZ = ((nodeACoords.z>nodeBCoords.z) ? nodeACoords.z : nodeBCoords.z);
			float largerX = ((nodeACoords.x>nodeBCoords.x) ? nodeACoords.x : nodeBCoords.x);
			
			if (Math.abs(e.nodeA-e.nodeB) == mazeWidth){
				walls.add(new Wall(wallSize, new Vector3f(nodeACoords.x*wallSize, 0.0f, (largerZ + Wall.THICK)*wallSize)));
				System.out.println(e.toString());
			}
			
			if (Math.abs(e.nodeA-e.nodeB) == 1){
				walls.add(new Wall(wallSize, new Vector3f((largerX - Wall.THICK)*wallSize, 0.0f, nodeACoords.z*wallSize)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
				System.out.println(e.toString());
			}
		}
		
		
		// add outer walls
		for (int i=1;i< mazeWidth;i++){
			walls.add(new Wall(wallSize, new Vector3f(wallSize*i,0.0f,0.0f)));
			walls.add(new Wall(wallSize, new Vector3f(wallSize*(i-1),0.0f,wallSize*(mazeLength))));
		}
		
		for (int i=0;i< mazeLength;i++){
			walls.add(new Wall(wallSize, new Vector3f(0.0f,0.0f,i*wallSize)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
			walls.add(new Wall(wallSize, new Vector3f(wallSize*(mazeWidth),0.0f,i*wallSize)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
		}
		
		for (Wall wall: walls){
			addRenderable(wall);
		}
		
		
		addUpdateable(new Player(new Vector3f(0.0f,0.0f,0.0f)));
		
		
	}

	@Override
	public void onExit() {
		
		
	}

}
