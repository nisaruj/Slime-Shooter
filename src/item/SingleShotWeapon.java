package item;
import bullet.Bullet;
import bullet.CannonBall;
import bullet.MachineGunBullet;
import bullet.RandomBullet;
import bullet.Rocket;
import bullet.SingleShotBullet;

public class SingleShotWeapon extends Weapon {

	private static final int DAMAGE = 10;
	private static final int BULLET_SPEED = 10;
	protected int fireRate = 10;
	protected int reloadingTime = 0;

	public SingleShotWeapon(String name) {
		super(name);
		this.bullet = new MachineGunBullet(BULLET_SPEED, DAMAGE);
	}
	
	public SingleShotWeapon(String name, int x, int y, int ammo) {
		super(name, x, y, ammo);
		this.bullet = new MachineGunBullet(BULLET_SPEED, DAMAGE);
	}

	@Override
	public boolean isReady() {
		return ammo > 0 && reloadingTime >= fireRate;
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
