package item;

import bullet.Bullet;
import bullet.RandomBullet;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class Matter extends SingleShotWeapon {

	private static final int DAMAGE = 0; //Damage depends on bullet type
	private static final int BULLET_SPEED = 5;

	public Matter() {
		super("matter");
		this.bullet = new RandomBullet(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
	}

	public Matter(int x, int y, int ammo) {
		super("matter", x, y, ammo);
		this.bullet = new RandomBullet(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
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
			return new RandomBullet((RandomBullet) bullet, velocity);
		}
		return null;
	}

}
