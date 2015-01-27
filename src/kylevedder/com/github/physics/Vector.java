/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

/**
 *
 * @author Kyle
 */
public class Vector
{

    private float speed;
    private float angle;

    public static float ANGLE_MIN = 0;
    public static float ANGLE_MAX = 360;

    /**
     * Vector object, where speed is always positive in m/s and angle is in
     * degrees.
     *
     * <p>
     * 0 angle with positive speed is positive on X axis.
     * </p>
     *
     * @param speed
     * @param angle
     */
    public Vector(float speed, float angle)
    {
        this.speed = Math.abs(speed);
        this.angle = angle;
    }

    public Vector(float xComp, float yComp, int unused)
    {
        //Pythagorean Therom
        this.speed = (float) Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp, 2));
        this.angle = Utils.wrapFloat((float) Math.toDegrees(Math.atan2(yComp, xComp)), ANGLE_MIN, ANGLE_MAX);
    }

    /**
     * Gets the X component of the vector.
     *
     * @return
     */
    public float getXComp()
    {
        return this.speed * (float) Math.sin(Math.toRadians(angle));
    }

    /**
     * Gets the Y component of the vector.
     *
     * @return
     */
    public float getYComp()
    {
        return this.speed * (float) Math.cos(Math.toRadians(angle));
    }

    /**
     * Gets the angle of the vector in degrees.
     *
     * @return
     */
    public float getAngle()
    {
        return this.angle;
    }

    /**
     * Gets the speed of the vector.
     *
     * @return
     */
    public float getSpeed()
    {
        return speed;
    }

    /**
     * Sets the angle
     *
     * @param angle
     */
    public void setAngle(float angle)
    {
        this.angle = Utils.wrapFloat(angle, ANGLE_MIN, ANGLE_MAX);
    }

    /**
     * Adds to the angle
     *
     * @param angleAdd
     */
    public void addAngle(float angleAdd)
    {
        this.angle += angleAdd;
        this.angle = Utils.wrapFloat(angle, ANGLE_MIN, ANGLE_MAX);
    }

    /**
     * Sets the speed
     *
     * @param speed
     */
    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    /**
     * Adds speed
     *
     * @param speedAdd
     */
    public void addSpeed(float speedAdd)
    {
        this.speed += speedAdd;
    }

    @Override
    public String toString()
    {
        return "X Comp: " + this.getXComp() + " Y Comp: " + this.getYComp() + " Speed: " + this.getSpeed() + " Angle: " + this.getAngle();
    }

    /**
     * Draws the vector, centered around the given X and Y axis.
     *
     * @param g
     * @param x
     * @param y
     * @throws SlickException
     */
    public void render(Graphics g, float x, float y, float speedScale)
    {
        g.setColor(Color.red);
        g.drawLine(x, y, x + this.getXComp() * speedScale, y - this.getYComp() * speedScale);
        g.drawOval(x + this.getXComp() * speedScale - 4, y - this.getYComp() * speedScale - 4, 8, 8);
    }

    /**
     * Adds two vectors together.
     *
     * @param v1
     * @param v2
     * @return Added Vector
     */
    public static Vector add(Vector v1, Vector v2)
    {
        return new Vector(v1.getXComp() + v2.getXComp(), v1.getYComp() + v2.getYComp(), 0/*unused*/);
    }

    /**
     * Flips the axis of the Vector
     *
     * @param v Vector to flip
     * @param xAxis if flip X
     * @param yAxis if flip Y
     * @return Flipped Vector
     */
    public static Vector flipAxis(Vector v, boolean xAxis, boolean yAxis)
    {
        return new Vector(v.getXComp() * ((xAxis) ? -1 : 1), v.getYComp() * ((yAxis) ? -1 : 1), 0/*unused*/);
    }

    /**
     * Vector with the magnitude and direction of gravity.
     *
     * @return
     */
    public static Vector gravityVector(int delta)
    {
        return new Vector(.0981f / ((float) delta / 1000f), 270);//Vector(0.00098f / ((float) delta / 1000f), 270);
    }

    /**
     * Vector with the magnitude and direction of zero.
     *
     * @return
     */
    public static Vector zeroVector()
    {
        return new Vector(0, 0);
    }
}
