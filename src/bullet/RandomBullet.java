package bullet;

import java.util.Random;

import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class RandomBullet extends Bullet {

	private static final double FIRE_RADIUS = 10;
	private static final double ROTATE_SPEED = 5;
	private static final Image CAT_IMG = new Image("file:res/bullets/cat.png");
	private static final Image CANNON_IMG = new Image("file:res/bullets/cannonball.png");
	private static final Image CUP_IMG = new Image("file:res/bullets/cup.png");
	private static final Image MICROWAVE_IMG = new Image("file:res/bullets/microwave.png");
	private int rotateDirection;

	public RandomBullet(double speed, int damage) {
		super("cat", speed, damage);
		this.rotateDirection = 1;
	}

	public RandomBullet(RandomBullet bullet, Coord velocity) {
		super(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, FIRE_RADIUS);
		Random rand = new Random();
		int type = rand.nextInt(4);
		switch (type) {
		case 0:
			this.bulletImage = CAT_IMG;
			this.damage = 20;
			break;
		case 1:
			this.bulletImage = CANNON_IMG;
			this.damage = 50;
			break;
		case 2:
			this.bulletImage = CUP_IMG;
			this.damage = 15;
			break;
		case 3:
			this.bulletImage = MICROWAVE_IMG;
			this.damage = 30;
			break;
		default:
			this.bulletImage = CAT_IMG;
			this.damage = 20;
		}
		this.rotateDirection = rand.nextInt(2) == 0 ? 1 : -1;
		this.speed = bullet.speed;
	}

	@Override
	public void update() {
		updatePosition();
		angle += ROTATE_SPEED * rotateDirection;
	}

}
