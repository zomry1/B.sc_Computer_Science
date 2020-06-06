package game.body;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.mechanics.Alien;
import game.mechanics.AlienFormation;
import game.mechanics.Block;
import game.mechanics.Collidable;
import game.mechanics.Counter;
import game.mechanics.GameEnvironment;
import game.mechanics.Spaceship;
import game.mechanics.Shot;
import game.mechanics.Sprite;
import game.mechanics.SpriteCollection;
import geometry.Point;
import geometry.Rectangle;
import indicators.LevelIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import listeners.AlienRemover;
import listeners.ShootRemover;
import listeners.ShieldRemover;
import listeners.ScoreTrackingListener;

/**
 * Initialize and run the level.
 * implement Animation
 * @author Omry
 *
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environmet;
    private KeyboardSensor keyboard;
    private Spaceship mainSpaceship;
    private int xFrameSize = 800;
    private int yFrameSize = 600;
    private Counter remainingAliens;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private ShieldRemover blockRemover;
    private ShootRemover ballRemover;
    private ScoreTrackingListener scoreTrack;
    private AlienRemover alienRemover;
    private AlienFormation aliens;
    private List<Shot> shots;
    private int levelNumber;

    /**
     * Constructor.
     * @param levelNumber - the number of the level
     * @param ar - the animation runner that run the animations
     * @param ks - the keyboard to input the user controls
     * @param lives - counter of the lives
     * @param score - counter of the score
     */
    public GameLevel(int levelNumber, AnimationRunner ar, KeyboardSensor ks,
                    Counter lives, Counter score) {
        this.levelNumber = levelNumber;
        this.runner = ar;
        this.keyboard = ks;
        this.lives = lives;
        this.score = score;
        this.remainingAliens = new Counter();
        this.sprites  = new SpriteCollection();
        this.environmet  = new GameEnvironment();
        this.shots = new ArrayList<Shot>();
    }
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
        //Create Listeners
        this.blockRemover = new ShieldRemover(this);
        this.scoreTrack = new ScoreTrackingListener(this.score);
        this.ballRemover = new ShootRemover(this);
        this.alienRemover = new AlienRemover(this,  this.remainingAliens);

        //Add the background to the sprites
        addBackground();

        //Add indicators
        ScoreIndicator scoreInd = new ScoreIndicator(new Rectangle(new Point(0, 0), xFrameSize + 1, 20), this.score);
        scoreInd.addToGame(this);
        LivesIndicator liveInd = new LivesIndicator(new Rectangle(new Point(0, 0), xFrameSize + 1, 20), this.lives);
        liveInd.addToGame(this);
        LevelIndicator levelInd = new LevelIndicator(new Rectangle(
                new Point(0, 0), xFrameSize + 1, 20), "Battle no. " + this.levelNumber);
        levelInd.addToGame(this);

        //Create the spaceShip
        Rectangle spaceshipRect = new Rectangle(new Point(300, 560), 100, 20);
        this.mainSpaceship = new Spaceship(spaceshipRect, this.keyboard, xFrameSize - 20, 625, this);
        mainSpaceship.addToGame(this);

        Block top = new Block(new Rectangle(new Point(0, 20), xFrameSize + 1, 20));
        Block bottom = new Block(new Rectangle(new Point(0, yFrameSize - 20), xFrameSize + 1, 20));
        top.addToGame(this);
        top.addHitListener(this.ballRemover);
        bottom.addToGame(this);
        bottom.addHitListener(this.ballRemover);
        //Don't show those blocks
        this.removeSprite(top);
        this.removeSprite(bottom);

        //Remove bottom from sprite collection and add the bottom to the "death" region
        this.sprites.getSprites().remove(bottom);
        bottom.addHitListener(this.ballRemover);

        this.createShields();

        this.aliens = this.createFormation();
    }

    /**
     * Run the game - start the infinite animation loop.
     * the loop:
     * Draw the back ground
     * draw all the Sprites in Sprite list
     * shot the gui
     * check collision by calling notifyAll
     */

    /**
     * PlayOneTurn - initiliaze the turn, run the countdown animation.
     * and coninute untill the turn's end (no more balls/blocks)
     */
    public void playOneTurn() {
        //Middle the paddle, reset the shoots
        initilaizeTurn();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * initiliazeTurn - called each turn.
     * Middle the paddle, create the balls and update the balls counter
     */
    private void initilaizeTurn() {
        this.mainSpaceship.resetPaddle();
        //Reorganize aliens
        this.aliens.centerFormation();
        //Remove remain shots
        this.removeShots();
    }

    /**
     * remove all the shots from the shots list (to start a new turn).
     */
    private void removeShots() {
        //Remove all the shots from the shots list
        for (Shot shot: this.shots) {
            shot.removeFromGame(this);
        }
    }

    /**
     * Remove Collidable from the Collidables list.
     * @param c - the Collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environmet.getCollidables().remove(c);
    }

    /**
     * Remove Sprite from the Sprites list.
     * @param s - the Sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }

    /**
     * Draw one frame, draw and notify.
     * @param d - the surface we want to draw on
     * @param dt - the relative time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Draw all the Sprites
        this.sprites.drawAllOn(d);
        //Notify all sprite TimePassed
        this.sprites.notifyAllTimePassed(dt);
        //Notify the formation that time passed
        this.aliens.notifyFormation();

        //Check for pause game
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }

    /**
     * When the animation should stop.
     * the remaining blocks is 0 / the remaining balls is 0
     * @return true - stop the animation. false - continue the animation
     */
    public boolean shouldStop() {
        //There is no more aliens - the user win the level
        if (this.remainingAliens.getValue() == 0) {
            //Give the user points
            //this.score.increase(100);
            //Stop the animation
            this.running = true;

            //The alien got the spaceship or the spaceship was hitted
        } else if (!this.aliens.checkDown() || this.mainSpaceship.hitted()) {
            //Stop the animation
            this.running = true;
        } else {
            //The turn shouldn't stop
            this.running = false;
        }
        return this.running;
    }

    /**
     * @return - the number of the remaining blocks
     */
    public int getRemainingBlocks() {
        return this.remainingAliens.getValue();
    }

    /**
     * Create the shields (blocks) and add them to the game.
     */
    private void createShields() {
        Block currBlock;
        int blockWidth = 5;
        int blockHeight = 5;
        for (int y = 490; y >= 480; y -= 5) {
            for (int x = 130; x < 250; x += 5) {
                currBlock = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight));
                currBlock.addToGame(this);
                currBlock.addHitListener(this.blockRemover);
                currBlock.addHitListener(this.ballRemover);
            }
            for (int x = 350; x < 470; x += 5) {
                currBlock = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight));
                currBlock.addToGame(this);
                currBlock.addHitListener(this.blockRemover);
                currBlock.addHitListener(this.ballRemover);
            }
            for (int x = 570; x < 690; x += 5) {
                currBlock = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight));
                currBlock.addToGame(this);
                currBlock.addHitListener(this.blockRemover);
                currBlock.addHitListener(this.ballRemover);
            }
        }
    }

    /**
     * Create AlienFormation.
     * create the aliens and add them to lists
     * @return - the new AlienForamtion for the level
     */
    private AlienFormation createFormation() {
        List<List<Alien>> rows = new ArrayList<List<Alien>>();
        for (int i = 0; i < 5; i++) {
            rows.add(new ArrayList<Alien>());
        }
        int blockWidth = 40;
        int blockHeight = 30;

        Alien currBlock;
        //Create row
        for (int j = 1; j <= 5; j++) {
                for (int i = 0; i < 10; i++) {
                        Point upperLeft = new Point(50 + i * 50, 50 + (blockHeight + 10) * j);
                        currBlock = new Alien(
                                new Rectangle(upperLeft, blockWidth, blockHeight), i + 1, this);
                        currBlock.addToGame(this);
                        currBlock.addHitListener(this.scoreTrack);
                        currBlock.addHitListener(this.alienRemover);
                        currBlock.addHitListener(this.ballRemover);
                        rows.get(j - 1).add(currBlock);
                        //Increase counter of aliens
                        this.remainingAliens.increase(1);
                    }
        }
        return new AlienFormation(this.levelNumber, rows);
    }
    /**
     * @return the gameEnvironment of the game
     */
    public GameEnvironment getEnvironmet() {
        return environmet;
    }

    /**
     * Add shot to the shots list.
     * @param shot - the shot we want to add to the list.
     */
    public void addShot(Shot shot) {
        this.shots.add(shot);
    }

    /**
     * Create the background and add the game.
     */
    private void addBackground() {
        this.addSprite(new Sprite() {
            public void drawOn(DrawSurface d) {
                d.setColor(Color.BLACK);
                d.fillRectangle(0, 0, xFrameSize, yFrameSize);
            }
            public void timePassed(double dt) {
            }
        });
    }

    /**
     * Remove alien from the aliens list.
     * @param al - the alien we want to remove
     */
    public void removeAlien(Alien al) {
        this.aliens.removeAlien(al);
    }
}
