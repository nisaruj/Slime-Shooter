package bullet;

import javafx.scene.image.Image;
import util.Coord;

public class CannonBall extends SingleShotBullet {

	public static final double FIRE_RADIUS = 10;
	private static final double MASS = 1.5;
	private static final Image CANNON_IMG = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
	
	public CannonBall(double speed, int damage) {
		super("cannonball", speed, damage, MASS);
	}
	
	public CannonBall(CannonBall bullet, Coord velocity) {
		super(bullet, velocity, FIRE_RADIUS);
		this.bulletImage = CANNON_IMG;
	}
	
}
