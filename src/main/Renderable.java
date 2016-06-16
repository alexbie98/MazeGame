package main;

import graphics.Shader;

import graphics.Texture;
import graphics.VertexArray;
import math.Vector3f;

public abstract class Renderable {
	
	public final static int ROTATION_AXIS_X = 0;
	public final static int ROTATION_AXIS_Y = 1;
	public final static int ROTATION_AXIS_Z = 2;
	
	
	protected VertexArray vertexArray;
	public Texture texture;
	public Shader shader;
	
	
	public float [] vertices;
	protected byte [] indices;
	protected float [] textureCoordinates; 
	
	public Vector3f location;
	
	public Renderable(Vector3f location){
		
		this.location = location;
				
	}
	
	protected abstract void defineVertices();

	protected abstract void defineTexture();
	
	protected abstract void defineShader();
	
	protected abstract void defineIndices();
	
	public void build(){
		defineVertices();
		defineTexture();
		defineShader();
		defineIndices();
	}
	
	public void generate(){
		vertexArray = new VertexArray(vertices, indices, textureCoordinates);

	}

	public void render(){

		vertexArray.bind();
		vertexArray.draw();
		vertexArray.unbind();
	
	}
	



}
