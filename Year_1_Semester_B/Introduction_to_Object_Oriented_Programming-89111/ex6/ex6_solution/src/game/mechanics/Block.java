package game.mechanics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import game.body.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
/**
 * The Block - Collidable and Sprite.
 * can collision with the ball and draw on the screen
 *
 * its Build from Rectangle have a color and a number of hits left
 * @author Omry
 *
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private Color color;
    private int hits = 0;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructor of the Ball.
     * @param rect - the Rectangle of the Block
     * @param color - the color of the Block
     * @param hits - the number of hits the block left
     */
    public Block(Rectangle rect, Color color, int hits) {
        this.rect = rect;
        this.color = color;
        this.hits = hits;
    }

    /**
     * Return the Rectangle the Block is.
     * @return the Rectangle of the Block
     */
    public Rectangle getCollistionRectangle() {
        return this.rect;
    }

    /**
     * Return the new Velocity the ball should have after hitting the Block.
     * @param collisionPoint - the collision point of the ball with the Block
     * @param currentVelocity  - the Velocity of the Ball before hitting the Block
     * @param hitter - the ball that hit the block
     * @return the new Velocity the ball should have after hitting the Block
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        //Remove one from the hits left to the Block
        if (this.hits > 0) {
            this.hits--;
        }
        //Copy the Velocity of the Ball
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        //Get 2 point of the Rectangle of the Block
        Point upperLeft = this.rect.getUpperLeft();
        Point bottomRight = this.rect.getBottomRight();
        //Check where the Ball hit the Rectangle the left or the right
        if (this.inRange(upperLeft.getX(), collisionPoint.getX())
           || this.inRange(bottomRight.getX(), collisionPoint.getX())) {
            newVel.setDx(newVel.getDx() * -1);
        }
        //Or/And the up or bottom
        if (this.inRange(upperLeft.getY(), collisionPoint.getY())
           || this.inRange(bottomRight.getY(), collisionPoint.getY())) {
            newVel.setDy(newVel.getDy() * -1);
        }
        return newVel;
    }

    /**
     * Draw the Block on the surface.
     * @param d - the surface we want to draw the Block on it
     */
    public void drawOn(DrawSurface d) {
        //Create index's to easy use
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int)  this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        //int xText = (int) (topLeftCornerX + (width / 2));
        //int yText = (int) (topLeftCornerY + (height / 2) + 5);

        //Draw the rectangle
        d.setColor(color);
        d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);

        //Draw the frame
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle(topLeftCornerX, topLeftCornerY, width, height);

        /*
        //Draw the text (number of hits left)
        d.setColor(java.awt.Color.WHITE);
        if (this.hits == 0) {
            d.drawText(xText, yText, "X", 15);
        } else {
            d.drawText(xText, yText, String.valueOf(this.hits), 15);
        }*/
    }
    /**
     * Time Passed to the Block - now its nothing.
     * just here because the implements
     */
    public void timePassed(double dt) {

    }

    /**
     * Add the Block to the Collidable's and Sprite's lists of a Game.
     * @param g - The Game that we want to add our Block to this lists.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }

    /**
     * Check if the point is in range of other point with 0.2.
     * To fix Deviation in big numbers calculation
     * @param side - the point that we want to check if the point is very close to
     * @param point - the point we want to check if the side is very close to
     * @return true if the point is very close to the side, otherwise - false
     */
    private boolean inRange(double side, double point) {
        if (side - 0.2 <= point && side + 0.2 >= point) {
            return true;
        }
        return false;
    }

    /**
     * Remove the block from the sprites and collidable lists of game.
     * @param game - the game we want to remove the block from his sprite's and collidable's lists
     */
    public void removeFromeGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add a listener to the block.
     * @param hl - the listener we want to add to the block
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove a listener from the block.
     * @param hl - the listener we want to remove
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notify all the listener that there is a hit.
     * @param hitter - send the hitter ball to the listeners
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl: listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Get the current hit points of the block.
     * @return - the current hit points of the block
     */
    public int getHitPoints() {
        return hits;
    }
}
