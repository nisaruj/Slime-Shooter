package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

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
		MainMenu mainMenu = new MainMenu();
		gameScene = new GameScene(mainMenu);
		
		root.getChildren().addAll(gameScene, mainMenu);

		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		playBGM();
		launch(args);
	}
	
	private static void playBGM() {
		Thread playBGM = new Thread(() ->  {
			MediaPlayer bgm = new MediaPlayer(new Media(ClassLoader.getSystemResource("bgm.mp3").toString()));
			bgm.setOnReady(() -> {
				bgm.setOnEndOfMedia(() -> {
					bgm.seek(Duration.ZERO);
				});
				bgm.play();
			});
		});
		playBGM.start();
	}

}
