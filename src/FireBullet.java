import javafx.scene.image.Image;

public class FireBullet extends Bullet {
	
	protected int lifeTime;
	
	public FireBullet(double speed, int damage, int lifeTime) {
		super(speed, damage);
		this.lifeTime = lifeTime;
		this.bulletImage = new Image("file:res/bullets/flamethrower_bullet.png");
	}
	
	public FireBullet(FireBullet bullet, Coord velocity) {
		super(velocity);
		this.bulletImage = new Image("file:res/bullets/flamethrower_bullet.png");
		this.lifeTime = bullet.lifeTime;
		this.speed = bullet.speed;
		this.damage = bullet.damage;
	}
	
	public boolean isDisappear() {
		return lifeTime < 0;
	}
	
	@Override
	public void update() {
		position.setX(position.getX() + velocity.getX());
		position.setY(position.getY() + velocity.getY());
		lifeTime--;
	}
	
}
