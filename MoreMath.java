public class MoreMath {
	public static float deg2rad(float deg) {
		return (float) Math.PI * (deg / 180.0f);
	}	

	public static float lerp(float a, float b, float val) {
		return a + (b - a) * val;
	}
}