Player you;
Map m;

int forward;
int back;
int left;
int right;

PrintWriter testWriter;

void setup() {
  fullScreen();
  m = new Map("/home/wil/Documents/sketchbook/projects/sour_ketchup/resources/maps/map.txt");
  you = new Player(110, width, height, m);

  m.generate();
  m.init();
  println(m);

  testWriter = createWriter("file.txt");
}

void draw() {
  background(20);
  stroke(255);
  strokeWeight(4);
  drawWireframeFake();
  you.move(passInput());
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

void drawWireframe() {
  for (int i = 0; i < m.getLength(); i++) {
    Sector sect = m.getSector(i);
    for (int j = 0; j < sect.getLength() - 1; j++) {
      Point p1 = sect.getPoint(i);
      Point p2 = sect.getPoint(i + 1);
      // y value of points is actually z depth

      Point realP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), false);
      if (realP1 == null) {
        Point[] test = sect.getAdjacentPoints(j);
        Point t1 = test[0];
        Point t2 = test[1];
        if (you.onScreenPos(t1.getX(), sect.getFloor(), t1.getY(), false) != null || you.onScreenPos(t2.getX(), sect.getFloor(), t2.getY(), false) != null) {
          realP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), true);
        }
      }

      Point realP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), false);
      if (realP2 == null) {
        Point[] test = sect.getAdjacentPoints(j);
        Point t1 = test[0];
        Point t2 = test[1];
        if (you.onScreenPos(t1.getX(), sect.getFloor(), t1.getY(), false) != null || you.onScreenPos(t2.getX(), sect.getFloor(), t2.getY(), false) != null) {
          realP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), true);
        }
      }

      if (realP1 != null && realP1 != null) line(realP1.getX(), realP1.getY(), realP2.getX(), realP2.getY());
    }
  }
}
