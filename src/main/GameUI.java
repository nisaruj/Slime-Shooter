package main;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class GameUI extends StackPane {

	private static final Image HEALTH_FRAME = new Image(ClassLoader.getSystemResource("other/player_healthbar_frame.png").toString());
	private static final Image HEALTH_BAR = new Image(ClassLoader.getSystemResource("other/player_healthbar.png").toString());
	private static final Image COIN_UI = new Image(ClassLoader.getSystemResource("other/coin_ui.png").toString());
	private Canvas canvas;
	private GraphicsContext gc;

	public GameUI() {
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		Font font = Font.loadFont(ClassLoader.getSystemResource("m5x7.ttf").toString(), 45);
		gc.setFont(font);
		
		PauseUI upgradeUI = new PauseUI();
		upgradeUI.setVisible(false);
		
		PauseButton pauseButton = new PauseButton();
		pauseButton.setTranslateX(MainApplication.SCREEN_WIDTH - 60);
		pauseButton.setTranslateY(10);
		pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				GameScene.toggleGamePause();
				upgradeUI.setVisible(true);
			}

		});

		this.getChildren().addAll(canvas, pauseButton, upgradeUI);
	}

	public void render() {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		double healthPercent = (double) GameScene.getCharacter().getHealth() / GameScene.getCharacter().getMaxHealth();
		double ammoPercent = (double) GameScene.getCharacter().getWeapon().getAmmo()
				/ GameScene.getCharacter().getWeapon().getMagazineSize();

		// Health Bar
		gc.drawImage(HEALTH_BAR, 4, 0, healthPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 10,
				healthPercent * (HEALTH_BAR.getWidth() - 8), 30);

		// Ammo Bar
		if (GameScene.getCharacter().inUseDamageMultiplier()) {
			gc.drawImage(HEALTH_BAR, 4, 60, ammoPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 40,
					ammoPercent * (HEALTH_BAR.getWidth() - 8), 30);
		} else {
			gc.drawImage(HEALTH_BAR, 4, 30, ammoPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 40,
					ammoPercent * (HEALTH_BAR.getWidth() - 8), 30);
		}

		gc.drawImage(HEALTH_FRAME, 0, 0, HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight(), 10, 10,
				HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight());

		// Coin UI
		gc.drawImage(COIN_UI, 0, 0, COIN_UI.getWidth(), COIN_UI.getHeight(), 10, 80, COIN_UI.getWidth() * 0.7,
				COIN_UI.getHeight() * 0.7);

		gc.fillText(Integer.toString((int) GameScene.getCharacter().getAnimatedCoinCount()), 45, 105);
	}

}
