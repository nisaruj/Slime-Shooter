package item;

import bullet.Bullet;
import bullet.Rocket;

public class RocketLauncher extends SingleShotWeapon {

	private static final int DAMAGE = 80;
	private static final int BULLET_SPEED = 20;

	public RocketLauncher() {
		super("rocket");
		this.bullet = new Rocket(BULLET_SPEED, DAMAGE);
		this.fireRate = 50;
	}

	public RocketLauncher(int x, int y, int ammo) {
		super("rocket",x, y, ammo);
		this.bullet = new Rocket(BULLET_SPEED, DAMAGE);
		this.fireRate = 50;
	}
	
	@Override
	public Bullet shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			return new Rocket((Rocket) bullet, Bullet.initailVelocity(bullet.getSpeed()));
		}
		return null;
	}
	
}
