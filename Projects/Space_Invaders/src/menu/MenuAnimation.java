package menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Show animation of the menu with sub-menues.
 * implements menu<T>
 * @author Omry Zur
 *
 * @param <T> - the type we want to run
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<T> tasks;
    private List<String> keys;
    private List<String> messages;
    private KeyboardSensor ks;
    private T status;
    private boolean choosed;
    private String title;
    //privates for check already presse cases
    private boolean firstRun;
    private String keyPressed;
    //private for submenus

    /**
     * Construcotr.
     * @param ks - the keyboard to check if key pressed
     * @param title - the title of the menu
     */
    public MenuAnimation(KeyboardSensor ks, String title) {
        this.ks = ks;
        this.title = title;
        this.tasks = new ArrayList<T>();
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        //set true for first run
        this.firstRun = true;
    }

    /**
     * Draw the menu and check for pressed key.
     * @param d - the surface we want to draw on
     * @param dt - the realtive time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Draw the menu

        //Background and frame
        d.setColor(new Color(105, 105, 105));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        for (int i = 12; i > 0; i--) {
            d.setColor(new Color(255 - i * 5, 255 - i * 5, 255 - i * 5));
            d.drawRectangle(0, 0, d.getWidth() - i, d.getHeight() - i);
        }
        //Draw the title
        d.setColor(Color.BLUE);
        d.drawText(50, 50, title, 50);
        d.drawText(49, 50, title, 50);
        d.drawText(48, 50, title, 50);
        d.setColor(Color.YELLOW);
        d.drawText(52, 50, title, 50);
        //Draw the options
        int x = 80;
        int y = 120;
        //Draw the options
        for (String key: this.keys) {
            d.setColor(Color.YELLOW);
            d.drawText(x, y, "(" + key + ")", 40);
            d.setColor(Color.BLUE);
            d.drawText(x - 2, y, "(" + key + ")", 40);
            d.setColor(Color.BLUE);
            d.drawText(x + 150, y, this.messages.get(this.keys.indexOf(key)), 40);
            d.setColor(Color.YELLOW);
            d.drawText(x + 148, y, this.messages.get(this.keys.indexOf(key)), 40);
            y += 70;
        }
        // Check if key pressed from other task and key already pressed cases
        if (this.firstRun) {
            for (String key : this.keys) {
                if (this.ks.isPressed(key)) {
                    this.keyPressed = key;
                }
            }
            this.firstRun = false;
        } else if (this.keyPressed != null) {
            if (!this.ks.isPressed(this.keyPressed)) {
                this.keyPressed = null;
            }
        }

        for (String key : this.keys) {
            if (this.ks.isPressed(key) && !key.equals(this.keyPressed)) {
                this.status = this.tasks.get(this.keys.indexOf(key));
                this.choosed = true;
                break;
            }
        }
    }

    /**
     * @return true option selected - stop the animation and reset members.
     */
    public boolean shouldStop() {
        if (this.choosed) {
            this.choosed = false;
            this.firstRun = true;
            return true;
        }
        return false;
    }

    /**
     * Add selection to the menu.
     * @param key - the key for the option
     * @param message - the title of the option
     * @param returnVal - the value return from the option select
     */
    public void addSelection(String key, String message, T returnVal) {
        this.tasks.add(returnVal);
        this.keys.add(key);
        this.messages.add(message);
    }

    /**
     * @return - the seleced option.
     */
    public T getStatus() {
        return this.status;
    }

}
