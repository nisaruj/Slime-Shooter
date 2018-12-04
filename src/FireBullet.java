
import javafx.scene.image.Image;

public class FireBullet extends Bullet {
	
	protected int lifeTime;
	private static final double FIRE_RADIUS = 17;
	protected static final Image FIRE_BULLET = new Image("file:res/bullets/flamethrower_bullet.png");
	
	public FireBullet(double speed, int damage, int lifeTime) {
		super(speed, damage);
		this.lifeTime = lifeTime;
		this.bulletImage = FIRE_BULLET;
	}
	
	public FireBullet(FireBullet bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, FIRE_RADIUS);
		this.bulletImage = FIRE_BULLET;
		this.lifeTime = bullet.lifeTime;
		this.speed = bullet.speed;
		this.damage = bullet.damage;
	}
	
	public boolean isDisappear() {
		return lifeTime < 0;
	}
	
	@Override
	public void update() {
		updatePosition();
		lifeTime--;
	}
	
}
