package item;

import bullet.Bullet;
import bullet.CannonBall;

public class Cannon extends SingleShotWeapon {

	public Cannon(int x, int y, int ammo) {
		super("cannon", x, y, ammo);
		this.bullet = new CannonBall(5, 50);
		this.fireRate = 30;
		this.reloadSize = 5;
		this.reloadCost = 10;
	}
}
