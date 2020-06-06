import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.DrawSurface;
import java.util.Random;
/**
 *
 * @author Omry
 *
 */
public class BouncingBallAnimation {
    /**
     * Create a screen GUI and DrawSurface.
     * create ball in the screen that moves in the frame
     * @param args - none
     */
    public static void main(String[] args) {
        //The size of the screen
        int xFrameSize = 200;
        int yFrameSize = 200;
        //Create the GUI
        GUI gui = new GUI("BouncingBallAnimation", 200, 200);
        //Create Timer
        Sleeper sleeper = new Sleeper();
        //Create the random
        Random rand = new Random();
        //the radius of the ball
        int radius = 30;
        //Random x and y coordinates for the ball
        int xLocation = rand.nextInt(xFrameSize - radius);
        if (xLocation <= radius) {
            xLocation += 31;
        }
        int yLocation = rand.nextInt(yFrameSize - radius);
        if (yLocation <= radius) {
            yLocation += 31;
        }
        //Create the ball
        Ball ball = new Ball(new Point(xLocation, yLocation), radius, java.awt.Color.BLACK, xFrameSize, yFrameSize);
        //Set a velocity for the ball
        ball.setVelocity(2, 2);
        //Until the program is running
        while (true) {
           //Move the ball
           ball.moveOneStep();
           //Get a new DrawSurface
           DrawSurface d = gui.getDrawSurface();
           //Draw the ball on the surface
           ball.drawOn(d);
           //Show the surface
           gui.show(d);
           //Start timer
           sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

}
