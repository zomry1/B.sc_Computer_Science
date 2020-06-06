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
 * Level three information.
 * Implements levelInformaiton
 * @author Omry Zur
 *
 */
public class LevelThree implements LevelInformation {

    /**
     * @return - number of balls in the level.
     */
    public int numberOfBalls() {
        return 2;
    }

    /**
     * @return - list of the velocities of the balls.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        ballVelocities.add(Velocity.fromAngleAndSpeed(120, 7));
        ballVelocities.add(Velocity.fromAngleAndSpeed(60, 7));
        return ballVelocities;
    }

    /**
     * @return -the speed of the paddle.
     */
    public int paddleSpeed() {
        return 10;
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
        return "Green 3";
    }

    /**
     * @return - the sprite that is the background of the level.
     */
    public Sprite getBackground() {
        return new LevelThreeBackground();
    }

    /**
     * @return - list of the blocks in the level.
     */
    public List<Block> blocks() {
        //Create the Block
        int screenEdge = 800 - 20;
        int blockWidth = 50;
        int blockHeight = 20;

        Block currBlock;
        List<Block> blockList = new ArrayList<Block>();
        for (int i = 0; i < 10; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 150 + 20);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.GRAY, 2);
            blockList.add(currBlock);
        }
        for (int i = 0; i < 9; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 150 + 40);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.RED, 1);
            blockList.add(currBlock);
        }
        for (int i = 0; i < 8; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 150 + 60);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.YELLOW, 1);
            blockList.add(currBlock);
        }
        for (int i = 0; i < 7; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 150 + 80);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.BLUE, 1);
            blockList.add(currBlock);
        }
        for (int i = 0; i < 6; i++) {
            Point upperLeft = new Point(screenEdge - i * blockWidth - blockWidth, 150 + 100);
            currBlock = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), Color.WHITE, 1);
            blockList.add(currBlock);
        }
        return blockList;
    }

    /**
     * @return - number of blocks in the level
     */
    public int numberOfBlocksToRemove() {
        return 40;
    }

}
