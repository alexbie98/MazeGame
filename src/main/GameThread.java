package main;

import java.awt.Font;

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
import screen.Renderer;

public class GameThread implements Runnable{
	
	
	private long window;
	private long monitor;
	
	
	

	private int WIDTH = 1280, HEIGHT = 720;
	
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
		
		GLFW.glfwInit();
		
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
		
		GLFW.glfwSwapInterval(1);
		
		GLFW.glfwShowWindow(window);
		
		GL.createCapabilities();
		
		Shader.loadAll();
		
		
		System.out.println("Running LWJGL Version "+ Version.getVersion()+"!");
		System.out.println("Running OpenGL Version "+GL11.glGetString(GL11.GL_VERSION)+"!");
		
		
		Box box = new Box(5.0f, new Vector3f(0.0f, 0.0f, 0.0f));
		
		Shader.TILE.enable();
		Shader.TILE.setUniform4f("pr_matrix", Matrix4f.orthographic(10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f));
		Shader.TILE.setUniform4f("vw_matrix", Matrix4f.identity());
		Shader.TILE.disable();
		
		Renderer.addRenderable(box);
		

	}
	
	private void update(){

		GLFW.glfwPollEvents();
		
		

	}
	
	private void render(){
		GL.createCapabilities();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glClearColor(0.2f, 0.4f, 0.8f, 1.0f);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(0.5f, 0);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glVertex2f(0, -0.5f);
		GL11.glEnd();
		
		Renderer.render();
		
		GLFW.glfwSwapBuffers(window);
		
	}

}
