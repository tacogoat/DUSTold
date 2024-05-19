// this is old and probably not useful anymore

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class OldMap {
	private ArrayList<Sector> sectors;
	private Point startPos;
	private Dir startDir;
	private String src;

	public OldMap(String s) {
		this.sectors = new ArrayList<Sector>();
		this.startPos = new Point(0.0f, 0.0f);
		this.startDir = Dir.N;
		this.src = s;
	}

	public Point getStartPos() {return this.startPos;}
	public Dir getStartDir() {return this.startDir;}
  public int getLength() {return this.sectors.size();}
  public Sector getSector(int index) {return this.sectors.get(index);}
	
	public void generate() {
		try {
      File f = new File(this.src);
			Scanner mapReader = new Scanner(f);
			int currentSector = -1;
			
			boolean isWindow = false;
			while (mapReader.hasNext()) {
				String current = mapReader.next();
				switch (current) {
					case "Sector":
						currentSector++;
						this.sectors.add(new Sector());
						break;
					case "Floor":
						this.sectors.get(currentSector).setFloor(Float.parseFloat(mapReader.next()));
						break;
					case "Ceil":
						this.sectors.get(currentSector).setCeil(Float.parseFloat(mapReader.next()));				
						break;
					case "StartPos":
						this.startPos.setPoint(Float.parseFloat(mapReader.next()), Float.parseFloat(mapReader.next()));
						break;
					case "StartDir":
						this.startDir = Dir.valueOf(mapReader.next());
						break;
					case "Window":
						isWindow = true;
						break;
					default:
						this.sectors.get(currentSector).addPoint(new Point(Float.parseFloat(current), Float.parseFloat(mapReader.next())));
						this.sectors.get(currentSector).addWindow(isWindow);
						isWindow = false;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("no map?");
			e.printStackTrace();
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
