package math;

public class Vector3f {
	
	public float x, y, z;
	
	public Vector3f(){
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * returns a string in form [Vector3f][x][y][z]
	 */
	public String toString(){
		return "[Vector3f][x="+this.x+"][y="+this.y+"][z="+this.z+"]";
	}
	
	/**
	 * checks if the vector is equal to another one
	 */
	public boolean equals(Vector3f v){
		if(v.x == this.x && v.x == this.x && v.x == this.x){
			return true;
		}
		return false;
	}
	
	public Vector3f normalized(){
		float magnitude = getMagnitude();
		
		this.x = this.x/magnitude;
		this.y = this.y/magnitude;
		this.z = this.z/magnitude;
		
		return this;
	}
	
	public float getMagnitude(){
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
	}
	
	/**
	 * subtracts vector a from vector b
	 */
	public static Vector3f subtract(Vector3f a, Vector3f b){
		Vector3f result = new Vector3f();
		
		result.x = b.x-a.x;
		result.y = b.y-a.y;
		result.z = b.z-a.z;
		
		return result;
	}
	
	public static Vector3f add(Vector3f a, Vector3f b){
		Vector3f result = new Vector3f();
		result.x = a.x + b.x;
		result.y = a.y + b.y;
		result.z = a.z + b.z;
		return result;
	}
	
	/**
	 * scalar multiplication
	 */
	public static Vector3f multiply(Vector3f a, float scalar){
		Vector3f result = new Vector3f();
		result.x = a.x * scalar;
		result.y = a.y * scalar;
		result.z = a.z * scalar;
		return result;
	}
	

}
