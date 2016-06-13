package screen;

import java.util.ArrayList;

import algorithm.Edge;
import algorithm.Graph;
import algorithm.GraphAlgorithm;
import math.Matrix4f;
import math.Vector3f;

public class Maze {
	
	public final int width;
	public final int length;
	public final Vector3f location;
	public final float size;
	
	public Graph mazeGraph;
	
	public ArrayList<Wall> mazeWalls = new ArrayList<>();
	public ArrayList<Tile> solutionTiles = new ArrayList<>();
	
	public Graph solution;
	
	public Maze(float size, int width, int length, Vector3f location){
	
		this.size = size;
		this.width = width;
		this.length = length;
		this.location = location;
		
		//get grid
		Graph grid = GraphAlgorithm.generateMazeGraph(width, length);
		
		//get minspan
		mazeGraph = GraphAlgorithm.kruskal(grid);
		
		//get walls from subtracting minSpan from grid
		Graph mazeWallGraph = GraphAlgorithm.subtract(grid, mazeGraph);
		
		//get the solution of the minSpan
		solution = GraphAlgorithm.RecursiveDepthSolve(mazeGraph, new Graph(), 0, width*length-1);

		//build maze walls from the graph
		mazeWalls = buildMazeWalls(mazeWallGraph);
		
		//build the solution tiles from the solution
		solutionTiles = buildSolutionTiles(solution);
		
	}
	
	

	public ArrayList<Wall> buildMazeWalls(Graph mazeWall){
		
		
		
		
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		
		
		for (Edge e: mazeWall.edges){
			
			Vector3f nodeACoords = new Vector3f(e.nodeA%width, 0.0f, e.nodeA/width);
			Vector3f nodeBCoords = new Vector3f(e.nodeB%width, 0.0f, e.nodeB/width);
			
			float largerZ = ((nodeACoords.z>nodeBCoords.z) ? nodeACoords.z : nodeBCoords.z);
			float largerX = ((nodeACoords.x>nodeBCoords.x) ? nodeACoords.x : nodeBCoords.x);
			
			System.out.println(e.toString());
			
			if (Math.abs(e.nodeA-e.nodeB) == width){
				walls.add(new Wall(size, new Vector3f(location.x+nodeACoords.x*size, location.y, location.z+(largerZ + Wall.THICK)*size)));

			}
			
			if (Math.abs(e.nodeA-e.nodeB) == 1){
				walls.add(new Wall(size, new Vector3f(location.x+(largerX - Wall.THICK)*size, location.y, location.z+nodeACoords.z*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));

			}
		}
		
		
		// add outer walls
		for (int i=1;i< width;i++){
			walls.add(new Wall(size, new Vector3f(location.x+size*i,location.y,location.z)));
			walls.add(new Wall(size, new Vector3f(location.x+size*(i-1),location.y,location.z+size*(length))));
		}
		
		for (int i=0;i< length;i++){
			walls.add(new Wall(size, new Vector3f(location.x,location.y,location.z+i*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
			walls.add(new Wall(size, new Vector3f(location.x+size*(width),+location.y+0.0f,location.z+i*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
		}
		
		return walls;
	}
	
	public ArrayList<Tile> buildSolutionTiles(Graph solution) {
		
		ArrayList<Tile> tiles = new ArrayList<>();
		
		for (int n: solution.nodes){
			
			int x = n%width;
			int z = n/width;
			
			tiles.add(new Tile(size, size, new Vector3f(location.x+x*size,location.y -size,location.z+z*size)));
					
			
		}
		
		return tiles;
	}
	
	public ArrayList<Wall> getWalls(){
		return mazeWalls;
	}
	
	public ArrayList<Tile> getSolutionTiles(){
		return solutionTiles;
	}
	
	

}
