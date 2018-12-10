package bullet;

import java.util.ArrayList;

import character.Enemy;
import javafx.scene.image.Image;
import util.Coord;

public class Rocket extends SingleShotBullet {
	
	private static final Image ROCKET = new Image(ClassLoader.getSystemResource("bullets/rocket.png").toString());
	
	public Rocket(double speed, int damage) {
		super("rocket", speed, damage, 0.1);
	}
	
	public Rocket(Rocket bullet, Coord velocity) {
		super(bullet, velocity, 1);
		this.bulletImage = ROCKET;
	}
	
	public void explode(ArrayList<Enemy> enemies) {
		for (Enemy e : enemies) {
			if (isInRange(e)) {
				e.takeDamage(damage);
			}
		}
	}
	
	private boolean isInRange(Enemy e) {
		return Coord.distance(this.absolutePosition, e.getPosition()) < 100;
	}

}
