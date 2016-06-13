package screen;

import graphics.Shader;
import graphics.Texture;
import main.Renderable;
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
				
				location.x, location.y, location.z, 					// left-top-far			0
				location.x, location.y, location.z+length, 				// left-top-near 		1
				location.x + width, location.y, location.z+length,		// right-top-near		2
				location.x + width, location.y, location.z,				// right-top-far		3
				
		};
		
	}

	@Override
	protected void defineTexture() {
		
		texture = new Texture("res/stone.png");
		
		textureCoordinates = new float[]{
				
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f,
				0.0f, 1.0f,
				
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
	
	

}
