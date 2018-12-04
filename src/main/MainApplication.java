package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 700;
	public static final double SIZE_MULTIPLIER = 1;

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");

		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		GameScene gameScene = new GameScene();

		// TODO: Game Menu
		root.getChildren().addAll(canvas, gameScene);

		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
