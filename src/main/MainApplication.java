package main;

import org.scenicview.ScenicView;

import javafx.application.Application;
import javafx.scene.Scene;
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
	private static MediaPlayer bgm;

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Slime Shooter");
		MainMenu mainMenu = new MainMenu();
		GameOver gameOver = new GameOver(mainMenu);
		gameScene = new GameScene(gameOver);
		
		root.getChildren().addAll(gameScene, mainMenu, gameOver);

		primaryStage.setResizable(false);
		primaryStage.show();
//		ScenicView.show(scene);
	}

	public static void main(String[] args) {
		playBGM();
		launch(args);
	}
	
	private static void playBGM() {
		Thread playBGM = new Thread(() ->  {
			bgm = new MediaPlayer(new Media(ClassLoader.getSystemResource("bgm.mp3").toString()));
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
