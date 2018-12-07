package character;

import item.Cannon;
import item.Flamethrower;
import item.Matter;
import item.RocketLauncher;
import item.Shotgun;
import item.SingleShotWeapon;
import item.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class Character {

	private String name;
	private Image[] characterImage = new Image[5];
	private static final int CHARACTER_WIDTH = 50;
	private static final int CHARACTER_HEIGHT = 60;
	public static final int MAX_MOVE_SPEED = 10;
	private int type;
	private int facingDirection;
	private int mirrorDirection;
	private double health;
	private double maxHealth;
	private boolean isDead;
	private Weapon weapon;
	private int isMoving;
	private int movingSpeed;
	private double speed;
	private Coord position;
	private double healthRegen;
	private double damageMultiplier;
	private int damageMultiplierTime;
	private int coinCount;
	private double animatedCoinCount;

	public Character(String name, int type) {
		// TODO: Add other types
		this.name = name;
		this.type = type;
		this.position = new Coord(300, 300);
		this.speed = 1.5;
		this.health = 100;
		this.maxHealth = 100;
		this.isDead = false;
		this.healthRegen = 0.005;
		this.damageMultiplier = 1;
		this.damageMultiplierTime = 0;
		setMovingSpeed(5);
		this.facingDirection = 0;
		this.mirrorDirection = 1;
		this.coinCount = 0;
		this.animatedCoinCount = 0;
		this.characterImage[0] = new Image(ClassLoader.getSystemResource("characters/" + type + "_south.png").toString());
		this.characterImage[1] = new Image(ClassLoader.getSystemResource("characters/" + type + "_diagdown.png").toString());
		this.characterImage[2] = new Image(ClassLoader.getSystemResource("characters/" + type + "_side.png").toString());
		this.characterImage[3] = new Image(ClassLoader.getSystemResource("characters/" + type + "_diagup.png").toString());
		this.characterImage[4] = new Image(ClassLoader.getSystemResource("characters/" + type + "_north.png").toString());
		weapon = new SingleShotWeapon("mg", 0, 0, 100);

	}

	public Character() {
		this("No Name", 1);
	}

	public void update(Coord currentMousePosition) {
		changeFacingDirection(currentMousePosition);
		regenHealth(this.healthRegen);
		updateDamageMultiplier();
		updateCoinCountAnimation();
		weapon.update();

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

	public void regenHealth(double value) {
		this.health = Math.min(maxHealth, this.health + value);
	}

	private void changeFacingDirection(Coord currentMousePosition) {
		double angle;
		double dX = currentMousePosition.getX() - MainApplication.SCREEN_WIDTH / 2;
		double dY = currentMousePosition.getY() - MainApplication.SCREEN_HEIGHT / 2;
		if (dX > 0 && dY < 0) {
			angle = Math.atan(Math.abs(dY) / Math.abs(dX));
		} else if (dX < 0 && dY < 0) {
			angle = Math.PI - Math.atan(Math.abs(dY) / Math.abs(dX));
		} else if (dX < 0 && dY > 0) {
			angle = Math.PI + Math.atan(Math.abs(dY) / Math.abs(dX));
		} else {
			angle = 2 * Math.PI - Math.atan(Math.abs(dY) / Math.abs(dX));
		}
		if (angle < Math.PI / 8) {
			// Side View
			facingDirection = 2;
			mirrorDirection = 1;
		} else if (angle < 3 * Math.PI / 8) {
			// DiagUp View
			facingDirection = 3;
			mirrorDirection = 1;
		} else if (angle < 5 * Math.PI / 8) {
			// North View
			facingDirection = 4;
			mirrorDirection = 1;
		} else if (angle < 7 * Math.PI / 8) {
			// DiagUp Mirror View
			facingDirection = 3;
			mirrorDirection = -1;
		} else if (angle < 9 * Math.PI / 8) {
			// Side Mirror View
			facingDirection = 2;
			mirrorDirection = -1;
		} else if (angle < 11 * Math.PI / 8) {
			// DiagDown Mirror View
			facingDirection = 1;
			mirrorDirection = -1;
		} else if (angle < 13 * Math.PI / 8) {
			// South View
			facingDirection = 0;
			mirrorDirection = 1;
		} else if (angle < 15 * Math.PI / 8) {
			// DiagDown View
			facingDirection = 1;
			mirrorDirection = 1;
		} else {
			// Side View
			facingDirection = 2;
			mirrorDirection = 1;
		}
	}

	public void render(GraphicsContext gc) {
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;

		if (facingDirection == 3 || facingDirection == 4) {
			// Render weapon first
			if (weapon != null) {
				weapon.render(gc, facingDirection, mirrorDirection, true);
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
				weapon.render(gc, facingDirection, mirrorDirection, false);
			}

		}

	}

	public void takeDamage(double damage) {
		if (isDead) {
			return;
		}
		this.health = Math.max(0, this.health - damage);
		if (health == 0) {
			isDead = true;
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

	public boolean isDead() {
		return isDead;
	}

	public void move() {
		isMoving++;
	}

	public void moveLeft() {
		this.position.setX(this.position.getX() - speed);
	}

	public void moveRight() {
		this.position.setX(this.position.getX() + speed);
	}

	public void moveUp() {
		this.position.setY(this.position.getY() - speed);
	}

	public void moveDown() {
		this.position.setY(this.position.getY() + speed);
	}

	public void idle() {
		isMoving = 0;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public double getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(double multiplier, int time) {
		this.damageMultiplier = Math.max(1, multiplier);
		this.damageMultiplierTime = Math.max(0, time);
	}

	public void setMovingSpeed(int speed) {
		if (speed >= MAX_MOVE_SPEED)
			speed = MAX_MOVE_SPEED - 1;
		this.movingSpeed = speed;
	}

	public Coord getPosition() {
		return position;
	}

	public void setPosition(double x, double y) {
		this.position.setXY(x, y);
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void addCoin(int amount) {
		this.coinCount += amount;
	}
	
	public boolean useCoin(int amount) {
		if (this.coinCount < amount) {
			return false;
		}
		this.coinCount -= amount;
		return true;
	}

	public int getCoin() {
		return this.coinCount;
	}
	
	public boolean buyAmmo() {
		int cost = 0, amount = 0;
		if (this.weapon instanceof Flamethrower) {
			cost = 12;
			amount = 20;
		} else if (this.weapon instanceof Matter) {
			cost = 7;
			amount = 10;
		} else if (this.weapon instanceof Shotgun) {
			cost = 12;
			amount = 3;
		} else if (this.weapon instanceof Cannon) {
			cost = 10;
			amount = 5;
		} else if (this.weapon instanceof RocketLauncher) {
			cost = 30;
			amount = 1;
		} else {
			cost = 10;
			amount = 20;
		}
		if (!weapon.isFull()) {
			if (this.useCoin(cost)) {
				this.weapon.refillAmmo(amount);
				return true;
			}
		}
		return false;
	}
	
	public double getAnimatedCoinCount() {
		return Math.round(this.animatedCoinCount);
	}
}
