package screen;

import graphics.Shader;
import graphics.Texture;
import main.Renderable;
import math.Matrix4f;
import math.Vector3f;

public class Box extends Renderable{
	
	public float length = 0;

	public Box(float length, Vector3f location) {
		super(location);
		this.length = length;

		build();
		generate();
	}

	@Override
	protected void defineVertices() {
		
		vertices = new float []{
			
			//front face
			location.x, location.y, location.z, 							// left-top-near 		0
			location.x, location.y - length, location.z,					// left-bottom-near		1
			location.x + length, location.y - length, location.z,			// right-bottom-near	2
			location.x + length, location.y, location.z,					// right-top-near		3
			
			//back face
			location.x + length, location.y, location.z+length,				// right-top-far		4
			location.x + length, location.y - length, location.z+length,	// right-bottom-far		5
			location.x, location.y - length, location.z+length,				// left-bottom-far		6
			location.x, location.y, location.z+length, 						// left-top-far			7
			
			//left face
			location.x, location.y, location.z+length, 						// left-top-far			8
			location.x, location.y - length, location.z+length,				// left-bottom-far		9
			location.x, location.y - length, location.z,					// left-bottom-near		10
			location.x, location.y, location.z, 							// left-top-near		11
			
			//right face
			location.x + length, location.y, location.z,					// right-top-near		12
			location.x + length, location.y - length, location.z,			// right-bottom-near	13
			location.x + length, location.y - length, location.z+length,	// right-bottom-far		14
			location.x + length, location.y, location.z+length,				// right-top-far		15
			
			//bottom face
			location.x, location.y - length, location.z,					// left-bottom-near		16
			location.x, location.y - length, location.z+length,				// left-bottom-far		17
			location.x + length, location.y - length, location.z+length,	// right-bottom-far		18
			location.x + length, location.y - length, location.z,			// right-bottom-near	19
			
			//top face
			location.x, location.y, location.z+length, 						// left-top-far			20
			location.x, location.y, location.z, 							// left-top-near 		21
			location.x + length, location.y, location.z,					// right-top-near		22
			location.x + length, location.y, location.z+length,				// right-top-far		23
			

		};
		
		
	}

	@Override
	protected void defineTexture() {
		texture = new Texture("res/stone.png");
		
		textureCoordinates = new float []{
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				0.0f, 1.0f,
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				
				
				
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
	

}
