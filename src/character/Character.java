package character;
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

	public Character(String name, int type) {
		// TODO: Add other types
		this.name = name;
		this.type = type;
		this.position = new Coord(300, 300);
		this.speed = 1;
		this.health = 100;
		this.maxHealth = 100;
		this.isDead = false;
		this.healthRegen = 0.01;
		setMovingSpeed(5);
		this.facingDirection = 0;
		this.mirrorDirection = 1;
		this.characterImage[0] = new Image("file:res/characters/" + type + "_south.png");
		this.characterImage[1] = new Image("file:res/characters/" + type + "_diagdown.png");
		this.characterImage[2] = new Image("file:res/characters/" + type + "_side.png");
		this.characterImage[3] = new Image("file:res/characters/" + type + "_diagup.png");
		this.characterImage[4] = new Image("file:res/characters/" + type + "_north.png");
		weapon = new SingleShotWeapon("mg", 0, 0, 100);

	}

	public Character() {
		this("No Name", 1);
	}

	public void update(Coord currentMousePosition) {
		changeFacingDirection(currentMousePosition);
		this.health = Math.min(maxHealth, this.health + this.healthRegen);
		weapon.update();
		
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
	
	public void takeDamage(int damage) {
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

}
