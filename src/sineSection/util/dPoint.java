package sineSection.util;

public class dPoint {
	
	public double x, y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(x);
		out.append(", ");
		out.append(y);
		return out.toString();
	}

}
