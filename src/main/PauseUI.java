package main;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PauseUI extends StackPane {

	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 700;
	private Canvas canvas;
	private GraphicsContext gc;

	public PauseUI() {
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		Font font = Font.loadFont("file:res/m5x7.ttf", 100);
		gc.setFont(font);
		
		gc.setFill(new Color(0, 0, 0, 0.4));
		gc.fillRect(MainApplication.SCREEN_WIDTH / 2 - WINDOW_WIDTH / 2,
				MainApplication.SCREEN_HEIGHT / 2 - WINDOW_HEIGHT / 2, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.web("#CCC"));
		gc.fillText("PAUSED", MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2);
		
		this.setOnMouseClicked(e -> {
			GameScene.toggleGamePause();
			this.setVisible(false);
		});
		
		this.getChildren().add(canvas);
	}

}
