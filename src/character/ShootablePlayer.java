package character;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import item.Weapon;
import javafx.scene.image.Image;

public class ShootablePlayer extends Player {
	protected Map<String, Weapon> weapon = new HashMap<String, Weapon>();
	protected String currentWeaponName;

	public ShootablePlayer(int type) {
		// TODO: Add other types
		super();
		this.health = 100;
		this.maxHealth = 100;
		this.characterImage[0] = new Image(
				ClassLoader.getSystemResource("characters/" + type + "_south.png").toString());
		this.characterImage[1] = new Image(
				ClassLoader.getSystemResource("characters/" + type + "_diagdown.png").toString());
		this.characterImage[2] = new Image(
				ClassLoader.getSystemResource("characters/" + type + "_side.png").toString());
		this.characterImage[3] = new Image(
				ClassLoader.getSystemResource("characters/" + type + "_diagup.png").toString());
		this.characterImage[4] = new Image(
				ClassLoader.getSystemResource("characters/" + type + "_north.png").toString());
	}
	
	public void setWeapon(Weapon weapon) {
		String newWeaponName = weapon.getName();
		if (getWeapon(newWeaponName) != null) {
			getWeapon(newWeaponName).setFull();
		} else {
			this.weapon.put(weapon.getName(), weapon);
		}
	}

	public Weapon getWeapon() {
		return weapon.get(currentWeaponName);
	}

	public Weapon getWeapon(String name) {
		return weapon.get(name);
	}

	public void toggleWeapon() {
		Object[] nameList = getWeaponNameList().toArray();
		currentWeaponName = (String) nameList[(Arrays.asList(nameList).indexOf(currentWeaponName) + 1)
				% nameList.length];
	}

	public Set<String> getWeaponNameList() {
		return weapon.keySet();
	}

	public boolean buyAmmo() {
		Weapon currentWeapon = getWeapon();
		if (!currentWeapon.isFull()) {
			if (this.useCoin(currentWeapon.getReloadCost())) {
				currentWeapon.refillAmmo();
				return true;
			}
		}
		return false;
	}
}