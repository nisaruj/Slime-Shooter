package render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class Explosion extends Effect {

	private static final Image EXPLOSION = new Image(ClassLoader.getSystemResource("other/explosion.png").toString());
	private static final int IMAGE_SIZE = 64;
	private static final int RENDER_SPEED = 5;
	private int frameCount;
	private int currentFrame;

	public Explosion(int x, int y) {
		super(x, y);
		this.frameCount = 0;
	}
	
	public Explosion(Coord position) {
		super(position);
		this.frameCount = 0;
	}
	
	@Override
	public boolean isRenderFinished() {
		return currentFrame > 16;
	}

	@Override
	public void update() {
		frameCount++;
		currentFrame = frameCount / RENDER_SPEED;
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		if (x < -IMAGE_SIZE || y < -IMAGE_SIZE || x > MainApplication.SCREEN_WIDTH
				|| y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		gc.drawImage(EXPLOSION, (currentFrame % 4) * IMAGE_SIZE, (currentFrame / 4) * IMAGE_SIZE, IMAGE_SIZE,
				IMAGE_SIZE, x - IMAGE_SIZE, y - IMAGE_SIZE, IMAGE_SIZE * 2, IMAGE_SIZE * 2);
	}

}
