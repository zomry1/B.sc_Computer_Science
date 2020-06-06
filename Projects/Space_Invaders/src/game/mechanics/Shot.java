package game.mechanics;

import java.awt.Color;

import biuoop.DrawSurface;
import game.body.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
/**
 *
 * @author Omry
 *
 */
public class Shot implements Sprite {
    /**
     * Class Ball.
     * members: center - Point center of the circle,
     *          radius - int, the radius of the circle,
     *          color - Color, the color of the circle,
     *          vel - Velocity, the velocity of the circle movement
     * Constructor: 2 - with Point of the center, with coordinats of the center
     * Methods: getX, getY, getSize, drawOn, setVelocity(dx,dy),
     *          setVelocity(Velocity),getVelocity, moveOneStep, timePassed
     *          addToGame, setY
     */
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment environment;
    private Velocity dVel;
    private boolean isAlien;

    /**
     * Constructor.
     * @param center - the center point of the ball
     * @param isAlien - true if its alien shot, otherwise - false
     * @param environment - the environment the ball is belongs to
     */
    public Shot(Point center, boolean isAlien, GameEnvironment environment) {
        this.center = center;
        this.environment = environment;
        this.isAlien = isAlien;
        //Create the match shot according to the shooter type
        if (isAlien) {
            this.vel = Velocity.fromAngleAndSpeed(180, 600);
            this.radius = 6;
            this.color  = Color.RED;
        } else {
            this.vel = Velocity.fromAngleAndSpeed(0, 600);
            this.radius = 4;
            this.color = Color.WHITE;
        }
    }

    /**
     * Return the x coordinate of the center of the ball.
     * @return the x coordinate of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Return the y coordinate of the center of the ball.
     * @return theyx coordinate of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Return size of the ball.
     * @return size of the ball
     */
    public int getSize() {
        return (int) (Math.PI * Math.pow(this.radius, 2));
    }

    /**
     * Draw the ball on the given surface.
     * @param surface - DrawSurface, the surface we want to draw on it
     */
    public void drawOn(DrawSurface surface) {
        //Set the color of the ball to be his color we set already
        surface.setColor(this.color);
        //Draw the fill circle (ball)
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        //Draw the frame of the circle (ball)
        surface.setColor(java.awt.Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * Set the velocity of the ball - by velocity.
     * @param v - Velocity, to be set
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * Set the velocity of the ball - by dx and dy.
     * @param dx - double, number of x unites to move
     * @param dy - double, number of y unites to move
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity((new Velocity(dx, dy)));
    }

    /**
     * Return the Velocity of the ball.
     * @return Velocity of the ball
     */
    public Velocity getVelocity() {
        return this.vel;
    }


    /**
     * Check if the ball crash and change the velocity according to the crash.
     * call the function applyToPoint to change the center point
     */
    public void moveOneStep() {
        //Create what the next point of the ball should be
        double xNextPoint = this.center.getX() + this.dVel.getDx();
        double yNextPoint = this.center.getY() + this.dVel.getDy();
        Point nextPoint = new Point(xNextPoint, yNextPoint);
        //Create the line of the movement of the ball
        Line movmentLine = new Line(this.center, nextPoint);
        //Fix build of line automate start is the smaller x coordinate
        movmentLine.setStart(this.center);
        movmentLine.setEnd(nextPoint);
        //Get the collisionInfo to the ball
        CollisionInfo info = this.environment.getClosestCollision(movmentLine);
        //If there is no collision just move the ball to the next point
        if (info == null) {
            this.center = nextPoint;
            return;
        }
        int r = 1;
        //If there is a collision check the dx and dy to put the ball right before the collision
        Point newCenter = new Point(info.collitionPoint().getX(), info.collitionPoint().getY());
        if (this.dVel.getDx() > 0) {
            newCenter.setX(info.collitionPoint().getX() - r);
        } else if (this.dVel.getDx() < 0) {
            newCenter.setX(info.collitionPoint().getX() + r);
        }
        if (this.dVel.getDy() > 0) {
            newCenter.setY(info.collitionPoint().getY() - r);
        } else if (this.dVel.getDy() < 0) {
            newCenter.setY(info.collitionPoint().getY() + r);
        }
        this.center = newCenter;
        //Notify shot hit
        info.collisionObject().hit(this);

        //Check if the ball is in a block - spaceship that moves
        for (Collidable col: this.environment.getCollidables()) {
            Rectangle rec = col.getCollistionRectangle();
            if (this.center.getX() > rec.getBottomLeft().getX() && this.center.getX() < rec.getBottomRight().getX()) {
                if (this.center.getY() > rec.getUpperLeft().getY() && this.center.getY() < rec.getBottomLeft().getY()) {
                    //X is ok but we need to set the y to little above the spaceship
                    this.center.setY(rec.getUpperLeft().getY() - 1);
                }
            }
        }
    }

    /**
     * Each time the animation goes - move the ball.
     * @param dt - the relative time
     */
    public void timePassed(double dt) {
        this.dVel = new Velocity(this.vel.getDx() * dt, this.vel.getDy() * dt);
        this.moveOneStep();
    }

    /**
     * Add the Ball to the Sprite collection in a Game.
     * @param g - the Game we want to add to
     */
    public void addToGame(GameLevel g) {
        g.addSprite((Sprite) this);
    }

    /**
     * Set the y of the the center point.
     * @param y - the y we want to set to
     */
    public void setY(double y) {
        this.center.setY(y);
    }

    /**
     * Remove the ball from the sprites list of game.
     * @param g - the game we want to remove the ball from his sprite's list
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Check if the shot is from alien shooter or not.
     * @return true if the shooter wat alien, otherwise - false
     */
    protected boolean isAlien() {
        return this.isAlien;
    }
}
