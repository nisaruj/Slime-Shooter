package item;

import bullet.MachineGunBullet;

public class MachineGun extends SingleShotWeapon {

	public MachineGun(int x, int y, int ammo) {
		super("mg", x, y, ammo);
		this.bullet = new MachineGunBullet(10, 10);
		this.fireRate = 10;
		this.reloadSize = 10;
		this.reloadCost = 5;
	}
}
