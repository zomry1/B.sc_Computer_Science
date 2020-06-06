package com.example.ofiriki.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Joystick extends View {
    private Paint stick;
    private Paint circle;
    private int x;
    private int y;

    private int width;
    private int height;

    private float circleRadius;
    private float stickRadius;

    private float aileron;
    private float elevator;

    /**
     * constructor - paint the UI
     * @param context - the context
     */
    public Joystick(Context context) {
        super(context);

        stick = new Paint(Paint.ANTI_ALIAS_FLAG);
        stick.setColor(Color.YELLOW);
        stick.setStyle(Paint.Style.FILL);

        circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setColor(Color.BLUE);
        circle.setStyle(Paint.Style.FILL);
    }

    /**
     * return the aileron
     * @return the aileron
     */
    public float getAileron() {
        return aileron;
    }

    /**
     * return the elevator
     * @return the elevator
     */
    public float getElevator() {
        return elevator;
    }

    /**
     * this method brings the joystick back to the center
     */
    public void resetJoystick() {
        x = width / 2;
        y = height / 2;
        // redraw
        this.invalidate();
    }

    /**
     * this method tells whether the user pressed on the joystick
     * @param touchX - the x value
     * @param touchY - the y value
     * @return - true if the user did, false otherwise
     */
    public boolean isPressed(float touchX, float touchY) {
        // check if the distance between the pressing point and center is lower than the radius
        if(Math.sqrt(Math.pow((touchX - width /2), 2) + Math.pow((touchY - height /2), 2)) > stickRadius) {
            return false;
        }
        return true;
    }

    /**
     * this method
     * @param w - the width
     * @param h - the height
     * @param oldw - the old width
     * @param oldh - the old height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);

        this.width = w;
        this.height = h;

        x = width /2;
        y = height /2;
        // set the padding
        int xpad = getPaddingLeft() + getPaddingRight();
        int ypad = getPaddingBottom() + getPaddingTop();
        //set the radiuses of the two circles
        circleRadius = Math.min((w - xpad),(h-ypad));
        circleRadius = (float) (circleRadius/2.5);

        stickRadius = circleRadius / 5;
    }

    /**
     * this method is drawing the two circles
     * @param canvas - the canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, circleRadius, circle );

        canvas.drawCircle(x, y, stickRadius, stick );
    }

    /**
     * this method calculates the x and y the joystick should be at and the new
     * aileron's and elevetor's values
     * @param touchX of the user
     * @param touchY of the user
     */
    public void gesture(float touchX, float touchY) {
        double distance;
        // check if the distance between the pressing point and center is higher than the radius
        if((distance = Math.sqrt(Math.pow((touchX - (width / 2)),2) + Math.pow((touchY - (height/ 2)),2)))> circleRadius) {
            // calculate the point inside the circle in the same angle
            float ratio = (float) (circleRadius / distance);
            x = (int) (width/2 + (touchX-(width/2)) * ratio);
            y = (int) (height/2 + (touchY-(height/2)) * ratio);

        }
        else {
            x = (int)touchX;
            y = (int)touchY;
        }
        //redraw
        this.invalidate();
        //calculate the aileron's and elevetor's values
        aileron = (x - width / 2) / circleRadius;
        elevator = -(y - height / 2) / circleRadius;
    }

}