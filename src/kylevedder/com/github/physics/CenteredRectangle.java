/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

/**
 *
 * @author Kyle
 */
public class CenteredRectangle
{

    private float angle;
    private Polygon p;
    private float centerX;
    private float centerY;
    private float width;
    private float height;

    public CenteredRectangle(CenteredRectangle cr)
    {
        this.centerX = cr.centerX;
        this.centerY = cr.centerY;
        this.width = cr.width;
        this.height = cr.height;
        this.angle = cr.getAngle();                        
        p = cr.p;
    }
    
    public CenteredRectangle(float centerX, float centerY, float width, float height, float rotation)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = rotation;
        this.width = width;
        this.height = height;
        //centers rectangle
        Rectangle r = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);
        //angles rectangle
        p = (Polygon) r.transform(Transform.createRotateTransform((float) Math.toRadians(rotation), centerX, centerY));
    }

    /**
     * Update position using delta values; delta x, delta y, and delta angle
     *
     * @param dx
     * @param dy
     * @param dangle
     */
    public void updateDelta(float dx, float dy, float dangle)
    {
        p = (Polygon) p.transform(Transform.createRotateTransform((float) Math.toRadians(dangle), centerX, centerY));
        p = (Polygon) p.transform(Transform.createTranslateTransform(dx, dy));
        this.centerX += dx;
        this.centerY += dy;
        this.angle += dangle;
    }

    /**
     * Update position using absolute values; absolute x, absolute y, and
     * absolute angle
     *
     * @param x
     * @param y
     * @param angle
     */
    public void updateAbs(float x, float y, float angle)
    {
        p = (Polygon) p.transform(Transform.createRotateTransform((float) Math.toRadians(angle - this.angle), centerX, centerY));
        p = (Polygon) p.transform(Transform.createTranslateTransform(x - centerX, y - centerY));
        this.centerX = x;
        this.centerY = y;
        this.angle = angle;
    }

    /**
     * Draw the rectangle plus helper polygons.
     *
     * @param g
     */
    public void render(Graphics g)
    {
        g.drawOval(centerX - 4, centerY - 4, 8, 8);
        g.draw(p);
    }

    /**
     * Gets the minimum X of the rectangle.
     *
     * @return
     */
    public float getMinX()
    {
        return p.getMinX();
    }

    /**
     * Gets the maximum X of the rectangle.
     *
     * @return
     */
    public float getMaxX()
    {
        return p.getMaxX();
    }

    /**
     * Gets the minimum Y of the rectangle.
     *
     * @return
     */
    public float getMinY()
    {
        return p.getMinY();
    }

    /**
     * Gets the maximum Y of the rectangle.
     *
     * @return
     */
    public float getMaxY()
    {
        return p.getMaxY();
    }

    /**
     * Gets the X of the center of the object
     * @return 
     */
    public float getCenterX()
    {
        return centerX;
    }

    /**
     * Gets the Y of the center of the object
     * @return 
     */
    public float getCenterY()
    {
        return centerY;
    }

    
    
    /**
     * Gets the angle of the box in degrees.
     * @return 
     */
    public float getAngle()
    {
        return angle;
    }

    /**
     * Sets the angle of the box in degrees
     * @param angle 
     */
    public void setAngle(float angle)
    {
        p = (Polygon) p.transform(Transform.createRotateTransform((float) Math.toRadians(angle - this.angle), centerX, centerY));
        this.angle = angle;
    }

    
    
    /**
     * Gets the rectangle width
     * @return 
     */
    public float getWidth()
    {
        return width;
    }

    /**
     * Gets the rectangle height
     * @return 
     */
    public float getHeight()
    {
        return height;
    }
    
    
    

    
    /**
     * Gets the points of the CenteredRectangle
     *
     * @return
     */
    public Point[] getPoints()
    {
        return Utils.getPoints(p.getPoints());
    }

    /**
     * Gets the Lines for the sides of the items.
     *
     * @return sides. Will return null if the shape contains less than two points.
     */
    public Line[] getSides()
    {
        Point[] points = getPoints();
        if (points.length > 1)
        {
            Line[] lines = new Line[points.length];
            for (int pointitr = 0; pointitr - 1 < points.length; pointitr++)
            {
                //lowPoint used as an iterator here
                lines[pointitr] = Utils.getLine(points[pointitr], points[pointitr + 1]);
            }
            lines[lines.length] = Utils.getLine(points[0], points[points.length - 1]);
            return lines;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Gets the polygon of the rectangle.
     *
     * @return
     */
    public Polygon getPolygon()
    {
        return p;
    }
    
    /**
     * Checks to see if this CenteredRectangle collides with the passed CenteredRectangle.
     * @param c
     * @return 
     */
    public boolean collides(CenteredRectangle c)
    {
        return p.intersects(c.getPolygon());
    }
    
    /**
     * Checks to see if this CenteredRectangle collides with the passed polygon.
     * @param c
     * @return 
     */
    public boolean collides(Polygon p)
    {
        return p.intersects(p);
    }
}
