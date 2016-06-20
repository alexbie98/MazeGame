package gui;

import graphics.Texture;
import main.Updateable;
import math.Vector3f;

public class TitleLabel extends Label implements Updateable{
	
	
	public float width;
	public float height;
	
	public int tick = 0;
	public int frame = 0;
	
	private static final float Y_TEX_RATE  = 1.0f / 2980.0f;

	public TitleLabel(Vector3f location, float width, float height, Texture texture) {
		super(location, width, height, texture);

	}



	@Override
	protected void defineTexture() {
		
		updateTextureCoords();
		
		
	}


	@Override
	public void update() {
		
		
		
		updateTextureCoords();
		generate();
		
		tick++;
		
	}

	private void updateTextureCoords() {
		
		if (tick%3 == 0){
			
			textureCoordinates = new float [] {
					
					0.0f, 149.0f * frame*Y_TEX_RATE,
					0.0f, 149.0f * (frame+1)*Y_TEX_RATE,
					1.0f, 149.0f * (frame+1)*Y_TEX_RATE,
					1.0f, 149.0f * frame*Y_TEX_RATE,
					
			};
			
			frame++;
		}
		
		
		
		
	}



	@Override
	public boolean isUpdatePaused() {
		// TODO Auto-generated method stub
		return false;
	}


}
