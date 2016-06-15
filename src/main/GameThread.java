package main;


import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import graphics.Shader;
import math.Matrix4f;
import math.Vector3f;
import screen.Box;

public class GameThread implements Runnable{
	
	
	private long window;
	private long monitor;
	

	public static final int WIDTH = 1280, HEIGHT = 720;
	

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		init();
		
		
		while(true){
			
			if (GLFW.glfwWindowShouldClose(window) == GLFW.GLFW_TRUE){
				GLFW.glfwDestroyWindow(window);
				break;
			}
			
			update();
			render();
			
			
		}
		
		
		
	}
	
	private void init(){
		
		//setup error callback
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(glfwInit() == GL_FALSE)
		{
			// TODO: Handle error
		}
		
		monitor = GLFW.glfwGetPrimaryMonitor();
		
//		window = GLFW.glfwCreateWindow(GLFW.glfwGetVideoMode(monitor).width(), GLFW.glfwGetVideoMode(monitor).height(), "MazeGame", monitor, 0);
		
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "MazeGame", 0, 0);
		
		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
					GLFW.glfwSetWindowShouldClose(window, GLFW.GLFW_TRUE);
					
					
				}
				
				
			}
		});
		
		
		
		
		
		
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GLFW.glfwShowWindow(window);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		//enable textures
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		
		
		
		GL11.glClearColor(0.2f, 0.4f, 0.8f, 1.0f);
		
		
		
		System.out.println("Running LWJGL Version "+ Version.getVersion()+"!");
		System.out.println("Running OpenGL Version "+GL11.glGetString(GL11.GL_VERSION)+"!");
		
		Shader.loadAll();
		
		GameStateManager.setWindow(window);
		GameStateManager.goTo(GameStateManager.Test);
		
		
		
		

	}
	
	private void update(){

		GLFW.glfwPollEvents();
		
		GameStateManager.update();
		
		

	}
	
	private void render(){
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GameStateManager.render();
		
		GLFW.glfwSwapBuffers(window);
		
	}

}
