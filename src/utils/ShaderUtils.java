package utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
	
	// Prevent instance of static class
	private ShaderUtils(){
	}
	
	public static int load(String vertPath, String fragPath){
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	public static int create(String vert, String frag){
		int program = glCreateProgram();
		int vID = glCreateShader(GL_VERTEX_SHADER);
		int fID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vID, vert);
		glShaderSource(fID, frag);
		
		glCompileShader(vID);
		if(glGetShaderi(vID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vID));
			return -1;
		}
		
		glCompileShader(fID);
		if(glGetShaderi(fID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(vID));
			return -1;
		}
		
		glAttachShader(program, vID);
		glAttachShader(program, fID);
		glLinkProgram(program);
		glValidateProgram(program);
		
		glDeleteShader(vID);
		glDeleteShader(fID);
		
		return program;
	}
}
