package screen;

import graphics.Shader;
import graphics.Texture;
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
			location.x, location.y, location.z, 					// left-top-near
			location.x + length, location.y, location.z,			// right-top-near
			location.x, location.y - length, location.z,			// left-bottom-near
			location.x + length, location.y - length, location.z,	// right-bottom-near
			
			
			location.x, location.y, location.z-length, 						// left-top-far
			location.x + length, location.y, location.z-length,				// right-top-far
			location.x, location.y - length, location.z-length,				// left-bottom-far
			location.x + length, location.y - length, location.z-length,	// right-bottom-far

		};
		
		
	}

	@Override
	protected void defineTexture() {
		texture = new Texture("res/white.jpg");
		
		textureCoordinates = new float []{
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f,
				
		};
		
	}

	@Override
	protected void defineShader() {
		shader = Shader.TILE;
		
	}
	
	@Override
	protected void defineIndices(){
		indices = new byte[]{
				
				0, 1, 3, //near face
				3, 2, 0,
				
				5, 4, 6, //far face
				6, 7, 5,
				
				5, 4, 1, //top face
				1, 0, 4,
				
				2, 3, 7, //bottom face
				7, 6, 2,
				
				1, 5, 7, //right face
				7, 3, 1,
				
				4, 0, 2, //left face
				2, 6, 4,
				
		};
	}
	

}
