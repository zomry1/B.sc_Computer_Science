import java.util.ArrayList;
import java.util.List;

import animations.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.body.GameFlow;
import levels.LevelFour;
import levels.LevelInformation;
import levels.LevelOne;
import levels.LevelThree;
import levels.LevelTwo;

/**
 * Run the Game we created.
 * @author Omry
 *
 */
public class Ass5Game {
    /**
     * Initialize and run the Game.
     * @param args - nothing
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Brick Breaker v100", 800, 600);
        AnimationRunner ar  = new AnimationRunner(gui);
        KeyboardSensor ks = gui.getKeyboardSensor();
        GameFlow gf = new GameFlow(ar, ks);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        //Create the levels
        LevelOne one = new LevelOne();
        LevelTwo two = new LevelTwo();
        LevelThree three = new LevelThree();
        LevelFour four = new LevelFour();

        if (args.length == 0) {
            levels.add(one);
            levels.add(two);
            levels.add(three);
            levels.add(four);
        } else {
            for (String level: args) {
                switch (level) {
                case "1":
                    levels.add(one);
                    break;
                case "2":
                    levels.add(two);
                    break;
                case "3":
                    levels.add(three);
                    break;
                case "4":
                    levels.add(four);
                    break;
                default:
                    break;
                }
                if (level == "1") {
                    levels.add(one);
                }
            }
            //All the inputs are wrong
            if (levels.isEmpty()) {
                levels.add(one);
                levels.add(two);
                levels.add(three);
                levels.add(four);
            }
        }
        gf.runLevels(levels);

        gui.close();
    }

}
