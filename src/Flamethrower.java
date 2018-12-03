
public class Flamethrower extends Weapon {
	
	private static final int DEFAULT_LIFETIME = 50;
	protected int fireRate = 10;
	protected int reloadingTime;

	public Flamethrower(String name) {
		super(name);
		this.bullet = new FireBullet(3, 1, DEFAULT_LIFETIME);
	}

	public Flamethrower(String name, int x, int y) {
		super(name, x, y);
		this.bullet = new FireBullet(3, 1, DEFAULT_LIFETIME);
	}

	@Override
	public Bullet shoot() {
		// TODO : Shot wide direction
		if (isReady()) {
			reloadingTime = 0;
			int halfWidth = MainApplication.SCREEN_WIDTH / 2;
			int halfHeight = MainApplication.SCREEN_HEIGHT / 2;
			Coord velocity = new Coord(GameScene.getMousePosition().getX() - halfWidth,
					GameScene.getMousePosition().getY() - halfHeight).normalize(bullet.getSpeed());
			return new FireBullet((FireBullet) bullet, velocity);
		}
		return null;
	}

	@Override
	public boolean isReady() {
		return reloadingTime >= fireRate;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
