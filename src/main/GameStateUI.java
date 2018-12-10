package main;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class GameStateUI extends StackPane {

	private PauseUI upgradeUI = new PauseUI();
	private GraphicsContext gc;

	public GameStateUI() {
		Canvas canvas;
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		Font font = Font.loadFont(ClassLoader.getSystemResource("m5x7.ttf").toString(), 45);
		gc.setFont(font);

		upgradeUI.setVisible(false);

		PauseButton pauseButton = new PauseButton();
		pauseButton.setTranslateX(MainApplication.SCREEN_WIDTH / 2 - 30);
		pauseButton.setTranslateY(30 - MainApplication.SCREEN_HEIGHT / 2);
		pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				GameScene.setPause(true);
			}

		});

		this.getChildren().addAll(canvas, pauseButton, upgradeUI);
	}

	public void render() {
		final Image HEALTH_FRAME = new Image(
				ClassLoader.getSystemResource("other/player_healthbar_frame.png").toString());
		final Image HEALTH_BAR = new Image(ClassLoader.getSystemResource("other/player_healthbar.png").toString());
		final Image COIN_UI = new Image(ClassLoader.getSystemResource("other/coin_ui.png").toString());
		String[] WeaponNameList = { "cannon", "flamethrower", "mg", "matter", "rocket", "shotgun" };
		character.HumanPlayer player = GameScene.getHumanPlayer();

		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		double healthPercent = (double) player.getHealth() / player.getMaxHealth();
		double ammoPercent = (double) player.getWeapon().getAmmo() / player.getWeapon().getMagazineSize();

		// Health Bar
		gc.drawImage(HEALTH_BAR, 4, 0, healthPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 10,
				healthPercent * (HEALTH_BAR.getWidth() - 8), 30);

		// Ammo Bar
		if (player.inUseDamageMultiplier()) {
			gc.drawImage(HEALTH_BAR, 4, 60, ammoPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 40,
					ammoPercent * (HEALTH_BAR.getWidth() - 8), 30);
		} else {
			gc.drawImage(HEALTH_BAR, 4, 30, ammoPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 40,
					ammoPercent * (HEALTH_BAR.getWidth() - 8), 30);
		}

		gc.drawImage(HEALTH_FRAME, 0, 0, HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight(), 10, 10,
				HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight());

		// Coin UI
		gc.drawImage(COIN_UI, 0, 0, COIN_UI.getWidth(), COIN_UI.getHeight(), 10, 120, COIN_UI.getWidth() * 0.7,
				COIN_UI.getHeight() * 0.7);
		gc.fillText(Integer.toString((int) player.getAnimatedCoinCount()) + "  "
				+ Integer.toString((int) player.getWeapon().getAmmo()) + "/"
				+ Integer.toString((int) player.getWeapon().getMagazineSize()), 45, 145);
		gc.fillText("Press 'E' to reload", MainApplication.SCREEN_WIDTH - 280, 100);
		
		gc.setFont(Font.loadFont(ClassLoader.getSystemResource("m5x7.ttf").toString(), 20));

		// Weapon Bar
		for (int i = 0; i < WeaponNameList.length; i++) {
			String weaponName = WeaponNameList[i];
			if (player.getWeaponNameList().contains(weaponName)) {
				Image weaponThumbnail = new Image(ClassLoader
						.getSystemResource("weapons/" + weaponName + "/" + weaponName + "_side.png").toString());
				gc.drawImage(weaponThumbnail, 0, 0, weaponThumbnail.getWidth(), weaponThumbnail.getHeight(),
						10 + (i * 80), 85, weaponThumbnail.getWidth(), weaponThumbnail.getHeight());
				gc.fillText(Integer.toString((int) player.getWeapon(weaponName).getAmmo()) + "/"
						+ Integer.toString((int) player.getWeapon(weaponName).getMagazineSize()), 10 + (i * 80), 110);
			} else {
				Image weaponThumbnail = new Image(ClassLoader
						.getSystemResource("weapons/" + weaponName + "/" + weaponName + "_bw.png").toString());
				gc.drawImage(weaponThumbnail, 0, 0, weaponThumbnail.getWidth(), weaponThumbnail.getHeight(),
						10 + (i * 80), 85, weaponThumbnail.getWidth(), weaponThumbnail.getHeight());

			}

		}
		gc.setFont(Font.loadFont(ClassLoader.getSystemResource("m5x7.ttf").toString(), 25));
		gc.fillText(
				"(" + Integer.toString((int) player.getWeapon().getReloadSize()) + " Ammo" + "/"
						+ Integer.toString((int) player.getWeapon().getReloadCost()) + " Coins)",
				MainApplication.SCREEN_WIDTH - 160, 130);
		gc.setFont(Font.loadFont(ClassLoader.getSystemResource("m5x7.ttf").toString(), 45));
	}

	public void showPause(boolean isShow) {
		upgradeUI.setVisible(isShow);
	}

}
