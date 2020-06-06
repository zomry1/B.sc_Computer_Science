package levels;
import java.awt.Color;

import biuoop.DrawSurface;
import game.mechanics.Sprite;

/**
 * Level two background.
 * implements sprite
 * @author Omry Zur
 */
public class LevelTwoBackground implements Sprite {

    /**
     * Draw the background on the surface.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //Draw white background
        d.setColor(java.awt.Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);

        //Draw sunbeam
        double green = 153;
        double blue = 51;
        for (int i = 780; i > 20; i = i - 7) {
            d.setColor(new Color(255, (int) green, (int) blue));
            d.drawLine(100, 100, i, 250);
            green += 0.2;
            blue += 0.2;
        }

        //Draw sun
        for (int i = 50; i > 0; i--) {
            d.setColor(new Color(255, 255 - (i * 2), 0));
            d.fillCircle(100, 100, i);
        }

    }

    /**
     * Nothing.
     */
    public void timePassed() {
    }

}
