package item;

import javafx.scene.canvas.GraphicsContext;
import main.MainApplication;

public abstract class Powerup extends Item {
	
	protected int frameCount;
	protected int currentFrame;
	protected int IMAGE_SIZE = 40;
	protected static final int RENDER_SPEED = 7;
	
	public Powerup(String name, int x, int y) {
		super(name, x, y);
		this.currentFrame = 0;
		this.frameCount = 0;
	}
	
	public void update() {
		frameCount++;
		currentFrame = frameCount / RENDER_SPEED % 4;
	}
	
	public boolean isExpired() {
		return frameCount > 1200;
	}
	
	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		if (x < -IMAGE_SIZE || y < -IMAGE_SIZE || x > MainApplication.SCREEN_WIDTH || y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		gc.drawImage(itemImage, currentFrame * IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE, x - IMAGE_SIZE / 2, y - IMAGE_SIZE / 2, IMAGE_SIZE,
				IMAGE_SIZE);
	}
	
	public abstract void equip();
	
}
