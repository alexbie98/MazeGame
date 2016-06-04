package algorithm;

public class Edge {
	
	public int nodeA;
	
	public int nodeB;
	
	public int weight;
	
	public boolean biDirectional;
	
	
	public Edge(int nodeA, int nodeB, int weight, boolean biDirectional){
		
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.weight = weight;
		this.biDirectional = biDirectional;
	}
	
	public boolean startsAt(int node){
		

		if (node == nodeA){
			return true;
		}

		
		return false;
	}
	
	public boolean finishesAt(int node){
		
	
		if (node == nodeB){
			return true;
		}
		
		return false;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	
	
	public String toString(){
		return "[Edge "+nodeA+"-"+nodeB+"]"+"[weight: "+weight+"]";
	}

}
