package screen;

import graphics.Shader;
import graphics.Texture;
import main.Renderable;
import math.Matrix4f;
import math.Vector3f;

public class Tile extends Renderable{
	
	private float width;
	private float length;

	public Tile(float width, float length, Vector3f location) {
		super(location);
		
		this.length = length;
		this.width = width;
		
		build();
		generate();
		
		
	}

	@Override
	protected void defineVertices() {
		
		vertices = new float []{
				
				location.x - width/2.0f, location.y, location.z - length/2.0f, 		// left-top-far			0
				location.x - width/2.0f, location.y, location.z + length/2.0f, 		// left-top-near 		1
				location.x + width/2.0f, location.y, location.z + length/2.0f,		// right-top-near		2
				location.x + width/2.0f, location.y, location.z - length/2.0f,		// right-top-far		3
				
		};
		
	}

	@Override
	protected void defineTexture() {
		
		texture = new Texture("res/stone.png");
		
		textureCoordinates = new float[]{
				
				0.0f, 0.0f,
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f,
				
		};
		
	}

	@Override
	protected void defineShader() {
		
		shader = Shader.WORLD;
		
	}

	@Override
	protected void defineIndices() {
		
		indices = new byte[]{
				
				0, 1, 2,
				2, 3, 0,
		};
		
	}
	
	public Tile rotated(Matrix4f rotation){
		
		if (vertexArray==null){ 
			System.err.println("build and generate first before rotating!");
		}
		
//		System.out.println("original");
//		for (int i = 0; i< 4;i++){
//
//			System.out.println(vertices[i*3]+" "+vertices[i*3+1]+" "+vertices[i*3+2]);
//		}
		
		Vector3f [] vectorVertices = new Vector3f [vertices.length/3];
		
		for (int i = 0; i< 4;i++){
			
			
			
			vectorVertices[i] = rotation.multiply(new Vector3f(this.vertices[i*3] - location.x, this.vertices[i*3+1]-location.y, this.vertices[i*3+2] -location.z));
		}
		
		for (int i=0;i<4;i++){
			
			
			this.vertices[3*i] = vectorVertices[i].x + location.x; 
			this.vertices[3*i+1] = vectorVertices[i].y + location.y; 
			this.vertices[3*i+2] = vectorVertices[i].z + location.z; 
		}
		
		generate();
		
//		System.out.println("after");
//		
//		for (int i = 0; i< 4;i++){
//			
//			
//			System.out.println(vertices[i*3]+" "+vertices[i*3+1]+" "+vertices[i*3+2]);
//		}
		
		return this;
	}
	
	

}
