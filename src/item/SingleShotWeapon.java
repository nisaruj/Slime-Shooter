package item;
import bullet.Bullet;

public class SingleShotWeapon extends Weapon {

	private static final int DAMAGE = 10;
	private static final int BULLET_SPEED = 10;
	private static final double MASS = 0.1;
	protected int fireRate = 10;
	protected int reloadingTime = 0;

	public SingleShotWeapon(String name) {
		super(name);
		this.bullet = new Bullet("bulletc", BULLET_SPEED, DAMAGE, MASS);
		this.reloadSize = 20;
		this.reloadCost = 10;
	}
	
	public SingleShotWeapon(String name, int x, int y, int ammo) {
		super(name, x, y, ammo);
		this.bullet = new Bullet("bulletc", BULLET_SPEED, DAMAGE, MASS);
		this.reloadSize = 20;
		this.reloadCost = 10;
	}

	@Override
	public boolean isReady() {
		return ammo > 0 && reloadingTime >= fireRate;
	}

	@Override
	public Bullet shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			return new Bullet(bullet, Bullet.initailVelocity(bullet.getSpeed()));
		}
		return null;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
