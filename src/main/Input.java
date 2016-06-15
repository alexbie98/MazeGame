package main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input {
	
	public static boolean [] keys = new boolean [6556];
	
	public static GLFWKeyCallback controls = new GLFWKeyCallback() {
		
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			
			
			if (action == GLFW.GLFW_PRESS){
				keys[key] = true;
			}
			
			else if (action ==GLFW.GLFW_RELEASE){
				keys[key] = false;
			}


		}
	};

}
