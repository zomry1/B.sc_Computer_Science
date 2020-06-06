import java.io.File;
import java.io.IOException;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.body.GameFlow;
import game.mechanics.Task;
import menu.Menu;
import menu.MenuAnimation;
import score.HighScoresTable;

/**
 * Run the Game we created.
 * @author Omry Zur
 *
 */
public class SpaceInvaders {
    /**
     * Initialize and run the Game.
     * @param args
     *            - nothing
     */
    public static void main(String[] args) {
        // Create the base of the game
        GUI gui = new GUI("Space Invaders v100", 800, 600);
        DialogManager dm = gui.getDialogManager();
        AnimationRunner ar = new AnimationRunner(gui);
        KeyboardSensor ks = gui.getKeyboardSensor();

        // Hard coded file locations
        final String tableLocation = "highscores.ser";

        // Load table - if there is no table create new one
        File filename = new File(tableLocation);
        HighScoresTable scoresTable = HighScoresTable.loadFromFile(filename);
        if (scoresTable.getHighScores().isEmpty()) {
            try {
                scoresTable.save(filename);
            } catch (IOException e) {
                System.err.println("Unable to load the table");
                e.printStackTrace();
            }
        }

        // Create game flow
        GameFlow gf = new GameFlow(ar, ks, dm, scoresTable);

        //Create the menu and add selctions
        Menu<Task<Void>> menu = new MenuAnimation<>(ks, "Menu");

        menu.addSelection("s", "play", new Task() {
            public Void run() {
                gf.runLevels();
                return null;
            }
        });

        menu.addSelection("h", "High Scores Table", new Task() {
            public Void run() {
                ar.run(new KeyPressStoppableAnimation(ks, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(scoresTable)));
                return null;
            }
        });

        menu.addSelection("q", "Quit", new Task() {
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        // show the menu
        while (true) {
            ar.run(menu);
            Task toDo = menu.getStatus();
            toDo.run();
        }

    }

}

