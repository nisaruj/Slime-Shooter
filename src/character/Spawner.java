package character;

import java.util.Random;

import item.DamageMultiply;
import item.HealthBox;
import item.Powerup;
import main.GameScene;
import util.Coord;

public class Spawner {

	private static final int POWERUP_DROP_RATE = 20;
	private int delay;
	private int time;
	private String type;
	private Coord spawnTopLeft;
	private Coord spawnBottomRight;
	private Coord spawnPosition;
	private boolean randomSpawn;

	public Spawner(String type, int delay, Coord position) {
		this.type = type;
		this.spawnPosition = position;
		this.delay = delay;
		this.time = 0;
		this.randomSpawn = false;
	}

	public Spawner(String type, int delay, Coord topLeft, Coord bottomRight) {
		this.type = type;
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

		if (type.equals("enemy")) {
			GameScene.getEnemyList().add(new Enemy((int) spawnPosition.getX(), (int) spawnPosition.getY()));
		} else {
			Powerup p = dropPowerup();
			if (p != null) {
				GameScene.getItemList().add(p);
			}
		}
	}
	
	public Powerup dropPowerup() {
		Random rand = new Random();
		int x = rand.nextInt(100);
		if (x < POWERUP_DROP_RATE) {
			return x < POWERUP_DROP_RATE / 2 ? new HealthBox((int) spawnPosition.getX(), (int) spawnPosition.getY()) : new DamageMultiply((int) spawnPosition.getX(), (int) spawnPosition.getY());
		}
		return null;
	}

	public void update() {
		time++;
		if (time > delay) {
			time = 0;
			spawn();
		}
	}

}
