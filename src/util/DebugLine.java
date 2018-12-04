package util;
import javafx.scene.shape.Line;

public class DebugLine extends Line {
	
	private int halfWidth;
	private int halfHeight;
	private double angle;
	
	public DebugLine(int width, int height) {
		super();
		halfWidth = width / 2;
		halfHeight = height / 2;
	}
	
	public void redraw(double mouseX, double mouseY) {
		angle = Math.atan((halfHeight - mouseY) / (mouseX - halfWidth));
		this.setStartX(0);
		this.setStartY(0);
		this.setEndX(halfWidth - (mouseX - halfWidth) * 100);
		this.setEndY(halfHeight + (halfHeight - mouseY) * 100);
	}
	
	public double getAngle() {
		return angle * 180 / Math.PI;
	}

}
