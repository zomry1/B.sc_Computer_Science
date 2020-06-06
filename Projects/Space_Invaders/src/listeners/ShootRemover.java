package listeners;
import game.body.GameLevel;
import game.mechanics.Shot;
import game.mechanics.Block;

/**
 * Ball remover. remove Ball when hit the "death" blocks.
 * implements HitLisetener
 * @author Omry Zur
 */
public class ShootRemover implements HitListener {

    private GameLevel game;

    /**
     * Constructor.
     * @param game - the game we want to remove the ball from
     */
    public ShootRemover(GameLevel game) {
        this.game = game;
    }

    /**
     * When the block hit by the ball, remove the ball from the game.
     * @param beingHit - the block that being hit
     * @param hitter - the ball that hit the block - we need to remove from game
     */
    public void hitEvent(Block beingHit, Shot hitter) {
        hitter.removeFromGame(this.game);
    }

}
