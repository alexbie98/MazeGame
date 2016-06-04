package algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MinSpan {

	public static void main(String[] args) {
		
		
		Graph minSpan = prim(GraphGenerator.generate());
		
		for (Edge e: minSpan.edges){
			System.out.println(e.toString());
		}
		
		
		
		
		
		

	}
	
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
	
	

}
