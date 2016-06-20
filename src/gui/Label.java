package gui;

import graphics.Shader;
import graphics.Texture;
import main.Renderable;
import main.Updateable;
import math.Vector3f;

public class Label extends Renderable{
	
	
	public float width;
	public float height;
	
	public int tick = 0;


	public Label(Vector3f location, float width, float height, Texture texture) {
		super(location);
		
		this.width = width;
		this.height = height;
		this.texture = texture;
		
		build();
		generate();
	}

	@Override
	protected void defineVertices() {
		
		vertices = new float []{
				location.x, location.y, location.z,
				
				location.x, location.y - height, location.z,
				
				location.x + width, location.y - height, location.z,
				
				location.x + width, location.y, location.z
		};
		
		
		
		
	}

	@Override
	protected void defineTexture() {
		
		
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

}
