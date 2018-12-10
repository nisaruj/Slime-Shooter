package item;

import bullet.SingleShotBullet;

public class SingleShotWeapon extends Weapon {

	public SingleShotWeapon(String name, int x, int y, int ammo) {
		super(name, x, y, ammo);
	}

	@Override
	public SingleShotBullet shoot() {
		if (isReady()) {
			ammo--;
			reloadingTime = 0;
			SingleShotBullet output = ((SingleShotBullet) this.bullet).duplicate((SingleShotBullet) this.bullet);
			return output;
		}
		return null;
	}

	@Override
	public void update() {
		reloadingTime += 1;
	}

}
