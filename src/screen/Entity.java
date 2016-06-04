package screen;

import main.Renderable;
import main.Updateable;
import math.Vector3f;

public abstract class Entity extends Renderable implements Updateable{
	
	public boolean paused = false;

	public Entity(Vector3f location) {
		super(location);
		
		build();
		generate();

	}

	@Override
	protected void defineVertices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void defineTexture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void defineShader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void defineIndices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUpdatePaused() {
		return paused;
	}


}
