package game.mechanics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import biuoop.DrawSurface;
import game.body.GameLevel;
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
    private int hits;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructor of the Ball.
     * @param rect - the Rectangle of the Block
     */
    public Block(Rectangle rect) {
        this.rect = rect;
        this.hits = 1;
    }

    /**
     * Return the Rectangle the Block is.
     * @return the Rectangle of the Block
     */
    public Rectangle getCollistionRectangle() {
        return this.rect;
    }

    /**
     * Notify and remove the block.
     * @param hitter - the shot that hit the block
     */
    public void hit(Shot hitter) {
        this.notifyHit(hitter);
        //Remove one from the hits left to the Block
        if (this.hits > 0) {
            this.hits--;
        }
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

        //Draw the rectangle
        d.setColor(Color.CYAN);
        d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
    }
    /**
     * Time Passed to the Block - now its nothing.
     * just here because the implements
     */
    public void timePassed() {

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
     * Remove the block from the sprites and collidable lists of game.
     * @param game
     *            - the game we want to remove the block from his sprite's and
     *            collidable's lists
     */
    public void removeFromeGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add a listener to the block.
     * @param hl
     *            - the listener we want to add to the block
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove a listener from the block.
     * @param hl
     *            - the listener we want to remove
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notify all the listener that there is a hit.
     * @param hitter
     *            - send the hitter ball to the listeners
     */
    protected void notifyHit(Shot hitter) {
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

    /**
     * Time Passed to the Block - now its nothing. just here because the implements.
     * @param dt - the relative time
     */
    public void timePassed(double dt) {
    }

    /**
     * @return - the hits points of the block.
     */
    public int getHits() {
        return hits;
    }

    /**
     * @return - the rectangle of the block.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * @return - the list of hitlisteners of the block.
     */
    public List<HitListener> getHitListeners() {
        return hitListeners;
    }
}
