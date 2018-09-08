package graphics;

public class Bound {

	public float x;
	public float y;
	public float width;
	public float height;

	public Bound(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Bound() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	public boolean isCollision(Bound other) {
		if ((Math.abs(this.x - other.x) * 2 < this.width + other.width)
				&& Math.abs(this.y - other.y) * 2 < this.height + other.height) {
			//System.out.println(this.x + " AND " + other.x);
			return true;
		}
		return false;
	}
	
	public void log() {
		System.out.println("X: " + x + ", Y: " + y + ", Width: " + width + ", Height: " + height);
	}

}
