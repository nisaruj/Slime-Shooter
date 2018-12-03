import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Weapon {
	
	protected String name;
	protected Image[] weaponImage = new Image[5];
	protected int damage;
	protected Bullet bullet;
	
	public Weapon(String name) {
		this.name = name;
		this.bullet = new Bullet();
		this.damage = 10;
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
	
	public abstract Bullet shoot();
	public abstract boolean isReady();
	public abstract void update();
}
