package bullet;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import main.GameScene;
import main.MainApplication;
import render.Renderable;
import util.Coord;

public class Bullet implements Renderable {

	protected Image bulletImage = new Image(ClassLoader.getSystemResource("bullets/bulletc.png").toString());
	private static final double FIRE_RADIUS = 3;

	protected Coord position;
	protected Coord absolutePosition;
	protected Coord velocity;
	protected double mass;
	protected double speed;
	protected double angle;
	protected double damage;

	public Bullet(Coord velocity) {
		this(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity, FIRE_RADIUS);
	}

	public Bullet(Bullet bullet, Coord velocity) {
		this(velocity);
		this.bulletImage = bullet.bulletImage;
		this.mass = bullet.mass;
		this.speed = bullet.speed;
		this.damage = bullet.damage * GameScene.getCharacter().getDamageMultiplier();
	}

	public Bullet(String type, double speed, int damage, double mass) {
		this.bulletImage = new Image(ClassLoader.getSystemResource("bullets/" + type + ".png").toString());
		this.mass = mass;
		this.speed = speed;
		this.damage = damage;
	}

	protected Bullet(Coord position, Coord velocity, double fireRadius) {
		this.absolutePosition = new Coord(GameScene.getCharacter().getPosition().getX() + velocity.getX() * fireRadius,
				GameScene.getCharacter().getPosition().getY() + velocity.getY() * fireRadius);
		position.setXY(position.getX() + velocity.getX() * fireRadius, position.getY() + velocity.getY() * fireRadius);
		this.position = position;
		this.velocity = velocity;
		if (velocity.getX() > 0 && velocity.getY() < 0) {
			angle = Math.atan(Math.abs(velocity.getY()) / Math.abs(velocity.getX()));
		} else if (velocity.getX() < 0 && velocity.getY() < 0) {
			angle = Math.PI - Math.atan(Math.abs(velocity.getY()) / Math.abs(velocity.getX()));
		} else if (velocity.getX() < 0 && velocity.getY() > 0) {
			angle = Math.PI + Math.atan(Math.abs(velocity.getY()) / Math.abs(velocity.getX()));
		} else {
			angle = -Math.atan(Math.abs(velocity.getY()) / Math.abs(velocity.getX()));
		}
		angle = -angle * 180 / Math.PI;
	}

	public void update() {
		updatePosition();
	}
	
	protected void updatePosition() {
		position.setXY(position.getX() + velocity.getX(), position.getY() + velocity.getY());
		absolutePosition.setXY(absolutePosition.getX() + velocity.getX(), absolutePosition.getY() + velocity.getY());
	}
	
	public static Coord initailVelocity(double speed) {
		int halfWidth = MainApplication.SCREEN_WIDTH / 2;
		int halfHeight = MainApplication.SCREEN_HEIGHT / 2;
		return new Coord(GameScene.getMousePosition().getX() - halfWidth,
				GameScene.getMousePosition().getY() - halfHeight).normalize(speed);
	}

	public Coord getPosition() {
		return position;
	}
	
	public Coord getAbsolutePosition() {
		return absolutePosition;
	}

	public Coord getVelocity() {
		return velocity;
	}

	public double getSpeed() {
		return speed;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setBulletImage(Image bulletImage) {
		this.bulletImage = bulletImage;
	}
	
	public double getWidth() {
		return bulletImage.getWidth();
	}
	
	public double getHeight() {
		return bulletImage.getHeight();
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save();
		rotate(gc, angle, tlpx, tlpy);
		gc.drawImage(image, tlpx - image.getWidth() / 2, tlpy - image.getHeight() / 2);
		gc.restore();
	}

	@Override
	public String toString() {
		return position.toString();
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		drawRotatedImage(gc, bulletImage, angle, x, y);
	}

}
