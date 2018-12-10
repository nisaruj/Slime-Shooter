package item;

import bullet.Rocket;

public class RocketLauncher extends SingleShotWeapon {

	public RocketLauncher(int x, int y, int ammo) {
		super("rocket",x, y, ammo);
		this.bullet = new Rocket(20, 80);
		this.fireRate = 50;
		this.reloadSize = 1;
		this.reloadCost = 50;
	}
	
}
