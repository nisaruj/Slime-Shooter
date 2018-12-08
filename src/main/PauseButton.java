package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseButton extends ImageView {
	
	private static final Image PAUSE = new Image(ClassLoader.getSystemResource("other/pausebtn.png").toString());
	
	public PauseButton() {
		this.setImage(PAUSE);
	}
}
