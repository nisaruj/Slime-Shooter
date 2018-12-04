package main;

import javafx.scene.layout.StackPane;

public class MainMenu extends StackPane {
	
	private GameScene gameScene;
	
	public MainMenu(GameScene gameScene) {
		this.gameScene = gameScene;
		
		playGame(); // Calling this method will start the game.
	}
	
	public void playGame() {
		this.setVisible(false);
		gameScene.startGameLoop();
	}
	
}
