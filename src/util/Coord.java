package util;

public class Coord {
	
	private double x;
	private double y;
	
	public Coord(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord() {
		this(0, 0);
	}
	
	public Coord normalize(double c) {
		return new Coord(this.toUnitVector().getX() * c, this.toUnitVector().getY() * c);
	}
	
	public Coord toUnitVector() {
		return new Coord(this.x / norm(), this.y / norm());
	}
	
	public double norm() {
		return Math.sqrt(x * x + y * y);
	}
	
	@Override
	public String toString() {
		return "Coord(" + x + "," + y + ")";
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}
