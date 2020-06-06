import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * The Paddle - the "Block" moves by the user input.
 * @author Omry
 *
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rect;
    private biuoop.KeyboardSensor keyboard;
    private double lScreenBorder;
    private double rScreenBorder;
    private List<Ball> balls;

    /**
     * Constructor.
     * @param rect - the Rectangle of the Paddle
     * @param keyboard - the user input sensor
     * @param rScreenBorder - the right border of the screen
     * @param balls - list of the Balls that belongs to the Game
     */
    public Paddle(Rectangle rect, biuoop.KeyboardSensor keyboard, double rScreenBorder, List<Ball> balls) {
        this.rect = rect;
        this.keyboard = keyboard;
        this.lScreenBorder = 20; //the width of the sides block
        this.rScreenBorder = rScreenBorder;
        this.balls = balls;
    }

    /**
     * Move the Paddle left.
     */
    public void moveLeft() {
        if (rect.getUpperLeft().getX() < this.lScreenBorder + 10) {
            return;
        }
        this.rect.moveRectangle(-10);
    }
    /**
     * Move the Paddle to the right.
     */
    public void moveRight() {
        if (rect.getUpperRight().getX() > this.rScreenBorder - 10) {
              return;
         }
         this.rect.moveRectangle(10);
    }

    /**
     * Check if the right or the left key is Pressed if so move the Paddle to the correct place.
     * Check if Ball is "stuck" in the Paddle if so "rescue" it - if the Paddle moves to the Ball location
     */
    public void timePassed() {
        //Check if the keys are pressed and move the Paddle
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        //For each ball check if it stuck in the Paddle if so rescue it
        for (Ball ball: this.balls) {
            double xBall = ball.getX();
            double yBall = ball.getY();
            if ((this.rect.getUpperLeft().getY() < yBall && this.rect.getBottomRight().getY() > yBall)
                && this.rect.getUpperLeft().getX() < xBall && this.rect.getBottomRight().getX() > xBall) {
                ball.setY(ball.getY() - 20);
            }
        }
    }

    /**
     * Draw the Paddle on the surface.
     * @param d - the surface we want to draw on it
     */
    public void drawOn(DrawSurface d) {
        //Get the coordinate's for easy use
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int)  this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        //Draw the Paddle
        d.setColor(java.awt.Color.YELLOW);
        d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
        //Draw the frame of the Paddle
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle(topLeftCornerX, topLeftCornerY, width, height);
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
     * Return the new Velocity after hitting the Paddle.
     * the paddle have 5 parts on the top - each of them return other angle of Velocity
     * and 1 part each side - return the Velocity with negative dx
     * @param collisionPoint - the Collision point of the Ball with the Paddle
     * @param currentVelocity - the current Velocity of the Ball
     * @return the new Velocity after hitting the Paddle
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        //Copy the current Velocity of the
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        //Get the speed of the Velocity
        double speed = currentVelocity.getSpeed();
        //the x of the collision point
        double xCPoint = collisionPoint.getX();
        //The x start of the paddle
        double startPaddleX = this.rect.getUpperLeft().getX();
        //Check what is the width of 1/5 of the paddle
        double paddlePart = (this.rect.getUpperRight().getX() - startPaddleX) / 5;
        //Check what to do with =
        //Zone 1 - the first left part of the paddle
        if (xCPoint >= startPaddleX &&  xCPoint < startPaddleX + paddlePart) {
            newVel = Velocity.fromAngleAndSpeed(150, speed);
        //Zone 2 - the second left part of the paddle
        } else if (xCPoint >= startPaddleX + paddlePart &&  xCPoint <= startPaddleX + paddlePart * 2) {
            newVel = Velocity.fromAngleAndSpeed(120, speed);
        //Zone 3 - the middle part of the paddle
        } else if (xCPoint > startPaddleX + paddlePart * 2 &&  xCPoint <= startPaddleX + paddlePart * 3) {
            newVel.setDy(newVel.getDy() * -1);
        //Zone 4 - the second right part of the paddle
        } else if (xCPoint > startPaddleX + paddlePart * 3 &&  xCPoint < startPaddleX + paddlePart * 4) {
            newVel = Velocity.fromAngleAndSpeed(60, speed);
        //Zone 5 - the first right part of the paddle
        } else if (xCPoint >= startPaddleX + paddlePart * 4 &&  xCPoint <= startPaddleX + paddlePart * 5) {
            newVel = Velocity.fromAngleAndSpeed(30, speed);
        //Else its the 2 sides of the Paddle
        } else {
            newVel.setDx(newVel.getDx() * -1);
        }
        return newVel;

    }

    /**
     * Add the Paddle to the Collidable's and Sprite's lists belongs to the Game.
     * @param g - the Game thats the lists are belongs to
     */
    public void addToGame(Game g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }
}
