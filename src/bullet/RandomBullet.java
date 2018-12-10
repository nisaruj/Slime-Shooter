package bullet;

import java.util.Random;

import javafx.scene.image.Image;
import main.GameScene;
import util.Coord;

public class RandomBullet extends SingleShotBullet {

	private static final Image CAT_IMG = new Image(ClassLoader.getSystemResource("bullets/cat.png").toString());
	private static final Image CANNON_IMG = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
	private static final Image CUP_IMG = new Image(ClassLoader.getSystemResource("bullets/cup.png").toString());
	private static final Image MICROWAVE_IMG = new Image(ClassLoader.getSystemResource("bullets/microwave.png").toString());
	private int rotateDirection;

	public RandomBullet(double speed, int damage) {
		super("cat", speed, damage, 1);
		this.rotateDirection = 1;
	}

	public RandomBullet(RandomBullet bullet, Coord velocity) {
		super(bullet, velocity, 10);
		Random rand = new Random();
		int type = rand.nextInt(4);
		switch (type) {
		case 0:
			this.bulletImage = CAT_IMG;
			this.damage = 20;
			this.mass = 0.2;
			break;
		case 1:
			this.bulletImage = CANNON_IMG;
			this.damage = 50;
			this.mass = 1;
			break;
		case 2:
			this.bulletImage = CUP_IMG;
			this.damage = 15;
			this.mass = 0.1;
			break;
		case 3:
			this.bulletImage = MICROWAVE_IMG;
			this.damage = 30;
			this.mass = 0.8;
			break;
		default:
			this.bulletImage = CAT_IMG;
			this.damage = 20;
			this.mass = 0.2;
		}
		this.damage *= GameScene.getCharacter().getDamageMultiplier();
		this.rotateDirection = rand.nextInt(2) == 0 ? 1 : -1;
	}

	@Override
	public void update() {
		updatePosition();
		angle += 5 * rotateDirection;
	}

	@Override
	public SingleShotBullet duplicate(SingleShotBullet bullet) {
		return new RandomBullet((RandomBullet) bullet, Bullet.initailVelocity(bullet.getSpeed()));
	}

}
