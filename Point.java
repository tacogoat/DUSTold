public class Point {
	private float x;
	private float y;

	public Point(float xPos, float yPos) {
		this.x = xPos;
		this.y = yPos;
	}

	public Point() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	public float getX() {return this.x;}
	public float getY() {return this.y;}
	
	public void setPoint(float xPos, float yPos) {
		this.x = xPos;
		this.y = yPos;
	}

	public void shift(float xVal, float yVal) {
		this.x += xVal;
		this.y += yVal;
	}
	
	public void faceDir(Dir target) {
		switch (target) {
			case N:
				break;
			case S:
				this.x = -this.x;
				this.y = -this.y;
				break;
			case E:
				float ogX1 = this.x;
				this.x = -this.y;
				this.y = ogX1;
				break;
			case W:
				float ogX2 = this.x;
				this.x = this.y;
				this.y = -ogX2;
				break;
		}
	}

	public void rotate(float theta) {
		float ogX = this.x;
		float ogY = this.y;
		this.x = (float) (ogX * Math.cos(MoreMath.deg2rad(theta)) - ogY * Math.sin(MoreMath.deg2rad(theta)));
		this.y = (float) (ogX * Math.sin(MoreMath.deg2rad(theta)) + ogY * Math.cos(MoreMath.deg2rad(theta)));
	}

	public static Point makeStandard(Point good, int w, int h) {
		// changes (0, 0) from the center of the screen to the top left
		Point weird = new Point();
		weird.x = good.x + (w / 2.0f);
		weird.y = -good.y + (h / 2.0f);
		return weird;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}