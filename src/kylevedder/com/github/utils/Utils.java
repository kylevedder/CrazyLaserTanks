/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.utils;

import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;

/**
 *
 * @author Kyle
 */
public class Utils
{

    /**
     * Calculates the distance between the two rectangles.
     *
     * @param c1
     * @param c2
     * @return
     */
    public static float getDistanceBetween(CenteredRectangle c1, CenteredRectangle c2)
    {
        return (float) Math.sqrt(Math.pow(c1.getCenterX() - c2.getCenterX(), 2) + Math.pow(c1.getCenterY() - c2.getCenterY(), 2));
    }
    
    /**
     * Calculates the distance between the rect and the point.
     *
     * @param c1
     * @param c2
     * @return
     */
    public static float getDistanceBetween(CenteredRectangle c1, Point p1)
    {
        return (float) Math.sqrt(Math.pow(c1.getCenterX() - p1.getCenterX(), 2) + Math.pow(c1.getCenterY() - p1.getCenterY(), 2));
    }

    /**
     * Calculates the angle between the anchor and the other item for the
     * turret.
     *
     * @param anchor
     * @param other
     * @return
     */
    public static float calculateTurretAngle(CenteredRectangle anchor, CenteredRectangle other)
    {
        return (float) Math.toDegrees(Math.atan2(other.getCenterY() - anchor.getCenterY(), other.getCenterX() - anchor.getCenterX())) + 90f - anchor.getAngle();
    }

    /**
     * Calculates the angle between the anchor and the other item.
     *
     * @param anchor
     * @param other
     * @return
     */
    public static float calculateAngle(CenteredRectangle anchor, CenteredRectangle other)
    {
        return (float) Math.toDegrees(Math.atan2(other.getCenterY() - anchor.getCenterY(), other.getCenterX() - anchor.getCenterX())) + 90f;
    }
    
    /**
     * Calculates the angle between the anchor and the other item.
     *
     * @param anchor
     * @param other
     * @return
     */
    public static float calculateAngle(float anchorX, float anchorY, float otherX, float otherY)
    {
        return (float) Math.toDegrees(Math.atan2(otherY - anchorY, otherX - anchorX)) + 90f;
    }

    /**
     *
     * If val greater than high, wraps val to # above high to low (inclusive)
     * <p>
     * If val lower than low, wraps val to # below low to high (inclusive)
     * </p>
     *
     * @param val
     * @param low
     * @param high
     * @return
     */
    public static int wrapInt(int val, int low, int high)
    {
        int newVal = val % high;
        return (newVal < 0) ? high + newVal : newVal;
    }

    /**
     *
     * If val greater than high, wraps val to # above high to low (inclusive)
     * <p>
     * If val lower than low, wraps val to # below low to high (inclusive)
     * </p>
     *
     * @param val
     * @param low
     * @param high
     * @return
     */
    public static float wrapFloat(float val, float low, float high)
    {
        float newVal = val % high;
        return (newVal < 0f) ? high + newVal : newVal;
    }

    /**
     *
     * If val greater than high, sets val to high (inclusive)
     * <p>
     * If val lower than low, sets val to low (inclusive)
     * </p>
     *
     * @param val
     * @param low
     * @param high
     * @return
     */
    public static int clampInt(int val, int low, int high)
    {
        if (val > high)
        {
            return high;
        }
        if (val < low)
        {
            return low;
        }
        return val;
    }

    /**
     *
     * If val greater than high, sets val to high (inclusive)
     * <p>
     * If val lower than low, sets val to low (inclusive)
     * </p>
     *
     * @param val
     * @param low
     * @param high
     * @return
     */
    public static float clampFloat(float val, float low, float high)
    {
        if (val > high)
        {
            return high;
        }
        if (val < low)
        {
            return low;
        }
        return val;
    }

    /**
     * If float is NaN, sets it to Zero
     *
     * @param val
     * @return
     */
    public static float verifyFloat(float val)
    {
        if (Float.isNaN(val))
        {
            return 0f;
        }
        return val;
    }

    /**
     * Wraps the delta angle to smooth out degree jumps due to angle roll over
     *
     * @param val
     * @return
     */
    public static float wrapAngleDelta(float val)
    {
        if (val > 180)
        {
            return (val - 360f);
        }
        if (val < -180)
        {
            return (val + 360f);
        }
        return val;
    }

    /**
     * Translates an array of x,y floats into an array of points.
     *
     * @param pointValsArray
     * @return
     */
    public static Point[] getPoints(float[] pointValsArray)
    {
        Point[] points = new Point[pointValsArray.length / 2];
        for (int i = 0; i < pointValsArray.length / 2; i++)
        {
            points[i] = new Point(pointValsArray[2 * i], pointValsArray[2 * i + 1]);
        }
        return points;
    }

    /**
     * Translates two Points into a Line
     *
     * @param p1
     * @param p2
     * @return
     */
    public static Line getLine(Point p1, Point p2)
    {
        return new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Takes the append angle value and appends it to the angle.
     *
     * Wraps to [0 - 360]
     *
     * @param angle
     * @param appendValue
     * @return
     */
    public static float wrapAngle(float angle, float appendValue)
    {
        return ((angle + appendValue) % 360f);
    }

}
