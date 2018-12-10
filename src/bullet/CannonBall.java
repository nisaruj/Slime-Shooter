package bullet;

import javafx.scene.image.Image;
import util.Coord;

public class CannonBall extends SingleShotBullet {

	private static final Image CANNON_IMG = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
	
	public CannonBall(double speed, int damage) {
		super("cannonball", speed, damage, 1.5);
	}
	
	public CannonBall(CannonBall bullet, Coord velocity) {
		super(bullet, velocity, 10);
		this.bulletImage = CANNON_IMG;
	}

	@Override
	public SingleShotBullet duplicate(SingleShotBullet bullet) {
		return new CannonBall((CannonBall) bullet, Bullet.initailVelocity(bullet.getSpeed()));
	}
	
}
