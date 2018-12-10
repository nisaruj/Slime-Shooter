package bullet;

import javafx.scene.image.Image;
import util.Coord;

public class MachineGunBullet extends SingleShotBullet {
	
	public static final double FIRE_RADIUS = 3;
	private static final double MASS = 0.1;
	private static final Image MG_BULLET = new Image(ClassLoader.getSystemResource("bullets/bulletc.png").toString());
	
	public MachineGunBullet(double speed, int damage) {
		super("bulletc", speed, damage, MASS);
	}
	
	public MachineGunBullet(MachineGunBullet bullet, Coord velocity) {
		super(bullet, velocity, FIRE_RADIUS);
		this.bulletImage = MG_BULLET;
	}

}
