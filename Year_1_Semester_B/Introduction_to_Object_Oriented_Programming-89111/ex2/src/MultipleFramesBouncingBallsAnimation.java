import java.util.Random;

import biuoop.DrawSurface;
import biuoop.GUI;
/**
 *
 * @author Omry
 *
 */
public class MultipleFramesBouncingBallsAnimation {
    /**
     *     /**
     * Create a screen GUI and DrawSurface, get radius in the commandline.
     * create balls in the screen that moves in the frame
     * with the same radius
     * divide the balls to 2 frames, 1 gray and 1 yellow
     * @param args - the radius of the ball
     */
    public static void main(String[] args) {
        //There are not numbers in args
        if (args.length == 0) {
            System.out.println("No sizes in args");
            return;
        }
        //Create instance of the class to call functions through it
        MultipleFramesBouncingBallsAnimation mainInstance = new MultipleFramesBouncingBallsAnimation();
        //The size of the screen
        int xFrameSize = 1000;
        int yFrameSize = 1000;
        if (!mainInstance.checkArgs(args) || !mainInstance.checkScreenSize(args, xFrameSize, yFrameSize)) {
            return;
        }
        //Create the GUI
        GUI gui = new GUI("MultipleFramesBouncingBallsAnimation", xFrameSize, yFrameSize);
        //Create Timer
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        //Create Random
        Random rand = new Random();
        //Create array of balls in size of the number we got - the first half
        Ball[] ballsArrayGray = new Ball[args.length / 2];
        //Create array of balls in size of the number we got - the second half
        Ball[] ballsArrayYellow = new Ball[args.length / 2];
        //Run through the numbers in args and create random ball
        for (int i = 0; i < args.length / 2; i++) {
            //Create ball for the current index and for the ball in the second half
            ballsArrayGray[i] = mainInstance.createRandomBall((Integer.parseInt(args[i])),
                                rand, 50, 50, 500, 500);
            ballsArrayYellow[i] = mainInstance.createRandomBall((Integer.parseInt(args[i + (args.length / 2)])),
                                  rand, 450, 450, 600, 600);
        }
        //Run until exit the program
        while (true) {
            //Get DrawSurface from the GUI
            DrawSurface d = gui.getDrawSurface();
            //Draw the 2 frames (gray, yellow)
            mainInstance.drawFrames(d);
            //Foreach Ball
            for (int i = 0; i < args.length / 2; i++) {
                //Move the balls and draw them again
                ballsArrayGray[i].moveOneStep();
                ballsArrayYellow[i].moveOneStep();
                ballsArrayGray[i].drawOn(d);
                ballsArrayYellow[i].drawOn(d);
            }
            //Show the new balls
            gui.show(d);
            //Timer of 50 milliseconds
            sleeper.sleepFor(50);
        }
    }
    /**
     * Create ball with specific radius and random location and velocity.
     * @param size - the radius of the ball
     * @param rand - random to random the location and velocity
     * @param xStartFrame - the x coordinate of the start of the frame
     * @param yStartFrame - the y coordinate of the start of the frame
     * @param xEndFrame - the x coordinate of the end of the frame
     * @param yEndFrame - the y coordinate of the end of the frame
     * @return the new ball
     */
    public Ball createRandomBall(int size, Random rand, int xStartFrame, int yStartFrame,
                                 int xEndFrame, int yEndFrame) {
        //Random number between the width of the frame
        int x = rand.nextInt(xEndFrame - size - xStartFrame)  + xStartFrame;
        if (x <= xStartFrame + size) {
            x += size + 1;
        }
        //Random number between the height of the frame
        int y = rand.nextInt(yEndFrame - size - yStartFrame) + yStartFrame;
        if (y <= yStartFrame + size) {
            y += size + 1;
        }
        //Create the new ball
        Ball returnedBall = new Ball(x, y, size, java.awt.Color.BLUE, xEndFrame, yEndFrame);
        returnedBall.setXStartFrame(xStartFrame);
        returnedBall.setYStartFrame(yStartFrame);
        //Random the angle
        double angle = (double) rand.nextInt(360);
        //Set speed according to the size of the ball
        double speed;
        if (size > 50) {
            speed = 5;
        } else {
            speed = (double) (35 / size);
            if (speed > 15) {
                speed = speed * 0.6;
            }
        }
        //Create the velocity and set it to the ball
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        returnedBall.setVelocity(v);
        //Return the ball
        return returnedBall;
    }

    /**
     * Draw the 2 frames.
     * @param d - the surface to draw on it.
     */
    public void drawFrames(DrawSurface d) {
        //Change the color to gray and draw the gray frame
        d.setColor(java.awt.Color.gray);
        d.fillRectangle(50, 50, 450, 450);
        //Change the color to yellow and draw the yellow frame
        d.setColor(java.awt.Color.yellow);
        d.fillRectangle(450, 450, 150, 150);
    }

    /**
     * Check the arguments in args (if they are ints).
     * @param args the strings we want to check
     * @return true for all arguments are ints, otherwise false.
     */
    public boolean checkArgs(String[] args) {
         for (int i = 0; i < args.length; i++) {
                try {
                    Integer.parseInt(args[i]);
                } catch (Exception ex) {
                    System.out.println("Error insert radius of balls (not ints)");
                    return false;
                }
         }
         return true;
    }

    /**
     * Check if all the balls smaller then the screen.
     * @param args - the radius of the balls
     * @param xScreen - the width of the screen
     * @param yScreen - the height of the screen
     * @return true if all the balls smaller then the screen, otherwise false
     */
    public boolean checkScreenSize(String[] args, int xScreen, int yScreen) {
        for (int i = 0; i < args.length; i++) {
            if (Integer.parseInt(args[i]) * 2 >= xScreen || Integer.parseInt(args[i]) * 2 >= yScreen) {
                System.out.println("Error inset radius of balls (ball too big)");
                return false;
            }
        }
        return true;
    }

}
