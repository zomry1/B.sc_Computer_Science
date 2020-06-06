package levels;
import java.awt.Color;

import biuoop.DrawSurface;
import game.mechanics.Sprite;

/**
 * Level three background.
 * implements sprite
 * @author Omry Zur
 */
public class LevelThreeBackground implements Sprite {

    /**
     * Draw the background on the surface.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //Draw green background
        d.setColor(java.awt.Color.GREEN);
        d.fillRectangle(0, 0, 800, 600);

        //Draw the body of the building
        d.setColor(new Color(64, 64, 64));
        d.fillRectangle(50, 350, 100, 250);

        //Draw the top of the building
        d.setColor(new Color(96, 96, 96));
        d.fillRectangle(85, 300, 30, 50);

        d.setColor(new Color(32, 32, 32));
        d.fillRectangle(93, 150, 15, 150);

        //Draw the light
        for (int i = 20; i > 0; i--) {
            d.setColor(new Color((i * 5), (i * 5), 230));
            d.fillCircle(100, 150, i);
        }

        //Draw windows
        d.setColor(new Color(102, 178, 255));
        d.fillRectangle(65, 355, 70, 20);
        d.setColor(new Color(192, 192, 192));
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                d.fillRectangle(50 + (j * 15), 355 + (i * 40), 10, 25);
            }
        }

    }

    /**
     * Nothing.
     */
    public void timePassed(double dt) {
    }

}
