package algorithm;

import main.Renderable;

public class BoxCollision {
	
	private BoxCollision(){}

	public static boolean checkCollision(Renderable a, Renderable b){
		
		float [] aBox = createCornerArray(a);
		float [] bBox = createCornerArray(b);
		
		return compareBoundingBox(aBox, bBox);

	}
	
	public static boolean checkCollision(Renderable a, float [] bBox){
		
		float [] aBox = createCornerArray(a);
		
		return compareBoundingBox(aBox, bBox);
	}
	
	public static boolean checkCollision(float [] aBox, float [] bBox){
		
		return compareBoundingBox(aBox, bBox);
	}
	
	private static boolean compareBoundingBox(float [] aBox, float [] bBox){
		
		if (bBox[1] < aBox[0] && bBox[0] > aBox[1]){ //b xMin less than a xMax and b xMax greater than a xMin
			
			if (bBox[3] < aBox[2] && bBox[2] > aBox[3]){ //b yMin less than a yMax and b yMax greater than a yMin
				
				if (bBox[5] < aBox[4] && bBox[4] < aBox[5]){ //b zMin less than a zMax and b zMax greater than a zMin
					
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	private static float [] createCornerArray(Renderable r){
		
		//[xMax] [xMin] [yMax] [yMin] [zMax] [zMin]
		
		float [] vertices = {r.vertices[0], r.vertices[0], r.vertices[1], r.vertices[1], r.vertices[2], r.vertices[2]};
		
		
		for (int i =0; i< r.vertices.length;i++){
			
			if (i%3 == 0){
				
				if (i>vertices[0]){ // larger than max
					vertices[0] = i;
				}
				
				if (i<vertices[1]){ // smaller than min
					vertices[1] = i;
				}
			}
			
			else if (i%3 == 1){
				
				if (i>vertices[2]){ // larger than max
					vertices[2] = i;
				}
				
				if (i<vertices[3]){ // smaller than min
					vertices[3] = i;
				}
				
			}
			
			else if (i%3 == 2){
				
				if (i>vertices[4]){ // larger than max
					vertices[4] = i;
				}
				
				if (i<vertices[5]){ // smaller than min
					vertices[5] = i;
				}
				
			}
			
		}
		
		return vertices;
		
	}
	
	
	
	
}
