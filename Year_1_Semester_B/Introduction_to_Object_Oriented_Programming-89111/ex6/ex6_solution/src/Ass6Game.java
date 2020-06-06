import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.body.GameFlow;
import levels.LevelFour;
import levels.LevelInformation;
import levels.LevelOne;
import levels.LevelThree;
import levels.LevelTwo;
import score.HighScoresTable;

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
        DialogManager dm = gui.getDialogManager();
        AnimationRunner ar  = new AnimationRunner(gui);
        KeyboardSensor ks = gui.getKeyboardSensor();
        //load the score file
        
        
        File filename = new File("D:\\Projects\\Intro OOP\\Assigment 6\\src\\highscores.ser");
        HighScoresTable scoresTable = HighScoresTable.loadFromFile(filename);
        if(scoresTable.getHighScores().isEmpty()) {
        	try {
				scoresTable.save(filename);
			} catch (IOException e) {
				System.err.println("Unable to load the table");
				e.printStackTrace();
			}
        }
        
        //load the levels
        GameFlow gf = new GameFlow(ar, ks, dm);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        //Create the levels
        LevelOne one = new LevelOne();
        LevelTwo two = new LevelTwo();
        LevelThree three = new LevelThree();
        LevelFour four = new LevelFour();

        if (args.length == 0) {
            //levels.add(one);
            //levels.add(two);
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
        //
        //
        //Create the menu
        Menu<Task<Void>> menu = new MenuAnimation<>(ar, ks);
        //add Selections
        
        menu.addSelection("h", "High Scores Table", new Task() {
        	public Void run() {
        		ar.run(new KeyPressStoppableAnimation(ks, KeyboardSensor.SPACE_KEY, new HighScoresAnimation(scoresTable)));
				return null;
        	}
        });
        
        menu.addSelection("q", "Quit", new Task() {
        	public Void run() {
        		System.exit(0);
        		return null; 
        	}
        });
        
        menu.addSelection("p", "Play", new Task() {
        	public Void run() {
        		gf.runLevels(levels);
        		return null;
        	}
        });
        //show the menu
        
        while(true) {
        	ar.run(menu);
        	Task toDo  = menu.getStatus();
        	toDo.run();
        }
        
    }

}
