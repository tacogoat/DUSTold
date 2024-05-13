import java.awt.Robot;
import java.awt.AWTException;

Player you;
Map m;

Robot r;

int forward;
int back;
int left;
int right;

float mouseSens = 0.1;

PrintWriter testWriter;

void setup() {
  fullScreen();
  m = new Map("C:\\Users\\335863\\Documents\\Processing\\projects\\DUST\\resources\\maps\\map.txt");
  you = new Player(110, width, height, m);

  m.generate();
  m.init();
  println(m);
  
  try {
    r = new Robot();
  } catch (AWTException e) {
    e.printStackTrace();
  }
  
  r.mouseMove(width / 2, height / 2);
  noCursor();
}

void draw() {
  background(20);
  stroke(255);
  strokeWeight(2);
  drawWireframe();
  you.move(passInput());
  
  float lookMag = (mouseX - width / 2) * mouseSens;
  you.look(lookMag);
  r.mouseMove(width / 2, height / 2);
  
  strokeWeight(8);
  line(width / 2, height / 2, width / 2, height / 2);
}

void drawWireframe() {
  for (int i = 0; i < m.getLength(); i++) {
    Sector sect = m.getSector(i);
    for (int j = 0; j < sect.getLength(); j++) {
      Point p1 = sect.getPoint(j);
      Point p2;
      if (j == sect.getLength() - 1) {
        p2 = sect.getPoint(0);
      } else {
        p2 = sect.getPoint(j + 1);
      }

      // if one of the points is null, find out where the line intersects with the screen
      // if both of the points are null, check if the line intersects the screen at two points

      Point floorP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), true);
      Point floorP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), true);
      Point ceilP1 = you.onScreenPos(p1.getX(), sect.getCeil(), p1.getY(), true);
      Point ceilP2 = you.onScreenPos(p2.getX(), sect.getCeil(), p2.getY(), true);

      line(floorP1.getX(), floorP1.getY(), floorP2.getX(), floorP2.getY());
      line(ceilP1.getX(), ceilP1.getY(), ceilP2.getX(), ceilP2.getY());
      line(floorP1.getX(), floorP1.getY(), ceilP1.getX(), ceilP1.getY());
    }
  }
}

void drawWireframeFake() {
  for (int i = 0; i < m.getLength(); i++) {
    Sector sect = m.getSector(i);
    for (int j = 0; j < sect.getLength() - 1; j++) {
      Point p1 = sect.getPoint(j);
      Point p2 = sect.getPoint(j + 1);
      // y value of points is actually z depth

      Point realP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), true);

      Point realP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), true);

      if (realP1 != null && realP1 != null) line(realP1.getX(), realP1.getY(), realP2.getX(), realP2.getY());
    }
    Point p1 = sect.getPoint(sect.getLength() - 1);
    Point p2 = sect.getPoint(0);
    // y value of points is actually z depth

    Point realP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), true);

    Point realP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), true);

    if (realP1 != null && realP1 != null) line(realP1.getX(), realP1.getY(), realP2.getX(), realP2.getY());
  }
}

void keyPressed() {
  if (keyCode == UP) {forward = -1;}
  if (keyCode == DOWN) {back = 1;}
  if (keyCode == LEFT) {left = 1;}
  if (keyCode == RIGHT) {right = -1;}
}

void keyReleased() {
  if (keyCode == UP) {forward = 0;}
  if (keyCode == DOWN) {back = 0;}
  if (keyCode == LEFT) {left = 0;}
  if (keyCode == RIGHT) {right = 0;}

}

Point passInput() {
  return new Point(left + right, forward + back);
}
