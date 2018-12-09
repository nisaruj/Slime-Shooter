package main;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

public class MainMenu extends StackPane {

	private static final Image MAIN_MENU = new Image(ClassLoader.getSystemResource("main.png").toString());

	public MainMenu() {
		BackgroundImage myBI = new BackgroundImage(MAIN_MENU, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
		PlayButton playButton = new PlayButton();
		playButton.setTranslateY(100);

		playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				playGame();
			}

		});
		
		this.getChildren().add(playButton);
	}

	public void playGame() {
		this.setVisible(false);
		GameScene.setPause(false);
	}

	public void showMainMenu() {
		this.setVisible(true);
	}

}
