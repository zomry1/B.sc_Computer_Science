package levels;
import biuoop.DrawSurface;
import game.mechanics.Sprite;

/**
 * Level one background.
 * implements sprite
 * @author Omry Zur
 */
public class LevelOneBackground implements Sprite {

    /**
     * Draw the background on the surface.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //The black background
        d.setColor(java.awt.Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        //The circels
        d.setColor(java.awt.Color.BLUE);
        d.drawCircle(400, 150, 100);
        d.drawCircle(400, 150, 75);
        d.drawCircle(400, 150, 50);

        //Draw the lines
        d.drawLine(275, 150, 525, 150);
        d.drawLine(400, 40, 400, 275);

    }

    /**
     * Nothing.
     */
    public void timePassed() {
    }

}
