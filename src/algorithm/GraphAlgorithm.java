package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GraphAlgorithm {

	private GraphAlgorithm(){}
	
	public static Graph generate(){
		Graph g = new Graph();
		
		
		
//		g.addEdge(new Edge(0, 1, 6, true));
//		g.addEdge(new Edge(0, 5, 1, true));
//		g.addEdge(new Edge(1, 2, 3, true));
//		g.addEdge(new Edge(5, 2, 7, true));
//		g.addEdge(new Edge(2, 4, 1, true));
//		g.addEdge(new Edge(1, 4, 2, true));
//		g.addEdge(new Edge(4, 3, 5, true));
//		
//		return g;
		
		g.addEdge(new Edge(0, 1, 6, true));
		g.addEdge(new Edge(0, 3, 3, true));
		g.addEdge(new Edge(1, 3, 7, true));
		g.addEdge(new Edge(1, 2, 2, true));
		g.addEdge(new Edge(1, 6, 8, true));
		g.addEdge(new Edge(3, 2, 8, true));
		g.addEdge(new Edge(3, 4, 1, true));
		g.addEdge(new Edge(4, 2, 7, true));
		g.addEdge(new Edge(2, 5, 5, true));
		g.addEdge(new Edge(2, 8, 6, true));
		g.addEdge(new Edge(2, 6, 4, true));
		g.addEdge(new Edge(6, 5, 3, true));
		g.addEdge(new Edge(6, 7, 2, true));
		g.addEdge(new Edge(5, 8, 5, true));
		g.addEdge(new Edge(5, 7, 1, true));
		g.addEdge(new Edge(7, 8, 9, true));
		
		
		return g;


		
		
	}
	
	/**
	 * Generates a grid graph with random weights
	 * @param the number of nodes in the x-direction of the maze
	 * @param the number of nodes in the z-direction of the maze
	 * @return
	 */
	public static Graph generateMazeGraph(int width, int height){
		
		Graph g = new Graph();
		
		
		for (int y=0; y< height; y++){
			for (int x=0; x< width; x++){
				
				int nodeNum = y*width +x;
				
				if (nodeNum%width - (nodeNum-1)%width >=0){

					g.addEdge(new Edge(nodeNum, nodeNum-1, 0, true));

					

				}
				
				if ((nodeNum+1)%width - nodeNum%width >=0){
					

					g.addEdge(new Edge(nodeNum, nodeNum+1, 0, true));

				}
				
				if ((nodeNum-width) >=0){
					g.addEdge(new Edge(nodeNum, nodeNum-width, 0, true));
				}
				

				if ((nodeNum+width) <= (width*height-1)){
					g.addEdge(new Edge(nodeNum, nodeNum+width, 0, true));
				}
				
			}
		}
		
		for (Edge e: g.edges){	
			e.setWeight((int) (Math.random() * width*height));

		}
		
		return g;
		
	}
	
	/**
	 * subtracts a subgraph (b) from graph a
	 */
	public static Graph subtract(Graph a, Graph b){
		Graph g = new Graph();
		
		for (Edge aEdge: a.edges){
			
			if (!b.edges.contains(aEdge)){
				g.addEdge(aEdge);
			}

		}
		
		return g;
	}
	



	/**
	 * An implementation of prim's algorithm for random maze generation
	 * @param g a bidirectional, connected graph with random edge weights
	 * @return minSpan the minimum spanning tree of the graph
	 */
	public static Graph prim(Graph g){
	
		Graph minSpan = new Graph();
	
		minSpan.addNode(g.nodes.get((int)(Math.random()* g.nodes.size())));
		
		while (g.nodes.size()!=minSpan.nodes.size()){
			
			
			ArrayList<Edge> choices = new ArrayList<Edge>();
			
			for (int node: minSpan.nodes){
				for (Edge edge: g.edges){
					if ((edge.startsAt(node) && !minSpan.nodes.contains(edge.nodeB)) || (edge.finishesAt(node) && !minSpan.nodes.contains(edge.nodeA))){
						choices.add(edge);
					}
				}
			}
			
			Collections.sort(choices, new Comparator<Edge>(){
	
				@Override
				public int compare(Edge o1, Edge o2) {
					
					return o1.weight - o2.weight;
				}
			});
	
			g.edges.remove(choices.get(0));
			minSpan.addEdge(choices.get(0));
	
		
		
		
		}
		
		return minSpan;
	
	}
	/**
	 * An implementation of kruskal's algorithm for random maze generation
	 * @param g a bidirectional, connected graph with random edge weights
	 * @return minSpan the minimum spanning tree of the graph
	 */
	public static Graph kruskal(Graph g){
		
		Graph minSpan = new Graph();
		
		ArrayList<Graph> subGraphs = new ArrayList<Graph>();
		
		
		ArrayList<Edge> edges = g.edges;
		
		Collections.sort(edges, new Comparator<Edge>(){
	
			@Override
			public int compare(Edge o1, Edge o2) {
				
				return o1.weight - o2.weight;
			}
		});
		
		subGraphs.add(new Graph());
		subGraphs.get(0).addEdge(edges.remove(0));
		
		
		while (g.nodes.size() != minSpan.nodes.size()){
			
			for (Edge e: edges){
				
				boolean connecting = false;
				boolean cycling = false;
				
				for(Graph subGraph: subGraphs){
					
					if (subGraph.nodes.contains(e.nodeA) && subGraph.nodes.contains(e.nodeB)){
						cycling = true;
						break;
					}
					
					if (subGraph.nodes.contains(e.nodeA) || subGraph.nodes.contains(e.nodeB)){
						subGraph.addEdge(e);
						connecting = true;
						break;
					}
				}
				
				if (connecting == false && cycling == false){
					subGraphs.add(new Graph());
					subGraphs.get(subGraphs.size()-1).addEdge(e);
				}
				
				if (connecting == true && cycling == false){
				
					boolean common = false;
					
					for (Graph subGraph: subGraphs){
						for (Graph otherSubGraph: subGraphs){
							if (subGraph.nodes.contains(e.nodeA) && otherSubGraph.nodes.contains(e.nodeB) && subGraph!=otherSubGraph){
								common = true;
								
								for (Edge edg: otherSubGraph.edges){
									subGraph.addEdge(edg);
								}
								
								subGraphs.remove(otherSubGraph);
								break;
							}
						}
						
						if (common){
							break;
						}
						
						
					}
				}
				
				
				if (subGraphs.size() == 1){
					minSpan = subGraphs.get(0);
				}
				
			
				
				
				
				
				
			}
			
			
		}
		
		return minSpan;
	}
	
	/**
	 * Recursively solves a minimum spanning tree, giving the graph of the shortest path between two nodes
	 * @param g the minimum spanning tree to solve
	 * @param solution and empty graph
	 * @param currentNode start at 0
	 * @param finalNode end at the highest node number
	 * @return the solution graph
	 */
	public static Graph RecursiveDepthSolve(Graph g, Graph solution, int currentNode, int finalNode){
		
		
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
		
		ArrayList<Graph> solutions = new ArrayList<Graph>();
		
		for (Edge choice: choices){
			
			Graph solutionGraph = solution.copy();
			solutionGraph.addEdge(choice);
			
			Graph end = RecursiveDepthSolve(g, solutionGraph, (choice.nodeA != currentNode) ? choice.nodeA: choice.nodeB, finalNode);
			
			if (end.edges.size()!=0){
				solutions.add(end);
			}
		}
		
		solutions.sort(new Comparator<Graph>(){

			@Override
			public int compare(Graph o1, Graph o2) {
				
				if(o1.edges.size() == 0){
					return -1;
				}
				
				if (o2.edges.size() == 0){
					return 1;
				}
				
				return o1.edges.size() - o2.edges.size();
			}
			
		});
		
		if (solutions.size() != 0){
			return solutions.get(0);
		}
		


		
		return new Graph();

		
		
		
		
		
	}
}
