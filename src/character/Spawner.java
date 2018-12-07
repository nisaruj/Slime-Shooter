package character;

import java.util.Random;

import item.Cannon;
import item.DamageMultiply;
import item.Flamethrower;
import item.HealthBox;
import item.Item;
import item.Matter;
import item.Powerup;
import item.RocketLauncher;
import item.Shotgun;
import item.SingleShotWeapon;
import item.Weapon;
import main.GameScene;
import util.Coord;

public class Spawner {

	private static final int POWERUP_DROP_RATE = 20;
	private static final int MAX_WEAPON_IN_MAP = 6;
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
			} else {
				Weapon weapon = dropWeapon();
				if (weapon != null) {
					GameScene.getItemList().add(weapon);
				}
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
	
	public Weapon dropWeapon() {
		if (countWeaponInMap() >= MAX_WEAPON_IN_MAP) {
			return null;
		}
		Random rand = new Random();
		int x = rand.nextInt(100);
		int xPos = (int) spawnPosition.getX();
		int yPos = (int) spawnPosition.getY();
		if (x < 6) {
			return new SingleShotWeapon("mg", xPos, yPos, 200);
		} else if (x < 8) {
			return new Flamethrower(xPos, yPos, 200);
		} else if (x < 13) {
			return new Matter(xPos, yPos, 50);
		} else if (x < 18) {
			return new Shotgun(xPos, yPos, 7);
		} else if (x < 24) {
			return new Cannon(xPos, yPos, 20);
		} else if (x < 26) {
			return new RocketLauncher(xPos, yPos, 3);
		}
		return null;
	}
	
	private int countWeaponInMap() {
		int c = 0;
		for (Item i : GameScene.getItemList()) {
			if (i instanceof Weapon) {
				c++;
			}
		}
		return c;
	}

	public void update() {
		time++;
		if (time > delay) {
			time = 0;
			spawn();
		}
	}

}
