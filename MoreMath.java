public class MoreMath {
	public static float deg2rad(float deg) {
		return (float) Math.PI * (deg / 180.0f);
	}	

	public static float lerp(float a, float b, float val) {
		return a + (b - a) * val;
	}

  public static Point intersect(Point p1, Point p2, Point p3, Point p4) {
    float t = ((p1.getX() - p3.getX()) * (p3.getY() - p4.getY()) -
               (p1.getY() - p3.getY()) * (p3.getX() - p4.getX())) /
              ((p1.getX() - p2.getX()) * (p3.getY() - p4.getY()) -
               (p1.getY() - p2.getY()) * (p3.getX() - p4.getX()));
    float u = ((p1.getX() - p2.getX()) * (p1.getY() - p3.getY()) -
               (p1.getY() - p2.getY()) * (p1.getX() - p3.getX())) /
              ((p1.getX() - p2.getX()) * (p3.getY() - p4.getY()) -
               (p1.getY() - p2.getY()) * (p3.getX() - p4.getX()));
    if (t >= 0 && t <= 1) {
      return new Point(p1.getX() + t * (p2.getX() - p1.getX()),
                       p1.getY() + t * (p2.getY() - p1.getY()));
    } else if (u >= 0 && u <= 1) {
      return new Point(p3.getX() + u * (p4.getX() - p3.getX()),
                       p3.getY() + u * (p4.getY() - p3.getY()));
    } else {
      return null;
    }
  }
}
