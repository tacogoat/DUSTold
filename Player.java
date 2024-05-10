public class Player extends Cam {
	private float vX, vY;
	private float x, y;
	private float theta;
	private Map m;

	private static final float v = 12.0f; // units per second
	private static final float vEase = 0.1f; // lerp interval, must be between 0.0f and 1.0f
	
	public Player(int fovVal, int wVal, int hVal, Map mapVal) {
		super(fovVal, wVal, hVal);
		this.m = mapVal;
		this.x = m.getStartPos().getX();
		this.y = m.getStartPos().getY();
		this.theta = m.getStartDir().deg();
		
		this.vX = 0.0f;
		this.vY = 0.0f;
	}

	public void move(Point input) {
		// handle input in processing, call this method to move
		float xMag;
		float yMag;
		if (input.getX() != 0.0f) {
			xMag = input.getX() * v / 60;
		} else {
			xMag = 0.0f;
		}
		if (input.getY() != 0.0f) {
			yMag = input.getY() * v / 60;
		} else {
			yMag = 0.0f;
		}
		this.vX = MoreMath.lerp(this.vX, xMag, vEase);
		this.vY = MoreMath.lerp(this.vY, yMag, vEase);

		// move position in map (not real)
		// think this works probably
		this.x += (float) (vX * Math.cos(MoreMath.deg2rad(this.theta)) + vY * Math.sin(MoreMath.deg2rad(this.theta))); 
		this.y += (float) (vX * Math.sin(MoreMath.deg2rad(this.theta)) - vY * Math.cos(MoreMath.deg2rad(this.theta)));

		// move map around player
		this.m.shift(vX, vY);
	}

	public void look(float dTheta) {
		// take amount that mouse moves each frame, multiply by constant to get theta
		// positive is left, negative is right because trig
		this.theta += dTheta;
		this.m.rotate(dTheta);

		// makes sure theta stays [0.0f, 360.0f)
		while (this.theta >= 360.0f) {
			this.theta -= 360.0f;
		}
		while (this.theta < 0.0f) {
			this.theta += 360.0f;
		}
	}
}
