package levels;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.mechanics.Block;
import game.mechanics.Sprite;
import game.mechanics.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * Level four information.
 * Implements levelInformaiton
 * @author Omry Zur
 *
 */
public class LevelFour implements LevelInformation {

    /**
     * @return - number of balls in the level.
     */
    public int numberOfBalls() {
        return 3;
    }

    /**
     * @return - list of the velocities of the balls.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        ballVelocities.add(new Velocity(0, -438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(120, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(60, 438));
        return ballVelocities;
    }

    /**
     * @return -the speed of the paddle.
     */
    public int paddleSpeed() {
        return 625;
    }

    /**
     * @return - the width of the paddle.
     */
    public int paddleWidth() {
        return 100;
    }

    /**
     * @return - the name of the level.
     */
    public String levelName() {
        return "Final Four";
    }

    /**
     * @return - the sprite that is the background of the level.
     */
    public Sprite getBackground() {
        return new LevelFourBackground();
    }

    /**
     * @return - list of the blocks in the level.
     */
    public List<Block> blocks() {
        //Create the Block
        int screenEdge = 775;
        int blockWidth = 50;
        int blockHeight = 20;

        Block currBlock;
        List<Block> blockList = new ArrayList<Block>();
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.GRAY, 2);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 20);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.RED, 1);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 40);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.YELLOW, 1);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 60);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.GREEN, 1);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 80);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.WHITE, 1);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 100);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.PINK, 1);
            blockList.add(currBlock);
            }
        for (int i = 0; i < 15; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 100 + 120);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.CYAN, 1);
            blockList.add(currBlock);
            }
        return blockList;

    }

    /**
     * @return - number of blocks in the level
     */
    public int numberOfBlocksToRemove() {
        return 105;
    }

}
