package screen;

import graphics.Shader;
import graphics.Texture;
import main.Renderable;
import math.Matrix4f;
import math.Vector3f;

public class Wall extends Renderable{

	public static final float THICK = 1.0f/8.0f;
	
	public float size;
	
	public Wall(float size, Vector3f location) {
		super(location);
		
		this.size = size;
		
		build();
		generate();
	}

	@Override
	protected void defineVertices() {
		
		vertices = new float []{
		
		//front face
		location.x, location.y, location.z, 								// left-top-near 		0
		location.x, location.y - size, location.z,							// left-bottom-near		1
		location.x + size, location.y - size, location.z,					// right-bottom-near	2
		location.x + size, location.y, location.z,							// right-top-near		3
		
		//back face
		location.x + size, location.y, location.z-size*THICK,				// right-top-far		4
		location.x + size, location.y - size, location.z-size*THICK,		// right-bottom-far		5
		location.x, location.y - size, location.z-size*THICK,				// left-bottom-far		6
		location.x, location.y, location.z-size*THICK, 						// left-top-far			7
		
		//left face
		location.x, location.y, location.z-size*THICK, 						// left-top-far			8
		location.x, location.y - size, location.z-size*THICK,				// left-bottom-far		9
		location.x, location.y - size, location.z,							// left-bottom-near		10
		location.x, location.y, location.z, 								// left-top-near		11
		
		//right face
		location.x + size, location.y, location.z,							// right-top-near		12
		location.x + size, location.y - size, location.z,					// right-bottom-near	13
		location.x + size, location.y - size, location.z-size*THICK,		// right-bottom-far		14
		location.x + size, location.y, location.z-size*THICK,				// right-top-far		15
		
		//bottom face
		location.x, location.y - size, location.z,							// left-bottom-near		16
		location.x, location.y - size, location.z-size*THICK,				// left-bottom-far		17
		location.x + size, location.y - size, location.z-size*THICK,		// right-bottom-far		18
		location.x + size, location.y - size, location.z,					// right-bottom-near	19
		
		//top face
		location.x, location.y, location.z-size*THICK, 						// left-top-far			20
		location.x, location.y, location.z, 								// left-top-near 		21
		location.x + size, location.y, location.z,							// right-top-near		22
		location.x + size, location.y, location.z-size*THICK,				// right-top-far		23
		
		};
		
	}

	@Override
	protected void defineTexture() {
		texture = new Texture("res/sandstone.jpg");
		

		float yrate = 1.0f/18.0f;
		float xrate = 1.0f/16.0f;
		
		textureCoordinates = new float []{
				
				// [back] [front] [left] [right] [top] [bottom]
				
				0.0f, 2.0f * yrate,					
				0.0f, 18.0f * yrate,
				1.0f, 18.0f * yrate,
				1.0f, 2.0f * yrate,
				
				0.0f, 2.0f * yrate,					
				0.0f, 18.0f * yrate,
				1.0f, 18.0f * yrate,
				1.0f, 2.0f * yrate,
				
				0.0f, 2.0f * yrate,
				1.0f, 2.0f * yrate,
				1.0f, 0.0f,
				0.0f, 0.0f,
				
				0.0f, 2.0f * yrate,
				0.0f, 18.0f * yrate,
				2.0f * xrate, 18.0f * yrate,
				2.0f * xrate, 2.0f * yrate,

				0.0f, 2.0f * yrate,
				0.0f, 18.0f * yrate,
				2.0f * xrate, 18.0f * yrate,
				2.0f * xrate, 2.0f * yrate,
				
				0.0f, 0.0f,
				0.0f, 2.0f*yrate,
				1.0f, 2.0f*yrate,
				1.0f, 0.0f,

				
		};
		
	
		
	}

	@Override
	protected void defineShader() {
		shader = Shader.WORLD;
		
	}

	@Override
	protected void defineIndices(){
		indices = new byte[]{
				
			0, 1, 2, //near face
			2, 3, 0,
			
			4, 5, 6, //far face
			6, 7, 4,
			
			8, 9, 10, //left face
			10, 11, 8,
			
			12, 13, 14, //right face
			14, 15, 12,
			
			16, 17, 18, //bottom face
			18, 19, 16,
			
			20, 21, 22, //top face
			22, 23, 20,
				
		};
	}
	
	public Wall rotated(Matrix4f rotation){
		
		
//		System.out.println("original");
//		for (int i = 0; i< 24;i++){
//
//			System.out.println(vertices[i*3]+" "+vertices[i*3+1]+" "+vertices[i*3+2]);
//		}
		
		Vector3f [] vectorVertices = new Vector3f [24];
		
		for (int i = 0; i< 24;i++){
			
			
			
			vectorVertices[i] = rotation.multiply(new Vector3f(this.vertices[i*3] - location.x, this.vertices[i*3+1]-location.y, this.vertices[i*3+2] -location.z));
		}
		
		for (int i=0;i<24;i++){
			
			
			this.vertices[3*i] = vectorVertices[i].x + location.x; 
			this.vertices[3*i+1] = vectorVertices[i].y + location.y; 
			this.vertices[3*i+2] = vectorVertices[i].z + location.z; 
		}
		
		generate();
		
//		System.out.println("after");
//		
//		for (int i = 0; i< 24;i++){
//			
//			
//			System.out.println(vertices[i*3]+" "+vertices[i*3+1]+" "+vertices[i*3+2]);
//		}
		
		return this;
		
		
		

	}

}
