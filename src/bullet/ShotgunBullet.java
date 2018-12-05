package bullet;

import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class ShotgunBullet extends Bullet {
	private static final double FIRE_RADIUS = 1;
	private static final double MASS = 0.1;
	private static final Image SHOTGUN_BULLET = new Image("file:res/bullets/bulleta.png");
	
	public ShotgunBullet(double speed, int damage) {
		super("bulleta", speed, damage, MASS);
	}
	
	public ShotgunBullet(ShotgunBullet bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, FIRE_RADIUS);
		this.bulletImage = SHOTGUN_BULLET;
		this.damage = bullet.damage;
		this.speed = bullet.speed;
		this.mass = bullet.mass;
	}
}
