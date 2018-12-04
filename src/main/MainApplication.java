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
	private static GameScene gameScene;

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");

		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		gameScene = new GameScene();
		MainMenu mainMenu = new MainMenu(gameScene);
		
		root.getChildren().addAll(canvas, mainMenu, gameScene);

		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void restart() {
		gameScene = new GameScene();
	}
}
