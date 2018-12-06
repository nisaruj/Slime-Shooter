package item;

import main.GameScene;

public class DamageMultiply extends Powerup {
	
	private double multiplier;
	private int time;

	public DamageMultiply(int x, int y, double multiplier, int time) {
		super("green", x, y);
		this.multiplier = multiplier;
		this.time = time;
	}

	@Override
	public void equip() {
		GameScene.getCharacter().setDamageMultiplier(multiplier, time);
		
	}

}
