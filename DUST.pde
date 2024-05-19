import java.util.ArrayList;

import java.awt.Robot;
import java.awt.AWTException;

import java.io.File;
import java.io.FileNotFoundException;

Player you;
Map m;

Robot r;

int forward;
int back;
int left;
int right;

float mouseSens = 0.2;

PImage bird;

void setup() {
  fullScreen(P2D);

  // generate and initialize map
  String[] testMapArr = loadStrings("maps/map.txt");
  ArrayList<String> testMap = Map.parseStringArr(testMapArr);
  m = new Map(testMap);
  m.generate();
  m.init();
  println(m);

  you = new Player(110, width, height, m);
  bird = loadImage("bird.jpg");
  
  // lock mouse to center of screen
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

  // draw sky
  fill(25); strokeWeight(0);
  rect(0, 0, width, height / 2);

  // draw map
  // stroke(255); strokeWeight(2);
  // drawWireframe();
  drawTextures(bird);

  // handle movement
  you.move(passInput());
  you.bob();
  
  // handle mouse input, lock mouse to center of screen
  float lookMag = (mouseX - width / 2) * mouseSens;
  you.look(lookMag);
  r.mouseMove(width / 2, height / 2);
  
  // draw crosshair
  fill(255);
  circle(width / 2, height / 2, 3);
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

      Point floorP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), false);
      Point floorP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), false);
      Point ceilP1 = you.onScreenPos(p1.getX(), sect.getCeil(), p1.getY(), false);
      Point ceilP2 = you.onScreenPos(p2.getX(), sect.getCeil(), p2.getY(), false);

      if (floorP1 != null && floorP2 != null) {
        line(floorP1.getX(), floorP1.getY(), floorP2.getX(), floorP2.getY());
      }
      if (ceilP1 != null && ceilP2 != null) {
        line(ceilP1.getX(), ceilP1.getY(), ceilP2.getX(), ceilP2.getY());
      }
      if (floorP1 != null && ceilP1 != null) {
        line(floorP1.getX(), floorP1.getY(), ceilP1.getX(), ceilP1.getY());
      }
    }
  }
}

ArrayList<Point> pushTextures() {
  ArrayList<float[]> zList = new ArrayList<float[]>();
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
    }
  }
  return new ArrayList<Point>();
}

void drawTextures(PImage img) {
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

      Point floorP1 = you.onScreenPos(p1.getX(), sect.getFloor(), p1.getY(), false);
      Point floorP2 = you.onScreenPos(p2.getX(), sect.getFloor(), p2.getY(), false);
      Point ceilP1 = you.onScreenPos(p1.getX(), sect.getCeil(), p1.getY(), false);
      Point ceilP2 = you.onScreenPos(p2.getX(), sect.getCeil(), p2.getY(), false);

      if (floorP1 != null && floorP2 != null && ceilP1 != null && ceilP2 != null) {
        beginShape();
        texture(bird);
        vertex(ceilP1.getX(), ceilP1.getY(), 0, 0);
        vertex(ceilP2.getX(), ceilP2.getY(), img.width, 0);
        vertex(floorP2.getX(), floorP2.getY(), img.width, img.height);
        vertex(floorP1.getX(), floorP1.getY(), 0, img.height);
        endShape();
      }
    }
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
