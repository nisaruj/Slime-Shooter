package bullet;

import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class CannonBall extends Bullet {

	private static final double FIRE_RADIUS = 10;
	private static final double MASS = 1.5;
	private static final Image CANNON_IMG = new Image("file:res/bullets/cannonball.png");
	
	public CannonBall(double speed, int damage) {
		super("cannonball", speed, damage, MASS);
	}
	
	public CannonBall(CannonBall bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, FIRE_RADIUS);
		this.bulletImage = CANNON_IMG;
		this.damage = bullet.damage;
		this.speed = bullet.speed;
		this.mass = bullet.mass;
	}
	
}
