package levels;
import java.awt.Color;
import java.util.Random;

import biuoop.DrawSurface;
import game.mechanics.Sprite;
import geometry.Point;

/**
 * Level four background.
 * implements sprite
 * @author Omry Zur
 */
public class LevelFourBackground implements Sprite {

    /**
     * Draw the background on the surface.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //Draw blue background
        d.setColor(new Color(51, 153, 255));
        d.fillRectangle(0, 0, 800, 600);

        drawCloud(d, new Point(200, 400));
        drawCloud(d, new Point(500, 200));
    }

    /**
     * Nothing.
     */
    public void timePassed() {
    }

    /**
     * Draw cloud.
     * @param d - the surface we want to draw on
     * @param middleCloud - the middle point of the cloud
     */
    private void drawCloud(DrawSurface d, Point middleCloud) {
        int xMiddle = (int) middleCloud.getX();
        int yMiddle = (int) middleCloud.getY();
        Random rand = new Random();
        int randNum1;
        int randNum2;
        int randNum3;
        int randNum4;
        int randNum5;
        int randNum6;

        d.setColor(new Color(170, 170, 170));
        d.fillCircle(xMiddle - 20, yMiddle + 10, 30);

        d.setColor(new Color(165, 165, 165));
        d.fillCircle(xMiddle - 10, yMiddle + 40, 20);

        d.setColor(new Color(160, 160, 160));
        d.fillCircle(xMiddle, yMiddle, 30);

        d.setColor(new Color(150, 150, 150));
        d.fillCircle(xMiddle + 45, yMiddle + 10, 30);

        d.setColor(new Color(140, 140, 140));
        d.fillCircle(xMiddle + 15, yMiddle + 40, 20);

        //Draw drops
        d.setColor(new Color(102, 178, 255));
        for (int i = 20; i > 0; i--) {
            randNum1 = rand.nextInt(200);
            randNum2 = rand.nextInt(200);
            d.fillOval(xMiddle - 100 + randNum1, yMiddle + randNum2, 10, 15);
        }

        //Draw lightning
        d.setColor(new Color(255, 255, 0));
        for (int i = 3; i > 0; i--) {
            randNum1 = rand.nextInt(100) - 50;
            randNum2 = rand.nextInt(100);
            randNum3 = rand.nextInt(200) - 100;
            randNum4 = rand.nextInt(200);
            randNum5 = rand.nextInt(300) - 200;
            randNum6 = rand.nextInt(300);
            d.drawLine(xMiddle, yMiddle, xMiddle + randNum1, yMiddle + randNum2);
            d.drawLine(xMiddle + randNum1, yMiddle + randNum2, xMiddle + randNum3, yMiddle + randNum4);
            d.drawLine(xMiddle + randNum3, yMiddle + randNum4, xMiddle + randNum5, yMiddle + randNum6);
        }
    }
}
