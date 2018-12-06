package item;

import main.GameScene;

public class HealthBox extends Powerup {
	
	private double regenValue;
	
	public HealthBox(int x, int y, double regenValue) {
		super("red", x, y);
		this.regenValue = regenValue;
	}

	@Override
	public void equip() {
		GameScene.getCharacter().regenHealth(this.regenValue);

	}

}
