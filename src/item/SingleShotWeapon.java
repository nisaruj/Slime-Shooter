package item;
import bullet.Bullet;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class SingleShotWeapon extends Weapon {

	protected int fireRate = 10;
	protected int reloadingTime = 0;

	public SingleShotWeapon(String name) {
		super(name);
	}
	
	public SingleShotWeapon(String name, int x, int y) {
		super(name, x, y);
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
