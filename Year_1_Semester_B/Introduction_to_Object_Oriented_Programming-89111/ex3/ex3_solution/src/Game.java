import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Initialize and run the game.
 * @author Omry
 *
 */
public class Game {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environmet = new GameEnvironment();
    private GUI gui;
    private Sleeper sleeper;
    private int xFrameSize = 800;
    private int yFrameSize = 600;

    /**
     * Add a Collidable  to the Collidable list.
     * @param c - the Collidable we want to add
     */
    public void addCollidable(Collidable c) {
        this.environmet.addCollidable(c);
    }

    /**
     * Add a Sprite to the Sprite list.
     * @param s - the Sprite we want to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    /**
     * Initialize the game - Create the Blocks,Ball,Paddle and add them to the lists.
     */
    public void initilaize() {
        //Create the Timer
        this.sleeper = new Sleeper();

        //Create the GUI
        this.gui = new GUI("TestGame", this.xFrameSize, this.yFrameSize);

        //Create the balls
        Ball firstBall = new Ball(new Point(400, 300), 10, java.awt.Color.MAGENTA, environmet);
        //Create Velocity
        Velocity vel =  Velocity.fromAngleAndSpeed(23, 10);
        firstBall.setVelocity(vel);
        firstBall.addToGame(this);

        Ball secondBall = new Ball(new Point(300, 450), 10, java.awt.Color.CYAN, environmet);
        //Create Velocity
        Velocity vel2 =  Velocity.fromAngleAndSpeed(102, 10);
        secondBall.setVelocity(vel2);
        secondBall.addToGame(this);

        //Create list of balls
        List<Ball> balls = new ArrayList<Ball>();
        balls.add(firstBall);
        balls.add(secondBall);

        //Create the paddle
        Rectangle paddleRect = new Rectangle(new Point(400, 560), 100, 20);
        Paddle mainPaddle = new Paddle(paddleRect, gui.getKeyboardSensor(), xFrameSize - 20, balls);
        mainPaddle.addToGame(this);

        //Create Borders
        Block top = new Block(new Rectangle(new Point(0, 0), xFrameSize + 1, 20), Color.GRAY, 0);
        Block bottom = new Block(new Rectangle(new Point(0, yFrameSize - 20), xFrameSize + 1, 20), Color.GRAY, 0);
        Block left = new Block(new Rectangle(new Point(0, 0), 20, yFrameSize + 1), Color.GRAY, 0);
        Block right = new Block(new Rectangle(new Point(xFrameSize - 20, 0), 20, yFrameSize + 1), Color.GRAY, 0);
        top.addToGame(this);
        bottom.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);

        //Create the Block
        int screenEdge = xFrameSize - 20;
        int blockWidth = 40;
        int blockHeight = 20;
        for (int i = 0; i < 12; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 20);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.GRAY, 2);
            currBlock.addToGame(this);

        }
        for (int i = 0; i < 11; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 40);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.RED, 1);
            currBlock.addToGame(this);
        }
        for (int i = 0; i < 10; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 60);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.YELLOW, 1);
            currBlock.addToGame(this);
        }
        for (int i = 0; i < 9; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 80);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.BLUE, 1);
            currBlock.addToGame(this);
        }
        for (int i = 0; i < 8; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 100);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.PINK, 1);
            currBlock.addToGame(this);
        }
        for (int i = 0; i < 7; i++) {
            Point upperLeft = new Point(screenEdge - i * 40 - blockWidth, 150 + 120);
            Block currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.GREEN, 1);
            currBlock.addToGame(this);
        }
    }

    /**
     * Run the game - start the infinite animation loop.
     * the loop:
     * Draw the back ground
     * draw all the Sprites in Sprite list
     * shot the gui
     * check collision by calling notifyAll
     */
    public void run() {
        //60 Frames per second
        int framesPerSecond = 60;
        //Check how much milliseconds we have to each frame
        int millisecondsPerFrame = 1000 / framesPerSecond;
        //Infinite loop
        while (true) {
            //Save the current time in milliseconds
            long startTime = System.currentTimeMillis();

            //get a surface
            DrawSurface d = gui.getDrawSurface();
            //Draw the background
            d.setColor(java.awt.Color.BLUE);
            d.fillRectangle(0, 0, this.xFrameSize, this.yFrameSize);
            //Draw all the Sprites
            this.sprites.drawAllOn(d);
            //Show the GUI
            gui.show(d);
            //notify all sprite TimePassed
            this.sprites.notifyAllTimePassed();

            //Timing - check the time was past and if we have time space wait it
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
