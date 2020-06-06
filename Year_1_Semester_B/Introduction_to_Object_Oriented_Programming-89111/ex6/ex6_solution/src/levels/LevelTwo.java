package levels;
import java.util.ArrayList;
import java.util.List;

import game.mechanics.Block;
import game.mechanics.Sprite;
import game.mechanics.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * Level two information.
 * Implements levelInformaiton
 * @author Omry Zur
 *
 */
public class LevelTwo implements LevelInformation {

     /**
     * @return - number of balls in the level.
     */
    public int numberOfBalls() {
        return 10;
    }

    /**
     * @return - list of the velocities of the balls.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        ballVelocities.add(Velocity.fromAngleAndSpeed(40, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(50, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(60, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(70, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(80, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(100, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(110, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(120, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(130, 438));
        ballVelocities.add(Velocity.fromAngleAndSpeed(140, 438));
        return ballVelocities;
    }

    /**
     * @return -the speed of the paddle.
     */
    public int paddleSpeed() {
        return 313;
    }

    /**
     * @return - the width of the paddle.
     */
    public int paddleWidth() {
        return 450;
    }

    /**
     * @return - the name of the level.
     */
    public String levelName() {
        return "Wide Easy";
    }

    /**
     * @return - the sprite that is the background of the level.
     */
    public Sprite getBackground() {
        return new LevelTwoBackground();
    }

    /**
     * @return - list of the blocks in the level.
     */
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<Block>();
        Block block;
        int blockWidth = 50;
        int blockHeight = 20;
        int blockY = 250;
        block = new Block(new Rectangle(new Point(25, blockY), blockWidth, blockHeight), java.awt.Color.RED, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(75, blockY), blockWidth, blockHeight), java.awt.Color.RED, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(125, blockY), blockWidth, blockHeight), java.awt.Color.ORANGE, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(175, blockY), blockWidth, blockHeight), java.awt.Color.ORANGE, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(225, blockY), blockWidth, blockHeight), java.awt.Color.YELLOW, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(275, blockY), blockWidth, blockHeight), java.awt.Color.YELLOW, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(325, blockY), blockWidth, blockHeight), java.awt.Color.GREEN, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(375, blockY), blockWidth, blockHeight), java.awt.Color.GREEN, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(425, blockY), blockWidth, blockHeight), java.awt.Color.GREEN, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(475, blockY), blockWidth, blockHeight), java.awt.Color.BLUE, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(525, blockY), blockWidth, blockHeight), java.awt.Color.BLUE, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(575, blockY), blockWidth, blockHeight), java.awt.Color.PINK, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(625, blockY), blockWidth, blockHeight), java.awt.Color.PINK, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(675, blockY), blockWidth, blockHeight), java.awt.Color.CYAN, 1);
        blockList.add(block);
        block = new Block(new Rectangle(new Point(725, blockY), blockWidth, blockHeight), java.awt.Color.CYAN, 1);
        blockList.add(block);
        return blockList;
    }

    /**
     * @return - number of blocks in the level
     */
    public int numberOfBlocksToRemove() {
        return 15;
    }

}
