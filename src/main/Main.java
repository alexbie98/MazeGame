package main;

import algorithm.Edge;
import algorithm.Graph;
import algorithm.GraphAlgorithm;


public class Main {

	public static void main(String[] args) {
		
		
		Thread gameThread = new Thread(new GameThread());
		gameThread.start();
		
		
		Graph maze = GraphAlgorithm.prim(GraphAlgorithm.generateMazeGraph(4, 4));
	
		for (Edge e: maze.edges){
			System.out.println(e.toString());
		}
		

		System.out.println();
		
		Graph solution = GraphAlgorithm.RecursiveDepthSolve(maze, new Graph(), 0, 15);
		
		for (Edge e: solution.edges){
			System.out.println(e.toString());
		}

	}

}
