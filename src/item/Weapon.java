package item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import bullet.Bullet;
import main.GameScene;
import main.MainApplication;


public abstract class Weapon extends Item {
	
	protected String name;
	protected Image[] weaponImage = new Image[5];
	protected Bullet bullet;
	
	public Weapon(String name) {
		this(name, 0, 0);
	}
	
	public Weapon(String name, int x, int y) {
		super(name, x, y);
		this.name = name;
		this.bullet = new Bullet("bulletc", 10, 10, 0.1);
		this.weaponImage[0] = new Image("file:res/weapons/" + name + "/" + name + "_down.png");
		this.weaponImage[1] = new Image("file:res/weapons/" + name + "/" + name + "_diagdown.png");
		this.weaponImage[2] = new Image("file:res/weapons/" + name + "/" + name + "_side.png");
		this.weaponImage[3] = new Image("file:res/weapons/" + name + "/" + name + "_diagup.png");
		this.weaponImage[4] = new Image("file:res/weapons/" + name + "/" + name + "_up.png");
	}
	
	public void render(GraphicsContext gc, int facingDirection, int mirrorDirection, boolean renderFirst) {
		double imgWidth = weaponImage[facingDirection].getWidth();
		double imgHeight = weaponImage[facingDirection].getHeight();
		double holdOffset = renderFirst ? +10 : +20;
		
		gc.drawImage(weaponImage[facingDirection], 0, 0, imgWidth, imgHeight,
				MainApplication.SCREEN_WIDTH / 2
						- imgWidth / 2 * mirrorDirection * MainApplication.SIZE_MULTIPLIER,
				MainApplication.SCREEN_HEIGHT / 2 - imgHeight / 2 + holdOffset,
				imgWidth * MainApplication.SIZE_MULTIPLIER * mirrorDirection,
				imgHeight * MainApplication.SIZE_MULTIPLIER);
	}
	
	@Override
	public void equip() {
		GameScene.getCharacter().setWeapon(this);
	}
	
	public String getName() {
		return name;
	}
	
	public abstract Object shoot();
	public abstract boolean isReady();
	public abstract void update();

}
