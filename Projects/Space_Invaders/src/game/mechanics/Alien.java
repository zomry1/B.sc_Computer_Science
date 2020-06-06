package game.mechanics;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import game.body.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
/**
 * Alien extend block.
 * can also shot, and move and have a column.
 * @author Omry Zur
 *
 */
public class Alien extends Block {
    private Image alienImage;
    private int column;
    private GameLevel game;

    /**
     * Constructor, use the block constructor and also add a few members.
     * @param rect - the rectangle of the block.
     * @param column - the column of the alien in the alien formation
     * @param game - the gamelevel , to add the shots to.
     */
    public Alien(Rectangle rect, int column, GameLevel game) {
        //Use the block construcotor for the rectangles
        super(rect);
        //If its the first alien we create, load the alienImage
        Image img = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("enemy.png");
            img = ImageIO.read(is);
        } catch (Exception e) {
            // There is no image for the alien, quit the game
            System.out.println("Error load image of block");
            System.exit(0);
        }
        this.alienImage = img;
         this.column = column;
         this.game = game;
    }
    /**
     * Override the block drawOn, draws the image of the alien.
     * @param d - the draw surface we want to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        int topLeftCornerX = (int) this.getRect().getUpperLeft().getX();
        int topLeftCornerY = (int) this.getRect().getUpperLeft().getY();
        d.drawImage(topLeftCornerX, topLeftCornerY, this.alienImage);
    }

    /**
     * Override, the block hit, check if the hit is by alien - only remove the shot.
     * if it by spaceship kill the alien
     * @param hitter - the shot that hit the alien
     */
    @Override
    public void hit(Shot hitter) {
        //Is not alien shoot
        if (!hitter.isAlien()) {
            this.notifyHit(hitter);
            //Remove one from the hits left to the Block
            for (HitListener hl: this.getHitListeners()) {
                this.removeHitListener(hl);
            }
        } else {
            //Its alien shoot
            hitter.removeFromGame(game);
        }
    }

    /**
     * Move the rectangle of the alien by x in the x coordinate.
     * @param x - the number of pixels we want to move
     */
    public void moveX(int x) {
        this.getRect().moveRectangleX(x);
    }

    /**
     *  Move the rectangle of the alien by x in the x coordinate.
     * @param y - the number of pixels we want to move
     */
    public void moveY(int y) {
        this.getRect().moveRectangleY(y);
    }

    /**
     * Get the column of  the alien in the alien formation.
     * @return the column of the alien
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Shot from the alien.
     * create shot and add it to the list of shots and the game.
     */
    public void shot() {
        Point shotPoint = new Point(this.getRect().getUpperLeft().getX()
                + (this.getRect().getWidth() / 2), this.getRect().getBottomLeft().getY() + 10);
        Shot alienShot = new Shot(shotPoint, true, game.getEnvironmet());
        alienShot.addToGame(this.game);
        this.game.addShot(alienShot);
    }

}
