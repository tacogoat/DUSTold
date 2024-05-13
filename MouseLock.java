import java.awt.Robot;
import java.awt.AWTException;

public class MouseLock {
 public static void center(int x, int y) {
   try {
     Robot r = new Robot();
     r.mouseMove(x, y);
   } catch (AWTException e) {
     e.printStackTrace();
   }
 }
}
