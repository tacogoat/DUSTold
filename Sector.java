import java.util.ArrayList;

public class Sector {
	private float floor;
	private float ceil;
	private ArrayList<Point> points;
	private ArrayList<Boolean> windows;

	public Sector() {
		this.floor = 0.0f;
		this.ceil = 0.0f;
		this.points = new ArrayList<Point>();
		this.windows = new ArrayList<Boolean>();
	}

	public void setFloor(float floorVal) {this.floor = floorVal;}
  public float getFloor() {return this.floor;}
	public void setCeil(float ceilVal) {this.ceil = ceilVal;}
  public float getCeil() {return this.ceil;}
  public int getLength() {return this.points.size();}
  public Point getPoint(int index) {return this.points.get(index);}
	public void addPoint(Point newPoint) {this.points.add(newPoint);}
	public void addWindow(boolean isWindow) {this.windows.add(isWindow);}

	public void shift(float xVal, float yVal) {
		for (Point i : this.points) {
			i.shift(xVal, yVal);
		}
	}

	public void rotate(float theta) {
		for (Point i : this.points) {
			i.rotate(theta);
		}
	}

	public void faceDir(Dir target) {
		for (Point i : this.points) {
			i.faceDir(target);
		}
	}

	public Point[] getAdjacentPoints(int i) {
		Point[] adjacent = new Point[2];
		int len = this.points.size();
		int index1;
		if (i - 1 == -1) {
			index1 = len - 1;
		} else {
			index1 = i - 1;
		}
		adjacent[0] = this.points.get(index1);
		int index2;
		if (i + 1 == len) {
			index2 = 0;
		} else {
			index2 = 1 + 1;
		}
		adjacent[1] = this.points.get(index2);
		return adjacent;
	}
	
	public String toString() {
		String s1 = "Floor Height: " + this.floor + "\n";
		String s2 = "Ceiling Height: " + this.ceil + "\n";
		String s3 = "";
		for (int i = 0; i < points.size(); i++) {
			s3 += this.points.get(i).toString();
			if (this.windows.get(i)) s3 += " Window";
			if (i != this.points.size() - 1) s3 += "\n";
		}
		return s1 + s2 + s3;
	}
}
