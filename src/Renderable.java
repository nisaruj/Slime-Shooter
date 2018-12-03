import javafx.scene.canvas.GraphicsContext;

//Implement this class when render something on the screen.

public interface Renderable {

	public void render(GraphicsContext gc, int x, int y) throws Exception;

}
