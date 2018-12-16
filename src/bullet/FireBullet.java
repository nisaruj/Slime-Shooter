package bullet;


import javafx.scene.image.Image;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class FireBullet extends Bullet {
	
	protected int lifeTime;
	protected static final Image FIRE_BULLET = new Image(ClassLoader.getSystemResource("bullets/flamethrower_bullet.png").toString());
	
	public FireBullet(double speed, int damage, int lifeTime) {
		super("flamethrower_bullet", speed, damage, 0);
		this.lifeTime = lifeTime;
	}
	
	public FireBullet(FireBullet bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, 17);
		this.lifeTime = bullet.lifeTime;
		this.bulletImage = bullet.bulletImage;
		this.speed = bullet.speed;
		this.damage = bullet.damage * GameScene.getHumanPlayer().getDamageMultiplier();
		this.mass = bullet.mass;
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