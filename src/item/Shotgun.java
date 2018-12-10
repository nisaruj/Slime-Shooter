package item;

import java.util.Random;

import bullet.ShotgunBullet;
import main.GameScene;
import main.MainApplication;
import util.Coord;

public class Shotgun extends Weapon {

	public Shotgun(int x, int y, int ammo) {
		super("shotgun", x, y, ammo);
		this.bullet = new ShotgunBullet(13, 10);
		this.reloadSize = 3;
		this.reloadCost = 12;
		this.fireRate = 50;
	}

	@Override
	public ShotgunBullet[] shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			int halfWidth = MainApplication.SCREEN_WIDTH / 2;
			int halfHeight = MainApplication.SCREEN_HEIGHT / 2;
			double dX = GameScene.getMousePosition().getX() - halfWidth;
			double dY = GameScene.getMousePosition().getY() - halfHeight;
			double angle;
			if (dX > 0 && dY < 0) {
				angle = Math.atan(Math.abs(dY) / Math.abs(dX));
			} else if (dX < 0 && dY < 0) {
				angle = Math.PI - Math.atan(Math.abs(dY) / Math.abs(dX));
			} else if (dX < 0 && dY > 0) {
				angle = Math.PI + Math.atan(Math.abs(dY) / Math.abs(dX));
			} else {
				angle = -Math.atan(Math.abs(dY) / Math.abs(dX));
			}

			ShotgunBullet[] bulletList = new ShotgunBullet[10];
			Coord velocity = new Coord();
			Random rand = new Random();
			for (int i = 0; i < 10; i++) {
				double error = rand.nextDouble() * Math.PI / 12 * (rand.nextInt(2) == 0 ? 1 : -1);
				double newAngle = angle + error;
				if (newAngle >= 0 && newAngle < Math.PI / 2) {
					velocity.setXY(1, -Math.tan(newAngle));
				} else if (newAngle >= Math.PI / 2 && newAngle < Math.PI) {
					velocity.setXY(-1, -Math.tan(Math.PI - newAngle));
				} else if (newAngle >= Math.PI && newAngle <= 3 * Math.PI / 2) {
					velocity.setXY(-1, Math.tan(newAngle - Math.PI));
				} else if (newAngle > 3 * Math.PI / 2) {
					velocity.setXY(1, Math.tan(2 * Math.PI - newAngle));
				} else {
					// newAngle is negative
					if (-newAngle < Math.PI / 2) {
						velocity.setXY(1, Math.tan(-newAngle));
					} else {
						velocity.setXY(-1, Math.tan(Math.PI + newAngle));
					}
				}
				bulletList[i] = new ShotgunBullet((ShotgunBullet) bullet, velocity.normalize(bullet.getSpeed()));
			}
			return bulletList;
		}
		return null;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
