package game.mechanics;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.body.GameLevel;
import geometry.Point;
import geometry.Rectangle;
/**
 * The Paddle - the "Block" moves by the user input.
 * @author Omry
 *
 */
public class Spaceship implements Sprite, Collidable {
    private Rectangle rect;
    private biuoop.KeyboardSensor keyboard;
    private double lScreenBorder;
    private double rScreenBorder;
    private int speed;
    private int dSpeed;
    private GameLevel game;
    private Long previousShot;
    private int shotTimeInMili;
    private boolean alienShotHit;
    private Image spaceShipImage;

    /**
     * Constructor.
     * @param rect - the Rectangle of the Paddle
     * @param keyboard - the user input sensor
     * @param rScreenBorder - the right border of the screen
     * @param speed - the speed of the paddle
     * @param game - the game we want the spaceship will add the shots to
     */
    public Spaceship(Rectangle rect, biuoop.KeyboardSensor keyboard, double rScreenBorder, int speed, GameLevel game) {
        this.rect = rect;
        this.keyboard = keyboard;
        this.lScreenBorder = 20; //the width of the sides block
        this.rScreenBorder = rScreenBorder;
        this.speed = speed;
        this.dSpeed = speed;
        this.game = game;
        this.shotTimeInMili = 350;
        this.previousShot = System.currentTimeMillis();
        this.alienShotHit = false;
        //Load image
        Image img = null;
         try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("spaceship.png");
                img = ImageIO.read(is);
            } catch (Exception e) {
                System.out.println("Error load image of spaceship");
                System.exit(0);
            }
            this.spaceShipImage = img;
    }

    /**
     * Move the Paddle left.
     */
    private void moveLeft() {
        if (rect.getUpperLeft().getX() < this.lScreenBorder + 10) {
            return;
        }
        this.rect.moveRectangleX(this.dSpeed * -1);
    }
    /**
     * Move the Paddle to the right.
     */
    private void moveRight() {
        if (rect.getUpperRight().getX() > this.rScreenBorder - 10) {
              return;
         }
         this.rect.moveRectangleX(this.dSpeed);
    }

    /**
     * return a point that the spaceship shot from.
     * @return - the point to shot from
     */
    private Point shotPoint() {
        return new Point(this.rect.getUpperLeft().getX()
                + (this.rect.getWidth() / 2), this.rect.getUpperLeft().getY() - 10);
    }
    /**
     * Create a shot and add it to the game.
     */
    private void shot() {
        //Create the shot
        Shot paddleShot = new Shot(this.shotPoint(), false, this.game.getEnvironmet());
        //Add to the game and the list of shots
        paddleShot.addToGame(this.game);
        this.game.addShot(paddleShot);
    }

    /**
     * Check if the right or the left key is Pressed if so move the Paddle to the correct place.
     * @param dt - relative time.
     */
    public void timePassed(double dt) {
        this.dSpeed = (int) (this.speed * dt);
        //Check if the keys are pressed and move the Paddle
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        //Check if the space is pressed and pass enough time to shot again
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)
                && (System.currentTimeMillis() - this.previousShot) >= this.shotTimeInMili) {
            shot();
            //Update the last shot time
            this.previousShot = System.currentTimeMillis();
        }
    }

    /**
     * Draw the Paddle on the surface.
     * @param d - the surface we want to draw on it
     */
    public void drawOn(DrawSurface d) {
        //Get the coordinate's for easy use
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY() - 40;

        d.drawImage(topLeftCornerX, topLeftCornerY, this.spaceShipImage);
    }

    //Collidable implements
    /**
     * Return the Rectangle of the Paddle.
     * @return return the Rectangle of the Paddle
     */
    public Rectangle getCollistionRectangle() {
        return this.rect;
    }

    /**
     * Set that the spaceship being hit.
     * @param hitter - the ball that hit the paddles
     */
    public void hit(Shot hitter) {
        this.alienShotHit = true;
    }

    /**
     * Add the Paddle to the Collidable's and Sprite's lists belongs to the Game.
     * @param g - the Game thats the lists are belongs to
     */
    public void addToGame(GameLevel g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }

    /**
     * Change the location of the paddle to the middle of the screen.
     */
    public void resetPaddle() {
        this.alienShotHit = false;
    }

    /**
     * Check if the space ship being hit.
     * @return the spaceship being hit - true. otherwise - false
     */
    public boolean hitted() {
        return this.alienShotHit;
    }
}
