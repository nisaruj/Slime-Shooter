package item;

import bullet.RandomBullet;

public class Matter extends SingleShotWeapon {

	public Matter(int x, int y, int ammo) {
		super("matter", x, y, ammo);
		this.bullet = new RandomBullet(5, 0);
		this.fireRate = 30;
		this.reloadSize = 10;
		this.reloadCost = 7;
	}

}
