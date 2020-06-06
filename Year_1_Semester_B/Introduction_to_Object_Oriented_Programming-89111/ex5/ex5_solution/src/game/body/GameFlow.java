package game.body;
import java.util.List;

import biuoop.KeyboardSensor;
import animations.AnimationRunner;
import animations.EndScreen;
import game.mechanics.Counter;
import levels.LevelInformation;
/**
 * GameFlow - control the game (lives,score).
 * run the levels and close when the game end
 * @author Omry Zur
 */
public class GameFlow {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private Counter lives;
    private Counter score;

    /**
     * Constructor.
     * @param ar - animation runner for the game
     * @param ks - keyboard for the game
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.ar = ar;
        this.ks = ks;
        this.lives = new Counter();
        //set the lives to 7
        this.lives.increase(7);
        this.score = new Counter();
    }

    /**
     * Run the levels one after one (by the list of levels)
     * rematch the level if all the balls drop.
     * stop the game if the user win (complete all the levels) or lose (all the lives are gone)
     * @param levels - list of the levels to run in the game
     */
    public void runLevels(List<LevelInformation> levels) {
        //We assume the user is a pro gamer and he can win in the game - joke
        boolean win  = true;
        //Run the levels one after one
        for (LevelInformation levelInfo : levels) {
            //Create a game level and initilaize it
            GameLevel level = new GameLevel(levelInfo, this.ar, this.ks, this.lives, this.score);
            level.initilaize();

            //while the user dont lose and there is still a block on the screen play a turn
            while (this.lives.getValue() > 0 && level.getRemainingBlocks() > 0) {
                level.playOneTurn();
                //There is still a blocks and turn end? - loose 1 heart
                if (level.getRemainingBlocks() > 0) {
                    this.lives.decrease(1);
                }
                //All the hearts are gone
                if (lives.getValue() == 0) {
                    //Noob
                    win = false;
                    break;
                }
            }
        }
        //Start the end screen and wait to spacebar being pressed - close the gui
        this.ar.run(new EndScreen(win, this.ks, this.score));
    }
}
