package screen;

import java.util.ArrayList;

import algorithm.Edge;
import algorithm.Graph;
import algorithm.GraphAlgorithm;
import graphics.Texture;
import main.Renderable;
import math.Matrix4f;
import math.Vector3f;

public class Maze {
	
	public static Texture TEX_STRAIGHT = new Texture("res/forward.png");
	public static Texture TEX_BENT = new Texture("res/left.png");
	
	public final int mazeWidth;
	public final int mazeLength;
	public final Vector3f location;
	public final float size;
	
	public Graph mazeGraph;
	
	public ArrayList<Wall> mazeWalls = new ArrayList<>();
	public ArrayList<Tile> solutionTiles = new ArrayList<>();
	public ArrayList<Tile> floorTiles = new ArrayList<>();
	
	public Graph solution;
	
	public Maze(float size, int mazeWidth, int mazeLength, Vector3f location){
	
		this.size = size;
		this.mazeWidth = mazeWidth;
		this.mazeLength = mazeLength;
		this.location = location;
		
		//get grid
		Graph grid = GraphAlgorithm.generateMazeGraph(mazeWidth, mazeLength);
		
		//get minspan
		mazeGraph = GraphAlgorithm.kruskal(grid);
		
		//get walls from subtracting minSpan from grid
		Graph mazeWallGraph = GraphAlgorithm.subtract(grid, mazeGraph);

		//build maze walls from the graph
		mazeWalls = buildMazeWalls(mazeWallGraph);

		
		//build the floor tiles of the maze
		floorTiles = buildFloorTiles();
		
//		generateSolution(0);
		
	}
	
	public void generateSolution(int location){
		
		//get the solution of the minSpan
		solution = GraphAlgorithm.RecursiveDepthSolve(mazeGraph, new Graph(), location, mazeWidth*mazeLength-1);
		
		//build the solution tiles from the solution
		solutionTiles = buildSolutionTiles(solution);
	}
	
	

