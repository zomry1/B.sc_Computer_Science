package game.mechanics;

import java.util.List;
import java.util.Random;
/**
 * List of lists (rows) of alien.
 * handle everything the alien formation needs
 * moves, shots, new turn.
 * @author Dror-PC
 *
 */
public class AlienFormation {
    private List<List<Alien>> rows;

    private boolean toRight;
    private double relativeSpeedTime;
    private int speedTime;
    private long lastMoveInMili;
    private long lastShotInMili;

    /**
     * Constructor.
     * @param level - the level of the game (speed of movment)
     * @param rows - the list of lists of alien
     */
    public AlienFormation(int level, List<List<Alien>> rows) {
        this.speedTime = 40 - (level * 4);
        if (this.speedTime < 0) {
            this.speedTime = 0;
        }
        this.relativeSpeedTime = this.speedTime;
        this.rows = rows;

        this.toRight = true;
        this.lastMoveInMili = System.currentTimeMillis();
        this.lastShotInMili = System.currentTimeMillis();
    }

    /**
     * Handle each frame, check for move and shot.
     */
    public void notifyFormation() {
        //Check if pass enought time to shot again
        if (System.currentTimeMillis() - this.lastShotInMili > 500) {
            this.shot();
            this.lastShotInMili = System.currentTimeMillis();
        }

        //Check if pass enought time to shot again
        if (System.currentTimeMillis() - this.lastMoveInMili > this.speedTime) {
            //Update the last move time
            this.lastMoveInMili = System.currentTimeMillis();
            if (this.toRight) {
                this.moveX(5);
                //Check if we out of bounds (yes - change direction and down line)
                if (!this.checkBounds()) {
                    this.toRight = false;
                    this.relativeSpeedTime = (this.relativeSpeedTime / 11) * 10;
                    this.moveY(10);
                }
            } else {
                this.moveX(-5);
                if (!this.checkBounds()) {
                    this.toRight = true;
                    this.relativeSpeedTime = (this.relativeSpeedTime / 11) * 10;
                    this.moveY(10);
                }
            }
        }
    }

    /**
     * Move the alien by x.
     * @param x - the number of pixels we want to move the alien
     */
    private void moveX(int x) {
        for (List<Alien> list: this.rows) {
            for (Alien al: list) {
                al.moveX(x);
            }
        }
    }

    /**
     * Move the alien by x.
     * @param y - the number of pixels we want to move the alien
     */
    private void moveY(int y) {
        for (List<Alien> list: this.rows) {
            for (Alien al: list) {
                al.moveY(y);
            }
        }
    }

    /**
     * Check if the alien in the screen border.
     * @return in the screen border - true, otherwise - false
     */
    private boolean checkBounds() {
        for (List<Alien> list: this.rows) {
            if (!list.isEmpty()) {
                //Check the most left one and right one in the row (list)
                if (list.get(0).getRect().getBottomLeft().getX() <= 30
                        || list.get(list.size() - 1).getRect().getBottomRight().getX() >= 770) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the aliens pass the shields.
     * @return pass the shields - false, otherwise - true
     */
    public boolean checkDown() {
        int shieldY = 480;

        for (List<Alien> list: this.rows) {
            if (!list.isEmpty()) {
                if (list.get(0).getRect().getBottomLeft().getY() > shieldY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * center the formation to a new turn.
     */
    public void centerFormation() {
        int mostLeft = 801;
        int mostUp = 601;
        for (List<Alien> list: this.rows) {
            if (!list.isEmpty()) {
                //Get the most left and the most down of the alien
                for (Alien al: list) {
                    if (al.getCollistionRectangle().getUpperLeft().getX() < mostLeft) {
                        mostLeft = (int) al.getCollistionRectangle().getUpperLeft().getX();
                    }
                    if (al.getCollistionRectangle().getUpperLeft().getY() < mostUp) {
                        mostUp = (int) al.getCollistionRectangle().getUpperLeft().getY();
                    }
                }
            }
        }
        //Check how much to move all the formation
        int moveLeft = -(mostLeft - 50);
        int moveUp = -(mostUp - 50);
        //Move the aliens
        for (List<Alien> list: this.rows) {
            for (Alien al: list) {
                al.moveX(moveLeft);
                al.moveY(moveUp);
            }
        }

        //set the speed to the start speed of the level
        this.relativeSpeedTime = this.speedTime;
        this.toRight = true;
    }

    /**
     * get random alien (from the lowest row in the column) and shot from it.
     */
    private void shot() {
        Random rand = new Random();
        //Random column
        int column = rand.nextInt(10);
        //Set already shot to false
        boolean shooted = false;
        //While dont shot already
        while (!shooted) {
            //Start from the most down row
            for (int i = 4; i >= 0; i--) {
                for (Alien al: this.rows.get(i)) {
                    if (al.getColumn() == column) {
                        //If the alien is in the correct column shot from it.
                        al.shot();
                        //Set true that shot
                        shooted = true;
                        break;
                    }
                }
                if (shooted) {
                    break;
                }
            }
            //We got to the first column start from the last column
            if (column == 0) {
                column = 9;
            } else {
                //Move to the previous column
                column--;
            }
        }
    }

    /**
     * Remove alien from the list.
     * @param al - the alien we want to remove
     */
    public void removeAlien(Alien al) {
        for (List<Alien> list: this.rows) {
            list.remove(al);
        }
    }
}
