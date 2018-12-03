import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameScene extends StackPane {

	private Canvas canvas;
	private GraphicsContext gc;
	private ArrayList<Bullet> bullets;
	private Character character;
	private static Coord currentMousePosition;
	private Map map;
	private Set<KeyCode> keyboardStatus;

	public GameScene() {
		bullets = new ArrayList<Bullet>();
		character = new Character();
		map = new Map(character);

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
			addBullet(character.getWeapon().shoot());
		}

		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		map.render(gc);

		Iterator<Bullet> itr = bullets.iterator();
		while (itr.hasNext()) {
			Bullet b = (Bullet) itr.next();
			if (b.getPosition().getX() < 0 || b.getPosition().getX() > MainApplication.SCREEN_WIDTH
					|| b.getPosition().getY() < 0 || b.getPosition().getY() > MainApplication.SCREEN_HEIGHT) {
				itr.remove();
			} else {
				b.update();
				b.render(gc);
			}
		}
		character.update(currentMousePosition);
		character.render(gc);

	}

	public static Coord getMousePosition() {
		return currentMousePosition;
	}

	public Character getCharacter() {
		return character;
	}
}
