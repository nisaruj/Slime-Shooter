package character;

import java.util.Random;

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
	private double damage;
	private double health;
	private double maxHealth;
	private boolean isDead;
	private Coord knockBackVelocity;
	private double mass;

	public Enemy(int x, int y) {
		this("slime1", x, y);
	}

	public Enemy(String name, int x, int y) {
		slimeImage = new Image[3];
		slimeImage[0] = new Image(ClassLoader.getSystemResource("monsters/" + name + "_front.png").toString());
		slimeImage[1] = new Image(ClassLoader.getSystemResource("monsters/" + name + "_side.png").toString());
		slimeImage[2] = new Image(ClassLoader.getSystemResource("monsters/" + name + "_back.png").toString());
		healthBar = new Image(ClassLoader.getSystemResource("other/healthbar.png").toString());
		this.position = new Coord(x, y);
		this.maxHealth = 100;
		this.damage = 20;
		this.health = 100;
		this.isDead = false;
		this.isMoving = 0;
		this.movingSpeed = 3;
		this.speed = 1.1;
		this.mass = 1;
		this.knockBackVelocity = new Coord(0, 0);
	}

	public void takeDamage(double damage) {
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

	private boolean isCollidePlayer() {
		int posX = (int) GameScene.getHumanPlayer().getPosition().getX();
		int posY = (int) GameScene.getHumanPlayer().getPosition().getY();
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

	public double getDamage() {
		return damage;
	}

	public void update(GraphicsContext gc, int startRenderX, int startRenderY) {
		Random rand = new Random();
		if (isCollidePlayer()) {
			GameScene.explode(this);
			GameScene.getHumanPlayer().takeDamage(getDamage());
			return;
		}else if (isDead()) {
			GameScene.explode(this);
			GameScene.getHumanPlayer().addCoin(rand.nextInt(10) + 1);
			return;
		} else {
			isMoving++;
			if (knockBackVelocity.norm() > 0) {
				this.position.setXY(this.position.getX() + knockBackVelocity.getX(),
						this.position.getY() + knockBackVelocity.getY());
				reduceKnockBackVelocity();
				return;
			}
			Coord direction = new Coord(GameScene.getHumanPlayer().getPosition().getX() - this.position.getX(),
					GameScene.getHumanPlayer().getPosition().getY() - this.position.getY()).normalize(speed);
			this.position.setXY(this.position.getX() + direction.getX(), this.position.getY() + direction.getY());
			try {
				render(gc, startRenderX + (int) getPosition().getX(), startRenderY + (int) getPosition().getY());
			} catch (Exception e) {
				// Do nothing
			}
		}
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