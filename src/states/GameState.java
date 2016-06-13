package states;

import java.util.ArrayList;

import main.Renderable;
import main.Updateable;

public abstract class GameState {
	
	
	
	private ArrayList<Renderable> renderables = new ArrayList<>();
	private ArrayList<Updateable> updateables = new ArrayList<>();
	
	protected boolean done = false;
	private boolean pausedState = false;
	
	
	public ArrayList<Renderable> getRenderables(){
		
		return renderables;
	}
	
	public void addRenderable(Renderable r){
		
		if (r instanceof Updateable){
			updateables.add((Updateable)r);
		}
		
		renderables.add(r);
	}

	
	public ArrayList<Updateable> getUpdateables(){
		
		return updateables;
	}
	
	public void addUpdateable(Updateable u){
		updateables.add(u);
	}
	
	public void setPausedState(boolean paused){
		pausedState = paused;
	}
	
	public boolean isPausedState(){
		return pausedState;
	}
	
	public ArrayList<Updateable> getUpdateableRenderables(){
		
		ArrayList<Updateable> updateableRenderables = new ArrayList<>();
		
		for (Renderable r: renderables){
			if (r instanceof Updateable){
				updateableRenderables.add((Updateable) r);
			}
		}
		
		return updateableRenderables;
	}
	
	// used by the game state manager
	public boolean isDone(){
		return done;
	}
	
	// used for init and any clean up
	public abstract void onStart();
	public abstract void onExit();
	
	
	
	
	
	

}
