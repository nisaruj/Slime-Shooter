import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Bullet {

	public Image bulletImage = new Image("file:res/bullets/bulletc.png");
	
	private Coord position;
	private Coord velocity;
	private double speed;
	private double angle;

	public Bullet(Coord velocity) {
		this(new Coord(MainApplication.SCREEN_WIDTH / 2, MainApplication.SCREEN_HEIGHT / 2), velocity);
	}

	public Bullet(Coord position, Coord velocity) {
		this.speed = 10;
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
	
	public Bullet(Bullet bullet, Coord velocity) {
		this(velocity);
	}
	
	public Bullet() {
		this.speed = 10;
	}

	public void update() {
		position.setX(position.getX() + velocity.getX());
		position.setY(position.getY() + velocity.getY());
	}

	public Coord getPosition() {
		return position;
	}

	public Coord getVelocity() {
		return velocity;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void render(GraphicsContext gc) {
		drawRotatedImage(gc, bulletImage, angle, position.getX(), position.getY());
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}

	@Override
	public String toString() {
		return position.toString();
	}

}
