package character;

import java.util.Random;

import main.GameScene;
import util.Coord;

public class Spawner {

	private int delay;
	private int time;
	private Object gameObject;
	private Coord spawnTopLeft;
	private Coord spawnBottomRight;
	private Coord spawnPosition;
	private boolean randomSpawn;

	public Spawner(Object gameObject, int delay, Coord position) {
		this.gameObject = gameObject;
		this.spawnPosition = position;
		this.delay = delay;
		this.time = 0;
		this.randomSpawn = false;
	}
	
	public Spawner(Object gameObject, int delay, Coord topLeft, Coord bottomRight) {
		this.gameObject = gameObject;
		this.delay = delay;
		this.time = 0;
		this.spawnPosition = new Coord();
		this.spawnTopLeft = topLeft;
		this.spawnBottomRight = bottomRight;
		this.randomSpawn = true;
	}

	public void spawn() {
		if (randomSpawn) {
			Random rand = new Random();
			int x = rand.nextInt((int) spawnBottomRight.getX() - (int) spawnTopLeft.getX()) + (int) spawnTopLeft.getX();
			int y = rand.nextInt((int) spawnBottomRight.getY() - (int) spawnTopLeft.getY()) + (int) spawnTopLeft.getY();
			spawnPosition.setXY(x, y);
		}
		
		if (gameObject instanceof Enemy) {
			GameScene.getEnemyList().add(new Enemy((int) spawnPosition.getX(), (int) spawnPosition.getY()));
		}
	}

	public void update() {
		time++;
		if (time > delay) {
			time = 0;
			spawn();
		}
	}

}
