package main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class UpgradeUI extends StackPane {

	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 500;
	private Canvas canvas;
	private GraphicsContext gc;

	public UpgradeUI() {
		canvas = new Canvas(MainApplication.SCREEN_WIDTH, MainApplication.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.DARKCYAN);
		gc.fillRect(MainApplication.SCREEN_WIDTH / 2 - WINDOW_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2 - WINDOW_HEIGHT / 2, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.getChildren().add(canvas);
		// this.setVisible(false);
	}

}
