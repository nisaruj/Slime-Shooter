package character;

import item.MachineGun;
import item.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainApplication;
import render.Map;
import render.Tile;
import util.Coord;

public class Player {
	public static final int MAX_MOVE_SPEED = 10;
	protected static int CHARACTER_WIDTH = 50;
	protected static int CHARACTER_HEIGHT = 60;

	protected Image[] characterImage = new Image[5];
	protected int facingDirection;
	protected int mirrorDirection;
	protected double health;
	protected double maxHealth;
	protected boolean isDead;
	protected int isMoving;
	protected int movingSpeed;
	protected double speed;
	protected Coord position;
	protected double healthRegen;
	protected int coinCount;

	public void update(Coord currentMousePosition) {
		changeFacingDirection(currentMousePosition);
		regenHealth(this.healthRegen);
	}

	public void render(GraphicsContext gc, int startRenderX, int startRenderY) {
		final int DEFAULT_SIZE = 43;
		final double x = startRenderX + position.getX(), y = startRenderY + position.getY();
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;
		Image healthBar = new Image(ClassLoader.getSystemResource("other/healthbar.png").toString());
		double healthPercent = (double) health / maxHealth;

		if (x < -DEFAULT_SIZE || y < -DEFAULT_SIZE || x > MainApplication.SCREEN_WIDTH
				|| y > MainApplication.SCREEN_HEIGHT) {
			return;
		}

		gc.drawImage(healthBar, healthPercent * (healthBar.getWidth() - 1), 0, 1, 5, x - healthBar.getWidth() / 2,
				y - healthBar.getHeight() / 2 - DEFAULT_SIZE / 2 - 10, healthPercent * healthBar.getWidth(), 5);
		gc.drawImage(characterImage[facingDirection], moveFrame * CHARACTER_WIDTH, 0, DEFAULT_SIZE, DEFAULT_SIZE,
				x - DEFAULT_SIZE / 2, y - DEFAULT_SIZE / 2, DEFAULT_SIZE, DEFAULT_SIZE);
	}

	public void render(GraphicsContext gc) {
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;

		gc.drawImage(characterImage[facingDirection], CHARACTER_WIDTH * moveFrame, 0, CHARACTER_WIDTH, CHARACTER_HEIGHT,
				MainApplication.SCREEN_WIDTH / 2
						- CHARACTER_WIDTH / 2 * mirrorDirection * MainApplication.SIZE_MULTIPLIER,
				MainApplication.SCREEN_HEIGHT / 2 - CHARACTER_HEIGHT / 2,
				CHARACTER_WIDTH * MainApplication.SIZE_MULTIPLIER * mirrorDirection,
				CHARACTER_HEIGHT * MainApplication.SIZE_MULTIPLIER);

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

	public boolean isDead() {
		return isDead;
	}

	protected void changeFacingDirection(Coord currentMousePosition) {
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

	protected void setMovingSpeed(int speed) {
		if (speed >= MAX_MOVE_SPEED)
			speed = MAX_MOVE_SPEED - 1;
		this.movingSpeed = speed;
	}

	public void move() {
		isMoving++;
	}

	public void idle() {
		isMoving = 0;
	}

	public void moveLeft() {
		double nextX = this.position.getX() - speed;
		if (nextX > 0)
			this.position.setX(nextX);

	}

	public void moveRight() {
		double nextX = this.position.getX() + speed;
		if (nextX < Tile.TILE_SIZE * render.Map.getMapWidth())
			this.position.setX(nextX);
	}

	public void moveUp() {
		double nextY = this.position.getY() - speed;
		if (nextY > 0)
			this.position.setY(nextY);
	}

	public void moveDown() {
		double nextY = this.position.getY() + speed;
		if (nextY < Tile.TILE_SIZE * render.Map.getMapHeight())
			this.position.setY(nextY);
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

	public void regenHealth(double value) {
		this.health = Math.min(maxHealth, this.health + value);
	}

	public int getCoin() {
		return this.coinCount;
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

	public Player() {
		// TODO: Add other types
		this.position = new Coord(Map.getMapWidth() * Tile.TILE_SIZE / 2, Map.getMapHeight() * Tile.TILE_SIZE / 2);
		this.speed = 0;
		this.health = 500;
		this.maxHealth = 500;
		this.isDead = false;
		this.healthRegen = 0.01;
		setMovingSpeed(5);
		this.facingDirection = 0;
		this.mirrorDirection = 1;
		this.coinCount = 0;
		this.characterImage[0] = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
		this.characterImage[1] = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
		this.characterImage[2] = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
		this.characterImage[3] = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());
		this.characterImage[4] = new Image(ClassLoader.getSystemResource("bullets/cannonball.png").toString());

	}
}