	public ArrayList<Wall> buildMazeWalls(Graph mazeWall){
		
		
		
		
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		
		
		for (Edge e: mazeWall.edges){
			
			Vector3f nodeACoords = new Vector3f(e.nodeA%mazeWidth, 0.0f, e.nodeA/mazeWidth);
			Vector3f nodeBCoords = new Vector3f(e.nodeB%mazeWidth, 0.0f, e.nodeB/mazeWidth);
			
			float largerZ = ((nodeACoords.z>nodeBCoords.z) ? nodeACoords.z : nodeBCoords.z);
			float largerX = ((nodeACoords.x>nodeBCoords.x) ? nodeACoords.x : nodeBCoords.x);
			
			
			if (Math.abs(e.nodeA-e.nodeB) == mazeWidth){
				walls.add(new Wall(size, new Vector3f(location.x+nodeACoords.x*size, location.y, location.z+(largerZ + Wall.THICK)*size)));

			}
			
			if (Math.abs(e.nodeA-e.nodeB) == 1){
				walls.add(new Wall(size, new Vector3f(location.x+(largerX - Wall.THICK)*size, location.y, location.z+nodeACoords.z*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));

			}
		}
		
		
		// add outer walls
		for (int i=1;i< mazeWidth;i++){
			walls.add(new Wall(size, new Vector3f(location.x+size*i,location.y,location.z)));
			walls.add(new Wall(size, new Vector3f(location.x+size*(i-1),location.y,location.z+size*(mazeLength))));
		}
		
		walls.add(new Wall(size, new Vector3f(location.x,location.y,location.z)));
		
		for (int i=0;i< mazeLength;i++){
			walls.add(new Wall(size, new Vector3f(location.x,location.y,location.z+i*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
			walls.add(new Wall(size, new Vector3f(location.x+size*(mazeWidth),+location.y+0.0f,location.z+i*size)).rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, -90)));
		}
		
		return walls;
		
	}
	
	
	
	public ArrayList<Tile> buildSolutionTiles(Graph solution) {
		
		ArrayList<Tile> tiles = new ArrayList<>();
		
		for (int n: solution.nodes){
			
			ArrayList<Integer> startEnd = new ArrayList<>();
			
			
			if (n!=solution.nodes.get(0)){
				startEnd.add(solution.nodes.get(solution.nodes.indexOf(n)-1));
			}
			else{
				startEnd.add(solution.nodes.get(0)-mazeWidth);
			}
			
			if (n!=solution.nodes.get(solution.nodes.size()-1)){
				startEnd.add(solution.nodes.get(solution.nodes.indexOf(n)+1));
			}
			else{
				startEnd.add(solution.nodes.get(solution.nodes.size()-1)+mazeWidth);
			}
			
			
//			for (Edge e: solution.edges){
//			
//					
//				if (e.nodeA == n){
//					startEnd.add(e.nodeB);
//		
//				}
//				else if(e.nodeB == n){
//					startEnd.add(e.nodeA);
//				}
//
//			}
//			
//			
//			
//			if (n==solution.nodes.get(0)){
//				startEnd.add(solution.nodes.get(0)-width);
//				endnode = true;
//			}
//			
//			if (n==solution.nodes.get(solution.nodes.size()-1)){
//				startEnd.add(solution.nodes.get(solution.nodes.size()-1)+width);
//				endnode = true;
//			}
			
			
			
//			if ((solution.nodes.indexOf(startEnd.get(1)) - solution.nodes.indexOf(startEnd.get(0))<0)&& !endnode){
//
//				int temp = startEnd.get(1);
//				startEnd.set(1, startEnd.get(0));
//				startEnd.set(0, temp);
//				
//			}
			
			if (startEnd.size()!=2){
				System.err.println("unable to find matching edge!");
				return tiles;
			}
			
			int x = n%mazeWidth;
			int z = n/mazeWidth;
			
			Tile t = new Tile(size, size, new Vector3f(location.x+(x+0.5f)*size,location.y -size+0.05f,location.z+(z+0.5f)*size));
			
			int start = n-startEnd.get(0);
			int end = startEnd.get(1)-n;
			
			
			float rot = 0.0f;
			
			
			if (start - end == 0){
				t.texture = TEX_STRAIGHT;
				
				if (start == mazeWidth || end == mazeWidth){
					rot = 0.0f;
				}
				else{
					rot = 90.0f;
				}
				
			}
			
			else{
				
				t.texture = TEX_BENT;
				
				if ((start == 1 && end == mazeWidth)|| (start== -mazeWidth && end == -1)){
					rot = 0.0f;
				}
				
				else if ((start == 1 && end == -mazeWidth)|| (start== mazeWidth && end == -1)){
					rot = -90.0f;
				}
				
				else if ((start == -1 && end == -mazeWidth)|| (start== mazeWidth && end == 1)){
					rot = 180.0f;
				}
				
				else if ((start == -1 && end == mazeWidth)|| (start== -mazeWidth && end == 1)){
					rot = 90.0f;
				}
				
			}
			
			t.rotated(Matrix4f.rotate(Matrix4f.ROTATION_AXIS_Y, rot));
	
			tiles.add(t);
			
		}
		
		return tiles;
	}
	
	public ArrayList<Tile> buildFloorTiles(){
		
		ArrayList<Tile> floorTiles = new ArrayList<>();
		
		for (int i = 0; i<mazeLength*mazeWidth;i++){
			
			int x = i%mazeWidth;
			int z = i/mazeWidth;
			
			Tile tile = new Tile(size, size, new Vector3f((location.x+x+0.5f)*size,location.y-size,(location.z+z+0.5f)*size));
			
			floorTiles.add(tile);
		}
		
		return floorTiles;
		
		
		
		
	}
	
	public ArrayList<Wall> getWalls(){
		return mazeWalls;
	}
	
	public ArrayList<Tile> getSolutionTiles(){
		return solutionTiles;
	}
	
	public ArrayList<Tile> getFloorTiles(){
		return floorTiles;
	}
	
	public ArrayList<Renderable> getRenderables(){
		
		
		ArrayList<Renderable> renderables = new ArrayList<>();
		
		renderables.addAll(mazeWalls);
//		renderables.addAll(solutionTiles);
		renderables.addAll(floorTiles);
		
		return renderables;
		
	}
	

}
