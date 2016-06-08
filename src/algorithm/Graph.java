package algorithm;
import java.util.ArrayList;

public class Graph {
	
	public ArrayList<Integer> nodes;
	public ArrayList<Edge> edges;
	
	public Graph(){
		
		nodes = new ArrayList<Integer>();
		edges = new ArrayList<Edge>();
	}
	
	public void addNode(int n){
		this.nodes.add(n);
	}
	
	
	public void addEdge(Edge e){
		
		
		for (Edge edge: edges){
			if (edge.startsAt(e.nodeA) && edge.finishesAt(e.nodeB) || edge.startsAt(e.nodeB) && edge.finishesAt(e.nodeA) ){
				edges.remove(edge);
				break;
			}
		}
		

		this.edges.add(e);
		
		if(!nodes.contains(e.nodeA)){
			nodes.add(e.nodeA);
		}
		
		if (!nodes.contains(e.nodeB)){
			nodes.add(e.nodeB);
		}
		
	}
	
	
	public Graph copy(){
		
		Graph g = new Graph();
		g.edges.addAll(this.edges);
		g.nodes.addAll(this.nodes);
		
		return g;
	}
	


}
