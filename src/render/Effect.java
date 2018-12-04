package render;

import javafx.scene.canvas.GraphicsContext;
import util.Coord;

public abstract class Effect implements Renderable {
	
	protected Coord position;
	
	public Effect(int x, int  y) {
		position = new Coord(x, y);
	}
	
	public Effect(Coord position) {
		this.position = position;
	}
	
	public Coord getPosition() {
		return position;
	}
	
	@Override
	public abstract void render(GraphicsContext gc, int x, int y) throws Exception;
	public abstract void update();
	public abstract boolean isRenderFinished();

}
