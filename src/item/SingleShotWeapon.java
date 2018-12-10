package item;
import bullet.Bullet;
import bullet.CannonBall;
import bullet.MachineGunBullet;
import bullet.RandomBullet;
import bullet.Rocket;
import bullet.SingleShotBullet;

public class SingleShotWeapon extends Weapon {

	public SingleShotWeapon(String name, int x, int y, int ammo) {
		super(name, x, y, ammo);
	}

	@Override
	public SingleShotBullet shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			SingleShotBullet output = null;
			if (bullet instanceof CannonBall) {
				output = new CannonBall((CannonBall) bullet, Bullet.initailVelocity(bullet.getSpeed()));
			} else if (bullet instanceof RandomBullet) {
				output = new RandomBullet((RandomBullet) bullet, Bullet.initailVelocity(bullet.getSpeed()));
			} else if (bullet instanceof Rocket) {
				output = new Rocket((Rocket) bullet, Bullet.initailVelocity(bullet.getSpeed()));
			} else if (bullet instanceof MachineGunBullet) {
				output = new MachineGunBullet((MachineGunBullet) bullet, Bullet.initailVelocity(bullet.getSpeed()));
			}
			return output;
		}
		return null;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
