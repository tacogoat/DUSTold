public class Player extends Cam {
	private float vX, vZ;
	private float x, z;
	private float theta;
	private Map m;
  private int headBob;

	private static final float v = 16.0f; // units per second
	private static final float vEase = 0.2f; // lerp interval, must be between 0.0f and 1.0f
	
	public Player(int fovVal, int wVal, int hVal, Map mapVal) {
		super(fovVal, wVal, hVal);
		this.m = mapVal;
		this.x = m.getStartPos().getX();
		this.z = m.getStartPos().getY();
		this.theta = m.getStartDir().deg();
		
		this.vX = 0.0f;
		this.vZ = 0.0f;
    
    this.headBob = 0;
	}

	public void move(Point input) {
		// handle input in processing, call this method to move
		float xMag;
		float zMag;
		if (input.getX() != 0.0f) {
			xMag = input.getX() * v / 60;
		} else {
			xMag = 0.0f;
		}
		if (input.getY() != 0.0f) {
			zMag = input.getY() * v / 60;
		} else {
			zMag = 0.0f;
		}
		this.vX = MoreMath.lerp(this.vX, xMag, vEase);
		this.vZ = MoreMath.lerp(this.vZ, zMag, vEase);

		// move position in map (not real)
		// think this works probably
		this.x += (float) (vX * Math.cos(MoreMath.deg2rad(this.theta)) + vZ * Math.sin(MoreMath.deg2rad(this.theta))); 
		this.z += (float) (vX * Math.sin(MoreMath.deg2rad(this.theta)) - vZ * Math.cos(MoreMath.deg2rad(this.theta)));

		// move map around player
		this.m.shift(vX, vZ);
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

  public void bob() { 
    // view bobbing
    if (Math.abs(this.vX) > 0.1f || Math.abs(this.vZ) > 0.1f) {
      this.setCamHeight(2.0f - 0.05f * (float) (Math.cos(this.headBob / 60.0f * (2.0f * (float) Math.PI)) - 1.0f));
    } else {
      this.setCamHeight(MoreMath.lerp(this.getCamHeight(), 2.0f, 0.3f));
      this.headBob = 0;
    }

    if (this.headBob == 60) {
      this.headBob = 0;
    } else {
      this.headBob += 2;
    }
  }
}
