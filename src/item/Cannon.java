package item;

import bullet.CannonBall;

public class Cannon extends SingleShotWeapon {

	public Cannon(int x, int y, int ammo) {
		super("cannon", x, y, ammo);
		this.bullet = new CannonBall(50, 5);
		this.fireRate = 30;
		this.reloadSize = 5;
		this.reloadCost = 10;
	}

	@Override
	public Bullet shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			return new CannonBall((CannonBall) bullet, Bullet.initailVelocity(bullet.getSpeed()));
		}
		return null;
	}
}
