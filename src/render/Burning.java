package render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainApplication;
import util.Coord;

public class Burning extends Effect {
	
	private static final Image BURNING = new Image("file:res/other/burning.png");
	private static final int IMAGE_SIZE = 40;
	private static final int RENDER_SPEED = 5;
	private int frameCount = 0;
	private int currentFrame = 0;
	
	
	public Burning(int x, int y) {
		super(x, y);
		this.frameCount = 0;
	}
	
	public Burning(Coord position) {
		super(position);
		this.frameCount = 0;
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		if (x < -IMAGE_SIZE || y < -IMAGE_SIZE || x > MainApplication.SCREEN_WIDTH
				|| y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		gc.drawImage(BURNING, (currentFrame % 4) * IMAGE_SIZE, 0, IMAGE_SIZE,
				33, x - IMAGE_SIZE / 2, y - 16, IMAGE_SIZE, 33);
		
	}

	@Override
	public void update() {
		frameCount++;
		currentFrame = frameCount / RENDER_SPEED;
		
	}

	@Override
	public boolean isRenderFinished() {
		return currentFrame > 20;
	}
}
