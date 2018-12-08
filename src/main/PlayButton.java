package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayButton extends ImageView {
	
	private static final Image PLAY = new Image(ClassLoader.getSystemResource("other/startbtn.png").toString());

	public PlayButton() {
		this.setImage(PLAY);
	}
	
}
