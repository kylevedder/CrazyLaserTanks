/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.utils;

import kylevedder.com.github.main.GameEngine;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.main.ObjectBoilerplate;
import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;

/**
 *
 * @author Kyle
 */
public class Utils
{

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

    /**
     * Useful utility to check if an item needs to be rendered.
     *
     * @param rect rectangle of the item
     * @param image image of the item
     * @param scale scale of the item
     * @param renderOffsetX renderer offset of the item in the X
     * @param renderOffsetY renderer offset of the item in the Y
     * @return
     */
    public static boolean isVisible(CenteredRectangle rect, Image image, float scale, float renderOffsetX, float renderOffsetY)
    {
        return //within screen X
                rect.getMinX() - renderOffsetX + image.getWidth() * scale > 0 && rect.getMinX() - renderOffsetX < MainApp.SCREEN_WIDTH
                && //within screen Y
                rect.getMinY() - renderOffsetY + image.getHeight() * scale > 0 && rect.getMinY() - renderOffsetY < MainApp.SCREEN_WIDTH;
    }

    public static int convertObjectToGroundTileX(ObjectBoilerplate o)
    {
        return (int) o.getRectangle().getCenterX() / GameEngine.TILE_SIZE;
    }

    public static int convertObjectToGroundTileY(ObjectBoilerplate o)
    {
        return (int) o.getRectangle().getCenterY() / GameEngine.TILE_SIZE;
    }
}
