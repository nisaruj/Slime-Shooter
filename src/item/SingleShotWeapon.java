package item;
import bullet.Bullet;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class SingleShotWeapon extends Weapon {

	protected int fireRate;
	protected int reloadingTime;

	public SingleShotWeapon(String name) {
		super(name);
		this.fireRate = 10;
		this.reloadingTime = 0;
	}
	
	public SingleShotWeapon(String name, int x, int y) {
		super(name, x, y);
		this.fireRate = 5;
		this.reloadingTime = 0;
	}

	@Override
	public boolean isReady() {
		return reloadingTime >= fireRate;
	}

	@Override
	public Bullet shoot() {
		if (isReady()) {
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
