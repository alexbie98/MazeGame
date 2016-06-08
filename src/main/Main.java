package main;

import algorithm.Edge;
import algorithm.Graph;
import algorithm.GraphGenerator;
import algorithm.MazeSolver;
import algorithm.MinSpan;

public class Main {

	public static void main(String[] args) {
		
		
//		Thread gameThread = new Thread(new GameThread());
//		gameThread.start();
		
		
		Graph maze = MinSpan.prim(GraphGenerator.generateMazeGraph(3, 3));
		
		for (Edge e: maze.edges){
			System.out.println(e.toString());
		}
		
//		Graph grid = GraphGenerator.generateMazeGraph(3, 3);
//		
//		for (Edge e: grid.edges){
//			System.out.println(e.toString());
//		}
		
		System.out.println();
		
		Graph solution = MazeSolver.RecursiveDepthSolve(maze, new Graph(), 0, 8);
		
		for (Edge e: solution.edges){
			System.out.println(e.toString());
		}

	}

}
