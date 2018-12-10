package bullet;

import javafx.scene.image.Image;
import util.Coord;

public class MachineGunBullet extends SingleShotBullet {
	
	private static final Image MG_BULLET = new Image(ClassLoader.getSystemResource("bullets/bulletc.png").toString());
	
	public MachineGunBullet(double speed, int damage) {
		super("bulletc", speed, damage, 0.1);
	}
	
	public MachineGunBullet(MachineGunBullet bullet, Coord velocity) {
		super(bullet, velocity, 3);
		this.bulletImage = MG_BULLET;
	}

	@Override
	public SingleShotBullet duplicate(SingleShotBullet bullet) {
		return new MachineGunBullet((MachineGunBullet) bullet, Bullet.initailVelocity(bullet.getSpeed()));
	}

}
