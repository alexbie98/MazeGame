package main;

public class Main {

	public static void main(String[] args) {
		
		
		Thread gameThread = new Thread(new GameThread());
		gameThread.start();

	}

}
