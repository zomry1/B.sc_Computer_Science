package game.mechanics;

import geometry.Rectangle;

/**
 * The interface of the collidable -
 * each object the ball can collision with
 * its the paddle and the block.
 * @author Omry
 *
 */
public interface Collidable {
    // Return the "collision shape" of the object.
    /**
     * Return the Rectangle that involved in the collision.
     * @return the Rectangle that involved in the collision.
     */
    Rectangle getCollistionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * @param hitter - the ball that hit the block
     */
    void hit(Shot hitter);
}
