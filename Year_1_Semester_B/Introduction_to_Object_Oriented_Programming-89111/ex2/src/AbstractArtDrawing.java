import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;
/**
 *
 * @author Omry
 *
 */
public class AbstractArtDrawing {
    /**
     * Main of the class.
     * create a new gui with draweSurface
     * draw 10 random lines and draw blue circle in the middle of each line
     * draw red circle in each intersection of 2 lines
     * @param args - nothing
     */
    public static void main(String[] args) {
        //Create Screen,drawSurface and random
        int xScreen = 400, yScreen = 300;
        GUI gui = new GUI("Random Lines Example", xScreen, yScreen);
        DrawSurface d = gui.getDrawSurface();
        Random rand = new Random();
        //Create instance of the class
        AbstractArtDrawing mainInstance = new AbstractArtDrawing();
        //Line array
        int numberOfLines = 10;
        Line[] lineArray = new Line[numberOfLines];
        //Create 10 randomLines
        for (int i = 0; i < numberOfLines; i++) {
            lineArray[i] = mainInstance.drawRandomLine(rand, d);
        }
        //Draw middle points
        for (int i = 0; i < numberOfLines; i++) {
            mainInstance.drawMiddlePoint(lineArray[i], d);
        }
        //Draw intersection points
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = i + 1; j < numberOfLines; j++) {
                if (lineArray[i].isIntersecting(lineArray[j])) {
                mainInstance.drawIntesctionPoint(lineArray[i].intersectionWith(lineArray[j]), d);
                }
            }
        }
        gui.show(d);
    }

    /**
     * Create a random line and draw it.
     * @param rand - Random, to random coordinates
     * @param d - DrawSurfce, to draw the lines on it
     * @return Line, return the random line
     */
    public Line drawRandomLine(Random rand, DrawSurface d) {
        //Random the coordinates
        int x1 = rand.nextInt(400);
        int x2 = rand.nextInt(400);
        int y1 = rand.nextInt(300);
        int y2 = rand.nextInt(300);
        //Draw the line on the DrawSurface
        d.drawLine(x1, y1, x2, y2);
        //Return the new line
        return new Line(x1, y1, x2, y2);
    }

    /**
     * Draw a blue circle in the middle of the line.
     * @param line - Line, the line we want to draw in the middle of it
     * @param d - DrawSurface, the surface we want to draw on it
     */
    public void drawMiddlePoint(Line line, DrawSurface d) {
        //Find the middle of the line
        Point middlePoint = line.middle();
        //Change the color to blue
        d.setColor(Color.BLUE);
        //Draw the circle
        d.fillCircle((int) middlePoint.getX(),
                     (int) middlePoint.getY(), 3);
    }
    /**
     * Draw the intersection point.
     * @param point - Point, the intersection point
     * @param d - DrawSurface, the surface we want to draw on it
     */
    public void drawIntesctionPoint(Point point, DrawSurface d) {
        //The x and y coordinates
        int xIndex = (int) point.getX();
        int yIndex = (int) point.getY();
        //Change the color to blue
        d.setColor(Color.RED);
        //Draw the circle
        d.fillCircle(xIndex, yIndex, 3);
    }
}
