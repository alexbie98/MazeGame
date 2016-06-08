package algorithm;

import java.util.ArrayList;

public class MazeSolver {
	
	private MazeSolver(){}
	
	public static Graph RecursiveDepthSolve(Graph g, Graph solution, int currentNode, int finalNode){
		
		System.out.println(currentNode);
		
		if (currentNode == 0){
			solution.addNode(0);
		}
		
		if (currentNode == finalNode){
			return solution;
		}
		
		
		ArrayList<Edge> choices = new ArrayList<Edge>();
		
		for (Edge e: g.edges){
			
			if ((e.nodeA == currentNode && !solution.nodes.contains(e.nodeB))||(e.nodeB == currentNode && !solution.nodes.contains(e.nodeA))) {
				
				
				choices.add(e);
				
				
			}
		}
		
		for (Edge choice: choices){
			
			Graph solutionGraph = solution.copy();
			solutionGraph.addEdge(choice);
			
			Graph end = RecursiveDepthSolve(g, solutionGraph, (choice.nodeA != currentNode) ? choice.nodeA: choice.nodeB, finalNode);
			
			if(end.edges.size()!=0){
				return end;
			}
		}

		return new Graph();
		
		
		
		
		
	}
	
	public static StackBreadthSolve(Graph g, int finalNode){
		
		Graph solution = new Graph();
		
		solution.addNode(0);
		
		int currentNode = 0;
		
		ArrayList<Edge> edgeStack = new ArrayList<Edge>();
		
		while (currentNode!= finalNode){
			
			for (Edge e: g.edges){
				if((e.nodeA == currentNode && !solution.nodes.contains(e.nodeB))||(e.nodeB == currentNode && !solution.nodes.contains(e.nodeA))) {
					edgeStack.add(e);
				}
			}
		}
		
	}

}
