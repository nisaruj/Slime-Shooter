package bullet;

import javafx.scene.image.Image;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class SingleShotBullet extends Bullet {

	private static final Image DEFAULT_BULLET = new Image(ClassLoader.getSystemResource("bullets/bulletc.png").toString());
	
	public SingleShotBullet(String name, double speed, int damage, double mass) {
		super(name, speed, damage, mass);
	}
	
	public SingleShotBullet(SingleShotBullet bullet, Coord velocity, double fireRadius) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, fireRadius);
		this.bulletImage = DEFAULT_BULLET;
		this.damage = bullet.damage * GameScene.getCharacter().getDamageMultiplier();
		this.speed = bullet.speed;
		this.mass = bullet.mass;
	}

}
