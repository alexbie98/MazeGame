package algorithm;

import java.util.ArrayList;

import main.Renderable;
import math.Vector3f;

public class BoxCollision {
	
	private BoxCollision(){}

	public static boolean checkCollision(Renderable a, Renderable b){
		
		float [] aBox = createBoundBox(a);
		float [] bBox = createBoundBox(b);
		
		return compareBoundingBox(aBox, bBox);

	}
	
	public static boolean checkCollision(Renderable a, float [] bBox){
		
		float [] aBox = createBoundBox(a);
		
		return compareBoundingBox(aBox, bBox);
	}
	
	public static boolean checkCollision(float [] aBox, float [] bBox){
		
		return compareBoundingBox(aBox, bBox);
	}
	
	private static boolean compareBoundingBox(float [] aBox, float [] bBox){
		
		if (bBox[1] < aBox[0] && bBox[0] > aBox[1]){ //b xMin less than a xMax and b xMax greater than a xMin
			
			if (bBox[3] < aBox[2] && bBox[2] > aBox[3]){ //b yMin less than a yMax and b yMax greater than a yMin
				
				if (bBox[5] < aBox[4] && bBox[4] > aBox[5]){ //b zMin less than a zMax and b zMax greater than a zMin
					
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public static Vector3f getOpenDirection(float [] aBox, float [] bBox){
		
		Vector3f openDirection = new Vector3f();
		
		if (bBox[0] > aBox[0] && bBox[1] > aBox[1]){
			openDirection.x++;
		}
		
		else if (bBox[0] < aBox[0] && bBox[1] < aBox[1]){
			openDirection.x--;
		}
		
		if (bBox[4] > aBox[4] && bBox[5] > aBox[5]){
			openDirection.z++;
		}
		
		else if (bBox[4] < aBox[4] && bBox[5] < aBox[5]){
			openDirection.z--;
		}
		
		return openDirection;
	}
	
	
	public static float [] createBoundBox(Renderable r){
		
		//[xMax] [xMin] [yMax] [yMin] [zMax] [zMin]
		
		float [] boundBox = {r.vertices[0], r.vertices[0], r.vertices[1], r.vertices[1], r.vertices[2], r.vertices[2]};
		
		
		for (int i =0; i< r.vertices.length;i++){
			
			
			if (i%3 == 0){
				
				if (r.vertices[i]>boundBox[0]){ // larger than max
					boundBox[0] = r.vertices[i];
				}
				
				else if (r.vertices[i]<boundBox[1]){ // smaller than min
					boundBox[1] = r.vertices[i];
				}
			}
			
			else if (i%3 == 1){
				
				if (r.vertices[i]>boundBox[2]){ // larger than max
					boundBox[2] = r.vertices[i];
				}
				
				else if (r.vertices[i]<boundBox[3]){ // smaller than min
					boundBox[3] = r.vertices[i];
				}
				
			}
			
			else if (i%3 == 2){
				
				if (r.vertices[i]>boundBox[4]){ // larger than max
					boundBox[4] = r.vertices[i];
				}
				
				else if (r.vertices[i]<boundBox[5]){ // smaller than min
					boundBox[5] = r.vertices[i];
				}
				
			}
			
		}
		
		return boundBox;
		
	}
	
	public static float [] sumBoundBoxes(ArrayList<Renderable> renderables){
		
		
		float [] boundBox = createBoundBox(renderables.get(0));
		
		for (int i = 1; i<renderables.size();i++){
			
			float [] box = createBoundBox(renderables.get(i));
			
			for (int j=0; j<6;j++){
				
				if (j%2 == 0){
					if (box[j]> boundBox[j]){
						boundBox[j] = box[j];
					}
				}
				
				if (j%2 == 1){
					if (box[j] < boundBox[j]){
						boundBox[j] = box[j];
					}
				}
				
			}
			
			
		}
		
		return boundBox;
		
		
		
	}
	
	
	
	
}
