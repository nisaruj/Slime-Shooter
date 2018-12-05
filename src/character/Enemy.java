package character;

import bullet.Bullet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameScene;
import main.MainApplication;
import render.Renderable;
import util.Coord;

public class Enemy implements Renderable {

	private static final int MONSTER_SIZE = 40;
	public static final int MAX_MOVE_SPEED = 10;
	private int isMoving;
	private int movingSpeed;

	private Coord position;
	private Image[] slimeImage;
	private Image healthBar;
	private double speed;
	private int damage;
	private int health;
	private int maxHealth;
	private boolean isDead;
	private Coord knockBackVelocity;
	private double mass;

	public Enemy(int x, int y) {
		this("slime1", x, y);
	}

	public Enemy(String name, int x, int y) {
		slimeImage = new Image[3];
		slimeImage[0] = new Image("file:res/monsters/" + name + "_front.png");
		slimeImage[1] = new Image("file:res/monsters/" + name + "_side.png");
		slimeImage[2] = new Image("file:res/monsters/" + name + "_back.png");
		healthBar = new Image("file:res/other/healthbar.png");
		this.position = new Coord(x, y);
		this.maxHealth = 100;
		this.damage = 20;
		this.health = 100;
		this.isDead = false;
		this.isMoving = 0;
		this.movingSpeed = 3;
		this.speed = 0.5;
		this.mass = 1;
		this.knockBackVelocity = new Coord(0, 0);
	}

	public void takeDamage(int damage) {
		if (isDead) {
			return;
		}
		health = Math.max(0, health - damage);
		if (health == 0) {
			isDead = true;
		}
	}
	
	public void takeKnockBack(Coord bulletVelocity, double bulletMass) {
		Coord momentum = bulletVelocity.productScalar(bulletMass / this.mass);
		knockBackVelocity.plusVector(momentum);
	}
	
	public void reduceKnockBackVelocity() {
		double newSpeed = Math.max(0, knockBackVelocity.norm() - 1);
		knockBackVelocity = knockBackVelocity.normalize(newSpeed);
	}

	public boolean isCollideBullet(Bullet bullet) {
		int posX = (int) bullet.getAbsolutePosition().getX();
		int posY = (int) bullet.getAbsolutePosition().getY();
		boolean isCollide = posX > position.getX() - MONSTER_SIZE / 2 && posX < position.getX() + MONSTER_SIZE / 2
				&& posY > position.getY() - MONSTER_SIZE / 2 && posY < position.getY() + MONSTER_SIZE / 2;
		return isCollide;
	}

	public boolean isCollidePlayer() {
		int posX = (int) GameScene.getCharacter().getPosition().getX();
		int posY = (int) GameScene.getCharacter().getPosition().getY();
		return posX > position.getX() - MONSTER_SIZE / 2 && posX < position.getX() + MONSTER_SIZE / 2
				&& posY > position.getY() - MONSTER_SIZE / 2 && posY < position.getY() + MONSTER_SIZE / 2;
	}

	public boolean isDead() {
		return isDead;
	}

	public Coord getPosition() {
		return position;
	}
	
	public double getMass() {
		return mass;
	}
	
	public int getDamage() {
		return damage;
	}

	public void update() {
		isMoving++;
		if (knockBackVelocity.norm() > 0) {
			this.position.setXY(this.position.getX() + knockBackVelocity.getX(), this.position.getY() + knockBackVelocity.getY());
			reduceKnockBackVelocity();
			return;
		}
		Coord direction = new Coord(GameScene.getCharacter().getPosition().getX() - this.position.getX(),
				GameScene.getCharacter().getPosition().getY() - this.position.getY()).normalize(speed);
		this.position.setXY(this.position.getX() + direction.getX(), this.position.getY() + direction.getY());
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;

		if (x < -MONSTER_SIZE || y < -MONSTER_SIZE || x > MainApplication.SCREEN_WIDTH
				|| y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		double healthPercent = (double) health / maxHealth;
		gc.drawImage(healthBar, healthPercent * (healthBar.getWidth() - 1), 0, 1, 5, x - healthBar.getWidth() / 2,
				y - healthBar.getHeight() / 2 - MONSTER_SIZE / 2 - 10, healthPercent * healthBar.getWidth(), 5);
		gc.drawImage(slimeImage[0], moveFrame * MONSTER_SIZE, 0, MONSTER_SIZE, MONSTER_SIZE, x - MONSTER_SIZE / 2,
				y - MONSTER_SIZE / 2, MONSTER_SIZE, MONSTER_SIZE);
	}
}
