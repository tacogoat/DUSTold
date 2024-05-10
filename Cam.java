public class Cam {
	private int fov;
	private int w;
	private int h;
	private float dist;
	private float distU;
	private float camHeight; // in units

	private float screenDist() {
		float opposite = this.w / 2.0f;
		float pos = 1.0f / (float) Math.tan(MoreMath.deg2rad(this.fov) / 2.0f) * opposite;
		return pos;
	}

	public Cam(int fovVal, int wVal, int hVal) {
		this.fov = fovVal;
		this.w = wVal;
		this.h = hVal;
		this.dist = screenDist();
		this.distU = Units.px2u(this.dist);
		this.camHeight = 2.0f; // should be 5.0f
	}

	public float getScreenDist() {
		return this.dist;
	}

	public Point onScreenPos(float x, float y, float z, boolean force) {
		// if force is true, this will return a value regardless of whether the point is on or off screen
		// when rendering: if point is null, check if adjacent to point on screen
		// if point is adjacent, get position with force = true
		if (z < this.distU && !force) {
			return null;
		}
		else {
			x = Units.u2px(x);
			y = Units.u2px(y - camHeight);
			z = Units.u2px(z);
			float xPos = this.dist * (x / z);
			float yPos = this.dist * (y / z);
			if ((Math.abs(xPos) > this.w / 2.0f || Math.abs(yPos) > this.h / 2.0f) && !force) {
				return null;
			} else {
				Point p = new Point(xPos, yPos);
				return Point.makeStandard(p, this.w, this.h);
			}
		}
	}
}
