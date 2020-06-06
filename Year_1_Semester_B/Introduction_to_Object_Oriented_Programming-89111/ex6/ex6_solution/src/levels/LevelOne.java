package levels;
import java.util.ArrayList;
import java.util.List;

import game.mechanics.Block;
import game.mechanics.Sprite;
import game.mechanics.Velocity;
import geometry.Point;
import geometry.Rectangle;
/**
 * Level one information.
 * Implements levelInformaiton
 * @author Omry Zur
 *
 */
public class LevelOne implements LevelInformation {

    /**
     * @return - number of balls in the level.
     */
    public int numberOfBalls() {
        return 1;
    }

    /**
     * @return - list of the velocities of the balls.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        ballVelocities.add(new Velocity(0, -438));
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
        return "Direct Hit";
    }

    /**
     * @return - the sprite that is the background of the level.
     */
    public Sprite getBackground() {
        return new LevelOneBackground();
    }

    /**
     * @return - list of the blocks in the level.
     */
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<Block>();

        Block centerBlock = new Block(new Rectangle(new Point(385, 135), 30, 30), java.awt.Color.RED, 1);
        blockList.add(centerBlock);
        return blockList;
    }

    /**
     * @return - number of blocks in the level
     */
    public int numberOfBlocksToRemove() {
        return 1;
    }

}
