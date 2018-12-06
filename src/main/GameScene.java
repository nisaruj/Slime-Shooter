package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import bullet.Bullet;
import bullet.CannonBall;
import bullet.FireBullet;
import bullet.Rocket;
import bullet.ShotgunBullet;
import character.Enemy;
import character.Character;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import render.Burning;
import render.Effect;
import render.Explosion;
import render.Map;
import util.Coord;

public class GameScene extends StackPane {

	private static final Image CROSSHAIR = new Image("file:res/other/crosshair.png");
	private Canvas canvas;
	private GraphicsContext gc;
	private ArrayList<Bullet> bullets;
	private ArrayList<Item> items;
	private ArrayList<Enemy> enemies;
	private ArrayList<Effect> effects;
	private static Character character;
	private static Coord currentMousePosition;
	private Map map;
	private Set<KeyCode> keyboardStatus;
	private boolean isPaused = false;
	private HealthBarUI healthBar;

	public GameScene() {
		gameSetup();
		healthBar = new HealthBarUI();
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		canvas.setFocusTraversable(true);
		gc = canvas.getGraphicsContext2D();
		currentMousePosition = new Coord();
		keyboardStatus = new HashSet<KeyCode>();

		this.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				currentMousePosition.setXY(event.getX(), event.getY());
			}

		});

		this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keyboardStatus.add(e.getCode());
			}

		});

		this.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keyboardStatus.remove(e.getCode());
			}

		});

		this.getChildren().addAll(canvas, healthBar);
		this.setStyle("-fx-background-color: grey");
	}

	public void startGameLoop() {
		// final long startNanoTime = System.nanoTime();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (!isPaused) {
					update();
				}
			}

		}.start();
	}

	public void gameSetup() {
		bullets = new ArrayList<Bullet>();
		items = new ArrayList<Item>();
		enemies = new ArrayList<Enemy>();
		effects = new ArrayList<Effect>();
		character = new Character();
		map = new Map(character);
		items.add(new Flamethrower(500, 300, 1000));
		items.add(new Matter(600, 300, 100));
		items.add(new Cannon(500, 200, 100));
		items.add(new Shotgun(600, 200, 10));
		items.add(new RocketLauncher(400, 200, 3));
		items.add(new HealthBox(300, 200, 50));
		items.add(new DamageMultiply(200, 200, 1.5, 300));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				enemies.add(new Enemy(400 + 100 * i, 600 + 100 * j));
			}
		}
	}

	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	private void keyboardHandle() {
		boolean isPressed = false;
		if (keyboardStatus.contains(KeyCode.W)) {
			character.moveUp();
			isPressed = true;
		}
		if (keyboardStatus.contains(KeyCode.A)) {
			character.moveLeft();
			isPressed = true;
		}
		if (keyboardStatus.contains(KeyCode.S)) {
			character.moveDown();
			isPressed = true;
		}
		if (keyboardStatus.contains(KeyCode.D)) {
			character.moveRight();
			isPressed = true;
		}
		if (keyboardStatus.contains(KeyCode.SPACE)) {
			// handlePlayerShoot();
		}
		if (isPressed) {
			character.move();
		} else {
			character.idle();
		}
	}

	public void handlePlayerShoot() {
		if (character.getWeapon().isReady()) {
			Object o = character.getWeapon().shoot();
			if (o instanceof FireBullet[]) {
				for (FireBullet b : (FireBullet[]) o) {
					addBullet(b);
				}
			} else if (o instanceof ShotgunBullet[]) {
				for (ShotgunBullet b : (ShotgunBullet[]) o) {
					addBullet(b);
				}
			} else {
				addBullet((Bullet) o);
			}
		}
	}

	public void update() {
		keyboardHandle();
		handlePlayerShoot();

		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		// Render Map
		map.render(gc);

		// Render UI
		healthBar.render();

		int startRenderX = (MainApplication.SCREEN_WIDTH / 2) - (int) character.getPosition().getX();
		int startRenderY = (MainApplication.SCREEN_HEIGHT / 2) - (int) character.getPosition().getY();

		renderEffects(startRenderX, startRenderY);
		renderItems(startRenderX, startRenderY);
		renderEnemies(startRenderX, startRenderY);
		renderBullets(startRenderX, startRenderY);

		// Render player
		character.update(currentMousePosition);
		character.render(gc);

		if (character.isDead()) {
			gameSetup();
		}

	}

	private void renderItems(int startRenderX, int startRenderY) {
		// Render Items
		Iterator<Item> itr = items.iterator();
		while (itr.hasNext()) {
			Item item = (Item) itr.next();
			if (item.isCollidePlayer()) {
				itr.remove();
				item.equip();
			} else {
				if (item instanceof Powerup) {
					((Powerup) item).update();
				}
				try {
					item.render(gc, startRenderX + (int) item.getPosition().getX(),
							startRenderY + (int) item.getPosition().getY());
				} catch (Exception e) {
					// Do nothing
				}
			}
		}
	}

	private void renderEnemies(int startRenderX, int startRenderY) {
		// Render Enemies
		Iterator<Enemy> itr = enemies.iterator();
		while (itr.hasNext()) {
			Enemy enemy = (Enemy) itr.next();
			if (enemy.isDead()) {
				effects.add(new Explosion(enemy.getPosition()));
				itr.remove();
			} else if (enemy.isCollidePlayer()) {
				character.takeDamage(enemy.getDamage());
				itr.remove();
			} else {
				enemy.update();
				try {
					enemy.render(gc, startRenderX + (int) enemy.getPosition().getX(),
							startRenderY + (int) enemy.getPosition().getY());
				} catch (Exception e) {
					// Do nothing
				}
			}
		}
	}

	private void renderBullets(int startRenderX, int startRenderY) {
		// Render Bullets
		Iterator<Bullet> itr = bullets.iterator();
		while (itr.hasNext()) {
			Bullet b = (Bullet) itr.next();
			boolean isCollideEnemy = false;

			for (Enemy e : enemies) {
				if (e.isCollideBullet(b)) {
					try {
						if (b instanceof Rocket) {
							((Rocket) b).explode(enemies);
							effects.add(new Explosion(b.getAbsolutePosition()));
						} else {
							e.takeDamage(b.getDamage());
							e.takeKnockBack(b.getVelocity(), b.getMass());
						}
						itr.remove();
					} catch (IllegalStateException error) {
						// Do nothing
					}
					isCollideEnemy = true;
				}
			}
			if (isCollideEnemy)
				continue;

			if (b instanceof FireBullet && ((FireBullet) b).isDisappear()) {
				effects.add(new Burning(b.getAbsolutePosition()));
				itr.remove();
			} else {
				b.update();
				try {
					b.render(gc, startRenderX + (int) b.getAbsolutePosition().getX(),
							startRenderY + (int) b.getAbsolutePosition().getY());
				} catch (Exception e1) {
					// The bullet is rendered out of the screen, so delete it.
					itr.remove();
				}

				/// DEBUG ///
//				int radius = 5;
//				int x = startRenderX + (int) b.getAbsolutePosition().getX() - radius;
//				int y = startRenderY + (int) b.getAbsolutePosition().getY() - radius;
//				if (x < -5 || y < -5 || x > MainApplication.SCREEN_WIDTH || y > MainApplication.SCREEN_HEIGHT) {
//
//				} else {
//					gc.fillOval(x, y, radius, radius);
//				}
				/// END DEBUG ///
			}
		}
	}

	private void renderEffects(int startRenderX, int startRenderY) {
		// Render Items
		Iterator<Effect> itr = effects.iterator();
		while (itr.hasNext()) {
			Effect effect = (Effect) itr.next();
			effect.update();
			if (effect.isRenderFinished()) {
				itr.remove();
			} else {
				try {
					effect.render(gc, startRenderX + (int) effect.getPosition().getX(),
							startRenderY + (int) effect.getPosition().getY());
				} catch (Exception e) {
					// Do nothing
				}
			}
		}
	}

	public static Coord getMousePosition() {
		return currentMousePosition;
	}

	public static Character getCharacter() {
		return character;
	}
}
