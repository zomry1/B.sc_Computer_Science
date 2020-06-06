package listeners;
import game.body.GameLevel;
import game.mechanics.Shot;
import game.mechanics.Block;

/**
 * Shield remover. remove shield when hit by a shot
 * implements HitLisetener
 * @author Omry Zur
 */
public class ShieldRemover implements HitListener {

    private GameLevel game;
    /**
     * Constructor.
     * @param game - the game we want to remove the block from
     */
    public ShieldRemover(GameLevel game) {
        this.game = game;
    }

    /**
     *  Blocks that are hit and reach 0 hit-points should be removed
     *  from the game.
     *  @param beingHit - the block that being hit - we want to remove
     *  @param hitter - the ball that hit the block
     */
    public void hitEvent(Block beingHit, Shot hitter) {
        if (beingHit.getHitPoints() == 1) {
            // remove this listener from the block that is being removed from the game.
            beingHit.removeHitListener(this);
            beingHit.removeFromeGame(this.game);
        }

    }

}
