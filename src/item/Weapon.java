package item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import bullet.Bullet;
import main.GameScene;
import main.MainApplication;


public abstract class Weapon extends Item {
	
	protected String name;
	protected int ammo;
	protected int magazineSize;
	protected int reloadSize;
	protected int reloadCost;
	protected Image[] weaponImage = new Image[5];
	protected Bullet bullet;
	
	public Weapon(String name) {
		this(name, 0, 0, 100);
	}
	
	public Weapon(String name, int x, int y, int ammo) {
		super(name, x, y);
		this.name = name;
		this.magazineSize = ammo;
		this.ammo = ammo;
		this.weaponImage[0] = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_down.png").toString());
		this.weaponImage[1] = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_diagdown.png").toString());
		this.weaponImage[2] = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_side.png").toString());
		this.weaponImage[3] = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_diagup.png").toString());
		this.weaponImage[4] = new Image(ClassLoader.getSystemResource("weapons/" + name + "/" + name + "_up.png").toString());
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
	
	public int getAmmo() {
		return ammo;
	}
	
	public void refillAmmo() {
		this.ammo = Math.min(this.magazineSize, this.ammo + this.reloadSize);
	}
	
	public boolean isFull() {
		return this.ammo == this.magazineSize;
	}
	
	public int getMagazineSize() {
		return magazineSize;
	}
	
	public int getReloadCost() {
		return reloadCost;
	}
	
	public int getReloadSize() {
		return reloadSize;
	}
	
	public abstract Object shoot();
	public abstract boolean isReady();
	public abstract void update();

}
