import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	private ArrayList<Sector> sectors;
	private Point startPos;
	private Dir startDir;
	private ArrayList<String> src;

	public Map(ArrayList<String> arr) {
		this.sectors = new ArrayList<Sector>();
		this.startPos = new Point(0.0f, 0.0f);
		this.startDir = Dir.N;
		this.src = arr;
	}

	public Point getStartPos() {return this.startPos;}
	public Dir getStartDir() {return this.startDir;}
  public int getLength() {return this.sectors.size();}
  public Sector getSector(int index) {return this.sectors.get(index);}
	
	public void generate() {
    int currentSector = -1;
    boolean isWindow = false;
    int i = 0;
    while (i < this.src.size()) {
      String current = this.src.get(i);
      switch (current) {
        case "Sector":
          currentSector++;
          this.sectors.add(new Sector());
          break;
        case "Floor":
          this.sectors.get(currentSector).setFloor(Float.parseFloat(this.src.get(i + 1)));
          i++;
          break;
        case "Ceil":
          this.sectors.get(currentSector).setCeil(Float.parseFloat(this.src.get(i + 1)));
          i++;
          break;
        case "StartPos":
          this.startPos.setPoint(Float.parseFloat(this.src.get(i + 1)), Float.parseFloat(this.src.get(i + 2)));
          i += 2;
          break;
        case "StartDir":
          this.startDir = Dir.valueOf(this.src.get(i + 1));
          i++;
          break;
        case "Window":
          isWindow = true;
          break;
        default:
          this.sectors.get(currentSector).addPoint(new Point(Float.parseFloat(this.src.get(i)), Float.parseFloat(this.src.get(i + 1))));
          i++;
          this.sectors.get(currentSector).addWindow(isWindow);
          isWindow = false;
      }
      i++;
    }
	}

	public void init() {
		for (Sector i : this.sectors) {
			i.shift(-startPos.getX(), -startPos.getY());
			i.faceDir(this.startDir);
		}
	}

	public void shift(float xVal, float yVal) {
		for (Sector i : this.sectors) {
			i.shift(xVal, yVal);
		}
	}

	public void rotate(float theta) {
		for (Sector i : this.sectors) {
			i.rotate(theta);
		}
	}

  public static ArrayList<String> parseStringArr(String[] arr) {
    ArrayList<String> output = new ArrayList<String>();
    for (int i = 0; i < arr.length; i++) {
      Scanner s = new Scanner(arr[i]);
      while (s.hasNext()) {
        output.add(s.next());
      }
      s.close();
    }
    return output;
  }

	public String toString() {
		String s1 = "Map:\n";
		String s2 = "Start Position: " + this.startPos + "\n";
		String s3 = "Start Direction: " + this.startDir + "\n\n";
		String str = s1 + s2 + s3;
		for (int i = 0; i < this.sectors.size(); i++) {
			str += "Sector " + i + ";\n";
			str += this.sectors.get(i).toString();
			if (i != sectors.size() - 1) str += "\n\n";
		}
		return str;
	}
}
