package item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameScene;
import main.MainApplication;
import render.Renderable;
import util.Coord;

public abstract class Item implements Renderable {
	
	protected Coord position;
	protected Image itemImage;
	
	public Item() {
		this("mg", 0, 0);
	}
	
	public Item(String name, int x, int y) {
		this.position = new Coord(x, y);
		if (this instanceof Weapon) {
			itemImage = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_side.png").toString());
		} else if (this instanceof Powerup) {
			itemImage = new Image(ClassLoader.getSystemResource("powerups/powerup_" + name + ".png").toString());
		}
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		if (x < -itemImage.getWidth() || y < -itemImage.getHeight() || x > MainApplication.SCREEN_WIDTH || y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		gc.drawImage(itemImage, 0, 0, itemImage.getWidth(), itemImage.getHeight(), x - itemImage.getWidth() / 2, y - itemImage.getHeight() / 2, itemImage.getWidth(),
					itemImage.getHeight());
	}
	
	public boolean isCollidePlayer() {
		int posX = (int) GameScene.getHumanPlayer().getPosition().getX();
		int posY = (int) GameScene.getHumanPlayer().getPosition().getY();
		return posX > position.getX() - itemImage.getWidth() / 2 && posX < position.getX() + itemImage.getWidth() / 2 &&
				posY > position.getY() - itemImage.getHeight() / 2 &&  posY < position.getY() + itemImage.getHeight() / 2;
	}
	
	public Coord getPosition() {
		return position;
	}
	
	public abstract void equip();
	
}