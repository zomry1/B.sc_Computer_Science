import java.util.Random;

import biuoop.DrawSurface;
import biuoop.GUI;
/**
 *
 * @author Omry
 *
 */
public class MultipleBouncingBallsAnimation {
    /**
     * Create a screen GUI and DrawSurface, get radius in the commandline.
     * create balls in the screen that moves in the frame
     * with the same radius
     * @param args - the radius of the ball
     */
    public static void main(String[] args) {
        //Create instance of the class to call functions through it
        MultipleBouncingBallsAnimation mainInstance = new MultipleBouncingBallsAnimation();
        //The size of the screen
        int xFrameSize = 800;
        int yFrameSize = 600;
        //There are not numbers in args
        if (args.length == 0) {
            System.out.println("No sizes in args");
            return;
        }
        if (!mainInstance.checkArgs(args) || !mainInstance.checkScreenSize(args, xFrameSize, yFrameSize)) {
            return;
        }
        //Create the GUI
        GUI gui = new GUI("MultipleBouncingBallsAnimation", xFrameSize, yFrameSize);
        //Create Timer
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        //Create Random
        Random rand = new Random();
        //Create array of balls in size of the number we got
        Ball[] ballsArray = new Ball[args.length];
        int castInt;
        //Run through the numbers in args and create random ball
        for (int i = 0; i < args.length; i++) {
            castInt = Integer.parseInt(args[i]);
            ballsArray[i] = mainInstance.createRandomBall(castInt, rand, xFrameSize, yFrameSize);
        }
        //Run until exit the program
        while (true) {
            //Get DrawSurface from the GUI
            DrawSurface d = gui.getDrawSurface();
            //Foreach Ball
            for (int i = 0; i < args.length; i++) {
                //Move the ball and draw it again
                ballsArray[i].moveOneStep();
                ballsArray[i].drawOn(d);
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
     * @param xFrameSize - the width of the screen
     * @param yFrameSize - the height of the screen
     * @return the new ball
     */
    public Ball createRandomBall(int size, Random rand, int xFrameSize, int yFrameSize) {
        //Random number between the width of the frame
        int x = rand.nextInt(xFrameSize - size);
        if (x <= size) {
            x += size + 1;
        }
        //Random number between the height of the frame
        int y = rand.nextInt(yFrameSize - size);
        if (y <= size) {
            y += size + 1;
        }
        //Create the new ball
        Ball returnedBall = new Ball(x, y, size, java.awt.Color.BLACK, xFrameSize, yFrameSize);
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

