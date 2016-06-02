package math;

import java.nio.FloatBuffer;

import utils.BufferUtils;

public class Matrix4f {
	
public float[] elements = new float[4 * 4];
	
	public final static int ROTATION_AXIS_X = 0;
	public final static int ROTATION_AXIS_Y = 1;
	public final static int ROTATION_AXIS_Z = 2;
	
	
	public Matrix4f(){}
	

	public static Matrix4f identity(){
		
		Matrix4f result = new Matrix4f();
		for (int i=0;i<4*4;i++){
			result.elements[i]=0.0f;
		}
		result.elements[0*4+0]=1.0f;
		result.elements[1*4+1]=1.0f;
		result.elements[2*4+2]=1.0f;
		result.elements[3*4+3]=1.0f;
		return result;
	}
	
	public static Matrix4f scale(float factor){
		Matrix4f result = identity();
		
		result.elements[0]=factor;
		result.elements[5]=factor;
		result.elements[10]=factor;
		
		return result;
				
	}
	
	public static Matrix4f translate(Vector3f vector){
		Matrix4f result = identity();
		
		result.elements[12]=vector.x;
		result.elements[13]=vector.y;
		result.elements[14]=vector.z;
		
		return result;
				
	}
	
	public static Matrix4f rotate(int axis, float angleInDegrees){
		Matrix4f result = identity();
		double angleInRadians = Math.toRadians(angleInDegrees);
		
		
		if (axis==0){
			result.elements[5] = (float) Math.cos(angleInRadians);
			result.elements[6] = (float) Math.sin(angleInRadians);
			result.elements[9] = (float) (Math.sin(angleInRadians)*-1);
			result.elements[10] = (float) Math.cos(angleInRadians);
		}
		
		if (axis==1){
			result.elements[0] = (float) Math.cos(angleInRadians);
			result.elements[2] = (float) (Math.sin(angleInRadians)*-1);
			result.elements[8] = (float) Math.sin(angleInRadians);
			result.elements[10] = (float) Math.cos(angleInRadians);
		}
		
		if (axis==2){
			result.elements[0] = (float) Math.cos(angleInRadians);
			result.elements[1] = (float) Math.sin(angleInRadians);
			result.elements[4] = (float) (Math.sin(angleInRadians)*-1);
			result.elements[5] = (float) Math.cos(angleInRadians);
		}
		
		return result;
		
			
	}
	
	
	public static Matrix4f orthographic(float right, float left, float top, float bottom, float far, float near){
		Matrix4f result = identity();
		
		result.elements[0] = 2.0f/(right-left);
		result.elements[5] = 2.0f/(top-bottom);
		result.elements[10] = -2.0f/(far-near);
		result.elements[12] = -(right+left)/(right-left);
		result.elements[13] = -(top+bottom)/(top-bottom);
		result.elements[14] = -(far+near)/(far-near);
		
		return result;
		
	}
	
	// source: http://www.geeks3d.com/20090729/howto-perspective-projection-matrix-in-opengl/
	
	// fov: Field of view, pi/4 radians is a good value
	// aspect: Ratio of height to width
	// znear, zfar: used for clipping
	
	public static Matrix4f perspective(float fov, float aspect, float znear, float zfar){
		Matrix4f result = identity();
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix){
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x+e*4] * matrix.elements[e+y*4];
				}
				result.elements[x+y*4] = sum;
			}
		}
		return result;
		
	}
	
	public Matrix4f multiply(float value){
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				result.elements[4*y + x] = value * this.elements[4*y + x];

			}
		}
		return result;
		
	}
	
	
	
	public Vector3f multiply (Vector3f position){
		
		Vector3f result = new Vector3f();
		result.x = elements[0]*position.x + elements[4]*position.y + elements[8]*position.z + elements[12]*1.0f;
		result.y = elements[1]*position.x + elements[5]*position.y + elements[9]*position.z + elements[13]*1.0f;
		result.z = elements[2]*position.x + elements[5]*position.y + elements[10]*position.z + elements[14]*1.0f;

		return result;
	}
	
	public FloatBuffer toFloatBuffer(){
		return BufferUtils.createFloatBuffer(elements);
	}

}
