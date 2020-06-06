package listeners;

import game.body.GameLevel;
import game.mechanics.Alien;
import game.mechanics.Block;
import game.mechanics.Counter;
import game.mechanics.Shot;


/**
 * Alien remover. remove alien when hit by a shot
 * implements HitLisetener
 * @author Omry Zur
 */
public class AlienRemover implements HitListener {

    private GameLevel game;
    private Counter reamainingAliens;

    /**
     * Constructor.
     * @param game - the game we want to remove the block from
     * @param reamainingAliens - counter of the blocks
     */
    public AlienRemover(GameLevel game, Counter reamainingAliens) {
        this.game = game;
        this.reamainingAliens = reamainingAliens;
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
            //Its always alien
            this.game.removeAlien((Alien) beingHit);
            this.reamainingAliens.decrease(1);
        }

    }

}
