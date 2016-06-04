package states;

import graphics.Shader;
import main.GameThread;
import main.Renderer;
import math.Matrix4f;
import math.Vector3f;
import screen.Box;
import screen.Player;

public class Test extends GameState{
	
	private float SCREEN_RATIO = (float)GameThread.HEIGHT/ (float)GameThread.WIDTH;
	
	private Matrix4f pr_matrix = Matrix4f.orthographic(10.0f, -10.0f, 10.0f*SCREEN_RATIO, -10.0f*SCREEN_RATIO, 10.0f, -10.0f);
	private Matrix4f vw_matrix = Matrix4f.rotate(0, 35.254f).multiply(Matrix4f.rotate(1,-35.0f));

	@Override
	public void onStart() {
		
		Shader.WORLD.enable();
		Shader.WORLD.setUniform4f("pr_matrix", pr_matrix);
		Shader.WORLD.setUniform4f("vw_matrix", vw_matrix);
		Shader.WORLD.setUniform1i("tex", 1);
		Shader.WORLD.disable();
		
		
		Box box = new Box(2.0f, new Vector3f(0f, 0f, 0f));
		box.build();
		box.generate();
		
		addRenderable(box);
		
		addUpdateable(new Player(new Vector3f(0.0f,0.0f,0.0f)));
		
		
	}

	@Override
	public void onExit() {
		
		
	}

}
