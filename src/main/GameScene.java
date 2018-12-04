package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import bullet.Bullet;
import bullet.FireBullet;
import character.Enemy;
import character.Character;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import render.Map;
import util.Coord;

public class GameScene extends StackPane {

	private Canvas canvas;
	private GraphicsContext gc;
	private ArrayList<Bullet> bullets;
	private ArrayList<Item> items;
	private ArrayList<Enemy> enemies;
	private static Character character;
	private static Coord currentMousePosition;
	private Map map;
	private Set<KeyCode> keyboardStatus;

	public GameScene() {
		bullets = new ArrayList<Bullet>();
		items = new ArrayList<Item>();
		enemies = new ArrayList<Enemy>();
		character = new Character();
		map = new Map(character);
		items.add(new Flamethrower(500, 300));
		items.add(new Matter(600, 300));
		enemies.add(new Enemy(400, 600));
		enemies.add(new Enemy(500, 600));
		enemies.add(new Enemy(600, 600));

		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		canvas.setFocusTraversable(true);
		gc = canvas.getGraphicsContext2D();
		currentMousePosition = new Coord();
		keyboardStatus = new HashSet<KeyCode>();

		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				currentMousePosition.setXY(event.getX(), event.getY());
			}

		});

		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keyboardStatus.add(e.getCode());
			}

		});

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keyboardStatus.remove(e.getCode());
			}

		});

		this.getChildren().addAll(canvas);
		this.setStyle("-fx-background-color: grey");
		startGameLoop();
	}

	public void startGameLoop() {
		// final long startNanoTime = System.nanoTime();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				update();
			}

		}.start();
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
		if (isPressed) {
			character.move();
		} else {
			character.idle();
		}
	}

	public void update() {
		keyboardHandle();

		if (character.getWeapon().isReady()) {
			Object o = character.getWeapon().shoot();
			if (o instanceof FireBullet[]) {
				for (FireBullet b : (FireBullet[]) o) {
					addBullet(b);
				}
			} else {
				addBullet((Bullet) o);
			}
		}

		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		// Render Map
		map.render(gc);

		int startRenderX = (MainApplication.SCREEN_WIDTH / 2) - (int) character.getPosition().getX();
		int startRenderY = (MainApplication.SCREEN_HEIGHT / 2) - (int) character.getPosition().getY();
		
		renderItems(startRenderX, startRenderY);
		renderEnemies(startRenderX, startRenderY);
		renderBullets(startRenderX, startRenderY);

		// Render player
		character.update(currentMousePosition);
		character.render(gc);

	}
	
	private void renderItems(int startRenderX, int startRenderY) {
		// Render Items
		Iterator<Item> itr1 = items.iterator();
		while (itr1.hasNext()) {
			Item item = (Item) itr1.next();
			if (item.isCollidePlayer()) {
				itr1.remove();
				item.equip();
			} else {
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
					itr.remove();
					isCollideEnemy = true;
				}
			}
			if (isCollideEnemy)
				continue;

			if (b.getPosition().getX() < 0 || b.getPosition().getX() > MainApplication.SCREEN_WIDTH
					|| b.getPosition().getY() < 0 || b.getPosition().getY() > MainApplication.SCREEN_HEIGHT) {
				itr.remove();
			} else if (b instanceof FireBullet && ((FireBullet) b).isDisappear()) {
				itr.remove();
			} else {
				b.update();
				try {
					b.render(gc, startRenderX + (int)b.getAbsolutePosition().getX(), startRenderY + (int)b.getAbsolutePosition().getY());
				} catch (Exception e1) {
					// Do nothing
				}
				
				/// DEBUG ///
				int radius = 5;
				int x = startRenderX + (int)b.getAbsolutePosition().getX() - radius;
				int y = startRenderY + (int)b.getAbsolutePosition().getY() - radius;
				if (x < -5 || y < -5 || x > MainApplication.SCREEN_WIDTH || y > MainApplication.SCREEN_HEIGHT) {
					
				} else {
					gc.fillOval(x, y, radius, radius);
				}
				/// END DEBUG ///
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
