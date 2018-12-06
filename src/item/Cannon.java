package item;

import bullet.Bullet;
import bullet.CannonBall;

public class Cannon extends SingleShotWeapon {
	
	private static final int DAMAGE = 50;
	private static final int BULLET_SPEED = 5;

	public Cannon() {
		super("cannon");
		this.bullet = new CannonBall(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
	}

	public Cannon(int x, int y, int ammo) {
		super("cannon", x, y, ammo);
		this.bullet = new CannonBall(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
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
