package item;

import bullet.Bullet;
import bullet.CannonBall;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class Cannon extends SingleShotWeapon {
	
	private static final int DAMAGE = 50;
	private static final int BULLET_SPEED = 5;

	public Cannon() {
		super("cannon");
		this.bullet = new CannonBall(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
	}

	public Cannon(int x, int y) {
		super("cannon", x, y);
		this.bullet = new CannonBall(BULLET_SPEED, DAMAGE);
		this.fireRate = 30;
	}
	
	@Override
	public Bullet shoot() {
		if (isReady()) {
			reloadingTime = 0;
			int halfWidth = MainApplication.SCREEN_WIDTH / 2;
			int halfHeight = MainApplication.SCREEN_HEIGHT / 2;
			Coord velocity = new Coord(GameScene.getMousePosition().getX() - halfWidth,
					GameScene.getMousePosition().getY() - halfHeight).normalize(bullet.getSpeed());
			return new CannonBall((CannonBall) bullet, velocity);
		}
		return null;
	}
}
