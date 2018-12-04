import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy implements Renderable {

	private static final int MONSTER_SIZE = 40;
	public static final int MAX_MOVE_SPEED = 10;
	private int isMoving;
	private int movingSpeed;

	private Coord position;
	private Image[] slimeImage;
	private int health;
	private boolean isDead;

	public Enemy(int x, int y) {
		this("slime1", x, y);
	}

	public Enemy(String name, int x, int y) {
		slimeImage = new Image[3];
		slimeImage[0] = new Image("file:res/monsters/" + name + "_front.png");
		slimeImage[1] = new Image("file:res/monsters/" + name + "_side.png");
		slimeImage[2] = new Image("file:res/monsters/" + name + "_back.png");
		this.position = new Coord(x, y);
		this.health = 100;
		this.isDead = false;
		this.isMoving = 0;
		this.movingSpeed = 3;
	}

	public void takeDamage(int damage) {
		if (isDead) {
			return;
		}
		health = Math.max(0, health - damage);
		if (health == 0) {
			isDead = true;
		}
	}

	public boolean isCollideBullet(Bullet bullet) {
		int posX = (int) bullet.getAbsolutePosition().getX();
		int posY = (int) bullet.getAbsolutePosition().getY();
		boolean isCollide = posX > position.getX() - MONSTER_SIZE / 2 && posX < position.getX() + MONSTER_SIZE / 2
				&& posY > position.getY() - MONSTER_SIZE / 2 && posY < position.getY() + MONSTER_SIZE / 2;
		if (isCollide) {
			takeDamage(bullet.damage);
			// System.out.println("HIT!");
		}
		return isCollide;
	}

	public boolean isCollidePlayer() {
		int posX = (int) GameScene.getCharacter().getPosition().getX();
		int posY = (int) GameScene.getCharacter().getPosition().getY();
		return posX > position.getX() - MONSTER_SIZE / 2 && posX < position.getX() + MONSTER_SIZE / 2
				&& posY > position.getY() - MONSTER_SIZE / 2 && posY < position.getY() + MONSTER_SIZE / 2;
	}

	public boolean isDead() {
		return isDead;
	}

	public Coord getPosition() {
		return position;
	}

	public void update() {
		isMoving++;
	}

	@Override
	public void render(GraphicsContext gc, int x, int y) throws Exception {
		int moveFrame = isMoving / (MAX_MOVE_SPEED - movingSpeed) % 4;

		if (x < -MONSTER_SIZE || y < -MONSTER_SIZE || x > MainApplication.SCREEN_WIDTH
				|| y > MainApplication.SCREEN_HEIGHT) {
			throw new Exception("Render out of screen");
		}
		gc.drawImage(slimeImage[0], moveFrame * MONSTER_SIZE, 0, MONSTER_SIZE, MONSTER_SIZE, x - MONSTER_SIZE / 2,
				y - MONSTER_SIZE / 2, MONSTER_SIZE, MONSTER_SIZE);
	}
}
