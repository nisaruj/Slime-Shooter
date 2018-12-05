package item;
import bullet.Bullet;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class SingleShotWeapon extends Weapon {

	private static final int DAMAGE = 10;
	private static final int BULLET_SPEED = 10;
	private static final double MASS = 0.1;
	protected int fireRate = 10;
	protected int reloadingTime = 0;

	public SingleShotWeapon(String name) {
		super(name);
		this.bullet = new Bullet("bulletc", BULLET_SPEED, DAMAGE, MASS);
	}
	
	public SingleShotWeapon(String name, int x, int y, int ammo) {
		super(name, x, y, ammo);
		this.bullet = new Bullet("bulletc", BULLET_SPEED, DAMAGE, MASS);
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
			int halfWidth = MainApplication.SCREEN_WIDTH / 2;
			int halfHeight = MainApplication.SCREEN_HEIGHT / 2;
			Coord velocity = new Coord(GameScene.getMousePosition().getX() - halfWidth,
					GameScene.getMousePosition().getY() - halfHeight).normalize(bullet.getSpeed());
			return new Bullet(bullet, velocity);
		}
		return null;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
