package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PauseButton extends Pane {
	
	private static final Image PAUSE = new Image("file:res/other/pausebtn.png");
	private ImageView imageView;
	
	public PauseButton() {
		imageView = new ImageView();
		imageView.setImage(PAUSE);
		this.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
		this.getChildren().add(imageView);
	}
}
