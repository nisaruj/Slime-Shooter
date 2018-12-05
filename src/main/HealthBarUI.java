package main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class HealthBarUI extends StackPane {

	private static final Image HEALTH_FRAME = new Image("file:res/other/player_healthbar_frame.png");
	private static final Image HEALTH_BAR = new Image("file:res/other/player_healthbar.png");
	private Canvas canvas;
	private GraphicsContext gc;

	public HealthBarUI() {
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		this.getChildren().add(canvas);
	}

	public void render() {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		double healthPercent = (double)GameScene.getCharacter().getHealth() / GameScene.getCharacter().getMaxHealth();
		
		// Health Bar
		gc.drawImage(HEALTH_BAR, 4, 0, healthPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 10,
				healthPercent * (HEALTH_BAR.getWidth() - 8), 30);
		
		// Ammo Bar
		gc.drawImage(HEALTH_BAR, 4, 30, healthPercent * (HEALTH_BAR.getWidth() - 8), 30, 14, 40,
				healthPercent * (HEALTH_BAR.getWidth() - 8), 30);
		
		gc.drawImage(HEALTH_FRAME, 0, 0, HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight(), 10, 10,
				HEALTH_FRAME.getWidth(), HEALTH_FRAME.getHeight());
	}

}
