package listeners;
import game.mechanics.Ball;
import game.mechanics.Block;

/**
 * -Just for test-
 * PrintingHitListener . print when a block hit
 * implements HitLisetener
 * @author Omry Zur
 */
public class PrintingHitListener implements HitListener {

    /**
     * Constructor.
     */
    public PrintingHitListener() {

    }
    /**
     * Print a message when a block being hit.
     * @param beingHit - the block that being hit
     * @param hitter - the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit");
    }

}
