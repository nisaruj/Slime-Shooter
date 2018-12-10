package item;

import bullet.Bullet;

public class MachineGun extends SingleShotWeapon {

	public MachineGun(int x, int y, int ammo) {
		super("mg", x, y, ammo);
		this.bullet = new Bullet("bulletc", 10, 10, 0.1);
		this.fireRate = 10;
		this.reloadSize = 10;
		this.reloadCost = 5;
	}
}
