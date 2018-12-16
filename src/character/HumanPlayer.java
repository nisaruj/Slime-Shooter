package character;

import item.*;
import javafx.scene.canvas.GraphicsContext;
import main.MainApplication;
import util.Coord;

public class HumanPlayer extends ShootablePlayer {

	private double damageMultiplier;
	private int damageMultiplierTime;
	private double animatedCoinCount;

	public HumanPlayer() {
		// TODO: Add other types
		super(1);
		this.speed = 3;
		this.damageMultiplier = 1;
		this.damageMultiplierTime = 0;
		this.animatedCoinCount = 0;
		this.weapon.put("mg", new MachineGun(0, 0, 100));
		this.currentWeaponName = "mg";

	}

	@Override
	public void update(Coord currentMousePosition) {
		changeFacingDirection(currentMousePosition);
		regenHealth(this.healthRegen);
		updateDamageMultiplier();
		updateCoinCountAnimation();
		getWeapon().update();

	}

	private void updateDamageMultiplier() {
		damageMultiplierTime = Math.max(0, damageMultiplierTime - 1);
		if (!inUseDamageMultiplier()) {
			damageMultiplier = 1;
		}
	}

	public boolean inUseDamageMultiplier() {
		return damageMultiplierTime > 0;
	}

	@Override
	public void render(GraphicsContext gc) {
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;
		Weapon currentWeapon = getWeapon();
		if (facingDirection == 3 || facingDirection == 4) {
			// Render weapon first
			if (weapon != null) {
				currentWeapon.render(gc, facingDirection, mirrorDirection, true);
			}
			gc.drawImage(characterImage[facingDirection], CHARACTER_WIDTH * moveFrame, 0, CHARACTER_WIDTH,
					CHARACTER_HEIGHT,
					MainApplication.SCREEN_WIDTH / 2
							- CHARACTER_WIDTH / 2 * mirrorDirection * MainApplication.SIZE_MULTIPLIER,
					MainApplication.SCREEN_HEIGHT / 2 - CHARACTER_HEIGHT / 2,
					CHARACTER_WIDTH * MainApplication.SIZE_MULTIPLIER * mirrorDirection,
					CHARACTER_HEIGHT * MainApplication.SIZE_MULTIPLIER);
		} else {
			// Render character first
			gc.drawImage(characterImage[facingDirection], CHARACTER_WIDTH * moveFrame, 0, CHARACTER_WIDTH,
					CHARACTER_HEIGHT,
					MainApplication.SCREEN_WIDTH / 2
							- CHARACTER_WIDTH / 2 * mirrorDirection * MainApplication.SIZE_MULTIPLIER,
					MainApplication.SCREEN_HEIGHT / 2 - CHARACTER_HEIGHT / 2,
					CHARACTER_WIDTH * MainApplication.SIZE_MULTIPLIER * mirrorDirection,
					CHARACTER_HEIGHT * MainApplication.SIZE_MULTIPLIER);

			if (weapon != null) {
				currentWeapon.render(gc, facingDirection, mirrorDirection, false);
			}

		}

	}

	public void updateCoinCountAnimation() {
		final double epsilon = 0.001;
		if (Math.abs(this.coinCount - this.animatedCoinCount) < epsilon) {
			return;
		}
		if (this.coinCount > this.animatedCoinCount) {
			this.animatedCoinCount += 0.2;
		} else if (this.coinCount < this.animatedCoinCount) {
			this.animatedCoinCount -= 0.2;
		}
	}

	public double getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(double multiplier, int time) {
		this.damageMultiplier = Math.max(1, multiplier);
		this.damageMultiplierTime = Math.max(0, time);
	}

	public double getAnimatedCoinCount() {
		return Math.round(this.animatedCoinCount);
	}
}
