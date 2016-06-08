package algorithm;

public class GraphGenerator {

	
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
	 * subtracts a subgraph of a (b) from the graph a
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
}
