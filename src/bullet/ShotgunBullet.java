package bullet;

import javafx.scene.image.Image;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class ShotgunBullet extends Bullet {
	
	private static final Image SHOTGUN_BULLET = new Image(ClassLoader.getSystemResource("bullets/bulleta.png").toString());
	
	public ShotgunBullet(double speed, int damage) {
		super("bulleta", speed, damage, 0.1);
	}
	
	public ShotgunBullet(ShotgunBullet bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, 1);
		this.bulletImage = SHOTGUN_BULLET;
		this.damage = bullet.damage * GameScene.getHumanPlayer().getDamageMultiplier();
		this.speed = bullet.speed;
		this.mass = bullet.mass;
	}
}